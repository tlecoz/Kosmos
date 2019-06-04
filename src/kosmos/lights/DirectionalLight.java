package kosmos.lights;

import kosmos.displayList.layers.Renderer;



public class DirectionalLight extends AmbientLight implements ILight {
	
	public DirectionalLight(float red,float green,float blue,float directionX,float directionY,float directionZ){
		super(red,green,blue,directionX,directionY,directionZ);
	}
	
	public void apply(Renderer renderer){
		renderer.directionalLight(r, g, b, x, y, z);
		applyOptions(renderer);
	}
	
}
