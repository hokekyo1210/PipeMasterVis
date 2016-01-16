package tmcit.freedom.UI.Visualize;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import tmcit.freedom.Algorithm.MyThread;
import tmcit.freedom.System.Answer;
import tmcit.freedom.System.Problem;
import tmcit.freedom.System.ProblemManager;
import tmcit.freedom.UI.ProblemData;
import tmcit.freedom.UI.ReaderPanel;

public class VisualizePanel extends JPanel implements ActionListener {
	private JCheckBox checkbox;

	private ViewerPanel viewerPanel;
	private ReaderPanel readPanel;

	private ProblemData problemData;
	private Problem problem;
	private Answer answer;

	private AnswerArrayPanel answerList;
	private JViewport view;
	private JScrollPane scrollPane;

	private MyThread runningList;
	
	public VisualizePanel(){
		this.problem = new Problem();
		this.answer = new Answer();

		this.viewerPanel = new ViewerPanel();
		this.add(viewerPanel);

		this.readPanel = new ReaderPanel();	
		this.readPanel.setMainPanel(viewerPanel);
		this.readPanel.setProblem(problem);
		this.readPanel.setAnswer(answer);
		this.add(readPanel);
		
		this.problemData = new ProblemData();
		this.add(problemData);
		
		this.setArrayLists();

		this.viewerPanel.setProblemData(this.problemData);
		
		this.setCheckBox();
	}
	
	public void initilize(){
		this.problem.initilize();
		this.answer.initilize();
		this.viewerPanel.initilize();
		this.problemData.initilize();
		this.answerList.initilize();
	}
	
	private void setCheckBox(){
		this.checkbox = new JCheckBox("InPipeMode", true);
		this.checkbox.setLayout(null);
		this.checkbox.setBounds(730, 730, 150, 20);
		this.checkbox.setVisible(true);
		this.add(this.checkbox);
		this.checkbox.addActionListener(this);
	}
	
	private void setArrayLists(){
		this.scrollPane = new JScrollPane();
		this.answerList = new AnswerArrayPanel();
    	this.scrollPane.setViewportView(this.answerList);

    	this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    	this.scrollPane.setBounds(730, 5, 158, 355);

		this.scrollPane.getHorizontalScrollBar().setUnitIncrement(10);

		this.view = scrollPane.getViewport();
		this.view.setView(this.answerList);
		this.answerList.setVP(this);
		this.add(this.scrollPane);
	}
	
	public void readProblem(String dir){
		if(ProblemManager.readProblem(dir, problem)){
			this.viewerPanel.setData(problem);
			this.problemData.setData(problem);
			this.paintBoard(false);
			this.readPanel.setTextPrbReader(dir);
		}else{
			System.out.println("Problem Read Error");
		}
	}

	public void readAnswer(String dir){
		if(ProblemManager.readAnswer(dir, answer)){
			this.setAnswer();
			this.readPanel.setTextAnsReader(dir);
		}else{
			System.out.println("Answer Read Error");
		}
	}
	
	public void setAnswer(){
		this.viewerPanel.setData(problem, this.answer);
		this.problemData.setData(this.answer);
		this.paintBoard(true);
	}
	
	public void setAnswer(Answer answer){
		ProblemManager.copyAnswer(answer, this.answer);
		this.setAnswer();
//		System.out.println("setAns");
	}

	private void paintBoard(boolean f){
		this.viewerPanel.paintBoard(f);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o instanceof JCheckBox){
			JCheckBox cb = (JCheckBox) o;
			boolean flag = cb.isSelected();
			this.viewerPanel.setPaintInPipe(flag);
		}
	}

	///0:BLUE 1:RED Another:BLACK
	public String start(){
		if(problem.getExist() == false)return "1#Problem Not Found. You must show Problem.";

		runningList = new MyThread();
		runningList.setProblem(problem);
		runningList.setAnsList(answerList);
		runningList.setVisuPanel(this);
		runningList.start();

		return "-1#return";
	}

}
