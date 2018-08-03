package com.edu.common;

import org.apache.commons.lang3.StringUtils;

public abstract class Assert {

    public static void isEmpty(String str,String msg){
        if(StringUtils.isEmpty(str)){
            throw new IllegalArgumentException(msg);
        }
    }

}
