public class Clef {
	private Position pos;
	private int type;
	
	public Clef(Position p){
		this.pos = p;
		this.type = 8;
	}
	
	public Position getPos(){
		return this.pos;
	}
	
	public int getType(){
		return this.type;
	}
}
