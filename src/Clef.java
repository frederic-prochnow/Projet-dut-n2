/**
 * Class Clef
 * Classe de gestion de la clef
 * @author Team J3
 */
public class Clef {
	/**
	 * Attribut
	 */
	private Position pos;
	private int type;
	
	/**
	 * Constructeur de classe
	 * @param position de la clef
	 */
	public Clef(Position p){
		this.pos = p;
		this.type = 8;
	}
	
	/**
	 * Retourne la position de la clef
	 * @return position
	 */
	public Position getPos(){
		return this.pos;
	}
	
	/**
	 * Retourne le type de l'élément
	 * @return int type
	 */
	public int getType(){
		return this.type;
	}
	
	/**
	 * Permet de modifier la position de la clef
	 * @param position de la clef
	 */
	public void setPosition(Position p) {
		this.pos = p;
	}
}
