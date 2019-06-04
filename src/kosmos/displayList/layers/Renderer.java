package kosmos.displayList.layers;

import java.util.ArrayList;

import kosmos.displayList.Shape3D;
import kosmos.events.EventDispatcher3D;
import kosmos.lights.ILight;
import kosmos.utils.Alert;
import kosmos.utils.AppletInstance;
import kosmos.utils.GraphicUtils;
import kosmos.utils.borderFinder.BorderFinder;
import kosmos.utils.borderFinder.borderSimplificator.BorderSimplificator;
import kosmos.utils.box2dUtils.earCutting.EarCutting;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.opengl.PGraphicsOpenGL;

public class Renderer extends PGraphicsOpenGL{
	
	
	private ArrayList<ILayer> layers;
	
	
	private PApplet applet;
	private float screenW;
	private float screenH;
	
	private ILight[] lightElements;
	private int nbLight;
	
	private boolean useMouseEvent;
	public Shape3D objectUnderMouse;
	
	public boolean mousePressed = false; //must be updated by the multi-renderer
	public float mouseX = 0;//must be updated by the multi-renderer
	public float mouseY = 0;//must be updated by the multi-renderer
	
	public boolean autoclear;
	
	//public PGraphics graphics;
	
	public Renderer(PApplet appletObj,boolean observMouseActivity){
		super();
		
		
	    applet = appletObj;
	    
	    
		useMouseEvent = observMouseActivity;
		initElements();
	}
	public Renderer(PApplet appletObj){
		applet = appletObj;
		useMouseEvent = false;
		initElements();
		
	}
	
	private void initElements(){
		
		autoclear = true;
		
		applet.hint(applet.DISABLE_DEPTH_TEST);
		
		setParent(applet);
	    setPrimary(false);
	    setSize(applet.width,applet.height);
		
	    lights();
	    lightElements = new ILight[8];
	    nbLight = 0;
	    
	    
		screenW = applet.width;
		screenH = applet.height;
		
		//graphics = applet.createGraphics((int)screenW,(int)screenH,applet.P3D);
		
		layers = new ArrayList<ILayer>();
		objectUnderMouse = null;
		new GraphicUtils(applet);
		new AppletInstance(applet);
		new BorderFinder();
		new BorderSimplificator();
		new EarCutting();
	}
	
	
	public float getWidth(){
		return screenW;
	}
	public float getHeight(){
		return screenH;
	}
	
	
	public ILight[] getLightList(){
		return lightElements;
	}
	public int __addLight(ILight light){ //must be called from class "Layer3D"
		if(nbLight == 8){
			Alert.error("Renderer", "addLight", "You can define a maximum of 8 lights by Renderer");
		}
		lightElements[nbLight++] = light;
		return nbLight - 1;
	}
	
	
	public Layer3D createLayer3D(String layerName,int nbTriangleMax,boolean enableMouseEvent,boolean removeDefaultParticleTexture){
		Layer3D layer = new Layer3D(this,screenW,screenH,nbTriangleMax,useMouseEvent,removeDefaultParticleTexture);
		layers.add(layer);
		layer.name = layerName;
		if(enableMouseEvent) useMouseEvent = true;
		return layer;
	}
	
	public Layer3D createLayer3D(int nbTriangleMax,boolean enableMouseEvent,boolean removeDefaultParticleTexture){
		Layer3D layer = new Layer3D(this,screenW,screenH,nbTriangleMax,enableMouseEvent,removeDefaultParticleTexture);
		layer.name = "Layer_"+layers.size();
		layers.add(layer);
		if(enableMouseEvent) useMouseEvent = true;
		return layer;
	}
	public Layer3D createLayer3D(int nbTriangleMax,boolean enableMouseEvent){
		Layer3D layer = new Layer3D(this,screenW,screenH,nbTriangleMax,enableMouseEvent,false);
		layer.name = "Layer_"+layers.size();
		layers.add(layer);
		if(enableMouseEvent) useMouseEvent = true;
		return layer;
	}
	public Layer3D createLayer3D(int nbTriangleMax){
		Layer3D layer = new Layer3D(this,screenW,screenH,nbTriangleMax,useMouseEvent,false);
		layer.name = "Layer_"+layers.size();
		layers.add(layer);
		return layer;
	}
	public Layer3D createLayer3D(boolean enableMouseEvent){
		Layer3D layer = new Layer3D(this,screenW,screenH,5000,enableMouseEvent,false);
		layer.name = "Layer_"+layers.size();
		layers.add(layer);
		if(enableMouseEvent) useMouseEvent = true;
		return layer;
	}
	public Layer3D createLayer3D(){
		
		Layer3D layer = new Layer3D(this,screenW,screenH,5000,useMouseEvent,false);
		layer.name = "Layer_"+layers.size();
		layers.add(layer);
		return layer;
	}
	
	
	public LayerImage createLayerImage(PImage img){
		LayerImage layer = new LayerImage(this,img);
		layer.name = "Layer_"+layers.size();
		layers.add(layer);
		return layer;
	}
	public LayerImage createLayerImage(PImage img,float destX,float destY,float destW,float destH){
		LayerImage layer = new LayerImage(this,img,destX,destY,destW,destH);
		layer.name = "Layer_"+layers.size();
		layers.add(layer);
		return layer;
	}
	
	
	
	public LayerBox2D createLayerBox2D(){
		LayerBox2D layer = new LayerBox2D(this);
		layer.name = "Layer_"+layers.size();
		layers.add(layer);
		return layer;
	}
	public LayerBox2D createLayerBox2D(int nbTriangleMax,boolean useMouseEvent,boolean removeDefaultParticleTexture){
		LayerBox2D layer = new LayerBox2D(this,nbTriangleMax,useMouseEvent,removeDefaultParticleTexture);
		layer.name = "Layer_"+layers.size();
		layers.add(layer);
		return layer;
	}
	
	public PApplet getApplet(){
		return applet;
	}
	
	
	private int num = 0;
	
	
	
	public Shape3D update(boolean checkMouseActivty){
		
		beginDraw();
		if(autoclear) background(0,0,0,0);
		noStroke();
		noFill();
		noSmooth();
		
		//ambientLight(0.85f,0.45f,0.15f);
		//directionalLight(255, 255, 255, 0, 0, -1);
		//spotLight(255, 255, 255, getWidth()/2, getHeight()/2, 750, 0, 0, -1, PI/2,1f);
		//pointLight(250, 255, 255,getWidth(), getHeight()/2, 0);
		//g.colorMode(g.RGB, 1.0f);
		
		//graphics.beginShape(graphics.TRIANGLES);
		
		int i;
		int start = layers.size()-1;
		//System.out.println(layers.size());
		objectUnderMouse = null;
		
		
		if(useMouseEvent == false || checkMouseActivty == false) for(i=start;i>-1;i--) layers.get(i).update(false);
		else {
			boolean checkMouseEvent = true;
			
			for(i=start;i>-1;i--){
				if(checkMouseEvent){
					objectUnderMouse = layers.get(i).update(checkMouseEvent);
					if(objectUnderMouse != null) checkMouseEvent = false;
				}else{
					layers.get(i).update(false);
				}
			}
		}
		
		
		//layers.get(1).update(false);
		
		
		noTint();
		//graphics.endShape();
		endDraw();
		//updateDisplay();
		return objectUnderMouse;
	}
	
	
}
