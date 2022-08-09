package TSP;

import java.util.Arrays;
import java.util.Random;

public class GeneticAlgorithm {
	private int populationSize;		//��Ⱥ��ģ
	private double mutationRate;	//������
	private double crossoverRate;	//������
	private int elitismCount;		//��Ӣ��Ա��
	protected int tournamentSize;	//��������ģ
	public GeneticAlgorithm(int populationSize,double mutationRate,double crossover,int elitismCount,int tournamentSize){
		this.populationSize=populationSize;
		this.mutationRate=mutationRate;
		this.crossoverRate=crossover;                   //���췽��
		this.elitismCount=elitismCount;
		this.tournamentSize=tournamentSize;
	}
	public Population initPopulation(int chromosomeLength){		//��ʼ����Ⱥ  ����һ����Ⱥ���趨�����������һ���趨����(����)�����Ⱦɫ��  
		Population p=new Population(this.populationSize,chromosomeLength);
		return p;
	}
	public double calculateIndividualFitness(Individual in,City cities[]){			//��Ӧ�Ⱥ���    ����������Ӧ��
		//���ݴ�������Ⱦɫ��˳��  �ͳ���������������Ӧ��
		Route route=new Route(in,cities);
		double fitness=1/route.getDistance();  
		/*��1�������ܾ���  ʹ����̾���ĸ���ӵ�иߵ���Ӧ��
		 * 
		 * */
		in.setFitness(fitness);
		return fitness;
	}
	public void evaluatePopulationFitness(Population p,City cities[]){  	//����Ⱥ����Ӧ��     ���ǽ���Ⱥ��ÿ��������Ӧ�����
			double populationFitness=0;
			for(Individual in : p.getIndividuals()){
				populationFitness +=calculateIndividualFitness(in,cities);
			}
			double avgFitness=populationFitness/p.size();
			p.setPopulationFitness(avgFitness);					//Ŀǰ������  ������ѡ�񷽷�
	}
	public boolean isTerminationConditionMet(int generationCount,int maxGeneration){	//��ֹ���
		
		return (generationCount>maxGeneration);
	}
	public Individual selectParent(Population p){	//ʵ�ֽ�����ѡ�񷽷�    ��������p�����������  ��˳��Ҫ����
		Population tournament=new Population(this.tournamentSize);
		p.shuffle();
		for(int i=0;i<this.tournamentSize;i++){
			tournament.setIndividual(i, p.getIndividual(i));
		}
		return tournament.getFittest(0);
	}
	public Population crossoverPopulation(Population p){		//�״����򽻲淽��   ��������p�Ѿ������������
		/*�����״�1�ϵĻ���    1 2 5 3 6 4  ����Ϊ6
		 * 	�״�2�ϵĻ���       5 1 4 3 6 2 ����Ϊ6
		 * ���򽻲淽��     ����õ�0-6��������6������������ �� 2 4   
		 *        ���״���2��ʼ��4������������4���Ļ���������ͬλ������
		 *                 _ _ 5 3 _ _
		 *        ����ѭ���״�2  ��4���λ�ÿ�ʼ��ͷѭ��  ��������û���������Ͱ������������
		 * 				   6 2 5 3 1 4
		 * */
		Population newp=new Population(p.size());
		for(int populationIndex=0;populationIndex<p.size();populationIndex++){
			Individual parent1=p.getIndividual(populationIndex);
			//�ж��Ƿ񽻲�
			if(this.crossoverRate>Math.random()&&populationIndex>=this.elitismCount){
				//�ý������ķ�ʽ�ҵڶ����״�
				Individual parent2=this.selectParent(p);
				//�����հ׺��Ⱦɫ��	
				int offspringChromosome[]=new int[parent1.getChromosomeLength()];
				Arrays.fill(offspringChromosome,-1);  //������������ֵ��ֵΪ-1
				Individual offspring=new Individual(offspringChromosome);
				//�õ��״�1���Ӽ�  ����2�����ж�
				Random rnd=new Random();
				int substrPos1=rnd.nextInt(parent1.getChromosomeLength());
				int substrPos2=rnd.nextInt(parent1.getChromosomeLength());
				//�ж��ĸ����ĸ�С   ��һ����ô��?????   һ���Ļ��״�1����ȥ�� ֻҪ�״�2�Ļ���Ӷϵ㿪ʼ����
				int startSubstr=Math.min(substrPos1, substrPos2);
				int endSubstr=Math.max(substrPos1, substrPos2);
				for(int i=startSubstr;i<endSubstr;i++){   //����2���жϵ� ���״�1����λ�������
					offspring.setGene(i,parent1.getGene(i));
				}
				//�����״�1�Ķϵ㿪ʼѭ���״�2  ������û�иû���ͰѸû�������
				for(int i=0;i<parent2.getChromosomeLength();i++){
					int parent2Gene=i+endSubstr;  //�״�ѭ����ָ��λ
					if(parent2Gene>=parent2.getChromosomeLength()){
						//��������״�2Ⱦɫ��ĳ���  �ͼ�ȥһ������  ��ͷ��ʼѭ��   �
						parent2Gene=parent2Gene-parent2.getChromosomeLength();
					}
					if(offspring.containGene(parent2.getGene(parent2Gene))==false){  //��������������λ�õĻ���  �ʹ��浽����д�ͷ��ʼֱ������-1��λ��
						//ѭ��ȥ�ҵ�����пյ�λ�ã�Ҳ���ǵ���-1��
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
	public Population mutatePopulation(Population p){	//	���û�λ����  ��Ȼ���ܻᵼ���ظ�
				/*����������Ⱥp ǰ�澫Ӣ������Ӧ�ȱ���ԭ������  ������һЩû�н��н���ĸ���  
				 *  ʣ�µĶ����Ӵ�  ��Ӧ�ȶ�Ϊ-1
				 * 
				 * */
		Population newp=new Population(p.size());
		for(int populationIndex=0;populationIndex<p.size();populationIndex++){
			Individual in=p.getFittest(populationIndex);//����Ӧ�ò�������  ��Ϊ��Ӣ����Ҫ����ǰ���ֹ����
			if(populationIndex>=this.elitismCount){
				for(int geneIndex=0;geneIndex<in.getChromosomeLength();geneIndex++){  //ÿλ�����жϽ��б���
					if(this.mutationRate>Math.random()){
						int newGenePos=(int)(Math.random()*in.getChromosomeLength());//������������λ�������� Ϊʲô����֮ǰ��    ��Ϊ���������ʽҪ����������ѭ������λ�û����п��ܸ�֮ǰ��λ�ý�����
						int oldGene=in.getGene(geneIndex);  //ȡ��ԭ��λ�õĻ���
						in.setGene(geneIndex, in.getGene(newGenePos));  //��ԭ��λ��������λ�õĻ���
						in.setGene(newGenePos, oldGene);	//�ٽ��ո�ȡ�������ϻ������õ��µ�λ��  ��λ����ɹ�
					}
				}
			}
				newp.setIndividual(populationIndex, in);
		}
		return newp;
	}
}
