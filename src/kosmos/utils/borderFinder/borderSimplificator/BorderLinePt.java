package kosmos.utils.borderFinder.borderSimplificator;

import kosmos.utils.borderFinder.BorderPt;

public class BorderLinePt implements Comparable{
	int id;
	int d;
	float a;
	BorderPt p1;
	BorderPt p2;
	float x;
	float y;
	BorderLinePt(){
		
	}
	
	public int compareTo(Object o){
		return d - ((BorderLinePt) o).d;
	}
	
}
