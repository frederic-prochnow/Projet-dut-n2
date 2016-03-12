package jeu;

import java.util.Random;

public class Ile {

	private Parcelle [][] plateau;
	Random r = new Random();
	
	Ile () {
		this.plateau = new Parcelle [10][10];
	}
	
	Ile (int taille) {
		this.plateau = new Parcelle [taille][taille];
	}
	
	public void initialiser (double pourcentage) {
		int nbRochers = 1;
		double pourcentageActuel = 0;
		for (int i=0; i<plateau.length; i++) {
			for (int j=0; j<plateau.length; j++) {
				plateau [i][j] = new Parcelle(-1);
			}
		}
		// EAU
		for(int i=0; i<plateau.length; i++) {
			plateau[i][0].type = 9;
			plateau[0][i].type = 9;
			plateau[plateau.length-1][i].type = 9;
			plateau[i][plateau.length-1].type = 9;
		}
		// NAVIRE
		plateau[1][1].type = 2;
		plateau[plateau.length -2][plateau.length -2].type = 5;
		// COFFRE
		int x, y;
		do {
			// EX: -3= de 0  a 7, +1= 1 a 8
			x=r.nextInt(plateau.length -3)+1;
			y =r.nextInt(plateau.length -3)+1;
		} while(plateau[x][y].type != -1);
		plateau[x][y].type = 7;
		plateau[x][y].estCompte = true;
		
		// ROCHERS
		int xR, yR;
		while (pourcentageActuel < pourcentage/100) {
			xR=r.nextInt(plateau.length -3)+1;
			yR =r.nextInt(plateau.length -3)+1;
			//if (accessibiliteAmorce(x,y, nbRochers)) {
			if (plateau[xR][yR].type == -1) {
				plateau[xR][yR].type = 6;
				nbRochers++;
				pourcentageActuel = (nbRochers/((plateau.length-2)*(plateau.length-2)));
			}
		//}
		}
	}
	
	private boolean accessibiliteAmorce(int x, int y, int nbRochers) {
		int compteur = 0;
		// on lance dans les 4 directions si le coffre se situe a partir de x>=2 x<=8 et y>=2 et y<=8
		if (x>1 && x<plateau.length-2 && y>1 && y<plateau.length-2) {
			verification(x-1, y);
			verification(x+1, y);
			verification(x, y-1);
			verification(x, y+1);
		// cas de bord gauche a faire et les autres bords
		} else if (x==1 && y>0 && y<plateau.length-1) {
		}
		for (int i=1; i<plateau.length-2;i++) {
			for (int j=1; j<plateau.length-2;j++) {
				if (plateau[x][y].estCompte) {
					compteur++;
				}
			}
		}
		// -2 dans multiplication car eau des deux cotes et puis -2 car deux navires
		if ( (plateau.length-2)*(plateau.length-2)-nbRochers-2 == compteur) {
			return true;
		}
		return false;
	}

	private void verification(int i, int y) {
		plateau[i][y].estCompte = true;
		if (i>1 && i<plateau.length-2 && y>1 && y<plateau.length-2) {
			verification(i-1, y);
			verification(i+1, y);
			verification(i, y-1);
			verification(i, y+1);
		// cas des bords
		} else if (i==1 && y>0 && y<plateau.length-1) {
		}
	}

	boolean deplacer(int x, int y, int a, int b) {
		// cas parcelle vide
		if (plateau[a][b].type == -1) {
			plateau[a][b].type = plateau[x][y].type;
			plateau[x][y].type = -1;
			changerEnergie(x,y,0);
			return true;
		// cas navire equipe 1 || equipe 2
		} else if ( (plateau[y][x].equipe && plateau[b][a].equipe) || (!plateau[y][x].equipe && !plateau[b][a].equipe) ) {
			plateau[a][b].type = plateau[x][y].type;
			plateau[x][y].type = -1;
			plateau[a][b].surNavire = true;
			changerEnergie(x,y,1);
			return true;
		// cas rocher dessus clef || coffre
		// A AJOUTER VERIFICATION QUE C'EST UN EXPLORATEUR
		} else if (plateau[a][b].type == 7 || plateau[a][b].type == 8 ) {
			changerEnergie(x,y,2);
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
		int nombre = (plateau.length*2)+1;
		boolean ligneV = true;
		boolean ligneH = true;
		int ligne=0,colonne=0;
		String res = "";
		for (int i=0;i<nombre;i++) {
			colonne=0;
			ligneV = true;
			for (int j=0;j<nombre;j++) {
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