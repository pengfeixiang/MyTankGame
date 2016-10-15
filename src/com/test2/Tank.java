package com.test2;
import java.util.*;
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

//�����ҵ�̹��
class Hero extends Tank{
	//����һ������ҵ�̹���ӵ��ļ���
	Vector<Shot>ss = new Vector<Shot>();
	//��¼�ҵ�̹���Ƿ񻹻���
	boolean isAlive=true;
	Shot s=null;
	//���캯��
	public Hero(int x,int y){
		
		super(x, y);
	}
	public void shotEnemy(){
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
	//����
	public void moveUp(){
		if(y>15)
		y-=speed;
	}
	//����
	public void moveDown(){
		if(y<248)
		y+=speed;
	}
	//����
	public void moveLeft(){
		if(x>15)
		x-=speed;
	}
	//����
	public void moveRight(){
		if(x<370)
		x+=speed;
	}
}

//�������̹��(������̹������Ϊ�߳�)
class EnemyTank extends Tank implements Runnable{
	
	  //��¼�з�̹���Ƿ񻹻���
	  boolean isAlive=true;
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
			//�ж��Ƿ�ø�����̹������µ��ӵ�
			     if(this.isAlive){
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

