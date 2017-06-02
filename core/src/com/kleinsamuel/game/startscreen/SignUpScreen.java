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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kleinsamuel.game.util.DebugMessageFactory;

/**
 * Created by sam on 01.06.17.
 */

public class SignUpScreen implements Screen {

    private StartScreen startScreen;

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Viewport menuPort;
    private Stage stage;
    private Skin skin;
    private Texture backGround;

    private MessageDialog dialog;

    private boolean sendButtonPressed = false;
    public boolean changeStatusText = false;
    private long signUpDelay = 1000;
    private long currentStamp;

    public SignUpScreen(StartScreen startScreen){
        this.startScreen = startScreen;

        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        menuPort = new FitViewport(StartScreen.START_WIDTH, StartScreen.START_HEIGHT, camera);
        stage = new Stage(menuPort, batch);
        skin = new Skin(Gdx.files.internal("skins/pixthulhu-ui.json"));
        backGround = new Texture(Gdx.files.internal("bg_menu_1.png"));
    }

    @Override
    public void show() {

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.top();

        Label testLabel = new Label("SIGN UP", skin);
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

        Label password2Label = new Label("AGAIN:", skin);
        password2Label.setColor(Color.WHITE);
        password2Label.setFontScale(1.5f, 1.5f);

        final TextField password2Field = new TextField("", skin);
        password2Field.setMessageText("password");
        password2Field.setPosition(500, 30);
        password2Field.setPasswordCharacter('*');
        password2Field.setPasswordMode(true);

        TextButton backButton = new TextButton("BACK", skin);
        TextButton sendButton = new TextButton("SEND", skin);

        dialog = new MessageDialog("SIGN UP STATUS:", skin);
        dialog.setSize(StartScreen.START_WIDTH*0.7f, StartScreen.START_HEIGHT*0.6f);
        dialog.hide();

        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                startScreen.game.setScreen(startScreen);
            }
        });

        sendButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                sendButtonPressed = true;

                dialog = new MessageDialog("SIGN UP STATUS:", skin);
                dialog.setSize(StartScreen.START_WIDTH*0.7f, StartScreen.START_HEIGHT*0.6f);
                dialog.hide();

                DebugMessageFactory.printInfoMessage("USERNAME: "+usernameField.getText());
                DebugMessageFactory.printInfoMessage("PASSWORD: "+passwordField.getText());

                String userName = usernameField.getText();
                String passWord1 = passwordField.getText();
                String passWord2 = password2Field.getText();

                if(!checkUsername(userName)){
                    dialog.text("FAILURE!\nUsername length must be between 5 and 12!");
                    changeStatusText = true;
                }else if(!checkPassword(passWord1, passWord2)){
                    dialog.text("FAILURE!\nPassword length must be between 6 and 15!\nEntered passwords must be the same.");
                    changeStatusText = true;
                }else {
                    startScreen.game.registerPlayer(usernameField.getText(), passwordField.getText());
                    dialog.text("Waiting...");
                    changeStatusText = true;
                }
                dialog.show(stage);

                currentStamp = System.currentTimeMillis();
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
        mainTable.add(password2Label).expandX().padTop(10).right();
        mainTable.add(password2Field).expandX().padTop(10).padLeft(10).width(300);
        mainTable.row();
        mainTable.add(backButton).expandX().padTop(20);
        mainTable.add(sendButton).expandX().padTop(20);

        stage.addActor(mainTable);
        Gdx.input.setInputProcessor(stage);
    }

    private boolean checkUsername(String username){
        return username.length() >= 5 && username.length() <= 12;
    }

    private boolean checkPassword(String pw1, String pw2){
        if(pw1.length() < 6 || pw1.length() > 15){
            return false;
        }
        if(!pw1.equals(pw2)){
            return false;
        }
        return true;
    }

    @Override
    public void render(float delta) {

        if(sendButtonPressed && !changeStatusText && (System.currentTimeMillis() - currentStamp) > signUpDelay ) {
            if (startScreen.game.signInSuccessfull) {
                dialog = new MessageDialog("SIGN UP STATUS:", skin);
                dialog.setSize(StartScreen.START_WIDTH*0.7f, StartScreen.START_HEIGHT*0.6f);
                dialog.text("SUCCESS!\nYou can now enjoy the game.");
                dialog.show(stage);
            } else {
                dialog = new MessageDialog("SIGN UP STATUS:", skin);
                dialog.setSize(StartScreen.START_WIDTH*0.7f, StartScreen.START_HEIGHT*0.6f);
                dialog.text("FAILURE!\nUSERNAME already taken.");
                dialog.show(stage);
            }
            changeStatusText = true;
            sendButtonPressed = false;
        }

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
