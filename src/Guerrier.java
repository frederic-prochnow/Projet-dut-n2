/**
 * Class guerrier
 * Classe de gestion des guerriers
 * @author Team J3
 */
public class Guerrier extends Personnage{
	/**
	 * Constructeur de Guerrier
	 * @param nom Son nom
	 * @param equipe1 Appartient à l'équipe 1 
	 * @param energie Energie de base
	 * @param p Sa position de base
	 * @param type Son type selon Parcelle
	 */
	public Guerrier(String nom, boolean equipe, int energie, Position p, int type){
		super(nom,equipe,energie,p,type);
	}
}
