package kosmos.lights.lightOptions;

import kosmos.displayList.layers.Renderer;

public class LightSpecular extends Ambient {

	public LightSpecular(float red, float green, float blue) {
		super(red, green, blue);
	}
	public void apply(Renderer renderer){
		renderer.lightSpecular(r,g,b);
	}
}
