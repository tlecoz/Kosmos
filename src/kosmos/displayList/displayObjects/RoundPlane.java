package kosmos.displayList.displayObjects;

import kosmos.displayList.Shape3D;
import kosmos.displayList.layers.Layer3D;
import kosmos.texture.TextureArea;
import kosmos.uiToolbox.textures.RoundRectTextureObj;

public class RoundPlane extends Shape3D {
	
	RoundRectTextureObj roundRectTexture;
	
	public RoundPlane(Layer3D renderer,float w,float h,RoundRectTextureObj textureObj){
	    
	    super(renderer,textureObj.center,9*2);
	    
	    roundRectTexture = textureObj;
	    
	    int corner = textureObj.corner;
	    float midW = (int)(w - corner * 2);
	    float midH = (int)(h - corner * 2);
	    
	   
	    
	    TextureArea texture;
	    float px = (int) (-(w/2.0));
	    float py = (int) (-(h/2.0));
	    
	    float startX = px;
	    
	    int i0,i1,i2,i3;
	    
	    nbVertex = 9*4;
	    
	    
	    //topLeft
	    texture = textureObj.topLeft;
	    startVertexId = i0 = renderer.getNewVertex(px       ,py       ,0,texture.minX,texture.minY);
	    i1 = renderer.getNewVertex(px+corner,py       ,0,texture.maxX,texture.minY);
	    i2 = renderer.getNewVertex(px       ,py+corner,0,texture.minX,texture.maxY);
	    i3 = renderer.getNewVertex(px+corner,py+corner,0,texture.maxX,texture.maxY);
	    
	    renderer.addNewTriangle(this,i0,i1,i2);
	    renderer.addNewTriangle(this,i1,i3,i2);
	    
	    
	    
	    //top
	    px += corner;
	    texture = textureObj.top;
	    i0 = renderer.getNewVertex(px       ,py       ,0,texture.minX,texture.minY);
	    i1 = renderer.getNewVertex(px+midW  ,py       ,0,texture.maxX,texture.minY);
	    i2 = renderer.getNewVertex(px       ,py+corner,0,texture.minX,texture.maxY);
	    i3 = renderer.getNewVertex(px+midW  ,py+corner,0,texture.maxX,texture.maxY);
	    
	    renderer.addNewTriangle(this,i0,i1,i2);
	    renderer.addNewTriangle(this,i1,i3,i2);
	    
	    
	    //topRight
	    px += midW;
	    texture = textureObj.topRight;
	    i0  = renderer.getNewVertex(px       ,py       ,0,texture.minX,texture.minY);
	    i1  = renderer.getNewVertex(px+corner,py       ,0,texture.maxX,texture.minY);
	    i2 = renderer.getNewVertex(px       ,py+corner,0,texture.minX,texture.maxY);
	    i3 = renderer.getNewVertex(px+corner,py+corner,0,texture.maxX,texture.maxY);
	    
	    renderer.addNewTriangle(this,i0,i1,i2);
	    renderer.addNewTriangle(this,i1,i3,i2);
	   
	    //------
	    
	    px = startX;
	    py += corner;
	    
	    //left
	    texture = textureObj.left;
	    i0 = renderer.getNewVertex(px       ,py       ,0,texture.minX,texture.minY);
	    i1 = renderer.getNewVertex(px+corner,py       ,0,texture.maxX,texture.minY);
	    i2 = renderer.getNewVertex(px       ,py+midH  ,0,texture.minX,texture.maxY);
	    i3 = renderer.getNewVertex(px+corner,py+midH  ,0,texture.maxX,texture.maxY);
	    
	    renderer.addNewTriangle(this,i0,i1,i2);
	    renderer.addNewTriangle(this,i1,i3,i2);
	   
	    
	    //center
	    px += corner;
	    texture = textureObj.center;
	    i0 = renderer.getNewVertex(px       ,py       ,0,texture.minX,texture.minY);
	    i1 = renderer.getNewVertex(px+midW  ,py       ,0,texture.maxX,texture.minY);
	    i2 = renderer.getNewVertex(px       ,py+midH  ,0,texture.minX,texture.maxY);
	    i3 = renderer.getNewVertex(px+midW  ,py+midH  ,0,texture.maxX,texture.maxY);
	    
	    renderer.addNewTriangle(this,i0,i1,i2);
	    renderer.addNewTriangle(this,i1,i3,i2);
	    
	    
	    
	    //right
	    px += midW;
	    texture = textureObj.right;
	    i0  = renderer.getNewVertex(px       ,py       ,0,texture.minX,texture.minY);
	    i1  = renderer.getNewVertex(px+corner,py       ,0,texture.maxX,texture.minY);
	    i2 = renderer.getNewVertex(px       ,py+midH  ,0,texture.minX,texture.maxY);
	    i3 = renderer.getNewVertex(px+corner,py+midH  ,0,texture.maxX,texture.maxY);
	    
	    renderer.addNewTriangle(this,i0,i1,i2);
	    renderer.addNewTriangle(this,i1,i3,i2);
	    
	    
	    //------
	    
	    px = startX;
	    py += midH-1;
	    
	    //bottomLeft
	    texture = textureObj.bottomLeft;
	    i0 = renderer.getNewVertex(px       ,py       ,0,texture.minX,texture.minY);
	    i1 = renderer.getNewVertex(px+corner,py       ,0,texture.maxX,texture.minY);
	    i2 = renderer.getNewVertex(px       ,py+corner,0,texture.minX,texture.maxY);
	    i3 = renderer.getNewVertex(px+corner,py+corner,0,texture.maxX,texture.maxY);
	    
	    renderer.addNewTriangle(this,i0,i1,i2);
	    renderer.addNewTriangle(this,i1,i3,i2);
	    
	    //bottom
	    px += corner;
	    texture = textureObj.bottom;
	    i0 = renderer.getNewVertex(px       ,py       ,0,texture.minX,texture.minY);
	    i1 = renderer.getNewVertex(px+midW  ,py       ,0,texture.maxX,texture.minY);
	    i2 = renderer.getNewVertex(px       ,py+corner,0,texture.minX,texture.maxY);
	    i3 = renderer.getNewVertex(px+midW  ,py+corner,0,texture.maxX,texture.maxY);
	    
	    renderer.addNewTriangle(this,i0,i1,i2);
	    renderer.addNewTriangle(this,i1,i3,i2);
	    
	    
	    //bottomRight
	    px += midW;
	    texture = textureObj.bottomRight;
	    i0 = renderer.getNewVertex(px       ,py       ,0,texture.minX,texture.minY);
	    i1 = renderer.getNewVertex(px+corner,py       ,0,texture.maxX,texture.minY);
	    i2 = renderer.getNewVertex(px       ,py+corner,0,texture.minX,texture.maxY);
	    i3 = renderer.getNewVertex(px+corner,py+corner,0,texture.maxX,texture.maxY);
	    
	    renderer.addNewTriangle(this,i0,i1,i2);
	    renderer.addNewTriangle(this,i1,i3,i2);
	    
	    
	  }  
	  
	
	public void updateRoundRectTexture(RoundRectTextureObj roundRectTexture){
		
		int startVertex = startVertexId;
		
		updateVertexUv(roundRectTexture.topLeft,startVertex);
		startVertex += 4;
		  
		updateVertexUv(roundRectTexture.top,startVertex);
		startVertex += 4;
		
		updateVertexUv(roundRectTexture.topRight,startVertex);
		startVertex += 4;
		
		updateVertexUv(roundRectTexture.left,startVertex);
		startVertex += 4;
		
		updateVertexUv(roundRectTexture.center,startVertex);
		startVertex += 4;
		
		updateVertexUv(roundRectTexture.right,startVertex);
		startVertex += 4;
		
		updateVertexUv(roundRectTexture.bottomLeft,startVertex);
		startVertex += 4;
		
		updateVertexUv(roundRectTexture.bottom,startVertex);
		startVertex += 4;
		
		updateVertexUv(roundRectTexture.bottomRight,startVertex);
		startVertex += 4;
		
		
	}
	
	
	public void resize(float w,float h){
		int corner = roundRectTexture.corner;
	    float midW = (int)(w - corner * 2);
	    float midH = (int)(h - corner * 2);
	    
	    float px = (int) (-(w/2.0));
	    float py = (int) (-(h/2.0));
	    
	    int startVertex = startVertexId;
	    
	    updateVertexXYZ(px,py,corner,corner,startVertex);
	    startVertex += 4;
	    
	    updateVertexXYZ(px+corner,py,midW,corner,startVertex);
	    startVertex += 4;
	    
	    updateVertexXYZ(px+corner+midW,py,corner,corner,startVertex);
	    startVertex += 4;
	    
	    updateVertexXYZ(px,py+corner,corner,midH,startVertex);
	    startVertex += 4;
	    
	    updateVertexXYZ(px+corner,py+corner,midW,midH,startVertex);
	    startVertex += 4;
	    
	    updateVertexXYZ(px+corner+midW,py+corner,corner,midH,startVertex);
	    startVertex += 4;
	    
	    updateVertexXYZ(px,py+corner+midH,corner,corner,startVertex);
	    startVertex += 4;
	    
	    updateVertexXYZ(px+corner,py+corner+midH,midW,corner,startVertex);
	    startVertex += 4;
	    
	    updateVertexXYZ(px+corner+midW,py+corner+midH,corner,corner,startVertex);
	    startVertex += 4;
	}
	
	
	
