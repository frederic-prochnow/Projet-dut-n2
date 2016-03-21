public class Personnage {
	protected String nom;
	protected char symboleNom;
	protected int equipe;
	protected int energie;
	
	Personnage(String nom, int equipe, int energie){
		this.nom = nom;
		this.energie = energie;
		this.equipe = equipe;
		
		if(equipe == 1){
			this.symboleNom = nom.charAt(0);
		}else{
			this.symboleNom = (nom.toUpperCase()).charAt(0);
		}
	}
	
	public void perdEnergie(int energie){
		this.energie -= energie;
	}
	
	public String getNom(){
		return nom;
	}
	
	public char getSymbole(){
		return symboleNom;
	}
	
	public int getEquipe(){
		return equipe;
	}
	
	public int getEnergie(){
		return energie;
	}
	
	public String toString(){
		return nom+" de l'équipe "+equipe+" possede "+energie+" points d'energie.";
	}
}
