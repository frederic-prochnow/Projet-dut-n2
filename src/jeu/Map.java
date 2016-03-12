package jeu;

public class Map {

	public static void main(String [] args) {
		Ile plateau = new Ile();
		plateau.initialiser(5);
		System.out.println(plateau.toString());
	}

}