package com.yds.multimodellive.media;

import android.Manifest;
import android.app.Activity;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.yds.multimodellive.util.FileUtil;
import com.yds.multimodellive.util.PermissionUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VideoDataExecutor {
    private CameraManager cameraManager;

    private Activity activity;
    private SurfaceView surfaceView;


    private HandlerThread mHandlerThread;
    private Handler childHandler;

    private CaptureRequest.Builder requestBuilder;

    public VideoDataExecutor(Activity activity, SurfaceView surfaceView) {
        this.activity = activity;
        this.surfaceView = surfaceView;
        onCreate();
    }


    private void onCreate() {
        cameraManager = (CameraManager) activity.getSystemService(activity.CAMERA_SERVICE);
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                openCamera();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }

    private MediaRecorder mMediaRecorder;

//    private void setUpMediaRecorder() throws IOException {
//
//        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
//        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
//        mNextVideoAbsolutePath = FileUtil.createVideoDiskFile(appContext, FileUtil.createVideoFileName()).getAbsolutePath();
//        mMediaRecorder.setOutputFile(mNextVideoAbsolutePath);
//        mMediaRecorder.setVideoEncodingBitRate(10000000);
//        //每秒30帧
//        mMediaRecorder.setVideoFrameRate(30);
//        mMediaRecorder.setVideoSize(mVideoSize.getWidth(), mVideoSize.getHeight());
//        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
//        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
//        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
//        switch (mSensorOrientation) {
//            case SENSOR_ORIENTATION_DEFAULT_DEGREES:
//                mMediaRecorder.setOrientationHint(DEFAULT_ORIENTATIONS.get(rotation));
//                break;
//            case SENSOR_ORIENTATION_INVERSE_DEGREES:
//                mMediaRecorder.setOrientationHint(ORIENTATIONS.get(rotation));
//                break;
//            default:
//                break;
//        }
//        mMediaRecorder.prepare();
//    }

    /**
     * 打开摄像头
     */
    private void openCamera() {
        if (mHandlerThread == null) {
            mHandlerThread = new HandlerThread("Camera2");
            mHandlerThread.start();
            childHandler = new Handler(mHandlerThread.getLooper());

        }


        List<String> list = new ArrayList<>();
        list.add(Manifest.permission.CAMERA);
        list.add(Manifest.permission.RECORD_AUDIO);
        try {
            PermissionUtil.checkPermission(activity,list);
            cameraManager.openCamera("0", cameraCallBack, childHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建摄像头打开回调并获取摄像头对象
     */
    private CameraDevice cameraDevice;
    private CameraDevice.StateCallback cameraCallBack = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            //获取摄像头对象
            cameraDevice = camera;
            preview(camera);
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            cameraDevice.close();
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            cameraDevice.close();
        }
    };

    private void preview(CameraDevice camera){
        try{
            // 创建预览需要的CaptureRequest.Builder
            requestBuilder  = camera.createCaptureRequest(CameraDevice.TEMPLATE_RECORD);
            // 将SurfaceView的surface作为CaptureRequest.Builder的目标
            requestBuilder.addTarget(surfaceView.getHolder().getSurface());
            requestBuilder.set(CaptureRequest.CONTROL_AF_MODE,CaptureRequest.CONTROL_AF_MODE_OFF);
            requestBuilder.set(CaptureRequest.CONTROL_AE_MODE,CaptureRequest.CONTROL_AF_MODE_OFF);
            //设置拍摄图像时相机设备是否使用光学防抖（OIS）。
            requestBuilder.set(CaptureRequest.LENS_OPTICAL_STABILIZATION_MODE,CaptureRequest.LENS_OPTICAL_STABILIZATION_MODE_ON);
            //感光灵敏度

            camera.createCaptureSession(Arrays.asList(surfaceView.getHolder().getSurface()),captureSessionCallback,childHandler);
        }catch (CameraAccessException e){
            e.printStackTrace();
        }
    }

    /**
     * 创建摄像头访问回调并获取访问会话对象
     */
    private CameraCaptureSession captureSession;
    private CameraCaptureSession.StateCallback captureSessionCallback = new CameraCaptureSession.StateCallback() {
        @Override
        public void onConfigured(@NonNull CameraCaptureSession session) {
            captureSession = session;

//            if(cameraDevice!=null&&requestBuilder==null){
//                try {
//                    requestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
//                    // 将imageReader的surface作为CaptureRequest.Builder的目标
//                    requestBuilder.addTarget(imageReader.getSurface());
//
//                    //关闭自动对焦
//                    requestBuilder.set(CaptureRequest.CONTROL_AF_MODE,CaptureRequest.CONTROL_AF_MODE_OFF);
//                    requestBuilder.set(CaptureRequest.CONTROL_AE_MODE,CaptureRequest.CONTROL_AE_MODE_OFF);
//                    //设置拍摄图像时相机设备是否使用光学防抖（OIS）。
//                    requestBuilder.set(CaptureRequest.LENS_OPTICAL_STABILIZATION_MODE,CaptureRequest.LENS_OPTICAL_STABILIZATION_MODE_ON);
//                    requestBuilder.set(CaptureRequest.SENSOR_SENSITIVITY,CaptureRequest.STATISTICS_OIS_DATA_MODE_ON);
//                    //曝光补偿
//                    requestBuilder.set(CaptureRequest.CONTROL_AE_EXPOSURE_COMPENSATION,0);
//
//                } catch (CameraAccessException e) {
//                    e.printStackTrace();
//                }
//            }
            updatePreview(session);
        }

        @Override
        public void onConfigureFailed(@NonNull CameraCaptureSession session) {

        }
    };

    private CameraCaptureSession.CaptureCallback captureCallback = new CameraCaptureSession.CaptureCallback() {
        @Override
        public void onCaptureCompleted(@NonNull CameraCaptureSession session,  @NonNull CaptureRequest request,@NonNull TotalCaptureResult result) {
            super.onCaptureCompleted(session, request, result);
        }
    };

    private void updatePreview(CameraCaptureSession session){
        try {
            session.setRepeatingRequest(requestBuilder.build(),captureCallback,childHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }


}
