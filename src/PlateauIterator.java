/**
 * Importation
 */
import java.util.Iterator;
/**
 * Class PlateauIterator
 * @author Team J3
 */
public class PlateauIterator implements Iterator<Integer>{
	/**
	 * Attribut
	 */
	private int lg,col;
	private int[][] tableau;
	
	/**
	 * Constructeur de la classe
	 * @param int[][] plateau de jeu
	PlateauIterator(int[][] tableau){
		this.tableau=tableau;
		lg=tableau.length-1;
		col=tableau[0].length-1;
	}
	/**
	 * Au suivant !
	 * @return suivant
	 */
	public boolean hasNext() {
		return (lg+col>0);
	}
	/**
	 * Suivant
	 * @return tableau
	 */
	public Integer next() {	
		if (col>0) col--; else {lg--;col=tableau[0].length-1;}
		return tableau[lg][col];
	}
	/**
	 * Effacer
	 */
	public void remove() {		
	}
	
}
