package proj;
import java.util.*;
public class Genetic extends Other{
	private long dna;
	private int generation;
	private int choiceMethod;
	private int dnaPos;
	public Genetic(){
		this.dna = (new Random()).nextLong();
		setV(0);
		this.generation = 0;
		choiceMethod = 2;
		dnaPos = 1;
		super.setChoice(true);
	}
	public Genetic(long dna){
		this();
		this.dna = dna;
	}
	public void plusG(){
		++this.generation;
	}
	public int getG(){
		return this.generation;
	}
	public void setG(int g){
		this.generation = g;
	}
	public void setDna(long dna){
		this.dna = dna;
	}
	public long getDna(){
		return this.dna;
	}
	public Genetic evolve(Genetic genetic, int shift){
		long dna = genetic.getDna();
		long n = (1 << shift)-1,
			m = -1 ^ n;
		float avg = (getV() + genetic.getV())/2;
		Genetic g = new Genetic(this.dna&m | dna&n);
		g.setV(avg);
		g.setG(genetic.getG());
		return g;
	}
	public void mutate(){
		long n = 1 << (new Random()).nextInt(63);
		this.dna ^= n;
	}
	public String printDna(){
		String str = "";
		long v = this.getDna(), n = 1 << 62;
		if(((n<<1)&v)!=0) str+="C";
		else str+="D";
		for(long i = n; i!=0; i=i>>1) {
			if((i&v) != 0) str+="C";
			else str+="D";
		}
		return str;
	}
	public void setChoiceMethod(int c){
		choiceMethod = c;
	}
	public void newChoice() {
		if(choiceMethod == 0){
			if(getDna() >= 0) super.setChoice(true);
		}
		else if(choiceMethod == 1){
			long point = 1<<(new Random()).nextInt(64);
			if((getDna()&point) != 0 ) super.setChoice(true);
			else super.setChoice(false);
		}
		else if(choiceMethod == 2){
			if((this.dna&dnaPos)!=0) super.setChoice(true);
			else super.setChoice(false);
			dnaPos<<=1;
		}
	}
	public void newChoice(Other o) {
		newChoice();
	}
	public void reset(){
		setV(0);
		this.dnaPos = 1;
	}
}
