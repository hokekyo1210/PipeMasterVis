package tmcit.freedom.UI.Visualize;

import java.awt.Color;

import tmcit.freedom.System.Answer;
import tmcit.freedom.System.Problem;
import tmcit.freedom.System.ProblemManager;
import tmcit.freedom.UI.MainPanel;
import tmcit.freedom.UI.PipePanel;
import tmcit.freedom.UI.ProblemData;
import tmcit.freedom.Util.PipeType;

public class ViewerPanel extends MainPanel {
	private ProblemData problemData;
	private boolean isPaintInPipe;
	
	
	public ViewerPanel(){
		this.setLayout(null);
		this.setBounds(5, 5, 720, 720);
		this.setBackground(Color.LIGHT_GRAY);

		this.setField();
		this.initilize();
		
		this.isPaintInPipe = false;

		this.setVisible(true);
	}
	
	public void setProblemData(ProblemData problemData){
		this.problemData = problemData;
	}
	
	@Override
	public void paintBoard(boolean f){
		for(int i = 0; i < 30; i++){
			for(int j = 0; j < 30; j++){
			if(f && this.isPaintInPipe && routeMemo[i + 1][j + 1] == false)
				this.pipePanel[i][j].paintInPipe();
			else
				this.pipePanel[i][j].paintPipe();
			}
		}
		this.repaint();
	}
	
	@Override
	public boolean setData(Problem problem){
		boolean sw = true;
		PipeType[][] t = problem.getBoard();
		for(int i = 0; i < 30; i++){
			for(int j = 0; j < 30; j++){
				this.pipePanel[i][j].setPipeType(t[i][j]);
			}
		}
		this.problemData.setData(problem);
		return sw;
	}

	@Override
	public boolean setData(Problem problem, Answer answer){
		boolean sw = true;
		this.board = ProblemManager.getAscertain(problem, answer);
		for(int i = 0; i < 30; i++){
			for(int j = 0; j < 30; j++){
				this.pipePanel[i][j].setPipeType(board[i][j]);
			}
		}
		this.routeMemo = ProblemManager.getRouteMemo(board);
		this.problemData.setScore(ProblemManager.getScore(routeMemo));
		this.problemData.setData(answer);
		return sw;
	}


	public PipePanel[][] getPipePanel() {
		return this.pipePanel;
	}

	public void setPaintInPipe(boolean f){
		this.isPaintInPipe = f;
	}
	
	public boolean getPaintInPipe(){
		return this.isPaintInPipe;
	}

}
