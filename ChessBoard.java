package wuziqi;
import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseListener;
//import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
/*
 * ������-������
 */
public class ChessBoard extends JPanel implements MouseListener
{
	public static final int MARGIN = 30,GRID_SPAN = 35,ROWS = 20,COLS = 20;
	//�߾࣬�����࣬������������������
	Point[] chessList = new Point[(ROWS+1)*(COLS+1)];
	//��ʼÿ������Ԫ��Ϊnull
	boolean isBlack = true;//�Ƿ����
	boolean gameOver = false;
	int chessCount; //��ǰ���̵����Ӹ���
	int xIndex,yIndex;//��ǰ���µ����ӵ�����
	public ChessBoard()
	{
		setBackground(Color.cyan);
		addMouseListener(this);
		addMouseMotionListener(new MouseMotionListener() {
			public void mouseMoved(MouseEvent e) {
				int x1 = (e.getX()-MARGIN+GRID_SPAN/2)/GRID_SPAN;
				//����굥��������λ��װ������������
				int y1 = (e.getY()-MARGIN+GRID_SPAN/2)/GRID_SPAN;
				//��Ϸ�Ѿ�������������
				//���������⣬������
				//x��yλ���Ѿ������Ӵ��ڣ�������
				if (x1<0||x1>ROWS||y1<0||y1>COLS||gameOver||findChess(x1,y1)) 
				{
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));//���ó�Ĭ����
				}
				else
				{
					setCursor(new Cursor(Cursor.HAND_CURSOR));//���ó�����
				}
			}
			public void mouseDragged(MouseEvent e) {
			}
		});
	}
	//����
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		//������
		for (int i = 0; i <= ROWS; i++) {
			g.drawLine(MARGIN, MARGIN+i*GRID_SPAN, MARGIN+COLS*GRID_SPAN,MARGIN+i*GRID_SPAN);
		}
		for (int i = 0; i <= COLS; i++) {
			g.drawLine(MARGIN+i*GRID_SPAN, MARGIN, MARGIN+i*GRID_SPAN, MARGIN+ROWS*GRID_SPAN);
		}
		for (int i = 0; i < chessCount; i++) {
			int xPos = chessList[i].getX()*GRID_SPAN+MARGIN;
			//���񽻲���x����
			int yPos = chessList[i].getY()*GRID_SPAN+MARGIN;
			//���񽻲���y����
			g.setColor(chessList[i].getColor());//������ɫ
		
			g.fillOval(xPos-Point.DIAMETER/2, yPos-Point.DIAMETER/2, Point.DIAMETER, Point.DIAMETER);
			//�������
			
			//������һ�����ӵĺ���ο�
			if (i==chessCount-1) 
			{//���һ������
				g.setColor(Color.red);
				g.drawRect(xPos-Point.DIAMETER/2, yPos-Point.DIAMETER/2, Point.DIAMETER,Point.DIAMETER);
			}
		}
	}
	//��갴��������ϰ���ʱ����
	public void mousePressed(MouseEvent e) 
	{//��Ϸ�Ѿ�������������
		if (gameOver) return;
		String colorName = isBlack?"����":"����";
		xIndex = (e.getX()-MARGIN+GRID_SPAN/2)/GRID_SPAN;
		//����굥��������λ��ת������������
		yIndex = (e.getY()-MARGIN+GRID_SPAN/2)/GRID_SPAN;
		
		//���������⣬������
		if (xIndex<0||xIndex>ROWS||yIndex<0||yIndex>COLS) return;
		
		//x��yλ�� �Ѿ������� ���ڣ�������
		if (findChess(xIndex,yIndex)) return;
		
		Point ch = new Point(xIndex, yIndex, isBlack?Color.black:Color.white);
		chessList[chessCount++] = ch;
		repaint();// ֪ͨϵͳ���»���
		if (isWin()) 
		{//����ʤ����Ϣ�������ټ�������
			String msg = String.format("��ϲ��%sӮ��", colorName);
			JOptionPane.showMessageDialog(this, msg);
			gameOver = true;
		}
			isBlack = !isBlack;
	}
		//���� MouseListener�ķ���
	public void mouseClisked(MouseEvent e){
	}//��갴��������ϵ��������²��ͷţ�ʱ����
	public void mouseEntered(MouseEvent e){
	}//�����뵽�����ʱ����
	public void mouseExited(MouseEvent e){
	}//����뿪���ʱ����
	public void mouseReleased(MouseEvent e){
	}//��갴ť��������ͷ�ʱ����
		
	//�����������в����Ƿ�������Ϊx��y�����Ӵ���
	private boolean findChess(int x,int y)
	{
		for (Point c : chessList) 
		{
			if (c!=null&&c.getX()==x&&c.getY()==y) return true;
		}
		return false;
	}
	
	//�ж��ķ�Ӯ
	private boolean isWin()
	{
		int continueCount = 1;
		//�������ӵĸ���
		//��������Ѱ��
		for (int x = xIndex-1; x >= 0; x--) 
		{
			Color c = isBlack ? Color.black:Color.white;
			if (getChess(x,yIndex,c)!=null) continueCount++;
			else break;
		}
		
		//������Ѱ��
		for (int x = xIndex+1; x <= COLS; x++) 
		{
			Color c = isBlack ? Color.black:Color.white;
			if (getChess(x,yIndex,c)!=null) continueCount++;
			else break;
		}
		
		//�жϼ�¼���Ƿ���ڵ���5������ʾ�˷�ȡʤ
		if (continueCount>=5) {
			return true;
		}else 
			continueCount = 1;
		
		//������һ�����������������
		//��������Ѱ��
		for (int y = yIndex-1; y >= 0; y--) 
		{
			Color c = isBlack ? Color.black:Color.white;
			if (getChess(xIndex,y,c)!=null) continueCount++;
			else break;
		}
		//������Ѱ��
		for (int y = yIndex+1; y <= ROWS; y++) 
		{
			Color c = isBlack ? Color.black:Color.white;
			if (getChess(xIndex,y,c)!=null) continueCount++;
			else break;
		}
		//�жϼ�¼���Ƿ���ڵ���5������ʾ�˷�ȡʤ
		if (continueCount>=5) {
			return true;
		}else 
			continueCount = 1;
		
		//������һ�������������б��
		//����Ѱ��
		for (int x = xIndex+1, y = yIndex-1; x <= COLS&&y >=0; x++,y--) 
		{
			Color c = isBlack ? Color.black:Color.white;
			if (getChess(x,y,c)!=null) continueCount++;
			else break;
		}
		//����Ѱ��
		for (int x = xIndex-1, y = yIndex+1; x >= 0&&y <= ROWS; x--,y++) 
		{
			Color c = isBlack ? Color.black:Color.white;
			if (getChess(x,y,c)!=null) continueCount++;
			else break;
		}
		//�жϼ�¼���Ƿ���ڵ���5������ʾ�˷�ȡʤ
		if (continueCount>=5) {
			return true;
		}else 
			continueCount = 1;
		
		//������һ�������������б��
		//����Ѱ��
		for (int x = xIndex+1, y = yIndex+1; x <= COLS&&y <= ROWS; x++,y++) 
		{
			Color c = isBlack ? Color.black:Color.white;
			if (getChess(x,y,c)!=null) continueCount++;
			else break;
		}
		//����Ѱ��
		for (int x = xIndex-1, y = yIndex-1; x >= 0&&y >= 0; x--,y--) 
		{
			Color c = isBlack ? Color.black:Color.white;
			if (getChess(x,y,c)!=null) continueCount++;
			else break;
		}
		//�жϼ�¼���Ƿ���ڵ���5������ʾ�˷�ȡʤ
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
	{//�������
		for (int i = 0; i < chessList.length; i++) chessList[i] = null;
		
		//�ָ���Ϸ��صı���ֵ
		isBlack = true;
		gameOver = false;
		chessCount = 0;
		repaint();
	}
	
	//����
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
	//Dimension:����
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
	
	


