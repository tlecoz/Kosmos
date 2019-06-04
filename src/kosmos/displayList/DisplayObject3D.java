package kosmos.displayList;

import kosmos.displayList.layers.Layer3D;
import kosmos.displayList.layers.Renderer;
import kosmos.texture.TextureArea;

public class DisplayObject3D extends Object3D {
	
	public float _realRed;
	public float _realGreen;
	public float _realBlue;
	public float _realAlpha;
	
	//getter & setter
	public float redMulti;
	public float greenMulti;
	public float blueMulti;
	public float alpha;
	
	public boolean visible;
	private boolean _visible;
	
	public TextureArea texture;
	  
	
	//must be used as getter only
	public float width;
	public float height;
	public float depth;
	
	
	protected float offsetPositionX;
	protected float offsetPositionY;
	protected float offsetPositionZ;
	
	protected boolean mouseIsOver;
	
	protected float minimalX;
    protected float minimalY;
    protected float maximalX;
    protected float maximalY;
  
    protected float boundMinX;
    protected float boundMaxX;
    protected float boundMinY;
    protected float boundMaxY;
    protected float boundMinZ;
    protected float boundMaxZ;
    protected long getWidthTime;
    protected long getHeightTime;
    protected long getDepthTime;
	
    
    public TriangleObj[] allTriangles;
    protected int startTriangleId;
	protected int nbTriangle;
    
	
	 public boolean useLocalParentRotation; 
	 public boolean useLocalRotation; 
	
	 protected Renderer renderer;
	 
	DisplayObject3D(){
		super();
		_realRed = _realGreen = _realBlue = _realAlpha = redMulti = greenMulti = blueMulti = alpha = (float) 1.0;
		width = height = depth = 0.0f;
	    visible = _visible = true;
	    texture = null;
	    offsetPositionX = offsetPositionY = 0;
	    useLocalParentRotation = useLocalRotation = false;
	}
	
	public void dispose(){
		super.dispose();
		_realRed = _realGreen = _realBlue = _realAlpha = redMulti = greenMulti = blueMulti = alpha = (float) 0.0;
		width = height = depth = 0.0f;
	    visible = _visible = false;
	    texture = null;
	    minimalX = minimalY = maximalX = maximalY = 0;
	    boundMinX = boundMinY = boundMaxX = boundMaxY = boundMinZ = boundMaxZ = 0;
	    getWidthTime = getHeightTime = getDepthTime = 0;
	}
	
	 protected void updateTriangleVisibility(){
	    int i,len = startTriangleId + nbTriangle;
	    for(i=startTriangleId;i<len;i++) allTriangles[i].visible = visible;
	  }
	 
	 protected void onCameraPositionChanged(){
		 super.onCameraPositionChanged();
		 int i,len = startTriangleId + nbTriangle;
		 cameraZ = layer.camera.z;
		 minimalZ = (float) (layer.camera.z- focalLength);
		 for(i=startTriangleId;i<len;i++) allTriangles[i].setMinimalZ(cameraZ,minimalZ);
		  
	  }
	  
	  
	  public void setMouseIsOver(boolean b){
		  mouseIsOver = b;
		  if(mouseIsOver == true) dispatchEvent("ON_MOUSE_OVER");
		  else dispatchEvent("ON_MOUSE_OUT");
	  }
	  
	  public void changeTexture(TextureArea t){
		  dispatchEvent("ON_TEXTURE_CHANGE");
		  texture = t;
		  
		  //must be overrided -->> must update the UV in the array
	  }
	  
	  public void mousePressed(){
		  dispatchEvent("ON_MOUSE_PRESS");
	  }
	  public void mouseReleased(){
		  dispatchEvent("ON_MOUSE_RELEASE");
	  }
	  public void mouseClick(){
		  dispatchEvent("ON_MOUSE_CLICK");
	  }
	  
	  protected void setLayer(Layer3D r){
	     super.setLayer(r);
	     if(layer != null){
	    	 
	    	 renderer = r.getRenderer();
	    	 
	    	 updateWidth();
		     updateHeight();
		     
		     minimalX = (float) ((float) -(width/2) * Math.sqrt(2));
		     minimalY = (float) ((float) -(height/2) * Math.sqrt(2));
		     
		     maximalX = (float) ((float) (r.stageW - minimalX) * Math.sqrt(2));
		     maximalY = (float) ((float) (r.stageH - minimalY) * Math.sqrt(2));
	    	 dispatchEvent("ON_ADDED_TO_LAYER");
	     }
	     else dispatchEvent("ON_REMOVED_FROM_LAYER");
	     
	     
	  }
	  protected void setParent(DisplayObject3D p){
	     parent = p;
	     if(parent != null) dispatchEvent("ON_ADDED");
	     else dispatchEvent("ON_REMOVED");
	  }
	  
	  
	  public void updatePosition(){
		  super.updatePosition();
		  
		  
		  _realRed = parent._realRed * redMulti;;
		  _realGreen = parent._realGreen * greenMulti;
		  _realBlue = parent._realBlue * blueMulti;
		  _realAlpha = parent._realAlpha * alpha;
		  
		  if(visible != _visible){
			  _visible = visible;
			  updateTriangleVisibility();
		  }
	  }
	  
	  
	  public void ____applyColorTransform(DisplayObject3D o){
		  if(this != o && layer != null) {
			 
			  renderer.tint(_realRed,_realGreen,_realBlue,_realAlpha);
		  }
	  }
	  
	  
	  void updateWidth(){
		  
	  }
	  
	  void updateHeight(){
		  
	  }
	  
	  void getDepth(){
		  
	  }
}
