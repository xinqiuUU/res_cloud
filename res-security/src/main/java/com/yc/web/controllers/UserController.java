package com.yc.web.controllers;

import com.yc.bean.Resuser;
import com.yc.service.ResuserBiz;
import com.yc.utils.JwtTokenUtil;
import com.yc.web.model.ResponseResult;
import com.yc.web.model.ResuserVO;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/ressecurity")
public class UserController {

    @Autowired
    private ResuserBiz resuserBiz;

    //注册
    @PostMapping("/register")
    public ResponseResult register(@RequestBody @Valid ResuserVO resuser){
        try{
            int userid = this.resuserBiz.regUser( resuser );
            resuser.setUserid( userid ); // 注册成功后，将用户id设置到resuser对象中
            resuser.setPassword( "" ); // 注册成功后，将密码设置为""
            return ResponseResult.ok( "注册成功" ).setData( resuser );
        }catch (Exception e){
            log.error( "注册失败", e );
            return ResponseResult.error( "注册失败" );
        }
    }

    // 注入 认证管理器
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtUtil;

    //登录
    @PostMapping("/login")
    public ResponseResult login(@RequestBody ResuserVO resuserVO , HttpSession session){
        //获取验证码
        String captcha = (String) session.getAttribute( "captcha" );
        // 验证码校验  验证码  忽略大小写
//        if(!captcha.equals( resuserVO.getCaptcha() ) ){
//            return ResponseResult.error( "验证码错误" );
//        }
        //用户认证
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken( resuserVO.getUsername() ,
                        resuserVO.getPassword() ) );
        //将认证信息设置到 SecurityContext 中，表示用户已通过认证。
        SecurityContextHolder.getContext().setAuthentication( authentication );

        //认证成功后，获取当前认证的用户信息。
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        //生成jwt负载 JSON Web Token
        Map<String,String> payload = new HashMap<>();
        payload.put( "username", userDetails.getUsername() );
        payload.put("userid", String.valueOf(  String.valueOf( ((Resuser)userDetails).getUserid())));
        payload.put( "role", "user" );
        payload.put( "email", ((Resuser)userDetails).getEmail() );
        //生成token
        String jwtToken = jwtUtil.encodeJWT( payload );

//        this.resuserBiz.( resuserVO.getUsername() );

        return ResponseResult.ok( "登录成功" ).setData( jwtToken );
    }

    //退出
    @PostMapping("/logout")
    public ResponseResult logout(@RequestHeader("Authorization") String token){
        //这里可以实现JWT 黑名单机制  或者让客户端删除存储的JWT
        //例如 将token 添加到redis黑名单中
        return ResponseResult.ok( "退出成功" );
    }

    //权限认证  只有登录成功后才能访问该接口
    @PostMapping("/check")
    public ResponseResult check(){
        log.info( "权限认证成功" );
        // 从 SecurityContext 中获取当前认证的用户信息。
        Resuser resuser = (Resuser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        resuser.setPassword( "" );
        return ResponseResult.ok( "成功" ).setData( resuser );
    }


}
