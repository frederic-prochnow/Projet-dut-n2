import java.util.Iterator;
import java.util.List;

public class GestionJeu {
	private boolean tourEquipe1; // True = equipe 1 , false = equipe2
	private boolean fini;
	private int round;
	private int equipeVainqueur;
	
	GestionJeu(boolean equipeDemarre){
		tourEquipe1 = equipeDemarre;
		fini = false;
		equipeVainqueur = -1;
		round = 1;
	}
	
	public int getEquipeVainqueur(){
		return this.equipeVainqueur;
	}
	
	public boolean getTourEquipe(){
		return tourEquipe1;
	}
	
	public boolean getEstFini(List<Personnage> equipe){
		Personnage temp;
		for (Iterator<Personnage> itr = equipe.iterator();itr.hasNext();) {
			temp = itr.next();
			if (temp.getDetientTresor() && temp.getSurnavire()) {
				return true;
			}
		}
		return false;
	}
	
	public void setEstFini(boolean f){
		this.fini = f;
	}
	
	public int getRound(){
		return this.round;
	}
	
	public String toString(){
		if(this.fini){
			return "Le vainqueur est l'équipe "+this.equipeVainqueur+". Avec "+this.round+" tours.";
		}else{
			return "Tour actuel : "+this.round;
		}
	}
	
	public void nextRound(){
		tourEquipe1 = !tourEquipe1;
		round++;
	}
}
