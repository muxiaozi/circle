package cn.muxiaozi.circle.libgdx.flappy_bird.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

import cn.muxiaozi.circle.libgdx.flappy_bird.MainGame;
import cn.muxiaozi.circle.libgdx.flappy_bird.actor.ResultGroup;
import cn.muxiaozi.circle.libgdx.flappy_bird.DataFactory;
import cn.muxiaozi.circle.libgdx.flappy_bird.Res;
import cn.muxiaozi.circle.libgdx.BaseActor;
import cn.muxiaozi.circle.libgdx.BaseStage;
import cn.muxiaozi.circle.core.CoreService;

/**
 * 游戏结束时显示的舞台
 *
 * @xietansheng
 */
public class GameOverStage extends BaseStage<MainGame> {

    /**
     * 游戏结束提示
     */
    private BaseActor gameOverActor;

    /**
     * 分数结果显示
     */
    private ResultGroup resultGroup;

    /**
     * 重新开始按钮
     */
    private ImageButton restartButton;

    /**
     * 点击按钮恢复到初始状态时播放的音效
     */
    private Sound restartSound;


    public GameOverStage(MainGame mainGame, Viewport viewport) {
        super(mainGame, viewport);
        init();
    }

    private void init() {
        //音效初始化
        restartSound = getGame().getAssetManager().get(Res.Audios.AUDIO_RESTART, Sound.class);

        // 创建游戏结束提示
        gameOverActor = new BaseActor(getGame().getAtlas().findRegion(Res.Atlas.IMAGE_GAME_OVER));
        gameOverActor.setCenterX(getWidth() / 2);
        gameOverActor.setTopY(getHeight() - 150);
        addActor(gameOverActor);

        // 创建游戏结果显示组合
        resultGroup = new ResultGroup(getGame());
        resultGroup.setCenterX(getWidth() / 2);
        resultGroup.setTopY(gameOverActor.getY() - 30);
        addActor(resultGroup);

        // 创建重新开始按钮
        restartButton = new ImageButton(
                new TextureRegionDrawable(getGame().getAtlas().findRegion(Res.Atlas.IMAGE_GAME_START_01_TO_02, 1)),
                new TextureRegionDrawable(getGame().getAtlas().findRegion(Res.Atlas.IMAGE_GAME_START_01_TO_02, 2))
        );
        restartButton.setX(getWidth() / 2 - restartButton.getWidth() / 2);
        restartButton.setY(resultGroup.getY() - 30 - restartButton.getHeight());
        addActor(restartButton);

        // 按钮添加事件监听器
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (CoreService.isServer()) {
                    getGame().send(DataFactory.packRequestPrepare());
                    if (getGame().hasSounds()) {
                        restartSound.play();
                    }
                    getGame().getGameScreen().restartReadyGame();
                }
            }
        });
    }

    /**
     * 设置当前分数
     */
    public void setCurrentScore(int currScore) {
        resultGroup.updateCurrScore(currScore);
    }

    @Override
    public void receive(byte[] data) {
        switch (data[0]) {
            case DataFactory.TYPE_REQUEST_PREPARE:
            case DataFactory.TYPE_READY:
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        // 重新开始游戏
                        getGame().getGameScreen().restartReadyGame();
                    }
                });
                break;
            default:
                break;
        }
    }
}
















