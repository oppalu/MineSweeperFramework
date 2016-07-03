package edu.nju.model.impl;

import java.util.ArrayList;
import java.util.List;

import edu.nju.model.po.BlockPO;
import edu.nju.model.service.ChessBoardModelService;
import edu.nju.model.service.GameModelService;
import edu.nju.model.service.ParameterModelService;
import edu.nju.model.state.BlockState;
import edu.nju.model.state.GameResultState;
import edu.nju.model.state.GameState;
import edu.nju.model.vo.BlockVO;
import edu.nju.network.client.TestClient;
import edu.nju.network.host.TestServer;

public class ChessBoardModelImpl extends BaseModel implements ChessBoardModelService{
	
	private GameModelService gameModel;
	private ParameterModelService parameterModel;
	private BlockPO[][] blockMatrix;
	private int mineNum;

	public ChessBoardModelImpl(ParameterModelService parameterModel){
		this.parameterModel = parameterModel;
	}

	public  ParameterModelService getParameterModel(){
  		return this.parameterModel;
  	}
	
	@Override
	public boolean initialize(int width, int height, int mineNum) {
		// TODO Auto-generated method stub
		blockMatrix = new BlockPO[width][height];
		setBlock(mineNum);
		
		this.parameterModel.setMineNum(mineNum);
		
		this.printBlockMatrix();
		
		this.mineNum=mineNum;
		//游戏开始时已标记的雷数清零
		this.parameterModel.setTrueMineNum();
		return false;
	}

	@Override
	public boolean excavate(int x, int y) {
		// TODO Auto-generated method stub

		if(blockMatrix==null){
			return false;
		}
		
		List<BlockPO> blocks = new ArrayList<BlockPO>();
		BlockPO block = blockMatrix[x][y];
		if(block.getState()==BlockState.UNCLICK){
			block.setState(BlockState.CLICK);
		}else{
			return false;
		}
		blocks.add(block);
		
		GameState gameState = GameState.RUN;
		if(block.isMine()){
			gameState = GameState.OVER;
			this.gameModel.gameOver(GameResultState.FAIL);
		}

		super.updateChange(new UpdateMessage("excute",this.getDisplayList(blocks, gameState)));			

		if(block.getMineNum()==0){
			for(int i=x-1;i<=x+1;i++){
				for(int j=y-1;j<=y+1;j++){
					if(isValid(i,j)){
						excavate(i,j);
					}
				}
			}
		}
		
		return true;
		
	}
	
	@Override
	public boolean mark(int x, int y) {
		// TODO Auto-generated method stub
		
		if(blockMatrix==null){
			return false;
		}
		
		List<BlockPO> blocks = new ArrayList<BlockPO>();
		BlockPO block = blockMatrix[x][y];
		 
		if(block.getState()==BlockState.UNCLICK&&(this.parameterModel.minusMineNum()==true)){
			block.setState(BlockState.FLAG);
			if(block.isMine()){
				this.parameterModel.addTrueMineNum();
			}
		}
		else if(block.getState()==BlockState.FLAG){
			block.setState(BlockState.UNCLICK);
			this.parameterModel.addMineNum();
			if(block.isMine()){
				this.parameterModel.minusTrueMineNum();
			}
		}
		
		blocks.add(block);	
		super.updateChange(new UpdateMessage("excute",this.getDisplayList(blocks, GameState.RUN)));
		
		if(this.parameterModel.getTrueMineNum()==mineNum){
			this.gameModel.gameOver(GameResultState.SUCCESS);
		}
		
		return true;
	}

	@Override
	public boolean quickExcavate(int x, int y) {
		// TODO Auto-generated method stub
		
		if(blockMatrix==null){
			return false;
		}
		
		BlockPO block = blockMatrix[x][y];
		if(block.getState()!=BlockState.CLICK){
			return false;
		}
		
		int flagNum=0;
		for(int i=x-1;i<=x+1;i++){
			for(int j=y-1;j<=y+1;j++){
				if(isValid(i,j)){
					if((blockMatrix[i][j].getState()==BlockState.FLAG)){
						flagNum++;
					}
					
				}
			}
		}

		if(flagNum==block.getMineNum()){
			for(int i=x-1;i<=x+1;i++){
				for(int j=y-1;j<=y+1;j++){
					if(isValid(i,j)){
						if((blockMatrix[i][j].getState()!=BlockState.FLAG)){
							excavate(i,j);
						}
						
					}
				}
			}
		}

		return true;
	}

