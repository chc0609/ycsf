import java.util.ArrayList;
import java.util.List;


public class Job {
	private int id;
	private int s;  //����������Ҫ�ķ���ʱ��si
	private int e;  //�������ʼʱ��ei
	private int l;  //�������ʼʱ��li
	private int ES;  //���з�Χ�����翪ʼ    ��ס������ÿ����·������һ��  ����ǵù��ʼ
	private int LS;  //���з�Χ����ٿ�ʼ
	private int tp;  //������������ķ���ʱ���
	private List<TransitionPoint> TranSet; //�ٽ�㼯��
	private List<Integer> TempTranSet; //��ʱ���ٽ�㼯��   ��Ϊ��ʱ�㼯��ֻ��Ҫʱ��
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getS() {
		return s;
	}
	public void setS(int s) {
		this.s = s;
	}
	public int getE() {
		return e;
	}
	public void setE(int e) {
		this.e = e;
	}
	public int getL() {
		return l;
	}
	public void setL(int l) {
		this.l = l;
	}
	public int getTp() {
		return tp;
	}
	public void setTp(int tp) {
		this.tp = tp;
	}
	public int getES() {
		return ES;
	}
	public void setES(int eS) {
		ES = eS;
	}
	public int getLS() {
		return LS;
	}
	public void setLS(int lS) {
		LS = lS;
	}
	public Job(int id,int an,int bn,int s){  //���Ĺ��췽��  û����ʱ�ٽ�㼯��
		this.id=id;
		this.e=an;
		this.l=bn;
		TranSet=new ArrayList<TransitionPoint>();
		TransitionPoint t=new TransitionPoint(bn, 0, bn);
		TranSet.add(t);
		this.s=s;
	}
	public Job(int id,int s,int e,int l,int tp){  //һ�㹤�����Ĺ��췽��
		this.id=id;
		this.s=s;
		this.e=e;
		this.l=l;
		this.tp=tp;
		TranSet=new ArrayList<TransitionPoint>();  //����Ҫ��
	}
	public List<TransitionPoint> getTranSet() {
		return TranSet;
	}
	public void setTranSet(List<TransitionPoint> tranSet) {
		TranSet = tranSet;
	}
	public List<Integer> getTempTranSet() {
		return TempTranSet;
	}
	public void setTempTranSet(List<Integer> tempTranSet) {
		TempTranSet = tempTranSet;
	}
	
}
