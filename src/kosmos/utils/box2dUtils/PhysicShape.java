package kosmos.utils.box2dUtils;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;

import processing.core.PGraphics;
import processing.core.PImage;
import shiffman.box2d.Box2DProcessing;
import kosmos.displayList.displayObjects.Plane;
import kosmos.displayList.layers.LayerBox2D;
import kosmos.texture.TextureArea;
import kosmos.utils.AppletInstance;

public class PhysicShape extends Plane {
	
	public static int index; 
	
	public Body body;
	private Box2DProcessing box2d;
	private float boxW;
	private float boxH;
	private boolean started;
	private boolean autoStarter;
	
	private float sw2;
	private float sh2;
	private Vec2[] vertices;
	private int limitVertex;
	
	public boolean debugPhysic = false;
	
	
	
	
	public PhysicShape(LayerBox2D layerBox2d,float w,float h,TextureArea texture,boolean autoStart,int nbVertexMax){
		super(layerBox2d,w,h,texture);
		box2d = layerBox2d.box2D;
		boxW = w;
		boxH = h;
		limitVertex = nbVertexMax;
		//z = index++ / 10000.f;
		autoStarter = autoStart;
		visible = started = false;
		sw2 = layerBox2d.stageW/2;
		sh2 = layerBox2d.stageH/2;
	}
	
	
	
	public PhysicShape(LayerBox2D renderer,float w,float h,TextureArea texture,int nbVertexMax){
		super(renderer,w,h,texture);
		box2d = renderer.box2D;
		boxW = w;
		boxH = h;
		autoStarter = true;
		limitVertex = nbVertexMax;
		visible = started = false;
		sw2 = renderer.stageW/2;
		sh2 = renderer.stageH/2;
	}
	
	public PhysicShape(LayerBox2D renderer,float w,float h,TextureArea texture){
		super(renderer,w,h,texture);
		box2d = renderer.box2D;
		boxW = w;
		boxH = h;
		autoStarter = true;
		limitVertex = 50;
		visible = started = false;
		sw2 = renderer.stageW/2;
		sh2 = renderer.stageH/2;
	}
	
	
	
	public void destroyPhysicBody(){
		box2d.destroyBody(body);
	}
	
	private void addToPhysicWorld(){
		System.out.println("add to world");
		//vertices = texture.computePhysicVertex(boxW, boxH);
		body = texture.createPhysicBody(box2d, limitVertex, x+sw2, y+sh2, boxW, boxH);
		
		body.setLinearVelocity(new Vec2((float)(-5f+Math.random()*10f),(float)( 2f+Math.random()*3f)));
	    body.setAngularVelocity((float)(-5+Math.random()*10f));
		
		visible = true;
		started = true;
	}
	
	

	
	
	public void updateVertexPosition(){
		if(started == false && autoStarter == true){
			addToPhysicWorld();
		}
		
		
		
		
		
		
		Vec2 pos = box2d.getBodyPixelCoord(body);
	    // Get its angle of rotation
	    float a = body.getAngle();
	    x = pos.x- sw2;
		y = pos.y- sh2;
		rotationZ = -body.getAngle();
			    
	    if(debugPhysic){
	    	
	    	
		    Fixture f = body.getFixtureList();
		    PolygonShape ps;
		    while(f != null ){
		    	ps = (PolygonShape) f.getShape();
		    	renderer.rectMode(renderer.CENTER);
		    	renderer.pushMatrix();
		    	renderer.translate(pos.x, pos.y);
		    	renderer.rotate(-a);
		    	renderer.fill(175);
		    	renderer.stroke(0);
		    	renderer.beginShape();
	
			    for (int i = 0; i < ps.getVertexCount(); i++) {
			      Vec2 v = box2d.vectorWorldToPixels(ps.getVertex(i));
			      renderer.vertex(v.x, v.y);
			    }
			    renderer.endShape(renderer.CLOSE);
			    renderer.popMatrix();
			    
			    f = f.getNext();
		    }
		
	    }
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//Vec2 pos = box2d.getBodyPixelCoord(body);
		
		
		//System.out.println("y = "+y+" : "+body.getPosition());
		
		
		
		
		
		super.updateVertexPosition();
	}
}
