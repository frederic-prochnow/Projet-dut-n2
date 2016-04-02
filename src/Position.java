/**
 * Class Poition
 * Class de gestion de la position d'un élément
 * @author Team J3
 */
public class Position {
	/**
	 * attribut
	 */
	public int x;
	public int y;
	
	/**
	 * Constructeur de la class
	 * Crée une position
	 * @param int longitude,int latitude
	 */
	Position(int x,int y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Méthode d'affichage toString
	 * @return String resultat
	 */
	public String toString(){
		return "("+x+","+y+")";
	}
	
	/**
	 * Méthode de calcul d'égalité sur position
	 * @return boolean
	 */
	public boolean equals(Position p){
		return ((this.x == p.x) && (this.y == p.y));
	}
}
