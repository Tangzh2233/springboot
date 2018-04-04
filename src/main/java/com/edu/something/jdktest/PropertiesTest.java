package com.edu.something.jdktest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Tangzhihao
 * @date 2017/12/15
 */
public class PropertiesTest {

    private static Properties pop;

    /*public void init(){
        pop = new Properties();
        InputStream in = Object.class.getResourceAsStream("/feemapper.properties");

        try {
            pop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/

    static {
        pop = new Properties();
        InputStream in = Object.class.getResourceAsStream("/feemapper.properties");
        try {
            pop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        //System.out.println(pop.get("JBS_0801_GRJY"));
        String s = pop.getProperty("JBS_0801_GRJY");
        String s1 = pop.getProperty("JBS_0802_PT");
        System.out.println(s);
        System.out.println(s1);

    }
}
