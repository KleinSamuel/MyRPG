package com.kleinsamuel.game.startscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kleinsamuel.game.model.Assets;
import com.kleinsamuel.game.util.DebugMessageFactory;
import com.kleinsamuel.game.util.Utils;

/**
 * Created by sam on 12.06.17.
 */

public class RaceChooseScreen implements Screen {

    private StartScreen startScreen;

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Viewport menuPort;
    public Stage stage;
    private Skin skin;
    private Texture backGround;

    private float PADDING_TOP = 10.0f;
    private float PADDING_LEFT = 0.0f;

    private float HUMAN_X = 100.0f;
    private float HUMAN_Y = 380.0f;
    private float HUMAN_WIDTH = 300.0f;
    private float HUMAN_HEIGHT = (StartScreen.START_HEIGHT*0.6f)/7;

    private float HALFLING_X = HUMAN_X;
    private float HALFLING_Y = HUMAN_Y - (HUMAN_HEIGHT+PADDING_TOP)*1;

    private float DWARF_X = HUMAN_X;
    private float DWARF_Y = HUMAN_Y - (HUMAN_HEIGHT+PADDING_TOP)*2;

    private float ELB_X = HUMAN_X;
    private float ELB_Y = HUMAN_Y - (HUMAN_HEIGHT+PADDING_TOP)*3;

    private float ORC_X = HUMAN_X;
    private float ORC_Y = HUMAN_Y - (HUMAN_HEIGHT+PADDING_TOP)*4;

    private float WIEDERGAENGER_X = HUMAN_X;
    private float WIEDERGAENGER_Y = HUMAN_Y - (HUMAN_HEIGHT+PADDING_TOP)*5;

    private float MURONY_X = HUMAN_X;
    private float MURONY_Y = HUMAN_Y - (HUMAN_HEIGHT+PADDING_TOP)*6;

    private int clickIdentifier = 0;

    public ConfirmationDialog confirmationDialog;

