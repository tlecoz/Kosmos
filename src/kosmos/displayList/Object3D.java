package kosmos.displayList;


import kosmos.displayList.layers.Layer3D;
import kosmos.events.EventDispatcher3D;
import kosmos.events.IEventReceiver;

public class Object3D extends EventDispatcher3D implements IEventReceiver{
	  
	
	 //X/Y/Z are contained inside Point3D which is contained in EventDispatcher3D
	
	  //public float x;
	  //public float y;
	  //public float z;
	  
	  protected float focalLength;
	  protected float minimalZ;
	  protected float cameraZ;
	  
	  public float scaleX;
	  public float scaleY;
	  public float scaleZ;
	  
	  public float rotationX;
	  public float rotationY;
	  public float rotationZ;
	  
	  public float _realX;
	  public float _realY;
	  public float _realZ;
	  public float _realSX;
	  public float _realSY;
	  public float _realSZ;
	  public float _realRX;
	  public float _realRY;
	  public float _realRZ;
	  
	  
	  
	  
	  public DisplayObject3D parent;
	  public Layer3D layer;
	  
	  
	  protected Object3D(){ 
		 
	    _realX = _realY = _realZ = _realRX = _realRY = _realRZ = x =  y = z = rotationX = rotationY = rotationZ = (float) 0.0;
	    _realSX = _realSY = _realSZ = scaleX = scaleY = scaleZ = (float) 1.0; 
	  }
	  
	  public void dispose(){
		  super.dispose();
		  _realX = _realY = _realZ = _realRX = _realRY = _realRZ = x =  y = z = rotationX = rotationY = rotationZ = (float) 0.0;
		  _realSX = _realSY = _realSZ = scaleX = scaleY = scaleZ = (float) 1.0; 
		  parent = layer = null;
		  focalLength = minimalZ = 0;
	  }
	  
	  
	  protected void setLayer(Layer3D r){
	     layer = r;
	     focalLength = layer.focalLength;
	     ////System.out.println(""+stage+" : "+stage.camera);
	     if(layer.camera != null){
	    	 cameraZ = layer.camera.z;
	    	 minimalZ = (float) (layer.camera.z- focalLength);
	    	 layer.camera.addEventListener( (IEventReceiver) this);
	    	 onCameraPositionChanged();
	    	 
	     }
	     
	  }
	  
	  
	  protected void onCameraPositionChanged(){
		  cameraZ = layer.camera.z;
		  minimalZ = (float) (layer.camera.z- focalLength);
	  }
	  
	  public void applyEvent(String action, EventDispatcher3D dispatcher){
		  if(action == "CAMERA_Z_CHANGED"){
			  onCameraPositionChanged();
		  }
		  
	  }
	  
	  protected void setParent(DisplayObject3D p){
	     parent = p;
	  }
	  
	  protected void updatePosition(){
	    
	    
	    _realX = parent._realX + x;
	    _realY = parent._realY + y;
	    _realZ = parent._realZ + z;
	     
	    _realSX = parent._realSX * scaleX;
	    _realSY = parent._realSY * scaleY;
	    _realSZ = parent._realSZ * scaleZ;
	    
	    _realRX = parent._realRX + rotationX;
	    _realRY = parent._realRY + rotationY;
	    _realRZ = parent._realRZ + rotationZ;
	    
	   
	   
	  }
	  
	  void updateVertexPosition(){
		  
	  }
	  
	  
	  
	}