import java.awt.Point;

public class Personnage {
	protected String nom;
	protected char symboleNom;
	protected int type; // Même que dans parcelle
	protected String cheminImage;
	protected boolean equipe1;
	protected int energie;
	protected Point pos;
	protected boolean surNavire;
	private boolean detientClef;
	private boolean detientTresor;
	private Position dernierTag;

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
			this.cheminImage = "images/2.voleur.png";
		} else if (type == 12) {
			this.cheminImage = "images/2.voleur.png";
		} else if (type == 13) {
			this.cheminImage = "images/2.voleur.png";
		}
		
		
		this.pos = p;
		this.dernierTag = new Position(-1, -1);
		
		if(equipe1){
			this.symboleNom = nom.charAt(0);
		}else{
			this.symboleNom = (nom.toUpperCase()).charAt(0);
		}
	}
	
	public int getType(){
		return this.type;
	}
	
	public String getCheminImage() {
		return this.cheminImage;
	}
	
	public void perdEnergie(int energie){
		this.energie -= energie;
	}
	
	public Point getPos(){
		return pos;
	}
	
	public void setPos(Point point){
		pos = point;
	}
	
	public String getNom(){
		return nom;
	}
	
	// retourne le type du navire selon le personnage
	public int getNavireType(){
		if(equipe1){
			return 2;
		}else{
			return 5;
		}
	}
	
	public char getSymbole(){
		return symboleNom;
	}
	
	public boolean getEquipe1(){
		return equipe1;
	}
	public boolean getEquipe2() {
		if (getEquipe1()) {
			return false;
		}
		return true;
	}
	
	public int getEnergie(){
		return energie;
	}
	public void setEnergie(int setEnergie) {
		this.energie = setEnergie;
	}
	
	public boolean getSurNavire(){
		return this.surNavire;
	}
	
	public void setSurNavire(boolean b){
		this.surNavire = b;
	}
	
	public boolean getDetientClef(){
		return this.detientClef;
	}
	
	public void setDetientClef(boolean set) {
		this.detientClef = set;
	}
	
	public boolean getDetientTresor(){
		return this.detientTresor;
	}
	
	public void setDetientTresor(boolean set){
		this.detientTresor = set;
	}
	public String toString(){
		if (equipe1) {
			return nom+" de l'équipe 1 possede "+energie+" points d'energie.";
		}
		return nom+" de l'équipe 2 possede "+energie+" points d'energie.";
	}
	
	Object[] actionsSimples = { "haut", "bas", "gauche", "droite" };
	Object[] actionsMulti = { "haut", "bas", "gauche", "droite", "haut gauche", "haut droite", "bas gauche",
	"bas droite" };
	
	public int choixDeplacement(){
		return -1;
	}
	
	public Position getDernierTag() {
		return this.dernierTag;
	}
	
	public void setDernierTag(int x, int y) {
		this.dernierTag.setLocation(x, y);
	}

}
