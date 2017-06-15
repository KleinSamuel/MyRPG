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
                return "YOU UNLOCKED THE DRAWE!";

        }

        return "THIS IDENTIFIER DOES NOT EXIST!";
    }

}
