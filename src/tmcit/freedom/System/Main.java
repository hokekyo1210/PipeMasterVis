package tmcit.freedom.System;

import javax.swing.ToolTipManager;

import tmcit.freedom.UI.MainFrame;
import tmcit.freedom.Util.IconUtil;

public class Main {
	final public static String appName = "PIPE M@STER Visualize Application";
	
	public Main(){
		new IconUtil("Parts");
		this.setToolTip();
		ProblemManager.setMainFrame(new MainFrame(appName));
	}

	private void setToolTip(){
		ToolTipManager tp = ToolTipManager.sharedInstance();
		tp.setInitialDelay(0);
	}

	public static void main(String[] args){
		new ProblemDownloader();
		new Main();
	}

}
