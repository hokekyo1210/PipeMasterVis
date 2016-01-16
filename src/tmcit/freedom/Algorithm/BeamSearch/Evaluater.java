package tmcit.freedom.Algorithm.BeamSearch;

import java.util.ArrayDeque;
import java.util.Queue;

import tmcit.freedom.Util.Pair;
import tmcit.freedom.Util.PipeType;

public class Evaluater {
	//0 ~ 60.0
	private static final double[] aroundP = {/*STR,GOL*/5.0, /*BLO*/10.0, /*Pipe*/15.0};
	//~ -100 ~ 0
	private static final double powNum = 20.0;
	private static final double useRateP = 10.0;
	//1 ~ 30
	private static final double nowCoolP = 0.5;
	//-80 ~ 0
	private static final double closedP = 40.0;
	
	private void startEvaluate(){
		if(this.canReachGoal() == false){
			this.expoint = -9999996;
			return;
		}
		this.expoint += this.aroundE();
		this.expoint += this.useRateE();
//		this.expoint += this.useRateOldE();
//		this.expoint += this.nowCoolE();
		this.expoint += this.closedPipe();
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

	private double closedPipe(){
		double e = 0;
		int x = atX + dirX[atB];
		int y = atY + dirY[atB];
		///int b = AnswerData.getRebirth(atB);
		for(int i = 0; i < 4; i++){
			///if(i == b)continue;
			int tx = x + dirX[i];
			int ty = y + dirY[i];
			if(tx == atX&&ty == atY)continue;
			if(this.board[ty + 1][tx + 1] != PipeType.EMP)continue;
//			boolean b1 = true;
			int pipeNum = 0;
			for(int j = 0; j < 4; j++){
				///if(pipeNum >= 3){b1 = false;break;}
				if(this.board[ty + dirY[j] + 1][tx + dirX[j] + 1] != PipeType.EMP
						&&this.board[ty + dirY[j] + 1][tx + dirX[j] + 1] != PipeType.GOL)pipeNum++;
			}
			if(pipeNum>=3){
				e -= closedP;
//				for(int k = 0;k<30;k++){
//					for(int j = 0;j<30;j++){
//						if(tx==j&&ty==k){
//							System.out.print("X ");
//						}else{
//							PipeType type = this.board[k+1][j+1];
//							///if(type==PipeType.)
//							System.out.print(this.board[k+1][j+1]+" ");
//						}
//					}
//					System.out.print("\n");
//				}
//				System.out.print("\n");
			}
//			if(b1)continue;
//			else e -= closedP;
		}
		
		return e;
	}

	private double useRateE(){
		double e = 0.0;
		
		double remainI = AnswerData.getLimI() - nowI;
		double remainL = AnswerData.getLimL() - nowL;
		///System.out.println(nowI+" "+nowL+" "+remainI+" "+remainL);
		
		double rate = remainI / (remainI + remainL);

		//30
		e -= Math.abs(0.5 - rate)*powNum;
//		e -= Math.pow(Math.abs(0.5 - rate)*powNum, 2.0);
		
		///System.out.println(rate +" "+ e);

		//10
		return e*useRateP;
	}
	
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
	
	private boolean canReachGoal(){
		Queue<Pair> que = new ArrayDeque<Pair>();
		boolean[][] memo = new boolean[32][32];
		que.add(new Pair(atX, atY));
		while(que.isEmpty() == false){
			Pair p = que.poll();
			int x = p.p1;
			int y = p.p2;
			if(this.board[y + 1][x + 1] == PipeType.GOL)return true;
			if(this.board[y + 1][x + 1] != PipeType.EMP)continue;
			if(memo[x + 1][y + 1])continue;
			memo[x + 1][y + 1] = true;
			for(int i = 0; i < 4; i++){
				if(memo[x + dirX[i] + 1][y + dirY[i] + 1])continue;
				que.add(new Pair(x + dirX[i], y + dirY[i]));
			}
		}
		return false;
	}
	
	
	/*AnswerData*/
	private double expoint;
	private int nowI, nowL, nowX;
	private PipeType[][] board;

	private int atX, atY, atB;
	private int putPipeNum;

	public Evaluater(AnswerData answerData){
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
