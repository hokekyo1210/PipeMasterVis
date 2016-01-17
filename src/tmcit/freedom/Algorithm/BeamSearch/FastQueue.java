package tmcit.freedom.Algorithm.BeamSearch;

public class FastQueue {
	
	private int array[];
	private int l = 0,r = 0;
	
	public FastQueue(int size){
		array = new int[size];
	}
	
	public void push(int v){
		array[r++] = v;
	}
	public int pop(){
		return array[l++];
	}
	public int size(){
		return r-l;
	}
	public void clear(){
		l = 0;
		r = 0;
	}

}
