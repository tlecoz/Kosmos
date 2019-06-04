package kosmos.displayList;

import kosmos.displayList.layers.Layer3D;
import kosmos.texture.TextureArea;



public class Shape3D extends DisplayObject3D {
	  
	  
	//###### INTERNAL ######################
	  
	  protected int startVertexId;
	  protected int nbVertex;
	  protected float[] allVertexXYZ;
	  protected float[] allVertexUV;
	  protected float[] allTransformXYZ;
	  private boolean _renderable;
	  private boolean _visible;
	 //##############################
	  
	 
	 
	 
	  public void dispose(){
		  super.dispose();
		  layer = null;
		  _renderable = false;
		  allVertexXYZ = allVertexUV = allTransformXYZ = null;
		  
		  int i,len = startTriangleId + nbTriangle;
		  for(i=startTriangleId;i<len;i++) allTriangles[i].dispose();
		  allTriangles = null;
	  }
	  
	  
	  
	  protected Shape3D(){
	    _renderable = true;
	  }
	  
	  
	  
	  protected Shape3D(Layer3D r,TextureArea textureArea,int numTriangle){
		 
		  super();
		 //System.out.println("area = "+textureArea); 
		  
		
	     visible = _visible = _renderable = true;
	     mouseIsOver = false;
	     
	     layer = r;
	     nbTriangle = numTriangle;
	     startTriangleId = layer.nbTriangle;
	     
	     
	     texture = textureArea;
	     allVertexXYZ = layer.allVertexXYZ;
	     allVertexUV = layer.allVertexUV;
	     allTransformXYZ = layer.allTransformXYZ; 
	     allTriangles = layer.allTriangles;
	     
	     getWidthTime = getHeightTime = getDepthTime = 0;
	  }
	  
	  
	  
	  
	  boolean renderable(){
		  return _renderable;
	  }
	  
	  
	  
	  void updateWidth(){
		  
		  long time = System.currentTimeMillis();
		  if(time - getWidthTime > 0) {
		  
			  boundMinX = 100000.0f;
			  boundMaxX = -100000.0f;
			  
			  int i,start = startVertexId * 3, len = (startVertexId + nbVertex) * 3;
			  float n;
			  for(i=start;i<len;i+=3){
				  n = allTransformXYZ[i];
				  if(n < boundMinX) boundMinX = n;
				  if(n > boundMaxX) boundMaxX = n;
			  }
			  getWidthTime = time;
		  }
		  width = boundMaxX-boundMinX;
	  }
	  
	  void updateHeight(){
		  long time = System.currentTimeMillis();
		  if(time - getHeightTime > 0) {
			  
			  boundMinY = 100000.0f;
			  boundMaxY = -100000.0f;
			  
			  int i,start = (startVertexId * 3 + 1), len = (startVertexId + nbVertex) * 3;
			  float n;
			  for(i=start;i<len;i+=3){
				  n = allTransformXYZ[i];
				  if(n < boundMinY) boundMinY = n;
				  if(n > boundMaxY) boundMaxY = n;
			  }
			  getHeightTime = time;
		  }
		  
		  height = boundMaxY-boundMinY;
	  }
	  
