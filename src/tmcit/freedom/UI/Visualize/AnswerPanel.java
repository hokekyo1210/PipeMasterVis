package tmcit.freedom.UI.Visualize;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import tmcit.freedom.System.Answer;

public class AnswerPanel extends JPanel {

	final public static int Height = 100;

	private int index;
	private int nowI, nowL, nowX;
	private String text;

	private JButton button;
	private JLabel label;
	private Answer answer;

	public AnswerPanel(int index, Answer answer){
		this.setLayout(null);
		this.index = index;
		this.answer = answer;
		
		this.setAnswerData();
		this.setThisPanel();

		this.setVisible(true);
	}
	
	private void setThisPanel(){
		this.setBounds(0, Height*this.index, 140, Height);

		this.setBorder(new BevelBorder(BevelBorder.RAISED));
		this.setBackground(Color.LIGHT_GRAY);

		this.label = new JLabel(this.text);
		this.label.setBounds(3, 3, 134, 60);
		this.add(this.label);
		
		this.button = new JButton("Save");
		this.button.setBounds(70, 65, 65, 22);
		this.add(this.button);
	}
	
	private void setAnswerData(){
		this.nowI = this.answer.getNowI();
		this.nowL = this.answer.getNowL();
		this.nowX = this.answer.getNowX();

		this.text = "<html>";
		this.text += "Index:" + String.valueOf(index) + "<br>";
		this.text += "I:" + String.valueOf(nowI) + "  ";
		this.text += "L:" + String.valueOf(nowL) + "  ";
		this.text += "X:" + String.valueOf(nowX);
	}
	
	public void choosePanel(){
		this.setBackground(Color.GRAY);
	}

	public void fixPanel(){
		this.setBackground(Color.LIGHT_GRAY);
	}
	
	public JButton getBut(){
		return this.button;
	}
	
	public Answer getAnswer(){
		return this.answer;
	}
	
	public int getIndex(){
		return this.index;
	}
}
