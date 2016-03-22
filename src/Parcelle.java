/**
 * Class Parcelle
 * Gestion de parcelle de l'Ile
 * 
 * @author TeamJ3
 *
 */
public class Parcelle {

	private int type;
	//private boolean cleEquipe1 = false;
	//private boolean cleEquipe2 = false;
	private boolean estCompte = false;
	

	/**
	 * Constructeur avec param�tre de Parcelle
	 * Ce param�tre permet de cr�er une parcelle type choisit
	 *
	 * @param type
	 */
	Parcelle (int type) {
		if(type>=0 && type<11) {
			this.type = type;
		} else {
			this.type = -1;
		}
	}
	
	/**
	 * Fonction permettant de r�cup�rer le type de la pareclle concern�e
	 * @return int
	 */
	public int getType(){
		return this.type;
	}
	
	public void setType(int x){
		this.type = x;
	}
	
	public boolean getEstCompte(){
		return estCompte;
	}
	
	public void setEstCompte(boolean b){
		this.estCompte = b;
	}
	
	/**
	 * Fonction d'affichage TEXTUEL de la parcelle en fonction de son type
	 * <br>0 pour explorateur �quipe 1
	 * <br>1 pour voleur �quipe 1
	 * <br>2 pour navire �quipe 1
	 * <br>3 pour explorateur �quipe 2
	 * <br>4 pour voleur �quipe 2
	 * <br>5 pour navire �quipe 2
	 * <br>6 pour rocher
	 * <br>7 pour coffre
	 * <br>8 pour cl�
	 * <br>9 pour eau
	 * @return String
	 */
	public String typeToString() {
		if(type == -1) {return " ";}
		else if(type == 0) {return "e";}
		else if(type == 1) {return "v";}
		else if(type == 2) {return "n";}
		else if(type == 3) {return "E";}
		else if(type == 4) {return "V";}
		else if(type == 5) {return "N";}
		else if(type == 6) {return "R";} // rocher
		else if(type == 7) {return "C";} // coffre
		else if(type == 8) {return "K";} // cle
		else if(type == 9) {return "~";} // eau
		else  { return "";}
	} 
	
}
