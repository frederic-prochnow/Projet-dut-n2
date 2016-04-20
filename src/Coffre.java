
public class Coffre {
	private Position pos;
	private boolean estOuvert;
	private boolean estVide;
	private int type;
	
	public Coffre(Position p){
		this.pos = p;
		this.estOuvert = false;
		this.type = 7;
	}
	
	public int getType(){
		return this.type;
	}
	
	public boolean getEstOuvert(){
		return this.estOuvert;
	}
	
	public void setEstOuvert(boolean set) {
		this.estOuvert = set;
	}

	public boolean getEstVide(){
		return this.estVide;
	}
	
	public void setEstVide(boolean set) {
		this.estVide = set;
	}
	public Position getPos(){
		return this.pos;
	}
	
	
}
