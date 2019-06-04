package kosmos.text;

import java.util.ArrayList;
import java.util.HashMap;

import kosmos.displayList.displayObjects.TextCharacter;
import kosmos.displayList.layers.Layer3D;
import kosmos.texture.TextureArea;
import kosmos.texture.TwoTriangleTextureArea;
import kosmos.uiToolbox.textures.ComposedTextureObj;
import kosmos.utils.AppletInstance;
import kosmos.utils.GraphicUtils;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.data.XML;

public class Font {
	
	//public static String defaultFontName = "Arial";
	//private static Font defaultFont;
	private static ArrayList<String> fontNames = new ArrayList<String>();
	private static HashMap<String,Font> fontByName = new HashMap<String,Font>(128);;
	
	private String name;
	
	private HashMap<String,TextCharUv> uvByString;
	
	public ComposedTextureObj fontTexture;
	//private TwoCharacterTextureArea[] charTextures;
	
	
	private float x0;
	private float y0;
	private float x1;
	private float y1;
	private float x2;
	private float y2;
	private float charSize;
	private float hMax;
	private float wMax;
	private int cellSize;
	
	public Font(PApplet applet,String fontName){
		  
		  name = fontName;
		  ////System.out.println("Font name = "+fontName);
		  
		  FontParser parser = new FontParser(fontName);
		  int nbX = parser.nbX;
		  int nbY = parser.nbY;
		  int textSize = parser.textSize;
		  cellSize = parser.cellSize;
		  int nbChar = parser.nbChar;
		  String[] charList = parser.charList;
		  float[] widths = parser.widths;
		  PImage texture = parser.texture;
		  
		  //computeTriangleVertexPosition();
		  
		  PGraphics g = GraphicUtils.getGraphics();
		  g.setSize(cellSize*nbX, cellSize*nbY);
		  g.beginDraw();
		  g.clear();
		  g.background(0,0,0,0);
		  g.noStroke();
		  g.noFill();
		  g.image(texture,0,0);
		  g.endDraw();
		  
		  int nbSubTexture = (int) Math.ceil(nbChar/2.0f);
		  
		  fontTexture = new ComposedTextureObj(nbSubTexture);
		  TextureArea[] charTextures = fontTexture.getAreas();
		  
		  System.out.println(fontName+" ###### 111111 ######## "+nbSubTexture);
		  
		  //charTextures = new TwoCharacterTextureArea[nbX*nbY];
		  int i,j,k = 0,charId = 0;
		  TwoCharacterTextureArea t;
		  
		  uvByString = new HashMap<String,TextCharUv>(nbChar);
		  
		  
		  System.out.println(nbX+" : : : "+nbY);
		  
		  for (i = 0; i < nbY; i++){
			  for (j = 0; j < nbX; j++){
				  
				  System.out.println(charId+" = "+cellSize+" : "+charList[charId]+" : "+charList[charId+1]);
				  charTextures[k++] = t = new TwoCharacterTextureArea(cellSize,charList[charId],charList[charId+1],widths[charId],widths[charId+1]);
				  //t.setTemporaryImage( GraphicUtils.getImage(j*cellSize, i*cellSize, cellSize, cellSize, 0, 0, cellSize, cellSize) );
				 
				  t.setTemporaryImage(texture, j*cellSize, i*cellSize, cellSize, cellSize);
				  
				  
				  uvByString.put(charList[charId++], (TextCharUv) t.top);
				  if(charId < nbChar) uvByString.put(charList[charId++], (TextCharUv) t.bottom);
				  if(charId == nbChar) break;
			  }
		  }  
		  
		  ////System.out.println("###### 222222 ########");
		  g.background(0,0,0, 0);
		  
		  fontByName.put(name,this);
		  fontNames.add(name);
		  
		  
		  
		  hMax = (float) cellSize - textSize;
		  wMax = parser.wMax;
		  
		  
		  
		  
		  
		  
		 
	}
	
	public void addFontToRenderer(Layer3D renderer){
		////System.out.println("addFontToRenderer");
		renderer.addTextureImage(fontTexture);
	}
	
	public TextCharUv getUvByName(String n){
		return uvByString.get(n);
	}
	
	
	public static Font getFontByName(String n){
		return fontByName.get(n);
	}
	public static boolean fontExists(String fontName){
		int i,len = fontNames.size();
		for(i=0;i<len;i++) if(fontNames.get(i) == fontName) return true;
		return false;
	}
	
	public float getCharSize(){
		return charSize;
	}
	public float getCharacterMaxHeight(){
		return hMax;
	}
	public float getCharacterMaxWidth(){
		return wMax;
	}
	public String getFontName(){
		return name;
	}
	
	
	
	public TextCharUv getUvByChar(String charText){
		return uvByString.get(charText);
	}
	
	
	public TextCharacter createTextCharacterInstance(Layer3D layer,String charString, float posx,float posy,float textSize){
		return new TextCharacter(layer,uvByString.get(charString),posx,posy,textSize/ hMax);
	}
	
	
	
	
	
}
