package kosmos.examples;

import kosmos.displayList.DisplayObject3D;
import kosmos.displayList.Sprite3D;
import kosmos.displayList.Object3D;
import kosmos.displayList.displayObjects.Plane;
import kosmos.displayList.displayObjects.RoundPlane;
import kosmos.displayList.displayObjects.textfields.TextField;
import kosmos.displayList.layers.KosmosRenderer;
import kosmos.displayList.layers.Layer3D;
import kosmos.displayList.layers.LayerImage;
import kosmos.displayList.layers.Renderer;
import kosmos.events.EventDispatcher3D;
import kosmos.events.IEventReceiver;
import kosmos.text.Font;
import kosmos.text.TextCharUv;
import kosmos.text.TextFormat;
import kosmos.texture.Texture3D;
import kosmos.texture.TextureArea;
import kosmos.uiToolbox.textures.RoundRectTextureObj;
import kosmos.utils.texturePacker.TexturePacker;
import processing.core.PApplet;
import processing.core.PImage;

public class Example03 implements IEventReceiver,IExample {
	
	PApplet applet;
	Renderer manager;
	Layer3D layer;
	KosmosRenderer kosmos;
	Sprite3D contener;
	Sprite3D contener2;
	RoundPlane p;
	float angle;
	
	TextField txt;
	TextCharUv uv;
	PImage img;
	
	
	
	public Example03(PApplet appletObj){
		  applet = appletObj;
		  
		  String lines[] = applet.loadStrings("lorem_ipsum.txt");
		  String c = "";
		  //println("there are " + lines.length + " lines");
		  for (int i = 0 ; i < lines.length; i++) {
		    c += lines[i] ;
		  }
		  
		 //Font.defaultFontName = "Cordia";
		 //Font.defaultFontName = "Arial"; 
		 
		  int i,nbPlane = 10;
		  kosmos = new KosmosRenderer(applet);
		  manager = kosmos.createRenderer(false);
		  layer = manager.createLayer3D( 2000, true);
		  LayerImage layerImg = manager.createLayerImage(applet.loadImage("beer.png"),400,400,200,300);
		  
		  Layer3D layer2 = manager.createLayer3D( 2000, true);
		  
		  layer.addFont("Arial");
		  layer.addFont("Stencil_Std");
		  
		  layer2.addFont("Arial");
		  
		  TextFormat tf1 = layer.createTextFormat("Stencil_Std",TextFormat.LEFT_ALIGN,applet.color(255,0,0,255),50,12.5f,0);
		  TextFormat tf = layer2.createTextFormat("Arial",TextFormat.LEFT_ALIGN,applet.color(255,0,0,255),50,12.5f,0);
		  //System.out.println("tf font = "+tf.getFont().getFontName());
		  
		  String test = "azertyuiopqsdfghjklmwxcvbnAZERTYUIOPQSDFGHJKLMWXCVBN@<>0132456789+-*/=%&, ;:!.?}])^_`|(['{#ηΰκιθωµ$£~²§°";
		  test += test;
		  //test += test;
		  //test += test;
		 
		  txt = new TextField(layer,400,1200,tf1,200);
		  txt.text = test;
		  txt.x = 120;
		  contener = new Sprite3D();
		  layer.appendObject(contener);
		  contener.appendObject(txt);
		  layer.start();
		  
		  
		  //contener.useLocalParentRotation = true;
		  //txt.useLocalParentRotation = false;
		  //txt.useChildrenLocalParentRotation = true;
		  //txt.useChildrenLocalRotation = true;
		  
		  TextField txt2 = new TextField(layer2,400,1200,tf,200);
		  txt2.x = -400;
		  
		  
		  txt2.text = test;//(String) applet.trim(c); //"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed dolor ligula, laoreet non facilisis ac, posuere consequat justo.";//"blabla youpi";
		  
		  contener2 = new Sprite3D();
		  txt2.useLocalParentRotation = false;
		  txt2.useLocalRotation = true;
		  
		  layer2.appendObject(contener2);
		  contener2.appendObject(txt2);
		  layer2.start();
		  
		  angle = 0;
		  
		  
		 /* 
		 int len = txt.children.size();
		 DisplayObject3D o;
		 
		 for(i=0;i<len;i++){
			  o = txt.children.get(i); 
			  //System.out.println(o.getClass().getSimpleName());
			  //o.redMulti = (float)Math.random() * 1.5f;
			  //o.greenMulti = (float)Math.random() * 1.0f;
			  //o.blueMulti = (float)Math.random() * 0.5f;
		 }
		 //txt.alpha = 0.5f;
		  */
	}
	
	public void applyEvent(String action, EventDispatcher3D dispatcher){
		//Plane p = (Plane) dispatcher;
		//if(action == "ON_MOUSE_OVER") p.changeTexture(p.overTexture);
		//else if(action == "ON_MOUSE_OUT")p.changeTexture(p.normalTexture);
		//else if(action == "ON_MOUSE_CLICK"){
		//	applet.println("CLICK");
		//}
	}
	
	
	
	public void update(){ 
		  
		angle += (float) (((applet.mouseX - 500.0) / 500.0) * 0.01);

		
		 kosmos.update();
		
		 /*
		 
		 int i,len = txt.children.size();
		 float len2 = (float) len-1.0f;
		 DisplayObject3D o;
		 //System.out.println(len);
		 for(i=0;i<len;i++){
			  o = txt.children.get(i); 
			  //o.alpha = 0.25f + (float)  Math.abs(Math.sin(angle+((float)i))) * (i+1)/len2;
		      //o.rotationX += ((float)((i+1)/len2) * 0.1);
		      //o.rotationY += ((float)((i+1)/len2) * 0.2);
		      //o.rotationZ += ((float)((i+1)/len2) * 0.3);
		 }
		 */

		 contener.rotationY = (float) (((applet.mouseX - 500.0) / 500.0) * applet.PI*2);
		 contener.rotationX = (float) (((applet.mouseY - 500.0) / 500.0) * applet.PI*2);
		
		 
		 //txt.text = ""+applet.frameRate+" : "+applet.frameRate+" : "+applet.frameRate+" : "+applet.frameRate;
		 
		 //txt.getTextFormat().setTextSize(applet.mouseX);
		 
		 applet.fill(0);
		 applet.rect(0, 0, 100, 30);
		 applet.fill(255,255,255);
		 applet.text(""+applet.frameRate,15,15);
		 
		
	}
	
}
