package sut.game01.core;


import static playn.core.PlayN.*;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.*;
import playn.core.ImageLayer;

import playn.core.util.Clock;
import sut.game01.core.bin.BlueBin;
import sut.game01.core.bin.GreenBin;
import sut.game01.core.trash.BottleGlass;
import sut.game01.core.trash.Can;
import sut.game01.core.trash.Trash;
import sut.game01.core.bin.YellowBin;
import tripleplay.game.Screen;
import react.UnitSlot;
import tripleplay.game.UIScreen;
import tripleplay.game.ScreenStack;
import tripleplay.ui.*;
import tripleplay.ui.layout.AxisLayout;
import java.lang.String;
import static playn.core.PlayN.graphics;

import sut.game01.core.character.Mike;
import sut.game01.core.gauge.Gauge;
import sut.game01.core.trash.Trash;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class GameScreen extends Screen {

    private final ScreenStack ss;
    private final SettingScreen settingScreen;
    private final OverScreen overScreen;
    private final EndScreen endScreen;
    private final ImageLayer bg;
    private final ImageLayer backButton;
    private final ImageLayer settingButton;
    private final ImageLayer pauseButton;
    private final ImageLayer overButton;
    private final ImageLayer endButton;
    private final ImageLayer cloud;
    private final ImageLayer blueBin;
    private final ImageLayer yellowBin;
    private final ImageLayer greenBin;
    private Image bgImage;
    private Image cloudImage;
    private float xC = 24.0f;
    private float yC = 100;

    private final ImageLayer wall;
    private Mike mike;
    private Gauge gauge;
    /*private BlueBin blueBin;
    private GreenBin greenBin;
    private YellowBin yellowBin;*/
    private Trash trash;
    private final ImageLayer mario;
    static int numTrash = 0;
    private int b = 0;
    private float yM = 395;
    private Root root;
    static float xMike2;
    static float yMike2;
    static float power;
    ArrayList<Trash> t = new ArrayList<Trash>();
    int t1 = 0;
    ArrayList<Can> can = new ArrayList<Can>();
    int canNum = 0;
    ArrayList<BottleGlass> bottleGlass = new ArrayList<BottleGlass>();
    int bottleGlassNum=0;
    public static HashMap<Object, String> bodies = new HashMap<Object, String>();
    public static HashMap<String, Body> bodiesGround = new HashMap<String, Body>();
    private String debugString = String.valueOf(bodies);

    public static float M_PER_PIXEL = 1 / 26.666667f;
    private static int width = 24;
    private static int height = 18;
    private float power3;

    private World world;
    private boolean showDebugDraw = false;
    private DebugDrawBox2D debugDraw;


    public GameScreen(final ScreenStack ss) {
        this.ss = ss;
        this.settingScreen = new SettingScreen(ss);
        this.overScreen = new OverScreen(ss);
        this.endScreen = new EndScreen(ss);


        bgImage = assets().getImage("images/bg.png");
        this.bg = graphics().createImageLayer(bgImage);

        //====================================================================backButton
        Image backImage = assets().getImage("images/back.png");
        this.backButton = graphics().createImageLayer(backImage);
        backButton.setTranslation(10, 10);

        backButton.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                ss.remove(ss.top()); // pop screen
            }
        });
        //====================================================================settingButton
        Image settingImage = assets().getImage("images/setting.png");
        this.settingButton = graphics().createImageLayer(settingImage);
        settingButton.setTranslation(200, 10);

        settingButton.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                ss.push(settingScreen);
            }
        });
        //=============================================================================pauseButton=====
        Image pauseImage = assets().getImage("images/pauseButton.png");
        this.pauseButton = graphics().createImageLayer(pauseImage);
        pauseButton.setTranslation(270, 0);

        pauseButton.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                // ss.push(overScreen);
            }
        });

        //==================================================================================over
        Image overImage = assets().getImage("images/overButton.png");
        this.overButton = graphics().createImageLayer(overImage);
        overButton.setTranslation(40, 0);

        overButton.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                ss.push(overScreen);
            }
        });
        //==================================================================================end
        Image endImage = assets().getImage("images/endButton.png");
        this.endButton = graphics().createImageLayer(endImage);
        endButton.setTranslation(540, 0);

        endButton.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                ss.push(endScreen);
            }
        });
        //==========================================================================clound
        cloudImage = assets().getImage("images/cloud.png");
        cloud = graphics().createImageLayer(cloudImage);
        graphics().rootLayer().add(cloud);
        cloud.setTranslation(0, 105);


        //===========================================================================wall
        Image wallImage = assets().getImage("images/wall.png");
        this.wall = graphics().createImageLayer(wallImage);
        wall.setTranslation(280, 330);

        //===========================================================================bluebin
        Image blueBinImage = assets().getImage("images/bin/blueBin.png");
        this.blueBin = graphics().createImageLayer(blueBinImage);
        blueBin.setTranslation(380, 395);
        //===========================================================================yellowbin
        Image yellowBinImage = assets().getImage("images/bin/yellowBin.png");
        this.yellowBin = graphics().createImageLayer(yellowBinImage);
        yellowBin.setTranslation(480, 395);
        //===========================================================================greenbin
        Image greenBinImage = assets().getImage("images/bin/greenBin.png");
        this.greenBin = graphics().createImageLayer(greenBinImage);
        greenBin.setTranslation(580, 395);


        //==================================================================mario
        Image marioImage = assets().getImage("images/mario.png");
        this.mario = graphics().createImageLayer(marioImage);
        mario.setTranslation(360, yM);
        //gaugeShow();

        Vec2 gravity = new Vec2(0.0f, 10.0f);
        world = new World(gravity);
        world.setWarmStarting(true);
        world.setAutoClearForces(true);
        mike = new Mike(world, 100f, 480f);
        gauge = new Gauge(10f, 10f);
    }


    //=============================================================
    @Override
    public void wasShown() {
        super.wasShown();
        this.layer.add(bg);
        //this.layer.add(backButton);
        //this.layer.add(settingButton);
        //this.layer.add(mario);
        this.layer.add(pauseButton);
        this.layer.add(overButton);
        this.layer.add(endButton);


        this.layer.add(blueBin);
        this.layer.add(yellowBin);
        this.layer.add(greenBin);
        mike.getBody().setTransform(new Vec2(100f*M_PER_PIXEL,480f*M_PER_PIXEL),0);
        //mike = new Mike(world, 100f, 480f);
        this.layer.add(mike.layer());
        bodies.put(mike, "mike");
        this.layer.add(cloud);


        /*blueBin = new BlueBin(world, 400f, 480f);
        this.layer.add(blueBin.layer());
        yellowBin = new YellowBin(world, 500f, 480f);
        this.layer.add(yellowBin.layer());
        greenBin = new GreenBin(world, 600f, 480f);
        this.layer.add(greenBin.layer());*/


        t.add(t1, new Trash(world, -100f, 480f));
        layer.add(t.get(t1).layer());
        can.add(canNum, new Can(world, -100f, 480f));
        layer.add(can.get(canNum).layer());
        bottleGlass.add(bottleGlassNum, new BottleGlass(world, -100f, 480f));
        layer.add(bottleGlass.get(bottleGlassNum).layer());



        this.layer.add(wall);
        //gauge = new Gauge(10f, 10f);
        this.layer.add(gauge.layer());

        if (showDebugDraw) {
            CanvasImage image = graphics().createImage(
                    (int) (width / M_PER_PIXEL),
                    (int) (height / M_PER_PIXEL)
            );
            layer.add(graphics().createImageLayer(image));
            debugDraw = new DebugDrawBox2D();
            debugDraw.setCanvas(image);
            debugDraw.setFlipY(false);
            debugDraw.setStrokeAlpha(150);
            debugDraw.setFillAlpha(75);
            debugDraw.setStrokeWidth(2.0f);
            debugDraw.setFlags(DebugDraw.e_shapeBit |
                    DebugDraw.e_jointBit |
                    DebugDraw.e_aabbBit);

            debugDraw.setCamera(0, 0, 1f / M_PER_PIXEL);
            world.setDebugDraw(debugDraw);
        }

        Body ground = world.createBody(new BodyDef());
        EdgeShape groundShape = new EdgeShape();
        groundShape.set(new Vec2(0, height), new Vec2(width, height));
        ground.createFixture(groundShape, 0.0f);
        bodiesGround.put("ground", ground);

        Body ground2 = world.createBody(new BodyDef());
        EdgeShape groundShape2 = new EdgeShape();
        groundShape2.set(new Vec2(width, 0), new Vec2(width, height));
        ground2.createFixture(groundShape2, 0.0f);

        Body ground3 = world.createBody(new BodyDef());
        EdgeShape groundShape3 = new EdgeShape();
        groundShape3.set(new Vec2(0, height), new Vec2(0, 0));
        ground3.createFixture(groundShape3, 0.0f);

        //================================================static wall
       /* Body groundWall1 = world.createBody(new BodyDef());
        EdgeShape groundShapeWall1 = new EdgeShape();
        groundShapeWall1.set(new Vec2(290f*M_PER_PIXEL, 440f*M_PER_PIXEL), new Vec2(290f*M_PER_PIXEL, 330f*M_PER_PIXEL));
        groundWall1.createFixture(groundShapeWall1, 0.0f);

        Body groundWall2 = world.createBody(new BodyDef());
        EdgeShape groundShapeWall2 = new EdgeShape();
        groundShapeWall2.set(new Vec2(317f*M_PER_PIXEL, 480f*M_PER_PIXEL), new Vec2(317f*M_PER_PIXEL, 390f*M_PER_PIXEL));
        groundWall2.createFixture(groundShapeWall2, 0.0f);*/

        Body groundWall3 = world.createBody(new BodyDef());
        EdgeShape groundShapeWall3 = new EdgeShape();
        groundShapeWall3.set(new Vec2(293f*M_PER_PIXEL, 336f*M_PER_PIXEL), new Vec2(313f*M_PER_PIXEL, 480f*M_PER_PIXEL));
        groundWall3.createFixture(groundShapeWall3, 0.0f);
        //===========================================================static bluebin
        Body bluebinWallLeft = world.createBody(new BodyDef());
        EdgeShape bluebinShapeWallLeft = new EdgeShape();
        bluebinShapeWallLeft.set(new Vec2(382f*M_PER_PIXEL, 480f*M_PER_PIXEL), new Vec2(382f*M_PER_PIXEL, 395f*M_PER_PIXEL));
        bluebinWallLeft.createFixture(bluebinShapeWallLeft, 0.0f);

        Body bluebinWallRight = world.createBody(new BodyDef());
        EdgeShape bluebinShapeWallRight = new EdgeShape();
        bluebinShapeWallRight.set(new Vec2(438f*M_PER_PIXEL, 480f*M_PER_PIXEL), new Vec2(438f*M_PER_PIXEL, 395f*M_PER_PIXEL));
        bluebinWallRight.createFixture(bluebinShapeWallRight, 0.0f);

        Body blueBincheck = world.createBody(new BodyDef());
        final CircleShape shape = new CircleShape();
        shape.setRadius(0.3f);
        shape.m_p.set(410f*M_PER_PIXEL,460f*M_PER_PIXEL);
        /*EdgeShape coinShape = new EdgeShape();
        groundShape.set(new Vec2(0, height), new Vec2(width, height));*/
        blueBincheck.createFixture(shape, 0.0f);

        //===========================================================static yellowbin
        Body yellowWallLeft = world.createBody(new BodyDef());
        EdgeShape yellowShapeWallLeft = new EdgeShape();
        yellowShapeWallLeft.set(new Vec2(482f*M_PER_PIXEL, 480f*M_PER_PIXEL), new Vec2(482f*M_PER_PIXEL, 395f*M_PER_PIXEL));
        yellowWallLeft.createFixture(yellowShapeWallLeft, 0.0f);

        Body yellowWallRight = world.createBody(new BodyDef());
        EdgeShape yellowShapeWallRight = new EdgeShape();
        yellowShapeWallRight.set(new Vec2(538f*M_PER_PIXEL, 480f*M_PER_PIXEL), new Vec2(538f*M_PER_PIXEL, 395f*M_PER_PIXEL));
        yellowWallRight.createFixture(yellowShapeWallRight, 0.0f);

        Body yellowBincheck = world.createBody(new BodyDef());
        final CircleShape shape2 = new CircleShape();
        shape2.setRadius(0.3f);
        shape2.m_p.set(510f*M_PER_PIXEL,460f*M_PER_PIXEL);
        /*EdgeShape coinShape = new EdgeShape();
        groundShape.set(new Vec2(0, height), new Vec2(width, height));*/
        yellowBincheck.createFixture(shape2, 0.0f);
        //===========================================================static greenbin
        Body greenbinWallLeft = world.createBody(new BodyDef());
        EdgeShape greenbinShapeWallLeft = new EdgeShape();
        greenbinShapeWallLeft.set(new Vec2(582f*M_PER_PIXEL, 480f*M_PER_PIXEL), new Vec2(582f*M_PER_PIXEL, 395f*M_PER_PIXEL));
        greenbinWallLeft.createFixture(greenbinShapeWallLeft, 0.0f);

        Body greenbinWallRight = world.createBody(new BodyDef());
        EdgeShape greenbinShapeWallRight = new EdgeShape();
        greenbinShapeWallRight.set(new Vec2(638f*M_PER_PIXEL, 480f*M_PER_PIXEL), new Vec2(638f*M_PER_PIXEL, 395f*M_PER_PIXEL));
        greenbinWallRight.createFixture(greenbinShapeWallRight, 0.0f);

        Body greenBincheck = world.createBody(new BodyDef());
        final CircleShape shape3 = new CircleShape();
        shape3.setRadius(0.3f);
        shape3.m_p.set(610f*M_PER_PIXEL,460f*M_PER_PIXEL);
        /*EdgeShape coinShape = new EdgeShape();
        groundShape.set(new Vec2(0, height), new Vec2(width, height));*/
        greenBincheck.createFixture(shape3, 0.0f);

      /*Body ground4 = world.createBody(new BodyDef());
      EdgeShape groundShape4 = new EdgeShape();
      groundShape4.set(new Vec2(width, height), new Vec2(width, 0));
      ground4.createFixture(groundShape4, 0.0f);*/
      /*mouse().setListener(new Mouse.Adapter() {
          @Override
          public void onMouseDown(Mouse.ButtonEvent event) {
              t.add(t1, new Trash(world, xMike2, yMike2-90));
              layer.add(t.get(t1).layer());
              t1++;
              t2++;
          }

      });*/
     throwTrash();


    }

    @Override
    public void update(int delta) {
        super.update(delta);
        mike.update(delta);
        gauge.update(delta);
        /*blueBin.update(delta);
        yellowBin.update(delta);
        greenBin.update(delta);*/
        move();


        world.step(0.033f, 10, 10);
        //=========================================moveCloud
        xC += 0.5f * delta / 8;
        if (xC > bgImage.width() + cloudImage.width()) {
            xC = -cloudImage.width();
        }
        cloud.setTranslation(xC, yC);


        if (yM > 320 && b == 0) {
            yM -= 0.5f * delta / 20;
        } else if (yM >= 320) {
            b = 1;
            yM += 0.5f * delta / 20;
            if (yM == 395)
                b = 0;
        }
        mario.setTranslation(360, yM);

        for (int k = 0; k <= t1; k++) {
            t.get(k).update(delta);
        }

        for (int k2 = 0; k2 <= canNum; k2++) {
            can.get(k2).update(delta);
        }

        for (int k3 = 0; k3 <= bottleGlassNum; k3++) {
            bottleGlass.get(k3).update(delta);
        }


    }

    @Override
    public void paint(Clock clock) {
        super.paint(clock);
        mike.paint(clock);
       /* blueBin.paint(clock);
        yellowBin.paint(clock);
        greenBin.paint(clock);*/

        for (int k = 0; k <= t1; k++)
            t.get(k).paint(clock);

        for (int k2 = 0; k2 <= canNum; k2++)
            can.get(k2).paint(clock);

        for (int k3 = 0; k3 <= bottleGlassNum; k3++)
            bottleGlass.get(k3).paint(clock);


        if (showDebugDraw) {
            debugDraw.getCanvas().clear();
            debugDraw.getCanvas().setFillColor(Color.rgb(255, 255, 255));
            // debugDraw.getCanvas().drawText(debugString, 100,100);
            world.drawDebugData();
        }

    }

    public void move() {
        PlayN.keyboard().setListener(new Keyboard.Adapter() {
            @Override
            public void onKeyDown(Keyboard.Event event) {
                if (event.key() == Key.RIGHT) {
                    Mike.action = 1;
                    switch (Mike.state) {
                        case IDLE:
                            if (Mike.action == 1) {
                                Mike.state = Mike.State.WALK;
                            }
                            break;
                        //case WALK: state = State.THROW; break;
                        case THROW:
                            Mike.state = Mike.State.WALK;
                            break;

                    }
                    //GameScreen.recivePosition(x_px,y_px);

                } else if (event.key() == Key.LEFT) {
                    Mike.action = 1;
                    switch (Mike.state) {
                        case IDLE:
                            if (Mike.action == 1) {
                                Mike.state = Mike.State.BACK;
                            }
                            break;
                        //case WALK: state = State.THROW; break;
                        case THROW:
                            Mike.state = Mike.State.WALK;
                            break;
                    }
                    // GameScreen.recivePosition(x_px,y_px);
                } else if (event.key() == Key.ENTER) {
                    switch (Mike.state) {
                        //case IDLE: state = State.WALK; break;
                        case WALK:
                            Mike.state = Mike.State.IDLE;
                            break;
                        case THROW:
                            Mike.state = Mike.State.IDLE;
                            break;
                        //Gauge g = new Gauge(10f, 10f);
                    }
                } else if (event.key() == Key.SPACE) {
                    switch (Mike.state) {
                        case IDLE:
                            Mike.state = Mike.State.THROW;
                            break;
                        case WALK:
                            Mike.state = Mike.State.THROW;
                            break;
                        //case THROW: state = State.IDLE; break;


                    }

                    Gauge.power(-99);
                    Random rand = new Random();
                    int nRand = rand.nextInt(3) +1;

                    if(nRand ==1)
                        createTrash(t1);
                    else if(nRand ==2)
                        createCan(canNum);
                    else if(nRand ==3)
                        createBottleGlass(bottleGlassNum);


                }


            }


            public void onKeyUp(Keyboard.Event event) {
                if (event.key() == Key.RIGHT) {
                    Mike.action = 0;
                    if (Mike.action == 0 && Mike.state == Mike.State.WALK) {
                        Mike.state = Mike.State.IDLE;
                    }

                }
                if (event.key() == Key.LEFT) {
                    Mike.action = 0;
                    if (Mike.action == 0 && Mike.state == Mike.State.BACK) {
                        Mike.state = Mike.State.IDLE;
                    }

                }
            }
        });
    }

    public static void recivePosition(float xMike, float yMike) {
        xMike2 = xMike;
        yMike2 = yMike;

    }

    public void createTrash(int trashTotal) {
        this.t1 = trashTotal;
        t.add(t1, new Trash(world, xMike2 + 25, yMike2 - 30));
        bodies.put(t, "Trash" + t1);
        layer.add(t.get(t1).layer());
        t.get(t1).hasThrow(1);
        t1++;



    }
    public void createCan(int canNum2){
        this.canNum = canNum2;
        can.add(canNum, new Can(world, xMike2 + 30, yMike2 - 70));
        bodies.put(can, "Can " + canNum);
        layer.add(can.get(canNum).layer());
        can.get(canNum).hasThrow(1);
        canNum++;
    }
    public void createBottleGlass(int bottleGlassNum2){
        this.bottleGlassNum = bottleGlassNum2;
        bottleGlass.add(bottleGlassNum, new BottleGlass(world, xMike2 + 30, yMike2 - 70));
        bodies.put(bottleGlass, "Trash " + bottleGlassNum);
        layer.add(bottleGlass.get(bottleGlassNum).layer());
        bottleGlass.get(bottleGlassNum).hasThrow(1);
        bottleGlassNum++;
    }

    public void throwTrash(){
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                //System.out.println("p in contact  " +power3 );
                Body a = contact.getFixtureA().getBody();
                Body b = contact.getFixtureB().getBody();
                //System.out.println(bodies.get(mike));
                //System.out.println(bodies.get(b));
                if ((contact.getFixtureA().getBody() ==  mike.getBody())) /*|| bodies.get(b) == "mike") || (bodies.get(b).charAt(0) == 'T' && bodies.get(a) == "mike")*/ {
                    //System.out.println(power);
                    b.applyForce(new Vec2(power, -150f), b.getPosition());
                    for(Body b1:bodiesGround.values()){
                        if(b1 == b)
                            System.out.println("contact ground");
                    }
                    //b.applyLinearImpulse(new Vec2(power, -0), b.getPosition());

                }
                if ((contact.getFixtureB().getBody() ==  mike.getBody())) /*|| bodies.get(b) == "mike") || (bodies.get(b).charAt(0) == 'T' && bodies.get(a) == "mike")*/ {
                    //System.out.println(power);
                    a.applyForce(new Vec2(power, -150f), b.getPosition());
                    for(Body b1:bodiesGround.values()){
                        if(b1 == a)
                            System.out.println("contact ground");
                    }
                    //b.applyLinearImpulse(new Vec2(power, -0), b.getPosition());

                }


            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold manifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse contactImpulse) {

            }
        });
    }
    public static void powerMethod(int power2){

        if(power2 == 0)
            power = 0;
        else if(power2 ==1)
            power = 50f;
        else if(power2 ==2)
            power = 100f;
        else if(power2 ==3)
            power = 150f;
        else if(power2 ==4)
            power = 200f;
        else if(power2 ==5)
            power = 250f;
        else if(power2 ==6)
            power = 300f;
        else if(power2 ==7)
            power = 350f;
        else if(power2 ==8)
            power = 400f;
        else if(power2 ==9)
            power = 500f;
        else if(power2 ==10)
            power = 550;

       //System.out.println("p" +power );
    }


}
