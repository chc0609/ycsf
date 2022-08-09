package TSP;

import java.util.Comparator;

public class MyComparator implements Comparator<Individual>{   //比较器    降序排列  a比b大a排前面   
	public int compare(Individual a,Individual b){
		if(a.getFitness()>b.getFitness()){
			return -1; 
		}else if(a.getFitness()<b.getFitness()){		//要升序排列把1 -1换下
			return 1;         
		}else{
			return 0;
		}
	}
}
