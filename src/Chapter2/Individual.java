package Chapter2;

public class Individual {       	//����һ����ѡ�⣬�ø��帺��洢�Ͳ���һ��Ⱦɫ��
	private int chromosome[];    	//Ⱦɫ�����������  ÿ��λ�ô����
	private double fitness=-1;		//��Ӧ��
	public int[] getChromosome(){
		return this.chromosome;    
	}
	public int getChromosomeLength(){
		return this.chromosome.length;
	}
	public void setGene(int location,int geneValue){
		this.chromosome[location]=geneValue;      //�ѻ���ŵ�Ⱦɫ����    offset��ʾλ��     gene��1��0
	}
	public int getGene(int location){
		return this.chromosome[location];		//�õ�ĳ��λ�õĻ���
	}
	public void setFitness(double fitness){
		this.fitness=fitness;
	}
	public double getFitness(){
		return this.fitness;
	}
	public Individual(int chrosome[]){					//���췽��1    ������������������ΪȾɫ��
		this.chromosome=chrosome;              
	}
	public Individual(int chromosomeLength){       		//���췽��2   ����һ�����ȴ���һ�����Ⱦɫ��
		this.chromosome=new int[chromosomeLength];
		for(int geneLocation=0;geneLocation<chromosomeLength;geneLocation++){
			if(Math.random()>=0.5){
				this.setGene(geneLocation,1);
			}else{
				this.setGene(geneLocation,0);
			}
		}
	}
	public String toString(){		//��д��������ķ���
		String output="";
		int a=0;
		for(int geneLocation=0;geneLocation<this.chromosome.length;geneLocation++){
			if(this.chromosome[geneLocation]==1){
				a++;
			}
			output +=this.chromosome[geneLocation];
		}
		return output+"   "+a+"��1";
	}
}
