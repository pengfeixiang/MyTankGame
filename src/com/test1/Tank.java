package com.test1;

//定义坦克类
class Tank{
	
	//坦克的横坐标
	int x=0;
	//坦克的纵坐标
	int y=0;
	//定义坦克速度变量
	int speed=6;
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
	//构造函数
	public Hero(int x,int y){
		
		super(x, y);
	}
	//向上
	public void moveUp(){
		y-=speed;
	}
	//向下
	public void moveDown(){
		y+=speed;
	}
	//向左
	public void moveLeft(){
		x-=speed;
	}
	//向右
	public void moveRight(){
		x+=speed;
	}
}

//定义敌人坦克
class EnemyTank extends Tank{
	
	public EnemyTank(int x,int y){
		super(x, y);
	}
}
