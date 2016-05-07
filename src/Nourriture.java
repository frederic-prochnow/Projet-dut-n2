/**
 * Class Nourriture
 * Gestion de la nourriture
 * @author Team J3
 */
public class Nourriture {

	/**
	 * @return le energie
	 */
	public int getEnergie() {
		return energie;
	}

	/**
	 * @param energie le energie à définir
	 */
	public void setEnergie(int energie) {
		this.energie = energie;
	}

	Personnage perso;
	int energie;
	Position p;
	
	public Nourriture(Personnage perso,int energie) {
		this.energie = energie;
		if(this.perso.getPos().equals(p)) {
			this.perso.setEnergie(this.energie + energie);
		}
	}
	

}
