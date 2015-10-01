//Josh Hompoth, Joe Nguyen, Scott Phounpadith
//
//Compete.setChoiceMethod(0) == is value positive
//Compete.setChoiceMethod(1) == randomly find a position in the DNA; Cooperate on 1 or Defect on 0
//Compete.setChoiceMethod(2) == choose from right to left of the DNA to Cooperate on 1 or Defect on 0 (default)
//Compete.mutateChoice(0) == You can't mutate; Other can't
//Compete.mutateChoice(1) == You can mutate; Other can't
//Compete.mutateChoice(2) == You can't mutate; Other can
//Compete.mutateChoice(3) == You can mutate; Other can
//Compete.setBreak(bool) == Sets if there is a break point on expected succession
//Compete.load(val) == if val is 1-63, Genetic Algorithm verse a combination of strategies
//Compete.load(val) == if val is 64, Genetic verse a Genetic Algorithm
//Compete.load(val) == if val is 65-100, Strategy verse another Strategy
//Compete.run() == Computes the load version (By default 63)

package proj;
import java.util.*;
public class Main {
	public static void main(String args[]){
		int pop = 100, gen = 100, mut = 100;
		System.out.println("The total population is "+pop+" the final generation is "+gen+" and the mutate amount is "+mut);
		Compete match = new Compete(pop, gen, mut);
		match.load(63);
		match.setBreak(true);
		match.run();
	}
}
