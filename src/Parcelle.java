/**
 * Class Parcelle
 * Gestion de parcelle de l'Ile
 * 
 * @author TeamJ3
 *
 */
public class Parcelle {
	/**
	 * attributs de la classe
	 */
	int type;
	int energie = 0;
	int objetDetenu = -1;
	boolean equipe;
	boolean surNavire;
	boolean cleEquipe1 = false;
	boolean cleEquipe2 = false;
	boolean estCompte = false;
	

	/**
	 * Constructeur avec paramétre de Parcelle
	 * Ce paramétre permet de créer une parcelle type choisit
	 *
	 * @param type
	 */
	Parcelle (int type) {
		if(type>=0 && type<11) {
			this.type = type;
			if (type<=5) {
				this.energie = 100;
				if (type<=2) {
					this.equipe = true;
				} else {
					this.equipe = false;
				}
			}
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
		else if(type == 8) {return "K";} // clé
		else if(type == 9) {return "~";} // eau
		else  { return "";}
	} 
	
	/**
	 * Fonction d'affichage TEXTUEL de la parcelle
	 */
	public String toString() {
		if (this.surNavire && this.equipe){
			return typeToString() + "n";
		} else if (this.surNavire && !this.equipe){
			return typeToString() + "N";
		}
		return typeToString();
	}
}
