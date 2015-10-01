package proj;

import java.util.*;

public class Compete {
	private Other[] other;
	private int otherSize;
	private Genetic[] population;
	private int totalPopulation;
	private int generation, totalGenerations;
	private boolean geneticCooperate, otherCooperate;
	private int Version;
	private float mutateFor;
	private float mutatePercent, mutatePercent2;
	private int choiceMethod, choice, choice2;
	private Other strategyOne, strategyTwo;
	private int breakEarly;
	private boolean allowBreak;
	private String names[] = {"Cooperate", "Defect", "TitForTat", "TitForTwoTat", "SuspiciousTitForTat", "DontTitMyTat", "Genetic"};
	public Compete(int p, int g, float m){
		if(m<0) m = 0;
		m/=100;
		mutateFor = m;
		mutatePercent = m;
		mutatePercent2 = m;
		totalPopulation = p;
		otherSize = 6;
		other = new Other[otherSize*totalPopulation];
		totalGenerations = g;
		population = new Genetic[totalPopulation];
		choiceMethod = 2;
		breakEarly = totalGenerations/20;
		allowBreak = false;
		load(63);
	}
	public void setBreak(boolean b){
		allowBreak = b;
	}
	public void setChoiceMethod(int c){
		for(int i = 0; i < totalPopulation; ++i) {
			population[i].setChoiceMethod(c);
			if(Version == 64) ((Genetic)other[i]).setChoiceMethod(c);
		}
		choiceMethod = c;
	}
	public void load(int c){
		Version = c;
		if(c>0 && c<65){
			otherSize = 0;
			for(int i = 1; i <= 32; i<<=1) if((c&i)!=0) ++otherSize;
			if(c == 64) otherSize = 1;
			other = new Other[otherSize*totalPopulation];
			for(int i = 0; i < totalPopulation; ++i) {
				population[i] = new Genetic();
				int offset = 0;
				if((c&1) != 0) other[i * otherSize + offset++] = new Cooperate();
				if((c&2) != 0) other[i * otherSize + offset++] = new Defect();
				if((c&4) != 0) other[i * otherSize + offset++] = new TitForTat();
				if((c&8) != 0) other[i * otherSize + offset++] = new TitForTwoTat();
				if((c&16) != 0) other[i * otherSize + offset++] = new SuspiciousTitForTat();
				if((c&32) != 0) other[i * otherSize + offset++] = new DontTitMyTat();
				if(c==64) other[i] = new Genetic();
			}
		}
		else if(c > 64){
			choice = (c-65)%6;
			choice2 = (c-65)/6;
			if(choice == 0) strategyOne = new Cooperate();
			if(choice == 1) strategyOne = new Defect();
			if(choice == 2) strategyOne = new TitForTat();
			if(choice == 3) strategyOne = new TitForTwoTat();
			if(choice == 4) strategyOne = new SuspiciousTitForTat();
			if(choice == 5) strategyOne = new DontTitMyTat();
			if(choice2 == 0) strategyTwo = new Cooperate();
			if(choice2 == 1) strategyTwo = new Defect();
			if(choice2 == 2) strategyTwo = new TitForTat();
			if(choice2 == 3) strategyTwo = new TitForTwoTat();
			if(choice2 == 4) strategyTwo = new SuspiciousTitForTat();
			if(choice2 == 5) strategyTwo = new DontTitMyTat();
		}
	}
	private float getValue(boolean geneticCooperate, boolean otherCooperate){
		float avgVal = 0;
		if(geneticCooperate && otherCooperate) avgVal=3;
		if(geneticCooperate && !otherCooperate) avgVal=0;
		if(!geneticCooperate && otherCooperate) avgVal=5;
		if(!geneticCooperate && !otherCooperate) avgVal=1;
		return avgVal;
	}
	public void run(){
		if(Version<65) genetic();
		if(Version>64) strategy();
		if(Version<65 && Version>0) randomMutationHillClimbing();
	}
	private void strategy(){
		for(generation = 0; generation < totalGenerations; ++generation){
			boolean oneCooperate, twoCooperate;
			oneCooperate = strategyOne.getChoice();
			twoCooperate = strategyTwo.getChoice();
			strategyOne.newChoice(strategyTwo);
			strategyTwo.newChoice(strategyOne);
			strategyOne.addV(getValue(oneCooperate, twoCooperate));
			strategyTwo.addV(getValue(twoCooperate, oneCooperate));
		}
		printInfo();
	}
	private void hillclimbing(){
		float popVal[] = new float[population.length];
		for(int p = 0; p < population.length; ++p){
			equateDnaValue(p);
			popVal[p] = population[p].getV();
		}
		// Now the hill climbing algorithm
		// In the array popVal, find the largest value
		// This can also be used for greedy search
	}
	private void randomMutationHillClimbing(){
		population[0] = new Genetic();
		int count = 0, i;
		for(i = 0; i < population.length*totalGenerations; ++i){
			count++;
			equateDnaValue(0);
			float v, v2 = population[0].getV();
			Genetic newDna = new Genetic(population[0].getDna());
			newDna.mutate();
			v = equateDnaValue(newDna).getV();
			if(v>v2) {
				population[0] = newDna;
				count = 0;
			}
			else if(v==v2) {
				population[0] = newDna;
			}
			if(count == breakEarly*population.length && allowBreak){
				break;
			}
		}
		System.out.println("\nRandom-Mutation Hill Climbing had a makeup of "+population[0].printDna());
		if(allowBreak) System.out.println("RMHC finished after "+i+" steps");
		// Now the hill climbing algorithm
		// In the array popVal, find the largest value
		// This can also be used for greedy search
	}
	private void genetic(){
		int count = 0;
		float v2 = population[0].getV(), v;
		for(generation = 0; generation < totalGenerations; ++generation){
			mutate();
			for(int p = 0; p < population.length; ++p){
				if(choiceMethod == 0 || choiceMethod == 1){
					equateValue(p);
					population[p].setG(generation);
				}
				else if(choiceMethod == 2){
					equateDnaValue(p);
					population[p].setG(generation);
				}
			}
			//Sort.QuickSort(population); // doesn't work on large populations because of memory
			//Sort.BubbleSort(population); // O(1) space but very slow
			Sort.HeapSort(population); // O(1) space of bubble sort with O(n log(n)) time complexity
			evolve();
			v = population[0].getV();
			count++;
			if(v>v2) count = 0;
			v2=v;
			if(count == breakEarly && allowBreak){
				break;
			}
		}
		printInfo();
		if(allowBreak) System.out.println("GA finished after "+generation*population.length+" steps");
	}
	private Genetic equateDnaValue(Genetic g){
		Genetic tmp = population[0];
		population[0] = g;
		equateDnaValue(0);
		Genetic tmp2 = population[0];
		population[0] = tmp;
		return tmp2;
	}
	private void equateDnaValue(int p){
		for(int i = 0; i < otherSize; ++i){
			population[p].reset();
			other[p * otherSize + i].reset();
		}
		for(int c = 0; c<64; ++c){
			equateValue(p);
		}
	}
	private void equateValue(int p){
		population[p].newChoice();
		geneticCooperate = population[p].getChoice();
		float avgVal = 0;
		for(int i = 0; i < otherSize; ++i){
			other[p * otherSize + i].newChoice(population[p]);
			otherCooperate = other[p * otherSize + i].getChoice();
			avgVal += getValue(geneticCooperate, otherCooperate);
			other[p * otherSize + i].addV(getValue(otherCooperate, geneticCooperate));
		}
		avgVal/=otherSize;
		population[p].addV(avgVal);
	}
	private void evolve(){
		if(Version == 64) Sort.HeapSort(other);
		for(int i = 0; i < population.length/4; ++i){
			int shift = (new Random()).nextInt(60)+2; // The least successful half are replaced by successful children
			population[population.length/2 + i] = population[i*2].evolve(population[i*2+1], shift);
			population[population.length/2 + i + 1] = population[i*2+1].evolve(population[i*2], shift);
			if(Version == 64){
				other[population.length/2 + i] = ((Genetic)other[i*2]).evolve((Genetic)other[i*2+1], shift);
				other[population.length/2 + i + 1] = ((Genetic)other[i*2+1]).evolve((Genetic)other[i*2], shift);
			}
		}
	}
	private void mutate(){
		int mutateBy = (int)(totalPopulation*mutatePercent);
		int mutateAmt = (mutateBy>0)?(new Random()).nextInt(mutateBy):0;
		for(int i = 0; i < mutateAmt; ++i) 
			population[(new Random()).nextInt(totalPopulation)].mutate();
		if(Version == 64){
			int mutateBy2 = (int)(totalPopulation*mutatePercent2);
			int mutateAmt2 = (mutateBy2>0)?(new Random()).nextInt(mutateBy2):0;
			for(int i = 0; i < mutateAmt2; ++i) 
				((Genetic)other[(new Random()).nextInt(totalPopulation)]).mutate();
		}
	}
	public void mutateChoice(int c){
		mutatePercent = mutatePercent2 = 0;
		if((c&1)!=0) mutatePercent = mutateFor;
		if((c&2)!=0) mutatePercent2 = mutateFor;
	}
	private void printInfo(){
		if(Version<65 && Version>0){
			int amt;
			float avgVal = 0;
			float total = 0, coop, defect;
			if(choiceMethod == 0){
				for(int i = 0; i < population.length; ++i) {
					if(population[i].getDna()>=0) ++total;
					avgVal+=population[i].getV();
				}
			}
			else if(choiceMethod == 1){
				for(int i = 0; i < population.length; ++i){
					long dna = population[i].getDna();
					amt = 0;
					for(long j = 1; j!=0; j<<=1) if((j&dna)!=0) ++amt;
					if(amt>=32) ++total;
					avgVal+=population[i].getV();
				}
			}
			else if(choiceMethod == 2){
				for(int i = 0; i<totalPopulation/2; ++i) avgVal+=population[i].getV();
				avgVal/=64/2;
				avgVal/=totalPopulation;
				System.out.printf("The average Genetic DNA Strand had a score of %1$.2f", avgVal);
				String opponentName = "";
				for(int i = 0; i<6; ++i) if(((1<<i)&Version)!=0)opponentName += names[i]+" ";
				if(Version == 64) opponentName = names[6];
				System.out.println(" against "+opponentName);
				avgVal = 0;
				for(int i = 0; i<otherSize*totalPopulation/2; ++i) avgVal+=other[i].getV();
				avgVal/=64/2;
				avgVal/=totalPopulation;
				avgVal/=otherSize;
				System.out.printf("It's opponent had an average score of %1$.2f\n",avgVal);
				System.out.println("The top strand had a makeup of "+population[0].printDna());
			}
			if(choiceMethod == 0 || choiceMethod == 1){
				avgVal/=population.length;
				avgVal/=totalGenerations;
				coop = 100*total/population.length;
				defect = 100-coop;
				String opponentName = "";
				for(int i = 0; i<6; ++i) if(((1<<i)&Version)!=0)opponentName += names[i]+" ";
				if(Version == 64) opponentName = names[6];
				System.out.printf("\nScore of %1$.2f against "+opponentName+"\n",avgVal);
				for(int i = 0; i < otherSize; ++i)
					System.out.printf(i+1+" had a score of %1$.2f\n",other[i].getV()/totalGenerations);

				System.out.printf("Cooperative Percentage: %1$.2f\n",coop);
				System.out.printf("Defective Percentage: %1$.2f\n",defect);
			}
		}
		else if (Version>64){
			System.out.println("\nVersion: "+Version);
			System.out.printf(names[choice]+" has a score of %1$.2f\n",strategyOne.getV()/totalGenerations);
			System.out.printf(names[choice2]+" has a score of %1$.2f\n",strategyTwo.getV()/totalGenerations);
		}
	}
}
