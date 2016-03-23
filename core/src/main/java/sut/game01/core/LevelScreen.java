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



public class LevelScreen extends Screen  {

  private final ScreenStack ss;
  private final GameScreen gameScreen;
  private final ImageLayer bg;
  private final ImageLayer backButton;
  private final ImageLayer startButton;
  private Root root;

  public LevelScreen(final ScreenStack ss){
    this.ss = ss;
    this.gameScreen =new GameScreen(ss);
    Image bgImage = assets().getImage("images/levelPage.png");
    this.bg = graphics().createImageLayer(bgImage);
    
    //============================================================backButton
    Image backImage = assets().getImage("images/back.png");
    this.backButton = graphics().createImageLayer(backImage);
    backButton.setTranslation(10, 10);
    
    backButton.addListener(new Mouse.LayerAdapter(){
      @Override
      public void onMouseUp(Mouse.ButtonEvent event){
        ss.remove(ss.top()); // pop screen
      }
    });

    //=============================================================gameButton
    Image gameImage = assets().getImage("images/start.png");
    this.startButton = graphics().createImageLayer(gameImage);
    startButton.setTranslation(200, 10);
    
    startButton.addListener(new Mouse.LayerAdapter(){
      @Override
      public void onMouseUp(Mouse.ButtonEvent event){
        ss.push(gameScreen);
      }
    });
  }

  //=============================================================
  @Override
  public void wasShown (){
    super.wasShown();
    this.layer.add(bg);
    this.layer.add(backButton);
    this.layer.add(startButton);
  
  }
}
