package com.yc.web.controllers;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.bean.Resfood;
import com.yc.service.FileService;
import com.yc.service.ResfoodBiz;
import com.yc.utils.JwtTokenUtil;
import com.yc.web.model.JsonModel;
import com.yc.web.model.ResfoodVO;
import com.yc.web.models.MyPageBean;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

//全部  Rest API 风格
@RestController
@RequestMapping("resfood")   //请求路径的前缀  http://localhost:8080/resfood/ + xxx
@Slf4j
@Tag(name = "菜品API" , description = "菜品管理相关接口")
@RefreshScope  // 动态刷新配置文件
public class ResfoodController {

    @Autowired
    private ResfoodBiz resfoodBiz;    // 注入商品服务

    @Autowired
    private FileService fileService;    // 注入文件上传的服务

    @Value("${res.date.format}")
    private String dateFormat;

    @Autowired
    private RedisTemplate redisTemplate;

    // @PathVariable 将路径变量映射到控制器方法的参数中
    @GetMapping(value = "findById/{fid}")  //GET  http://localhost:8080/resfood/findById/2
    @SentinelResource("hotkey-page") // 限流 流控资源名
    public JsonModel findById(@PathVariable int fid) {

        Date date = new Date();
        DateFormat df = new SimpleDateFormat( dateFormat );
        log.info("测试配置中心的刷新效果:"+df.format(date) );

        JsonModel jm = new JsonModel();
        Resfood rf = null;
        try{
            rf = resfoodBiz.findById(fid);
        }catch (Exception e){
            e.printStackTrace();
            jm.setCode(0);
            jm.setMsg(e.getMessage());
            return jm;
        }
        jm.setCode(1);
        jm.setObj(rf);
        return jm;
    }

    @GetMapping(value = "findAll")  //GET  http://localhost:8080/resfood/findAll
    public JsonModel findAll() {
        JsonModel jm = new JsonModel();
        List<Resfood> list = null;
        try{
            list = resfoodBiz.findAll();
        }catch (Exception e){
            e.printStackTrace();
            jm.setCode(0);
            jm.setMsg(e.getMessage());
            return jm;
        }
        jm.setCode(1);
        jm.setObj(list);
        return jm;
    }

    //利用Json数据做参数
    @Operation(summary = "分页查询",description = "分页查询")
    @ApiResponse(responseCode = "200",description = "分页查询结果成功",
        content = @Content(mediaType = "application/json" ,
        schema = @Schema(implementation = MyPageBean.class)) ) //响应数据的格式
    @PostMapping(value = "findByPage")  // POST  http://localhost:8080/resfood/findByPage
    public JsonModel findByPage( @RequestBody @Parameter(description = "分页信息") MyPageBean myPageBean) {
        JsonModel jm = new JsonModel();
        Page<Resfood> page = null;
        page = resfoodBiz.findByPage(myPageBean.getCurrent(), myPageBean.getSize(), myPageBean.getSortby(), myPageBean.getSort());
        BeanUtils.copyProperties(page, myPageBean);
        myPageBean.calculate();
        jm.setCode(1);
        jm.setObj(myPageBean);
        return jm;
    }

    //利用Json数据做参数
    @Operation(summary = "分页查询",description = "分页查询")
    @ApiResponse(responseCode = "200",description = "分页查询结果成功",
            content = @Content(mediaType = "application/json" ,
                    schema = @Schema(implementation = MyPageBean.class)) ) //响应数据的格式
    @PostMapping(value = "findByPageWithCondition")  // POST  http://localhost:8080/resfood/findByPageWithCondition
    public JsonModel findByPageWithCondition( @RequestBody @Parameter(description = "分页信息") MyPageBean myPageBean) {
        JsonModel jm = new JsonModel();
        Page<Resfood> page = null;
        page = resfoodBiz.findFoods(myPageBean.getResfood(), myPageBean.getCurrent(), myPageBean.getSize(), myPageBean.getSortby(), myPageBean.getSort());
        BeanUtils.copyProperties(page, myPageBean);
        myPageBean.calculate();
        jm.setCode(1);
        jm.setObj(myPageBean);
        return jm;
    }
    //利用Json数据做参数
    @Operation(summary = "添加菜品",description = "添加菜品")
    @ApiResponse(responseCode = "200",description = "添加菜品成功",
            content = @Content(mediaType = "application/json" ,
                    schema = @Schema(implementation = ResfoodVO.class)) ) //响应数据的格式
    @PostMapping(value = "addResfood")  // POST  http://localhost:8080/resfood/addResfood
    public JsonModel addResfood( @ModelAttribute ResfoodVO resfoodVO) throws ExecutionException, InterruptedException {
        String fphotoFilePath = "";  // 上传图片的位置

        //异步上传图片  异步操作
        CompletableFuture<String> fileUrlFuture = fileService.upload(resfoodVO.getFphotofile());
        //阻塞当前线程直到异步操作完成
        fphotoFilePath = fileUrlFuture.get();

        JsonModel jm = new JsonModel();
        Resfood resfood = new Resfood(); //PO对象，数据库表的字段

        //VO -> PO  字段的复制
        //忽略  fphotofile  字段  不复制
        BeanUtils.copyProperties(resfoodVO, resfood);

        resfood.setFphoto(fphotoFilePath); // OSS中图片的地址存入PO中
        int fid = resfoodBiz.addResfood(resfood);// 插入数据库  返回主键值

        resfoodVO.setFid(fid); //VO 是 前端页面展示的对象 所有将 fid 赋值给 VO
        resfoodVO.setFphoto(fphotoFilePath); // OSS中图片的地址存入VO中
        jm.setCode(1);
        jm.setObj(resfoodVO);
        return jm;
    }

    @Autowired
    private JwtTokenUtil jwtUtil;
    //商品点赞
    @GetMapping(value = "clickPraise/{fid}") //GET
    public JsonModel clickPraise(@PathVariable Integer fid , HttpServletRequest request) {
        JsonModel jm = new JsonModel();
        String token = request.getHeader("token");
        Claims claims = jwtUtil.decodeJWTWithkey(token);
        String userid = claims.get("userid").toString();
        // 在redis中使用的是 <String,set>
        //      key: user_praise_fid:1001   value: 1001  1002  1003  1004  1005  1006  1007  1008  1009  1010
        if ( this.redisTemplate.opsForSet().isMember("user_praise_fid:"+userid, fid) ){
            //已经点赞过了
            jm.setCode(0);
            jm.setMsg("您已经点赞过了");
            // 点赞次数
            jm.setObj(this.redisTemplate.opsForValue().get("praise:" + fid));
            return jm;
        }
        //累计一个商品点赞次数
        long praise =  this.redisTemplate.opsForValue().increment("praise:" + fid);
        //记录某个用户曾经点赞过的商品的编号
        this.redisTemplate.opsForSet().add("user_praise_fid:"+userid, fid);
        //记录某个商品被哪些用户点赞过
        this.redisTemplate.opsForSet().add("fid_praise_user:"+fid, userid);

        jm.setObj(praise);    //点赞次数
        jm.setCode(1);
        jm.setMsg("点赞成功");
        return jm;
    }




}
