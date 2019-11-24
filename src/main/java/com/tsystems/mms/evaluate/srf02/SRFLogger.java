package com.tsystems.mms.evaluate.srf02;

public class SRFLogger {
    public static int INFO_MSG = 1;
    public static int ERROR_MSG = 2;
    public static int SUCCESS = 3;

    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RESET = "\u001B[0m";

    public static void showMessage(int msgType, String msg) {
        String prefix = ANSI_BLUE;

        if (msgType == INFO_MSG) prefix = ANSI_BLUE;
        else if (msgType == ERROR_MSG) prefix = ANSI_RED;
        else if (msgType == SUCCESS) prefix = ANSI_GREEN;

        System.out.println(prefix + msg + ANSI_RESET);
    }
}
