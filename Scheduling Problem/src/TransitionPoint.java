
public class TransitionPoint {  //�ٽ����
	int t;//�����ڸõ�Ŀ�ʼʱ��
	int PartialPenality;  //ֱ���õ���ۼƳͷ�
	int w;  //����ʱ��
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
