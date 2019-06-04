package kosmos.texture;

import processing.core.PImage;

public class Texture3D extends PImage {
	
	private TextureArea[] areas;
	private String[] names;
	private int nb;
	
	public Texture3D(int w,int h,int mode,int nbSubTextureMax){
		super(w,h,mode);
		areas = new TextureArea[nbSubTextureMax];
		names = new String[nbSubTextureMax];
		nb = 0;
	}
	
	public Texture3D(int w,int h,int mode){
		super(w,h,mode);
		areas = new TextureArea[256];
		names = new String[256];
		nb = 0; 
	}
	public Texture3D(int w,int h){
		super(w,h,2);//2 -> ARGB
		areas = new TextureArea[256];
		names = new String[256];
		nb = 0;
	}
	public Texture3D(int nbSubTextureMax){
		super(2048,2048,2);//2 -> ARGB
		areas = new TextureArea[nbSubTextureMax];
		names = new String[nbSubTextureMax];
		nb = 0;
	}
	public Texture3D(){
		super(2048,2048,2);//2 -> ARGB
		areas = new TextureArea[256];
		names = new String[256];
		nb = 0;
	}
	
	public TextureArea createTextureArea(String imgName,int px,int py,int pw,int ph){
		TextureArea area = new TextureArea(this,px,py,pw,ph);
		areas[nb] = area;
		names[nb] = imgName;
		nb++;
		return area;
	}
	
	public TextureArea createTextureArea(int px,int py,int pw,int ph){
		TextureArea area = new TextureArea(this,px,py,pw,ph);
		areas[nb] = area;
		names[nb] = "area_"+nb;
		nb++;
		return area;
	}
	
	
	public TextureArea getTextureAreaByName(String name){
		int i;
		for(i=0;i<nb;i++) if(names[i] == name) return areas[i];
		return null;
	}
	
	public TextureArea getTextureAreaById(int id){
		return areas[id];
	}
	
	
}
