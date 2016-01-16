package tmcit.freedom.UI.ProblemEdit;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import tmcit.freedom.System.Problem;
import tmcit.freedom.System.ProblemManager;
import tmcit.freedom.UI.MainPanel;
import tmcit.freedom.UI.PipePanel;
import tmcit.freedom.Util.DropFileHandler;
import tmcit.freedom.Util.PipeType;

public class WriterReaderPanel  extends JPanel implements ActionListener {
	private JTextField readField;
	private JTextField saveField;

	private JButton redBut;
	private JButton savBut;

	private Problem problem;
	private MainPanel mainPanel;

	public WriterReaderPanel(){
		this.setLayout(null);
		this.setBounds(5, 730, 720, 65);
		this.setOpaque(true);

		this.launchField();
		
		this.setBackground(Color.GRAY);
		this.setVisible(true);
	}
	
	public void setProblemEditor(MainPanel mainPanel){
		this.mainPanel = mainPanel;
	}
	
	public void setProblme(Problem problem){
		this.problem = problem;
	}
	
	private void launchField(){
		this.readField = new JTextField(); 
		this.saveField = new JTextField();
		this.redBut = new JButton("Load");
		this.savBut = new JButton("Save");		
		this.readField.setTransferHandler(new DropFileHandler(readField, redBut));
		this.saveField.setTransferHandler(new DropFileHandler(saveField, null));
		this.redBut.addActionListener(this);
		this.savBut.addActionListener(this);
		this.addInputPanel("Load", readField, redBut, 0);
		this.addInputPanel("Save", saveField, savBut, 1);
	}

	public void addInputPanel(String taglabel, JTextField jtf, JButton jbtn, int count){
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == redBut){
			if(readField.getText().equalsIgnoreCase(""))return;
			File file = new File(readField.getText());
			this.readProblem(file);
		}else if(e.getSource() == savBut){
			if(saveField.getText().equalsIgnoreCase(""))return;
			File file = new File(saveField.getText());
			this.saveProblemFromFile(file);
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

	private void saveProblemFromFile(File file) {
		try{
			if(file.exists()){
				System.out.println("War:File Exists at Save Path.");
			}
			FileWriter filewriter = new FileWriter(file);
			PipePanel[][] prob = this.mainPanel.getPipePanel();
			if(prob == null){
				System.out.println("Err:You Dont Make Problem.");
				return;
			}
			for(int i = 0; i < 30; i++){
				String str = "";
				for(int j = 0; j < 30; j++){
					PipeType type = prob[i][j].getPipeType();
					if(type == PipeType.BLO){
						str += "#";
					}else if(type == PipeType.STR){
						str += "S";
					}else if(type == PipeType.GOL){
						str += "G";
					}else{
						str += ".";
					}
				}
				filewriter.write(str + "\r\n");
			}
			
			int limI = problem.getLimI();
			int limL = problem.getLimL();
			int limX = problem.getLimX();

			filewriter.write(String.valueOf(limI) + " ");
			filewriter.write(String.valueOf(limL) + " ");
			filewriter.write(String.valueOf(limX) + "\r\n");

			filewriter.close();
		}catch(IOException e){
			System.out.println(e);
		}
	}

	public void setTextPrbReader(String dir) {
		this.readField.setText(dir);		
	}
}
