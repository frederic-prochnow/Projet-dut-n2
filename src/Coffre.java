public class Coffre {
	private Position pos;
	private boolean estOuvert;
	private int type;
	
	Coffre(Position p){
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
	
	public Position getPos(){
		return this.pos;
	}
}
