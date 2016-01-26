package com.strv.codemanager.utility;

import com.strv.codemanager.CodeManagerConfig;


public class Log {
    public static void d(String msg) {
        if (CodeManagerConfig.LOGS) System.out.println(msg);
    }


    public static void e(String msg) {
        if (CodeManagerConfig.LOGS) System.err.println(msg);
    }
}
