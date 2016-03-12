package jeu;

public class Parcelle {
	int type;
	int energie = 0;
	int objetDetenu = -1;
	boolean equipe;
	boolean surNavire;
	boolean cleEquipe1 = false;
	boolean cleEquipe2 = false;
	boolean estCompte = false;
	

	
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
	
	public String toString() {
		if (this.surNavire && this.equipe){
			return typeToString() + "n";
		} else if (this.surNavire && !this.equipe){
			return typeToString() + "N";
		}
		return typeToString();
	}
}
