package MyRcpsp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SRCPSPMain {
    private static int totalTime ;
    private static int[] resourcesLimit;
	public static void main(String[] args) {
		//Task(int id,int duration,int []resourceDemands)
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
		Task t12=new Task(12,0,new int[]{0,0});  //初始化
		//为每个有后继结点的增加后继集合
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
		resourcesLimit=new int[]{3,4};
		List<Task> TaskSet=new ArrayList<Task>();
		TaskSet.add(t1);TaskSet.add(t2);TaskSet.add(t3);TaskSet.add(t4);TaskSet.add(t5);TaskSet.add(t6);
		TaskSet.add(t7);TaskSet.add(t8);TaskSet.add(t9);TaskSet.add(t10);TaskSet.add(t11);TaskSet.add(t12);
		
		for(Task t:TaskSet){
			totalTime  +=t.getDuration();
		}
		//以上数据初始化
		rcpsp.printTaskSetInfo(TaskSet);
		
		Map<Integer,Task> schedule=new LinkedHashMap<>();
//		schedule.put(1, t1);
//		while(schedule.size()!=TaskSet.size()){     //产生可行的随机序列的方法
//			int index=(int) (Math.random()*12+1);  //index范围在1-12
//			if(!schedule.containsKey(index)){
//				Task t=TaskSet.get(index-1);
//				boolean add=true;
//				for(Task pre:t.getPredecesorTaskSets()){
//					if(!schedule.containsKey(pre.getId())){  //调度计划中如果不包含紧前结点  设置add=false
//						add=false;
//						break;   //如果有一个不包含马上跳出
//					}
//				}
//				if(add){  //如果任务t的紧前结点全在了
//					schedule.put(t.getId(), t);
//				}
//			}
//		}
		//测试数据1
//		schedule.put(1, t1);schedule.put(3, t3);schedule.put(4, t4);schedule.put(8, t8);
//		schedule.put(7, t7);schedule.put(2, t2);schedule.put(6, t6);schedule.put(5, t5);
//		schedule.put(10, t10);schedule.put(9, t9);schedule.put(11, t11);schedule.put(12, t12);
		//测试数据2
		schedule.put(1, t1);schedule.put(5, t5);schedule.put(2, t2);schedule.put(9, t9);
	    schedule.put(4, t4);schedule.put(6, t6);schedule.put(3, t3);schedule.put(8, t8);
		schedule.put(7, t7);schedule.put(11, t11);schedule.put(10, t10);schedule.put(12, t12);

		//以下代码  解析成网络图   根据资源分配每个任务
		int [][]resourcesUsage=new int[totalTime][2];
		for(Task t:schedule.values()){
			int time=0;
			if(t.getPredecesorTaskSets().isEmpty()){
				t.setStarttime(0);  //初始结点1
			}else{
				for(Task pret : t.getPredecesorTaskSets()){
					if(pret.getEndtime()>time){
						time=pret.getEndtime();
					}
				}
				t.setStarttime(time);//这个时间是没有资源限制下任务t开始的时间
				/*这个方法安排任务t在有资源限制下，t任务开始的时间
				*基本原理是将t任务放置时在t任务所处的个时间阶段判断有无超过资源数组的限制，有的话退一格  还不满足的话  再退
				*返回的是一个资源使用的数组，任务t放置后，要更新原来的资源数组，也就是要加上任务t各阶段所使用的资源
				*/
				resourcesUsage=rcpsp.stepBackAlgorithm(t, resourcesLimit, resourcesUsage);
			}
		}
		Iterator<Integer> iterator2 = schedule.keySet().iterator();  
        while (iterator2.hasNext()) {  
            Integer key = iterator2.next();  
            Task value = schedule.get(key);  
            System.out.print(value+"的开始时间为"+value.getStarttime());  
            System.out.println();  
        } 
        System.out.println();
        Integer key = schedule.size();  
        System.out.println("总任务工期为"+schedule.get(key).getEndtime());
	}
	
}
