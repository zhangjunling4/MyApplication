package com.seek.cocos2ddemo;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCJumpBy;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCRotateBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.actions.interval.CCTintBy;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.ccColor3B;

/**
 * Created by admin on 2017/9/7.
 */

public class ActionLayer extends CCLayer {

    public ActionLayer() {
        init();
    }

    private void init() {
        CCSprite ccSprite = getSprite();

//        moveTo(ccSprite);
//        moveBy(ccSprite);
        jumpBy2(ccSprite);

        tint();

    }

    /**
     * 颜色渐变的效果
     * CCLabel 专门用于显示文字的精灵
     */
    private void tint() {
        CCLabel label = CCLabel.makeLabel("那些年，我们一起玩坏的僵尸...", "hkbd.ttf", 24);
        label.setColor(ccc3(50,0,255));
        label.setAnchorPoint(0, 0);
        label.setPosition(100, 50);
        this.addChild(label);

        ccColor3B ccColor3B = ccc3(100, 255, -100);
        CCTintBy by = CCTintBy.action(1, ccColor3B);
        CCTintBy reverse = by.reverse();
        CCSequence sequence = CCSequence.actions(by, reverse);
        CCRepeatForever forever = CCRepeatForever.action(sequence);

        label.runAction(forever);
    }

    private void jumpBy(CCSprite ccSprite) {
        CCJumpBy ccJumpBy = CCJumpBy.action(2, ccp(100, 100), 100, 2);
        CCJumpBy reverse = ccJumpBy.reverse();
        CCSequence ccSequence = CCSequence.actions(ccJumpBy, reverse);
        CCRepeatForever forever = CCRepeatForever.action(ccSequence);
        ccSprite.
        runAction(forever);
    }

    private void jumpBy2(CCSprite ccSprite) {
        CCJumpBy ccJumpBy = CCJumpBy.action(4, ccp(100, 100), 100, 2);
        CCRotateBy ccRotateBy = CCRotateBy.action(2, 360);

        CCSpawn ccSpawn = CCSpawn.actions(ccJumpBy, ccRotateBy);
//        CCJumpBy reverse = ccJumpBy.reverse();
        CCSequence ccSequence = CCSequence.actions(ccSpawn, ccSpawn.reverse());
        CCRepeatForever forever = CCRepeatForever.action(ccSequence);
        ccSprite.setAnchorPoint(0.5f, 0.5f);
        ccSprite.setPosition(50, 50);
        ccSprite.runAction(forever);
    }

    private void moveBy(CCSprite ccSprite) {
        CCMoveBy ccMoveTo = CCMoveBy.action(2, CCNode.ccp(200, 0));
        ccSprite.runAction(ccMoveTo);
    }

    private void moveTo(CCSprite ccSprite) {
        CCMoveTo ccMoveTo = CCMoveTo.action(2, CCNode.ccp(200, 0));
        ccSprite.runAction(ccMoveTo);
    }

    private CCSprite getSprite() {
        CCSprite ccSprite = CCSprite.sprite("z_1_attack_01.png");
        ccSprite.setAnchorPoint(0, 0);
        this.addChild(ccSprite);
        return ccSprite;
    }
}
