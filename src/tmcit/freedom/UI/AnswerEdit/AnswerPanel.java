package tmcit.freedom.UI.AnswerEdit;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import tmcit.freedom.System.Answer;
import tmcit.freedom.System.Problem;
import tmcit.freedom.System.ProblemManager;
import tmcit.freedom.UI.ChooserPanel;
import tmcit.freedom.UI.PipePanel;
import tmcit.freedom.UI.ProblemData;
import tmcit.freedom.Util.PipeType;

public class AnswerPanel extends JPanel implements MouseListener, MouseWheelListener {
	private Problem problem;
	private Answer answer;

	private AnswerEditor answerEditorPanel;
	private ReaderWriterPanel readerPanel;
	private JViewport view;
	private JScrollPane scrollPane;
	private ChooserPanel chooserPanel;
	
	private ProblemData problemData;

	public AnswerPanel(){
		this.problem = new Problem();
		this.answer = new Answer();

		this.answerEditorPanel = new AnswerEditor();
		this.add(this.answerEditorPanel);

		this.problemData = new ProblemData();
		this.add(this.problemData);

		this.readerPanel = new ReaderWriterPanel();
		this.readerPanel.setMainPanel(this.answerEditorPanel);
		this.readerPanel.setProblem(this.problem);
		this.readerPanel.setAnswer(this.answer);
		this.readerPanel.setProblemData(this.problemData);
		this.add(this.readerPanel);
		
		this.setScrollPane();
		this.setMouseListner();
	}

	private void setScrollPane() {
		this.scrollPane = new JScrollPane();
		this.chooserPanel = new ChooserPanel(PipeType.getAllPipeType());
    	this.scrollPane.setViewportView(this.chooserPanel);

    	this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    	this.scrollPane.setBounds(730, 5, 158, 360);

		this.scrollPane.getHorizontalScrollBar().setUnitIncrement(10);

		this.view = scrollPane.getViewport();
		this.view.setView(this.chooserPanel);
		this.add(this.scrollPane);
	}
	
	public void setMouseListner(){
		PipePanel[][] pp = this.answerEditorPanel.getPipePanel();
		for(int i = 0; i < 30; i++){
			for(int j = 0; j < 30; j++){
				pp[i][j].addMouseListener(this);
				pp[i][j].addMouseWheelListener(this);

			}
		}
	}
	
	public void initilize(){
		this.answerEditorPanel.initilize();
		this.chooserPanel.setNowIndex(0);
		this.problem.initilize();
		this.answer.initilize();
		this.problemData.initilize();
	}

	public void readProblem(String dir){
		if(ProblemManager.readProblem(dir, problem)){
			this.answerEditorPanel.setData(problem);
			this.problemData.setData(problem);
			this.answerEditorPanel.paintBoard(false);
			this.readerPanel.setTextPrbReader(dir);
		}else{
			System.out.println("Problem Read Error");
		}
	}

	public void readAnswer(String dir){
		if(ProblemManager.readAnswer(dir, answer)){
			this.answerEditorPanel.setData(problem, answer);
			this.problemData.setData(answer);
			this.answerEditorPanel.paintBoard(true);
			this.readerPanel.setTextAnsReader(dir);
		}else{
			System.out.println("Answer Read Error");
		}
	}

	private int clk = -1;
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.clk = e.getButton();
		Component c = e.getComponent();
		if(c instanceof PipePanel){
			this.mouseEdit(e, (PipePanel)c);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.clk = -1;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(clk != -1){
			Component c = e.getComponent();
			if(c instanceof PipePanel){
				this.mouseEdit(e, (PipePanel)c);
			}
		}
	}
	
	private void mouseEdit(MouseEvent e, PipePanel pp){
		if(problem.getExist() == false)return;
		PipeType pressType = PipeType.EMP;
		if(clk == MouseEvent.BUTTON1){
			pressType = this.chooserPanel.getNowType();
		}
		final int x = pp.getAtx();
		final int y = pp.getAty();
		if(this.answerEditorPanel.canPut(x, y) == false)return;
		this.answerEditorPanel.paintPipe(x, y, pressType);
	}


	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		Component c = e.getComponent();
		if(c instanceof PipePanel){
			this.chooseIndex(e.getWheelRotation());
		}
	}

	private void chooseIndex(int wheelRotation) {
		if(wheelRotation < 0)this.chooserPanel.chooseUp(true);
		else this.chooserPanel.chooseUp(false);
	}
}
