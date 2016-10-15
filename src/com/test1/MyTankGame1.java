package com.test1;
/*
 * 功能：1.绘制坦克
 *       2.让坦克可以上下左右移动
 */
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
public class MyTankGame1 extends JFrame{
    Mypanel mp=null;
	
	public static void main(String[] args) {
		MyTankGame1 mtk=new MyTankGame1();

	}
	
	//构造函数
	public MyTankGame1(){
	  	mp=new Mypanel();
	  	this.add(mp);
	  	this.addKeyListener(mp);
	  	this.setSize(400, 300);
	  	int w = (Toolkit.getDefaultToolkit().getScreenSize().width-400) / 2;
	  	int h = (Toolkit.getDefaultToolkit().getScreenSize().height-300 ) / 2;
	  	this.setLocation(w, h);
	  	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  	this.setVisible(true);
		
	}

}
//定义我的面板
class Mypanel extends JPanel implements KeyListener{
	//声明一个我的坦克变量
	Hero hero=null;
	//声明一个敌人坦克组
	Vector<EnemyTank>ets=new Vector<EnemyTank>();
	int ensize=3;
	//构造函数
	public Mypanel(){
		hero=new Hero(100,100);
		for(int i=0;i<ensize;i++){
			EnemyTank et=new EnemyTank((i+1)*50,20);
			et.setDirection(1);
			ets.add(et);
		 }
	  }
	public void paint(Graphics g){
	   //覆盖父类函数
		super.paint(g);
	   //首先设置颜色
	   //设置窗体的背景色为黑色
		g.fillRect(0, 0, 400, 300);
		this.drawTank(hero.getX(), hero.getY(), g, this.hero.direction, 1);
		for(int i=0;i<ets.size();i++){
			this.drawTank(ets.get(i).getX(), ets.get(i).getY(), g,ets.get(i).direction, 0);
		}
		
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
			g.fill3DRect(x-10, y-15, 5, 30,false);
		   //画出右边矩形
			g.fill3DRect(x+5, y-15, 5, 30,false);
		   //画出中间矩形
			g.fill3DRect(x-5, y-10, 10, 20,false);
			//画出中间圆形
			g.fillOval(x-6, y-5, 10, 10);
			//画出中间直线
			g.drawLine(x-1, y, x-1, y-15);	
			break;
		    case 1:
		    //画出左边矩形
			g.fill3DRect(x-10, y-15, 5, 30,false);
			//画出右边矩形
			g.fill3DRect(x+5, y-15, 5, 30,false);
			//画出中间矩形
			g.fill3DRect(x-5, y-10, 10, 20,false);
			//画出中间圆形
			g.fillOval(x-6, y-5, 10, 10);
			//画出中间直线
			g.drawLine(x-1, y, x-1, y+15);	
			break;
		    case 2:
		    //画出上边矩形
		    g.fill3DRect(x-15, y-10, 30, 5, false);
		    //画出下边矩形
		    g.fill3DRect(x-15, y+5,30, 5, false);
		    //画出中间矩形
		    g.fill3DRect(x-10, y-5, 20,10, false);
		    //画出中间圆形
		    g.fillOval(x-5, y-5, 10, 10);
		    //画出中间直线
		    g.drawLine(x, y-1, x-15, y-1);
		    break;
		    case 3:
		    //画出上边矩形
			g.fill3DRect(x-15, y-10, 30, 5, false);
			//画出下边矩形
			g.fill3DRect(x-15, y+5,30, 5, false);
			//画出中间矩形
			g.fill3DRect(x-10, y-5, 20,10, false);
			//画出中间圆形
			g.fillOval(x-5, y-5, 10, 10);
			//画出中间直线
			g.drawLine(x, y-1, x+15, y-1);
			break;
		 }
		
	}
	
	public void keyPressed(KeyEvent e) {
		 //坦克向上
		if(e.getKeyCode()==KeyEvent.VK_W){
			this.hero.setDirection(0);
			this.hero.moveUp();
			
		}//坦克向下
		else if(e.getKeyCode()==KeyEvent.VK_S){
			this.hero.setDirection(1);
			this.hero.moveDown();
		}//坦克向左
		else if(e.getKeyCode()==KeyEvent.VK_A){
			this.hero.setDirection(2);
			this.hero.moveLeft();	
		}//坦克向右
		else if(e.getKeyCode()==KeyEvent.VK_D){
			this.hero.setDirection(3);
			this.hero.moveRight();
		}
		this.repaint();
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}

