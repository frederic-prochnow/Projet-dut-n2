package jeu;

public class Map {

	public static void main(String [] args) {
		Ile plateau = new Ile();
		plateau.initialiser();
		System.out.println(plateau.toString());
	}

}