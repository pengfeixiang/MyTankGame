package com.test2;
import java.util.*;
//定义炸弹类
class Bomb
{
	//定义坐标
	int x;
	int y;
	//炸弹的生命值，用来控制三张图片的切换
	int lifetime=9;
	//记录炸弹是否还活着
	boolean isAlive=true;
	//构造函数
	public Bomb(int x,int y){
		
		this.x=x;
		this.y=y;
	}
	//控制lifetime变量的减少
	public void lifeDown(){
		if(lifetime>0)
		{
		this.lifetime--;
		}else{
		this.isAlive=false;	
		}
	}
	
}
//定义子弹类
class Shot implements Runnable{
	int x;
	int y;
	int direction;
	int speed=3;
	//记录子弹线程是否还活着
	boolean isAlive=true;
	public Shot(int x,int y,int direction)
	{
		this.x=x;
		this.y=y;
		this.direction=direction;
	}
	//重写线程里面的run函数
	public void run(){
		
		while(true)
		{
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch(direction){
		case 0:
			//向上
			y-=speed;
			break;
		case 1:
			//向下
			y+=speed;
			break;
		case 2:
			//向左
			x-=speed;
			break;
		case 3:
		    //向右
			x+=speed;
			break;
		}
		
        //当子弹到达窗体边框时，子弹消亡（也就是线程停止）
		if(x<1||x>399||y<1||y>299){
			
            this.isAlive=false;
            break;
		}
	  }
	}
	
}

//定义坦克类
class Tank{
	
	//坦克的横坐标
	int x=0;
	//坦克的纵坐标
	int y=0;
	//定义坦克速度变量
	int speed=1;
	//定义坦克的方向
	//0表示向上，1表示向下，2表示向左，3表示向右
	int direction=0;
	//定义坦克颜色
	int color;
	
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
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	
}

//定义我的坦克
class Hero extends Tank{
	//创建一个存放我的坦克子弹的集合
	Vector<Shot>ss = new Vector<Shot>();
	//记录我的坦克是否还活着
	boolean isAlive=true;
	Shot s=null;
	//构造函数
	public Hero(int x,int y){
		
		super(x, y);
	}
	public void shotEnemy(){
		switch(this.direction){
		    //坦克向上时的子弹
		case 0:
			//创建一颗子弹
			s=new Shot(x-1,y-15,0);
			//将子弹添加到集合中
			ss.add(s);
			break;
			//坦克向下时的子弹
		case 1:
			s=new Shot(x-1,y+15,1);
			ss.add(s);
			break;
			//坦克向左时的子弹
		case 2:
			s=new Shot(x-15,y-1,2);
			ss.add(s);
			break;
			//坦克向右时的子弹
		case 3:
			s=new Shot(x+15,y-1,3);
			ss.add(s);
			break;
		}
		Thread t=new Thread(s);
		t.start();
	}
	//向上
	public void moveUp(){
		if(y>15)
		y-=speed;
	}
	//向下
	public void moveDown(){
		if(y<248)
		y+=speed;
	}
	//向左
	public void moveLeft(){
		if(x>15)
		x-=speed;
	}
	//向右
	public void moveRight(){
		if(x<370)
		x+=speed;
	}
}

//定义敌人坦克(将敌人坦克升级为线程)
class EnemyTank extends Tank implements Runnable{
	
	  //记录敌方坦克是否还活着
	  boolean isAlive=true;
      //创建存放敌人坦克子弹的集合
	  Vector<Shot>ss=new Vector<Shot>();
	  //添加子弹(敌人坦克创建时或敌人坦克子弹消亡时)
	
	public EnemyTank(int x,int y){
		super(x, y);
	}

    public void run() {
		
		while(true){
		   
		switch(this.direction){
		  case 0:
			  for(int i=0;i<50;i++)
			  {
			      if(y>15)
			      y-=speed;
			 
			  try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
			  break;
		  case 1:
			  for(int i=0;i<50;i++)
			  {   
				  if(y<248)
			      y+=speed;
			  
			  try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
			  break;
		  case 2:
			  for(int i=0;i<50;i++)
			  {   
				  if(x>15)
			      x-=speed;
			 
			  try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
			  break;
		  case 3:
			  for(int i=0;i<50;i++)
			  {   
				  if(x<370)
			      x+=speed;
			  
			  try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
			  break;
		   }
		   this.direction=(int)(Math.random()*4);
		   if(this.isAlive==false){
			
			   break;
		     }
			//判断是否该给敌人坦克添加新的子弹
			     if(this.isAlive){
				  if(this.ss.size()<5){
					 Shot s=null;
					 switch(this.direction) {
					    case 0:
						    //坦克向上时的子弹
							//创建一颗子弹
						  
							s=new Shot(x-1,y-15,0);
							//将子弹添加到集合中
							this.ss.add(s);
							break;
							//坦克向下时的子弹
						case 1:
							 
							s=new Shot(x-1,y+15,1);
							this.ss.add(s);
							break;
							//坦克向左时的子弹
						case 2:
							 
							s=new Shot(x-15,y-1,2);
							this.ss.add(s);
							break;
							//坦克向右时的子弹
						case 3:
							  
							s=new Shot(x+15,y-1,3);
							this.ss.add(s);
							break;
					 }
					 //启动子弹线程
					 Thread t=new Thread(s);
					 t.start();
				  }
					
				}
				
			}
			
		  }
	}

