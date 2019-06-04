package kosmos.displayList.displayObjects.textfields;

import java.util.ArrayList;

import kosmos.displayList.Sprite3D;
import kosmos.displayList.displayObjects.TextCharacter;
import kosmos.displayList.layers.Layer3D;
import kosmos.events.EventDispatcher3D;
import kosmos.events.IEventReceiver;
import kosmos.text.Font;
import kosmos.text.TextFormat;
import kosmos.utils.Alert;

public class TextField extends Sprite3D implements IEventReceiver{
	
	private TextFormat textFormat;
	private Font font;
	private float charSize;
	private float charScale;
	
	
	public String text;
	private String _text;
	
	private float textSize;
	private float letterSpacing;
	private float lineSpacing;
	private boolean italic;
	private boolean bold;
	private int align;
	private float textfieldW;
	private float textfieldH;
	
	
	
	private TextCharacter[] allChars;
	private int nbCharMax;
	private int nbCharUsed;
	
	private int nbTextfieldLineUsed;
	private int nbTextfieldLineMax;
	private int nbTextLine;
	
	
	
	public TextField(Layer3D layer,float w,float h,int nbChar){
		super();
		if(layer.defaultTextFormat == null){
			Alert.error("TextField", "constructor", "you must add a font to a layer before the creation of a TextField object");
		}
		init(layer,w,h,layer.defaultTextFormat,nbChar);
	}
	
	public TextField(Layer3D layer,float w,float h,TextFormat tf,int nbChar){
		super();
		String fn = tf.getFont().getFontName(); 
		if(layer.fontExists(fn) == false){
			Alert.error("TextField", "constructor", "You're tring to apply a TextFormat from one layer to another layer. Each TextFormat is 'linked' to a layer, thay can't be shared from a layer to an other" );
		}
		init(layer,w,h,tf,nbChar);
	}
	
	
	
	private void init(Layer3D layer,float w,float h,TextFormat tf,int nbChar){
		textfieldW = w;
		textfieldH = h;
		text = _text = "";
		nbCharMax = nbChar;
		nbCharUsed = 0;
		textFormat = tf;
		textFormat.addEventListener((IEventReceiver) this);
		
		nbTextfieldLineMax = (int) Math.floor(textfieldH / (tf.getTextSize() + tf.getLineSpacing())  );
		
		Font f = tf.getFont();
		
		int i;
		allChars = new TextCharacter[nbChar];
		TextCharacter t;
		for(i=0;i<nbChar;i++){
			allChars[i] = t = f.createTextCharacterInstance(layer," ", 0, 0, tf.getTextSize());
			
			appendObject(t);
		}
		
		applyTextFormat();
	}
	
	
	
	public float getTextfieldWidth(){ return textfieldW; }
	public float getTextfieldHeight(){ return textfieldH; }
	
	public void setTextfieldWidth(float n){ 
		textfieldW = n;
		updateCharacterPosition();
	}
	public void setTextfieldHeight(float n){
		textfieldH = n; 
		updateCharacterPosition();
	}
	
	
	public TextFormat getTextFormat(){
		return textFormat;
	}
	public void setTextFormat(TextFormat tf){
		
		if(tf != textFormat){
			textFormat = tf;
			applyTextFormat();
		}
		
	}
	
	private void applyTextFormat(){
		
		font = textFormat.getFont();
		// //////System.out.println("font = "+font);
		changeTextSize();
		
		redMulti = textFormat.getRed();
		greenMulti = textFormat.getGreen();
		blueMulti = textFormat.getBlue();
		alpha = textFormat.getAlpha();
		
		letterSpacing = textFormat.getLetterSpacing();
		lineSpacing = textFormat.getLineSpacing();
		
		italic = textFormat.getItalic();
		bold = textFormat.getBold();
		
		align = textFormat.getAlign();
		
		applyBold();
		applyItalic();
		updateCharacterPosition();
		
	}
	
	private void changeTextSize(){
		
		charSize = font.getCharacterMaxHeight();
		textSize = textFormat.getTextSize();
		charScale = textSize / charSize; 
		
		int i,len = children.size();
		for(i=0;i<len;i++) ( (TextCharacter) children.get(i)).changeScale(charScale);
		
		updateCharacterPosition();
		
	}
	
	/*
	public void changeFont(){
		
		font = textFormat.getFont();
		charSize = font.getCharSize();
		textSize = textFormat.getTextSize();
		charScale = textSize / charSize; 
		
		int i,len = children.size();
		for(i=0;i<len;i++) {
			( (TextCharacter) children.get(i)).changeFont(font,charScale);
		}
		//stage.packTextures();
		updateCharacterPosition();
		
	}
	*/
	
	private void applyBold(){
		int i,len = children.size();
		for(i=0;i<len;i++) ( (TextCharacter) children.get(i)).bold(bold);
		updateCharacterPosition();
	}
	private void applyItalic(){
		int i,len = children.size();
		for(i=0;i<len;i++) ( (TextCharacter) children.get(i)).italic(italic);
	}
	
	
	
