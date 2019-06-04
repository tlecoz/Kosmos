package kosmos.color.progressiveFill;

import java.util.ArrayList;

import processing.core.PImage;
import processing.data.IntList;

public class ProgressiveFillAbstract {
	
	protected PImage context;
	protected PImage renderBd;
	protected PImage usedBd;
	protected PImage emptyBd;
	
	protected IntList pixels;
	protected int[] proximity;
	protected ArrayList<IntList> waves;
	
	public int currentColor;
	
	protected int len;
	protected int frame;
	protected int proximityId;
	protected boolean fillVisiblePixel;
	
	private boolean firstStart;
	
	ProgressiveFillAbstract(){
		
		currentColor=0;
		len = 0;
		frame = 0;
		proximityId =0;
		fillVisiblePixel=false;
		firstStart = false;
		
		usedBd = new PImage(2,2,2);
		emptyBd = new PImage(2,2,2);
		
		initProximityPositions();
	}
	private void initProximityPositions() {
		proximity = new int[16];
		proximity[0] = -1;
		proximity[1] = -1;
		proximity[2] = 0;
		proximity[3] = -1;
		proximity[4] = 1;
		proximity[5] = -1;
		proximity[6] = 1;
		proximity[7] = 0;
		proximity[8] = 1;
		proximity[9] = 1;
		proximity[10] = 0;
		proximity[11] = 1;
		proximity[12] = -1;
		proximity[13] = 1;
		proximity[14] = -1;
		proximity[15] = 0;
	}
	
	
	protected void addBorderPixelToCurrentWave(int px,int py) {
		waves.get(frame).append(px);
		waves.get(frame).append(py);
		renderBd.set(px, py, currentColor);
	}
	protected void addPixelToList(int px,int py) {
		pixels.append(px);
		pixels.append(py);
		len += 2;
	}
	protected void addInvisiblePixel(int px,int py) {
		usedBd.set(px, py, 0xff000000);
		pixels.append(px);
		pixels.append(py);
		len += 2;
	}
	protected void addVisiblePixel(int px,int py) {
		usedBd.set(px, py, 0x00000000);
		pixels.append(px);
		pixels.append(py);
		len += 2;
	}
	
	
	
	protected void addProximityInvisiblePixel(int px,int py) {
		
		renderBd.set(px, py, currentColor);
		waves.get(frame).append(px);
		waves.get(frame).append(py);
		
		int i;
		int id;
		int _x;
		int _y;
		
		for (i = 0; i < 16; i+=2) {
			id = (proximityId + i) % 15;	
			_x = px + proximity[id];
			_y = py + proximity[id + 1];
			if (0 == renderBd.get(_x,_y)) {
				if(0 == usedBd.get(_x,_y))addInvisiblePixel(_x,_y);	
				else addBorderPixelToCurrentWave(_x, _y);
			}
		}
	}
	
	
	
	protected void addProximityVisiblePixel(int px, int py) {
		renderBd.set(px, py, currentColor);
		waves.get(frame).append(px);
		waves.get(frame).append(py);
		
		int i;
		int id;
		int _x;
		int _y;
		for (i = 0; i <16; i+=2) {
			id = (proximityId + i) % 15;
			_x = px + proximity[id];
			_y = py + proximity[id + 1];
			if (0 == renderBd.get(_x, _y)) {
				if (0 != usedBd.get(_x, _y)) addVisiblePixel(_x, _y);
			}
		}
	}
	
	protected void fillInvisiblePixels(int nbPass) {
		//le calcul se fait dans une classe fille
	}
	protected void fillVisiblePixels(int nbPass) {
		//le calcul se fait dans une classe fille
	}
	
	
	
	public boolean isWorking(int nbPass) {
		return _isWorking(nbPass);
	}
	public boolean isWorking() {
		return _isWorking(10);
	}
	
	
	private boolean _isWorking(int nbPass){
		if (fillVisiblePixel) fillVisiblePixels(nbPass);
		else fillInvisiblePixels(nbPass);
		
		if (pixels.size() > 0){
			return true;
		}
		
		return false;	
	}
	
	
	
	public void initContext(PImage sourceImage,PImage destImage) {
		context = sourceImage;
		renderBd = destImage;
		
		usedBd.resize(renderBd.width, renderBd.height);
		usedBd.copy(emptyBd,0,0,2,2,0,0,usedBd.width,usedBd.height);
		usedBd.copy(context,0,0,context.width,context.height,0,0,usedBd.width,usedBd.height);
		firstStart = true;
	}
	
	
	public void startFill(int startX,int startY,boolean fillInvisiblePixels,boolean clearFill,int orientation) {
		_startFill(startX,startY,fillInvisiblePixels,clearFill,orientation);
	}
	
	public void startFill(int startX,int startY,boolean fillInvisiblePixels,boolean clearFill) {
		_startFill(startX,startY,fillInvisiblePixels,clearFill,0);
	}
	
	public void startFill(int startX,int startY,boolean fillInvisiblePixels) {
		_startFill(startX,startY,fillInvisiblePixels,true,0);
	}
	
	public void startFill(int startX,int startY) {
		_startFill(startX,startY,true,true,0);
	}
	
	
	private void _startFill(int startX,int startY,boolean fillInvisiblePixels,boolean clearFill,int orientation){
		fillVisiblePixel = !fillInvisiblePixels;

		if (clearFill || firstStart) {
			firstStart = false;
			pixels = new IntList();
			waves = new ArrayList<IntList>();
			len = 0;
			frame = 0;
		}
		
		proximityId = orientation * 2;
		
		if (fillInvisiblePixels) addInvisiblePixel(startX,startY);
		else addVisiblePixel(startX, startY);
	}
	
	
	
	
	public void resetContext(boolean resetAlreadyUsedPixels,boolean resetRenderData){
		_resetContext(resetAlreadyUsedPixels,resetRenderData);
	}
	public void resetContext(boolean resetAlreadyUsedPixels){
		_resetContext(resetAlreadyUsedPixels,true);
	}
	public void resetContext(){
		_resetContext(true,true);
	}
	
	
	private void _resetContext(boolean resetAlreadyUsedPixels,boolean resetRenderData) {
		if(resetRenderData) renderBd.copy(emptyBd,0,0,2,2,0,0,renderBd.width,renderBd.height);
		if (resetAlreadyUsedPixels) {
			usedBd.copy(emptyBd,0,0,2,2,0,0,usedBd.width,usedBd.height);
			usedBd.copy(context,0,0,context.width,context.height,0,0,usedBd.width,usedBd.height);
		}
	}
	
	
	public PImage getRenderData() {
		return renderBd;
	}
	public ArrayList<IntList> getPixelWaves() {
		return waves;
	}
	
	
	
}
