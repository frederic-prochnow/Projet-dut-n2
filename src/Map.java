import javax.swing.JOptionPane;

import tps.Plateau;
/**
 * Class Map
 * Class de test et application des classes Ile et Parcelle
 * @author TeamJ3
 *
 */
public class Map {
	/**
	 * Main de test et applications des classes Ile et Parcelle
	 */
	public static void main(String [] args) {
		
		/* DECLARATION DES VARIABLES */		
		String[] img = {"images/sable.png",
				"images/1.explorateur.png",
				"images/1.piegeur.png",
				"images/1.navire.png",
				"images/2.explorateur.png",
				"images/2.piegeur.png",
				"images/2.navire.png",
				"images/rocher.png",
				"images/coffre.png",
				"images/cle.jpg",
				"images/mer.png"};
		Ile plateau = new Ile();
		Plateau plateauGraph = new Plateau(img,plateau.getSize());
		
		/* PERSONNAGES */
		Explorateur explo1 = new Explorateur("explorateur",1,100,plateau.getPos(2),0);
		Personnage voleur1 = new Voleur("voleur", 1, 100, plateau.getPos(2), 1);
		Object[] persos = { "explorateur", "voleur", "guerrier", "piegeur" };
		
		/* INITIALISATIONS */				
		plateauGraph.setJeu(plateau.getplateaugraphique());
		
		/* ACTIONS */			
		int nb=0;
		while (nb != 40) {// boucle tant que personne n'a gagn�
			refresh(plateau, plateauGraph);
			String persoSelection = JOptionPane.showInputDialog(null, "Quel personnage voulez-vous deplacer?",
					"choix perso", JOptionPane.INFORMATION_MESSAGE, null, persos, "liste des personnages").toString();
			if (persoSelection
					.equals("explorateur") /* +la condidtion de l'equipe */) {
				plateau.deplacer(explo1, explo1.choixDeplacement(1));
				refresh(plateau, plateauGraph);
			} else if (persoSelection.equals("voleur")) {
				plateau.deplacer(voleur1, voleur1.choixDeplacement(2));
				refresh(plateau, plateauGraph);
			} // creer les else if avec les autres persos quand ils seront actifs
		nb++;
		}
		
	
	}
	
	/* permet de rafraichir l'affichage apres chaque action */
	public static void refresh(Ile plateau, Plateau plateauGraph){
		try{Thread.sleep(1000);}catch(Exception ie){}
		plateauGraph.setJeu(plateau.getplateaugraphique());
		plateauGraph.affichage(); // Affichage graphique
		//System.out.println(plateau.toString()); // Affichage textuel
	}
}