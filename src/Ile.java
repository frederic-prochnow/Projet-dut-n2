import java.util.Random;

import javax.swing.JOptionPane;
/**
 * Classe Ile
 * Cette classe permet la création d'une île
 * @author Team J3
 *
 */
public class Ile {

	/**
	 * attributs de la classe
	 */
	private Parcelle[][] plateau;
	private int[][] plateauGraphique;
	Random r = new Random();
	int nbRochers = 0;
	
	/**
	 * Constructeur de la classe sans paramétres
	 * 
	 * Ce constructeur crée un plateau terminal et graphique de taille dégini 
	 * par l'interface graphique java
	 * 
	 */
	Ile() {
		int tailleI = 0;
		int percentR = 10;
		
		String taille = JOptionPane.showInputDialog(null, "Taille de l'ile? ");
		tailleI = Integer.valueOf(taille);
		while (tailleI < 10 || tailleI > 25) {
			JOptionPane.showMessageDialog(null,
					"Cette valeur n'est pas autorisee");
			taille = JOptionPane.showInputDialog(null, "Taille de l'ile? ");
			tailleI = Integer.valueOf(taille);
		}
		
		String percent = JOptionPane.showInputDialog(null, "Pourcentage de rochers ?");
		percentR = Integer.valueOf(percent);
		while (percentR < 0 || percentR > 40) {
			JOptionPane.showMessageDialog(null,
					"Cette valeur n'est pas autorisee");
			percent = JOptionPane.showInputDialog(null, "Pourcentage de rochers ?");
			percentR = Integer.valueOf(percent);
		}
		
		this.plateau = new Parcelle[tailleI][tailleI];
		this.plateauGraphique = new int[tailleI][tailleI];
		
		initialiser(percentR);
	}

	/**
	 * Fonction qui initialise le plateau avec les différents éléments requis
	 * Eau, navire, coffre, clés, rochers ...
	 * 
	 * @param pourcentage
	 */
	public void initialiser(double pourcentage) {
		int xn=0, yn=0, xN=0, yN=0;
		double pourcentageActuel = 0;
		nbRochers = 0;
		for (int i = 0; i < plateau.length; i++) {
			for (int j = 0; j < plateau.length; j++) {
				plateau[i][j] = new Parcelle(-1);
			}
		}
		// EAU
		for (int i = 0; i < plateau.length; i++) {
			plateau[i][0].setType(9);
			plateau[0][i].setType(9);
			plateau[plateau.length - 1][i].setType(9);
			plateau[i][plateau.length - 1].setType(9);
		}
		// NAVIRE
		int cote = r.nextInt(4);
		int rxn = r.nextInt(plateau.length-2) +1;
		int ryn = r.nextInt(plateau.length-2) +1;
		
		int rXN = r.nextInt(plateau.length-2) +1;
		int rYN = r.nextInt(plateau.length-2) +1;
		
		if (cote==0) {
			xn = rxn;
			yn = 1;
			xN=rXN;
			yN=plateau.length-2;
		} else if (cote==1) {
			xn = plateau.length-2;
			yn = ryn;
			xN=1;
			yN=rYN;
		} else if (cote==2) {
			xn = rxn;
			yn = plateau.length-2;
			xN=rXN;
			yN=1;
		} else if (cote==3) {
			xn = 1;
			yn = ryn;
			xN=plateau.length-2;
			yN=rYN;
		}

		plateau[xn][yn].setType(2);
		plateau[xN][yN].setType(5);
		
		
		// COFFRE
		int x, y;
		do {
			// EX: -3= de 0 a 7, +1= 1 a 8
			x = r.nextInt(plateau.length - 3) + 1;
			y = r.nextInt(plateau.length - 3) + 1;
		} while (plateau[x][y].getType() != -1);
		plateau[x][y].setType(7);
		plateau[x][y].setEstCompte(true);// COFFRE
		int xCle, yCle;

		// CLE
		do {
			// EX: -3= de 0 a 7, +1= 1 a 8
			xCle = r.nextInt(plateau.length - 3) + 1;
			yCle = r.nextInt(plateau.length - 3) + 1;
		} while (plateau[xCle][yCle].getType() != -1);
		plateau[xCle][yCle].setType(8);
		plateau[xCle][yCle].setEstCompte(true);

		// ROCHERS
		int xR, yR;
		while (pourcentageActuel < pourcentage / 100) {

			// on ajoute un rocher random
			xR = r.nextInt(plateau.length - 2) + 1;
			yR = r.nextInt(plateau.length - 2) + 1;
			if (plateau[xR][yR].getType() == -1) {
				plateau[xR][yR].setType(6);
				nbRochers++;
				pourcentageActuel = ((double) nbRochers / ((plateau.length - 2) * (plateau.length - 2)));

				// on le supprime si elle ruine l'accessibilite
				// ou si les 4 rochers qui l'entourent ne sont pas accessibles
				if (!(accessibiliteAmorce(x, y, nbRochers) && accessibiliteAmorce(xn, yn, nbRochers)
						&& accessibiliteAmorce(xN, yN, nbRochers)
						&& accessibiliteAmorce(xCle, yCle, nbRochers))
						|| rocherEntoure(xR-1, yR) || rocherEntoure(xR+1, yR) || rocherEntoure(xR, yR-1)
						|| rocherEntoure(xR, yR+1) )  {
					plateau[xR][yR].setType(-1);
					nbRochers--;
					pourcentageActuel = ((double) nbRochers / ((plateau.length - 2) * (plateau.length - 2)));
				}
			}
		}
	}
	
