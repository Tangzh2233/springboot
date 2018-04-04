package com.edu.controller.filter;

import com.edu.dao.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tangzhihao
 * @date 2017/10/11
 */

public class LoginIntercepotr implements HandlerInterceptor {


    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    public final static String LOGIN_JSP = "/myspringboot/login.html";
    public final static String LOGIN_DO = "/myspringboot/login.do";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        User user = (User) request.getSession().getAttribute("user");
        String url = request.getRequestURI();
        System.out.println(url);
        if(user!=null || LOGIN_JSP.equals(url) || LOGIN_DO.equals(url)){
            return true;
        }else{
            response.sendRedirect("login.html");
            return false;
        }
    }

}
