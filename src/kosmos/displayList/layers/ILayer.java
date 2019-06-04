package kosmos.displayList.layers;

import kosmos.displayList.Shape3D;


public interface ILayer {
	public Shape3D update(boolean enableMouseEvent);
}
