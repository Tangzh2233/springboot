package com.edu.controller;

import com.edu.dao.domain.Dlog;
import com.edu.service.ILoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Random;
import java.util.UUID;

/**
 * @author Tangzhihao
 * @date 2018/3/13
 */
@Controller
@RequestMapping("/mytest")
public class InsertController {

    @Resource
    private ILoginService loginService;


    @RequestMapping("/addDlog.html")
    public String showTest(){
        return "test";
    }

    @RequestMapping("/addDlog.do")
    public String addDlog(Dlog dlog){
        int i = new Random().nextInt(10);
        dlog.setId(String.valueOf(i));
        System.out.println(dlog.toString());
        loginService.addDlog(dlog);
        return "main";
    }
}
