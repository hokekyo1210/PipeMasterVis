package tmcit.freedom.Util;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.TransferHandler;

public class DropFileHandler  extends TransferHandler {
	private JTextField tf;
	private JButton jb;

	public DropFileHandler(JTextField tf,JButton jb){
		this.tf = tf;
		this.jb = jb;
	}

	@Override
    public boolean canImport(TransferSupport support) {
		if(!support.isDrop()){
			return false;
		}
        return true;
    }

	@Override
	public boolean importData(TransferSupport support){
		if(!canImport(support)){
			return false;
		}
		Transferable t = support.getTransferable();
		try {
			@SuppressWarnings("unchecked")
			List<File> files = (List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);
			if(files.size()==1){
				File file = files.get(0);
				if(isText(file)){
					tf.setText(file.getAbsolutePath());
					if(jb!=null){
						jb.doClick();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean isImage(File file){
		String fileName = file.getName();
		if(fileName.indexOf(".png")!=-1||fileName.indexOf(".jpg")!=-1||fileName.indexOf(".ppm")!=-1){
			return true;
		}
		return false;
	}
	public static boolean isText(File file){
		String fileName = file.getName();
		if(fileName.indexOf(".txt")!=-1){
			return true;
		}
		return false;
	}

	public static String readTxt(File file) throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(file));
		String answer = "";
		String str;
		while((str = br.readLine()) != null){
			answer+=str+System.getProperty("line.separator");
		}
		br.close();
		return answer;
	}


}
