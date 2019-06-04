package kosmos.events;



import kosmos.utils.Point3D;

public class EventDispatcher3D extends Point3D implements IEventReceiver{
	
	public String name;
	private IEventReceiver[] receivers;
	private int nbEventReceiver;
	
	
	protected EventDispatcher3D(){
	  super();
      nbEventReceiver = 0;
      name = (String) "EventDispatcher_"+(Math.random()*10000000);
	}
	
	public void dispose(){
		name = null;
		receivers = null;
		nbEventReceiver = 0;
	}
	
	public void applyEvent(String eventAction, EventDispatcher3D obj){
		//must be overrided
	}
	
	
	
	private boolean receiverExists(IEventReceiver r){
		if(nbEventReceiver == 0) return false;
		int i;
		for(i=0;i<nbEventReceiver;i++) if(receivers[i] == r) return true;
		return false;
	}
	
	public void addEventListener(IEventReceiver receiver){
		if(receiverExists(receiver) == false){
			if(nbEventReceiver == 0){
				receivers = new IEventReceiver[1];
				receivers[0] = receiver;
				nbEventReceiver++;
			}else{
				IEventReceiver[] temp = new IEventReceiver[nbEventReceiver+1];
				System.arraycopy(receivers, 0, temp, 0,nbEventReceiver);
				temp[nbEventReceiver++] = receiver;
				receivers = temp;
			}
		}
	}
	public void removeEventListener(IEventReceiver receiver){
		if(receiverExists(receiver) == true){
			IEventReceiver[] temp = new IEventReceiver[nbEventReceiver-1];
			int i,k = 0;
			for(i=0;i<nbEventReceiver;i++) if(receiver != receivers[i]) temp[k++] = receivers[i];
			nbEventReceiver--;
		}
	}
	
	public void dispatchEvent(String message){
		int i;
		for(i=0;i<nbEventReceiver;i++) receivers[i].applyEvent(message, this);
	}
}
