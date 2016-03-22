
public class Parcelle {
	private int type;
	private boolean cleEquipe1 = false;
	private boolean cleEquipe2 = false;
	private boolean estCompte = false;
	

	
	Parcelle (int type) {
		if(type>=0 && type<11) {
			this.type = type;
		} else {
			this.type = -1;
		}
	}
	
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
	
	/* LIE A L'AFFICHAGE TEXTUEL */
	public String toString() {
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
