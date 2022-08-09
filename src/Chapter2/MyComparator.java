package Chapter2;

import java.util.Comparator;

public class MyComparator implements Comparator<Individual>{   //比较器    降序排列  a比b大a排前面   
	public int compare(Individual a,Individual b){     //注意以后要考虑相等的情况  不然每次排序结果都不一样   交叉取个体时可能会取到一样的个体
		if(a.getFitness()>b.getFitness()){
			return -1; 
		}else if(a.getFitness()<b.getFitness()){		//要升序排列把1 -1换下
			return 1;         
		}else{
			return 0;
		}
	}
}
