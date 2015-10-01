package proj;

public class Defect extends Other{

	public Defect(){
		super.setChoice(false);
	}
	public void newChoice(Other o) {}
	public void reset(){
		super.setV(0);
	}
}
