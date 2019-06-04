package kosmos.texture;

import processing.core.PImage;

public class ParticleTextureUv implements Comparable{
	
	public float u0;
	public float v0;
	public float u1;
	public float v1;
	public float u2;
	public float v2;
	
	public float w;
	public float h;
	protected TwoTriangleTextureArea texture;
	
	
	protected PImage _temporaryImage;
	protected int _surface;
	
	public ParticleTextureUv(TwoTriangleTextureArea textureSource){
		texture = textureSource;
	}
	
	public ParticleTextureUv(PImage particleImg){
		_temporaryImage = particleImg;
		_surface = particleImg.width * particleImg.height;
		w = particleImg.width;
		h = particleImg.height;
	}
	
	
	public void initUV(float x0,float y0,float x1,float y1,float x2,float y2){
		//System.out.println("UV>> "+x0+","+y0+","+x1+","+y1+","+x2+","+y2);
		    
		u0 = x0;
		v0 = y0;
		u1 = x1;
		v1 = y1;
		u2 = x2;
		v2 = y2;
	}
	
	
	public int compareTo(Object o){
		return ((ParticleTextureUv) o).getSurface() - _surface;
	}
	
	
	public ParticleTextureUv clone(){
		ParticleTextureUv o = new ParticleTextureUv(texture);
		o.initUV(u0,v0,u1,v1,u2,v2);
		return o;
	}
	
	public void setTexture(TwoTriangleTextureArea t){
		texture = t;
	}
	
	public TwoTriangleTextureArea getTexture(){
		return texture;
	}
	
	public void clearTemporaryImage(){
		_temporaryImage = null;
	}
	
	public int getSurface(){
		return _surface;
	}
	
	
	public PImage getTemporaryImage(){
		return _temporaryImage;
	}
}
