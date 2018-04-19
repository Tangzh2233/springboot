package com.edu.something.JDK;

import com.edu.something.JDK.ENUM.EChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tangzhihao
 * @date 2018/1/4
 */

public class LogTest {
    private static  final Logger logger = LoggerFactory.getLogger(LogTest.class);
    public static void main(String[] args) {
        logger.info("--------------info");
        logger.debug("-------------debug");
        logger.warn("-------------warn");
        logger.error("--------------error");
        for (int i=1;i<6;i++){
            EChannel eChannel = EChannel.EChannel_Map.get(i);
            System.out.println(eChannel.getDesc());
        }
        try {
            Class<?> aClass = Class.forName("com.edu.something.JDK.LogTest");
            Class<?>[] interfaces = aClass.getInterfaces();
            for (Class<?> a:interfaces) {
                System.out.println(a.getSimpleName()+"22");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
