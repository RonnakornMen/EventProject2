package sut.game01.core.gauge;


import static playn.core.PlayN.*;
import playn.core.Image;
import playn.core.*;
import playn.core.Keyboard;
import playn.core.ImageLayer;
import playn.core.ImageLayer;
import playn.core.Mouse;
import playn.core.PlayN;
import tripleplay.game.Screen;
import react.UnitSlot;
import tripleplay.game.UIScreen;
import tripleplay.game.ScreenStack;
import tripleplay.ui.*;
import tripleplay.ui.layout.AxisLayout;
import static playn.core.PlayN.graphics;
import sut.game01.core.sprite.*;
import playn.core.util.*;




public class Gauge   {
	private static ImageLayer gauge0;
	private final ImageLayer gauge1;
	private final ImageLayer gauge2;
	private final ImageLayer gauge3;
	private final ImageLayer gauge4;
	private final ImageLayer gauge5;
	private final ImageLayer gauge6;
	private final ImageLayer gauge7;
	private final ImageLayer gauge8;
	private final ImageLayer gauge9;
	private final ImageLayer gauge10;

	public Gauge(){
	    Image gauge0Image = assets().getImage("images/gauge/gauge0.png");
    	this.gauge0 = graphics().createImageLayer(gauge0Image);
    	gauge0.setTranslation(10, 10);

    	Image gauge1Image = assets().getImage("images/gauge/gauge1.png");
    	this.gauge1 = graphics().createImageLayer(gauge1Image);
    	gauge1.setTranslation(10, 10);

    	Image gauge2Image = assets().getImage("images/gauge/gauge2.png");
    	this.gauge2 = graphics().createImageLayer(gauge2Image);
    	gauge2.setTranslation(10, 10);

    	Image gauge3Image = assets().getImage("images/gauge/gauge3.png");
    	this.gauge3 = graphics().createImageLayer(gauge3Image);
    	gauge3.setTranslation(10, 10);

    	Image gauge4Image = assets().getImage("images/gauge/gauge4.png");
    	this.gauge4 = graphics().createImageLayer(gauge4Image);
    	gauge4.setTranslation(10, 10);

    	Image gauge5Image = assets().getImage("images/gauge/gauge5.png");
    	this.gauge5 = graphics().createImageLayer(gauge5Image);
    	gauge5.setTranslation(10, 10);

    	Image gauge6Image = assets().getImage("images/gauge/gauge6.png");
    	this.gauge6 = graphics().createImageLayer(gauge6Image);
    	gauge6.setTranslation(10, 10);

    	Image gauge7Image = assets().getImage("images/gauge/gauge7.png");
    	this.gauge7 = graphics().createImageLayer(gauge7Image);
    	gauge7.setTranslation(10, 10);

    	Image gauge8Image = assets().getImage("images/gauge/gauge8.png");
    	this.gauge8 = graphics().createImageLayer(gauge8Image);
    	gauge8.setTranslation(10, 10);

    	Image gauge9Image = assets().getImage("images/gauge/gauge9.png");
    	this.gauge9 = graphics().createImageLayer(gauge9Image);
    	gauge9.setTranslation(10, 10);

    	Image gauge10Image = assets().getImage("images/gauge/gauge10.png");
    	this.gauge10 = graphics().createImageLayer(gauge10Image);
        

    

	}
   
	
  	/*public static ImageLayer show(){
    	int g = 0;
    	for(g=0;g<=10;g++){
    		if(g == 0){
                  return gauge0;
            }
            else if(g ==1){
            }
            else if(g ==2){
            }
             else if(g ==3){
            }
            else if(g ==4){
            }
            else if(g ==5){
            }
            else if(g ==6){
             }
            else if(g ==7){
            }
            else if(g ==8){
            }
            else if(g ==9){
            }
            else if(g ==10){
                g=0;
             }
    	}
      
    
	}*/
    
}
 
