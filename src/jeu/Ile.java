package jeu;

import java.util.Random;

public class Ile {

	private Parcelle[][] plateau;
	Random r = new Random();
	int iterationsRocher = 0;
	int nbRochers = 0;

	Ile() {
		this.plateau = new Parcelle[10][10];
	}

	Ile(int taille) {
		this.plateau = new Parcelle[taille][taille];
	}

	public void initialiser(double pourcentage) {
		double pourcentageActuel = 0;
		nbRochers = 0;
		iterationsRocher = 0;
		for (int i = 0; i < plateau.length; i++) {
			for (int j = 0; j < plateau.length; j++) {
				plateau[i][j] = new Parcelle(-1);
			}
		}
		// EAU
		for (int i = 0; i < plateau.length; i++) {
			plateau[i][0].type = 9;
			plateau[0][i].type = 9;
			plateau[plateau.length - 1][i].type = 9;
			plateau[i][plateau.length - 1].type = 9;
		}
		// NAVIRE
		plateau[1][1].type = 2;
		plateau[plateau.length - 2][plateau.length - 2].type = 5;
		// COFFRE
		int x, y;
		do {
			// EX: -3= de 0 a 7, +1= 1 a 8
			x = r.nextInt(plateau.length - 3) + 1;
			y = r.nextInt(plateau.length - 3) + 1;
		} while (plateau[x][y].type != -1);
		plateau[x][y].type = 7;
		plateau[x][y].estCompte = true;// COFFRE
		int xCle, yCle;

		// CLE
		do {
			// EX: -3= de 0 a 7, +1= 1 a 8
			xCle = r.nextInt(plateau.length - 3) + 1;
			yCle = r.nextInt(plateau.length - 3) + 1;
		} while (plateau[xCle][yCle].type != -1);
		plateau[xCle][yCle].type = 8;
		plateau[xCle][yCle].estCompte = true;

		// ROCHERS
		int xR, yR;
		while (pourcentageActuel < pourcentage / 100) {
			// si le nombre de fois qu'on repasse dans le while pour creer un seul rocher est superieur a 25, on re-inialise
			// si c'est le cas, ca veut dire qu'on ne peut pas ajouter de rochers dans le plateau. Il faut donc un nouveau plateau
			if (iterationsRocher > 25) {
				initialiser(pourcentage);
			}
			xR = r.nextInt(plateau.length - 3) + 1;
			yR = r.nextInt(plateau.length - 3) + 1;

			if (plateau[xR][yR].type == -1 && accessibiliteAmorce(x, y, nbRochers, "coffre")
					&& accessibiliteAmorce(1, 1, nbRochers, "navire gauche") && accessibiliteAmorce(plateau.length - 2, plateau.length - 2, nbRochers, "navire droite")
					&& accessibiliteAmorce(xCle, yCle, nbRochers, "cle")) {
				plateau[xR][yR].type = 6;
				nbRochers++;
				pourcentageActuel = ((double) nbRochers / ((plateau.length - 2) * (plateau.length - 2)));
				iterationsRocher = 0;
			}
			iterationsRocher++;
		}
	}

	private boolean accessibiliteAmorce(int x, int y, int nbRochers, String ou) {
		int nbAccessibles = 0;
		
		// reinitialisation des .estCompte car il seront comptes plusieurs fois independemment
		for (int i = 1; i <= plateau.length - 2; i++) {
			for (int j = 1; j <= plateau.length - 2; j++) {
				plateau[i][j].estCompte = false;
			}
		}
		verification(x - 1, y);
		verification(x + 1, y);
		verification(x, y - 1);
		verification(x, y + 1);
		
		for (int i = 1; i <= (plateau.length - 2); i++) {
			for (int j = 1; j <= (plateau.length - 2); j++) {
				if (plateau[i][j].estCompte && plateau[i][j].type != 6) {
					nbAccessibles++;
				}
			}
		}
		// -2 dans multiplication car eau des deux cotes et puis -4 car deux navires coffre et cle
		if (((((plateau.length - 2) * (plateau.length - 2)) - nbRochers) - 4) == nbAccessibles) {
			return true;
		}
		return false;
	}

	private void verification(int x, int y) {
		if (!plateau[x][y].estCompte && plateau[x][y].type == -1) {
			plateau[x][y].estCompte = true;
			// verif de 1 a 8, meme si 0,0 est mis a estCompte, ceci ne change rien car nbAcc compte que ceux de 1 a 8
			if ((x > 0) && (x < (plateau.length-1)) && (y > 0) && (y < (plateau.length-1))) {
				verification(x - 1, y);
				verification(x + 1, y);
				verification(x, y - 1);
				verification(x, y + 1);
			}
		}
	}

	boolean deplacer(int x, int y, int a, int b) {
		// cas parcelle vide
		if (plateau[a][b].type == -1) {
			plateau[a][b].type = plateau[x][y].type;
			plateau[x][y].type = -1;
			changerEnergie(x, y, 0);
			return true;
			// cas navire equipe 1 || equipe 2
		} else if ((plateau[y][x].equipe && plateau[b][a].equipe) || (!plateau[y][x].equipe && !plateau[b][a].equipe)) {
			plateau[a][b].type = plateau[x][y].type;
			plateau[x][y].type = -1;
			plateau[a][b].surNavire = true;
			changerEnergie(x, y, 1);
			return true;
			// cas rocher dessus clef || coffre
			// A AJOUTER VERIFICATION QUE C'EST UN EXPLORATEUR
		} else if (plateau[a][b].type == 7 || plateau[a][b].type == 8) {
			changerEnergie(x, y, 2);
		}
		return false;
	}

	// 0 pour aller a une case vide
	// 1 pour aller a son navire
	// 2 pour soulever un rocher
	void changerEnergie(int x, int y, int deplacement) {
		if (deplacement == 0) {
			plateau[x][y].energie -= 1;
		} else if (deplacement == 1) {
			plateau[x][y].energie += 10;
		} else if (deplacement == 2) {
			plateau[x][y].energie -= 5;
		}
	}

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

}