package TSP;

import java.util.Arrays;
import java.util.Random;

public class GeneticAlgorithm {
	private int populationSize;		//种群规模
	private double mutationRate;	//变异率
	private double crossoverRate;	//交叉率
	private int elitismCount;		//精英成员数
	protected int tournamentSize;	//竞标赛规模
	public GeneticAlgorithm(int populationSize,double mutationRate,double crossover,int elitismCount,int tournamentSize){
		this.populationSize=populationSize;
		this.mutationRate=mutationRate;
		this.crossoverRate=crossover;                   //构造方法
		this.elitismCount=elitismCount;
		this.tournamentSize=tournamentSize;
	}
	public Population initPopulation(int chromosomeLength){		//初始化种群  返回一个种群有设定数量个体带有一个设定长度(基因)的随机染色体  
		Population p=new Population(this.populationSize,chromosomeLength);
		return p;
	}
	public double calculateIndividualFitness(Individual in,City cities[]){			//适应度函数    计算个体的适应度
		//根据传进来的染色体顺序  和城市数组计算个体适应度
		Route route=new Route(in,cities);
		double fitness=1/route.getDistance();  
		/*用1来除以总距离  使得最短距离的个体拥有高的适应性
		 * 
		 * */
		in.setFitness(fitness);
		return fitness;
	}
	public void evaluatePopulationFitness(Population p,City cities[]){  	//评估群体适应度     就是将该群体每个个体适应度相加
			double populationFitness=0;
			for(Individual in : p.getIndividuals()){
				populationFitness +=calculateIndividualFitness(in,cities);
			}
			double avgFitness=populationFitness/p.size();
			p.setPopulationFitness(avgFitness);					//目前有问题  锦标赛选择方法
	}
	public boolean isTerminationConditionMet(int generationCount,int maxGeneration){	//终止检查
		
		return (generationCount>maxGeneration);
	}
	public Individual selectParent(Population p){	//实现锦标赛选择方法    传进来的p评估排序过了  但顺序要打乱
		Population tournament=new Population(this.tournamentSize);
		p.shuffle();
		for(int i=0;i<this.tournamentSize;i++){
			tournament.setIndividual(i, p.getIndividual(i));
		}
		return tournament.getFittest(0);
	}
	public Population crossoverPopulation(Population p){		//亲代排序交叉方法   传进来的p已经评估排序过了
		/*举例亲代1上的基因    1 2 5 3 6 4  长度为6
		 * 	亲代2上的基因       5 1 4 3 6 2 长度为6
		 * 排序交叉方法     随机得到0-6（不包括6）以内两个点 如 2 4   
		 *        将亲代从2开始到4结束（不包括4）的基因给后代相同位置如下
		 *                 _ _ 5 3 _ _
		 *        接着循环亲代2  从4这个位置开始从头循环  如果后代中没有这个基因就把这个基因给后代
		 * 				   6 2 5 3 1 4
		 * */
		Population newp=new Population(p.size());
		for(int populationIndex=0;populationIndex<p.size();populationIndex++){
			Individual parent1=p.getIndividual(populationIndex);
			//判断是否交叉
			if(this.crossoverRate>Math.random()&&populationIndex>=this.elitismCount){
				//用锦标赛的方式找第二个亲代
				Individual parent2=this.selectParent(p);
				//创建空白后代染色体	
				int offspringChromosome[]=new int[parent1.getChromosomeLength()];
				Arrays.fill(offspringChromosome,-1);  //将数组中所有值赋值为-1
				Individual offspring=new Individual(offspringChromosome);
				//得到亲代1的子集  先找2个点切断
				Random rnd=new Random();
				int substrPos1=rnd.nextInt(parent1.getChromosomeLength());
				int substrPos2=rnd.nextInt(parent1.getChromosomeLength());
				//判断哪个大哪个小   若一样怎么办?????   一样的话亲代1就舍去了 只要亲代2的基因从断点开始排序
				int startSubstr=Math.min(substrPos1, substrPos2);
				int endSubstr=Math.max(substrPos1, substrPos2);
				for(int i=startSubstr;i<endSubstr;i++){   //根据2个切断点 把亲代1的这段基因给后代
					offspring.setGene(i,parent1.getGene(i));
				}
				//根据亲代1的断点开始循环亲代2  如果后代没有该基因就把该基因给后代
				for(int i=0;i<parent2.getChromosomeLength();i++){
					int parent2Gene=i+endSubstr;  //亲代循环的指针位
					if(parent2Gene>=parent2.getChromosomeLength()){
						//如果超出亲代2染色体的长度  就减去一个周期  从头开始循环   妙！
						parent2Gene=parent2Gene-parent2.getChromosomeLength();
					}
					if(offspring.containGene(parent2.getGene(parent2Gene))==false){  //若后代不包含这个位置的基因  就代替到后代中从头开始直到等于-1的位置
						//循环去找到后代中空的位置（也就是等于-1）
						for(int ii=0;ii<offspring.getChromosomeLength();ii++){
							if(offspring.getGene(ii)==-1){
								offspring.setGene(ii, parent2.getGene(parent2Gene));
								break;
							}
						}
					}
				}
				newp.setIndividual(populationIndex, offspring);
			}else{
				newp.setIndividual(populationIndex, parent1);
			}
		}
		return newp;
	}
	public Population mutatePopulation(Population p){	//	采用换位变异  不然可能会导致重复
				/*传进来的种群p 前面精英个体适应度保持原来不变  另外多出一些没有进行交叉的个体  
				 *  剩下的都是子代  适应度都为-1
				 * 
				 * */
		Population newp=new Population(p.size());
		for(int populationIndex=0;populationIndex<p.size();populationIndex++){
			Individual in=p.getFittest(populationIndex);//这里应该采用排序  因为精英个体要排在前面防止变异
			if(populationIndex>=this.elitismCount){
				for(int geneIndex=0;geneIndex<in.getChromosomeLength();geneIndex++){  //每位基因判断进行变异
					if(this.mutationRate>Math.random()){
						int newGenePos=(int)(Math.random()*in.getChromosomeLength());//？？？？？换位置有问题 为什么不用之前的    因为变异这个方式要求这样就是循环到的位置还是有可能跟之前的位置交换的
						int oldGene=in.getGene(geneIndex);  //取出原来位置的基因
						in.setGene(geneIndex, in.getGene(newGenePos));  //在原来位置设置新位置的基因
						in.setGene(newGenePos, oldGene);	//再将刚刚取出来的老基因设置到新的位置  换位变异成功
					}
				}
			}
				newp.setIndividual(populationIndex, in);
		}
		return newp;
	}
}
