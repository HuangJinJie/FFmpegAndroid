package com.example.ffmpegandroidcompress;

import android.nfc.Tag;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private String mFileBeforeName = "2018-04-02_1522640848468_camera.mp4";
    private String mFileAfterName = "my_girl.mp4";
    private String mark = "ic_launcher.png";
    ///sdcard/ych/videotemp/2018-04-02_1522640848468_camera.mp4

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FFMpegNativeBridge.setDebug(true);//开启日志系统

        Button bt = (Button) findViewById(R.id.bt_text);
        final ProgressBar pb = (ProgressBar) findViewById(R.id.pb);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String basePath = Environment.getExternalStorageDirectory().getPath()+"/ych/videotemp";
                        if (new File(basePath + "/" + mFileBeforeName).exists() && !new File(basePath + "/" + mFileAfterName).exists()) {
                            String cmd_transcoding = String.format(
                                    "ffmpeg -i %s -c:v libx264 %s  -c:a libfdk_aac %s",
                                    basePath + "/" + mFileBeforeName,
                                    "-crf 40",
                                    basePath + "/" + mFileAfterName);
//                            String cmd_transcoding = String.format(
//                                    "ffmpeg -i %s -vf movie=%s[watermark];[in][watermark]overlay=10:10[out] %s",
//                                    basePath + "/" + mFileBeforeName,
//                                    basePath + "/" +mark,
//                                    basePath + "/" + mFileAfterName);
                            Log.e("命令", cmd_transcoding);
                            int i = FFmpegCMDRun(cmd_transcoding);
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    pb.setVisibility(View.GONE);
                                    Toast.makeText(MainActivity.this, "压缩成功了", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else if (new File(basePath + "/" + mFileBeforeName).exists() && new File(basePath + "/" + mFileAfterName).exists()) {
                            pb.post(new Runnable() {
                                @Override
                                public void run() {
                                    pb.setVisibility(View.GONE);
                                    Toast.makeText(MainActivity.this, "已经压缩成功了", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pb.setVisibility(View.GONE);
                                    Toast.makeText(MainActivity.this, "没找过该视频文件", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        });

    }

    /**
     * 以空格分割字符串
     */
    public int FFmpegCMDRun(String cmd) {
        String regulation = "[ \\t]+";
        String[] split = cmd.split(regulation);
        //执行命令
        return FFMpegNativeBridge.RunCommand(split);
    }
}
