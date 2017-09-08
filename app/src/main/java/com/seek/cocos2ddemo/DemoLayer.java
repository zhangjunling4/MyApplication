package com.seek.cocos2ddemo;

import android.view.MotionEvent;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCJumpBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCRotateBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCTMXObjectGroup;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.particlesystem.CCParticleSnow;
import org.cocos2d.particlesystem.CCParticleSystem;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.util.CGPointUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2017/9/8.
 */

public class DemoLayer extends CCLayer {

    private CCTMXTiledMap map;
    private List<CGPoint> roadPoints;

    private CCSprite sprite;

    private int position = 0;
    private CCParticleSystem system;

    public DemoLayer() {
        this.setIsTouchEnabled(true);
        init();
    }

    private void init() {
        loadMap();
        parserMap();

        loadParticle();
        loadZombies();
    }

    /**
     * 粒子系统的展示
     */
    private void loadParticle() {
        system = CCParticleSnow.node();
        system.setTexture(CCTextureCache.sharedTextureCache().addImage("snow.png"));
        this.addChild(system, 1);
    }

    /**
     * 展示僵尸
     */
    private void loadZombies() {
        sprite = CCSprite.sprite("z_1_01.png");
        sprite.setAnchorPoint(0.5f, 0);
        sprite.setPosition(roadPoints.get(0));
        sprite.setScale(0.65f);
        sprite.setFlipX(true);//水平翻转

        map.addChild(sprite);  //通过地图添加僵尸
        //地图随着手指移动，僵尸也会随之手指移动

        //序列帧的播放
        ArrayList<CCSpriteFrame> frames = new ArrayList<>();
        String format = "z_1_%02d.png";
        for (int i=1; i<=7; i++){
            CCSpriteFrame displayedFrame = CCSprite.sprite(String.format(format, i)).displayedFrame();
            frames.add(displayedFrame);
        }
        CCAnimation ccAnimation = CCAnimation.animation("walk", 0.2f, frames);
        CCAnimate animate = CCAnimate.action(ccAnimation);
        //序列帧动作默认是永不停止的循环
        CCRepeatForever forever = CCRepeatForever.action(animate);
        sprite.runAction(forever);

        moveToNext();
    }

    /**
     * 前行
     */
    private int speed = 20;
    public void moveToNext() {
        position ++;
        if (position < roadPoints.size()){
            CGPoint cgPopint = roadPoints.get(position);
            float time = CGPointUtil.distance(roadPoints.get(position - 1), cgPopint) / speed;

            CCMoveTo ccMoveTo = CCMoveTo.action(time, cgPopint);
            ;//调用一个对象的某一个方法
            CCSequence ccSequence = CCSequence.actions(ccMoveTo,
                    CCCallFunc.action(this, "moveToNext"));

            sprite.runAction(ccSequence);
        }else{
            //移动完成
            system.stopSystem();//停止粒子系统;
            sprite.stopAllActions();//停止所有动作

            dance();
            SoundEngine engine = SoundEngine.sharedEngine();
            //参数1  上下文  参数2 音乐资源的ID， 参数3  是否循环播放
            engine.playSound(CCDirector.theApp, R.raw.psy, true);
        }
    }

    private void dance() {
        sprite.setAnchorPoint(0.5f, 0.5f);
        CCJumpBy ccJumpBy = CCJumpBy.action(2, ccp(-20, 10), 10, 2);
        CCRotateBy by = CCRotateBy.action(1, 360);
        CCSpawn ccSpawn = CCSpawn.actions(ccJumpBy, by);
        CCSequence ccSequence = CCSequence.actions(ccSpawn, ccSpawn.reverse());
        CCRepeatForever forever = CCRepeatForever.action(ccSequence);

        sprite.runAction(forever);

    }

    @Override
    public boolean ccTouchesMoved(MotionEvent event) {
        //该方法生效必须保证地图的锚点在中间位置。
        map.touchMove(event, map);//地图随着手指移动而移动
        return super.ccTouchesMoved(event);
    }

    /**
     * 地图加载
     */
    private void loadMap() {
        map = CCTMXTiledMap.tiledMap("map_hm14.tmx");
        map.setAnchorPoint(0.5f, 0.5f);
        //因为修改了锚点，所以坐标也是需要修改的
        map.setPosition(map.getContentSize().width / 2, map.getContentSize().height / 2);
        this.addChild(map);
    }

    /**
     * 地图解析
     */
    private void parserMap() {
        roadPoints = new ArrayList<>();

        CCTMXObjectGroup objectGroupNamed = map.objectGroupNamed("road");
        ArrayList<HashMap<String, String>> objects = objectGroupNamed.objects;
        for (HashMap<String, String> hashMap : objects){
            int x = Integer.parseInt(hashMap.get("x"));
            int y = Integer.parseInt(hashMap.get("y"));
            CGPoint cgPoint = ccp(x, y);
            roadPoints.add(cgPoint);
        }

    }

    @Override
    public boolean ccTouchesBegan(MotionEvent event) {
        this.onExit();
        //场景添加新的图层
        this.getParent().addChild(new PauseLayer());

        return super.ccTouchesBegan(event);
    }

    class PauseLayer extends CCLayer{
        private CCSprite heart;
        public PauseLayer() {
            this.setIsTouchEnabled(true);//打开触摸事件的开关
            heart = CCSprite.sprite("start.png");
            CGSize winSize = CCDirector.sharedDirector().getWinSize();
            heart.setPosition(winSize.getWidth() / 2, winSize.getHeight() / 2);
            this.addChild(heart);
        }

        @Override
        public boolean ccTouchesBegan(MotionEvent event) {
            CGRect boundingBox = heart.getBoundingBox();
            CGPoint convertTouch = this.convertTouchToNodeSpace(event);
            if (CGRect.containsPoint(boundingBox, convertTouch)){
                this.removeSelf();
                DemoLayer.this.onEnter();
            }
            return super.ccTouchesBegan(event);
        }
    }
}
