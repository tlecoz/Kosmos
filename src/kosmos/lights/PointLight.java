package kosmos.lights;

import kosmos.displayList.layers.Renderer;

public class PointLight extends AmbientLight implements ILight {
	
	public PointLight(float red,float green,float blue,float px,float py,float pz){
		super(red,green,blue,px,py,pz);
	}
	
	public void apply(Renderer renderer){
		renderer.pointLight(r, g, b, x, y, z);
		applyOptions(renderer);
	}
	
}
