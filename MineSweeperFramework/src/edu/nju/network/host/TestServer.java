package edu.nju.network.host;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.model.impl.ChessBoardModelImpl;
import edu.nju.model.impl.GameModelImpl;
import edu.nju.model.impl.ParameterModelImpl;

public class TestServer {
	
	/**
	 * @param args
	 */
	public static boolean isServer;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		init_server();
			
	}

	public static void init_server() {
		HostServiceImpl host = new HostServiceImpl();
		HostInHandlerImpl hostH = new HostInHandlerImpl();

		GameModelImpl game = (GameModelImpl) OperationQueue.getGameModel();
		game.addObserver(host);
		ChessBoardModelImpl board =(ChessBoardModelImpl)OperationQueue.getChessBoardModel();
		board.addObserver(host);
		ParameterModelImpl parameter = (ParameterModelImpl) board.getParameterModel();
		parameter.addObserver(host);
		
		if(host.init(hostH)){
			System.out.println("Connecting!!!");
			game.startGame();
		}
		
		isServer=true;
	}
}
