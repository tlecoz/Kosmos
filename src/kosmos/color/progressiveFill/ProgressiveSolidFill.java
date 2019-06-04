package kosmos.color.progressiveFill;

import processing.data.IntList;

public class ProgressiveSolidFill extends ProgressiveFillAbstract {
	
	ProgressiveSolidFill(){
		super();
	}
	
	public void initFill(int color) {
																	
		currentColor = color;
	}
	
	protected void fillInvisiblePixels(int nbPass) {
		
		super.fillInvisiblePixels(nbPass);
		
		int i, nb;
		IntList v;
		boolean b = true;
		
		while(nbPass-- > 0){
			nb = len;
			v = pixels;
			pixels = new IntList();
			len = 0;
			waves.add(new IntList());	
			for (i = 0; i < nb; i += 2) {
				if (b) addProximityInvisiblePixel(v.get(i), v.get(i + 1));
				else addPixelToList(v.get(i),v.get(i+1));
				b = !b;
			}
			
			frame++;
			
		}
		
	}
	
	protected void fillVisiblePixels(int nbPass) {
		
		super.fillVisiblePixels(nbPass);
		
		int i, nb;
		IntList v;
		boolean b = true;
		
		while(nbPass-->0){
			nb = len;
			v = pixels;
			pixels = new IntList();
			len = 0;
			waves.add(new IntList());	
			for (i = 0; i < nb; i += 2) {
				if (b) addProximityVisiblePixel(v.get(i), v.get(i + 1));
				else addPixelToList(v.get(i), v.get(i + 1));
				b = !b;
			}
			
			frame++;
		}
		
	}	
	
}
