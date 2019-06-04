package kosmos.utils.borderFinder.borderSimplificator;

import java.util.ArrayList;
import java.util.Collections;

import processing.core.PImage;
import kosmos.texture.TextureArea;
import kosmos.utils.borderFinder.BorderFinder;
import kosmos.utils.borderFinder.BorderPt;

public class BorderSimplificator {
	
	private static BorderSimplificator instance;
	
	
	BorderPt[] sourcePoints;
	public BorderPt[] points;
	public float width;
	public float height;
	public float minX;
	public float minY;
	
	BorderPt firstPt;
	float scale;
	
	int nbPointMax;
	
	
	public BorderSimplificator(){
		instance = this;
		
	}
	public static BorderSimplificator getInstance(){
		if(instance == null) new BorderSimplificator();
		return instance;
	}
	public BorderPt[] init(int maxPointWanted,BorderPt[] borderPoints){
		return _init(maxPointWanted,borderPoints);
	}
	
	public BorderPt[] init(int maxPointWanted,TextureArea texture){
		BorderPt[] borderPoints = BorderFinder.getInstance().getBorderImage(texture);
		return _init(maxPointWanted,borderPoints);
	}
	
	public BorderPt[] init(int maxPointWanted,PImage img,int px,int py,int pw,int ph){
		BorderPt[] borderPoints = BorderFinder.getInstance().getBorderImage(img,px,py,pw,ph);
		return _init(maxPointWanted,borderPoints);
	}
	
	private BorderPt[] _init(int maxPointWanted,BorderPt[] borderPoints){
		sourcePoints = borderPoints;
		
		nbPointMax = maxPointWanted;
		
		points = new BorderPt[borderPoints.length];
		System.arraycopy(borderPoints, 0, points, 0, borderPoints.length);
				
		
		firstPt = points[0];

		process();
		
		return points;
	}
	
	
	
	
	protected void process(){
		
		//by default, the process apply a very strong optimization of the path.
		//I wrote that class to convert a pixel-path made of hundreds/thousands of pixels 
		//into the minimal amount of point possible with approximatly the same shape
		
		//I did it to convert a custom-pixel-shape into Box2D-polygon
		float startLen = points.length;
		System.out.println("NB POINT BEFORE OPTIMISATION : "+startLen);
		
		rescaleBorderBeforeSimplificationIfPictureIsTooSmall();
		
		int len = points.length;
		int count = 1;
		int maxPoint = (int) (points.length / 10f);
		
		float num;
		num = 5f;
		while(len > maxPoint && count++ > 0 && count < 500){
			removeNearLine(num );
			num -= 0.15f;
			len = points.length;
		}
		
		int count2 = count;
		count = 0;
		num = 1;
		float numAdding = 0.75f;
		float num2Adding= 0.025f;
		float num2 = 0.025f;
		float ratio = 1f;
		
		numAdding *= ratio;
		num2Adding *= ratio;
				
		
				
		maxPoint = nbPointMax;
		boolean b = true;
		while(len > maxPoint){
			
			if(count++ < 500){ 
				removeSmallLines(num+=numAdding,b);
				
				if(count % 25 == 0) removeNearLine(num2+=num2Adding);
			}
			else{
				//num = 1;
				if(count++<1000) removeSmallLines(num+=0.1,b);
				else break;
			}
			
			b = !b;
			len = points.length;
		}
		
		
		
		System.out.println("nbPass = "+(count+count2));
		
		System.out.println("NB POINT AFTER OPTIMISATION : "+points.length+" ( "+(points.length / startLen)*100.0f+" % of the original )");
		
		
		System.out.println(points.length);
		getBackOriginalScale();
	}
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	protected void getBackOriginalScale(){
		minX = 100000;
		float maxX = 0;
		minY = 100000;
		float maxY = 0;
		int i,k=0,len = sourcePoints.length;
		
		BorderPt point;
		for(i=0;i<len;i++){
			point = sourcePoints[i];
			if(sourcePoints[i] == null){
				//System.out.println("#1 - point is null -> "+i);
				continue;	
			}
			                     
			
			point.x /= scale;
			point.y /= scale;
			
			
		}
		
		if (points[0] == points[points.length - 1]) len--;
		
		len = points.length;
		BorderPt[] temp = new BorderPt[len];
		
		for (i = 0; i < len; i++) {
			if(points[i] == null){
				//System.out.println("#2 - point is null -> "+i);
				continue;	
			}
			point = points[i];
			temp[k++] = new BorderPt(point.x,point.y,0);
			if (point.x < minX) minX = point.x;
			if (point.y < minY) minY = point.y;
			if (point.x > maxX) maxX = point.x;
			if (point.y > maxY) maxY = point.y;
		}
		
		width = maxX - minX;
		height = maxY - minY;
		
		/*
		if(normalize == true){
			for(i=0;i<k;i++){
				point = temp[i];
				point.x -= minX;
				point.y -= minY;
				point.x /= w;
				point.y /= h;
				point.x -= 0.5;
				point.y -= 0.5;
			}
		}
		*/
		
		BorderPt[] temp2 = new BorderPt[k+1];
		System.arraycopy(temp, 0, temp2, 0, k);
		temp2[k] = firstPt;
		points = temp2;
	}
	
	
	
