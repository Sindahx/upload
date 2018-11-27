package com.chrl.bs.sfs.util;

/**
 * Created by cat(chrl) on 2015/6/3.
 */
public class Debug {
    private static final boolean isDebug = true;
    public static void out(String s){
        if (isDebug){
            System.out.println(s);
        }
    }
}
