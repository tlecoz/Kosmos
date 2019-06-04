package kosmos.lights;

import kosmos.displayList.layers.Renderer;
import kosmos.events.EventDispatcher3D;
import kosmos.lights.lightOptions.Ambient;
import kosmos.lights.lightOptions.Emissive;
import kosmos.lights.lightOptions.LightSpecular;
import kosmos.lights.lightOptions.Shininess;
import kosmos.lights.lightOptions.Specular;

public class AmbientLight extends EventDispatcher3D implements ILight {
	
	public float r;
	public float g;
	public float b;
	
	protected boolean useAmbient;
	protected boolean useEmissive;
	protected boolean useLightSpecular;
	protected boolean useShininess;
	protected boolean useSpecular;
	
	protected Ambient ambient;
	protected Emissive emissive;
	protected LightSpecular lightSpecular;
	protected Shininess shininess;
	protected Specular specular;
	
	
	public AmbientLight(float red,float green,float blue,float px,float py,float pz){
		super();
		r = red;
		g = green;
		b = blue;
		x = px;
		y = py;
		z = pz;
		
		useAmbient = useEmissive = useLightSpecular = useShininess = useSpecular = false;
	}
	
	public void setAmbient(Ambient obj){
		ambient = obj;
		useAmbient = true;
	}
	public void setEmissive(Emissive obj){
		emissive = obj;
		useEmissive = true;
	}
	public void setLightSpeculat(LightSpecular obj){
		lightSpecular = obj;
		useLightSpecular = true;
	}
	public void setShininess(Shininess obj){
		shininess = obj;
		useShininess = true;
	}
	public void setSpecular(Specular obj){
		specular = obj;
		useSpecular = true;
	}
	
	protected void applyOptions(Renderer renderer){
		if(useAmbient) ambient.apply(renderer);
		if(useEmissive) emissive.apply(renderer);
		if(useLightSpecular) lightSpecular.apply(renderer);
		if(useShininess) shininess.apply(renderer);
		if(useSpecular) specular.apply(renderer);
	}
	
	public void apply(Renderer renderer){
		renderer.ambientLight(r, g, b, x, y, z);
		applyOptions(renderer);
	}
}
