/**
 * Importation
 */
import java.awt.event.MouseEvent;
/**
 * Classe MyMouseEvent
 * Classe de la gestion des evenements souris
 * @author Team J3
 */
public class MyMouseEvent implements Event {
	/**
	 * Attribut
	 */
	private int x ;
	private int y ;
	private MouseEvent mouseEvent ;
	/**
	 * Constructeur de la classe
	 * @param coordonnee x
	 * @param coordonnee y
	 * @param evenement souris
	 */
	public MyMouseEvent(int x, int y, MouseEvent mouseEvent) {
		super();
		this.x = x;
		this.y = y;
		this.mouseEvent = mouseEvent ;
	}
	/**
	 * Retourne l evenement
	 * @return evevenement
	 * @Override
	 */
	public int getEventType() {
		return Event.MOUSE_EVENT ;
	}
	/**
	 * Retourne la coordonnee X
	 * @return x
	 */
	public int getX() { return x ;} ;
	/**
	 * Retourne la coordonnee y
	 * @return y
	 */
	public int getY() { return y ; } ;
	/**
	 * Retourne l evenement souris
	 * @return evenement
	 */
	public MouseEvent getmouseEvent() { return mouseEvent ; }
	/**
	 * Methode d affichage toString
	 * @return message
	 * @Override
	 */
	public String toString() {
		return "MyMouseEvent [x=" + x + ", y=" + y + ", mouseEvent="
				+ mouseEvent + "]";
	}
}
