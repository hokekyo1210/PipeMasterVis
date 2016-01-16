package tmcit.freedom.UI.ProblemEdit;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import tmcit.freedom.System.Problem;
import tmcit.freedom.System.ProblemManager;
import tmcit.freedom.UI.ChooserPanel;
import tmcit.freedom.UI.PipePanel;
import tmcit.freedom.Util.PipeType;


public class ProblemPanel extends JPanel implements MouseListener, ActionListener, MouseWheelListener {
	private Problem problem;

	private ProblemEditorPanel problemEditorPanel;

	private JViewport view;
	private JScrollPane scrollPane;
	private ChooserPanel chooserPanel;

	private WriterReaderPanel savePanel;
	private GenerateSet generateSet;
	private DataSliderPanel dataSliderPanel;
	
	public ProblemPanel(){
		this.problem = new Problem();

		this.problemEditorPanel = new ProblemEditorPanel();
		this.add(this.problemEditorPanel);
		
		this.dataSliderPanel = new DataSliderPanel();
		this.dataSliderPanel.setProblem(problem);
		this.dataSliderPanel.initilize();
		this.add(this.dataSliderPanel);
		
		this.generateSet = new GenerateSet();
		this.add(this.generateSet);
		this.generateSet.getButton().addActionListener(this);

		this.savePanel = new WriterReaderPanel();
		this.savePanel.setProblemEditor(this.problemEditorPanel);
		this.savePanel.setProblme(this.problem);
		this.add(this.savePanel);

		PipePanel[][] pp = this.problemEditorPanel.getPipePanel();
		for(int i = 0; i < 30; i++){
			for(int j = 0; j < 30; j++){
				pp[i][j].addMouseListener(this);
				pp[i][j].addMouseWheelListener(this);
			}
		}

		this.setScrollPane();
	}
	
	private void setScrollPane() {
		this.scrollPane = new JScrollPane();
		this.chooserPanel = new ChooserPanel(PipeType.getAllBlockType());
    	this.scrollPane.setViewportView(this.chooserPanel);

    	this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    	this.scrollPane.setBounds(730, 5, 158, 203);

		this.scrollPane.getHorizontalScrollBar().setUnitIncrement(10);

		this.view = scrollPane.getViewport();
		this.view.setView(this.chooserPanel);
		this.add(this.scrollPane);
	}


	public void initilize() {
		this.problemEditorPanel.initilize();
		this.chooserPanel.setNowIndex(0);
		this.dataSliderPanel.initilize();

	}
	
//	public void generateProblem(){
//		int n = 0;
//		long seed = 1;
//		YuruconGen gen = new YuruconGen(n, seed);
//		gen.generateProblem(this.problem);
//	}

	public void readProblem(String dir){
		if(ProblemManager.readProblem(dir, problem)){
			this.problemEditorPanel.setData(problem);
			this.dataSliderPanel.setProblem(problem);
			this.problemEditorPanel.paintBoard(false);
			this.savePanel.setTextPrbReader(dir);
		}else{
			System.out.println("Problem Read Error");
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	private int pressX = -1, pressY = -1;
	private int clk = -1;

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
		PipeType pressType = PipeType.EMP;
		if(clk == MouseEvent.BUTTON1){
			pressType = this.chooserPanel.getNowType();
		}
		final int x = pp.getAtx();
		final int y = pp.getAty();

		if(e.isShiftDown()){
			if(pressX < 0 || 30 <= pressX || pressY < 0 || 30 <= pressY){
				this.pressX = x;
				this.pressY = y;
				this.problemEditorPanel.paintPipe(x, y, pressType);
			}else{
				this.problemEditorPanel.paintPipe(x, y, pressX, pressY, pressType);
				this.pressX = this.pressY = -1;
			}
		}else if(e.isControlDown()){
			this.problemEditorPanel.paintPenki(x, y, pressType);
		}else{
			this.problemEditorPanel.paintPipe(x, y, pressType);
		}
		///this.repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(e.isShiftDown() == false){
			this.pressX = -1;
			this.pressY = -1;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o instanceof JButton){
			JButton jbt = (JButton) o;
			if(jbt.getText().equals("Generate")){
				this.generate();
			}
		}
	}
	
	public void generate(){
		int n = this.generateSet.getN();
		long seed = this.generateSet.getSeed();
		YuruconGen gen = null;
		try {
			gen = new YuruconGen(n, seed);
		} catch (Exception e) {
			ProblemManager.error("Generate Error.");
			return;
		}
		gen.generateProblem(problem);
		this.problemEditorPanel.setData(problem);
		this.problemEditorPanel.paintBoard(false);
		this.dataSliderPanel.setProblem();

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
