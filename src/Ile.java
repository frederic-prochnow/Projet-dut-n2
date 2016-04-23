/**
 * Importation
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;
/**
 * Classe Ile Cette classe permet la creation d'une cle
 * @author Team J3
 */
public class Ile {

	/**
	 * attributs de la classe
	 */
	private Parcelle[][] plateau;
	private int[][] tabIconesGraphiqueEquipe1;
	private int[][] tabIconesGraphiqueEquipe2;
	private int[][] brouillardEquipe1;
	private int[][] brouillardEquipe2;
	private Random r = new Random();
	private int nbRochers = 0;
	private Coffre coffre;
	private Clef clef;
	private Navire navireEquipe1;
	private Navire navireEquipe2;

	/**
	 * Constructeur de la classe sans parametres
	 * 
	 * Ce constructeur cree un plateau qui nous sert de base a toutes modifications (Parcellle[][]) 
	 * Ensuite, ceci est traduit vers un int[][] (geIcones) dont chaque int correspond a une image
	 * 
	 */
	Ile(int tailleI, int pourcentageRochers) {		
		this.plateau = new Parcelle[tailleI][tailleI];
		this.tabIconesGraphiqueEquipe1 = new int[tailleI][tailleI];
		this.tabIconesGraphiqueEquipe2 = new int[tailleI][tailleI];
		this.brouillardEquipe1 = new int[tailleI][tailleI];
		this.brouillardEquipe2 = new int[tailleI][tailleI];

		initialiser(pourcentageRochers);
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
				
				// 0 = l'equipe a vu cette case
				// ce tab est mis à 1 au debut du jeu, des cases seront mises a 0 au cours du jeu
				// mais jamais revenir a 1
				brouillardEquipe1[i][j] = 1;
				brouillardEquipe2[i][j] = 1;
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
		navireEquipe1 = new Navire(getPos(2), true);
		navireEquipe2 = new Navire(getPos(5), false);

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
		System.out.println(x + " " + y);

		// CLE
		int xCle, yCle;
		do {
			// EX: -3= de 0 a 7, +1= 1 a 8
			xCle = r.nextInt(plateau.length - 3) + 1;
			yCle = r.nextInt(plateau.length - 3) + 1;
		} while (plateau[xCle][yCle].getType() != -1);

		clef = new Clef(new Position(xCle, yCle));
		plateau[xCle][yCle].setType(6);
		plateau[xCle][yCle].setEstCompte(true);
		System.out.println(xCle + " " + yCle);

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
						&& accessibiliteAmorce(xN, yN, nbRochers) && accessibiliteAmorce(xCle, yCle, nbRochers))
						|| rocherEntoure(xR - 1, yR) || rocherEntoure(xR + 1, yR) || rocherEntoure(xR, yR - 1)
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

		if (x == 0 || y == 0 || (x == (plateau.length - 1)) || (y == (plateau.length - 1))) {
			return false;
		} else if (x == 1) {
			return ((plateau[x + 1][y].getType() != -1) && (plateau[x][y - 1].getType() != -1)
					&& (plateau[x][y + 1].getType() != -1));
		} else if (x == (plateau.length - 2)) {
			return ((plateau[x - 1][y].getType() != -1) && (plateau[x][y - 1].getType() != -1)
					&& (plateau[x][y + 1].getType() != -1));
		} else if (y == 1) {
			return ((plateau[x - 1][y].getType() != -1) && (plateau[x + 1][y].getType() != -1)
					&& (plateau[x][y + 1].getType() != -1));
		} else if (y == (plateau.length - 2)) {
			return ((plateau[x - 1][y].getType() != -1) && (plateau[x + 1][y].getType() != -1)
					&& (plateau[x][y - 1].getType() != -1));
		}

