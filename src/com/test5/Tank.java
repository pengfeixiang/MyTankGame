package com.test5;
import java.util.*;
//����һ���࣬������¼̹�˵���Ϣ
class Recorder
{
    //��¼����̹�˵�����
	private static int enNum=20;
    //��¼�ҵ�����
	private static int myLife=3;
   public static int getEnNum() {
		return enNum;
	}
	public static void setEnNum(int enNum) {
		Recorder.enNum = enNum;
	}
	public static int getMyLife() {
		return myLife;
	}
	public static void setMyLife(int myLife) {
		Recorder.myLife = myLife;
	}
	public static void reduceenNum()
	{
		enNum--;
	 }
	public static void reducemyLife()
	{
		myLife--;
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
	//����һ������(�����ж��Ƿ�ø��ҵ�
	//̹�������µ��ӵ�)
	static int isChange=0;
	//����һ������ҵ�̹���ӵ��ļ���
	Vector<Shot>ss = new Vector<Shot>();
	//����һ����ŵ���̹�˵ļ���
	Vector<EnemyTank> ets=new Vector<EnemyTank>();
	//��¼�ҵ�̹���Ƿ񻹻���
	boolean isAlive=true;
	Shot s=null;
	//���캯��
	public Hero(int x,int y){
		
		super(x, y);
	}
	//��ȡ�洢����̹�˵ļ���
	public void setEnemyTank(Vector<EnemyTank> ss)
	{
		this.ets=ss;
	}
	public void shotEnemy(){
		if(isChange==0){
		switch(this.direction){
		    //̹������ʱ���ӵ�
		case 0:
			//����һ���ӵ�
			s=new Shot(x-1,y-15,0);
			//���ӵ����ӵ�������
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
	//�����ҵ�̹�������̹���ص����ҵ�̹�˲��ص�����̹�ˣ�
	public boolean isTouchEnemyTank()
	{
		boolean b=false;
		switch(this.direction){
		case 0:
			//�ҵ�̹������ʱ
			for(int i=0;i<ets.size();i++){
				//ȡ��ÿһ������̹��
				EnemyTank et=ets.get(i);
				if(et.isAlive){
				
			       //������̹��Ϊ���ϻ�����ʱ
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
					//������̹��Ϊ���������ʱ
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
			//�ҵ�̹������ʱ
			for(int i=0;i<ets.size();i++){
				//ȡ��ÿһ������̹��
				EnemyTank et=ets.get(i);
				    if(et.isAlive){
					//������̹��Ϊ���ϻ�����ʱ
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
					//������̹��Ϊ���������ʱ
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
			//�ҵ�̹������ʱ
			for(int i=0;i<ets.size();i++){
				//ȡ��ÿһ������̹��
				EnemyTank et=ets.get(i);
			        if(et.isAlive){
					//������̹��Ϊ���ϻ�����ʱ
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
					//������̹��Ϊ���������ʱ
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
			//�ҵ�̹������ʱ
			for(int i=0;i<ets.size();i++){
				//ȡ��ÿһ������̹��
				EnemyTank et=ets.get(i);
				    if(et.isAlive){
					//������̹��Ϊ���ϻ�����ʱ
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
					//������̹��Ϊ���������ʱ
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
	//����
	public void moveUp(){
		if(y>15&&!isTouchEnemyTank())
		y-=speed;
	}
	//����
	public void moveDown(){
		if(y<285&&!isTouchEnemyTank())
		y+=speed;
	}
	//����
	public void moveLeft(){
		if(x>15&&!isTouchEnemyTank())
		x-=speed;
	}
	//����
	public void moveRight(){
		if(x<385&&!isTouchEnemyTank())
		x+=speed;
	}
}

//�������̹��(������̹������Ϊ�߳�)
class EnemyTank extends Tank implements Runnable{
	  //����һ����̬��������¼�Ƿ�Ӧ�ò����������
	  //�ñ��������ж��Ƿ�Ӧ�ø�̹�������µ��ӵ�ʱ����
	  //����Ҳ�����ж��Ƿ�ø�̹�������µ��ӵ�
	  static int isChange=0;
	  //��¼�з�̹���Ƿ񻹻���
	  boolean isAlive=true;
      //������ŵ���̹���ӵ��ļ���
	  Vector<Shot>ss=new Vector<Shot>();
	  //�����ӵ�(����̹�˴���ʱ�����̹���ӵ�����ʱ)
	  //����һ����ŵ���̹�˵ļ���
	  Vector<EnemyTank> ets=new Vector<EnemyTank>();
	  //����һ������ҵ�̹�˵ı���
	  Hero hero=null;
    public EnemyTank(int x,int y){
		super(x, y);
	   }
	
	//����һ����ȡ���е���̹�˵ĺ���
	public void setEnemyTank(Vector<EnemyTank> ss){
		this.ets=ss;
		
	}
	//����һ����ȡ�ҵ�̹�˵ĺ���
	public void setHero(Hero ss){
		this.hero=ss;
	}
	
	//��������Ĺ�����Ҫ���õ���̹�˲��ص��ҵ�̹��
	public boolean isTouchHero(){
		boolean b=false;
		for(int i=0;i<ets.size();i++)
		{
			EnemyTank et=ets.get(i);
			switch(et.direction)
			{
			case 0:
				//����̹������ʱ
				//����ҵ�̹�˻�����
				if(hero.isAlive)
				{
				//�ҵ�̹��Ϊ���ϻ�����ʱ
				if(hero.direction ==0||hero.direction ==1){
					if(et.x-10>=hero.x-10&&et.x-10<=hero.x+10&&et.y-15>=hero.y-15&&et.y-15<=hero.y+15)
					{
						return true;
					 }
					if(et.x+10>=hero.x-10&&et.x+10<=hero.x+10&&et.y-15>=hero.y-15&&et.y-15<=hero.y+15)
					{
						return true;
					}
				}
				//�ҵ�̹��Ϊ���������ʱ
				if(hero.direction ==2||hero.direction ==3){
					if(et.x-10>=hero.x-15&&et.x-10<=hero.x+15&&et.y-15>=hero.y-10&&et.y-15<=hero.y+10)
					{
						return true;
					 }
					if(et.x+10>=hero.x-15&&et.x+10<=hero.x+15&&et.y-15>=hero.y-10&&et.y-15<=hero.y+10)
					{
						return true;
					 }
				 }
				}
				break;
			case 1:
				//����̹������ʱ
				//����ҵ�̹�˻�����
				if(hero.isAlive){
				//�ҵ�̹��Ϊ���ϻ�����ʱ
				if(hero.direction ==0||hero.direction ==1){
					if(et.x-10>=hero.x-10&&et.x-10<=hero.x+10&&et.y+15>=hero.y-15&&et.y+15<=hero.y+15)
					{
						return true;
					 }
					if(et.x+10>=hero.x-10&&et.x+10<=hero.x+10&&et.y+15>=hero.y-15&&et.y+15<=hero.y+15)
					{
						return true;
					 }
				}
				//�ҵ�̹��Ϊ���������ʱ
				if(hero.direction ==2||hero.direction ==3){
					if(et.x-10>=hero.x-15&&et.x-10<=hero.x+15&&et.y+15>=hero.y-10&&et.y+15<=hero.y+10)
					{
						return true;
					 }
					if(et.x+10>=hero.x-15&&et.x+10<=hero.x+15&&et.y+15>=hero.y-10&&et.y+15<=hero.y+10)
					{
						return true;
					 }
				}
			}
				break;
			case 2:
				//����̹������ʱ
				//����ҵ�̹�˻�����
				if(hero.isAlive){
				//�ҵ�̹��Ϊ���ϻ�����ʱ
				if(hero.direction ==0||hero.direction ==1){
					if(et.x-15>=hero.x-10&&et.x-15<=hero.x+10&&et.y-10>=hero.y-15&&et.y-10<=hero.y+15)
					{
						return true;
					 }
					if(et.x-15>=hero.x-10&&et.x-15<=hero.x+10&&et.y+10>=hero.y-15&&et.y+10<=hero.y+15)
					{
						return true;
					 }
				}
				//�ҵ�̹��Ϊ���������ʱ
				if(hero.direction ==2||hero.direction ==3){
					if(et.x-15>=hero.x-15&&et.x-15<=hero.x+15&&et.y-10>=hero.y-10&&et.y-10<=hero.y+10)
					{
						return true;
					 }
					if(et.x-15>=hero.x-15&&et.x-15<=hero.x+15&&et.y+10>=hero.y-10&&et.y+10<=hero.y+10)
					{
						return true;
					 }
				}
			}
				 break;
			case 3:
				//����̹������ʱ
				//����ҵ�̹�˻�����
				if(hero.isAlive){
				//�ҵ�̹��Ϊ���ϻ�����ʱ
				if(hero.direction ==0||hero.direction ==1){
					if(et.x+15>=hero.x-10&&et.x+15<=hero.x+10&&et.y-10>=hero.y-15&&et.y-10<=hero.y+15)
					{
						return true;
					 }
					if(et.x+15>=hero.x-10&&et.x+15<=hero.x+10&&et.y+10>=hero.y-15&&et.y+10<=hero.y+15)
					{
						return true;
					}
				}
				//�ҵ�̹��Ϊ���������ʱ
				if(et.direction ==2||et.direction ==3){
					if(et.x+15>=hero.x-15&&et.x+15<=hero.x+15&&et.y-10>=hero.y-10&&et.y-10<=hero.y+10)
					{
						return true;
					 }
					if(et.x+15>=hero.x-15&&et.x+15<=hero.x+15&&et.y+10>=hero.y-10&&et.y+10<=hero.y+10)
					{
						return true;
					 }
				}
			}
				break;
			 }
		 }
		return b;
	}
	
	//��������Ĺ�����Ҫ���õ���̹��֮���໥���ص�
	public boolean isTouchOtherTanks(){
		
		boolean b=false;
		switch(this.direction){
		case 0:
			//̹������ʱ
			for(int i=0;i<ets.size();i++){
				//ȡ��ÿһ��̹��
				EnemyTank et=ets.get(i);
				//����̹�˲����Լ�������ʱ��
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
				EnemyTank et=ets.get(i);
				//����̹�˲����Լ�������ʱ��
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
				EnemyTank et=ets.get(i);
				//����̹�˲����Լ�������ʱ��
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
				EnemyTank et=ets.get(i);
				//����̹�˲����Լ�������ʱ��
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

    public void run() {
		
		while(true){
		   
		switch(this.direction){
		  case 0:
			  for(int i=0;i<50;i++)
			  {   //�������һ�����⣺����һ������̹�������ҵ�̹��ʱ�����е���̹�˶�����
				  //�ƶ���������this.isTouchHero()�е����⣬��Ϊÿһ������̹�˶���һ���߳�
				  //��һֱ����ִ��run����������ÿ�ζ������isTouchHero()��������ֻҪ����̹��
				  //����һ��̹�������ҵ�̹�ˣ��ͻ᷵��false,��ʱ���еĵ���̹�˵õ���ֵ��Ϊfalse
				  //�������Ƕ������ƶ�
			      if(y>15&&!this.isTouchOtherTanks()&&!this.isTouchHero())
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
				  if(y<285&&!this.isTouchOtherTanks()&&!this.isTouchHero())
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
				  if(x>15&&!this.isTouchOtherTanks()&&!this.isTouchHero())
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
				  if(x<385&&!this.isTouchOtherTanks()&&!this.isTouchHero())
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
			//�ж��Ƿ�ø�����̹�������µ��ӵ�
		    /*
		     * ���㰴����ͣ��ʱ��ʵ�ַ������ǽ��ӵ����ٶȱ�Ϊ0��
		     * ������̹�˳��˴���ʱ��һ���ӵ��⣬�������ӵ�����
		     * �����ڵ���̹���̵߳�run�����У�run������ʵ����ͨ��һ��
		     * whileѭ����̹��ֻ����ÿһ�α����˲��Ż�����һ���ӵ���������
		     * ÿһ����ͣ������ÿһ�α���֮ǰ�����Բ����ܽ��²�������һ���ӵ���
		     * �ٶ�����Ϊ0�����Խ���취�ǰ�����ͣ���󣬲���̹���������µ��ӵ���
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
							//���ӵ����ӵ�������
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
