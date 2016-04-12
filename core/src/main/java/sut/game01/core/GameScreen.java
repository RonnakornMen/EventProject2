package sut.game01.core;


import static playn.core.PlayN.*;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.ImageLayer;
import playn.core.Mouse;

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

  private  ImageLayer gauge0;
  private  ImageLayer gauge1;
  private  ImageLayer gauge2;
  private  ImageLayer gauge3;
  private  ImageLayer gauge4;
  private  ImageLayer gauge5;
  private  ImageLayer gauge6;
  private  ImageLayer gauge7;
  private  ImageLayer gauge8;
  private  ImageLayer gauge9;
  private  ImageLayer gauge10;

  private Root root;

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

  
    //gaugeShow();
  }
 //=============================================================
  @Override
  public void wasShown (){
    super.wasShown();
    this.layer.add(bg);
    //this.layer.add(backButton);
    //this.layer.add(settingButton);
    this.layer.add(pauseButton);
    this.layer.add(overButton);
    this.layer.add(endButton);
    mike = new Mike(560f, 400f);
    this.layer.add(mike.layer());
    this.layer.add(cloud);
    this.layer.add(blueBin);
    this.layer.add(yellowBin);
    this.layer.add(greenBin);
    this.layer.add(wall);
    gauge = new Gauge(10f, 10f);
    this.layer.add(gauge.layer());
    /*int g = 0;
      for(g=0;g<=10;g++){
        if(g == 0){
          this.layer.add(gauge0);
        }
        else if(g ==1){
          this.layer.add(gauge1);
        }
        else if(g ==2){
          this.layer.add(gauge2);
        }
        else if(g ==3){
          this.layer.add(gauge3);
        }
        else if(g ==4){
          this.layer.add(gauge4);
        }
        else if(g ==5){
          this.layer.add(gauge5);
        }
        else if(g ==6){
          this.layer.add(gauge6);
        }
        else if(g ==7){
          this.layer.add(gauge7);
        }
        else if(g ==8){
          this.layer.add(gauge8);
        }
        else if(g ==9){
          this.layer.add(gauge9);
        }
        else if(g ==10){
          this.layer.add(gauge10);
          g=0;
        }
      }*/
   
    
  
  }
  @Override
  public void update(int delta){
    super.update(delta);
    mike.update(delta);
    gauge.update(delta);
    //=========================================moveCloud
     xC += 0.5f * delta /8;
    if (xC> bgImage.width() + cloudImage.width()){
      xC = -cloudImage.width(); 
    }
    cloud.setTranslation(xC, yC);
  
  }
  
  /*public void gaugeShow(){
    int g = 0;
      for(g=0;g<=10;g++){
        if(g == 0){
          this.layer.add(gauge0);
        }
        else if(g ==1){
          this.layer.add(gauge1);
        }
        else if(g ==2){
          this.layer.add(gauge2);
        }
        else if(g ==3){
          this.layer.add(gauge3);
        }
        else if(g ==4){
          this.layer.add(gauge4);
        }
        else if(g ==5){
          this.layer.add(gauge5);
        }
        else if(g ==6){
          this.layer.add(gauge6);
        }
        else if(g ==7){
          this.layer.add(gauge7);
        }
        else if(g ==8){
          this.layer.add(gauge8);
        }
        else if(g ==9){
          this.layer.add(gauge9);
        }
        else if(g ==10){
          this.layer.add(gauge10);
          g=0;
        }
      }
  }*/
}
