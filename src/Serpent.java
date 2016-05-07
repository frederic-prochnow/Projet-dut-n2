/**
 * Class Serpent
 * Gestion des serpents
 * @author Frédéric
 */
public class Serpent {

	/**
	 * @return le vie
	 */
	public boolean isVie() {
		return vie;
	}

	/**
	 * @param vie le vie à définir
	 */
	public void setVie(boolean vie) {
		this.vie = vie;
	}

	/**
	 * @return le degat
	 */
	public int getDegat() {
		return degat;
	}

	/**
	 * @param degat le degat à définir
	 */
	public void setDegat(int degat) {
		this.degat = degat;
	}

	private boolean vie = true;
	private int degat;
	private Personnage perso;
	private Position p;
	
	public Serpent(Personnage perso,int degat) {
		this.degat = degat;
		if(this.perso.getPos().equals(p) && vie == true) {
			this.perso.setEnergie(this.perso.getEnergie() - degat);
			vie = false;
		}
	}
	

}
