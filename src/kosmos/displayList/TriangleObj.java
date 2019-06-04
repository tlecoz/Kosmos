package kosmos.displayList;

import kosmos.displayList.layers.Renderer;
import processing.core.PApplet;
import processing.core.PGraphics;

public class TriangleObj implements Comparable {
	 
	   
	
	 private int i0x2;
	 private int i1x2;
	 private int i2x2;
	 
	 private int i0y2;
	 private int i1y2;
	 private int i2y2;

	 private int i0x3;
	 private int i1x3;
	 private int i2x3;
	 
	 private int i0y3;
	 private int i1y3;
	 private int i2y3;
	 
	 private int i0z3;
	 private int i1z3;
	 private int i2z3;
	 
	 private float[] allTransformXYZ;
	 private float[] allVertexUV;
	 private float[] allXYUV;
	 
	 private Renderer renderer;
	
	 //private PApplet applet;
	 
	 public int z;
	 public Shape3D obj;
	 public Boolean visible;
	 private Boolean renderable;
	 
	 
	 private float minimalTriangleSize;
	 private float minimalZ;
	 private float maximalZ;
	 private float cameraZ;
	 private float maxX;
	 private float maxY;
	 private float maxZ;
	 
	 private boolean useLight;
	 
	 
	 public TriangleObj(Renderer rendererObj,Shape3D target,int _i0,int _i1,int _i2,float[] _allTransformXYZ,float[] _allVertexUV,float[] _allXYZUV){
	  
	  //applet = appletObj;
	  renderer = rendererObj;
	  
	  i0x2 = _i0 * 2;
	  i1x2 = _i1 * 2;
	  i2x2 = _i2 * 2; 
	   
	  i0y2 = _i0 * 2 + 1;
	  i1y2 = _i1 * 2 + 1;
	  i2y2 = _i2 * 2 + 1;  
	   
	  i0x3 = _i0 * 3;
	  i1x3 = _i1 * 3;
	  i2x3 = _i2 * 3; 
	   
	  i0y3 = _i0 * 3 + 1;
	  i1y3 = _i1 * 3 + 1;
	  i2y3 = _i2 * 3 + 1; 
	   
	  i0z3 = _i0 * 3 + 2;
	  i1z3 = _i1 * 3 + 2;
	  i2z3 = _i2 * 3 + 2;
	  
	  visible = renderable = true;
	  
	  obj = target;
	  allTransformXYZ = _allTransformXYZ;
	  allVertexUV = _allVertexUV;
	  allXYUV = _allXYZUV;
	  
	  ////System.out.println(obj.renderer.focalLength+" + "+obj.renderer.root.z);
	  minimalTriangleSize = 1f;
	  minimalZ = (float) (obj.layer.focalLength)*5;
	  maxZ = (float) 50000f;
	  maxX = renderer.getWidth();
	  maxY = renderer.getHeight();
	  
	  
	  useLight = true;
	  
	  //centerX = maxX/2;
	  //centerY = maxY/2;
	  //centerDist = (float)Math.sqrt(centerX*centerX + centerY*centerY);
	  
	 } 
	 
	 public void applyLight(boolean b){
		 useLight = b;
	 }
	 
	 public void setMinimalZ(float camZ,float minZ){
		 cameraZ = (float) camZ * 3f;
		 minimalZ = (float) -cameraZ-(minZ);
		 maximalZ = maxZ - cameraZ;
		// System.out.println("maxZ = "+maxZ+" : "+maximalZ+" : "+minZ + " : "+cameraZ);
	 }
	 public void maximalZ(float cz){
		// maxZ = (float) (cz*3f);
		// maximalZ = maxZ + cameraZ * 3f;
	 }
	 
	 public void setMinimalTriangleSize(float f){
		 minimalTriangleSize = f;
	 }
	 
	 public void dispose(){
		 obj = null;
		 visible = renderable = false;
	 }
	 
	 
	 
