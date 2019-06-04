package kosmos.examples;

import processing.core.PApplet;
import kosmos.color.gradient.Gradient;
import kosmos.color.gradient.LinearGradient;
import kosmos.color.gradient.RadialGradient;
import kosmos.displayList.layers.KosmosRenderer;
import kosmos.displayList.layers.LayerBox2D;
import kosmos.displayList.layers.Renderer;
import kosmos.events.EventDispatcher3D;
import kosmos.events.IEventReceiver;
import kosmos.texture.TextureArea;
import kosmos.utils.box2dUtils.PhysicShape;

public class Example05 implements IEventReceiver,IExample {
	
	PApplet applet;
	KosmosRenderer kosmos;
	Renderer manager;
	LayerBox2D layer;
	
	TextureArea[] textures;
	int id = 0;
	
	Gradient gradient;
	
	public Example05(PApplet appletObj){
		applet = appletObj;
		applet.frameRate(60);
		kosmos = new KosmosRenderer(applet);
		manager = kosmos.createRenderer(true);
		layer = manager.createLayerBox2D();
		
		textures = new TextureArea[5];
		textures[0] = layer.addTextureImage(applet.loadImage("beer2.png"));
		textures[1] = layer.addTextureImage(applet.loadImage("ballon.png"));
		textures[2] = layer.addTextureImage(applet.loadImage("phoenix_transparent.png"));
		textures[3] = layer.addTextureImage(applet.loadImage("qFfCj.png"));
		textures[4] = layer.addTextureImage(applet.loadImage("Horned_King_transparent.png"));
		layer.start();
		
		
		PhysicShape shape = new PhysicShape(layer,200,300,textures[0],20);
		PhysicShape shape2 = new PhysicShape(layer,200,200,textures[1],15);
		PhysicShape shape3 = new PhysicShape(layer,80,120,textures[2],25);
		PhysicShape shape4 = new PhysicShape(layer,50,100,textures[3],30);
		PhysicShape shape5 = new PhysicShape(layer,150,250,textures[4],30);
		shape2.y = -250;
		shape2.x = -150;
		shape3.y = -100;
		shape3.x = 180;
		shape4.y = -50;
		shape4.x = -280;
		shape5.y = 180;
		shape5.x = 380;
		layer.camera.z = 0;
		layer.appendObject(shape);
		layer.appendObject(shape2);
		layer.appendObject(shape3);
		layer.appendObject(shape4);
		layer.appendObject(shape5); 
		
		
		layer.addEventListener(this);
		/*
		gradient = RadialGradient.getInstance();
		gradient.reset();
		gradient.addColor(255, 0, 0, 255, 0f);
		gradient.addColor(0, 255, 0, 255, 0.5f);
		gradient.addColor(0,0,255,255,1f);
		gradient.render(256, 256);
		*/
	}
	
	public void applyEvent(String eventAction, EventDispatcher3D obj){
		//must be overrided
		
		if(obj == layer && eventAction.equals("ON_MOUSE_RELEASE")){
			PhysicShape s = new PhysicShape(layer,(float)(50f+Math.random()*50f),(float)(50f+Math.random()*50f),textures[id++%5],50);
			s.x = applet.mouseX - applet.width/2;
			s.y = applet.mouseY - applet.height/2;
			
			System.out.println(s);
			layer.appendObject(s);
			
		}
	}
	
	
	public void update(){
		kosmos.update();
		
		//applet.image(gradient, 50, 50);
	}
	
}
