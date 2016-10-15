package com.test8_2;
import java.util.*;
import java.io.*;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

//播放声音的类
class AePlayWave extends Thread {

	private String filename;
	public AePlayWave(String wavfile) {
		filename = wavfile;

	}

	public void run() {

		File soundFile = new File(filename);

		AudioInputStream audioInputStream = null;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(soundFile);
		} catch (Exception e1) {
			e1.printStackTrace();
			return;
		}

		AudioFormat format = audioInputStream.getFormat();
		SourceDataLine auline = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

		try {
			auline = (SourceDataLine) AudioSystem.getLine(info);
			auline.open(format);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		auline.start();
		int nBytesRead = 0;
		//这是缓冲
		byte[] abData = new byte[512];

		try {
			while (nBytesRead != -1) {
				nBytesRead = audioInputStream.read(abData, 0, abData.length);
				if (nBytesRead >= 0)
					auline.write(abData, 0, nBytesRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} finally {
			auline.drain();
			auline.close();
		}

	}

	
}
//墙类
class Wall
   {
	//记录墙的横坐标
	int x=0;
	//记录墙的纵坐标
	int y=0;
	
	//记录墙是否被子弹摧毁
	boolean isAlive=true;
	//构造函数
	public Wall(int x,int y)
	{
	 this.x=x;
	 this.y=y;
	 }
	}
//定义一个类，用来记录坦克的信息
class Recorder
{
    //记录敌人坦克的数量
	private static int enNum=20;
    //记录我的生命
	private static int myLife=3;
	//记录我击中的敌人坦克数量(从玩游戏以来，击中的总数)
	private static int shotEnNum=0;
	//记录本次打开游戏，我击中的敌人坦克数量
	private static int shotEnNumToday=0;
	//声明一个集合，存储所有的敌人坦克
	private Vector<EnemyTank>ets=new Vector<EnemyTank>();
	
	//声明文件输出流和输入流对象
	private static FileWriter fw=null;
	private static FileReader fr=null;
	//声明缓冲流对象
	private static BufferedWriter bw=null;
	private static BufferedReader br=null;
	
    public static int getShotEnNum() {
		return shotEnNum;
	}
	public static void setShotEnNum(int shotEnNum) {
		Recorder.shotEnNum = shotEnNum;
	}
	public static int getShotEnNumToday() {
		return shotEnNumToday;
	}
	public static void setShotEnNumToday(int shotEnNumToday){
		Recorder.shotEnNumToday = shotEnNumToday;
	}
    public static int getEnNum(){
		return enNum;
	}
	public static void setEnNum(int enNum) {
		Recorder.enNum = enNum;
	}
	public static int getMyLife(){
		return myLife;
	}
	public static void setMyLife(int myLife) {
		Recorder.myLife = myLife;
	}
	public Vector<EnemyTank> getEts(){
		return ets;
	}
	public void setEts(Vector<EnemyTank> ets){
		this.ets = ets;
	}
	public static void reduceenNum()
	{
		enNum--;
	 }
	//当我的坦克被击中时，我的生命减一
	public static void reducemyLife()
	{
		myLife--;
	 }
	//当我击中了一辆敌人坦克，击中的总数加一
	public static void addShotEnNum()
	{
		shotEnNum++;
	 }
	//当我击中了一辆敌人坦克，本轮游戏击中的总数加一
	public static void addShotEnNumToday()
	{
		shotEnNumToday++;
	 }
	public void keepRecAndEnemyTank()
	{
		 try 
		   {   
			   //创建输入流对象，读取指定目录文件的信息
			   fw=new FileWriter("d:/java项目/recorder.txt");
			   //创建缓冲流对象
			   bw=new BufferedWriter(fw);
			   bw.write(shotEnNum+"\r\n");
			   for(int i=0;i<ets.size();i++)
			   {
				  EnemyTank et=ets.get(i);
				  if(et.isAlive)
				  {
					String s=et.getX()+" "+et.getY()+" "+et.getDirection();
					bw.write(s+"\r\n");
				   }
			    }
		      } 
		 catch (Exception e) 
		   {
			e.printStackTrace();
		      }
		 finally
		   {
			  try{
				 //关闭流
				bw.close();
				fw.close();
			 } 
			  catch (IOException e)
			  {
				
				e.printStackTrace();
			  }
		   }
	 }
	public static void getRecording()
	{
	   try 
	   {   
		   //创建输入流对象，读取指定目录文件的信息
		   fr=new FileReader("d:/java项目/recorder.txt");
		   //创建缓冲流对象
		   br=new BufferedReader(fr);
		   String s=null;
		   //读取
		   s=br.readLine();
		   //将字符串类型数据转换为整形
		   shotEnNum=Integer.parseInt(s);
	      } 
	   catch (Exception e) 
	   {
		e.printStackTrace();
	      }
	   finally
	   {
		  try {
			 //关闭流
			br.close();
			fr.close();
		} 
		  catch (IOException e)
		  {
			
			e.printStackTrace();
		  }
	    }
	   
	}
	public static void keepRecording()
	{
		try {
			//创建输出流对象，并将信息保存到指定目录
			fw=new FileWriter("d:/java项目/recorder.txt");
			//创建缓冲流对象
			bw=new BufferedWriter(fw);
			//输出信息
			bw.write(shotEnNum+"");
			
		 } 
		catch (IOException e)
		{
			e.printStackTrace();
		 }
		finally
		{
		  try {
			  //关闭流
			  bw.close();
			  fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 }
	 }
  }
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
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
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
	//记录坦克是否活着
	boolean isAlive=true;
	//定义一个存放所有坦克的集合
	Vector<Tank> ets=new Vector<Tank>();
//	//定义四个二维对象数组（存储墙对象）
//	Wall walls1[][]=new Wall[5][12];
//	Wall walls2[][]=new Wall[4][12];
//	Wall walls3[][]=new Wall[4][12];
//	Wall walls4[][]=new Wall[4][12];
	//定义一个获取所有坦克的函数(敌人坦克和我的坦克)
	public  void setTank(Vector<Tank> ss){
		this.ets=ss;
	   }
	
	//构造函数
	public Tank(){}
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
//	public void setWalls1(Wall[][] walls1) {
//		this.walls1 = walls1;
//	}
//	public void setWalls2(Wall[][] walls2) {
//		this.walls2 = walls2;
//	}
//	public void setWalls3(Wall[][] walls3) {
//		this.walls3 = walls3;
//	}
//	public void setWalls4(Wall[][] walls4) {
//		this.walls4 = walls4;
//	}
//	//让坦克不要通过墙1
//	public boolean isTouchWall1()
//	{
//	  boolean b=false;
////      for(int i=0;i<ets.size();i++)
////      {
////    	Tank et=ets.get(i);
//    	if(this.isAlive)
//    	{ 
//    	  switch(this.direction)
//    	  {
//    	   //坦克向上时
//    	   case 0:
//    		   for(int j=0;j<4;j++)
//    		   for(int k=0;k<12;k++)
//    		   { //取出墙1中的一个基本单元（即墙对象）
//    			 Wall wall=walls1[j][k]; 
//    			 if(wall.isAlive)
//    			 {
//    				if(wall.x>this.x-10&&wall.x<this.x+10&&wall.y+5>this.y-15&&wall.y+5<this.y+15)
//    				{
//    				 return true;	
//    				 }
//    				if(wall.x+8>this.x-10&&wall.x+8<this.x+10&&wall.y+5>this.y-15&&wall.y+5<this.y+15)
//    				{
//    				 return true;	
//    				 }
//    			  }
//    		    }
//    		  break;
//    		 //坦克向下时
//    	     case 1:
//    		   for(int j=0;j<4;j++)
//    		   for(int k=0;k<12;k++)
//    		   { //取出墙1中的一个基本单元（即墙对象）
//    			 Wall wall=walls1[j][k]; 
//    			 if(wall.isAlive)
//    			 {
//    				if(wall.x>this.x-10&&wall.x<this.x+10&&wall.y>this.y-15&&wall.y<this.y+15)
//    				{
//    				 return true;	
//    				 }
//    				if(wall.x+8>this.x-10&&wall.x+8<this.x+10&&wall.y>this.y-15&&wall.y<this.y+15)
//    				{
//    				 return true;	
//    				 }
//    			  }
//    		    }
//    		   break;
//    		 //坦克向左时
//      	     case 2:
//      		   for(int j=0;j<4;j++)
//      		   for(int k=0;k<12;k++)
//      		   { //取出墙1中的一个基本单元（即墙对象）
//      			 Wall wall=walls1[j][k]; 
//      			 if(wall.isAlive)
//      			 {
//      				if(wall.x+8>this.x-15-10&&wall.x+8<this.x+15&&wall.y>this.y-10&&wall.y<this.y+10)
//      				{
//      				 return true;	
//      				 }
//      				if(wall.x+8>this.x-15&&wall.x+8<this.x+15&&wall.y+5>this.y-10&&wall.y+5<this.y+10)
//      				{
//      				 return true;	
//      				 }
//      			  }
//      		    }
//      		   break;
//      		 //坦克向右时
//      	     case 3:
//      		   for(int j=0;j<4;j++)
//      		   for(int k=0;k<12;k++)
//      		   { //取出墙1中的一个基本单元（即墙对象）
//      			 Wall wall=walls1[j][k]; 
//      			 if(wall.isAlive)
//      			 {
//      				if(wall.x>this.x-15&&wall.x<this.x+15&&wall.y>this.y-10&&wall.y<this.y+10)
//      				{
//      				 return true;	
//      				 }
//      				if(wall.x>this.x-15&&wall.x<this.x+15&&wall.y+5>this.y-10&&wall.y+5<this.y+10)
//      				{
//      				 return true;	
//      				 }
//      			  }
//      		    }
//      		   break;
//    		 }
//       }
//	  return b;
//	 }
//	//让坦克不要通过墙2
//	public boolean isTouchWall2()
//	{
//	  boolean b=false;
////      for(int i=0;i<ets.size();i++)
////      {
////    	Tank et=ets.get(i);
//    	if(this.isAlive)
//    	{ 
//    	  switch(this.direction)
//    	  {
//    	   //坦克向上时
//    	   case 0:
//    		   for(int j=0;j<4;j++)
//    		   for(int k=0;k<12;k++)
//    		   { //取出墙1中的一个基本单元（即墙对象）
//    			 Wall wall=walls2[j][k]; 
//    			 if(wall.isAlive)
//    			 {
//    				if(wall.x>this.x-10&&wall.x<this.x+10&&wall.y+5>this.y-15&&wall.y+5<this.y+15)
//    				{
//    				 return true;	
//    				 }
//    				if(wall.x+8>this.x-10&&wall.x+8<this.x+10&&wall.y+5>this.y-15&&wall.y+5<this.y+15)
//    				{
//    				 return true;	
//    				 }
//    			  }
//    		    }
//    		  break;
//    		 //坦克向下时
//    	     case 1:
//    		   for(int j=0;j<4;j++)
//    		   for(int k=0;k<12;k++)
//    		   { //取出墙1中的一个基本单元（即墙对象）
//    			 Wall wall=walls2[j][k]; 
//    			 if(wall.isAlive)
//    			 {
//    				if(wall.x>this.x-10&&wall.x<this.x+10&&wall.y>this.y-15&&wall.y<this.y+15)
//    				{
//    				 return true;	
//    				 }
//    				if(wall.x+8>this.x-10&&wall.x+8<this.x+10&&wall.y>this.y-15&&wall.y<this.y+15)
//    				{
//    				 return true;	
//    				 }
//    			  }
//    		    }
//    		   break;
//    		 //坦克向左时
//      	     case 2:
//      		   for(int j=0;j<4;j++)
//      		   for(int k=0;k<12;k++)
//      		   { //取出墙1中的一个基本单元（即墙对象）
//      			 Wall wall=walls2[j][k]; 
//      			 if(wall.isAlive)
//      			 {
//      				if(wall.x+8>this.x-15-10&&wall.x+8<this.x+15&&wall.y>this.y-10&&wall.y<this.y+10)
//      				{
//      				 return true;	
//      				 }
//      				if(wall.x+8>this.x-15&&wall.x+8<this.x+15&&wall.y+5>this.y-10&&wall.y+5<this.y+10)
//      				{
//      				 return true;	
//      				 }
//      			  }
//      		    }
//      		   break;
//      		 //坦克向右时
//      	     case 3:
//      		   for(int j=0;j<4;j++)
//      		   for(int k=0;k<12;k++)
//      		   { //取出墙1中的一个基本单元（即墙对象）
//      			 Wall wall=walls2[j][k]; 
//      			 if(wall.isAlive)
//      			 {
//      				if(wall.x>this.x-15&&wall.x<this.x+15&&wall.y>this.y-10&&wall.y<this.y+10)
//      				{
//      				 return true;	
//      				 }
//      				if(wall.x>this.x-15&&wall.x<this.x+15&&wall.y+5>this.y-10&&wall.y+5<this.y+10)
//      				{
//      				 return true;	
//      				 }
//      			  }
//      		    }
//      		   break;
//    		 }
//       }
//	  return b;
//	 }
//	//让坦克不要通过墙3
//	public boolean isTouchWall3()
//	{
//	  boolean b=false;
////      for(int i=0;i<ets.size();i++)
////      {
////    	Tank et=ets.get(i);
//    	if(this.isAlive)
//    	{ 
//    	  switch(this.direction)
//    	  {
//    	   //坦克向上时
//    	   case 0:
//    		   for(int j=0;j<4;j++)
//    		   for(int k=0;k<12;k++)
//    		   { //取出墙1中的一个基本单元（即墙对象）
//    			 Wall wall=walls3[j][k]; 
//    			 if(wall.isAlive)
//    			 {
//    				if(wall.x>this.x-10&&wall.x<this.x+10&&wall.y+5>this.y-15&&wall.y+5<this.y+15)
//    				{
//    				 return true;	
//    				 }
//    				if(wall.x+8>this.x-10&&wall.x+8<this.x+10&&wall.y+5>this.y-15&&wall.y+5<this.y+15)
//    				{
//    				 return true;	
//    				 }
//    			  }
//    		    }
//    		  break;
//    		 //坦克向下时
//    	     case 1:
//    		   for(int j=0;j<4;j++)
//    		   for(int k=0;k<12;k++)
//    		   { //取出墙1中的一个基本单元（即墙对象）
//    			 Wall wall=walls3[j][k]; 
//    			 if(wall.isAlive)
//    			 {
//    				if(wall.x>this.x-10&&wall.x<this.x+10&&wall.y>this.y-15&&wall.y<this.y+15)
//    				{
//    				 return true;	
//    				 }
//    				if(wall.x+8>this.x-10&&wall.x+8<this.x+10&&wall.y>this.y-15&&wall.y<this.y+15)
//    				{
//    				 return true;	
//    				 }
//    			  }
//    		    }
//    		   break;
//    		 //坦克向左时
//      	     case 2:
//      		   for(int j=0;j<4;j++)
//      		   for(int k=0;k<12;k++)
//      		   { //取出墙1中的一个基本单元（即墙对象）
//      			 Wall wall=walls3[j][k]; 
//      			 if(wall.isAlive)
//      			 {
//      				if(wall.x+8>this.x-15-10&&wall.x+8<this.x+15&&wall.y>this.y-10&&wall.y<this.y+10)
//      				{
//      				 return true;	
//      				 }
//      				if(wall.x+8>this.x-15&&wall.x+8<this.x+15&&wall.y+5>this.y-10&&wall.y+5<this.y+10)
//      				{
//      				 return true;	
//      				 }
//      			  }
//      		    }
//      		   break;
//      		 //坦克向右时
//      	     case 3:
//      		   for(int j=0;j<4;j++)
//      		   for(int k=0;k<12;k++)
//      		   { //取出墙1中的一个基本单元（即墙对象）
//      			 Wall wall=walls3[j][k]; 
//      			 if(wall.isAlive)
//      			 {
//      				if(wall.x>this.x-15&&wall.x<this.x+15&&wall.y>this.y-10&&wall.y<this.y+10)
//      				{
//      				 return true;	
//      				 }
//      				if(wall.x>this.x-15&&wall.x<this.x+15&&wall.y+5>this.y-10&&wall.y+5<this.y+10)
//      				{
//      				 return true;	
//      				 }
//      			  }
//      		    }
//      		   break;
//    		 }
//       }
//	  return b;
//	 }
//	//让坦克不要通过墙4
//	public boolean isTouchWall4()
//	{
//	  boolean b=false;
////      for(int i=0;i<ets.size();i++)
////      {
////    	Tank et=ets.get(i);
//    	if(this.isAlive)
//    	{ 
//    	  switch(this.direction)
//    	  {
//    	   //坦克向上时
//    	   case 0:
//    		   for(int j=0;j<4;j++)
//    		   for(int k=0;k<12;k++)
//    		   { //取出墙1中的一个基本单元（即墙对象）
//    			 Wall wall=walls4[j][k]; 
//    			 if(wall.isAlive)
//    			 {
//    				if(wall.x>this.x-10&&wall.x<this.x+10&&wall.y+5>this.y-15&&wall.y+5<this.y+15)
//    				{
//    				 return true;	
//    				 }
//    				if(wall.x+8>this.x-10&&wall.x+8<this.x+10&&wall.y+5>this.y-15&&wall.y+5<this.y+15)
//    				{
//    				 return true;	
//    				 }
//    			  }
//    		    }
//    		  break;
//    		 //坦克向下时
//    	     case 1:
//    		   for(int j=0;j<4;j++)
//    		   for(int k=0;k<12;k++)
//    		   { //取出墙1中的一个基本单元（即墙对象）
//    			 Wall wall=walls4[j][k]; 
//    			 if(wall.isAlive)
//    			 {
//    				if(wall.x>this.x-10&&wall.x<this.x+10&&wall.y>this.y-15&&wall.y<this.y+15)
//    				{
//    				 return true;	
//    				 }
//    				if(wall.x+8>this.x-10&&wall.x+8<this.x+10&&wall.y>this.y-15&&wall.y<this.y+15)
//    				{
//    				 return true;	
//    				 }
//    			  }
//    		    }
//    		   break;
//    		 //坦克向左时
//      	     case 2:
//      		   for(int j=0;j<4;j++)
//      		   for(int k=0;k<12;k++)
//      		   { //取出墙1中的一个基本单元（即墙对象）
//      			 Wall wall=walls4[j][k]; 
//      			 if(wall.isAlive)
//      			 {
//      				if(wall.x+8>this.x-15-10&&wall.x+8<this.x+15&&wall.y>this.y-10&&wall.y<this.y+10)
//      				{
//      				 return true;	
//      				 }
//      				if(wall.x+8>this.x-15&&wall.x+8<this.x+15&&wall.y+5>this.y-10&&wall.y+5<this.y+10)
//      				{
//      				 return true;	
//      				 }
//      			  }
//      		    }
//      		   break;
//      		 //坦克向右时
//      	     case 3:
//      		   for(int j=0;j<4;j++)
//      		   for(int k=0;k<12;k++)
//      		   { //取出墙1中的一个基本单元（即墙对象）
//      			 Wall wall=walls4[j][k]; 
//      			 if(wall.isAlive)
//      			 {
//      				if(wall.x>this.x-15&&wall.x<this.x+15&&wall.y>this.y-10&&wall.y<this.y+10)
//      				{
//      				 return true;	
//      				 }
//      				if(wall.x>this.x-15&&wall.x<this.x+15&&wall.y+5>this.y-10&&wall.y+5<this.y+10)
//      				{
//      				 return true;	
//      				 }
//      			  }
//      		    }
//      		   break;
//    		 }
//       }
//	  return b;
//	 }
	//让坦克不要通过河流
	public boolean isTouchRiver()
	{
		boolean b=false;
		switch(this.direction)
		{
		 //当坦克向上时
		 case 0:
		 if((this.x-10>40&&this.x-10<120&&this.y-15>200&&this.y-15<220)||(this.x-10>160&&this.x-10<240
			&&this.y-15>200&&this.y-15<220)||(this.x-10>280&&this.x-10<360&&this.y-15>200&&this.y-15<220))
		 {
			return true; 
		  }
		 if((this.x+10>40&&this.x+10<120&&this.y-15>200&&this.y-15<220)||(this.x+10>160&&this.x+10<240
			&&this.y-15>200&&this.y-15<220)||(this.x+10>280&&this.x+10<360&&this.y-15>200&&this.y-15<220))
		 {
			return true; 
		  }
		 break;
		 //当坦克向下时
		 case 1:
		 if((this.x-10>40&&this.x-10<120&&this.y+15>200&&this.y+15<220)||(this.x-10>160&&this.x-10<240
			&&this.y+15>200&&this.y+15<220)||(this.x-10>280&&this.x-10<360&&this.y+15>200&&this.y+15<220))
			{
			return true; 
			 }
		if((this.x+10>40&&this.x+10<120&&this.y+15>200&&this.y+15<220)||(this.x+10>160&&this.x+10<240
			&&this.y+15>200&&this.y+15<220)||(this.x+10>280&&this.x+10<360&&this.y+15>200&&this.y+15<220))
			{
			return true; 
			 }
		 break;
		 //当坦克向左时
		 case 2:
		if((this.x-15>40&&this.x-15<120&&this.y-10>200&&this.y-10<220)||(this.x-15>160&&this.x-15<240
			&&this.y-10>200&&this.y-10<220)||(this.x-15>280&&this.x-15<360&&this.y-10>200&&this.y-10<220))
		    {
			return true; 
			 }
		if((this.x-15>40&&this.x-15<120&&this.y+10>200&&this.y+10<220)||(this.x-15>160&&this.x-15<240
				&&this.y+10>200&&this.y+10<220)||(this.x-15>280&&this.x-15<360&&this.y+10>200&&this.y+10<220))
			    {
				return true; 
				 }
		 break;
		 //当坦克向右时
		 case 3:
		if((this.x+15>40&&this.x+15<120&&this.y-10>200&&this.y-10<220)||(this.x+15>160&&this.x+15<240
			&&this.y-10>200&&this.y-10<220)||(this.x+15>280&&this.x+15<360&&this.y-10>200&&this.y-10<220))
			{
			return true; 
			 }
		if((this.x+15>40&&this.x+15<120&&this.y+10>200&&this.y+10<220)||(this.x+15>160&&this.x+15<240
				&&this.y+10>200&&this.y+10<220)||(this.x+15>280&&this.x+15<360&&this.y+10>200&&this.y+10<220))
				{
				return true; 
				 }
		 break;
			 
		}
		return b;
	 } 
	//让所有的坦克之间互不重叠
	public boolean isTouchOtherTanks(){
		
		boolean b=false;
		switch(this.direction){
		case 0:
			//坦克向上时
			for(int i=0;i<ets.size();i++){
				//取出每一辆坦克
				Tank et=ets.get(i);
				//当该坦克不是自己本身的时候
				if(et!=this&&et.isAlive==true){
					//其他坦克为向上或向下时
					if(et.direction ==0||et.direction ==1){
						if(this.x-10>=et.x-10&&this.x-10<=et.x+10&&this.y-15>=et.y-15&&this.y-15<=et.y+15)
						{
							return true;
						 }
						if(this.x+10>=et.x-10&&this.x+10<=et.x+10&&this.y-15>=et.y-15&&this.y-15<=et.y+15)
						{
							return true;
						}
					}
					//其他坦克为向左或向右时
					if(et.direction ==2||et.direction ==3){
						if(this.x-10>=et.x-15&&this.x-10<=et.x+15&&this.y-15>=et.y-10&&this.y-15<=et.y+10)
						{
							return true;
						 }
						if(this.x+10>=et.x-15&&this.x+10<=et.x+15&&this.y-15>=et.y-10&&this.y-15<=et.y+10)
						{
							return true;
						 }
					}
				}
				
			}
			break;
		case 1:
			//坦克向下时
			for(int i=0;i<ets.size();i++){
				//取出每一辆坦克
				Tank et=ets.get(i);
				//当该坦克不是自己本身的时候
				if(et!=this&&et.isAlive==true){
					//其他坦克为向上或向下时
					if(et.direction ==0||et.direction ==1){
						if(this.x-10>=et.x-10&&this.x-10<=et.x+10&&this.y+15>=et.y-15&&this.y+15<=et.y+15)
						{
							return true;
						 }
						if(this.x+10>=et.x-10&&this.x+10<=et.x+10&&this.y+15>=et.y-15&&this.y+15<=et.y+15)
						{
							return true;
						 }
					}
					//其他坦克为向左或向右时
					if(et.direction ==2||et.direction ==3){
						if(this.x-10>=et.x-15&&this.x-10<=et.x+15&&this.y+15>=et.y-10&&this.y+15<=et.y+10)
						{
							return true;
						 }
						if(this.x+10>=et.x-15&&this.x+10<=et.x+15&&this.y+15>=et.y-10&&this.y+15<=et.y+10)
						{
							return true;
						 }
					}
				}
				
			}
			break;
		case 2:
			//坦克向左时
			for(int i=0;i<ets.size();i++){
				//取出每一辆坦克
				Tank et=ets.get(i);
				//当该坦克不是自己本身的时候
				if(et!=this&&et.isAlive==true){
					//其他坦克为向上或向下时
					if(et.direction ==0||et.direction ==1){
						if(this.x-15>=et.x-10&&this.x-15<=et.x+10&&this.y-10>=et.y-15&&this.y-10<=et.y+15)
						{
							return true;
						 }
						if(this.x-15>=et.x-10&&this.x-15<=et.x+10&&this.y+10>=et.y-15&&this.y+10<=et.y+15)
						{
							return true;
						}
					}
					//其他坦克为向左或向右时
					if(et.direction ==2||et.direction ==3){
						if(this.x-15>=et.x-15&&this.x-15<=et.x+15&&this.y-10>=et.y-10&&this.y-10<=et.y+10)
						{
							return true;
						 }
						if(this.x-15>=et.x-15&&this.x-15<=et.x+15&&this.y+10>=et.y-10&&this.y+10<=et.y+10)
						{
							return true;
						 }
					}
				}
				
			}
			break;
		case 3:
			//坦克向右时
			for(int i=0;i<ets.size();i++){
				//取出每一辆坦克
				Tank et=ets.get(i);
				//当该坦克不是自己本身的时候
				if(et!=this&&et.isAlive==true){
					//其他坦克为向上或向下时
					if(et.direction ==0||et.direction ==1){
						if(this.x+15>=et.x-10&&this.x+15<=et.x+10&&this.y-10>=et.y-15&&this.y-10<=et.y+15)
						{
							return true;
						 }
						if(this.x+15>=et.x-10&&this.x+15<=et.x+10&&this.y+10>=et.y-15&&this.y+10<=et.y+15)
						{
							return true;
						}
					}
					//其他坦克为向左或向右时
					if(et.direction ==2||et.direction ==3){
						if(this.x+15>=et.x-15&&this.x+15<=et.x+15&&this.y-10>=et.y-10&&this.y-10<=et.y+10)
						{
							return true;
						 }
						if(this.x+15>=et.x-15&&this.x+15<=et.x+15&&this.y+10>=et.y-10&&this.y+10<=et.y+10)
						{
							return true;
						 }
					}
				}
				
			}
			break;
		}
		  return b;  
	}
	
}

//定义我的坦克
class Hero extends Tank{
	//声明一个变量(用来判断是否该给我的
	//坦克添加新的子弹)
	static int isChange=0;
	//创建一个存放我的坦克子弹的集合
	Vector<Shot>ss = new Vector<Shot>();
	//创建一个静态变量存放我的坦克是否碰着墙
	public static boolean isTouchWall=false;
	//创建一个存放敌人坦克的集合
//	Vector<EnemyTank> ets=new Vector<EnemyTank>();
	
	Shot s=null;
	//构造函数
	public Hero(int x,int y){
		
		super(x, y);
	}
	//获取存储敌人坦克的集合
//	public void setEnemyTank(Vector<EnemyTank> ss)
//	{
//		this.ets=ss;
//	 }
	public void shotEnemy(){
		if(isChange==0){
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
    }
//	//不让我的坦克与敌人坦克重叠
//	public boolean isTouchEnemyTank()
//	{
//		boolean b=false;
//		switch(this.direction){
//		case 0:
//			//我的坦克向上时
//			for(int i=0;i<ets.size();i++){
//				//取出每一辆敌人坦克
//				EnemyTank et=ets.get(i);
//				if(et.isAlive){
//				
//			       //当敌人坦克为向上或向下时
//					if(et.direction ==0||et.direction ==1){
//						if(this.x-10>=et.x-10&&this.x-10<=et.x+10&&this.y-15>=et.y-15&&this.y-15<=et.y+15)
//						{
//							return true;
//						 }
//						if(this.x+10>=et.x-10&&this.x+10<=et.x+10&&this.y-15>=et.y-15&&this.y-15<=et.y+15)
//						{
//							return true;
//						}
//					}
//					//当敌人坦克为向左或向右时
//					if(et.direction ==2||et.direction ==3){
//						if(this.x-10>=et.x-15&&this.x-10<=et.x+15&&this.y-15>=et.y-10&&this.y-15<=et.y+10)
//						{
//							return true;
//						 }
//						if(this.x+10>=et.x-15&&this.x+10<=et.x+15&&this.y-15>=et.y-10&&this.y-15<=et.y+10)
//						{
//							return true;
//						 }
//					}
//				
//				}
//			}
//			break;
//		case 1:
//			//我的坦克向下时
//			for(int i=0;i<ets.size();i++){
//				//取出每一辆敌人坦克
//				EnemyTank et=ets.get(i);
//				    if(et.isAlive){
//					//当敌人坦克为向上或向下时
//					if(et.direction ==0||et.direction ==1){
//						if(this.x-10>=et.x-10&&this.x-10<=et.x+10&&this.y+15>=et.y-15&&this.y+15<=et.y+15)
//						{
//							return true;
//						 }
//						if(this.x+10>=et.x-10&&this.x+10<=et.x+10&&this.y+15>=et.y-15&&this.y+15<=et.y+15)
//						{
//							return true;
//						 }
//					}
//					//当敌人坦克为向左或向右时
//					if(et.direction ==2||et.direction ==3){
//						if(this.x-10>=et.x-15&&this.x-10<=et.x+15&&this.y+15>=et.y-10&&this.y+15<=et.y+10)
//						{
//							return true;
//						 }
//						if(this.x+10>=et.x-15&&this.x+10<=et.x+15&&this.y+15>=et.y-10&&this.y+15<=et.y+10)
//						{
//							return true;
//						 }
//					}
//				
//				}
//			}
//			break;
//		case 2:
//			//我的坦克向左时
//			for(int i=0;i<ets.size();i++){
//				//取出每一辆敌人坦克
//				EnemyTank et=ets.get(i);
//			        if(et.isAlive){
//					//当敌人坦克为向上或向下时
//					if(et.direction ==0||et.direction ==1){
//						if(this.x-15>=et.x-10&&this.x-15<=et.x+10&&this.y-10>=et.y-15&&this.y-10<=et.y+15)
//						{
//							return true;
//						 }
//						if(this.x-15>=et.x-10&&this.x-15<=et.x+10&&this.y+10>=et.y-15&&this.y+10<=et.y+15)
//						{
//							return true;
//						}
//					}
//					//当敌人坦克为向左或向右时
//					if(et.direction ==2||et.direction ==3){
//						if(this.x-15>=et.x-15&&this.x-15<=et.x+15&&this.y-10>=et.y-10&&this.y-10<=et.y+10)
//						{
//							return true;
//						 }
//						if(this.x-15>=et.x-15&&this.x-15<=et.x+15&&this.y+10>=et.y-10&&this.y+10<=et.y+10)
//						{
//							return true;
//						 }
//					}
//				
//				}
//			}
//			break;
//		case 3:
//			//我的坦克向右时
//			for(int i=0;i<ets.size();i++){
//				//取出每一辆敌人坦克
//				EnemyTank et=ets.get(i);
//				    if(et.isAlive){
//					//当敌人坦克为向上或向下时
//					if(et.direction ==0||et.direction ==1){
//						if(this.x+15>=et.x-10&&this.x+15<=et.x+10&&this.y-10>=et.y-15&&this.y-10<=et.y+15)
//						{
//							return true;
//						 }
//						if(this.x+15>=et.x-10&&this.x+15<=et.x+10&&this.y+10>=et.y-15&&this.y+10<=et.y+15)
//						{
//							return true;
//						}
//					}
//					//当敌人坦克为向左或向右时
//					if(et.direction ==2||et.direction ==3){
//						if(this.x+15>=et.x-15&&this.x+15<=et.x+15&&this.y-10>=et.y-10&&this.y-10<=et.y+10)
//						{
//							return true;
//						 }
//						if(this.x+15>=et.x-15&&this.x+15<=et.x+15&&this.y+10>=et.y-10&&this.y+10<=et.y+10)
//						{
//							return true;
//						 }
//					}
//				}
//			}
//			break;
//		}
//		
//		return b;
//	 }
	//向上
	public void moveUp(){
		if(y>15&&!isTouchOtherTanks()&&!isTouchRiver()&&!isTouchWall)
		y-=speed;
	}
	//向下
	public void moveDown(){
		if(y<285&&!isTouchOtherTanks()&&!isTouchRiver()&&!isTouchWall)
		y+=speed;
	}
	//向左
	public void moveLeft(){
		if(x>15&&!isTouchOtherTanks()&&!isTouchRiver()&&!isTouchWall)
		x-=speed;
	}
	//向右
	public void moveRight(){
		if(x<385&&!isTouchOtherTanks()&&!isTouchRiver()&&!isTouchWall)
		x+=speed;
	}
}

//定义敌人坦克(将敌人坦克升级为线程)
class EnemyTank extends Tank implements Runnable{
	  //设置一个静态变量（记录是否应该产生随机方向）
	  //该变量还在判断是否应该给坦克添加新的子弹时出现
	  //作用也就是判断是否该给坦克添加新的子弹
	  static int isChange=0;
	 
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
			      if(y>15&&!isTouchOtherTanks()&&!isTouchRiver())
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
				  if(y<285&&!isTouchOtherTanks()&&!isTouchRiver())
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
				  if(x>15&&!isTouchOtherTanks()&&!isTouchRiver())
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
				  if(x<385&&!isTouchOtherTanks()&&!isTouchRiver())
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
		   if(isChange==0)
		   {
		   this.direction=(int)(Math.random()*4);
		    }
		   if(this.isAlive==false){
			
			   break;
		     }
			//判断是否该给敌人坦克添加新的子弹
		    /*
		     * 当你按了暂停的时候，实现方法就是将子弹的速度变为0，
		     * 而敌人坦克除了创建时有一颗子弹外，其他的子弹添加
		     * 都是在敌人坦克线程的run函数中，run函数的实现是通过一个
		     * while循环，坦克只有在每一次变向的瞬间才会添加一颗子弹，而我们
		     * 每一次暂停都是在每一次变向之前，所以并不能将新产生的这一颗子弹的
		     * 速度设置为0，所以解决办法是按了暂停键后，不让坦克再添加新的子弹，
		     * 这就是通过isChange来实现的
		     */
			     if(this.isAlive&&isChange==0){
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

