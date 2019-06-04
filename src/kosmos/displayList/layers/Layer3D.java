 package kosmos.displayList.layers;

import java.util.ArrayList;
import java.util.Collections;

import kosmos.displayList.DisplayObject3D;
import kosmos.displayList.Object3D;
import kosmos.displayList.Shape3D;
import kosmos.displayList.Sprite3D;
import kosmos.displayList.TriangleObj;
import kosmos.displayList.displayObjects.textfields.TextField;
import kosmos.lights.ILight;
import kosmos.text.Font;
import kosmos.text.TextFormat;
import kosmos.texture.ParticleTextureUv;
import kosmos.texture.TextureArea;
import kosmos.texture.TwoTriangleTextureArea;
import kosmos.uiToolbox.textures.ComposedTextureObj;
import kosmos.utils.Alert;
import kosmos.utils.Camera3D;
import kosmos.utils.GraphicUtils;
import kosmos.utils.texturePacker.TexturePacker;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

public class Layer3D extends Sprite3D implements ILayer{
	  
	 
	  public TexturePacker texturePacker;
	  
	  //public PApplet applet;
	  
	  protected Renderer renderer;
	  
	  public Camera3D camera;
	  public Shape3D objectUnderMouse;
	  
	  public int nbTriangle;
	  public int nbVertex;
	  public int nbVertexVisible;
	  public int nbObject;
	  
	  
	  protected ILight[] allLights;
	  protected int[] lightIndexs;
	  protected int nbLight;
	  
	  
	  
	  public float[] allVertexXYZ;
	  public float[] allVertexUV;
	  public float[] allTransformXYZ;
	  public float[] allXYUV;
	  public TriangleObj[] allTriangles;
	  
	  protected ArrayList<TriangleObj> triangleList;
	  
	  protected ArrayList<ParticleTextureUv> particleTextures;
	  protected boolean useTopTriangleParticleTexture;
	  
	  public float stageW;
	  public float stageH;
	  public float halfStageW;
	  public float halfStageH;
	  
	  public float focalLength;
	  public float fieldOfView;
	  
	  
	  private boolean checkMouseEvent;
	  private boolean mouseIsPressed;
	  private Shape3D pressedObject;
	  
	  private String[] fontNames;
	  private Font[] fonts;
	  private int nbFont;
	  public TextFormat defaultTextFormat;
	  
	  
	  private ParticleTextureUv _circleTexture;
	  private ParticleTextureUv _squareTexture;
	  private TextureArea _colorTexture;
	  
	  public Layer3D(Renderer rendererObj,float screenWidth,float screenHeight,int nbTriangleMax,boolean useMouseEvent,boolean removeDefaultParticleTexture){
	    super();
	    
	    _constructor(rendererObj,screenWidth,screenHeight,nbTriangleMax,useMouseEvent,removeDefaultParticleTexture);
	  }
	  
	  private void _constructor(Renderer rendererObj,float screenWidth,float screenHeight,int nbTriangleMax,boolean useMouseEvent,boolean removeDefaultParticleTexture){
		    renderer = rendererObj;
		    //applet = renderer.getApplet();
		    
		    allLights = renderer.getLightList();
		    lightIndexs = new int[8];
		    nbLight = 0;
		    
		    checkMouseEvent = useMouseEvent;
		    mouseIsPressed = false;
		    setRenderDimension(screenWidth,screenHeight);
		    texturePacker = new TexturePacker();	
		    
		    
		    
		    initBuffers(nbTriangleMax); 
		    
		    if(removeDefaultParticleTexture == false){
		    	createDefaultParticleTextures();
		    }
		    
		    createSolidFillTexture();
	  }
	  
	  private void createDefaultParticleTextures(){
		  PGraphics g = GraphicUtils.getGraphics();
		  
		  //CIRCLE
		  
		  g.beginDraw();
		  g.background(0,0);
		  g.noStroke();
		  g.fill(255,255,255);
		  g.ellipse(65,65,128,128);
		  g.endDraw();
		  
		  PImage circle = new PImage(130,130,2);
		  circle.copy(g, 0, 0, 130, 130, 0, 0, 130,130);
		  
		  
		  //SQUARE
		  
		  g.beginDraw();
		  g.background(0,0);
		  g.noStroke();
		  g.fill(255,255,255);
		  g.rect(1,1,128,128);
		  g.endDraw();
		  
		  PImage rect = new PImage(130,130,2);
		  rect.copy(g, 0, 0, 130, 130, 0, 0, 130,130);

		  _circleTexture = this.addParticleImage(circle);
		  _squareTexture = this.addParticleImage(rect);
		  
	  }
	  
