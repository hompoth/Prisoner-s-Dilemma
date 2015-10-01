package proj;

import java.util.LinkedList;

public class SuspiciousTitForTat extends Other {
	private boolean memory;
	public SuspiciousTitForTat(){
		memory = false;
	}
	public void newChoice(Other o) {
		super.setChoice(memory);
		memory = o.getChoice();
	}
	public void reset(){
		super.setV(0);
		memory = false;
		super.setChoice(memory);
	}
}
