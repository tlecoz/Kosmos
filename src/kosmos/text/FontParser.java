package kosmos.text;

import kosmos.utils.AppletInstance;
import processing.core.PApplet;
import processing.core.PImage;

public class FontParser {
	
	public int textSize;
	public int cellSize;
	public int nbChar;
	public int nbX;
	public int nbY;
	public String[] charList;
	public float[] widths;
	public PImage texture;
	public float wMax;
	private String[] allChars;
	
	
	FontParser(String fontName){
		
		
		
		String allCharacters = "azertyuiopqsdfghjklmwxcvbnAZERTYUIOPQSDFGHJKLMWXCVBN@<>0132456789+-*/=%&, ;:!.?}])^_`|(['{#ηΰκιθβο~ωµ$£²§°";
		allCharacters += '"';
		
		allChars = new String[allCharacters.length()];
		widths = new float[allCharacters.length()];
		
		
		int i,len = allCharacters.length();
		for(i=0;i<len;i++) allChars[i] = allCharacters.substring(i,i+1);
		
		
		
		
		
		PApplet applet = AppletInstance.applet;
		PImage temp = applet.loadImage(fontName+".png");
		
		texture = new PImage(temp.width,temp.height-1,2);
		texture.copy(temp, 0, 1, temp.width, temp.height-1, 0, 0, temp.width, temp.height-1);
		
		int n = (int)Math.abs(applet.color(0,0,0,255));
		
		int px = 0;
		
		
		
		textSize = n+temp.get(px, 0);
		px += 2;
		nbChar = n+temp.get(px, 0);
		px += 2;
		cellSize = n+temp.get(px, 0);
		px += 2;
		
		nbX = (int) Math.floor(temp.width / cellSize);
		nbY = (int) Math.ceil(((nbChar/2.0f)/nbX));
		
		
		System.out.println(temp.width+" / "+cellSize);
		
		charList = new String[nbChar];
		wMax = 0;
		for(i=0;i<nbChar;i++){
			charList[i] = allChars[n + temp.get(px, 0)];
			widths[i] = ((n + temp.get(px+1, 0)) / 1000.0f);
			//System.out.println(charList[i]+" : "+widths[i]);
			if(widths[i]>wMax) wMax = widths[i];
			px += 2;
		}
		
		////System.out.println(textSize+" : "+nbChar+" : "+cellSize+" : "+nbX+" : "+nbY);
		
	}
	
	
}
