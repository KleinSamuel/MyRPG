package com.kleinsamuel.game.model.miscellaneous.chat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kleinsamuel.game.screens.PlayScreen;
import com.kleinsamuel.game.util.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by sam on 18.06.17.
 */

public class ChatWindowSmall {

    private PlayScreen playScreen;

    private final float chat_window_x = 10;
    private final float chat_window_y = 10;
    private final float chat_window_width = 250;
    private final float chat_window_height = 50;

    private boolean dragging = false;
    private int oldDrag_y = -1;

    private SpriteBatch chatBatch;
    private Viewport chatViewport;
    private Skin skin;
    private Stage stage;
    private ScrollPane scrollPane;
    private List chatList;

    private TreeMap<Integer, ChatMessage> lastMessages;
    private HashMap<Integer, Vector3> positions;

    private int maxYOffset;
    private int currentYOffset;

    public ChatWindowSmall(PlayScreen playScreen){
        this.playScreen = playScreen;
        positions = new HashMap<Integer, Vector3>();

        handleNewMessages();
        updatePositions(0);
    }

    public void handleNewMessages(){
        lastMessages = playScreen.chatFactory.getLastMessages("global", 7);
        currentYOffset = 0;
        updatePositions(currentYOffset);
    }

    public void updatePositions(int currentY){
        float x = chat_window_x;
        float y = chat_window_y;

        for(Map.Entry<Integer, ChatMessage> entry : lastMessages.entrySet()){
            Utils.chatFont.getData().setScale(0.7f, 0.7f);
            Utils.chatFont.setColor(Color.BLACK);
            Vector3 widthAndHeightOfWrap = Utils.getWidthAndHeightOfWrappedString(Utils.chatFont, entry.getValue().getPrettyString(), (int)chat_window_width, 2);
            y += widthAndHeightOfWrap.y+2;

            positions.put(entry.getKey(), new Vector3(x, y-currentY, 0));
        }

        maxYOffset = (int)y;
    }

    public boolean handleClickUp(int x, int y){
        if(x >= chat_window_x && x <= chat_window_x+chat_window_width && y >= chat_window_y && y <= chat_window_y+chat_window_height){
            playScreen.chatWindowBig.SHOW_CHAT = true;
            playScreen.chatWindowBig.onCreate();
            playScreen.button_click.play();
            return true;
        }
        return false;
    }

    public void render(SpriteBatch batch){
        for (Map.Entry<Integer, ChatMessage> entry : lastMessages.entrySet()){
            Vector3 pos = positions.get(entry.getKey());
            if(pos.y >= chat_window_y && pos.y <= chat_window_y+chat_window_height){
                Utils.chatFont.getData().setScale(0.7f, 0.7f);
                Utils.chatFont.setColor(Color.BLACK);
                Utils.chatFont.draw(batch, entry.getValue().getPrettyString(), pos.x, pos.y, chat_window_width, Align.left, true);
            }
        }

    }

}
