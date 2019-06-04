package kosmos.displayList.displayObjects;

import kosmos.displayList.Particle3D;
import kosmos.displayList.layers.Layer3D;
import kosmos.utils.Point3D;

public class Line extends Particle3D {
	
	private float _x0;
	private float _y0;
	private float _z0;
	
	private float _x1;
	private float _y1;
	private float _z1;
	
	private Point3D _p0;
	private Point3D _p1;
	
	private boolean updatePosition;
	private boolean useTargets;
	
	public Line(Layer3D layer,float x0,float y0,float z0,float x1,float y1,float z1){
		super(layer,layer.getSquareParticleTexture());
		_x0 = x0;
		_y0 = y0;
		_z0 = z0;
		_x1 = x1;
		_y1 = y1;
		_z1 = z1;
		
		updatePosition = true;
		useTargets = false;
	}
	
	public Line(Layer3D layer,float x0,float y0,float x1,float y1){
		super(layer,layer.getSquareParticleTexture());
		_x0 = x0;
		_y0 = y0;
		_z0 = 0;
		_x1 = x1;
		_y1 = y1;
		_z1 = 0;
		
		updatePosition = true;
		useTargets = false;
	}
	
	public Line(Layer3D layer,Point3D p0,Point3D p1){
		super(layer,layer.getSquareParticleTexture());
		
		_p0 = p0;
		_p1 = p1;
		
		updatePosition = true;
		useTargets = true;
	}
	
	
	
	
	protected void updateVertexPosition(){
		
		if(useTargets){
			_x0 = _p0.x;
			_y0 = _p0.y;
			_z0 = _p0.z;
			_x1 = _p1.x;
			_y1 = _p1.y;
			_z1 = _p1.z;
				
		}
		
		
		if(updatePosition){
			float dx = _x1 - _x0;
			float dy = _y1 - _y0;
			float dz = _z1 - _z0;
			
			float d = (float) Math.sqrt(dx*dx+dy*dy+dz*dz) / 2f;
			
			float a_xy = (float) Math.atan2(dy,dx);
			float a_xz = (float) Math.atan2(dy, dx);
			
			x = (float) (_x0 + Math.cos(a_xy) * d);
			y = (float) (_y0 + Math.sin(a_xy) * d);
			z = (float) (_z0 + Math.sin(a_xz) * d);
			
			rotationZ = a_xy;
			rotationX = a_xz;
			
		}
			
		
		
		
		
		super.updateVertexPosition();
	}
}
