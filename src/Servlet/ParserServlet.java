package Servlet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import Database.Database;
import parsing.AnalyzeTab;

/**
 * Servlet implementation class ParserServlet
 */
@WebServlet("/ParserServlet")
public class ParserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ParserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		// TODO Auto-generated method stub
		String title = request.getParameter("title");
		String artist = request.getParameter("artist");

		String tab = request.getParameter("stringTab");
		String[] chord = tab.split("\\r?\\n");
		ArrayList<String> riffCode = new ArrayList<String>();
		ArrayList<Integer> numberOfChord = new ArrayList<Integer>();
		ArrayList<String> information = (ArrayList<String>) session.getAttribute("information");
		String username;
		String result = "";
		int i;
		AnalyzeTab analyze;
		Database store = new Database();
		/*
		File folder = new File("ReadFile/");
		System.out.println(folder.getAbsolutePath());
		File[] listOfFiles = folder.listFiles();

		    for (i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		        System.out.println("File " + listOfFiles[i].getName());
		        String s = readFile(listOfFiles[i].getAbsolutePath());
		        String[] s1 = s.split("\\r?\\n");
		        analyze = new AnalyzeTab();
		        analyze.changeHexa(s1);//change to hexadecimal for more than 1 letter
				
				analyze.convertRiffCode(s1);
				riffCode = analyze.getRiffCode();
				analyze.getLocalDistribution(riffCode);
				numberOfChord = analyze.countNumberOfChord(riffCode);
				analyze.getChordDistribution(riffCode, numberOfChord);
				analyze.getFingerDistribution(riffCode, numberOfChord);
				analyze.getFretDistribution(riffCode, numberOfChord);
				
		        writeExcel(analyze, "model", "model");
		      } else if (listOfFiles[i].isDirectory()) {
		    	  
		      }
		    }
		*/
		if(artist.equals("")){
			artist = "No Author";
		}
		
		try{
			username = information.get(0);
		}
		
		catch(NullPointerException n){
			username = "guest";
		}
		
		try{
			
			store.initialize();
			analyze = new AnalyzeTab();
	        
			analyze.changeHexa(chord);//change to hexadecimal for more than 1 letter
					
			analyze.convertRiffCode(chord);
			riffCode = analyze.getRiffCode();
			numberOfChord = analyze.countNumberOfChord(riffCode);
					
			System.out.println(riffCode.size());
					
			for(i = 0; i < riffCode.size(); i++){
				System.out.print(" " + riffCode.get(i));
			}
			
			analyze.findPattern(riffCode);
			analyze.getLocalDistribution(riffCode);
			analyze.getChordDistribution(riffCode, numberOfChord);
			analyze.getFingerDistribution(riffCode, numberOfChord);
			analyze.getFretDistribution(riffCode, numberOfChord);
					
			double totalTech = (double)analyze.getFreqOfSlur() + (double)analyze.getFreqOfTech();
			
			HashMap<String, Double> resultList = new HashMap<String, Double>();
			resultList.put("ratioOfChord", (double)analyze.getRatioOfChord());
			resultList.put("ratioOfFret", (double)0);
			resultList.put("freqOfSlur", (double)analyze.getFreqOfSlur() / totalTech);
			resultList.put("freqOfTech", (double)analyze.getFreqOfTech() / totalTech);
			
			
			resultList.put("localDist", analyze.getLocalDist().getMean());
			for(i = 0; i < analyze.getChordDist().getNumbers().size(); i++){
				resultList.put("chordDist-" + (i+1), (double)analyze.getChordDist().getNumbers().get(i) / 
													 (double)analyze.getFingerDist().getNumbers().size());
			}
			
			for(i = 0; i < analyze.getFingerDist().getCountNumbers().size()-1; i++){
				resultList.put("fingerDist-" + (i), (double)analyze.getFingerDist().getCountNumbers().get(i) / 
													(double)analyze.getFingerDist().getNumbers().size());
			}
			
			for(i = 0; i < analyze.getFretDist().getCountNumbers().size(); i++){
				resultList.put("fretDist-" + (i), (double)analyze.getFretDist().getCountNumbers().get(i) / 
												  (double)analyze.getFretDist().getNumbers().size());
			}
			
			/*double diffLevel = (3.2539 * resultList.get("ratioOfChord")) + 
							   (0.0059 * resultList.get("freqOfSlur")) + 
							   (14.4712 * resultList.get("localDist")) + 
							   ((-1.6703) * resultList.get("chordDist-3")) + 
							   (22.0544 * resultList.get("fingerDist-0")) + 
							   (33.5032 * resultList.get("fingerDist-1")) + 
							   (14.4712 * resultList.get("fingerDist-2")) + 
							   (4.6713 * resultList.get("fretDist-1")) + 
							   ((-4.4707) * resultList.get("fretDist-2")) + 
							   (-3.4812 * resultList.get("fretDist-3")) + 
							   -19.0416;*///first formula weka
			/*double diffLevel = ( -1.124 * resultList.get("ratioOfChord")) + 
					(0.0061 * resultList.get("freqOfSlur")) + 
					(1.4634 * resultList.get("chordDist-2")) + 
					(-7.8733 * resultList.get("fingerDist-0")) +
					(5.0397 * resultList.get("fretDist-1")) + 10.5201;*///ÀÚ±âÀü
			//third formula
			/*
			double diffLevel = (0.00652564 * resultList.get("freqOfSlur")) +
							   (-0.0011247 * resultList.get("freqOfTech")) +
							   (6.685635 * resultList.get("localDist")) +
							   (-0.0810785 * resultList.get("chordDist-2")) +
							   (-0.1510032 * resultList.get("chordDist-4")) +
							   (10.73256 * resultList.get("fingerDist-1")) +
							   2.849424;
			*/
			
			double diffLevel = (0.00652564 * resultList.get("freqOfSlur")) +
					   (-0.0011247 * resultList.get("freqOfTech")) +
					   (-6.91816 * resultList.get("fingerDist-0")) +
					   (-0.0810785 * resultList.get("chordDist-2")) +
					   (-0.1510032 * resultList.get("chordDist-4")) +
					   (4.046927 * resultList.get("fingerDist-1")) +
					   9.53506;
	
			if(diffLevel < 1){
				diffLevel = 1;
			}
			
			else if(diffLevel > 8){
				diffLevel = 8;
			}
			
			else{
				diffLevel = Math.round(diffLevel);
			}
			
			
			System.out.println(diffLevel + ": diffLevel");
			writeExcel(analyze, title, artist);
			
			Date date = new Date(Calendar.getInstance().getTime().getTime());
			
			tab = tab.replaceAll("\\r?\\n", "<br>");
			store.insertGuitarTab(title, artist, date.toString(), username, (int)diffLevel, tab);
			store.insertAnalyzeResult(title, artist, resultList);
			
			session.setAttribute("resultList", resultList);
			session.setAttribute("result", result);
			session.setAttribute("tab", store.getTab(title, artist));
			
			response.sendRedirect("tabpage.jsp");
		}
		
		catch(Exception e){
			e.printStackTrace();
			response.sendRedirect("upload.jsp");
		}
	}
	
	public void writeExcel(AnalyzeTab analyze, String title, String artist){
		int i;
		int j;
		boolean exist = false;
		
		i = 0;
		
			try{
				String directory = "src/NotParsed/";
				File f = new File(directory+artist+".csv");
				File parentDir = f.getParentFile();
				System.out.println(directory+i+".csv");
				FileWriter fileWriter;
				StringBuffer oneLineStringBuffer = new StringBuffer();
				PrintWriter csvWriter;
				System.out.println(f.getAbsolutePath());
				
				if(! parentDir.exists()) 
				      parentDir.mkdirs();
				
				if(!f.exists()){
					f.createNewFile();
				}
				
				else{
					
					if(f.exists()){
						exist = true;
					}
				
				}
				
				fileWriter = new FileWriter(f,true);
		    	//BufferedWriter writer give better performance
		    	BufferedWriter bw = new BufferedWriter(fileWriter);
				
				if(!exist){
					bw.write("title,");
					bw.write("nGrams,");
					bw.write("topNGrams,");
					bw.write("ratioOfChord,");
					bw.write("ratioOfFrets,");
					bw.write("freqOfSlur,");
					bw.write("freqOfTech,");
					bw.write("localDist,");
					bw.write("1note,");
					bw.write("2note,");
					bw.write("3note,");
					bw.write("4note,");
					bw.write("5note,");
					bw.write("6note,");
					bw.write("fingerDist-0,");
					bw.write("fingerDist-1,");
					bw.write("fingerDist-2,");
					bw.write("fretDist-0,");
					bw.write("fretDist-1,");
					bw.write("fretDist-2,");
					bw.write("fretDist-3");
					bw.write("\n");
				}
				
				bw.write(title + ",");
				bw.write(analyze.getNGrams() + ",");
				bw.write("0,");
				bw.write(analyze.getRatioOfChord() + ",");
				bw.write(analyze.getRatioOfFrets() + ",");
				bw.write(analyze.getFreqOfSlur() + ",");
				bw.write(analyze.getFreqOfTech() + ",");
				
				bw.write("" + (double)analyze.getLocalDist().getMean());
				bw.write(",");
				
				for(j = 0; j < analyze.getChordDist().getNumbers().size(); j++){
					bw.write("" + (double)analyze.getChordDist().getNumbers().get(j) / 
							(double)(analyze.getChordDist().getNumbers().get(0) + 
							 analyze.getChordDist().getNumbers().get(1) +
							 analyze.getChordDist().getNumbers().get(2) +
							 analyze.getChordDist().getNumbers().get(3) +
							 analyze.getChordDist().getNumbers().get(4) +
							 analyze.getChordDist().getNumbers().get(5)));
					bw.write(",");
				}
				
				for(j = 0; j < analyze.getFingerDist().getCountNumbers().size()-1; j++){
					bw.write("" + (double)analyze.getFingerDist().getCountNumbers().get(j) / 
								  (double)analyze.getFingerDist().getNumbers().size());
					bw.write(",");
				}
				
				for(j = 0; j < analyze.getFretDist().getCountNumbers().size(); j++){
					bw.write("" + (double)analyze.getFretDist().getCountNumbers().get(j) / 
								  (double)analyze.getFretDist().getNumbers().size());
					bw.write(",");
				}
				
				bw.write("\n");
				bw.close();
		        
		        System.out.println(f.getAbsolutePath());
		        
		        File fileDirectory = new File(directory);
		        
			}catch(Exception e){
				e.printStackTrace();
			}
	}
	
	public String readFile(String filename)
	{
	    String content = null;
	    File file = new File(filename); //for ex foo.txt
	    FileReader reader = null;
	    try {
	        reader = new FileReader(file);
	        char[] chars = new char[(int) file.length()];
	        reader.read(chars);
	        content = new String(chars);
	        reader.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        if(reader !=null){try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
	    }
	    return content;
	}
}
