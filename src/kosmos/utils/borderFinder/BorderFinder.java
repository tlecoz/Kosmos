package kosmos.utils.borderFinder;

import java.util.ArrayList;

import kosmos.texture.TextureArea;
import processing.core.PImage;

public class BorderFinder {
	
	
	private static BorderFinder instance;
	
	private BorderPt center;
	private BorderPt pt;
	private PImage bd;
	private BorderPt first;
	private float diago;
	private int n;
	private ArrayList<BorderPt> tab;
	private int[] pixelUsed;
	private BorderPt old;
	private int count;
	private boolean working;
	private int oldAngle;
	private int firstId;
	
	private int[] directionDatas;
	
	private int _width;
	private int _height;
	private boolean bug;
	
	
	
	public BorderPt[] borderPoints;
	public String directions = "";
	
	PImage usedImg;
	PImage emptyImg;
	
	public BorderFinder(){
		
		instance = this;
		
		diago = (float) Math.sqrt(2);
		tab = new ArrayList<BorderPt>();
		
		n = 0;
		old = new BorderPt(0,0,0);
		count = 0;
		directionDatas = new int[766];
		int i;
		for(i=0;i<766;i++) directionDatas[i] = 0;
		
		directionDatas[0] = 3;
		directionDatas[45] = 4;
		directionDatas[90] = 5;
		directionDatas[135] = 6;
		directionDatas[180] = 7;
		directionDatas[225] = 8;
		directionDatas[270] = 1;
		directionDatas[315] = 2;
		directionDatas[360] = 3;
		directionDatas[405] = 4;
		directionDatas[450] = 5;
		directionDatas[495] = 6;
		directionDatas[540] = 7;
		directionDatas[585] = 8;
		directionDatas[630] = 1;
		directionDatas[675] = 2;
		directionDatas[720] = 3;
		directionDatas[765] = 4;
		
		bd = new PImage(2,2,2);
		usedImg = new PImage(2,2,2);
		emptyImg = new PImage(2,2,2);
		
	}
	
	public static BorderFinder getInstance(){
		if(instance == null) new BorderFinder();
		return instance;
	}
	
	public BorderPt[] getBorderImage(PImage source){
		return init(source,0,0,source.width,source.height);
	}
	public BorderPt[] getBorderImage(PImage source,int srcX,int srcY,int srcW,int srcH){
		return init(source,srcX,srcY,srcW,srcH);
	}
	public BorderPt[] getBorderImage(TextureArea texture){
		return init(texture.textureImage,texture.x,texture.y,texture.w,texture.h);
	}
	
	
	
	private BorderPt[] init(PImage source,int px,int py,int pw,int ph){
		
		int area = pw*ph;
		float s = (400.0f*400.0f) / area;
		
		if( s < 1){
			s = 1;
			bd.resize(pw+4,ph+4);
			usedImg.resize(pw+4,ph+4);
			
			bd.copy(emptyImg, 0, 0, 2, 2, 0, 0, bd.width, bd.height);
			usedImg.copy(emptyImg, 0, 0, 2, 2, 0, 0, bd.width, bd.height);
			
			
			bd.copy(source, px, py, pw, ph, 2, 2, pw, ph);
			
		}else{
			//s =  (1.0f/s);
			int pw2 = (int) (pw * s);
			int ph2 = (int) (ph * s);
			
			bd.resize(pw2+4,ph2+4);
			usedImg.resize(pw2+4,ph2+4);
			
			bd.copy(emptyImg, 0, 0, 2, 2, 0, 0, bd.width, bd.height);
			usedImg.copy(emptyImg, 0, 0, 2, 2, 0, 0, bd.width, bd.height);
			
			
			bd.copy(source, px, py, pw, ph, 2, 2, pw2, ph2);
			System.out.println("ok "+pw2+" ! "+ph2+" ! "+s);
		}
		
		
		
		
		
		
		pixelUsed = usedImg.pixels;
		

		int i,len = pixelUsed.length;
		for(i=0;i<len;i++) pixelUsed[i] = 0;
		
		px = bd.width >> 1;
		py = 2;
		while(bd.get(px,py) == 0) py++;
		
		n = 0;
		tab = new ArrayList<BorderPt>();
		old = new BorderPt(0,0,0);
		directions = "";
		count = 0;
		first = center = new BorderPt(px,py,0);
		pt = new BorderPt(px,py-1,0);
		
		int id = firstId = (int) (py * bd.width + px);
		pixelUsed[id] = 1;
		
		_width = bd.width;
		_height = bd.height;
		bug = false;
		
		
		
		working = true;
		
		while (working && bug == false)
		{
			getBorder();
			bug = count > 100;
		}
		
		if(bug == true) return null;
		
		borderPoints = new BorderPt[tab.size()];
		len = tab.size();
		for(i=0;i<len;i++){
			
			borderPoints[i] = tab.get(i);
			if(borderPoints[i] != null){
				borderPoints[i].x = (int) ((borderPoints[i].x) / s);
				borderPoints[i].y = (int) ((borderPoints[i].y) / s);
			}
		}
		
		return borderPoints;
		
	}
	
