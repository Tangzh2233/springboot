package com.edu.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.edu.common.httpresp.HttpResponse;
import com.edu.dao.domain.User;

import com.edu.service.ILoginService;
import com.edu.service.quartz.DailyJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

import javax.servlet.http.HttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author: Tangzhihao
 * @date: 2017/11/17 14:30
 * @description: 登录逻辑处理类
 */
@Controller
@RequestMapping("/myspringboot")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private ILoginService loginService;
    @Resource
    private DailyJob dailyJob;
    public String userId;

    @RequestMapping("/login.html")
    public String gologin(){
        return "login";
    }

    @RequestMapping("/main.html")
    public String gomain(){
        return "main";
    }


    /**
     * @author: Tangzhihao
     * @date: 2017/11/17 14:30
     * @params: HttpServerletRequest,User
     * @return: String
     * @description:
     */
    @ResponseBody
    @RequestMapping(value = "/login.do",method = RequestMethod.POST)
    public String login(User usr,HttpServletRequest request) throws Exception,JedisConnectionException {
        logger.info("请求参数: "+usr.toString());
        Jedis jedis = null;
        HttpSession session;
        try {
            jedis = new JedisPool("127.0.0.1", 6379).getResource();
        }catch (JedisConnectionException e){
            logger.error("redis connect exception", StringUtils.toString(e));
        }
        try{
            session = request.getSession();
            if(jedis!=null){
                logger.info("redis connect success",jedis);
                String pwd = jedis.get(usr.getUsername());
                logger.info("select redis result "+usr.getUsername()+"--------"+pwd);
                if(pwd!=null && usr.getPassword().equals(pwd)){
                    session.setAttribute("user",usr);
                    return "main";
                }else{
                    Boolean aBoolean = loginSorF(usr,session);
                    if (aBoolean){
                        //添加Redis缓存
                        jedis.set(usr.getUsername(),usr.getPassword());
                        return "main";
                    }else{
                        return "login";
                    }
                }
            }else{
                logger.info("redis connect fail");
                if(loginSorF(usr,session)){
                    return "main";
                }else{
                    return "login";
                }
            }
        }catch (Exception e){
            return "login";
        }finally {
            if(jedis!=null) {
                jedis.close();
            }
        }
    }

    @RequestMapping("/exit.do")
    public String exit(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return "redirect:login.html";
    }

    @ResponseBody
    @RequestMapping("/httpPost.do")
    public HttpResponse httpTest(User user){
        HttpResponse resp = new HttpResponse();
        HttpResponse.HttpRespData data = new HttpResponse.HttpRespData();
        HttpResponse.HttpRespError error = new HttpResponse.HttpRespError();
        if("tang".equals(user.getUsername())&&"123".equals(user.getPassword())){
            error.setReturnCode("00");
            error.setReturnMessage("SUCCESS");
        }else {
            error.setReturnCode("11");
            error.setReturnMessage("FAIL");
        }
        resp.setData(data);
        resp.setError(error);
        return resp;
    }

    private Boolean loginSorF(User usr,HttpSession session){
        User checkuser = loginService.login(usr.getUsername());
        if(checkuser!=null){
            if(usr.getPassword().equals(checkuser.getPassword())){
                userId = checkuser.getId().toString();
                session.setAttribute("user",checkuser);
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    @RequestMapping("/sendEmail.do")
    private String sendEmail(){
        dailyJob.execute();
        return "main";
    }

}
