/**
 * Importation
 */
import java.util.Iterator;
import java.util.List;
/**
 * Class Gestion Jeu
 * Classe de gestion du jeu
 * @author Team J3
 */
public class GestionJeu {
	/**
	 * Attribut
	 */
	private boolean tourEquipe1; // True = equipe 1 , false = equipe2
	private boolean fini;
	private int round;
	private int equipeVainqueur;
	private boolean debutJeu;
	private boolean vsOrdi;
	
	/**
	 * Constructeur de la classe
	 * @param Booleen qui genere l equipe qui demarre
	 * @param Booleen qui genere le jeu contre l'ordinateur
	 */
	GestionJeu(boolean equipeDemarre, boolean vsOrdi){
		tourEquipe1 = equipeDemarre;
		fini = false;
		equipeVainqueur = -1;
		round = 1;
		debutJeu = true;
		this.vsOrdi = vsOrdi;
	}
	
	/**
	 * Retourne le numero de l'équipe vainqueur
	 * @return equipe vainqueur
	 */
	public int getEquipeVainqueur(){
		return this.equipeVainqueur;
	}
	
	/**
	 * Retourne le boolean de gestion de tour de l'equipe 1
	 * @return booleen
	 */
	public boolean getTourEquipe1(){
		return tourEquipe1;
	}
	
	/**
	 * Retourne le boolean de gestion de jeu fini
	 * @return booleen jeu fini
	 */
	public boolean getEstFini(List<Personnage> equipe){
		Personnage temp;
		for (Iterator<Personnage> itr = equipe.iterator();itr.hasNext();) {
			temp = itr.next();
			if (temp.getDetientTresor() && temp.getSurNavire()) {
				this.fini = true;
				if (this.round % 2 == 0 ) {
					this.equipeVainqueur = 1;
				} else {
					this.equipeVainqueur = 2;
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Retourne le booleen qui indique si le jeu est fini
	 * @return booleen jeu fini
	 */
	public boolean getEstFini(){
		return this.fini;
	}
	
	/**
	 * Retourne le numero de la manche
	 * @return manche
	 */
	public int getRound(){
		return this.round;
	}
	
	/**
	 * Retourne le booleen de debut de jeu
	 * @return booleen
	 */
	public boolean getDebutJeu() {
		return this.debutJeu;
	}
	
	/**
	 * Parametre le booleen de debut de jeu
	 * @param booleen de parametre
	 */
	public void setDebutJeu(boolean set) {
		this.debutJeu = set;
	}
	
	/**
	 * Retourne le booleen de gestion de jeu contre un ordinateur
	 * @return booleen
	 */
	public boolean getVsOrdi() {
		return vsOrdi;
	}
	
	/**
	 * Imprime l'etat de jeu, soit le numero de round ou le gagnant
	 * @return vainqueur ou tour
	 */
	public String toString(){
		if(this.fini){
			return "Le vainqueur est l'équipe "+this.equipeVainqueur+". Avec "+this.round+" tours.";
		}else{
			return "Tour actuel : "+this.round;
		}
	}
	
	/**
	 * Passer a la manche suivante
	 */
	public void nextRound(){
		tourEquipe1 = !tourEquipe1;
		round++;
	}
}
