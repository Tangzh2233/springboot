package com.edu.something.JDK.ENUM;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tangzhihao
 * @date 2018/3/1
 */

public class EnumTest {
    private static List<InterfaceA> EInterfaceImpl = new ArrayList<>();

    public static void main(String[] args) {
        EInterfaceImpl.add(EClassImpl.MemberA);
        EInterfaceImpl.add(EClassImpl.MemberB);
        EInterfaceImpl.get(0).execute();
    }
}
