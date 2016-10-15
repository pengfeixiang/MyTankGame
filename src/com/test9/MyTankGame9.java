package com.test9;
/*
 * 功能：1.绘制坦克
 *       2.让我的坦克可以上下左右移动
 *       3.让我的坦克可以发射子弹，最多5颗
 *       4.当击中敌人坦克，子弹及敌人坦克消失
 *       5.敌人坦克击中我的坦克，我的坦克消失
 *       6.被击中的坦克都会发生爆炸的效果
 *       7.坦克之间的运行不能重叠
 *       8.产生一个分关的效果(即存在一个提示信息，
 *       实现是通过另一个JPanel)
 *       9.可以暂停和继续(实现是通过将坦克的速度设为0，方向设为不变)
 *      10.记录玩家的成绩
 *      11.开始玩游戏的时候，有游戏音乐
 *       
 */
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
public class MyTankGame9 extends JFrame implements ActionListener{
	//这两个变量是用来控制多次单击“开始新游戏”，产生混乱效果
//	    int n=0;
//	    String p=null;
	    Mystartpanel msp=null;
        Mypanel mp=null;
        Mypanel mp1=null;
        //声明菜单组件
        JMenuBar jmb=null;
        JMenu jm1= null;
        JMenuItem jmi1=null;
        JMenuItem jmi2=null;
        JMenuItem jmi3=null;
        JMenuItem jmi4=null;
        //定义一个集合，存放所有由Mypanel定义的组件
        Vector<Mypanel>mpel=new Vector<Mypanel>();
	
	public static void main(String[] args){
		MyTankGame9 mtk=new MyTankGame9();

	}
	
	//构造函数
	public MyTankGame9(){
	  	
		jmb=new JMenuBar();
		jm1=new JMenu("游戏(g)");
		jm1.setMnemonic('g');
		jmi1=new JMenuItem("开始新游戏(n)");
		jmi1.setMnemonic('n');
		jmi1.addActionListener(this);
		jmi1.setActionCommand("newgame");
		jmi2=new JMenuItem("退出游戏(e)");
		jmi2.setMnemonic('e');
		jmi2.addActionListener(this);
		jmi2.setActionCommand("exit");
		jmi3=new JMenuItem("存盘退出游戏(s)");
		jmi3.setMnemonic('s');
		jmi3.addActionListener(this);
		jmi3.setActionCommand("saveexit");
		jmi4=new JMenuItem("恢复上次游戏(c)");
		jmi4.setMnemonic('c');
		jmi4.addActionListener(this);
		jmi4.setActionCommand("congame");
		
		jm1.add(jmi1);
		jm1.add(jmi2);
		jm1.add(jmi3);
		jm1.add(jmi4);
		jmb.add(jm1);
		this.setJMenuBar(jmb);
		//建立一个开始面板
		msp=new Mystartpanel();
		//启动线程
		Thread t=new Thread(msp);
		t.start();
		this.add(msp);
	  	//设置窗体属性
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
			
			//新建一个我的面板
			mp=new Mypanel("newgame");
			//这里的主要作用是再次开始游戏时，将ShotEnNumToday变量
			//设置为0，也就是设置右边提示信息画出坦克可以重新开始
			Recorder.setShotEnNumToday(0);
			//启动面板线程
		  	Thread t=new Thread(mp);
		  	t.start();
		  	
		  	//将开始游戏提示面板移除
		  	this.remove(msp);
		  	//将游戏面板添加到集合中
		  	mpel.add(mp);
		  	//如果size()大于1，则单击了两次“开始新游戏”
		  	if(mpel.size()>1)
		  	{
		  	//因为先开始游戏的面板应该在前面（也就是
		  	//将该面板移除)
		  	this.remove(mpel.remove(0));
		  	 }
		  	this.add(mp);
		  	this.addKeyListener(mp);
		  	this.setVisible(true);
	 
			}
		else if(e.getActionCommand().equals("exit"))
		{   
			//调用函数，将游戏信息保存到外部文件
			Recorder.keepRecording();
			//关闭游戏(即将面板关闭)
			System.exit(0);
		 }
		else if(e.getActionCommand().equals("saveexit"))
		{   
			//让Recorder类得到所有的敌人坦克集合
			Recorder.setEts(mp.ets);
			//调用函数，保存敌人坦克的坐标与方向
			Recorder.keepRecAndEnemyTank();
			//退出
			System.exit(0);
		  }
		else if(e.getActionCommand().equals("congame"));
		{
			//新建一个我的面板
			mp=new Mypanel("angel");
			//这里的主要作用是再次开始游戏时，将ShotEnNumToday变量
			//设置为0，也就是设置右边提示信息画出坦克可以重新开始
			
			//启动面板线程
		  	Thread t=new Thread(mp);
		  	t.start();
		  
		  	
		  	//将开始游戏提示面板移除
		  	this.remove(msp);
		  	//将游戏面板添加到集合中
		  	mpel.add(mp);
		  	//如果size()大于1，则单击了两次“开始新游戏”
		  	if(mpel.size()>1)
		  	{
		  	//因为先开始游戏的面板应该在前面（也就是
		  	//将该面板移除)
		  	this.remove(mpel.remove(0));
		  	 }
		  	this.add(mp);
		  	this.addKeyListener(mp);
		  	this.setVisible(true);	
		 }
	   }
    }
