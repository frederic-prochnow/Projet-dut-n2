/**
 * Importation
 */
import java.awt.event.KeyEvent;
/**
 * Class MyKeyEvent
 * Classe de gestion des evenements
 * @author Team J3
 */
public class MyKeyEvent implements Event {

	/**
	 * Attribut
	 */
	private KeyEvent keyEvent = null ;
	@Override
	/**
	 * Retourne la clee de l evenement
	 * @return clee
	 */
	public int getEventType() {
		return Event.KEY_EVENT ;
	}
	/**
	 * Retourne la clee de l evenement
	 * @return clee
	 */
	public KeyEvent getKeyEvent() {
		return keyEvent;
	}
	/**
	 * Configuration de  la clee de l evenement
	 * @param clee
	 */
	public MyKeyEvent(KeyEvent keyEvent) {
		super();
		this.keyEvent = keyEvent;
	}

}
