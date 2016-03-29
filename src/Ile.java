import java.util.Random;

import javax.swing.JOptionPane;

/**
 * Classe Ile Cette classe permet la creation d'une ele
 * 
 * @author Team J3
 * 
 */
public class Ile {

	/**
	 * attributs de la classe
	 */
	private Parcelle[][] plateau;
	private int[][] plateauGraphique;
	private Random r = new Random();
	private int nbRochers = 0;
	private Coffre coffre;
	private Clef clef;

	/**
	 * Constructeur de la classe sans parametres
	 * 
	 * Ce constructeur cree un plateau terminal et graphique de taille degini
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

		String percent = JOptionPane.showInputDialog(null,
				"Pourcentage de rochers ?");
		percentR = Integer.valueOf(percent);
		while (percentR < 0 || percentR > 40) {
			JOptionPane.showMessageDialog(null,
					"Cette valeur n'est pas autorisee");
			percent = JOptionPane.showInputDialog(null,
					"Pourcentage de rochers ?");
			percentR = Integer.valueOf(percent);
		}

		this.plateau = new Parcelle[tailleI][tailleI];
		this.plateauGraphique = new int[tailleI][tailleI];

		initialiser(percentR);
	}

	/**
	 * Fonction qui initialise le plateau avec les differents elements requis
	 * Eau, navire, coffre, cles, rochers ...
	 * 
	 * @param pourcentage
	 */
	public void initialiser(double pourcentage) {
		int xn = 0, yn = 0, xN = 0, yN = 0;
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
		int rxn = r.nextInt(plateau.length - 2) + 1;
		int ryn = r.nextInt(plateau.length - 2) + 1;

		int rXN = r.nextInt(plateau.length - 2) + 1;
		int rYN = r.nextInt(plateau.length - 2) + 1;

		if (cote == 0) {
			xn = rxn;
			yn = 1;
			xN = rXN;
			yN = plateau.length - 2;
		} else if (cote == 1) {
			xn = plateau.length - 2;
			yn = ryn;
			xN = 1;
			yN = rYN;
		} else if (cote == 2) {
			xn = rxn;
			yn = plateau.length - 2;
			xN = rXN;
			yN = 1;
		} else if (cote == 3) {
			xn = 1;
			yn = ryn;
			xN = plateau.length - 2;
			yN = rYN;
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
		coffre = new Coffre(new Position(x, y));
		plateau[x][y].setType(6);
		plateau[x][y].setEstCompte(true);// COFFRE
		int xCle, yCle;
		System.out.println("coffre x=" + x + " y=" + y);
		System.out.println(coffre.getPos().x + " " + coffre.getPos().y);

		// CLE
		do {
			// EX: -3= de 0 a 7, +1= 1 a 8
			xCle = r.nextInt(plateau.length - 3) + 1;
			yCle = r.nextInt(plateau.length - 3) + 1;
		} while (plateau[xCle][yCle].getType() != -1);
		
		clef = new Clef(new Position(xCle, yCle));
		plateau[xCle][yCle].setType(6);
		plateau[xCle][yCle].setEstCompte(true);
		System.out.println("cle x=" + xCle + " y=" + yCle);
		System.out.println(clef.getPos().x);

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
				if (!(accessibiliteAmorce(x, y, nbRochers)
						&& accessibiliteAmorce(xn, yn, nbRochers)
						&& accessibiliteAmorce(xN, yN, nbRochers) && accessibiliteAmorce(
							xCle, yCle, nbRochers))
						|| rocherEntoure(xR - 1, yR)
						|| rocherEntoure(xR + 1, yR)
						|| rocherEntoure(xR, yR - 1)
						|| rocherEntoure(xR, yR + 1)) {
					plateau[xR][yR].setType(-1);
					nbRochers--;
					pourcentageActuel = ((double) nbRochers / ((plateau.length - 2) * (plateau.length - 2)));
				}
			}
		}
	}

	/**
	 * Fonction rocherEntoure si le rocher est en fait de l'eau, on dira qu'elle
	 * EST accessible. <br>
	 * dans cette situation, le rocher de base se situe sur une cote elle ne
	 * peut ruiner l'access que de 3 rochers car une case est l'eau on le
	 * retirer si elle ruine l'access A UN SEUL de ces 3(i.e. ces 3 sont
	 * entoures apres avoir ajoute ce dernier) il faut donc dire que la case EAU
	 * est entoure pour qu'elle ne soit pas retiree <br>
	 * <br>
	 * dans ces autres cas, le rocher des base est a une case de distance de
	 * l'eau pour que son voisin qui le separe avec l'eau soit entoure, on ne
	 * doit alors que regarder dans 3 directions <br>
	 * <br>
	 * sinon, on peut regarder dans les 4 directions
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean rocherEntoure(int x, int y) {
		// si le rocher est en fait de l'eau, on dira qu'elle EST accessible.
		// dans cette situation, le rocher de base se situe sur une cote
		// elle ne peut ruiner l'access que de 3 rochers car une case est l'eau
		// on le retirer si elle ruine l'access A UN SEUL de ces 3(i.e. ces 3
		// sont entoures apres avoir ajoute ce dernier)
		// il faut donc dire que la case EAU est entoure pour qu'elle ne soit
		// pas retiree

		if (x == 0 || y == 0 || (x == (plateau.length - 1))
				|| (y == (plateau.length - 1))) {
			return false;
		} else if (x == 1) {
			return ((plateau[x + 1][y].getType() != -1)
					&& (plateau[x][y - 1].getType() != -1) && (plateau[x][y + 1]
					.getType() != -1));
		} else if (x == (plateau.length - 2)) {
			return ((plateau[x - 1][y].getType() != -1)
					&& (plateau[x][y - 1].getType() != -1) && (plateau[x][y + 1]
					.getType() != -1));
		} else if (y == 1) {
			return ((plateau[x - 1][y].getType() != -1)
					&& (plateau[x + 1][y].getType() != -1) && (plateau[x][y + 1]
					.getType() != -1));
		} else if (y == (plateau.length - 2)) {
			return ((plateau[x - 1][y].getType() != -1)
					&& (plateau[x + 1][y].getType() != -1) && (plateau[x][y - 1]
					.getType() != -1));
		}

		// sinon, on peut regarder dans les 4 directions
		return ((plateau[x - 1][y].getType() != -1)
				&& (plateau[x + 1][y].getType() != -1)
				&& (plateau[x][y - 1].getType() != -1) && (plateau[x][y + 1]
				.getType() != -1));

	}

	/**
	 * Fonction accessibiliteAmorce qui verifie l'acessibilite des elements
	 * 
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
				if (plateau[i][j].getEstCompte()
						&& plateau[i][j].getType() == -1) {
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
	 * Fonction de verification des differents elements places
	 * 
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
	 * Fonction de deplacements de certain elements, ici les exploraters
	 * 
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
	 * Fonction de recuperation du plateau graphique
	 * 
	 * @return int[][]
	 */
	public int[][] getplateaugraphique() {
		for (int i = 0; i < plateau[0].length; i++) {
			for (int j = 0; j < plateau[1].length; j++) {
				plateauGraphique[i][j] = plateau[i][j].getType() + 2; // +2 nescessaire pour demarer le tableau d'img a 0  et  non  -1
			}
		}
		return plateauGraphique;
	}

	/**
	 * Fonction de recuperation de la taille du plateau
	 * 
	 * @return int
	 */
	public int getSize() {
		return plateau[0].length;
	}

	/**
	 * Fonction de recuperation de la position d'une entite voulu sur la carte(
	 * ATTENTION ! ne pas mettre de type susceptible d'etre trouve plusieurs
	 * fois)
	 * 
	 * @return Position
	 */
	public Position getPos(int type) {
		for (int i = 0; i < plateau[0].length; i++) {
			for (int j = 0; j < plateau[1].length; j++) {
				if (plateau[i][j].getType() == type) {
					return new Position(i, j);
				}
			}
		}
		return null;
	}

	/**
	 * Fonction qui permet de deplacer un personnage dans une direction
	 * 
	 * @param perso
	 * @param x
	 * 
	 *            Si x vaut 1 => GAUCHE Si x vaut 2 => HAUT Si x vaut 3 =>
	 *            DROITE Si x vaut 4 => BAS
	 * 
	 */
	public void deplacer(Personnage perso, int x) {
		if (x == 1) {
			Position pos = new Position(perso.getPos().x - 1, perso.getPos().y);

			if (estVide(pos)) {
				if (perso.getSurnavire()) { // Est sur navire
					plateau[perso.getPos().x][perso.getPos().y].setType(2);
					perso.setSurnavire(false);
				} else { // Sur sol
					plateau[perso.getPos().x][perso.getPos().y].setType(-1);
				}
				perso.setPos(pos);
				perso.perdEnergie(1);
				plateau[perso.getPos().x][perso.getPos().y].setType(perso
						.getType());
			} else if (estRocher(pos)) {
				if (perso instanceof Explorateur) {
					if (estCoffre(pos)) {
						System.out
								.println("Peut soulever le rocher a gauche et il y a le coffre en dessous");
						plateau[pos.x][pos.y].setType(7); // on revele le coffre
						if (perso.getDetientClef()) {
							coffre.setEstOuvert(true); // on ouvre le coffre
							System.out
									.println("Il a la cle donc il a pris le tresor");
							perso.setDetientCoffre(true);
							coffre.setEstVide(true);
						}
					} else if (estCle(pos)) {
						System.out
								.println("Peut soulever le rocher a gauche et il y a la cle en dessous");
						plateau[pos.x][pos.y].setType(8);
						perso.setDetientClef(true);
					} else {
						System.out
								.println("Souleve le rocher a gauche et il n'a rien");
					}
					perso.perdEnergie(5);
				} else {
					System.out
							.println("Que les explorateurs peuvent soulever les rochers");
				}
			} else if (estCoffre(pos) && coffre.getEstOuvert()) {
				if (perso instanceof Explorateur) {
					if (perso.getDetientClef()) {
						System.out
								.println("Il a la cle donc il a pris le tresor");
						perso.setDetientCoffre(true);
						coffre.setEstVide(true);
					} else {
						System.out.println("Peut pas prendre le tresor car n'a pas la cle");
					}
				} else {
					System.out.println("Que les explorateurs peuvent prendre le tresor");
				}
			} else if (estNavire(pos, perso.getEquipe())) { // Verifie si c'est
															// le navire allie
				if (perso.getSurnavire()) { // Est sur navire
					plateau[perso.getPos().x][perso.getPos().y].setType(2);
					perso.setSurnavire(false);
				} else { // Sur sol
					plateau[perso.getPos().x][perso.getPos().y].setType(-1);
				}
				perso.setPos(pos);
				perso.perdEnergie(1);
				perso.setSurnavire(true);
			} else {
				System.out.println("Ne peut pas se deplacer a gauche");
			}
		} else if (x == 2) {
			Position pos = new Position(perso.getPos().x, perso.getPos().y - 1);

			if (estVide(pos)) {
				if (perso.getSurnavire()) { // Est sur navire
					plateau[perso.getPos().x][perso.getPos().y].setType(2);
					perso.setSurnavire(false);
				} else { // Sur sol
					plateau[perso.getPos().x][perso.getPos().y].setType(-1);
				}
				perso.setPos(pos);
				perso.perdEnergie(1);
				plateau[perso.getPos().x][perso.getPos().y].setType(perso
						.getType());
			} else if (estRocher(pos)) {
				if (perso instanceof Explorateur) {
					if (estCoffre(pos)) {
						System.out
								.println("Peut soulever le rocher en haut et il y a le coffre en dessous");
						plateau[pos.x][pos.y].setType(7); // on revele le coffre
						if (perso.getDetientClef()) {
							coffre.setEstOuvert(true); // on ouvre le coffre
							System.out
									.println("Il a la cle donc il a pris le tresor");
							perso.setDetientCoffre(true);
							coffre.setEstVide(true);
						}
					} else if (estCle(pos)) {
						System.out
								.println("Peut soulever le rocher en haut et il y a la cle en dessous");
						plateau[pos.x][pos.y].setType(8);
						perso.setDetientClef(true);
					} else {
						System.out
								.println("Souleve le rocher en haut et il n'a rien");
					}
					perso.perdEnergie(5);
				} else {
					System.out
							.println("Que les explorateurs peuvent soulever les rochers");
				}
			} else if (estCoffre(pos) && coffre.getEstOuvert()) {
				if (perso instanceof Explorateur) {
					if (perso.getDetientClef()) {
						System.out
								.println("Il a la cle donc il a pris le tresor");
						perso.setDetientCoffre(true);
						coffre.setEstVide(true);
					} else {
						System.out
								.println("Peut pas prendre le tresor car n'a pas la cle");
					}
				} else {
					System.out
							.println("Que les explorateurs peuvent prendre le tresor");
				}
			} else if (estNavire(pos, perso.getEquipe())) { // Verifie si c'est
															// le navire allie
				if (perso.getSurnavire()) { // Est sur navire
					plateau[perso.getPos().x][perso.getPos().y].setType(2);
					perso.setSurnavire(false);
				} else { // Sur sol
					plateau[perso.getPos().x][perso.getPos().y].setType(-1);
				}
				perso.setPos(pos);
				perso.perdEnergie(1);
				perso.setSurnavire(true);
			} else {
				System.out.println("Ne peut pas se deplacer en haut");
			}
		} else if (x == 3) {
			Position pos = new Position(perso.getPos().x + 1, perso.getPos().y);

			if (estVide(pos)) {
				if (perso.getSurnavire()) { // Est sur navire
					plateau[perso.getPos().x][perso.getPos().y].setType(2);
					perso.setSurnavire(false);
				} else { // Sur sol
					plateau[perso.getPos().x][perso.getPos().y].setType(-1);
				}
				perso.setPos(pos);
				perso.perdEnergie(1);
				plateau[perso.getPos().x][perso.getPos().y].setType(perso
						.getType());
			} else if (estRocher(pos)) {
				if (perso instanceof Explorateur) {
					if (estCoffre(pos)) {
						System.out
								.println("Peut soulever le rocher a droite et il y a le coffre en dessous");
						plateau[pos.x][pos.y].setType(7); // on revele le coffre
						if (perso.getDetientClef()) {
							coffre.setEstOuvert(true); // on ouvre le coffre
							System.out
									.println("Il a la cle donc il a pris le tresor");
							perso.setDetientCoffre(true);
							coffre.setEstVide(true);
						}
					} else if (estCle(pos)) {
						System.out
								.println("Peut soulever le rocher a droite et il y a la cle en dessous");
						plateau[pos.x][pos.y].setType(8);
						perso.setDetientClef(true);
					} else {
						System.out
								.println("Souleve le rocher a droite et il n'a rien");
					}
					perso.perdEnergie(5);
				} else {
					System.out
							.println("Que les explorateurs peuvent soulever les rochers");
				}
			} else if (estCoffre(pos) && coffre.getEstOuvert()) {
				if (perso instanceof Explorateur) {
					if (perso.getDetientClef()) {
						System.out
								.println("Il a la cle donc il a pris le tresor");
						perso.setDetientCoffre(true);
						coffre.setEstVide(true);
					} else {
						System.out
								.println("Peut pas prendre le tresor car n'a pas la cle");
					}
				} else {
					System.out
							.println("Que les explorateurs peuvent prendre le tresor");
				}
			} else if (estNavire(pos, perso.getEquipe())) { // Verifie si c'est
															// le navire allie
				if (perso.getSurnavire()) { // Est sur navire
					plateau[perso.getPos().x][perso.getPos().y].setType(2);
					perso.setSurnavire(false);
				} else { // Sur sol
					plateau[perso.getPos().x][perso.getPos().y].setType(-1);
				}
				perso.setPos(pos);
				perso.perdEnergie(1);
				perso.setSurnavire(true);
			} else {
				System.out.println("Ne peut pas se deplacer a droite");
			}
		} else if (x == 4) {
			Position pos = new Position(perso.getPos().x, perso.getPos().y + 1);

			if (estVide(pos)) {
				if (perso.getSurnavire()) { // Est sur navire
					plateau[perso.getPos().x][perso.getPos().y].setType(2);
					perso.setSurnavire(false);
				} else { // Sur sol
					plateau[perso.getPos().x][perso.getPos().y].setType(-1);
				}
				perso.setPos(pos);
				perso.perdEnergie(1);
				plateau[perso.getPos().x][perso.getPos().y].setType(perso
						.getType());
			} else if (estRocher(pos)) {
				if (perso instanceof Explorateur) {
					if (estCoffre(pos)) {
						System.out
								.println("Peut soulever le rocher en bas et il y a le coffre en dessous");
						plateau[pos.x][pos.y].setType(7); // on revele le coffre
						if (perso.getDetientClef()) {
							coffre.setEstOuvert(true); // on ouvre le coffre
							System.out
									.println("Il a la cle donc il a pris le tresor");
							perso.setDetientCoffre(true);
							coffre.setEstVide(true);
						}
					} else if (estCle(pos)) {
						System.out
								.println("Peut soulever le rocher en base et il y a la cle en dessous");
						plateau[pos.x][pos.y].setType(8);
						perso.setDetientClef(true);
					} else {
						System.out
								.println("Souleve le rocher en bas et il n'a rien");
					}
					perso.perdEnergie(5);
				} else {
					System.out
							.println("Que les explorateurs peuvent soulever les rochers");
				}
			} else if (estCoffre(pos) && coffre.getEstOuvert()) {
				if (perso instanceof Explorateur) {
					if (perso.getDetientClef()) {
						System.out
								.println("Il a la cle donc il a pris le tresor");
						perso.setDetientCoffre(true);
						coffre.setEstVide(true);
					} else {
						System.out
								.println("Peut pas prendre le tresor car n'a pas la cle");
					}
				} else {
					System.out
							.println("Que les explorateurs peuvent prendre le tresor");
				}
			} else if (estNavire(pos, perso.getEquipe())) { // Verifie si c'est
															// le navire allie
				if (perso.getSurnavire()) { // Est sur navire
					plateau[perso.getPos().x][perso.getPos().y].setType(2);
					perso.setSurnavire(false);
				} else { // Sur sol
					plateau[perso.getPos().x][perso.getPos().y].setType(-1);
				}
				perso.setPos(pos);
				perso.perdEnergie(1);
				perso.setSurnavire(true);
			} else {
				System.out.println("Ne peut pas se deplacer en dessous");
			}
		}
		if (perso instanceof Voleur) {
			if (x == 5) {

				Position pos = new Position(perso.getPos().x - 1,
						perso.getPos().y - 1);

				if (estVide(pos)) {
					if (perso.getSurnavire()) { // Est sur navire
						plateau[perso.getPos().x][perso.getPos().y].setType(2);
						perso.setSurnavire(false);
					} else { // Sur sol
						plateau[perso.getPos().x][perso.getPos().y].setType(-1);
					}
					perso.setPos(pos);
					perso.perdEnergie(-1);
					plateau[perso.getPos().x][perso.getPos().y].setType(perso
							.getType());

				} else if (estNavire(pos, perso.getEquipe())) { // Verifie si
																// c'est
																// le navire
																// allie
					if (perso.getSurnavire()) { // Est sur navire
						plateau[perso.getPos().x][perso.getPos().y].setType(2);
						perso.setSurnavire(false);
					} else { // Sur sol
						plateau[perso.getPos().x][perso.getPos().y].setType(-1);
					}
					perso.setPos(pos);
					perso.perdEnergie(-1);
					perso.setSurnavire(true);
				} else {
					System.out.println("Ne peut pas se deplacer en dessous");
				}

			} else if (x == 6) {
				Position pos = new Position(perso.getPos().x + 1,
						perso.getPos().y - 1);

				if (estVide(pos)) {
					if (perso.getSurnavire()) { // Est sur navire
						plateau[perso.getPos().x][perso.getPos().y].setType(2);
						perso.setSurnavire(false);
					} else { // Sur sol
						plateau[perso.getPos().x][perso.getPos().y].setType(-1);
					}
					perso.setPos(pos);
					perso.perdEnergie(-1);
					plateau[perso.getPos().x][perso.getPos().y].setType(perso
							.getType());

				} else if (estNavire(pos, perso.getEquipe())) { // Verifie si
																// c'est
																// le navire
																// allie
					if (perso.getSurnavire()) { // Est sur navire
						plateau[perso.getPos().x][perso.getPos().y].setType(2);
						perso.setSurnavire(false);
					} else { // Sur sol
						plateau[perso.getPos().x][perso.getPos().y].setType(-1);
					}
					perso.setPos(pos);
					perso.perdEnergie(-1);
					perso.setSurnavire(true);
				} else {
					System.out.println("Ne peut pas se deplacer en dessous");
				}
			} else if (x == 7) {
				Position pos = new Position(perso.getPos().x - 1,
						perso.getPos().y + 1);

				if (estVide(pos)) {
					if (perso.getSurnavire()) { // Est sur navire
						plateau[perso.getPos().x][perso.getPos().y].setType(2);
						perso.setSurnavire(false);
					} else { // Sur sol
						plateau[perso.getPos().x][perso.getPos().y].setType(-1);
					}
					perso.setPos(pos);
					perso.perdEnergie(-1);
					plateau[perso.getPos().x][perso.getPos().y].setType(perso
							.getType());

				} else if (estNavire(pos, perso.getEquipe())) { // Verifie si c'est le navire allie
					if (perso.getSurnavire()) { // Est sur navire
						plateau[perso.getPos().x][perso.getPos().y].setType(2);
						perso.setSurnavire(false);
					} else { // Sur sol
						plateau[perso.getPos().x][perso.getPos().y].setType(-1);
					}
					perso.setPos(pos);
					perso.perdEnergie(-1);
					perso.setSurnavire(true);
				} else {
					System.out.println("Ne peut pas se deplacer en dessous");
				}

			} else if (x == 8) {
				Position pos = new Position(perso.getPos().x + 1,
						perso.getPos().y + 1);

				if (estVide(pos)) {
					if (perso.getSurnavire()) { // Est sur navire
						plateau[perso.getPos().x][perso.getPos().y].setType(2);
						perso.setSurnavire(false);
					} else { // Sur sol
						plateau[perso.getPos().x][perso.getPos().y].setType(-1);
					}
					perso.setPos(pos);
					perso.perdEnergie(-1);
					plateau[perso.getPos().x][perso.getPos().y].setType(perso
							.getType());

				} else if (estNavire(pos, perso.getEquipe())) { // Verifie si c'est le navire allie
					if (perso.getSurnavire()) { // Est sur navire
						plateau[perso.getPos().x][perso.getPos().y].setType(2);
						perso.setSurnavire(false);
					} else { // Sur sol
						System.out.println("Ne peut pas se deplacer a droite");
					}
				}
			}
		}
	}

	public boolean estVide(Position p) {
		return (plateau[p.x][p.y].getType() == -1);
	}

	public boolean estNavire(Position p, int equipe) {
		if (equipe == 1) {
			return (plateau[p.x][p.y].getType() == 2);
		} else {
			return (plateau[p.x][p.y].getType() == 5);
		}
	}

	public boolean estRocher(Position p) {
		return (plateau[p.x][p.y].getType() == 6);
	}

	public boolean estCoffre(Position p) {
		return p.equals(coffre.getPos());
	}

	public boolean estCle(Position p) {
		return p.equals(clef.getPos());
	}

}