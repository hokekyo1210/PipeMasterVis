package tmcit.freedom.Util;

public class Third {
	public int p1, p2, p3;

	public Third(int p1, int p2, int p3){
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
	}

	public Third PairClone(){
		return new Third(p1, p2, p3);
	}

}
