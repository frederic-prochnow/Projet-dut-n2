package Jeu;
/**
 * Class Parcelle
 * @author TeamJ3
 * Projet
 */
public class Parcelle {
	/**
	 * attribut
	 */
	int type;

	/**
	 * Constructeur de Parcelle
	 * @param int type
	 */
	Parcelle (int type) {
		if(type>=0 && type<11) {
			this.type = type;
		} else {
			this.type = -1;
		}
	}

	/**
	 * Methode d'affichage toString
	 */
	public String toString() {
		if(type == -1) {return " ";}
		else if(type == 0) {return "e";}
		else if(type == 1) {return "v";}
		else if(type == 2) {return "n";}
		else if(type == 3) {return "E";}
		else if(type == 4) {return "V";}
		else if(type == 5) {return "N";}
		else if(type == 6) {return "R";}
		else if(type == 7) {return "C";}
		else if(type == 8) {return "~";}
		else  { return "";}
	} 
}
