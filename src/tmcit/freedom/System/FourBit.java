package tmcit.freedom.System;

import tmcit.freedom.Util.PipeType;


public class FourBit {
	//0:U 1:D 2:L 3:R
	final public static int[] dirX = {0, 0, -1, 1};
	final public static int[] dirY = {-1, 1, 0, 0};
	
	public static int rebirth(int before){
		if(before == 0)return 1;
		if(before == 1)return 0;
		if(before == 2)return 3;
		if(before == 3)return 2;
		return -1;
	}

	//Pipe:0, Str:1, Gol:2, Blo3, Emp4
	private int event;
	private boolean[] segment;
	private boolean isCloss;

	public FourBit(){
		this.segment = new boolean[4];

		this.initilize();
	}
	
	private void initilize(){
		for(int i = 0; i < 4; i++){
			this.segment[i] = false;
		}
		this.isCloss = false;
		this.event = 3;
	}
	
	public int getEvent(){
		return this.event;
	}
	
	public int getNext(int before){
		if(isCloss){
			if(before == 0)return 1;
			if(before == 1)return 0;
			if(before == 2)return 3;
			if(before == 3)return 2;
		}
		if(this.segment[before] == false)return -1;
		for(int i = 0; i < 4;i++){
			if(i == before)continue;
			if(this.segment[i])return i;
		}
		return -1;
	}
	
	public void setBit(PipeType type){
		if(type == PipeType.UD){this.segment[0] = this.segment[1] = true; this.event = 0;}else
		if(type == PipeType.LR){this.segment[2] = this.segment[3] = true; this.event = 0;}else
		if(type == PipeType.UL){this.segment[0] = this.segment[2] = true; this.event = 0;}else
		if(type == PipeType.UR){this.segment[0] = this.segment[3] = true; this.event = 0;}else
		if(type == PipeType.DL){this.segment[1] = this.segment[2] = true; this.event = 0;}else
		if(type == PipeType.DR){this.segment[1] = this.segment[3] = true; this.event = 0;}else
		if(type == PipeType.XX){this.isCloss    = true; this.event = 0;}else
		if(type == PipeType.EMP){this.event = 4;}else
		if(type == PipeType.STR){this.event = 1;}else
		if(type == PipeType.GOL){this.event = 2;}else
		if(type == PipeType.BLO){this.event = 3;}
	}
	
	public PipeType getPipeType(){
		if(this.segment[0] && this.segment[1])return PipeType.UD;
		if(this.segment[2] && this.segment[3])return PipeType.LR;
		if(this.segment[0] && this.segment[2])return PipeType.UL;
		if(this.segment[0] && this.segment[3])return PipeType.UR;
		if(this.segment[1] && this.segment[2])return PipeType.DL;
		if(this.segment[1] && this.segment[3])return PipeType.DR;
		if(this.isCloss)return PipeType.XX;
		return null;
	}


}
