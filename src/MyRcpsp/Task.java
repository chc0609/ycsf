package MyRcpsp;

import java.util.ArrayList;
import java.util.List;




public class Task implements Cloneable{
	private List<Task> preList;//紧前结点集合
	private List<Task> succList;//后继结点集合
	private int id;
	private int duration;
	private int []resourceDemands;
	private int starttime;
	private int endtime;
	public Task(int id,int duration,int []resourceDemands){
		preList=new ArrayList<Task>();
		succList=new ArrayList<Task>();
		this.id=id;
		this.duration=duration;
		this.resourceDemands=resourceDemands;
	}
	public List<Task> getSuccessorTaskSets(){
		return succList;
	}
	public List<Task> getPredecesorTaskSets(){
		return preList;
	}
	public void clearPredecesorAndSuccessorTaskSets(){  //清空紧前紧后集合列表  在复制时使用
		this.succList.clear();
		this.preList.clear();
	}
	public Task addSuccessor(Task Succ){  //将后继结点加入后继集合
		this.succList.add(Succ);
		Succ.preList.add(this);//并将当前任务加入后继任务的紧前集合
		return this;
	}
	public void addPredecesor(Task pre){   //将任务加入当前任务的紧前集合
		this.preList.add(pre);
		pre.succList.add(this);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int[] getResourceDemands() {
		return resourceDemands;
	}
	public void setResourceDemands(int[] resourceDemands) {
		this.resourceDemands = resourceDemands;
	}
	public int getStarttime() {
		return starttime;
	}
	public void setStarttime(int starttime) {
		this.starttime = starttime;
		endtime=starttime+duration;
	}
	public void setEndtime(int endtime) {
		this.endtime = endtime;
	}
	public int getEndtime() {
		return endtime;
	}
    protected Task clone()  {  //复制该对象
	try {
	    return (Task) super.clone();
	}
	catch (CloneNotSupportedException e) {
	    return null;
	}
    }
	public String toString(){
		return "任务"+this.getId()+"";
	}
}
