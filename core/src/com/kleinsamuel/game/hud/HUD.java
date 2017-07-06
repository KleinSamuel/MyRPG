package com.kleinsamuel.game.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kleinsamuel.game.hud.bars.Experiencebar;
import com.kleinsamuel.game.hud.bars.Healthbar;
import com.kleinsamuel.game.hud.bars.Manabar;
import com.kleinsamuel.game.model.Assets;
import com.kleinsamuel.game.model.miscellaneous.chat.ChatWindowBig;
import com.kleinsamuel.game.model.miscellaneous.chat.ChatWindowSmall;
import com.kleinsamuel.game.screens.PlayScreen;
import com.kleinsamuel.game.util.Utils;

import static com.kleinsamuel.game.util.Utils.FACTOR;

/**
 * Created by sam on 30.05.17.
 */

public class HUD {

    public Stage stage;
    private Viewport  viewport;
    public SpriteBatch batch;

    Label testLabel;

    public Healthbar healthbar;
    public Manabar manabar;
    public Experiencebar experiencebar;
    public ChatWindowSmall chatWindowSmall;

    private PlayScreen playScreen;

    public HUD(PlayScreen playScreen){

        batch = new SpriteBatch();
        this.playScreen = playScreen;

        viewport = new FitViewport(PlayScreen.V_WIDTH, PlayScreen.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        healthbar = new Healthbar();
        manabar = new Manabar();
        experiencebar = new com.kleinsamuel.game.hud.bars.Experiencebar();
        chatWindowSmall = new ChatWindowSmall(playScreen);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        //testLabel = new Label("HELLO WORLD!", new Label.LabelStyle(Utils.basicFont, Color.FIREBRICK));
        //table.add(testLabel).expandX().padTop(10);

        stage.addActor(table);
    }

    private final float MONEY_SKULL_BAR_X = 332.0f * FACTOR;
    private final float MONEY_SKULL_BAR_Y = 484.0f * FACTOR;
    private final float MONEY_SKULL_BAR_WIDTH = 363.0f * FACTOR;
    private final float MONEY_SKULL_BAR_HEIGHT = 41.0f * FACTOR;

    private final int button_width = 42;
    private final int button_height = 42;
    private final int button_padding_top = 10;
    private final int button_padding_right = 10;

    private final int settings_button_x = PlayScreen.V_WIDTH-1*button_width-1*button_padding_right;
    private final int settings_button_y = PlayScreen.V_HEIGHT-button_height-button_padding_top;

    private final int shop_button_x = PlayScreen.V_WIDTH-2*button_width-2*button_padding_right;
    private final int shop_button_y = PlayScreen.V_HEIGHT-button_height-button_padding_top;

    private final int news_button_x = PlayScreen.V_WIDTH-3*button_width-3*button_padding_right;
    private final int news_button_y = PlayScreen.V_HEIGHT-button_height-button_padding_top;

    private final int stat_button_x = PlayScreen.V_WIDTH-button_width-button_padding_right;
    private final int stat_button_y = PlayScreen.V_HEIGHT-2*button_height-2*button_padding_top;

    private final int bag_button_x = PlayScreen.V_WIDTH-button_width-button_padding_right;
    private final int bag_button_y = PlayScreen.V_HEIGHT-3*button_height-3*button_padding_top;

    private final int social_button_x = PlayScreen.V_WIDTH-button_width-button_padding_right;
    private final int social_button_y = PlayScreen.V_HEIGHT-4*button_height-4*button_padding_top;

    private final float notification_bullet_width = 28 * Utils.FACTOR;
    private final float notification_bullet_height = 28 * Utils.FACTOR;

    private final int lexicon_button_x = PlayScreen.V_WIDTH-button_width-button_padding_right;
    private final int lexicon_button_y = PlayScreen.V_HEIGHT-5*button_height-5*button_padding_top;

    private final float potion_red_button_x = 10;
    private final float potion_red_button_y = PlayScreen.V_HEIGHT*0.4f+10+button_height;

    private final float potion_blue_button_x = 10;
    private final float potion_blue_button_y = PlayScreen.V_HEIGHT*0.4f;

    public boolean clickOnSettings(int screenX, int screenY){
        return (screenX >= settings_button_x && screenX <= settings_button_x+button_width && screenY >= settings_button_y && screenY <= settings_button_y+button_height);
    }
    public boolean clickOnSocial(int screenX, int screenY){
        return (screenX >= social_button_x && screenX <= social_button_x+button_width && screenY >= social_button_y && screenY <= social_button_y+button_height);
    }
    public boolean clickOnNews(int screenX, int screenY){
        return (screenX >= news_button_x && screenX <= news_button_x+button_width && screenY >= news_button_y && screenY <= news_button_y+button_height);
    }
    public boolean clickOnStat(int screenX, int screenY){
        return (screenX >= stat_button_x && screenX <= stat_button_x+button_width && screenY >= stat_button_y && screenY <= stat_button_y+button_height);
    }
    public boolean clickOnLexicon(int screenX, int screenY){
        return (screenX >= lexicon_button_x && screenX <= lexicon_button_x+button_width && screenY >= lexicon_button_y && screenY <= lexicon_button_y+button_height);
    }
    public boolean clickOnShop(int screenX, int screenY){
        return (screenX >= shop_button_x && screenX <= shop_button_x+button_width && screenY >= shop_button_y && screenY <= shop_button_y+button_height);
    }
    public boolean clickOnBag(int screenX, int screenY){
        return (screenX >= bag_button_x && screenX <= bag_button_x+button_width && screenY >= bag_button_y && screenY <= bag_button_y+button_height);
    }

    public boolean clickOnPotionRed(float screenX, float screenY){
        return (screenX >= potion_red_button_x && screenX <= potion_red_button_x+button_width && screenY >= potion_red_button_y && screenY <= potion_red_button_y+button_height);
    }
    public boolean clickOnPotionBlue(float screenX, float screenY){
        return (screenX >= potion_blue_button_x && screenX <= potion_blue_button_x+button_width && screenY >= potion_blue_button_y && screenY <= potion_blue_button_y+button_height);
    }

    public void drawMoneyString(SpriteBatch batch){

        Utils.font10.getData().setScale(1.2f, 1.4f);
        Utils.font10.setColor(new Color(255.0f, 255.0f, 0.0f, 10.0f));
        String s = ""+playScreen.player.content.MONEY;
        //Vector3 dims = Utils.getWidthAndHeightOfString(Utils.basicFont, s);
        Utils.font10.draw(batch, s, MONEY_SKULL_BAR_X+27, MONEY_SKULL_BAR_Y+20);

        Utils.font10.getData().setScale(1.2f, 1.4f);
        Utils.font10.setColor(new Color(255.0f, 255.0f, 0.0f, 10.0f));
        String skulls = ""+playScreen.player.content.SKULLS;
        //Vector3 dimsSkull = Utils.getWidthAndHeightOfString(Utils.font10, skulls);
        Utils.font10.draw(batch, skulls, MONEY_SKULL_BAR_X+150, MONEY_SKULL_BAR_Y+20);

    }

    public void render() {
        healthbar.render(batch);
        manabar.render(batch);
        experiencebar.render(batch);

        batch.draw(Assets.manager.get(Assets.money_skull_bar, Texture.class), MONEY_SKULL_BAR_X, MONEY_SKULL_BAR_Y, MONEY_SKULL_BAR_WIDTH, MONEY_SKULL_BAR_HEIGHT);

        drawMoneyString(batch);

        batch.draw(Assets.manager.get(Assets.settings_button, Texture.class), settings_button_x, settings_button_y, button_width, button_height);

        batch.draw(Assets.manager.get(Assets.social_button, Texture.class), social_button_x, social_button_y, button_width, button_height);
        if(playScreen.chatFactory.UNREAD_MESSAGES){
            batch.draw(Assets.manager.get(Assets.notification_bullet, Texture.class), social_button_x+button_width-(notification_bullet_width*0.7f), social_button_y+button_height-(notification_bullet_height*0.7f), notification_bullet_width, notification_bullet_height);
        }

        batch.draw(Assets.manager.get(Assets.news_button, Texture.class), news_button_x, news_button_y, button_width, button_height);
        batch.draw(Assets.manager.get(Assets.shop_button, Texture.class), shop_button_x, shop_button_y, button_width, button_height);
        batch.draw(Assets.manager.get(Assets.bag_button, Texture.class), bag_button_x, bag_button_y, button_width, button_height);
        batch.draw(Assets.manager.get(Assets.lexicon_button, Texture.class), lexicon_button_x, lexicon_button_y, button_width, button_height);
        batch.draw(Assets.manager.get(Assets.stats_button, Texture.class), stat_button_x, stat_button_y, button_width, button_height);

        batch.draw(Assets.manager.get(Assets.potion_red_button, Texture.class), potion_red_button_x, potion_red_button_y, button_width, button_height);
        batch.draw(Assets.manager.get(Assets.potion_blue_button, Texture.class), potion_blue_button_x, potion_blue_button_y, button_width, button_height);

        if(chatWindowSmall != null && chatWindowSmall.SHOW_CHAT_SMALL){
            chatWindowSmall.render(batch);
        }

    }
}
