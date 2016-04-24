/**
 * Importation
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * Class Equipe
 * Classe de gestion des Equipes
 * @author Team J3
 */
public class Equipe {
	
	/**
	 * Attribut
	 */
	private List<Personnage> liste;
	
	/**
	 * Constructeur de classe
	 */
	public Equipe() {
		liste = new ArrayList<Personnage>();
	}
	
	/**
	 * Recupere la liste de personnage
	 * @return Liste de personnage
	 */
	public List<Personnage> getListe() {
		return this.liste;
	}
	
	/**
	 * Recupere le personnage a une position donnee
	 * @param x abscisse
	 * @param y ordonnee
	 * @return personnage concernee
	 */
	public Personnage getPersoSurPosition(int x, int y) {
		Personnage temp;
		for (Iterator<Personnage> perso = liste.iterator();perso.hasNext();) {
			temp = perso.next();
			if (temp.getPos().equals(new Position(x, y))) {
				return temp;
			}
		}
		return new Personnage("existe pas", true, 0, new Position(-1, -1), 1);
	}
	
	public Personnage getPersoSurPosition(Position pos) {
		Personnage temp;
		for (Iterator<Personnage> perso = liste.iterator();perso.hasNext();) {
			temp = perso.next();
			if (temp.getPos().equals(new Position(pos.x, pos.y))) {
				return temp;
			}
		}
		return new Personnage("existe pas", true, 0, new Position(-1, -1), 1);
	}
}
