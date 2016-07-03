/*
 *
 TODO to start to program. this program are wrote base on MVC structure
 */
package edu.nju.main;


import edu.nju.controller.impl.MenuControllerImpl;
import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.controller.service.MenuControllerService;
import edu.nju.model.impl.ChessBoardModelImpl;
import edu.nju.model.impl.GameModelImpl;
import edu.nju.model.impl.ParameterModelImpl;
import edu.nju.model.impl.StatisticModelImpl;
import edu.nju.view.MainFrame;

public class JMineSweeper {

	static MenuControllerService menuController = new MenuControllerImpl();
	public static MainFrame ui;
	public static void main(String[] args) {
		
		ui = new MainFrame();
		StatisticModelImpl statisticModel = new StatisticModelImpl();
 		ParameterModelImpl mineNumberModel = new ParameterModelImpl();
 		ChessBoardModelImpl mineBoardModel = new ChessBoardModelImpl(mineNumberModel);
		GameModelImpl gameModel = new GameModelImpl(statisticModel,mineBoardModel);		
 		
		gameModel.addObserver(ui);//给GameModelImpl添加观察者，监视游戏状态的变化
 		mineNumberModel.addObserver(ui.getMineNumberLabel());//给ParameterModelImpl添加观察者，监视雷数目的变化
 		mineBoardModel.addObserver(ui.getMineBoard());//给ChessBoardModelImpl添加观察者，监视动作变化
 		
 		OperationQueue operationQueue = new OperationQueue(mineBoardModel, gameModel);
 		Thread operationThread = new Thread(operationQueue);
 		operationThread.start();
 	    try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
 		menuController.startGame();
	}
}

