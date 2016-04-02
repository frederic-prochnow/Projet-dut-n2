import java.awt.Point;

public class Personnage {
	protected String nom;
	protected char symboleNom;
	protected int type; // M�me que dans parcelle
	protected int equipe;
	protected int energie;
	protected Point pos;
	protected boolean surNavire;
	private boolean detientClef;
	private boolean detientTresor;

	
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
	
	public int getType(){
		return this.type;
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
		if(equipe == 1){
			return 2;
		}else{
			return 5;
		}
	}
	
	public char getSymbole(){
		return symboleNom;
	}
	
	public int getEquipe(){
		return equipe;
	}
	
	public int getEnergie(){
		return energie;
	}
	
	public boolean getSurnavire(){
		return this.surNavire;
	}
	
	public void setSurnavire(boolean b){
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
		return nom+" de l'équipe "+equipe+" possede "+energie+" points d'energie.";
	}
	
	Object[] actionsSimples = { "haut", "bas", "gauche", "droite" };
	Object[] actionsMulti = { "haut", "bas", "gauche", "droite", "haut gauche", "haut droite", "bas gauche",
	"bas droite" };
	
	public int choixDeplacement(){
		return -1;
	}

	public boolean deplacementInvalide(Point destination) {
		if ((destination.x == (pos.x+1)) && (destination.y == pos.y)) {
			return false;
		}
		else if ((destination.x == (pos.x-1)) && (destination.y == pos.y)) {
			return false;
		}
		else if ((destination.y == (pos.y+1)) && (destination.x == pos.x)) {
			return false;
		}
		else if ((destination.y == (pos.y-1)) && (destination.x == pos.x)) {
			return false;
		}
		if (this instanceof Voleur) {
			if ((destination.x == (pos.x+1)) && (destination.y == pos.y+1)) {
				return false;
			}
			else if ((destination.x == (pos.x-1)) && (destination.y == pos.y+1)) {
				return false;
			}
			else if ((destination.y == (pos.x+1)) && (destination.y == pos.y-1)) {
				return false;
			}
			else if ((destination.y == (pos.x-1)) && (destination.y == pos.y-1)) {
				return false;
			}
		}
		return true;
	}
}
