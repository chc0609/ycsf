package MyRcpsp;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class GeneticAlgorithm {
	private int populationSize;		//种群规模
	private double mutationRate;	//变异率
	private double crossoverRate;	//交叉率
	private int elitismCount;		//精英成员数
	protected int tournamentSize;	//竞标赛规模
	private int []resourcesLimit;   //种群中每个个体统一的资源限量
	private List<Task> TaskSet;  //初始任务集合
	private int totalTime;//总任务时间  每个个体都一样  用来算适应度
	public GeneticAlgorithm(int populationSize,double mutationRate,double crossover,int elitismCount,int tournamentSize,int []resourcesLimit){
		this.populationSize=populationSize;
		this.mutationRate=mutationRate;
		this.crossoverRate=crossover;                   //构造方法
		this.elitismCount=elitismCount;
		this.tournamentSize=tournamentSize;
		this.resourcesLimit=resourcesLimit;
	}
	public Population initPopulation(List<Task> TaskSet){		
		//初始化种群  返回一个种群有设定数量个体 且每个个体都带有符合约束的随机序列  和相同的总时间  
		this.TaskSet=TaskSet;
		int totalTime=0;
		for(Task t:TaskSet){
			totalTime  +=t.getDuration();
		}
		this.totalTime=totalTime;
		Population p=new Population(this.populationSize,TaskSet,totalTime,this.resourcesLimit);
		return p;
	}
	public int calculateIndividualFitness(Individual in){			//适应度函数    计算个体的适应度
		//放进去的个体的映射列表必须对应初始的任务集合  而不能是克隆的
		int totalTime=in.getTotalTime();
		int []resourcesLimit=in.getResourcesLimit();
		int [][]resourcesUsage=new int[totalTime][resourcesLimit.length];
		Map<Integer,Task> schedule=in.getScheduleChromosome();
		in.resetTask(TaskSet);
		/*史诗级BUG 为什么加这里  因为种群中个体排序时 适应度相等时排序会不一样  这就导致后代可能会取到两个一样的个体  虽然两个个体映射对应的都是初始
		 * 集合  但是第一个个体在计算完适应度之后  映射的是克隆的任务集合  这些克隆里紧前紧后关系不对而且带有时间   第二个个体跟随第一个个体改变
		 * 所以第二个个体计算适应度时  放进去带有的是错误的任务集合  就会导致适应度计算错误
		 * */
		for(Task t:schedule.values()){//根据该个体映射表对最底层的任务进行时间安排      
					//	对最底层元素的操作对所有个体共享   所以结束后还要将这些任务起始时间都设为0
			int time=0;
			if(t.getPredecesorTaskSets().isEmpty()){
				t.setStarttime(0);  //初始结点1
			}else{
				for(Task pret : t.getPredecesorTaskSets()){
					if(pret.getEndtime()>time){
						time=pret.getEndtime();
					}
				}
				t.setStarttime(time);//这个时间是没有资源限制下任务t开始的时间
				/*这个方法安排任务t在有资源限制下，t任务开始的时间
				*基本原理是将t任务放置时在t任务所处的个时间阶段判断有无超过资源数组的限制，有的话退一格  还不满足的话  再退
				*返回的是一个资源使用的数组，任务t放置后，要更新原来的资源数组，也就是要加上任务t各阶段所使用的资源
				*/
				resourcesUsage=rcpsp.stepBackAlgorithm(t,resourcesLimit,resourcesUsage);
			}
		}

		Integer key=schedule.size();
		int fitness=totalTime-schedule.get(key).getEndtime();   
		in.setFitness(fitness);
		/*因为初始化时使用的是映射表，每个个体的映射表键值都与初始任务集合的任务相互绑定，因此根据第一个个体的映射表按顺序对取到任务进行操作
		 * 即对每个任务设定开始时间，其他个体的任务显示也会发生变化。所以按第一个个体的映射表对任务设定时间求出任务工期后，在该个体进行记录，
		 * 之后对每个任务时间进行重置，之后下一个个体按照它的映射表读取任务，设置任务时间，计算任务工期。
		 * */
		Map<Integer,Task> newSchedule=new LinkedHashMap<Integer,Task>();
		for(Task t:schedule.values()){  //将上述计算好的任务时间克隆  建新的映射表对这些克隆后的任务形成映射（注意虽然我只要克隆任务的开始时间）  
										//并将原来计算过的任务时间重置以便下一张表计算
			Task newt=t.clone();
			newSchedule.put(newt.getId(), newt);   //注意不要调用克隆任务的紧前紧后   因为关联到的还是原来最开始集合里的任务
			t.setStarttime(0);
			t.setEndtime(0);//保险一点
		}
		in.setScheduleChromosome(newSchedule);//将新的映射表赋于该个体  以便之后遍历该个体每个任务的开始时间
		return fitness;
	}
	public void evaluatePopulationFitness(Population p){  	//评估群体适应度     就是将该群体每个个体适应度相加
		double populationFitness=0;
		for(Individual in : p.getIndividuals()){
			calculateIndividualFitness(in);
		}
//		double avgFitness=populationFitness/p.size();
//		p.setPopulationFitness(avgFitness);	
	}
	public boolean isTerminationConditionMet(int generationCount,int maxGeneration){	//终止检查
		
		return (generationCount>maxGeneration);
	}
	public Individual selectParent(Population p){	//实现锦标赛选择方法    传进来的p评估排序过了  但顺序要打乱
		Population tournament=new Population(this.tournamentSize);
		p.shuffle();  //打乱p种群的顺序  让前几个位置加入竞标赛  这就起到随机进入的作用
		for(int i=0;i<this.tournamentSize;i++){
			tournament.setIndividual(i, p.getIndividual(i));  
		}
		return tournament.getFittest(0);  //在竞标赛中选取最好的一个    虽然紧前紧后关系是错的但是没事  适应度是对的
	}
	public Population crossoverPopulation(Population p){		//亲代排序交叉   传进来的p已经评估排序过了
		/*交叉采用一点交叉的方式   如 父亲：1 4 3 8 2 5 7 6 10 9 11 12
		 * 						母亲：1 4 3 5 9 7 2 8 11 6 10 12
		 * 生成一个1-11的随机数   比如4  后代就截取父亲的前四个基因   1 4 3 8 -1 -1 -1 -1 -1 -1 -1 -1 
		 * 然后从第5个基因开始  将母亲的基因从头放入  如果后代中有就不放入
		 * 结果为 1 4 3 8 5 9 7 2 11 6 10 12
		 * */
		Population newp=new Population(p.size());		//创建新的空种群
		for(int populationIndex=0;populationIndex<p.size();populationIndex++){
			Individual parent1=p.getFittest(populationIndex);
			
			if(this.crossoverRate>Math.random()&&populationIndex>=this.elitismCount){
				Individual parent2=this.selectParent(p);
				//parent2.resetTask(this.TaskSet);
				//下面2段将两个个体的任务序列由映射表赋值到数组上便于操作
				int p1[]=parent1.getScheduleSequence();
				int p2[]=parent2.getScheduleSequence();
				
				int cutIndex=(int) (Math.random()*(p1.length-1)+1);    //随机生成亲代1的位置切点 假如12个任务  那么切点的范围为1-11    
				int offspringChromosome[]=new int[parent1.getScheduleChromosome().size()];
				Arrays.fill(offspringChromosome,-1);  //将数组中所有值赋值为-1
				for(int i=0;i<cutIndex;i++){
					offspringChromosome[i]=p1[i];
				}
				int pos=0;  //每次大循环后  亲代2 开始循环的位置
				for(int j=cutIndex;j<p1.length;j++){  
					for(int m=pos;m<p2.length;m++){   //在m的数组下标位不管有没有对后代进行赋值  都让pos自加   
															//让结束后下一次进入大循环从pos这个数对应的数组位开始
						if(!test.contain(offspringChromosome, p2[m])){  
							offspringChromosome[j]=p2[m];
							pos++;
							break;
						}
						pos++;
					}
				}
				//设置新个体要设置映射序列  资源限量  还有所有任务总时间
				Map<Integer,Task> offspringscheduleChromosome=new LinkedHashMap<Integer,Task>();
				for(int i=0;i<offspringChromosome.length;i++){
					//将映射序列按照数组上的顺序映射任务个体
					offspringscheduleChromosome.put(offspringChromosome[i], this.TaskSet.get(offspringChromosome[i]-1));
				}
				Individual offspring=new Individual(offspringscheduleChromosome);
				offspring.setResourcesLimit(this.resourcesLimit);
				offspring.setTotalTime(this.totalTime);
				newp.setIndividual(populationIndex, offspring);
			}else{
				//如果不交叉或者为精英个体  就将这些个体重新放入这个新的种群  注意还不能直接放 因为个体的紧前紧后顺序都是错的  之后评估会出错
				//把重置放到计算适应度那里
				//parent1.resetTask(this.TaskSet);  //作用是重置该个体映射表所对应的任务 反正都要交叉了 只是交叉顺序  至于对应的克隆任务可以不要了
				newp.setIndividual(populationIndex, parent1);
			}
		}
		return newp;
	}
	public Population mutatePopulation(Population p){	//	变异方法  
		Population newp=new Population(p.size());
		for(int populationIndex=0;populationIndex<p.size();populationIndex++){
			Individual in=p.getFittest(populationIndex);//这里应该采用排序  因为精英个体要排在前面防止变异
				if(populationIndex>=this.elitismCount){
				
				
				}
				newp.setIndividual(populationIndex, in);
		}
		return newp;
	}
}
