package kosmos.utils;

import kosmos.texture.TwoTriangleTextureArea;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

public class GraphicUtils extends Object{
	
	static private PGraphics g;
	static private PImage emptyImg;
	static private PApplet applet;
	//static private PImage tempImg;
	
	public GraphicUtils(PApplet _applet){
		
		applet = _applet;
		if(g == null){
			emptyImg = applet.createImage(2, 2, 2);
			emptyImg.loadPixels();
			int i,len = emptyImg.pixels.length;
			for(i=0;i<len;i++) emptyImg.pixels[i] = 0;
			emptyImg.updatePixels();
			
			g = applet.createGraphics(2048, 2048,applet.P2D);
		}
		
	}
	
	public static PGraphics getGraphics(){
		return g;
	}
	
	public static PImage getImage(int srcX,int srcY,int srcW,int srcH,int destX,int destY,int destW,int destH){
		
		PImage img = applet.createImage(destW,destH,2);
		img.copy(g,srcX, srcY, srcW, srcH, destX, destY, destW, destH);
		return img;
	}

	
	public static PImage transformImageToSquare(int size, PImage img){
		
		float w = img.width;
		float h = img.height;
		float r = ((float)size) / w;
		w *= r;
		h *= r;
		
		if(h > size){
			r = ((float)size) / h;
			w *= r;
			h *= r;
		}
		
		r = w / img.width;
		
		g.beginDraw();
		g.background(255,0);
		g.noStroke();
		g.noFill();
		g.pushMatrix();
		g.scale(r);
		g.translate((size-w)/2f, (size-h)/2f);
		g.image(img,0,0);
		g.popMatrix();
		g.endDraw();
		//PImage p = applet.createImage(size,size,2);
		
		img.resize(size, size);
		img.copy(g, 0, 0, size, size,0,0,size,size);
		return img;
		
		
		//p.copy(img, 0, 0, img.width, img.height,(int) ((size-img.width)/2f),(int)( (size-img.height)/2f), img.width, img.height);
		//return p;
		
	}
	
	
	
	public static void drawParticleTextureTop(PImage particleTexture, TwoTriangleTextureArea dest ){
		  

		  float d = (float)(Math.sqrt(dest.w*dest.w+dest.h*dest.h) / 2f - particleTexture.width / 2f -0.5f); 
		  float px,py;
		  double angle =  (Math.PI/4.0);
		  px = (float) Math.cos(angle) * d;
		  py = (float) Math.sin(angle) * d;
		  angle -= Math.PI/2.0;
		  px += (float) Math.cos(angle) * (particleTexture.height);
		  py += (float) Math.sin(angle) * (particleTexture.height);
		  
		  g.beginDraw();
		 
		  g.clear();
		  g.background(255,0);
		  g.noStroke();
		  g.noSmooth();
		  g.noFill();
		  g.pushMatrix();
		  g.translate((float) px,py);
		  g.rotate((float) (Math.PI/4.0f ) );
		  
		  g.image(particleTexture,0,0);
		  g.popMatrix();
		  g.endDraw();
		  
		  dest.temporaryImage.blend(g,0,0,dest.w,dest.h,0,0,dest.w, dest.h,0);
		  
		  g.clear();
	}
	
	public static void drawParticleTextureBottom(PImage particleTexture, TwoTriangleTextureArea dest){
		  
		  float d = (float)(Math.sqrt(dest.w*dest.w+dest.h*dest.h) / 2f + particleTexture.width / 2f-0.5f ); 
		  float px,py;
		  float angle = (float) (Math.PI/4.0f);
		  px = (float) Math.cos(angle) * d;
		  py = (float) Math.sin(angle) * d;
		  angle += Math.PI/2f;
		  px += (float) Math.cos(angle) * (particleTexture.height);
		  py += (float) Math.sin(angle) * (particleTexture.height);
		  
		  
		  g.beginDraw();
		  g.background(0,0);
		  g.clear();
		  g.noStroke();
		  g.noFill();
		  g.pushMatrix();
		  g.translate((float) px,py);
		  g.rotate((float) (Math.PI/4 + Math.PI) );
		  g.image(particleTexture,0,0);
		  g.popMatrix();
		  
		  //g.stroke(0);
		  //g.line(0, 0, dest.w, dest.h);
		  //g.line(0, dest.h,dest.w, 0);
		  
		  g.endDraw();
		  
		  
		  
		  dest.temporaryImage.blend(g,0,0,dest.w,dest.h,0,0,dest.w, dest.h,1);
		  
		  g.clear();
	}
}
