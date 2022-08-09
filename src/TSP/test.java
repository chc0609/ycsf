package TSP;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int numCities=15;
		City cities[]=new City[numCities];
		for(int cityIndex=0;cityIndex<numCities;cityIndex++){
			int x=(int)(100*Math.random());
			int y=(int)(100*Math.random());		//给这100个城市赋予随机的坐标
			cities[cityIndex]=new City(x,y);
			System.out.println(cities[cityIndex]);
		}
	}

}
/*记录学习到的基本算法
 * 算法1   数组切位开始从尾循环到头再到尾
 *算法2   数组里面打乱顺序
 * */
