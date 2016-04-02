import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Position extends Point{
	
	private List<Personnage> persosPresents;
	
	public Position(int x,int y){
		super(x,y);
		persosPresents = new ArrayList<>();
	}
		
	// on parcour les personnages de l'equipe voulue, et on verifie si l'un
	// au moins a la meme position que celle selectionnee
	public boolean pointValide(List<Personnage> equipe) {
		Personnage temp;
		for (int i=0;i<equipe.size();i++) {
			temp = equipe.get(i);
			if (temp.getPos().equals(this)) {
				return true;
			}
		}
		return false;
	}
	
	// retourne une liste des personnages de l'equipe courante sur cette position
	public List<Personnage> getPersosSurPosition(List<Personnage> equipe) {
		Personnage temp;
		persosPresents.clear();
		for (Iterator<Personnage> itr = equipe.iterator();itr.hasNext();) {
			temp = itr.next();
			if (temp.getPos().equals(this)) {
				persosPresents.add(temp);
			}
		}
		return persosPresents;
	}
	
}

