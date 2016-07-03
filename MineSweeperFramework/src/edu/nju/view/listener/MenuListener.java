/*
 *
 * TODO To manage menu action
 */
package edu.nju.view.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.nju.controller.impl.MenuControllerImpl;
import edu.nju.controller.impl.SettingControllerImpl;
import edu.nju.controller.service.MenuControllerService;
import edu.nju.controller.service.SettingControllerService;
import edu.nju.model.impl.StatisticModelImpl;
import edu.nju.model.service.StatisticModelService;
import edu.nju.network.client.TestClient;
import edu.nju.network.host.TestServer;
import edu.nju.view.CustomDialog;
import edu.nju.view.MainFrame;
import edu.nju.view.RecordDialog;
 

public class MenuListener implements ActionListener{

	private MainFrame ui;
	private CustomDialog customDialog;
	private RecordDialog recordDialog;
	MenuControllerService menuController = new MenuControllerImpl();
	SettingControllerService settingController = new SettingControllerImpl();
	StatisticModelService statisticModel = new StatisticModelImpl();
	
	public MenuListener(MainFrame ui){
		this.ui = ui;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ui.getMenuItem("start")) {//生成游戏，默认生成简单游戏
			menuController.startGame();
			ui.second=0;
			ui.timer.start();
		} else if (e.getSource() == ui.getMenuItem("easy")) {//生成简单游戏
			settingController.setEasyGameLevel();
			ui.second=0;
			ui.timer.restart();
		} else if (e.getSource() == ui.getMenuItem("hard")) {//生成中等游戏
			settingController.setHardGameLevel();
			ui.second=0;
			ui.timer.restart();
		} else if (e.getSource() == ui.getMenuItem("hell")) {//生成大型游戏
			settingController.setHellGameLevel();
			ui.second=0;
			ui.timer.restart();
		} else if (e.getSource() == ui.getMenuItem("custom")) {//生成定制游戏，需要向controller传递棋盘的高、宽和雷数
			customDialog=new CustomDialog(ui.getMainFrame());
			customDialog.show();
			settingController.setCustomizedGameLevel(customDialog.getHeight(), customDialog.getWidth(), customDialog.getMineNumber());
			ui.second=0;
			ui.timer.restart();
		} else if (e.getSource() == ui.getMenuItem("exit")) {
			System.exit(0);
		} else if (e.getSource() == ui.getMenuItem("record")) {//统计胜率信息
			recordDialog=new RecordDialog(ui.getMainFrame());
			recordDialog.show();
		}else if(e.getSource() == ui.getMenuItem("host")){//注册成为主机
			TestServer.init_server();
			ui.second=0;
			ui.timer.restart();
		}else if(e.getSource() == ui.getMenuItem("client")){//注册成为客户端
			TestClient.init_client();
			ui.second=0;
			ui.timer.restart();
		}
	}


}