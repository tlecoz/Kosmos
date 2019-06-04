package kosmos.uiToolbox.elements;

import kosmos.displayList.displayObjects.Plane;
import kosmos.displayList.layers.Layer3D;
import kosmos.texture.TextureArea;
import kosmos.uiToolbox.textures.ButtonTextureObj;

public class UIButton extends Plane {
	
	static int NORMAL = 0;
	static int OVER = 0;
	static int SELECTED = 0;
	
	protected boolean selectable;
	protected boolean selected;
	protected boolean isOver;
	
	public TextureArea overTexture;  
	public TextureArea normalTexture;
	public TextureArea selectedTexture;
	
	
	
	UIButton(Layer3D renderer,ButtonTextureObj textureObj,boolean isSelectable,boolean isSelected){
		super(renderer,textureObj.w,textureObj.h,textureObj.normal);
		overTexture = textureObj.over;
		selectedTexture = textureObj.selected;
		selected = isSelected;
		selectable = isSelectable;
		isOver = false;
	}
	
	public void setState(int buttonState){
		if(0 == buttonState){
			texture = normalTexture;
			isOver = selected = false;
		}
		else if(1 == buttonState){
			texture = overTexture;
			isOver = true;
			selected = false;
		}
		else if(2 == buttonState){
			texture = selectedTexture;
			selected = true;
		}
	}
	
	public void select(){
		if(selected == false) setState(SELECTED);
	}
	
	public void unselect(){
		if(selected == true) setState(NORMAL);
	}
	
	public void onRollOver(){
		if(isOver == false) setState(OVER);
	}
	
	public void onRollOut(){
		if(isOver == true) setState(NORMAL);
	}
	
	
	
	
}
