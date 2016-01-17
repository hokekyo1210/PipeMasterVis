package tmcit.freedom.Algorithm.BeamSearch;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
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
	//-100000*N~0
	private static final double badAreaP = 100000.0; 
	
	private static int[] dirX = {0, 0, -1, 1};
	private static int[] dirY = {-1, 1, 0, 0};
	
	/*AnswerData*/
	private double expoint;
	private int nowI, nowL, nowX;
	private PipeType[][] board;

	private int atX, atY, atB;
	private int putPipeNum;
	
	private void startEvaluate(){
		if(this.canReach(atX,atY,PipeType.GOL) == false){
			this.expoint = -9999996;
			return;
		}
		if(board[atX + dirX[atB] + 1][atY + dirY[atB] + 1] == PipeType.STR){
			////first node only
			this.expoint += allBoardScore();
		}
				
		this.expoint += this.aroundE();
		this.expoint += this.useRateE();
//		this.expoint += this.useRateOldE();
//		this.expoint += this.nowCoolE();
		this.expoint += this.closedPipe();
	}
	
	public double allBoardScore(){
		double e = 0;
		boolean[][] mem = new boolean[32][32];
		for(int i = 0;i < 32;i++)
		for(int j = 0;j < 32;j++){
			if(mem[j][i])continue;
			if(board[j][i] != PipeType.EMP)continue;
			
			Queue<Pair> que = new ArrayDeque<Pair>();
			que.add(new Pair(j,i));
			boolean goalFlag = false,startFlag = false;
			while(!que.isEmpty()){
				Pair p = que.poll();
				int x = p.p1;
				int y = p.p2;
				if(board[x][y] == PipeType.STR)startFlag = true;
				else if(board[x][y] == PipeType.GOL)goalFlag = true;
				if(board[x][y] != PipeType.EMP)continue;
				if(mem[x][y])continue;
				mem[x][y] = true;
				
				for(int d = 0;d < 4;d++){
					int tx = x + dirX[d];
					int ty = y + dirY[d];
					if(mem[tx][ty])continue;
					que.add(new Pair(tx,ty));
				}
			}
			if(!goalFlag||!startFlag){
				e -= badAreaP;
			}
		}
		return e;
	}

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
			}
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
	
	private static FastQueue fastQueueX = new FastQueue(32*32*2);
	private static FastQueue fastQueueY = new FastQueue(32*32*2);
	private static long[][] memo = new long[32][32];
	private static long id = 1;
	
	private boolean canReach(int sx,int sy,PipeType target){
		if(id==1){
			////memo initialize
			for(int i = 0;i < 32;i++)
				for(int j = 0;j < 32;j++)memo[i][j] = 0;
		}
		////BFS O(32*32)
		fastQueueX.clear();
		fastQueueY.clear();
		fastQueueX.push(sx);
		fastQueueY.push(sy);
		boolean result = false;
		while(fastQueueX.size()!=0){
			int x = fastQueueX.pop();
			int y = fastQueueY.pop();
			if(this.board[y + 1][x + 1] == target){result = true;break;}
			if(this.board[y + 1][x + 1] != PipeType.EMP)continue;
			if(memo[x + 1][y + 1] == id)continue;
			memo[x + 1][y + 1] = id;
			for(int i = 0; i < 4; i++){
				if(memo[x + dirX[i] + 1][y + dirY[i] + 1] == id)continue;
				fastQueueX.push(x + dirX[i]);
				fastQueueY.push(y + dirY[i]);
			}
		}
		id++;
		return result;
	}

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
