package proj;

public class Cooperate extends Other{

	public Cooperate(){
		super.setChoice(true);
	}
	public void newChoice(Other o) {}
	public void reset(){
		super.setV(0);
	}
}
