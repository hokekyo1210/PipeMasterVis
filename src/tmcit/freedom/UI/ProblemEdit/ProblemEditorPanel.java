package tmcit.freedom.UI.ProblemEdit;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Queue;

import javax.swing.SwingUtilities;

import tmcit.freedom.UI.MainPanel;
import tmcit.freedom.UI.PipePanel;
import tmcit.freedom.Util.Pair;
import tmcit.freedom.Util.PipeType;

public class ProblemEditorPanel extends MainPanel{
	
	private ProblemEditorPanel own;

	public ProblemEditorPanel(){
		own = this;
		this.setLayout(null);
		this.setBounds(5, 5, 720, 720);
		this.setBackground(Color.LIGHT_GRAY);

		this.setField();
		this.initilize();

		this.setVisible(true);
	}
	
	public PipePanel getPipePanel(int x, int y){
		if(x < 0 || 30 <= x || y < 0 || 30 <= y)return null;
		return pipePanel[y][x];
	}

	public void paintPipe(int x, int y, PipeType type){
		this.pipePanel[y][x].paintPipe(type);
	}
	
	public void paintPipe(int x1, int y1, int x2, int y2, PipeType type){
		int miX = Math.min(x1, x2);
		int miY = Math.min(y1, y2);
		int maX = Math.max(x1, x2);
		int maY = Math.max(y1, y2);
		if(miX < 0 || miY < 0 || 30 <= maX || 30 <= maY)return;
		for(int i = miY; i <= maY; i++){
			for(int j = miX; j <= maX; j++){
				this.pipePanel[i][j].paintPipe(type);
			}						
		}
	}
	
	final private static int[] dirX = {0, 0, -1, 1}; 
	final private static int[] dirY = {-1, 1, 0, 0}; 
	public void paintPenki(final int atx, final int aty, PipeType pressType){
		final PipeType pt = pressType;
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				Queue<Pair> que = new ArrayDeque<Pair>();
				que.add(new Pair(atx, aty));
				while(que.isEmpty() == false){
					Pair p = que.poll();
					int x = p.p1;
					int y = p.p2;
					if(x < 0 || 30 <= x || y < 0 || 30 <= y)continue;
					if(pipePanel[y][x].getPipeType() == pt)continue;

					pipePanel[y][x].paintPipe(pt);

					for(int i = 0; i < 4; i++){
						que.add(new Pair(x + dirX[i], y + dirY[i]));
					}
				}
				own.repaint();
			}
		});
	}

}
