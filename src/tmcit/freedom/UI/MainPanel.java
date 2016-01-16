package tmcit.freedom.UI;

import javax.swing.JPanel;

import tmcit.freedom.System.Answer;
import tmcit.freedom.System.Problem;
import tmcit.freedom.System.ProblemManager;
import tmcit.freedom.Util.PipeType;

public abstract class MainPanel extends JPanel {
	protected String score;
	protected PipeType[][] board;
	protected PipePanel[][] pipePanel;
	protected boolean[][] routeMemo;
	
	public void setField(){
		this.board = new PipeType[30][30];
		this.routeMemo = new boolean[32][32];
		this.pipePanel = new PipePanel[30][30];
		for(int i = 0; i < 30; i++){
			for(int j = 0; j < 30; j++){
				PipePanel tmp = new PipePanel(j, i, PipeType.NULL);
				tmp.setOpaque(true);
				this.add(tmp);
				this.pipePanel[i][j] = tmp;
			}
		}
	}

	public void initilize(){
		this.score = "No Score";
		for(int i = 0; i < 30; i++){
			for(int j = 0; j < 30; j++){
				this.routeMemo[i][j] = true;
				this.board[i][j] = PipeType.NULL;
				this.pipePanel[i][j].paintPipe(PipeType.NULL);
			}
		}
	}
	
	public void paintBoard(boolean f){
		for(int i = 0; i < 30; i++){
			for(int j = 0; j < 30; j++){
				pipePanel[i][j].paintPipe();
			}
		}
		this.repaint();
	}

	public PipePanel[][] getPipePanel(){
		return pipePanel;
	}
	
	public PipeType[][] getPipeType(){
		return board;
	}

	public boolean setData(Problem problem){
		boolean sw = true;
		PipeType[][] t = problem.getBoard();
		for(int i = 0; i < 30; i++){
			for(int j = 0; j < 30; j++){
				this.pipePanel[i][j].setPipeType(t[i][j]);
			}
		}
		return sw;
	}

	public boolean setData(Problem problem, Answer answer){
		boolean sw = true;
		this.board = ProblemManager.getAscertain(problem, answer);
		for(int i = 0; i < 30; i++){
			for(int j = 0; j < 30; j++){
				this.pipePanel[i][j].setPipeType(board[i][j]);
			}
		}
		this.routeMemo = ProblemManager.getRouteMemo(board);
		return sw;
	}

}
