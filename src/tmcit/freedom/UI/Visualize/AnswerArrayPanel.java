package tmcit.freedom.UI.Visualize;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import tmcit.freedom.System.Answer;
import tmcit.freedom.System.ProblemManager;
import tmcit.freedom.Util.DropFileHandler;

public class AnswerArrayPanel extends JPanel implements ActionListener, MouseListener {
	
	private int index;
	private JTextField dir;
	private JButton bot;
	private ArrayList<AnswerPanel> lists;
	
	private VisualizePanel vp;

	public AnswerArrayPanel(){
		this.setLayout(null);
		this.setBounds(730, 5, 158, 360);
		this.setBackground(Color.LIGHT_GRAY);

		this.lists = new ArrayList<AnswerPanel>();
		this.dir = new JTextField();
		this.bot = new JButton();
		this.setTransferHandler(new DropFileHandler(this.dir, this.bot));
		this.bot.addActionListener(this);

		this.setVisible(true);
	}
	
	public void addAnswer(Answer answer){
		AnswerPanel ap = new AnswerPanel(lists.size(), answer);
		int reSize = AnswerPanel.Height*(lists.size() + 1);
		this.setPreferredSize(new Dimension(158, Math.max(reSize, 360)));
		this.lists.add(ap);
		ap.addMouseListener(this);
		ap.getBut().addActionListener(this);
		this.add(ap);
//		System.out.println("Add Answer No:" + lists.size());
		this.repaint();
	}
	
	public void setVP(VisualizePanel vp){
		this.vp = vp;
	}

	public void initilize(){
		this.index = 0;
		this.lists.clear();
		this.removeAll();
		this.setPreferredSize(new Dimension(158, 360));
	}
	
	public void setIndex(int index){
		if(this.lists.size() == 0)return;

		this.lists.get(this.index).fixPanel();
		this.lists.get(index).choosePanel();

		this.index = index;
		this.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(this.bot)){
			String d = dir.getText();
			Answer answer = new Answer();
			if(ProblemManager.readAnswer(d, answer) == false)return;
			this.addAnswer(answer);
		}else if(o instanceof JButton){
			JButton jb = (JButton) o;
			AnswerPanel ap = (AnswerPanel) jb.getParent();
			Answer ans = ap.getAnswer();
			this.writeAnswer(ans);
		}
	}
	
	private void writeAnswer(Answer answer){
		FileDialog dialog = new FileDialog(ProblemManager.getMainFrame() , "Save Answer" ,FileDialog.SAVE);
		dialog.setVisible(true);
		String dir = dialog.getDirectory() + dialog.getFile();
		if(dialog.getDirectory() == null || dialog.getFile() == null){return;}
		ProblemManager.saveAnswerToFile(dir, answer);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Object o = e.getSource();
		if(o instanceof AnswerPanel){
			AnswerPanel ap = (AnswerPanel) o;
			int index = ap.getIndex();
			this.setIndex(index);
			if(e.getButton() == MouseEvent.BUTTON1){
				Answer answer = this.lists.get(this.index).getAnswer();
				this.vp.setAnswer(answer);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
}