	  private void createSolidFillTexture(){
		  PGraphics g = GraphicUtils.getGraphics();
		  //solid fill color texture
		  
		  g.beginDraw();
		  g.clear();
		  g.background(0,0);
		  g.noStroke();
		  g.fill(255,255,255);
		  g.rect(0,0,32,32);
		  g.endDraw();
		  
		  PImage col = new PImage(130,130,2);
		  col.copy(g, 0, 0, 32, 32, 0, 0, 32,32);
		  _colorTexture = this.addTextureImage(col);
	  }
	  
	  public ParticleTextureUv getCircleParticleTexture(){
		  if(_circleTexture == null) Alert.error("Layer3D", "getCircleParticleTexture", "you must set 'removeDefaultParticleTexture' to false in the Layer3D constructor to be able to call Layer3d.getCircleParticleTexture");
		  return _circleTexture;
	  }
	  public ParticleTextureUv getSquareParticleTexture(){
		  if(_squareTexture == null) Alert.error("Layer3D", "getSquareParticleTexture", "you must set 'removeDefaultParticleTexture' to false in the Layer3D constructor to be able to call Layer3d.getSquareParticleTexture");
		  return _squareTexture;
	  }
	  public TextureArea getSolidFillColorTexture(){
		  return _colorTexture;
	  }
	  
	  public Layer3D(Renderer rendererObj,int nbTriangleMax,boolean useMouseEvent,boolean removeDefaultParticleTexture){
	    super();
	    _constructor(rendererObj,rendererObj.getWidth(),rendererObj.getHeight(),nbTriangleMax,useMouseEvent,removeDefaultParticleTexture);
	  }
	  
	  public Layer3D(Renderer rendererObj,boolean useMouseEvent){
	    super();
	    _constructor(rendererObj,rendererObj.getWidth(),rendererObj.getHeight(),10000,useMouseEvent,false);
	  }
	  
	  public Layer3D(Renderer rendererObj){
	    super();
	    _constructor(rendererObj,rendererObj.getWidth(),rendererObj.getHeight(),10000,true,false);
	  }
	  
	 
	  
	  public Renderer getRenderer(){
		  return renderer;
	  }
	  
	  
	  public void addLight(ILight light){
		  lightIndexs[nbLight++] = renderer.__addLight(light);
	  }
	  
	  protected void applyLights(){
		 renderer.noLights();
		 int i;
		 for(i=0;i<nbLight;i++) allLights[lightIndexs[i]].apply(renderer);
	  }
	  
	  
	  
	  
	  public TextureArea addTextureImage(PImage img){
		  return texturePacker.addSubTexture(img);
	  }
	  public ComposedTextureObj addTextureImage(ComposedTextureObj o){
		  return texturePacker.addSubTexture(o);
	  }
	  public TextureArea addTextureImage(TextureArea area){
		  return texturePacker.addSubTexture(area);
	  }
	  
	  
	  public ParticleTextureUv addParticleImage(PImage img){
		  ParticleTextureUv o = new ParticleTextureUv(img);
		 
		  particleTextures.add(o);
		  return o;
	  }
	  
	  
	  private void packParticleImages(){
		  
		  int i,len = particleTextures.size();
		  if(len == 0) return;
		  
		  
		  Collections.sort(particleTextures);
		  
		  boolean lastOne = len % 2 != 0;
		  if(lastOne) len--;
		  
		  TwoTriangleTextureArea t;
		  for(i=0;i<len;i+=2)  addTextureImage( new TwoTriangleTextureArea(texturePacker,particleTextures.get(i),particleTextures.get(i+1)) );
		  
		  if(lastOne) addTextureImage(new TwoTriangleTextureArea(texturePacker,particleTextures.get(len),null) );
		  
	  }
	  
	  
	  public void start(){
		  packParticleImages();
		  texturePacker.drawElementsAndGetCorrectUV();
	  }
	  
	  
	  public int getFontId(String fontName){
		  int i;
		  for(i=0;i<nbFont;i++) if(fontNames[i] == fontName) return i;
		  return -1;
	  }
	  
