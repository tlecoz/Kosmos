package kosmos.displayList.displayObjects;

import kosmos.displayList.Shape3D;
import kosmos.displayList.layers.Layer3D;
import kosmos.texture.TextureArea;

public class Plane extends Shape3D {
	  
	  //public TextureArea overTexture;  
	  //public TextureArea normalTexture;
	  
	  public Plane(Layer3D layer,float w,float h,TextureArea texture){
	    
	    super(layer,texture,2);
	    
	    //normalTexture = texture;
	    
	    float w2 = (float) (w/2.0);
	    float h2 = (float) (h/2.0);
	    
	    int i0 = layer.getNewVertex(-w2,-h2,0,texture.minX,texture.minY);
	    int i1 = layer.getNewVertex(+w2,-h2,0,texture.maxX,texture.minY);
	    int i2 = layer.getNewVertex(-w2,+h2,0,texture.minX,texture.maxY);
	    int i3 = layer.getNewVertex(+w2,+h2,0,texture.maxX,texture.maxY);
	    
	    
	    
	    layer.addNewTriangle(this,i0,i1,i2);
	    layer.addNewTriangle(this,i1,i3,i2);
	    
	    nbVertex = 4;
	    startVertexId = i0;
	  }  
	  
	  
	  public void changeTexture(TextureArea t){
		  
		  
		  int id = startVertexId * 2;
		  
		  allVertexUV[id++] = t.minX;
		  allVertexUV[id++] = t.minY;
		  
		  allVertexUV[id++] = t.maxX;
		  allVertexUV[id++] = t.minY;
		  
		  allVertexUV[id++] = t.minX;
		  allVertexUV[id++] = t.maxY;
		  
		  allVertexUV[id++] = t.maxX;
		  allVertexUV[id++] = t.maxY;
		  
		  
		  
		  super.changeTexture(t);
	  }
	  
	  
	  /*
	  protected void setMouseIsOver(boolean b){
		  super.setMouseIsOver(b);
		  if(mouseIsOver){
			  changeTexture(overTexture);
		  }else{
			  changeTexture(normalTexture);
		  }
	  }
	  */
	}
