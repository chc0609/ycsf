package MyRcpsp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Individual {       	//����ĸ���Ⱦɫ����Ǵ洢�ƻ���һ����������  	
	private Map<Integer,Task> scheduleChromosome;      //Ⱦɫ�������һ��ӳ�伯��  ������ID��ÿ�������
	private int totalTime;    //���������������ʱ��
	private int[] resourcesLimit;
	private int fitness=-1;		//��Ӧ��
	private int id;
	private int [] scheduleSequence;
	public int [] getScheduleSequence(){  //��ӳ���˳��ת��Ϊ�����ϵ�˳��
		this.scheduleSequence=new int[this.scheduleChromosome.size()];
		int i=0;
		for(int t:this.scheduleChromosome.keySet()){  //��ֵҲ�ǰ���ӳ���˳��ֵ��
			scheduleSequence[i]=t;   //����ѭ��ֻѭ��12��
			i++;
		}
		return scheduleSequence;
	}
	public Map<Integer,Task> getScheduleChromosome(){
		return this.scheduleChromosome;    
	}
	public void setScheduleChromosome(Map<Integer,Task> scheduleChromosome){
		this.scheduleChromosome=scheduleChromosome;
	}
	//����
	public void setId(int id){
		this.id=id;
	}
	public int getId(){
		return this.id;
	}
	public int getScheduleChromosomeLength(){
		return this.scheduleChromosome.size();
	}
	public void setGene(Integer location,Task t){
		this.scheduleChromosome.put(location, t);      //�����ж�Ӧλ������Ϊ�µ�����
	}
	public Task getGene(Integer location){
		return this.scheduleChromosome.get(location);		//�õ�ĳ��λ�õĻ���
	}
	public void setFitness(int fitness){
		this.fitness=fitness;
	}
	public int getFitness(){
		return this.fitness;
	}

	public int getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}
	public int[] getResourcesLimit() {
		return resourcesLimit;
	}
	public void setResourcesLimit(int[] resourcesLimit) {
		this.resourcesLimit = resourcesLimit;
	}
	public void resetTask(List<Task> TaskSet){  //��Ϊ������������������ĸ���  ӳ������Ӧ�Ķ��ǿ�¡����
		for(Task t:TaskSet){
			this.getScheduleChromosome().put(t.getId(), t);   
			//���״�ÿ����ֵ����Ӧ��������и���  ����ԭ�����ϵ���������ǿ�¡��  ����ӳ������ʱ˳�򲻸ı�  ��Ϊֻ�Ǹ���  �滻��ſ�������
		}
	}
	public Individual(Map<Integer,Task> scheduleChromosome){		//���췽��1    ����һ������������ΪȾɫ��
		this.scheduleChromosome=scheduleChromosome;              
	}
	public Individual(List<Task> TaskSet){       		
		//���췽��2   ������Ҫһ�����񼯺�  ����һ�����е�������и���  
		this.scheduleChromosome=new LinkedHashMap<>();
		scheduleChromosome.put(1, TaskSet.get(0));
		while(scheduleChromosome.size()!=TaskSet.size()){
			int index=(int) (Math.random()*TaskSet.size()+1);  //  �����12������ index��Χ��1-12 
			if(!scheduleChromosome.containsKey(index)){
				Task t=TaskSet.get(index-1); 
				boolean add=true;
				for(Task pre:t.getPredecesorTaskSets()){
					if(!scheduleChromosome.containsKey(pre.getId())){  //���ȼƻ��������������ǰ���
						add=false;
						break;   //�����һ����������������
					}
				}
				if(add){ //�������t�Ľ�ǰ���ȫ���� 
					/*�������ַ�ʽʵ��̫�鷳 Ŀǰ�����ڴ���
					Task newt=t.clone(); �������񼯺���ѡ�񵽵Ķ���  ��������ƵĶ�����üƻ�Ⱦɫ���Ӧ��ֵ��   ��������¸��Ƶ�����Ľ�ǰ����������ԭ����
					for(Task pre:t.getPredecesorTaskSets()){  //Ϊ�¸��Ƶ����������µĽ�ǰ���󼯺ϣ���Ӧ��Ҳ�Ǹ��ƺ������  Ŀǰ��������  ������ǰһ�ַ���
						Integer key=pre.getId();
						newt.clearPredecesorAndSuccessorTaskSets();
						newt.addPredecesor(scheduleChromosome.get(key));
					}	
					scheduleChromosome.put(newt.getId(),newt);   ע������ֻ�ǽ���ֵ��TaskSet���ĳһ�������     ������������
														  scheduleChromosome�е�һ����ֵ��ͬһ�������  ��ô�ı��һ������  �ڶ�������Ҳ��ͬʱ�ı�*/
					scheduleChromosome.put(t.getId(),t);
				}
			}
		}
	}
	public String toString(){		//��д��������ķ���
		Iterator<Integer> iterator2 = this.scheduleChromosome.keySet().iterator();  
		String output="";
		while (iterator2.hasNext()) {  
            Integer key = iterator2.next();  
            Task value = scheduleChromosome.get(key);  
            output +="����"+value.getId()+" ";
        } 
		Integer key = scheduleChromosome.size();
		return output+"������"+this.getId()+"  ����"+this.scheduleChromosome.get(key).getEndtime();
	}
	public void printScheduleChromosome(){
		Iterator<Integer> iterator2 = scheduleChromosome.keySet().iterator();  
        while (iterator2.hasNext()) {  
            Integer key = iterator2.next();  
            Task value = scheduleChromosome.get(key);  
            System.out.print(value+"�Ŀ�ʼʱ��Ϊ"+value.getStarttime());  
            System.out.println();  
        } 
        Integer key = scheduleChromosome.size();  
        System.out.println("��������Ϊ"+scheduleChromosome.get(key).getEndtime());
	}
	}
