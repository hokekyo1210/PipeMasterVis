package tmcit.freedom.UI.AnswerEdit;

import java.awt.Color;
import java.util.ArrayList;

import tmcit.freedom.UI.MainPanel;
import tmcit.freedom.UI.PipePanel;
import tmcit.freedom.Util.PipeType;

public class AnswerEditor extends MainPanel {

	public AnswerEditor(){
		this.setLayout(null);
		this.setBounds(5, 5, 720, 720);
		this.setBackground(Color.LIGHT_GRAY);

		this.setField();
		this.initilize();

		this.setVisible(true);

	}

	public boolean canPut(int x, int y){
		if(x < 0 || 30 <= x || y < 0 || 30 <= y)return false;
		PipeType type = this.pipePanel[y][x].getPipeType();
		if(type == PipeType.BLO)return false;
		if(type == PipeType.STR)return false;
		if(type == PipeType.GOL)return false;
		return true;
	}
	
	public PipePanel[][] getPipePanel(){
		return this.pipePanel;
	}
	
	public void paintPipe(int x, int y, PipeType type){
		this.pipePanel[y][x].paintPipe(type);
		this.repaint();
	}

	public ArrayList<String> outAnswer(){
		ArrayList<String> ans = new ArrayList<String>();
		for(int i = 0; i < 30; i++){
			for(int j = 0; j < 30; j++){
				PipeType type = this.pipePanel[i][j].getPipeType();
				if(type == PipeType.NULL)continue;
				if(type == PipeType.EMP)continue;
				if(type == PipeType.BLO)continue;
				if(type == PipeType.STR)continue;
				if(type == PipeType.GOL)continue;
				String line = "";
				line += String.valueOf(j) + " ";
				line += String.valueOf(i) + " ";
				line += PipeType.getString(type);
				line += "\r\n";
				ans.add(line);
			}
		}

		return ans;
	}


}
