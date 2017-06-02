package com.kleinsamuel.game.startscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kleinsamuel.game.GameClass;
import com.kleinsamuel.game.screens.PlayScreen;
import com.kleinsamuel.game.util.DebugMessageFactory;

/**
 * Created by sam on 31.05.17.
 */

public class StartScreen implements Screen{

    public GameClass game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Viewport menuPort;
    private Stage stage;
    private Skin skin;

    private Texture backGround;
    private StartScreen startScreen;
    public SignUpScreen signUpScreen;
    public LogInScreen logInScreen;

    private boolean connecting = false;
    private Label onlineStatusLabel;
    TextButton logInButton;
    TextButton signUpButton;

    private boolean FIRST_STARTUP = true;

    public static final int START_WIDTH = 1920/2;
    public static final int START_HEIGHT = 1080/2;

    public StartScreen(GameClass game){
        this.game = game;

        startScreen = this;

        camera = new OrthographicCamera();
        batch = new SpriteBatch();

        menuPort = new FitViewport(StartScreen.START_WIDTH, StartScreen.START_HEIGHT, camera);
        stage = new Stage(menuPort, batch);

        skin = new Skin(Gdx.files.internal("skins/pixthulhu-ui.json"));

        backGround = new Texture(Gdx.files.internal("bg_menu_1.png"));

        logInScreen = new LogInScreen(this);
        signUpScreen = new SignUpScreen(this);

        game.connect();
    }

    @Override
    public void show() {

        DebugMessageFactory.printInfoMessage("SHOW THIS SHIT!");

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.top();

        logInButton = new TextButton("LOG IN", skin);
        signUpButton = new TextButton("SIGN UP", skin);

        //Add listeners to buttons
        logInButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                game.setScreen(new PlayScreen(game));
                /*if(game.CONNECTED) {
                    onlineStatusLabel.clear();
                    game.setScreen(logInScreen);
                }*/
            }
        });

        signUpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                DebugMessageFactory.printInfoMessage("CLICKED: "+game.CONNECTED);
                if(game.CONNECTED) {
                    onlineStatusLabel.clear();
                    game.setScreen(signUpScreen);
                }
            }
        });

        Label testLabel = new Label("Clash Of RPG Brawl Online", skin);
        testLabel.setColor(Color.ROYAL);
        testLabel.setFontScale(StartScreen.START_WIDTH * 0.002f, StartScreen.START_WIDTH * 0.002f);

        if(FIRST_STARTUP) {
            onlineStatusLabel = new Label("ONLINE STATUS: OFFLINE", skin);
            onlineStatusLabel.setColor(Color.RED);
            onlineStatusLabel.setFontScale(StartScreen.START_WIDTH * 0.0015f, StartScreen.START_WIDTH * 0.0015f);
            FIRST_STARTUP = false;
        }

        Image img = new Image(new Texture(Gdx.files.internal("ui/refresh_button.png")));

        img.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!game.CONNECTED) {
                    game.connect();
                }
            }
        });

        //Add buttons to table
        mainTable.add(testLabel).padTop(StartScreen.START_HEIGHT * 0.05f);
        mainTable.row();
        mainTable.add(logInButton).padTop(StartScreen.START_HEIGHT * 0.1f);
        mainTable.row();
        mainTable.add(signUpButton).padTop(StartScreen.START_HEIGHT * 0.01f);
        mainTable.row();
        mainTable.add(onlineStatusLabel).padTop(StartScreen.START_HEIGHT * 0.1f);
        mainTable.add(img).padTop(StartScreen.START_HEIGHT * 0.1f).left().width(50).height(50);

        //Add table to stage
        stage.addActor(mainTable);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {

        if(!game.CONNECTED){
            onlineStatusLabel.clear();
            onlineStatusLabel.setText("ONLINE STATUS: OFFLINE");
            onlineStatusLabel.setColor(Color.RED);

        }else{
            onlineStatusLabel.clear();
            onlineStatusLabel.setText("ONLINE STATUS: ONLINE");
            onlineStatusLabel.setColor(Color.GREEN);
        }

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(backGround, 0, 0, StartScreen.START_WIDTH, StartScreen.START_HEIGHT);
        batch.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
