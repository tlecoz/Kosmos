package kosmos.uiToolbox;

import kosmos.displayList.layers.Layer3D;
import kosmos.displayList.layers.Renderer;
import kosmos.text.Font;
import kosmos.text.TextFormat;
import kosmos.texture.TextureArea;
import processing.core.PApplet;
import processing.core.PImage;

public class UILayer extends Layer3D {
	
	private TextureArea _scrollBtUpTexture;
	private TextureArea _scrollBtDownTexture;
	private TextureArea _scrollBgTexture;
	private TextureArea _scrollCursorTexture;
	
	private TextureArea _radioBtnOnTexture;
	private TextureArea _radioBtnOffTexture;
	
	private TextureArea _checkboxOnTexture;
	private TextureArea _checkboxOffTexture;
	
	private TextureArea _arrowUpTexture;
	private TextureArea _arrowDownTexture;
	
	private TextFormat _labelTextFormat;
	
	private TextureArea _allColorTexture;
	
	UILayer(Renderer renderer,int nbTriangleMax){
		
		super(renderer,nbTriangleMax,true,false);
		
		PApplet applet = renderer.getApplet();
		
		_scrollBtUpTexture = addTextureImage(applet.loadImage("buttonUp.png"));
		_scrollBtDownTexture = addTextureImage(applet.loadImage("buttonDown.png"));
		_scrollBgTexture = addTextureImage(applet.loadImage("scrollBg.png"));
		_scrollCursorTexture = addTextureImage(applet.loadImage("scrollCursor.png"));
		
		_radioBtnOnTexture = addTextureImage(applet.loadImage("radioBtn_off.png"));
		_radioBtnOffTexture = addTextureImage(applet.loadImage("radioBtn_on.png"));
		
		_checkboxOnTexture = addTextureImage(applet.loadImage("checkbox_off.png"));
		_checkboxOffTexture = addTextureImage(applet.loadImage("checkbox_on.png"));
		
		_arrowUpTexture = addTextureImage(applet.loadImage("arrowUp.png"));
		_arrowDownTexture = addTextureImage(applet.loadImage("arrowDown.png"));
		
		_allColorTexture = addTextureImage(applet.loadImage("color.jpg"));
		
		addFont("Arial.png");
		float labelTextSize = 12f;
		float labelLetterSpacing = labelTextSize / 4f;
		_labelTextFormat = createTextFormat("Arial",TextFormat.LEFT_ALIGN,applet.color(0,0,0,255),labelTextSize,labelLetterSpacing,0);
	}
	
	
	public TextureArea getScrollBtUpTexture(){
		return _scrollBtUpTexture;
	}
	public TextureArea getScrollBtDownTexture(){
		return _scrollBtDownTexture;
	}
	public TextureArea getScrollBgTexture(){
		return _scrollBgTexture;
	}
	public TextureArea getScrollCursorTexture(){
		return _scrollCursorTexture;
	}
	
	public TextureArea getRadioBtnOnTexture(){
		return _radioBtnOnTexture;
	}
	public TextureArea getRadioBtnOffnTexture(){
		return _radioBtnOffTexture;
	}

	public TextureArea getCheckboxOnTexture(){
		return _checkboxOnTexture;
	}
	public TextureArea getCheckboxOffTexture(){
		return _checkboxOffTexture;
	}

	public TextureArea getArrowUpTexture(){
		return _arrowUpTexture;
	}
	public TextureArea getArrowDownTexture(){
		return _arrowDownTexture;
	}
	
	public TextFormat getLabelTextFormat(){
		return _labelTextFormat;
	}
}