	/**
	 * Fonction rocherEntoure
	 * si le rocher est en fait de l'eau, on dira qu'elle EST accessible.
	 * <br>dans cette situation, le rocher de base se situe sur une cote
	 * elle ne peut ruiner l'access que de 3 rochers  car une case est l'eau
	 * on le retirer si elle ruine l'access A UN SEUL de ces 3(i.e. ces 3 sont entoures apres avoir ajoute ce dernier)
	 * il faut donc dire que la case EAU est entoure pour qu'elle ne soit pas retiree
	 * <br>
	 * <br>dans ces autres cas, le rocher des base est a une case de distance de l'eau
 	 * pour que son voisin qui le separe avec l'eau soit entoure, on ne doit alors que regarder dans 3 directions
 	 * <br>
 	 * <br>sinon, on peut regarder dans les 4 directions
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean rocherEntoure(int x, int y) {
		// si le rocher est en fait de l'eau, on dira qu'elle EST accessible.
		// dans cette situation, le rocher de base se situe sur une cote
		// elle ne peut ruiner l'access que de 3 rochers  car une case est l'eau
		// on le retirer si elle ruine l'access A UN SEUL de ces 3(i.e. ces 3 sont entoures apres avoir ajoute ce dernier)
		// il faut donc dire que la case EAU est entoure pour qu'elle ne soit pas retiree

		if (x==0 || y==0 || (x==(plateau.length-1)) || (y==(plateau.length-1))) {
			return false;
		}
		else if (x==1) {
			return ( (plateau[x + 1][y].getType() != -1) && (plateau[x][y - 1].getType() != -1) && (plateau[x][y + 1].getType() != -1));
		}
		else if (x==(plateau.length-2)) {
			return ((plateau[x - 1][y].getType() != -1) && (plateau[x][y - 1].getType() != -1) && (plateau[x][y + 1].getType() != -1));
		}
		else if (y==1) {
			return ((plateau[x - 1][y].getType() != -1) && (plateau[x + 1][y].getType() != -1) && (plateau[x][y + 1].getType() != -1));
		}
		else if (y==(plateau.length-2)) {
			return ((plateau[x - 1][y].getType() != -1) && (plateau[x + 1][y].getType() != -1) && (plateau[x][y - 1].getType() != -1));
		}

		// sinon, on peut regarder dans les 4 directions
		return ((plateau[x - 1][y].getType() != -1) && (plateau[x + 1][y].getType() != -1) && (plateau[x][y - 1].getType() != -1) && (plateau[x][y + 1].getType() != -1));


	}

	/**
	 * Fonction accessibiliteAmorce qui vérifie l'acessibilité des éléments
	 * @param x
	 * @param y
	 * @param nbRochers
	 * @return
	 */
	private boolean accessibiliteAmorce(int x, int y, int nbRochers) {
		int nbAccessibles = 0;

		// reinitialisation des .estCompte car il seront comptes plusieurs fois
		// independemment
		for (int i = 1; i <= plateau.length - 2; i++) {
			for (int j = 1; j <= plateau.length - 2; j++) {
				plateau[i][j].setEstCompte(false);
			}
		}
		verification(x - 1, y);
		verification(x + 1, y);
		verification(x, y - 1);
		verification(x, y + 1);

		for (int i = 1; i <= (plateau.length - 2); i++) {
			for (int j = 1; j <= (plateau.length - 2); j++) {
				if (plateau[i][j].getEstCompte() && plateau[i][j].getType() == -1) {
					nbAccessibles++;
				}
			}
		}
		// -2 dans multiplication car eau des deux cotes et puis -4 car deux
		// navires coffre et cle
		if (((((plateau.length - 2) * (plateau.length - 2)) - nbRochers) - 4) == nbAccessibles) {
			return true;
		}
		return false;
	}

