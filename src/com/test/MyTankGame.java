package com.test;
/*
 * ���ܣ�����̹��
 */
import java.awt.*;
import javax.swing.*;
public class MyTankGame extends JFrame{
    Mypanel mp=null;
	
	public static void main(String[] args) {
		MyTankGame mtk=new MyTankGame();

	}
	
	//���캯��
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
//�����ҵ����
class Mypanel extends JPanel{
	//����һ���ҵ�̹�˱���
	Hero hero=null;
	//���캯��
	public Mypanel(){
		hero=new Hero(10,10);
	}
	public void paint(Graphics g){
	   //���Ǹ��ຯ��
		super.paint(g);
	   //����������ɫ
	   //���ô���ı���ɫΪ��ɫ
		g.fillRect(0, 0, 400, 300);
		this.drawTank(hero.getX(), hero.getY(), g, 0, 1);
		
	}
	public void drawTank(int x,int y,Graphics g,int direction,int type){
		
		//�ж�̹�˵�����
		switch(type){
		
			case 0:g.setColor(Color.cyan);
			break;
			case 1:g.setColor(Color.yellow);
			break;
			
		}
		//�ж�̹�˵��ж�����
		switch(direction){
		
		    case 0:
		   //������߾���
			g.fill3DRect(x, y, 5, 30,false);
		   //�����ұ߾���
			g.fill3DRect(x+15, y, 5, 30,false);
		   //�����м����
			g.fill3DRect(x+5, y+5, 10, 20,false);
			//�����м�Բ��
			g.fillOval(x+4, y+10, 10, 10);
			//�����м�ֱ��
			g.drawLine(x+9, y, x+9, y+15);	
			break;
		}
		
	}
	
}

//����̹����
class Tank{
	
	//̹�˵ĺ�����
	int x=0;
	//̹�˵�������
	int y=0;
	//���캯��
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

//�����ҵ�̹��
class Hero extends Tank{
	//���캯��
	public Hero(int x,int y){
		
		super(x, y);
	}
}