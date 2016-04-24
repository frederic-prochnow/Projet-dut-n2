/**
 * Importation
 */
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * Class Position
 * @author Team J3
 */
public class Position extends Point{
	/**
	 * Attribut
	 */
	private List<Personnage> persosPresents;
	/**
	 * Constructeur de la classe
	 * @param coordonnee x
	 * @param coordonnee y
	 */
	public Position(int x,int y){
		super(x,y);
		persosPresents = new ArrayList<>();
	}
	/** Constructeur de la classe
	 * @param point
	 */
	public Position(Point point){
		super(point);
		persosPresents = new ArrayList<>();
	}
	/**
	 * on parcour les personnages de l'equipe voulue, et on verifie si l'un
	 * au moins a la meme position que celle selectionnee
	 * @param Liste de personnage
	 * @return booleen
	 */
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
	
	/**
	 * Retourne une liste des personnages de l'equipe courante sur cette position
	 * @param liste de personnage
	 * @return personnage present
	 */
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
	/**
	 * Localisation de l element
	 * @param position
	 */
	public void setLocation(Position p) {
		this.x = p.x;
		this.y = p.y;
	}
	/**
	 * Coordonne differente
	 * @return coordonnee
	 */
	public Position differenceCoordonnees(Position p) {
		return new Position(this.x-p.x, this.y-p.y);
	}
	/**
	 * Addition de coordonnee
	 * @return position
	 */
	public Position additionner(Position p) {
		return new Position(this.x+p.x, this.y+p.y);
	}
	
	public boolean getNulle() {
		return this.equals(new Position(-1,-1));
	}
	
	public boolean estCentreDe(Position pos) {
		for (int i=this.x-1;i<=this.x+1;i++) {
			for (int j=this.y-1;j<=this.y+1;j++) {
				if (!this.equals(new Position(i ,j)) && (pos.x == i && pos.y == j) )  {
					return true;
				}
			}
		}
		return false;
	}
	
}

