/**
 * Class Parcelle
 * Gestion de parcelle de l'Ile
 * 
 * @author TeamJ3
 *
 */
public class Parcelle {
<<<<<<< HEAD
	private int type;
	//private boolean cleEquipe1 = false;
	//private boolean cleEquipe2 = false;
	private boolean estCompte = false;
=======
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
>>>>>>> 31d3eef69688432df043c743110a895a9b0dd954
	

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
	
<<<<<<< HEAD
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
=======
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
>>>>>>> 31d3eef69688432df043c743110a895a9b0dd954
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
	
<<<<<<< HEAD
	
=======
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
>>>>>>> 31d3eef69688432df043c743110a895a9b0dd954
}
