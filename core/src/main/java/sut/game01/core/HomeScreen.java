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


public class HomeScreen extends Screen  {

  private final LevelScreen levelScreen;
  private final SettingScreen settingScreen;
  private final ScreenStack ss;
  private final ImageLayer bg;
  private final ImageLayer startButton;
  private final ImageLayer settingButton;
  private Root root;

  public HomeScreen(final ScreenStack ss){
    this.ss = ss;
    this.levelScreen =new LevelScreen(ss);
    this.settingScreen =new SettingScreen(ss);
    Image bgImage = assets().getImage("images/home.png");
    this.bg = graphics().createImageLayer(bgImage);
    
    //=============================================================START
    Image startImage = assets().getImage("images/start.png");
    this.startButton = graphics().createImageLayer(startImage);
    startButton.setTranslation(10, 10);

    startButton.addListener(new Mouse.LayerAdapter(){
      @Override
      public void onMouseUp(Mouse.ButtonEvent event){
        ss.push(levelScreen); 
      }
    });

    //=============================================================Setting
    Image settingImage = assets().getImage("images/setting.png");
    this.settingButton = graphics().createImageLayer(settingImage);
    settingButton.setTranslation(200, 10);
    
    settingButton.addListener(new Mouse.LayerAdapter(){
      @Override
      public void onMouseUp(Mouse.ButtonEvent event){
        ss.push(settingScreen); 
      }
    });
    
  }

  //=============================================================
  @Override
  public void wasShown (){
    super.wasShown();
    this.layer.add(bg);
    this.layer.add(startButton);  
    this.layer.add(settingButton);
  
  }
}

