package com.seek.cocos2ddemo;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCJumpBy;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;

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
        jumpBy(ccSprite);

    }

    private void jumpBy(CCSprite ccSprite) {
        CCJumpBy ccJumpBy = CCJumpBy.action(2, ccp(100, 100), 100, 2);
        CCJumpBy reverse = ccJumpBy.reverse();
        CCSequence ccSequence = CCSequence.actions(ccJumpBy, reverse);
        CCRepeatForever forever = CCRepeatForever.action(ccSequence);
        ccSprite.
        runAction(forever);
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
