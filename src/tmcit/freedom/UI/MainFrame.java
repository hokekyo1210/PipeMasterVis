package tmcit.freedom.UI;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import tmcit.freedom.System.Main;
import tmcit.freedom.System.ProblemDownloader;
import tmcit.freedom.UI.AnswerEdit.AnswerPanel;
import tmcit.freedom.UI.ProblemEdit.ProblemPanel;
import tmcit.freedom.UI.Visualize.VisualizePanel;


public class MainFrame extends JFrame implements ActionListener {
	final public static int Width  = 900;
	final public static int Height = 885;
	
	private String title;
	private JTabbedPane tabbedpane;

	///Visualizer Pane
	private VisualizePanel visualizePanel;
	
	//ProblemEditor Pane
	private ProblemPanel problemPanel;
	
	//AnswerEditor Pane
	private AnswerPanel answerPanel;

	public MainFrame(String title){
		this.title = title;

		this.initialize();

		this.setVisible(true);
	}
	
	private void initialize(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(title);
		this.setSize(Width, Height);

		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setLayout(null);

		this.setTabbedpane();

		this.setMenuBar();
//		this.addKeyListener(new MyKeyListener());

	}
	
	private void setTabbedpane(){
		this.tabbedpane = new JTabbedPane();
		this.tabbedpane.setBounds(0, 0, Width, 830);

		this.visualizePanel = new VisualizePanel();
		this.visualizePanel.setLayout(null);
		this.tabbedpane.add("Viewer", visualizePanel);
		
		this.problemPanel = new ProblemPanel();
		this.problemPanel.setLayout(null);
		this.tabbedpane.add("ProbEditor", problemPanel);

		this.answerPanel = new AnswerPanel();
		this.answerPanel.setLayout(null);
		this.tabbedpane.add("AnsEditor", answerPanel);

		this.add(tabbedpane);
	}
	
	private void setMenuBar(){
		JMenuBar menubar = new JMenuBar();
		JMenu menu1 = new JMenu("File");

		JMenuItem menuitem11 = new JMenuItem("ProblemOpen");
		JMenuItem menuitem12 = new JMenuItem("AnswerOpen");
		JMenu menu13 = new JMenu("Practice Problem");
		
		JMenuItem menuitem13 = new JMenuItem("Initilize");
		JMenuItem menuitem14 = new JMenuItem("Reflesh");
		JMenuItem menuitem15 = new JMenuItem("Exit");
		
		int num = ProblemDownloader.getProblemNum();
		for(int i = 0; i < num; i++){
			String fileName = ProblemDownloader.getFileName(i + 1);
			JMenuItem item = new JMenuItem(fileName);
			item.addActionListener(this);
			menu13.add(item);
		}

		menuitem11.addActionListener(this);
		menuitem12.addActionListener(this);
		menuitem13.addActionListener(this);
		menuitem14.addActionListener(this);
		menuitem15.addActionListener(this);

		menu1.add(menuitem11);
		menu1.add(menuitem12);
		menu1.add(menu13);
		menu1.add(menuitem13);
		menu1.add(menuitem14);
		menu1.add(menuitem15);

		JMenu menu2 = new JMenu("ALG");
		
		JMenuItem menuitem21 = new JMenuItem("Start");

		menuitem21.addActionListener(this);
		
		menu2.add(menuitem21);

		menubar.add(menu1);
		menubar.add(menu2);
		this.setJMenuBar(menubar);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o instanceof JMenuItem){
			this.actionMenuEvent((JMenuItem) e.getSource());
		}
	}

	private void actionMenuEvent(JMenuItem item) {
		String text = item.getText();
		if(text.equalsIgnoreCase("Exit")){
			System.exit(0);
		}else if(text.equalsIgnoreCase("Initilize")){
			int option = JOptionPane.showConfirmDialog(this, 
					"This App will do the initialization of the currently selected tab.  Are you OK?",
					"Initilize",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE
				);

			if (option == JOptionPane.YES_OPTION){
				this.setTitle(Main.appName);
				if(this.tabbedpane.getSelectedIndex() == 0){//Viewer
					this.visualizePanel.initilize();
				}else if(this.tabbedpane.getSelectedIndex() == 1){//ProbEditor
					this.problemPanel.initilize();
				}else if(this.tabbedpane.getSelectedIndex() == 2){//AnswEditor
					this.answerPanel.initilize();
				}
				this.requestFocusInWindow();
				this.repaint();
			}

		}else if(text.equalsIgnoreCase("Reflesh")){
			this.requestFocusInWindow();
			this.repaint();

		}else if(text.equalsIgnoreCase("ProblemOpen")){
			FileDialog dialog = new FileDialog(this, "Select Problem File");
			dialog.setVisible(true);
			String dir = dialog.getDirectory() + dialog.getFile();
			if(dialog.getDirectory() != null && dialog.getFile() != null){
				this.openProblem(dir);
			}
		}else if(text.equalsIgnoreCase("AnswerOpen")){
			if(this.tabbedpane.getSelectedIndex() == 1){return;}
			FileDialog dialog = new FileDialog(this, "Select Answer File");
			dialog.setVisible(true);
			String dir = dialog.getDirectory() + dialog.getFile();
			if(dialog.getDirectory() != null && dialog.getFile() != null){
				this.openAnswer(dir);
			}
		}else if(text.equals("Start")){
			String message = this.visualizePanel.start();
			String[] tmp = message.split("#");
			int color = Integer.parseInt(tmp[0]);

			if(color == -1)return;

			JLabel label = new JLabel(tmp[1]);
			if(color == 0){
				label.setForeground(Color.BLUE);
			}else if(color == 1){
				label.setForeground(Color.RED);
			}else{
				label.setForeground(Color.BLACK);
			}
			JOptionPane.showMessageDialog(this, label);
		}else{
			int num = ProblemDownloader.getProblemNum();
			for(int i = 0; i < num; i++){
				String file = ProblemDownloader.getFileName(i + 1);
				if(text.equals(file)){
					this.openProblem(ProblemDownloader.getDirectory() + file);
				}
			}
		}
	}
	
	private void openProblem(String dir){
		if(this.tabbedpane.getSelectedIndex() == 0){
			this.visualizePanel.readProblem(dir);
		}else if(this.tabbedpane.getSelectedIndex() == 1){
			this.problemPanel.readProblem(dir);
		}else if(this.tabbedpane.getSelectedIndex() == 2){
			this.answerPanel.readProblem(dir);
		}
	}

	private void openAnswer(String dir){
		if(this.tabbedpane.getSelectedIndex() == 0){
			this.visualizePanel.readAnswer(dir);
		}else if(this.tabbedpane.getSelectedIndex() == 2){
			this.answerPanel.readAnswer(dir);
		}
	}
	
	public void error(String str){
		this.setTitle("Error!!");
		JLabel label = new JLabel(str);
		label.setForeground(Color.RED);
		JOptionPane.showMessageDialog(this, label);
		this.setTitle(Main.appName);
	}

}
