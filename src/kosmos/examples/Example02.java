package kosmos.examples;

import kosmos.displayList.Sprite3D;
import kosmos.displayList.Object3D;
import kosmos.displayList.displayObjects.Plane;
import kosmos.displayList.displayObjects.RoundPlane;
import kosmos.displayList.layers.KosmosRenderer;
import kosmos.displayList.layers.Layer3D;
import kosmos.displayList.layers.Renderer;
import kosmos.events.EventDispatcher3D;
import kosmos.events.IEventReceiver;
import kosmos.texture.Texture3D;
import kosmos.texture.TextureArea;
import kosmos.uiToolbox.textures.RoundRectTextureObj;
import kosmos.utils.texturePacker.TexturePacker;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class Example02 implements IEventReceiver,IExample {
	
	PApplet applet;
	Renderer manager;
	KosmosRenderer kosmos;
	Layer3D renderer;
	Sprite3D contener;
	RoundPlane p;
	float angle;
	
	public Example02(PApplet appletObj){
		  applet = appletObj;
		  
		  
		  
		 
		  
		  int i,nbPlane = 10;
		  kosmos = new KosmosRenderer(applet);
		  manager = kosmos.createRenderer(false);
		  renderer = manager.createLayer3D( 100, true);
		 
		  RoundRectTextureObj roundRectTexture = (RoundRectTextureObj) renderer.addTextureImage(new RoundRectTextureObj(applet.color(80),applet.color(150),0,50));
		  renderer.start();
		  
		  contener = new Sprite3D();
		  
		 
		  p = new RoundPlane(renderer,400,400,roundRectTexture);
		  //p.x = -200;
		   
		 
		  renderer.appendObject(contener);
		  
		  contener.appendObject(p);
		  
		  renderer.camera.z = 0;
		  angle = 0;
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
		  
		
		//applet.directionalLight(255, 255, 255, 0, -1, -0.8f);
		// applet.ambientLight(200, 202, 202);
		
		
		 kosmos.update();
		 //p.z--;
		 p.rotationX+=0.001;
		 p.rotationY+=0.002;
		 p.rotationZ+=0.003;
		 
		 p.resize(applet.mouseX, applet.mouseY);
		 
		 //System.out.println(""+contener.rotationY);
		 
		 contener.rotationY = (float) (((applet.mouseX - 500.0) / 500.0) * applet.PI);
		
		
	}
	
}
