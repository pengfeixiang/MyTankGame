package com.test2;
/*
 * 功能：1.绘制坦克
 *       2.让我的坦克可以上下左右移动
 *       3.让我的坦克可以发射子弹，最多5颗
 *       4.当击中敌人坦克，子弹及敌人坦克消失
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
	
	//构造函数
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
//定义我的面板
class Mypanel extends JPanel implements KeyListener,Runnable{
	//声明一个我的坦克变量
	Hero hero=null;
	//声明一个敌人坦克组
	Vector<EnemyTank>ets=new Vector<EnemyTank>();
	//声明一个炸弹组（在一个屏幕中，可能有同时出现多个爆炸画面）
	Vector<Bomb>bombs=new Vector<Bomb>();
	int ensize=3;
	//爆炸效果是三张图片的迅速切换，所以定义三张图片
	Image image1=null;
	Image image2=null;
	Image image3=null;
	
     //构造函数
	 public Mypanel(){
		hero=new Hero(80,100);
//		image1=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
//		image2=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
//		image3=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));
		
		//这种方式第一个爆炸的效果不明显，故采用另外的申明方式
		//但这种申明方式存在另一个问题，击中我的坦克后，再次击中该区域
		//还会出现爆炸效果
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
			//敌人坦克创建时有一颗子弹
			Shot s=new Shot(et.x,et.y+15,1);
			//将子弹添加到集合中
			et.ss.add(s);
			ets.add(et);
			//启动敌人坦克的线程
			Thread t=new Thread(et);
			t.start();
			//启动子弹的线程
			Thread t1=new Thread(s);
			t1.start();
			
		 }
	  }
	public void paint(Graphics g){
	   //覆盖父类函数
		super.paint(g);
	   //首先设置颜色
	   //设置窗体的背景色为黑色
		g.fillRect(0, 0, 400, 300);
		//画出我的坦克
		if(hero.isAlive)
		{
		this.drawTank(hero.getX(), hero.getY(), g, this.hero.direction, 1);
		}
		//画出子弹（而且是多颗）
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
		//画出敌人的坦克
		for(int i=0;i<ets.size();i++){
			EnemyTank et=ets.get(i);
			if(et.isAlive)
			{
			   //画出敌人坦克
			   this.drawTank(ets.get(i).getX(), ets.get(i).getY(), g,ets.get(i).direction, 0);
			   //画出敌人坦克的子弹
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
	//我的坦克击中敌人坦克 
	public void hitTank(Shot s,EnemyTank et){
		switch(et.direction)
		{
		   case 0:
		   case 1:
			        if(s.x>(et.x-10)&&s.x<(et.x+10)&&s.y>(et.y-15)&&s.y<(et.y+15))
			       {
				     //击中 
				     //子弹消亡
				      s.isAlive=false;
				     //敌方坦克消亡
				      et.isAlive=false;
				      //创建一颗炸弹
				      Bomb b=new Bomb(et.x-10,et.y-15);
				      //将炸弹添加到bombs集合中
                      bombs.add(b);
				    }
			          break;
		   case 2:
		   case 3:
		   
			        if(s.x>(et.x-15)&&s.x<(et.x+15)&&s.y>(et.y-10)&&s.y<(et.y+10))
			       {
				     //击中
				     //子弹消亡
				      s.isAlive=false;
				     //敌方坦克消亡
				      et.isAlive=false;
				     //创建一颗炸弹
				      Bomb b=new Bomb(et.x-15,et.y-10);
				     //将炸弹添加到bombs集合中
				      bombs.add(b);
			        }
			         break;
			   
		   
		
		}
		
	}
	   //敌人坦克击中我的坦克
	     public void shotMytank(Shot s,Hero hero){
	    	 switch(hero.direction)
	 		{
	 		   case 0:
	 		   case 1:
	 			        if(s.x>(hero.x-10)&&s.x<(hero.x+10)&&s.y>(hero.y-15)&&s.y<(hero.y+15))
	 			       {
	 				     //击中 
	 				     //子弹消亡
	 				      s.isAlive=false;
	 				     //敌方坦克消亡
	 				      hero.isAlive=false;
	 				      //创建一颗炸弹
	 				      Bomb b=new Bomb(hero.x-10,hero.y-15);
	 				      //将炸弹添加到bombs集合中
	 				      bombs.add(b);
	 				    }
	 			          break;
	 		   case 2:
	 		   case 3:
	 		   
	 			        if(s.x>(hero.x-15)&&s.x<(hero.x+15)&&s.y>(hero.y-10)&&s.y<(hero.y+10))
	 			       {
	 				     //击中
	 				     //子弹消亡
	 				      s.isAlive=false;
	 				     //敌方坦克消亡
	 				      hero.isAlive=false;
	 				     //创建一颗炸弹
	 				      Bomb b=new Bomb(hero.x-15,hero.y-10);
	 				     //将炸弹添加到bombs集合中
	 				      bombs.add(b);
	 			        }
	 			         break;
	 			   }
	 	        }
	   //画出坦克的函数
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
		//这里画坦克是以坦克的中心为定位坐标
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
		
		 //按j键，发射子弹
		if(e.getKeyCode()==KeyEvent.VK_J){
			if(hero.ss.size()<=4)
			{
			this.hero.shotEnemy();
			 }
	    }
		
		//调用重绘函数
		this.repaint();
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
    //重写Mypanel线程里面的run函数
	public void run() {
		while(true){
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * 采取遍历的方式，通过双层循环，取出每一颗子弹，还
		 * 有每一辆坦克，比对是否子弹击中坦克（也就是调用hitTank
		 * 函数，看子弹的坐标是否在坦克的坐标方位之内）
		 */
		for(int i=0;i<hero.ss.size();i++){
			//取出一颗子弹
			Shot myShot=hero.ss.get(i);
			//这个if判断语句是必须的
			if(myShot.isAlive)
			{
				for(int j=0;j<ets.size();j++)
				{
					//取出一辆敌方坦克
					EnemyTank et=ets.get(j);
					//这个if判断语句是必须的
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
	
		//重绘
		this.repaint();
	 }
  }
	
}

