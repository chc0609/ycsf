package MyRcpsp;

import java.util.ArrayList;
import java.util.List;


public class SRCPSPBegin { 			//���и������㷨�´���д
	public static int maxGenerations = 20;		//����������
    private static int[] resourcesLimit;
	public static void main(String[] args){
//		resourcesLimit=new int[]{30,32,34,29};
//		List<Task> TaskSet=new projectNum().initTaskSet32();

		List<Task> TaskSet=new projectNum().initTaskSet6();
		resourcesLimit=new int[]{3,4};
		//initial GA��ʼ��GA
		GeneticAlgorithm ga=new GeneticAlgorithm(5,0.01,0.5,1,2,resourcesLimit);    //3 0.01 0.95 0 1
		                                                                        // 40 0.01 0.85 0 5
		Population p=ga.initPopulation(TaskSet);
		//������Ⱥ
		ga.evaluatePopulationFitness(p);
		int generation=1;
		while(ga.isTerminationConditionMet(generation,maxGenerations)==false){
			//���ô���Ⱥ����Ӧ����ߵĸ������·�����ٴ�ӡ����·���ľ���
			Individual in=p.getFittest(0);
			System.out.println("��"+generation+"�����Ž�:");
			in.printScheduleChromosome();
			System.out.println();
			//����
			p=ga.crossoverPopulation(p);
//			//����
//			p=ga.mutatePopulation(p);
			//����
			ga.evaluatePopulationFitness(p);
			generation++;
		}
		//��ӡ���	
		/*		Individual in=p.getFittest(0);
		 * Individual in2=ga.selectParent(p);
		System.out.println(in.getFitness());
		//System.out.println(in2.getFitness());
		//System.out.println(in2.getFitness());
		in.printScheduleChromosome();//��ӡ���ø���Ŀ�ʼʱ��
		//in2.printScheduleChromosome();//��ӡ���ø���Ŀ�ʼʱ��
		System.out.println();
		in2.printScheduleChromosome();*/
	}
}
