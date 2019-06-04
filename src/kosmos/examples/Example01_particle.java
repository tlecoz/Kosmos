package kosmos.examples;

import kosmos.displayList.DisplayObject3D;
import kosmos.displayList.Particle3D;
import kosmos.displayList.Sprite3D;
import kosmos.displayList.Object3D;
import kosmos.displayList.displayObjects.Plane;
import kosmos.displayList.layers.KosmosRenderer;
import kosmos.displayList.layers.Layer3D;
import kosmos.displayList.layers.Renderer;
import kosmos.events.EventDispatcher3D;
import kosmos.events.IEventReceiver;
import kosmos.lights.AmbientLight;
import kosmos.lights.DirectionalLight;
import kosmos.lights.lightOptions.Ambient;
import kosmos.lights.lightOptions.Emissive;
import kosmos.lights.lightOptions.LightSpecular;
import kosmos.lights.lightOptions.Specular;
import kosmos.texture.ParticleTextureUv;
import kosmos.texture.Texture3D;
import kosmos.texture.TextureArea;
import kosmos.utils.GraphicUtils;
import kosmos.utils.texturePacker.TexturePacker;
import processing.core.PApplet;
import processing.core.PImage;

public class Example01_particle implements IEventReceiver,IExample {
	
	PApplet applet;
	Renderer manager;
	KosmosRenderer kosmos;
	Layer3D renderer;
	Sprite3D contener;
	PImage img;
	Particle3D particle;
	float angle;
	