	/**
	 * 用于处理边界的情况
	 */
	public boolean isValid(int x,int y){
		boolean valid=false;
		
		if((x>=0&&x<blockMatrix.length)&&(y>=0&&y<blockMatrix[0].length)){
			valid=true;
		}
		
		return valid;	
	}
	
	@Override
	public boolean showAll(int width,int height){
		if(blockMatrix == null)
			return false;
		
		List<BlockPO> blocks = new ArrayList<BlockPO>();
		for(int i=0;i<width;i++){
			for(int j=0;j<height;j++){
				BlockPO block = blockMatrix[i][j];
				blocks.add(block);
			}
		}
		
		super.updateChange(new UpdateMessage("excute",this.getDisplayList(blocks, GameState.OVER)));
			
		return true;
	}
	
	/**
	 * 示例方法，可选择是否保留该形式
	 * 
	 * 初始化BlockMatrix中的Block，并随机设置mineNum颗雷
	 * 同时可以为每个Block设定附近的雷数
	 * @param mineNum
	 * @return
	 */
	private boolean setBlock(int mineNum){
		
		int width = blockMatrix.length;
		int height = blockMatrix[0].length;
		int a,b;
		int c=mineNum;
		
		for(int i = 0 ; i<width; i++){
			for (int j = 0 ; j< height; j++){
				blockMatrix[i][j] = new BlockPO(i,j);
			}
		}
		
		for(int i=0;i<c;i++){
			a=(int)(Math.random()*width);
			b=(int)(Math.random()*height);
			
			if(blockMatrix[a][b].isMine()){
				i--;
			}else{
				blockMatrix[a][b].setMine(true);
				addMineNum(a,b);
				mineNum--;
			}
		}

		return false;
	}
	
	
	/**
	 * 示例方法，可选择是否保留该形式
	 * 
	 * 将(i,j)位置附近的Block雷数加1
	 * @param i
	 * @param j
	 */
	private void addMineNum(int i, int j){
		int width = blockMatrix.length;
		int height = blockMatrix[0].length;
		
		int tempI = i-1;		
		
		for(;tempI<=i+1;tempI++){
			int tempJ = j-1;
			for(;tempJ<=j+1;tempJ++){
				if((tempI>-1&&tempI<width)&&(tempJ>-1&&tempJ<height)){					
					blockMatrix[tempI][tempJ].addMine();
				}
			}
		}
		
	}
	
	/**
	 * 将逻辑对象转化为显示对象
	 * @param blocks
	 * @param gameState
	 * @return
	 */
	private List<BlockVO> getDisplayList(List<BlockPO> blocks, GameState gameState){
		List<BlockVO> result = new ArrayList<BlockVO>();
		for(BlockPO block : blocks){
			if(block != null){
				BlockVO displayBlock = block.getDisplayBlock(gameState);
				if(displayBlock.getState() != null)
				result.add(displayBlock);
			}
		}
		return result;
	}

	@Override
	public void setGameModel(GameModelService gameModel) {
		// TODO Auto-generated method stub
		this.gameModel = gameModel;
	}
	
	private void printBlockMatrix(){
		for(BlockPO[] blocks : this.blockMatrix){
			for(BlockPO b :blocks){
				String p = b.getMineNum()+"";
				if(b.isMine())
					p="*";
				System.out.print(p);
			}
			System.out.println();
		}
	}

