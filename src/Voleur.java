/**
 * Class Voleur
 * Gestion des Voleur
 * @author Team J3
 */
public class Voleur extends Personnage{
	/**
	 * Attribut
	 */
	boolean cleVolee;
	
	/**
	 * Constructeur de Voleur
	 * @param nom Son nom
	 * @param equipe1 Appartient à l'équipe 1 
	 * @param energie Energie de base
	 * @param p Sa position de base
	 * @param type Son type selon Parcelle
	 */
	public Voleur(String nom, boolean equipe, int energie, Position p, int type, int pointsMouvements){
		super(nom,equipe,energie,p,type,pointsMouvements);
		this.cleVolee=false;
	}
	/**
	 * Configuration de clee volee
	 */
	public void setCleVolee(){
		this.cleVolee=true;
	}
	/**
	 * Verification de clee volee
	 * @return booleen
	 */
	public boolean getCleVolee() {
		return this.cleVolee;
	}
}