	  void getDepth(){
		  long time = System.currentTimeMillis();
		  if(time - getDepthTime > 0) {
			  boundMinZ = 100000.0f;
			  boundMaxZ = -100000.0f;
			  
			  int i,start = (startVertexId * 3 + 2), len = (startVertexId + nbVertex) * 3;
			  float n;
			  for(i=start;i<len;i+=3){
				  n = allTransformXYZ[i];
				  if(n < boundMinZ) boundMinZ = n;
				  if(n > boundMaxZ) boundMaxZ = n;
			  }
			  getDepthTime = time;
		  }
		  
		  depth = boundMaxZ-boundMinZ;
	  }
	  
	  
	  
	 
	  
	 
	  
	  
	  protected void updateVertexPosition(){
	    
		if(visible != _visible){
			_visible = visible;
			int i,len = startTriangleId + nbTriangle;
			for(i=startTriangleId;i<len;i++) allTriangles[i].visible = visible;
		}
		  
		
	    //if(visible){
	      
	      float px,py,pz,xy,xz,yz,yx,zx,zy;
	      float ppx,ppy,ppz,sx,cx,sy,cy,sz,cz;
	      
	      if(useLocalParentRotation){
		      
	    	  sx = (float) Math.sin(parent.rotationX);
		      cx = (float) Math.cos(parent.rotationX);
		      
		      sy = (float) Math.sin(parent.rotationY);
		      cy = (float) Math.cos(parent.rotationY);
		      
		      sz = (float) Math.sin(parent.rotationZ);
		      cz = (float) Math.cos(parent.rotationZ);
	    	  
	      }else{
	    	  sx = (float) Math.sin(parent._realRX);
		      cx = (float) Math.cos(parent._realRX);
		      
		      sy = (float) Math.sin(parent._realRY);
		      cy = (float) Math.cos(parent._realRY);
		      
		      sz = (float) Math.sin(parent._realRZ);
		      cz = (float) Math.cos(parent._realRZ);
	      }
	     
	      
	      ppx = x + offsetPositionX;
	      ppy = y + offsetPositionY;
	      ppz = z + offsetPositionZ;
	      
	      
	      
	      float scaleFactor;
	      
	      //ROTATION X
	      xy = cx * ppy - sx * ppz;
	      xz = sx * ppy + cx * ppz;

	      //ROTATION Y
	      yz = cy * xz - sy * ppx;
	      yx = sy * xz + cy * ppx;

	      //ROTATION Z
	      zx = cz*yx - sz*xy;
	      zy = sz*yx + cz*xy;
	      
	      
	      scaleFactor = focalLength/(focalLength -parent._realZ+ yz);
	        
	      ppx = zx * scaleFactor;
	      ppy = zy * scaleFactor;
	      ppz = yz ;
	      
	      
	      
	      ppx += parent._realX;
	      ppy += parent._realY;
	      ppz += parent._realZ;
	      
	      
	      
	      
	      
	      //ROTATIONS
	      
	      if(useLocalRotation){
	      
		      sx = (float) Math.sin(rotationX);
		      cx = (float) Math.cos(rotationX);
		      sy = (float) Math.sin(rotationY);
		      cy = (float) Math.cos(rotationY);
		      sz = (float) Math.sin(rotationZ);
		      cz = (float) Math.cos(rotationZ);
	      
	      }else{
	    	  
	    	  sx = (float) Math.sin(_realRX);
		      cx = (float) Math.cos(_realRX);
		      sy = (float) Math.sin(_realRY);
		      cy = (float) Math.cos(_realRY);
		      sz = (float) Math.sin(_realRZ);
		      cz = (float) Math.cos(_realRZ);
	    	  
	      }
	      
	      
	      
	      float oldFactor = scaleFactor;
	      
	      int i,i3,i2,i33,k = 0,len = startVertexId + nbVertex;
	      for(i = startVertexId;i<len;i++){
	        
	        i3 = i*3;
	        
	        px = allVertexXYZ[i3] * _realSX;
	        py = allVertexXYZ[i3+1] * _realSY;
	        pz = allVertexXYZ[i3+2] * _realSZ;
	        
	        
	        //ROTATION X
	        xy = cx * py - sx * pz;
	        xz = sx * py + cx * pz;
	  
	        //ROTATION Y
	        yz = cy * xz - sy * px;
	        yx = sy * xz + cy * px;
	  
	        //ROTATION Z
	        zx = cz*yx - sz*xy;
	        zy = sz*yx + cz*xy;
	        
	        
	        scaleFactor = ((focalLength)/(focalLength + yz)) * oldFactor;
	        
	        px = zx * scaleFactor;
	        py = zy * scaleFactor; 
	        pz = yz ;
	        
	        px += ppx;
	        py += ppy;
	        pz += ppz;
	        
	        
	       
	        
	        allTransformXYZ[i3] = _realX = px;// + x + px ;
	        allTransformXYZ[i3+1] =  _realY = py;// + y + py;
	        allTransformXYZ[i3+2] =   _realZ = pz;// + z + pz;
	        
	       
	        //_renderable = true;//(pz - cameraZ) > minimalZ && px >= minimalX && px <= maximalX && py >= minimalY && py <= maximalY && alpha >= 0.01;
	        
	      }
	    //}else{
	    	//_renderable = false;
	    //}
	     
	  }
	  
	}
