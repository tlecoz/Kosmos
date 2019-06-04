package kosmos.lights;

import kosmos.displayList.layers.Renderer;

public class SpotLight extends AmbientLight implements ILight {
	
	public float directionX;
	public float directionY;
	public float directionZ;
	public float angle;
	public float concentration;
	
	public SpotLight(float red,float green,float blue,float px,float py,float pz,float dirX,float dirY,float dirZ,float a,float concentrationValue){
		super(red,green,blue,px,py,pz);
		
		directionX = dirX;
		directionY = dirY;
		directionZ = dirZ;
		angle = a;
		concentration = concentrationValue;
	}
	
	public void apply(Renderer renderer){
		renderer.spotLight(r, g, b, x, y, z,directionX,directionY,directionZ,angle,concentration);
		applyOptions(renderer);
	}
	
}
