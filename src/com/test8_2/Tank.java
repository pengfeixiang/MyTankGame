package com.test8_2;
import java.util.*;
import java.io.*;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

//������������
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
		//���ǻ���
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
//ǽ��
class Wall
   {
	//��¼ǽ�ĺ�����
	int x=0;
	//��¼ǽ��������
	int y=0;
	
	//��¼ǽ�Ƿ��ӵ��ݻ�
	boolean isAlive=true;
	//���캯��
	public Wall(int x,int y)
	{
	 this.x=x;
	 this.y=y;
	 }
	}
//����һ���࣬������¼̹�˵���Ϣ
class Recorder
{
    //��¼����̹�˵�����
	private static int enNum=20;
    //��¼�ҵ�����
	private static int myLife=3;
	//��¼�һ��еĵ���̹������(������Ϸ���������е�����)
	private static int shotEnNum=0;
	//��¼���δ���Ϸ���һ��еĵ���̹������
	private static int shotEnNumToday=0;
	//����һ�����ϣ��洢���еĵ���̹��
	private Vector<EnemyTank>ets=new Vector<EnemyTank>();
	
	//�����ļ������������������
	private static FileWriter fw=null;
	private static FileReader fr=null;
	//��������������
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
	//���ҵ�̹�˱�����ʱ���ҵ�������һ
	public static void reducemyLife()
	{
		myLife--;
	 }
	//���һ�����һ������̹�ˣ����е�������һ
	public static void addShotEnNum()
	{
		shotEnNum++;
	 }
	//���һ�����һ������̹�ˣ�������Ϸ���е�������һ
	public static void addShotEnNumToday()
	{
		shotEnNumToday++;
	 }
	public void keepRecAndEnemyTank()
	{
		 try 
		   {   
			   //�������������󣬶�ȡָ��Ŀ¼�ļ�����Ϣ
			   fw=new FileWriter("d:/java��Ŀ/recorder.txt");
			   //��������������
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
				 //�ر���
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
		   //�������������󣬶�ȡָ��Ŀ¼�ļ�����Ϣ
		   fr=new FileReader("d:/java��Ŀ/recorder.txt");
		   //��������������
		   br=new BufferedReader(fr);
		   String s=null;
		   //��ȡ
		   s=br.readLine();
		   //���ַ�����������ת��Ϊ����
		   shotEnNum=Integer.parseInt(s);
	      } 
	   catch (Exception e) 
	   {
		e.printStackTrace();
	      }
	   finally
	   {
		  try {
			 //�ر���
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
			//������������󣬲�����Ϣ���浽ָ��Ŀ¼
			fw=new FileWriter("d:/java��Ŀ/recorder.txt");
			//��������������
			bw=new BufferedWriter(fw);
			//�����Ϣ
			bw.write(shotEnNum+"");
			
		 } 
		catch (IOException e)
		{
			e.printStackTrace();
		 }
		finally
		{
		  try {
			  //�ر���
			  bw.close();
			  fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 }
	 }
  }
//����ը����
class Bomb
{
	//��������
	int x;
	int y;
	//ը��������ֵ��������������ͼƬ���л�
	int lifetime=9;
	//��¼ը���Ƿ񻹻���
	boolean isAlive=true;
	//���캯��
	public Bomb(int x,int y){
		
		this.x=x;
		this.y=y;
	}
	//����lifetime�����ļ���
	public void lifeDown(){
		if(lifetime>0)
		{
		this.lifetime--;
		}else{
		this.isAlive=false;	
		}
	}
	
}
//�����ӵ���
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
	//��¼�ӵ��߳��Ƿ񻹻���
	boolean isAlive=true;
	public Shot(int x,int y,int direction)
	{
		this.x=x;
		this.y=y;
		this.direction=direction;
	 }
	//��д�߳������run����
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
			//����
			y-=speed;
			break;
		case 1:
			//����
			y+=speed;
			break;
		case 2:
			//����
			x-=speed;
			break;
		case 3:
		    //����
			x+=speed;
			break;
		}
		
        //���ӵ����ﴰ��߿�ʱ���ӵ�������Ҳ�����߳�ֹͣ��
		if(x<1||x>399||y<1||y>299){
			
            this.isAlive=false;
            break;
		}
	  }
	}
	
}

//����̹����
class Tank{
	
