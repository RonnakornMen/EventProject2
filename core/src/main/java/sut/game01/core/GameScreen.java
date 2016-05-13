package sut.game01.core;


import static playn.core.PlayN.*;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import playn.core.*;
import playn.core.ImageLayer;

import playn.core.util.Clock;
import tripleplay.game.Screen;
import react.UnitSlot;
import tripleplay.game.UIScreen;
import tripleplay.game.ScreenStack;
import tripleplay.ui.*;
import tripleplay.ui.layout.AxisLayout;
import static playn.core.PlayN.graphics;
import sut.game01.core.character.Mike;
import sut.game01.core.gauge.Gauge;



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
  private Image bgImage;
  private Image cloudImage;
  private float xC= 24.0f;
  private float yC = 100;
  private final ImageLayer blueBin;
  private final ImageLayer yellowBin;
  private final ImageLayer greenBin;
  private final ImageLayer wall;
  private Mike mike;
  private Gauge gauge;
  private final ImageLayer mario;

  private int b=0;
  private float yM = 395;
  private Root root;

    public static float M_PER_PIXEL = 1 / 26.666667f;
    private static int width = 24;
    private static int height = 18;


    private World world;
    private boolean showDebugDraw = true;
    private DebugDrawBox2D debugDraw;



    public GameScreen(final ScreenStack ss){
    this.ss = ss;
    this.settingScreen =new SettingScreen(ss);
    this.overScreen =new OverScreen(ss);
    this.endScreen =new EndScreen(ss);


    bgImage = assets().getImage("images/bg.png");
    this.bg = graphics().createImageLayer(bgImage);
    
    //====================================================================backButton
    Image backImage = assets().getImage("images/back.png");
    this.backButton = graphics().createImageLayer(backImage);
    backButton.setTranslation(10, 10);
    
    backButton.addListener(new Mouse.LayerAdapter(){
      @Override
      public void onMouseUp(Mouse.ButtonEvent event){
        ss.remove(ss.top()); // pop screen
      }
    });
    //====================================================================settingButton
    Image settingImage = assets().getImage("images/setting.png");
    this.settingButton = graphics().createImageLayer(settingImage);
    settingButton.setTranslation(200, 10);
    
    settingButton.addListener(new Mouse.LayerAdapter(){
      @Override
      public void onMouseUp(Mouse.ButtonEvent event){
        ss.push(settingScreen); 
      }
    });
    //=============================================================================pauseButton=====
    Image pauseImage = assets().getImage("images/pauseButton.png");
    this.pauseButton = graphics().createImageLayer(pauseImage);
    pauseButton.setTranslation(270, 0);
    
    pauseButton.addListener(new Mouse.LayerAdapter(){
      @Override
      public void onMouseUp(Mouse.ButtonEvent event){
       // ss.push(overScreen); 
      }
    });

    //==================================================================================over
    Image overImage = assets().getImage("images/overButton.png");
    this.overButton = graphics().createImageLayer(overImage);
    overButton.setTranslation(40, 0);
    
    overButton.addListener(new Mouse.LayerAdapter(){
      @Override
      public void onMouseUp(Mouse.ButtonEvent event){
        ss.push(overScreen); 
      }
    });
    //==================================================================================end
    Image endImage = assets().getImage("images/endButton.png");
    this.endButton = graphics().createImageLayer(endImage);
    endButton.setTranslation(540, 0);
    
    endButton.addListener(new Mouse.LayerAdapter(){
      @Override
      public void onMouseUp(Mouse.ButtonEvent event){
        ss.push(endScreen); 
      }
    });
    //==========================================================================clound
    cloudImage = assets().getImage("images/cloud.png");
    cloud = graphics().createImageLayer(cloudImage);
    graphics().rootLayer().add(cloud);
    cloud.setTranslation(0, 105);
    //===========================================================================bluebin
    Image blueBinImage = assets().getImage("images/blueBin.png");
    this.blueBin = graphics().createImageLayer(blueBinImage);
    blueBin.setTranslation(360, 360);
    //===========================================================================yellowbin
    Image yellowBinImage = assets().getImage("images/yellowBin.png");
    this.yellowBin = graphics().createImageLayer(yellowBinImage);
    yellowBin.setTranslation(460, 360);
    //===========================================================================greenbin
    Image greenBinImage = assets().getImage("images/greenBin.png");
    this.greenBin = graphics().createImageLayer(greenBinImage);
    greenBin.setTranslation(560, 360);
    //===========================================================================wall
    Image wallImage = assets().getImage("images/wall.png");
    this.wall = graphics().createImageLayer(wallImage);
    wall.setTranslation(320, 320);

    //==================================================================mario
    Image marioImage = assets().getImage("images/mario.png");
    this.mario = graphics().createImageLayer(marioImage);
    mario.setTranslation(360, yM);
    //gaugeShow();

     Vec2 gravity = new Vec2(0.0f, 10.0f);
     world = new World(gravity);
     world.setWarmStarting(true);
     world.setAutoClearForces(true);
  }
 //=============================================================
  @Override
  public void wasShown (){
    super.wasShown();
    this.layer.add(bg);
    //this.layer.add(backButton);
    //this.layer.add(settingButton);
    //this.layer.add(mario);
    this.layer.add(pauseButton);
    this.layer.add(overButton);
    this.layer.add(endButton);
    mike = new Mike(world,100f,480f);
    this.layer.add(mike.layer());
    this.layer.add(cloud);
    this.layer.add(blueBin);
    this.layer.add(yellowBin);
    this.layer.add(greenBin);
    this.layer.add(wall);
    gauge = new Gauge(10f, 10f);
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

          debugDraw.setCamera(0, 0, 1f /M_PER_PIXEL);
          world.setDebugDraw(debugDraw);
      }

      Body ground = world.createBody(new BodyDef());
      EdgeShape groundShape = new EdgeShape();
      groundShape.set(new Vec2(0, height), new Vec2(width, height));
      ground.createFixture(groundShape, 0.0f);




  }
  @Override
  public void update(int delta){
    super.update(delta);
    mike.update(delta);
    gauge.update(delta);
      world.step(0.033f, 10, 10);
    //=========================================moveCloud
     xC += 0.5f * delta /8;
    if (xC> bgImage.width() + cloudImage.width()){
      xC = -cloudImage.width(); 
    }
    cloud.setTranslation(xC, yC);


    
    if (yM > 320 && b==0){
         yM -= 0.5f * delta /20;
    }
    else if(yM >=320 ){
      b =1;
      yM += 0.5f * delta /20;
      if(yM == 395)
        b=0;
    }
    mario.setTranslation(360, yM);
  }

    @Override
    public void paint(Clock clock) {
        super.paint(clock);
        mike.paint(clock);

        if (showDebugDraw) {
            debugDraw.getCanvas().clear();
            debugDraw.getCanvas().setFillColor(Color.rgb(255,255,255));
           // debugDraw.getCanvas().drawText(debugString, 100,100);
            world.drawDebugData();
        }
    }

}
