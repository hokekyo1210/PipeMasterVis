package tmcit.freedom.UI;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import tmcit.freedom.Util.IconUtil;
import tmcit.freedom.Util.PipeType;

public class PipePanel extends JLabel{
	private int atx, aty;
	private PipeType type;
	
	private ImageIcon icon;
	private String urlDir = "Parts/";
	private String dir = ".\\Parts\\";

	final public static int Length = 24;
	
	public PipePanel(PipeType type){
		this.type = type;
		this.icon = IconUtil.getIcon(this.type.getString() + ".png");
		this.setIcon(icon);
		this.setToolTipText(type.getString());
	}

	public PipePanel(int x, int y, PipeType type){
		this.atx = x;
		this.aty = y;
		this.type = type;
		this.setBounds(x*Length, y*Length, Length, Length);

		this.paintPipe();
	}

	public void paintInPipe(){
		this.icon = IconUtil.getIcon(this.type.getString() + "_in.png");
		this.setIcon(icon);
		this.setToolTipText(getTipText(type));
	}

	public void paintPipe(PipeType type){
		this.type = type;
		this.icon = IconUtil.getIcon(this.type.getString() + ".png");
		this.setIcon(icon);
		this.setToolTipText(getTipText(type));
	}

	public void paintPipe(){
		this.icon = IconUtil.getIcon(this.type.getString() + ".png");
		this.setIcon(icon);
		this.setToolTipText(getTipText(type));
	}
	
	public void setPipeType(PipeType type){
		this.type = type;
		this.setToolTipText(getTipText(type));
	}

	private String getTipText(PipeType type){
		String str = "";
		str += "(" + String.valueOf(atx) + ",";
		str += String.valueOf(aty) + ", ";
		str += type + ")";
		return str;
	}


	public PipeType getPipeType(){
		return this.type;
	}
	
	public int getAtx(){
		return this.atx;
	}

	public int getAty(){
		return this.aty;
	}
	
	private ImageIcon getImage(String dir){
		URL url = this.getClass().getClassLoader().getResource(dir);
		try {
			return new ImageIcon(url);
		}catch(Exception ex){
			return null;
		}
	}

}
