package proj;

//import java.awt.*;
//import java.lang.*;
import java.util.*;

public class DontTitMyTat extends Other{
	private int Version = 0; // Version 0 - Better against genetic algorithm; Version 3 - Better against strategies
	// both versions better overall
	private Queue<Boolean> memory;
	public DontTitMyTat(){
		super.setChoice(true);
		memory = new LinkedList<Boolean>();
		for(int i = 0; i<6-Version; ++i) memory.add(true); // Memory depth of 6 or 3
	}
	public void newChoice(Other o) {
		Object[] arr = memory.toArray();
		boolean allSix = false, lastThree = false;
		for(int i = 0; i<6-Version; ++i) {
			allSix|=((Boolean)arr[i]).booleanValue();
			if(i>2) lastThree|=((Boolean)arr[i]).booleanValue();
		}
		super.setChoice(!lastThree&&allSix);
		memory.add(o.getChoice());
		memory.remove();
	}
	public void reset(){
		super.setV(0);
		super.setChoice(true);
		memory = new LinkedList<Boolean>();
		for(int i = 0; i<6-Version; ++i) memory.add(true); 
	}
}
