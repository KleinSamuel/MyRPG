package com.kleinsamuel.game.model.miscellaneous.chat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kleinsamuel.game.model.Assets;
import com.kleinsamuel.game.screens.MyInputProcessor;
import com.kleinsamuel.game.screens.PlayScreen;
import com.kleinsamuel.game.util.DebugMessageFactory;
import com.kleinsamuel.game.util.Utils;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by sam on 19.06.17.
 */

public class ChatWindowBig {

    private PlayScreen playScreen;

    private Stage stage;
    private SpriteBatch batch;
    private Viewport viewport;
    private Skin skin;

    private boolean focusedTextinput = false;
    private float camPosoriginal;
    private String currentChatUser = "global";

    private TextField tf;
    private List list, listChat;
    private ScrollPane scrollPaneChat;

    public boolean SHOW_CHAT = false;

    private final float EXIT_X = 913 * Utils.FACTOR;
    private final float EXIT_Y = 496 * Utils.FACTOR;
    private final float EXIT_WIDTH = 38 * Utils.FACTOR;
    private final float EXIT_HEIGHT = 37 * Utils.FACTOR;

    public ChatWindowBig(final PlayScreen playScreen){
        this.playScreen = playScreen;

        batch = new SpriteBatch();
        viewport = new FitViewport(PlayScreen.V_WIDTH, PlayScreen.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);
        camPosoriginal = stage.getCamera().position.y;

        skin = new Skin(Gdx.files.internal("skins/default/uiskin.json"));

        Image bg = new Image(Assets.manager.get(Assets.chat_background, Texture.class));
        bg.setWidth(PlayScreen.V_WIDTH);
        bg.setHeight(PlayScreen.V_HEIGHT);

        tf = new TextField("", skin);
        tf.setName("textfield");

        list = new List(skin);
        list.setName("users");

        final ScrollPane scrollPane = new ScrollPane(list, skin);
        scrollPane.setScrollingDisabled(false, false);
        scrollPane.setFadeScrollBars(true);
        scrollPane.setOverscroll(false , false);
        scrollPane.setCancelTouchFocus(true);
        scrollPane.setName("usersScrollpane");

        list.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                currentChatUser = list.getSelected().toString();
                updateMessages();
                return false;
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.row();
        table.left();
        table.add(scrollPane).width(200).height(300).padLeft(30);

        listChat = new List(skin);
        listChat.setName("chat");
        listChat.setTouchable(null);
        listChat.setSelected(null);

        DebugMessageFactory.printInfoMessage("CREATE SCROLL PANE");
        scrollPaneChat = new ScrollPane(listChat, skin);
        scrollPaneChat.setScrollingDisabled(false, false);
        scrollPaneChat.setupFadeScrollBars(1.0f, 1.0f);
        scrollPaneChat.setFadeScrollBars(true);
        scrollPaneChat.setOverscroll(false , false);
        scrollPaneChat.setCancelTouchFocus(true);
        scrollPaneChat.setName("chatScrollpane");

        TextButton b = new TextButton("SEND", skin);

        table.add(scrollPaneChat).height(300).width(360).padLeft(15.0f);
        table.row();
        table.add();

        Table table2 = new Table();
        table2.add(tf).width(285).padLeft(15.0f);
        table2.add(b).width(70).padLeft(5.0f).height(30);

        table.add(table2);

        Table.debugTableColor = Color.BLACK;
        table.debug();

        stage.getRoot().addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!(event.getTarget() instanceof List) && !(event.getTarget() instanceof ScrollPane) && !(event.getTarget() instanceof TextField)){
                    stage.setScrollFocus(null);
                    stage.unfocusAll();
                }else {
                    focusedTextinput = false;
                    if(event.getTarget().getName().equals("chat") || event.getTarget().getName().equals("chatScrollpane")){
                        stage.unfocus(list);
                        stage.unfocus(scrollPane);
                    }else if(event.getTarget().getName().equals("users") || event.getTarget().getName().equals("usersScrollpane")){
                        stage.unfocus(listChat);
                        stage.unfocus(scrollPaneChat);
                    }else if(event.getTarget().getName().equals("textfield")){
                        stage.unfocus(listChat);
                        stage.unfocus(scrollPaneChat);
                        focusedTextinput = true;
                    }

                }
                return true;
            }
        });

        b.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                playScreen.chatFactory.addMessageOutgoing(currentChatUser, playScreen.player.content.NAME, tf.getText());
                playScreen.hud.chatWindowSmall.handleNewMessages();
                updateMessages();
                playScreen.game.sendMessageGlobal(currentChatUser, tf.getText());
                Gdx.input.setOnscreenKeyboardVisible(false);
                focusedTextinput = false;
                tf.setText("");
                return true;
            }
        });

        stage.addActor(bg);
        stage.addActor(table);
        stage.setDebugAll(true);

        updateMessages();
    }

    public void onCreate(){
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(new InputMultiplexer(new MyInputProcessor(playScreen), stage));
        Gdx.input.setCatchBackKey(true);
    }

    public void updateMessages(){
        if(listChat != null) {
            listChat.clearItems();
        }

        list.setSelected(currentChatUser);

        String[] users = new String[playScreen.chatFactory.getMessagesMap().size()];

        int usersCounter = 0;
        for(Map.Entry<String , TreeMap<Integer, ChatMessage>> entry : playScreen.chatFactory.getMessagesMap().entrySet()){
            users[usersCounter] = entry.getKey();

            String[] chat = new String[entry.getValue().size()];
            int counter = 0;
            for(ChatMessage cm : entry.getValue().values()){
                chat[counter] = cm.getPrettyString();
                counter++;
            }
            usersCounter++;

            if(entry.getKey().equals(currentChatUser)){
                if(listChat != null) {
                    listChat.setItems(chat);
                }
                if(scrollPaneChat != null){
                    scrollPaneChat.layout();
                    scrollPaneChat.scrollTo(0, 0, 0, 0);
                }
            }

        }
        listChat.setSelected(null);
        if(list != null) {
            list.setItems(users);
        }
    }

    public void handleClick(float screenX, float screenY){
        if(clickOnExit(screenX, screenY)){
            SHOW_CHAT = false;
            dispose();
            playScreen.button_click.play();
            playScreen.chatFactory.UNREAD_MESSAGES = false;
            return;
        }
    }

    public boolean clickOnExit(float screenX, float screenY){
        return (screenX >= EXIT_X && screenX <= EXIT_X+EXIT_WIDTH && screenY >= EXIT_Y && screenY <= EXIT_Y+EXIT_HEIGHT);
    }

    public void render(SpriteBatch batch) {
        if(SHOW_CHAT) {
            stage.draw();
            if (focusedTextinput) {
                stage.getCamera().position.y = camPosoriginal - PlayScreen.V_HEIGHT * 0.5f;
            } else {
                stage.getCamera().position.y = camPosoriginal;
            }
            stage.act();
        }
    }

    public void dispose(){
        Gdx.input.setInputProcessor(new MyInputProcessor(playScreen));
    }

}
