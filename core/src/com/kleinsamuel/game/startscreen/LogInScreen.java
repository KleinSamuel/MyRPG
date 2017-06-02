package com.kleinsamuel.game.startscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kleinsamuel.game.screens.PlayScreen;

/**
 * Created by sam on 01.06.17.
 */

public class LogInScreen implements Screen {

    private StartScreen startScreen;

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Viewport menuPort;
    private Stage stage;
    private Skin skin;
    private Texture backGround;

    private Vector3 camPosition;
    private boolean moveCam = true;

    public LogInScreen(StartScreen startScreen) {
        this.startScreen = startScreen;

        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        menuPort = new FitViewport(StartScreen.START_WIDTH, StartScreen.START_HEIGHT, camera);
        stage = new Stage(menuPort, batch);
        skin = new Skin(Gdx.files.internal("skins/pixthulhu-ui.json"));
        backGround = new Texture(Gdx.files.internal("bg_menu_1.png"));

        camPosition = new Vector3(camera.position.x, camera.position.y, camera.position.z);
    }

    @Override
    public void show() {

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.top();

        Label testLabel = new Label("LOG IN", skin);
        testLabel.setColor(Color.BLACK);

        testLabel.setFontScale(2f, 3f);

        Label usernameLabel = new Label("USERNAME:", skin);
        usernameLabel.setColor(Color.WHITE);
        usernameLabel.setFontScale(1.5f, 1.5f);

        final TextField usernameField = new TextField("", skin);
        usernameField.setMessageText("username");

        Label passwordLabel = new Label("PASSWORD:", skin);
        passwordLabel.setColor(Color.WHITE);
        passwordLabel.setFontScale(1.5f, 1.5f);

        final TextField passwordField = new TextField("", skin);
        passwordField.setMessageText("password");
        passwordField.setPosition(500, 30);
        passwordField.setPasswordCharacter('*');
        passwordField.setPasswordMode(true);

        TextButton backButton = new TextButton("BACK", skin);
        TextButton sendButton = new TextButton("SEND", skin);

        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                startScreen.game.setScreen(startScreen);
            }
        });

        sendButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                startScreen.game.connect();
                startScreen.game.setScreen(new PlayScreen(startScreen.game));
            }
        });

        mainTable.add(testLabel).colspan(2).padTop(20);
        mainTable.row();
        mainTable.add(usernameLabel).expandX().padTop(30).right();
        mainTable.add(usernameField).expandX().padTop(30).padLeft(10).width(300);
        mainTable.row();
        mainTable.add(passwordLabel).expandX().padTop(20).right();
        mainTable.add(passwordField).expandX().padTop(20).padLeft(10).width(300);
        mainTable.row();
        mainTable.add(backButton).expandX().padTop(20);
        mainTable.add(sendButton).expandX().padTop(20);

        stage.addActor(mainTable);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(backGround, 0, 0, StartScreen.START_WIDTH, StartScreen.START_HEIGHT);

        //drawStatus(batch);
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
