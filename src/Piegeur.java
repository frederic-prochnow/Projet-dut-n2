/**
 * Class Piegeur
 * Gestion des piegeur
 */
public class Piegeur extends Personnage{
	/**
	 * Constructeur de Piegeur
	 * @param nom Son nom
	 * @param equipe1 Appartient à l'équipe 1 
	 * @param energie Energie de base
	 * @param p Sa position de base
	 * @param type Son type selon Parcelle
	 */
	public Piegeur(String nom, boolean equipe, int energie, Position p, int type){
		super(nom,equipe,energie,p,type);
	}
}
