package Chapter2;

import java.util.Arrays;
import java.util.Comparator;

public class TestComparator implements Comparator<Individual>{
	public int compare(Individual a,Individual b){
		if(a.getFitness()>b.getFitness()){
			return -1; 
		}else if(a.getFitness()<b.getFitness()){		//要升序排列把1 -1换下
			return 1;         
		}else{
			return 0;
		}
	}
	
	public static void main(String[] args) {
			Individual in[]=new Individual[5];
			int a1[]={0,0,0,1,1};
			int a2[]={1,0,0,1,1};
			int a3[]={0,1,1,1,1};
			int a4[]={0,0,0,0,0};
			int a5[]={1,1,1,1,1};
			in[0]=new Individual(a1);
			in[1]=new Individual(a2);
			in[2]=new Individual(a3);
			in[3]=new Individual(a4);
			in[4]=new Individual(a5);
			in[0].setFitness(2);
			in[1].setFitness(3);
			in[2].setFitness(4);
			in[3].setFitness(0);
			in[4].setFitness(5);
			System.out.println(in[0]);
			Arrays.sort(in,new TestComparator());
			String output="";
			for(int i=0;i<in.length;i++){
				System.out.println(in[i]);
			}
			
	}

}