	@Override
	public boolean netExcavate(int x, int y, boolean online) {
		// TODO Auto-generated method stub
		if(blockMatrix==null){
			return false;
		}
		
		List<BlockPO> blocks = new ArrayList<BlockPO>();
		BlockPO block = blockMatrix[x][y];
		if(block.getState()==BlockState.UNCLICK){
			block.setState(BlockState.CLICK);
		}else{
			return false;
		}
		blocks.add(block);
		
		GameState gameState = GameState.RUN;
		if(online){
			if(block.isMine()){
				gameState = GameState.OVER;
				this.gameModel.gameOver(GameResultState.SUCCESS);
			}	
		}
		else{
			if(block.isMine()){
				gameState = GameState.OVER;
				this.gameModel.gameOver(GameResultState.FAIL);
			}
		}

		super.updateChange(new UpdateMessage("excute",this.getDisplayList(blocks, gameState)));			

		if(block.getMineNum()==0){
			for(int i=x-1;i<=x+1;i++){
				for(int j=y-1;j<=y+1;j++){
					if(isValid(i,j)){
						netExcavate(i,j,online);
					}
				}
			}
		}
		
		return true;
		
	}

	@Override
	public boolean netMark(int x, int y, boolean isOnline) {
		// TODO Auto-generated method stub
		if(blockMatrix == null)
			return false;

		List<BlockPO> blocks = new ArrayList<BlockPO>();
		BlockPO block = blockMatrix[x][y];
		 
		BlockState state = block.getState();
		if(state==BlockState.UNCLICK){
			if(this.parameterModel.getMineNum()>0)
			block.setState(BlockState.FLAG);
			this.parameterModel.minusMineNum();
			if(TestServer.isServer||TestClient.isClient){
				if(isOnline){
					block.setState(BlockState.FLAG1);
					if(block.isMine()){
						mineNum--;
						this.parameterModel.addClientMineNum();
						if(mineNum==0){
							if(parameterModel.getClientMineNum()>parameterModel.getTrueMineNum()){
								this.gameModel.gameOver(GameResultState.FAIL);
							}else{
								this.gameModel.gameOver(GameResultState.SUCCESS);
							}
						}
					}else{
						this.gameModel.gameOver(GameResultState.SUCCESS);
					}
				}else{
					if(block.isMine()){
						mineNum--;
						this.parameterModel.addTrueMineNum();
						if(mineNum==0){
							if(parameterModel.getClientMineNum()>parameterModel.getTrueMineNum()){
								this.gameModel.gameOver(GameResultState.FAIL);
							}else{
								this.gameModel.gameOver(GameResultState.SUCCESS);
							}
						}
					}else{
						this.gameModel.gameOver(GameResultState.FAIL);
					}
				}
				
			}else{
				if(block.isMine()){
					mineNum--;
					if(mineNum==0){
						this.gameModel.gameOver(GameResultState.SUCCESS);
					}
				}
			}
		}else if((state==BlockState.FLAG)||(state==BlockState.FLAG1)){
			block.setState(BlockState.UNCLICK);
			this.parameterModel.addMineNum();
			if(TestServer.isServer||TestClient.isClient){
				if(isOnline){
					parameterModel.minusClientMineNum();
				}else{
					parameterModel.minusTrueMineNum();
				}
			}else{
				if(block.isMine()){
					mineNum++;
				}
			}
		}
		
		blocks.add(block);	
		super.updateChange(new UpdateMessage("excute",this.getDisplayList(blocks, GameState.RUN)));
		
		return true;
	}

	@Override
	public boolean netQuickExcavate(int x, int y, boolean isClient) {
	
		if(blockMatrix==null){
			return false;
		}
		
		BlockPO block = blockMatrix[x][y];
		if(block.getState()!=BlockState.CLICK){
			return false;
		}
		
		int flagNum=0;
		for(int i=x-1;i<=x+1;i++){
			for(int j=y-1;j<=y+1;j++){
				if(isValid(i,j)){
					if((blockMatrix[i][j].getState()==BlockState.FLAG)||(blockMatrix[i][j].getState()==BlockState.FLAG1)){
						flagNum++;
					}
					
				}
			}
		}

		if(flagNum==block.getMineNum()){
			for(int i=x-1;i<=x+1;i++){
				for(int j=y-1;j<=y+1;j++){
					if(isValid(i,j)){
						if((blockMatrix[i][j].getState()!=BlockState.FLAG)&&(blockMatrix[i][j].getState()!=BlockState.FLAG1)){
							netExcavate(i,j,isClient);
						}
						
					}
				}
			}
		}

		return true;
	}
}
