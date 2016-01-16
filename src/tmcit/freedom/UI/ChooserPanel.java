package tmcit.freedom.UI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import tmcit.freedom.Util.PipeType;


public class ChooserPanel extends JPanel implements MouseListener {

	private int now;
	private ArrayList<ChoosePipePanel> lists;

	public ChooserPanel(PipeType[] types){
		this.setLayout(null);
		this.setBounds(730, 5, 158, 360);
		this.setBackground(Color.WHITE);

		this.now = 0;
		this.lists = new ArrayList<ChoosePipePanel>();

		int reSize = 0;

		for(int i = 0; i < types.length; i++){
			ChoosePipePanel cpp = new ChoosePipePanel(i, types[i]);
			cpp.addMouseListener(this);
			cpp.getTextF().addMouseListener(this);
			cpp.getPipePanel().addMouseListener(this);
			this.add(cpp);
			this.lists.add(cpp);
			reSize += ChoosePipePanel.Height;
		}
		this.choosePanel(now);


		this.setPreferredSize(new Dimension(158, reSize));

		this.setVisible(true);
	}

	public int getNowIndex(){
		return this.now;
	}
	
	public void setNowIndex(int index){
		this.choosePanel(index);
	}
	
	public PipeType getNowType(){
		return lists.get(this.now).getType();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Component c = e.getComponent();
		if(c instanceof ChoosePipePanel){
			ChoosePipePanel cpp = (ChoosePipePanel) c;
			this.choosePanel(cpp.getIndex());
		}else{
			c = c.getParent();
			if(c instanceof ChoosePipePanel){
				ChoosePipePanel cpp = (ChoosePipePanel) c;
				this.choosePanel(cpp.getIndex());
			}
		}
	}

	private void choosePanel(int index){
		if(index < 0 || lists.size() <= index)return;
		ChoosePipePanel cpp = lists.get(now);
		cpp.releasePanel();

		cpp = lists.get(index);
		cpp.choosePanel();
		
		this.now = index;
		repaint();
	}
	
	public void chooseUp(boolean up){
		if(up){
			this.choosePanel(now - 1);
		}else{
			this.choosePanel(now + 1);
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

	@Override
	public void mouseClicked(MouseEvent e) {
	}


}
