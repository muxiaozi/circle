package cn.muxiaozi.circle.libgdx.flappy_bird.actor;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.List;

import cn.muxiaozi.circle.libgdx.flappy_bird.MainGame;
import cn.muxiaozi.circle.libgdx.BaseActor;
import cn.muxiaozi.circle.libgdx.BaseGroup;

/**
 * 数字组显示, 将一个整数的每一位分别用一个演员来表示, 然后水平排列成一组达到显示整数的效果
 *
 * @xietansheng
 */
public class NumGroup extends BaseGroup<MainGame> {

    private TextureRegion[] digitRegions;

    private final List<BaseActor> digitActorList = new ArrayList<>();

    private int num;

    public NumGroup(MainGame mainGame) {
        super(mainGame);
    }

    public NumGroup(MainGame mainGame, String resName) {
        super(mainGame);
        Array<TextureAtlas.AtlasRegion> regions = mainGame.getAtlas().findRegions(resName);
        TextureRegion[] digitRegions = new TextureRegion[regions.size];
        for (int i = 0; i < regions.size; i++) {
            digitRegions[i] = regions.get(i);
        }
        setDigitRegions(digitRegions);
    }

    public NumGroup(MainGame mainGame, TextureRegion[] digitRegions) {
        super(mainGame);
        setDigitRegions(digitRegions);
    }

    public TextureRegion[] getDigitRegions() {
        return digitRegions;
    }

    public void setDigitRegions(TextureRegion[] digitRegions) {
        this.digitRegions = digitRegions;
        justNumDigit(num);
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        if (this.num != num && num >= 0) {
            this.num = num;
            justNumDigit(this.num);
        }
    }

    public void addNum(int numStep) {
        setNum(num + numStep);
    }

    /**
     * 校准数字位数
     *
     * @param num
     */
    private void justNumDigit(int num) {
        if (digitRegions == null) {
            return;
        }

        char[] numChars = ("" + num).toCharArray();

        if (digitActorList.size() > numChars.length) {
            int removeCount = digitActorList.size() - numChars.length;
            for (int i = 0; i < removeCount; i++) {
                removeActor(digitActorList.remove(0));
            }
        } else if (digitActorList.size() < numChars.length) {
            int addCount = numChars.length - digitActorList.size();
            for (int i = 0; i < addCount; i++) {
                BaseActor digitActor = new BaseActor();
                digitActorList.add(digitActor);
                addActor(digitActor);
            }
        }

        for (int i = 0; i < digitActorList.size(); i++) {
            TextureRegion region = digitRegions[Integer.parseInt("" + numChars[i])];
            digitActorList.get(i).setRegion(region);
        }

        justDigitLayout();
    }

    /**
     * 校准布局
     */
    private void justDigitLayout() {
        float digitWidth = digitActorList.get(0).getWidth();
        float digitHeight = digitActorList.get(0).getHeight();
        for (int i = 0; i < digitActorList.size(); i++) {
            digitActorList.get(i).setX(digitWidth * i);
        }
        setSize(digitWidth * digitActorList.size(), digitHeight);
    }


}















