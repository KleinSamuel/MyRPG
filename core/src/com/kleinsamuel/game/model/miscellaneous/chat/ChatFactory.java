package com.kleinsamuel.game.model.miscellaneous.chat;

import com.kleinsamuel.game.model.entities.OtherPlayer;
import com.kleinsamuel.game.screens.PlayScreen;
import com.kleinsamuel.game.util.DebugMessageFactory;
import com.kleinsamuel.game.util.Utils;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by sam on 18.06.17.
 */

public class ChatFactory {

    private PlayScreen playScreen;
    private TreeMap<String, TreeMap<Integer, ChatMessage>> messagesMap;

    public boolean UNREAD_MESSAGES = false;

    public ChatFactory(PlayScreen playScreen){
        this.playScreen = playScreen;

        messagesMap = new TreeMap<String, TreeMap<Integer, ChatMessage>>();

        messagesMap.put("global", new TreeMap<Integer, ChatMessage>());
        messagesMap.put("local", new TreeMap<Integer, ChatMessage>());

        DebugMessageFactory.printInfoMessage("INIT CHAT");
        for(OtherPlayer op : playScreen.game.otherPlayers.values()){
            DebugMessageFactory.printInfoMessage("OP: "+op.name);
            playScreen.chatFactory.getMessagesMap().put(op.name, new TreeMap<Integer, ChatMessage>());
            playScreen.chatWindowBig.updateMessages();
        }
    }

    public void addMessageIncoming(String identifier, String sender, String message){

        /* put messages in basic chatrooms */
        if(identifier.equals("global") || identifier.equals("local")){
            this.messagesMap.get(identifier).put(messagesMap.get(identifier).size(), new ChatMessage(sender, message));
            return;
        }

        if(identifier.equals(playScreen.player.content.NAME)){
            if(!messagesMap.containsKey(sender)){
                messagesMap.put(sender, new TreeMap<Integer, ChatMessage>());
            }
            messagesMap.get(sender).put(messagesMap.get(sender).size(), new ChatMessage(sender, message));
            playScreen.notification.play();
            UNREAD_MESSAGES = true;
            return;
        }
    }

    public void addMessageOutgoing(String identifier, String sender, String message){

        /* put messages in basic chatrooms */
        if(identifier.equals("global") || identifier.equals("local")){
            this.messagesMap.get(identifier).put(messagesMap.get(identifier).size(), new ChatMessage(sender, message));
            return;
        }

        if(!messagesMap.containsKey(identifier)){
            messagesMap.put(identifier, new TreeMap<Integer, ChatMessage>());
        }
        messagesMap.get(identifier).put(messagesMap.get(identifier).size(), new ChatMessage(sender, message));

    }

    public ChatMessage getMessage(String identifier, int i){
        return messagesMap.get(identifier).get(i);
    }

    public TreeMap<Integer, ChatMessage> getMessages(String identifier){
        return this.messagesMap.get(identifier);
    }

    public TreeMap<String, TreeMap<Integer, ChatMessage>> getMessagesMap(){
        return this.messagesMap;
    }

    public TreeMap<Integer, ChatMessage> getLastMessages(String identifier, int amount){

        if(messagesMap.get(identifier).size() <= amount){
            return messagesMap.get(identifier);
        }

        TreeMap<Integer, ChatMessage> out = new TreeMap<Integer, ChatMessage>();
        for(int i = amount-1; i >= 0; i--){
            out.put(i, messagesMap.get(identifier).get(messagesMap.get(identifier).size()-1-i));
        }
        return out;
    }
}
