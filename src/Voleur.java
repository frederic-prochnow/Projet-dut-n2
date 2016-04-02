public class Voleur extends Personnage{

	boolean cleVolee;
	
	Voleur(String nom, int equipe, int energie, Position p, int type){
		super(nom,equipe,energie,p,type);
		this.cleVolee=false;
	}
	
	public void setCleVolee(){
		this.cleVolee=true;
	}
	
	public boolean getCleVolee() {
		return this.cleVolee;
	}
}