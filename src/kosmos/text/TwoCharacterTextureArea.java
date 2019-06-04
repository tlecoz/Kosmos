 package kosmos.text;

import kosmos.texture.TwoTriangleTextureArea;

public class TwoCharacterTextureArea extends TwoTriangleTextureArea {
	
	TwoCharacterTextureArea(int size,String topText,String bottomText,float topW,float bottomW){
		super(size,size);
		top = new TextCharUv(this,topText,topW);
		bottom = new TextCharUv(this,bottomText,bottomW);
	}
	
	protected void initUvs(){
		if(null != bottom) bottom.initUV(maxX, maxY,minX, minY, maxX, minY);
		if(null != top) top.initUV(minX, minY, maxX, maxY, minX, maxY);
	}
	
	
	
	
}
