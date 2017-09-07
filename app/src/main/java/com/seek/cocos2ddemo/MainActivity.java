package com.seek.cocos2ddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

public class MainActivity extends AppCompatActivity {

    private CCDirector director;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //隐去状态栏部分(电池等图标和一些修饰部分)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);

        CCGLSurfaceView  surfaceView = new CCGLSurfaceView(this);
        setContentView(surfaceView);

        director = CCDirector.sharedDirector();//单利模式
        director.attachInView(surfaceView);//开启线程

        director.setDeviceOrientation(CCDirector.kCCDeviceOrientationLandscapeLeft);//水平
        director.setDisplayFPS(true);//显示帧率
        director.setScreenSize(480, 320);

        CCScene ccScene = CCScene.node();
        ccScene.addChild(new ActionLayer());
        director.runWithScene(ccScene);

    }

    @Override
    protected void onResume() {
        super.onResume();
        director.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        director.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        director.end();
    }
}
