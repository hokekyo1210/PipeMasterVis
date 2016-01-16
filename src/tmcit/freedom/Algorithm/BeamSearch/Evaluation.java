package tmcit.freedom.Algorithm.BeamSearch;

import tmcit.freedom.Util.PipeType;

public class Evaluation {
	//0 ~ 60.0
	private static final double[] aroundP = {/*STR,GOL*/5.0, /*BLO*/10.0, /*Pipe*/15.0};
	//~ -100 ~ 0
	private static final double useRateP = 10.0;
	//1 ~ 30
	private static final double nowCoolP = 0.5;
	
	private void startEvaluate(){
		this.expoint += this.aroundE();
//		this.expoint += this.useRateE();
		this.expoint += this.useRateOldE();
//		this.expoint += this.nowCoolE();
	}
	
	private static int[] dirX = {0, 0, -1, 1};
	private static int[] dirY = {-1, 1, 0, 0};
	private double aroundE(){
		double e = 0;
		for(int i = 0; i < 4; i++){
			if(i == atB)continue;
			PipeType t = this.board[atY + dirY[i] + 1][atX + dirX[i] + 1];
			if(t == PipeType.EMP)continue;
			if(t == PipeType.STR || t == PipeType.GOL)e += aroundP[0];
			else if(t == PipeType.BLO)e += aroundP[1];
			else e += aroundP[2];
		}
		return e;
	}
	
//	private double useRateE(){
//		double e = 0.0;
//		double nowRateI = (double)((nowI + 1)/(nowL + nowI + 1));
//		double nowRateL = (double)((nowI + 1)/(nowL + nowI + 1));
//
//		e +=  1.0 - Math.abs(nowRateI - AnswerData.getLimRateI());
//		e +=  1.0 - Math.abs(nowRateL - AnswerData.getLimRateL());
//		
//		e += 1.0 - Math.abs(nowRateI - 0.5);
//
//		return e*useRateP;
//	}
	
	private double useRateOldE(){
		double e = 0.0;
		double nowRateIL = (double)((nowI + 1)/(nowL + 1));
		
		e -= Math.abs(nowRateIL - AnswerData.getLimRateIL());

		return e*useRateP;
	}
	
	private static int[][] distanceSG;
	public static void setDistanceSG(int[][] disMemo){distanceSG = disMemo;}
	private double nowCoolE(){
		if(distanceSG == null)return 0.0;
		return distanceSG[atY + 1][atX + 1]*nowCoolP;
	}
	
	
	/*AnswerData*/
	private double expoint;
	private int nowI, nowL, nowX;
	private PipeType[][] board;

	private int atX, atY, atB;
	private int putPipeNum;

	public Evaluation(AnswerData answerData){
		this.setAnswerData(answerData);
		this.startEvaluate();
		
		answerData.setExp(this.expoint);
	}
	
	private void setAnswerData(AnswerData answerData){
		this.expoint = answerData.getExp();
		this.board = answerData.getBoard();
		this.nowI = answerData.getNowI();
		this.nowL = answerData.getNowL();
		this.nowX = answerData.getNowX();
		this.atX = answerData.getAtX();
		this.atY = answerData.getAtY();
		this.atB = answerData.getAtB();
		this.putPipeNum = answerData.getPutPipeNum();
	}

}
