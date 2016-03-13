package jeuaaa;

public class Map {

	public static void main(String [] args) {
		Ile plateau = new Ile();
		plateau.initialiser(20);
		System.out.println(plateau.toString());
	}

}