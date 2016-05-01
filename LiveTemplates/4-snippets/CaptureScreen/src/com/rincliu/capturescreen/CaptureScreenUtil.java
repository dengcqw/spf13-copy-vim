package com.rincliu.capturescreen;

public class CaptureScreenUtil
{
    static {
        System.loadLibrary("cptrscrn");
    }

    /**
     * 
     * @param fileName
     */
    public static native void captureScreenToFile(String fileName);
}
