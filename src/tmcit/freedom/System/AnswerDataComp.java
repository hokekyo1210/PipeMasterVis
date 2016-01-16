package tmcit.freedom.System;

import java.util.Comparator;

import tmcit.freedom.Algorithm.AnswerData;

public class AnswerDataComp implements Comparator<AnswerData> {

	@Override
	public int compare(AnswerData o1, AnswerData o2) {
		if(o1.getScore() < o2.getScore()) {
			return 1;
		}else if(o1.getScore() > o2.getScore()) {
			return -1;
		}else{
			return 0;
		}
	}
}
