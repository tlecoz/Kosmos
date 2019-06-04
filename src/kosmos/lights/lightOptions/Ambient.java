package kosmos.lights.lightOptions;

import kosmos.displayList.layers.Renderer;

public class Ambient {
	
	public float r;
	public float g;
	public float b;
	
	public Ambient(float red,float green,float blue){
		r = red;
		g = green;
		b = blue;
	}
	
	Ambient(float grey){
		r = g = b = grey;
	}
	
	public void apply(Renderer renderer){
		renderer.ambient(r,g,b);
	}
}
