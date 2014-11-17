package wuziqi;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
/*
 * 五子棋-主框架类，程序启动类
 */
public class StartChessJFrame extends JFrame
{
	private ChessBoard chessBoard;
	//对战面板
	private JPanel toolbar;
	//工具条面板
	private JButton startButton,backButton,exitButton;
	//重新开始 按钮 悔棋按钮 和退出按钮
	private JMenuBar menuBar;
	//菜单栏
	private JMenu sysMenu;
	//系统菜单
	private JMenuItem startMenuItem,exitMenuItem,backMenuItem;
	//重新开始  退出  和 悔棋 菜单项
	public StartChessJFrame() 
	{
		setTitle("单机版五子棋");
		chessBoard = new ChessBoard();
		//初始化面板对象
		//创建和添加菜单
		menuBar = new JMenuBar();
		sysMenu = new JMenu("系统");
		startMenuItem = new JMenuItem("重新开始");
		backMenuItem = new JMenuItem("悔棋");
		exitMenuItem = new JMenuItem("退出");
		sysMenu.add(startMenuItem);sysMenu.add(backMenuItem);sysMenu.add(exitMenuItem);
		MyItemListener lis = new MyItemListener();
		startMenuItem.addActionListener(lis);
		backMenuItem.addActionListener(lis);
		exitMenuItem.addActionListener(lis);
		menuBar.add(sysMenu);
		setJMenuBar(menuBar);
		toolbar = new JPanel();
		startButton = new JButton("重新开始");
		backButton = new JButton("悔棋");
		exitButton = new JButton("退出");
		toolbar.setLayout(new FlowLayout(FlowLayout.CENTER));
		toolbar.add(startButton);
		toolbar.add(backButton);
		toolbar.add(exitButton);
		startButton.addActionListener(lis);
		backButton.addActionListener(lis);
		exitButton.addActionListener(lis);
		add(toolbar,BorderLayout.SOUTH);
		add(chessBoard);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//pack();
		setSize(406,493);
//		setResizable(false);
	}
	private class MyItemListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			Object obj = e.getSource();
			if (obj==StartChessJFrame.this.startMenuItem||obj==startButton) 
			{//重新开始 //JFiveFrame.this 内部类引用外部类
				System.out.println("重新开始...");
				chessBoard.restartGame();
			}
			else if (obj==exitMenuItem||obj==exitButton) 
			{//结束应用程序
				System.exit(0);
			}
			else if (obj==backMenuItem||obj==backButton) 
			{
				System.out.println("悔棋...");
				chessBoard.goback();
			}
		}
	}
	public static void main(String[] args) 
	{
		StartChessJFrame f = new StartChessJFrame();
		//创建主框架
		f.setVisible(true);
		//显示主框架
	}
}











