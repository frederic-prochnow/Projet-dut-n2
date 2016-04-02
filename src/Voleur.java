/**
 * Class Voleur
 * Class gestion et cération de voleur
 */
 public class Voleur extends Personnage{
	/**
	 * atribut
	 */
	boolean cleVolee;
	
	/**
	 * Constructeur de la calsss Voleur
	 * Crée un voleur
	 * @param String nom,int equipe,int energie, Position p,int type
	 */
	Voleur(String nom, int equipe, int energie, Position p, int type){
		super(nom,equipe,energie,p,type);
		this.cleVolee=false;
	}
	
	/**
	 * Méthode d'application sur le boolean gérant le vol de clef
	 */
	public void setCleVolee(){
		this.cleVolee=true;
	}
	
	/**
	 * Méthode qui retourne un boolean gérant le vol de clef
	 * @return boolean
	 */
	public boolean getCleVolee() {
		return this.cleVolee;
	}
}
