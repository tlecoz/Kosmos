package kosmos.displayList.layers;

import java.util.ArrayList;

import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;
import kosmos.displayList.DisplayObject3D;
import kosmos.displayList.Shape3D;
import kosmos.utils.box2dUtils.PhysicShape;
import kosmos.utils.box2dUtils.PhysicWall;

public class LayerBox2D extends Layer3D implements ILayer {
	
	
	public Box2DProcessing box2D;
	
	private PhysicWall[] walls;
	
	LayerBox2D(Renderer rendererObj){
		super(rendererObj);
		_constructor();
	}
	LayerBox2D(Renderer rendererObj,int nbTriangleMax,boolean useMouseEvent,boolean removeDefaultParticleTexture){
		super(rendererObj,nbTriangleMax,useMouseEvent,removeDefaultParticleTexture);
		_constructor();
	}
	
	
	
	private void _constructor(){
		box2D = new Box2DProcessing(renderer.getApplet());
		box2D.createWorld();
		box2D.setContinuousPhysics(true);
		box2D.setGravity(0, -30);
		initWalls();
	}
	
	
	private void initWalls(){
		
		float w = renderer.getWidth();
		float h = renderer.getHeight();
		walls = new PhysicWall[4];
		walls[0] = new PhysicWall(this,w/2,h,w,10,0);
		walls[1] = new PhysicWall(this,w/2,0,w,10,0);
		walls[2] = new PhysicWall(this,0,h/2,10,h,0);
		walls[3] = new PhysicWall(this,w,h/2,10,h,0);
		//wall = new PhysicWall(this,w,h/2,1,h,0);

	}
	 
	public Shape3D update(boolean enableMouseEvent){
		box2D.step();
		Shape3D s = super.update(enableMouseEvent);
		
		walls[0].draw();
		walls[1].draw();
		walls[2].draw();
		walls[3].draw();
		
		return s;
	}
	
	
}
