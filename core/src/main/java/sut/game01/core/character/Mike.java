package sut.game01.core.character;


import static playn.core.PlayN.*;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.Image;
import playn.core.*;
import playn.core.Keyboard;
import playn.core.ImageLayer;
import playn.core.ImageLayer;
import playn.core.Mouse;
import playn.core.PlayN;
import sut.game01.core.GameScreen;
import sut.game01.core.HomeScreen;
import sut.game01.core.gauge.Gauge;
import tripleplay.game.Screen;
import react.UnitSlot;
import tripleplay.game.UIScreen;
import tripleplay.game.ScreenStack;
import tripleplay.ui.*;
import tripleplay.ui.layout.AxisLayout;
import static playn.core.PlayN.graphics;
import sut.game01.core.sprite.*;
import playn.core.util.*;




public class Mike   {
    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    //private float x= 60;
    public static int action = 0;
    private int a =0;
    Gauge gauge;

    private  float diffY , diffX;
    private double angle;
    private World world;
    private Body body;
    public enum State {
        IDLE, WALK, THROW, BACK
    };

    public static State state = State.IDLE;

    private int e = 0;
    private int offset = 0;

    public Mike(final World world,final float x_px, final float y_px){
       /* PlayN.keyboard().setListener(new Keyboard.Adapter(){
            @Override
            public void onKeyDown(Keyboard.Event event){
                if (event.key() == Key.RIGHT) {
                    action = 1;
                    switch (state){
                        case IDLE: if(action ==1){state = State.WALK;} break;
                        //case WALK: state = State.THROW; break;
                        case THROW: state = State.WALK; break;

                    }
                    //GameScreen.recivePosition(x_px,y_px);

                }
                else if (event.key() == Key.LEFT) {
                    action = 1;
                    switch (state){
                        case IDLE: if(action ==1){state = State.BACK;} break;
                        //case WALK: state = State.THROW; break;
                        case THROW: state = State.WALK; break;
                    }
                   // GameScreen.recivePosition(x_px,y_px);
                }
                else if (event.key() == Key.SPACE) {
                    switch (state){
                        //case IDLE: state = State.WALK; break;
                        case WALK: state = State.IDLE; break;
                        case THROW: state = State.IDLE; break;
                        //Gauge g = new Gauge(10f, 10f);
                    }
                }

                else if (event.key() == Key.ENTER) {
                    switch (state){
                        case IDLE: state = State.THROW; break;
                        case WALK: state = State.THROW; break;
                        //case THROW: state = State.IDLE; break;

                    }

                    Gauge.power(-99);


                }


            }


            public void onKeyUp(Keyboard.Event event){
                if (event.key() == Key.RIGHT) {
                    action = 0;
                    if(action ==0&& state == State.WALK){
                        state = State.IDLE;
                    }

                }
                if (event.key() == Key.LEFT) {
                    action = 0;
                    if(action ==0&& state == State.BACK){
                        state = State.IDLE;
                    }

                }
            }
        });*/

        sprite = SpriteLoader.getSprite("images/mike2.json");
        sprite.addCallback(new Callback<Sprite>(){
            @Override
            public void onSuccess(Sprite result){
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width() /2f,
                        sprite.height() /2f);
                //sprite.layer().setTranslation(300, 225);
                body = initPhysicsBody(world,
                        GameScreen.M_PER_PIXEL * x_px,
                        GameScreen.M_PER_PIXEL * y_px);
                hasLoaded = true;
            }
            @Override
            public void onFailure(Throwable cause){
                PlayN.log().error("Error loading image!", cause);
            }

        });
        PlayN.mouse().setListener(new Mouse.Adapter(){
            @Override
            public void onMouseMove(Mouse.MotionEvent event) {
                layerAngleUpdate(event.x() , event.y());
            }

            private void layerAngleUpdate(float x1, float y1) {
                diffY = Math.abs(x1 - x_px) ;
                diffX = Math.abs(y1 - y_px);
                angle = Math.toDegrees(Math.atan(diffY / diffX));
                if (angle < 54f){
                    angle = 54f;
                }else if (angle > 84f){
                    angle = 84f;
                }
                //System.out.println("X :" + x1 + "   Y :" + y1 + "Degree " + angle);


                // System.out.println(angle);
            }
        });
    }
    private Body initPhysicsBody(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position = new Vec2(0, 0);//แปลง pixel ให้เป็น m คือ เอา pixel ไปคูณ กับค่าคงที่
        body = world.createBody(bodyDef);
        //bodyDef.active = new Boolean(true);


        //PolygonShape shape = new PolygonShape();
        /*CircleShape shape = new CircleShape();
        shape.setRadius(0.7f);*/
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(56 * GameScreen.M_PER_PIXEL / 2,
                sprite.layer().height() * GameScreen.M_PER_PIXEL / 2);

        FixtureDef fixtureDef = new FixtureDef();//น้ำหนัก
        fixtureDef.shape = shape;
        fixtureDef.density = 0.4f;
        fixtureDef.friction = 0.1f;
        fixtureDef.restitution = 0.35f;
        body.setFixedRotation(true);
        body.createFixture(fixtureDef);
        body.setLinearDamping(0.2f);
        body.setTransform(new Vec2(x, y), 0f);

        return body;
    }
    public Layer layer() {
        return sprite.layer();
    }

    public void update(int delta) {
        if (hasLoaded == false) return;
        //System.out.println(action);
        e = e +delta;
        if (e > 150) {
            switch(state){
                case IDLE: offset =0; break;
                case WALK: offset =4; break;
                case BACK: offset =9; break;

                case THROW: offset =10;
                    if (spriteIndex ==12) {
                        state = State.IDLE;
                    } break;

            }
            if(state == State.IDLE){
                spriteIndex = offset + ((spriteIndex +1 ) %4);
            }
            else if(state == State.WALK){
                spriteIndex = offset + ((spriteIndex +1 ) %6);
            }
            else if(state == State.BACK){
                spriteIndex = offset - (a++ %6);
            }
            else if(state == State.THROW){
                spriteIndex = offset + ((spriteIndex +1 ) %3);
            }
            sprite.setSprite(spriteIndex);
            e = 0;
        }
        //sprite.layer().setTranslation(60 , 400);
        if(state == State.WALK){
            body.applyForce(new Vec2(10f, 0),body.getPosition());

        }
        else if (state == State.BACK){
            body.applyForce(new Vec2(-10f, 0),body.getPosition());

        }
        //sprite.layer().setTranslation(x , 400);
    }

    public void paint(Clock clock) {
        if(hasLoaded == false)return;


        sprite.layer().setTranslation(
                (body.getPosition().x / GameScreen.M_PER_PIXEL) +10,
                body.getPosition().y / GameScreen.M_PER_PIXEL);
        //sprite.layer().setRotation(( (float) angle / 30f) - 2.9f);
        GameScreen.recivePosition(body.getPosition().x / GameScreen.M_PER_PIXEL + 10, body.getPosition().y / GameScreen.M_PER_PIXEL);
    }
    public String getInfo(){
        return String.valueOf(layer().rotation());

    }

    public Body getBody() {
        return body;
    }
}
