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
  private final ImageLayer homeButton;
  private final ImageLayer levelSelect;
  private final ImageLayer startButton;
  private final ImageLayer level1;
  private final ImageLayer level2;
  private final ImageLayer level3;
  private final ImageLayer level4;
  private Root root;

  public LevelScreen(final ScreenStack ss){
    this.ss = ss;
    this.gameScreen =new GameScreen(ss);
    Image bgImage = assets().getImage("images/bg.png");
    this.bg = graphics().createImageLayer(bgImage);
    
 
    //=============================================================window
    Image levelSelectImage = assets().getImage("images/levelSelect.png");
    this.levelSelect = graphics().createImageLayer(levelSelectImage);
    levelSelect.setTranslation(110, 20);

    Image level1Image = assets().getImage("images/level1.png");
    this.level1 = graphics().createImageLayer(level1Image);
    level1.setTranslation(140, 140);

    level1.addListener(new Mouse.LayerAdapter(){
      @Override
      public void onMouseUp(Mouse.ButtonEvent event){
        ss.push(gameScreen);

         }
    });

    Image level2Image = assets().getImage("images/level2.png");
    this.level2 = graphics().createImageLayer(level2Image);
    level2.setTranslation(220, 140);

    Image level3Image = assets().getImage("images/level3.png");
    this.level3 = graphics().createImageLayer(level3Image);
    level3.setTranslation(300, 140);

    Image level4Image = assets().getImage("images/level4.png");
    this.level4 = graphics().createImageLayer(level4Image);
    level4.setTranslation(380, 140);


    //============================================================backButton
    Image homeImage = assets().getImage("images/homeButton.png");
    this.homeButton = graphics().createImageLayer(homeImage);
    homeButton.setTranslation(220, 240);
    
    homeButton.addListener(new Mouse.LayerAdapter(){
      @Override
      public void onMouseUp(Mouse.ButtonEvent event){
        ss.remove(ss.top()); // pop screen
      }
    });
    //=============================================================gameButton
    Image gameImage = assets().getImage("images/start.png");
    this.startButton = graphics().createImageLayer(gameImage);
    startButton.setTranslation(200, 10);
    
    
   
  }

  //=============================================================
  @Override
  public void wasShown (){
    super.wasShown();
    this.layer.add(bg);
    //this.layer.add(startButton);
    this.layer.add(levelSelect);
    this.layer.add(level1);
    this.layer.add(level2);
    this.layer.add(level3);
    this.layer.add(level4);
    this.layer.add(homeButton);

  
  }
}
