package MyRcpsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class test {
	public static void main(String []args){
		Task t1=new Task(1,0,new int[]{0,0});
		Task t2=new Task(2,3,new int[]{2,0});
		Task t3=new Task(3,4,new int[]{1,0});
		Task t4=new Task(4,2,new int[]{1,0});
		Task t5=new Task(5,6,new int[]{0,2});
		Task t6=new Task(6,3,new int[]{0,3});
		Task t7=new Task(7,2,new int[]{0,2});
		Task t8=new Task(8,3,new int[]{2,0});
		Task t9=new Task(9,4,new int[]{0,1});
		Task t10=new Task(10,3,new int[]{3,0});
		Task t11=new Task(11,2,new int[]{0,2});
		Task t12=new Task(12,0,new int[]{0,0});  //��ʼ��
		//Ϊÿ���к�̽������Ӻ�̼���
		t1.addSuccessor(t2).addSuccessor(t3).addSuccessor(t4).addSuccessor(t5);
		t2.addSuccessor(t6);
		t3.addSuccessor(t7);
		t4.addSuccessor(t8);
		t5.addSuccessor(t9);
		t6.addSuccessor(t10);
		t7.addSuccessor(t10);
		t8.addSuccessor(t11);
		t9.addSuccessor(t11);
		t10.addSuccessor(t12);
		t11.addSuccessor(t12);
		List<Task> TaskSet=new ArrayList<Task>();
		TaskSet.add(t1);TaskSet.add(t2);TaskSet.add(t3);TaskSet.add(t4);TaskSet.add(t5);TaskSet.add(t6);
		TaskSet.add(t7);TaskSet.add(t8);TaskSet.add(t9);TaskSet.add(t10);TaskSet.add(t11);TaskSet.add(t12);
//		int a=1;
//		test t =new test();
//		t.getA(++a);   ++��ǰ�������Լ����ڰѲ�������ȥ
/*�������һ�㽻��ķ�ʽ   �� ���ף�1 4 3 8 2 5 7 6 10 9 11 12
 * 						ĸ�ף�1 4 3 5 9 7 2 8 11 6 10 12
 * ����һ��1-11�������   ����4  ����ͽ�ȡ���׵�ǰ�ĸ�����   1 4 3 8 -1 -1 -1 -1 -1 -1 -1 -1 
 * Ȼ��ӵ�5������ʼ  ��ĸ�׵Ļ����ͷ����  ���������оͲ�����
 * ���Ϊ 1 4 3 8 5 9 7 2 11 6 10 12
 * */
		int[] a1=new int[]{1,4,3,8,2,5,7,6,10,9,11,12};
		int[] a2=new int[]{1,4,3,5,9,7,2,8,11,6,10,12};
		int cutIndex=(int) (Math.random()*(a1.length-1)+1);    //��Χ 1-11
		//int substrPos1=4;    //��Χ0-a1.length-1
		System.out.println(cutIndex);
		int [] offSpringChromosome=new int[a1.length];
		Arrays.fill(offSpringChromosome,-1);  //������������ֵ��ֵΪ-1
		for(int i=0;i<cutIndex;i++){
			offSpringChromosome[i]=a1[i];
		}
		int pos2=0;
		for(int j=cutIndex;j<a1.length;j++){  
			for(int m=pos2;m<a2.length;m++){   //��m�������±�λ������û�жԺ�����и�ֵ  ����pos2�Լ�   
													//�ý�������һ�ν����ѭ����pos2�������Ӧ������λ��ʼ
				if(!test.contain(offSpringChromosome, a2[m])){  
					offSpringChromosome[j]=a2[m];
					pos2++;
					break;
				}
				pos2++;
			}
		}
		for(int i=0;i<offSpringChromosome.length;i++){
			System.out.print(offSpringChromosome[i]+" ");
		}
		System.out.println();
		Map<Integer,Task> offspringscheduleChromosome=new LinkedHashMap<Integer,Task>();
		for(int i=0;i<offSpringChromosome.length;i++){
			offspringscheduleChromosome.put(offSpringChromosome[i], TaskSet.get(offSpringChromosome[i]-1));
		}
		Iterator<Integer> iterator2 = offspringscheduleChromosome.keySet().iterator();  
        while (iterator2.hasNext()) {  
            Integer key = iterator2.next();  
            Task value = offspringscheduleChromosome.get(key);  
            System.out.print(value+"�Ŀ�ʼʱ��Ϊ"+value.getStarttime());  
            System.out.println();  
        } 
        Integer key = offspringscheduleChromosome.size();  
        System.out.println("��������Ϊ"+offspringscheduleChromosome.get(key).getEndtime());
	}
	public static boolean contain(int []a,int b){
		for(int i=0;i<a.length;i++){
			if(a[i]==b){
				return true;
			}
		}
		return false;
	}
	public void getA(int a){
		System.out.println(a);
	}
}
