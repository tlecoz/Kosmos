package kosmos.lights.lightOptions;

import kosmos.displayList.layers.Renderer;

public class Specular extends Ambient {

	public Specular(float red, float green, float blue) {
		super(red, green, blue);
	}
	public void apply(Renderer renderer){
		renderer.specular(r,g,b);
	}
	
}
