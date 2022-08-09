package MyRcpsp;

import java.util.List;

public class rcpsp {  //这个类用来判断资源限量
		/*这个方法用来判断任务各个阶段加入资源占用函数符不符合资源限制，若有一阶段不符合直接结束返回false
		 * */
	public static boolean IsFeasible(Task t,int resourcesLimit[],int resourcesUsage[][]){
			for(int i=t.getStarttime();i<t.getEndtime();i++){
				for(int j=0;j<resourcesLimit.length;j++){  //这个循环先判断当前时间阶段的资源符不符合资源约束
					if(resourcesUsage[i][j]+t.getResourceDemands()[j]>resourcesLimit[j]){
						return false;
					}
				}
				}
			return true;
		}
	
		/*
		 * 下面的方法是一个自己调用自己的递归算法，直到一个任务后退到能满足各个阶段的资源需求
		 * 首先利用IsFeasible方法判断任务各个阶段加入资源使用函数符不符合资源限制，若符合将任务t各阶段使用的资源加入
		 * 资源占用函数，然后返回最新的资源占用函数。如果任务t的开始时间之后的各阶段不满足资源限制，就将开始时间往后退一格
		 * 然后再次调用自己的这个方法   直到任务往后退到可行返回最新的资源占用函数
		 * */
	public static int[][] stepBackAlgorithm(Task t,int resourcesLimit[],int resourcesUsage[][]){  //退格算法递归
			if(rcpsp.IsFeasible(t, resourcesLimit, resourcesUsage)){	 //若不受限制则把资源加到资源占用函数resourcesUsage里
				for(int i=t.getStarttime();i<t.getEndtime();i++){
						for(int j=0;j<resourcesLimit.length;j++){ 
						resourcesUsage[i][j]+=t.getResourceDemands()[j];
						}
				}
				return resourcesUsage;      //返回任务设置之后资源的最新情况
			}else{    //将开始时间往后退一格  再重新调用自己  看看退一格后各阶段资源能否满足限制  不满足就再退
					int steptime=t.getStarttime()+1;    //退后一个的时刻
					t.setStarttime(steptime);
					return rcpsp.stepBackAlgorithm(t, resourcesLimit, resourcesUsage);  
				}
		}
		public static void printTaskSetInfo(List<Task> TaskSet){  //打印每个任务的紧前与紧后结点
			for(Task t:TaskSet){
				System.out.print("任务"+t.getId());
				
				if(!t.getPredecesorTaskSets().isEmpty()){  //输出紧前集合
				System.out.print("   紧前集合:");
				List<Task> preSet=t.getPredecesorTaskSets();
				for(Task pret:preSet){
					System.out.print(pret.getId()+"  ");
				}
				}else{
					System.out.print("   紧前集合:无  ");
				}
				
				if(!t.getSuccessorTaskSets().isEmpty()){   //输出紧后集合
				System.out.print("  紧后集合:");
				List<Task> succSet=t.getSuccessorTaskSets();
				for(Task succt:succSet){
					System.out.print(succt.getId()+"  ");
				}
				System.out.println();
				}else{
					System.out.print("   紧后集合:无");
				}
				System.out.println();
			}
		}
}
