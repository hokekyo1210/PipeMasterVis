package tmcit.freedom.Algorithm.BeamSearch;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

import tmcit.freedom.System.AnswerDataComp;
import tmcit.freedom.System.Problem;
import tmcit.freedom.Util.Pair;
import tmcit.freedom.Util.PipeType;
import tmcit.freedom.Util.Third;


public class MainALG {
	
	public static int beamWidth = 500;

	private Problem problem;

	private PriorityQueue<AnswerData> queue;
	private ArrayList<AnswerData> storage;
//	private ArrayList<AnswerData> goalStorage;

	private PriorityQueue<AnswerData> answerStock;


	private int maxScore;

	public MainALG(Problem problem){
		this.problem = problem;

		this.initilize();
	}
	
	private void initilize() {
		this.maxScore = 0;
		//
		this.queue   = new PriorityQueue<AnswerData>(MainALG.beamWidth + 1, new MyComparator());
		this.storage = new ArrayList<AnswerData>();
		this.answerStock = new PriorityQueue<AnswerData>(MainALG.beamWidth, new AnswerDataComp());
		this.setStartPoint();
//		this.runDistance();
	}

	private void setStartPoint(){
		AnswerData.setProblem(problem);
		ArrayList<Pair> sPoints = AnswerData.getStartPoints();
		for(Pair p : sPoints){
			AnswerData tmp = new AnswerData();
			tmp.initilizeAnswer();
			tmp.setCool(p.p1, p.p2, p.p3);
			queue.add(tmp);
		}
	}
	
	public void start(){
		this.startBeamSearch();
	}
	
	private void runDistance(){
		Queue<Third> que = new ArrayDeque<Third>();
		PipeType[][] board = problem.getBoard();
		int[][] disMemo = new int[32][32];
		for(int i = 0; i < 32; i++){
			for(int j = 0; j < 32; j++){
				disMemo[i][j] = -1;
				if(i == 0 || j == 0 || i == 31 || j == 31)continue;
				disMemo[i][j] = 1024;
				if(board[i - 1][j - 1] != PipeType.STR && board[i - 1][j - 1] != PipeType.GOL)continue;
				que.add(new Third(j - 1, i - 1, 0));
			}
		}
		int[] dirX = {0, 0, -1, 1};
		int[] dirY = {-1, 1, 0, 0};
		while(que.isEmpty() == false){
			Third point = que.poll();
			int x = point.p1;
			int y = point.p2;
			int cost = point.p3;
			if(disMemo[y + 1][x + 1] < cost)continue;
			disMemo[y + 1][x + 1] = cost;
			for(int i = 0; i < 4; i++){
				int nextX = x + dirX[i];
				int nextY = y + dirY[i];
				que.add(new Third(nextX, nextY, cost + 1));
			}
		}
		Evaluater.setDistanceSG(disMemo);
	}
	
//	public void debugDistMemo(){
//		for(int i = 0; i < 32; i++){
//			for(int j = 0; j < 32; j++){
//				System.out.print(disMemo[i][j] + " ");
//			}
//			System.out.println();
//		}
//	}

	private void startBeamSearch(){
		while(queue.isEmpty() == false){
			while(queue.isEmpty() == false){
				AnswerData ad = queue.poll();
				for(int i = 0; i < 4; i++){
					AnswerData d = ad.getClone();
					int bool = d.setNext(i);
					if(bool == -1)continue;//失�?
					if(bool == 1){//ゴール
//						System.out.println("GOAL");
						this.setStartOff(d);
						continue;
					}
					new Evaluater(d);
					this.storage.add(d);
				}
			}
			this.returnArrayToQueue();
		}
	}
	
	private void setStartOff(AnswerData ad){
		ArrayList<Pair> sPoints = AnswerData.getStartPoints();
		for(Pair p : sPoints){
			AnswerData tmp = ad.getClone();
			tmp.setCool(p.p1, p.p2, p.p3);
			new Evaluater(tmp);
			this.storage.add(tmp);
		}
	}
	
	private void returnArrayToQueue(){
		for(AnswerData ad : this.storage){
			this.queue.add(ad);
			int thisScore = ad.getScore();
			if(thisScore > this.maxScore){
				this.answerStock.add(ad);
				this.maxScore = thisScore;
//				System.out.println(this.maxScore);
			}
			if(this.queue.size() > MainALG.beamWidth){
				this.queue.poll();
			}
		}
		this.storage.clear();
	}
	
	public PriorityQueue<AnswerData> getAnswerData(){
		return answerStock;
	}

}
