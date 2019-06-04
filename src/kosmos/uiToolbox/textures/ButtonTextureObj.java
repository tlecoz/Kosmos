package kosmos.uiToolbox.textures;

import processing.core.PImage;
import kosmos.texture.TextureArea;

public class ButtonTextureObj  {
	
	public int x;
	public int y;
	public int w;
	public int h;
	
	
	public TextureArea normal;
	public TextureArea over;
	public TextureArea selected;
	
	
	
	ButtonTextureObj(PImage mainTexture,int btnW,int btnH){
		normal = new TextureArea(mainTexture,btnW,btnH);
		over = new TextureArea(mainTexture,btnW,btnH);
		selected = new TextureArea(mainTexture,btnW,btnH);
		
		x = y = 0;
		w = (btnW+5) * 3;
		h = btnH;
	}
	
	
	
	
	
	
}
