package TSP;

public class Route {		
	private City route[];		//�������·�� Ȼ�������
	private double distance=0;
	public Route(Individual in,City cities[]){
		int chromosome[]=in.getChromosome();
		this.route=new City[cities.length];
		for(int geneIndex=0;geneIndex<chromosome.length;geneIndex++){
			this.route[geneIndex]=cities[chromosome[geneIndex]];	//��Ⱦɫ���ϵ�˳�����·��˳��
		}
		/*����5������  Ⱦɫ���ϻ���ֵ��Ϊ 2 1 4 3 0
		 * ��ôroute[0]=cities[2]
		 * 	  route[1]=cities[1]
		 *    route[2]=cities[4]
		 *    route[3]=cities[3]
		 *    route[4]=cities[0]
		 * */
	}
	public double getDistance(){		//�����·��˳����ܾ���
		if(this.distance>0){
			return this.distance;
		}
		double totalDistance=0;
		for(int cityIndex=0;cityIndex+1<this.route.length;cityIndex++){  
			totalDistance +=this.route[cityIndex].distanceFrom(this.route[cityIndex+1]);
		}
		totalDistance +=this.route[this.route.length-1].distanceFrom(this.route[0]);
		this.distance=totalDistance;
		return totalDistance;
	}
}