	private int returnAngle(BorderPt p0,BorderPt p1){
		return (int) (Math.atan2(p1.y - p0.y, p1.x - p0.x) / (Math.PI / 180.0f) );
	}
	
	private void getBorder(){
		int _angle = returnAngle(center, pt) + 45;
	
		int a = _angle;
		boolean test = a - oldAngle > 0;
		
		
		int len = a + 315;
		float r;
		int px=0, py=0;
		
		BorderPt temp;
		boolean bool = false;
		int pixelIndex; //  c:String;
		float radian = (float) (Math.PI/180.0f);
		
		while (a < len)
		{
			
			r = radian * a;
			
			
			if (a % 45 != 0)
			{
				px = (int) Math.round(center.x +  Math.cos(r) * diago);
				py = (int) Math.round(center.y +  Math.sin(r) * diago);
			}else
			{
				px = (int) Math.round(center.x +  Math.cos(r));
				py = (int) Math.round(center.y +  Math.sin(r));
			}
			pixelIndex = py * _width + px;
			//c = px + "_" + py;
			
			if (bd.get(px, py)!=0)
			{
				if (pixelUsed[pixelIndex] == 1)
				{
					if(pixelIndex == firstId){
						System.out.println("FINISH");
						System.out.println("len = " + tab.size());
						
						/*
						if (!lastChance && len < 50) {
							lastChance = true;
							working = false;
							count = 0;
							initFromBitmapData(originalData,true);
						}
						
						
						//
						//trace(directions);
						trace("");
						//
						
						//simplify();
						//simplify2(25);
						
						
						
						
						
						
						var minX:Number = 100000, maxX:Number = 0;
						var minY:Number = 100000, maxY:Number = 0;
						var i:int;
						len = tab.length;
						var point:Pt;
						for (i = 0; i < len; i++) {
							point = tab[i];
							if (point.x < minX) minX = point.x;
							if (point.y < minY) minY = point.y;
							if (point.x > maxX) maxX = point.x;
							if (point.y > maxY) maxY = point.y;
						}
						
						var w:Number = maxX - minX;
						var h:Number = maxY - minY;
						var wh:Number = w * h;
						//var normalWh:Number = 400 * 400;
						var scale:Number = 1
						
						if (wh >= 400*400) scale = 0.75;
						else {
							if (wh > 300 * 300) scale = 1;
							else if (wh > 200 * 200) scale = 1.4;
							else if (wh > 100 * 100) scale = 1.8;
							else if (wh > 60 * 60) scale = 2.2;
							else scale = 3.5;
						}
						
						
						//trace("scale = "+normalWh+" : "+wh)
						trace(scale);
						
						for (i = 0; i < tab.length; i++) {
							tab[i].x *= scale;
							tab[i].y *= scale;
							tab[i].x -= scale;
							tab[i].y -= scale;
						}
						
						
						
						
						
						simplify2();
						simplify();
						
						
						
						
						
						removeNearestPoints(5);
						removeNearestPoints(10);
						removeNearestPoints(15);
						
						
						removeNearestPoints2(5);
						removeNearestPoints2(10);
						removeNearestPoints2(15);
						
						
						
						for (i = 0; i < 11; i++) {
							removeNearLine(1+i);
						}
						
						len = tab.length;
						if (tab[0] == tab[tab.length - 1]) len--;
						
						
						
						
						minX = 100000;
						maxX = 0;
						minY = 100000;
						maxY = 0;
						for (i = 0; i < len; i++) {
							point = tab[i];
							point.x /= scale;
							point.y /= scale;
							if (point.x < minX) minX = point.x;
							if (point.y < minY) minY = point.y;
							if (point.x > maxX) maxX = point.x;
							if (point.y > maxY) maxY = point.y;
						}
						_width = maxX - minX;
						_height = maxY - minY;
						
						
						trace("time = " + (getTimer() - time));
						
						trace("clearSmallerSegments(1.6) len = "+tab.length);
						
						//angles.sort(16)
						//trace(angles[0]+" : "+angles[angles.length-1]);
						 * 
						 */
						
						working = false;
						bool = false;
						count = 0;
						break;
					}
				}else
				{	
					directions += directionDatas[a+45];
					//angles.push(a);
					pixelUsed[pixelIndex] = 1;
					tab.add(new BorderPt(px+3, py+2, n++));//new Point(px, py);
					bool = true;
					
					break;
				}	
			}
			a += 45;
		}
		
		
		if(bool){
			temp = new BorderPt(center.x,center.y,0);
			center = new BorderPt(px, py,0);
			pt = new BorderPt(temp.x, temp.y,0);
			count = 0;
			
		}else
		{
			count++;
			
			center = new BorderPt(pt.x, pt.y,0);
			
			int d = tab.size() - count;
			if(d>=0){
				pt = tab.get(d) ;
				//working = false;
				
			}
			
		}
	}
}