//定义开始的提示面板
class Mystartpanel extends JPanel implements Runnable{
	   //定义一个控制变量
	   int times=0;
	   public void paint(Graphics g){
		  
		   //初始化父类方法
		   super.paint(g);
		   g.fillRect(0, 0,400, 300);
		   //如果times除以2，余数为0则画出内容
		   if(times%2==0){
		   //设置字体颜色
		   g.setColor(Color.YELLOW); 
		   //设置字体
		   Font myfont=new Font("华文新魏",Font.BOLD ,30);
		   g.setFont(myfont);
		   //画出字符串
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
	        //让times变量不断累积	
		    times++;
		    //达到一个清零的效果
		    if(times==200){
		    	times=0;
		    }
		    //调用repaint函数时会自动调用paint函数
			this.repaint();
		}
		
	}
 }
//定义我的面板
class Mypanel extends JPanel implements KeyListener,Runnable{
	//用来控制产生新的敌人坦克的位置
	int u=0;
	//游戏右边本轮总成绩下面画出的坦克要换行，
	//这几个变量就是用来控制换行的
	//坦克的横坐标
    int x=430;
    //坦克的纵坐标
    int y=130;
    //用来控制画坦克的横坐标
    int n=0;
    //用来控制画坦克的纵坐标
    int m=0;
   
	//声明一个我的坦克变量
	Hero hero=null;
	
	//声明一个敌人坦克组
	Vector<EnemyTank>ets=new Vector<EnemyTank>();
	//声明一个炸弹组（在一个屏幕中，可能有同时出现多个爆炸画面）
	//存储敌人坦克的爆炸效果
	Vector<Bomb>bombs=new Vector<Bomb>();
	//存储我的坦克的爆炸效果
	Vector<Bomb>bombs1=new Vector<Bomb>();
	//定义一个存放所有坦克的集合
	Vector<Tank>tank=new Vector<Tank>();
	
	Vector<Node>nodes=new Vector<Node>();
   
	int ensize=3;
	//爆炸效果是三张图片的迅速切换，所以定义三张图片
	Image image1=null;
	Image image2=null;
	Image image3=null;
	
