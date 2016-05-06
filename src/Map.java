
/**
 * Importation
 */
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

	Equipe equipe1 = new Equipe();
	Equipe equipe2 = new Equipe();
	int totalE1 = 0;
	int totalE2 = 0;
	Ile plateau;
	Plateau plateauGraph;
	GestionJeu jeu;
	boolean faitAction;
	Actions actions;
	Equipe tempEquipe;

	/* ACTIONS */

	Position persoSelectionPosition = new Position(-1, -1);
	Position choixDeplacementPosition = new Position(-1, -1);
	List<Personnage> presentsEquipe = new ArrayList<>();
	Personnage personnnageSelectionne;
	Position confirmationFinTour = new Position(-1, -1);
	int persoSelec = -1;

	boolean bonneSelectionEquipePreced = true;
	boolean bonneSelectionEquipe = false;
	boolean deplacementValide = false;
	boolean changerEquipe = false;

	/**
	 * Fonction pour jouer avec le jeu
	 */
	public void jouer() {

		/* DECLARATION DES VARIABLES */
		String[] img = { "images/grass.jpg", "images/1.explorateur.png", "images/1.voleur.png", "images/1.navire.png", "images/2.explorateur.png", "images/2.voleur.png", "images/2.navire.png",
				"images/rocher.png", "images/coffre.png", "images/cle.jpg", "images/mer4.png", "images/1.guerrier.png", "images/1.piegeur.png", "images/2.guerrier.png", "images/2.piegeur.png",
				"images/pieger.png", "images/cle_prise.jpg", "images/coffre_ouvert.png", "images/coffre_ouvert_tresor.png", "images/black.png" };
		Menu menu = new Menu();
		menu.affichage();
		while (!menu.getConfirme()) {
			menu.waitValidation(10000);
		}
		plateau = new Ile(menu.getTaille(), menu.getRochers());
		plateauGraph = new Plateau(img, plateau.getSize(), true);
		jeu = new GestionJeu(true, menu.getVersusOrdi());
		actions = new Actions();

		// Gestion de la liste d'explorateur de l'équipe 1
		Explorateur[] listExplo1 = new Explorateur[menu.getNbPersos(0)];
		int i = 0;
		for (Explorateur e : listExplo1) {
			i++;
			e = new Explorateur("explorateur1-" + i, true, 100, plateau.getPos(2), 0, 3);
			equipe1.getListe().add(e);
			totalE1++;
		}

		// Gestion de la liste de voleur de l'equipe 1
		Personnage[] listVoleur1 = new Voleur[menu.getNbPersos(1)];
		i = 0;
		for (Personnage e : listVoleur1) {
			i++;
			e = new Voleur("voleur1-" + i, true, 100, plateau.getPos(2), 1, 3);
			equipe1.getListe().add(e);
			totalE1++;
		}

		// Gestion de la liste de guerrier de l'equipe 1
		Personnage[] listGuerrier1 = new Guerrier[menu.getNbPersos(2)];
		i = 0;
		for (Personnage e : listGuerrier1) {
			i++;
			e = new Guerrier("guerrier1-" + i, true, 100, plateau.getPos(2), 10, 2);
			equipe1.getListe().add(e);
			totalE1++;
		}

		// Gestion de la liste de piegeur de l'equipe 1
		Personnage[] listPiegeur1 = new Piegeur[menu.getNbPersos(3)];
		i = 0;
		for (Personnage e : listPiegeur1) {
			i++;
			e = new Piegeur("piegeur1-" + i, true, 100, plateau.getPos(2), 11, 2);
			equipe1.getListe().add(e);
			totalE1++;
		}

		// Gestion de la liste d'explorateur de l'équipe 2
		Explorateur[] listExplo2 = new Explorateur[menu.getNbPersos(4)];
		i = 0;
		for (Explorateur e : listExplo2) {
			i++;
			e = new Explorateur("explorateur2-" + i, false, 100, plateau.getPos(5), 3, 3);
			equipe2.getListe().add(e);
			totalE2++;
		}

		// gestion de la liste de voleur de l'équipe 2
		Personnage[] listVoleur2 = new Voleur[menu.getNbPersos(5)];
		i = 0;
		for (Personnage e : listVoleur2) {
			i++;
			e = new Voleur("voleur2-" + i, false, 100, plateau.getPos(5), 4, 3);
			equipe2.getListe().add(e);
			totalE2++;
		}

		// Gestion de la liste de guerrier de l'equipe 2
		Personnage[] listGuerrier2 = new Guerrier[menu.getNbPersos(6)];
		i = 0;
		for (Personnage e : listGuerrier2) {
			i++;
			e = new Guerrier("guerrier2-" + i, false, 100, plateau.getPos(5), 12, 2);
			equipe2.getListe().add(e);
			totalE2++;
		}

		// Gestion de la liste de piegeur de l'equipe 2
		Personnage[] listPiegeur2 = new Piegeur[menu.getNbPersos(7)];
		i = 0;
		for (Personnage e : listPiegeur2) {
			i++;
			e = new Piegeur("piegeur2-" + i, false, 100, plateau.getPos(5), 13, 2);
			equipe2.getListe().add(e);
			totalE2++;
		}

		plateau.getNavire(true).addPersos(totalE1);
		
		/* INITIALISATIONS */
		plateauGraph.setJeu(plateau.getImagesCorrespondants(jeu.getTourEquipe1(), plateauGraph, equipe1.getListe(), equipe2.getListe()), jeu.getDebutJeu());

		if (menu.getChoixManuel()) {
			
			boolean placementE1 = true;

			while (!equipe1.finPlacement() && !equipe2.finPlacement()) {
				
				if (placementE1) {
					tempEquipe = equipe1;
				} else {
					tempEquipe = equipe2;
				}
				
				plateauGraph.setJeu(plateau.getPlacementManuel(tempEquipe.getListe()), true);
				changerEquipe = false;
				bonneSelectionEquipe = false;
				personnnageSelectionne = null;
				persoSelectionPosition.setLocation(-1, -1);
				presentsEquipe.clear();
				plateauGraph.ajouterSelectionPersos(new ArrayList<Personnage>());
				deplacementValide = false;
				confirmationFinTour.setLocation(-1, -1);
				persoSelec = -1;
				plateauGraph.setConfirmeSelection(false);
				plateauGraph.setTempPersoSelec(null);
				plateauGraph.setConfirmeFinTour(false);
				plateauGraph.setPasser(false);
				plateauGraph.setAttendFinTour(false);

				// ici on clique sur une case d'un de notre personnage ou le selectionne directement au clavier
				// CAS SOURIS : on boucle tant que le point selectionne est -1,-1 (defaut) et que c'est un point invalide pour l'équipe
				while (!bonneSelectionEquipe && !plateauGraph.getPasser()) {
					plateauGraph.clearConsole();
					plateauGraph.print("C'est au tour de l'", jeu.getTourEquipe1());

					if (plateauGraph.getTempPersoSelec() != null) {
						plateauGraph.resetHighlight(plateauGraph.getTempPersoSelec().getPos());
					}
					// ici on ajoute tous les persos de l'équipe pour selectionner au clavier
					plateauGraph.ajouterSelectionPersos(tempEquipe.getListe());
					plateauGraph.println("Cliquez sur le personnage que vous voulez deplacer");
					plateauGraph.setFaitAction(false);
					plateauGraph.setAttendFinTour(false);
					plateauGraph.setConfirmeSelection(false);
					plateauGraph.setConfirmeSelectionPane(false);
					persoSelectionPosition.setLocation(-1, -1);
					plateauGraph.waitSelectionPerso(5000);

					// si on a confirmé sa séléction par le clavier, la selection est forcément correcte ou clique dans persopane
					if (plateauGraph.getConfirmeSelection() || plateauGraph.getConfirmeSelectionPane()) {
						bonneSelectionEquipe = true;
					} else if (!plateauGraph.getConfirmeSelection() && !plateauGraph.getConfirmeSelectionPane()) {
						// on met persoSelection a x,y du clic de la souris
						persoSelectionPosition.setLocation(plateauGraph.getX(), plateauGraph.getY());
					}

					// verifie si le joueur a bien selectionne un perso de son equipe SEULEMENT s'il a cliqué
					if (!persoSelectionPosition.getNulle()) {
						plateauGraph.setConfirmeSelection(true);
						bonneSelectionEquipe = persoSelectionPosition.pointValide(tempEquipe.getListe());
						if (bonneSelectionEquipe) {
							presentsEquipe = persoSelectionPosition.getPersosSurPosition(tempEquipe.getListe());
						} else {
							presentsEquipe = new ArrayList<Personnage>();
						}
						plateauGraph.ajouterSelectionPersos(presentsEquipe);
					}
				}
				
				if (plateauGraph.getAnnulerChoix() || plateauGraph.getPasser()) {
					personnnageSelectionne = new Personnage("personne selectionne", true, 0, new Position(-1, -1), -1, 0);
				}

				// CAS : il y a plusieurs persos sur une case
				// ceci ne s'éxécute que si on a cliqué sur une position sinon la selection
				// de personnage est deja fait si le clavier est utilisé
				if (presentsEquipe.size() > 1 && !plateauGraph.getAnnulerChoix() && !plateauGraph.getPasser()) {
					Personnage temp = new Personnage("temp plusieurs", jeu.getTourEquipe1(), 100, new Position(persoSelectionPosition), 0, 0);
					plateauGraph.clearConsole();
					plateauGraph.print("C'est au tour de l'", jeu.getTourEquipe1());
					plateauGraph.println("Plusieurs personnages de votre equipe sont presents sur cette case");
					plateauGraph.println("Veuillez selectionner une de ceux-cis");
					plateauGraph.ajouterSelectionPersos(presentsEquipe);
					plateauGraph.setConfirmeSelectionPane(false);
					System.out.println("temp pos = " + temp.getPos());
					setActionsPossibles(temp, tempEquipe, plateau);

					while (!plateauGraph.getConfirmeSelectionPane() && !plateauGraph.getAnnulerChoix() && !plateauGraph.getPasser()) {
						plateauGraph.waitEvent(1000, true);
						persoSelec = plateauGraph.getPersoPrecis();
					}
					if (plateauGraph.getAnnulerChoix()) {
						personnnageSelectionne = new Personnage("personne selectionne", true, 0, new Position(-1, -1), -1, 0);
					} else {
						personnnageSelectionne = presentsEquipe.get(persoSelec);
					}
				} else {
					if (!plateauGraph.getAnnulerChoix() && !plateauGraph.getPasser()) {
						// personnageSelectionne a partir du clic sur le plateau
						if (!persoSelectionPosition.equals(new Position(-1, -1))) {
							personnnageSelectionne = persoSelectionPosition.getPersosSurPosition(tempEquipe.getListe()).get(0);
						} else {
							// personnageSelectionne a partir du clic dans PersoPane ou d'une selection au clavier
							personnnageSelectionne = tempEquipe.getListe().get(plateauGraph.getPersoPrecis());
							persoSelectionPosition.setLocation(personnnageSelectionne.getPos());
						}
						// pas d'actions mais besoin du bouton annuler
						plateauGraph.actionsSiListeUnique();
					}
				}

				if (!personnnageSelectionne.getPos().getNulle() && !plateauGraph.getAnnulerChoix() && !plateauGraph.getPasser()) {
					System.out.println("Le perso " + personnnageSelectionne.nom + " est à " + personnnageSelectionne.getPos());
					plateauGraph.setHighlight(personnnageSelectionne.getPos().x, personnnageSelectionne.getPos().y, Color.CYAN);
				}

				while (!personnnageSelectionne.finMouvement() && !plateauGraph.getAnnulerChoix() && !plateauGraph.getPasser()) {

					while ((!deplacementValide && plateauGraph.getDirectionDeplacementNulle()) && !plateauGraph.getAnnulerChoix()) {

						plateauGraph.setHighlight(personnnageSelectionne.getPos().x, personnnageSelectionne.getPos().y, Color.CYAN);
						plateauGraph.setVeutDeplacer(false);
						plateauGraph.clearConsole();
						plateauGraph.print("C'est au tour de l'", jeu.getTourEquipe1());
						plateauGraph.println("Vous avez selectionnée : " + personnnageSelectionne.getNom());
						if (!deplacementValide) {
							plateauGraph.recover();
						}
						plateauGraph.waitDeplacementOuAction(5000);

						plateauGraph.println("Cliquez sur la case où vous voulez qu'il se déplace");
						choixDeplacementPosition.setLocation(plateauGraph.getX(), plateauGraph.getY());

						// choixDeplacementPosition est mis directment par le clic de la souris
						if (!choixDeplacementPosition.getNulle()) {
							plateauGraph.setDirectionDeplacement(choixDeplacementPosition.differenceCoordonnees(persoSelectionPosition));
							// choixDeplacementPosition mis par la direction du clavier
						} else if (!plateauGraph.getDirectionDeplacementNulle()) {
							choixDeplacementPosition.setLocation(plateauGraph.getDirectionDeplacement().additionner(personnnageSelectionne.getPos()));
						}
						// deplacement a partir de choixDeplacementPosition
						if (!choixDeplacementPosition.getNulle()) {
							deplacementValide = plateau.placer(choixDeplacementPosition, personnnageSelectionne, plateauGraph, true);
							plateauGraph.setFaitAction(deplacementValide);
						}
					}

					plateauGraph.refreshPersoPanel(plateauGraph.getPersoPrecis());
					deplacementValide = false;
					choixDeplacementPosition.setLocation(-1, -1);
					plateauGraph.setDirectionDeplacement(new Position(-1, -1));
					System.out.println("Le perso " + personnnageSelectionne.nom + " est maintenant à " + personnnageSelectionne.getPos());
					plateauGraph.setJeu(plateau.getPlacementManuel(tempEquipe.getListe()), true);
				}

				plateauGraph.disableAnnulerEtActions();
				bonneSelectionEquipe = false;
				plateauGraph.setAttendFinTour(true);

				while ((confirmationFinTour.equals(new Position(-1, -1)) && !plateauGraph.getConfirmeFinTour()) && !jeu.getEstFini() && !plateauGraph.getAnnulerChoix() && plateauGraph.getPasser()) {
					plateauGraph.clearConsole();
					plateauGraph.recover();
					plateauGraph.println("L'", jeu.getTourEquipe1(), "a fini son tour");
					plateauGraph.println("Veuillez cliquez sur la fenetre pour passer a l'equipe suivante");
					plateauGraph.waitEvent(5000, false);
					confirmationFinTour.setLocation(plateauGraph.getX(), plateauGraph.getY());
				}

				if ((!plateauGraph.getAnnulerChoix() && tempEquipe.finTour()) || plateauGraph.getPasser()) {
					placementE1 = !placementE1;
				}
			}
		}

		while (!jeu.getEstFini(equipe1.getListe()) && !jeu.getEstFini(equipe2.getListe())) {// boucle tant que personne n'a gagne

			/* Initialisations du plateau en fonction de l'équipe et des selections divers */

			if (!jeu.getDebutJeu() && changerEquipe) {
				plateauGraph.println("A l'equipe suivante");
				System.out.println("A l'equipe suivante");
			} else {
				jeu.setDebutJeu(false);
			}

			if (jeu.getTourEquipe1()) {
				tempEquipe = equipe1;
			} else {
				tempEquipe = equipe2;
			}
			plateauGraph.clearHighlight();
			plateauGraph.affichage();
			plateau.bonusEnergie(tempEquipe.getListe(), plateauGraph);
			plateau.updateEnergie(tempEquipe.getListe(), plateauGraph);
			refresh(plateau, plateauGraph, jeu.getTourEquipe1(), jeu.getDebutJeu(), equipe1.getListe(), equipe2.getListe());

			changerEquipe = false;
			bonneSelectionEquipe = false;
			personnnageSelectionne = null;
			persoSelectionPosition.setLocation(-1, -1);
			presentsEquipe.clear();
			plateauGraph.ajouterSelectionPersos(new ArrayList<Personnage>());
			deplacementValide = false;
			confirmationFinTour.setLocation(-1, -1);
			persoSelec = -1;
			plateauGraph.setConfirmeSelection(false);
			plateauGraph.setTempPersoSelec(null);
			plateauGraph.setConfirmeFinTour(false);
			plateauGraph.setPasser(false);
			setActionsPossibles(plateauGraph.getTempPersoSelec(), tempEquipe, plateau);
			plateauGraph.setAttendFinTour(false);

			if ((jeu.getVsOrdi() && jeu.getTourEquipe1()) || !jeu.getVsOrdi()) {

				// ici on clique sur une case d'un de notre personnage ou le selectionne directement au clavier
				// CAS SOURIS : on boucle tant que le point selectionne est -1,-1 (defaut) et que c'est un point invalide pour l'équipe
				while (!bonneSelectionEquipe && !plateauGraph.getPasser()) {
					plateauGraph.clearConsole();
					plateauGraph.print("C'est au tour de l'", jeu.getTourEquipe1());
					if (!bonneSelectionEquipePreced) {
						plateauGraph.println("Vous n'avez pas selectionnee une case ou vous disposez de personnage");
					}
					if (plateauGraph.getTempPersoSelec() != null) {
						plateauGraph.resetHighlight(plateauGraph.getTempPersoSelec().getPos());
					}
					// ici on ajoute tous les persos de l'équipe pour selectionner au clavier
					plateauGraph.ajouterSelectionPersos(tempEquipe.getListe());
					plateauGraph.println("Cliquez sur le personnage que vous voulez deplacer");
					plateauGraph.setFaitAction(false);
					plateauGraph.setAttendFinTour(false);
					plateauGraph.setConfirmeSelection(false);
					plateauGraph.setConfirmeSelectionPane(false);
					persoSelectionPosition.setLocation(-1, -1);
					plateauGraph.waitSelectionPerso(5000);

					// si on a confirmé sa séléction par le clavier, la selection est forcément correcte ou clique dans persopane
					if (plateauGraph.getConfirmeSelection() || plateauGraph.getConfirmeSelectionPane()) {
						bonneSelectionEquipe = true;
					} else if (!plateauGraph.getConfirmeSelection() && !plateauGraph.getConfirmeSelectionPane()) {
						// on met persoSelection a x,y du clic de la souris
						persoSelectionPosition.setLocation(plateauGraph.getX(), plateauGraph.getY());
					}

					// verifie si le joueur a bien selectionne un perso de son equipe SEULEMENT s'il a cliqué
					if (!persoSelectionPosition.getNulle()) {
						plateauGraph.setConfirmeSelection(true);
						bonneSelectionEquipe = persoSelectionPosition.pointValide(tempEquipe.getListe());
						if (bonneSelectionEquipe) {
							presentsEquipe = persoSelectionPosition.getPersosSurPosition(tempEquipe.getListe());
						} else {
							presentsEquipe = new ArrayList<Personnage>();
						}
						plateauGraph.ajouterSelectionPersos(presentsEquipe);
					}
					bonneSelectionEquipePreced = bonneSelectionEquipe;
				}
				if (plateauGraph.getAnnulerChoix() || plateauGraph.getPasser()) {
					personnnageSelectionne = new Personnage("personne selectionne", true, 0, new Position(-1, -1), -1, 0);
					bonneSelectionEquipePreced = true;
				}

				// CAS : il y a plusieurs persos sur une case
				// ceci ne s'éxécute que si on a cliqué sur une position sinon la selection
				// de personnage est deja fait si le clavier est utilisé
				if (presentsEquipe.size() > 1 && !plateauGraph.getAnnulerChoix() && !plateauGraph.getPasser()) {
					Personnage temp = new Personnage("temp plusieurs", jeu.getTourEquipe1(), 100, new Position(persoSelectionPosition), 0, 0);
					plateauGraph.clearConsole();
					plateauGraph.print("C'est au tour de l'", jeu.getTourEquipe1());
					plateauGraph.println("Plusieurs personnages de votre equipe sont presents sur cette case");
					plateauGraph.println("Veuillez selectionner une de ceux-cis");
					plateauGraph.ajouterSelectionPersos(presentsEquipe);
					plateauGraph.setConfirmeSelectionPane(false);
					System.out.println("temp pos = " + temp.getPos());
					setActionsPossibles(temp, tempEquipe, plateau);

					while (!plateauGraph.getConfirmeSelectionPane() && !plateauGraph.getAnnulerChoix() && !plateauGraph.getPasser()) {
						plateauGraph.waitEvent(1000, true);
						persoSelec = plateauGraph.getPersoPrecis();
					}
					if (plateauGraph.getAnnulerChoix()) {
						personnnageSelectionne = new Personnage("personne selectionne", true, 0, new Position(-1, -1), -1, 0);
						bonneSelectionEquipePreced = true;
					} else {
						personnnageSelectionne = presentsEquipe.get(persoSelec);
					}
				} else {
					if (!plateauGraph.getAnnulerChoix() && !plateauGraph.getPasser()) {
						// personnageSelectionne a partir du clic sur le plateau
						if (!persoSelectionPosition.equals(new Position(-1, -1))) {
							personnnageSelectionne = persoSelectionPosition.getPersosSurPosition(tempEquipe.getListe()).get(0);
						} else {
							// personnageSelectionne a partir du clic dans PersoPane ou d'une selection au clavier
							personnnageSelectionne = tempEquipe.getListe().get(plateauGraph.getPersoPrecis());
							persoSelectionPosition.setLocation(personnnageSelectionne.getPos());
						}
						setActionsPossibles(personnnageSelectionne, tempEquipe, plateau);
						plateauGraph.setDejaFaits(false);
						plateauGraph.actionsSiListeUnique();
					}
				}

				if (!personnnageSelectionne.getPos().getNulle() && !plateauGraph.getAnnulerChoix() && !plateauGraph.getPasser()) {
					System.out.println("Le perso " + personnnageSelectionne.nom + " est à " + personnnageSelectionne.getPos());
					plateauGraph.setHighlight(personnnageSelectionne.getPos().x, personnnageSelectionne.getPos().y, Color.CYAN);
				}

				while (!personnnageSelectionne.finMouvement() && !plateauGraph.getAnnulerChoix() && !plateauGraph.getPasser()) {

					while ((!deplacementValide || !plateauGraph.getFaitAction() && plateauGraph.getDirectionDeplacementNulle()) && !plateauGraph.getAnnulerChoix()) {
						plateauGraph.setHighlight(personnnageSelectionne.getPos().x, personnnageSelectionne.getPos().y, Color.CYAN);
						plateauGraph.setVeutDeplacer(false);
						plateauGraph.clearConsole();
						plateauGraph.print("C'est au tour de l'", jeu.getTourEquipe1());
						plateauGraph.println("Vous avez selectionnée : " + personnnageSelectionne.getNom());
						if (!deplacementValide) {
							plateauGraph.recover();
						}
						plateauGraph.waitDeplacementOuAction(5000);
						// un clic interrompt waitEvent, ensuite, soit il a appuyé sur pieger soit un endroit pour y se deplacer
						if (plateauGraph.getClicAction()) {
							executerAction();
						}
						if (!plateauGraph.getFaitAction()) {
							plateauGraph.println("Cliquez sur la case où vous voulez qu'il se déplace");
							choixDeplacementPosition.setLocation(plateauGraph.getX(), plateauGraph.getY());

							// choixDeplacementPosition est mis directment par le clic de la souris
							if (!choixDeplacementPosition.getNulle()) {
								plateauGraph.setDirectionDeplacement(choixDeplacementPosition.differenceCoordonnees(persoSelectionPosition));
								// choixDeplacementPosition mis par la direction du clavier
							} else if (!plateauGraph.getDirectionDeplacementNulle()) {
								choixDeplacementPosition.setLocation(plateauGraph.getDirectionDeplacement().additionner(personnnageSelectionne.getPos()));
							}
							// deplacement a partir de choixDeplacementPosition
							if (!choixDeplacementPosition.getNulle()) {
								deplacementValide = plateau.deplacerV2Amorce(choixDeplacementPosition, personnnageSelectionne, plateauGraph, jeu.getTourEquipe1());
								plateauGraph.setFaitAction(deplacementValide);
							}
						}
					}
					if (personnnageSelectionne.getPointsMouvement() == personnnageSelectionne.getMaxPointsMouvement() - 1) {
						personnnageSelectionne.setAJoue(deplacementValide);
					}
					plateauGraph.refreshPersoPanel(plateauGraph.getPersoPrecis());
					plateauGraph.setFaitAction(false);
					deplacementValide = false;
					choixDeplacementPosition.setLocation(-1, -1);
					plateauGraph.setDirectionDeplacement(new Position(-1, -1));
					plateau.updateEnergie(tempEquipe.getListe(), plateauGraph);
					System.out.println("Le perso " + personnnageSelectionne.nom + " est maintenant à " + personnnageSelectionne.getPos());
					refresh(plateau, plateauGraph, jeu.getTourEquipe1(), jeu.getDebutJeu(), equipe1.getListe(), equipe2.getListe());
				}

				plateauGraph.disableAnnulerEtActions();
				bonneSelectionEquipe = false;
				plateauGraph.setAttendFinTour(true);

				while ((confirmationFinTour.equals(new Position(-1, -1)) && !plateauGraph.getConfirmeFinTour()) && !jeu.getEstFini() && !plateauGraph.getAnnulerChoix() && plateauGraph.getPasser()) {
					plateauGraph.clearConsole();
					plateauGraph.recover();
					plateauGraph.println("L'", jeu.getTourEquipe1(), "a fini son tour");
					plateauGraph.println("Veuillez cliquez sur la fenetre pour passer a l'equipe suivante");
					plateauGraph.waitEvent(5000, false);
					confirmationFinTour.setLocation(plateauGraph.getX(), plateauGraph.getY());
					tempEquipe.finPiege();
				}
				plateau.retirerMorts(tempEquipe.getListe());

				if ((!plateauGraph.getAnnulerChoix() && tempEquipe.finTour()) || plateauGraph.getPasser()) {
					jeu.nextRound();
					tempEquipe.resetFinTour();
					changerEquipe = true;
				}

			} else {
				tourOrdinateur();
				plateau.updateEnergie(tempEquipe.getListe(), plateauGraph);
				refresh(plateau, plateauGraph, jeu.getTourEquipe1(), 2000, jeu.getDebutJeu(), equipe1.getListe(), equipe2.getListe());
				jeu.nextRound();
			}
		}
		plateauGraph.clearConsole();
		plateauGraph.println(jeu.toString());
	}

	private void executerAction() {
		if (plateauGraph.veutPieger()) {
			actions.pieger(personnnageSelectionne, plateau, jeu.getTourEquipe1());
		} else if (plateauGraph.veutAttaquer()) {
			actions.attaquer(personnnageSelectionne, plateauGraph, equipe1, equipe2);
		} else if (plateauGraph.veutVoler()) {
			actions.voler(personnnageSelectionne, plateauGraph, equipe1, equipe2);
		} else if (plateauGraph.getVeutEchangerClef()) {
			actions.echangerClef(personnnageSelectionne, tempEquipe, plateauGraph);
		} else if (plateauGraph.getVeutEchangerTresor()) {
			actions.echangerTresor(personnnageSelectionne, tempEquipe, plateauGraph);
		}

		plateauGraph.setFaitAction(true);
		deplacementValide = true;
	}

	/**
	 * Action Possibles dans le jeu en fonction du personnage et de l equipe
	 * 
	 * @param personnage
	 *            concernee
	 * @param equipe
	 *            1
	 * @param equipe
	 *            2
	 * @param le
	 *            plateau de jeu
	 */
	private void setActionsPossibles(Personnage perso, Equipe tempEquipe, Ile plateau) {
		if (perso != null) {
			plateauGraph.setPeutVoler(actions.peutVoler(perso, plateau));
			plateauGraph.setPeutPieger(actions.peutTenterPiege(perso, plateau));
			plateauGraph.setPeutEchangerClef(actions.peutEchanger(perso, true, tempEquipe, plateau));
			plateauGraph.setPeutEchangerTresor(actions.peutEchanger(perso, false, tempEquipe, plateau));
			plateauGraph.setPeutAttaquer(actions.peutAttaquer(perso, plateau));
			plateauGraph.setDetientClef(perso.getDetientClef());
			plateauGraph.setDetientCoffre(perso.getDetientTresor());
		} else {
			plateauGraph.setPeutVoler(false);
			plateauGraph.setPeutPieger(false);
			plateauGraph.setPeutEchangerClef(false);
			plateauGraph.setPeutEchangerTresor(false);
			plateauGraph.setPeutAttaquer(false);
			plateauGraph.setDetientClef(false);
			plateauGraph.setDetientCoffre(false);
		}
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
				deplacement = (r.nextInt(2) * 2) - 1; // Soit -1 ou 1
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
				deplacementValide = plateau.deplacerV2Amorce(persoChoisi.getPos().additionner(persoChoisi.getDirectionDeplacement()), persoChoisi, plateauGraph, jeu.getTourEquipe1());
				if (deplacementValide) {
					System.out.println("s'est deplace dans la mm direction : " + persoChoisi.getDirectionDeplacement());
				}
			}
			essaisDeplacements++;
		}
		System.out.println("Le perso " + persoChoisi.nom + " est maintenant à " + persoChoisi.getPos());
		plateau.retirerMorts(tempEquipe.getListe());
	}

	/**
	 * Permet de rafraichir l'affichage apres chaque action
	 * 
	 * @param Plateau
	 *            de jeu
	 * @param Plateau
	 *            graphique du jeu
	 * @param equipe
	 *            en cour de jeu
	 * @param temps
	 *            restant
	 * @param boolean
	 *            de debut
	 * @param liste
	 *            des personnages de l equipe 1
	 * @param liste
	 *            des personnages de l equipe 2
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
	 * 
	 * @param plateau
	 *            de jeu
	 * @param plateau
	 *            graphique du jeu
	 * @param equipe
	 *            en cour de jeu
	 * @param booleen
	 *            de debut
	 * @param liste
	 *            des personnages de l equipe 1
	 * @param liste
	 *            des personnages de l equipe 2
	 */
	public void refresh(Ile plateau, Plateau plateauGraph, boolean equipeCourante, boolean debut, List<Personnage> equipe1, List<Personnage> equipe2) {
		plateauGraph.setJeu(plateau.getImagesCorrespondants(equipeCourante, plateauGraph, equipe1, equipe2), debut);
		plateauGraph.affichage(); // Affichage graphique
	}
}
