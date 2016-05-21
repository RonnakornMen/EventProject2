package sut.game01.core;


import static playn.core.PlayN.*;

import javafx.event.ActionEvent;
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
import java.lang.IndexOutOfBoundsException;
import playn.core.util.Clock;
import sut.game01.core.bin.BlueBin;
import sut.game01.core.bin.GreenBin;
import sut.game01.core.trash.*;
import sut.game01.core.bin.YellowBin;
import tripleplay.game.Screen;
import react.UnitSlot;
import tripleplay.game.UIScreen;
import tripleplay.game.ScreenStack;
import tripleplay.ui.*;
import tripleplay.ui.layout.AxisLayout;
import javax.swing.Timer;
import java.lang.String;
import java.awt.event.ActionListener;

import static playn.core.PlayN.graphics;

import sut.game01.core.character.Mike;
import sut.game01.core.gauge.Gauge;
import sut.game01.core.trash.Trash;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


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
    private final  ImageLayer scoreWindows;
    private final ImageLayer nextWindows;
    private final ImageLayer clock;
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
    ArrayList<Trash> tRemove =new ArrayList<Trash>();
    ArrayList<Can> canRemove =new ArrayList<Can>();
    ArrayList<BottleGlass> bottleGlassRemove =new ArrayList<BottleGlass>();
    ArrayList<PlasticBottle> plasticBottleRemove =new ArrayList<PlasticBottle>();
    ArrayList<Book> bookRemove =new ArrayList<Book>();
    ArrayList<PlasticGlass> plasticGlassRemove =new ArrayList<PlasticGlass>();
    ArrayList<Trash> t = new ArrayList<Trash>();
    int t1 = 0;
    ArrayList<Can> can = new ArrayList<Can>();
    int canNum = 0;
    ArrayList<BottleGlass> bottleGlass = new ArrayList<BottleGlass>();
    int bottleGlassNum = 0;
    ArrayList<PlasticBottle> plasticBottle = new ArrayList<PlasticBottle>();
    int plasticBottleNum =0;
    ArrayList<Book> book = new ArrayList<Book>();
    int bookNum =0;
    ArrayList<PlasticGlass> plasticGlass = new ArrayList<PlasticGlass>();
    int plasticGlassNum =0;
    int trashNum = 0;
    int timeI = 0;
    int bottleGlass3 = 0;

    private boolean destroy = false;

    ArrayList<Object> tR = new ArrayList<Object>();
    int tRN = 0;

    //public static Map<Object, String> bodies0 = new HashMap<Object, String>();
     public static HashMap<Object, String> bodies = new HashMap<Object, String>();
    //Map<Object, String> bodies = Collections.synchronizedMap(bodies0);
    public static HashMap<String, Body> bodiesGround = new HashMap<String, Body>();

    /*public static HashMap<String, Body> bodiesBluebin = new HashMap<String, Body>();
    public static HashMap<String, Body> bodiesYellowbin = new HashMap<String, Body>();
    public static HashMap<String, Body> bodiesGreenbin = new HashMap<String, Body>();*/
    private String debugString = String.valueOf(bodies);
    private String strScore;
    private String strTime;
    int score =0;
    int time =60;
    int time2;
    int targetScore =100;

    public static float M_PER_PIXEL = 1 / 26.666667f;
    private static int width = 24;
    private static int height = 18;
    private float power3;
    int next = 0;
    private String nextString;
    private World world;
    private boolean showDebugDraw = true;
    private DebugDrawBox2D debugDraw;

    Random rand = new Random();
    int nRand = rand.nextInt(6) + 1;



    public GameScreen(final ScreenStack ss) {
        this.ss = ss;
        this.settingScreen = new SettingScreen(ss);
        this.overScreen = new OverScreen(ss);
        this.endScreen = new EndScreen(ss);




        if (nRand == 1)
            nextString = "Paper";
        else if (nRand == 2)
            //nextString = "Can";
             nextString = "Plastic Bottle";
        else if (nRand == 3)
            nextString = "Glass Bottle";
        else if (nRand == 4)
            nextString = "Can";
        else if (nRand == 5)
            nextString = "Book";
        else if (nRand == 6)
            nextString = "Plastic Glass";

        debugString = "Next is " + nextString;

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
        //===========================================================================scoreWindows
        Image scoreWindowsImage = assets().getImage("images/scoreWindows.png");
        this.scoreWindows = graphics().createImageLayer(scoreWindowsImage);
        scoreWindows.setTranslation(480, 20);
        //===========================================================================clock
        Image clockImage = assets().getImage("images/clock.png");
        this.clock = graphics().createImageLayer(clockImage);
        clock.setTranslation(400, 20);
        //===========================================================================nextWindows
        Image nextWindowsImage = assets().getImage("images/scoreWindows.png");
        this.nextWindows = graphics().createImageLayer(nextWindowsImage);
        nextWindows.setTranslation(10, 30);


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
        gauge = new Gauge(25f, 180f);


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
        //this.layer.add(overButton);
       // this.layer.add(endButton);


        this.layer.add(blueBin);
        this.layer.add(yellowBin);
        this.layer.add(greenBin);
        this.layer.add(scoreWindows);
        this.layer.add(clock);
        this.layer.add(nextWindows);

        mike.getBody().setTransform(new Vec2(100f * M_PER_PIXEL, 480f * M_PER_PIXEL), 0);
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
        plasticBottle.add(plasticBottleNum, new PlasticBottle(world, -100f, 480f));
        layer.add(plasticBottle.get(plasticBottleNum).layer());
        book.add(bookNum, new Book(world, -100f, 480f));
        layer.add(book.get(bookNum).layer());
        plasticGlass.add(plasticGlassNum, new PlasticGlass(world, -100f, 480f));
        layer.add(plasticGlass.get(plasticGlassNum).layer());


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
           /* debugDraw.setFlags(DebugDraw.e_shapeBit |
                    DebugDraw.e_jointBit |
                    DebugDraw.e_aabbBit);*/

            debugDraw.setCamera(0, 0, 1f / M_PER_PIXEL);
            world.setDebugDraw(debugDraw);
        }

        Body ground = world.createBody(new BodyDef());
        EdgeShape groundShape = new EdgeShape();
        groundShape.set(new Vec2(0, height), new Vec2(width, height));
        ground.createFixture(groundShape, 0.0f);
        bodiesGround.put("ground", ground);
        bodies.put(ground, "ground2");

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
        groundShapeWall3.set(new Vec2(293f * M_PER_PIXEL, 336f * M_PER_PIXEL), new Vec2(313f * M_PER_PIXEL, 480f * M_PER_PIXEL));
        groundWall3.createFixture(groundShapeWall3, 0.0f);
        //===========================================================static bluebin
        Body bluebinWallLeft = world.createBody(new BodyDef());
        EdgeShape bluebinShapeWallLeft = new EdgeShape();
        bluebinShapeWallLeft.set(new Vec2(382f * M_PER_PIXEL, 480f * M_PER_PIXEL), new Vec2(382f * M_PER_PIXEL, 395f * M_PER_PIXEL));
        bluebinWallLeft.createFixture(bluebinShapeWallLeft, 0.0f);

        Body bluebinWallRight = world.createBody(new BodyDef());
        EdgeShape bluebinShapeWallRight = new EdgeShape();
        bluebinShapeWallRight.set(new Vec2(438f * M_PER_PIXEL, 480f * M_PER_PIXEL), new Vec2(438f * M_PER_PIXEL, 395f * M_PER_PIXEL));
        bluebinWallRight.createFixture(bluebinShapeWallRight, 0.0f);

        Body blueBincheck = world.createBody(new BodyDef());
        final CircleShape shape = new CircleShape();
        shape.setRadius(0.3f);
        shape.m_p.set(410f * M_PER_PIXEL, 460f * M_PER_PIXEL);
        /*EdgeShape coinShape = new EdgeShape();
        groundShape.set(new Vec2(0, height), new Vec2(width, height));*/
        blueBincheck.createFixture(shape, 0.0f);
        bodies.put(blueBincheck, "blueBin");
        //bodiesGround.put("blueBin", blueBincheck);

        //===========================================================static yellowbin
        Body yellowWallLeft = world.createBody(new BodyDef());
        EdgeShape yellowShapeWallLeft = new EdgeShape();
        yellowShapeWallLeft.set(new Vec2(482f * M_PER_PIXEL, 480f * M_PER_PIXEL), new Vec2(482f * M_PER_PIXEL, 395f * M_PER_PIXEL));
        yellowWallLeft.createFixture(yellowShapeWallLeft, 0.0f);

        Body yellowWallRight = world.createBody(new BodyDef());
        EdgeShape yellowShapeWallRight = new EdgeShape();
        yellowShapeWallRight.set(new Vec2(538f * M_PER_PIXEL, 480f * M_PER_PIXEL), new Vec2(538f * M_PER_PIXEL, 395f * M_PER_PIXEL));
        yellowWallRight.createFixture(yellowShapeWallRight, 0.0f);

        Body yellowBincheck = world.createBody(new BodyDef());
        final CircleShape shape2 = new CircleShape();
        shape2.setRadius(0.3f);
        shape2.m_p.set(510f * M_PER_PIXEL, 460f * M_PER_PIXEL);
        /*EdgeShape coinShape = new EdgeShape();
        groundShape.set(new Vec2(0, height), new Vec2(width, height));*/
        yellowBincheck.createFixture(shape2, 0.0f);
        bodies.put(yellowBincheck, "yellowBin");
        //bodiesGround.put("yellowBin", yellowBincheck);
        //===========================================================static greenbin
        Body greenbinWallLeft = world.createBody(new BodyDef());
        EdgeShape greenbinShapeWallLeft = new EdgeShape();
        greenbinShapeWallLeft.set(new Vec2(582f * M_PER_PIXEL, 480f * M_PER_PIXEL), new Vec2(582f * M_PER_PIXEL, 395f * M_PER_PIXEL));
        greenbinWallLeft.createFixture(greenbinShapeWallLeft, 0.0f);

        Body greenbinWallRight = world.createBody(new BodyDef());
        EdgeShape greenbinShapeWallRight = new EdgeShape();
        greenbinShapeWallRight.set(new Vec2(638f * M_PER_PIXEL, 480f * M_PER_PIXEL), new Vec2(638f * M_PER_PIXEL, 395f * M_PER_PIXEL));
        greenbinWallRight.createFixture(greenbinShapeWallRight, 0.0f);

        Body greenBincheck = world.createBody(new BodyDef());
        final CircleShape shape3 = new CircleShape();
        shape3.setRadius(0.3f);
        shape3.m_p.set(610f * M_PER_PIXEL, 460f * M_PER_PIXEL);
        /*EdgeShape coinShape = new EdgeShape();
        groundShape.set(new Vec2(0, height), new Vec2(width, height));*/
        greenBincheck.createFixture(shape3, 0.0f);
        bodies.put(greenBincheck, "greenBin");
        // bodiesGround.put("greenBin", greenBincheck);

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
        //throwTrash();


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
        time = 60-(timeI/40);
        timeI++;
        if(time ==0) {
            checkScore();
        }
        strTime = ""+ time;
        strScore = "Score = "+score+"/"+targetScore;



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
        for (int k4 = 0; k4 <= plasticBottleNum; k4++) {
            plasticBottle.get(k4).update(delta);
        }
        for (int k5 = 0; k5 <= plasticGlassNum; k5++) {
            plasticGlass.get(k5).update(delta);
        }
        for (int k6 = 0; k6 <= bookNum; k6++) {
            book.get(k6).update(delta);
        }
       /* if(destroy == true){
            // t.get(t1).layer().destroy();
            world.destroyBody(t.get(t1).getBody());
            destroy =false;
            }*/
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                //System.out.println("p in contact  " +power3 );
                Body a = contact.getFixtureA().getBody();
                Body b = contact.getFixtureB().getBody();
                // System.out.println("a =" +bodies.get(a));
                // System.out.println("b = "+bodies.get(b));
                if ((contact.getFixtureA().getBody() == mike.getBody())) {
                    //System.out.println(power);
                    b.applyForce(new Vec2(power, -150f), b.getPosition());
                    for (Body b1 : bodiesGround.values()) {
                        if (b1 == b)
                            System.out.println("contact ground");
                    }
                    //b.applyLinearImpulse(new Vec2(power, -0), b.getPosition());

                }
                if ((contact.getFixtureB().getBody() == mike.getBody())) {
                    //System.out.println(power);

                    for (Body b1 : bodiesGround.values()) {
                        if (b1 == a)
                            System.out.println("contact ground");
                    }

                    //b.applyLinearImpulse(new Vec2(power, -0), b.getPosition());

                }




               for (Trash trash: t) {
                    if ((contact.getFixtureA().getBody() == trash.getBody() && "blueBin" == bodies.get(b)) ||
                            (contact.getFixtureA().getBody() == trash.getBody() && "yellowBin" == bodies.get(b)) ||
                            (contact.getFixtureA().getBody() == trash.getBody() && "greenBin" == bodies.get(b))||
                            (contact.getFixtureA().getBody() == trash.getBody() && "ground2" == bodies.get(b))) {
                        if ("ground2" != bodies.get(b)&&"blueBin" == bodies.get(b)){
                            score += 10;
                        }
                        else if("yellowBin" == bodies.get(b) || "greenBin" == bodies.get(b)) {
                            if (score > 0)
                                score -= 5;
                        }
                        trash.layer().destroy();
                        tRemove.add(trash);


                    }
                    if ((contact.getFixtureB().getBody() == trash.getBody() && "blueBin" == bodies.get(a)) ||
                            (contact.getFixtureB().getBody() == trash.getBody() && "yellowBin" == bodies.get(a)) ||
                            (contact.getFixtureB().getBody() == trash.getBody() && "greenBin" == bodies.get(a))||
                            (contact.getFixtureB().getBody() == trash.getBody() && "ground2" == bodies.get(a))) {
                        if ("ground2" != bodies.get(b)&&"blueBin" == bodies.get(b)){
                            score += 10;
                        }
                        else if("yellowBin" == bodies.get(b) || "greenBin" == bodies.get(b)) {
                            if (score > 0)
                                score -= 5;
                        }
                        trash.layer().destroy();
                        //world.destroyBody(trash.getBody());
                        tRemove.add(trash);

                    }
                   //System.out.println("trash = " + trashNum);
                   trashNum++;
                }
                for (Can can2: can) {
                    if ((contact.getFixtureA().getBody() == can2.getBody() && "blueBin" == bodies.get(b)) ||
                            (contact.getFixtureA().getBody() == can2.getBody() && "yellowBin" == bodies.get(b)) ||
                            (contact.getFixtureA().getBody() == can2.getBody() && "greenBin" == bodies.get(b))||
                            (contact.getFixtureA().getBody() == can2.getBody() && "ground2" == bodies.get(b))) {
                        if ("ground2" != bodies.get(b)&&"yellowBin" == bodies.get(b)){
                            score += 10;
                        }
                        else if("blueBin" == bodies.get(b) || "greenBin" == bodies.get(b)){
                            if (score > 0)
                                score -=5;
                        }
                       can2.layer().destroy();
                       canRemove.add(can2);

                    }
                    if ((contact.getFixtureB().getBody() == can2.getBody() && "blueBin" == bodies.get(a)) ||
                            (contact.getFixtureB().getBody() == can2.getBody() && "yellowBin" == bodies.get(a)) ||
                            (contact.getFixtureB().getBody() == can2.getBody() && "greenBin" == bodies.get(a))||
                            (contact.getFixtureB().getBody() == can2.getBody() && "ground2" == bodies.get(a))) {
                        if ("ground2" != bodies.get(b)&&"yellowBin" == bodies.get(b)){
                            score += 10;
                        }
                        else if("blueBin" == bodies.get(b) || "greenBin" == bodies.get(b)){
                            if (score > 0)
                                score -=5;
                        }
                        can2.layer().destroy();
                        canRemove.add(can2);

                    }
                }
                for (BottleGlass bottleGlass2: bottleGlass) {
                    if ((contact.getFixtureA().getBody() == bottleGlass2.getBody() && "blueBin" == bodies.get(b)) ||
                            (contact.getFixtureA().getBody() == bottleGlass2.getBody() && "yellowBin" == bodies.get(b)) ||
                            (contact.getFixtureA().getBody() == bottleGlass2.getBody() && "greenBin" == bodies.get(b))||
                            (contact.getFixtureA().getBody() == bottleGlass2.getBody() && "ground2" == bodies.get(b))) {
                        if ("ground2" != bodies.get(b)&&"greenBin" == bodies.get(b)){
                            score += 10;
                        }
                        else if("blueBin" == bodies.get(b) || "yellowBin" == bodies.get(b)){
                            if (score > 0)
                                score -=5;
                        }
                        bottleGlass2.layer().destroy();
                        bottleGlassRemove.add(bottleGlass2);

                    }
                    if ((contact.getFixtureB().getBody() == bottleGlass2.getBody() && "blueBin" == bodies.get(a)) ||
                            (contact.getFixtureB().getBody() == bottleGlass2.getBody() && "yellowBin" == bodies.get(a)) ||
                            (contact.getFixtureB().getBody() == bottleGlass2.getBody() && "greenBin" == bodies.get(a))||
                            (contact.getFixtureB().getBody() == bottleGlass2.getBody() && "ground2" == bodies.get(a))) {
                        if ("ground2" != bodies.get(b)&&"greenBin" == bodies.get(b)){
                            score += 10;
                        }
                        else if("blueBin" == bodies.get(b) || "yellowBin" == bodies.get(b)){
                            if (score > 0)
                                score -=5;
                        }
                        bottleGlass2.layer().destroy();
                        bottleGlassRemove.add(bottleGlass2);


                    }

                }
                for (PlasticBottle plasticBottle2: plasticBottle) {
                    if ((contact.getFixtureA().getBody() == plasticBottle2.getBody() && "blueBin" == bodies.get(b)) ||
                            (contact.getFixtureA().getBody() == plasticBottle2.getBody() && "yellowBin" == bodies.get(b)) ||
                            (contact.getFixtureA().getBody() == plasticBottle2.getBody() && "greenBin" == bodies.get(b))||
                            (contact.getFixtureA().getBody() == plasticBottle2.getBody() && "ground2" == bodies.get(b))) {
                        if ("ground2" != bodies.get(b)&&"yellowBin" == bodies.get(b)){
                            score += 10;
                        }
                        else if("blueBin" == bodies.get(b) || "greenBin" == bodies.get(b)){
                            if (score > 0)
                                score -=5;
                        }
                        plasticBottle2.layer().destroy();
                        plasticBottleRemove.add(plasticBottle2);

                    }
                    if ((contact.getFixtureB().getBody() == plasticBottle2.getBody() && "blueBin" == bodies.get(a)) ||
                            (contact.getFixtureB().getBody() == plasticBottle2.getBody() && "yellowBin" == bodies.get(a)) ||
                            (contact.getFixtureB().getBody() == plasticBottle2.getBody() && "greenBin" == bodies.get(a))||
                            (contact.getFixtureB().getBody() == plasticBottle2.getBody() && "ground2" == bodies.get(a))) {
                        if ("ground2" != bodies.get(b)&&"yellowBin" == bodies.get(b)){
                            score += 10;
                        }
                        else if("blueBin" == bodies.get(b) || "greenBin" == bodies.get(b)){
                            if (score > 0)
                                score -=5;
                        }
                        plasticBottle2.layer().destroy();
                        plasticBottleRemove.add(plasticBottle2);


                    }

                }
                for (Book book2: book) {
                    if ((contact.getFixtureA().getBody() == book2.getBody() && "blueBin" == bodies.get(b)) ||
                            (contact.getFixtureA().getBody() == book2.getBody() && "yellowBin" == bodies.get(b)) ||
                            (contact.getFixtureA().getBody() == book2.getBody() && "greenBin" == bodies.get(b))||
                            (contact.getFixtureA().getBody() == book2.getBody() && "ground2" == bodies.get(b))) {
                        if ("ground2" != bodies.get(b)&&"blueBin" == bodies.get(b)){
                            score += 10;
                        }
                        else if("yellowBin" == bodies.get(b) || "greenBin" == bodies.get(b)) {
                            if (score > 0)
                                score -= 5;
                        }
                        book2.layer().destroy();
                        bookRemove.add(book2);

                    }
                    if ((contact.getFixtureB().getBody() == book2.getBody() && "blueBin" == bodies.get(a)) ||
                            (contact.getFixtureB().getBody() == book2.getBody() && "yellowBin" == bodies.get(a)) ||
                            (contact.getFixtureB().getBody() == book2.getBody() && "greenBin" == bodies.get(a))||
                            (contact.getFixtureB().getBody() == book2.getBody() && "ground2" == bodies.get(a))) {
                        if ("ground2" != bodies.get(b)&&"blueBin" == bodies.get(b)){
                            score += 10;
                        }
                        else if("yellowBin" == bodies.get(b) || "greenBin" == bodies.get(b)) {
                            if (score > 0)
                                score -= 5;
                        }
                        book2.layer().destroy();
                        bookRemove.add(book2);


                    }

                    for (PlasticGlass plasticGlass2: plasticGlass) {
                        if ((contact.getFixtureA().getBody() == plasticGlass2.getBody() && "blueBin" == bodies.get(b)) ||
                                (contact.getFixtureA().getBody() == plasticGlass2.getBody() && "yellowBin" == bodies.get(b)) ||
                                (contact.getFixtureA().getBody() == plasticGlass2.getBody() && "greenBin" == bodies.get(b))||
                                (contact.getFixtureA().getBody() == plasticGlass2.getBody() && "ground2" == bodies.get(b))) {
                            if ("ground2" != bodies.get(b)&&"yellowBin" == bodies.get(b)){
                                score += 10;
                            }
                            else if("blueBin" == bodies.get(b) || "greenBin" == bodies.get(b)){
                                if (score > 0)
                                    score -=5;
                            }
                            plasticGlass2.layer().destroy();
                            plasticGlassRemove.add(plasticGlass2);

                        }
                        if ((contact.getFixtureB().getBody() == plasticGlass2.getBody() && "blueBin" == bodies.get(a)) ||
                                (contact.getFixtureB().getBody() == plasticGlass2.getBody() && "yellowBin" == bodies.get(a)) ||
                                (contact.getFixtureB().getBody() == plasticGlass2.getBody() && "greenBin" == bodies.get(a))||
                                (contact.getFixtureB().getBody() == plasticGlass2.getBody() && "ground2" == bodies.get(a))) {
                            if ("ground2" != bodies.get(b)&&"yellowBin" == bodies.get(b)){
                                score += 10;
                            }
                            else if("blueBin" == bodies.get(b) || "greenBin" == bodies.get(b)){
                                if (score > 0)
                                    score -=5;
                            }
                            plasticGlass2.layer().destroy();
                            plasticGlassRemove.add(plasticGlass2);


                        }
                    }

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
        for(Trash tRemoves: tRemove){
            world.destroyBody(tRemoves.getBody());
        }
        for(Can canRemoves: canRemove){
            world.destroyBody(canRemoves.getBody());
        }
        for(BottleGlass bottleGlass1Removes: bottleGlassRemove){
            world.destroyBody(bottleGlass1Removes.getBody());
        }
        for(PlasticBottle plasticBottleRemoves: plasticBottleRemove){
            world.destroyBody(plasticBottleRemoves.getBody());
        }
        for(Book bookRemoves: bookRemove){
            world.destroyBody(bookRemoves.getBody());
        }
        for(PlasticGlass plasticGlass1Removes: plasticGlassRemove){
            world.destroyBody(plasticGlass1Removes.getBody());
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

        for (int k4 = 0; k4 <= plasticBottleNum; k4++)
            plasticBottle.get(k4).paint(clock);

        for (int k5 = 0; k5 <= plasticGlassNum; k5++)
            plasticGlass.get(k5).paint(clock);

        for (int k6 = 0; k6 <= bookNum; k6++)
            book.get(k6).paint(clock);

        if (showDebugDraw) {
            debugDraw.getCanvas().clear();
            debugDraw.getCanvas().setFillColor(Color.rgb(200, 15, 15));

            debugDraw.getCanvas().drawText(debugString, 15, 50);
            debugDraw.getCanvas().drawText(strScore, 510, 40);
            debugDraw.getCanvas().drawText(strTime, 420, 60);
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
                    next = nRand;

                    nRand = rand.nextInt(6) + 1;
                    if (nRand == 1)
                        nextString = "Paper";
                    else if (nRand == 2)
                        //nextString = "Can";
                        nextString = "Plastic Bottle";
                    else if (nRand == 3)
                        nextString = "Glass Bottle";
                    else if (nRand == 4)
                        nextString = "Can";
                    else if (nRand == 5)
                        nextString = "Book";
                    else if (nRand == 6)
                        nextString = "Plastic Glass";
                    debugString = "Next is " + nextString;
                    //nRand =1;
                    if (next == 1)
                        createTrash(t1);
                    else if (next == 2)
                        //createTrash(t1);
                        //createCan(canNum);
                        createPlasticBottle(plasticBottleNum);
                    else if (next == 3)
                        //createTrash(t1);
                        createBottleGlass(bottleGlassNum);
                    else if (next == 4)
                        //createTrash(t1);
                        //createCan(canNum);
                        createCan(canNum);
                    else if (next == 5)
                        //createTrash(t1);
                        createBook(bookNum);
                    else if (next == 6)
                        //createTrash(t1);
                        createPlasticGlass(plasticGlassNum);


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

    public void createTrash(int t2) {
        this.t1 = t2;
        t.add(t1, new Trash(world, xMike2 + 25, yMike2 - 30));
        bodies.put(t, "Trash" + t1);
        layer.add(t.get(t1).layer());
        t.get(t1).hasThrow(1);
        t1++;
        /*for (int i =0 ; i <= t1 ; i++){

            graphics().rootLayer().add(t.get(i).layer());
            bodies.put(t.get(i),"trash"+i);
            System.out.println(bodies.get(t.get(i)));

        }*/
         }

    public void createCan(int canNum2) {
        this.canNum = canNum2;
        can.add(canNum, new Can(world, xMike2 + 30, yMike2 - 70));
        bodies.put(can, "Can " + canNum);
        layer.add(can.get(canNum).layer());
        can.get(canNum).hasThrow(1);
        canNum++;
        /*can.add(canNum, new Can(world, xMike2 + 20, yMike2 - 70));
        canNum++;
        for (int i =0 ; i <= canNum ; i++){
            graphics().rootLayer().add(can.get(i).layer());
            bodies.put(can.get(i),"can"+i);
            System.out.println(bodies.get(can.get(i)));

        }*/
    }

    public void createBottleGlass(int bottleGlassNum2) {
        this.bottleGlassNum = bottleGlassNum2;
        bottleGlass.add(bottleGlassNum, new BottleGlass(world, xMike2 + 30, yMike2 - 70));
        bodies.put(bottleGlass, "BottleGlass " + bottleGlassNum);
        layer.add(bottleGlass.get(bottleGlassNum).layer());
        bottleGlass.get(bottleGlassNum).hasThrow(1);
        bottleGlassNum++;
    }

    public void createPlasticBottle(int plasticBottleNum2) {
        this.plasticBottleNum = plasticBottleNum2;
        plasticBottle.add(plasticBottleNum, new PlasticBottle(world, xMike2 + 30, yMike2 - 70));
        bodies.put(plasticBottle, "PlasticBottle " + plasticBottleNum);
        layer.add(plasticBottle.get(plasticBottleNum).layer());
        plasticBottle.get(plasticBottleNum).hasThrow(1);
        plasticBottleNum++;
    }


    public void createBook(int bookNum2) {
        this.bookNum = bookNum2;
        book.add(bookNum, new Book(world, xMike2 + 30, yMike2 - 70));
        bodies.put(book, "Book " + bookNum);
        layer.add(book.get(bookNum).layer());
        book.get(bookNum).hasThrow(1);
        bookNum++;
    }

    public void createPlasticGlass(int plasticGlassNum2) {
        this.plasticGlassNum = plasticGlassNum2;
        plasticGlass.add(plasticGlassNum, new PlasticGlass(world, xMike2 + 30, yMike2 - 70));
        bodies.put(plasticGlass, "PlasticGlass " + plasticGlassNum);
        layer.add(plasticGlass.get(plasticGlassNum).layer());
        plasticGlass.get(plasticGlassNum).hasThrow(1);
        plasticGlassNum++;
    }



    public static void powerMethod(int power2) {

        if (power2 == 0)
            power = 0;
        else if (power2 == 1)
            power = 50f;
        else if (power2 == 2)
            power = 100f;
        else if (power2 == 3)
            power = 150f;
        else if (power2 == 4)
            power = 200f;
        else if (power2 == 5)
            power = 250f;
        else if (power2 == 6)
            power = 300f;
        else if (power2 == 7)
            power = 350f;
        else if (power2 == 8)
            power = 400f;
        else if (power2 == 9)
            power = 500f;
        else if (power2 == 10)
            power = 550;

        //System.out.println("p" +power );
    }
    public void checkScore(){
        if(score <=targetScore) {
            timeI = 0;
            time = 60;
            score =0;
            debugDraw.getCanvas().clear();
            ss.push(overScreen);
        }
        else if(score >=targetScore){
            timeI = 0;
            time = 60;
            score =0;
            debugDraw.getCanvas().clear();
            ss.push(endScreen);
        }
    }

    public void setOrderOfLayer(Layer layer, int order) {
        /*for (int h = 0; h <= t1; h++) {
                    if ((contact.getFixtureA().getBody() == t.get(h).getBody() && "blueBin" == bodies.get(b)) ||
                            (contact.getFixtureA().getBody() == t.get(h).getBody() && "yellowBin" == bodies.get(b)) ||
                            (contact.getFixtureA().getBody() == t.get(h).getBody() && "greenBin" == bodies.get(b)) ||
                            (contact.getFixtureA().getBody() == t.get(h).getBody() && "ground2" == bodies.get(b))) {
                        t.get(h).layer().destroy();
                        a.setActive(false);
                        //t.get(h).layer().setVisible(false);
                       // t.get(h).getBody().setActive(false);
                        //t.get(h).layer().setVisible(false);
                        //world.destroyBody(t.get(h).getBody());

                    }
                    if ((contact.getFixtureB().getBody() == t.get(h).getBody() && "blueBin" == bodies.get(a)) ||
                            (contact.getFixtureB().getBody() == t.get(h).getBody() && "yellowBin" == bodies.get(a)) ||
                            (contact.getFixtureB().getBody() == t.get(h).getBody() && "greenBin" == bodies.get(a)) ||
                            (contact.getFixtureB().getBody() == t.get(h).getBody() && "ground2" == bodies.get(a))) {
                        b.setActive(false);
                        t.get(h).layer().destroy();
                        //t.get(h).getBody().setActive(false);
                        //t.get(h).layer().setVisible(false);
                        //t.get(h).layer().setVisible(false);



                    }

                }
                for (int h2 = 0; h2 <= canNum; h2++) {
                    if ((contact.getFixtureA().getBody() == can.get(h2).getBody() && "blueBin" == bodies.get(b)) ||
                            (contact.getFixtureA().getBody() == can.get(h2).getBody() && "yellowBin" == bodies.get(b)) ||
                            (contact.getFixtureA().getBody() == can.get(h2).getBody() && "greenBin" == bodies.get(b)) ||
                            (contact.getFixtureA().getBody() == can.get(h2).getBody() && "ground2" == bodies.get(b))) {
                        //System.out.println("a");
                        // tR.add(tRN,trash);
                        can.get(h2).getBody().setActive(false);
                        can.get(h2).layer().setVisible(false);


                        //wood.destroy();
                    }
                    if ((contact.getFixtureB().getBody() == can.get(h2).getBody() && "blueBin" == bodies.get(a)) ||
                            (contact.getFixtureB().getBody() == can.get(h2).getBody() && "yellowBin" == bodies.get(a)) ||
                            (contact.getFixtureB().getBody() == can.get(h2).getBody() && "greenBin" == bodies.get(a)) ||
                            (contact.getFixtureB().getBody() == can.get(h2).getBody() && "ground2" == bodies.get(a))) {
                        //System.out.println("b");
                        can.get(h2).getBody().setActive(false);
                        //tR.add(tRN,trash);

                        can.get(h2).layer().setVisible(false);
                    }
                    //System.out.println("can = " + h2);
                }
                for (int h3 = 0; h3 <= bottleGlassNum; h3++) {
                    if ((contact.getFixtureA().getBody() == bottleGlass.get(h3).getBody() && "blueBin" == bodies.get(b)) ||
                            (contact.getFixtureA().getBody() == bottleGlass.get(h3).getBody() && "yellowBin" == bodies.get(b)) ||
                            (contact.getFixtureA().getBody() == bottleGlass.get(h3).getBody() && "greenBin" == bodies.get(b)) ||
                            (contact.getFixtureA().getBody() == bottleGlass.get(h3).getBody() && "ground2" == bodies.get(b))) {
                        //System.out.println("a");
                        // tR.add(tRN,trash);
                        bottleGlass.get(h3).getBody().setActive(false);
                        bottleGlass.get(h3).layer().setVisible(false);


                        //wood.destroy();
                    }
                    if ((contact.getFixtureB().getBody() == bottleGlass.get(h3).getBody() && "blueBin" == bodies.get(a)) ||
                            (contact.getFixtureB().getBody() == bottleGlass.get(h3).getBody() && "yellowBin" == bodies.get(a)) ||
                            (contact.getFixtureB().getBody() == bottleGlass.get(h3).getBody() && "greenBin" == bodies.get(a)) ||
                            (contact.getFixtureB().getBody() == bottleGlass.get(h3).getBody() && "ground2" == bodies.get(a))) {
                        //System.out.println("b");
                        bottleGlass.get(h3).getBody().setActive(false);
                        //tR.add(tRN,trash);

                        bottleGlass.get(h3).layer().setVisible(false);
                    }
                    //System.out.println("bottle = " + h3);
                }*/
        // Process the reordering of layer by removing it, then adding it at the expecting order index
        //jMap.getLayers().remove(layer);
        //jMap.getLayers().add(order,  layer);
    }


}
