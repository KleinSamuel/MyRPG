package com.kleinsamuel.game.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kleinsamuel.game.model.Assets;
import com.kleinsamuel.game.screens.PlayScreen;
import com.kleinsamuel.game.util.Utils;

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

    public HUD(){

        batch = new SpriteBatch();

        viewport = new FitViewport(PlayScreen.V_WIDTH, PlayScreen.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        healthbar = new Healthbar();
        manabar = new Manabar();
        experiencebar = new Experiencebar();

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        testLabel = new Label("HEIL HITLER DU NUTTE!", new Label.LabelStyle(Utils.basicFont, Color.FIREBRICK));

        table.add(testLabel).expandX().padTop(10);

        stage.addActor(table);
    }

    private final int button_width = 42;
    private final int button_height = 42;
    private final int button_padding_top = 10;
    private final int button_padding_right = 10;

    private final int settings_button_x = PlayScreen.V_WIDTH-1*button_width-1*button_padding_right;
    private final int settings_button_y = PlayScreen.V_HEIGHT-button_height-button_padding_top;

    private final int social_button_x = PlayScreen.V_WIDTH-2*button_width-2*button_padding_right;
    private final int social_button_y = PlayScreen.V_HEIGHT-button_height-button_padding_top;

    private final int news_button_x = PlayScreen.V_WIDTH-3*button_width-3*button_padding_right;
    private final int news_button_y = PlayScreen.V_HEIGHT-button_height-button_padding_top;

    private final int chat_button_x = PlayScreen.V_WIDTH-button_width-button_padding_right;
    private final int chat_button_y = PlayScreen.V_HEIGHT-2*button_height-2*button_padding_top;

    private final int bag_button_x = PlayScreen.V_WIDTH-button_width-button_padding_right;
    private final int bag_button_y = PlayScreen.V_HEIGHT-3*button_height-3*button_padding_top;

    public boolean clickOnSettings(int screenX, int screenY){
        return (screenX >= settings_button_x && screenX <= settings_button_x+button_width && screenY >= settings_button_y && screenY <= settings_button_y+button_height);
    }
    public boolean clickOnSocial(int screenX, int screenY){
        return (screenX >= social_button_x && screenX <= social_button_x+button_width && screenY >= social_button_y && screenY <= social_button_y+button_height);
    }
    public boolean clickOnNews(int screenX, int screenY){
        return (screenX >= news_button_x && screenX <= news_button_x+button_width && screenY >= news_button_y && screenY <= news_button_y+button_height);
    }
    public boolean clickOnChat(int screenX, int screenY){
        return (screenX >= chat_button_x && screenX <= chat_button_x+button_width && screenY >= chat_button_y && screenY <= chat_button_y+button_height);
    }
    public boolean clickOnBag(int screenX, int screenY){
        return (screenX >= bag_button_x && screenX <= bag_button_x+button_width && screenY >= bag_button_y && screenY <= bag_button_y+button_height);
    }

    public void render() {
        healthbar.render(batch);
        manabar.render(batch);
        //experiencebar.render(batch);

        batch.draw(Assets.manager.get(Assets.settings_button, Texture.class), settings_button_x, settings_button_y, button_width, button_height);
        batch.draw(Assets.manager.get(Assets.social_button, Texture.class), social_button_x, social_button_y, button_width, button_height);
        batch.draw(Assets.manager.get(Assets.news_button, Texture.class), news_button_x, news_button_y, button_width, button_height);

        batch.draw(Assets.manager.get(Assets.chat_button, Texture.class), chat_button_x, chat_button_y, button_width, button_height);
        batch.draw(Assets.manager.get(Assets.bag_button, Texture.class), bag_button_x, bag_button_y, button_width, button_height);
    }
}
