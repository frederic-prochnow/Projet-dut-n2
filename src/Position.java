public class Position {
	public int x;
	public int y;
	
	Position(int x,int y){
		this.x = x;
		this.y = y;
	}
	
	public String toString(){
		return "("+x+","+y+")";
	}
	
	public boolean equals(Position p){
		return ((this.x == p.x) && (this.y == p.y));
	}
}
