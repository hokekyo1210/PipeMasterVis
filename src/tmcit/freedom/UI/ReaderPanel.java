package tmcit.freedom.UI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import tmcit.freedom.System.Answer;
import tmcit.freedom.System.Problem;
import tmcit.freedom.System.ProblemManager;
import tmcit.freedom.Util.DropFileHandler;

public class ReaderPanel extends JPanel implements ActionListener {
	private JTextField prbReader;
	private JTextField ansReader;

	private JButton prbBut;
	private JButton ansBut;

	private Problem problem;
	private Answer answer;
	private MainPanel mainPanel;

	public ReaderPanel(){
		this.setLayout(null);
		this.setBounds(5, 730, 720, 65);
		this.setOpaque(true);

		this.launchField();
		
		this.setBackground(Color.GRAY);
		this.setVisible(true);
	}
	
	public void setProblem(Problem problem){
		this.problem = problem;
	}
	
	public void setAnswer(Answer answer){
		this.answer = answer;
	}
	
	public void setMainPanel(MainPanel mainPanel){
		this.mainPanel = mainPanel;
	}
	
	private void launchField(){
		prbReader = new JTextField();
		ansReader = new JTextField();
		prbBut = new JButton("Load");
		ansBut = new JButton("Load");
		prbReader.setTransferHandler(new DropFileHandler(prbReader, prbBut));
		ansReader.setTransferHandler(new DropFileHandler(ansReader, ansBut));
		prbBut.addActionListener(this);
		ansBut.addActionListener(this);
		addInputPanel("Problem", prbReader, prbBut, 0);
		addInputPanel("Answer", ansReader, ansBut, 1);
	}

	private void addInputPanel(String taglabel, JTextField jtf, JButton jbtn, int count){
		JLabel label = new JLabel(taglabel);
		label.setHorizontalAlignment(JLabel.CENTER);
		jtf.setBorder(new BevelBorder(BevelBorder.LOWERED));

		label.setBounds(3, 4 + count*30, 50, 22);
		jtf.setBounds(53, 4 + count*30, 580, 22);
		jbtn.setBounds(645, 4 + count*30, 65, 22);

		this.add(label);
		this.add(jtf);
		this.add(jbtn);

		this.repaint();
	}
	
	public void setTextPrbReader(String dir){
		this.prbReader.setText(dir);
	}

	public void setTextAnsReader(String dir){
		this.ansReader.setText(dir);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == prbBut){
			if(prbReader.getText().equalsIgnoreCase(""))return;
			File file = new File(prbReader.getText());
			this.readProblem(file);
		}else if(e.getSource() == ansBut){
			if(ansReader.getText().equalsIgnoreCase(""))return;
			File file = new File(ansReader.getText());
			this.readAnswerFromFile(file);
		}
	}

	private void readProblem(File file){
		if(DropFileHandler.isText(file)){
			String path = file.getAbsolutePath();
			if(ProblemManager.readProblem(path, this.problem)){
				mainPanel.setData(this.problem);
				mainPanel.paintBoard(false);
			}
		}
	}
	
	private void readAnswerFromFile(File file){
		if(this.problem == null)return;
		if(DropFileHandler.isText(file)){
			String path = file.getAbsolutePath();
			if(ProblemManager.readAnswer(path, this.answer)){
				mainPanel.setData(this.problem, this.answer);
				mainPanel.paintBoard(true);
			}
		}
	}

}
