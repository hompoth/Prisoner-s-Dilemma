package proj;

public class TitForTwoTat extends Other {
	private boolean memory;
	private boolean newMemory;
	public TitForTwoTat(){
		memory = true;
		newMemory = true;
	}
	public void newChoice(Other o) {
		if(newMemory == memory) super.setChoice(memory);
		memory = newMemory;
		newMemory = o.getChoice();
	}
	public void reset(){
		super.setV(0);
		memory = true;
		super.setChoice(memory);
	}
}
