package com.kleinsamuel.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.kleinsamuel.game.screens.PlayScreen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by sam on 30.05.17.
 */

public class Utils {

    public static final int TILEWIDTH = 24;
    public static final int TILEHEIGHT = 24;
    public static final float ZOOM_FACTOR = 0.5f;

    private static final float BASE_WIDTH = 960;
    private static final float BASE_HEIGHT = 540;

    public static final float FACTOR = PlayScreen.V_WIDTH/BASE_WIDTH;

    public static Random random = new Random();

    public static BitmapFont basicFont = new BitmapFont(Gdx.files.internal("font/8bitfont.fnt"));
    public static BitmapFont testFont = new BitmapFont(Gdx.files.internal("font/editundo.fnt"));

    public static ArrayList<String> turkroachNames;

    public static FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/editundo.ttf"));
    public static FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    public static BitmapFont font10, moneyFont;

    static {

        parameter.size = 14;
        parameter.gamma = 10;
        parameter.mono = true;
        parameter.spaceX = 1;
        font10 = generator.generateFont(parameter);
        font10.setUseIntegerPositions(false);

        parameter.borderWidth = 2;
        parameter.borderColor = Color.BLACK;
        moneyFont = generator.generateFont(parameter);
        moneyFont.setUseIntegerPositions(false);

        FileHandle roachHandle = Gdx.files.internal("turkroach_names.txt");
        String[] tmp = roachHandle.readString().split("\n");
        turkroachNames = new ArrayList<String>(Arrays.asList(tmp));
    }

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

    /**
     * Returns x and y coordinates of array position in which more than 50% of players mass are.
     *
     * @param screenX
     * @param screenY
     * @return
     */
    public static Vector3 getArrayCoordinatesMiddle(float screenX, float screenY){
        float xOffset = screenX%TILEWIDTH;
        float yOffset = screenY%TILEHEIGHT;

        int x = 0;
        if(xOffset < TILEWIDTH/2){
            x = (int)screenX/TILEWIDTH;
        }else{
            x = ((int)screenX/TILEWIDTH)+1;
        }

        int y = 0;
        if(yOffset < TILEHEIGHT/2){
            y = (int)screenY/TILEHEIGHT;
        }else{
            y = ((int)screenY/TILEHEIGHT)+1;
        }

        return new Vector3(x, y, 0);
    }

    public static float CameraPixel(final OrthographicCamera pCamera){
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        float CameraPixel;

        float targetRatio = (float)width/(float)height;
        float sourceRatio = pCamera.viewportWidth/pCamera.viewportHeight;

        if(targetRatio > sourceRatio)
            CameraPixel = height/pCamera.viewportHeight;
        else
            CameraPixel = width/pCamera.viewportWidth;

        return (int)CameraPixel;
    }

    private static GlyphLayout layout = new GlyphLayout();

    public static Vector3 getWidthAndHeightOfString(BitmapFont font, String s){
        layout.setText(font, s);
        float width = layout.width;
        float height = layout.height;
        return new Vector3(width, height, 0);
    }

}
