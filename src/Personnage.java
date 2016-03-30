import javax.swing.JOptionPane;

public class Personnage {
	protected String nom;
	protected char symboleNom;
	protected int type; // M�me que dans parcelle
	protected int equipe;
	protected int energie;
	protected Position pos;
	protected boolean surNavire;
	private boolean detientClef;
	private boolean detientCoffre;

	
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
	
	public Position getPos(){
		return this.pos;
	}
	
	public void setPos(Position p){
		this.pos = p;
	}
	
	public String getNom(){
		return nom;
	}
	
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
	
	public boolean getDetientCoffre(){
		return this.detientCoffre;
	}
	
	public void setDetientCoffre(boolean set){
		this.detientCoffre = set;
	}
	public String toString(){
		return nom+" de l'équipe "+equipe+" possede "+energie+" points d'energie.";
	}
	
	Object[] actionsSimples = { "haut", "bas", "gauche", "droite" };
	Object[] actionsMulti = { "haut", "bas", "gauche", "droite", "haut gauche", "haut droite", "bas gauche",
	"bas droite" };
	
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
