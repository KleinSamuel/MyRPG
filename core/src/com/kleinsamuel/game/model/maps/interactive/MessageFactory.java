package com.kleinsamuel.game.model.maps.interactive;

/**
 * Contains all messages which are displayed by MessageInteractionTiles as String
 *
 * Created by sam on 15.06.17.
 */

public class MessageFactory {

    public static String getMessageForMessageIdentifier(int messageIdentifier){

        switch (messageIdentifier){

            case 0:
                return "THIS DRAWER IS LOCKED!";
            case 1:
                return "YOU UNLOCKED THE DRAWER!";
            case 2:
                return "THIS IS A CONFIRMATION DIALOG. DO YOU WANT TO ACCEPT THE NON SPECIFIED CONDITIONS?";

        }

        return "THIS IDENTIFIER DOES NOT EXIST!";
    }

}
