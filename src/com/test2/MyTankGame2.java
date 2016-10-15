package com.test2;
/*
 * ���ܣ�1.����̹��
 *       2.���ҵ�̹�˿������������ƶ�
 *       3.���ҵ�̹�˿��Է����ӵ������5��
 *       4.�����е���̹�ˣ��ӵ�������̹����ʧ
 */
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
public class MyTankGame2 extends JFrame{
       Mypanel mp=null;
	
	public static void main(String[] args) {
		MyTankGame2 mtk=new MyTankGame2();

	}
	
	//���캯��
	public MyTankGame2(){
	  	mp=new Mypanel();
	  	Thread t=new Thread(mp);
	  	t.start();
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
class Mypanel extends JPanel implements KeyListener,Runnable{
	//����һ���ҵ�̹�˱���
	Hero hero=null;
	//����һ������̹����
	Vector<EnemyTank>ets=new Vector<EnemyTank>();
	//����һ��ը���飨��һ����Ļ�У�������ͬʱ���ֶ����ը���棩
	Vector<Bomb>bombs=new Vector<Bomb>();
	int ensize=3;
	//��ըЧ��������ͼƬ��Ѹ���л������Զ�������ͼƬ
	Image image1=null;
	Image image2=null;
	Image image3=null;
	
     //���캯��
	 public Mypanel(){
		hero=new Hero(80,100);
//		image1=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
//		image2=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
//		image3=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));
		
		//���ַ�ʽ��һ����ը��Ч�������ԣ��ʲ��������������ʽ
		//������������ʽ������һ�����⣬�����ҵ�̹�˺��ٴλ��и�����
		//������ֱ�ըЧ��
		try{
			  image1=ImageIO.read(new File("src/bomb_1.gif"));
			  image2=ImageIO.read(new File("src/bomb_2.gif"));
			  image3=ImageIO.read(new File("src/bomb_3.gif"));
		 }
		 catch(Exception e){
			 e.printStackTrace();
		  }
		for(int i=0;i<ensize;i++){
			EnemyTank et=new EnemyTank((i+1)*100,20);
			et.setDirection(1);
			//����̹�˴���ʱ��һ���ӵ�
			Shot s=new Shot(et.x,et.y+15,1);
			//���ӵ���ӵ�������
			et.ss.add(s);
			ets.add(et);
			//��������̹�˵��߳�
			Thread t=new Thread(et);
			t.start();
			//�����ӵ����߳�
			Thread t1=new Thread(s);
			t1.start();
			
		 }
	  }
	public void paint(Graphics g){
	   //���Ǹ��ຯ��
		super.paint(g);
	   //����������ɫ
	   //���ô���ı���ɫΪ��ɫ
		g.fillRect(0, 0, 400, 300);
		//�����ҵ�̹��
		if(hero.isAlive)
		{
		this.drawTank(hero.getX(), hero.getY(), g, this.hero.direction, 1);
		}
		//�����ӵ��������Ƕ�ţ�
		for(int i=0;i<hero.ss.size();i++)
	   {
		Shot myShot=hero.ss.get(i);	
		if(myShot!=null&&myShot.isAlive==true)
	    {
		g.draw3DRect(myShot.x,myShot.y, 1, 1, false);
		  
		 }
		if(myShot.isAlive==false)
		{
			hero.ss.remove(myShot);
		}
	 }
		for(int i=0;i<bombs.size();i++){
			Bomb b=bombs.get(i);
			if(b.lifetime>6){
				g.drawImage(image1, b.x, b.y, 30,30, this);
			}
			else if(b.lifetime>3){
				g.drawImage(image2, b.x, b.y, 30, 30, this);
			}
			else
			{
				g.drawImage(image3, b.x, b.y,30,30, this);
			 }
			
			b.lifeDown();
			if(b.lifetime==0){
				bombs.remove(b);
			}
		}
		//�������˵�̹��
		for(int i=0;i<ets.size();i++){
			EnemyTank et=ets.get(i);
			if(et.isAlive)
			{
			   //��������̹��
			   this.drawTank(ets.get(i).getX(), ets.get(i).getY(), g,ets.get(i).direction, 0);
			   //��������̹�˵��ӵ�
			   for(int j=0;j<et.ss.size();j++)
				{  
				 Shot s=et.ss.get(j);
			    if(s.isAlive){
			    	g.draw3DRect(s.x,s.y, 1, 1, false);
			     }
			    else{
			    	
			    	et.ss.remove(s);
			    }
			   }
			}
		}
	}
	//�ҵ�̹�˻��е���̹�� 
	public void hitTank(Shot s,EnemyTank et){
		switch(et.direction)
		{
		   case 0:
		   case 1:
			        if(s.x>(et.x-10)&&s.x<(et.x+10)&&s.y>(et.y-15)&&s.y<(et.y+15))
			       {
				     //���� 
				     //�ӵ�����
				      s.isAlive=false;
				     //�з�̹������
				      et.isAlive=false;
				      //����һ��ը��
				      Bomb b=new Bomb(et.x-10,et.y-15);
				      //��ը����ӵ�bombs������
                      bombs.add(b);
				    }
			          break;
		   case 2:
		   case 3:
		   
			        if(s.x>(et.x-15)&&s.x<(et.x+15)&&s.y>(et.y-10)&&s.y<(et.y+10))
			       {
				     //����
				     //�ӵ�����
				      s.isAlive=false;
				     //�з�̹������
				      et.isAlive=false;
				     //����һ��ը��
				      Bomb b=new Bomb(et.x-15,et.y-10);
				     //��ը����ӵ�bombs������
				      bombs.add(b);
			        }
			         break;
			   
		   
		
		}
		
	}
	   //����̹�˻����ҵ�̹��
	     public void shotMytank(Shot s,Hero hero){
	    	 switch(hero.direction)
	 		{
	 		   case 0:
	 		   case 1:
	 			        if(s.x>(hero.x-10)&&s.x<(hero.x+10)&&s.y>(hero.y-15)&&s.y<(hero.y+15))
	 			       {
	 				     //���� 
	 				     //�ӵ�����
	 				      s.isAlive=false;
	 				     //�з�̹������
	 				      hero.isAlive=false;
	 				      //����һ��ը��
	 				      Bomb b=new Bomb(hero.x-10,hero.y-15);
	 				      //��ը����ӵ�bombs������
	 				      bombs.add(b);
	 				    }
	 			          break;
	 		   case 2:
	 		   case 3:
	 		   
	 			        if(s.x>(hero.x-15)&&s.x<(hero.x+15)&&s.y>(hero.y-10)&&s.y<(hero.y+10))
	 			       {
	 				     //����
	 				     //�ӵ�����
	 				      s.isAlive=false;
	 				     //�з�̹������
	 				      hero.isAlive=false;
	 				     //����һ��ը��
	 				      Bomb b=new Bomb(hero.x-15,hero.y-10);
	 				     //��ը����ӵ�bombs������
	 				      bombs.add(b);
	 			        }
	 			         break;
	 			   }
	 	        }
	   //����̹�˵ĺ���
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
		//���ﻭ̹������̹�˵�����Ϊ��λ����
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
		
		 //��j���������ӵ�
		if(e.getKeyCode()==KeyEvent.VK_J){
			if(hero.ss.size()<=4)
			{
			this.hero.shotEnemy();
			 }
	    }
		
		//�����ػ溯��
		this.repaint();
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
    //��дMypanel�߳������run����
	public void run() {
		while(true){
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * ��ȡ�����ķ�ʽ��ͨ��˫��ѭ����ȡ��ÿһ���ӵ�����
		 * ��ÿһ��̹�ˣ��ȶ��Ƿ��ӵ�����̹�ˣ�Ҳ���ǵ���hitTank
		 * ���������ӵ��������Ƿ���̹�˵����귽λ֮�ڣ�
		 */
		for(int i=0;i<hero.ss.size();i++){
			//ȡ��һ���ӵ�
			Shot myShot=hero.ss.get(i);
			//���if�ж�����Ǳ����
			if(myShot.isAlive)
			{
				for(int j=0;j<ets.size();j++)
				{
					//ȡ��һ���з�̹��
					EnemyTank et=ets.get(j);
					//���if�ж�����Ǳ����
					if(et.isAlive)
					{
						this.hitTank(myShot, et);
					}
				}
			}
	     }
		  for(int i=0;i<ets.size();i++){
			  EnemyTank et=ets.get(i);
			  if(et.isAlive ){
				  for(int j=0;j<et.ss.size();j++){
					  Shot s=et.ss.get(j);
					  if(s.isAlive ){
						this.shotMytank(s, hero);  
					  }
				  }
			   }
		    }
	
		//�ػ�
		this.repaint();
	 }
  }
	
}

