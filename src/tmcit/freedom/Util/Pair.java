package tmcit.freedom.Util;

public class Pair {
	public int p1, p2, p3;
	public Pair(int p1, int p2){
		this.p1 = p1;
		this.p2 = p2;
	}

	public Pair(int p1, int p2, int p3){
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
	}

	public Pair PairClone(){
		return new Pair(p1, p2, p3);
	}
}
