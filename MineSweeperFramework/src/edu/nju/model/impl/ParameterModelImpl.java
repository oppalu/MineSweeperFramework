package edu.nju.model.impl;

import edu.nju.model.service.ParameterModelService;

public class ParameterModelImpl extends BaseModel implements ParameterModelService{
	
	private int maxMine;
	private int mineNum;
	public int trueMineNum;
	public int clientMineNum;

	@Override
	public int getTrueMineNum() {
		return trueMineNum;
	}

	@Override
	public int getMineNum(){
		return mineNum;
	}
	@Override
	public void setTrueMineNum(){
		trueMineNum=0;
		clientMineNum=0;
		System.out.println(trueMineNum);
	}
	
	@Override
	public boolean setMineNum(int num) {
		// TODO Auto-generated method stub
		mineNum = num;
		maxMine = num;
		
		super.updateChange(new UpdateMessage("mineNum", mineNum));
		return true;
	}

	@Override
	public boolean addMineNum() {
		// TODO Auto-generated method stub
		mineNum++;
		if(mineNum>maxMine){
			mineNum--;
			return false;
		}
		
		super.updateChange(new UpdateMessage("mineNum", mineNum));
		return true;
	}

	@Override
	public boolean minusMineNum() {
		// TODO Auto-generated method stub
		mineNum--;

		if(mineNum<0){
			mineNum++;
			return false;
		}
		super.updateChange(new UpdateMessage("mineNum", mineNum));	
		return true;
	}

	@Override
	public boolean addTrueMineNum(){
		trueMineNum++;
		//super.updateChange(new UpdateMessage("Client",trueMineNum));
		return true;
	}
	
	@Override
	public boolean minusTrueMineNum(){
		trueMineNum--;
		//super.updateChange(new UpdateMessage("Client",trueMineNum));
		return true;
	}

	@Override
	public boolean addClientMineNum() {
		// TODO Auto-generated method stub
		clientMineNum++;
		//super.updateChange(new UpdateMessage("Server",hostMineNum));
		return true;
	}

	@Override
	public boolean minusClientMineNum() {
		// TODO Auto-generated method stub
		clientMineNum--;
		//super.updateChange(new UpdateMessage("Server",hostMineNum));
		return true;
	}

	@Override
	public int getClientMineNum() {
		// TODO Auto-generated method stub
		return clientMineNum;
	}
	
}
