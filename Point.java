package wuziqi;

import java.awt.Color;

public class Point 
{
	private int x,y;
	private Color color; //��ɫ
	public static final int DIAMETER = 30;
	//ֱ��
	public Point(int x,int y,Color color)
	{
		this.x = x;
		this.y = y;
		this.color = color;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public Color getColor(){
		return color;
	}
}
