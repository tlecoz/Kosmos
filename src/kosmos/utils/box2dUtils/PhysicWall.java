package kosmos.utils.box2dUtils;

import kosmos.displayList.layers.LayerBox2D;
import kosmos.displayList.layers.Renderer;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;

import processing.core.PApplet;
import processing.core.PGraphics;
import shiffman.box2d.Box2DProcessing;


public class PhysicWall {
	
	
	
	float x;
	float y;
	float w;
	float h;
	float sw2;
	float sh2;
	
	Body body;
	//PApplet applet;
	Renderer renderer;
	
	public PhysicWall(LayerBox2D layer,float _x,float _y, float _w, float _h, float _a) {
		
		renderer = layer.getRenderer();
		
	    sw2 = renderer.getWidth()/2f;
	    sh2 = renderer.getHeight()/2f;
		x = -sw2 +_x;
	    y = -sh2 + _y;
	    w = _w;
	    h = _h;

	    Box2DProcessing box2d = layer.box2D;
	    
	    
	    
	    PolygonShape ps = new PolygonShape();
	    ps.setAsBox(box2d.scalarPixelsToWorld(w/2), box2d.scalarPixelsToWorld(h/2));

	    BodyDef bd = new BodyDef();
	    bd.type = BodyType.STATIC;
	    bd.angle = _a;
	    bd.position.set(box2d.coordPixelsToWorld( x+sw2, y+sh2));
	    
	    body = box2d.createBody(bd);
	    
	    body.createFixture(ps,1);
	  }
	
	public void draw(){
		
		
		renderer.fill(0);
		renderer.stroke(0);
		renderer.strokeWeight(1);
		renderer.rectMode(renderer.CENTER);
	    float a = body.getAngle();
	    renderer.pushMatrix();
	    renderer.translate(sw2+x,sh2+y);
	    renderer.rotate(-a);
	    renderer.rect(0,0,w,h);
	    renderer.popMatrix();
	}
	
}
