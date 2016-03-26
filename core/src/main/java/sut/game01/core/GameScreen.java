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



public class GameScreen extends Screen  {

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

  private Root root;

  public GameScreen(final ScreenStack ss){
    this.ss = ss;
    this.settingScreen =new SettingScreen(ss);
    this.overScreen =new OverScreen(ss);
    this.endScreen =new EndScreen(ss);


    Image bgImage = assets().getImage("images/bg.png");
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
    overButton.setTranslation(40, 390);
    
    overButton.addListener(new Mouse.LayerAdapter(){
      @Override
      public void onMouseUp(Mouse.ButtonEvent event){
        ss.push(overScreen); 
      }
    });
    //==================================================================================end
    Image endImage = assets().getImage("images/endButton.png");
    this.endButton = graphics().createImageLayer(endImage);
    endButton.setTranslation(540, 390);
    
    endButton.addListener(new Mouse.LayerAdapter(){
      @Override
      public void onMouseUp(Mouse.ButtonEvent event){
        ss.push(endScreen); 
      }
    });
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
  
  }
}
