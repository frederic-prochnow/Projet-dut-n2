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
		int x, y;
		do {
			// EX: -3= de 0  a 7, +1= 1 a 8
			x=r.nextInt(plateau.length -3)+1;
			y =r.nextInt(plateau.length -3)+1;
		} while(plateau[x][y].type != -1);
		plateau[x][y].type = 7;
		
	}
	
	void deplacer(int x, int y, int a, int b) {
		if (plateau[b][a].type == -1) {
			plateau[b][a].type = plateau[y][x].type;
			plateau[y][x].type = -1;
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
						res += " " + plateau[ligne][colonne].toString() + " ";
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