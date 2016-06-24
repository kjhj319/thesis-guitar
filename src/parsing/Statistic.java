package parsing;

import java.util.ArrayList;
import java.util.HashMap;

public class Statistic {
	private ArrayList<Integer> numbers;
	private ArrayList<Integer> countNumbers;
	private double mean;
	private int median;
	private int mode;
	private double std_dev;
	private double entropy;
	private double skewness;
	
	public Statistic(){
		mean = 0;
		median = 0;
		mode = 0;
		std_dev = 0;
		skewness = 0;
		entropy = 0;
		numbers = new ArrayList<Integer>();
		countNumbers = new ArrayList<Integer>();
	}
	
	public void calculateAll(String name){
		int i;
		int count = 0;
		int sum = 0;
		double sumStdev = 0;
		ArrayList<Double> power = new ArrayList<Double>();
		HashMap<Integer, Integer> countMode = new HashMap<Integer, Integer>();
		
		for(i = 0; i < numbers.size(); i++){
			sum += numbers.get(i);
			
			if(i == Math.round(numbers.size() / 2)){
				median = numbers.get(i);
				System.out.println("median: " + numbers.get(i));
			}
			
			if(countMode.get(numbers.get(i)) == null)
				countMode.put(numbers.get(i), 1);
			
			else
				countMode.put(numbers.get(i), count + 1);
		}
		
		if(name.equals("chordDist")){
			mean = (double)numbers.get(0) / 100000 + (double)numbers.get(1) / 10000 + (double)numbers.get(2) / 1000 +
					(double)numbers.get(3) / 100 + (double)numbers.get(4) / 10 + (double)numbers.get(5);
		}
		
		else{
			mean = (double)sum / (double)numbers.size();
		}
		
		for(i = 0; i < numbers.size(); i++){
			power.add(numbers.get(i) - mean);
			sumStdev += (numbers.get(i) - mean);
		}
		
		std_dev = Math.sqrt(sumStdev / (numbers.size() - 1)); 
		
		skewness = (mean-median) / std_dev;
		
		countElement();
		
		System.out.println("mean: " + mean + " median: " + median + " std_dev: " + std_dev + " skewness: " + skewness);
	}
	
	public void countElement(){
		int i;
		
		for(i = 0; i < 4; i++){
			countNumbers.add(0);
		}
		
		for(i = 0; i < numbers.size(); i++){
			if(numbers.get(i) == 0){
				countNumbers.set(0, countNumbers.get(0) + 1);
			}
			
			else if(numbers.get(i) == 1){
				countNumbers.set(1, countNumbers.get(1) + 1);
			}
			
			else if(numbers.get(i) == 2){
				countNumbers.set(2, countNumbers.get(2) + 1);
			}
			
			else if(numbers.get(i) == 3){
				countNumbers.set(3, countNumbers.get(3) + 1);
			}
			
		}
	}
	
	public ArrayList<Integer> getNumbers() {
		return numbers;
	}

	public void setNumbers(ArrayList<Integer> numbers) {
		this.numbers = numbers;
	}

	public ArrayList<Integer> getCountNumbers() {
		return countNumbers;
	}

	public void setCountNumbers(ArrayList<Integer> countNumbers) {
		this.countNumbers = countNumbers;
	}

	public double getMean() {
		return mean;
	}

	public void setMean(double mean) {
		this.mean = mean;
	}

	public int getMedian() {
		return median;
	}

	public void setMedian(int median) {
		this.median = median;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public double getStd_dev() {
		return std_dev;
	}

	public void setStd_dev(double std_dev) {
		this.std_dev = std_dev;
	}

	public double getEntropy() {
		return entropy;
	}

	public void setEntropy(double entropy) {
		this.entropy = entropy;
	}

	public double getSkewness() {
		return skewness;
	}

	public void setSkewness(double skewness) {
		this.skewness = skewness;
	}

}
