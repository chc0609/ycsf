
public class TransitionPoint {  //临界点类
	int t;//服务在该点的开始时间
	int PartialPenality;  //直到该点的累计惩罚
	int w;  //出发时间
	public int getT() {
		return t;
	}
	public void setT(int t) {
		this.t = t;
	}
	public int getPartialPenality() {
		return PartialPenality;
	}
	public void setPartialPenality(int partialPenality) {
		this.PartialPenality = partialPenality;
	}
	public int getW() {
		return w;
	}
	public void setW(int w) {
		this.w = w;
	}
	public TransitionPoint(int t,int PartialPenality,int w){
		this.t=t;
		this.PartialPenality=PartialPenality;
		this.w=w;
	}
	public String toString(){
		return "("+t+","+PartialPenality+","+w+")";
	}
}
