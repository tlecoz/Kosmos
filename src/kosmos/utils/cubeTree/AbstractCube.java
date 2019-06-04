package kosmos.utils.cubeTree;

public class AbstractCube {
	 int nbSphereMaxByCube;
	 int startId;
	 int currentId = 0;
	 int id;
	 int[] allIndexs;
	 
	 Boolean used = false;
	 
	 AbstractCube(int cubeId,int startIndex,int nbMaxByCube,int[] allSphereIndexs){
	   nbSphereMaxByCube = nbMaxByCube;
	   startId = startIndex;
	   allIndexs = allSphereIndexs; 
	   id = cubeId;
	 }
	 void addSphere(int sphereId){
	   if(currentId < nbSphereMaxByCube){
	     allIndexs[startId + currentId] = sphereId; 
	     currentId++;
	   }
	 }
	 void reset(){
	   used = false;
	   currentId = 0; 
	 }
}
