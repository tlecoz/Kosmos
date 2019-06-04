package kosmos.utils.texturePacker;

import java.util.ArrayList;
import java.util.Collections;

import kosmos.texture.TextureArea;
import kosmos.uiToolbox.textures.ComposedTextureObj;
import kosmos.uiToolbox.textures.RoundRectTextureObj;
import kosmos.utils.Alert;
import processing.core.PImage;

public class TexturePacker extends PImage {
	
	
	public static int maxHeight = 8192;
	public static int maxWidth = 8192;
	
	
	
	ArrayList<TextureArea> subTextures;
	
	public TexturePacker(){
		super(2,2,2);
		subTextures = new ArrayList<TextureArea>();
	}
	
	
	
	public TextureArea addSubTexture(PImage img) {
		TextureArea t = new TextureArea(this,img);
		subTextures.add(t);
		return t;
	}
	public TextureArea addSubTexture(TextureArea area){
		subTextures.add(area);
		return area;
	}
	public ComposedTextureObj addSubTexture(ComposedTextureObj o){
		
		System.out.println("add composedTexture");
		
		TextureArea[] areas = o.getAreas();
		int i,len = areas.length;
		TextureArea a;
		for(i=0;i<len;i++){
			a = areas[i];
			if(null != a){
				a.setMainTexture(this);
				subTextures.add(areas[i]);
			}else{
				System.out.println(i);
			}
		}
		
		return o;
	}
	
	public void drawElementsAndGetCorrectUV(){
		
		
		if(subTextures.size() == 0) return;
		
		TextureArea[] result = null;
		int w;
		int h = 1024;
		while(h <= maxHeight){
			w = 32;
			while(result == null && w <= maxWidth){
				w *= 2;
				result = process(w,h);
			}
			if(result != null) break;
			else h *= 2;
		}
		
		if(result == null){
			Alert.error("Layer3D.texturePacker", "drawElementsAndGetCorrectUV", "texture size limit reached ( "+maxWidth+" x "+maxHeight+")" );
		}
		
		
		int maxX = 0;
		int maxY = 0;
		int i,len = result.length;
		TextureArea t;
		for(i=0;i<len;i++){
			t = result[i];
			if(t.x + t.w > maxX) maxX = t.x + t.w;
			if(t.y + t.h > maxY) maxY = t.y + t.h;
		}
		
		resize(getNextPowerOfTwo(maxX),getNextPowerOfTwo(maxY));
		
		System.out.println("TEXTURE W = "+width+" , H = "+height);
		
		for(i=0;i<len;i++){
			t = result[i];
			t.drawTemporaryOnTexture();
		}
		
		
		
	}
	private int getNextPowerOfTwo(int n){
		int c = 1;
		while(c < n) c *= 2;
		return c;
	}
	
	
	private TextureArea[] process(int sizeX,int sizeY){
		int i,len = subTextures.size();
		int k = 0;
		
		
		
		TextureArea[] result = new TextureArea[len];
		
		//System.out.println("nbSub = "+len);
		
		for(i=0;i<len;i++) subTextures.get(i).compareData = subTextures.get(i).w;
		Collections.sort(subTextures);
		
		for(i=0;i<len;i++) subTextures.get(i).compareData = subTextures.get(i).h;
		Collections.sort(subTextures);
		
		
		boolean multiLine = false;
		
		ArrayList<TextureArea> rest = new ArrayList<TextureArea>();
		ArrayList<TextureArea> a = subTextures;
		
		rest.add(subTextures.get(0));
		
		
		int px = 0,py = 0;
		int iY = 0;
		TextureArea t;
		
		
		while(rest.size() > 0 && a.size() > 0){
			rest = new ArrayList<TextureArea>();
			px = 0;
			py += iY;
			iY = a.get(0).h;
			
			if(py > sizeY){
				
				return null;
			}
			
			
			for(i=0;i<len;i++){
				t = a.get(i);
				
				if(px + t.w > sizeX){
					multiLine = true;
					rest.add(t);
				}else{
					t.x = px;
					t.y = py;
					////System.out.println(">!< "+t);
					//t.drawTemporaryOnTexture();
					result[k++] = t;
					px += t.w;
				}
			}
			
			a = rest;
			len = a.size();
			
		}
		
		return result;
	}
	
	
	
}
