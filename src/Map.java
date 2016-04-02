import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
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
		String[] img = { "images/sable.png", "images/1.explorateur.png", "images/1.piegeur.png", "images/1.navire.png",
				"images/2.explorateur.png", "images/2.piegeur.png", "images/2.navire.png", "images/rocher.png",
				"images/coffre.png", "images/cle.jpg", "images/mer.png" };
		Ile plateau = new Ile();
		Plateau plateauGraph = new Plateau(img, plateau.getSize(),true);
		GestionJeu jeu = new GestionJeu(true);
		Object[] persos = { "explorateur", "voleur", "guerrier", "piegeur" };

		/* PERSONNAGES */
		
		List<Personnage> equipe1 = new ArrayList<>();
		List<Personnage> equipe2 = new ArrayList<>();
		
		Explorateur explo1 = new Explorateur("explorateur", 1, 100, plateau.getPos(2), 0);
		Personnage voleur1 = new Voleur("voleur", 1, 100, plateau.getPos(2), 1);
		equipe1.add(explo1);
		equipe1.add(voleur1);

		Explorateur explo2 = new Explorateur("explorateur", 2, 100, plateau.getPos(5), 3);
		Personnage voleur2 = new Voleur("voleur", 2, 100, plateau.getPos(5), 4);
		equipe2.add(explo2);
		equipe2.add(voleur2);
		

		/* INITIALISATIONS */
		plateauGraph.setJeu(plateau.getImagesCorrespondants());

		/* ACTIONS */
		
		Position persoSelectionPosition = new Position(-1, -1);
		Position selectionPrecisPosition = new Position(-1,-1);
		Position choixDeplacementPosition = new Position(-1, -1);
		List<Personnage> presentsEquipe = new ArrayList<>();
		Personnage personnnageSelectionne;
		Position confirmationFinTour = new Position(-1, -1);
		
		boolean bonneSelectionEquipe = true; 
		boolean deplacementInvalide = false;
		
		
		while (!jeu.getEstFini(equipe1) && !jeu.getEstFini(equipe2)) {// boucle tant que personne n'a gagne
			
			refresh(plateau, plateauGraph);
			
			if (!jeu.getDebutJeu()) {
				plateauGraph.println("A l'equipe suivante");
				System.out.println("A l'equipe suivante");
			}
			jeu.setDebutJeu(false);
			
			personnnageSelectionne = null;
			plateauGraph.clearConsole(jeu.getTourEquipe());
			persoSelectionPosition.setLocation(-1, -1);
			choixDeplacementPosition.setLocation(-1, -1);
			selectionPrecisPosition.setLocation(-1, -1);
			presentsEquipe.clear();
			plateauGraph.ajouterSelectionPersos(new ArrayList<Personnage>());
			deplacementInvalide = false;
				
			// ici on clique sur une case d'un de notre personnage
			// tant que le point selectionne est -1,-1 (defaut) et que c'est un point invalide (voir methode), la boucle continue a tourner
			while (persoSelectionPosition.getLocation().equals(new Point(-1, -1)) || !bonneSelectionEquipe) {
				plateauGraph.clearConsole(jeu.getTourEquipe());
				if (!bonneSelectionEquipe) {
					plateauGraph.println("Vous n'avez pas selectionnee une case ou vous disposez de personnage");
				}
				plateauGraph.println("Cliquez sur le personnage que vous voulez deplacer");
				plateauGraph.waitEvent(5000,false);
				// on met persoSelection a x,y du clic de la souris
				persoSelectionPosition.setLocation(plateauGraph.getX(plateauGraph.getCurrentEvent()), plateauGraph.getY(plateauGraph.getCurrentEvent()));
		//		System.out.println("selection perso " +persoSelectionPosition.getLocation());
				
				// verifie si le joueur a bien selectionne un perso de son equipe seulment si il a clique
				if (jeu.getTourEquipe() && !persoSelectionPosition.getLocation().equals(new Point(-1, -1))) {
					bonneSelectionEquipe = persoSelectionPosition.pointValide(equipe1);
					presentsEquipe = persoSelectionPosition.getPersosSurPosition(equipe1);
				} else if (!jeu.getTourEquipe()){
					bonneSelectionEquipe = persoSelectionPosition.pointValide(equipe2);
					presentsEquipe = persoSelectionPosition.getPersosSurPosition(equipe2);
				}
				
			}
			personnnageSelectionne = presentsEquipe.get(0);
			
			
			// CAS : il y a plusieurs persos sur une case : le debut du jeu
			if ( presentsEquipe.size() > 1) {
				plateauGraph.clearConsole(jeu.getTourEquipe());
				plateauGraph.println("Plusieurs personnages de votre equipê sont present sur cette case");
				plateauGraph.println("Veuillez selectionner une de ceux-cis");
				plateauGraph.ajouterSelectionPersos(presentsEquipe);
				refresh(plateau, plateauGraph);
								
				while (selectionPrecisPosition.getLocation().equals(new Position(-1, -1))) {
					plateauGraph.waitEvent(5000,true);
					selectionPrecisPosition.setLocation(-1,plateauGraph.getPerso(plateauGraph.getCurrentEvent()));
				}
				
				if (selectionPrecisPosition.equals(new Position(-1, 0))) {
					personnnageSelectionne = presentsEquipe.get(0);
				} else  if (selectionPrecisPosition.equals(new Position(-1, 1))) {
					personnnageSelectionne = presentsEquipe.get(1);
				}
				presentsEquipe.clear();
				presentsEquipe.add(personnnageSelectionne);
			}
			
			// on met que la perso selectionne (voir methode)
			plateauGraph.ajouterSelectionPersos(presentsEquipe);

			plateauGraph.clearConsole(jeu.getTourEquipe());
			while (choixDeplacementPosition.getLocation().equals(new Point(-1, -1)) || deplacementInvalide) {
				plateauGraph.clearConsole(jeu.getTourEquipe());
				plateauGraph.println("Vous avez selectionnée : " + personnnageSelectionne.getNom());
				if (deplacementInvalide) {plateauGraph.println("Ce deplacement n'est pas valide, veuillez reessayer");
				}
				plateauGraph.println("Cliquez sur la case ou vous voulez qu'il aille");
				plateauGraph.waitEvent(5000,false);
				choixDeplacementPosition.setLocation(plateauGraph.getX(plateauGraph.getCurrentEvent()), plateauGraph.getY(plateauGraph.getCurrentEvent()));
				
				
				if (plateau.deplacerV2Amorce(choixDeplacementPosition, personnnageSelectionne.getPos(), personnnageSelectionne)) {
					System.out.println("Le perso " + personnnageSelectionne.nom + " s'est deplace a " + choixDeplacementPosition.getLocation());
					deplacementInvalide = false;
				} else {
					deplacementInvalide = true;
				}
			}
			
			refresh(plateau, plateauGraph);
						
			while (confirmationFinTour.equals(new Position(-1, -1))) {
				plateauGraph.clearConsole("L'équipe 1 a fini son tour");
				plateauGraph.println("Veuillez cliquez sur la fenetre pour passer a l'equipe suivante");
				plateauGraph.waitEvent(5000, false);
				confirmationFinTour.setLocation(plateauGraph.getX(plateauGraph.getCurrentEvent()), plateauGraph.getY(plateauGraph.getCurrentEvent()));
			}

			jeu.nextRound();
			
	//		System.out.println("Le perso " + personnnageSelectionne.nom + " s'est deplace a " + choixDeplacementPosition.getLocation());
	//		System.out.println("verification de sa position actuelle " + personnnageSelectionne.getPos());
		
		}
		plateauGraph.clearConsole("");
		plateauGraph.println(jeu.toString());
	}

	/* permet de rafraichir l'affichage apres chaque action */
	public static void refresh(Ile plateau, Plateau plateauGraph, int waitTime) {
		refresh(plateau, plateauGraph);
		try {
			Thread.sleep(waitTime);
		} catch (Exception ie) {
		}
	}
	
	public static void refresh(Ile plateau, Plateau plateauGraph) {
		plateauGraph.setJeu(plateau.getImagesCorrespondants());
		plateauGraph.affichage(); // Affichage graphique
	}
}