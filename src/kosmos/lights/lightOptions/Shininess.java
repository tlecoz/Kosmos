package kosmos.lights.lightOptions;

import kosmos.displayList.layers.Renderer;

public class Shininess {
	
	float shine;
	
	Shininess(float shininess){
		shine = shininess;
	}
	
	public void apply(Renderer renderer){
		renderer.shininess(shine);
	}
}
