package MyRcpsp;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class GeneticAlgorithm {
	private int populationSize;		//��Ⱥ��ģ
	private double mutationRate;	//������
	private double crossoverRate;	//������
	private int elitismCount;		//��Ӣ��Ա��
	protected int tournamentSize;	//��������ģ
	private int []resourcesLimit;   //��Ⱥ��ÿ������ͳһ����Դ����
	private List<Task> TaskSet;  //��ʼ���񼯺�
	private int totalTime;//������ʱ��  ÿ�����嶼һ��  ��������Ӧ��
	public GeneticAlgorithm(int populationSize,double mutationRate,double crossover,int elitismCount,int tournamentSize,int []resourcesLimit){
		this.populationSize=populationSize;
		this.mutationRate=mutationRate;
		this.crossoverRate=crossover;                   //���췽��
		this.elitismCount=elitismCount;
		this.tournamentSize=tournamentSize;
		this.resourcesLimit=resourcesLimit;
	}
	public Population initPopulation(List<Task> TaskSet){		
		//��ʼ����Ⱥ  ����һ����Ⱥ���趨�������� ��ÿ�����嶼���з���Լ�����������  ����ͬ����ʱ��  
		this.TaskSet=TaskSet;
		int totalTime=0;
		for(Task t:TaskSet){
			totalTime  +=t.getDuration();
		}
		this.totalTime=totalTime;
		Population p=new Population(this.populationSize,TaskSet,totalTime,this.resourcesLimit);
		return p;
	}
	public int calculateIndividualFitness(Individual in){			//��Ӧ�Ⱥ���    ����������Ӧ��
		//�Ž�ȥ�ĸ����ӳ���б�����Ӧ��ʼ�����񼯺�  �������ǿ�¡��
		int totalTime=in.getTotalTime();
		int []resourcesLimit=in.getResourcesLimit();
		int [][]resourcesUsage=new int[totalTime][resourcesLimit.length];
		Map<Integer,Task> schedule=in.getScheduleChromosome();
		in.resetTask(TaskSet);
		/*ʷʫ��BUG Ϊʲô������  ��Ϊ��Ⱥ�и�������ʱ ��Ӧ�����ʱ����᲻һ��  ��͵��º�����ܻ�ȡ������һ���ĸ���  ��Ȼ��������ӳ���Ӧ�Ķ��ǳ�ʼ
		 * ����  ���ǵ�һ�������ڼ�������Ӧ��֮��  ӳ����ǿ�¡�����񼯺�  ��Щ��¡���ǰ�����ϵ���Զ��Ҵ���ʱ��   �ڶ�����������һ������ı�
		 * ���Եڶ������������Ӧ��ʱ  �Ž�ȥ���е��Ǵ�������񼯺�  �ͻᵼ����Ӧ�ȼ������
		 * */
		for(Task t:schedule.values()){//���ݸø���ӳ������ײ���������ʱ�䰲��      
					//	����ײ�Ԫ�صĲ��������и��干��   ���Խ�����Ҫ����Щ������ʼʱ�䶼��Ϊ0
			int time=0;
			if(t.getPredecesorTaskSets().isEmpty()){
				t.setStarttime(0);  //��ʼ���1
			}else{
				for(Task pret : t.getPredecesorTaskSets()){
					if(pret.getEndtime()>time){
						time=pret.getEndtime();
					}
				}
				t.setStarttime(time);//���ʱ����û����Դ����������t��ʼ��ʱ��
				/*���������������t������Դ�����£�t����ʼ��ʱ��
				*����ԭ���ǽ�t�������ʱ��t���������ĸ�ʱ��׶��ж����޳�����Դ��������ƣ��еĻ���һ��  ��������Ļ�  ����
				*���ص���һ����Դʹ�õ����飬����t���ú�Ҫ����ԭ������Դ���飬Ҳ����Ҫ��������t���׶���ʹ�õ���Դ
				*/
				resourcesUsage=rcpsp.stepBackAlgorithm(t,resourcesLimit,resourcesUsage);
			}
		}

		Integer key=schedule.size();
		int fitness=totalTime-schedule.get(key).getEndtime();   
		in.setFitness(fitness);
		/*��Ϊ��ʼ��ʱʹ�õ���ӳ���ÿ�������ӳ����ֵ�����ʼ���񼯺ϵ������໥�󶨣���˸��ݵ�һ�������ӳ���˳���ȡ��������в���
		 * ����ÿ�������趨��ʼʱ�䣬���������������ʾҲ�ᷢ���仯�����԰���һ�������ӳ���������趨ʱ����������ں��ڸø�����м�¼��
		 * ֮���ÿ������ʱ��������ã�֮����һ�����尴������ӳ����ȡ������������ʱ�䣬���������ڡ�
		 * */
		Map<Integer,Task> newSchedule=new LinkedHashMap<Integer,Task>();
		for(Task t:schedule.values()){  //����������õ�����ʱ���¡  ���µ�ӳ������Щ��¡��������γ�ӳ�䣨ע����Ȼ��ֻҪ��¡����Ŀ�ʼʱ�䣩  
										//����ԭ�������������ʱ�������Ա���һ�ű����
			Task newt=t.clone();
			newSchedule.put(newt.getId(), newt);   //ע�ⲻҪ���ÿ�¡����Ľ�ǰ����   ��Ϊ�������Ļ���ԭ���ʼ�����������
			t.setStarttime(0);
			t.setEndtime(0);//����һ��
		}
		in.setScheduleChromosome(newSchedule);//���µ�ӳ����ڸø���  �Ա�֮������ø���ÿ������Ŀ�ʼʱ��
		return fitness;
	}
	public void evaluatePopulationFitness(Population p){  	//����Ⱥ����Ӧ��     ���ǽ���Ⱥ��ÿ��������Ӧ�����
		double populationFitness=0;
		for(Individual in : p.getIndividuals()){
			calculateIndividualFitness(in);
		}
//		double avgFitness=populationFitness/p.size();
//		p.setPopulationFitness(avgFitness);	
	}
	public boolean isTerminationConditionMet(int generationCount,int maxGeneration){	//��ֹ���
		
		return (generationCount>maxGeneration);
	}
	public Individual selectParent(Population p){	//ʵ�ֽ�����ѡ�񷽷�    ��������p�����������  ��˳��Ҫ����
		Population tournament=new Population(this.tournamentSize);
		p.shuffle();  //����p��Ⱥ��˳��  ��ǰ����λ�ü��뾺����  �����������������
		for(int i=0;i<this.tournamentSize;i++){
			tournament.setIndividual(i, p.getIndividual(i));  
		}
		return tournament.getFittest(0);  //�ھ�������ѡȡ��õ�һ��    ��Ȼ��ǰ�����ϵ�Ǵ�ĵ���û��  ��Ӧ���ǶԵ�
	}
	public Population crossoverPopulation(Population p){		//�״����򽻲�   ��������p�Ѿ������������
		/*�������һ�㽻��ķ�ʽ   �� ���ף�1 4 3 8 2 5 7 6 10 9 11 12
		 * 						ĸ�ף�1 4 3 5 9 7 2 8 11 6 10 12
		 * ����һ��1-11�������   ����4  ����ͽ�ȡ���׵�ǰ�ĸ�����   1 4 3 8 -1 -1 -1 -1 -1 -1 -1 -1 
		 * Ȼ��ӵ�5������ʼ  ��ĸ�׵Ļ����ͷ����  ���������оͲ�����
		 * ���Ϊ 1 4 3 8 5 9 7 2 11 6 10 12
		 * */
		Population newp=new Population(p.size());		//�����µĿ���Ⱥ
		for(int populationIndex=0;populationIndex<p.size();populationIndex++){
			Individual parent1=p.getFittest(populationIndex);
			
			if(this.crossoverRate>Math.random()&&populationIndex>=this.elitismCount){
				Individual parent2=this.selectParent(p);
				//parent2.resetTask(this.TaskSet);
				//����2�ν��������������������ӳ���ֵ�������ϱ��ڲ���
				int p1[]=parent1.getScheduleSequence();
				int p2[]=parent2.getScheduleSequence();
				
				int cutIndex=(int) (Math.random()*(p1.length-1)+1);    //��������״�1��λ���е� ����12������  ��ô�е�ķ�ΧΪ1-11    
				int offspringChromosome[]=new int[parent1.getScheduleChromosome().size()];
				Arrays.fill(offspringChromosome,-1);  //������������ֵ��ֵΪ-1
				for(int i=0;i<cutIndex;i++){
					offspringChromosome[i]=p1[i];
				}
				int pos=0;  //ÿ�δ�ѭ����  �״�2 ��ʼѭ����λ��
				for(int j=cutIndex;j<p1.length;j++){  
					for(int m=pos;m<p2.length;m++){   //��m�������±�λ������û�жԺ�����и�ֵ  ����pos�Լ�   
															//�ý�������һ�ν����ѭ����pos�������Ӧ������λ��ʼ
						if(!test.contain(offspringChromosome, p2[m])){  
							offspringChromosome[j]=p2[m];
							pos++;
							break;
						}
						pos++;
					}
				}
				//�����¸���Ҫ����ӳ������  ��Դ����  ��������������ʱ��
				Map<Integer,Task> offspringscheduleChromosome=new LinkedHashMap<Integer,Task>();
				for(int i=0;i<offspringChromosome.length;i++){
					//��ӳ�����а��������ϵ�˳��ӳ���������
					offspringscheduleChromosome.put(offspringChromosome[i], this.TaskSet.get(offspringChromosome[i]-1));
				}
				Individual offspring=new Individual(offspringscheduleChromosome);
				offspring.setResourcesLimit(this.resourcesLimit);
				offspring.setTotalTime(this.totalTime);
				newp.setIndividual(populationIndex, offspring);
			}else{
				//������������Ϊ��Ӣ����  �ͽ���Щ�������·�������µ���Ⱥ  ע�⻹����ֱ�ӷ� ��Ϊ����Ľ�ǰ����˳���Ǵ��  ֮�����������
				//�����÷ŵ�������Ӧ������
				//parent1.resetTask(this.TaskSet);  //���������øø���ӳ�������Ӧ������ ������Ҫ������ ֻ�ǽ���˳��  ���ڶ�Ӧ�Ŀ�¡������Բ�Ҫ��
				newp.setIndividual(populationIndex, parent1);
			}
		}
		return newp;
	}
	public Population mutatePopulation(Population p){	//	���췽��  
		Population newp=new Population(p.size());
		for(int populationIndex=0;populationIndex<p.size();populationIndex++){
			Individual in=p.getFittest(populationIndex);//����Ӧ�ò�������  ��Ϊ��Ӣ����Ҫ����ǰ���ֹ����
				if(populationIndex>=this.elitismCount){
				
				
				}
				newp.setIndividual(populationIndex, in);
		}
		return newp;
	}
}
