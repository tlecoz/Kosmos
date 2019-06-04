package kosmos.uiToolbox.textures;

import kosmos.texture.TextureArea;

public class ComposedTextureObj {
	
	protected TextureArea[] _areas;
	
	public ComposedTextureObj(int nbArea){
		_areas = new TextureArea[nbArea];
	}
	
	public TextureArea[] getAreas(){
		return _areas;
	}
}
