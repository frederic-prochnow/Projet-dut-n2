import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Position extends Point{
	
	private List<Personnage> persosPresents;
	private boolean plusdEnergie;
	
	public Position(int x,int y){
		super(x,y);
		persosPresents = new ArrayList<>();
		plusdEnergie = true;
	}
		
	public Position(Point point){
		super(point);
		persosPresents = new ArrayList<>();
		plusdEnergie = true;
	}
	// on parcour les personnages de l'equipe voulue, et on verifie si l'un
	// au moins a la meme position que celle selectionnee
	public boolean pointValide(List<Personnage> equipe, Plateau plateauGraph) {
		Personnage temp;
		plusdEnergie = true;
		for (int i=0;i<equipe.size();i++) {
			temp = equipe.get(i);
			if (temp.getPos().equals(this)) {
				if (temp.getEnergie() > 0) {
					plusdEnergie = false;
				}
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

	public boolean getEnergieInvalide() {
		return plusdEnergie;
	}
	
	public void setLocation(Position p) {
		this.x = p.x;
		this.y = p.y;
	}
	
}

