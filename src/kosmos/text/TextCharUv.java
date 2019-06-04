package kosmos.text;

import kosmos.texture.ParticleTextureUv;

public class TextCharUv extends ParticleTextureUv{
	
	
	public String value;
	
	public TextCharUv(TwoCharacterTextureArea charTexture,String charText,float charWidth){
		super(charTexture);
		value = charText;
		w = charWidth*1.05f;
		texture = charTexture;
	}
	
	
}
