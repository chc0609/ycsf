package MyRcpsp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Individual {       	//这里的个体染色体就是存储计划的一个可行序列  	
	private Map<Integer,Task> scheduleChromosome;      //染色体存基因的一个映射集合  将任务ID与每个任务绑定
	private int totalTime;    //个体所有任务的总时间
	private int[] resourcesLimit;
	private int fitness=-1;		//适应度
	private int id;
	private int [] scheduleSequence;
	public int [] getScheduleSequence(){  //将映射表顺序转化为数组上的顺序
		this.scheduleSequence=new int[this.scheduleChromosome.size()];
		int i=0;
		for(int t:this.scheduleChromosome.keySet()){  //键值也是按照映射表顺序赋值的
			scheduleSequence[i]=t;   //正常循环只循环12次
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
	//测试
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
		this.scheduleChromosome.put(location, t);      //将序列对应位置设置为新的任务
	}
	public Task getGene(Integer location){
		return this.scheduleChromosome.get(location);		//得到某个位置的基因
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
	public void resetTask(List<Task> TaskSet){  //因为传进来的是评估过后的个体  映射表里对应的都是克隆对象
		for(Task t:TaskSet){
			this.getScheduleChromosome().put(t.getId(), t);   
			//对亲代每个键值所对应的任务进行更换  换成原来集合的任务而不是克隆的  但是映射表插入时顺序不改变  因为只是覆盖  替换后才可以评估
		}
	}
	public Individual(Map<Integer,Task> scheduleChromosome){		//构造方法1    接受一段序列用其作为染色体
		this.scheduleChromosome=scheduleChromosome;              
	}
	public Individual(List<Task> TaskSet){       		
		//构造方法2   参数需要一个任务集合  产生一个可行的随机序列个体  
		this.scheduleChromosome=new LinkedHashMap<>();
		scheduleChromosome.put(1, TaskSet.get(0));
		while(scheduleChromosome.size()!=TaskSet.size()){
			int index=(int) (Math.random()*TaskSet.size()+1);  //  如果是12个任务 index范围在1-12 
			if(!scheduleChromosome.containsKey(index)){
				Task t=TaskSet.get(index-1); 
				boolean add=true;
				for(Task pre:t.getPredecesorTaskSets()){
					if(!scheduleChromosome.containsKey(pre.getId())){  //调度计划中如果不包含紧前结点
						add=false;
						break;   //如果有一个不包含马上跳出
					}
				}
				if(add){ //如果任务t的紧前结点全在了 
					/*下面这种方式实现太麻烦 目前还存在错误
					Task newt=t.clone(); 复制任务集合里选择到的对象  将这个复制的对象与该计划染色体对应键值绑定   但是这个新复制的任务的紧前任务还是连着原来的
					for(Task pre:t.getPredecesorTaskSets()){  //为新复制的任务设置新的紧前紧后集合（对应的也是复制后的任务）  目前存在问题  还是用前一种方法
						Integer key=pre.getId();
						newt.clearPredecesorAndSuccessorTaskSets();
						newt.addPredecesor(scheduleChromosome.get(key));
					}	
					scheduleChromosome.put(newt.getId(),newt);   注意这里只是将键值与TaskSet里的某一个任务绑定     如果两个个体的
														  scheduleChromosome中的一个键值与同一个任务绑定  那么改变第一个个体  第二个个体也会同时改变*/
					scheduleChromosome.put(t.getId(),t);
				}
			}
		}
	}
	public String toString(){		//重写个体输出的方法
		Iterator<Integer> iterator2 = this.scheduleChromosome.keySet().iterator();  
		String output="";
		while (iterator2.hasNext()) {  
            Integer key = iterator2.next();  
            Task value = scheduleChromosome.get(key);  
            output +="任务"+value.getId()+" ";
        } 
		Integer key = scheduleChromosome.size();
		return output+"个体编号"+this.getId()+"  工期"+this.scheduleChromosome.get(key).getEndtime();
	}
	public void printScheduleChromosome(){
		Iterator<Integer> iterator2 = scheduleChromosome.keySet().iterator();  
        while (iterator2.hasNext()) {  
            Integer key = iterator2.next();  
            Task value = scheduleChromosome.get(key);  
            System.out.print(value+"的开始时间为"+value.getStarttime());  
            System.out.println();  
        } 
        Integer key = scheduleChromosome.size();  
        System.out.println("总任务工期为"+scheduleChromosome.get(key).getEndtime());
	}
	}
