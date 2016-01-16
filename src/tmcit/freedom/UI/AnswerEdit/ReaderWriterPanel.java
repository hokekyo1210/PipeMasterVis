package tmcit.freedom.UI.AnswerEdit;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import tmcit.freedom.System.Answer;
import tmcit.freedom.System.Problem;
import tmcit.freedom.System.ProblemManager;
import tmcit.freedom.UI.MainPanel;
import tmcit.freedom.UI.ProblemData;
import tmcit.freedom.Util.DropFileHandler;

public class ReaderWriterPanel extends JPanel implements ActionListener {
	private JTextField prbReader;
	private JTextField ansReader;

	private JButton prbBut;
	private JButton ansBut;
	private JButton ansSavBut;

	private Problem problem;
	private Answer answer;
	private ProblemData problemData;
	private MainPanel mainPanel;

	public ReaderWriterPanel(){
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

	public void setProblemData(ProblemData problemData){
		this.problemData = problemData;
	}
	
	private void launchField(){
		prbReader = new JTextField();
		ansReader = new JTextField();
		prbBut = new JButton("Load");
		ansBut = new JButton("Load");
		ansSavBut = new JButton("Save");

		prbReader.setTransferHandler(new DropFileHandler(prbReader, prbBut));
		ansReader.setTransferHandler(new DropFileHandler(ansReader, null));
		ansReader.setTransferHandler(new DropFileHandler(ansReader, null));

		prbBut.addActionListener(this);
		ansBut.addActionListener(this);
		ansSavBut.addActionListener(this);

		addInputPanel("Problem", prbReader, prbBut, 0);
		addInputPanel("Answer", ansReader, ansBut, 1);
	}	

	private void addInputPanel(String taglabel, JTextField jtf, JButton jbtn, int count){
		JLabel label = new JLabel(taglabel);
		label.setHorizontalAlignment(JLabel.CENTER);
		jtf.setBorder(new BevelBorder(BevelBorder.LOWERED));

		if(count == 1){
			label.setBounds(3, 4 + count*30, 50, 22);
			jtf.setBounds(53, 4 + count*30, 515, 22);

			jbtn.setBounds(577, 4 + count*30, 65, 22);
			this.ansSavBut.setBounds(645, 4 + count*30, 65, 22);
			this.add(this.ansSavBut);
		}else{
			label.setBounds(3, 4 + count*30, 50, 22);
			jtf.setBounds(53, 4 + count*30, 580, 22);
			jbtn.setBounds(645, 4 + count*30, 65, 22);
		}

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
	
		}else if(e.getSource() == ansSavBut){
			if(ansReader.getText().equalsIgnoreCase(""))return;
			File file = new File(ansReader.getText());
			this.saveAnswerFromFile(file);
		}
	}

	private void readProblem(File file){
		if(DropFileHandler.isText(file)){
			String path = file.getAbsolutePath();
			if(ProblemManager.readProblem(path, this.problem)){
				this.mainPanel.setData(this.problem);
				this.mainPanel.paintBoard(false);
				this.problemData.setData(this.problem);
			}
		}
	}
	
	private void readAnswerFromFile(File file){
		if(this.problem == null)return;
		if(DropFileHandler.isText(file)){
			String path = file.getAbsolutePath();
			if(ProblemManager.readAnswer(path, this.answer)){
				this.mainPanel.setData(this.problem, this.answer);
				this.mainPanel.paintBoard(true);
				this.problemData.setData(this.answer);
			}
		}
	}

	private void saveAnswerFromFile(File file) {
		try{
			if(file.exists()){
				System.out.println("War:File Exists at Save Path.");
			}
			FileWriter filewriter = new FileWriter(file);
			AnswerEditor ansEditor = (AnswerEditor) mainPanel;
			if(ansEditor == null){
				System.out.println("Err:Answer Cast Error.");
				return;
			}
			ArrayList<String> array = ansEditor.outAnswer();

			filewriter.write(String.valueOf(array.size()) + "\r\n");
			for(String str: array){
				filewriter.write(str);
			}

			filewriter.close();
		}catch(IOException e){
			System.out.println(e);
		}
	}

}
