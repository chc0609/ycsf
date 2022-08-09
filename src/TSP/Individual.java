package TSP;

public class Individual {       	//这里的个体染色体就是存储着城市顺序
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
	public Individual(int chromosomeLength){       		//构造方法2   按012345...99排列表示城市顺序
		int in[]=new int[chromosomeLength];					
		for(int gene=0;gene<chromosomeLength;gene++){
			in[gene]=gene;
		}
		this.chromosome=in;
	}
	public boolean containGene(int gene){		//判断有无重复基因
		for(int i=0;i<this.chromosome.length;i++){
			if(this.chromosome[i]==gene){
				return true;
			}
		}
		return false;
	}
	public String toString(){		//重写个体输出的方法
		String output="";
		for(int geneLocation=0;geneLocation<this.chromosome.length;geneLocation++){
			output +=this.chromosome[geneLocation]+"-";
		}
		return output+this.chromosome[0];
	}
}
