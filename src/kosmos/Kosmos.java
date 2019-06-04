package kosmos;

import kosmos.examples.Example01;
import kosmos.examples.Example01_particle;
import kosmos.examples.Example02;
import kosmos.examples.Example03;
import kosmos.examples.Example04;
import kosmos.examples.Example05;
import kosmos.examples.IExample;
import kosmos.utils.borderFinder.BorderFinder;
import kosmos.utils.borderFinder.BorderPt;
import kosmos.utils.borderFinder.borderSimplificator.BorderSimplificator;
import processing.core.PApplet;
import processing.core.PImage;


public class Kosmos extends PApplet {

	IExample example;
	//Example03 example;
	
	private float angle = 0f;
	
	public void setup(){ 
		size(1600,1000,P3D);
		example = new Example01_particle(this);
		//example = new Example02(this);
		//example = new Example03(this);
		//example = new Example04(this);
		//example = new Example05(this);
	} 
	 
	public void draw(){ 
		
		angle += PI/180f;
		//background(255,255,255);
		background(0,0,0);
		//directionalLight(255, 255, 255, 0, 0, -1);
		//spotLight(255, 255, 255, width/2, -5000f,0, 0, 1, 0, PI/1.5f, 2);
		
		example.update();
		
		
		
	} 
}
