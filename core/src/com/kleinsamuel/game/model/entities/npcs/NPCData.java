package com.kleinsamuel.game.model.entities.npcs;

import com.badlogic.gdx.math.Vector3;
import com.kleinsamuel.game.util.Utils;

/**
 * Created by sam on 02.06.17.
 */

public class NPCData {

    public int id;
    public int npc_key;
    public String name;
    public float x;
    public float y;
    public int level;
    public float speed;
    public int current_health;
    public int max_health;

    public Vector3 moveTo;

    public NPCData(int id, int npc_key, int level, float x, float y, float speed, int current_health, int max_health) {
        this.id = id;
        this.npc_key = npc_key;

        String randomRoachName = Utils.turkroachNames.get(Utils.random.nextInt(Utils.turkroachNames.size()));
        this.name = randomRoachName;

        this.x = x;
        this.y = y;
        this.speed = speed;
        this.level = level;
        this.current_health = current_health;
        this.max_health = max_health;
    }

}
