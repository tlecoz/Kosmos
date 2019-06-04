package kosmos.examples;

import kosmos.displayList.Sprite3D;
import kosmos.displayList.Object3D;
import kosmos.displayList.displayObjects.Plane;
import kosmos.displayList.layers.KosmosRenderer;
import kosmos.displayList.layers.Layer3D;
import kosmos.displayList.layers.Renderer;
import kosmos.events.EventDispatcher3D;
import kosmos.events.IEventReceiver;
import kosmos.texture.Texture3D;
import kosmos.texture.TextureArea;
import kosmos.utils.texturePacker.TexturePacker;
import kosmos.utils.texturePacker.TexturePacker;
import processing.core.PApplet;
import processing.core.PImage;

public class Example01 implements IEventReceiver,IExample {
	
	PApplet applet;
	Renderer manager;
	KosmosRenderer kosmos;
	Layer3D renderer;
	Sprite3D contener;
	PImage img;
	Plane plane;
	float angle;
	
	public Example01(PApplet appletObj){
		  applet = appletObj;
		  
		  
		  PImage img0 = applet.loadImage("beer.png");
		  PImage img1 = applet.loadImage("beer2.png");
		  PImage img2 = applet.loadImage("vectoBeer.png");
		  
		  TextureArea texture0,texture1,texture2;
		  //TexturePacker packer = new TexturePacker(1024,1200);
		  
		  
		  
		  //System.out.println(texture0+" : "+texture1+" : "+texture2);
		  
		  
		  int i,nbPlane = 1000;
		  kosmos = new KosmosRenderer(applet);
		  manager = kosmos.createRenderer(false);
	 	  renderer = manager.createLayer3D( nbPlane*2, true);
		  
		  texture0 = renderer.addTextureImage(img0);
		  texture1 = renderer.addTextureImage(img1);
		  texture2 = renderer.addTextureImage(img2);
		  
		  renderer.start();
		  
		  
		  
		  contener = new Sprite3D();
		  Plane p;
		  TextureArea currentTexture;
		  float scale;
		  
		  
		  
		  for(i=0;i<nbPlane;i++){
		    
		    if(i % 2 == 0) currentTexture = texture0; 
		    else currentTexture = texture1;
		    
		    scale = (float) (1.1- i/nbPlane);
		    p = plane = new Plane(renderer,currentTexture.w*scale,currentTexture.h*scale,currentTexture);
		    //p.overTexture = texture2;
		    //p.z = 500;
		    
		    
		    p.x = (float) (Math.cos((float) (i * 2.25f))*10000.0f)  ;
		    p.y = (float) (Math.sin((float) (i * 0.5f))*10000.0f) ;//-500 + random(1000);
		    p.z = i;//random(500);
		    p.redMulti = (float) Math.random()*2.5f;
		    p.greenMulti = (float) Math.random()*2.5f;
		    p.blueMulti = (float) Math.random()*2.5f;
		   // p.useLocalRotation = true;
		    p.addEventListener(this);

		    contener.appendObject(p); 
		  }
		  
		  
		  //contener.alpha = 0.75f;
				  
		  
		  renderer.appendObject(contener);
		  
		  renderer.camera.z = 100000.0f;
		  
		  
		  angle = 0;
		  
		  
		  
		//applet.directionalLight(255, 255, 255, 0.0f, 1f, -((float) (applet.mouseX / ((float)applet.width))));
		  
		
		  
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
		  
		  
		
		
		
		  kosmos.update();
		  
		  
		  //renderer.update();
		  
		  
		  angle += (float) (((applet.mouseX - 500.0) / 500.0) * 0.01);
		  
		  
		  int i,len = contener.children.size();
		  float len2 = (float) len;
		  Object3D o;
		  float rnd,rnd2;
		  
		 
		  if(applet.mousePressed){
			  
			  for(i=0;i<len;i++){
				 
				  rnd2 = (float) (0.01+Math.random()*0.01);
			      o = contener.children.get(i);
			      
			      
			      o.x -= o.x * rnd2 ;
				  o.y -= o.y * rnd2;
				  o.z -= o.z * rnd2;
			      o.rotationZ += ((float)((i+1)/len2) * 0.1);
			      o.rotationY += ((float)((i+1)/len2) * 0.1);
			      o.rotationZ += ((float)((i+1)/len2) * 0.1);
			  }
			  
		  }else{
		  
			  
			  for(i=0;i<len;i++){
				  rnd = (float) (0.01+Math.random()*0.05);
			      o = contener.children.get(i);
			      o.x -= (o.x -  (float) (Math.cos((float) (angle + i * 2.25f))*10000.0f) ) * 0.01 ;
				  o.y -= (o.y -  (float) (Math.sin((float) (angle + i * 2.5f))*10000.0f) ) * 0.01;//-500 + random(1000);
				  o.z -= (o.z -  (float) (-5000f+20f *((float)i) ) )*0.1;
			      o.rotationZ += ((float)((i+1)/len2) * 0.1);
			      o.rotationY += ((float)((i+1)/len2) * 0.1);
			      o.rotationZ += ((float)((i+1)/len2) * 0.1);
			  }
		  
		  }
		  
		  //plane.z = -4000 + applet.mouseX * 10;
		  //applet.println(renderer.allTriangles[4].z+" vs "+renderer.allTriangles[2].z+" vs "+renderer.allTriangles[0].z);
		  
		 contener.rotationY = (float) (((applet.mouseX - 500.0) / 500.0) * applet.PI*2);
		 contener.rotationX = (float) (((applet.mouseY - 500.0) / 500.0) * applet.PI*2);
		
		 //applet.fill(0);
		 //applet.rect(0, 0, 100, 30);
		 //applet.fill(255,255,255);
		 //applet.text(""+applet.frameRate,15,15);
		 
	}
	
}
