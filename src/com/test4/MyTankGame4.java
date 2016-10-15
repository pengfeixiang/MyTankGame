package com.test4;
/*
 * ���ܣ�1.����̹��
 *       2.���ҵ�̹�˿������������ƶ�
 *       3.���ҵ�̹�˿��Է����ӵ������5��
 *       4.�����е���̹�ˣ��ӵ�������̹����ʧ
 *       5.����̹�˻����ҵ�̹�ˣ��ҵ�̹����ʧ
 *       6.�����е�̹�˶��ᷢ����ը��Ч��
 *       7.̹��֮������в����ص�
 *       8.����һ���ֹص�Ч��(������һ����ʾ��Ϣ��
 *       ʵ����ͨ����һ��JPanel)
 *       9.������ͣ�ͼ���(ʵ����ͨ����̹�˵��ٶ���Ϊ0��������Ϊ����)
 *      10.��¼��ҵĳɼ�
 *       
 */
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
public class MyTankGame4 extends JFrame implements ActionListener{
	//�������������������ƶ�ε�������ʼ����Ϸ������������Ч��
//	    int n=0;
//	    String p=null;
	    Mystartpanel msp=null;
        Mypanel mp=null;
//      Mypanel mp1=null;
        //�����˵����
        JMenuBar jmb=null;
        JMenu jm1= null;
        JMenuItem jmi1=null;
	
	public static void main(String[] args){
		MyTankGame4 mtk=new MyTankGame4();

	}
	
	//���캯��
	public MyTankGame4(){
	  	
		jmb=new JMenuBar();
		jm1=new JMenu("��Ϸ(g)");
		jm1.setMnemonic('g');
		jmi1=new JMenuItem("��ʼ����Ϸ");
		jmi1.setMnemonic('n');
		jmi1.addActionListener(this);
		jmi1.setActionCommand("newgame");
		jm1.add(jmi1);
		jmb.add(jm1);
		this.setJMenuBar(jmb);
		//����һ����ʼ���
		msp=new Mystartpanel();
		//�����߳�
		Thread t=new Thread(msp);
		t.start();
		this.add(msp);
	  	//���ô�������
	  	this.setSize(600, 500);
	  	int w = (Toolkit.getDefaultToolkit().getScreenSize().width-600) / 2;
	  	int h = (Toolkit.getDefaultToolkit().getScreenSize().height-500) / 2;
	  	this.setLocation(w, h);
	  	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  	this.setVisible(true);
		
	}


	public void actionPerformed(ActionEvent e)
	{
		  
		if(e.getActionCommand().equals("newgame"))
		{   
			
			//�½�һ���ҵ����
			mp=new Mypanel();
//			mp.setName("mp");
//			p=mp.getName();
			//��������߳�
		  	Thread t=new Thread(mp);
		  	t.start();
		  	this.remove(msp);
		  	this.add(mp);
		  	this.addKeyListener(mp);
		  	this.setVisible(true);
//			mp1=new Mypanel();
//		  	mp1.setName("mp1");
//		  	p=mp1.getName();
//			//��������߳�
//			Thread t1=new Thread(mp1);
//		    t1.start();
//			if(p.equals("mp1"))
//			{
//			  this.remove(mp);
//			  }
//			if(p.equals("mp"))
//			 {
//			  this.remove(mp1);	
//			   }
//			  this.add(mp1);
//			  this.addKeyListener(mp1);
//			  this.setVisible(true);
			}
	    }
    }
//���忪ʼ����ʾ���
class Mystartpanel extends JPanel implements Runnable{
	   //����һ�����Ʊ���
	   int times=0;
	   public void paint(Graphics g){
		  
		   //��ʼ�����෽��
		   super.paint(g);
		   g.fillRect(0, 0,400, 300);
		   //���times����2������Ϊ0�򻭳�����
		   if(times%2==0){
		   //����������ɫ
		   g.setColor(Color.YELLOW); 
		   //��������
		   Font myfont=new Font("������κ",Font.BOLD ,30);
		   g.setFont(myfont);
		   //�����ַ���
		   g.drawString("Stage : 1", 140, 150);
		   }
	   }

