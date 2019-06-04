package kosmos.displayList;

import kosmos.displayList.layers.Layer3D;
import kosmos.texture.ParticleTextureUv;


public class Particle3D extends Shape3D {
	
	protected ParticleTextureUv uv;
	
	
	public Particle3D(Layer3D renderer,ParticleTextureUv singleTriangleUv){
		super(renderer,singleTriangleUv.getTexture(),1);   
		uv = singleTriangleUv;
	    nbVertex = 3;
	    startVertexId = setupTriangle(singleTriangleUv);
	}
	
	public Particle3D(Layer3D renderer,ParticleTextureUv singleTriangleUv,float w,float h){
		super(renderer,singleTriangleUv.getTexture(),1);   
		uv = singleTriangleUv;
	    nbVertex = 3;
	    startVertexId = setupTriangle(singleTriangleUv);
	    scaleX = w;//((float)uv.getTexture().w);
	    scaleY = h;//((float)uv.getTexture().h);
	    scaleZ = 1;
	}
	
	
	
	
	
	
	protected int setupTriangle(ParticleTextureUv uv){
		
		float tw = (float) 1f;//uv.getTexture().w;
		float th = (float) 1f;//uv.getTexture().h;
		
		//System.out.println("setupTriangle = "+tw+" : "+th);
		
		float _x0 = -1.0f * tw;
	    float _y0 = 0.5f * th;
	    float _x1 = 0f * tw;
	    float _y1 = -0.5f * th;
	    float _x2 = 1f * tw;
	    float _y2 = 0.5f * th;
	    
	    int i0 = layer.getNewVertex(_x0,_y0,0,uv.u0,uv.v0);
	    int i1 = layer.getNewVertex(_x1,_y1,0,uv.u1,uv.v1);
	    int i2 = layer.getNewVertex(_x2,_y2,0,uv.u2,uv.v2);
	    
	    layer.addNewTriangle(this,i0,i1,i2);
	    
	    return i0;
	}
	
	
	public void updateGeometry(float x0,float y0,float z0,float x1,float y1,float z1,float x2,float y2,float z2){
		int n = startVertexId * 3;
		allVertexXYZ[n++] = x0;
		allVertexXYZ[n++] = y0;
		allVertexXYZ[n++] = z0;
		allVertexXYZ[n++] = x1;
		allVertexXYZ[n++] = y1;
		allVertexXYZ[n++] = z1;
		allVertexXYZ[n++] = x2;
		allVertexXYZ[n++] = y2;
		allVertexXYZ[n++] = z2;
	}
	
	public void updateTexture(ParticleTextureUv triangleUv){
		if(null == triangleUv) return;
		int id = startVertexId * 2;
		
		uv = triangleUv;
		texture = uv.getTexture();
		
		allVertexUV[id++] = triangleUv.u1;
		allVertexUV[id++] = triangleUv.v1;	  
		allVertexUV[id++] = triangleUv.u2;
		allVertexUV[id++] = triangleUv.v2;
		allVertexUV[id++] = triangleUv.u0;
		allVertexUV[id++] = triangleUv.v0;
	}
	
	
}
