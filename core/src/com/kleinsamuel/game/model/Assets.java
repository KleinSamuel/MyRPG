package com.kleinsamuel.game.model;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by sam on 30.05.17.
 */

public class Assets {

    public static final AssetManager manager = new AssetManager();

    public static final String playerSprite = "player_v_3_small.png";
    public static final String tilemarker_normal = "tilemarker.png";
    public static final String tilemarker_red = "tilemarker_red.png";

    public static final String rectangle_red = "rectangle_red.png";
    public static final String rectangle_black = "rectangle_black.png";
    public static final String rectangle_gray = "rectangle_gray.png";
    public static final String rectangle_light_gray = "rectangle_light_gray.png";
    public static final String rectangle_blue = "rectangle_blue.png";

    /* health mana exp bars */
    public static final String empty_bar = "bars/bar_empty.png";
    public static final String red_bar = "bars/healthbar_internal_red.png";
    public static final String blue_bar = "bars/mana_internal_blue.png";
    public static final String green_bar = "bars/experience_internal_green.png";

    /* hud */
    public static final String settings_button = "hud/settings_button.png";
    public static final String bag_button = "hud/bag_button.png";
    public static final String chat_button = "hud/chat_button.png";
    public static final String news_button = "hud/news_button.png";
    public static final String social_button = "hud/social_button.png";

    /* NPCs */
    public static final String bug_small = "npcs/bug_small.png";
    public static final String bird_crow = "npcs/bird_crow.png";
    public static final String elemental_water = "npcs/elemental_water.png";
    public static final String eloa_war = "npcs/eloa_war.png";

    /* bag */
    public static final String inventory_background = "bag/inventory_background.png";
    public static final String item_selected_background = "bag/item_selected_background.png";
    public static final String bag_close_button = "bag/bag_close_button.png";
    public static final String use_button_1 = "bag/use_button_1.png";
    public static final String use_button_2 = "bag/use_button_2.png";
    public static final String equip_button_1 = "bag/equip_button_1.png";
    public static final String equip_button_2 = "bag/equip_button_2.png";
    public static final String unequip_button_1 = "bag/unequip_button_1.png";
    public static final String unequip_button_2 = "bag/unequip_button_2.png";

    /* items */
    public static final String arrows_1 = "items/arrows_1.png";
    public static final String foot_left_1 = "items/foot_left_1.png";
    public static final String foot_right_1 = "items/foot_right_1.png";
    public static final String hand_left_1 = "items/hand_left_1.png";
    public static final String hand_right_1 = "items/hand_right_1.png";
    public static final String helmet_2 = "items/helmet_2.png";
    public static final String helmet_3 = "items/helmet_3.png";
    public static final String legs_1 = "items/legs_1.png";
    public static final String necklace_1 = "items/necklace_1.png";
    public static final String potion_blue_1 = "items/potion_blue_1.png";
    public static final String potion_red_1 = "items/potion_red_1.png";
    public static final String potion_health_1 = "items/potion_health_1.png";
    public static final String torso_1 = "items/torso_1.png";

    public static void load() {
        manager.load(playerSprite, Texture.class);
        manager.load(tilemarker_normal, Texture.class);
        manager.load(tilemarker_red, Texture.class);
        manager.load(rectangle_black, Texture.class);
        manager.load(rectangle_red, Texture.class);
        manager.load(rectangle_gray, Texture.class);
        manager.load(rectangle_blue, Texture.class);
        manager.load(rectangle_light_gray, Texture.class);
        manager.load(settings_button, Texture.class);
        manager.load(bag_button, Texture.class);
        manager.load(chat_button, Texture.class);
        manager.load(news_button, Texture.class);
        manager.load(social_button, Texture.class);
        manager.load(inventory_background, Texture.class);
        manager.load(item_selected_background, Texture.class);
        manager.load(bag_close_button, Texture.class);
        manager.load(use_button_1, Texture.class);
        manager.load(use_button_2, Texture.class);
        manager.load(equip_button_1, Texture.class);
        manager.load(equip_button_2, Texture.class);
        manager.load(unequip_button_1, Texture.class);
        manager.load(unequip_button_2, Texture.class);

        manager.load(empty_bar, Texture.class);
        manager.load(red_bar, Texture.class);
        manager.load(blue_bar, Texture.class);
        manager.load(green_bar, Texture.class);

        manager.load(bug_small, Texture.class);
        manager.load(bird_crow, Texture.class);
        manager.load(elemental_water, Texture.class);
        manager.load(eloa_war, Texture.class);

        manager.load(arrows_1, Texture.class);
        manager.load(foot_left_1, Texture.class);
        manager.load(foot_right_1, Texture.class);
        manager.load(hand_left_1, Texture.class);
        manager.load(hand_right_1, Texture.class);
        manager.load(helmet_2, Texture.class);
        manager.load(helmet_3, Texture.class);
        manager.load(legs_1, Texture.class);
        manager.load(necklace_1, Texture.class);
        manager.load(potion_blue_1, Texture.class);
        manager.load(potion_health_1, Texture.class);
        manager.load(potion_red_1, Texture.class);
        manager.load(torso_1, Texture.class);

    }

    public static void dispose()     {
        manager.dispose();
    }

    public static boolean update() {
        return manager.update();
    }

}