	protected void rescaleBorderBeforeSimplificationIfPictureIsTooSmall(){
		int minX = 100000, maxX = 0;
		int minY = 100000, maxY = 0;
		int i;
		int len = points.length;
		BorderPt point;
		for (i = 0; i < len; i++) {
			point = points[i];
			if (point.x < minX) minX = point.x;
			if (point.y < minY) minY = point.y;
			if (point.x > maxX) maxX = point.x;
			if (point.y > maxY) maxY = point.y;
		}
		
		int w = maxX - minX;
		int h = maxY - minY;
		int wh = w * h;
		
		scale = ((1000.0f*1000.0f) / wh) ;
		
		for (i = 0; i < points.length; i++) {
			points[i].x *= scale;
			points[i].y *= scale;
			
		}
	
	}
	
	
	
	
	
	
	protected void removeSmallLines(float minDist, boolean direction){
		
		BorderPt[] temp = new BorderPt[points.length];
		int i,k = 0,len = points.length;
		
		if(direction){
			temp[k++] = points[0];
		}
		else temp[k++] = points[points.length-1];
		
		BorderPt p0,p1;
		float dx,dy,d;
		float oldDist = 0;
		
		if(direction == true){
		
			for(i=1;i<len;i++){
				
				p0 = points[i-1];
				p1 = points[i];
				dx = p0.x - p1.x;
				dy = p0.y - p1.y;
				d = (float) Math.sqrt(dx*dx+dy*dy);
				
				if(oldDist + d > minDist){
					temp[k++] = p1;
					oldDist = (oldDist + d - minDist) % minDist;
				}else{
					oldDist += d;
				}
				
			}
		
		} else {
			
			for(i=len-2;i>-1;i--){
				
				p0 = points[i+1];
				p1 = points[i];
				dx = p0.x - p1.x;
				dy = p0.y - p1.y;
				d = (float) Math.sqrt(dx*dx+dy*dy);
				
				if(oldDist + d > minDist){
					temp[k++] = p1;
					oldDist = (oldDist + d - minDist) % minDist;
				}else{
					oldDist += d;
				}
				
			}
		}
		
		
		BorderPt[] t = new BorderPt[k];
		System.arraycopy(temp, 0, t, 0, k);
		
		points = t;
		
	}

	
	protected void removeNearLine(float limit) {
		
		//it does almost the same thing than the function "simplify" 
		//but because it's not based on the "direction-datas" given by the BorderFinder class
		//it's possible to apply with multiple pass and get better results
		
		
		//For example, if I have 3 following points of a path like that
		//
		//  0
		//               1
		//
		//   
		//                  2
		
		
		//the algo loop on every points and create a virtual 4th point at the center of the hypothenus, like that
		//
		//  0
		//               1
		//          4
		//   
		//                  2
		
		
		//then it will compute the distance between 4 and 1 
		//if the distance is smaller than the variable "limit"
		//I remove the point 1 (the point 4 is just an abstraction, it is never added to the point-list  
		
		
		float dx,dy;
		int i;
		BorderPt p1, p2, p3;
		BorderLinePt o12 = new BorderLinePt();
		BorderLinePt o23 = new BorderLinePt();
		BorderLinePt o31 = new BorderLinePt();
		BorderPt center, opposite;
		int oppositeId;
		
		float o1x,o1y,o2x,o2y,o3x,o3y;
		
		ArrayList<BorderLinePt> linePoints = new ArrayList<BorderLinePt>();
		linePoints.add(o12);
		linePoints.add(o23);
		linePoints.add(o31);
		
		for (i = 2; i < points.length-1; i++) {
			p1 = points[i - 2];
			p2 = points[i - 1];
			p3 = points[i];
			
			o1x = p1.x;
			o1y = p1.y;
			o2x = p2.x;
			o2y = p2.y;
			o3x = p3.x;
			o3y = p3.y;
			
			
			int multi = 100000; //used to maximize the chance to have 3 different distance for the Collection.sort;
			
			o12.id = i - 2;
			o12.p1 = p1;
			o12.p2 = p2;
			dx = p1.x - p2.x;
			dy = p1.y - p2.y;
			o12.d = (int) Math.sqrt(dx*dx+dy*dy) * multi;
			o12.a = (float) Math.atan2(dy,dx);
			
			o23.id = i - 1;
			o23.p1 = p2;
			o23.p2 = p3;
			dx = p2.x - p3.x;
			dy = p2.y - p3.y;
			o23.d = (int) Math.sqrt(dx*dx+dy*dy) * multi;
			o23.a = (float) Math.atan2(dy,dx);
			
			o31.id = i;
			o31.p1 = p3;
			o31.p2 = p1;
			dx = p3.x - p1.x;
			dy = p3.y - p1.y;
			o31.d = (int) Math.sqrt(dx*dx+dy*dy)*multi;
			o31.a = (float) Math.atan2(dy,dx);

			Collections.sort(linePoints);

			BorderLinePt hypothenus = linePoints.get(2);
			center = hypothenus.p1;
			
			opposite = linePoints.get(0).p1;
			oppositeId = linePoints.get(0).id;
			if (opposite == hypothenus.p2) {
				opposite = linePoints.get(1).p1;
				oppositeId = linePoints.get(1).id;
			}
			
			dx = center.x - opposite.x;
			dy = center.y - opposite.y;
			float a = (float) Math.atan2(dy,dx);
			float d = (float) Math.sqrt(dx*dx+dy*dy);

			opposite.x = (int) (center.x + Math.cos(-hypothenus.a + a) * d);
			opposite.y = (int) (center.y + Math.sin(-hypothenus.a + a) * d);

			dy = Math.abs(opposite.y - center.y);
			
			p1.x = (int) o1x;
			p1.y = (int) o1y;
			p2.x = (int) o2x;
			p2.y = (int) o2y;
			p3.x = (int) o3x;
			p3.y = (int) o3y;
			
			
			if (dy < limit) {
				
				BorderPt[] temp = new BorderPt[points.length-1];
				if(oppositeId > 0) System.arraycopy(points, 0, temp, 0, oppositeId-1);
				if(oppositeId > 0) System.arraycopy(points, oppositeId, temp, oppositeId-1, points.length-oppositeId);
				else System.arraycopy(points, 0, temp, 0, points.length-1);
				points = temp;
			}
			
		}
	}
	
	
	

