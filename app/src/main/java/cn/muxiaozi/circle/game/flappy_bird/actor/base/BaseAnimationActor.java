package cn.muxiaozi.circle.game.flappy_bird.actor.base;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import cn.muxiaozi.circle.game.flappy_bird.MainGame;
import cn.muxiaozi.circle.game.framwork.AnimationActor;


/**
 * 动画演员基类
 *
 * @xietansheng
 */
public abstract class BaseAnimationActor extends AnimationActor {

    private MainGame mainGame;

    public BaseAnimationActor(MainGame mainGame) {
        this.mainGame = mainGame;
    }

    public BaseAnimationActor(MainGame mainGame, Animation animation) {
        super(animation);
        this.mainGame = mainGame;
    }

    public BaseAnimationActor(MainGame mainGame, float frameDuration, Array<? extends TextureRegion> keyFrames) {
        super(frameDuration, keyFrames);
        this.mainGame = mainGame;
    }

    public BaseAnimationActor(MainGame mainGame, float frameDuration, TextureRegion... keyFrames) {
        super(frameDuration, keyFrames);
        this.mainGame = mainGame;
    }

    public MainGame getMainGame() {
        return mainGame;
    }

    public void setMainGame(MainGame mainGame) {
        this.mainGame = mainGame;
    }

}















