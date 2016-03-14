package jeu;

public class Map {

	public static void main(String [] args) {
		Ile plateau = new Ile();
		String[] img = {"1"};
		Plateau plateauGraph = new Plateau(img,plateau.getSize());
		plateau.initialiser(20);
		plateauGraph.set(plateau.getplateaugraphique());
		plateauGraph.affichage();
		System.out.println(plateau.toString());
	}
}