	  public boolean fontExists(String fontName){
		  return getFontId(fontName) > -1;
	  }
	  
	 
	  
	  public void addFont(String fontName){
		  if(fontExists(fontName) ) return;
		  
		  Font font;
		  fonts[nbFont] = font = new Font(renderer.getApplet(),fontName);
		  fontNames[nbFont] = fontName;
		  nbFont++;
		  if(nbFont == 1)  defaultTextFormat = createTextFormat(fontName);
		 
		  
		  addTextureImage(font.fontTexture);
	  }
	  
	  
	  public TextFormat createTextFormat(String fontName,int textFormatAlign,int textColor,float textSize, float letterSpace,float lineSpace,boolean useBold,boolean useItalic){
		  return new TextFormat(fonts[getFontId(fontName)],textFormatAlign,textColor,textSize,letterSpace,lineSpace,useBold,useItalic);
	  }
	  public TextFormat createTextFormat(String fontName,int textFormatAlign,int textColor,float textSize, float letterSpace,float lineSpace){
		  return new TextFormat(fonts[getFontId(fontName)],textFormatAlign,textColor,textSize,letterSpace,lineSpace,false,false);
	  }
	  public TextFormat createTextFormat(String fontName,int textFormatAlign,int textColor,float textSize){
		  return new TextFormat(fonts[getFontId(fontName)],textFormatAlign,textColor,textSize,textSize/4.0f,0,false,false);
	  }
	  public TextFormat createTextFormat(String fontName,int textFormatAlign){
		  return new TextFormat(fonts[getFontId(fontName)],textFormatAlign,renderer.color(255,0,0,255),20,20/4.0f,0,false,false);
	  }
	  public TextFormat createTextFormat(String fontName){
		  return new TextFormat(fonts[getFontId(fontName)],TextFormat.LEFT_ALIGN,renderer.color(255,0,0,255),20,20/4.0f,0,false,false);
	  }
	  
	  
	  public DisplayObject3D appendObject(DisplayObject3D o){
	    return camera.appendObject(o);
	  }
	  
	  public Object3D removeObject(Object3D o){
	    return camera.removeObject(o);
	  }
	  
	  
	  
	  
	  
	  
	  
	  protected void setRenderDimension(float screenWidth,float screenHeight){
	    stageW = screenWidth;
	    stageH = screenHeight;
	    _realX = halfStageW = (float) (stageW / 2.0);
	    _realY = halfStageH = (float) (stageH / 2.0);
	    
	    fieldOfView = (float) (Math.PI/180.0 * 60.0);
	    focalLength = (float) (halfStageW * (Math.cos(fieldOfView/2.0) / Math.sin(fieldOfView/2.0)));
	    _realZ = 0;//focalLength;
	    
	    setLayer(this);
	    //println("focalLength = "+focalLength);
	  }
	  

	  protected void initBuffers(int nbTriangleMax){
	    int i,nbVertexMax = nbTriangleMax * 3;
	    
	    nbFont = 0;
	    fontNames = new String[8];
	    fonts = new Font[8];
	    for(i=0;i<8;i++) fontNames[i] = "";
	    
	    nbTriangle = nbVertex = nbVertexVisible = 0;
	    
	    allTriangles = new TriangleObj[nbTriangleMax];
	    triangleList = new ArrayList<TriangleObj>();
	    
	    allVertexXYZ = new float[nbVertexMax*3];
	    allVertexUV = new float[nbVertexMax*2];
	    allTransformXYZ = new float[nbVertexMax*3];
	    
	    particleTextures = new ArrayList<ParticleTextureUv>();
	    useTopTriangleParticleTexture = true;
	    
	    camera = new Camera3D();
	    camera.x = 0;
	    camera.y = 0;
	    camera.z = 0;//(float) (-focalLength);
	    super.appendObject(camera);
	    
	  }
	  /*
	  public TextureArea defineNewTextureArea(int px,int py,PImage img,Boolean useAlpha){
	     TextureArea t = new TextureArea(texturePacker,px,py,img.width,img.height);
	     t.update(img,useAlpha); 
	     return t;
	  }
	  */
	  
