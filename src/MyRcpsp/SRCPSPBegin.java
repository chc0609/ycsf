package MyRcpsp;

import java.util.ArrayList;
import java.util.List;


public class SRCPSPBegin { 			//还有个变异算法下次再写
	public static int maxGenerations = 20;		//最大迭代次数
    private static int[] resourcesLimit;
	public static void main(String[] args){
//		resourcesLimit=new int[]{30,32,34,29};
//		List<Task> TaskSet=new projectNum().initTaskSet32();

		List<Task> TaskSet=new projectNum().initTaskSet6();
		resourcesLimit=new int[]{3,4};
		//initial GA初始化GA
		GeneticAlgorithm ga=new GeneticAlgorithm(5,0.01,0.5,1,2,resourcesLimit);    //3 0.01 0.95 0 1
		                                                                        // 40 0.01 0.85 0 5
		Population p=ga.initPopulation(TaskSet);
		//评估种群
		ga.evaluatePopulationFitness(p);
		int generation=1;
		while(ga.isTerminationConditionMet(generation,maxGenerations)==false){
			//将该代种群中适应度最高的个体放入路径，再打印出该路径的距离
			Individual in=p.getFittest(0);
			System.out.println("第"+generation+"代最优解:");
			in.printScheduleChromosome();
			System.out.println();
			//交叉
			p=ga.crossoverPopulation(p);
//			//变异
//			p=ga.mutatePopulation(p);
			//评估
			ga.evaluatePopulationFitness(p);
			generation++;
		}
		//打印结果	
		/*		Individual in=p.getFittest(0);
		 * Individual in2=ga.selectParent(p);
		System.out.println(in.getFitness());
		//System.out.println(in2.getFitness());
		//System.out.println(in2.getFitness());
		in.printScheduleChromosome();//打印出该个体的开始时间
		//in2.printScheduleChromosome();//打印出该个体的开始时间
		System.out.println();
		in2.printScheduleChromosome();*/
	}
}
