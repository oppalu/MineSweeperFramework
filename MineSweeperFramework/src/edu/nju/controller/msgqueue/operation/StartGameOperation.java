package edu.nju.controller.msgqueue.operation;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.model.service.GameModelService;

public class StartGameOperation extends MineOperation{

	private static final long serialVersionUID = 1L;

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		GameModelService game = OperationQueue.getGameModel();
		
		game.setGameLevel("小");
		game.startGame();
	}

}
