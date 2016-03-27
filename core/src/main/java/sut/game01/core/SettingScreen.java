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



public class SettingScreen extends Screen  {

  private final ScreenStack ss;
  private final ImageLayer bg;
  private final ImageLayer backButton;
  private Root root;

  public SettingScreen(final ScreenStack ss){
    this.ss = ss;
    Image bgImage = assets().getImage("images/bg.png");
    this.bg = graphics().createImageLayer(bgImage);
    

    Image backImage = assets().getImage("images/backButton.png");
    this.backButton = graphics().createImageLayer(backImage);
    backButton.setTranslation(40, 390);
    
    backButton.addListener(new Mouse.LayerAdapter(){
      @Override
      public void onMouseUp(Mouse.ButtonEvent event){
        ss.remove(ss.top()); // pop screen
      }
    });
  }

  //=============================================================
  @Override
  public void wasShown (){
    super.wasShown();
    this.layer.add(bg);
    this.layer.add(backButton);
  
  }
}
