import java.util.ArrayList;
import java.util.List;


public class Job {
	private int id;
	private int s;  //该老人所需要的服务时间si
	private int e;  //最早服务开始时间ei
	private int l;  //最晚服务开始时间li
	private int ES;  //可行范围下最早开始    记住该属性每条子路径都不一样  用完记得归初始
	private int LS;  //可行范围下最迟开始
	private int tp;  //该老人最满意的服务时间点
	private List<TransitionPoint> TranSet; //临界点集合
	private List<Integer> TempTranSet; //临时的临界点集合   因为临时点集合只需要时间
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
	public Job(int id,int an,int bn,int s){  //起点的构造方法  没有临时临界点集合
		this.id=id;
		this.e=an;
		this.l=bn;
		TranSet=new ArrayList<TransitionPoint>();
		TransitionPoint t=new TransitionPoint(bn, 0, bn);
		TranSet.add(t);
		this.s=s;
	}
	public Job(int id,int s,int e,int l,int tp){  //一般工作结点的构造方法
		this.id=id;
		this.s=s;
		this.e=e;
		this.l=l;
		this.tp=tp;
		TranSet=new ArrayList<TransitionPoint>();  //必须要有
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
