package TSP;

public class Route {		
	private City route[];		//保存城市路线 然后算距离
	private double distance=0;
	public Route(Individual in,City cities[]){
		int chromosome[]=in.getChromosome();
		this.route=new City[cities.length];
		for(int geneIndex=0;geneIndex<chromosome.length;geneIndex++){
			this.route[geneIndex]=cities[chromosome[geneIndex]];	//让染色体上的顺序决定路径顺序
		}
		/*比如5个城市  染色体上基因赋值后为 2 1 4 3 0
		 * 那么route[0]=cities[2]
		 * 	  route[1]=cities[1]
		 *    route[2]=cities[4]
		 *    route[3]=cities[3]
		 *    route[4]=cities[0]
		 * */
	}
	public double getDistance(){		//计算该路径顺序的总距离
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
