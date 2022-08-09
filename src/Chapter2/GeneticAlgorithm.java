package Chapter2;

public class GeneticAlgorithm {
	private int populationSize;		//种群规模
	private double mutationRate;	//变异率
	private double crossoverRate;	//交叉率
	private int elitismCount;		//精英成员数
	public GeneticAlgorithm(int populationSize,double mutationRate,double crossover,int elitismCount){
		this.populationSize=populationSize;
		this.mutationRate=mutationRate;
		this.crossoverRate=crossover;                   //构造方法
		this.elitismCount=elitismCount;
	}
	public Population initPopulation(int chromosomeLength){		//初始化种群  返回一个种群有设定数量个体带有一个设定长度(基因)的随机染色体  
		Population p=new Population(this.populationSize,chromosomeLength);
		return p;
	}
	public double calculateIndividualFitness(Individual in){			//适应度函数    计算个体的适应度
		int correctGenes=0;
		for(int geneLocation=0;geneLocation<in.getChromosomeLength();geneLocation++){
			if(in.getGene(geneLocation)==1){
				correctGenes++;				//循环一遍个体的基因  将基因为1的个数统计出来
			}
		}
		double fitness=(double)correctGenes/in.getChromosomeLength();
		in.setFitness(fitness);
		return fitness;
	}
	public void evaluatePopulationFitness(Population p){  	//评估群体适应度     就是将该群体每个个体适应度相加
		double populationfitness=0;
		for(Individual in : p.getIndividuals()){
			populationfitness +=calculateIndividualFitness(in);
		}
		p.setPopulationFitness(populationfitness);
	}
	public boolean isTerminationConditionMet(Population p){			//终止检查
		for(Individual in : p.getIndividuals()){
			if(in.getFitness()==1){					//本例子中个体适应度为1就是最好     1的个数/染色体总长度=1最好		
				return true;						//如果种群中任何个体适应度为1  就可以停止
			}
		}
		return false;
	}
	public Individual selectParent(Population p){	//实现轮盘赌选择方法
		Individual individuals[]=p.getIndividuals();
		double populationFitness=p.getPopulationFitness();
		double rouletteWheelPosition=Math.random()*populationFitness;	//可以理解成指针
		double spinWheel=0;
		for(Individual in : individuals){
			spinWheel +=in.getFitness();		//前面的个体适应度累加刚好超过这个指针
			if(spinWheel>=rouletteWheelPosition){	
				return in;
			}
		}
		return null;   //必须要加  因为编译器不能判断是否能进for循环  虽然for里面的句子一定能返回
	}
	public Population crossoverPopulation(Population p){		//交叉方法   传进来的p已经评估排序过了
		Population newp=new Population(p.size());	//创建相同数量的种群
		for(int populationLocation=0;populationLocation<p.size();populationLocation++){
			Individual parent1=p.getFittest(populationLocation);
			if(this.crossoverRate>Math.random() && populationLocation>=this.elitismCount){	//前面那个条件正好是控制交叉率在95%
				Individual offspring =new Individual(parent1.getChromosomeLength());	//初始化子代
				Individual parent2=selectParent(p);	//找到第二个亲代
				//Individual parent2=p.getFittest(0);  //如果固定  就相当于缺少了进化   最后可能整个种群会变成一样
					for(int	geneLocation=0;geneLocation<parent1.getChromosomeLength();geneLocation++){
						if(Math.random()>=0.5){
							offspring.setGene(geneLocation, parent1.getGene(geneLocation));
						}else{
							offspring.setGene(geneLocation, parent2.getGene(geneLocation));
						}
					}
			newp.setIndividual(populationLocation, offspring);
			}else{
				newp.setIndividual(populationLocation, parent1);
			}
			
		}
		return newp;
	}
	public Population mutatePopulation(Population p){  
		Population newp=new Population(p.size());
		for(int populationLocation=0;populationLocation<p.size();populationLocation++){
			Individual in=p.getFittest(populationLocation);   //  后面排序后都是-1 不用管了 只用取过来变异就行
			//System.out.println(in.getFitness());
			if(populationLocation>=this.elitismCount){	//判断该个体需不需要变异
			for(int geneLocation=0;geneLocation<in.getChromosomeLength();geneLocation++){  //开始变异
					if(this.mutationRate>Math.random()){
						int newGene=1;
						if(in.getGene(geneLocation)==1){
							newGene=0;
						}
						in.setGene(geneLocation, newGene);
					}
				}
			}
			newp.setIndividual(populationLocation, in);
		}
		return newp;
	}
}
