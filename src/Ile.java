import java.util.Random;

import javax.swing.JOptionPane;

public class Ile {

	private Parcelle[][] plateau;
	private int[][] plateauGraphique;
	private Random r = new Random();
	private int nbRochers = 0;
	
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

	public void initialiser(double pourcentage) {
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
		int cote = r.nextInt(5);
		int xN = r.nextInt(plateau.length-2) +1;
		int yN = r.nextInt(plateau.length-2) +1;
		if (cote==0) {
			plateau[xN][1].setType(2);
		} else if (cote==1) {
			plateau[plateau.length-2][yN].setType(2);
		} else if (cote==3) {
			plateau[xN][plateau.length-2].setType(2);
		} else if (cote==4) {
			plateau[1][yN].setType(2);
		}
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
				if (!(accessibiliteAmorce(x, y, nbRochers) && accessibiliteAmorce(1, 1, nbRochers)
						&& accessibiliteAmorce(plateau.length - 2, plateau.length - 2, nbRochers)
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
	
	private boolean rocherEntoure(int x, int y) {
		// si le rocher est en fait de l'eau, on dira qu'elle EST accessible.
		// dans cette situation, le rocher de base se situe sur une cote
		// elle ne peut ruiner l'access que de 3 rochers  car une case est l'eau
		// on le retirer si elle ruine l'access A UN SEUL de ces 3(i.e. ces 3 sont entoures apres avoir ajoute ce dernier)
		// il faut donc dire que la case EAU est entoure pour qu'elle ne soit pas retiree
		if (x==0 || y==0 || (x==(plateau.length-1)) || (y==(plateau.length-1))) {
			return false;
		}
		// dans ces autres cas, le rocher des base est a une case de distance de l'eau
		// pour que son voisin qui le separe avec l'eau soit entoure, on ne doit alors que regarder dans 3 directions
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

	boolean deplacer(int x, int y, int a, int b) {
		return false;
	}

	// 0 pour aller a une case vide
	// 1 pour aller a son navire
	// 2 pour soulever un rocher

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

	public int[][] getplateaugraphique() {
		for(int i = 0; i < plateau[0].length; i++){
			for(int j = 0; j < plateau[1].length; j++){
				plateauGraphique[i][j] = plateau[i][j].getType() + 2; // +2 nescessaire pour demarer le tableau d'img � 0 et non -1
			}
		}
		return plateauGraphique;
	}
	
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