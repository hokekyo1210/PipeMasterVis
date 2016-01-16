package tmcit.freedom.Util;


import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;


public class IconUtil {

	private static HashMap<String,IconData> icons;
	
	private List<String> iconNameList;
	private String resourceName;

	public IconUtil(String resourceName){
		this.resourceName = resourceName;
		loadNames();
		if(!loadIcon()){
			System.exit(0);
		}
	}

	private void loadNames() {
		iconNameList = new ArrayList<String>();
		iconNameList.add("BLO.png");
		iconNameList.add("STR.png");
		iconNameList.add("GOL.png");
		iconNameList.add("EMP.png");
		iconNameList.add("NULL.png");
		iconNameList.add("NG.png");
		iconNameList.add("UD.png");
		iconNameList.add("UD_in.png");
		iconNameList.add("LR.png");
		iconNameList.add("LR_in.png");
		iconNameList.add("UL.png");
		iconNameList.add("UL_in.png");
		iconNameList.add("UR.png");
		iconNameList.add("UR_in.png");
		iconNameList.add("DL.png");
		iconNameList.add("DL_in.png");
		iconNameList.add("DR.png");
		iconNameList.add("DR_in.png");
		iconNameList.add("XX.png");
		iconNameList.add("XX_in.png");
	}

	private boolean loadIcon(){
		try{
			icons = new HashMap<String,IconData>();
			for(String name:iconNameList){
//				System.out.println(name);
				final URL filename = this.getClass().getResource("/"+resourceName+"/"+name);
				Image image = Toolkit.getDefaultToolkit().getImage(filename);
				if(image!=null){
					icons.put(name,new IconData(name,new ImageIcon(image)));
				}else{
					return false;
				}
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public static ImageIcon getIcon(String string) {
		return icons.get(string).getIcon();
/*		for(IconData id:icons){
			if(id.getName().equals(string)){
				return id.getIcon();
			}
		}
		return null;
*/
	}

}
