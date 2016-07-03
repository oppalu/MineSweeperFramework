package edu.nju.network.modelProxy;

import edu.nju.controller.msgqueue.operation.DoubleClickOperation;
import edu.nju.controller.msgqueue.operation.LeftClickOperation;
import edu.nju.controller.msgqueue.operation.MineOperation;
import edu.nju.controller.msgqueue.operation.RightClickOperation;
import edu.nju.model.service.ChessBoardModelService;
import edu.nju.model.service.GameModelService;
import edu.nju.network.client.ClientService;

public class ChessBoardModelProxy extends ModelProxy implements ChessBoardModelService{

	public ChessBoardModelProxy(ClientService client){
		this.net = client;
	}
	
	@Override
	public boolean initialize(int width, int height, int mineNum) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean excavate(int x, int y) {
		// TODO Auto-generated method stub
		MineOperation op = new LeftClickOperation(x,y);
		op.setOnline();
		net.submitOperation(op);
		return true;
	}

	@Override
	public boolean mark(int x, int y) {
		// TODO Auto-generated method stub
		MineOperation op = new RightClickOperation(x,y);
		op.setOnline();
		net.submitOperation(op);
		return true;
	}

	@Override
	public boolean quickExcavate(int x, int y) {
		// TODO Auto-generated method stub
		MineOperation op = new DoubleClickOperation(x,y);
		op.setOnline();
		net.submitOperation(op);
		return true;
	}

	@Override
	public void setGameModel(GameModelService gameModel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean showAll(int width, int height) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean netExcavate(int x, int y,boolean isClient) {
		// TODO Auto-generated method stub
		MineOperation op = new LeftClickOperation(x,y);
		op.setOnline();
		net.submitOperation(op);
		return true;
	}

	@Override
	public boolean netMark(int x, int y,boolean isClient) {
		// TODO Auto-generated method stub
		MineOperation op = new RightClickOperation(x,y);
		op.setOnline();
		net.submitOperation(op);
		return true;
	}

	@Override
	public boolean netQuickExcavate(int x, int y,boolean isClient) {
		// TODO Auto-generated method stub
		MineOperation op = new DoubleClickOperation(x,y);
		op.setOnline();
		net.submitOperation(op);
		return true;
	}

	
}
