package kosmos.lights.lightOptions;

import kosmos.displayList.layers.Renderer;

public class Emissive extends Ambient {

	public Emissive(float red, float green, float blue) {
		super(red, green, blue);
	}
	public void apply(Renderer renderer){
		renderer.emissive(r,g,b);
	}
}
