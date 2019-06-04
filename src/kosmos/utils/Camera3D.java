package kosmos.utils;

import kosmos.displayList.Sprite3D;

public class Camera3D extends Sprite3D {
	
	
	private float oldZ;
	
	public Camera3D(){
		super();
		oldZ = -100000000000000f;
		
	}
	
	 public void updateVertexPosition(){
		 
		 if(z != oldZ) dispatchEvent("CAMERA_Z_CHANGED");
		 oldZ = z;
		 
		 
		 super.updateVertexPosition();
	 }
}
