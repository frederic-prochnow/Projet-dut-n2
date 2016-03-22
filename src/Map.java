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
		Explorateur explo1 = new Explorateur("explorateur",2,100,plateau.getPos(2));
		
		/* ACTIONS */				
		plateauGraph.setJeu(plateau.getplateaugraphique());
		System.out.println(explo1.getPos().toString());
		
		/* AFFICHAGE */
		plateauGraph.affichage(); // Affichage graphique
		//System.out.println(plateau.toString()); // Affichage textuel
	}
}