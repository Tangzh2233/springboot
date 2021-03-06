package com.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Tangzhihao
 * @date 2017/10/11
 */

/*@Controller*/
public class ErrorController implements org.springframework.boot.autoconfigure.web.ErrorController{

    private static final String ERROR_PATH = "/error";

    @RequestMapping(value=ERROR_PATH)
    public String handleError(){
        return "404";
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
