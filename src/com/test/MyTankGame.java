package com.test;
/*
 * 功能：绘制坦克
 */
import java.awt.*;
import javax.swing.*;
public class MyTankGame extends JFrame{
    Mypanel mp=null;
	
	public static void main(String[] args) {
		MyTankGame mtk=new MyTankGame();

	}
	
	//构造函数
	public MyTankGame(){
	  	mp=new Mypanel();
	  	this.add(mp);
	  	this.setSize(400, 300);
	  	int w = (Toolkit.getDefaultToolkit().getScreenSize().width-400) / 2;
	  	int h = (Toolkit.getDefaultToolkit().getScreenSize().height-300 ) / 2;
	  	this.setLocation(w, h);
	  	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  	this.setVisible(true);
		
	}

}
//定义我的面板
class Mypanel extends JPanel{
	//声明一个我的坦克变量
	Hero hero=null;
	//构造函数
	public Mypanel(){
		hero=new Hero(10,10);
	}
	public void paint(Graphics g){
	   //覆盖父类函数
		super.paint(g);
	   //首先设置颜色
	   //设置窗体的背景色为黑色
		g.fillRect(0, 0, 400, 300);
		this.drawTank(hero.getX(), hero.getY(), g, 0, 1);
		
	}
	public void drawTank(int x,int y,Graphics g,int direction,int type){
		
		//判断坦克的类型
		switch(type){
		
			case 0:g.setColor(Color.cyan);
			break;
			case 1:g.setColor(Color.yellow);
			break;
			
		}
		//判断坦克的行动方向
		switch(direction){
		
		    case 0:
		   //画出左边矩形
			g.fill3DRect(x, y, 5, 30,false);
		   //画出右边矩形
			g.fill3DRect(x+15, y, 5, 30,false);
		   //画出中间矩形
			g.fill3DRect(x+5, y+5, 10, 20,false);
			//画出中间圆形
			g.fillOval(x+4, y+10, 10, 10);
			//画出中间直线
			g.drawLine(x+9, y, x+9, y+15);	
			break;
		}
		
	}
	
}

//定义坦克类
class Tank{
	
	//坦克的横坐标
	int x=0;
	//坦克的纵坐标
	int y=0;
	//构造函数
	public Tank(int x,int y){
		this.x=x;
		this.y=y;
		
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
}

//定义我的坦克
class Hero extends Tank{
	//构造函数
	public Hero(int x,int y){
		
		super(x, y);
	}
}