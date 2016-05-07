/**
 * Class Navire
 * Classe de gestion des Navires
 * @author Team J3
 */
public class Navire {
	/**
	 * Attribut
	 */
	private Position pos;
	private int type;
	private int contient;
	private int contientMax;
	
	/**
	 * Crée un navire
	 * @param p Sa Position
	 * @param equipe1 Appartient a l'équipe 1
	 */
	public Navire(Position p, boolean equipe1) {
		this.pos = p;
		this.contient = 0;
		contientMax = 0;
		if (equipe1) {
			this.type = 2;
		} else {
			this.type = 5;
		}
	}
	
	/**
	 * Retourne le type de l element
	 * @return int type
	 */
	public int getType(){
		return this.type;
	}
	/**
	 * Retourne la position du navire
	 * @return position
	 */
	public Position getPos(){
		return this.pos;
	}
	/**
	 * Ajoute un nombre de personnages dans le navire
	 * @param nombre
	 */
	public void addPersos(int nombre) {
		this.contient =+ nombre;
		this.contientMax += nombre;
	}
	/**
	 * Retire le personnage du navire
	 */
	public void retirePerso() {
		this.contient --;
	}
	
	/**
	 * Retourne ce que le navire contient
	 * @return contenu
	 */
	public int getContient() {
		return this.contient;
	}
	/**
	 * Il faut qu'au moins un personnage soit sur le plateau
	 * @return si il n'y a qu'un seul personnage sur le plateau
	 */
	public boolean getPlateauVide() {
		if (contientMax - contient <= 1) {
			return true;
		}
		return false;
	}

}
