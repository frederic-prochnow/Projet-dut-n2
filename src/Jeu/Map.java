package Jeu;
/**
 * class Map
 * @author TeamJ3
 * Projet
 */
public class Map {

	/**
	 * Methode main
	 */
	public static void main(String [] args) {
		Ile plateau = new Ile();
		plateau.initialiser();
		System.out.println(plateau.toString());
	}

}