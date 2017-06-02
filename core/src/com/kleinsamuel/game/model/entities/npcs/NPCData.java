package com.kleinsamuel.game.model.entities.npcs;

/**
 * Created by sam on 02.06.17.
 */

public class NPCData {

    private int id;
    private int npc_key;
    private int x;
    private int y;
    private int level;
    private int current_health;
    private int max_health;

    public NPCData(int id, int npc_key, int x, int y, int level, int current_health, int max_health) {
        setId(id);
        setNpc_key(npc_key);
        setX(x);
        setY(y);
        setCurrent_health(current_health);
        setMax_health(max_health);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNpc_key() {
        return npc_key;
    }

    public void setNpc_key(int npc_key) {
        this.npc_key = npc_key;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCurrent_health() {
        return current_health;
    }

    public void setCurrent_health(int current_health) {
        this.current_health = current_health;
    }

    public int getMax_health() {
        return max_health;
    }

    public void setMax_health(int max_health) {
        this.max_health = max_health;
    }

}