		// sinon, on peut regarder dans les 4 directions
		return ((plateau[x - 1][y].getType() != -1) && (plateau[x + 1][y].getType() != -1)
				&& (plateau[x][y - 1].getType() != -1) && (plateau[x][y + 1].getType() != -1));

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
			if ((x > 0) && (x < (plateau.length - 1)) && (y > 0) && (y < (plateau.length - 1))) {
				verification(x - 1, y);
				verification(x + 1, y);
				verification(x, y - 1);
				verification(x, y + 1);
			}
		}
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
						res += " " + plateau[colonne][ligne].typeToString() + " ";
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
	 * Traduit les types de Parcelle[][] vers un int[][] intermédiaire.
	 * Ceci permet de travailler sur une variable au lieu de 2.
	 * Les cases non vus sont highlight en noir, et ceux precedemment vus sont en gris clair.
	 * On sauvegarde les cases deja vus.
	 * 
	 * @param equipeCourante Permet de connaitre à qui appartient le tour
	 * @param plateauGraph On travaille sur l'instance pour ajouter les highlight
	 * @param equipe1 La liste des personnages de l'équipe 1 pour reveler()
	 * @param equipe2 La liste des personnages de l'équipe 2 pour reveler()
	 * @return int[][]. Ce tab est reinitialise avec du sable a chaque fois, à optimiser
	 */
	public int[][] getImagesCorrespondants(boolean equipeCourante, Plateau plateauGraph, List<Personnage> equipe1, List<Personnage> equipe2) {
		int[][] tab = new int[plateau[0].length][plateau[1].length];

		// reset eau
		for (int i = 0; i < plateau.length; i++) {
			tab[i][0] = 9 +2;
			tab[0][i] = 9 +2;
			tab[plateau.length - 1][i] = 9 +2;
			tab[i][plateau.length - 1] = 9 +2;		
		}
		// plateau interieur
		for (int i = 1; i < plateau[0].length-1; i++) {
			for (int j = 1; j < plateau[1].length-1; j++) {
				// 0 pour sable
				tab[i][j] = 1;
				plateauGraph.setHighlight(i, j, Color.BLACK);
			}
		}
		// Ici on affiche les cases precedemment vus avec a reveler() et brouillardEquipe.
		// Le tableau qu'on affiche prend les valeurs des cases LORSQU'ELLES ont ete dernierment vus
		for (int i = 1; i < plateau[0].length-1; i++) {
			for (int j = 1; j < plateau[1].length-1; j++) {
				if (equipeCourante && brouillardEquipe1[i][j] == 0) {
						tab[i][j] = tabIconesGraphiqueEquipe1[i][j];
						plateauGraph.setHighlight(i, j, Color.LIGHT_GRAY);
				} else if (!equipeCourante && brouillardEquipe2[i][j] == 0) {
						tab[i][j] = tabIconesGraphiqueEquipe2[i][j];
						plateauGraph.setHighlight(i, j, Color.LIGHT_GRAY);
				}
			}
		}
		
		// On parcourt le tableau et révèle les alentours de chaque position de l'équipe courante
		for (int i = 1; i < plateau[0].length-1; i++) {
			for (int j = 1; j < plateau[1].length-1; j++) {
				// on s'assure qu'on revele bien les entourages d'un personnages s'il correspond à l'équipe dont le tour courant appartient
				if (equipeCourante && plateau[i][j].getEquipe1()) {
					reveler(i,j, tab, plateauGraph, equipeCourante, equipe1, equipe2);
				} else if ( (!equipeCourante) && plateau[i][j].getEquipe2() ) {
					reveler(i,j, tab, plateauGraph, equipeCourante, equipe1, equipe2);
				}
			}
		}
		// affecte la valeur pour le navire de l'equipe courante
		tab[getPositionNavire(equipeCourante).x][getPositionNavire(equipeCourante).y] = (plateau[getPositionNavire(equipeCourante).x][getPositionNavire(equipeCourante).y].getType() + 2);
		plateauGraph.resetHighlight(getPositionNavire(equipeCourante).x, getPositionNavire(equipeCourante).y);
		
		// sauvegarde du tableau dans un tab dedié a l'équipe
		if (equipeCourante) {
			copy(tab, tabIconesGraphiqueEquipe1);
		} else {
			copy(tab, tabIconesGraphiqueEquipe2);
		}
		return tab;
	}
	/**
	 * Affecte les vrais valeurs a tabIconesGraphiques de x-1 à x+1 pour y-1 à y+1.
	 * Clear le highlight de ces cases
	 * 
	 * @param x Le x de la case centre de révélation
	 * @param y Le y de la case centre de révélation
	 * @param tab Le tableau qu'on travaille dessus = celui qui est affiché à l'équipe courante
	 * @param plateauGraph On travaille sur l'instance pour retirer les highlight
	 * @param equipeCourante Permet de connaitre l'équipe à qui appartient le tour
	 * @param equipe1 La liste des personnages de l'équipe 1
	 * @param equipe2 La liste des personnages de l'équipe 2
	 */
	private void reveler(int x, int y, int[][] tab, Plateau plateauGraph, boolean equipeCourante, List<Personnage> equipe1, List<Personnage> equipe2) {
		Personnage temp;
		for (int h = (x-1);h<=(x+1);h++) {
			for (int k = (y-1);k<=(y+1);k++) {
				// +2 necessaire pour demarrer le tableau d'img a 0 et non a -1
				if (equipeCourante && plateau[h][k].getType() == 14 && plateau[h][k].getEstpiegeE2()) {
					tab[h][k] = (-1+2);
				} else if (!equipeCourante && plateau[h][k].getType() == 14 && plateau[h][k].getEstpiegeE1()) {
					tab[h][k] = (-1+2);
				} else {
				tab[h][k] = ((plateau[h][k].getType())+2);
				}
				
				plateauGraph.resetHighlight(h, k);
			
				if (equipeCourante) {
					brouillardEquipe1[h][k] = 0;
					for (Iterator<Personnage> persoIt = equipe2.iterator(); persoIt.hasNext();) {
						temp = persoIt.next();
						if (temp.getPos().equals(new Point(h, k))) {
							removeTagDansTableau(temp, tab);
							temp.setDernierTag(h, k);
						}
					}
				} else {
					brouillardEquipe2[h][k] = 0;
					for (Iterator<Personnage> persoIt = equipe1.iterator(); persoIt.hasNext();) {
						temp = persoIt.next();
						if (temp.getPos().equals(new Point(h, k))) {
							removeTagDansTableau(temp, tab);
							temp.setDernierTag(h, k);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Le "tag" est la position ancienne d'un des personnages vu par l'autre equipe
	 * par l'intermédiaire du type dans Parcelle[][]
	 * Retire le vieux tag dans le tab
	 * @param tag Position du tag a retirer
	 * @param tab le tableau sur lequel on travaille
	 */
	private void removeTagDansTableau(Personnage perso, int[][] tab) {
		if (!perso.getDernierTag().equals(new Position(-1, -1))) {
			tab[perso.getDernierTag().x][perso.getDernierTag().y] = plateau[perso.getDernierTag().x][perso.getDernierTag().y].getType()+2;
		}
	}
	
	/**
	 * Copie les valeurs d'un int[][] source à destination
	 * 
	 * @param source Le tableau source
	 * @param dest Le tableau destination
	 * 
	 */
	private void copy(int[][] source, int[][] dest) {
		for (int i = 1; i < plateau[0].length-1; i++) {
			for (int j = 1; j < plateau[1].length-1; j++) {
				dest[i][j] = source[i][j];
			}
		}
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
	 * Cette méthode permet de vérifier que le deplacement se fait bien dans une des directions
	 * possibles selon le Personnage et que c'est dans ses alentours immédiats (x-1/x+1, y-1/y+1)
	 * 
	 * @param destination La position destination
	 * @param posActuel La position actuelle du personnage
	 * @param perso L'instance du personnage, sert a verifier les possibilités de déplacement
	 * @param plateauGraph 
	 * 
	 * @return boolean
	 */
	public boolean deplacerV2Amorce(Position destination, Personnage perso, Plateau plateauGraph, boolean equipeCourante) {
		// Cas thoeriquement valides
		if (destination.x == perso.getPos().x+1 && destination.y == perso.getPos().y) {
			return deplacerV2(destination, perso.getPos(), perso, plateauGraph, equipeCourante);
		}
		else if (destination.x == perso.getPos().x-1 && destination.y == perso.getPos().y) {
			return deplacerV2(destination, perso.getPos(), perso, plateauGraph, equipeCourante);
		}
		else if (destination.y == perso.getPos().y+1 && destination.x == perso.getPos().x) {
			return deplacerV2(destination, perso.getPos(), perso, plateauGraph, equipeCourante);
		}
		else if (destination.y == perso.getPos().y-1 && destination.x == perso.getPos().x) {
			return deplacerV2(destination, perso.getPos(), perso, plateauGraph, equipeCourante);
		}
		if (perso instanceof Voleur) {
			if ((destination.x == (perso.getPos().x+1)) && (destination.y == (perso.getPos().y+1))) {
				return deplacerV2(destination, perso.getPos(), perso, plateauGraph, equipeCourante);
			}
			else if ((destination.x == (perso.getPos().x-1)) && (destination.y == (perso.getPos().y+1))) {
				return deplacerV2(destination, perso.getPos(), perso, plateauGraph, equipeCourante);
			}
			else if ((destination.x == (perso.getPos().x+1)) && (destination.y == (perso.getPos().y-1))) {
				return deplacerV2(destination, perso.getPos(), perso, plateauGraph, equipeCourante);
			}
			else if ((destination.x == (perso.getPos().x-1)) && (destination.y == (perso.getPos().y-1))) {
				return deplacerV2(destination, perso.getPos(), perso, plateauGraph, equipeCourante);
			}
		}
		return false;
	}
	
	
	/**
	 * Cette methode exécute le déplacement dans Parcelle[][]
	 * De base, on considere qu'il est correcte. On écrit que les cas où il est faux
	 * ou qu'on veut verifier certaines conditions ou imprimer des messages.
	 *  
	 * @param destination La position destination
	 * @param posActuel La position actuelle du personnage
	 * @param perso L'instance du personnage, sert a verifier s'il peut soulever les rochers
	 * @return boolean si le deplacement est bien valide selon le personnage
	 */
	private boolean deplacerV2(Position destination, Point posActuel, Personnage perso, Plateau plateauGraph, boolean equipeCourante) {
		plateauGraph.clearConsole();
		plateauGraph.clearSave();
		if (estVide(destination)) {
			if (perso.getSurNavire()) { // Est sur navire
				plateau[perso.getPos().x][perso.getPos().y].setType(perso.getNavireType());
				perso.setSurNavire(false);
				getNavire(perso.getEquipe1()).retirePerso();
			} else { // Sur sol
				plateau[perso.getPos().x][perso.getPos().y].setType(-1);
				if ( plateau[perso.getPos().x][perso.getPos().y].getEstpiegeE1() || plateau[perso.getPos().x][perso.getPos().y].getEstpiegeE2() ) {
					plateau[perso.getPos().x][perso.getPos().y].setType(14);
				}
			}
			perso.setDirectionDeplacement(destination.differenceCoordonnees(perso.getPos()));
			perso.setPos(destination.getLocation());
			perso.perdEnergie(1);
			plateau[perso.getPos().x][perso.getPos().y].setType(perso.getType());
		} else if (estRocher(destination)) {
			if (perso instanceof Explorateur) {
				if (estCoffre(destination)) {
					System.out.println("Peut soulever le rocher et il y a le coffre en dessous");
					plateauGraph.println("Peut soulever le rocher et il y a le coffre en dessous");
					plateauGraph.save();
					plateau[destination.x][destination.y].setType(7); // on revele le coffre
					if (perso.getDetientClef()) {
						coffre.setEstOuvert(true); // on ouvre le coffre
						plateauGraph.refreshCase(destination, 7);
						plateauGraph.waitClicPersoPane(750);
						plateau[destination.x][destination.y].setType(16);
						System.out.println("Il a la cle donc il a pris le tresor");
						plateauGraph.println("Il a la cle donc il a pris le tresor");
						plateauGraph.save();
						perso.setDetientTresor(true);
						coffre.setEstVide(true);
					}
				} else if (estCle(destination)) {
					System.out.println("Peut soulever le rocher et il y a la cle en dessous");
					plateauGraph.println("Peut soulever le rocher et prend la cle en dessous");
					plateauGraph.save();
					plateauGraph.refreshCase(destination, 8);
					plateauGraph.waitClicPersoPane(750);
					plateau[destination.x][destination.y].setType(15);
					clef.setPosition(new Position(-1,-1));
					perso.setDetientClef(true);
				} else {
					System.out.println("Souleve le rocher et il n'a rien");
					plateauGraph.println("Souleve le rocher et il n'a rien");
					plateauGraph.save();
				}
				perso.perdEnergie(5);
			} else {
				System.out.println("Que les explorateurs peuvent soulever les rochers");
				plateauGraph.println("Que les explorateurs peuvent soulever les rochers");
				plateauGraph.save();
				return false;
			}
		// le coffre est deja revele par une autre joueur
		} else if (plateau[destination.x][destination.y].getType() == 7) {
			if (coffre.getEstOuvert()) {
				perso.setDetientTresor(true);
				coffre.setEstVide(true);
			} else { // pas ouvert
				if (perso.getDetientClef()) {
					coffre.setEstOuvert(true); // on ouvre le coffre
					System.out.println("Il a la cle donc il a pris le tresor");
					plateauGraph.println("Il a la cle donc il a pris le tresor");
					plateauGraph.save();
					perso.setDetientTresor(true);
					coffre.setEstVide(true);
				} else { // pas ouvert, detient pas cle
					System.out.println("Le personnage n'a pas la cle pour ouvrir le coffre");
					plateauGraph.println("Le personnage n'a pas la cle pour ouvrir le coffre");
					plateauGraph.save();
				}
			}
		// Verifie si c'est le navire allie
		} else if (estSonNavire(destination, perso.getEquipe1())) {
			if (getNavire(perso.getEquipe1()).getPlateauVide()) {
				System.out.println("Vous devez avoir au moins un personnage sur le plateau");
				plateauGraph.println("Vous devez avoir au moins un personnage sur le plateau");
				plateauGraph.save();
				return false;
			} else {
				plateau[perso.getPos().x][perso.getPos().y].setType(-1);
				perso.setPos(destination.getLocation());
				perso.perdEnergie(1);
				perso.setSurNavire(true);
				if ( plateau[perso.getPos().x][perso.getPos().y].getEstpiegeE1() || plateau[perso.getPos().x][perso.getPos().y].getEstpiegeE2() ) {
					plateau[perso.getPos().x][perso.getPos().y].setType(14);
				}
			}
		} else {
			System.out.println("Ne peut pas se deplacer ici");
			plateauGraph.println("Ne peut pas se deplacer ici");
			plateauGraph.save();
			return false;
		}
		return true;
	}
	/**
	 * Met à jour l'affichage de l'énergie sur le Plateau
	 * et prévient de la mort imminente d'un personnage
	 * 
	 * @param tourEquipe permet de savoir pour quels équipe afficher les énergies
	 * @param equipe1 liste des personnages de l'équipe 1
	 * @param equipe2 liste des personnages de l'équipe 2
	 * @param plateauGraph le Plateau sur lequelle on travaille, affiche
	 */
	public void updateEnergie(boolean tourEquipe, List<Personnage> equipe1, List<Personnage> equipe2, Plateau plateauGraph) {
		Personnage temp;
		List<Personnage> tempEquipe = new ArrayList<>();
		if (tourEquipe) {
			tempEquipe = equipe1;
		} else {
			tempEquipe = equipe2;
		}
		plateauGraph.clearText();
		for (int x=1;x<plateau[0].length-1;x++) {
			for (int y=1;y<plateau[1].length-1;y++) {
				for (Iterator<Personnage> perso = tempEquipe.iterator();perso.hasNext();) {
					temp = perso.next();
					if (temp.getPos().equals(new Point(x,y))) {
						plateauGraph.setText(x, y, "" + temp.getEnergie());
						if (temp.getEnergie() <= 0) {
							plateauGraph.println("Le personnage n'a plus d'énergie. Il va mourir");
							plateauGraph.save();
						}
					}
				}
			}
		}
	}
	/**
	 * Applique les bonus d'énergie pour les personnages de l'équipe courante
	 * si elles sont dans leur bateau
	 * 
	 * @param tourEquipe permet de savoir pour quels équipe afficher les énergies
	 * @param equipe1 liste des personnages de l'équipe 1
	 * @param equipe2 liste des personnages de l'équipe 2
	 * @param plateauGraph le Plateau sur lequelle on travaille, affiche
	 */
	public void bonusEnergie(boolean tourEquipe, List<Personnage> equipe1, List<Personnage> equipe2, Plateau plateauGraph) {
		Personnage temp;
		List<Personnage> tempE = new ArrayList<>();
		if (tourEquipe) {
			tempE = equipe1;
		} else {
			tempE = equipe2;
		}
		for (Iterator<Personnage> perso = tempE.iterator();perso.hasNext();) {
			temp = perso.next();
			if (temp.getSurNavire()) {
				if (temp.getEnergie()<90) {
					temp.setEnergie(temp.getEnergie() + 10);
				} else {
					temp.setEnergie(100);
				}
			}
		}
	}
	/**
	 * On retire les personnages n'ayant plus d'énergie
	 * @param tourEquipe si c'est au tour de l'équipe 1
	 * @param equipe1 la liste des personnages de l'équipe 1
	 * @param equipe2 la liste des personnages de l'équipe 2
	 */
	public void retirerMorts(boolean tourEquipe, List<Personnage> equipe1, List<Personnage> equipe2) {
		Personnage tempPerso;
		List<Personnage> tempEquipe = new ArrayList<>();
		if (tourEquipe) {
			tempEquipe = equipe1;
		} else {
			tempEquipe = equipe2;
		}
		for (int i=0; i<tempEquipe.size();i++) {
			tempPerso = tempEquipe.get(i);
			if (tempPerso.getEnergie() <= 0) {
				System.out.println(tempPerso.getNom() + " est mort");
				if (tempPerso.getDetientClef() && !tempPerso.getDetientTresor()) {
					plateau[tempPerso.getPos().x][tempPerso.getPos().y].setType(8);
				} else if (tempPerso.getDetientTresor()) {
					plateau[tempPerso.getPos().x][tempPerso.getPos().y].setType(7);
				} else {
					plateau[tempPerso.getPos().x][tempPerso.getPos().y].setType(-1);
				}
				tempEquipe.remove(i);
			}
		}
	}
	
	
	/**
	 * Permet de savoir si la position est inoccupé
	 * @param p La position vérifiée
	 * @return boolean
	 */
	public boolean estVide(Position p) {
		return (plateau[p.x][p.y].getType() == -1);
	}

	public boolean estSonNavire(Position p, boolean equipe1) {
		if (equipe1) {
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
	
	public Position getPositionNavire(boolean equipe1) {
		for (int i = 0; i < plateau[0].length; i++) {
			for (int j = 0; j < plateau[1].length; j++) {
				if (equipe1 && plateau[i][j].getType() == 2) {
					return new Position(i, j);
				} else if ( (!equipe1) && plateau[i][j].getType() == 5) {
					return new Position(i, j);
				}
			}
		}
		return null;
	}
	/**
	 * Retourne l'instance de Navire de l'équipe.
	 * @param equipe1 vous cherchez le navire de l'équipe 1
	 * @return Navire
	 */
	public Navire getNavire(boolean equipe1) {
		if (equipe1) {
			return navireEquipe1;
		} 
		return navireEquipe2;
	}
	
	/**
	 * Retourne le plateau
	 * @return plateau
	public Parcelle[][] getPlateau() {
		return this.plateau;
	}
}
