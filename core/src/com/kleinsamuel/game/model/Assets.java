package com.kleinsamuel.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
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

    /* MAX_HEALTH MAX_MANA exp bars */
    public static final String empty_bar = "bars/bar_empty.png";
    public static final String red_bar = "bars/healthbar_internal_red.png";
    public static final String blue_bar = "bars/mana_internal_blue.png";
    public static final String green_bar = "bars/experience_internal_green.png";

    public static final String uf_healthbar = "bars/uf_healthbar.png";
    public static final String uf_manabar = "bars/uf_manabar.png";
    public static final String uf_experiencebar = "bars/uf_experiencebar.png";

    /* hud */
    public static final String money_skull_bar = "hud/money_skull_bar.png";
    public static final String settings_button = "hud/settings_button.png";
    public static final String bag_button = "hud/bag_button.png";
    public static final String news_button = "hud/news_button.png";
    public static final String social_button = "hud/social_button.png";
    public static final String stats_button = "hud/stats_button.png";
    public static final String lexicon_button = "hud/ency_button.png";
    public static final String shop_button = "hud/shop_button.png";

    public static final String tick_box_unticked = "tick_box_unticked.png";
    public static final String tick_box_ticked = "tick_box_ticked.png";

    /* NPC ANIMALS */
    public static final String bug_small_green = "npcs/animals/uf_bug_small_green.png";
    public static final String bug_small_purple = "npcs/animals/uf_bug_small_purple.png";
    public static final String bug_small_red = "npcs/animals/uf_bug_small_red.png";
    public static final String bug_big_green = "npcs/animals/uf_bug_big_green.png";
    public static final String bug_big_purple = "npcs/animals/uf_bug_big_purple.png";
    public static final String bug_big_red = "npcs/animals/uf_bug_big_red.png";

    public static final String butterfly_small_red = "npcs/animals/uf_butterfly_small_red.png";
    public static final String butterfly_small_black = "npcs/animals/uf_butterfly_small_black.png";
    public static final String butterfly_small_white = "npcs/animals/uf_butterfly_small_white.png";

    public static final String crow_small_black = "npcs/animals/uf_crow_small_black.png";
    public static final String crow_small_brown = "npcs/animals/uf_crow_small_brown.png";
    public static final String crow_small_white = "npcs/animals/uf_crow_small_white.png";

    public static final String frog_small_blue = "npcs/animals/uf_frog_small_blue.png";
    public static final String frog_small_brown = "npcs/animals/uf_frog_small_brown.png";
    public static final String frog_small_green = "npcs/animals/uf_frog_small_green.png";

    public static final String rat_small_black = "npcs/animals/uf_rat_small_black.png";
    public static final String rat_small_brown = "npcs/animals/uf_rat_small_brown.png";
    public static final String rat_small_grey = "npcs/animals/uf_rat_small_grey.png";
    public static final String rat_small_white = "npcs/animals/uf_rat_small_white.png";
    public static final String rat_big_black = "npcs/animals/uf_rat_big_black.png";
    public static final String rat_big_brown = "npcs/animals/uf_rat_big_brown.png";
    public static final String rat_big_grey = "npcs/animals/uf_rat_big_grey.png";
    public static final String rat_big_white = "npcs/animals/uf_rat_big_white.png";

    public static final String snake_small_green = "npcs/animals/uf_snake_small_green.png";
    public static final String snake_big_green = "npcs/animals/uf_snake_big_green.png";

    public static final String spider_small_black = "npcs/animals/uf_spider_small_black.png";
    public static final String spider_small_brown = "npcs/animals/uf_spider_small_brown.png";
    public static final String spider_small_purple = "npcs/animals/uf_spider_small_purple.png";
    public static final String spider_big_black = "npcs/animals/uf_spider_big_black.png";
    public static final String spider_big_brown = "npcs/animals/uf_spider_big_brown.png";
    public static final String spider_big_purple = "npcs/animals/uf_spider_big_purple.png";

    public static final String wolf_small_black = "npcs/animals/uf_wolf_small_black.png";
    public static final String wolf_small_brown = "npcs/animals/uf_wolf_small_brown.png";
    public static final String wolf_small_darkblack = "npcs/animals/uf_wolf_small_darkblack.png";
    public static final String wolf_small_white = "npcs/animals/uf_wolf_small_white.png";

    public static final String worm_small_brown = "npcs/animals/uf_worm_small_brown.png";
    public static final String worm_big_brown = "npcs/animals/uf_worm_big_brown.png";
    
    public static final String eloa_war = "npcs/eloa_war.png";
    public static final String musel = "npcs/musel24x24.png";

    /* bag */
    public static final String inventory_background = "bag/uf_inventory_background_2.png";
    public static final String item_selected_background = "bag/item_selected_background.png";
    public static final String bag_close_button = "bag/bag_close_button.png";
    public static final String use_button_1 = "bag/use_button_1.png";
    public static final String use_button_2 = "bag/use_button_2.png";
    public static final String equip_button_1 = "bag/equip_button_1.png";
    public static final String equip_button_2 = "bag/equip_button_2.png";
    public static final String unequip_button_1 = "bag/unequip_button_1.png";
    public static final String unequip_button_2 = "bag/unequip_button_2.png";

    /* buttons */
    public static final String yes_button = "buttons/yes_button.png";
    public static final String no_button = "buttons/no_button.png";
    public static final String ok_button = "buttons/ok_button.png";
    public static final String equip_button = "buttons/equip_button.png";
    public static final String unequip_button = "buttons/unequip_button.png";
    public static final String drop_button = "buttons/drop_button.png";
    public static final String use_button = "buttons/use_button.png";
    public static final String buy_button = "buttons/buy_button.png";
    public static final String sell_button = "buttons/sell_button.png";
    public static final String logout_button = "buttons/logout_button.png";
    public static final String potion_red_button = "buttons/potion_red_button.png";
    public static final String potion_blue_button = "buttons/potion_blue_button.png";

    /* lexicon */
    public static final String lexicon_background = "lexicon/uf_lexicon_background_filled.png";

    /* stats */
    public static final String stats_background = "stats/uf_stats_background_filled.png";

    /* merchant */
    public static final String merchant_1 = "merchant/merchant_1.png";
    public static final String merchant_background_buy = "merchant/merchant_background_buy.png";
    public static final String merchant_background_sell = "merchant/merchant_background_sell.png";

    public static final String chat_background = "clean_background.png";

    public static final String notification_bullet = "ui/notification_bullet.png";

    /* popup */
    public static final String popup_background = "popup_background.png";

    /* items */
    public static final String key_gray = "items/key_gray.png";

    public static final String locked = "locked.png";

    /* consumables */
    public static final String potion_red = "items/potions/potion_red.png";
    public static final String potion_blue = "items/potions/potion_blue.png";

    /* equipment */
    public static final String armor_blood_torso = "items/equipment/torso/armor_blood_torso.png";
    public static final String armor_blood_helmet = "items/equipment/helmet/armor_blood_helmet.png";
    public static final String armor_blood_legs = "items/equipment/legs/armor_blood_legs.png";
    public static final String armor_blood_foot_left = "items/equipment/foot/armor_blood_foot_left.png";
    public static final String armor_blood_foot_right = "items/equipment/foot/armor_blood_foot_right.png";
    public static final String armor_blood_hand_left = "items/equipment/hand/armor_blood_hand_left.png";
    public static final String armor_blood_hand_right = "items/equipment/hand/armor_blood_hand_right.png";

    public static final String armor_chain_torso = "items/equipment/torso/armor_chain_torso.png";
    public static final String armor_chain_helmet = "items/equipment/helmet/armor_chain_helmet.png";
    public static final String armor_chain_legs = "items/equipment/legs/armor_chain_legs.png";
    public static final String armor_chain_foot_left = "items/equipment/foot/armor_chain_foot_left.png";
    public static final String armor_chain_foot_right = "items/equipment/foot/armor_chain_foot_right.png";
    public static final String armor_chain_hand_left = "items/equipment/hand/armor_chain_hand_left.png";
    public static final String armor_chain_hand_right = "items/equipment/hand/armor_chain_hand_right.png";

    public static final String armor_cloth_torso = "items/equipment/torso/armor_cloth_torso.png";
    public static final String armor_cloth_helmet = "items/equipment/helmet/armor_cloth_helmet.png";
    public static final String armor_cloth_legs = "items/equipment/legs/armor_cloth_legs.png";
    public static final String armor_cloth_foot_left = "items/equipment/foot/armor_cloth_foot_left.png";
    public static final String armor_cloth_foot_right = "items/equipment/foot/armor_cloth_foot_right.png";
    public static final String armor_cloth_hand_left = "items/equipment/hand/armor_cloth_hand_left.png";
    public static final String armor_cloth_hand_right = "items/equipment/hand/armor_cloth_hand_right.png";

    public static final String armor_gold_torso = "items/equipment/torso/armor_gold_torso.png";
    public static final String armor_gold_helmet = "items/equipment/helmet/armor_gold_helmet.png";
    public static final String armor_gold_legs = "items/equipment/legs/armor_gold_legs.png";
    public static final String armor_gold_foot_left = "items/equipment/foot/armor_gold_foot_left.png";
    public static final String armor_gold_foot_right = "items/equipment/foot/armor_gold_foot_right.png";
    public static final String armor_gold_hand_left = "items/equipment/hand/armor_gold_hand_left.png";
    public static final String armor_gold_hand_right = "items/equipment/hand/armor_gold_hand_right.png";

    public static final String armor_leather_torso = "items/equipment/torso/armor_leather_torso.png";
    public static final String armor_leather_helmet = "items/equipment/helmet/armor_leather_helmet.png";
    public static final String armor_leather_legs = "items/equipment/legs/armor_leather_legs.png";
    public static final String armor_leather_foot_left = "items/equipment/foot/armor_leather_foot_left.png";
    public static final String armor_leather_foot_right = "items/equipment/foot/armor_leather_foot_right.png";
    public static final String armor_leather_hand_left = "items/equipment/hand/armor_leather_hand_left.png";
    public static final String armor_leather_hand_right = "items/equipment/hand/armor_leather_hand_right.png";

    public static final String armor_mystic_torso = "items/equipment/torso/armor_mystic_torso.png";
    public static final String armor_mystic_helmet = "items/equipment/helmet/armor_mystic_helmet.png";
    public static final String armor_mystic_legs = "items/equipment/legs/armor_mystic_legs.png";
    public static final String armor_mystic_foot_left = "items/equipment/foot/armor_mystic_foot_left.png";
    public static final String armor_mystic_foot_right = "items/equipment/foot/armor_mystic_foot_right.png";
    public static final String armor_mystic_hand_left = "items/equipment/hand/armor_mystic_hand_left.png";
    public static final String armor_mystic_hand_right = "items/equipment/hand/armor_mystic_hand_right.png";

    public static final String armor_plate_torso = "items/equipment/torso/armor_plate_torso.png";
    public static final String armor_plate_helmet = "items/equipment/helmet/armor_plate_helmet.png";
    public static final String armor_plate_legs = "items/equipment/legs/armor_plate_legs.png";
    public static final String armor_plate_foot_left = "items/equipment/foot/armor_plate_foot_left.png";
    public static final String armor_plate_foot_right = "items/equipment/foot/armor_plate_foot_right.png";
    public static final String armor_plate_hand_left = "items/equipment/hand/armor_plate_hand_left.png";
    public static final String armor_plate_hand_right = "items/equipment/hand/armor_plate_hand_right.png";

    public static final String armor_royal_torso = "items/equipment/torso/armor_royal_torso.png";
    public static final String armor_royal_helmet = "items/equipment/helmet/armor_royal_helmet.png";
    public static final String armor_royal_legs = "items/equipment/legs/armor_royal_legs.png";
    public static final String armor_royal_foot_left = "items/equipment/foot/armor_royal_foot_left.png";
    public static final String armor_royal_foot_right = "items/equipment/foot/armor_royal_foot_right.png";
    public static final String armor_royal_hand_left = "items/equipment/hand/armor_royal_hand_left.png";
    public static final String armor_royal_hand_right = "items/equipment/hand/armor_royal_hand_right.png";

    public static final String armor_studded_torso = "items/equipment/torso/armor_studded_torso.png";
    public static final String armor_studded_helmet = "items/equipment/helmet/armor_studded_helmet.png";
    public static final String armor_studded_legs = "items/equipment/legs/armor_studded_legs.png";
    public static final String armor_studded_foot_left = "items/equipment/foot/armor_studded_foot_left.png";
    public static final String armor_studded_foot_right = "items/equipment/foot/armor_studded_foot_right.png";
    public static final String armor_studded_hand_left = "items/equipment/hand/armor_studded_hand_left.png";
    public static final String armor_studded_hand_right = "items/equipment/hand/armor_studded_hand_right.png";

    public static final String armor_wild_torso = "items/equipment/torso/armor_wild_torso.png";
    public static final String armor_wild_helmet = "items/equipment/helmet/armor_wild_helmet.png";
    public static final String armor_wild_legs = "items/equipment/legs/armor_wild_legs.png";
    public static final String armor_wild_foot_left = "items/equipment/foot/armor_wild_foot_left.png";
    public static final String armor_wild_foot_right = "items/equipment/foot/armor_wild_foot_right.png";
    public static final String armor_wild_hand_left = "items/equipment/hand/armor_wild_hand_left.png";
    public static final String armor_wild_hand_right = "items/equipment/hand/armor_wild_hand_right.png";

    /* animations */
    public static final String slash_single = "animations/slash_single.png";
    public static final String slash_triple = "animations/slash_triple.png";
    public static final String slash_curve_half = "animations/slash_curve_half.png";
    public static final String slash_curve_full = "animations/slash_curve_full.png";
    public static final String slash_x_symmetrical = "animations/slash_x_symmetrical.png";
    public static final String slash_x_asymmetrical = "animations/slash_x_asymmetrical.png";

    /* player sprites */
    public static final String dwarf_basic = "player/dwarf/dwarf_basic.png";
    public static final String elb_basic = "player/elb/elb_basic.png";
    public static final String halfling_basic = "player/halfling/halfling_basic.png";
    public static final String human_basic = "player/human/human_basic.png";
    public static final String murony_basic = "player/murony/murony_basic.png";
    public static final String orc_basic = "player/orc/orc_basic.png";
    public static final String wiedergaenger_basic = "player/wiedergaenger/wiedergaenger_basic.png";

    /* player portraits */
    public static final String portrait_human = "portraits/human_portrait.png";
    public static final String portrait_halfling = "portraits/halfling_portrait.png";
    public static final String portrait_dwarf = "portraits/dwarf_portrait.png";
    public static final String portrait_elb = "portraits/elb_portrait.png";
    public static final String portrait_orc = "portraits/orc_portrait.png";
    public static final String portrait_wiedergaenger = "portraits/wiedergaenger_portrait.png";
    public static final String portrait_murony = "portraits/murony_portrait.png";

    /*
        MUSIC
     */

    /*
        SOUND EFFECTS
     */
    public static final String hit_enemy = "sounds/hit_enemy.wav";
    public static final String hit_player = "sounds/hit_player.wav";
    public static final String drink_potion = "sounds/drink_sound.mp3";
    public static final String button_click_01 = "sounds/button_click_01_48k.wav";
    public static final String footsteps = "sounds/footsteps.mp3";

    public static Sound click_sound;

    public static void load() {
        manager.load(playerSprite, Texture.class);
        manager.load(tilemarker_normal, Texture.class);
        manager.load(tilemarker_red, Texture.class);
        manager.load(rectangle_black, Texture.class);
        manager.load(rectangle_red, Texture.class);
        manager.load(rectangle_gray, Texture.class);
        manager.load(rectangle_blue, Texture.class);
        manager.load(rectangle_light_gray, Texture.class);

        /* player */
        manager.load(dwarf_basic, Texture.class);
        manager.load(elb_basic, Texture.class);
        manager.load(halfling_basic, Texture.class);
        manager.load(human_basic, Texture.class);
        manager.load(murony_basic, Texture.class);
        manager.load(orc_basic, Texture.class);
        manager.load(wiedergaenger_basic, Texture.class);

        manager.load(portrait_human, Texture.class);
        manager.load(portrait_halfling, Texture.class);
        manager.load(portrait_dwarf, Texture.class);
        manager.load(portrait_elb, Texture.class);
        manager.load(portrait_orc, Texture.class);
        manager.load(portrait_wiedergaenger, Texture.class);
        manager.load(portrait_murony, Texture.class);

        manager.load(uf_healthbar, Texture.class);
        manager.load(uf_manabar, Texture.class);
        manager.load(uf_experiencebar, Texture.class);

        manager.load(settings_button, Texture.class);
        manager.load(bag_button, Texture.class);
        manager.load(lexicon_button, Texture.class);
        manager.load(news_button, Texture.class);
        manager.load(social_button, Texture.class);
        manager.load(money_skull_bar, Texture.class);
        manager.load(shop_button, Texture.class);
        manager.load(stats_button, Texture.class);

        manager.load(inventory_background, Texture.class);
        manager.load(item_selected_background, Texture.class);
        manager.load(bag_close_button, Texture.class);
        manager.load(use_button_1, Texture.class);
        manager.load(use_button_2, Texture.class);
        manager.load(equip_button_1, Texture.class);
        manager.load(equip_button_2, Texture.class);
        manager.load(unequip_button_1, Texture.class);
        manager.load(unequip_button_2, Texture.class);

        manager.load(yes_button, Texture.class);
        manager.load(no_button, Texture.class);
        manager.load(ok_button, Texture.class);
        manager.load(equip_button, Texture.class);
        manager.load(unequip_button, Texture.class);
        manager.load(drop_button, Texture.class);
        manager.load(use_button, Texture.class);
        manager.load(buy_button, Texture.class);
        manager.load(sell_button, Texture.class);
        manager.load(logout_button, Texture.class);
        manager.load(potion_red_button, Texture.class);
        manager.load(potion_blue_button, Texture.class);

        manager.load(tick_box_unticked, Texture.class);
        manager.load(tick_box_ticked, Texture.class);

        manager.load(lexicon_background, Texture.class);

        manager.load(stats_background, Texture.class);

        manager.load(chat_background, Texture.class);

        manager.load(notification_bullet, Texture.class);

        manager.load(merchant_background_buy, Texture.class);
        manager.load(merchant_background_sell, Texture.class);
        manager.load(merchant_1, Texture.class);

        manager.load(popup_background, Texture.class);

        manager.load(empty_bar, Texture.class);
        manager.load(red_bar, Texture.class);
        manager.load(blue_bar, Texture.class);
        manager.load(green_bar, Texture.class);

        /* NPC ANIMALS */
        manager.load(bug_small_green, Texture.class);
        manager.load(bug_small_purple, Texture.class);
        manager.load(bug_small_red, Texture.class);
        manager.load(bug_big_green, Texture.class);
        manager.load(bug_big_purple, Texture.class);
        manager.load(bug_big_red, Texture.class);

        manager.load(butterfly_small_black, Texture.class);
        manager.load(butterfly_small_red, Texture.class);
        manager.load(butterfly_small_white, Texture.class);

        manager.load(crow_small_black, Texture.class);
        manager.load(crow_small_brown, Texture.class);
        manager.load(crow_small_white, Texture.class);

        manager.load(frog_small_green, Texture.class);
        manager.load(frog_small_blue, Texture.class);
        manager.load(frog_small_brown, Texture.class);

        manager.load(rat_small_black, Texture.class);
        manager.load(rat_small_brown, Texture.class);
        manager.load(rat_small_grey, Texture.class);
        manager.load(rat_small_white, Texture.class);
        manager.load(rat_big_black, Texture.class);
        manager.load(rat_big_brown, Texture.class);
        manager.load(rat_big_grey, Texture.class);
        manager.load(rat_big_white, Texture.class);

        manager.load(snake_small_green, Texture.class);
        manager.load(snake_big_green, Texture.class);

        manager.load(spider_small_black, Texture.class);
        manager.load(spider_small_brown, Texture.class);
        manager.load(spider_small_purple, Texture.class);
        manager.load(spider_big_black, Texture.class);
        manager.load(spider_big_brown, Texture.class);
        manager.load(spider_big_purple, Texture.class);

        manager.load(wolf_small_black, Texture.class);
        manager.load(wolf_small_brown, Texture.class);
        manager.load(wolf_small_darkblack, Texture.class);
        manager.load(wolf_small_white, Texture.class);

        manager.load(worm_small_brown, Texture.class);
        manager.load(worm_big_brown, Texture.class);

        manager.load(eloa_war, Texture.class);
        manager.load(musel, Texture.class);

        manager.load(locked, Texture.class);

        /* items */
        manager.load(key_gray, Texture.class);

        /* consumables */
        manager.load(potion_red, Texture.class);
        manager.load(potion_blue, Texture.class);

        /* equipment */
        manager.load(armor_blood_torso, Texture.class);
        manager.load(armor_blood_helmet, Texture.class);
        manager.load(armor_blood_legs, Texture.class);
        manager.load(armor_blood_foot_left, Texture.class);
        manager.load(armor_blood_foot_right, Texture.class);
        manager.load(armor_blood_hand_left, Texture.class);
        manager.load(armor_blood_hand_right, Texture.class);

        manager.load(armor_chain_torso, Texture.class);
        manager.load(armor_chain_helmet, Texture.class);
        manager.load(armor_chain_legs, Texture.class);
        manager.load(armor_chain_foot_left, Texture.class);
        manager.load(armor_chain_foot_right, Texture.class);
        manager.load(armor_chain_hand_left, Texture.class);
        manager.load(armor_chain_hand_right, Texture.class);

        manager.load(armor_cloth_torso, Texture.class);
        manager.load(armor_cloth_helmet, Texture.class);
        manager.load(armor_cloth_legs, Texture.class);
        manager.load(armor_cloth_foot_left, Texture.class);
        manager.load(armor_cloth_foot_right, Texture.class);
        manager.load(armor_cloth_hand_left, Texture.class);
        manager.load(armor_cloth_hand_right, Texture.class);

        manager.load(armor_gold_torso, Texture.class);
        manager.load(armor_gold_helmet, Texture.class);
        manager.load(armor_gold_legs, Texture.class);
        manager.load(armor_gold_foot_left, Texture.class);
        manager.load(armor_gold_foot_right, Texture.class);
        manager.load(armor_gold_hand_left, Texture.class);
        manager.load(armor_gold_hand_right, Texture.class);

        manager.load(armor_leather_torso, Texture.class);
        manager.load(armor_leather_helmet, Texture.class);
        manager.load(armor_leather_legs, Texture.class);
        manager.load(armor_leather_foot_left, Texture.class);
        manager.load(armor_leather_foot_right, Texture.class);
        manager.load(armor_leather_hand_left, Texture.class);
        manager.load(armor_leather_hand_right, Texture.class);

        manager.load(armor_mystic_torso, Texture.class);
        manager.load(armor_mystic_helmet, Texture.class);
        manager.load(armor_mystic_legs, Texture.class);
        manager.load(armor_mystic_foot_left, Texture.class);
        manager.load(armor_mystic_foot_right, Texture.class);
        manager.load(armor_mystic_hand_left, Texture.class);
        manager.load(armor_mystic_hand_right, Texture.class);

        manager.load(armor_plate_torso, Texture.class);
        manager.load(armor_plate_helmet, Texture.class);
        manager.load(armor_plate_legs, Texture.class);
        manager.load(armor_plate_foot_left, Texture.class);
        manager.load(armor_plate_foot_right, Texture.class);
        manager.load(armor_plate_hand_left, Texture.class);
        manager.load(armor_plate_hand_right, Texture.class);

        manager.load(armor_royal_torso, Texture.class);
        manager.load(armor_royal_helmet, Texture.class);
        manager.load(armor_royal_legs, Texture.class);
        manager.load(armor_royal_foot_left, Texture.class);
        manager.load(armor_royal_foot_right, Texture.class);
        manager.load(armor_royal_hand_left, Texture.class);
        manager.load(armor_royal_hand_right, Texture.class);

        manager.load(armor_studded_torso, Texture.class);
        manager.load(armor_studded_helmet, Texture.class);
        manager.load(armor_studded_legs, Texture.class);
        manager.load(armor_studded_foot_left, Texture.class);
        manager.load(armor_studded_foot_right, Texture.class);
        manager.load(armor_studded_hand_left, Texture.class);
        manager.load(armor_studded_hand_right, Texture.class);

        manager.load(armor_wild_torso, Texture.class);
        manager.load(armor_wild_helmet, Texture.class);
        manager.load(armor_wild_legs, Texture.class);
        manager.load(armor_wild_foot_left, Texture.class);
        manager.load(armor_wild_foot_right, Texture.class);
        manager.load(armor_wild_hand_left, Texture.class);
        manager.load(armor_wild_hand_right, Texture.class);

        manager.load(slash_single, Texture.class);
        manager.load(slash_triple, Texture.class);
        manager.load(slash_curve_half, Texture.class);
        manager.load(slash_curve_full, Texture.class);
        manager.load(slash_x_symmetrical, Texture.class);
        manager.load(slash_x_asymmetrical, Texture.class);

        //manager.load(hit_enemy, Sound.class);
        //manager.load(hit_player, Sound.class);
        //manager.load(drink_potion, Sound.class);
        //manager.load(button_click_01, Sound.class);
        //manager.load(footsteps, Sound.class);

        //click_sound = Gdx.audio.newSound(Gdx.files.internal("sounds/button_click_01_48k.wav"));
    }

    public static void dispose()     {
        manager.dispose();
    }

    public static boolean update() {
        return manager.update();
    }

}
