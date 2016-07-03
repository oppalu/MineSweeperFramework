package edu.nju.network.client;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.main.JMineSweeper;
import edu.nju.network.modelProxy.ChessBoardModelProxy;
import edu.nju.network.modelProxy.GameModelProxy;
import edu.nju.network.modelProxy.ParameterModelProxy;

public class TestClient {

	/**
	 * @param args
	 */
	public static boolean isClient;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		init_client();
	}

	public static void init_client() {
		ClientServiceImpl client = new ClientServiceImpl();
		ClientInHandlerImpl clientH = new ClientInHandlerImpl();

		GameModelProxy gameProxy = new GameModelProxy(client);
		ParameterModelProxy parameterProxy = new ParameterModelProxy();
		ChessBoardModelProxy chessProxy = new ChessBoardModelProxy(client);
		
		gameProxy.addObserver(JMineSweeper.ui);
		chessProxy.addObserver(JMineSweeper.ui.getMineBoard());

		clientH.addObserver(gameProxy);
		clientH.addObserver(chessProxy);
		clientH.addObserver(parameterProxy);
		client.init("127.0.0.1", clientH);
		
		OperationQueue.setGameModel(gameProxy);
		OperationQueue.setChessBoardModel(chessProxy);
		
		isClient=true;
	}

}