	//̹�˵ĺ�����
	int x=0;
	//̹�˵�������
	int y=0;
	//����̹���ٶȱ���
	int speed=1;
	//����̹�˵ķ���
	//0��ʾ���ϣ�1��ʾ���£�2��ʾ����3��ʾ����
	int direction=0;
	//����̹����ɫ
	int color;
	//��¼̹���Ƿ����
	boolean isAlive=true;
	//����һ���������̹�˵ļ���
	Vector<Tank> ets=new Vector<Tank>();
//	//�����ĸ���ά�������飨�洢ǽ����
//	Wall walls1[][]=new Wall[5][12];
//	Wall walls2[][]=new Wall[4][12];
//	Wall walls3[][]=new Wall[4][12];
//	Wall walls4[][]=new Wall[4][12];
	//����һ����ȡ����̹�˵ĺ���(����̹�˺��ҵ�̹��)
	public  void setTank(Vector<Tank> ss){
		this.ets=ss;
	   }
	
	//���캯��
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
//	//��̹�˲�Ҫͨ��ǽ1
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
//    	   //̹������ʱ
//    	   case 0:
//    		   for(int j=0;j<4;j++)
//    		   for(int k=0;k<12;k++)
//    		   { //ȡ��ǽ1�е�һ��������Ԫ����ǽ����
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
//    		 //̹������ʱ
//    	     case 1:
//    		   for(int j=0;j<4;j++)
//    		   for(int k=0;k<12;k++)
//    		   { //ȡ��ǽ1�е�һ��������Ԫ����ǽ����
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
//    		 //̹������ʱ
//      	     case 2:
//      		   for(int j=0;j<4;j++)
//      		   for(int k=0;k<12;k++)
//      		   { //ȡ��ǽ1�е�һ��������Ԫ����ǽ����
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
//      		 //̹������ʱ
//      	     case 3:
//      		   for(int j=0;j<4;j++)
//      		   for(int k=0;k<12;k++)
//      		   { //ȡ��ǽ1�е�һ��������Ԫ����ǽ����
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
//	//��̹�˲�Ҫͨ��ǽ2
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
//    	   //̹������ʱ
//    	   case 0:
//    		   for(int j=0;j<4;j++)
//    		   for(int k=0;k<12;k++)
//    		   { //ȡ��ǽ1�е�һ��������Ԫ����ǽ����
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
//    		 //̹������ʱ
//    	     case 1:
//    		   for(int j=0;j<4;j++)
//    		   for(int k=0;k<12;k++)
//    		   { //ȡ��ǽ1�е�һ��������Ԫ����ǽ����
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
//    		 //̹������ʱ
//      	     case 2:
//      		   for(int j=0;j<4;j++)
//      		   for(int k=0;k<12;k++)
//      		   { //ȡ��ǽ1�е�һ��������Ԫ����ǽ����
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
//      		 //̹������ʱ
//      	     case 3:
//      		   for(int j=0;j<4;j++)
//      		   for(int k=0;k<12;k++)
//      		   { //ȡ��ǽ1�е�һ��������Ԫ����ǽ����
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
//	//��̹�˲�Ҫͨ��ǽ3
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
//    	   //̹������ʱ
//    	   case 0:
//    		   for(int j=0;j<4;j++)
//    		   for(int k=0;k<12;k++)
//    		   { //ȡ��ǽ1�е�һ��������Ԫ����ǽ����
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
//    		 //̹������ʱ
//    	     case 1:
//    		   for(int j=0;j<4;j++)
//    		   for(int k=0;k<12;k++)
//    		   { //ȡ��ǽ1�е�һ��������Ԫ����ǽ����
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
//    		 //̹������ʱ
//      	     case 2:
//      		   for(int j=0;j<4;j++)
//      		   for(int k=0;k<12;k++)
//      		   { //ȡ��ǽ1�е�һ��������Ԫ����ǽ����
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
//      		 //̹������ʱ
//      	     case 3:
//      		   for(int j=0;j<4;j++)
//      		   for(int k=0;k<12;k++)
//      		   { //ȡ��ǽ1�е�һ��������Ԫ����ǽ����
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
//	//��̹�˲�Ҫͨ��ǽ4
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
//    	   //̹������ʱ
//    	   case 0:
//    		   for(int j=0;j<4;j++)
//    		   for(int k=0;k<12;k++)
//    		   { //ȡ��ǽ1�е�һ��������Ԫ����ǽ����
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
//    		 //̹������ʱ
//    	     case 1:
//    		   for(int j=0;j<4;j++)
//    		   for(int k=0;k<12;k++)
//    		   { //ȡ��ǽ1�е�һ��������Ԫ����ǽ����
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
//    		 //̹������ʱ
//      	     case 2:
//      		   for(int j=0;j<4;j++)
//      		   for(int k=0;k<12;k++)
//      		   { //ȡ��ǽ1�е�һ��������Ԫ����ǽ����
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
//      		 //̹������ʱ
//      	     case 3:
//      		   for(int j=0;j<4;j++)
//      		   for(int k=0;k<12;k++)
//      		   { //ȡ��ǽ1�е�һ��������Ԫ����ǽ����
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
	//��̹�˲�Ҫͨ������
	public boolean isTouchRiver()
	{
		boolean b=false;
		switch(this.direction)
		{
		 //��̹������ʱ
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
		 //��̹������ʱ
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
		 //��̹������ʱ
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
		 //��̹������ʱ
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
	//�����е�̹��֮�以���ص�
	public boolean isTouchOtherTanks(){
		
		boolean b=false;
		switch(this.direction){
		case 0:
			//̹������ʱ
			for(int i=0;i<ets.size();i++){
				//ȡ��ÿһ��̹��
				Tank et=ets.get(i);
				//����̹�˲����Լ������ʱ��
				if(et!=this&&et.isAlive==true){
					//����̹��Ϊ���ϻ�����ʱ
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
					//����̹��Ϊ���������ʱ
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
			//̹������ʱ
			for(int i=0;i<ets.size();i++){
				//ȡ��ÿһ��̹��
				Tank et=ets.get(i);
				//����̹�˲����Լ������ʱ��
				if(et!=this&&et.isAlive==true){
					//����̹��Ϊ���ϻ�����ʱ
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
					//����̹��Ϊ���������ʱ
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
			//̹������ʱ
			for(int i=0;i<ets.size();i++){
				//ȡ��ÿһ��̹��
				Tank et=ets.get(i);
				//����̹�˲����Լ������ʱ��
				if(et!=this&&et.isAlive==true){
					//����̹��Ϊ���ϻ�����ʱ
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
					//����̹��Ϊ���������ʱ
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
			//̹������ʱ
			for(int i=0;i<ets.size();i++){
				//ȡ��ÿһ��̹��
				Tank et=ets.get(i);
				//����̹�˲����Լ������ʱ��
				if(et!=this&&et.isAlive==true){
					//����̹��Ϊ���ϻ�����ʱ
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
					//����̹��Ϊ���������ʱ
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

//�����ҵ�̹��
class Hero extends Tank{
	//����һ������(�����ж��Ƿ�ø��ҵ�
	//̹������µ��ӵ�)
	static int isChange=0;
	//����һ������ҵ�̹���ӵ��ļ���
	Vector<Shot>ss = new Vector<Shot>();
	//����һ����̬��������ҵ�̹���Ƿ�����ǽ
	public static boolean isTouchWall=false;
	//����һ����ŵ���̹�˵ļ���
//	Vector<EnemyTank> ets=new Vector<EnemyTank>();
	
	Shot s=null;
	//���캯��
	public Hero(int x,int y){
		
		super(x, y);
	}
	//��ȡ�洢����̹�˵ļ���
//	public void setEnemyTank(Vector<EnemyTank> ss)
//	{
//		this.ets=ss;
//	 }
	public void shotEnemy(){
		if(isChange==0){
		switch(this.direction){
		    //̹������ʱ���ӵ�
		case 0:
			//����һ���ӵ�
			s=new Shot(x-1,y-15,0);
			//���ӵ���ӵ�������
			ss.add(s);
			break;
			//̹������ʱ���ӵ�
		case 1:
			s=new Shot(x-1,y+15,1);
			ss.add(s);
			break;
			//̹������ʱ���ӵ�
		case 2:
			s=new Shot(x-15,y-1,2);
			ss.add(s);
			break;
			//̹������ʱ���ӵ�
		case 3:
			s=new Shot(x+15,y-1,3);
			ss.add(s);
			break;
		}
		Thread t=new Thread(s);
		t.start();
	  }
    }
//	//�����ҵ�̹�������̹���ص�
//	public boolean isTouchEnemyTank()
//	{
//		boolean b=false;
//		switch(this.direction){
//		case 0:
//			//�ҵ�̹������ʱ
//			for(int i=0;i<ets.size();i++){
//				//ȡ��ÿһ������̹��
//				EnemyTank et=ets.get(i);
//				if(et.isAlive){
//				
//			       //������̹��Ϊ���ϻ�����ʱ
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
//					//������̹��Ϊ���������ʱ
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
//			//�ҵ�̹������ʱ
//			for(int i=0;i<ets.size();i++){
//				//ȡ��ÿһ������̹��
//				EnemyTank et=ets.get(i);
//				    if(et.isAlive){
//					//������̹��Ϊ���ϻ�����ʱ
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
//					//������̹��Ϊ���������ʱ
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
//			//�ҵ�̹������ʱ
//			for(int i=0;i<ets.size();i++){
//				//ȡ��ÿһ������̹��
//				EnemyTank et=ets.get(i);
//			        if(et.isAlive){
//					//������̹��Ϊ���ϻ�����ʱ
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
//					//������̹��Ϊ���������ʱ
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
//			//�ҵ�̹������ʱ
//			for(int i=0;i<ets.size();i++){
//				//ȡ��ÿһ������̹��
//				EnemyTank et=ets.get(i);
//				    if(et.isAlive){
//					//������̹��Ϊ���ϻ�����ʱ
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
//					//������̹��Ϊ���������ʱ
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
	//����
	public void moveUp(){
		if(y>15&&!isTouchOtherTanks()&&!isTouchRiver()&&!isTouchWall)
		y-=speed;
	}
	//����
	public void moveDown(){
		if(y<285&&!isTouchOtherTanks()&&!isTouchRiver()&&!isTouchWall)
		y+=speed;
	}
	//����
	public void moveLeft(){
		if(x>15&&!isTouchOtherTanks()&&!isTouchRiver()&&!isTouchWall)
		x-=speed;
	}
	//����
	public void moveRight(){
		if(x<385&&!isTouchOtherTanks()&&!isTouchRiver()&&!isTouchWall)
		x+=speed;
	}
}

//�������̹��(������̹������Ϊ�߳�)
class EnemyTank extends Tank implements Runnable{
	  //����һ����̬��������¼�Ƿ�Ӧ�ò����������
	  //�ñ��������ж��Ƿ�Ӧ�ø�̹������µ��ӵ�ʱ����
	  //����Ҳ�����ж��Ƿ�ø�̹������µ��ӵ�
	  static int isChange=0;
	 
      //������ŵ���̹���ӵ��ļ���
	  Vector<Shot>ss=new Vector<Shot>();
	  //����ӵ�(����̹�˴���ʱ�����̹���ӵ�����ʱ)
	 
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
			//�ж��Ƿ�ø�����̹������µ��ӵ�
		    /*
		     * ���㰴����ͣ��ʱ��ʵ�ַ������ǽ��ӵ����ٶȱ�Ϊ0��
		     * ������̹�˳��˴���ʱ��һ���ӵ��⣬�������ӵ����
		     * �����ڵ���̹���̵߳�run�����У�run������ʵ����ͨ��һ��
		     * whileѭ����̹��ֻ����ÿһ�α����˲��Ż����һ���ӵ���������
		     * ÿһ����ͣ������ÿһ�α���֮ǰ�����Բ����ܽ��²�������һ���ӵ���
		     * �ٶ�����Ϊ0�����Խ���취�ǰ�����ͣ���󣬲���̹��������µ��ӵ���
		     * �����ͨ��isChange��ʵ�ֵ�
		     */
			     if(this.isAlive&&isChange==0){
				  if(this.ss.size()<5){
					 Shot s=null;
					 switch(this.direction) {
					    case 0:
						    //̹������ʱ���ӵ�
							//����һ���ӵ�
						  
							s=new Shot(x-1,y-15,0);
							//���ӵ���ӵ�������
							this.ss.add(s);
							break;
							//̹������ʱ���ӵ�
						case 1:
							 
							s=new Shot(x-1,y+15,1);
							this.ss.add(s);
							break;
							//̹������ʱ���ӵ�
						case 2:
							 
							s=new Shot(x-15,y-1,2);
							this.ss.add(s);
							break;
							//̹������ʱ���ӵ�
						case 3:
							  
							s=new Shot(x+15,y-1,3);
							this.ss.add(s);
							break;
					 }
					 //�����ӵ��߳�
					 Thread t=new Thread(s);
					 t.start();
				  }
				}
			  }
			}
	      }

