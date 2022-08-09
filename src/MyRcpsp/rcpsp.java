package MyRcpsp;

import java.util.List;

public class rcpsp {  //����������ж���Դ����
		/*������������ж���������׶μ�����Դռ�ú�������������Դ���ƣ�����һ�׶β�����ֱ�ӽ�������false
		 * */
	public static boolean IsFeasible(Task t,int resourcesLimit[],int resourcesUsage[][]){
			for(int i=t.getStarttime();i<t.getEndtime();i++){
				for(int j=0;j<resourcesLimit.length;j++){  //���ѭ�����жϵ�ǰʱ��׶ε���Դ����������ԴԼ��
					if(resourcesUsage[i][j]+t.getResourceDemands()[j]>resourcesLimit[j]){
						return false;
					}
				}
				}
			return true;
		}
	
		/*
		 * ����ķ�����һ���Լ������Լ��ĵݹ��㷨��ֱ��һ��������˵�����������׶ε���Դ����
		 * ��������IsFeasible�����ж���������׶μ�����Դʹ�ú�������������Դ���ƣ������Ͻ�����t���׶�ʹ�õ���Դ����
		 * ��Դռ�ú�����Ȼ�󷵻����µ���Դռ�ú������������t�Ŀ�ʼʱ��֮��ĸ��׶β�������Դ���ƣ��ͽ���ʼʱ��������һ��
		 * Ȼ���ٴε����Լ����������   ֱ�����������˵����з������µ���Դռ�ú���
		 * */
	public static int[][] stepBackAlgorithm(Task t,int resourcesLimit[],int resourcesUsage[][]){  //�˸��㷨�ݹ�
			if(rcpsp.IsFeasible(t, resourcesLimit, resourcesUsage)){	 //���������������Դ�ӵ���Դռ�ú���resourcesUsage��
				for(int i=t.getStarttime();i<t.getEndtime();i++){
						for(int j=0;j<resourcesLimit.length;j++){ 
						resourcesUsage[i][j]+=t.getResourceDemands()[j];
						}
				}
				return resourcesUsage;      //������������֮����Դ���������
			}else{    //����ʼʱ��������һ��  �����µ����Լ�  ������һ�����׶���Դ�ܷ���������  �����������
					int steptime=t.getStarttime()+1;    //�˺�һ����ʱ��
					t.setStarttime(steptime);
					return rcpsp.stepBackAlgorithm(t, resourcesLimit, resourcesUsage);  
				}
		}
		public static void printTaskSetInfo(List<Task> TaskSet){  //��ӡÿ������Ľ�ǰ�������
			for(Task t:TaskSet){
				System.out.print("����"+t.getId());
				
				if(!t.getPredecesorTaskSets().isEmpty()){  //�����ǰ����
				System.out.print("   ��ǰ����:");
				List<Task> preSet=t.getPredecesorTaskSets();
				for(Task pret:preSet){
					System.out.print(pret.getId()+"  ");
				}
				}else{
					System.out.print("   ��ǰ����:��  ");
				}
				
				if(!t.getSuccessorTaskSets().isEmpty()){   //������󼯺�
				System.out.print("  ���󼯺�:");
				List<Task> succSet=t.getSuccessorTaskSets();
				for(Task succt:succSet){
					System.out.print(succt.getId()+"  ");
				}
				System.out.println();
				}else{
					System.out.print("   ���󼯺�:��");
				}
				System.out.println();
			}
		}
}
