package com.edu.pojo;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * Created by Administrator on 2017/8/4.
 */
@Data
public class Student {
    private String name;
    private Integer age;
    private Date birthday;

    /*@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }*/
}
