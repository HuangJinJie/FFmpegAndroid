package com.example.ffmpegandroidcompress;

/**
 * <pre>
 *     author : Administrator (Jacket)
 *     e-mail : 378315764@qq.com
 *     time   : 2018/04/02
 *     desc   :
 *     version: 3.2
 * </pre>
 */

public class FFMpegNativeBridge {
    static {
        System.loadLibrary("ffmpeg-lib");
    }
    /**
     * 设置是否处于调试状态
     * @param debug
     */
    public static native void setDebug(boolean debug);

    /**
     * 执行ffmpeg命令
     * @param cmd
     * @return
     */
    public static native int RunCommand(String[] cmd);
}
