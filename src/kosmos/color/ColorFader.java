package kosmos.color;

public class ColorFader {
	
	private static ColorFader instance;
	
	ColorFader(){
		instance = this;
	}
	
	public static ColorFader getInstance(){
		if(null == instance) new ColorFader();
		return instance;
	}
	
	public int fader(int color1,int color2,float percent){
		
		percent %= 1.0f;
		
		
		int a1 = color1 >>> 24;
		int r1 = color1 >>> 16 & 0xFF;
		int g1 = color1 >>>  8 & 0xFF;
		int b1 = color1 & 0xFF;
		
		int a2 = color2 >>> 24;
		int r2 = color2 >>> 16 & 0xFF;
		int g2 = color2 >>>  8 & 0xFF;
		int b2 = color2 & 0xFF;
		
		a1 = (int) (a1 + (a2 - a1) * percent);
		r1 = (int) (r1 + (r2 - r1) * percent);
		g1 = (int) (g1 + (g2 - g1) * percent);
		b1 = (int) (b1 + (b2 - b1) * percent);
		
		return a1 << 24 | r1 << 16 | g1 << 8 | b1;
	}
	
	
	
	
	public int getBrightness(int color,float percent){//time entre 0 et 1
		return fader(color,0xffffffff,percent);
	}
	public int getDarkness(int color,float percent){//time entre 0 et 1
		return fader(color,0xff000000,percent);
	}
	
}
