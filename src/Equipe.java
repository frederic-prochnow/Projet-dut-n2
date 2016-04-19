import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Equipe {
	private List<Personnage> liste;
	
	public Equipe() {
		liste = new ArrayList<Personnage>();
	}
	
	public List<Personnage> getListe() {
		return this.liste;
	}
	
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
}
