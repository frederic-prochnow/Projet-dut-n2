package Jeu;
import java.util.Random;
/**
 * Class Ile
 * @author teamJ3
 * Projet
 */
public class Ile {
	/**
	 * attribut
	 */
	private Parcelle [][] plateau;
	Random r = new Random();
	
	/**
	 * Constructeur de plateau
	 */
	Ile () {
		this.plateau = new Parcelle [10][10];
	}
	
	/**
	 * Constructeur de plateau avec selection de taille
	 * @param int taille
	 */
	Ile (int taille) {
		this.plateau = new Parcelle [taille][taille];
	}
	
	/**
	 * Methode d'initialisation de tableau
	 */
	public void initialiser () {
		for (int i=0; i<plateau.length; i++) {
			for (int j=0; j<plateau.length; j++) {
				plateau [i][j] = new Parcelle(-1);
			}
		}
		// EAU
		for(int i=0; i<plateau.length; i++) {
			plateau[i][0].type = 8;
			plateau[0][i].type = 8;
			plateau[plateau.length-1][i].type = 8;
			plateau[i][plateau.length-1].type = 8;
		}
		// NAVIRE
		plateau[1][1].type = 2;
		plateau[plateau.length -2][plateau.length -2].type = 5;
		// COFFRE
		int x =r.nextInt(plateau.length -2);
		int y =r.nextInt(plateau.length -2);
		if(plateau[x][y].type == -1) {
			plateau[x][y].type = 7;
		}
		
	}
	
	/**
	 * Methode d'affichage toString
	 */
	public String toString() {
		int nombre = plateau.length;
		String res = "";
		for (int i=0; i<nombre; i++) {
			for (int j=0; j<nombre; j++) {
				res += "|" + plateau[i][j].toString();
			}
			res += "\n";
		}
		return res;
	}
}