/**
 * Class Coffre
 * Classe de gestion du coffre
 * @author Team J3
 */
public class Coffre {
	/**
	 * Attribut
	 */
	private Position pos;
	private boolean estOuvert;
	private boolean estVide;
	private int type;
	
	/**
	 * Constructeur de la Classe
	 * @param Position du coffre
	 */
	public Coffre(Position p){
		this.pos = p;
		this.estOuvert = false;
		this.type = 7;
	}
	
	/**
	 * Retourne le type de l'élément
	 * @return int type
	 */
	public int getType(){
		return this.type;
	}
	
	/**
	 * Retourne un boolean qui indique si le coffre est ouvert
	 * @return le booleen
	 */
	public boolean getEstOuvert(){
		return this.estOuvert;
	}
	
	/**
	 * Configure le booleen qui indique si le coffre est ouvert
	 * @param booleen de configuration
	 */
	public void setEstOuvert(boolean set) {
		this.estOuvert = set;
	}

	/**
	 * Retourne un boolean qui indique si le coffre est vide
	 * @return le booleen
	 */
	public boolean getEstVide(){
		return this.estVide;
	}
	
	/**
	 * Configure le booleen qui indique si le coffre est vide
	 * @param booleen de configuration
	 */
	public void setEstVide(boolean set) {
		this.estVide = set;
	}
	
	/**
	 * Retourne la position du coffre
	 * @param position du coffre
	 */
	public Position getPos(){
		return this.pos;
	}
	
	
}
