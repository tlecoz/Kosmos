package kosmos.texture;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import kosmos.utils.Alert;
import kosmos.utils.borderFinder.BorderFinder;
import kosmos.utils.borderFinder.BorderPt;
import kosmos.utils.borderFinder.borderSimplificator.BorderSimplificator;
import kosmos.utils.box2dUtils.PhysicVertexBuilder;
import kosmos.utils.box2dUtils.earCutting.ShortArray;
import processing.core.PImage;
import shiffman.box2d.Box2DProcessing;

public class TextureArea implements Comparable{
	  
	 public int x;
	 public int y;
	 public int w;
	 public int h;
	 public float minX;
	 public float minY;
	 public float maxX;
	 public float maxY;
	 
	 public int compareData;
	 
	 public PImage textureImage;
	 public PImage temporaryImage;
	 public int tempX;
	 public int tempY;
	 public int tempW;
	 public int tempH;
	 
	 public int nbPhysicVertexMax=30;
	 private PhysicVertexBuilder vertexBuilder;
	 private BorderPt[] _border;
	 private int[] _physicIndices;
	 
	 
	 
	 public TextureArea(PImage textureImg,int px,int py,int pw,int ph){
	   
	   textureImage = textureImg;
	   
	   x = px;
	   y = py;
	   w = pw;
	   h = ph;
	   
	   tempX = tempY = tempW = tempH = 0;
	   
	   minX = (float) (x);
	   minY = (float) (y);
	   maxX = (float) (x + w);
	   maxY = (float) (y + h);
	 }
	 
	 
	 
	 
	 public TextureArea(PImage textureImg){
		 x = y = w = h = 0;
		 minX = maxX = minY = maxY = 0.0f;
		 textureImage = textureImg;
		 tempX = tempY = tempW = tempH = 0;
	 }
	 public TextureArea(int pw,int ph){
		 x = y = 0;
		 w = pw; 
		 h = ph;
		 tempX = tempY = tempW = tempH = 0;
		 minX = maxX = minY = maxY = 0.0f;
		 //textureImage = textureImg;
	 }
	 public TextureArea(PImage textureImg,PImage temporaryImg){
		 x = y = 0; 
		 w = temporaryImg.width; 
		 h = temporaryImg.height;
		 minX = maxX = minY = maxY = 0.0f;
		 textureImage = textureImg;
		 temporaryImage = temporaryImg;
		 tempX = tempY = 0;
		 tempW = temporaryImage.width;
		 tempH = temporaryImage.height;
	 }
	 public TextureArea(PImage textureImg,int pw,int ph){
		 textureImage = textureImg;
		 w = pw;
		 h = ph;
		 x = y = 0;
		 minX = maxX = minY = maxY = 0.0f;
		 tempX = tempY = tempW = tempH = 0;
	 }
	 
	 public void setMainTexture(PImage source){
		 textureImage = source;
	 }
	 public void setTemporaryImage(PImage temp){
		 temporaryImage = temp;
		 tempW = temp.width;
		 tempH = temp.height;
	 }
	 public void setTemporaryImage(PImage temp,int imgX,int imgY,int imgW,int imgH){
		 temporaryImage = temp;
		 tempW = imgW;
		 tempH = imgH;
		 tempX = imgX;
		 tempY = imgY;
	 }
	 
	 
	 public void drawTemporaryOnTexture(){
		 
		 
		 if(temporaryImage != null && tempW > 0 && tempH > 0) textureImage.copy(temporaryImage,tempX,tempY,tempW,tempH,x,y,w,h); 
		 
		 //update(temporaryImage);
		 minX = (float) (x+1);
		 minY = (float) (y+1);
		 maxX = (float) (x + w-2);
		 maxY = (float) (y + h-2);
		 
		
		 temporaryImage = textureImage;
		 tempX = x;
		 tempY = y;
		 tempW = w;
		 tempH = h;
	 }
	 
	 public BorderPt[] getBorder(){
		 if(_border == null) _border = BorderFinder.getInstance().getBorderImage(this);
		 return _border;
	 }
	 
	 public int[] getPhysicTriangleIndices(){
		 if(_physicIndices == null) Alert.error("TextureArea", "getTriangleIndices", "You must call 'computePhysicVertex' before calling 'getTriangleIndices' ");;
		 return _physicIndices;
	 }
	 
	 public Vec2[] computePhysicVertex(float displayObjectWidth,float displayObjectHeight){
		if(vertexBuilder == null){
			vertexBuilder = new PhysicVertexBuilder(nbPhysicVertexMax,getBorder());
			_physicIndices = vertexBuilder.getTriangleIndices();
		}
		return vertexBuilder.getPhysicVertex(displayObjectWidth, displayObjectHeight);
	 }
	 
	 public Vec2[] computePhysicVertex(int nbVertexMax,float displayObjectWidth,float displayObjectHeight){
		if(vertexBuilder == null){
			vertexBuilder = new PhysicVertexBuilder(nbVertexMax,getBorder());	
			_physicIndices = vertexBuilder.getTriangleIndices();
		}
		return vertexBuilder.getPhysicVertex(displayObjectWidth, displayObjectHeight);
	 }
	 
	 
	 public Body createPhysicBody(Box2DProcessing box2d,int nbVertexMax,float displayObjectX,float displayObjectY,float displayObjectWidth,float displayObjectHeight){
		 if(vertexBuilder == null){
			 System.out.println("ooooooooo");
			 computePhysicVertex(nbVertexMax,displayObjectWidth,displayObjectHeight);
		 }
		 return vertexBuilder.createBox2dBody(box2d,this, displayObjectX, displayObjectY, displayObjectWidth, displayObjectHeight);
	 }
	 
	 
	 
	 
	 
	 
	 public TextureArea clone(PImage newMainTexture){
		 TextureArea t = new TextureArea(newMainTexture,x,y,w,h);
		 t.temporaryImage = temporaryImage;
		 t.tempX = tempX;
		 t.tempY = tempY;
		 t.tempW = tempW;
		 t.tempH = tempH;
		 return t;
	 }
	 
	 public void update(PImage src){
	    if(src != null) textureImage.copy(src,0,0,src.width,src.height,x,y,w,h); 
	 }
	 
	 public void update(PImage src,Boolean useAlpha){
	    if(useAlpha == true){
	      textureImage.blend(src,0,0,src.width,src.height,x,y,w,h,1); 
	    }else{
	      textureImage.copy(src,0,0,src.width,src.height,x,y,w,h); 
	    }
	 }
	 public void update(PImage src,int srcX,int srcY,int srcW,int srcH,Boolean useAlpha){
	    if(useAlpha == true){
	      textureImage.blend(src,srcX,srcY,srcW,srcH,x,y,w,h,1); 
	    }else{
	      textureImage.copy(src,srcX,srcY,srcW,srcH,x,y,w,h); 
	    }
	 }
	 
	 public int compareTo(Object o){
		return ((TextureArea) o).compareData - compareData;
	}
	 
	 
	 
}

	


