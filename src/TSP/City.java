package TSP;

public class City {			//该类就是记录一个城市的坐标   并且城市之间相互计算距离
	private int x;
	private	int y;	
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
	public  City(int x,int y){		//构造方法
		this.x=x;
		this.y=y;
	}
	public double distanceFrom(City othercity){		//计算与其他城市的距离
		double dealtaXSq=Math.pow(this.getX()-othercity.getX(), 2);		//math.pow(底数，平方数)
		double dealtaYSq=Math.pow(this.getY()-othercity.getY(), 2);
		return Math.sqrt(dealtaXSq+dealtaYSq);
	}
	public String toString(){  //返回当前城市坐标
		return "("+this.getX()+","+this.getY()+")";
	}
}
