package Chapter2;

public class Individual {       	//代表一个候选解，该个体负责存储和操作一条染色体
	private int chromosome[];    	//染色体存基因的数组  每个位置存基因
	private double fitness=-1;		//适应度
	public int[] getChromosome(){
		return this.chromosome;    
	}
	public int getChromosomeLength(){
		return this.chromosome.length;
	}
	public void setGene(int location,int geneValue){
		this.chromosome[location]=geneValue;      //把基因放到染色体上    offset表示位置     gene是1或0
	}
	public int getGene(int location){
		return this.chromosome[location];		//得到某个位置的基因
	}
	public void setFitness(double fitness){
		this.fitness=fitness;
	}
	public double getFitness(){
		return this.fitness;
	}
	public Individual(int chrosome[]){					//构造方法1    接受整数数组用其作为染色体
		this.chromosome=chrosome;              
	}
	public Individual(int chromosomeLength){       		//构造方法2   接受一个长度创造一个随机染色体
		this.chromosome=new int[chromosomeLength];
		for(int geneLocation=0;geneLocation<chromosomeLength;geneLocation++){
			if(Math.random()>=0.5){
				this.setGene(geneLocation,1);
			}else{
				this.setGene(geneLocation,0);
			}
		}
	}
	public String toString(){		//重写个体输出的方法
		String output="";
		int a=0;
		for(int geneLocation=0;geneLocation<this.chromosome.length;geneLocation++){
			if(this.chromosome[geneLocation]==1){
				a++;
			}
			output +=this.chromosome[geneLocation];
		}
		return output+"   "+a+"个1";
	}
}
