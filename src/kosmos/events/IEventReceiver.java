package kosmos.events;

public interface IEventReceiver {
	public void applyEvent(String eventAction, EventDispatcher3D obj);
}
