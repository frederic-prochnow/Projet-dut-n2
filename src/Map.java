import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Map Class de test et application des classes Ile et Parcelle
 * 
 * @author TeamJ3
 *
 */
public class Map {
	/**
	 * Main de test et applications des classes Ile et Parcelle
	 */
	public static void main(String[] args) {
	
		/* DECLARATION DES VARIABLES */
		String[] img = { "images/sable.png", "images/1.explorateur.png", "images/1.voleur.png", "images/1.navire.png",
				"images/2.explorateur.png", "images/2.voleur.png", "images/2.navire.png", "images/rocher.png",
				"images/coffre.png", "images/cle.jpg", "images/mer.png" };
		Menu menu = new Menu();
		menu.affichage();
		while (!menu.getConfirme()) {
			menu.waitValidation(10000);
		}
		Ile plateau = new Ile(menu.getTaille(), menu.getRochers());
		Plateau plateauGraph = new Plateau(img, plateau.getSize(),true);
		GestionJeu jeu = new GestionJeu(true);

		/* PERSONNAGES */
		
		List<Personnage> equipe1 = new ArrayList<>();
		List<Personnage> equipe2 = new ArrayList<>();
		
		Explorateur explo1 = new Explorateur("explorateur1-1", true, 100, plateau.getPos(2), 0);
		Personnage voleur1 = new Voleur("voleur1-1", true, 100, plateau.getPos(2), 1);
		Personnage voleur12 = new Voleur("voleur1-2", true, 100, plateau.getPos(2), 1);
		Personnage voleur13 = new Voleur("voleur1-3", true, 100, plateau.getPos(2), 1);
		equipe1.add(explo1);
		equipe1.add(voleur1);
		equipe1.add(voleur12);
		equipe1.add(voleur13);
		plateau.getNavire(true).addPersos(2);

		Explorateur explo2 = new Explorateur("explorateur2-1", false, 100, plateau.getPos(5), 3);
		Personnage voleur2 = new Voleur("voleur2-1", false, 100, plateau.getPos(5), 4);
		equipe2.add(explo2);
		equipe2.add(voleur2);
		plateau.getNavire(false).addPersos(2);
				
		/* INITIALISATIONS */
		plateauGraph.setJeu(plateau.getImagesCorrespondants(jeu.getTourEquipe(), plateauGraph, equipe1, equipe2), jeu.getDebutJeu());

		/* ACTIONS */
		
		Position persoSelectionPosition = new Position(-1, -1);
		Position choixDeplacementPosition = new Position(-1, -1);
		List<Personnage> presentsEquipe = new ArrayList<>();
		Personnage personnnageSelectionne;
		Position confirmationFinTour = new Position(-1, -1);
		int persoSelec = -1;
		
		boolean bonneSelectionEquipe = true; 
		boolean deplacementInvalide = false;
		boolean pasEnergie = false;
		
		
		while (!jeu.getEstFini(equipe1) && !jeu.getEstFini(equipe2)) {// boucle tant que personne n'a gagne
			
			if (!jeu.getDebutJeu()) {
				plateauGraph.println("A l'equipe suivante");
				System.out.println("A l'equipe suivante");
			} else {
				jeu.setDebutJeu(false);
			}
			
			plateauGraph.clearHighlight();
			plateau.bonusEnergie(jeu.getTourEquipe(), equipe1, equipe2, plateauGraph);
			plateau.updateEnergie(jeu.getTourEquipe(), equipe1, equipe2, plateauGraph);
			refresh(plateau, plateauGraph,jeu.getTourEquipe(), jeu.getDebutJeu(), equipe1, equipe2);
					
			personnnageSelectionne = null;
			persoSelectionPosition.setLocation(-1, -1);
			choixDeplacementPosition.setLocation(-1, -1);
			presentsEquipe.clear();
			plateauGraph.ajouterSelectionPersos(new ArrayList<Personnage>());
			deplacementInvalide = false;
			confirmationFinTour.setLocation(-1, -1);
			persoSelec = -1;
			plateauGraph.setPeutVoler(false);
				
			// ici on clique sur une case d'un de notre personnage
			// tant que le point selectionne est -1,-1 (defaut) et que c'est un point invalide (voir methode), la boucle continue a tourner
			while (persoSelectionPosition.getLocation().equals(new Point(-1, -1)) || !bonneSelectionEquipe || pasEnergie) {
				plateauGraph.clearConsole();
				plateauGraph.print("C'est au tour de l'", jeu.getTourEquipe());
				if (!bonneSelectionEquipe) {
					plateauGraph.println("Vous n'avez pas selectionnee une case ou vous disposez de personnage");
				}
				if (pasEnergie) {
					plateauGraph.println("Ce personnage n'a plus d'énergie, veuillez séléctionner un autre");
				}
				plateauGraph.println("Cliquez sur le personnage que vous voulez deplacer");
				plateauGraph.waitEvent(5000,false);
				// on met persoSelection a x,y du clic de la souris
				persoSelectionPosition.setLocation(plateauGraph.getX(plateauGraph.getCurrentEvent()), plateauGraph.getY(plateauGraph.getCurrentEvent()));
				
				// verifie si le joueur a bien selectionne un perso de son equipe seulment si il a clique
				if (jeu.getTourEquipe() && !persoSelectionPosition.getLocation().equals(new Point(-1, -1))) {
					bonneSelectionEquipe = persoSelectionPosition.pointValide(equipe1, plateauGraph);
					pasEnergie = persoSelectionPosition.getEnergieInvalide();
					if (bonneSelectionEquipe && !pasEnergie) {
						presentsEquipe = persoSelectionPosition.getPersosSurPosition(equipe1);
					} else {
						presentsEquipe = new ArrayList<Personnage>();
					}
				} else if (!jeu.getTourEquipe()){
					bonneSelectionEquipe = persoSelectionPosition.pointValide(equipe2, plateauGraph);
					pasEnergie = persoSelectionPosition.getEnergieInvalide();
					if (bonneSelectionEquipe && !pasEnergie) {
						presentsEquipe = persoSelectionPosition.getPersosSurPosition(equipe2);
					} else {
						presentsEquipe = new ArrayList<Personnage>();
					}
				}
			}
			
			// CAS : il y a plusieurs persos sur une case : le debut du jeu
			if ( presentsEquipe.size() > 1) {
				plateauGraph.clearConsole();
				plateauGraph.print("C'est au tour de l'", jeu.getTourEquipe());
				plateauGraph.println("Plusieurs personnages de votre equipe sont presents sur cette case");
				plateauGraph.println("Veuillez selectionner une de ceux-cis");
				plateauGraph.ajouterSelectionPersos(presentsEquipe);
				
				while (persoSelec == -1) {
					plateauGraph.waitEvent(5000,true);
					persoSelec = plateauGraph.getPersoPrecis();
				}
				personnnageSelectionne = presentsEquipe.get(persoSelec);
			} else {
				personnnageSelectionne = presentsEquipe.get(0);
				plateauGraph.setPeutVoler(plateau.tenterVol(personnnageSelectionne));
				plateauGraph.setPeutEchangerClef(plateau.peutEchanger(personnnageSelectionne, true));
				plateauGraph.setPeutEchangerTresor(plateau.peutEchanger(personnnageSelectionne, false));
				plateauGraph.ajouterSelectionPersos(presentsEquipe);
			}
			
			plateauGraph.setHighlight(persoSelectionPosition.x, persoSelectionPosition.y, Color.CYAN);
			System.out.println("Le perso " + personnnageSelectionne.nom + " est à " + personnnageSelectionne.getPos());
			
			while (choixDeplacementPosition.getLocation().equals(new Point(-1, -1)) || deplacementInvalide) {
				plateauGraph.setHighlight(persoSelectionPosition.x, persoSelectionPosition.y, Color.CYAN);
				plateauGraph.clearConsole();
				plateauGraph.print("C'est au tour de l'", jeu.getTourEquipe());
				plateauGraph.println("Vous avez selectionnée : " + personnnageSelectionne.getNom());
				if (deplacementInvalide) {
					plateauGraph.recover();
				}
				plateauGraph.println("Cliquez sur la case ou vous voulez qu'il aille");
				plateauGraph.waitEvent(5000,false);
				choixDeplacementPosition.setLocation(plateauGraph.getX(plateauGraph.getCurrentEvent()), plateauGraph.getY(plateauGraph.getCurrentEvent()));
				
				if (!choixDeplacementPosition.getLocation().equals(new Point(-1, -1))) {
					if (plateau.deplacerV2Amorce(choixDeplacementPosition, personnnageSelectionne.getPos(), personnnageSelectionne, plateauGraph)) {
						deplacementInvalide = false;
					} else {
						deplacementInvalide = true;
					}
				}
			}
			plateau.updateEnergie(jeu.getTourEquipe(), equipe1, equipe2, plateauGraph);
			System.out.println("Le perso " + personnnageSelectionne.nom + " est maintenant à " + personnnageSelectionne.getPos());
			refresh(plateau, plateauGraph,jeu.getTourEquipe(), jeu.getDebutJeu(), equipe1, equipe2);
						
			while (confirmationFinTour.equals(new Position(-1, -1)) && !jeu.getEstFini()) {
				plateauGraph.clearConsole();
				plateauGraph.recover();
				plateauGraph.println("L'", jeu.getTourEquipe(),"a fini son tour");
				plateauGraph.println("Veuillez cliquez sur la fenetre pour passer a l'equipe suivante");
				plateauGraph.waitEvent(5000, false);
				confirmationFinTour.setLocation(plateauGraph.getX(plateauGraph.getCurrentEvent()), plateauGraph.getY(plateauGraph.getCurrentEvent()));
			}
			jeu.nextRound();
		}
		plateauGraph.clearConsole();
		plateauGraph.println(jeu.toString());
	}

	/* permet de rafraichir l'affichage apres chaque action */
	public static void refresh(Ile plateau, Plateau plateauGraph, boolean equipeCourante, int waitTime, boolean debut, List<Personnage> equipe1, List<Personnage> equipe2) {
		refresh(plateau, plateauGraph, equipeCourante, debut, equipe1, equipe2);
		try {
			Thread.sleep(waitTime);
		} catch (Exception ie) {
		}
	}
	
	public static void refresh(Ile plateau, Plateau plateauGraph, boolean equipeCourante, boolean debut, List<Personnage> equipe1, List<Personnage> equipe2) {
		plateauGraph.setJeu(plateau.getImagesCorrespondants(equipeCourante, plateauGraph, equipe1, equipe2), debut);
		plateauGraph.affichage(); // Affichage graphique
	}
}