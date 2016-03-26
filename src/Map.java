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
		
		/* INITIALISATIONS */				
		plateauGraph.setJeu(plateau.getplateaugraphique());
		
		/* ACTIONS */	
		refresh(plateau,plateauGraph);	
		plateau.deplacer(explo1, 4);
		refresh(plateau,plateauGraph);	
		plateau.deplacer(explo1, 3);
		refresh(plateau,plateauGraph);	
		plateau.deplacer(explo1, 1);
		refresh(plateau,plateauGraph);
		plateau.deplacer(explo1, 2);
		refresh(plateau,plateauGraph);
	
	}
	
	/* permet de rafraichir l'affichage apres chaque action */
	public static void refresh(Ile plateau, Plateau plateauGraph){
		try{Thread.sleep(1000);}catch(Exception ie){}
		plateauGraph.setJeu(plateau.getplateaugraphique());
		plateauGraph.affichage(); // Affichage graphique
		//System.out.println(plateau.toString()); // Affichage textuel
	}
}