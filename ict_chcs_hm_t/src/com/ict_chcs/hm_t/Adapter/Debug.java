package com.ict_chcs.hm_t.Adapter;

import android.util.Log;

public class Debug {

    private static boolean DEBUG_FLAG = true;
    private static boolean ERROR_FLAG = true;
    private static boolean INFO_FLAG = false;
    private static boolean WARNING_FLAG = false;
    private static boolean VERBOS_FLAG = false;
    
    public Debug()  {

    }

    public static void d(String tag, String message) {

        if (DEBUG_FLAG && tag.equalsIgnoreCase("") == false)   {
        	String log = buildLogMsg(message); 
			Log.d(tag, log);
        }
    }   

    public static void e(String tag, String message) {
        if (ERROR_FLAG && tag.equalsIgnoreCase("") == false)   {
            String log = buildLogMsg(message); 
            Log.e(tag, log);
        }
    }

    public static void i(String tag, String message) {
        if (INFO_FLAG && tag.equalsIgnoreCase("") == false)   {
            String log = buildLogMsg(message); 
            Log.i(tag, log);
        }
    }

    public static void w(String tag, String message) {
        if (WARNING_FLAG && tag.equalsIgnoreCase("") == false)    {
            String log = buildLogMsg(message); 
            Log.w(tag, log);
        }
    }

    public static void v(String tag, String message) {
        if (VERBOS_FLAG && tag.equalsIgnoreCase("") == false)   {
            String log = buildLogMsg(message); 
            Log.v(tag, log);
        }
    }

    private static String buildLogMsg(String message)   {
        StackTraceElement ste = Thread.currentThread().getStackTrace()[4];         
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(ste.getFileName());
        sb.append(" > ");
        sb.append(ste.getMethodName());
        sb.append(" > #");
        sb.append(ste.getLineNumber());
        sb.append("] ");
        sb.append(message);      
        return sb.toString();
    }
}