package kosmos.examples;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;

import kosmos.utils.borderFinder.BorderFinder;
import kosmos.utils.borderFinder.BorderPt;
import kosmos.utils.borderFinder.borderSimplificator.BorderSimplificator;
import kosmos.utils.box2dUtils.PhysicVertexBuilder;
import kosmos.utils.box2dUtils.earCutting.EarCutting;
import kosmos.utils.box2dUtils.earCutting.ShortArray;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.IntList;

public class Example04 implements IExample{
	
	PApplet applet;
	BorderPt[] borderPoints;
	BorderPt[] simpleBorder;
	ShortArray triangles;
	Vec2[] vertices;
	int id;
	
	public Example04(PApplet appletObj){
		applet = appletObj;
		String imageURL;
		imageURL = "Horned_King_transparent.png";
		//imageURL = "qFfCj.png";
		//imageURL = "RedbrushAlpha-pngquant15.png";
		//imageURL = "1414424623-wham-shell-lightcore.png";
		//imageURL = "Alpha-Amanitin–RNA_polymerase_II_complex_1K83.png";
		//imageURL = "tree_1__png_with_transparency__by_bupaje-d65ctod.png";
		//imageURL = "Triple-Spiral-4turns_green_transparent.png";
		imageURL = "beer.png";
		float scale = 1f;
		PImage img = applet.loadImage(imageURL);
		PImage scaleImg = new PImage((int) (img.width*scale),(int)(img.height*scale),2);
		scaleImg.copy(img, 0, 0, img.width, img.height, 0, 0,(int) (img.width*scale),(int)(img.height*scale));
		//PImage img = applet.loadImage("qFfCj.png");
		//BorderFinder border = new BorderFinder();
		//BorderSimplificator obj = new BorderSimplificator();
		
		//borderPoints = border.getBorderImage(scaleImg);
		
		
		
		//simpleBorder = obj.init(50,borderPoints);
		int time = applet.millis();
		PhysicVertexBuilder builder = new PhysicVertexBuilder(100,scaleImg,0,0,scaleImg.width,scaleImg.height);
		vertices = builder.getPhysicVertex(400, 400);
		
		triangles = EarCutting.getInstance().computeTriangles(vertices);
		System.out.println("time = "+(applet.millis() - time));
		id = 0;
	}
	
	public void update(){ 
		
		applet.stroke(200,155,100);
		
		int i,i0,i1,i2,len = triangles.size;
		//System.out.println(len/3);
		Vec2 p0,p1,p2;
		
		id += 3;
		id %= len;
		
		for(i=0;i<id;i+=3){
			i0 = triangles.get(i);
			i1 = triangles.get(i+1);
			i2 = triangles.get(i+2);
			
			p0 = vertices[i0];
			p1 = vertices[i1];
			p2 = vertices[i2];
			
			applet.line(200+p0.x,200+ p0.y,200+p1.x,200+ p1.y);
			applet.line(200+p1.x,200+ p1.y,200+p2.x,200+ p2.y);
			applet.line(200+p2.x,200+ p2.y,200+p0.x,200+ p0.y);
		}
		
		applet.noStroke();
		
		/*
		applet.fill(255,0,0,255);
		
		
		int i,len = borderPoints.length;
		BorderPt pt;
		applet.stroke(200,155,100,100);
		id+=10;
		id %= len;
		for(i=0;i<len;i++){
			pt = borderPoints[i];
			applet.rect(500+pt.x*0.5f,pt.y*0.5f,1,1);
		}
		
		BorderPt pt2;
		applet.stroke(100,255,100);
		
		float ox = simpleBorder[0].x;
		float oy = simpleBorder[0].y;
		float px,py;
		for(i=1;i<simpleBorder.length;i++){
			pt2 = simpleBorder[i];
			px = simpleBorder[i].x;
			py = simpleBorder[i].y;
			applet.line(ox*0.5f, oy*0.5f, px*0.5f,py*0.5f);
			ox = px;
			oy = py;
		}
		px = simpleBorder[0].x;
		py = simpleBorder[0].y;
		applet.line(ox*0.5f, oy*0.5f, px*0.5f,py*0.5f);
		
		*/
	}
}
