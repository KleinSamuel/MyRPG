package com.kleinsamuel.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by sam on 30.05.17.
 */

public class Utils {

    public static final int TILEWIDTH = 24;
    public static final int TILEHEIGHT = 24;
    public static final float ZOOM_FACTOR = 0.5f;

    public static BitmapFont basicFont = new BitmapFont(Gdx.files.internal("font/8bitfont.fnt"));

    public static Vector3 getArrayCoordinates(float screenX, float screenY){
        int x = (int)((screenX)/(TILEWIDTH));
        int y = (int)((screenY)/(TILEHEIGHT));
        return new Vector3(x, y, 0);
    }

    public static Vector3 getScreenCoordinates(int arrayX, int arrayY, OrthographicCamera cam){
        int adjX = arrayX*TILEWIDTH;
        int adjY = arrayY*TILEHEIGHT;
        return cam.project(new Vector3(adjX, adjY, 0));
    }

    private static GlyphLayout layout = new GlyphLayout();

    public static Vector3 getWidthAndHeightOfString(BitmapFont font, String s){
        layout.setText(font, s);
        float width = layout.width;
        float height = layout.height;
        return new Vector3(width, height, 0);
    }

}
