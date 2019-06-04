package kosmos.texture;

import kosmos.utils.GraphicUtils;
import processing.core.PImage;

public class TwoTriangleTextureArea extends TextureArea{
	
	public ParticleTextureUv top;
	public ParticleTextureUv bottom;
	
	
	
	
	public TwoTriangleTextureArea(int twoTriangleTextureW,int twoTriangleTextureH){
		super(twoTriangleTextureW,twoTriangleTextureH);
	}
	
	public TwoTriangleTextureArea(int particleRectSize){
		super((int)( (particleRectSize + 2) * Math.sqrt(2f)*1.5f),(int)( (particleRectSize + 2) * Math.sqrt(2f)*1.5f));
		temporaryImage = new PImage(w,h,2);
	}
	
	
	
	public TwoTriangleTextureArea(PImage particleImg){
		
		super((int)( (Math.max(particleImg.width,particleImg.height) + 2) * Math.sqrt(2f)*1.5f),(int)( (Math.max(particleImg.width,particleImg.height) + 2) * Math.sqrt(2f)*1.5f));
		temporaryImage = new PImage(w,h,2);
		
		GraphicUtils.transformImageToSquare(Math.max(particleImg.width,particleImg.height), particleImg);
		GraphicUtils.drawParticleTextureTop(particleImg, this);
		
		top = new ParticleTextureUv(this);
	}
	
	private static int getTextureSize(ParticleTextureUv particle_1,ParticleTextureUv particle_2){
		if(null == particle_2) return (int) Math.max(particle_1.getTemporaryImage().width, particle_1.getTemporaryImage().height);
		
		PImage particleImg_1 = particle_1.getTemporaryImage();
		PImage particleImg_2 = particle_2.getTemporaryImage();
		
		
		
		return (int)( (Math.max(Math.max(particleImg_1.width,particleImg_2.width),Math.max(particleImg_1.height,particleImg_2.height)) + 2) * Math.sqrt(2f)*1.5f);
	}
	
	public TwoTriangleTextureArea(PImage mainTexture,ParticleTextureUv particle1,ParticleTextureUv particle2){
		
		super(getTextureSize(particle1,particle2),getTextureSize(particle1,particle2));
		
		System.out.println(getTextureSize(particle1,particle2)+" : "+particle1.getTemporaryImage().width+" : "+particle1.getTemporaryImage().height);
		
		int d =(int)Math.floor(getTextureSize(particle1,particle2) );
		
		setTemporaryImage(new PImage(d,d,2));
		setMainTexture(mainTexture);
		
		d = (int)(((float) d-2) / ((float)(Math.sqrt(2f)*1.5f)) );
		
		System.out.println(d);
		
		PImage particleImg_1 = GraphicUtils.transformImageToSquare((int)d, particle1.getTemporaryImage());
		GraphicUtils.drawParticleTextureTop(particleImg_1, this);
		//particle1.clearTemporaryImage();
		
		
		if(null != particle2){
			PImage particleImg_2 = GraphicUtils.transformImageToSquare((int)d, particle2.getTemporaryImage());
			GraphicUtils.drawParticleTextureBottom(particleImg_2, this);
		    //particle2.clearTemporaryImage();
		}
		
		
		if(particle2 != null){
			top = particle2;
			top.setTexture(this);
		}
		if(particle1 != null){
			bottom = particle1;
			bottom.setTexture(this);
		}
		
	}
	
	
	public ParticleTextureUv createFirstParticleTexture(PImage particleImg){
		GraphicUtils.transformImageToSquare(Math.max(particleImg.width,particleImg.height), particleImg);
		GraphicUtils.drawParticleTextureTop(particleImg, this);
		top = new ParticleTextureUv(this);
		return top;
	}
	
	public ParticleTextureUv createSecondParticleTexture(PImage particleImg){
		GraphicUtils.transformImageToSquare(Math.max(particleImg.width,particleImg.height), particleImg);
		GraphicUtils.drawParticleTextureBottom(particleImg, this);
		bottom = new ParticleTextureUv(this);
		return bottom;
	}
	
	
	protected void initUvs(){
		if(null != bottom) bottom.initUV(minX, minY,maxX, minY,maxX, maxY);
		if(null != top) top.initUV( maxX, maxY,minX, maxY,minX, minY);
	}
	
	public void drawTemporaryOnTexture(){
	    super.drawTemporaryOnTexture();
		 
	    initUvs();

	}
	
	public TwoTriangleTextureArea clone(PImage newMainTexture){
		TwoTriangleTextureArea t = (TwoTriangleTextureArea) super.clone(newMainTexture);
		if(null!=top) t.top = top.clone();
		if(null!=bottom) t.bottom = bottom.clone();
		return t;
	}
}
