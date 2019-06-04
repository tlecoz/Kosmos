package kosmos.uiToolbox.textures;

import processing.core.PGraphics;
import kosmos.texture.TextureArea;
import kosmos.utils.GraphicUtils;

public class RoundRectTextureObj extends ComposedTextureObj{
	
	
	public TextureArea topLeft;
	public TextureArea top;
	public TextureArea topRight;
	public TextureArea left;
	public TextureArea center;
	public TextureArea right;
	public TextureArea bottomLeft;
	public TextureArea bottom;
	public TextureArea bottomRight;
	
	public int corner;
	

	
	
	
	
	public RoundRectTextureObj(int fillColor,int strokeColor,int strokeWeight,int cornerRadius){
		
		super(9);
		
		corner = (int) (cornerRadius);
		topLeft = 	_areas[0] = new TextureArea(corner,corner);
		top =	 	_areas[1] = new TextureArea(corner,corner);
		topRight =	_areas[2] = new TextureArea(corner,corner);
		right = 	_areas[3] = new TextureArea(corner,corner);
		bottomRight=_areas[4] = new TextureArea(corner,corner);
		bottom = 	_areas[5] = new TextureArea(corner,corner);
		bottomLeft =_areas[6] = new TextureArea(corner,corner);
		left = 		_areas[7] = new TextureArea(corner,corner);
		center = 	_areas[8] = new TextureArea(corner,corner);
		
		getGraphicImage(fillColor,strokeColor,strokeWeight,corner);
	}
	
	
	private void getGraphicImage(int fillColor,int strokeColor,int strokeWeight,float cornerRadius){
		
		
		
		PGraphics g = GraphicUtils.getGraphics();
		
		g.setSize((int) (cornerRadius * 4),(int)(cornerRadius*4));
		g.beginDraw();
		g.background(0,0,0,0);
		g.fill(fillColor);
		g.stroke(strokeColor);
		g.strokeWeight(strokeWeight);
		g.rect(0.0f, 0.0f, cornerRadius*4.0f,cornerRadius*4.0f,cornerRadius);
		g.endDraw();
		
		//topLeft
		_areas[0].setTemporaryImage( (GraphicUtils.getImage(0, 0,(int) cornerRadius,(int) cornerRadius, 0, 0,(int) cornerRadius,(int) cornerRadius)) ) ;
		//top
		_areas[1].setTemporaryImage( (GraphicUtils.getImage((int) cornerRadius, 0,(int) cornerRadius,(int) cornerRadius, 0, 0,(int) cornerRadius,(int) cornerRadius)) );
		//topRight
		_areas[2].setTemporaryImage( (GraphicUtils.getImage((int) cornerRadius*3, 0,(int) cornerRadius,(int) cornerRadius, 0, 0,(int) cornerRadius,(int) cornerRadius)) );
		//right
		_areas[3].setTemporaryImage( (GraphicUtils.getImage((int) cornerRadius*3, (int) cornerRadius,(int) cornerRadius,(int) cornerRadius, 0, 0,(int) cornerRadius,(int) cornerRadius)) );
		//bottomRight
		_areas[4].setTemporaryImage( (GraphicUtils.getImage((int) cornerRadius*3, (int) cornerRadius*3,(int) cornerRadius,(int) cornerRadius, 0, 0,(int) cornerRadius,(int) cornerRadius)) );
		//bottom
		_areas[5].setTemporaryImage( (GraphicUtils.getImage((int) cornerRadius, (int) cornerRadius*3,(int) cornerRadius,(int) cornerRadius, 0, 0,(int) cornerRadius,(int) cornerRadius)) );
		//bottomLeft
		_areas[6].setTemporaryImage( (GraphicUtils.getImage(0, (int) cornerRadius*3,(int) cornerRadius,(int) cornerRadius, 0, 0,(int) cornerRadius,(int) cornerRadius)) );
		//left
		_areas[7].setTemporaryImage( (GraphicUtils.getImage(0, (int) cornerRadius,(int) cornerRadius,(int) cornerRadius, 0, 0,(int) cornerRadius,(int) cornerRadius)) );
		//center
		_areas[8].setTemporaryImage( (GraphicUtils.getImage((int) cornerRadius, (int) cornerRadius ,(int) cornerRadius,(int) cornerRadius, 0, 0,(int) cornerRadius,(int) cornerRadius)) );
		
		g.background(0,0,0,0);
	}
	
	
	
	
}
