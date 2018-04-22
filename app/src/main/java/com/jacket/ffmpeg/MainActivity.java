package com.jacket.ffmpeg;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    // Used to load the 'native-lib' library on application startup.
    static
    {
        System.loadLibrary("native-lib");
//        System.loadLibrary("avcodec-57");
//        System.loadLibrary("avfilter-6");
//        System.loadLibrary("avformat-57");
//        System.loadLibrary("avutil-55");
//        System.loadLibrary("swresample-2");
//        System.loadLibrary("swscale-4");
    }

    private Button protocol,format,codec,filter;
    private TextView tv_info;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//                    ///sdcard/ych/videotemp/2018-03-23_1521792056455_camera.mp4
//            {
//                File inputFile = new File(Environment.getExternalStorageDirectory()+"/ych/videotemp", "2018-03-23_1521792056455_camera.mp4");
//                if (!inputFile.exists())
//                {
//                    Snackbar.make(view, "文件不存在", Snackbar.LENGTH_LONG).setAction("Action", null)
//                            .show();
//                    return;
//                }
//                File outputFile = new File(Environment.getExternalStorageDirectory(),
//                        "output_mv.yuv");
//                decode(inputFile.getAbsolutePath(), outputFile.getAbsolutePath());
//            }
//        });
//
//        // Example of a call to a native method
//        TextView tv = (TextView) findViewById(R.id.sample_text);
//        tv.setText(urlprotocolinfo());
        init();
    }


    private void init() {
        protocol = (Button) findViewById(R.id.btn_protocol);
        format = (Button) findViewById(R.id.btn_format);
        codec = (Button) findViewById(R.id.btn_codec);
        filter = (Button) findViewById(R.id.btn_filter);
        tv_info = (TextView) findViewById(R.id.tv_info);

        protocol.setOnClickListener(this);
        format.setOnClickListener(this);
        codec.setOnClickListener(this);
        filter.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_protocol:
                tv_info.setText(urlprotocolinfo());
                break;
            case R.id.btn_format:
                tv_info.setText(avformatinfo());
                break;
            case R.id.btn_codec:
                tv_info.setText(avcodecinfo());
                break;
            case R.id.btn_filter:
                tv_info.setText(avfilterinfo());
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    public native void decode(String input, String output);

    public native String avfilterinfo();

    public native String avcodecinfo();

    public native String avformatinfo();

    public native String urlprotocolinfo();
}
