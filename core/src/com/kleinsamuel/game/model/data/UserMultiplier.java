package com.kleinsamuel.game.model.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.kleinsamuel.game.util.DebugMessageFactory;

/**
 * Created by sam on 06.06.17.
 */

public class UserMultiplier {

    public float MULTIPLIER_ATTACK = 1.0f;
    public float MULTIPLIER_ATTACK_SPEED = 1.0f;
    public float MULTIPLIER_DEFENSE = 1.0f;
    public float MULTIPLIER_HEALTH = 1.0f;
    public float MULTIPLIER_MANA = 1.0f;
    public float MULTIPLIER_EXPERIENCE = 1.0f;
    public float MULTIPLIER_MOVING_SPEED = 1.0f;
    public float MULTIPLIER_RANGE = 1.0f;
    public float MULTIPLIER_CRIT_DAMAGE = 1.0f;
    public float MULTIPLIER_CRIT_CHANCE = 1.0f;
    public float MULTIPLIER_WEAPON_EFFECT = 1.0f;
    public float MULTIPLIER_LUCK = 1.0f;

    public UserMultiplier(){

    }

    public void writeToFile() {

        DebugMessageFactory.printInfoMessage("READ MULTIPLIER FILE IN LOCAL DIR: "+ Gdx.files.getLocalStoragePath());
        FileHandle handle = Gdx.files.local("multiplier.txt");

        StringBuilder sb = new StringBuilder();

        sb.append("mult_attack:"+MULTIPLIER_ATTACK+"\n");
        sb.append("mult_attack_speed:"+MULTIPLIER_ATTACK_SPEED+"\n");
        sb.append("mult_defense:"+MULTIPLIER_DEFENSE+"\n");
        sb.append("mult_health:"+MULTIPLIER_HEALTH+"\n");
        sb.append("mult_mana:"+MULTIPLIER_MANA+"\n");
        sb.append("mult_experience:"+MULTIPLIER_EXPERIENCE+"\n");
        sb.append("mult_speed:"+MULTIPLIER_MOVING_SPEED+"\n");
        sb.append("mult_range:"+MULTIPLIER_RANGE+"\n");
        sb.append("mult_crit_damage:"+MULTIPLIER_CRIT_DAMAGE+"\n");
        sb.append("mult_crit_chance:"+MULTIPLIER_CRIT_CHANCE+"\n");
        sb.append("mult_weapon_effect:"+MULTIPLIER_WEAPON_EFFECT+"\n");
        sb.append("mult_luck:"+MULTIPLIER_LUCK+"\n");

        DebugMessageFactory.printNormalMessage("SAVED USER MULTIPLIER TO FILE.");

        handle.writeString(sb.toString(), false);
    }

    public static UserMultiplier readFromFile() {

        FileHandle handle = Gdx.files.local("multiplier.txt");

        DebugMessageFactory.printNormalMessage("MULTIPLIER SAVEGAME: "+handle.path());

        UserMultiplier um = new UserMultiplier();

		/* if user file does not exists start with standard */
        if(!handle.exists()) {
            DebugMessageFactory.printInfoMessage("NO SAVEGAME FOUND. CREATED NEW STANDARD SAVEGAME.");
            return um;
        }

        String[] tmp = handle.readString().split("\n");

        for (int i = 0; i < tmp.length; i++) {

            String line = tmp[i];
            String[] lineArray = line.split(":");

            if (lineArray[0].equals("mult_attack")) {
                um.MULTIPLIER_ATTACK = Float.parseFloat(lineArray[1]);
            } else if (lineArray[0].equals("mult_attack_speed")) {
                um.MULTIPLIER_ATTACK_SPEED = Float.parseFloat(lineArray[1]);
            } else if (lineArray[0].equals("mult_defense")) {
                um.MULTIPLIER_DEFENSE = Float.parseFloat(lineArray[1]);
            } else if (lineArray[0].equals("mult_health")) {
                um.MULTIPLIER_HEALTH = Float.parseFloat(lineArray[1]);
            } else if (lineArray[0].equals("mult_mana")) {
                um.MULTIPLIER_MANA = Float.parseFloat(lineArray[1]);
            } else if (lineArray[0].equals("mult_experience")) {
                um.MULTIPLIER_EXPERIENCE = Float.parseFloat(lineArray[1]);
            } else if (lineArray[0].equals("mult_speed")) {
                um.MULTIPLIER_MOVING_SPEED = Float.parseFloat(lineArray[1]);
            } else if (lineArray[0].equals("mult_range")) {
                um.MULTIPLIER_RANGE = Float.parseFloat(lineArray[1]);
            } else if (lineArray[0].equals("mult_crit_damage")) {
                um.MULTIPLIER_CRIT_DAMAGE = Float.parseFloat(lineArray[1]);
            } else if (lineArray[0].equals("mult_crit_chance")) {
                um.MULTIPLIER_CRIT_CHANCE = Float.parseFloat(lineArray[1]);
            } else if (lineArray[0].equals("mult_weapon_effect")) {
                um.MULTIPLIER_WEAPON_EFFECT = Float.parseFloat(lineArray[1]);
            } else if (lineArray[0].equals("mult_luck")) {
                um.MULTIPLIER_LUCK = Float.parseFloat(lineArray[1]);
            }
        }
        return um;
    }

}
