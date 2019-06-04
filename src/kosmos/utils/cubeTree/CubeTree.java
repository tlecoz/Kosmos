package kosmos.utils.cubeTree;

import kosmos.utils.Point3D;

public class CubeTree {
	
	 AbstractCube[][][] map3D;
	 AbstractCube[] allCubes;
	 Point3D[] allSpheres;
	 int [] cubeWithSpheres; 
	 int [] allSphereIndexs;
	 int nbCubeWithSphere = 0;
	 int nbTotalCube;
	 
	 int nbDivX;
	 int nbDivY;
	 int nbDivZ;
	 
	 float divX;
	 float divY;
	 float divZ;
	 float sw2;
	 float sh2;
	 float sd2;
	 
	 CubeTree(int divisionX,int divisionY,int divisionZ,int screenW,int screenH,int screenD,int nbSphereTotal,int nbMaxSphereByCube, Point3D[] points){
	 
	   map3D = new AbstractCube[divisionX][][];
	   
	   nbTotalCube = divisionX*divisionY*divisionZ;
	   allCubes = new AbstractCube[nbTotalCube];
	   cubeWithSpheres = new int[nbTotalCube];
	   
	   allSpheres = points;
	   
	   int x,y,z;
	   int count = 0;
	   
	   nbDivX = divisionX;
	   nbDivY = divisionY;
	   nbDivZ = divisionZ;
	   
	   divX = screenW / divisionX;
	   divY = screenH / divisionY;
	   divZ = screenD / divisionZ;
	   sw2 = screenW / 2;
	   sh2 = screenH / 2;
	   sd2 = screenD / 2;
	   
	   int nbIndexs = nbTotalCube * (nbMaxSphereByCube);
	   allSphereIndexs = new int[nbIndexs];
	   for(x=0;x<nbIndexs;x++) allSphereIndexs[x] = 0;
	   
	   AbstractCube c;
	   
	   for(x=0;x<divisionX;x++){
	      map3D[x] = new AbstractCube[divisionY][];
	      for(y=0;y<divisionY;y++){
	         map3D[x][y] = new AbstractCube[divisionZ];
	         for(z=0;z<divisionZ;z++){
	             c = new AbstractCube(count,count * nbMaxSphereByCube,nbMaxSphereByCube,allSphereIndexs);
	             map3D[x][y][z] = c;
	             allCubes[count] = c;
	             count++;
	         } 
	      }
	   } 
	     
	 }
	 
	 void reset(){
	    nbCubeWithSphere = 0;
	    int i;
	    for(i=0;i<nbTotalCube;i++) allCubes[i].reset();
	    
	 }
	 
	 void findCubeContainer(Point3D s){
	    int px = (int) Math.floor((s.x + sw2) / divX);
	    int py = (int) Math.floor((s.y + sh2) / divY);
	    int pz = (int) Math.floor((s.z + sd2) / divZ);
	    
	    if(px >= nbDivX || py >= nbDivY || pz >= nbDivZ) return;
	    
	    //println(+" : "+floor((s.y + sh2) / divY)+" : "+floor((s.z + sd2) / divZ));
	    AbstractCube cube = map3D[px][py][pz];
	    cube.addSphere(s.id);
	    if(!cube.used){
	       cubeWithSpheres[nbCubeWithSphere++] = cube.id;
	       cube.used = true;
	    }
	   
	 }
}