	public void run() {
		while(true){
		  try {
			
			Thread.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        //��times���������ۻ�	
		    times++;
		    //�ﵽһ�������Ч��
		    if(times==200){
		    	times=0;
		    }
		    //����repaint����ʱ���Զ�����paint����
			this.repaint();
		}
		
	}
 }
//�����ҵ����
class Mypanel extends JPanel implements KeyListener,Runnable{
    int n=1;
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
			EnemyTank et=new EnemyTank((i+1)*50,20);
			et.setDirection(1);
			//����̹�˴���ʱ��һ���ӵ�
			Shot s=new Shot(et.x,et.y+15,1);
			//���ӵ���ӵ�������
			et.ss.add(s);
			ets.add(et);
			et.setEnemyTank(ets);
			hero.setEnemyTank(ets);
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
		//������Ϸ�������ʾ��Ϣ
		this.showInfo(g);
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
			    if(s!=null&&s.isAlive==true){
			    	g.draw3DRect(s.x,s.y, 1, 1, false);
			     }
			    else{
			    	
			    	et.ss.remove(s);
			    }
			   }
			}
		}
	}
	//������Ϸ�������ʾ��Ϣ
	public void showInfo(Graphics g)
	{  //����̹����ʾ��Ϣ����̹�˲��μ�ս����
	   //��������̹��
	   this.drawTank(70, 340, g, 0, 0);
	   //���û�����ɫ
	   g.setColor(Color.black);
	   //�½�һ���������
	   Font myfont=new Font("����",Font.BOLD,12);
	   //��������
	   g.setFont(myfont);
	   //�����Լ���̹��
	   g.drawString(Recorder.getEnNum()+"", 90, 345);
	   this.drawTank(130, 340, g, 0, 1);
	   g.setColor(Color.black);
	   g.drawString(Recorder.getMyLife()+"", 150, 345);
	   
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
				     //������̹�˱�����ʱ���������ʾ��Ϣ������һ
				      Recorder.reduceenNum();
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
				      //������̹�˱�����ʱ���������ʾ��Ϣ������һ
				      Recorder.reduceenNum();
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
	 				      //���ҵ�̹�˱�����ʱ��������һ
	 				      Recorder.reducemyLife();
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
	 				     //���ҵ�̹�˱�����ʱ��������һ
	 				      Recorder.reducemyLife();
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
			if(Hero.isChange==0)
			{
			this.hero.setDirection(0);
			this.hero.moveUp();
			 }
		}//̹������
		else if(e.getKeyCode()==KeyEvent.VK_S){
			if(Hero.isChange==0)
			{
			this.hero.setDirection(1);
			this.hero.moveDown();
			 }
		}//̹������
		else if(e.getKeyCode()==KeyEvent.VK_A){
			if(Hero.isChange==0)
			{
			this.hero.setDirection(2);
			this.hero.moveLeft();	
			 }
		}//̹������
		else if(e.getKeyCode()==KeyEvent.VK_D){
			if(Hero.isChange==0)
			{
			this.hero.setDirection(3);
			this.hero.moveRight();
			 }
		}
		
		 //��j���������ӵ�
		if(e.getKeyCode()==KeyEvent.VK_J){
			if(hero.ss.size()<=4)
			{
			this.hero.shotEnemy();
			 }
	    }
		//ʵ����ͣ�ͼ�����Ч��
		if(e.getKeyCode()==KeyEvent.VK_P){
			//ʵ����ͣ��Ч��
			if(Hero.isChange==0&&EnemyTank.isChange==0)
			{
			//���ҵ�̹���ٶ�����Ϊ0
			hero.setSpeed(0);
			//��Hero���еľ�̬��������Ϊ1
			Hero.isChange=1;
			for(int m=0;m<hero.ss.size();m++)
			{   //�õ��ҵ�̹���е������ӵ�
				Shot s=hero.ss.get(m);
				//���ӵ����ٶ�����Ϊ0
				s.setSpeed(0);
			 }
			for(int i=0;i<ets.size();i++)
			{   //�õ����еĵ���̹��
				EnemyTank et=ets.get(i);
				//������̹�˵��ٶ�����Ϊ0
				et.setSpeed(0);
				//����̬����isChange����Ϊ1
				et.isChange=1;
				for(int j=0;j<et.ss.size();j++)
				{   //�õ�����̹���е������ӵ�
					Shot myShot=et.ss.get(j);
					//���ӵ����ٶ�����Ϊ0
					myShot.setSpeed(0);
				 }
			   }
		   }
			//ʵ����֮ͣ�������Ч��
			else if(Hero.isChange==1&&EnemyTank.isChange==1)
			{
				hero.setSpeed(1);
				Hero.isChange=0;
				for(int m=0;m<hero.ss.size();m++)
				{
					Shot s=hero.ss.get(m);
					s.setSpeed(3);
				 }
				for(int i=0;i<ets.size();i++)
				{
					EnemyTank et=ets.get(i);
					et.setSpeed(1);
					et.isChange=0;
					for(int j=0;j<et.ss.size();j++)
					{
						Shot myShot=et.ss.get(j);
						myShot.setSpeed(3);
					 }
				   }
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
			  //ȡ��ÿһ������̹��
			  EnemyTank et=ets.get(i);
			  //�жϵ���̹���Ƿ����
			  if(et.isAlive ){
				  for(int j=0;j<et.ss.size();j++){
					  Shot s=et.ss.get(j);
					  if(s.isAlive ){
					  if(hero.isAlive )
					  {
						this.shotMytank(s, hero); 
					   }
					 }
				  }
			   }
		    }
	
		//�ػ�
		this.repaint();
	 }
  }
	
}

