/**
 * Class Coffre
 * Class de Gestion du Coffre
 * @author Team J3
 */
public class Coffre {
	/**
	 * attribut
	 */
	private Position pos;
	private boolean estOuvert;
	private boolean estVide;
	private int type;
	
	/**
	 * Constructeur de la classe Coffre
	 * @params Position p
	 */
	Coffre(Position p){
		this.pos = p;
		this.estOuvert = false;
		this.type = 7;
	}
	
	/**
	 * Méthode qui retourne le type
	 * @return int type
	 */
	public int getType(){
		return this.type;
	}
	
	/**
	 * Méthode de vérification de l'ouverture du Coffre
	 * @return boolean
	 */
	public boolean getEstOuvert(){
		return this.estOuvert;
	}
	
	/**
	 * Méthode d'application sur le boolean gérant l'ouverture ou non du coffre
	 * @params boolean set
	 */
	public void setEstOuvert(boolean set) {
		this.estOuvert = set;
	}

	/**
	 * Méthode de vérification si le Coffre est Vide
	 * @return boolean
	 */
	public boolean getEstVide(){
		return this.estVide;
	}
	
	/**
	 * Méthode d'application sur le boolean qui gérant le contenu du coffre (vide ou pas)
	 * @params boolean set
	 */
	public void setEstVide(boolean set) {
		this.estVide = set;
	}
	
	/**
	 * Méthode qui retourne la position du Coffre
	 * @return Position
	 */
	public Position getPos(){
		return this.pos;
	}
	
	
}
