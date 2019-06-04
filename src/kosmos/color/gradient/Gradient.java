package kosmos.color.gradient;

import processing.core.PImage;

public class Gradient extends PImage {
	
	protected int[] r;
	protected int[] g;
	protected int[] b;
	protected int[] a;
	protected float[] ratios;
	protected int nb;
	
	Gradient(){
		super(2,2,2);
		r = new int[16];
		g = new int[16];
		b = new int[16];
		a = new int[16];
		ratios = new float[16];
		nb = 0;
	}
	
	public void reset(){
		nb = 0;
		for(int i =0;i<16;i++) ratios[i] = 1;
	}
	public void addColor(int color, float ratio){
		a[nb] = color >>> 24;
		r[nb] = color >>> 16 & 0xFF;
		g[nb] = color >>>  8 & 0xFF;
		b[nb] = color & 0xFF;
		ratios[nb] = ratio;
		nb++;
	}
	
	public void addColor(int red,int green,int blue,int alpha, float ratio){
		a[nb] = alpha;
		r[nb] = red;
		g[nb] = green;
		b[nb] = blue;
		ratios[nb] = ratio;
		nb++;
	}
	
	public void render(int w,int h){
		//must be overrided;
	}
}
