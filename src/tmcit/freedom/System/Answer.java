package tmcit.freedom.System;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import tmcit.freedom.Util.PipeType;


public class Answer {
	private int pipeNum;
	private PipeType[][] board;
	private int nowI, nowL, nowX;
	
	public Answer(){
		this.board = new PipeType[30][30];

		this.initilize();
	}

	public void initilize(){
		this.pipeNum = -1;
		this.nowI = 0;
		this.nowL = 0;
		this.nowX = 0;
		for(int i = 0; i < 30; i++){
			for(int j = 0; j < 30; j++){
				this.board[i][j] = PipeType.NULL;
			}
		}
	}

	public PipeType[][] getBoard() {
		return board;
	}
	
	public int getPipeNum(){
		return this.pipeNum;
	}
	
	public int getNowI(){
		return this.nowI;
	}

	public int getNowL(){
		return this.nowL;
	}

	public int getNowX(){
		return this.nowX;
	}

	public void setPipeNum(int n){
		this.pipeNum = n;
	}
	
	public void setNowI(int n){
		this.nowI = n;
	}
	
	public void setNowL(int n){
		this.nowL = n;
	}

	public void setNowX(int n){
		this.nowX = n;
	}

	public void setPipeType(int x, int y, PipeType type){
		this.board[y][x] = type;
	}

	public boolean read(String directory) {
		try{
			FileReader rd = new FileReader(directory);
			BufferedReader br = new BufferedReader(rd);
			String str = br.readLine();
			if(str == null){
				return false;
			}
			Answer answer = this;
			int pipeNum = Integer.parseInt(str);
			answer.setPipeNum(pipeNum);
			int n1 = 0, n2 = 0, n3 = 0;
			for(int i = 0; i < pipeNum; i++){
				str = br.readLine();
				if(str == null)return false;
				String[] tmp = str.split(" ");
				int x, y;
				PipeType type;
				x = Integer.parseInt(tmp[0]);
				y = Integer.parseInt(tmp[1]);
				type = PipeType.getType(tmp[2].charAt(0), tmp[3].charAt(0));
				answer.setPipeType(x, y, type);
				if(type == PipeType.UD || type == PipeType.LR){
					n1++;
				}else if(type == PipeType.UL || type == PipeType.UR || type == PipeType.DL || type == PipeType.DR){
					n2++;
				}else if(type == PipeType.XX){
					n3++;
				}
			}
			answer.setNowI(n1);
			answer.setNowL(n2);
			answer.setNowX(n3);


			br.close();
			rd.close();
			return true;
		}catch(IOException e){
			System.out.println(e);
			ProblemManager.error("The format of Answer is incorrect.");
			return false;
		}
	}
	
//	public void debug(){
//		for(int i = 0; i < 30; i++){
//			for(int j = 0; j < 30; j++){
//				System.out.print(board[i][j]);
//			}
//			System.out.println();
//		}
//
//		System.out.println("(" +pipeNum+ ")");
//	}


}
