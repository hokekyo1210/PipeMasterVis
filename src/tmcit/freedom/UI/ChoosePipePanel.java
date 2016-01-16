package tmcit.freedom.UI;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import tmcit.freedom.Util.PipeType;



public class ChoosePipePanel extends JPanel {

	final public static int Height = 50;
	private int index;
	private JTextField name;
	private PipePanel pipe;

	public ChoosePipePanel(int index, PipeType type){
		this.setLayout(null);
		this.index = index;
		this.setBounds(0, Height*this.index, 140, Height);

		this.setBorder(new BevelBorder(BevelBorder.RAISED));
		this.setBackground(Color.GRAY);

		this.pipe = new PipePanel(type);
		this.pipe.setBounds(20, 12, 24, 24);
		this.add(this.pipe);

		this.setTextField();

		this.setVisible(true);
	}
	
	private void setTextField(){
		this.name = new JTextField( this.pipe.getPipeType().getString() );
		this.name.setBorder(null);
		this.name.setMargin(new Insets(10, 10, 10, 10));
		this.name.setBounds(44, 12, 50, 24);
		this.name.setEnabled(false);
		this.name.setDisabledTextColor(Color.BLACK);
		this.add(name);

	}
	
	public void choosePanel(){
		this.setBackground(Color.DARK_GRAY);
	}

	public void releasePanel(){
		this.setBackground(Color.GRAY);
	}
	
	public PipeType getType(){
		return this.pipe.getPipeType();
	}
	
	public PipePanel getPipePanel(){
		return this.pipe;
	}
	
	public JTextField getTextF(){
		return this.name;
	}

	public int getIndex() {
		return this.index;
	}
}
