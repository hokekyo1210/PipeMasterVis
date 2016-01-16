package tmcit.freedom.Algorithm.BeamSearch;

import java.util.ArrayList;

import tmcit.freedom.System.Answer;
import tmcit.freedom.System.Problem;
import tmcit.freedom.Util.Pair;
import tmcit.freedom.Util.PipeType;

public class AnswerData {
	private static final int[] dirX = {0, 0, -1, 1}, dirY = {-1, 1, 0, 0};

	private static PipeType[][] problemBoard;
	private static int limI, limL, limX;
	private static ArrayList<Pair> startPoint;

	private static double limRateIL;
	private static double limRateI, limRateL;

	public static void setProblem(Problem problem){
		limI = problem.getLimI() + problem.getLimX();
		limL = problem.getLimL();
		limX = problem.getLimX();
		limRateIL = limI/limL;
		limRateI = limI/(limL + limI);
		limRateL = limL/(limL + limI);
		PipeType[][] b = problem.getBoard();
		problemBoard = new PipeType[32][32];
		startPoint = new ArrayList<Pair>();
		for(int i = 0; i < 32; i++){
			for(int j = 0; j < 32; j++){
				if(i == 0 || j == 0 || i == 31 || j == 31){
					problemBoard[i][j] = PipeType.BLO;
				}else{
					problemBoard[i][j] = b[i - 1][j - 1];
					if(b[i - 1][j - 1] == PipeType.STR){
						for(int k = 0; k < 4; k++){
							int nextX = j - 1 + dirX[k];
							int nextY = i - 1 + dirY[k];
							int nextB = getRebirth(k);
							if(nextX < 0 || 30 <= nextX)continue;
							if(nextY < 0 || 30 <= nextY)continue;
							startPoint.add(new Pair(nextX, nextY, nextB));
						}
					}
				}
			}
		}
	}

	public static int getLimI(){return limI;}
	public static int getLimL(){return limL;}
	public static int getLimX(){return limX;}
	public static double getLimRateIL(){return limRateIL;}
	public static double getLimRateI(){return limRateI;}
	public static double getLimRateL(){return limRateL;}
	
	public static ArrayList<Pair> getStartPoints(){
		return startPoint;
	}

	public static int getRebirth(int v) {
		if(v == 0)return 1;
		if(v == 1)return 0;
		if(v == 2)return 3;
		if(v == 3)return 2;
		return -1;
	}


	/*Ansewr Data*/
	private double expoint;
	private int nowI, nowL, nowX;
	private PipeType[][] board;

	private int atX, atY, atB;
	private int score;
	private int putPipeNum;
	
	public AnswerData(){
		this.board = new PipeType[32][32];
	}

	public void initilizeAnswer(){
		this.score = 0;
		this.expoint = 0.0;
		this.putPipeNum = 0;
		this.nowI = this.nowL = this.nowX = 0;
		for(int i = 0; i < 32; i++){
			for(int j = 0; j < 32; j++){
				this.board[i][j] = problemBoard[i][j];
			}
		}
	}

	public void setCool(int nextX, int nextY, int nextB){
		this.atX = nextX;
		this.atY = nextY;
		this.atB = nextB;
	}

	public int setNext(int v){
		if(atB == v)return -1;
		int nextX = atX + dirX[v];
		int nextY = atY + dirY[v];
		if(board[nextY + 1][nextX + 1] == PipeType.GOL){
			if(putBoard(atX, atY, PipeType.getType(atB, v) ) == false)return -1;
			this.score += putPipeNum;
			this.putPipeNum = 0;
			return 1;
		}
		if(board[nextY + 1][nextX + 1] != PipeType.EMP)return -1;
		if(putBoard(atX, atY, PipeType.getType(atB, v) ) == false)return -1;
		this.setCool(nextX, nextY, getRebirth(v));
		return 0;
	}

	public boolean putBoard(int x, int y, PipeType type){
		if(board[y + 1][x + 1] != PipeType.EMP)return false;
		int t = getTypeOfPipeType(type);
		if(t == 0){
			if(++nowI > limI){nowI--;return false;}
		}else if(t == 1){
			if(++nowL > limL){nowL--;return false;}
		}else if(t == 2){
			if(++nowX > limX){nowX--;return false;}
		}
		this.board[y + 1][x + 1] = type;
		this.putPipeNum++;
		return true;
	}

	public static int getTypeOfPipeType(PipeType type) {
		if(type == PipeType.UD || type == PipeType.LR)return 0;
		if(type == PipeType.UL || type == PipeType.DL)return 1;
		if(type == PipeType.UR || type == PipeType.DR)return 1;
		if(type == PipeType.XX)return 2;
		return -1;
	}

//	public void fixBoard(int x, int y){
//		int t = getTypeOfPipeType(board[y + 1][x + 1]);
//		if(t == 0){nowI--;}
//		else if(t == 1){nowL--;}
//		else if(t == 2){nowX--;}
//		board[y + 1][x + 1] = PipeType.EMP;
//	}
		
	public Answer makeAnswer(){
		Answer ans = new Answer();
		ans.setNowI(this.nowI);
		ans.setNowL(this.nowL);
		ans.setNowX(this.nowX);
		for(int i = 0; i < 30; i++){
			for(int j = 0; j < 30; j++){
				PipeType t = this.board[i + 1][j + 1];
				if(PipeType.isPipe(t)){
					ans.setPipeType(j, i, t);
				}else{
					ans.setPipeType(j, i, PipeType.NULL);
				}
			}
		}

		return ans;
	}
	
	/*Copy*/
	public PipeType[][] getBoard(){return this.board;}
	
	public double getExp(){return this.expoint;}
	
	public int getScore(){return this.score;}
	
	public int getNowI(){return this.nowI;}
	public int getNowL(){return this.nowL;}
	public int getNowX(){return this.nowX;}
	
	public int getAtX(){return this.atX;}
	public int getAtY(){return this.atY;}
	public int getAtB(){return this.atB;}

	public int getPutPipeNum(){return this.putPipeNum;}

	public void setExp(double expoint){this.expoint = expoint;}
	public void setNow(int nowI, int nowL, int nowX){this.nowI = nowI;this.nowL = nowL;this.nowX = nowX;}
	
	public void setBoard(PipeType[][] board){
		for(int i = 0; i < 32; i++){
			for(int j = 0; j < 32; j++){
				this.board[i][j] = board[i][j];
			}
		}
	}
	
	public void setScore(int score){this.score = score;}
	
	public void setPutPipeNum(int putPipeNum){this.putPipeNum = putPipeNum;}
	
	public AnswerData getClone(){
		AnswerData tmp = new AnswerData();
		tmp.setExp(this.expoint);
		tmp.setNow(this.nowI, this.nowL, this.nowX);
		tmp.setCool(this.atX, this.atY, this.atB);
		tmp.setBoard(this.board);
		tmp.setScore(this.score);
		tmp.setPutPipeNum(this.putPipeNum);
		return tmp;
	}


}