	public Example01_particle(PApplet appletObj){
		  applet = appletObj;
		  
		  
		  PImage img0 = applet.loadImage("beer.png");
		  PImage img1 = applet.loadImage("beer2.png");
		  PImage img2 = applet.loadImage("whiteSquare.jpg");
		  
		  TextureArea texture0,texture1,texture2;
		  //TexturePacker packer = new TexturePacker(1024,1200);
		  
		  
		  
		  //System.out.println(texture0+" : "+texture1+" : "+texture2);
		  
		  
		  int i,nbParticle = 10000;
		  
		  kosmos = new KosmosRenderer(applet);
		  manager = kosmos.createRenderer(false);//new Renderer(applet);
		  renderer = manager.createLayer3D( nbParticle,false,false);
		  renderer.addLight(new AmbientLight(0.5f,0.5f,0.5f,0f,0f,0f));
		  
		  DirectionalLight dl = new DirectionalLight(1f,1f,1f,-0.5f,0.1f,-0.5f); 
		  dl.setAmbient(new Ambient(0.25f,0.05f,0.55f));
		  dl.setEmissive(new Emissive(0.25f,0.5f,0.75f));
		  dl.setLightSpeculat(new LightSpecular(0f,0f,1f));
		  dl.setSpecular(new Specular(0.1f,1f,0.2f));
		  renderer.addLight(dl);
		  
		  ParticleTextureUv uv0 = renderer.addParticleImage(img0);
		  ParticleTextureUv uv1 = renderer.addParticleImage(img1);
		  renderer.addParticleImage(img2);
		  
		  renderer.start();
		  
		 
		  manager.autoclear = false;
		  
		  
		  contener = new Sprite3D();
		  Particle3D p;
		  ParticleTextureUv currentTexture;
		  float scale;
		  float pct;
		  int len2 = nbParticle - 1;
		  float id;
		  for(i=0;i<nbParticle;i++){
		    
		    if(i % 2 == 0) currentTexture = uv0; 
		    else currentTexture = uv1;
		    
		    id = (float)i;
		   
		    scale = (float) (1.1- i/nbParticle);
		    
		    float pw = (float) currentTexture.w *scale*0.25f;
		    float ph = (float) currentTexture.h *scale*0.25f;
		    
		    //float pw = (float) Math.random() * 10f;//currentTexture.w *scale;
		    //float ph = 100 + (float)Math.random()*1000f;//currentTexture.h *scale;
		    
		    p = particle = new Particle3D(renderer,currentTexture,pw,ph);
		    
		    
		    pct = id/((float)len2)*0.1f;
		    
		    p.x = (float) (Math.cos( (angle + id * 0.225f))*5000.0f)  * 0.0001f ;
			p.y = (float) (Math.sin( (angle + id * 0.25f))*5000.0f)  * 0.00011f;//-500 + random(1000);
			p.z = -5000+(angle + id*0.5f ); //* pct * 0.5f ;
		    p.rotationX = (float)(id/len2) * 0.01f;
		    p.rotationY = (float)(id/len2) * 0.012f;
		    p.rotationZ = (float)(id/len2) * 0.013f;
		    
		    //p.redMulti = (float) Math.random();
			//p.greenMulti = (float) Math.random();
			//p.blueMulti = (float) Math.random();
			//p.alpha = 0.95f;
		    
		    //p.alpha = (float) Math.random();
		    
		    p.useLocalRotation = true;
		    p.addEventListener(this);

		    contener.appendObject(p); 
		  }
		  
		  contener.useLocalParentRotation = true;
		  //contener.redMulti = 0.5f;
		  //contener.greenMulti = 0.25f;
		  //contener.blueMulti = 0.125f;
		 // contener.alpha = 0.5f;
				  
		  
		  renderer.appendObject(contener);
		  
		  renderer.camera.z = 0.0f;
		  
		  
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
		
		
		//applet.image(renderer.texturePacker, 0, 0);
		
		kosmos.update();
	    
		 angle -= (float) (angle - ((applet.mouseX - 500.0) / 500.0) * Math.PI ) * 0.1;
		  
		  
		  int i,len = contener.children.size();
		  float len2 = (float) len;
		  DisplayObject3D o;
		  float rnd,rnd2;
		  float pct;
		 
		  if(applet.mousePressed){
			  
			  //contener.blueMulti -= (contener.blueMulti - 0.25f) * 0.025f;
			  //contener.redMulti -= (contener.redMulti - 1.0f) * 0.025f;
			  //contener.rotationZ -= (contener.rotationZ - angle*15f) * 0.03f ;
			  for(i=0;i<len;i++){
				 
				  //rnd2 = (float) (0.01+Math.random()*0.01);
			      o = contener.children.get(i);
			      pct = (float) (i+1)/((float)len)*0.1f;
			      
			      o.x -= (o.x -  (float) (Math.cos((float) (angle + i * 2.25f))*10000.0f) ) * 0.01 ;
				  o.y -= (o.y -  (float) (Math.sin((float) (angle + i * 2.5f))*10000.0f) ) * 0.01;//-500 + random(1000);
				  o.z -= (o.z -  (float) (-5000f+20f *((float)i) ) )*0.1;
			      o.rotationZ += ((float)((i+1)/len2) * 0.001);
			      o.rotationY += ((float)((i+1)/len2) * 0.001);
			      o.rotationZ += ((float)((i+1)/len2) * 0.001);
			  }
			  
		  }else{
		  
			  //contener.blueMulti -= (contener.blueMulti - 1.0f) * 0.05;
			  //contener.redMulti -= (contener.redMulti - 1.0f) * 0.04;
			  //contener.greenMulti -= (contener.greenMulti - 1.0f) * 0.03;
			  
			  for(i=0;i<len;i++){
				  pct = (float) (i+1)/((float)len)*0.1f;
			      o = contener.children.get(i);
			      o.x -= (o.x -  (float) (Math.cos((float) (angle + i * 2.25f))*5000.0f) ) * 0.01 ;
				  o.y -= (o.y -  (float) (Math.sin((float) (angle + i * 1.5f))*5000.0f) ) * 0.01;//-500 + random(1000);
				  o.z -= (o.z -  (float) (-5000f+0.5f *((float)i) ) )*0.01;
			      o.rotationZ += ((float)((i+1)/len2) * 0.1);
			      o.rotationY += ((float)((i+1)/len2) * 0.12);
			      o.rotationZ += ((float)((i+1)/len2) * 0.13);
			  }
		  
		  }
		  
		  //plane.z = -4000 + applet.mouseX * 10;
		  //applet.println(renderer.allTriangles[4].z+" vs "+renderer.allTriangles[2].z+" vs "+renderer.allTriangles[0].z);
		  
		 contener.rotationY = (float) (applet.PI + ((applet.mouseX - 500.0) / 500.0) * applet.PI*2);
		 //contener.rotationX = (float) (((applet.mouseY - 500.0) / 500.0) * applet.PI*2);
		  renderer.camera.z = (float) (applet.mouseY  * -25f);
		
		//particle.z = ((applet.mouseX - (applet.width / 2f)) / (applet.width/2f)) * 5000f;
		//System.out.println(particle.z);
		//contener.rotationX = ((applet.mouseX - (applet.width / 2f)) / (applet.width/2f)) * ((float)(Math.PI/8f));
		
		 applet.fill(0);
		 applet.rect(0, 0, 100, 30);
		 applet.fill(255,255,255);
		 applet.text(""+applet.frameRate,15,15);
		 
	}
	
	
	
}
