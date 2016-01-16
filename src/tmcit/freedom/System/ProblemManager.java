package tmcit.freedom.System;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import tmcit.freedom.System.ProblemDownloader.Bool;
import tmcit.freedom.UI.MainFrame;
import tmcit.freedom.Util.PipeType;

public class ProblemManager {
	private static MainFrame mainFrame;

	public static MainFrame getMainFrame(){
		return mainFrame;
	}
	public static void setMainFrame(MainFrame mf){
		mainFrame = mf;
	}

	public static boolean readProblem(String directory, Problem problem){
		File f = new File(directory);
		if(f.exists() == false){
			System.out.println("Err:File Not Found.");
			return false;
		}else if(problem == null){
			System.out.println("Err:Problem Null");
		}
		mainFrame.setTitle(Main.appName);
		problem.initilize();
		return problem.read(directory);
	}

	public static boolean readAnswer(String directory, Answer answer){
		File f = new File(directory);
		if(f.exists() == false){
			System.out.println("Err:File Not Found.");
			return false;
		}
		answer.initilize();
		return answer.read(directory);
	}
	
	public static boolean copyAnswer(Answer origin, Answer dest){
		final int pipeNum = origin.getPipeNum();
		final int nowI = origin.getNowI();
		final int nowL = origin.getNowL();
		final int nowX = origin.getNowX();
		dest.setPipeNum(pipeNum);
		dest.setNowI(nowI);
		dest.setNowL(nowL);
		dest.setNowX(nowX);
		final PipeType[][] board = origin.getBoard();
			  PipeType[][] board2 = dest.getBoard();
		for(int i = 0; i < 30; i++){
			for(int j = 0; j < 30; j++){
				board2[i][j] = board[i][j];
			}
		}
		return true;
	}

	public static PipeType[][] getAscertain(Problem problem, Answer answer){
		PipeType[][] board = new PipeType[30][30];
		if(problem == null || answer == null){
			System.out.println("Err:Not Read Problem or Answer.");
			return null;
		}
		PipeType[][] pb = problem.getBoard();
		PipeType[][] ab = answer.getBoard();
		int numI = 0, numL = 0, numX = 0;
		int limI = problem.getLimI();
		int limL = problem.getLimL();
		int limX = problem.getLimX();

		for(int i = 0; i < 30; i++){
			for(int j = 0; j < 30; j++){
				PipeType t1 = pb[i][j];
				PipeType t2 = ab[i][j];
				if(t2 == PipeType.NULL){
					board[i][j] = pb[i][j];
					continue;
				}
				if(t1 != PipeType.EMP){
					board[i][j] = PipeType.NG;
					continue;
				}

				if(t2 == PipeType.UD || t2 == PipeType.LR){
					if(limI < ++numI){
						board[i][j] = PipeType.NG;
						continue;
					}
				}else if(t2 == PipeType.UL || t2 == PipeType.UR || t2 == PipeType.DL || t2 == PipeType.DR){
					if(limL < ++numL){
						board[i][j] = PipeType.NG;
						continue;
					}
				}else if(t2 == PipeType.XX){
					if(limX < ++numX){
						board[i][j] = PipeType.NG;
						continue;
					}
				}else{
					System.out.println("SystemError: Problem Manage Err.");
					board[i][j] = PipeType.NG;
					continue;
				}
				board[i][j] = ab[i][j];
			}
		}
		return board;
	}

	public static String getScore(PipeType[][] board){
		String score = "No Score";
		int s = -1;
		Scorer scorer = new Scorer();
		s = scorer.setBoard(board);
		if(s != -1){
			score = "Score : " + String.valueOf(s);
		}

		return score;
	}

	public static boolean[][] getRouteMemo(PipeType[][] board) {
		Scorer scorer = new Scorer();
		scorer.setBoard(board);
		return scorer.getRouteMemo();
	}
	
	public static int getScore(boolean[][] routeMemo){
		int score = 0;
		for(int i = 1; i < 31; i++){
			for(int j = 1; j < 31; j++){
				if(routeMemo[i][j] == false)score++;
			}
		}
		return score;
	}
	
	public static String getPracticeProblemDirectory(int num){
		String fileName = ProblemDownloader.getFileName(num);
		if(ProblemDownloader.isFileExist(fileName) == Bool.TRUE){
			String dir = ProblemDownloader.getDirectory();
			return dir + fileName;
		}
		return null;
	}
	
	public static void error(String message){
		System.out.println("TEST");
		mainFrame.error("Error:" + message);
	}
	
	public static void saveAnswerToFile(String dir, Answer answer) {
		try{
			File file = new File(dir);
			if(file.exists()){
				System.out.println("War:File Exists at Save Path.");
			}else{
				file.createNewFile();
				System.out.println("War:New File");
			}
			FileWriter filewriter = new FileWriter(file);
			ArrayList<String> array = ProblemManager.outStringAnswer(answer);
			filewriter.write(String.valueOf(array.size()) + "\r\n");
			for(String str: array){
				filewriter.write(str);
			}

			filewriter.close();
		}catch(IOException e){
			System.out.println(e);
			ProblemManager.error("Answer Save Error.");
		}
	}

	public static ArrayList<String> outStringAnswer(Answer answer){
		ArrayList<String> ans = new ArrayList<String>();
		PipeType[][] pipeType = answer.getBoard();
		for(int i = 0; i < 30; i++){
			for(int j = 0; j < 30; j++){
				PipeType type = pipeType[i][j];
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
