/**
* Importation de class
*/
import java.util.Iterator;
import java.util.List;
/**
* Class GestionJeu
* Class de gestion du jeu
* @author Team J3
*/

public class GestionJeu {
	/**
	 * attribut
	 */
	private boolean tourEquipe1; // True = equipe 1 , false = equipe2
	private boolean fini;
	private int round;
	private int equipeVainqueur;
	
	/**
	 * Constructeur de la class
	 * Crée un interface de gestion du jeu
	 * @param boolean equipeDemarre
	 */
	GestionJeu(boolean equipeDemarre){
		tourEquipe1 = equipeDemarre;
		fini = false;
		equipeVainqueur = -1;
		round = 1;
	}
	
	/**
	 * Méthode qui retourne l'équipe qui a gagner
	 * @return int vainqueur
	 */
	public int getEquipeVainqueur(){
		return this.equipeVainqueur;
	}
	
	/**
	 * Méthode qui retourne le tour correspond é l'équipe 1
	 * @return boolean tourEquipe
	 */
	public boolean getTourEquipe(){
		return this.tourEquipe1;
	}
	
	/**
	 * Méthode qui retourne un boolean qui définit si la partie est fini
	 * @return boolean estfini
	 */
	public boolean getEstFini(List<Personnage> equipe){
		Personnage temp;
		for (Iterator<Personnage> itr = equipe.iterator();itr.hasNext();) {
			temp = itr.next();
			if (temp.getDetientTresor() && temp.getSurnavire()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Méthode d'application sur le boolean que la partie est fini ou pas
	 * @param boolean f
	 */
	public void setEstFini(boolean f){
		this.fini = f;
	}
	
	/**
	 * Méthode qui retourne le numéro de la manche en cour
	 * @return int manche
	 */
	public int getRound(){
		return this.round;
	}
	
	/**
	 * Méthode d'affichage toString
	 * @return String resultat
	 */
	public String toString(){
		if(this.fini){
			return "Le vainqueur est l'�quipe "+this.equipeVainqueur+". Avec "+this.round+" tours.";
		}else{
			return "Tour actuel : "+this.round;
		}
	}
	
	/**
	 * Méthode qui augmente de 1 le numero de la manche en cours
	 */
	public void nextRound(){
		tourEquipe1 = !tourEquipe1;
		round++;
	}
}
