package TSP;

public class Individual {       	//����ĸ���Ⱦɫ����Ǵ洢�ų���˳��
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
	public Individual(int chromosomeLength){       		//���췽��2   ��012345...99���б�ʾ����˳��
		int in[]=new int[chromosomeLength];					
		for(int gene=0;gene<chromosomeLength;gene++){
			in[gene]=gene;
		}
		this.chromosome=in;
	}
	public boolean containGene(int gene){		//�ж������ظ�����
		for(int i=0;i<this.chromosome.length;i++){
			if(this.chromosome[i]==gene){
				return true;
			}
		}
		return false;
	}
	public String toString(){		//��д��������ķ���
		String output="";
		for(int geneLocation=0;geneLocation<this.chromosome.length;geneLocation++){
			output +=this.chromosome[geneLocation]+"-";
		}
		return output+this.chromosome[0];
	}
}