	private void updateVertexUv(TextureArea t, int startVertex){
		
		  int id = startVertex * 2;
		  
		  allVertexUV[id++] = t.minX;
		  allVertexUV[id++] = t.minY;
		  
		  allVertexUV[id++] = t.maxX;
		  allVertexUV[id++] = t.minY;
		  
		  allVertexUV[id++] = t.minX;
		  allVertexUV[id++] = t.maxY;
		  
		  allVertexUV[id++] = t.maxX;
		  allVertexUV[id++] = t.maxY;
		
	}
	
	private void updateVertexXYZ(float px,float py,float pw,float ph, int startVertex){
		
		  int id = startVertex * 3;
		  
		  allVertexXYZ[id++] = px;
		  allVertexXYZ[id++] = py;
		  allVertexXYZ[id++] = 0;
		  
		  allVertexXYZ[id++] = px + pw;
		  allVertexXYZ[id++] = py;
		  allVertexXYZ[id++] = 0;
		  
		  allVertexXYZ[id++] = px;
		  allVertexXYZ[id++] = py+ ph;
		  allVertexXYZ[id++] = 0;
		  
		  allVertexXYZ[id++] = px + pw;
		  allVertexXYZ[id++] = py + ph;
		  allVertexXYZ[id++] = 0;
		
	}
	
	
}