	 public void updateZ(){
	   if(visible){
		   
		   z =  (int) ((allTransformXYZ[i0z3] + allTransformXYZ[i1z3] + allTransformXYZ[i2z3]) - cameraZ ) ;
		  //System.out.println(minimalZ+" VS "+z+" VS "+maximalZ);
		   //float pz = z;
		   renderable = z > -minimalZ && z < maximalZ && (allTransformXYZ[i0x3] >= 0 || allTransformXYZ[i1x3] >= 0 || allTransformXYZ[i2x3] >= 0) && (allTransformXYZ[i0x3] < maxX || allTransformXYZ[i1x3] < maxX || allTransformXYZ[i2x3] < maxX) && (allTransformXYZ[i0y3] >= 0 || allTransformXYZ[i1y3] >= 0 || allTransformXYZ[i2y3] >= 0) && (allTransformXYZ[i0y3] < maxY || allTransformXYZ[i1y3] < maxY || allTransformXYZ[i2y3] < maxY) ; 
		   
		   
		   if(renderable){
			   float dx0 =  allTransformXYZ[i0x3] - allTransformXYZ[i1x3];
			   float dx1 =  allTransformXYZ[i1x3] - allTransformXYZ[i2x3];
			   float dy0 =  allTransformXYZ[i0y3] - allTransformXYZ[i1y3];
			   float dy1 =  allTransformXYZ[i1y3] - allTransformXYZ[i2y3];
			   
			   renderable = (Math.sqrt(dx0*dx0+dy0*dy0) * Math.sqrt(dx1*dx1+dy1*dy1)) >= minimalTriangleSize;
		   }
		   
		   
		   
		   z *= 333; //multipy to get a larger z-range for z-sorting based on int
	  
	   }
	 }
	 
	 /*
	 public void drawTriangle2(){
		 if(visible == false || renderable == false) return ;
		 
		 int n = countObj.id;
		 int id = n * 5;
		 allXYUV[id++] = allTransformXYZ[i0x3];
		 allXYUV[id++] = allTransformXYZ[i0y3];
		 //allXYUV[id++] = n++ * 0.001f;
		 allXYUV[id++] = allVertexUV[i0x2];
		 allXYUV[id++] = allVertexUV[i0y2];
		 
		 allXYUV[id++] = allTransformXYZ[i1x3];
		 allXYUV[id++] = allTransformXYZ[i1y3];
		 //allXYUV[id++] = n++ * 0.001f;
		 allXYUV[id++] = allVertexUV[i1x2];
		 allXYUV[id++] = allVertexUV[i1y2];
		 
		 allXYUV[id++] = allTransformXYZ[i2x3];
		 allXYUV[id++] = allTransformXYZ[i2y3];
		 //allXYUV[id++] = n++ * 0.001f;
		 allXYUV[id++] = allVertexUV[i2x2];
		 allXYUV[id++] = allVertexUV[i2y2];
		 
		 countObj.id += 3;
		 
	 }
	 */
	 
	 
	 /*
	  * Begin Function CalculateSurfaceNormal (Input Triangle) Returns Vector
 
	Set Vector U to (Triangle.p2 minus Triangle.p1)
	Set Vector V to (Triangle.p3 minus Triangle.p1)
 
	Set Normal.x to (multiply U.y by V.z) minus (multiply U.z by V.y)
	Set Normal.y to (multiply U.z by V.x) minus (multiply U.x by V.z)
	Set Normal.z to (multiply U.x by V.y) minus (multiply U.y by V.x)
 
	Returning Normal
 
End Function
	  */
	 
	 
	 public DisplayObject3D drawTriangle(DisplayObject3D currentDrawObj){ 
		 
		 if(renderable == false || visible == false) return currentDrawObj;
		 
		 obj.____applyColorTransform(currentDrawObj);
		 
		 
		 ////System.out.println("drawTriangle  "+allVertexUV[i0x2]+" , "+allVertexUV[i0y2]+" , "+allVertexUV[i1x2]+" , "+allVertexUV[i1y2]+" , "+allVertexUV[i2x2]+" , "+allVertexUV[i2y2]);
		 
		 //System.out.println(i0x3+" !!! drawTriangle  "+allTransformXYZ[i0x3]+" , "+allTransformXYZ[i0y3]+" , "+allTransformXYZ[i1x3]+" , "+allTransformXYZ[i1y3]+" , "+allTransformXYZ[i2x3]+" , "+allTransformXYZ[i2y3]);
		 
		 
		 if(useLight){
			 
			 float ux = allTransformXYZ[i1x3] - allTransformXYZ[i0x3];
			 float uy = allTransformXYZ[i1y3] - allTransformXYZ[i0y3];
			 float uz = allTransformXYZ[i1z3] - allTransformXYZ[i0z3];
			 
			 float vx = allTransformXYZ[i2x3] - allTransformXYZ[i0x3];
			 float vy = allTransformXYZ[i2y3] - allTransformXYZ[i0y3];
			 float vz = allTransformXYZ[i2z3] - allTransformXYZ[i0z3];
			 
			 renderer.normal(uy * vz - uz * vy , uz * vx - ux * vz, ux * vy - uy * vx);
			 
		 }
		 
		 
		 
		 
		 
		 
		 renderer.vertex(allTransformXYZ[i0x3],allTransformXYZ[i0y3],allVertexUV[i0x2],allVertexUV[i0y2]);
		 renderer.vertex(allTransformXYZ[i1x3],allTransformXYZ[i1y3],allVertexUV[i1x2],allVertexUV[i1y2]);
		 renderer.vertex(allTransformXYZ[i2x3],allTransformXYZ[i2y3],allVertexUV[i2x2],allVertexUV[i2y2]);
	     
	     
	     
	     return obj;
	 }
	 
