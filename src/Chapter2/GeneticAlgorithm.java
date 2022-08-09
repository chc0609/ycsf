package Chapter2;

public class GeneticAlgorithm {
	private int populationSize;		//��Ⱥ��ģ
	private double mutationRate;	//������
	private double crossoverRate;	//������
	private int elitismCount;		//��Ӣ��Ա��
	public GeneticAlgorithm(int populationSize,double mutationRate,double crossover,int elitismCount){
		this.populationSize=populationSize;
		this.mutationRate=mutationRate;
		this.crossoverRate=crossover;                   //���췽��
		this.elitismCount=elitismCount;
	}
	public Population initPopulation(int chromosomeLength){		//��ʼ����Ⱥ  ����һ����Ⱥ���趨�����������һ���趨����(����)�����Ⱦɫ��  
		Population p=new Population(this.populationSize,chromosomeLength);
		return p;
	}
	public double calculateIndividualFitness(Individual in){			//��Ӧ�Ⱥ���    ����������Ӧ��
		int correctGenes=0;
		for(int geneLocation=0;geneLocation<in.getChromosomeLength();geneLocation++){
			if(in.getGene(geneLocation)==1){
				correctGenes++;				//ѭ��һ�����Ļ���  ������Ϊ1�ĸ���ͳ�Ƴ���
			}
		}
		double fitness=(double)correctGenes/in.getChromosomeLength();
		in.setFitness(fitness);
		return fitness;
	}
	public void evaluatePopulationFitness(Population p){  	//����Ⱥ����Ӧ��     ���ǽ���Ⱥ��ÿ��������Ӧ�����
		double populationfitness=0;
		for(Individual in : p.getIndividuals()){
			populationfitness +=calculateIndividualFitness(in);
		}
		p.setPopulationFitness(populationfitness);
	}
	public boolean isTerminationConditionMet(Population p){			//��ֹ���
		for(Individual in : p.getIndividuals()){
			if(in.getFitness()==1){					//�������и�����Ӧ��Ϊ1�������     1�ĸ���/Ⱦɫ���ܳ���=1���		
				return true;						//�����Ⱥ���κθ�����Ӧ��Ϊ1  �Ϳ���ֹͣ
			}
		}
		return false;
	}
	public Individual selectParent(Population p){	//ʵ�����̶�ѡ�񷽷�
		Individual individuals[]=p.getIndividuals();
		double populationFitness=p.getPopulationFitness();
		double rouletteWheelPosition=Math.random()*populationFitness;	//��������ָ��
		double spinWheel=0;
		for(Individual in : individuals){
			spinWheel +=in.getFitness();		//ǰ��ĸ�����Ӧ���ۼӸպó������ָ��
			if(spinWheel>=rouletteWheelPosition){	
				return in;
			}
		}
		return null;   //����Ҫ��  ��Ϊ�����������ж��Ƿ��ܽ�forѭ��  ��Ȼfor����ľ���һ���ܷ���
	}
	public Population crossoverPopulation(Population p){		//���淽��   ��������p�Ѿ������������
		Population newp=new Population(p.size());	//������ͬ��������Ⱥ
		for(int populationLocation=0;populationLocation<p.size();populationLocation++){
			Individual parent1=p.getFittest(populationLocation);
			if(this.crossoverRate>Math.random() && populationLocation>=this.elitismCount){	//ǰ���Ǹ����������ǿ��ƽ�������95%
				Individual offspring =new Individual(parent1.getChromosomeLength());	//��ʼ���Ӵ�
				Individual parent2=selectParent(p);	//�ҵ��ڶ����״�
				//Individual parent2=p.getFittest(0);  //����̶�  ���൱��ȱ���˽���   ������������Ⱥ����һ��
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
			Individual in=p.getFittest(populationLocation);   //  �����������-1 ���ù��� ֻ��ȡ�����������
			//System.out.println(in.getFitness());
			if(populationLocation>=this.elitismCount){	//�жϸø����費��Ҫ����
			for(int geneLocation=0;geneLocation<in.getChromosomeLength();geneLocation++){  //��ʼ����
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
