package tmcit.freedom.UI;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JTextField;

import tmcit.freedom.System.Answer;
import tmcit.freedom.System.Problem;

public class ProblemData extends JPanel {
	private JTextField title;

	private int spaceNum;
	private int limI, limL, limX;
	private int sgNum;
	
	private int score;
	private int nowI, nowL, nowX;
	
	private GraphBar[] graph;

	public ProblemData(){
		this.setLayout(null);
		this.setBounds(730, 365, 158, 360);
		this.setBackground(Color.GRAY);

		this.setGraphPanel();
		this.setTextField();
		this.initilize();

		this.setVisible(true);
	}
	
	public void initilize(){
		this.spaceNum = 0;
		this.limI = 0;
		this.limL = 0;
		this.limX = 0;
		this.sgNum = 0;

		this.score = 0;
		this.nowI = 0;
		this.nowL = 0;
		this.nowX = 0;
		this.setGraphData();
	}

	public void setData(Problem problem){
		this.nowI = 0;
		this.nowL = 0;
		this.nowX = 0;
		this.score = 0;
		this.spaceNum = problem.getSpaceNum();
		this.sgNum = problem.getSGNum();
		this.limI = problem.getLimI();
		this.limL = problem.getLimL();
		this.limX = problem.getLimX();
		this.setGraphData();
		this.repaint();
	}

	public void setData(Answer answer){
		this.nowI = answer.getNowI();
		this.nowL = answer.getNowL();
		this.nowX = answer.getNowX();
		this.setGraphData();
		this.repaint();
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setGraphData(){
		GraphBar b;
		b = graph[0];
		b.setData((double)nowI, (double)spaceNum);
		b = graph[1];
		b.setData((double)limI, (double)spaceNum);
		b = graph[2];
		b.setData((double)nowL, (double)spaceNum);
		b = graph[3];
		b.setData((double)limL, (double)spaceNum);
		b = graph[4];
		b.setData((double)nowX, (double)spaceNum);
		b = graph[5];
		b.setData((double)limX, (double)spaceNum);
		String str = "";
		str += "Score:" + String.valueOf(score) + "/" + String.valueOf(spaceNum);
		this.title.setText(str);
	}
	

	private void setTextField() {
		this.title = new JTextField("Graph");
		this.title.setLayout(null);
		this.title.setBounds(10, 10, 138, 30);
		this.title.setBorder(null);
		this.title.setMargin(new Insets(10, 10, 10, 10));
		this.title.setEnabled(false);
		this.title.setDisabledTextColor(Color.BLACK);
		this.title.setBackground(Color.GRAY);
		this.add(this.title);
	}
	
	private void setGraphPanel(){
		this.graph = new GraphBar[6];
		for(int i = 0; i < 6; i++){
			Color c = Color.RED;
			if(i%2 == 0)c = Color.BLUE;
			GraphBar gb = new GraphBar(i, c, Color.LIGHT_GRAY);
			this.graph[i] = gb;
			this.add(gb);
		}
	}
	

//	public void debug(){
//		final PipeType[][] b = ProblemManager.getProblemBoard();
//		if(b == null)return;
//		System.out.println(limI);
//		System.out.println(limL);
//		System.out.println(limX);
//		System.out.println(spaceNum);
//		System.out.println(sgNum);
//		for(int i = 0; i < 30; i++){
//			for(int j = 0; j < 30; j++){
//				System.out.println(b[i][j]);
//			}
//		}
//	}

}
