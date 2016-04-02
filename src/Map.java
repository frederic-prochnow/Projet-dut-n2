/**
* Importation de class
*/
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import tps.Plateau;
/**
 * Class Map Class de test et application des classes Ile et Parcelle
 * @author TeamJ3
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
		Plateau plateauGraph = new Plateau(img, plateau.getSize());
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
		plateauGraph.setJeu(plateau.getplateaugraphique());

		/* ACTIONS */
		String persoSelection;
		int choixDeplacement = -1;
		while (!jeu.getEstFini(equipe1) && !jeu.getEstFini(equipe2)) {// boucle tant que personne n'a gagne
			choixDeplacement = -1;
			persoSelection = null;
			refresh(plateau, plateauGraph);
			persoSelection = (String) JOptionPane.showInputDialog(null, "Quel personnage voulez-vous deplacer?",
					"choix perso", JOptionPane.INFORMATION_MESSAGE, null, persos, "liste des personnages");
			if (persoSelection != null) {
				if (jeu.getTourEquipe()) { // equipe 1
					if (persoSelection.equals("explorateur")) { // +la condidtion de l'equipe
						choixDeplacement = explo1.choixDeplacement();
						plateau.deplacer(explo1, choixDeplacement);
					} else if (persoSelection.equals("voleur")) {
						choixDeplacement = voleur1.choixDeplacement();
						plateau.deplacer(voleur1, choixDeplacement);
					} // creer les else if avec les autres persos quand ils seront actifs
				} else {
					if (persoSelection.equals("explorateur")) { // +la condidtion de l'equipe
						choixDeplacement = explo2.choixDeplacement();
						plateau.deplacer(explo2, choixDeplacement);
					} else if (persoSelection.equals("voleur")) {
						choixDeplacement = voleur2.choixDeplacement();
						plateau.deplacer(voleur2, choixDeplacement);
					} // creer les else if avec les autres persos quand ils seront actifs
				}

			}
			if (choixDeplacement != -1) { // Sinon le tour c'est bien pass� on // continue
				jeu.nextRound();
				refresh(plateau, plateauGraph);
				System.out.println(jeu.toString());
			}
			
		}
	}

	/**
	 * permet de rafraichir l'affichage apres chaque action
	 * @params Ile Plateau
	 * @params Plateau plateauGraph
	 */
	public static void refresh(Ile plateau, Plateau plateauGraph) {
		try {
			Thread.sleep(250);
		} catch (Exception ie) {
		}
		plateauGraph.setJeu(plateau.getplateaugraphique());
		plateauGraph.affichage(); // Affichage graphique
		// System.out.println(plateau.toString()); // Affichage textuel
	}
}
