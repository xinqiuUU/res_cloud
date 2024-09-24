openFeign API 准备步骤:
先开发要暴露的API的对应的服务 
一般使用步骤:
  1.创建api服务. 在此服务中加入要公开  API接口
               <dependency>
                  <groupId>org.springframework.cloud</groupId>
                  <artifactId>spring-cloud-starter-openfeign</artifactId>
              </dependency>
  2. 在这个api服务中开发  接口: 
       @FeignClient("resfood")
       public interface ResfoodApi {
           @RequestMapping( value="resfood/detailCountAdd" , method=RequestMethod.GET)
           public Map<String, Object> detailCountAdd(Integer fid);
           
        }
  3. 调用端的开发: 
              <dependency>
                  <artifactId>res-api</artifactId>
                  <groupId>org.example</groupId>
                  <version>1.0-SNAPSHOT</version>
              </dependency>
      
              <dependency>
                  <groupId>org.springframework.cloud</groupId>
                  <artifactId>spring-cloud-starter-openfeign</artifactId>
              </dependency>
         
       b)开启openfeign的客户端     
            @EnableFeignClients(basePackages= {"com.yc.api"})
       c)注入api接口到controller或业务层. 
              @Autowired
              private ResfoodApi resfoodApi;
              
///////////////////////////////底层: spring cloud openfeign 底层是feign框架. ///////////
feign中有哪些组件?
1. feign.Contract
2. feign.Client
3. 支持负载均衡与熔断。负载均衡 Ribbon 是对 feign.Client 进行包装
4. feign.codec.Decoder 和 feign.codec.Encoder