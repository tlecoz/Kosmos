package kosmos.displayList.displayObjects;

import kosmos.displayList.Particle3D;
import kosmos.displayList.layers.Layer3D;
import kosmos.text.Font;
import kosmos.text.TextCharUv;
import kosmos.texture.ParticleTextureUv;



public class TextCharacter extends Particle3D {
	
	protected float boldScale;
	protected float italicScale;
	protected boolean applyBold;
	protected boolean applyItalic;
	protected float scale;
	protected float charWidth;
	
	public TextCharacter(Layer3D layer,TextCharUv uvChar,float posx,float posy,float scale){

	    super(layer,uvChar);

	    x = posx;
	    y = posy;
	    z = 0.0f;
	    
	    charWidth = uvChar.w;
	    boldScale = 1.1f;
	    italicScale = 1.1f;
	    applyBold = false;
	    applyItalic = false;
	    
	    changeScale(scale);
	}

	
	public void updateCharacterTexture(TextCharUv characterUv){
		if(null == characterUv) return;
		
		charWidth = characterUv.w;
		updateTexture(characterUv);
	}
		
	public void changeFont(Font f,float scale){
	    changeScale(scale);
	    updateCharacterTexture(f.getUvByName(((TextCharUv)uv).value));
	}
	
	public void changeScale(float s){
		scale = s;
		if(false == applyBold) scaleX = scaleY = scaleZ = scale;
		else scaleX = scaleY = scaleZ = scale * boldScale;
	}
	
	public float getCharWidth(){
		return charWidth* scaleZ;
	}
	
	public void bold(boolean b){
		applyBold = b;
		changeScale(scale);
	}
	
	public void bold(boolean b,float boldScaleRatio){
		boldScale = boldScaleRatio;
		bold(b);
	}
	
	public void italic(boolean b){
		applyItalic = b;
		changeScale(scale);
	}
	
	public void italic(boolean b,float italicScaleRatio){
		italicScale = italicScaleRatio;
		italic(b);
	}
	
	
	protected int setupTriangle(ParticleTextureUv uv){
		
		float tw = (float) uv.getTexture().w;
		float th = (float) uv.getTexture().h;
		
		//System.out.println("setupTriangle = "+tw+" : "+th);
		
		float _x0 = -1.0f * tw;
	    float _y0 = 0.5f * th;
	    float _x1 = 0f * tw;
	    float _y1 = -0.5f * th;
	    float _x2 = 1f * tw;
	    float _y2 = 0.5f * th;
	    
	    int i0 = layer.getNewVertex(_x0,_y0,0,uv.u0,uv.v0);
	    int i1 = layer.getNewVertex(_x1,_y1,0,uv.u1,uv.v1);
	    int i2 = layer.getNewVertex(_x2,_y2,0,uv.u2,uv.v2);
	    
	    layer.addNewTriangle(this,i0,i1,i2);
	    
	    return i0;
	}
	
}
