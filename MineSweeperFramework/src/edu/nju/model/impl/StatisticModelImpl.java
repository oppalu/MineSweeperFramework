package edu.nju.model.impl;

import edu.nju.model.service.StatisticModelService;
import edu.nju.model.state.GameResultState;

import java.io.*;

public class StatisticModelImpl extends BaseModel implements StatisticModelService{
	
	FileWriter fw;
	FileReader fr;
	BufferedReader reader;

	@Override
	public void recordStatistic(GameResultState result,String level) {
		// TODO Auto-generated method stub
		try {
			FileWriter fw=new FileWriter("save.dat",true);
			fw.append(level+result+";");
			fw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String[] showStatistics() {
		// TODO Auto-generated method stub
		String temp="";
		String[] record=null;
		try {
			FileReader fr = new FileReader("save.dat");
			BufferedReader reader = new BufferedReader(fr);
			while((temp=reader.readLine())!=null){
				record=temp.split(";");
			}
			fr.close();
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return record;
	}

	@Override
	public void recordStatistic(GameResultState result, int time) {
		// TODO Auto-generated method stub
		
	}
	
	
}