    public RaceChooseScreen(StartScreen startScreen){
        this.startScreen = startScreen;
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        menuPort = new FitViewport(StartScreen.START_WIDTH, StartScreen.START_HEIGHT, camera);
        stage = new Stage(menuPort, batch);
        skin = startScreen.skin;
        backGround = new Texture(Gdx.files.internal("popup_background.png"));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new StartscreenInputProcessor(startScreen));
    }

    public void handleClick(float screenX, float screenY){
        if(clickOnHuman(screenX, screenY)){
            startScreen.game.button_click.play();
            if(clickIdentifier == 0){
                choose("HUMAN");
                return;
            }
            clickIdentifier = 0;
            return;
        }
        if(clickOnHalfling(screenX, screenY)){
            startScreen.game.button_click.play();
            if(clickIdentifier == 1){
                choose("HALFLING");
                return;
            }
            clickIdentifier = 1;
            return;
        }
        if(clickOnDwarf(screenX, screenY)){
            startScreen.game.button_click.play();
            if(clickIdentifier == 2){
                choose("DWARF");
                return;
            }
            clickIdentifier = 2;
            return;
        }
        if(clickOnElb(screenX, screenY)){
            startScreen.game.button_click.play();
            if(clickIdentifier == 3){
                choose("ELB");
                return;
            }
            clickIdentifier = 3;
            return;
        }
        if(clickOnOrc(screenX, screenY)){
            startScreen.game.button_click.play();
            if(clickIdentifier == 4){
                choose("ORC");
                return;
            }
            clickIdentifier = 4;
            return;
        }
        if(clickOnWiedergaenger(screenX, screenY)){
            startScreen.game.button_click.play();
            if(clickIdentifier == 5){
                choose("WIEDERGAENGER");
                return;
            }
            clickIdentifier = 5;
            return;
        }
        if(clickOnMurony(screenX, screenY)){
            startScreen.game.button_click.play();
            if(clickIdentifier == 6){
                choose("MURONY");
                return;
            }
            clickIdentifier = 6;
            return;
        }
    }

    private void choose(String name){
        confirmationDialog = new ConfirmationDialog(startScreen, 500, 180);
        confirmationDialog.setText("Are you sure you want to play as "+name+"?");
    }

    private boolean clickOnHuman(float screenX, float screenY){
        return (screenX >= HUMAN_X && screenX <= HUMAN_X+HUMAN_WIDTH && screenY >= HUMAN_Y && screenY <= HUMAN_Y+HUMAN_HEIGHT);
    }
    private boolean clickOnHalfling(float screenX, float screenY){
        return (screenX >= HALFLING_X && screenX <= HALFLING_X+HUMAN_WIDTH && screenY >= HALFLING_Y && screenY <= HALFLING_Y+HUMAN_HEIGHT);
    }
    private boolean clickOnDwarf(float screenX, float screenY){
        return (screenX >= DWARF_X && screenX <= DWARF_X+HUMAN_WIDTH && screenY >= DWARF_Y && screenY <= DWARF_Y+HUMAN_HEIGHT);
    }
    private boolean clickOnElb(float screenX, float screenY){
        return (screenX >= ELB_X && screenX <= ELB_X+HUMAN_WIDTH && screenY >= ELB_Y && screenY <= ELB_Y+HUMAN_HEIGHT);
    }
    private boolean clickOnOrc(float screenX, float screenY){
        return (screenX >= ORC_X && screenX <= ORC_X+HUMAN_WIDTH && screenY >= ORC_Y && screenY <= ORC_Y+HUMAN_HEIGHT);
    }
    private boolean clickOnWiedergaenger(float screenX, float screenY){
        return (screenX >= WIEDERGAENGER_X && screenX <= WIEDERGAENGER_X+HUMAN_WIDTH && screenY >= WIEDERGAENGER_Y && screenY <= WIEDERGAENGER_Y+HUMAN_HEIGHT);
    }
    private boolean clickOnMurony(float screenX, float screenY){
        return (screenX >= MURONY_X && screenX <= MURONY_X+HUMAN_WIDTH && screenY >= MURONY_Y && screenY <= MURONY_Y+HUMAN_HEIGHT);
    }

    private String getDescription(){
        switch (clickIdentifier){
            case 0:
                return "Do I really need to explain what a human is? The main advantage of the human is their adaptability; they can be almost any class and any specialization. They range from paragons of civilizations to brutes of tribal society.";
            case 1:
                return "Halflings are little, but with a big heart. Surprisingly, they are not timid creatures, but are actually fearless, and are not capable of panic or terror, although they have been known to shake in their boots when cornered by three ogres. They even more nimble than Elves, but are still yet weaker. ";
            case 2:
                return "Although Dwarves are extremely strong, they are not as nimble or dexterous, and therefore do not make many delicate creations like Elbs. Dwarves are very resistant to poison and disease. A Dwarf caught sick is a rare sight, and it is even rarer to see a Dwarf admit it.";
            case 3:
                return "These are nimble, wise, and beautiful beings. They appear to be Human, except for their small frame and pointed ears. Elbs live for hundreds of years, sometimes even a millennia. Extremely wise and intelligent due to their long life-span, and possess very refined senses. Their ability of sight makes them deadly archers.";
            case 4:
                return "Deadly creatures who perfer to attack in groups. Orcs are very strong, but stupid. They can see in the dark, but are dazzled by bright light. Orcs are extremely violent and the bane of all civilization.";
            case 5:
                return "The Wiedergaenger is the most remembered and basic undead creature. Usually a Wiedergaenger is an undead Human that feeds on the non-infected humans. They are not always undead - some forms of Wiedergaenger are merely Humans who have been infected with a virus that makes them aggressive and insane.";
            case 6:
                return "The \"Nachsteller\" out of Grimm's and the original romanian tales. Muronys are undead creatures which drain the vitality out of living beings without the need to be in physical contact. Some may still lie in their graves, some wander around terrifying the living.";
        }
        return "";
    }

    private Texture getPortrait(){
        switch (clickIdentifier){
            case 0:
                return Assets.manager.get(Assets.portrait_human, Texture.class);
            case 1:
                return Assets.manager.get(Assets.portrait_halfling, Texture.class);
            case 2:
                return Assets.manager.get(Assets.portrait_dwarf, Texture.class);
            case 3:
                return Assets.manager.get(Assets.portrait_elb, Texture.class);
            case 4:
                return Assets.manager.get(Assets.portrait_orc, Texture.class);
            case 5:
                return Assets.manager.get(Assets.portrait_wiedergaenger, Texture.class);
            case 6:
                return Assets.manager.get(Assets.portrait_murony, Texture.class);
        }
        return null;
    }

    private void setColor(int identifier){
        if(identifier == clickIdentifier){
            Utils.testFont.setColor(Color.valueOf("aaaaaaff"));
            PADDING_LEFT = 10.0f;
        }else{
            Utils.testFont.setColor(Color.valueOf("000000ff"));
            PADDING_LEFT = 0.0f;
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(backGround, 0, 0, StartScreen.START_WIDTH, StartScreen.START_HEIGHT);

        /* draw headline */
        Utils.testFont.getData().setScale(2.7f, 2.7f);
        Utils.testFont.setColor(Color.valueOf("000000ff"));
        String headline = "Choose your race";
        Vector3 dims = Utils.getWidthAndHeightOfString(Utils.testFont, headline);
        Utils.testFont.draw(batch, headline, (StartScreen.START_WIDTH/2)-(dims.x/2), StartScreen.START_HEIGHT*0.9f);

        Utils.testFont.getData().setScale(1.0f, 1.0f);
        String text = "Your choice DOES affect the stats of your character!";
        Vector3 dimsText = Utils.getWidthAndHeightOfString(Utils.testFont, text);
        Utils.testFont.draw(batch, text, (StartScreen.START_WIDTH/2)-(dimsText.x/2), StartScreen.START_HEIGHT*0.9f-40);

        Utils.testFont.getData().setScale(2.7f, 2.7f);

        /* draw divider */
        batch.draw(Assets.manager.get(Assets.rectangle_black, Texture.class), StartScreen.START_WIDTH/2-1, StartScreen.START_HEIGHT*0.08f, 3, StartScreen.START_HEIGHT*0.7f);

        /* draw race names */
        setColor(0);
        Utils.testFont.draw(batch, "HUMAN", HUMAN_X+PADDING_LEFT, HUMAN_Y+HUMAN_HEIGHT-PADDING_TOP);
        setColor(1);
        Utils.testFont.draw(batch, "HALFLING", HALFLING_X+PADDING_LEFT, HALFLING_Y+HUMAN_HEIGHT-PADDING_TOP);
        setColor(2);
        Utils.testFont.draw(batch, "DWARF", DWARF_X+PADDING_LEFT, DWARF_Y+HUMAN_HEIGHT-PADDING_TOP);
        setColor(3);
        Utils.testFont.draw(batch, "ELB", ELB_X+PADDING_LEFT, ELB_Y+HUMAN_HEIGHT-PADDING_TOP);
        setColor(4);
        Utils.testFont.draw(batch, "ORC", ORC_X+PADDING_LEFT, ORC_Y+HUMAN_HEIGHT-PADDING_TOP);
        setColor(5);
        Utils.testFont.draw(batch, "WIEDERGAENGER", WIEDERGAENGER_X+PADDING_LEFT, WIEDERGAENGER_Y+HUMAN_HEIGHT-PADDING_TOP);
        setColor(6);
        Utils.testFont.draw(batch, "MURONY", MURONY_X+PADDING_LEFT, MURONY_Y+HUMAN_HEIGHT-PADDING_TOP);

        /* draw information */
        setColor(-1);
        Utils.testFont.getData().setScale(1.3f, 1.3f);
        Utils.testFont.draw(batch, getDescription(), StartScreen.START_WIDTH/2+10, 300.0f, 420.0f, Align.left, true);

        Texture portrait = getPortrait();
        if(portrait != null){
            batch.draw(portrait, 630.0f, 320.0f, 100.0f, 100.0f);
        }

        if(confirmationDialog != null){
            Color colorBefore = batch.getColor();
            batch.setColor(new Color(colorBefore.r, colorBefore.g, colorBefore.b, 0.7f));
            batch.draw(Assets.manager.get(Assets.rectangle_black, Texture.class), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.setColor(new Color(colorBefore.r, colorBefore.g, colorBefore.b, 1.0f));
            confirmationDialog.render(batch);
        }

        batch.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
}
