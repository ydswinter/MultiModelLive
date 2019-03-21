package com.yds.multimodellive;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class LiveActivity extends AppCompatActivity {
    private static final String TAG = "LiveActivity";
    private int backPressedCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
    }

    @Override
    public void onBackPressed() {
        Log.e(TAG, "onBackPressed: 再按一次退出");
        if(backPressedCount%2==0){
            Toast.makeText(LiveActivity.this,"再按一次退出",Toast.LENGTH_SHORT).show();
            backPressedCount+=1;
            taskHanlder.sendEmptyMessageDelayed( 0x1002,2000);
        }else{
            finish();
        }

//        super.onBackPressed();
    }

    private Handler taskHanlder = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x1002:
                    backPressedCount = 0;
                    break;
            }
            super.handleMessage(msg);
        }
    };
}
