package kosmos.displayList.layers;

import java.util.ArrayList;

import processing.core.PApplet;
import kosmos.events.EventDispatcher3D;

public class KosmosRenderer extends EventDispatcher3D {
	
	protected PApplet applet;
	
	protected ArrayList<Renderer> renderList;
	
	public KosmosRenderer(PApplet appletObj){
		applet = appletObj;
		renderList = new ArrayList<Renderer>();
	}
	
	public Renderer createRenderer(boolean checkMouseActivity){
		Renderer r = new Renderer(applet,checkMouseActivity);
		renderList.add(r);
		return r;
	}
	public Renderer createRenderer(){
		Renderer r = new Renderer(applet,true);
		renderList.add(r);
		return r;
	}
	
	public void update(){
		int i,len = renderList.size();
		boolean checkMouseAction = true;
		Renderer r;
		for(i=len-1;i>-1;i--){
			r = renderList.get(i);
			
			r.mouseX = applet.mouseX;
			r.mouseY = applet.mouseY;
			r.mousePressed = applet.mousePressed;
			if(r.update(checkMouseAction) != null) checkMouseAction = false;
			
			applet.image(r, 0, 0);
		}
	}
	
}
