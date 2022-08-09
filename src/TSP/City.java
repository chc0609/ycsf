package TSP;

public class City {			//������Ǽ�¼һ�����е�����   ���ҳ���֮���໥�������
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
	public  City(int x,int y){		//���췽��
		this.x=x;
		this.y=y;
	}
	public double distanceFrom(City othercity){		//�������������еľ���
		double dealtaXSq=Math.pow(this.getX()-othercity.getX(), 2);		//math.pow(������ƽ����)
		double dealtaYSq=Math.pow(this.getY()-othercity.getY(), 2);
		return Math.sqrt(dealtaXSq+dealtaYSq);
	}
	public String toString(){  //���ص�ǰ��������
		return "("+this.getX()+","+this.getY()+")";
	}
}
