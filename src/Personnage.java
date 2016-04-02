/**
 * Importation de class
 */
import javax.swing.JOptionPane;
/**
 * Class Personnage
 * Class de création et de gestion des personnages
 */
public class Personnage {
	/**
	 * attribut
	 */
	protected String nom;
	protected char symboleNom;
	protected int type; // M�me que dans parcelle
	protected int equipe;
	protected int energie;
	protected Position pos;
	protected boolean surNavire;
	private boolean detientClef;
	private boolean detientTresor;

	/**
	 * Constructeur de la class Personnage
	 * Crée un personnage
	 * @params String nom, int nbequipe, int nvenergie, Position p, int type
	 */
	Personnage(String nom, int equipe, int energie, Position p, int type){
		this.nom = nom;
		this.energie = energie;
		this.equipe = equipe;
		this.surNavire = true;
		this.type = type;
		this.pos = p;
		
		if(equipe == 1){
			this.symboleNom = nom.charAt(0);
		}else{
			this.symboleNom = (nom.toUpperCase()).charAt(0);
		}
	}
	
	/**
	 * Méthode qui retourne le type de l'élément
	 * @return int type
	 */
	public int getType(){
		return this.type;
	}
	
	/**
	 * Méthode qui retire le nb d'énregie souhaiter
	 * @param int energie
	 */
	public void perdEnergie(int energie){
		this.energie -= energie;
	}
	
	/**
	 * Méthode qui retourne la position du personnage
	 * @return position p
	 */
	public Position getPos(){
		return this.pos;
	}
	
	/**
	 * Méthode d'application de la position du personnage
	 * @param Position p
	 */
	public void setPos(Position p){
		this.pos = p;
	}
	
	/**
	 * Méthode qui retourne le nom du personnage
	 * @return String nom
	 */
	public String getNom(){
		return nom;
	}
	
	/**
	 * Méthode qui retourne le type du navire selon le personnage
	 * @return int type
	 */
	public int getNavireType(){
		if(equipe == 1){
			return 2;
		}else{
			return 5;
		}
	}
	
	/**
	 * Méthode qui retourne le symbole de l'élément
	 * @return char symbole
	 */
	public char getSymbole(){
		return symboleNom;
	}
	
	/**
	 * Méthode qui return l'équipe
	 * @return int equipe
	 */
	public int getEquipe(){
		return equipe;
	}
	
	/**
	 * Méthode qui retournele niveau d'énergie
	 * @return int energie
	 */
	public int getEnergie(){
		return energie;
	}
	
	/**
	 * Méthode qui retourne un boolean signifiant si le personnage est sur le navire ou pas
	 * @return boolean
	 */
	public boolean getSurnavire(){
		return this.surNavire;
	}
	
	/**
	 * Méthode d'application sur le boolean gérant la position du personnage sur le navire
	 * @param boolean
	 */
	public void setSurnavire(boolean b){
		this.surNavire = b;
	}
	
	/**
	 * Méthode qui retourne un boolean signifiant si le personnage détient la clef du coffre
	 * @return boolean
	 */
	public boolean getDetientClef(){
		return this.detientClef;
	}
	
	/**
	 * Méthode d'application sur le bollean gérant l'aquisation de la clef par le personnage
	 * @param boolean
	 */
	public void setDetientClef(boolean set) {
		this.detientClef = set;
	}
	
	/**
	 * Méthode qui retourne le bollean signifiant si e personnage a en sa possesion le tresor
	 * @return boolean
	 */
	public boolean getDetientTresor(){
		return this.detientTresor;
	}
	
	/**
	 * Méthode d'application sur le boolean qui gére l'aquisition du trésor par le personnage
	 * @param boolean
	 */
	public void setDetientTresor(boolean set){
		this.detientTresor = set;
	}
	
	/**
	 * Méthode d'affichage ToString
	 * @return String resultat
	 */
	public String toString(){
		return nom+" de l'équipe "+equipe+" possede "+energie+" points d'energie.";
	}
	
	/**
	 * attribut de class
	 */
	Object[] actionsSimples = { "haut", "bas", "gauche", "droite" };
	Object[] actionsMulti = { "haut", "bas", "gauche", "droite", "haut gauche", "haut droite", "bas gauche",
	"bas droite" };

	/**
	 * Métode qui gére l'interface de déplacement
	 * @return int resultat
	 */
	public int choixDeplacement(){
		String deplacement;
		if (this.nom != "voleur"){ // deplacement normal
			deplacement= (String) JOptionPane.showInputDialog(null, "Dans quel sens?", "choix deplacement",JOptionPane.INFORMATION_MESSAGE, null, actionsSimples, actionsSimples[0]);
		} else { // 8 direction => voleur
			deplacement= (String) JOptionPane.showInputDialog(null, "Dans quel sens?", "choix deplacement",JOptionPane.INFORMATION_MESSAGE, null, actionsMulti, actionsMulti[0]);
		}
		
		if (deplacement != null) {
			if(deplacement.equals("haut")){
				return 2;
			} else if (deplacement.equals("gauche")){
				return 1;
			} else if (deplacement.equals("droite")){
				return 3;
			} else if (deplacement.equals("bas")){
				return 4;
			} else if (deplacement.equals("haut gauche")){
				return 5;
			} else if (deplacement.equals("haut droite")){
				return 6;
			} else if (deplacement.equals("bas gauche")){
				return 7;
			} else if (deplacement.equals("bas droite")){
				return 8;
			}
		}
		return -1;
	}
}
