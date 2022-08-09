package TSP;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
public class Population {
	private Individual population[];     //����ÿ��λ���ϴ�ĸ���  ����������Ⱥ
	private double populationFitness=-1;
	public Individual[] getIndividuals(){
		return this.population;				//������Ⱥ����
	}
	public void setPopulationFitness(double fitness){
		this.populationFitness=fitness;			//������Ⱥ��Ӧ��
	}
	public double getPopulationFitness(){
		return this.populationFitness;			//������Ⱥ��Ӧ��
	}
	public int size(){
		return this.population.length;			//������Ⱥ��ģ
	}
	public Individual setIndividual(int location,Individual in){
		return population[location]=in;			//��Ⱥ������Ӧλ�����ø���  ������
	}
	public Individual getIndividual(int location){    //�����������淽����ͬ���Ƿ�����Ⱥ��һ������
		return this.population[location];         
	}
	public Population(int populationSize){
		this.population=new Individual[populationSize]; 	//���췽��  ������Ⱥ��ģ
	}
	public Population(int populationSize,int chromosomeLength){			//��������ΪpopulationSize����Ⱥ
		this.population=new Individual[populationSize];					//�Ҹ���Ⱥ����ÿ�����Ⱦɫ�峤��ΪchromosomeLength
		for(int individualLocation=0;individualLocation<populationSize;individualLocation++){
			Individual in=new Individual(chromosomeLength);
			population[individualLocation]=in;					
		}
	}
	public Individual getFittest(int location){
		MyComparator mc=new MyComparator();    //�����Ƚ���  ���߱Ƚ�����������������αȴ�С
		Arrays.sort(this.population, mc);      //����ÿ��������Ӧ�Ƚ�������
		return this.population[location];		//�õ�ĳ��λ�õĸ���
	}
	public void shuffle(){		//ϴ��  ������Ⱥ�еĸ�������˳��
		Random rnd =new Random();
		for(int i=population.length-1;i>0;i--){
			int index=rnd.nextInt(i+1); //�÷���������������һ�������intֵ����ֵ����[0,n)�����䣬
										//		Ҳ����0��n֮������intֵ������0��������n
			Individual a=population[index];			//��3���� ������Ⱥ���鳤��Ϊ6    
			population[index]=population[i];		//ѭ����5 4 3 2 1 0 ÿ��λ��ʱ  �������һ��λ�ý��н�������
			population[i]=a;					//������i=5���λ�� �ͻ��� 0 1 2 3 4 5��ѡһ����λ��  ��i=4���λ��  �ͻ���ʣ��0 1 2 3 4ѡ 
		}
	}
}
