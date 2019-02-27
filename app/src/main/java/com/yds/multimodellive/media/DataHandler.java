package com.yds.multimodellive.media;

import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.util.Log;

import java.nio.ByteBuffer;

public class DataHandler {
    public static void codeCYuv(byte[] data){
        if(data==null){
            return;
        }

    }

    public static void test(){
        MediaCodecList list = new MediaCodecList(1);
        MediaCodecInfo[] mediaCodecInfos = list.getCodecInfos();
        Log.e("TAG", "test: "+mediaCodecInfos);
    }
}
