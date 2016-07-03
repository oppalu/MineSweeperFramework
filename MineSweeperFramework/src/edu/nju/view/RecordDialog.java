/*
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package edu.nju.view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import edu.nju.model.impl.StatisticModelImpl;
import edu.nju.model.service.StatisticModelService;

public class RecordDialog {

	public RecordDialog(JFrame parent) {
		super();
		initialization(parent);
	}

	public boolean show(int[][] score) {
		clear = false;
		this.score = score;
		dialog.setVisible(true);
		return clear;
	}
	
	public void show(){
		this.score=new int[][]{{0,0},{0,0},{0,0},{0,0}};
		statisticModel=new StatisticModelImpl();
		String[] record=statisticModel.showStatistics();
		if(record.length!=0){
		    for(int i=0;i<record.length;i++){
		    	if(record[i].contains("小")){
		    		score[0][1]++;
		    		if(record[i].substring(1).equals("SUCCESS")){
		    			score[0][0]++;
		    		}
		    	}else if(record[i].contains("中")){
		    		score[1][1]++;
		    		if(record[i].substring(1).equals("SUCCESS")){
		    			score[1][0]++;
		    		}
		    	}else if(record[i].contains("大")){
		    		score[2][1]++;
		    		if(record[i].substring(1).equals("SUCCESS")){
		    			score[2][0]++;
		    		}
		    	}else{
		    		score[3][1]++;
		    		if(record[i].substring(1).equals("SUCCESS")){
		    			score[3][0]++;
		    		}
		    	}
		    }
		}
		this.show(score);
	}

	private void initialization(JFrame parent) {

		dialog = new JDialog(parent, "record", true);

		okBtn = new JButton("ok");
		okBtn.setFont(new Font("Monospaced", Font.PLAIN, 12));
		okBtn.setBounds(100, 125, 70, 23);
		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
			}
		});
		
		this.score=new int[][]{{0,0},{0,0},{0,0},{0,0}};
		clearBtn = new JButton("clear");
		clearBtn.setFont(new Font("Monospaced", Font.PLAIN, 12));
		clearBtn.setBounds(192, 125, 70, 23);
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear = true;
				for(int i=0;i<4;i++){
					score[i][0]=0;
					score[i][1]=0;
				}
				textPanel.repaint();
				try {
					FileWriter writer = new FileWriter("save.dat");
					writer.write("");
					writer.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		line = new JSeparator();
		line.setBounds(20, 105, 240, 4);

		panel = new JPanel();
		panel.setLayout(null);

		textPanel = new DescribeTextPanel();
		panel.add(textPanel);

		panel.add(okBtn);
		panel.add(clearBtn);
		panel.add(line);

		dialog.setContentPane(panel);
		dialog.setBounds(parent.getLocation().x + 50,
				parent.getLocation().y + 50, 290, 190);

		clear = false;

	}

	@SuppressWarnings("serial")
	private class DescribeTextPanel extends JPanel {

		DescribeTextPanel() {
			super();
			setBounds(0, 0, 290, 110);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setFont(new Font("Monospaced", Font.PLAIN, 12));
			for (int i = 0; i < 4; i++) {
				g.drawString(rank[i], 40, 27 * (i + 1));
				g.drawString(String.valueOf(score[i][0]),150, 27 * (i + 1));
				g.drawString("/", 195, 27 * (i + 1));
				g.drawString(String.valueOf(score[i][1]),240, 27 * (i + 1));
			}
		}
	}

	private final String[] rank = { "Easy", "Hard", "Hell" ,"Custom"};
  	private JDialog dialog;

	private JPanel panel;

	private JButton okBtn;

	private JButton clearBtn;

	private JSeparator line;

	private int score[][];

	private JPanel textPanel;

	boolean clear;
	
	private StatisticModelService statisticModel;
}