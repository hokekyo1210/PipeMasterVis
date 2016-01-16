package tmcit.freedom.UI;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class GraphBar extends JLabel {
	final static String[] barName = {"NowI", "LimI", "NowL", "LimL", "NowX", "LimX"};

	private String name;
	private BufferedImage image;
	private Color bar;
	private Color back;

	public GraphBar(int n, Color bar, Color back){
		this.setLayout(null);

		this.setBounds(25*n + 7, 50, 20, 300);
		this.setOpaque(true);
		
		this.bar = bar;
		this.back = back;
		this.name = barName[n];
		
		this.setImage();
		this.setData(0.0, 100.0);

		this.setVisible(true);
	}
	
	public void setImage(){
		this.image = new BufferedImage(20, 300, BufferedImage.TYPE_INT_ARGB);
		this.setIcon(new ImageIcon(image));
	}

	private void setNum(int num){
		this.setToolTipText(name + ":" + String.valueOf(num));
	}

	public void setData(double num, double allNum){
		this.setNum((int)(num));
		Graphics2D g = image.createGraphics();
		int height = (int) (300.0*(num/allNum));
		g.setColor(back);
		g.fillRect(0, 0, 20, 300 - height);

		g.setColor(bar);
		g.fillRect(0, 300 - height, 20, height);
		
		g.dispose();
	}
}
