public class Explorateur extends Personnage {
	
	private boolean detientCoffre;
	private boolean detientClef;
	
	Explorateur(String nom, int equipe, int energie, Position p){
		super(nom,equipe,energie,p);
		this.detientClef = false;
		this.detientCoffre = false;
	}
	
	public boolean getDetientCoffre(){
		return detientCoffre;
	}
	
	public boolean getDetientClef(){
		return detientClef;
	}
}
