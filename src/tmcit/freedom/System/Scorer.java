package tmcit.freedom.System;

import tmcit.freedom.Util.PipeType;

public class Scorer {
	private boolean flag;
	private int score;
	private boolean[][] memo;
	private FourBit[][] board;

	public Scorer(){
		this.memo = new boolean[32][32];
		this.board = new FourBit[32][32];

		this.initilize();
	}
	
	private void initilize(){
		this.score = 0;
		this.flag = false;
		for(int i = 0; i < 32; i++){
			for(int j = 0; j < 32; j++){
				this.memo[i][j] = true;
				this.board[i][j] = new FourBit();
			}
		}
	}

	public int setBoard(PipeType[][] b){
		for(int i = 0; i < 30; i++){
			for(int j = 0; j < 30; j++){
				this.board[i + 1][j + 1].setBit(b[i][j]);
			}
		}
		if(startScoring()){
			return getScore();
		}
		return -1;
	}

	private boolean startScoring(){
		for(int i = 1; i < 31; i++){
			for(int j = 1; j < 31; j++){
				if(this.board[i][j].getEvent() == 1){//Search Start
					for(int k = 0; k < 4; k++){
						int nextX = FourBit.dirX[k] + j;
						int nextY = FourBit.dirY[k] + i;
						int tmpScore = saiki(nextX, nextY, k);
						if(tmpScore == -1)continue;
						this.score += tmpScore;
					}
				}
			}
		}
		return flag = true;
	}

	private int saiki(int x, int y, int before){
		int event = board[y][x].getEvent();
		if(event == 2){//Goal
			return 0;
		}
		if(event != 0)return -1;//Not Pipe
		before = FourBit.rebirth(before);
		int nextB = board[y][x].getNext(before);
		if(nextB == -1)return -1;//Next Pipe is Not Segment.

		int nextX = FourBit.dirX[nextB] + x;
		int nextY = FourBit.dirY[nextB] + y;

		int tmpScore = this.saiki(nextX, nextY, nextB);
		if(tmpScore == -1)return -1;
		if(memo[y][x]){
			tmpScore++;
			memo[y][x] = false;
		}
		return tmpScore;
	}

	public int getScore(){
		if(flag == false)return -1;
		return score;
	}

	public boolean[][] getRouteMemo() {
		return memo;
	}
}