	/*
	
	
	protected float returnDistance(BorderPt p0,BorderPt p1){
		float dx = p1.x - p0.x;
		float dy = p1.y - p0.y;
		return (float) Math.sqrt(dx*dx+dy*dy);
	}
	protected int returnAngle(BorderPt p0,BorderPt p1){
		//return angle in degree
		return (int) (Math.atan2(p1.y - p0.y, p1.x - p0.x) / (Math.PI / 180.0f) );
	}
	
	protected void removeNearestPoints(int limit) {
		
		//remove existing null point in the first part of the loop
		//(come from java port of my AS3 version, don't know what is the problem exactly...)
		
		//then
		
		//compare the current position to the old position
		//and remove every pixels inclosed in the "limit" distance.
		
		//compare also the current orientation / direction of the path to the older
		//and remove the point if we get an angle-distance less than 10° 
		
		
		
		int i,j,len = points.length,len2 = points.length + 2;
		
		float limitAngle = (float) Math.PI / 180 * 10.0f;
		float a1, a2;
		
		BorderPt[] t = new BorderPt[points.length+2];
		System.arraycopy(points, 0, t, 0, points.length);
		t[points.length] = points[0];
		t[points.length+1] = points[1];
		
		for (i = 2; i < t.length; i++) {
			
			if(t[i-1] == null){
				BorderPt[] temp = new BorderPt[t.length-1];
				System.arraycopy(t, 0, temp, 0, i-2);
				System.arraycopy(t, i-1, temp, i-2, t.length-i);
				t = temp;
				i--;
				continue;
			}
			
			if(t[i] == null){
				BorderPt[] temp = new BorderPt[t.length-1];
				System.arraycopy(t, 0, temp, 0, i-1);
				System.arraycopy(t, i, temp, i-1, t.length-i);
				t = temp;
				i--;
				continue;
			}
			
			
			if (returnDistance(t[i - 1],t[i]) < limit) {
				
				a1 = returnAngle(t[i - 1],t[i]) ;
				a2 = returnAngle(t[i - 2],t[i - 1]) ;
				
				if ( a1-a2 < limitAngle ) {
					
					BorderPt[] temp = new BorderPt[t.length-1];
					System.arraycopy(t, 0, temp, 0, i-1);
					System.arraycopy(t, i, temp, i-1, t.length-i);
					t = temp;
				}
				
				
				//directions = (directions.substr(0, i) + directions.substr(i + 1) );
			}
		}
		
		BorderPt[] temp = new BorderPt[t.length-2];
		System.arraycopy(t, 0, temp, 0, t.length-2);
		
		
		points = temp;
	}
	
	
	
	
	protected void removeNearestPoints2(int limit) {
		
		//remove useless nearest points --> the distance between each point must be greater to "limit"
		
		int i,j;
		for (j = 1; j < points.length+1; j++) {
			i = j%points.length;
			if(i<=1) i+=points.length-2;
			//Sytem.out.println(i+" ! "+points.length+" : "+points[i]+" : "+points[i-1]);
			if (returnDistance(points[i - 1],points[i]) < limit) {
				
				//Sytem.out.println(">>> "+points+" : "+points.length);
				BorderPt[] temp = new BorderPt[points.length-1];
				System.arraycopy(points, 0, temp, 0, i-1);
				System.arraycopy(points, i, temp, i-1, points.length-i);
				points = temp;
			}
		}
	}
	
	
	protected void simplify() {
		
		//follow the path without XY datas but using the directions of the path from the old pixel to the new one.
		
		//each pixel is surround by 8 pixels
		// 0 1 2
		// 7 X 3
		// 6 5 4
				
		//the "directions data" is a String that contains a list of int that represent the direction of the path, pixel by pixel
		//(the "direction data" come from the BorderFinder class )
		
		//the "simplify" method look at 3 following point to remove the useless 90° angle between 2 points.
		//for example, if I have a path like that
		//
		//  0  1
		//
		//     2  3
		//
		//        4
		
		//the "simplify" method will remove the points 1 and 3 and give me a diagonal
		//
		//  0
		//
		//     1
		//
		//        2
		
		
		//it will not change the main shape of the path, it's a kind of anti-smoother
		
		
		int len = points.length ;
		BorderPt pt1, pt2, pt3,pt4,pt5;
		ArrayList<BorderPt> _points = new ArrayList<BorderPt>();
		
		String d1 = directions.substring(0, 1);
		String d2 = directions.substring(1, 2);
		String d;
		int i, j;
		
		for (i = 2; i < len; i++) {
			d = directions.substring(i, i+1);
			if (d != d1 && d != d2) {
				String begin = directions.substring(i,directions.length());
				BorderPt[] beginPt = new BorderPt[len-i];
				System.arraycopy(points, i, beginPt, 0, beginPt.length);
				
				String end = directions.substring(0, i-1);
				BorderPt[] endPt = new BorderPt[i];
				System.arraycopy(points, 0, endPt, 0, endPt.length);
				
				directions = begin + end;
				points = new BorderPt[beginPt.length+endPt.length+2];
				System.arraycopy(beginPt, 0, points, 0, beginPt.length);
				System.arraycopy(endPt, 0, points, beginPt.length, endPt.length);
				
				break;
			}
		}
		
		
		
		_points.add(points[0]);
		_points.add(points[1]);
		String oldDir = directions.substring(0, 1);
		String dir;
		String _dir;
		int len2;
		boolean bool = false;
		String newDirection = oldDir;
		
		String old;
		String old2;
		len--;
		for (i = 1; i < len; i++) {
			dir = directions.substring(i, i+1);
			if (dir != oldDir) {
				if (i + 1 < len) len2 = i +1;
				else len2 = len;
				
				for (j = i + 1; j < len2; j++) {
					old = directions.substring(j, j+1);
					bool = true;
					newDirection += dir;
					_points.add(points[j]);
					i = j+1;
					old2 = old;
				}
				
				if (!bool) {
					newDirection += dir;
					_points.add(points[i]);
				}
				oldDir = dir;
			}
		}
		_points.add(_points.get(0));
		
		directions = newDirection;
		points = new BorderPt[_points.size()];
		len = points.length;
		for(i=0;i<len;i++) points[i] = _points.get(i);
	}
	
	
	protected void simplify2() {
		
		//follow the path without XY datas but using the directions of the path from the old pixel to the new one.
		
		//each pixel is surround by 8 pixels
		// 0 1 2
		// 7 X 3
		// 6 5 4
		
		//the "directions data" is a String that contains a list of int that represent the direction of the path, pixel by pixel
		
		//this function only remove the point with the same direction of the older.
		
		int len = points.length ;
		int i;
		ArrayList<BorderPt> results = new ArrayList<BorderPt>();
		String d;
		String newDirection = "";
		String d1 = "";
		String d2 = "";;

		
		for (i = 0; i < len; i++) {
			d = directions.substring(i,i+1);
			if (d != d1 && d != d2) {
				results.add(points[i]);
				newDirection += directions.substring(i, i+1);
				d2 = d1;
				d1 = d;
			}else {
				d2 = d1;
				d1 = d;
			}
		}
		newDirection += directions.substring(0, 1);
		results.add(results.get(0));
		
		points = new BorderPt[results.size()];
		len = points.length;
		for(i=0;i<len;i++) points[i] = results.get(i);
		
		directions = newDirection;
		
	}
	*/
	
}
