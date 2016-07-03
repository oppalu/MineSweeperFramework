package edu.nju.model.service;

/**
 * 负责控制游戏参数Model，现有参数：剩余雷数
 * @author Wangy
 *
 */
public interface ParameterModelService {

	/**
	 * 设置剩余类数量
	 * @param num
	 * @return
	 */
	public boolean setMineNum(int num);
	
	public int getMineNum();
	
	/**
	 * 减少雷数
	 * @return
	 */
	public boolean minusMineNum();
	
	/**
	 * 增加雷数
	 * @return
	 */
	public boolean addMineNum();

	/**
	 * 增加标记正确的雷数
	 * @return
	 */
	public boolean addTrueMineNum();
	
	/**
	 * 减少标记正确的雷数
	 * @return
	 */
	public boolean minusTrueMineNum();
	
	public int getTrueMineNum();

	/**
	 * 用于游戏结束时将trueMineNum归零
	 */
	public void setTrueMineNum();
	
	public boolean addClientMineNum();
	
	
	public boolean minusClientMineNum();
	
	public int getClientMineNum();
	
}
