package Tabcorp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;


public class Tabcorp {
	public static void main(String[] args){

		String file = args[0]+".txt";
		String result = args[1]+".txt";
//		String file = "race.txt";
//		String result = "dividends.txt";
		
		processRace(file,result);
	}
	
	public static void processRace(String file, String result){
    	Scanner s=null;
    	String line = null;
    	int first =0;
    	int second =0;
    	int third =0;
    	int winpool =0;
    	int placepool =0;
    	int exactapool =0;
    	double PERCENTAGE_W = 0.15;
    	double PERCENTAGE_P = 0.12;
    	double PERCENTAGE_E = 0.18;
    	int total_w =0;
    	int total_p1 =0;
    	int total_p2 =0;
    	int total_p3 =0;
    	int total_e =0;
    	ArrayList<String> selstake_w = new ArrayList<String>();
    	ArrayList<String> selstake_p = new ArrayList<String>();
    	ArrayList<String> selstake_e = new ArrayList<String>();
    	double payout_w =0.0;
    	double dividend_w =0.0;
    	double payout_p1 =0.0;
    	double dividend_p1 =0.0;
    	double payout_p2 =0.0;
    	double dividend_p2 =0.0;
    	double payout_p3 =0.0;
    	double dividend_p3 =0.0;
    	double payout_e =0.0;
    	double dividend_e =0.0;
    	
	    try {
	        s = new Scanner(new BufferedReader(new FileReader(file)));
	        boolean hasline = s.hasNextLine();
	        
	        while (hasline) {            	
	        	line = s.nextLine();// read line
	        	String[] splitline = line.split(":"); //split line by colon 
	        	
	        	if(splitline[0].equals("Result")){
	        		first = Integer.parseInt(splitline[1]);
	        		second = Integer.parseInt(splitline[2]);
	        		third = Integer.parseInt(splitline[3]);
	        	}
        		if(splitline[1].equals("W")){
        			winpool += Integer.parseInt(splitline[3]);
        			selstake_w.add(splitline[2]+":"+splitline[3]);
        		}
        		if(splitline[1].equals("P")){
        			placepool += Integer.parseInt(splitline[3]);
        			selstake_p.add(splitline[2]+":"+splitline[3]);
        		}
        		if(splitline[1].equals("E")){
        			exactapool += Integer.parseInt(splitline[3]);
        			selstake_e.add(splitline[2]+":"+splitline[3]);
        		}
	        	
	        }	        
	    }catch (Exception e) {
	    }finally {
	    	double commission_w = commission(winpool,PERCENTAGE_W);
	        double commission_p = commission(placepool,PERCENTAGE_P);
	        double commission_e = commission(exactapool,PERCENTAGE_E);
	        double remaining_w = winpool - commission_w;
	        double remaining_p = placepool - commission_p;
	        double remaining_e = exactapool - commission_e;
	        
	        //Starting Calculating WINNER bets
	        for(String sw: selstake_w){
	        	String[] splitsw = sw.split(":");
	        	if(Integer.parseInt(splitsw[0]) == first){
	        		total_w +=Integer.parseInt(splitsw[1]);
	        	}
	        }
	        for(String sw: selstake_w){
	        	String[] splitsw = sw.split(":");	        	
	        	if(Integer.parseInt(splitsw[0]) == first){
	        		double prop = Integer.parseInt(splitsw[1])*100.0/total_w;
	        		payout_w = prop * remaining_w/100;
	        		dividend_w = payout_w / Integer.parseInt(splitsw[1]);
	        	}
	        }
//	        System.out.println(dividend_w);	        
	        //Ending calculating WINNER bets
	        
	        //Starting Calculating PLACE bets
	        for(String sp: selstake_p){
	        	String[] splitsp = sp.split(":");
	        	if(Integer.parseInt(splitsp[0]) == first){
	        		total_p1 +=Integer.parseInt(splitsp[1]);
	        	}
	        	if(Integer.parseInt(splitsp[0]) == second){
	        		total_p2 +=Integer.parseInt(splitsp[1]);
	        	}
	        	if(Integer.parseInt(splitsp[0]) == third){
	        		total_p3 +=Integer.parseInt(splitsp[1]);
	        	}
	        }
	        
	        for(String sp: selstake_p){
	        	String[] splitsp = sp.split(":");
	        	if(Integer.parseInt(splitsp[0]) == first){
	        		double prop = Integer.parseInt(splitsp[1])*100.0/total_p1;
	        		payout_p1 = prop * (remaining_p/3)/100;
	        		dividend_p1 = payout_p1 / Integer.parseInt(splitsp[1]);
	        	}
	        	if(Integer.parseInt(splitsp[0]) == second){
	        		double prop = Integer.parseInt(splitsp[1])*100.0/total_p2;
	        		payout_p2 = prop * (remaining_p/3)/100;
	        		dividend_p2 = payout_p2 / Integer.parseInt(splitsp[1]);
	        	}
	        	if(Integer.parseInt(splitsp[0]) == third){
	        		double prop = Integer.parseInt(splitsp[1])*100.0/total_p3;
	        		payout_p3 = prop * (remaining_p/3)/100;
	        		dividend_p3 = payout_p3 / Integer.parseInt(splitsp[1]);
	        	}
	        }
//	        System.out.println(dividend_p1);
//	        System.out.println(dividend_p2);
//	        System.out.println(dividend_p3);
	        //Ending calculating PLACE bets
	        
	        //Starting Calculating EXACTA bets
	        for(String se: selstake_e){
	        	String[] splitse = se.split(":");
	        	String firstsecond = first+","+second;
	        	if(splitse[0].equals(firstsecond)){
	        		total_e +=Integer.parseInt(splitse[1]);
	        	}
	        }
	        for(String se: selstake_e){
	        	String[] splitse = se.split(":");
	        	String firstsecond = first+","+second;
	        	if(splitse[0].equals(firstsecond)){
	        		double prop = Integer.parseInt(splitse[1])*100.0/total_e;
	        		payout_e = prop * remaining_e/100;
	        		dividend_e = payout_e / Integer.parseInt(splitse[1]);
	        	}
	        }
//	        System.out.println(dividend_e);	
	        //Ending calculating EXACTA bets
	        
	        //Writing dividends to file
	        IOFiles.writeFile(result);
	        IOFiles.putln("Win:"+first+":$"+ Math.floor(dividend_w * 100) / 100);
	        IOFiles.putln("Place:"+first+":$"+ Math.floor(dividend_p1 * 100) / 100);
	        IOFiles.putln("Place:"+second+":$"+ Math.floor(dividend_p2 * 100) / 100);
	        IOFiles.putln("Place:"+third+":$"+ Math.floor(dividend_p3 * 100) / 100);
	        IOFiles.putln("Exacta:"+first+":$"+ Math.floor(dividend_e * 100) / 100);
	        
		    if (s != null) {		        
		        s.close();
		    }
	    }
	}
	
	public static double commission(int totalbet, double percentage){
		return totalbet * percentage;
	}
}

