package com.test1;

//����̹����
class Tank{
	
	//̹�˵ĺ�����
	int x=0;
	//̹�˵�������
	int y=0;
	//����̹���ٶȱ���
	int speed=6;
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
	//���캯��
	public Hero(int x,int y){
		
		super(x, y);
	}
	//����
	public void moveUp(){
		y-=speed;
	}
	//����
	public void moveDown(){
		y+=speed;
	}
	//����
	public void moveLeft(){
		x-=speed;
	}
	//����
	public void moveRight(){
		x+=speed;
	}
}

//�������̹��
class EnemyTank extends Tank{
	
	public EnemyTank(int x,int y){
		super(x, y);
	}
}
