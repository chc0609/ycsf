package Chapter2;

import java.util.Comparator;

public class MyComparator implements Comparator<Individual>{   //�Ƚ���    ��������  a��b��a��ǰ��   
	public int compare(Individual a,Individual b){     //ע���Ժ�Ҫ������ȵ����  ��Ȼÿ������������һ��   ����ȡ����ʱ���ܻ�ȡ��һ���ĸ���
		if(a.getFitness()>b.getFitness()){
			return -1; 
		}else if(a.getFitness()<b.getFitness()){		//Ҫ�������а�1 -1����
			return 1;         
		}else{
			return 0;
		}
	}
}
