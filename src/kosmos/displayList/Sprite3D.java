package kosmos.displayList;

import java.util.ArrayList;

import kosmos.displayList.displayObjects.Line;
import kosmos.displayList.displayObjects.textfields.TextField;
import kosmos.displayList.layers.Layer3D;
import kosmos.utils.Point3D;

public class Sprite3D extends DisplayObject3D {
	  
	 
	  
	  public ArrayList<DisplayObject3D> children;
	  
	  //private boolean _useLocalParentRotation;
	  //private boolean _useLocalRotation;
	  
	  public boolean useChildrenLocalParentRotation;
	  public boolean useChildrenLocalRotation;
	  private boolean _useChildrenLocalParentRotation;
	  private boolean _useChildrenLocalRotation;
	  
	  
	  
	  
	  
	  public Sprite3D(){
	    super();
	    visible = true;
	    children = new ArrayList<DisplayObject3D>();
	    _useChildrenLocalParentRotation = _useChildrenLocalRotation = useChildrenLocalParentRotation = useChildrenLocalRotation = false;
	    
	  }
	  
	  public void dispose(){
		  super.dispose();
		  int i,len = children.size()-1;
		  for(i=len;i>-1;i--){
			  children.get(i).dispose();
			  children.remove(i);
		  }
		  children = null;
	  }
	  
	  
	  
	  
	  public DisplayObject3D appendObject(DisplayObject3D o){
	    children.add(o);
	    //System.out.println(layer);
	    if(layer != null) o.setLayer(layer);
	    o.setParent(this);
	    return o;
	  }
	  
	  public Object3D removeObject(Object3D o){
	    children.remove(children.lastIndexOf(o));
	    o.setParent(null);
	    o.setLayer(null);
	    return o;
	  }
	  public Object3D removeObject(Object3D o,boolean clearObject){
	    children.remove(children.lastIndexOf(o));
	    if(clearObject) o.dispose();
	    o.setParent(null);
	    o.setLayer(null);
	    return o;
	  }
	  
	  
	  public void setLayer(Layer3D r){
	    super.setLayer(r);
	    int i,len = children.size();
	    for(i=0;i<len;i++) children.get(i).setLayer(r); 
	  }
	  
	  protected void updateTriangleVisibility(){
		  int i,len = children.size();
		  for(i=0;i<len;i++) children.get(i).updateTriangleVisibility();
	  }
	  
	  
	  private void applyLocalParentRotation(boolean b){
		  int i,len = children.size();
		  for(i=0;i<len;i++) children.get(i).useLocalParentRotation = b;
	  }
	  private void applyLocalRotation(boolean b){
		  int i,len = children.size();
		  for(i=0;i<len;i++) children.get(i).useLocalRotation = b;
	  }
	  public void updatePosition(){
		  //_realX = parent._realX + x;
		  //_realY = parent._realY + y;
		  //_realZ = parent._realZ + z;
		  
		  if(useChildrenLocalParentRotation != _useChildrenLocalParentRotation){
			  _useChildrenLocalParentRotation = useChildrenLocalParentRotation;
			  applyLocalParentRotation(useChildrenLocalParentRotation);
		  }
		  
		  if(useChildrenLocalRotation != _useChildrenLocalRotation){
			  _useChildrenLocalRotation = useChildrenLocalRotation;
			  applyLocalRotation(useChildrenLocalRotation);
		  }
		  
		  
		  
		  
		  _realSX = parent._realSX * scaleX;
		  _realSY = parent._realSY * scaleY;
		  _realSZ = parent._realSZ * scaleZ;
		    
		  _realRX = parent._realRX + rotationX;
		  _realRY = parent._realRY + rotationY;
		  _realRZ = parent._realRZ + rotationZ;
		  
		  _realRed = parent._realRed * redMulti;;
		  _realGreen = parent._realGreen * greenMulti;
		  _realBlue = parent._realBlue * blueMulti;
		  _realAlpha = parent._realAlpha * alpha;
		  
		  
		  
		  float px,py,pz,xy,xz,yz,yx,zx,zy;
	      float ppx,ppy,ppz;
	      
	      float psx,pcx,psy,pcy,psz,pcz;
	      
	      if(useLocalParentRotation == false){
	      
		      psx = (float) Math.sin(_realRX);
		      pcx = (float) Math.cos(_realRX);
		      
		      psy = (float) Math.sin(_realRY);
		      pcy = (float) Math.cos(_realRY);
		      
		      psz = (float) Math.sin(_realRZ);
		      pcz = (float) Math.cos(_realRZ);
	      
	      }else{
	    	  psx = (float) Math.sin(rotationX);
		      pcx = (float) Math.cos(rotationX);
		      
		      psy = (float) Math.sin(rotationY);
		      pcy = (float) Math.cos(rotationY);
		      
		      psz = (float) Math.sin(rotationZ);
		      pcz = (float) Math.cos(rotationZ);
	      
	      }
	      
	      ppx = x;
	      ppy = y;
	      ppz = z;
	      
	      
	      float scaleFactor;
	      
	      //ROTATION X
	      xy = pcx * ppy - psx * ppz;
	      xz = psx * ppy + pcx * ppz;

	      //ROTATION Y
	      yz = pcy * xz - psy * ppx;
	      yx = psy * xz + pcy * ppx;

	      //ROTATION Z
	      zx = pcz*yx - psz*xy;
	      zy = psz*yx + pcz*xy;
	      
	      
	      scaleFactor = focalLength/(focalLength -parent._realZ+ yz);
	        
	      ppx = zx * scaleFactor;
	      ppy = zy * scaleFactor;
	      ppz = yz ;
	      
	      
	      
	      _realX = ppx + parent._realX;
	      _realY = ppy + parent._realY;
	      _realZ = ppz + parent._realZ;
		  
		  
		  //stage.applet.println(_realZ);
		  
		  
		  
		  
	    
	      int i,len = children.size();
	      for(i=0;i<len;i++)children.get(i).updatePosition();
	  }
	 
	  
	  public void updateVertexPosition(){
	    if(visible){
	      int i,len = children.size();
	      for(i=0;i<len;i++)children.get(i).updateVertexPosition(); 
	    }
	  }
	  
	  
	  
	  
	  
	  
	  //------------------------- LINES ----------------------------
	  
	  
	  public Line drawLineBetween(Point3D p0,Point3D p1){
		 Line line = new Line(layer,p0,p1);// = new Line(layer,p0,p1);  
	  
		 return line;
	  }
	  
	  
	  
	  
	  
	  
	  
	}
