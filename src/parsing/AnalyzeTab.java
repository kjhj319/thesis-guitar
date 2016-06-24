package parsing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class AnalyzeTab {
	private ArrayList<String> riffCode;
	private int nGrams;
	private float ratioOfChord;
	private float ratioOfFrets;
	private int freqOfSlur;
	private int freqOfTech;
	private HashMap<String, Integer> techniques;
	private HashMap<String, String> countNGrams;
	private Statistic localDist;
	private Statistic chordDist;
	private Statistic fingerDist;
	private Statistic fretDist;
	
	public AnalyzeTab(){
		riffCode = new ArrayList<String>();
		nGrams = 0;
		ratioOfChord = 0;
		ratioOfFrets = 0;
		freqOfSlur = 0;
		freqOfTech = 0;
		techniques = new HashMap<String, Integer>();
		countNGrams = new HashMap<String, String>();
		localDist = new Statistic();
		chordDist = new Statistic();
		fingerDist = new Statistic();
		fretDist = new Statistic();
		initTechnique();
	}
	
	public void convertRiffCode(String[] chord){
		int numOfDash = 0;
		float numOfNote = 0;
		float numOfChord = 0;
		int i;
		int j;
		int k;
		int l;
		String temp = "";
		int numTemp;
		
		for(i = 0; i < chord.length; i++){
			chord[i] = chord[i].toLowerCase();
			//System.out.println(chord[i]);
		}
		
		ArrayList<String> resultExcel = new ArrayList<String>();
		
		for(i = 0; i < chord.length; i++){
			if(i + 5 < chord.length){
				
			if(chord[i].contains("-") && chord[i].contains("|") &&
			   chord[i + 1].contains("-") && chord[i + 1].contains("|") &&
			   chord[i + 2].contains("-") && chord[i + 2].contains("|") &&
		       chord[i + 3].contains("-") && chord[i + 3].contains("|") &&
			   chord[i + 4].contains("-") && chord[i + 4].contains("|") &&
			   chord[i + 5].contains("-") && chord[i + 5].contains("|")){
				int length;
				for(j = 1; j < chord[i].length(); j++){
					//for numbers
					length = chord[i].length();
						for(k = 0; k < 6; k++){
							while(chord[i + k].length() < chord[i].length()){
								chord[i + k] = chord[i + k] + "-";
							}
							
							if(chord[i + k].charAt(j) >= '0' && chord[i + k].charAt(j) <= '9'){
								// getting for the first number
								temp = temp + (k + 1) + (char)((int)chord[i + k].charAt(j) + 49);
								//if there is technique follows do this step
								
							}
							
							else if(chord[i + k].charAt(j) >= 'a' && chord[i + k].charAt(j) <= 'g'){
								temp = temp + (k + 1) + (char)((int)chord[i + k].charAt(j) + 10);
							}
							
							else if(chord[i + k].charAt(j) != '-' && chord[i + k].charAt(j) != '|'){
								
								if(techniques.containsKey(chord[i + k].charAt(j) + "")){
									numTemp = techniques.get(chord[i + k].charAt(j) + "");
									numTemp++;
									techniques.put(chord[i + k].charAt(j) + "", numTemp);
								}
							}
						}
					//check whether string is not in the if and else statement
					if(!temp.isEmpty()){
						if(temp.length() > 2){
							numOfChord++;
						}

						numOfNote++;
						riffCode.add(temp);
					}
					temp = "";
				}
				
				i += 5;
			}
			
			else{
			}
			
			}
		}
		freqOfSlur = techniques.get("h") + techniques.get("p") +
					  techniques.get("/") + techniques.get("\\");
		
		String key;
		int value;
		for (Entry<String, Integer> entry : techniques.entrySet()) {
		    key = entry.getKey();
		    value = entry.getValue();
		    if(key.equals("h") || key.equals("p") || key.equals("/") || key.equals("\\")){
		    	freqOfSlur += value;
		    }
		    
		    else{
		    	if(key.equals("#")){
		    		value = value / 2;
		    	}
		    	freqOfTech += value;
		    }
		    // ...
		}
		
		ratioOfChord = numOfChord/numOfNote;
	}
	
	public void findPattern(ArrayList<String> code){
		HashMap<String, Integer> multiplePattern = new HashMap<String, Integer>();
		int max = 1;
		int i;
		int j;
		String pattern = " ";
		String key;
		int value;
		
		for (Entry<String, Integer> entry : multiplePattern.entrySet()) {
		    key = entry.getKey();
		    value = entry.getValue();
		    if(value > max){
		    	max = value;
		    }
		    
		}
		
		nGrams = max;
	}
	
	public void getLocalDistribution(ArrayList<String> code){
		int i;
		int j;
		ArrayList<Integer> localDistribution = new ArrayList<Integer>();
		int min = 0;
		int max = 0;
		int total = 0;
		int firstNote = 0;
		int prevRefNote = 9999;
		int currRefNote = 0;
		
		for(i = 0; i < code.size(); i++){
			firstNote = 0;
			
			if(code.get(i).length() <= 3 && code.get(i).contains("a")){
				localDistribution.add(0);
			}
			
			else{
				for(j = 0; j < code.get(i).length(); j++){
					if(code.get(i).charAt(j) == 'a'){
						
					}
					
					else if(code.get(i).charAt(j) >= 'b' && code.get(i).charAt(j) <= 'z'){
						if(firstNote == 0){
							min = code.get(i).charAt(j);
							max = code.get(i).charAt(j);
							firstNote++;
						}
						
						else{
							if(min > code.get(i).charAt(j))
								min = code.get(i).charAt(j);
							
							if(max < code.get(i).charAt(j))
								max = code.get(i).charAt(j);
						}
						
						if(prevRefNote == 9999)
							prevRefNote = code.get(i).charAt(j);
					}
				}
				
				if(prevRefNote > min || max - prevRefNote > 4){
					localDistribution.add(1);
					prevRefNote = min;
					//System.out.println(":" + (char)prevRefNote + " " + (char)min + " " + (char)max + " " + prevRefNote);
				}
				else if(max - prevRefNote < 4 || max - prevRefNote == 4){
					localDistribution.add(0);
				}
				
			}
		}
		
		localDist.setNumbers(localDistribution);
		localDist.calculateAll("localDist");
	}
	
	public void getChordDistribution(ArrayList<String> code, ArrayList<Integer> numberOfChord){
		int i;
		ArrayList<Integer> chordDistribution = new ArrayList<Integer>();
		
		for(i = 0; i < 6; i++){
			chordDistribution.add(0);
		}
		
		for(i = 0; i < numberOfChord.size(); i++){
			if(numberOfChord.get(i) == 1){
				chordDistribution.set(0, chordDistribution.get(0) + 1);
			}
			
			else if(numberOfChord.get(i) == 2){
				chordDistribution.set(1, chordDistribution.get(1) + 1);
			}
			
			else if(numberOfChord.get(i) == 3){
				chordDistribution.set(2, chordDistribution.get(2) + 1);
			}
			
			else if(numberOfChord.get(i) == 4){
				chordDistribution.set(3, chordDistribution.get(3) + 1);
			}
			
			else if(numberOfChord.get(i) == 5){
				chordDistribution.set(4, chordDistribution.get(4) + 1);
			}
			
			else if(numberOfChord.get(i) == 6){
				chordDistribution.set(5, chordDistribution.get(5) + 1);
			}
			
		}
		
		chordDist.setNumbers(chordDistribution);
		chordDist.calculateAll("chordDist");
	}
	
	public void getFingerDistribution(ArrayList<String> code, ArrayList<Integer> numberOfChord){
		int i;
		int j;
		ArrayList<Integer> fingerDistribution = new ArrayList<Integer>();
		int min = 0;
		int max = 0;
		int total = 0;
		int firstNote = 0;
		int prevRefNote = 9999;
		int currRefNote = 0;
		
		for(i = 0; i < code.size(); i++){
			firstNote = 0;
			
			if(code.get(i).length() <= 3 && code.get(i).contains("a")){
				fingerDistribution.add(0);
			}
			
			else{
				for(j = 0; j < code.get(i).length(); j++){
					if(code.get(i).charAt(j) == 'a'){
						
					}
					
					else if(code.get(i).charAt(j) >= 'b' && code.get(i).charAt(j) <= 'z'){
						if(firstNote == 0){
							min = code.get(i).charAt(j);
							max = code.get(i).charAt(j);
							firstNote++;
						}
						
						else{
							if(min > code.get(i).charAt(j))
								min = code.get(i).charAt(j);
							
							if(max < code.get(i).charAt(j))
								max = code.get(i).charAt(j);
						}
						
						if(prevRefNote == 9999)
							prevRefNote = code.get(i).charAt(j);
					}
				}
				
				//System.out.println("ref note:" + prevRefNote + " " + min + " " + max);
				//System.out.println(":" + (char)prevRefNote + " " + (char)min + " " + (char)max);
				if(prevRefNote > min || max - prevRefNote > 4){
					fingerDistribution.add(2);
					prevRefNote = min;
					//System.out.println(":" + (char)prevRefNote + " " + (char)min + " " + (char)max + " " + prevRefNote);
				}
				
				else if(max - prevRefNote < 4){
					fingerDistribution.add(0);
				}
				
				else if(max - prevRefNote == 4){
					fingerDistribution.add(1);
				}
				
			}
		}
		
		fingerDist.setNumbers(fingerDistribution);
		fingerDist.calculateAll("fingerDist");
	}
	
	public void getFretDistribution(ArrayList<String> code, ArrayList<Integer> numberOfChord){
		int i;
		int j;
		ArrayList<Integer> fretDistribution = new ArrayList<Integer>();
		int fretCount = 0;
		int chordSize;
		char temp;
		String tempString;
		boolean found = false;;
		
		for(i = 1; i < code.size(); i++){//check every note
			found = false;
			
			if(code.get(i).length() < 4){//check if current note is length 1
				if(Math.abs(code.get(i).charAt(0) - code.get(i - 1).charAt(0)) == 1 && //check adjacent
				   code.get(i).charAt(1) == code.get(i-1).charAt(1) && //check same fret
				   code.get(i).length() == code.get(i-1).length()){ //check same single note
					fretDistribution.add(2);
				}
				
				else if(Math.abs(code.get(i).charAt(0) - code.get(i-1).charAt(0)) > 1 &&//check if current note is not adjacent
						code.get(i).charAt(1) == code.get(i-1).charAt(1) && //check same fret
						code.get(i).length() == code.get(i-1).length()){//check same single note
					fretDistribution.add(1);
				}
				
				else if(code.get(i-1).length() > 4){
					for(j = 0; j < numberOfChord.get(i - 1); j++){//check whether note is in the same fret
						if(code.get(i - 1).charAt(j * 2 + 1) == code.get(i).charAt(1)){
							found = true;
						}
					}
					
					if(found){//if single note is in the same fret as the chord
						fretDistribution.add(1);
					}
					
					else{
						fretDistribution.add(0);
					}
					
				}
				
				else{
					fretDistribution.add(0);
				}
				
			}
			else{
				
				for(j = 0; j < numberOfChord.get(i) - 1; j++){
					if(Math.abs(code.get(i).charAt(j * 2) - code.get(i).charAt(j * 2 + 2)) == 1 &&
					   code.get(i).charAt(j * 2 + 1) == code.get(i).charAt(j * 2 + 3)){
						found = true;
					}
					
				}
				
				if(found){
					fretDistribution.add(2);
				}
				
				else{
					fretDistribution.add(3);
				}
			}
		}
		
		fretDist.setNumbers(fretDistribution);
		fretDist.calculateAll("fretDist");
	}
	
	public String[] changeHexa(String[] chord){
		int i;
		int j;
		int length;
		
		for(i = 0; i < chord.length; i++){
			if(i + 5 < chord.length){
				if(chord[i].contains("-") && chord[i].contains("|") &&
				   chord[i + 1].contains("-") && chord[i + 1].contains("|") &&
				   chord[i + 2].contains("-") && chord[i + 2].contains("|") &&
				   chord[i + 3].contains("-") && chord[i + 3].contains("|") &&
				   chord[i + 4].contains("-") && chord[i + 4].contains("|") &&
				   chord[i + 5].contains("-") && chord[i + 5].contains("|")){
					
					for(j = 0; j < 6; j++){
						chord[i + j] = chord[i + j].toLowerCase();
						chord[i + j] = chord[i + j].replaceAll("10", "-a");
						chord[i + j] = chord[i + j].replaceAll("11", "-b");
						chord[i + j] = chord[i + j].replaceAll("12", "-c");
						chord[i + j] = chord[i + j].replaceAll("13", "-d");
						chord[i + j] = chord[i + j].replaceAll("14", "-e");
						chord[i + j] = chord[i + j].replaceAll("15", "-f");
						chord[i + j] = chord[i + j].replaceAll("16", "-g");
						chord[i + j] = chord[i + j].replaceAll("[()]", "#");
						chord[i + j] = chord[i + j].replaceAll("NH", "n");
						chord[i + j] = chord[i + j].replaceAll(" -", "--");
						chord[i + j] = chord[i + j].replaceAll("- ", "--");
						//chord[i] = chord[i].replaceAll("| ", "|-");
						//chord[i] = chord[i].replaceAll(" |", "-|");
						chord[i + j] = chord[i + j].replaceAll(" ", "");
					}
					i += 5;
				}
			}
		}
		return chord;
	}
	
	public ArrayList<Integer> countNumberOfChord(ArrayList<String> code){
		ArrayList<Integer> countAlphabet = new ArrayList<>();
		int i;
		int j;
		int count = 0;
		
		for(i = 0; i < code.size(); i++){
			count = 0;
			for(j = 0; j < code.get(i).length(); j++){
				if(code.get(i).charAt(j) >= 'a' && code.get(i).charAt(j) <= 'z'){
					count++;
				}
				
			}
			countAlphabet.add(count);
		}
		
		return countAlphabet;
	}
	
	public void initTechnique(){
		techniques = new HashMap<String, Integer>();
		
		techniques.put("L", 0); // L for tied note
		techniques.put("x", 0); // x for dead note
		techniques.put("g", 0); // g for grace note
		techniques.put("#", 0); // (n) for ghost note
		techniques.put(">", 0); // > for accentuated note
		techniques.put("n", 0); // NH
		techniques.put("ah", 0); // AH
		techniques.put("th", 0); // TH
		techniques.put("sh", 0); // SH
		techniques.put("ph", 0); // PH
		techniques.put("h", 0); // h
		techniques.put("p", 0); // p
		techniques.put("b", 0); // b
		techniques.put("br", 0); // br
		techniques.put("pr", 0); // pb
		techniques.put("pbr", 0); // pbr
		techniques.put("brb", 0); // brb
		techniques.put("s", 0); // s
		techniques.put("S", 0); // S
		techniques.put("/", 0); // /
		techniques.put("\\", 0); // \
		techniques.put("~", 0); // ~
		techniques.put("w", 0); // w
		techniques.put("tr", 0); // tr
		techniques.put("TP", 0); // TP
		techniques.put("T", 0); // T
		techniques.put("<", 0); // <
		techniques.put("^", 0); // ^
		techniques.put("v", 0); // v
	}
	
	public int returnHexa(char tab){
		if(tab == 'A')
			return 10;
		
		else if(tab == 'B')
			return 11;
		
		else if(tab == 'C')
			return 12;
		
		else if(tab == 'D')
			return 13;
		
		else if(tab == 'E')
			return 14;
		
		else if(tab == 'F')
			return 15;
		
		else if(tab == 'G')
			return 16;
		
		return 0;
	}
	
	public int getNGrams() {
		return nGrams;
	}
	
	public Statistic getLocalDist() {
		return localDist;
	}

	public void setLocalDist(Statistic localDist) {
		this.localDist = localDist;
	}

	public Statistic getChordDist() {
		return chordDist;
	}

	public void setChordDist(Statistic chordDist) {
		this.chordDist = chordDist;
	}

	public Statistic getFingerDist() {
		return fingerDist;
	}

	public void setFingerDist(Statistic fingerDist) {
		this.fingerDist = fingerDist;
	}

	public Statistic getFretDist() {
		return fretDist;
	}

	public void setFretDist(Statistic fretDist) {
		this.fretDist = fretDist;
	}
	
	public ArrayList<String> getRiffCode() {
		return riffCode;
	}

	public void setRiffCode(ArrayList<String> riffCode) {
		this.riffCode = riffCode;
	}
	
	public float getRatioOfChord() {
		return ratioOfChord;
	}

	public void setRatioOfChord(float ratioOfChord) {
		this.ratioOfChord = ratioOfChord;
	}

	public float getRatioOfFrets() {
		return ratioOfFrets;
	}

	public void setRatioOfFrets(float ratioOfFrets) {
		this.ratioOfFrets = ratioOfFrets;
	}

	public int getFreqOfSlur() {
		return freqOfSlur;
	}

	public void setFreqOfSlur(int freqOfSlur) {
		this.freqOfSlur = freqOfSlur;
	}

	public int getFreqOfTech() {
		return freqOfTech;
	}

	public void setFreqOfTech(int freqOfTech) {
		this.freqOfTech = freqOfTech;
	}

	public HashMap<String, Integer> getTechniques() {
		return techniques;
	}

	public void setTechniques(HashMap<String, Integer> techniques) {
		this.techniques = techniques;
	}

	public HashMap<String, String> getCountNGrams() {
		return countNGrams;
	}

	public void setCountNGrams(HashMap<String, String> countNGrams) {
		this.countNGrams = countNGrams;
	}
	
}
