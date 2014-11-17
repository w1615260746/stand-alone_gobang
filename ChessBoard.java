package wuziqi;
import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseListener;
//import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
/*
 * 五子棋-棋盘类
 */
public class ChessBoard extends JPanel implements MouseListener
{
	public static final int MARGIN = 30,GRID_SPAN = 35,ROWS = 20,COLS = 20;
	//边距，网格间距，棋盘行数，棋盘列数
	Point[] chessList = new Point[(ROWS+1)*(COLS+1)];
	//初始每个数组元素为null
	boolean isBlack = true;//是否黑先
	boolean gameOver = false;
	int chessCount; //当前棋盘的棋子个数
	int xIndex,yIndex;//当前刚下的棋子的索引
	public ChessBoard()
	{
		setBackground(Color.cyan);
		addMouseListener(this);
		addMouseMotionListener(new MouseMotionListener() {
			public void mouseMoved(MouseEvent e) {
				int x1 = (e.getX()-MARGIN+GRID_SPAN/2)/GRID_SPAN;
				//将鼠标单击的坐标位置装换成网格索引
				int y1 = (e.getY()-MARGIN+GRID_SPAN/2)/GRID_SPAN;
				//游戏已经结束，不能下
				//落在棋盘外，不能下
				//x，y位置已经有棋子存在，不能下
				if (x1<0||x1>ROWS||y1<0||y1>COLS||gameOver||findChess(x1,y1)) 
				{
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));//设置成默认形
				}
				else
				{
					setCursor(new Cursor(Cursor.HAND_CURSOR));//设置成手型
				}
			}
			public void mouseDragged(MouseEvent e) {
			}
		});
	}
	//绘制
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		//画棋盘
		for (int i = 0; i <= ROWS; i++) {
			g.drawLine(MARGIN, MARGIN+i*GRID_SPAN, MARGIN+COLS*GRID_SPAN,MARGIN+i*GRID_SPAN);
		}
		for (int i = 0; i <= COLS; i++) {
			g.drawLine(MARGIN+i*GRID_SPAN, MARGIN, MARGIN+i*GRID_SPAN, MARGIN+ROWS*GRID_SPAN);
		}
		for (int i = 0; i < chessCount; i++) {
			int xPos = chessList[i].getX()*GRID_SPAN+MARGIN;
			//网格交叉点的x坐标
			int yPos = chessList[i].getY()*GRID_SPAN+MARGIN;
			//网格交叉点的y坐标
			g.setColor(chessList[i].getColor());//设置颜色
		
			g.fillOval(xPos-Point.DIAMETER/2, yPos-Point.DIAMETER/2, Point.DIAMETER, Point.DIAMETER);
			//填充棋子
			
			//标记最后一个棋子的红矩形框
			if (i==chessCount-1) 
			{//最后一个棋子
				g.setColor(Color.red);
				g.drawRect(xPos-Point.DIAMETER/2, yPos-Point.DIAMETER/2, Point.DIAMETER,Point.DIAMETER);
			}
		}
	}
	//鼠标按键在组件上按下时调用
	public void mousePressed(MouseEvent e) 
	{//游戏已经结束，不能下
		if (gameOver) return;
		String colorName = isBlack?"黑棋":"白棋";
		xIndex = (e.getX()-MARGIN+GRID_SPAN/2)/GRID_SPAN;
		//将鼠标单击的坐标位置转换成网格索引
		yIndex = (e.getY()-MARGIN+GRID_SPAN/2)/GRID_SPAN;
		
		//落在棋盘外，不能下
		if (xIndex<0||xIndex>ROWS||yIndex<0||yIndex>COLS) return;
		
		//x，y位置 已经有棋子 存在，不能下
		if (findChess(xIndex,yIndex)) return;
		
		Point ch = new Point(xIndex, yIndex, isBlack?Color.black:Color.white);
		chessList[chessCount++] = ch;
		repaint();// 通知系统重新绘制
		if (isWin()) 
		{//给出胜利信息，不能再继续下棋
			String msg = String.format("恭喜，%s赢了", colorName);
			JOptionPane.showMessageDialog(this, msg);
			gameOver = true;
		}
			isBlack = !isBlack;
	}
		//覆盖 MouseListener的方法
	public void mouseClisked(MouseEvent e){
	}//鼠标按键在组件上单击（按下并释放）时调用
	public void mouseEntered(MouseEvent e){
	}//鼠标进入到组件上时调用
	public void mouseExited(MouseEvent e){
	}//鼠标离开组件时调用
	public void mouseReleased(MouseEvent e){
	}//鼠标按钮在组件上释放时调用
		
	//在棋子数组中查找是否有索引为x，y的棋子存在
	private boolean findChess(int x,int y)
	{
		for (Point c : chessList) 
		{
			if (c!=null&&c.getX()==x&&c.getY()==y) return true;
		}
		return false;
	}
	
	//判断哪方赢
	private boolean isWin()
	{
		int continueCount = 1;
		//连续棋子的个数
		//横向向西寻找
		for (int x = xIndex-1; x >= 0; x--) 
		{
			Color c = isBlack ? Color.black:Color.white;
			if (getChess(x,yIndex,c)!=null) continueCount++;
			else break;
		}
		
		//横向向东寻找
		for (int x = xIndex+1; x <= COLS; x++) 
		{
			Color c = isBlack ? Color.black:Color.white;
			if (getChess(x,yIndex,c)!=null) continueCount++;
			else break;
		}
		
		//判断记录数是否大于等于5，即表示此方取胜
		if (continueCount>=5) {
			return true;
		}else 
			continueCount = 1;
		
		//继续另一种情况的搜索：纵向
		//纵向向南寻找
		for (int y = yIndex-1; y >= 0; y--) 
		{
			Color c = isBlack ? Color.black:Color.white;
			if (getChess(xIndex,y,c)!=null) continueCount++;
			else break;
		}
		//纵向向北寻找
		for (int y = yIndex+1; y <= ROWS; y++) 
		{
			Color c = isBlack ? Color.black:Color.white;
			if (getChess(xIndex,y,c)!=null) continueCount++;
			else break;
		}
		//判断记录数是否大于等于5，即表示此方取胜
		if (continueCount>=5) {
			return true;
		}else 
			continueCount = 1;
		
		//继续另一种情况的搜索：斜向
		//右上寻找
		for (int x = xIndex+1, y = yIndex-1; x <= COLS&&y >=0; x++,y--) 
		{
			Color c = isBlack ? Color.black:Color.white;
			if (getChess(x,y,c)!=null) continueCount++;
			else break;
		}
		//左下寻找
		for (int x = xIndex-1, y = yIndex+1; x >= 0&&y <= ROWS; x--,y++) 
		{
			Color c = isBlack ? Color.black:Color.white;
			if (getChess(x,y,c)!=null) continueCount++;
			else break;
		}
		//判断记录数是否大于等于5，即表示此方取胜
		if (continueCount>=5) {
			return true;
		}else 
			continueCount = 1;
		
		//继续另一种情况的搜索：斜向
		//右下寻找
		for (int x = xIndex+1, y = yIndex+1; x <= COLS&&y <= ROWS; x++,y++) 
		{
			Color c = isBlack ? Color.black:Color.white;
			if (getChess(x,y,c)!=null) continueCount++;
			else break;
		}
		//左上寻找
		for (int x = xIndex-1, y = yIndex-1; x >= 0&&y >= 0; x--,y--) 
		{
			Color c = isBlack ? Color.black:Color.white;
			if (getChess(x,y,c)!=null) continueCount++;
			else break;
		}
		//判断记录数是否大于等于5，即表示此方取胜
		if (continueCount>=5) {
			return true;
		}else 
			continueCount = 1;
		return false;
	}
	
	
	private Point getChess(int xIndex,int yIndex,Color color)
	{
		for(Point c:chessList)
		{
			if (c!=null&&c.getX()==xIndex&&c.getY()==yIndex&&c.getColor()==color) 
				return c;
		}
		return null;
	}
	
	public void restartGame()
	{//清除棋子
		for (int i = 0; i < chessList.length; i++) chessList[i] = null;
		
		//恢复游戏相关的变量值
		isBlack = true;
		gameOver = false;
		chessCount = 0;
		repaint();
	}
	
	//悔棋
	public void goback()
	{
		if (chessCount==0) return;
		chessList[chessCount -1] = null;
		chessCount--;
		if (chessCount>0) 
		{
			xIndex = chessList[chessCount - 1].getX();
			yIndex = chessList[chessCount - 1].getY();
		}
		isBlack = !isBlack;
		repaint();
	}
	//Dimension:矩形
	public Dimension getpreferredSize(){
		return new Dimension(MARGIN*2+GRID_SPAN*COLS,MARGIN*2+GRID_SPAN*ROWS);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public static void main(String[] args) {
		JFrame aa = new JFrame();
		ChessBoard bb = new ChessBoard();
		aa.add(bb);
		aa.setVisible(true);
	}
	
}
	
	


