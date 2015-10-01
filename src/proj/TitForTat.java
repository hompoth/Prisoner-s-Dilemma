package proj;

public class TitForTat extends Other{
	private boolean memory;
	public TitForTat(){
		memory = true;
	}
	public void newChoice(Other o) {
		super.setChoice(memory);
		memory = o.getChoice();
	}
	public void reset(){
		super.setV(0);
		memory = true;
		super.setChoice(memory);
	}
}
