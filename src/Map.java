/**
 * Importation
 */
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.geometry.Pos;
import sun.net.www.content.text.plain;
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
	
	/* PERSONNAGES */
	
	Equipe equipe1 = new Equipe();
	Equipe equipe2 = new Equipe();
	int totalE1 = 0;
	int totalE2 = 0;
	Ile plateau;
	Plateau plateauGraph;
	GestionJeu jeu;
	boolean faitAction;
	Actions actions;
	
	/**
	 * Fonction pour jouer avec le jeu
	 */
	public void jouer() {
	
		/* DECLARATION DES VARIABLES */
		String[] img = { "images/grass.jpg", "images/1.explorateur.png", "images/1.voleur.png", "images/1.navire.png",
				"images/2.explorateur.png", "images/2.voleur.png", "images/2.navire.png", "images/rocher.png",
				"images/coffre.png", "images/cle.jpg", "images/mer4.png", "images/1.explorateur.png", "images/1.piegeur.png" 
				, "images/1.explorateur.png" , "images/2.piegeur.png", "images/pieger.png", "images/cle_prise.jpg",
				"images/coffre_ouvert.png"};
		Menu menu = new Menu();
		menu.affichage();
		while (!menu.getConfirme()) {
			menu.waitValidation(10000);
		}
		plateau = new Ile(menu.getTaille(), menu.getRochers());
		plateauGraph = new Plateau(img, plateau.getSize(),true);
		jeu = new GestionJeu(true, menu.getVersusOrdi());
		actions = new Actions();


		
		// Gestion de la liste d'explorateur de l'équipe 1
		Explorateur[] listExplo1 = new Explorateur[menu.getNbPersos(0)];
		int i = 0;
		for(Explorateur e : listExplo1){
			i++;
			e = new Explorateur("explorateur1-"+i, true, 100, plateau.getPos(2), 0);
			equipe1.getListe().add(e);
			totalE1++;
		}
		
		// Gestion de la liste de voleur de l'equipe 1
		Personnage[] listVoleur1 = new Voleur[menu.getNbPersos(1)];
		i = 0;
		for(Personnage e : listVoleur1){
			i++;
			e = new Voleur("voleur1-"+i, true, 100, plateau.getPos(2), 1);
			equipe1.getListe().add(e);
			totalE1++;
		}
		
		// Gestion de la liste de guerrier de l'equipe 1
		Personnage[] listGuerrier1 = new Guerrier[menu.getNbPersos(2)];
		i = 0;
		for(Personnage e : listGuerrier1){
			i++;
			e = new Guerrier("guerrier1-"+i, true, 100, plateau.getPos(2), 10);
			equipe1.getListe().add(e);
			totalE1++;
		}
		
		// Gestion de la liste de piegeur de l'equipe 1
		Personnage[] listPiegeur1 = new Piegeur[menu.getNbPersos(3)];
		i = 0;
		for(Personnage e : listPiegeur1){
			i++;
			e = new Piegeur("piegeur1-"+i, true, 100, plateau.getPos(2), 11);
			equipe1.getListe().add(e);
			totalE1++;
		}
		
		// Gestion de la liste d'explorateur de l'équipe 2
		Explorateur[] listExplo2 = new Explorateur[menu.getNbPersos(4)];
		i = 0;
		for(Explorateur e : listExplo2){
			i++;
			e = new Explorateur("explorateur2-"+i, false, 100, plateau.getPos(5), 3);
			equipe2.getListe().add(e);
			totalE2++;
		}
		
		// gestion de la liste de voleur de l'équipe 2
		Personnage[] listVoleur2 = new Voleur[menu.getNbPersos(5)];
		i = 0;
		for(Personnage e : listVoleur2){
			i++;
			e = new Voleur("voleur2-"+i, false, 100, plateau.getPos(5), 4);
			equipe2.getListe().add(e);
			totalE2++;
		}
		
		// Gestion de la liste de guerrier de l'equipe 2
		Personnage[] listGuerrier2 = new Guerrier[menu.getNbPersos(6)];
		i = 0;
		for(Personnage e : listGuerrier2){
			i++;
			e = new Guerrier("guerrier2-"+i, false, 100, plateau.getPos(5), 12);
			equipe2.getListe().add(e);
			totalE2++;
		}
		
		// Gestion de la liste de piegeur de l'equipe 2
		Personnage[] listPiegeur2 = new Piegeur[menu.getNbPersos(7)];
		i = 0;
		for(Personnage e : listPiegeur2){
			i++;
			e = new Piegeur("piegeur2-"+i, false, 100, plateau.getPos(5), 13);
			equipe2.getListe().add(e);
			totalE2++;
		}
				
		
		plateau.getNavire(true).addPersos(totalE1);
		plateau.getNavire(false).addPersos(totalE2);
		
		if(menu.getChoixManuel()){
			//lancement du menu de choix manuel
		}
				
		/* INITIALISATIONS */
		plateauGraph.setJeu(plateau.getImagesCorrespondants(jeu.getTourEquipe1(), plateauGraph, equipe1.getListe(), equipe2.getListe()), jeu.getDebutJeu());

		/* ACTIONS */
		
		Position persoSelectionPosition = new Position(-1, -1);
		Position choixDeplacementPosition = new Position(-1, -1);
		List<Personnage> presentsEquipe = new ArrayList<>();
		Personnage personnnageSelectionne;
		Position confirmationFinTour = new Position(-1, -1);
		int persoSelec = -1;
		
		boolean bonneSelectionEquipe = true; 
		boolean deplacementValide = false;
		boolean pasEnergie = false;

		Equipe tempEquipe;
		
		
		while (!jeu.getEstFini(equipe1.getListe()) && !jeu.getEstFini(equipe2.getListe())) {// boucle tant que personne n'a gagne
			
			if (!jeu.getDebutJeu()) {
				plateauGraph.println("A l'equipe suivante");
				System.out.println("A l'equipe suivante");
			} else {
				jeu.setDebutJeu(false);
			}
			
			plateauGraph.clearHighlight();
			plateau.bonusEnergie(jeu.getTourEquipe1(), equipe1.getListe(), equipe2.getListe(), plateauGraph);
			plateau.updateEnergie(jeu.getTourEquipe1(), equipe1.getListe(), equipe2.getListe(), plateauGraph);
			refresh(plateau, plateauGraph,jeu.getTourEquipe1(), jeu.getDebutJeu(), equipe1.getListe(), equipe2.getListe());
					
			personnnageSelectionne = null;
			persoSelectionPosition.setLocation(-1, -1);
			choixDeplacementPosition.setLocation(-1, -1);
			presentsEquipe.clear();
			plateauGraph.ajouterSelectionPersos(new ArrayList<Personnage>());
			deplacementValide = false;
			confirmationFinTour.setLocation(-1, -1);
			persoSelec = -1;
			plateauGraph.setPeutVoler(false);
			plateauGraph.setFaitAction(false);
			

			if (jeu.getTourEquipe1()) {
				tempEquipe = equipe1;
			} else {
				tempEquipe = equipe2;
			}
			
			if ( (jeu.getVsOrdi() && jeu.getTourEquipe1()) || !jeu.getVsOrdi()) {
				// ici on clique sur une case d'un de notre personnage
				// tant que le point selectionne est -1,-1 (defaut) et que c'est un point invalide (voir methode), la boucle continue a tourner
				while ( !plateauGraph.getConfirmeSelection() || (!persoSelectionPosition.equals(new Position(-1,-1)) && !bonneSelectionEquipe) ) {
					plateauGraph.clearConsole();
					plateauGraph.print("C'est au tour de l'", jeu.getTourEquipe1());
					if (!bonneSelectionEquipe) {
						plateauGraph.println("Vous n'avez pas selectionnee une case ou vous disposez de personnage");
					}
					if (pasEnergie) {
						plateauGraph.println("Ce personnage n'a plus d'énergie, veuillez séléctionner un autre");
					}
					plateauGraph.ajouterSelectionPersos(tempEquipe.getListe());
					plateauGraph.println("Cliquez sur le personnage que vous voulez deplacer");
					plateauGraph.waitSelectionPerso(5000);
					// on met persoSelection a x,y du clic de la souris
					persoSelectionPosition.setLocation(plateauGraph.getX(), plateauGraph.getY());
					if (!persoSelectionPosition.equals(new Position(-1,-1))) {
						plateauGraph.setConfirmeSelection(true);
					}
					
					// verifie si le joueur a bien selectionne un perso de son equipe seulment si il a clique
					if (jeu.getTourEquipe1() && !persoSelectionPosition.getLocation().equals(new Point(-1, -1))) {
						bonneSelectionEquipe = persoSelectionPosition.pointValide(equipe1.getListe(), plateauGraph);
						pasEnergie = persoSelectionPosition.getEnergieInvalide();
						if (bonneSelectionEquipe && !pasEnergie) {
							presentsEquipe = persoSelectionPosition.getPersosSurPosition(equipe1.getListe());
						} else {
							presentsEquipe = new ArrayList<Personnage>();
						}
					} else if (!jeu.getTourEquipe1() && !persoSelectionPosition.getLocation().equals(new Point(-1, -1))){
						bonneSelectionEquipe = persoSelectionPosition.pointValide(equipe2.getListe(), plateauGraph);
						pasEnergie = persoSelectionPosition.getEnergieInvalide();
						if (bonneSelectionEquipe && !pasEnergie) {
							presentsEquipe = persoSelectionPosition.getPersosSurPosition(equipe2.getListe());
						} else {
							presentsEquipe = new ArrayList<Personnage>();
						}
					}
				}
				
				System.out.println("finished");
				
				// CAS : il y a plusieurs persos sur une case : le debut du jeu
				if ( presentsEquipe.size() > 1) {
					Personnage temp = new Personnage("temp plusieurs", jeu.getTourEquipe1(), 100, new Position(persoSelectionPosition), 0);
					plateauGraph.clearConsole();
					plateauGraph.print("C'est au tour de l'", jeu.getTourEquipe1());
					plateauGraph.println("Plusieurs personnages de votre equipe sont presents sur cette case");
					plateauGraph.println("Veuillez selectionner une de ceux-cis");
					plateauGraph.ajouterSelectionPersos(presentsEquipe);
					setActionsPossibles(temp, equipe1, equipe2, plateau);
					
					while (!plateauGraph.getConfirmeSelection() && !plateauGraph.getAnnulerChoix()) {
						plateauGraph.waitEvent(1000,true, false);
						persoSelec = plateauGraph.getPersoPrecis();
					}
					if (plateauGraph.getAnnulerChoix()) {
						personnnageSelectionne = new Personnage("personne selectionne", true, 0, new Position(-1,-1), -1);
					} else {
						personnnageSelectionne = presentsEquipe.get(persoSelec);
					}
				}
				personnnageSelectionne = tempEquipe.getListe().get(plateauGraph.getPersoPrecis());
				setActionsPossibles(personnnageSelectionne, equipe1, equipe2, plateau);
				System.out.println("Le perso " + personnnageSelectionne.nom + " est à " + personnnageSelectionne.getPos());
				
				plateauGraph.setHighlight(personnnageSelectionne.getPos().x, personnnageSelectionne.getPos().y, Color.CYAN);
				
				while ( (!deplacementValide && !plateauGraph.getFaitAction()) && !plateauGraph.getAnnulerChoix()) {
					plateauGraph.setHighlight(personnnageSelectionne.getPos().x, personnnageSelectionne.getPos().y, Color.CYAN);
					plateauGraph.clearConsole();
					plateauGraph.print("C'est au tour de l'", jeu.getTourEquipe1());
					plateauGraph.println("Vous avez selectionnée : " + personnnageSelectionne.getNom());
					if (!deplacementValide) {
						plateauGraph.recover();
					}
					plateauGraph.waitEvent(5000,false, true);
					// un clic interrompt waitEvent, ensuite, soit il a appuyé sur pieger soit un endroit pour y se deplacer
					if (plateauGraph.veutPieger()) {
						System.out.println("veut pieger " + plateauGraph.veutPieger());
						plateauGraph.setFaitAction(true);
						actions.pieger(personnnageSelectionne, plateau, jeu.getTourEquipe1());
					}
					if (!plateauGraph.getFaitAction()) {
						plateauGraph.println("Cliquez sur la case ou vous voulez qu'il aille");
						choixDeplacementPosition.setLocation(plateauGraph.getX(), plateauGraph.getY());
						System.out.println("choix deplacement pos = " + choixDeplacementPosition);
						
						if (!choixDeplacementPosition.getLocation().equals(new Point(-1, -1))) {
							deplacementValide = plateau.deplacerV2Amorce(choixDeplacementPosition, personnnageSelectionne, plateauGraph, jeu.getTourEquipe1());
							plateauGraph.setFaitAction(deplacementValide);
						}
					}
				}
				plateauGraph.disableAnnuler();
				plateau.updateEnergie(jeu.getTourEquipe1(), equipe1.getListe(), equipe2.getListe(), plateauGraph);
				System.out.println("Le perso " + personnnageSelectionne.nom + " est maintenant à " + personnnageSelectionne.getPos());
				refresh(plateau, plateauGraph,jeu.getTourEquipe1(), jeu.getDebutJeu(), equipe1.getListe(), equipe2.getListe());
				while (confirmationFinTour.equals(new Position(-1, -1)) && !jeu.getEstFini() && !plateauGraph.getAnnulerChoix()) {
					plateauGraph.clearConsole();
					plateauGraph.recover();
					plateauGraph.println("L'", jeu.getTourEquipe1(),"a fini son tour");
					plateauGraph.println("Veuillez cliquez sur la fenetre pour passer a l'equipe suivante");
					plateauGraph.waitEvent(5000, false, false);
					confirmationFinTour.setLocation(plateauGraph.getX(), plateauGraph.getY());
				}
				plateau.retirerMorts(jeu.getTourEquipe1(),equipe1.getListe(),equipe2.getListe());
				if (!plateauGraph.getAnnulerChoix()) {
					jeu.nextRound();
				}
			} else {
				tourOrdinateur();
				plateau.updateEnergie(jeu.getTourEquipe1(), equipe1.getListe(), equipe2.getListe(), plateauGraph);
				refresh(plateau, plateauGraph, jeu.getTourEquipe1(), 2000, jeu.getDebutJeu(), equipe1.getListe(), equipe2.getListe());
				jeu.nextRound();
			}
		}
		plateauGraph.clearConsole();
		plateauGraph.println(jeu.toString());
	}

	/**
	 * Action Possibles dans le jeu en fonction du personnage et de l equipe
	 * @param personnage concernee
	 * @param equipe 1
	 * @param equipe 2
	 * @param le plateau de jeu
	 */
	private void setActionsPossibles(Personnage perso, Equipe equipe1, Equipe equipe2, Ile plateau) {
		plateauGraph.setPeutVoler(actions.tenterVol(perso, plateau));
		plateauGraph.setPeutPieger(actions.peutTenterPiege(perso, plateau));
		plateauGraph.setPeutEchangerClef(actions.peutEchanger(perso, true, equipe1, equipe2, plateau));
		plateauGraph.setPeutEchangerTresor(actions.peutEchanger(perso, false, equipe1, equipe2, plateau));
		
	}

	/**
	 * Gestion de manche d un ordinateur
	 */
	private void tourOrdinateur() {
		Random r = new Random();
		int nSelection = r.nextInt(equipe2.getListe().size());
		Personnage persoChoisi = equipe2.getListe().get(nSelection);
		System.out.println("Le perso " + persoChoisi.nom + " est à " + persoChoisi.getPos());
		Position deplacementPos = new Position(persoChoisi.getPos());
		boolean deplacementValide = false;
		
		int xOuY;
		int deplacement;
		int essaisDeplacements = 0;
		int nSelection2 = nSelection;
		
		while (!deplacementValide) {
			// Si le personnage a tenté de se déplacer 4 fois sans succès on choisit un autre personnage
			if (essaisDeplacements > 3) {
				while (nSelection2 == nSelection) {
					nSelection2 = r.nextInt(equipe2.getListe().size());
				}
				persoChoisi = equipe2.getListe().get(r.nextInt(equipe2.getListe().size()));
			}
			// Si le personnage ne s'est pas encore déplacé ou avec 50% des chances il se deplace selon 50% des chances selon les x ou les y
			if (persoChoisi.getDirectionDeplacement().equals(new Position(-1, -1)) || r.nextInt(2) == 0) {
				xOuY = r.nextInt(2);
				deplacement = (r.nextInt(2) * 2) -1; // Soit -1 ou 1
				if (xOuY == 0) {
					deplacementPos.setLocation(persoChoisi.getPos().additionner(new Position(deplacement, 0)));
					deplacementValide = plateau.deplacerV2Amorce(deplacementPos, persoChoisi, plateauGraph, jeu.getTourEquipe1());
				} else {
					deplacementPos.setLocation(persoChoisi.getPos().additionner(new Position(0, deplacement)));
					deplacementValide = plateau.deplacerV2Amorce(deplacementPos, persoChoisi, plateauGraph, jeu.getTourEquipe1());
				}
				if (!deplacementValide) {
					deplacementPos.setLocation(persoChoisi.getPos());
				}
			} else {
				deplacementValide = plateau.deplacerV2Amorce(persoChoisi.getPos().additionner(persoChoisi.getDirectionDeplacement()) , persoChoisi, plateauGraph, jeu.getTourEquipe1());
				if (deplacementValide) {
					System.out.println("s'est deplace dans la mm direction : " + persoChoisi.getDirectionDeplacement());
				}
			}
			essaisDeplacements++;
		}
		System.out.println("Le perso " + persoChoisi.nom + " est maintenant à " + persoChoisi.getPos());
		plateau.retirerMorts(jeu.getTourEquipe1(),equipe1.getListe(),equipe2.getListe());
	}

	/**
	* Permet de rafraichir l'affichage apres chaque action 
	* @param Plateau de jeu
	* @param Plateau graphique du jeu
	* @param equipe en cour de jeu
	* @param temps restant
	* @param boolean de debut
	* @param liste des personnages de l equipe 1
	* @param liste des personnages de l equipe 2
	*/
	public void refresh(Ile plateau, Plateau plateauGraph, boolean equipeCourante, int waitTime, boolean debut, List<Personnage> equipe1, List<Personnage> equipe2) {
		refresh(plateau, plateauGraph, equipeCourante, debut, equipe1, equipe2);
		try {
			Thread.sleep(waitTime);
		} catch (Exception ie) {
		}
	}
	/**
	 * Rafraichissement du jeu
	 * @param plateau de jeu
	 * @param plateau graphique du jeu
	 * @param equipe en cour de jeu
	 * @param booleen de debut
	 * @param liste des personnages de l equipe 1
	 * @param liste des personnages de l equipe 2
	 */
	public void refresh(Ile plateau, Plateau plateauGraph, boolean equipeCourante, boolean debut, List<Personnage> equipe1, List<Personnage> equipe2) {
		plateauGraph.setJeu(plateau.getImagesCorrespondants(equipeCourante, plateauGraph, equipe1, equipe2), debut);
		plateauGraph.affichage(); // Affichage graphique
	}
}
