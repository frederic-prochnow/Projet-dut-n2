/**
 * Importation
 */
import java.awt.Point;
/**
 * Class Personnage
 * Gestion des personnages
 * @author Team J3
 */
public class Personnage {
	/**
	 * Attribut
	 */
	protected String nom;
	protected char symboleNom;
	protected int type; // Même que dans parcelle
	protected String cheminImage;
	protected boolean equipe1;
	protected int energie;
	protected Position pos;
	protected boolean surNavire;
	private boolean detientClef;
	private boolean detientTresor;
	private Position dernierTag;
	private Position directionDeplacement;
	private boolean estPiege;
	private int tourNumPiege;

	/**
	 * Constructeur de Personnage
	 * @param nom Son nom
	 * @param equipe1 Appartient à l'équipe 1 
	 * @param energie Energie de base
	 * @param p Sa position de base
	 * @param type Son type selon Parcelle
	 */
	Personnage(String nom, boolean equipe1, int energie, Position p, int type){
		this.nom = nom;
		this.energie = energie;
		this.equipe1 = equipe1;
		this.surNavire = true;
		this.type = type;
		if (type == 0) {
			this.cheminImage = "images/1.explorateur.png";
		} else if (type == 1) {
			this.cheminImage = "images/1.voleur.png";
		} else if (type == 3) {
			this.cheminImage = "images/2.explorateur.png";
		} else if (type == 4) { 
			this.cheminImage = "images/2.voleur.png";
		} else if (type == 10) {
			this.cheminImage = "images/2.voleur.png";
		} else if (type == 11) {
			this.cheminImage = "images/1.piegeur.png";
		} else if (type == 12) {
			this.cheminImage = "images/2.voleur.png";
		} else if (type == 13) {
			this.cheminImage = "images/2.piegeur.png";
		}
		
		
		this.pos = p;
		this.dernierTag = new Position(-1, -1);
		this.directionDeplacement = new Position(-1,-1);
		
		if(equipe1){
			this.symboleNom = nom.charAt(0);
		}else{
			this.symboleNom = (nom.toUpperCase()).charAt(0);
		}
	}
	
	/**
	 * Retourne le type de personnage
	 * @return type
	 */
	public int getType(){
		return this.type;
	}
	
	/**
	 * Retourne le chemin
	 * @return chemin
	 */
	public String getCheminImage() {
		return this.cheminImage;
	}
	
	/**
	 * Perdition d energie
	 * @param energie
	 */
	public void perdEnergie(int energie){
		this.energie -= energie;
	}
	
	/**
	 * Retourne la position du personnage
	 * @return position
	 */
	public Position getPos(){
		return pos;
	}
	/**
	 * Configuration de la position
	 * @param point
	 */
	public void setPos(Point point){
		pos.x = point.x;
		pos.y = point.y;
	}
	
	/**
	 * Retourne le nom du personnage
	 * @return nom
	 */
	public String getNom(){
		return nom;
	}
	
	/**
	 * Retourne le type du navire selon le personnage
	 * @return type
	 */
	public int getNavireType(){
		if(equipe1){
			return 2;
		}else{
			return 5;
		}
	}
	
	/**
	 * Retourne le symbole du personnage
	 * @return symbole
	 */
	public char getSymbole(){
		return symboleNom;
	}
	/**
	 * Retourne si  l equipe 1
	 * @return equipe 1
	 */
	public boolean getEquipe1(){
		return equipe1;
	}
	/**
	 * Retourne si  l equipe 2
	 * @return equipe 1
	 */
	public boolean getEquipe2() {
		if (getEquipe1()) {
			return false;
		}
		return true;
	}
	
	/**
	 * Retourne le niveau d energie du personnage
	 * @return energie
	 */
	public int getEnergie(){
		return energie;
	}
	
	/**
	 * Retourne si le personnage est pieg�
	 * @return estPiege
	 */
	public boolean getEstPiege(){
		return estPiege;
	}
	
	/**
	 * Set si le personnage est pieg�
	 * @return estPiege
	 */
	public void setEstPiege(boolean b){
		this.estPiege = b;
	}
	
	/**
	 * Configuration de l energie
	 * @param energue
	 */
	public void setEnergie(int setEnergie) {
		this.energie = setEnergie;
	}
	
	/**
	 * Retourne si le nb de tour piege restant
	 * @return nbTourPiege
	 */
	public int getNumTourPiege(){
		return tourNumPiege;
	}
	
	/**
	 * Set le nb de tour restant piege
	 * @return nbTourPiege
	 */
	public void setNumTourPiege(int nb){
		this.tourNumPiege = nb;
	}
	
	/**
	 * Verification de presence sur navire
	 * @return booleen
	 */
	public boolean getSurNavire(){
		return this.surNavire;
	}
	/**
	 * Configuration de presence sur navire
	 * @param booleen
	 */
	public void setSurNavire(boolean b){
		this.surNavire = b;
	}
	
	/**
	 * Verification de detention de clee
	 * @return booleen
	 */
	public boolean getDetientClef(){
		return this.detientClef;
	}
	/**
	 * Configuration de detention de clee
	 * @param booleen
	 */
	public void setDetientClef(boolean set) {
		this.detientClef = set;
	}
	/**
	 * Verification de detention de Tresor
	 * @return booleen
	 */
	public boolean getDetientTresor(){
		return this.detientTresor;
	}
	/**
	 * Configuration de detention de tresor
	 * @param booleen
	 */
	public void setDetientTresor(boolean set){
		this.detientTresor = set;
	}
	/**
	 * Fonction d affichae toString
	 * @return message
	 */
	public String toString(){
		if (equipe1) {
			return nom+" de l'équipe 1 possede "+energie+" points d'energie.";
		}
		return nom+" de l'équipe 2 possede "+energie+" points d'energie.";
	}
	
	Object[] actionsSimples = { "haut", "bas", "gauche", "droite" };
	Object[] actionsMulti = { "haut", "bas", "gauche", "droite", "haut gauche", "haut droite", "bas gauche",
	"bas droite" };
	
	/**
	 * Selection choix de deplacement
	 * @return choix
	 */
	public int choixDeplacement(){
		return -1;
	}
	
	/**
	 * Obtention de position
	 * @return position
	 */
	public Position getDernierTag() {
		return this.dernierTag;
	}
	/**
	 * Configuration de position
	 * @param coordonnee x
	 * @param coordonnee y
	 */
	public void setDernierTag(int x, int y) {
		this.dernierTag.setLocation(x, y);
	}
	
	/**
	 * Obtention direction de deplacement
	 * @return direction
	 */
	public Position getDirectionDeplacement() {
		return this.directionDeplacement;
	}
	/**
	 * Configuration direction de deplacement
	 * @param psoition
	 */
	public void setDirectionDeplacement(Position p) {
		this.directionDeplacement.setLocation(p);
	}

}
