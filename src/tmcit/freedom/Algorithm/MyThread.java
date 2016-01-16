package tmcit.freedom.Algorithm;

import java.util.PriorityQueue;

import tmcit.freedom.System.Problem;
import tmcit.freedom.UI.Visualize.AnswerArrayPanel;
import tmcit.freedom.UI.Visualize.VisualizePanel;

public class MyThread extends Thread{

	private boolean isEnd = false;
	private Problem problem;
	
	private AnswerArrayPanel arrayPanel;
	private VisualizePanel visuPanel;

	private MainALG algorithm;
	
	public void run(){
		this.algorithm = new MainALG(problem);
		
		this.algorithm.start();

		PriorityQueue<AnswerData> que = this.algorithm.getAnswerData();
		while(que.isEmpty() == false){
			this.arrayPanel.addAnswer(que.poll().makeAnswer());
		}
		this.visuPanel.repaint();
		this.isEnd = true;
		return;
	}

	public void setProblem(Problem problem){
		this.problem = problem;
	}

	public void setAnsList(AnswerArrayPanel answerList) {
		this.arrayPanel = answerList;
	}

	public void setVisuPanel(VisualizePanel visuPanel){
		this.visuPanel = visuPanel;
	}
	public boolean isEnd(){
		return isEnd;
	}

}

