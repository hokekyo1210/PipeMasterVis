package tmcit.freedom.Util;

public enum PipeType {
	NULL("NULL"), EMP("EMP"), BLO("BLO"),
	UD("UD"), LR("LR"), UL("UL"), UR("UR"), DL("DL"), DR("DR"), XX("XX"),
	STR("STR"), GOL("GOL"), NG("NG");

	private final String text;
	
	private PipeType(final String text) {
		this.text = text;
	}
	
	public String getString(){
		return this.text;
	}
	
	public static String getString(PipeType type){
		String str;
		if(type == PipeType.UD)str = "U D";
		else if(type == PipeType.LR)str = "L R";
		else if(type == PipeType.UL)str = "U L";
		else if(type == PipeType.UR)str = "U R";
		else if(type == PipeType.DL)str = "D L";
		else if(type == PipeType.DR)str = "D R";
		else if(type == PipeType.XX)str = "X X";
		else str = "ERROR!";

		return str;
	}

	public static PipeType getType(char c1, char c2){
		if(c1 == 'U' && c2 == 'D' || c2 == 'U' && c1 == 'D'){
			return UD;
		}else if(c1 == 'L' && c2 == 'R' || c2 == 'L' && c1 == 'R'){
			return LR;
		}else if(c1 == 'U' && c2 == 'L' || c2 == 'U' && c1 == 'L'){
			return UL;
		}else if(c1 == 'U' && c2 == 'R' || c2 == 'U' && c1 == 'R'){
			return UR;
		}else if(c1 == 'D' && c2 == 'L' || c2 == 'D' && c1 == 'L'){
			return DL;
		}else if(c1 == 'D' && c2 == 'R' || c2 == 'D' && c1 == 'R'){
			return DR;
		}else if(c1 == 'X' && c2 == 'X'){
			return XX;
		}else{
			return null;
		}
	}
	
	public static PipeType[] getAllPipeType(){
		PipeType[] types = {UD, LR, UL, UR, DL, DR, XX, EMP};
		return types;
	}

	public static PipeType[] getAllBlockType(){
		PipeType[] types = {BLO, STR, GOL, EMP};
		return types;
	}
	
	public static PipeType getType(int b, int v){
		if(b == 0 && v == 1 || v == 0 && b == 1)return PipeType.UD;	
		if(b == 2 && v == 3 || v == 2 && b == 3)return PipeType.LR;
		if(b == 0 && v == 2 || v == 0 && b == 2)return PipeType.UL;
		if(b == 0 && v == 3 || v == 0 && b == 3)return PipeType.UR;
		if(b == 1 && v == 2 || v == 1 && b == 2)return PipeType.DL;
		if(b == 1 && v == 3 || v == 1 && b == 3)return PipeType.DR;
		return null;
	}
	
	public static boolean isPipe(PipeType type){
		if(type == PipeType.UD)return true;
		if(type == PipeType.LR)return true;
		if(type == PipeType.UL)return true;
		if(type == PipeType.UR)return true;
		if(type == PipeType.DL)return true;
		if(type == PipeType.DR)return true;
		if(type == PipeType.XX)return true;
		return false;
	}
}