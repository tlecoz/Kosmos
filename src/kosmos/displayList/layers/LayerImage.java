package kosmos.displayList.layers;

import kosmos.displayList.Shape3D;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

public class LayerImage implements ILayer {
	
	public PImage img;
	public String name;
	public float x;
	public float y;
	public float width;
	public float height;
	public float redMulti;
	public float greenMulti;
	public float blueMulti;
	public float alpha;
	
	protected Renderer renderer;
	
	
	
	public LayerImage(Renderer rendererObj,PImage image,float px,float py,float pw,float ph){
		_constructor(rendererObj,image,px,py,pw,ph);
	}
	
	public LayerImage(Renderer rendererObj,PImage image){		
		_constructor(rendererObj,image,0,0,image.width,image.height);
	}
	
	private void _constructor(Renderer rendererObj,PImage image,float px,float py,float pw,float ph){
		img = image;
		renderer = rendererObj;
		redMulti = greenMulti = blueMulti = alpha = 1.0f;
		x = px;
		y = py;
		width = pw;
		height = ph;
		name = "LayerImage";
	}
	
	
	
	
	
	
	public Shape3D update(boolean enableMouseEvent){
		renderer.tint(redMulti,greenMulti,blueMulti,alpha);
		renderer.image(img,x,y,width,height);
		renderer.noTint();
		return null;
	}
}
