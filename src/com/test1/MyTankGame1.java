package com.test1;
/*
 * ���ܣ�1.����̹��
 *       2.��̹�˿������������ƶ�
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
	
	//���캯��
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
//�����ҵ����
class Mypanel extends JPanel implements KeyListener{
	//����һ���ҵ�̹�˱���
	Hero hero=null;
	//����һ������̹����
	Vector<EnemyTank>ets=new Vector<EnemyTank>();
	int ensize=3;
	//���캯��
	public Mypanel(){
		hero=new Hero(100,100);
		for(int i=0;i<ensize;i++){
			EnemyTank et=new EnemyTank((i+1)*50,20);
			et.setDirection(1);
			ets.add(et);
		 }
	  }
	public void paint(Graphics g){
	   //���Ǹ��ຯ��
		super.paint(g);
	   //����������ɫ
	   //���ô���ı���ɫΪ��ɫ
		g.fillRect(0, 0, 400, 300);
		this.drawTank(hero.getX(), hero.getY(), g, this.hero.direction, 1);
		for(int i=0;i<ets.size();i++){
			this.drawTank(ets.get(i).getX(), ets.get(i).getY(), g,ets.get(i).direction, 0);
		}
		
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
			g.fill3DRect(x-10, y-15, 5, 30,false);
		   //�����ұ߾���
			g.fill3DRect(x+5, y-15, 5, 30,false);
		   //�����м����
			g.fill3DRect(x-5, y-10, 10, 20,false);
			//�����м�Բ��
			g.fillOval(x-6, y-5, 10, 10);
			//�����м�ֱ��
			g.drawLine(x-1, y, x-1, y-15);	
			break;
		    case 1:
		    //������߾���
			g.fill3DRect(x-10, y-15, 5, 30,false);
			//�����ұ߾���
			g.fill3DRect(x+5, y-15, 5, 30,false);
			//�����м����
			g.fill3DRect(x-5, y-10, 10, 20,false);
			//�����м�Բ��
			g.fillOval(x-6, y-5, 10, 10);
			//�����м�ֱ��
			g.drawLine(x-1, y, x-1, y+15);	
			break;
		    case 2:
		    //�����ϱ߾���
		    g.fill3DRect(x-15, y-10, 30, 5, false);
		    //�����±߾���
		    g.fill3DRect(x-15, y+5,30, 5, false);
		    //�����м����
		    g.fill3DRect(x-10, y-5, 20,10, false);
		    //�����м�Բ��
		    g.fillOval(x-5, y-5, 10, 10);
		    //�����м�ֱ��
		    g.drawLine(x, y-1, x-15, y-1);
		    break;
		    case 3:
		    //�����ϱ߾���
			g.fill3DRect(x-15, y-10, 30, 5, false);
			//�����±߾���
			g.fill3DRect(x-15, y+5,30, 5, false);
			//�����м����
			g.fill3DRect(x-10, y-5, 20,10, false);
			//�����м�Բ��
			g.fillOval(x-5, y-5, 10, 10);
			//�����м�ֱ��
			g.drawLine(x, y-1, x+15, y-1);
			break;
		 }
		
	}
	
	public void keyPressed(KeyEvent e) {
		 //̹������
		if(e.getKeyCode()==KeyEvent.VK_W){
			this.hero.setDirection(0);
			this.hero.moveUp();
			
		}//̹������
		else if(e.getKeyCode()==KeyEvent.VK_S){
			this.hero.setDirection(1);
			this.hero.moveDown();
		}//̹������
		else if(e.getKeyCode()==KeyEvent.VK_A){
			this.hero.setDirection(2);
			this.hero.moveLeft();	
		}//̹������
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

