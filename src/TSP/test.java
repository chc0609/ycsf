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
			int y=(int)(100*Math.random());		//����100�����и������������
			cities[cityIndex]=new City(x,y);
			System.out.println(cities[cityIndex]);
		}
	}

}
/*��¼ѧϰ���Ļ����㷨
 * �㷨1   ������λ��ʼ��βѭ����ͷ�ٵ�β
 *�㷨2   �����������˳��
 * */
