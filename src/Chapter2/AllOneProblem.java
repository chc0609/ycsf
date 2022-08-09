package Chapter2;

public class AllOneProblem {
	public static void main(String[] args) {
		GeneticAlgorithm ga=new GeneticAlgorithm(100,0.01,0.95,20);
		Population p=ga.initPopulation(50);	//����һ����ģΪ100����ÿ���������Ⱦɫ�峤��Ϊ50����Ⱥ
		ga.evaluatePopulationFitness(p);
		int generation=1;
		while(ga.isTerminationConditionMet(p)==false){
			System.out.println("Best solution   "+p.getFittest(0).toString());	//�õ���Ⱥ����Ӧ����õĸ���  �����Ǹ��������ǽ���������Ƚ���
			//����
			p=ga.crossoverPopulation(p);
			//����
			p=ga.mutatePopulation(p);
			ga.evaluatePopulationFitness(p);
			generation++;
		}
		System.out.println("�ڵ�"+generation+"���ҵ��Ž�");
		System.out.println("�Ž�Ϊ"+p.getFittest(0).toString());
	}

}
