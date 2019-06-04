package kosmos.utils.box2dUtils;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;

import processing.core.PImage;
import shiffman.box2d.Box2DProcessing;
import kosmos.texture.TextureArea;
import kosmos.utils.borderFinder.BorderFinder;
import kosmos.utils.borderFinder.BorderPt;
import kosmos.utils.borderFinder.borderSimplificator.BorderSimplificator;
import kosmos.utils.box2dUtils.earCutting.EarCutting;
import kosmos.utils.box2dUtils.earCutting.ShortArray;

public class PhysicVertexBuilder {
	
	private float[] posx;
	private float[] posy;
	private float borderX;
	private float borderY;
	private float borderW;
	private float borderH;
	private int[] triangleIndices;
	
	public PhysicVertexBuilder(int nbPhysicVertexMax,PImage img,int px,int py,int pw,int ph){
		
		BorderSimplificator border = BorderSimplificator.getInstance();
		BorderPt[] points = border.init(nbPhysicVertexMax, img,px,py,pw,ph);
		getTriangleIndices(points);
		normalizePoints(points,border.width,border.height,border.minX,border.minY);
	}

	public PhysicVertexBuilder(int nbPhysicVertexMax,TextureArea texture){
		
		BorderSimplificator border = BorderSimplificator.getInstance();
		BorderPt[] points = border.init(nbPhysicVertexMax, texture);
		getTriangleIndices(points);
		normalizePoints(points,border.width,border.height,border.minX,border.minY);
	} 
	public PhysicVertexBuilder(int nbPhysicVertexMax,BorderPt[] borderPoints){
		
		BorderSimplificator border = BorderSimplificator.getInstance();
		BorderPt[] points = border.init(nbPhysicVertexMax, borderPoints);
		getTriangleIndices(points);
		normalizePoints(points,border.width,border.height,border.minX,border.minY);
	} 
	
	
	private void getTriangleIndices(BorderPt[] points){
		int i,len = points.length,k = 0;
		float[] v = new float[len*2];
		for(i=0;i<len;i++){
			v[k++] = points[i].x;
			v[k++] = points[i].y;
		}
		
		ShortArray indices = EarCutting.getInstance().computeTriangles(v);
		len = indices.size;
		
		triangleIndices = new int[len];
		
		for(i=0;i<len;i++) triangleIndices[i] = indices.get(i);
		 
	}
	
	
	private void normalizePoints(BorderPt[] points,float borderWidth,float borderHeight,float borderMinX,float borderMinY){
		
		borderX = borderMinX;
		borderY = borderMinY;
		borderW = borderWidth;
		borderH = borderHeight;
		
		posx = new float[points.length];
		posy = new float[points.length];
		
		int i,len = points.length;
		BorderPt pt;
		for(i=0;i<len;i++){
			pt = points[i];
			posx[i] = pt.x;
			posy[i] = pt.y;
			posx[i] -= borderMinX;
			posy[i] -= borderMinY;
			posx[i] /= borderW;
			posy[i] /= borderH;
			posx[i] -= 0.5;
			posy[i] -= 0.5;
			
		}
		
		//borderX /= borderW;
		//borderY /= borderH;
	}
	
	public int[] getTriangleIndices(){
		return triangleIndices;
	}
	
	public Vec2[] getPhysicVertex(float w,float h,float offX,float offY){
		int i,len = posx.length;
		Vec2[] verts = new Vec2[len];
		for(i=0;i<len;i++){
			
			verts[i] = new Vec2((offX + posx[i]) * w,(offY + posy[i]) * h);
		}
		return verts;
	}
	public Vec2[] getPhysicVertex(float w,float h){
		int i,len = posx.length;
		Vec2[] verts = new Vec2[len];
		for(i=0;i<len;i++){
			
			verts[i] = new Vec2((posx[i]) * w,( posy[i]) * h);
		}
		return verts;
	}
	
	
	
	public Body createBox2dBody(Box2DProcessing box2d,TextureArea texture,float px,float py,float pw,float ph){
		BodyDef def = new BodyDef();
		def.type = BodyType.DYNAMIC;
		
		float sx = borderW / texture.w;
		float sy = borderH / texture.h;
		
		pw *= sx;
		ph *= sy;
		
		def.position.set(box2d.coordPixelsToWorld(new Vec2(px,py)));
		
		
		Body body = box2d.createBody(def);
		
		
		PolygonShape ps;
		int i,len = triangleIndices.length;
		
		int i0,i1,i2;
		Vec2 p0,p1,p2;
		
	
		float dist =  texture.w - borderW - borderX;
		float d = (borderX - dist)/(texture.w);
		
		
		float offsetX = d;
		
		dist = borderY - (texture.h - (borderY+borderH));
		float offsetY = ((dist) / ((float)texture.h)) ;//* sy;
		
		//System.out.println("BX = "+borderX*(1-sx));
		
		Vec2[] physicVerts = getPhysicVertex(pw,ph,offsetX/2,offsetY/2);
		Vec2[] triangleVerts;
		
		for(i=0;i<len;i+=3){
			
			ps = new PolygonShape();
			
			i0 = triangleIndices[i];
			i1 = triangleIndices[i+1];
			i2 = triangleIndices[i+2];
			
			p0 = physicVerts[i0];
			p1 = physicVerts[i1];
			p2 = physicVerts[i2];
			
			if(false == ((p0.x == p1.x && p1.x == p2.x) || (p0.y == p1.y && p1.y == p2.y) )){
				
				triangleVerts = new Vec2[3];
				triangleVerts[0] = box2d.vectorPixelsToWorld(p0);
				triangleVerts[1] = box2d.vectorPixelsToWorld(p1);
				triangleVerts[2] = box2d.vectorPixelsToWorld(p2);
				
				ps.set(triangleVerts, 3);
				
				body.createFixture(ps, 1.0f);
			
			}
		}
		
		return body;
	}
	

	
	
}
