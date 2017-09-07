package com.seek.cocos2ddemo;

import android.view.MotionEvent;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

/**
 * Created by admin on 2017/9/7.
 */

public class FirstLayer extends CCLayer {

    private CCSprite ccSprite;

    public FirstLayer() {
        setIsTouchEnabled(true); //打开触摸事件开关
        init();
    }

    private void init() {
        CCSprite bg = CCSprite.sprite("bbg_arena.jpg");
        bg.setAnchorPoint(0,0);
        this.addChild(bg);

        ccSprite = CCSprite.sprite("z_1_attack_01.png");
        ccSprite.setAnchorPoint(0,0);
        ccSprite.setPosition(100, 100);

        this.addChild(ccSprite);
    }

    @Override
    public boolean ccTouchesBegan(MotionEvent event) {
        CGPoint convertTouchToNodeSpace = this.convertTouchToNodeSpace(event);
        CGRect boundingBox = ccSprite.getBoundingBox();
        boolean containsPoint = CGRect.containsPoint(boundingBox, convertTouchToNodeSpace);
        if (containsPoint){
            ccSprite.setScale(ccSprite.getScale() + 0.2);
        }else{
            ccSprite.setScale(ccSprite.getScale() - 0.2);
        }

        return super.ccTouchesBegan(event);
    }
}
