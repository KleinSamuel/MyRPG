package com.kleinsamuel.game.model.animations;

import com.badlogic.gdx.graphics.Texture;
import com.kleinsamuel.game.model.Assets;
import com.kleinsamuel.game.sprites.SpriteSheet;

/**
 * Created by sam on 04.06.17.
 */

public class AnimationFactory {

    /**
     * Get SpriteSheet for AnimationEnum
     *
     * @param animEnum
     * @return
     */
    public static SpriteSheet getSpriteSheet(AnimationEnum animEnum){
        if(animEnum.equals(AnimationEnum.SLASH_SINGLE)){
            return new SpriteSheet(Assets.manager.get(Assets.slash_single, Texture.class), 1, 3);
        }else if(animEnum.equals(AnimationEnum.SLASH_TRIPLE)){
            return new SpriteSheet(Assets.manager.get(Assets.slash_triple, Texture.class), 1, 3);
        }else if(animEnum.equals(AnimationEnum.SLASH_CURVE_HALF)){
            return new SpriteSheet(Assets.manager.get(Assets.slash_curve_half, Texture.class), 1, 3);
        }else if(animEnum.equals(AnimationEnum.SLASH_CURVE_FULL)){
            return new SpriteSheet(Assets.manager.get(Assets.slash_curve_full, Texture.class), 1, 3);
        }else if(animEnum.equals(AnimationEnum.SLASH_X_SYMMETRICAL)){
            return new SpriteSheet(Assets.manager.get(Assets.slash_x_symmetrical, Texture.class), 1, 3);
        }else if(animEnum.equals(AnimationEnum.SLASH_X_ASYMMETRICAL)){
            return new SpriteSheet(Assets.manager.get(Assets.slash_x_asymmetrical, Texture.class), 1, 3);
        }
        return null;
    }

}
