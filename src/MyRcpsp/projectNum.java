package MyRcpsp;

import java.util.ArrayList;
import java.util.List;

public class projectNum {
	public List<Task> initTaskSet32(){  //resourcesLimit=new int[]{30,32,34,29};
		//Task(int id,int duration,int []resourceDemands)
		Task t1=new Task(1,0,new int[]{0,0,0,0});
		Task t2=new Task(2,8,new int[]{1,3,6,1});
		Task t3=new Task(3,10,new int[]{3,10,9,4});
		Task t4=new Task(4,9,new int[]{10,10,7,3});
		Task t5=new Task(5,7,new int[]{8,6,4,10});
		Task t6=new Task(6,9,new int[]{ 2,1,5,2});
		Task t7=new Task(7,3,new int[]{10,6,3,6});
		Task t8=new Task(8,4,new int[]{4,9,7,7});
		Task t9=new Task(9,10,new int[]{8,10,7,8});
		Task t10=new Task(10,1,new int[]{7,1,8,7});
		Task t11=new Task(11,10,new int[]{3,4,1,7});
		Task t12=new Task(12,1,new int[]{7,3,1,6});  //初始化
		Task t13=new Task(13,4,new int[]{8,9,6,3});
		Task t14=new Task(14,9,new int[]{3,7,6,2});
		Task t15=new Task(15,3,new int[]{2,3,5,6});
		Task t16=new Task(16,3,new int[]{7,3,10,3});
		Task t17=new Task(17,2,new int[]{4,6,9,9});
		Task t18=new Task(18,4,new int[]{4,10,3,6});
		Task t19=new Task(19,8,new int[]{2,3,4,3});
		Task t20=new Task(20,10,new int[]{1,4,5,8});
		Task t21=new Task(21,5,new int[]{1,6,10,6});
		Task t22=new Task(22,9,new int[]{4,9,7,7});
		Task t23=new Task(23,5,new int[]{1,1,4,3});
		Task t24=new Task(24,5,new int[]{10,5,1,7});
		Task t25=new Task(25,1,new int[]{8,7,4,10});
		Task t26=new Task(26,9,new int[]{9,2,1,2});
		Task t27=new Task(27,5,new int[]{6,10,7,5});
		Task t28=new Task(28,3,new int[]{3,4,3,7});
		Task t29=new Task(29,10,new int[]{4,6,4,10});
		Task t30=new Task(30,4,new int[]{3,6,7,7});
		Task t31=new Task(31,7,new int[]{7,8,1,7});
		Task t32=new Task(32,0,new int[]{0,0,0,0});
		//为每个有后继结点的增加后继集合
		t1.addSuccessor(t2).addSuccessor(t3).addSuccessor(t4);
		t2.addSuccessor(t6).addSuccessor(t10).addSuccessor(t15);
		t3.addSuccessor(t6).addSuccessor(t7).addSuccessor(t12);
		t4.addSuccessor(t5).addSuccessor(t13).addSuccessor(t23);
		t5.addSuccessor(t22).addSuccessor(t25).addSuccessor(t28);
		t6.addSuccessor(t8).addSuccessor(t11).addSuccessor(t14);
		t7.addSuccessor(t9).addSuccessor(t15).addSuccessor(t17);
		t8.addSuccessor(t16).addSuccessor(t17).addSuccessor(t30);
		t9.addSuccessor(t10).addSuccessor(t16).addSuccessor(t18);
		t10.addSuccessor(t13).addSuccessor(t19).addSuccessor(t26);
		t11.addSuccessor(t13).addSuccessor(t18).addSuccessor(t21);
		t12.addSuccessor(t16).addSuccessor(t20).addSuccessor(t21);
		t13.addSuccessor(t24).addSuccessor(t27);
		t14.addSuccessor(t19).addSuccessor(t20).addSuccessor(t25);
		t15.addSuccessor(t20).addSuccessor(t23).addSuccessor(t27);
		t16.addSuccessor(t19).addSuccessor(t25).addSuccessor(t28);
		t17.addSuccessor(t18).addSuccessor(t26).addSuccessor(t31);
		t18.addSuccessor(t28);
		t19.addSuccessor(t31);
		t20.addSuccessor(t22).addSuccessor(t24).addSuccessor(t29);
		t21.addSuccessor(t22).addSuccessor(t23);
		t22.addSuccessor(t30);
		t23.addSuccessor(t24).addSuccessor(t26);
		t24.addSuccessor(t30);
		t25.addSuccessor(t27);
		t26.addSuccessor(t29);
		t27.addSuccessor(t31);
		t28.addSuccessor(t29);
		t29.addSuccessor(t32);
		t30.addSuccessor(t32);
		t31.addSuccessor(t32);
		List<Task> TaskSet=new ArrayList<Task>();
		TaskSet.add(t1);TaskSet.add(t2);TaskSet.add(t3);TaskSet.add(t4);TaskSet.add(t5);TaskSet.add(t6);
		TaskSet.add(t7);TaskSet.add(t8);TaskSet.add(t9);TaskSet.add(t10);TaskSet.add(t11);TaskSet.add(t12);
		TaskSet.add(t13);TaskSet.add(t14);TaskSet.add(t15);TaskSet.add(t16);TaskSet.add(t17);TaskSet.add(t18);
		TaskSet.add(t19);TaskSet.add(t20);TaskSet.add(t21);TaskSet.add(t22);TaskSet.add(t23);TaskSet.add(t24);
		TaskSet.add(t25);TaskSet.add(t26);TaskSet.add(t27);TaskSet.add(t28);TaskSet.add(t29);TaskSet.add(t30);
		TaskSet.add(t31);TaskSet.add(t32);
		return TaskSet;
	}
	public List<Task> initTaskSet6(){
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
		List<Task> TaskSet=new ArrayList<Task>();
		TaskSet.add(t1);TaskSet.add(t2);TaskSet.add(t3);TaskSet.add(t4);TaskSet.add(t5);TaskSet.add(t6);
		TaskSet.add(t7);TaskSet.add(t8);TaskSet.add(t9);TaskSet.add(t10);TaskSet.add(t11);TaskSet.add(t12);
		return TaskSet;
		}
	}
