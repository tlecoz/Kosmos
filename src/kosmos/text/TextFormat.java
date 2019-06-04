package kosmos.text;

import kosmos.events.EventDispatcher3D;
import kosmos.utils.Alert;
import kosmos.utils.AppletInstance;

public class TextFormat extends EventDispatcher3D {
	
	public static final int FONT_CHANGED = 0;
	public static final int TEXTSIZE_CHANGED = 1;
	public static final int COLOR_CHANGED = 2;
	public static final int LETTERSPACING_CHANGED = 3;
	public static final int LINESPACING_CHANGED = 4;
	public static final int BOLD_CHANGED = 5;
	public static final int ITALIC_CHANGED = 6;
	public static final int ALIGN_CHANGED = 7;
	
	public static final int LEFT_ALIGN = 0;
	public static final int CENTER_ALIGN = 1;
	public static final int RIGHT_ALIGN = 2;
	
	private static TextFormat defaultTextFormat;
	
	private float size;
	private int color;
	private float r;
	private float g;
	private float b;
	private float a;
	private float letterSpacing;
	private float lineSpacing;
	private boolean bold;
	private boolean italic;
	private Font font;
	private int align;
	
	public TextFormat(Font textFont,int textFormatAlign,int textColor,float textSize, float letterSpace,float lineSpace,boolean useBold,boolean useItalic){
		super();
		
		font = textFont;
		size = textSize;
		color = textColor;
		letterSpacing = letterSpace;
		lineSpacing = lineSpace;
		bold = useBold;
		italic = useItalic;
		align = textFormatAlign;
		
		if(textFormatAlign > 2 ) Alert.error("TextFormat", "constructor", "TextFormat.align must be equals to TextFormat.LEFT_ALIGN, TextFormat.CENTER_ALIGN, or TextFormat.RIGHT_ALIGN");
		
		a = (float) (color >>> 24)/255.0f;
		r = (float) (color >>> 16 & 0xFF)/255.0f;
		g = (float) (color >>>  8 & 0xFF)/255.0f;
		b = (float) (color & 0xFF)/255.0f;
		
	}
	/*
	public TextFormat(Font textFont,int textColor,float textSize, float letterSpace,float lineSpace){
		super();
		
		font = textFont;
		size = textSize;
		color = textColor;
		letterSpacing = letterSpace;
		lineSpacing = lineSpace;
		bold = false;
		italic = false;
		
		a = color >>> 24;
		r = color >>> 16 & 0xFF;
		g = color >>>  8 & 0xFF;
		b = color & 0xFF;
	}
	
	public TextFormat(Font textFont,int textColor,float textSize){
		super();
		
		font = textFont;
		size = textSize;
		color = textColor;
		letterSpacing = 0;
		lineSpacing = 0;
		bold = false;
		italic = false;
		
		a = color >>> 24;
		r = color >>> 16 & 0xFF;
		g = color >>>  8 & 0xFF;
		b = color & 0xFF;
	}
	
	*/
	
	
	
	public Font getFont(){ return font;};
	public float getTextSize(){ return size;};
	public int getColor(){ return color;};
	public float getRed(){ return r;};
	public float getGreen(){ return g;};
	public float getBlue(){ return b;};
	public float getAlpha(){ return a;};
	public float getLetterSpacing(){ return letterSpacing;};
	public float getLineSpacing(){ return lineSpacing;};
	public boolean getBold(){ return bold;};
	public boolean getItalic(){ return italic;};
	public int getAlign(){return align;};
	
	

	/*
	public void setFont(Font n){
		if(font != n){
			font = n;
			dispatchEvent(""+FONT_CHANGED);
		}
	}
	*/
	
	
	public void setAlign(int textFormatAlign){
		if(align != textFormatAlign){
			if(textFormatAlign > 2 ) Alert.error("TextFormat", "setAlign", "TextFormat.align must be equals to TextFormat.LEFT_ALIGN, TextFormat.CENTER_ALIGN, or TextFormat.RIGHT_ALIGN");
			align = textFormatAlign;
			dispatchEvent(""+ALIGN_CHANGED);
		}
	}
	
	public void setTextSize(float n){
		if(size != n){
			size = n;
			dispatchEvent(""+TEXTSIZE_CHANGED);
		}
	}
	public void setColor(int n){ 
		if(color != n){
			color = n;
			a = (float) (color >>> 24)/255.0f;
			r = (float) (color >>> 16 & 0xFF)/255.0f;
			g = (float) (color >>>  8 & 0xFF)/255.0f;
			b = (float) (color & 0xFF)/255.0f;
			dispatchEvent(""+COLOR_CHANGED);
		}
	}
	public void setRed(float n){
		if(r != n){
			r = n;
			dispatchEvent(""+COLOR_CHANGED);
		}
	}
	public void setGreen(float n){
		if(g != n){
			g = n;
			dispatchEvent(""+COLOR_CHANGED);
		}
	}
	public void setBlue(float n){
		if(b != n){
			b = n;
			dispatchEvent(""+COLOR_CHANGED);
		}
	}
	public void setAlpha(float n){
		if(a != n){
			a = n;
			dispatchEvent(""+COLOR_CHANGED);
		}
	}
	public void setLetterSpacing(float n){
		if(letterSpacing != n){
			letterSpacing = n;
			dispatchEvent(""+LETTERSPACING_CHANGED);
		}
	}
	public void setLineSpacing(float n){
		if(lineSpacing != n){
			lineSpacing = n;
			dispatchEvent(""+LINESPACING_CHANGED);
		}
	}
	public void setBold(boolean n){
		if(bold != n){
			bold = n;
			dispatchEvent(""+BOLD_CHANGED);
		}
	}
	public void setItalic(boolean n){
		if(italic != n){
			italic = n;
			dispatchEvent(""+ITALIC_CHANGED);
		}	
	}
	
}
