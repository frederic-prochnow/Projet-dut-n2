public class Voleur extends Personnage{

	boolean cleVolee;
	
	/**
	 * Constructeur de Voleur
	 * @param nom Son nom
	 * @param equipe1 Appartient à l'équipe 1 
	 * @param energie Energie de base
	 * @param p Sa position de base
	 * @param type Son type selon Parcelle
	 */
	public Voleur(String nom, boolean equipe, int energie, Position p, int type){
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