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
	private boolean mortEquipe1;
	private boolean mortEquipe2;
	
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
		mortEquipe1 = false;
		mortEquipe2 = false;
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
		if (equipe.isEmpty()) {
			this.fini = true;
			if (this.round % 2 == 0 ) {
				this.equipeVainqueur = 1;
				mortEquipe2 = true;
			} else {
				this.equipeVainqueur = 2;
				mortEquipe1 = true;
			}
			return true;
		} else {
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
		String res = "";
		if(this.fini){
			res += "Le vainqueur est l'équipe "+this.equipeVainqueur+" avec "+this.round+" tours.";
			if (mortEquipe1) {
				res += "\nCar l'équipe 1 est morte";
			} else if (mortEquipe2) {
				res += "\nCar l'équipe 2 est morte";
			}
		}else{
			res += "Tour actuel : "+this.round;
		}
		System.out.println(res);
		return res;
	}
	
	/**
	 * Passer a la manche suivante
	 */
	public void nextRound(){
		tourEquipe1 = !tourEquipe1;
		round++;
	}
}
