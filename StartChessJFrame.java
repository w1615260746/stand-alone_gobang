package wuziqi;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
/*
 * ������-������࣬����������
 */
public class StartChessJFrame extends JFrame
{
	private ChessBoard chessBoard;
	//��ս���
	private JPanel toolbar;
	//���������
	private JButton startButton,backButton,exitButton;
	//���¿�ʼ ��ť ���尴ť ���˳���ť
	private JMenuBar menuBar;
	//�˵���
	private JMenu sysMenu;
	//ϵͳ�˵�
	private JMenuItem startMenuItem,exitMenuItem,backMenuItem;
	//���¿�ʼ  �˳�  �� ���� �˵���
	public StartChessJFrame() 
	{
		setTitle("������������");
		chessBoard = new ChessBoard();
		//��ʼ��������
		//��������Ӳ˵�
		menuBar = new JMenuBar();
		sysMenu = new JMenu("ϵͳ");
		startMenuItem = new JMenuItem("���¿�ʼ");
		backMenuItem = new JMenuItem("����");
		exitMenuItem = new JMenuItem("�˳�");
		sysMenu.add(startMenuItem);sysMenu.add(backMenuItem);sysMenu.add(exitMenuItem);
		MyItemListener lis = new MyItemListener();
		startMenuItem.addActionListener(lis);
		backMenuItem.addActionListener(lis);
		exitMenuItem.addActionListener(lis);
		menuBar.add(sysMenu);
		setJMenuBar(menuBar);
		toolbar = new JPanel();
		startButton = new JButton("���¿�ʼ");
		backButton = new JButton("����");
		exitButton = new JButton("�˳�");
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
			{//���¿�ʼ //JFiveFrame.this �ڲ��������ⲿ��
				System.out.println("���¿�ʼ...");
				chessBoard.restartGame();
			}
			else if (obj==exitMenuItem||obj==exitButton) 
			{//����Ӧ�ó���
				System.exit(0);
			}
			else if (obj==backMenuItem||obj==backButton) 
			{
				System.out.println("����...");
				chessBoard.goback();
			}
		}
	}
	public static void main(String[] args) 
	{
		StartChessJFrame f = new StartChessJFrame();
		//���������
		f.setVisible(true);
		//��ʾ�����
	}
}











