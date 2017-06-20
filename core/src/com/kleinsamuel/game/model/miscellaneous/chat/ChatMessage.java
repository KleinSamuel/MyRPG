package com.kleinsamuel.game.model.miscellaneous.chat;

import java.text.DateFormat;

/**
 * Created by sam on 18.06.17.
 */

public class ChatMessage {

    public String sender;
    public String message;
    public String date;

    public ChatMessage(String sender, String message){
        this.sender = sender;
        this.message = message;
        this.date =  DateFormat.getInstance().format(System.currentTimeMillis());
    }

    public String getPrettyString(){
        return sender+": "+message;
    }

}
