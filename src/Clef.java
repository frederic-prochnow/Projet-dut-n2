/**
 * Class Clef
 * Cette classe est utilisé pour la création et la gestion de la position des clefs
 * @author Team J3
 */
public class Clef {
	/**
	 * attribut
	 */
	private Position pos;
	private int type;
	
	/**
	 * Constructeur de la classe Position
	 * @param Position p
	 */
	Clef(Position p){
		this.pos = p;
		this.type = 8;
	}
	
	/**
	 * Méthode de retour de position de la clef
	 * @return Position
	 */
	public Position getPos(){
		return this.pos;
	}
	
	/**
	 * Méthode de retour du type
	 * @return int type
	 */
	public int getType(){
		return this.type;
	}
}