	  public int getNewVertex(float px,float py,float pz,float u,float v){
	    int vertexId =  nbVertex++;
	    int v3 = vertexId * 3;
	    int v2 = vertexId * 2;
	    allVertexXYZ[v3] = allTransformXYZ[v3] = px;
	    allVertexXYZ[v3+1] = allTransformXYZ[v3+1] = py;
	    allVertexXYZ[v3+2] = allTransformXYZ[v3+2] = pz;
	    
	    allVertexUV[v2] = u;
	    allVertexUV[v2+1] = v;
	    
	    return vertexId;
	  }
	  
	  public void addNewTriangle(Shape3D o,int i0,int i1,int i2){
	    TriangleObj t = new TriangleObj(renderer,o,i0,i1,i2,allTransformXYZ,allVertexUV,allXYUV);
	    triangleList.add(t);
	    allTriangles[nbTriangle++] = t;
	  }
	  
	  
	  private void applyMouseAction(){
		  if(renderer.mousePressed != mouseIsPressed){
				mouseIsPressed = renderer.mousePressed;
				if(mouseIsPressed){
					if(objectUnderMouse != null){
						objectUnderMouse.mousePressed();
						pressedObject = objectUnderMouse;
					}
					this.mousePressed();
				}else{
					if(objectUnderMouse != null){
					    if(pressedObject != null){
					    	pressedObject.mouseReleased();
					    	if(pressedObject == objectUnderMouse){
					    		pressedObject.mouseClick();
					    	}
					    }else{
					    	objectUnderMouse.mouseReleased();
					    }
					}else{
						if(pressedObject != null){
					    	pressedObject.mouseReleased();
					    }
					}
					this.mouseReleased();
				}
				
			}
	  }
	  
	  
	  
	  private Shape3D updateAll(boolean enableMouseEvent){
		  
		  applyLights();
		  
		  if(checkMouseEvent && enableMouseEvent) applyMouseAction();
		  
			//camera.updatePosition();
			//camera.updateVertexPosition();
		    
		    int i,len = children.size();
		    for(i=0;i<len;i++)children.get(i).updatePosition();
		    
		    updateVertexPosition();
		    
		    for(i=0;i<nbTriangle;i++) allTriangles[i].updateZ();
		    
		    Collections.sort(triangleList);
		    
		    
		   // System.out.println("nbTriangle = "+nbTriangle);
		   
		    
		    renderer.beginShape(renderer.TRIANGLES);
		    
		    renderer.colorMode(renderer.RGB, 1.0f);
		    renderer.texture(texturePacker);
		    
		    
		    
		    String c = "";
		    
		    int k = 0;
		    TriangleObj t;
		    Shape3D mc;
		    Boolean checkMouse = true;
		    
		    
		    
		    if(checkMouseEvent && enableMouseEvent){
			    for(i=0;i<nbTriangle;i++){
			       t = triangleList.get(i);
				    
			       if(t.hitTest(renderer.mouseX , renderer.mouseY ) == true ){
			    	  if(objectUnderMouse != t.obj){
			    		  if(objectUnderMouse != null) objectUnderMouse.setMouseIsOver(false);
			    	   	  objectUnderMouse = t.obj;
			    		  objectUnderMouse.setMouseIsOver(true);
			    	  }
			    	  checkMouse = false;
			    	  break;
			       }
			    }
			    
			    if(checkMouse == true){
			    	if(objectUnderMouse != null){
			    		objectUnderMouse.setMouseIsOver(false);
			    		objectUnderMouse = null;
			    	}
			    }
		    }
		    
		    //k = 0;
		    DisplayObject3D o = null;
		    for(i=nbTriangle-1;i>-1;i--) {
		    	o = triangleList.get(i).drawTriangle(o);
		    }
		    
		    ////System.out.println("K = "+k);
		    /*
		    countObj.id = 0;
		    for(i=nbTriangle-1;i>-1;i--)   triangleList.get(i).drawTriangle2();
		    float[] v = new float[countObj.id * 5];
		    System.arraycopy(allXYUV,0,v,0,countObj.id * 5);
		    applet.vertex(v);
		    */
		    
		    
		    renderer.noTint();
		    renderer.endShape();
		    //g.endDraw();
		    
		    
		    return objectUnderMouse;
		    //applet.println(objectUnderMouse);
		  
		  
	  }
	  
	  public Shape3D update(){
		 return updateAll(true);
	  }
	  
	  public Shape3D update(boolean enableMouseEvent){
		 return updateAll(enableMouseEvent);
	  }
	  
	  
	  
	}

