package TSP;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
public class Population {
	private Individual population[];     //数组每个位置上存的个体  合起来称种群
	private double populationFitness=-1;
	public Individual[] getIndividuals(){
		return this.population;				//返回种群数组
	}
	public void setPopulationFitness(double fitness){
		this.populationFitness=fitness;			//设置种群适应度
	}
	public double getPopulationFitness(){
		return this.populationFitness;			//返回种群适应度
	}
	public int size(){
		return this.population.length;			//返回种群规模
	}
	public Individual setIndividual(int location,Individual in){
		return population[location]=in;			//在群体中相应位置设置个体  并返回
	}
	public Individual getIndividual(int location){    //这里与最上面方法不同这是返回种群中一个个体
		return this.population[location];         
	}
	public Population(int populationSize){
		this.population=new Individual[populationSize]; 	//构造方法  设置种群规模
	}
	public Population(int populationSize,int chromosomeLength){			//构造数量为populationSize的种群
		this.population=new Individual[populationSize];					//且该种群生成每个随机染色体长度为chromosomeLength
		for(int individualLocation=0;individualLocation<populationSize;individualLocation++){
			Individual in=new Individual(chromosomeLength);
			population[individualLocation]=in;					
		}
	}
	public Individual getFittest(int location){
		MyComparator mc=new MyComparator();    //创建比较器  告诉比较器我这两个对象如何比大小
		Arrays.sort(this.population, mc);      //根据每个个体适应度降序排列
		return this.population[location];		//得到某个位置的个体
	}
	public void shuffle(){		//洗牌  打乱种群中的个体排列顺序
		Random rnd =new Random();
		for(int i=population.length-1;i>0;i--){
			int index=rnd.nextInt(i+1); //该方法的作用是生成一个随机的int值，该值介于[0,n)的区间，
										//		也就是0到n之间的随机int值，包含0而不包含n
			Individual a=population[index];			//这3句是 比如种群数组长度为6    
			population[index]=population[i];		//循环到5 4 3 2 1 0 每个位置时  都随机挑一个位置进行交换个体
			population[i]=a;					//比如在i=5这个位置 就会在 0 1 2 3 4 5中选一个换位置  在i=4这个位置  就会在剩下0 1 2 3 4选 
		}
	}
}
