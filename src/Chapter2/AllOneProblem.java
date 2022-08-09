package Chapter2;

public class AllOneProblem {
	public static void main(String[] args) {
		GeneticAlgorithm ga=new GeneticAlgorithm(100,0.01,0.95,20);
		Population p=ga.initPopulation(50);	//返回一个规模为100，且每个个体随机染色体长度为50的种群
		ga.evaluatePopulationFitness(p);
		int generation=1;
		while(ga.isTerminationConditionMet(p)==false){
			System.out.println("Best solution   "+p.getFittest(0).toString());	//得到种群中适应性最好的个体  里面那个函数就是将数组排序比较了
			//交叉
			p=ga.crossoverPopulation(p);
			//变异
			p=ga.mutatePopulation(p);
			ga.evaluatePopulationFitness(p);
			generation++;
		}
		System.out.println("在第"+generation+"代找到优解");
		System.out.println("优解为"+p.getFittest(0).toString());
	}

}
