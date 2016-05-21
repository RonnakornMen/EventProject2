package sut.game01.core.gauge;


import static playn.core.PlayN.*;
import playn.core.Image;
import playn.core.*;
import playn.core.Keyboard;
import playn.core.ImageLayer;
import playn.core.ImageLayer;
import playn.core.Mouse;
import playn.core.PlayN;
import sut.game01.core.GameScreen;
import tripleplay.game.Screen;
import react.UnitSlot;
import tripleplay.game.UIScreen;
import tripleplay.game.ScreenStack;
import tripleplay.ui.*;
import tripleplay.ui.layout.AxisLayout;
import static playn.core.PlayN.graphics;
import sut.game01.core.sprite.*;
import playn.core.util.*;




public class Gauge   {
    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    static int num;
    static int num2;

    public enum State {
        IDLE
    };
    private State state = State.IDLE;

    private int e = 0;
    private int offset = 0;



    public Gauge(final float x, final float y){
        /*PlayN.keyboard().setListener(new Keyboard.Adapter(){
            @Override
            public void onKeyDown(Keyboard.Event event){
               if (event.key() == Key.ENTER) {
                    switch (state){
                        System.out.println("ss");
                    }
                }
            }
        });*/


        sprite = SpriteLoader.getSprite("images/gauge/gauge.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width() / 2f,
                        sprite.height() / 2f);
                sprite.layer().setTranslation(x, y);
                hasLoaded = true;
            }

            @Override
            public void onFailure(Throwable cause) {
                PlayN.log().error("Error loading image!", cause);
            }

        });

    }
    public Layer layer() {
        return sprite.layer();
    }

    public static void power(int n){
        num = n;

    }

    public void update(int delta) {
        if (hasLoaded == false) return;
        e = e +delta;
        if (e > 150) {
            /*switch(state){
                case IDLE: offset =0; break;
            
            }*/
            if(num==-99){
               // System.out.println(spriteIndex);
                num2 = spriteIndex;
                spriteIndex =0;
                num=0;
                //System.out.println(num2);
                GameScreen.powerMethod(num2);
            }

            spriteIndex = offset + ((spriteIndex +1 ) %11);
            sprite.setSprite(spriteIndex);
            e = 0;

            //System.out.println(spriteIndex);
        }

    }


}