	/**
	 * Fonction de vérification des différents éléments placés
	 * @param x
	 * @param y
	 */
	private void verification(int x, int y) {
		if (!plateau[x][y].getEstCompte() && plateau[x][y].getType() == -1) {
			plateau[x][y].setEstCompte(true);
			// verif de 1 a 8, meme si 0,0 est mis a estCompte, ceci ne change
			// rien car nbAcc compte que ceux de 1 a 8
			if ((x > 0) && (x < (plateau.length - 1)) && (y > 0)
					&& (y < (plateau.length - 1))) {
				verification(x - 1, y);
				verification(x + 1, y);
				verification(x, y - 1);
				verification(x, y + 1);
			}
		}
	}

	/**
	 * Fonction de déplacements de certain éléments, ici les exploraters
	 * @param x
	 * @param y
	 * @param a
	 * @param b
	 * @return
	 */
	boolean deplacer(int x, int y, int a, int b) {
		return false;
	}

	/**
	 * Fonction d'affichage du plateau
	 */
	public String toString() {
		int nombre = (plateau.length * 2) + 1;
		boolean ligneV = true;
		boolean ligneH = true;
		int ligne = 0, colonne = 0;
		String res = "";
		for (int i = 0; i < nombre; i++) {
			colonne = 0;
			ligneV = true;
			for (int j = 0; j < nombre; j++) {
				if (ligneH) {
					if (ligneV) {
						res += "+";
					} else {
						res += "---";
					}
				} else {
					if (ligneV) {
						res += "|";
					} else {
						res += " " + plateau[colonne][ligne].toString() + " ";
						colonne++;
					}
				}
				ligneV = !ligneV;
			}
			if (!ligneH) {
				ligne++;
			}
			ligneH = !ligneH;
			res += "\n";
		}
		return res;
	}

	/**
	 * Fonction de récupération du plateau graphique
	 * @return int[][]
	 */
	public int[][] getplateaugraphique() {
		for(int i = 0; i < plateau[0].length; i++){
			for(int j = 0; j < plateau[1].length; j++){
				plateauGraphique[i][j] = plateau[i][j].getType() + 2; // +2 nescessaire pour demarer le tableau d'img à 0 et non -1
			}
		}
		return plateauGraphique;
	}
	
	/**
	 * Fonction de récupération de la taille du plateau
	 * @return int
	 */
	public int getSize(){
		return plateau[0].length;
	}
	
	public Position getPos(int type){
		for(int i = 0; i < plateau[0].length; i++){
			for(int j = 0; j < plateau[1].length; j++){
				if(plateau[i][j].getType() == type){ 
					return new Position(i,j);
				}
			}
		}
		return null;
	}
	
}