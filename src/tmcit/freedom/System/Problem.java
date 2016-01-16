package tmcit.freedom.System;

import java.io.BufferedReader;
import java.io.FileReader;

import tmcit.freedom.Util.PipeType;

public class Problem {
	private boolean isExist;
	private int spaceNum, sgNum;
	private int limI, limL, limX;
	private PipeType[][] board;
	
	public Problem(){
		this.board = new PipeType[30][30];
		this.initilize();
	}
	
	public void initilize(){
		this.isExist = false;
		this.limI = 0;
		this.limL = 0;
		this.limX = 0;
		this.spaceNum = 0;
		this.sgNum = 0;
		for(int i = 0; i < 30; i++){
			for(int j = 0; j < 30; j++){
				board[i][j] = PipeType.NULL;
			}
		}
	}

	public PipeType[][] getBoard(){
		return board;
	}
	
	public int getLimI(){
		return limI;
	}

	public int getLimL(){
		return limL;
	}
	
	public int getLimX(){
		return limX;
	}
	
	public void setLimI(int limI){
		this.limI = limI;
	}

	public void setLimL(int limL){
		this.limL = limL;
	}

	public void setLimX(int limX){
		this.limX = limX;
	}

	public void setLimitor(int[] num){
		this.limI = num[0];
		this.limL = num[1];
		this.limX = num[2];
	}

	public void setPipeType(int x, int y, PipeType type){
		this.board[y][x] = type;
	}

	public boolean read(String directory) {
		try{
			FileReader rd = new FileReader(directory);
			BufferedReader br = new BufferedReader(rd);
			String str;
			Problem problem = this;
			for(int i = 0; i < 30; i++){
				str = br.readLine();
				if(str == null)return false;
				for(int j = 0; j < 30; j++){
					if(str.charAt(j) == '.'){
						problem.setPipeType(j, i, PipeType.EMP);
						this.spaceNum++;
					}else if(str.charAt(j) == '#'){
						problem.setPipeType(j, i, PipeType.BLO);
					}else if(str.charAt(j) == 'S'){
						problem.setPipeType(j, i, PipeType.STR);
						this.sgNum++;
					}else if(str.charAt(j) == 'G'){
						problem.setPipeType(j, i, PipeType.GOL);
						this.sgNum++;
					}
				}
			}
			str = br.readLine();
			String[] tmp = str.split(" ");
			int[] num = new int[3];
			for(int i = 0; i < 3; i++){
				num[i] = Integer.valueOf(tmp[i]);
			}
			problem.setLimitor(num);
			this.isExist = true;
			br.close();
			rd.close();
			return true;
		}catch(Exception e){
			System.out.println(e);
			ProblemManager.error("The format of Problem is incorrect.");
			return false;
		}
	}
	
	public boolean getExist(){
		return this.isExist;
	}

	public int getSpaceNum() {
		return this.spaceNum;
	}

	public int getSGNum() {
		return sgNum;
	}
	
//	public void debug(){
//		for(int i = 0; i < 30; i++){
//			for(int j = 0; j < 30; j++){
//				System.out.print(board[i][j]);
//			}
//			System.out.println();
//		}
//
//		System.out.println("(" +limI+ ", " +limL+", " +limX+ ")");
//	}

}