	 /*
	 boolean drawTriangleAndCheckMouseIfMouseIsOver(float mouseX,float mouseY){
		 if(visible == false || renderable == false) return false;
		 
		 
		 applet.vertex(allTransformXYZ[i0x3],allTransformXYZ[i0y3],allVertexUV[i0x2],allVertexUV[i0y2]);
		 applet.vertex(allTransformXYZ[i1x3],allTransformXYZ[i1y3],allVertexUV[i1x2],allVertexUV[i1y2]);
		 applet.vertex(allTransformXYZ[i2x3],allTransformXYZ[i2y3],allVertexUV[i2x2],allVertexUV[i2y2]);
		 
		 return hitTest(mouseX,mouseY);
		 
	 }
	 */
	 
	 
	 
	
	 
	 
	 
	 
	 public Boolean hitTest(float posx,float posy){
	    
	    if(visible == false) return false;
	    
	    float p1x = allTransformXYZ[i0x3];
	    float p1y = allTransformXYZ[i0y3];
	    float p2x = allTransformXYZ[i1x3];
	    float p2y = allTransformXYZ[i1y3];
	    float p3x = allTransformXYZ[i2x3];
	    float p3y = allTransformXYZ[i2y3];
	 
	    float d1 =  p1x * (p2y - posy) + p2x * (posy - p1y) + posx * (p1y - p2y);
	    float d2 =  p2x * (p3y - posy) + p3x * (posy - p2y) + posx * (p2y - p3y);
	    float d3 =  p3x * (p1y - posy) + p1x * (posy - p3y) + posx * (p3y - p1y);
	 
	    if ((d1 > 0 && d2 > 0 && d3 > 0) || (d1 < 0 && d2 < 0 && d3 < 0)) {
	      return true;
	    }
	    
	    return false;
	 }
	  
	   
	 public int compareTo(Object o){
	  return z-((TriangleObj) o).z ; 
	 }
	 
	 
	 
	}
