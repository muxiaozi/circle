package cn.muxiaozi.circle.libgdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;

import cn.muxiaozi.circle.core.IConfig;
import cn.muxiaozi.circle.core.SystemPacket;
import cn.muxiaozi.circle.net.IReceiver;
import cn.muxiaozi.circle.net.ISender;

/**
 * Created by 慕宵子 on 2016/7/31 0031.
 */
public abstract class BaseGame extends Game implements IReceiver {
    private ISender mSender;
    private String[] mPlayers;

    private boolean isShowFPS;
    private boolean hasMusic;
    private boolean hasSounds;

    public BaseGame(String[] players) {
        this.mPlayers = players;
    }

    @Override
    public void create() {
        Preferences preferences = Gdx.app.getPreferences(IConfig.CIRCLE_CONFIG);
        isShowFPS = preferences.getBoolean(IConfig.Option.FPS, false);
        hasMusic = preferences.getBoolean(IConfig.Option.MUSIC, true);
        hasSounds = preferences.getBoolean(IConfig.Option.SOUNDS, true);
    }

    public String[] getPlayers() {
        return mPlayers;
    }

    /**
     * 设置消息发送者，使用之来发送游戏数据
     *
     * @param sender 消息发送者
     */
    public void setSender(ISender sender) {
        this.mSender = sender;
    }

    /**
     * 是否显示FPS
     *
     * @return 默认不显示
     */
    public boolean isShowFPS() {
        return isShowFPS;
    }

    /**
     * 是否有背景音乐
     *
     * @return 默认有
     */
    public boolean hasMusic() {
        return hasMusic;
    }

    /**
     * 是否有音效
     *
     * @return 默认有
     */
    public boolean hasSounds() {
        return hasSounds;
    }

    /**
     * 接收数据
     *
     * @param data 数据
     */
    @Override
    public void receive(byte[] data) {
        final Screen screen = getScreen();
        if (screen != null) {
            ((BaseScreen) screen).receive(data);
        }

        //如果收到与服务器断开连接的消息，退出游戏
        if (data[0] == SystemPacket.TYPE_DISCONNECT_SERVER) {
            Gdx.app.exit();
        }
    }

    /**
     * 发送数据
     *
     * @param data 数据
     */
    public void send(byte[] data) {
        if (mSender != null) {
            mSender.send(data);
        }
    }
}
