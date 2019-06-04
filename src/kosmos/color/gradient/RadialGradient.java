package kosmos.color.gradient;

public class RadialGradient extends Gradient {
	
	private static RadialGradient instance;
	
	RadialGradient(){
		super();
		instance = this;
	}
	
	public static RadialGradient getInstance(){
		if(null == instance) new RadialGradient();
		return instance;
	}
	
	private int[] createColorMap(int w,int h){
		int w2 = w >> 1;
		int h2 = h >> 1;
		
		int maxDist = (int) (1+Math.sqrt(w2*w2+h2*h2));
		int[] colorByDist = new int[maxDist];
		
		
		int i,j,k,col,len,len1;
		float ratio,pct;
		
		col = a[0] << 24 | r[0] << 16 | g[0] << 8 | b[0];;
		int px = 0;
		float oldR = ratios[0];
		if(oldR != 0){
			len = (int)(oldR * maxDist);
			for(px=0;px<len;px++) colorByDist[px] = col;
		}
		
		
		int a1,a2,r1,r2,g1,g2,b1,b2,a3,r3,g3,b3;
		
		for(i=1;i<nb;i++){
			//col1 = colors[i-1];
			//col2 = colors[i];
			
			
			a1 = a[i-1];
			a2 = a[i];
			r1 = r[i-1];
			r2 = r[i];
			g1 = g[i-1];
			g2 = g[i];
			b1 = b[i-1];
			b2 = b[i];
			
			
			
			ratio = ratios[i];
			
			len = (int)((ratio-oldR) * maxDist);
			
			oldR = ratio;
			
			len1 = len-1;
			for(j=0;j<len;j++){
				pct = (float)j / (float)len1;
				
				a3 = (int) ((float)(a1 + (a2 - a1) * pct));
				r3 = (int) ((float)(r1 + (r2 - r1) * pct));
				g3 = (int) ((float)(g1 + (g2 - g1) * pct));
				b3 = (int) ((float)(b1 + (b2 - b1) * pct));
				
				col = a3 << 24 | r3 << 16 | g3 << 8 | b3;
				
				colorByDist[px] = col;
				//for(k = 0;k<h;k++) set(px,k,col);
				px++;
			}
		}
		
		while(px < maxDist){
			colorByDist[px] = col;
			//for(k = 0;k<h;k++) set(px,k,col);	
			px++;
		}

		return colorByDist;
	}
	
	public void render(int w,int h){
		
		resize(w,h);
		
		int[] colorByDist = createColorMap(w,h);
		
		System.out.println(colorByDist.length);
		
		int w2 = w >> 1;
		int h2 = h >> 1;
		int dx,dy,d;
		int i,j;
		
		for(i=0;i<w;i++){
			for(j=0;j<h;j++){
				dx = i - w2;
				dy = j - h2;
				set(i,j, colorByDist[ (int) Math.sqrt(dx*dx+dy*dy) ] );
			}
		}
	}
	
}
