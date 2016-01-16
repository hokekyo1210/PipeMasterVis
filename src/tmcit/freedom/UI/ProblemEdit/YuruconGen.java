package tmcit.freedom.UI.ProblemEdit;

import java.security.SecureRandom;

import tmcit.freedom.System.Problem;
import tmcit.freedom.Util.PipeType;

public class YuruconGen {
	
	private static SecureRandom r1;

	private int N, M;
	private PipeType[][] board = null;
	private int S, C, X;

	public YuruconGen(int N, long seed) throws Exception {
		r1 = SecureRandom.getInstance("SHA1PRNG");
		r1.setSeed(seed);
		if(N==-1)
			N = (r1.nextInt(21))+10;
		this.N = N;
		this.generate();
	}
	
	public void generateProblem(Problem problem){
		for(int i = 0; i < 30; i++){
			for(int j = 0; j < 30; j++){
				problem.setPipeType(i, j, board[i][j]);
			}
		}
		problem.setLimI(this.S);
		problem.setLimL(this.C);
		problem.setLimX(this.X);
	}
	
	private final int[] DIRX = {-1,0,1,0};
	private final int[] DIRY = {0,-1,0,1};
	private boolean generate(){
		if(N < 10 || N > 30){
			System.out.println("N must be an integer between 10 and 30.");
			return false;
		}
		board = new PipeType[30][30];
		for(int h = 0; h < 30; h++)
		for(int w = 0; w < 30; w++){
			board[w][h] = PipeType.BLO;
		}
		for(int h = 0; h < N; h++)
		for(int w = 0; w < N; w++){
			board[w][h] = PipeType.EMP;
		}
		
		M = (r1.nextInt((N/2) - 2))+3;
		
		for(int i = 0; i < M; i++)
		for(int j = 0; j < 2; j++)
		while(true){
			int x = r1.nextInt(N);
			int y = r1.nextInt(N);
			if(board[x][y] != PipeType.EMP)continue;
			boolean can = true;
			for(int d = 0; d < 4; d++){
				int tx = x + DIRX[d];
				int ty = y + DIRY[d];
				if(tx < 0 || ty < 0 || tx >= N || ty >= N)continue;
				if(board[tx][ty] != PipeType.EMP)can = false;
			}
			if(!can)continue;
			if(j == 0)
				board[x][y] = PipeType.STR;
			if(j == 1)
				board[x][y] = PipeType.GOL;
			break;
		}
		
		int white = N*N - 2*M;
		int perc = r1.nextInt(201)+100;
		int cross = (int)(white*perc*0.0001);
		int pers = r1.nextInt(61) + 10;
		int straight = (int)(white*pers*0.01);
		int curve = white - (cross + straight);
		S = straight;
		C = curve;
		X = cross;
		
		return true;
	}
	
	public PipeType[][] getResult(){
		return board;
	}
	
	public int getN(){return N;}
	public int getM(){return M;}
	public int getS(){return S;}
	public int getC(){return C;}
	public int getX(){return X;}

}
