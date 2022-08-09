import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;




public class MainProblem {
	static int an=0;  //��ʾ��ʿ�ϰ෶Χ
	static int bn=70;
	static int nurseNum=1;
	static int jobNum=2;
	static int time[][]=new int[][]{{5,3,5},{5,5,5},{5,5,5}}; //·��ʱ��   ÿ����ʿӦ�ö�Ҫһ������
	public static void main(String[] args) {
		Job j0=new Job(0,an,bn,0);
		Job j1=new Job(1,6,4,20,12);
		Job j2=new Job(2,8,26,42,34);
		Job []JobSet=new Job[3];
		JobSet[0]=j0; JobSet[1]=j1;JobSet[2]=j2;  //����������˳���ʼ��
		
		ArrayList<Integer> sub_tour=new ArrayList<Integer>();
		sub_tour.add(0);sub_tour.add(1);sub_tour.add(2);sub_tour.add(0);  //·����ʽǰ��Ҫ0
		
		Job []JobSequence=new Job[sub_tour.size()];
		for(int i=0;i<JobSequence.length;i++){ 			 //�������е�·��˳�� ת��Ϊ�������˳��
			JobSequence[i]=JobSet[sub_tour.get(i)];
		}

		if(setEandL(JobSequence)){   	//ֻ��ÿ�����˶��п��е�ʱ�䷶Χ�ſ��Կ�ʼ
		for(int j=1;j<sub_tour.size()-1;j++){
			int i=j-1;
			produceTempTranSet(JobSequence[j]);
			int f=1; int g=1; //fΪ��ǰ�ٽ�㼯���������   gΪ��һ�������ʱ�ٽ�㼯������
			while(f<=JobSequence[i].getTranSet().size()&&g<=JobSequence[j].getTempTranSet().size()){
				//·������0-1Ϊ5 1-2ҲΪ5
				int temptime=time[JobSequence[i].getId()][JobSequence[j].getId()];
				if(JobSequence[i].getTranSet().get(f-1).getT()+JobSequence[i].getS()+temptime
						<=JobSequence[j].getTempTranSet().get(g-1)){
					int thj=Math.max(JobSequence[i].getTranSet().get(f-1).getT()+JobSequence[i].getS()+temptime,
							JobSequence[j].getE());
					int Penality=calculateSinglePenality(thj,JobSequence[j])+JobSequence[i].getTranSet().get(f-1).getPartialPenality();
					int wif=JobSequence[i].getTranSet().get(f-1).getW();
					TransitionPoint tran=new TransitionPoint(thj,Penality,wif);
					JobSequence[j].getTranSet().add(tran);
					if(JobSequence[i].getTranSet().get(f-1).getT()+JobSequence[i].getS()+temptime
							==JobSequence[j].getTempTranSet().get(g-1)){
						g++;
					}
					f++;
				}else{
					int tgtemp=JobSequence[j].getTempTranSet().get(g-1);
					int Penality=JobSequence[i].getTranSet().get(f-1).getPartialPenality()+
							calculateSinglePenality(tgtemp,JobSequence[j]);
					int w=JobSequence[i].getTranSet().get(f-1).getW()-
							(JobSequence[i].getTranSet().get(f-1).getT()+JobSequence[i].getS()+temptime-tgtemp);
					TransitionPoint tran=new TransitionPoint(tgtemp,Penality,w);
					JobSequence[j].getTranSet().add(tran);
					g++;
				}
			}
		}
		for(int i=0;i<JobSequence.length-1;i++){
			for(int j=0;j<JobSequence[i].getTranSet().size();j++){
				System.out.println(JobSequence[i].getTranSet().get(j));
			}
			System.out.println();
		}
		}
	}
	public static int calculateSinglePenality(int t,Job j){
		if(j.getTp()-t<=3&&j.getTp()-t>=0){  //���t�����ʱ������
			return 0;
		}else if(j.getTp()-t<=6&&j.getTp()-t>3){
			return 1;
		}else if(j.getTp()-t>6){
			return 2;
		}
		if(t-j.getTp()<=3&&t-j.getTp()>=0){//���t�����ʱ����ұ�
			return 0;
		}else if(t-j.getTp()>3&&t-j.getTp()<=6){
			return 1;
		}else if(t-j.getTp()>6){
			return 2;
		}
		return 1000000;
	}
	public static void produceTempTranSet(Job j){   //��0����յ���  �����궼��Ҫ��ʱ�ٽ�㼯��
		ArrayList<Integer>	TempTranSet=new ArrayList<Integer>();
		int es=j.getES();
		int ls=j.getLS();
		int temp1=j.getTp()-7;
		int temp2=j.getTp()-4;
		int temp3=j.getTp()+3;
		int temp4=j.getTp()+6;
		if(temp1>=es&&temp1<ls){   //�ڿ��з�Χ�����4���ٽ��
			TempTranSet.add(temp1);
		}
		if(temp2>=es&&temp2<ls){
			TempTranSet.add(temp2);
		}
		if(temp3>=es&&temp3<ls){
			TempTranSet.add(temp3);
		}
		if(temp4>=es&&temp4<ls){
			TempTranSet.add(temp4);
		}
		TempTranSet.add(ls);   //��ӿ�����ٿ�ʼʱ��
		//�����㷨���ei
//		if(es==j.getE()){  //��2��
//			TempTranSet.add(j.getE());
//		}
		Collections.sort(TempTranSet);
		
		j.setTempTranSet(TempTranSet);
	}
	public static int[] calulateEarliestTime(Job []JobSequence){
		int []EarliestTime=new int[JobSequence.length];		//�洢�������翪ʼʱ��
		EarliestTime[0]=JobSequence[0].getE();
		for(int i=1;i<EarliestTime.length;i++){
			EarliestTime[i]=Math.max(JobSequence[i].getE(), 
					EarliestTime[i-1]+JobSequence[i-1].getS()+time[JobSequence[i-1].getId()][JobSequence[i].getId()]);
		}
		return EarliestTime;
	}
	public static int[] calculateLatestTime(Job []JobSequence){
		int []LatestTime=new int[JobSequence.length];		//�洢��������ʼʱ��
		//Job LastJob=JobSequence[JobSequence.length-1];
		LatestTime[LatestTime.length-1]=JobSequence[JobSequence.length-1].getL();    //����㣨0��������ʼʱ����ǻ�ʿ�Ĺ�������ʱ��l
		for(int i=LatestTime.length-1;i>0;i--){
			LatestTime[i-1]=Math.min(JobSequence[i-1].getL(),
					LatestTime[i]-time[JobSequence[i-1].getId()][JobSequence[i].getId()]-JobSequence[i-1].getS());
		}
		return LatestTime;
	}
	public static boolean setEandL(Job []JobSequence){   //���Ƚ����ж�    ����Ϊÿ���������ÿ��з�Χ�ڵ����������ʼʱ��
		int []EarliestTime=calulateEarliestTime(JobSequence);
		int []LatestTime=calculateLatestTime(JobSequence);
		for(int i=0;i<EarliestTime.length;i++){
			if(EarliestTime[i]>LatestTime[i]){
				return false;
			}
		}
		for(int i=1;i<EarliestTime.length-1;i++){   //����˳�����鳤��Ϊ4  ��ôֻ��1 2λΪ������Ҫ�������ÿ��з�Χ������  ���ʱ��
			JobSequence[i].setES(EarliestTime[i]);
			JobSequence[i].setLS(LatestTime[i]);
		}
		return true;
	}
}
