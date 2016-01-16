package tmcit.freedom.Algorithm.BeamSearch;

import java.util.Comparator;

public class MyComparator implements Comparator<AnswerData> {

	@Override
	public int compare(AnswerData o1, AnswerData o2) {
		if(o1.getExp() > o2.getExp()) {
			return 1;
		}else if(o1.getExp() < o2.getExp()) {
			return -1;
		}else{
			if(o1.getScore() > o2.getScore()) {
				return 1;
			}else if(o1.getScore() < o2.getScore()) {
				return -1;
			}else{
				return 0;
			}
		}
	}

}
