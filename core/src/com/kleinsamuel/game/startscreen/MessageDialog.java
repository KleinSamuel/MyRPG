package com.kleinsamuel.game.startscreen;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by sam on 01.06.17.
 */

public class MessageDialog extends Dialog {

    public MessageDialog(String title, Skin skin) {
        super(title, skin);

        padTop(getHeight()*0.4f);
        padLeft(getHeight()*0.2f);
        padRight(getHeight()*0.2f);
        padBottom(getHeight()*0.2f);
        button("OK", true);

    }

}