	private void leftAlignPosition(){
		float px = 0,py = 0;
		int i,len = children.size();
		TextCharacter t;
		
		float lineH = textSize + lineSpacing;
		float wMax = font.getCharacterMaxWidth();
		//////System.out.println("> "+len);
		
		px = 0;//(wMax/2) ;
		py = textSize/2;
		for(i=0;i<len;i++){
			t = (TextCharacter) children.get(i);
			
			if(i != 0) px += t.getCharWidth()/2;
			else  {
				px = wMax / 4.0f  ;
				py = -lineH/4.0f;
			}
			
			if(px +  t.getCharWidth()/2 + letterSpacing > textfieldW) {
				px = wMax / 4.0f ;
				py += lineH;
			}
			t.x = px;
			t.y = py;
			
			px += t.getCharWidth()/2 + letterSpacing;
		}
	}
	
	private void centerAlignPosition(){
		float px = 0,py = 0;
		int i,len = children.size();
		TextCharacter t;
		
		float lineH = textSize + lineSpacing;
		float wMax = font.getCharacterMaxWidth();
		//////System.out.println("> "+len);
		
		px = 0;//(wMax/2) ;
		py = textSize/2;
		for(i=0;i<len;i++){
			t = (TextCharacter) children.get(i);
			
			if(i != 0) px += t.getCharWidth()/2;
			else  {
				px = -textfieldW/2.0f + wMax / 4.0f  ;
				py = -lineH/4.0f;
			}
			
			if(px +  t.getCharWidth()/2 + letterSpacing > textfieldW/2.0) {
				px = -textfieldW/2.0f + wMax / 4.0f ;
				py += lineH;
			}
			t.x = px;
			t.y = py;
			
			px += t.getCharWidth()/2 + letterSpacing;
		}
	}
	
	private void rightAlignPosition(){
		float px = 0,py = 0;
		int i,len = children.size();
		TextCharacter t;
		
		float lineH = textSize + lineSpacing;
		float wMax = font.getCharacterMaxWidth();
		//////System.out.println("> "+len);
		
		px = 0;//(wMax/2) ;
		py = textSize/2;
		for(i=0;i<len;i++){
			t = (TextCharacter) children.get(i);
			
			if(i != 0) px += t.getCharWidth()/2;
			else  {
				px = -textfieldW + wMax / 4.0f  ;
				py = -lineH/4.0f;
			}
			
			if(px +  t.getCharWidth()/2 + letterSpacing > 0) {
				px = -textfieldW + wMax / 4.0f ;
				py += lineH;
			}
			t.x = px;
			t.y = py;
			
			px += t.getCharWidth()/2 + letterSpacing;
		}
	}
	
	private void updateCharacterPosition(){
		if(align == 0) leftAlignPosition();
		else if(align == 1) centerAlignPosition();
		else if(align == 2) rightAlignPosition();
	}
	
	
	
	
	public void applyEvent(String action, EventDispatcher3D obj){
		//System.out.println("tf changed");
		if(obj == textFormat){
			int eventID = Integer.parseInt(action);
			switch(eventID){
				/*case TextFormat.FONT_CHANGED:
					font = textFormat.getFont();
					changeFont();
					changeTextSize();
					
					break;*/
			
				case TextFormat.COLOR_CHANGED:
					redMulti = textFormat.getRed();
					greenMulti = textFormat.getGreen();
					blueMulti = textFormat.getBlue();
					alpha = textFormat.getAlpha();
					break;
				case TextFormat.TEXTSIZE_CHANGED:
					changeTextSize();
					break;
				case TextFormat.LETTERSPACING_CHANGED:
					letterSpacing = textFormat.getLetterSpacing();
					updateCharacterPosition();
					break;
				case TextFormat.LINESPACING_CHANGED:
					lineSpacing = textFormat.getLineSpacing();
					updateCharacterPosition();
					break;
				case TextFormat.BOLD_CHANGED:
					bold = textFormat.getBold();
					applyBold();
					break;
				case TextFormat.ITALIC_CHANGED:
					italic = textFormat.getItalic();
					applyItalic();
					break;
				case TextFormat.ALIGN_CHANGED:
					align = textFormat.getAlign();
					updateCharacterPosition();
					break;
			} 
		}
		
	}
	
	
	 public void setStage(Layer3D r){
		 //r.initTextfieldFont(this);
	     super.setLayer(r);
	  }
	
	public void updatePosition(){
		super.updatePosition();
		
		
		
		////////System.out.println(">>> "+_text.length());
		
		if(text.equals(_text) == false){
			int i,len = text.length();
			TextCharacter t;
			Font f = textFormat.getFont();
			for(i=0;i<nbCharMax;i++){
				t = allChars[i];
				if(i<len){
					t.visible = true;
					////System.out.println("--------->> "+text.substring(i,i+1));
					t.updateCharacterTexture(f.getUvByChar(text.substring(i,i+1)));
				}else{
					t.visible = false;
				}
			}
			_text = text;
			updateCharacterPosition();
			
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