     //构造函数
	 public Mypanel(String flag){
		//调用函数，获得曾经保存在文件中的信息
		Recorder.getRecording();
		hero=new Hero(150,275);
		
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
		 if(flag.equals("newgame"))
		 {  
			System.out.println("开始游戏");
		    for(int i=0;i<ensize;i++)
		    {
			EnemyTank et=new EnemyTank((i+1)*100,20);
			et.setDirection(1);
			//敌人坦克创建时有一颗子弹
			Shot s=new Shot(et.x,et.y+15,1);
			//将子弹添加到集合中
			et.ss.add(s);
			ets.add(et);
            for(int j=0;j<ets.size();j++)
            {
            	EnemyTank et1=ets.get(j);
            	tank.add(et1);
             }
            tank.add(hero);
            et.setTank(tank);
            hero.setTank(tank);
            
//			et.setEnemyTank(ets);
//			hero.setEnemyTank(ets);
			//启动敌人坦克的线程
			Thread t=new Thread(et);
			t.start();
			
			//启动子弹的线程
			Thread t1=new Thread(s);
			t1.start();
		   }
		 }
		 else if(flag.equals("angel"))
		 {   
			System.out.println("接着往");
			nodes=Recorder.getRecAndEnemyTank();
			 for(int i=0;i<nodes.size();i++){
				    Node node=nodes.get(i);
				    EnemyTank et=new EnemyTank(node.x,node.y);
					et.setDirection(node.direction);
					//敌人坦克创建时有一颗子弹
					Shot s=new Shot(et.x,et.y+15,1);
					//将子弹添加到集合中
					et.ss.add(s);
					ets.add(et);
		            for(int j=0;j<ets.size();j++)
		            {
		            	EnemyTank et1=ets.get(j);
		            	tank.add(et1);
		             }
		            tank.add(hero);
		            et.setTank(tank);
		            hero.setTank(tank); 
//		            et.setEnemyTank(ets);
//					hero.setEnemyTank(ets);
					//启动敌人坦克的线程
					Thread t=new Thread(et);
					t.start();
					
					//启动子弹的线程
					Thread t1=new Thread(s);
					t1.start();
				   }
		   }
	  }
	public void paint(Graphics g){
	   //覆盖父类函数
		super.paint(g);
	   //首先设置颜色
	   //设置窗体的背景色为黑色
		g.fillRect(0, 0, 400, 300);
		//画出游戏外面的提示信息
		this.showInfo(g);
		//画出我的坦克
		if(hero.isAlive)
		{
		this.drawTank(hero.getX(), hero.getY(), g, this.hero.direction, 1);
		 }
		//我的坦克死亡时，产生新的我的坦克
		else
		{   //条件就是：
			//1.我的坦克还有生命的时候
			//2.必须在我的坦克爆炸效果结束后，才能产生新的我的坦克
			if(Recorder.getMyLife()>0&&bombs1.size()==0)
			 
			{ 	
			 //设置我的坦克的横坐标
             hero.setX(150);
		     //设置我的坦克的纵坐标
			 hero.setY(275);
			 //设置我的坦克的方向
			 hero.setDirection(0);
			 //将我的坦克
			 hero.isAlive=true;
			 //这句代码是不需要的,因为isAlive设置为true,则前面判断是否画出
			 //我的坦克的条件就为真了
			 //this.drawTank(hero.getX(), hero.getY(), g, this.hero.direction, 1);
		   	
		     }
			//当我的坦克生命为0时，显示“GAME OVER”
			else if(Recorder.getMyLife()==0)
			{   
				//设置画笔颜色
			    g.setColor(Color.red);
			    //新建一个字体对象
				Font myfont=new Font("正楷",Font.BOLD ,40);
				//设置画笔字体
			    g.setFont(myfont);
			    //显示“GAME OVER”
				g.drawString("GAME OVER", 75, 160);
			 }
			
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
		
		//画出敌人的坦克
		if(Recorder.getEnNum()>0){
		for(int i=0;i<ets.size();i++){
			EnemyTank et=ets.get(i);
			tank.add(et);
			tank.add(hero);
	        et.setTank(tank);
	        hero.setTank(tank);
			if(et.isAlive)
			{
			   //画出敌人坦克
			   this.drawTank(ets.get(i).getX(), ets.get(i).getY(), g,ets.get(i).direction, 0);
			   //画出敌人坦克的子弹
			   for(int j=0;j<et.ss.size();j++)
				{  
				 Shot s=et.ss.get(j);
			    if(s!=null&&s.isAlive==true){
			    	g.draw3DRect(s.x,s.y, 1, 1, false);
			     }
			    //如果子弹消亡，则从集合中移除已消亡的子弹
			    else{
			    	et.ss.remove(s);
			     }
			   }
			}
			else
			{   
				
				if(Recorder.getEnNum()>=3)
				{
				//将已消亡的坦克移除
				ets.remove(i);
				u=Recorder.getEnNum()%3;
				//创建一辆新的敌人坦克
				EnemyTank et1=new EnemyTank((u+1)*100,20);
				//将新建的坦克添加到刚移除坦克的位置，以
				//保证后面坦克的位置不变
				ets.add(i, et1);
				et1.setDirection(1);
				
	            //敌人坦克创建时有一颗子弹
				Shot s=new Shot(et1.x,et1.y+15,1);
				//将子弹添加到集合中
				et1.ss.add(s);
				Thread t1=new Thread(et1);
				t1.start();
				Thread t2=new Thread(s);
				t2.start();
				
				}
			 }
		}
	}    
		 //将所有敌人坦克消灭
		 else
			
		 {  //设置画笔颜色
			g.setColor(Color.blue);
		    Font myfont=new Font("华文新魏",Font.BOLD,50);
		    //设置字体
		    g.setFont(myfont);
		    //当所有敌人坦克被消灭后，显示“CONTINUE”
			g.drawString("CONTINUE", 55, 160); 
		  }
		
		
		//画出敌人坦克的爆炸效果
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
		//画出我的坦克的爆炸效果
		for(int j=0;j<bombs1.size();j++){
			Bomb b=bombs1.get(j);
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
				bombs1.remove(b);
			}
		 }
		 
	}
	//画出游戏外面的提示信息
	public void showInfo(Graphics g)
	{  
	   //画出坦克提示信息（该坦克不参加战斗）
	   //画出敌人坦克
	   this.drawTank(70, 340, g, 0, 0);
	   //设置画笔颜色
	   g.setColor(Color.black);
	   //新建一个字体对象
	   Font myfont=new Font("正楷",Font.BOLD,12);
	   //设置字体
	   g.setFont(myfont);
	   //画出自己的坦克
	   g.drawString(Recorder.getEnNum()+"", 90, 345);
	   this.drawTank(130, 340, g, 0, 1);
	   g.setColor(Color.black);
	   g.drawString(Recorder.getMyLife()+"", 150, 345);
	   //画出右边提示信息（即我总共击中了几辆敌人坦克）
	   //新建一个字体对象
	   Font myfont1=new Font("正楷",Font.BOLD,15);
	   //设置字体
	   g.setFont(myfont1);
	   //画出右边的提示信息文字
	   g.drawString("我的总成绩：", 410, 30);
	   this.drawTank(430, 60, g,0, 0);
	   g.setColor(Color.black );
	   //新建一个字体对象
	   Font myfont2=new Font("正楷",Font.BOLD,12);
	   //设置字体
	   g.setFont(myfont2);
	   g.drawString(Recorder.getShotEnNum()+"",450 , 64);
	   //新建一个字体对象
	   Font myfont3=new Font("正楷",Font.BOLD,13);
	   //设置字体
	   g.setFont(myfont3);
	   g.drawString("本轮总成绩：", 410, 100);
	  
	   //画出本轮总成绩下面的坦克（带有换行效果）
	   for(int i=0;i<Recorder.getShotEnNumToday();i++)
	   { 
		 //让换行后，横坐标可以回到开始
		 n=i%5;
		 //下面这个多分支选择语句，控制纵坐标，
		 //也就是让它有了换行效果
		 if(i<5)
		 {
			m=0; 
		   }
		 else if(i<10)
		 {
			m=1; 
		   }
		 else if(i<15)
		 {
			m=2; 
		   }
		 else if(i<20)
		 {
			m=3; 
		   }
		 //画出坦克
		 this.drawTank(x+n*30,y+m*40, g, 0, 0);
		
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
				     //当敌人坦克被击中时，下面的提示信息数量减一
				      Recorder.reduceenNum();
				      Recorder.addShotEnNum();
				      Recorder.addShotEnNumToday();
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
				      //当敌人坦克被击中时，下面的提示信息数量减一
				      Recorder.reduceenNum();
				      Recorder.addShotEnNum();
				      Recorder.addShotEnNumToday();
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
	 				      //当我的坦克被击中时，生命减一
	 				      Recorder.reducemyLife();
	 				      //创建一颗炸弹
	 				      Bomb b=new Bomb(hero.x-10,hero.y-15);
	 				      //将炸弹添加到集合
	 				      bombs1.add(b);
	 				   
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
	 				     //当我的坦克被击中时，生命减一
	 				      Recorder.reducemyLife();
	 				     //创建一颗炸弹
	 				      Bomb b=new Bomb(hero.x-15,hero.y-10);
	 				     //将炸弹添加到集合
	 				      bombs1.add(b);
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
			case 2:g.setColor(Color.black);
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
			if(Hero.isChange==0)
			{
			this.hero.setDirection(0);
			this.hero.moveUp();
			 }
		}//坦克向下
		else if(e.getKeyCode()==KeyEvent.VK_S){
			if(Hero.isChange==0)
			{
			this.hero.setDirection(1);
			this.hero.moveDown();
			 }
		}//坦克向左
		else if(e.getKeyCode()==KeyEvent.VK_A){
			if(Hero.isChange==0)
			{
			this.hero.setDirection(2);
			this.hero.moveLeft();	
			 }
		}//坦克向右
		else if(e.getKeyCode()==KeyEvent.VK_D){
			if(Hero.isChange==0)
			{
			this.hero.setDirection(3);
			this.hero.moveRight();
			 }
		}
		
		 //按j键，发射子弹
		if(e.getKeyCode()==KeyEvent.VK_J){
			if(hero.ss.size()<=4)
			{
			this.hero.shotEnemy();
			 }
	    }
		//实现暂停和继续的效果
		if(e.getKeyCode()==KeyEvent.VK_P){
			//实现暂停的效果
			if(Hero.isChange==0&&EnemyTank.isChange==0)
			{
			//将我的坦克速度设置为0
			hero.setSpeed(0);
			//将Hero类中的静态变量设置为1
			Hero.isChange=1;
			for(int m=0;m<hero.ss.size();m++)
			{   //得到我的坦克中的所有子弹
				Shot s=hero.ss.get(m);
				//将子弹的速度设置为0
				s.setSpeed(0);
			 }
			for(int i=0;i<ets.size();i++)
			{   //得到所有的敌人坦克
				EnemyTank et=ets.get(i);
				//将敌人坦克的速度设置为0
				et.setSpeed(0);
				//将静态变量isChange设置为1
				et.isChange=1;
				for(int j=0;j<et.ss.size();j++)
				{   //得到敌人坦克中的所有子弹
					Shot myShot=et.ss.get(j);
					//将子弹的速度设置为0
					myShot.setSpeed(0);
				 }
			   }
		   }
			//实现暂停之后继续的效果
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
			  //取出每一辆敌人坦克
			  EnemyTank et=ets.get(i);
			  //判断敌人坦克是否活着
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
	
		//重绘
		this.repaint();
	 }
  }
	
}

