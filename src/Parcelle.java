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
	private boolean estPiege = false;
	

	/**
	 * Constructeur avec paramétre de Parcelle
	 * Ce paramétre permet de créer une parcelle type choisit
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
	 * Fonction permettant de récupérer le type de la pareclle concernée
	 * @return int
	 */
	public int getType(){
		return this.type;
	}
	
	public boolean getEstPiege(){
		return estPiege;
	}
	
	public void setEstPiege(boolean b){
		estPiege = b;
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
	
	// permet de connaitre a quelle equipe est sur la position
	public boolean getEquipe1() {
		if (this.type == 0 || this.type == 1 || this.type == 2 || this.type == 10 || this.type == 11) {
			return true;
		}
		return false;
	}
	
	public boolean getEquipe2() {
		if (this.type == 3 || this.type == 4 || this.type == 5 || this.type == 12 || this.type == 13) {
			return true;
		}
		return false;
	}
	
	public boolean estNavire() {
		return this.type == 1 || this.type == 4;
	}
	
	/**
	 * Fonction d'affichage TEXTUEL de la parcelle en fonction de son type
	 * <br>0 pour explorateur équipe 1
	 * <br>1 pour voleur équipe 1
	 * <br>2 pour navire équipe 1
	 * <br>3 pour explorateur équipe 2
	 * <br>4 pour voleur équipe 2
	 * <br>5 pour navire équipe 2
	 * <br>6 pour rocher
	 * <br>7 pour coffre
	 * <br>8 pour clé
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
		else if(type == 10) {return "g";} // guerrier1
		else if(type == 11) {return "p";} // piegeur1
		else if(type == 12) {return "G";} // guerrier2
		else if(type == 13) {return "P";} // piegeur2
		else if (type == 14) {return "D";}  // cle prise
		else  { return "";}
	} 
	
}
