import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.Callable;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*; 
//import javax.json.Json;
//import javax.json.JsonObject;
//import javax.json.JsonReader;
//import javax.json.stream.JsonParser;
//import javax.json.stream.JsonParser.Event;


public class InitClass {
//	public static double xi0 = 0.5884, mu_0 = 0.4536, beta0 = 0.6296;
//	public static double xi0 = 0.1168, mu_0 = 3.81, 	beta0 = 0.98984;
	public static double[] probs  = {0.1, 0.04, 0.02, 0.01, 0.005};
	public static double goveAdaptH = 0.0;
	
	public static double inflation1 = 0.04;
	public static double inflation2 = 0.06;
	public static double inflation3 = 0.02;
	
	
	public static double Payment(double intRate, double N, double loan) {
	      double pay = 0.0;
	      pay = intRate * Math.pow(1+intRate, N) * loan /  ( Math.pow(1+intRate, N) - 1 ) ;
	      return pay;
	}
	public static void printArray(String[] rows) {
		      for (int i = 0; i < rows.length; i++) {
		         System.out.println(i + " , " +rows[i]);
		         System.out.print('\n');
		      }
		      System.out.println("\nlength of the array is "+ rows.length);
		      }
	public static ArrayList<Properties> residentialCensus(ArrayList<Properties> residentials, String CID){
		ArrayList<Properties> censusResid = new ArrayList<Properties>();
		for (Properties entity : residentials) {
				censusResid.add(entity);
		}
		return censusResid;
	}
	
	
	public static double PropertyJustVal( Properties propi, double deltaDEM, int yrs) { 
        double changeVal = propi.liveArea* (  propi.a1* yrs + propi.a3* yrs + propi.a4 * deltaDEM );
//        double risk = propi.AdaptFloodRisk + propi.AdaptationCost + propi.insuranceCost - propi.FloodRisk0;
        double updatedVal  = (propi.CurrentJV + changeVal)  ;
         // -1*propi.a1*propi.AdaptFloodRisk  - propi.AdaptFloodRisk  - propi.AdaptationCost + propi.insuranceCost
        return updatedVal ;
    } 
	
	
	public static double PropertyJustVal2( Properties propi, double deltaDEM, int yrs) { 
        double changeVal = propi.liveArea* (  propi.a1* yrs + propi.a3* yrs + propi.a4 * deltaDEM );
        double risk = propi.FloodRisk  - propi.FloodRisk0;
        double updatedVal  = (propi.CurrentJV + changeVal) - risk ;
         // -1*propi.a1*propi.AdaptFloodRisk  - propi.AdaptFloodRisk  - propi.AdaptationCost + propi.insuranceCost
        return updatedVal ;
    } 
	
	public static ArrayList<Agents> HHCensus( ArrayList<Agents> households, String CID ){
		ArrayList<Agents> censushhs = new ArrayList<Agents>();
		for (Agents hh : households) {
			if (hh.censusID.equals( CID ) ) {
				censushhs.add(hh);
		    }
		}
		return censushhs;
	}
	public static Map<Integer, Agents> mapHouseholds( ArrayList<Agents> households){
		Map <Integer, Agents> hm = new HashMap<Integer, Agents>();
		for (Agents hh : households) {
			hm.put(hh.HouseholdID, hh);
		}
		return hm;
	}
	public static double annualBenefit( double BidPricei ){
		double discountRate = 0.04;
		return BidPricei * ( discountRate * Math.pow( 1 + discountRate, 30) / ( Math.pow( 1 + discountRate, 30) - 1 ) ) ;
	}
	public static double bid( Properties Housei, double income, int multi ){
		double bidPrice = 0;	
		//int randIndex = rand.nextInt(numberOfElements);
		double NResids, Landval, LiveArea, EffectYrs;
		if(Housei.LV == 0){
			Housei.LV = Housei.Justvalue * 0.5;
		}
		if(multi == 1){
			NResids  = 1;
			Landval  = Housei.LV / Housei.NORESUNTS ;
			LiveArea = Housei.liveArea / Housei.NORESUNTS ;
			
		}else{
			NResids  = Housei.NORESUNTS;
			Landval  = Housei.LV ;
			LiveArea = Housei.liveArea ;
		}
		EffectYrs = Housei.EFFYER;
		
		if (income < 60000 ) {
			bidPrice =  -1.067e6  - 2.894e1* NResids + 
						3.0e2* Math.log( Landval + 0.01) +
						2.528e3* Math.log(LiveArea + 0.01) -
						5.607e3* Math.log(EffectYrs + 0.01) +
						1.183e5* Math.log(income + 0.01 ) ;
		} else if (income > 60000 && income < 120000) {
			bidPrice =  -3.315e6  + 1.285e2* NResids +
			        	1.967e1* Math.log( Landval + 0.01) -
			        	2.925e2* Math.log(LiveArea + 0.01) - 
			        	1.361e4* Math.log(EffectYrs + 0.01) +
			       		3.318e5* Math.log(income + 0.01) ;
		} else if (income > 120000 && income < 230000) {
			bidPrice =  -7.223e6  -
						5.288e1* NResids - 
			        	1.965e2* Math.log( Landval + 0.01)  -
			        	6.097e2* Math.log( LiveArea + 0.01) -
			        	5.05e2* Math.log( EffectYrs + 0.01) +
			        	6.572e5* Math.log(income + 0.01) ;
		} else if(income > 230000 && income < 500000 ){
			bidPrice =  -5.064e7  + 
					   	5.095e4* NResids -
		        		2.339e4* Math.log( Landval + 0.01)  -
		        		1.029e6* Math.log( LiveArea + 0.01) +
		        		1.099e6* Math.log( EffectYrs + 0.01) +
		        		4.107e6* Math.log( income + 0.01) ;
			   }
		return bidPrice;
		}
	
//	public static ArrayList<String> readCensus(ArrayList<Properties> ResidBidPrice) {
//		ArrayList<String> entities = new ArrayList<String>();
//		for(Properties residi : ResidBidPrice){
//			if( !entities.contains(residi.censusID) ){
//				entities.add(residi.censusID);
//			}
//		}
//		System.out.println("Finished reading Census! " + entities.size());
//		return entities;
//	}
	
	
	
	public static ArrayList<Properties> readProperty(String csvFile, double popPercent, int Zonelabel) {
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		ArrayList<Properties> entities = new ArrayList<Properties>();
		try {
			br = new BufferedReader(new FileReader(csvFile));
			
			br.readLine();
			int counti = 0;
			while ((line = br.readLine()) != null) {
	                String[] dataline = line.split(cvsSplitBy);
//	                printArray(dataline) ;
	                double rand_p = Math.random();
	                
	                String zonei = dataline[4]  ;
	                int Azone = zonei.indexOf('A');
	    			int Vzone = zonei.indexOf('V');
	    			int floodZone = 0;
	    			if( Azone >= 0 || Vzone >= 0) {
	    				floodZone = 1;
	    			} 
	                if( rand_p < popPercent ){
	                	if( Zonelabel == 1 ) {
	                		if( floodZone == 1 ) {
	                			Properties propertyi = new Properties(dataline) ;
	    	                	propertyi.FID = counti;
	    			            entities.add(propertyi);
	    			            counti++;
	                		}
	                	}else {
	                		Properties propertyi = new Properties(dataline) ;
		                	propertyi.FID = counti;
				            entities.add(propertyi);
				            counti++;
	                	}
	                	
	                	
	                }
	           }
			}catch (FileNotFoundException e){
				e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (br != null) {
	                try {
	                    br.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
//		System.out.println("Finished reading residential parcels!");
		return entities;
	}
	
//	public static ArrayList<Agents> readAgent(String csvFile) {
//		BufferedReader br = null;
//		String line = "";
//		String cvsSplitBy = ",";
//		ArrayList<Agents> households = new ArrayList<Agents>();
//		try {
//			br = new BufferedReader(new FileReader(csvFile));
//			br.readLine();
//			while ((line = br.readLine()) != null) {
//	                String[] dataline = line.split(cvsSplitBy);
//	                Agents householdi = new Agents(dataline) ;
//	                households.add(householdi);
//	            }
//			}catch (FileNotFoundException e){
//				e.printStackTrace();
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        } finally {
//	            if (br != null) {
//	                try {
//	                    br.close();
//	                } catch (IOException e) {
//	                    e.printStackTrace();
//	                    }
//	               }
//	        }
//		System.out.println("Finished reading households !");
//		return households;
//	}
	public static void writeData(ArrayList<BidPrice> ResidBidPrice) {
		CsvFileWriter.writeCsvFile(ResidBidPrice );
		System.out.println("Finished processing!");
	}
	
	public static double[] windDamageFunc(int ptype, double windspeed, double price){
		double[] damageProbability = {0.0, 0.0, 0.0, 0.0, 0.0};
		if(ptype == 1|| ptype == 4 && price < 500000){
			damageProbability[0]   = 1 / (1 + Math.exp(-0.0448*windspeed + 4.7456));
			damageProbability[1]   = 1 / (1 + Math.exp(-0.0557*windspeed + 7.177));
			damageProbability[2]   = 1 / (1 + Math.exp(-0.0602*windspeed + 8.663));
			damageProbability[3]   = 1 / (1 + Math.exp(-0.0581*windspeed + 7.756));
		}else if(ptype == 2 ||ptype == 3 ||ptype == 8 ||ptype == 9 && price < 600000){
			damageProbability[0]        = 1 / (1 + Math.exp(-0.0452*windspeed + 4.8307));
			damageProbability[1]       = 1 / (1 + Math.exp(-0.0469*windspeed + 6.0155));
			damageProbability[2]  = 1 / (1 + Math.exp(-0.0351*windspeed + 5.2818));
			damageProbability[3] = 1 / (1 + Math.exp(-0.0316*windspeed + 5.193));
		}else{
			
			damageProbability[0]   = 0;
			damageProbability[1]   = 1 / (1 + Math.exp(-0.0313*windspeed + 5.2811));
			damageProbability[2]   = 1 / (1 + Math.exp(-0.0349*windspeed + 6.2687));
			damageProbability[3]   = 1 / (1 + Math.exp(-0.0364*windspeed + 7.0583));
			
		}
		return damageProbability;
	}
	
	public static double MeanwindDamageP(int ptype, double windspeed, double price){ // for each windspeed
		double ExpectProb =0;
		double[] damageP      = {0.05, 0.15, 0.25, 0.5, 0.75};
		double[] damageProbability = {0.0, 0.0, 0.0, 0.0, 0.0};
		damageProbability = windDamageFunc( ptype, windspeed, price);
		if(windspeed < 75){
			ExpectProb =  0 ;
		}else if(windspeed < 120){
			ExpectProb =  damageP[0] * damageProbability[0] ;
		}else if(windspeed > 120 && windspeed < 160){
			ExpectProb =  damageP[1] * damageProbability[1] ;
		}else if(windspeed > 160 && windspeed < 220){
			ExpectProb =  damageP[2] * damageProbability[2] ;
		}else{
			ExpectProb =  damageP[3] * damageProbability[3] ;
		}
		
		if(ExpectProb < 0){
			System.out.println("Error Wind Loss probability is " + +windspeed);	
		}
		return ExpectProb;
	}
	
	public static double WindReductFract( double windspeed){
		double[] damageP = {0.05, 0.1, 0.15, 0.25, 0.5};
		double[] damageFract = {0.0, 0.0, 0.0, 0.0, 0.0};

		damageFract[0]   = 0;
		damageFract[1]   = 0;
		damageFract[2]   = 1 / (1 + Math.exp(-0.0349*windspeed + 6.2687));
		damageFract[3]   = 1 / (1 + Math.exp(-0.0364*windspeed + 7.0583));
		
		double ExpectDamage;
		if(windspeed < 120){
			ExpectDamage =  damageP[0]*damageFract[0] ;
		}else if(windspeed > 120 && windspeed < 160){
			ExpectDamage =  damageP[1]*damageFract[1] ;
			
		}else if(windspeed > 160 && windspeed < 240){
			
			ExpectDamage =  damageP[2]*damageFract[2] ;
		}else{
			ExpectDamage =  damageP[3]*damageFract[3] ;
		}
		
		return ExpectDamage;
		
	}
	
	
	public static double surgeHeightF(double returnP, double fxmin, double xi0, double beta0){
		double surge2;
		surge2 = (fxmin + ( (Math.pow( returnP, -xi0) - 1)*beta0 / xi0 )  )/  0.3048;
		if(surge2 < 0){surge2 = 0.0;}
		return surge2;
	}
	
	public static double WindSpeedF(double returnP){
		double WindSpeed;
		WindSpeed = 106.117481 + ( (Math.pow( returnP, -1 * 1.017104) - 1)*1.393671 / 1.017104 ) ;
		WindSpeed = WindSpeed / 1.609;
		if(WindSpeed > 160){
			WindSpeed = 160;
		}
		
//		if(WindSpeed < 75){
//			WindSpeed = 0;
//		}
		return WindSpeed;
	}
	
	
	public static double[] WindRiskF( int Ptype, int Nresid, double NearW, double[] winds, double currentValue, double shutterPay) {
//		double k =  0.1295860, 3.3788187, 0.2727094;
		double  TotalRisk[] = {0, 0};
		double loss;
//		double[] probs     = {0.1, 0.05, 0.02, 0.01}; // 0.10, 0.05, 0.02, 0.01, 0.005
		double[] AdaptWindDamage = {0.0, 0.0, 0.0, 0.0, 0.0};
		if(shutterPay > 0){
			AdaptWindDamage[0] = WindReductFract(  winds[1] ) ;
			AdaptWindDamage[1] = WindReductFract(  winds[2] ) ;
			AdaptWindDamage[2] = WindReductFract(  winds[3] ) ;
			AdaptWindDamage[3] = WindReductFract(  winds[4] ) ;
		}else{
			AdaptWindDamage[0] = MeanwindDamageP( Ptype, winds[1], currentValue);
			AdaptWindDamage[1] = MeanwindDamageP( Ptype, winds[2], currentValue);
			AdaptWindDamage[2] = MeanwindDamageP( Ptype, winds[3], currentValue);
			AdaptWindDamage[3] = MeanwindDamageP( Ptype, winds[4], currentValue);
}
		
		double damageProb = probs[4]*AdaptWindDamage[3]  ;
		
		loss = currentValue * damageProb;
		TotalRisk[1] = loss;
		TotalRisk[0] = currentValue * ( AdaptWindDamage[0] *probs[1] + AdaptWindDamage[1] *probs[2]  + 
				AdaptWindDamage[2] *probs[3] + AdaptWindDamage[3] *probs[4]   ) /4 ;
		
//		System.out.println("WindDamage p " + TotalRisk[0]);
		
		return TotalRisk;
	}
	
	public static double FloodRiskPay(Properties residi, double DEM, double[] surgeHeight, double currentprice, double mu){
		double justvalue = currentprice ;
		double FloodRisk = 0.0;
		double unitArea = residi.liveArea / (residi.NOBULDNG);
		if (residi.NOBULDNG <= 0) { unitArea = residi.liveArea;}
		
		FloodRisk =  Properties.FloodRiskF( DEM, surgeHeight, residi.CurrentJV, residi.Ptype,  0, 0.0 , goveAdaptH );
		return FloodRisk;
	}
	
	
	public static void QuantifyRisk(Properties residi, double mu){
		double[] Surge  = {0.00, 0.00, 0.00, 0.00, 0.0};
		double[] WindSpeed  = {0.00, 0.00, 0.00, 0.00, 0.0};
		for(int i = 0; i < probs.length; i ++){
			Surge[i] 		= surgeHeightF(probs[i], mu, residi.xi0, residi.beta0);
			WindSpeed[i] 	= WindSpeedF( probs[i] );
		} 
			
		residi.FloodRisk 	=  FloodRiskPay( residi, residi.elevation, Surge,  residi.CurrentJV, mu );
//		residi.WindRisk  	=  WindRiskF(  residi.Ptype, residi.NORESUNTS, residi.nearWater, 
//										WindSpeed, residi.CurrentJV , 0.0)[0];
//		residi.totaRisk    	=  residi.FloodRisk + residi.WindRisk;
	}
	
	
	public static int MinCosts( double[] allcosts){
		int index = 0;
		double temp = Double.POSITIVE_INFINITY;
		for (int i=0; i< allcosts.length; i++) {
			if (allcosts[i] < temp) {
				temp = allcosts[i];
		        index = i;
		    }
		}
		return index;
	}
	
	
	public static double findSum(double[] array) {
		double sum = 0;
	    for (double value : array) {
	        sum += value;
	    }
	    return sum;
	}
	
	public static int findSmallest0( double[] arr ){
		double min = arr[0];
		int minIndex1 = 0;
		int minIndex2 = 0;
		for(int i = 0; i < arr.length; i++){
			if (arr[i] < min){
				min = arr[i];
				//record the indices of the new min
				minIndex1 = i;
		    }
		}
		int idxs = minIndex1;
		return idxs;
	}
	
	public static int[] findSmallest( double[][] arr ){
		double min = arr[0][0];
		int minIndex1 = 0;
		int minIndex2 = 0;
		for(int i = 0; i < arr.length; i++){
		    for ( int j = 0; j < arr[i].length; j++ ) {		        
		        if (arr[i][j] < min){
		            min = arr[i][j];
		            //record the indices of the new min
		            minIndex1 = i;
		            minIndex2 = j;
		        }
		    }
		}
		int[] idxs = {minIndex1, minIndex2};
		return idxs;
	}
	
	public static double discountFactor( double arr, int starYear, int endYear, double distR ){
		double factor = 0;
		for(int i = starYear; i < endYear; i++){
			factor = factor + 1 / ( 1 +  Math.pow(1+distR, i) );
		}
		return arr*factor;
	}
	
	
	
	public static double[] MitigationEval3(Properties residi, ArrayList<Integer> AdaptMeasures, ArrayList<Double> AdaptHeight, ArrayList<Double> AdaptCosts, double[] inputs){
				
		int years   	= 	(int) inputs[0] ; 
		double distR   	= 	inputs[1] ;
		double paraB   	= 	inputs[2] ;
		int deltaT  	= 	(int) inputs[3] ;
		double muyi  	= 	 inputs[4] ; 
		double xiyi  	= 	 inputs[5] ;
		double betayi  	= 	 inputs[6] ;
		
		int N 				  = (int) years / deltaT;
		int Nmeasure = AdaptMeasures.size();
//	    Arrays.fill(FloodRisks, 0); 
	    double rate = 0.0;;
	    double slrH = 0.0;
	    int islot = 0;
		
	    double[][] TotalCosts 	= new double[N][Nmeasure];
	    double[][] PrivateCosts = new double[N][Nmeasure];
	    
	    double[][] FloodRisks  		= new double[Nmeasure][N];
	    double[] FloodRisks0  	= new double[N];
	    double para_b = 0.000003;
//	    Arrays.fill(FloodRisks, 0); 
		double friski = 0 ;
		double propertyVal 	= residi.CurrentJV;
		
		double[] heights  = {0.00, 0.00, 0.00, 0.00, 0.0};
		for(int i = 0; i < probs.length; i ++){
			heights[i] 	= surgeHeightF(probs[i], muyi, xiyi, betayi) ;
		} 
//		System.out.println( " heights are " + Arrays.toString( heights ) + muyi + xiyi+ betayi  );
		double[] Surge  		= {0.00, 0.00, 0.00, 0.00, 0.0};
		for(int yri = years - deltaT; yri >= 0; yri = yri - deltaT){
	    	islot = (int) (yri / deltaT) ;
//	    	if(yri >= 1 && paraB > 0){
//				rate = ( 0.0017*( yri +10) + 0.000003*Math.pow( (yri  +10), 2) )  ;
//			}else {
//				rate = 0 ;	
//			}
//			slrH = rate *3.28;
//			for(int ii = 0; ii < Surge.length; ii ++){
//				Surge[ii] 	= heights[ii] + slrH;
//			}
			
//			System.out.println( "Year " + yri + " SLR height " + Arrays.toString( Surge ) + muyi + xiyi+ betayi  );
			double propertyHeight = residi.elevation ;
			if( propertyVal < 0.0 ){
				propertyVal = 0.0 ;
			}
			int maxEnd = Math.max(yri+30, years) ;
			
			for( int i = yri; i >= 0; i = i-1 ){
				
				for(int ii = 0; ii < Surge.length; ii ++){
					Surge[ii] 	= heights[ii] + ( 0.0017*( i +10) + para_b*Math.pow( (i  +10), 2) )*3.28;
				}
				
				
				friski = Properties.FloodRiskF( propertyHeight , 
						Surge, propertyVal, residi.Ptype,  0, 0.0 , goveAdaptH ) ;
					
				FloodRisks0[islot] = FloodRisks0[islot] + friski;
			}
			
			for( int i = yri; i < maxEnd; i ++ ){
				
				for(int ii = 0; ii < Surge.length; ii ++){
					Surge[ii] 	= heights[ii] + ( 0.0017*( i +10) + para_b*Math.pow( (i  +10), 2) )*3.28;
				}
				
				for(int measureid = 0; measureid < Nmeasure; measureid ++  ) {
					if( measureid < 5 ) {
						friski = Properties.FloodRiskF( propertyHeight + AdaptHeight.get(measureid), 
								Surge, propertyVal, residi.Ptype,  AdaptMeasures.get(measureid), 0.0 , goveAdaptH ) ;
					}else {
						friski = Properties.FloodRiskF( propertyHeight , 
								Surge, propertyVal, residi.Ptype,  AdaptMeasures.get(measureid), AdaptHeight.get(measureid) , goveAdaptH) ;
					}
					FloodRisks[measureid][islot]  = FloodRisks[measureid][islot]  + friski ;
				}
			}
//			System.out.println( islot ); * 1 / ( 1 +  Math.pow(1+distR, i)  )
	    }
//		System.out.println( "Cum risk0 " + Arrays.toString( FloodRisks0 )  );
//		System.out.println( "InvCum risk10 " + Arrays.toString( FloodRisks[0] )  );

		for(int yri2 = 0; yri2 < years; yri2 = yri2 + deltaT){
	    	islot = (int) (yri2 / deltaT) ;
//	    	System.out.println("islot "+ islot);
//	    	TotalCosts[islot][0] 	= 	FloodRisks0[N-1] ;
//	    	PrivateCosts[islot][0] 	= 	0;
	    	double inflation = 0.0;
	    	for(int measureid = 0; measureid < Nmeasure; measureid ++  ) {
	    		
	    		if( measureid < 5) {
					inflation = inflation1;
				}else if( measureid < 7 ) {
					inflation = inflation2;
				}else {
					inflation = inflation3;
				}
	    		if(measureid == 0  ) {
	    			TotalCosts[islot][measureid] 	= 	FloodRisks0[N-1]  ;
	    		}else {
	    			if( islot > 0 ) {
	    				TotalCosts[islot][measureid] 	= 	FloodRisks0[islot -1] +
								FloodRisks[measureid][islot] +
								AdaptCosts.get(measureid)* 1 / ( 1 +  Math.pow(1+distR, yri2) ) +  
								discountFactor(AdaptCosts.get(measureid)*inflation, yri2, years, distR) ;
	    			}else {
	    				TotalCosts[islot][measureid] 	= 	FloodRisks[measureid][islot] +
								AdaptCosts.get(measureid)* 1 / ( 1 +  Math.pow(1+distR, yri2) ) +  
								discountFactor(AdaptCosts.get(measureid)*inflation, yri2, years, distR) ;
	    			}
	    		}
	    		
	    				
	    		PrivateCosts[islot][measureid] 	= 	AdaptCosts.get(measureid)* 1 / ( 1 +  Math.pow(1+distR, yri2) ) +  
	    													discountFactor(AdaptCosts.get(measureid)*inflation, yri2, years, distR) ;
	    	}
		}
	    int iid = findSmallest(TotalCosts)[0];
	    int jid = findSmallest(TotalCosts)[1];
	    
	    double AnnualCosts = PrivateCosts[iid][jid] / ( 30.0 );
	    
	    double[] results = { Double.valueOf(iid), Double.valueOf(  AdaptMeasures.get(jid) ), AnnualCosts, Double.valueOf(  AdaptHeight.get(jid) )   } ; 
	    
//	    System.out.print("Adaptation output is " + Arrays.toString( results )   + "\n");
	    return results;
	}
	
	
	public static void AdaptDecision( ArrayList<Properties> ResidBidPrice, double[] parayi , Government gov1) throws IOException{
		int years = (int)parayi[0];
		double distR = parayi[1];
		double paraB = parayi[2];
		int deltaT = (int)parayi[3];
		double muyi = parayi[4];
		double xiyi = parayi[5];
		double betayi = parayi[6];
		
		Iterator<Properties> iter = ResidBidPrice.iterator();
		int N = ResidBidPrice.size() ;
		double[][] adaptYM = new double[N][3];
		int kth = 0;
		gov1.goveAdaptH = 0.0;
		goveAdaptH = gov1.goveAdaptH;
//		System.out.print("Scenario 1 govenment Adaptation is " + goveAdaptH + "\n");
		double[][] AnnualCosts = new double[3][(int) years/deltaT];
		while(iter.hasNext()){
			Properties housei = iter.next();
			
//			QuantifyRisk(housei, muyi);
			housei.houseElevation( 0);
			
			ArrayList<Double>  AdaptCosts 	= new ArrayList<Double>();
		    ArrayList<Integer> MeasureList 	= new ArrayList<Integer>();
		    ArrayList<Double> AdaptHeight 	= new ArrayList<Double>();
			
			housei.dryproofCost2 = Properties.DryProofingCost( housei.Ptype, housei.liveArea, 2.0);
			housei.dryproofCost4 = Properties.DryProofingCost( housei.Ptype, housei.liveArea, 4.0);
			housei.dryproofCost6 = Properties.DryProofingCost( housei.Ptype, housei.liveArea, 6.0);
			
			housei.wetproofCost2 = Properties.WetProofingCost( housei.Ptype, housei.NORESUNTS, housei.liveArea, 2.0);
			housei.wetproofCost4 = Properties.WetProofingCost( housei.Ptype, housei.NORESUNTS, housei.liveArea, 4.0);
			housei.wetproofCost6 = Properties.WetProofingCost( housei.Ptype, housei.NORESUNTS, housei.liveArea, 6.0);
			
			double[] Surge  = {0.00, 0.00, 0.00, 0.00, 0.0};
			for(int i = 0; i < probs.length; i ++){
				Surge[i] 	= surgeHeightF(probs[i], muyi, xiyi, betayi) ;
			} 
			
			
			housei.FloodRisk0 	=  Properties.FloodRiskF( housei.elevation, 
												Surge, housei.CurrentJV, housei.Ptype,  0 , 0.0 , 0.0 );
			
			AdaptCosts.add( 0.0)	;
			AdaptCosts.add( housei.elevationCost2 )	;
			AdaptCosts.add( housei.elevationCost4 )	;
			AdaptCosts.add( housei.elevationCost6 )	;
			AdaptCosts.add( housei.elevationCost8 )	;
			AdaptCosts.add( housei.wetproofCost2  )	;
			AdaptCosts.add( housei.wetproofCost4  )	;
			AdaptCosts.add( housei.dryproofCost2  )	;
			AdaptCosts.add( housei.dryproofCost4 )	;
			
			
			MeasureList.add( 0 )	;
			MeasureList.add( 1 )	;
			MeasureList.add( 2 )	;
			MeasureList.add( 3 )	;
			MeasureList.add( 4 )	;
			MeasureList.add( 5 )	;
			MeasureList.add( 6 )	;
			MeasureList.add( 7 )	;
			MeasureList.add( 8 )	;
			
			
			AdaptHeight.add( 0.0 )	;
			AdaptHeight.add( 2.0 )	;
			AdaptHeight.add( 4.0 )	;
			AdaptHeight.add( 6.0 )	;
			AdaptHeight.add( 8.0 )	;
			AdaptHeight.add( 2.0 )	;
			AdaptHeight.add( 4.0 )	;
			AdaptHeight.add( 2.0 )	;
			AdaptHeight.add( 4.0 )	;
			
			
			
			double[] inputArray = {years, distR, paraB, deltaT, muyi, xiyi,betayi} ;
			
			double[] arr 			= MitigationEval3(housei, MeasureList, AdaptHeight, AdaptCosts, inputArray);

			
			adaptYM[kth][0] 		= (int) arr[0] * deltaT ;
			adaptYM[kth][1] 		= (int) arr[1] ;
			adaptYM[kth][2] 		= (int) arr[2] ;
			kth = kth +1;
			
			housei.adaptYear 		= (int) arr[0]* deltaT ;
			housei.FloodMeasure 	= (int) arr[1] ;
			
//			System.out.print("Scenario 1 Adaptation is " + Arrays.toString( arr )   + "\n");
			
			
			
			if( housei.FloodMeasure > 0 && housei.FloodMeasure <= 4 ){
				if(  housei.FloodMeasure == 1 ){
					housei.eleHeight = 2.0;
				}else if( housei.FloodMeasure == 2 ){
					housei.eleHeight = 4.0;
				}else if( housei.FloodMeasure == 3 ){
					housei.eleHeight = 6.0;
				}else{
					housei.eleHeight = 8.0;
				}
				housei.FloodMeasure = 1;
				housei.adaptH = 0.0 ;
			}else if( housei.FloodMeasure > 4 && housei.FloodMeasure <= 6 ){
				if(  housei.FloodMeasure == 5 ){
					housei.adaptH = 2.0;
				}else if( housei.FloodMeasure == 6 ){
					housei.adaptH = 4.0;
				}else {
					housei.adaptH = 6.0;
				}
				housei.FloodMeasure = 2;
				housei.eleHeight = 0.0;
			}else if( housei.FloodMeasure > 6  ){
				if(  housei.FloodMeasure == 8 ){
					housei.adaptH = 2.0;
				}else if( housei.FloodMeasure == 9 ){
					housei.adaptH = 4.0;
				}else {
					housei.adaptH = 6.0;
				}
				housei.FloodMeasure = 3;
				housei.eleHeight = 0.0;
			}else {
				if( housei.FloodMeasure == 0 ) {
					housei.eleHeight = 0.0;
					housei.adaptH = 0.0 ;
					
				}
			}
			
			
			if(   housei.FloodMeasure == 1 && housei.eleHeight == 0) {
		    	
			    System.out.print("arr are " + Arrays.toString( arr )   + "\n");
			    
		    }
			
			
			housei.AdaptationCost 	= (int) (arr[2] * 30) ;
			housei.FloodAdaptPay 	= (int) arr[2] ; 
			
			if( housei.FloodMeasure == 1){
				housei.FloodRisk 	=  Properties.FloodRiskF( housei.elevation + housei.eleHeight, 
											Surge, housei.CurrentJV, housei.Ptype,  
											housei.FloodMeasure, housei.adaptH , goveAdaptH );
			}else{
				housei.FloodRisk 	=  Properties.FloodRiskF( housei.elevation, 
											Surge, housei.CurrentJV, housei.Ptype,  
											housei.FloodMeasure, housei.adaptH , goveAdaptH);
			}
		}
			
//		for( int k = 0; k < years; k= k + deltaT ){
//			int slotk = (int) k / deltaT;
//			AnnualCosts[0][slotk] = k ;	
//			for( int k2 = 0; k2 < N; k2++ ){
//				if( adaptYM[k2][0] == k && adaptYM[k2][1] > 0){
//					AnnualCosts[1][slotk] = AnnualCosts[1][slotk] + adaptYM[k2][2] ;
//					AnnualCosts[2][slotk] = AnnualCosts[2][slotk] + 1.0 ;
//				}
//			}
//		}
//		
//		for( int k = deltaT; k < years; k= k + deltaT ){
//			int slotk = (int) k / deltaT;
//			AnnualCosts[1][slotk] = AnnualCosts[1][slotk] + AnnualCosts[1][slotk - 1] ;
//			AnnualCosts[2][slotk] = AnnualCosts[2][slotk] + AnnualCosts[2][slotk - 1] ;
//		}
		
		
//		String bidPth0 		= path0 + "\\Annualcosts" + slr + ".csv";
//		CsvFileWriter.writeAdaptInfo( AnnualCosts, bidPth0 , true);	
//		String bidPth 		= path0 + "\\totalcosts" + slr + ".csv";;
//		CsvFileWriter.writeAdaptInfo( adaptYM, bidPth, true );
	}
	
	public static void AdaptDecision2( ArrayList<Properties> ResidBidPrice, double[] parayi , Government gov1) throws IOException{
		int years = (int)parayi[0];
		double distR = parayi[1];
		double paraB = parayi[2];
		int deltaT = (int)parayi[3];
		double muyi 	= parayi[4];
		double xiyi 	= parayi[5];
		double betayi 	= parayi[6];
		
		Iterator<Properties> iter = ResidBidPrice.iterator();
		int N = ResidBidPrice.size() ;
		double[][] adaptYM = new double[N][3];
		int kth = 0;
		double[][] AnnualCosts = new double[3][(int)years/deltaT];
		gov1.goveAdaptH = 0.0;
//		System.out.print("Scenario 2 govenment Adaptation is " + goveAdaptH + "\n");
		goveAdaptH = gov1.goveAdaptH;
		while(iter.hasNext()){
			Properties housei = iter.next();
			
//			if( kth % 10000 == 0 ){
//				System.out.print("household id is " + kth + "\n");
//			}
			
			double willPay 	  = housei.bidIncome * housei.payratio * 10;
			
//			System.out.print("willPay is " + willPay + "\n");
			
			double mu;
			housei.houseElevation( 0);
			
	        ArrayList<Double>  AdaptCosts 	= new ArrayList<Double>();
	        ArrayList<Integer> MeasureList 	= new ArrayList<Integer>();
	        ArrayList<Double> AdaptHeight 	= new ArrayList<Double>();
	        
	        housei.dryproofCost2 = Properties.DryProofingCost( housei.Ptype, housei.liveArea, 2.0);
	        housei.dryproofCost4 = Properties.DryProofingCost( housei.Ptype, housei.liveArea, 4.0);
			housei.dryproofCost6 = Properties.DryProofingCost( housei.Ptype, housei.liveArea, 6.0);
			housei.wetproofCost2 = Properties.WetProofingCost( housei.Ptype, housei.NORESUNTS, housei.liveArea, 2.0);
			housei.wetproofCost4 = Properties.WetProofingCost( housei.Ptype, housei.NORESUNTS, housei.liveArea, 4.0);
			housei.wetproofCost6 = Properties.WetProofingCost( housei.Ptype, housei.NORESUNTS, housei.liveArea, 6.0);
	        
			AdaptCosts.add( 0.0 ) ;
        	MeasureList.add( 0 ) ;
        	AdaptHeight.add( 0.0 ) ;
        	
			if( willPay > housei.elevationCost2 ) {
				AdaptCosts.add( housei.elevationCost2 ) ;
	        	MeasureList.add( 1 ) ;
	        	AdaptHeight.add( 2.0 ) ;
	        }
	        if( willPay > housei.elevationCost4 ) {
	        	AdaptCosts.add( housei.elevationCost4 ) ;
	        	MeasureList.add( 2 ) ;
	        	AdaptHeight.add( 4.0 ) ;
	        }
	        if( willPay > housei.elevationCost6 ) {
	        	AdaptCosts.add( housei.elevationCost6 ) ;
	        	MeasureList.add( 3 ) ;
	        	AdaptHeight.add( 6.0 ) ;
	        }
	        if( willPay > housei.elevationCost8 ) {
	        	AdaptCosts.add( housei.elevationCost8 ) ;
	        	MeasureList.add( 4 ) ;
	        	AdaptHeight.add( 8.0 ) ;
	        }
			
			if( willPay > housei.wetproofCost2 ) {
				AdaptCosts.add( housei.wetproofCost2 ) ;
	        	MeasureList.add( 5 ) ;
	        	AdaptHeight.add( 2.0 ) ;
	        }
	        if( willPay > housei.wetproofCost4 ) {
	        	AdaptCosts.add( housei.wetproofCost4 ) ;
	        	MeasureList.add( 6 ) ;
	        	AdaptHeight.add( 4.0 ) ;
	        }
	        if( willPay > housei.dryproofCost2 ) {
	        	AdaptCosts.add( housei.dryproofCost2 ) ;
	        	MeasureList.add( 7 ) ;
	        	AdaptHeight.add( 2.0 ) ;
	        }
	        if( willPay > housei.dryproofCost4 ) {
	        	AdaptCosts.add( housei.dryproofCost4 ) ;
	        	MeasureList.add( 8 ) ;
	        	AdaptHeight.add( 4.0 ) ;
	        }
			
			double[] Surge  = {0.00, 0.00, 0.00, 0.00, 0.0};
			for(int i = 0; i < probs.length; i ++){
				Surge[i] 	= surgeHeightF(probs[i], muyi, xiyi, betayi) ;
			} 
			
			housei.FloodRisk0 	=  Properties.FloodRiskF( housei.elevation, Surge, housei.CurrentJV, housei.Ptype,  0 , 0.0 , 0.0 );
			
			
			double[] inputArray = {years, distR, paraB, deltaT, muyi, xiyi,betayi} ;
			
			double[] arr 			= MitigationEval3(housei, MeasureList, AdaptHeight, AdaptCosts, inputArray);
			
			
			adaptYM[kth][0] 		= (int) arr[0] * deltaT ;
			adaptYM[kth][1] 		= (int) arr[1] ;
			adaptYM[kth][2] 		= (int) arr[2] ;
			kth = kth +1;
			
			housei.adaptYear 		= (int) arr[0]* deltaT ;
			housei.FloodMeasure 	= (int) arr[1] ;
			housei.adaptH 			= 0.0 ;
			
			
//			System.out.print("Scenario 2 Adaptation is " + Arrays.toString( arr )   + "\n");
			
			
			if( housei.FloodMeasure > 0 && housei.FloodMeasure < 5 ){
				if(  housei.FloodMeasure == 1 ){
					housei.eleHeight = 2.0;
				}else if( housei.FloodMeasure == 2 ){
					housei.eleHeight = 4.0;
				}else if( housei.FloodMeasure == 3 ){
					housei.eleHeight = 6.0;
				}else if( housei.FloodMeasure == 4 ){
					housei.eleHeight = 8.0;
				}
				housei.FloodMeasure = 1;
			}else if( housei.FloodMeasure > 4 && housei.FloodMeasure < 7 ){
				if(  housei.FloodMeasure == 5 ){
					housei.adaptH = 2.0;
				}else if( housei.FloodMeasure == 6 ){
					housei.adaptH = 4.0;
				}else {
					housei.adaptH = 6.0;
				}
				housei.FloodMeasure = 2;
				housei.eleHeight = 0.0;
			}else if( housei.FloodMeasure > 6  ){
				if(  housei.FloodMeasure == 7 ){
					housei.adaptH = 2.0;
				}else if( housei.FloodMeasure == 8 ){
					housei.adaptH = 4.0;
				}else {
					housei.adaptH = 6.0;
				}
				housei.FloodMeasure = 3;
				housei.eleHeight = 0.0;
			}else {
				if( housei.FloodMeasure == 0 ) {
					housei.eleHeight = 0.0;
					housei.adaptH = 0.0 ;
					
				}
				
			}
			
			housei.AdaptationCost 	= (int) (arr[2] * 30) ;
			housei.FloodAdaptPay 	= (int) arr[2] ; 
			
			if( housei.FloodMeasure == 1){
				housei.FloodRisk 	=  Properties.FloodRiskF( housei.elevation + housei.eleHeight, 
											Surge, housei.CurrentJV, housei.Ptype,  
											housei.FloodMeasure, housei.adaptH , goveAdaptH );
			}else{
				housei.FloodRisk 	=  Properties.FloodRiskF( housei.elevation, 
											Surge, housei.CurrentJV, housei.Ptype,  
											housei.FloodMeasure, housei.adaptH , goveAdaptH);
			}
		}	
		
		

		
//		for( int k = 0; k < years; k= k + deltaT ){
//			int slotk = (int) k / deltaT;
//			AnnualCosts[0][slotk] = k ;	
//			for( int k2 = 0; k2 < N; k2++ ){
//				if( adaptYM[k2][0] == k && adaptYM[k2][1] > 0){
//					AnnualCosts[1][slotk] = AnnualCosts[1][slotk] + adaptYM[k2][2] ;
//					AnnualCosts[2][slotk] = AnnualCosts[2][slotk] + 1.0 ;
//				}
//			}
//		}
//		for( int k = deltaT; k < years; k= k + deltaT ){
//			int slotk = (int) k / deltaT;
//			AnnualCosts[1][slotk] = AnnualCosts[1][slotk] + AnnualCosts[1][slotk - 1] ;
//			AnnualCosts[2][slotk] = AnnualCosts[2][slotk] + AnnualCosts[2][slotk - 1] ;
//		}
	}

	public static void AdaptDecision3( ArrayList<Properties> ResidBidPrice, double[] parayi, Government gov1 ) throws IOException{
		
		int years = (int)parayi[0];
		double distR = parayi[1];
		double paraB = parayi[2];
		int deltaT = (int)parayi[3];
		double muyi 	= parayi[4];
		double xiyi 	= parayi[5];
		double betayi 	= parayi[6];
		
		gov1.goveAdaptH = 2.0;
//		System.out.print("Scenario 3 govenment Adaptation is " + goveAdaptH + "\n");
		goveAdaptH = gov1.goveAdaptH;
		Iterator<Properties> iter = ResidBidPrice.iterator();
		int N = ResidBidPrice.size() ;
		double[][] adaptYM = new double[N][3];
		int kth = 0;
		double[][] AnnualCosts = new double[3][(int)years/deltaT];
		while(iter.hasNext()){
			Properties housei = iter.next();	
//			if( kth % 10000 == 0 ){
//				System.out.print("household id is " + kth + "\n");
//			}
			
			double willPay 	  = housei.bidIncome * housei.payratio * 30;
			
			double[] Surge  = {0.00, 0.00, 0.00, 0.00, 0.0};
			for(int i = 0; i < probs.length; i ++){
				Surge[i] 	= surgeHeightF(probs[i], muyi, xiyi, betayi) ;
//				if( Surge[i] < goveAdaptH  ) {
//					Surge[i] = 0.0;
//				}else {
//					Surge[i] = Surge[i] - goveAdaptH;
//				}
				
			} 
//			if( kth % 10000 == 0 ){
//				System.out.print("household id is " + kth + "\n");
//			}
			
			housei.bfeH =  surgeHeightF(probs[3], housei.mu0 - 1.0 *housei.muse0, 
					housei.xi0 - 1.0 *housei.xise0, housei.beta0 - 1.0 *housei.betase0) - housei.elevation + 1;
			if(housei.bfeH < 0) { housei.bfeH = 0; }
			
			housei.houseElevation( 0);
			
	        ArrayList<Double>  AdaptCosts 	= new ArrayList<Double>();
	        ArrayList<Integer> MeasureList 	= new ArrayList<Integer>();
	        ArrayList<Double>  AdaptHeight 	= new ArrayList<Double>();
	        
	        housei.dryproofCost2 = Properties.DryProofingCost( housei.Ptype, housei.liveArea, 2.0);
	        housei.dryproofCost4 = Properties.DryProofingCost( housei.Ptype, housei.liveArea, 4.0);
			housei.dryproofCost6 = Properties.DryProofingCost( housei.Ptype, housei.liveArea, 6.0);
			housei.wetproofCost2 = Properties.WetProofingCost( housei.Ptype, housei.NORESUNTS, housei.liveArea, 2.0);
			housei.wetproofCost4 = Properties.WetProofingCost( housei.Ptype, housei.NORESUNTS, housei.liveArea, 4.0);
			housei.wetproofCost6 = Properties.WetProofingCost( housei.Ptype, housei.NORESUNTS, housei.liveArea, 6.0);

        	if( housei.floodZone == 1 ) {
        		if( housei.bfeH <= 0.0 ) {
            		AdaptCosts.add( 0.0 ) ;
                	MeasureList.add( 0 ) ;
                	AdaptHeight.add( 0.0 ) ;
            	}
        		if( housei.bfeH <= 2.0  ) {
    				AdaptCosts.add( housei.elevationCost2 ) ;
    	        	MeasureList.add( 1 ) ;
    	        	AdaptHeight.add( 2.0 ) ;
    	        }
    	        if(  housei.bfeH <= 4.0 ) {
    	        	AdaptCosts.add( housei.elevationCost4 ) ;
    	        	MeasureList.add( 2 ) ;
    	        	AdaptHeight.add( 4.0 ) ;
    	        }
    	        if(  housei.bfeH <= 6.0) {
    	        	AdaptCosts.add( housei.elevationCost6 ) ;
    	        	MeasureList.add( 3 ) ;
    	        	AdaptHeight.add( 6.0 ) ;
    	        }
    	        if( housei.bfeH > 6.0) {
    	        	AdaptCosts.add( housei.elevationCost8 ) ;
    	        	MeasureList.add( 4 ) ;
    	        	AdaptHeight.add( 8.0 ) ;
    	        }
    	        
    			
        	}else {
        		AdaptCosts.add( 0.0 ) ;
            	MeasureList.add( 0 ) ;
            	AdaptHeight.add( 0.0 ) ;
        		if( willPay > housei.elevationCost2 ) {
    				AdaptCosts.add( housei.elevationCost2 ) ;
    	        	MeasureList.add( 1 ) ;
    	        	AdaptHeight.add( 2.0 ) ;
    	        }
    	        if( willPay > housei.elevationCost4 ) {
    	        	AdaptCosts.add( housei.elevationCost4 ) ;
    	        	MeasureList.add( 2 ) ;
    	        	AdaptHeight.add( 4.0 ) ;
    	        }
    	        if( willPay > housei.elevationCost6 ) {
    	        	AdaptCosts.add( housei.elevationCost6 ) ;
    	        	MeasureList.add( 3 ) ;
    	        	AdaptHeight.add( 6.0 ) ;
    	        }
    	        if( willPay > housei.elevationCost8 ) {
    	        	AdaptCosts.add( housei.elevationCost8 ) ;
    	        	MeasureList.add( 4 ) ;
    	        	AdaptHeight.add( 8.0 ) ;
    	        }
    			if( willPay > housei.wetproofCost2 ) {
    				AdaptCosts.add( housei.wetproofCost2 ) ;
    	        	MeasureList.add( 5 ) ;
    	        	AdaptHeight.add( 2.0 ) ;
    	        }
    	        if( willPay > housei.wetproofCost4 ) {
    	        	AdaptCosts.add( housei.wetproofCost4 ) ;
    	        	MeasureList.add( 6 ) ;
    	        	AdaptHeight.add( 4.0 ) ;
    	        }
    	        if( willPay > housei.dryproofCost2 ) {
    	        	AdaptCosts.add( housei.dryproofCost2 ) ;
    	        	MeasureList.add( 7 ) ;
    	        	AdaptHeight.add( 2.0 ) ;
    	        }
    	        if( willPay > housei.dryproofCost4 ) {
    	        	AdaptCosts.add( housei.dryproofCost4 ) ;
    	        	MeasureList.add( 8 ) ;
    	        	AdaptHeight.add( 4.0 ) ;
    	        }
        	}
        	
        	
			housei.FloodRisk0 	=  Properties.FloodRiskF( housei.elevation, Surge, housei.CurrentJV, housei.Ptype,  0 , 0.0, 0.0  );
			
			double[] inputArray = {years, distR, paraB, deltaT, muyi, xiyi, betayi} ;
			
			double[] arr 			= MitigationEval3(housei, MeasureList, AdaptHeight, AdaptCosts, inputArray);
			
//			System.out.print("Scenario 3 Adaptation is " + Arrays.toString( arr )   + "\n");
			
			adaptYM[kth][0] 		= (int) arr[0] * deltaT ;
			adaptYM[kth][1] 		= (int) arr[1] ;
			adaptYM[kth][2] 		= (int) arr[2] ;
			kth = kth +1;
			
			housei.adaptYear 		=  (int) arr[0]* deltaT ;
			housei.FloodMeasure 	=  (int)arr[1] ;
			housei.adaptH = 0.0 ;
			
			
//			if( housei.FloodMeasure > 0 ) {
//				
//				System.out.print("Scenario 3 Adaptation is " + Arrays.toString( arr )   + "\n");
//				
//			}
			
			if( housei.FloodMeasure > 0 && housei.FloodMeasure < 5 ){
				if(  housei.FloodMeasure == 1 ){
					housei.eleHeight = 2.0;
				}else if( housei.FloodMeasure == 2 ){
					housei.eleHeight = 4.0;
				}else if( housei.FloodMeasure == 3 ){
					housei.eleHeight = 6.0;
				}else if( housei.FloodMeasure == 4 ){
					housei.eleHeight = 8.0;
				}
				housei.FloodMeasure = 1;
			}else if( housei.FloodMeasure > 4 && housei.FloodMeasure < 7 ){
				if(  housei.FloodMeasure == 5 ){
					housei.adaptH = 2.0;
				}else if( housei.FloodMeasure == 6 ){
					housei.adaptH = 4.0;
				}else {
					housei.adaptH = 6.0;
				}
				housei.FloodMeasure = 2;
				housei.eleHeight = 0.0;
			}else if( housei.FloodMeasure > 6  ){
				if(  housei.FloodMeasure == 7 ){
					housei.adaptH = 2.0;
				}else if( housei.FloodMeasure == 8 ){
					housei.adaptH = 4.0;
				}else {
					housei.adaptH = 6.0;
				}
				housei.FloodMeasure = 3;
				housei.eleHeight = 0.0;
			}else {
				if( housei.FloodMeasure == 0 ) {
					housei.eleHeight = 0.0;
					housei.adaptH = 0.0 ;
					
				}
				
			}
			
			housei.AdaptationCost 	= arr[2] * 30.0 ;
			housei.FloodAdaptPay 	= arr[2] ; 
			if( housei.FloodMeasure == 1){
				housei.FloodRisk 	=  Properties.FloodRiskF( housei.elevation + housei.eleHeight, 
						housei.Surge, housei.CurrentJV, housei.Ptype,  
											housei.FloodMeasure, housei.adaptH , goveAdaptH );
			}else{
				housei.FloodRisk 	=  Properties.FloodRiskF( housei.elevation, 
						housei.Surge, housei.CurrentJV, housei.Ptype,  
											housei.FloodMeasure, housei.adaptH , goveAdaptH);
			}
		}
			
//		for( int k = 0; k < years; k= k + deltaT ){
//			int slotk = (int) k / deltaT;
//			AnnualCosts[0][slotk] = k ;	
//			for( int k2 = 0; k2 < N; k2++ ){
//				if( adaptYM[k2][0] == k && adaptYM[k2][1] > 0){
//					AnnualCosts[1][slotk] = AnnualCosts[1][slotk] + adaptYM[k2][2] ;
//					AnnualCosts[2][slotk] = AnnualCosts[2][slotk] + 1.0 ;
//				}
//			}
//		}
//		for( int k = deltaT; k < years; k= k + deltaT ){
//			int slotk = (int) k / deltaT;
//			AnnualCosts[1][slotk] = AnnualCosts[1][slotk] + AnnualCosts[1][slotk - 1] ;
//			AnnualCosts[2][slotk] = AnnualCosts[2][slotk] + AnnualCosts[2][slotk - 1] ;
//		}
	}
	
	public static void AdaptDecision4( ArrayList<Properties> ResidBidPrice, double[] parayi, Government gov1 ) throws IOException{

		int years = (int)parayi[0];
		double distR = parayi[1];
		double paraB = parayi[2];
		int deltaT = (int)parayi[3];
		double muyi 		= parayi[4];
		double xiyi 		= parayi[5];
		double betayi 		= parayi[6];
		
		gov1.goveAdaptH = 6.0;
		goveAdaptH = gov1.goveAdaptH;
		Iterator<Properties> iter = ResidBidPrice.iterator();
		int N = ResidBidPrice.size() ;
		double[][] adaptYM = new double[N][3];
		int kth = 0;
//		double[][] AnnualCosts = new double[3][(int)years/deltaT];
		while(iter.hasNext()){
			Properties housei = iter.next();			
			double[] Surge  = {0.00, 0.00, 0.00, 0.00, 0.0};
			for(int i = 0; i < probs.length; i ++){
				Surge[i] 	= surgeHeightF(probs[i], muyi, xiyi, betayi) ;
			} 

			housei.bfeH =  surgeHeightF(probs[3], muyi + 1 *housei.muse0, 
					housei.xi0 + 1 *housei.xise0, housei.beta0 + 1 *housei.betase0) - housei.elevation + 1;
			if(housei.bfeH < 0) { housei.bfeH = 0; }
			
			double willPay 	  = housei.bidIncome * housei.payratio * 30;
			housei.houseElevation( 0);
			
	        ArrayList<Double>  AdaptCosts 	= new ArrayList<Double>();
	        ArrayList<Integer> MeasureList 	= new ArrayList<Integer>();
	        ArrayList<Double>  AdaptHeight 	= new ArrayList<Double>();
	        
	        housei.dryproofCost2 = Properties.DryProofingCost( housei.Ptype, housei.liveArea, 2.0);
	        housei.dryproofCost4 = Properties.DryProofingCost( housei.Ptype, housei.liveArea, 4.0);
			housei.dryproofCost6 = Properties.DryProofingCost( housei.Ptype, housei.liveArea, 6.0);
			housei.wetproofCost2 = Properties.WetProofingCost( housei.Ptype, housei.NORESUNTS, housei.liveArea, 2.0);
			housei.wetproofCost4 = Properties.WetProofingCost( housei.Ptype, housei.NORESUNTS, housei.liveArea, 4.0);
			housei.wetproofCost6 = Properties.WetProofingCost( housei.Ptype, housei.NORESUNTS, housei.liveArea, 6.0);
        	
        	if( housei.floodZone == 1 ) {
        		if( housei.bfeH <= 0.0 ) {
            		AdaptCosts.add( 0.0 ) ;
                	MeasureList.add( 0 ) ;
                	AdaptHeight.add( 0.0 ) ;
            	}
        		if( housei.bfeH <= 2.0  ) {
    				AdaptCosts.add( housei.elevationCost2 ) ;
    	        	MeasureList.add( 1 ) ;
    	        	AdaptHeight.add( 2.0 ) ;
    	        }
    	        if(  housei.bfeH <= 4.0 ) {
    	        	AdaptCosts.add( housei.elevationCost4 ) ;
    	        	MeasureList.add( 2 ) ;
    	        	AdaptHeight.add( 4.0 ) ;
    	        }
    	        if(  housei.bfeH <= 6.0) {
    	        	AdaptCosts.add( housei.elevationCost6 ) ;
    	        	MeasureList.add( 3 ) ;
    	        	AdaptHeight.add( 6.0 ) ;
    	        }
    	        if( housei.bfeH > 6.0) {
    	        	AdaptCosts.add( housei.elevationCost8 ) ;
    	        	MeasureList.add( 4 ) ;
    	        	AdaptHeight.add( 8.0 ) ;
    	        }
    			
        	}else {
        		AdaptCosts.add( 0.0 ) ;
            	MeasureList.add( 0 ) ;
            	AdaptHeight.add( 0.0 ) ;
        		if( willPay > housei.elevationCost2 ) {
    				AdaptCosts.add( housei.elevationCost2 ) ;
    	        	MeasureList.add( 1 ) ;
    	        	AdaptHeight.add( 2.0 ) ;
    	        }
    	        if( willPay > housei.elevationCost4 ) {
    	        	AdaptCosts.add( housei.elevationCost4 ) ;
    	        	MeasureList.add( 2 ) ;
    	        	AdaptHeight.add( 4.0 ) ;
    	        }
    	        if( willPay > housei.elevationCost6 ) {
    	        	AdaptCosts.add( housei.elevationCost6 ) ;
    	        	MeasureList.add( 3 ) ;
    	        	AdaptHeight.add( 6.0 ) ;
    	        }
    	        if( willPay > housei.elevationCost8 ) {
    	        	AdaptCosts.add( housei.elevationCost8 ) ;
    	        	MeasureList.add( 4 ) ;
    	        	AdaptHeight.add( 8.0 ) ;
    	        }
    			if( willPay > housei.wetproofCost2 ) {
    				AdaptCosts.add( housei.wetproofCost2 ) ;
    	        	MeasureList.add( 5 ) ;
    	        	AdaptHeight.add( 2.0 ) ;
    	        }
    	        if( willPay > housei.wetproofCost4 ) {
    	        	AdaptCosts.add( housei.wetproofCost4 ) ;
    	        	MeasureList.add( 6 ) ;
    	        	AdaptHeight.add( 4.0 ) ;
    	        }
    	        if( willPay > housei.dryproofCost2 ) {
    	        	AdaptCosts.add( housei.dryproofCost2 ) ;
    	        	MeasureList.add( 7 ) ;
    	        	AdaptHeight.add( 2.0 ) ;
    	        }
    	        if( willPay > housei.dryproofCost4 ) {
    	        	AdaptCosts.add( housei.dryproofCost4 ) ;
    	        	MeasureList.add( 8 ) ;
    	        	AdaptHeight.add( 4.0 ) ;
    	        }
        	}
        	
			housei.FloodRisk0 	=  Properties.FloodRiskF( housei.elevation, Surge, housei.CurrentJV, housei.Ptype,  0 , 0.0, 0.0  );
        	
			
			double[] inputArray = {years, distR, paraB, deltaT, muyi, xiyi,betayi} ;
			
			double[] arr 			= MitigationEval3(housei, MeasureList, AdaptHeight, AdaptCosts, inputArray);
			
//			System.out.print("Scenario 4 Adaptation is " + Arrays.toString( arr )   + "\n");
			
			adaptYM[kth][0] 		= (int) arr[0] * deltaT ;
			adaptYM[kth][1] 		= (int) arr[1] ;
			adaptYM[kth][2] 		= (int) arr[2] ;
			kth = kth +1;
			housei.adaptYear 		= (int) arr[0]* deltaT ;
			housei.FloodMeasure 	= (int) arr[1] ;
			housei.adaptH = 0.0 ;
						
			
			
			if( housei.FloodMeasure > 0 && housei.FloodMeasure < 5 ){
				if(  housei.FloodMeasure == 1 ){
					housei.eleHeight = 2.0;
				}else if( housei.FloodMeasure == 2 ){
					housei.eleHeight = 4.0;
				}else if( housei.FloodMeasure == 3 ){
					housei.eleHeight = 6.0;
				}else if( housei.FloodMeasure == 4 ){
					housei.eleHeight = 8.0;
				}
				housei.FloodMeasure = 1;
				housei.adaptH = 0.0;
			}else if( housei.FloodMeasure > 4 && housei.FloodMeasure < 7 ){
				if(  housei.FloodMeasure == 5 ){
					housei.adaptH = 2.0;
				}else if( housei.FloodMeasure == 6 ){
					housei.adaptH = 4.0;
				}else {
					housei.adaptH = 6.0;
				}
				housei.FloodMeasure = 2;
				housei.eleHeight = 0.0;
			}else if( housei.FloodMeasure > 6  ){
				if(  housei.FloodMeasure == 7 ){
					housei.adaptH = 2.0;
				}else if( housei.FloodMeasure == 8 ){
					housei.adaptH = 4.0;
				}else {
					housei.adaptH = 6.0;
				}
				housei.FloodMeasure = 3;
				housei.eleHeight = 0.0;
			}else {
					housei.eleHeight = 0.0;
					housei.adaptH = 0.0 ;
			}
			
			housei.AdaptationCost 	= (int) (arr[2] * 30) ;
			housei.FloodAdaptPay 	= (int) arr[2] ; ;
			
			if( housei.FloodMeasure == 1){
				housei.FloodRisk 	=  Properties.FloodRiskF( housei.elevation + housei.eleHeight, 
						housei.Surge, housei.CurrentJV, housei.Ptype,  
											housei.FloodMeasure, housei.adaptH, goveAdaptH  );
			}else{
				housei.FloodRisk 	=  Properties.FloodRiskF( housei.elevation, 
						housei.Surge, housei.CurrentJV, housei.Ptype,  
											housei.FloodMeasure, housei.adaptH, goveAdaptH );
			}
		}	
//		for( int k = 0; k < years; k= k + deltaT ){
//			int slotk = (int) k / deltaT;
//			AnnualCosts[0][slotk] = k ;	
//			for( int k2 = 0; k2 < N; k2++ ){
//				if( adaptYM[k2][0] == k && adaptYM[k2][1] > 0){
//					AnnualCosts[1][slotk] = AnnualCosts[1][slotk] + adaptYM[k2][2] ;
//					AnnualCosts[2][slotk] = AnnualCosts[2][slotk] + 1.0 ;
//				}
//			}
//		}
//		for( int k = deltaT; k < years; k= k + deltaT ){
//			int slotk = (int) k / deltaT;
//			AnnualCosts[1][slotk] = AnnualCosts[1][slotk] + AnnualCosts[1][slotk - 1] ;
//			AnnualCosts[2][slotk] = AnnualCosts[2][slotk] + AnnualCosts[2][slotk - 1] ;
//		}
	}
	
	public static Map<Integer, Properties> PropertyDict(ArrayList<Properties> properties){
		Map <Integer, Properties> houseDictionary = new HashMap<Integer, Properties>();
		Iterator<Properties> iter = properties.iterator();
		while(iter.hasNext()){
			Properties housei = iter.next();
			houseDictionary.put(housei.FID, housei);
		}
		houseDictionary.get(100).displayProperties();
		return houseDictionary;
	}		
	
	public static double PropertyMorgageF( double housevalue){
		double interestsR = 0.04;
		double Principle = 0.7*housevalue;
		double Payment = Principle*interestsR / (1 - Math.pow(1+interestsR, -30));
		return Payment;
	}
	
	public static double PropertyTaxF( double housevalue){
		double interestsR = 0.00678;
		double Tax = housevalue*interestsR ;
		return Tax;
	}
	
	public static double[][][] futureChange2( ArrayList<Properties> Allhouseholds, double[][] Return_periods, double[][] gev_paras,
												HashMap<Integer, ArrayList<Double> > paras2,
												double[] inputs_rp, String tail) throws IOException{
		//  SLRb, DiscountRate, 49, simYrs
		int Nhouse = Allhouseholds.size();
//		System.out.println( "The number of properties is "+ Nhouse );
		double floodHeight_mu1 = 0;
		double floodH_yrsi;
		double AdaptWaterH;
		/*Risk perception parameters*/
		double FInundation = 0 ;
		double rdHurricane_yri;
		double rate = 0.0;
		double DiscountRate;
		double para_b = inputs_rp[0];
		DiscountRate = inputs_rp[1];
		int Totyrs = (int) inputs_rp[3];
		int Nloops = (int) inputs_rp[4];
		int scenarioi = (int) inputs_rp[5];
		
		Properties propi = new Properties();
		
		double  betayi  	=  propi.beta0 ;
		double  muyi    	=  propi.mu0 ;
		double  xiyi    	=  propi.xi0 ;
		CreateHousehold(Allhouseholds, paras2, 1);
		Government gov1 = new Government();
		int ifSensitivity = (int) inputs_rp[6];
		int deltaT = (int) inputs_rp[7];
		
		double[][][] ResultInfo  	= new double[10][Nloops][Totyrs ] ;
		
//		if( scenarioi == 1) {
//			goveAdaptH = 0;
//		}else if( scenarioi == 2) {
//			goveAdaptH = 0;
//		}else if( scenarioi == 3) {
//			goveAdaptH = 4;
//		}else {
//			goveAdaptH = 10;
//		}
		double[] parayi = { Totyrs, DiscountRate, para_b, deltaT, muyi, xiyi, betayi  } ;
		
		if( ifSensitivity >= 0 ) {
			if( scenarioi == 2  ) {
				InitClass.AdaptDecision2(Allhouseholds, parayi, gov1) ;
			}else if( scenarioi == 3 ) {
				InitClass.AdaptDecision3(Allhouseholds, parayi, gov1) ;
			}else if( scenarioi == 4 ) {
				InitClass.AdaptDecision4(Allhouseholds, parayi, gov1) ;
			}else {
				InitClass.AdaptDecision( Allhouseholds, parayi, gov1) ;
			}
			
			System.out.print("Adaptation Scenario"+ scenarioi + " government adaptation " + goveAdaptH + "\n");
		}
		int 	totalLossN 			= 0;
		for( int loopi = 0; loopi < Nloops; loopi++){
			double slrH = 0;
			double structureValue;
			double propertyHeight = 0.0;
				
			if( ifSensitivity == 1 )  {
				Random randomno = new Random();
				DiscountRate  = randomno.nextGaussian() * 2.05   + 3.5 ;				
//				parayi[1] = DiscountRate;
//				parayi[2] = para_b;
//				parayi[3] = deltaT;
			}
			
//			double[] parayi2 = { 100, DiscountRate, para_b, deltaT, muyi, xiyi, betayi  } ;
//			double[] parayi2 = { Totyrs, DiscountRate, para_b, deltaT, muyi, xiyi, betayi  } ;
//			if( scenarioi == 2  ) {
//				AdaptDecision2(Allhouseholds, parayi2, gov1) ;
//			}else if( scenarioi == 3 ) {
//				AdaptDecision3(Allhouseholds, parayi2, gov1) ;
//			}else if( scenarioi == 4 ) {
//				AdaptDecision4(Allhouseholds, parayi2, gov1) ;
//			}else {
//				AdaptDecision( Allhouseholds, parayi2, gov1) ;
//			}
//			
//			goveAdaptH = gov1.goveAdaptH;
			
			CreateHousehold(Allhouseholds, paras2, 1);
			double totalrisk0 		 	= 0.0 ;
			double totalrisk1 			= 0.0 ;
			double totalPropertyVal 	= 0.0;
			double fdamage0 			= 0.0;
			double fdamage1				= 0.0;
			double AdaptationtotalPay 			= 0.0 ;
			
			double HouseelevationPay 		= 0.0 ;
			double dryproofPay 			= 0.0 ;
			double wetproofPay 			= 0.0 ;
			totalLossN 			= 0;
//			System.out.println(loopi +"th iteration start !");
			for(int yi = 0; yi < Totyrs; yi++){
				
				
				if( ifSensitivity == 0 ) {
//					Random randomno = new Random();
//					betayi  = randomno.nextGaussian() * propi.betase0   + propi.beta0 ;
//					muyi    = randomno.nextGaussian() * propi.muse0     + propi.mu0 ;
//					xiyi    = randomno.nextGaussian() * propi.xise0     + propi.xi0 ;
					betayi  = gev_paras[loopi][yi*3] ;   // beta0
					muyi    = gev_paras[loopi][yi*3+1] ; // mu0
					xiyi    = gev_paras[loopi][yi*3+2] ; // xi0
//					System.out.print("Sim"+loopi+ " mu" + muyi + " beta " + betayi + " xi " + xiyi);
				}
				
				AdaptWaterH 		= 0.0;
				totalrisk0 		 	= 0.0 ;
				totalrisk1 			= 0.0 ;
				totalPropertyVal 	= 0.0;
				fdamage0 			= 0.0;
				fdamage1			= 0.0;
				AdaptationtotalPay 			= 0.0 ;
				
				HouseelevationPay 		= 0.0 ;
				dryproofPay 		= 0.0 ;
				wetproofPay 		= 0.0 ;
				
				
				rdHurricane_yri = Return_periods[loopi][yi];
				
				if(yi >= 1 && para_b > 0){
					rate = ( 0.0017*( yi ) + para_b*Math.pow( ( yi ), 2) ) - ( 0.0017*( yi -1 ) + para_b*Math.pow( (yi -1), 2) ) ; // yi	and yi -1
				}else {
					rate = 0 ;
				}
				slrH = slrH + rate *3.28084;
				
				//System.out.println(slrH);
				Nhouse = Allhouseholds.size();
				Iterator<Properties> iter3 = Allhouseholds.iterator();
				while(iter3.hasNext())  {
					Properties hhi = iter3.next();
					
					if(yi == 0) {		
						hhi.TotFloodDamageYrs = 0.0;
					}
					
					double[] Surge  = {0.00, 0.00, 0.00, 0.00, 0.0};
					for(int i = 0; i < probs.length; i ++){
						Surge[i] 	= surgeHeightF(probs[i], muyi + slrH, xiyi, betayi) ;
					}
					
					hhi.slrHeight = slrH;
					//* floodHeight_mu0 = hhi.fmu + hhi.slrHeight;	*//
					floodHeight_mu1 = muyi + hhi.slrHeight;	
					
//					if(rdHurricane_yri <= 0.998 && rdHurricane_yri >= 0.9){
//						floodH_yrsi =  floodHeight_mu1  +
//										( (Math.pow( 1 - rdHurricane_yri, -1* xiyi) - 1)*betayi / xiyi ) ;
//						
//						floodH_yrsi = floodH_yrsi *3.28084 ;
//						
//					}else if( floodHeight_mu1 > hhi.elevation  ){
//						floodH_yrsi =  floodHeight_mu1  +
//								( (Math.pow( 1 - rdHurricane_yri, -1*xiyi) - 1)*betayi / xiyi ) ;
//				
//						floodH_yrsi = floodH_yrsi *3.28084 ;
//					}else {
//						floodH_yrsi = 0.0 ;
//					}	
					
					
					if(rdHurricane_yri <= 0.998 && rdHurricane_yri >= 0.9){
						floodH_yrsi =  floodHeight_mu1  + ( (Math.pow( -1*Math.log(rdHurricane_yri), -1*xiyi) - 1)*betayi / xiyi ) ;
						
						floodH_yrsi = floodH_yrsi *3.28084 ;
						
					}else if( floodHeight_mu1 > hhi.elevation  ){
						floodH_yrsi =  floodHeight_mu1  + ( (Math.pow( -1*Math.log(rdHurricane_yri ), -1*xiyi) - 1)*betayi / xiyi ) ;
				
						floodH_yrsi = floodH_yrsi *3.28084 ;
					}else {
						floodH_yrsi = 0.0 ;
					}	
					
					
					
					/* needs to be calculated in feet*/
					if( floodH_yrsi < hhi.elevation ){  FInundation  = 0 ; 
					}else{  FInundation  = floodH_yrsi - hhi.elevation; }
					
					if( FInundation < 0 ){
						FInundation = 0;
					}
					
					if( hhi.FloodMeasure == 1 && hhi.adaptYear <= yi){
						AdaptWaterH = FInundation - hhi.eleHeight;
					}else{
						AdaptWaterH = FInundation ;
					}
					
					if( AdaptWaterH <= goveAdaptH  ) {
						AdaptWaterH = 0.0;
					}else {
						AdaptWaterH = AdaptWaterH - goveAdaptH;
					}
					
					if( AdaptWaterH < 0 ){
						AdaptWaterH = 0;
					}
					
					/* Calculate inundation under different adaptation measures and situations */
					
					if( hhi.FloodMeasure == 1){
						propertyHeight 	= hhi.elevation + hhi.eleHeight ;
					}else{
						propertyHeight 	= hhi.elevation ;
					}
					structureValue = hhi.CurrentJV ;
					if( structureValue < 0 ) {structureValue = 0;}
	//				QuantifyRisk(hhi, mu_0);
					
					if( hhi.adaptYear <= yi ){
						hhi.floodDamage 	=  structureValue * Properties.FloodDamageFunc(AdaptWaterH, hhi.Ptype, hhi.FloodMeasure, hhi.adaptH)[0];
						hhi.FloodRisk 		=  Properties.FloodRiskF( propertyHeight, Surge, structureValue, hhi.Ptype,  hhi.FloodMeasure, hhi.adaptH, goveAdaptH);
						AdaptationtotalPay = AdaptationtotalPay + hhi.FloodAdaptPay ;
							
						if( hhi.FloodMeasure == 1) {
							HouseelevationPay = HouseelevationPay + hhi.FloodAdaptPay ;
						}
						if( hhi.FloodMeasure == 2) {
							dryproofPay = dryproofPay + hhi.FloodAdaptPay ;
						}
						if( hhi.FloodMeasure == 3) {
							wetproofPay = wetproofPay + hhi.FloodAdaptPay ;
						}
//							System.out.print("measure "+ hhi.FloodMeasure + " pay " + hhi.FloodAdaptPay + "\n");
					}else{
						hhi.floodDamage 	=  structureValue * Properties.FloodDamageFunc(AdaptWaterH, hhi.Ptype, 0, 0.0)[0];
						hhi.FloodRisk 		=  Properties.FloodRiskF( hhi.elevation, Surge, structureValue, hhi.Ptype,  0, 0.0, goveAdaptH );
					}
					
					hhi.floodDamage0 		=  structureValue * Properties.FloodDamageFunc(FInundation, hhi.Ptype, 0, 0.0)[0];
					hhi.FloodRisk0 			=  Properties.FloodRiskF( hhi.elevation, Surge, structureValue, hhi.Ptype,  0, 0.0, 0.0 );
					
					
					
					if( (hhi.floodDamage > hhi.floodDamage0 )  ) {
						System.out.println( "Risk is 0 "+ FInundation + " "+ 
											hhi.ParcelID +" "+ hhi.floodDamage0 + " "+hhi.floodDamage  );
					}
					if( yi > 10 &&  FInundation > 0 && hhi.floodDamage0 <= 0  ) {
						System.out.println( floodH_yrsi + " SLR height" +slrH+ " mu"+ muyi + " beta"+ betayi + " xi" + xiyi );
						System.out.println( "Risk is not 0 "+ hhi.FloodRisk0 + " AdaptRisk"+ hhi.FloodRisk + 
											" damage0 "+ hhi.floodDamage0 + " adapt Damage "+hhi.floodDamage + 
											" height " + FInundation + " " + structureValue  );
					}
					
					
					
//					if( hhi.CurrentJV  > 0 && hhi.TotFloodDamageYrs > 2*hhi.CurrentJV ){
//						structureValue 		= 0.0 ;
//						totalLossN 			= totalLossN + 1 ;
//					}
					
					
					
//					if( floodHeight_mu1 > propertyHeight && structureValue 	> 0){
//						structureValue 		= 0.0 ;
//						totalLossN 			= totalLossN + 1 ;
//					}
					
					if( hhi.FloodAdaptPay == 0 ) {
						hhi.CBratio = 0.0;
					}else if( hhi.FloodAdaptPay > 0 ){
						hhi.CBratio = ( hhi.FloodRisk0 - hhi.FloodRisk) /  hhi.FloodAdaptPay;
					}else {
						hhi.CBratio = -1;
					}	
					
					hhi.TotFloodDamageYrs += hhi.floodDamage; 
					/** Output matrix  **/
					hhi.TotFloodDamage   += hhi.floodDamage0;
					hhi.TotFloodDamage1  += hhi.floodDamage;
					hhi.TotalAdaptCost   += hhi.AdaptationCost ;
					hhi.TotalAdaptPay	 += hhi.FloodAdaptPay ;
					
					
//					hhi.TotWindDamage 	 += hhi.windDamage0;
//					hhi.TotWindDamage1 	 += hhi.windDamage;
					
//					hhi.TotDamage 	 	 +=  hhi.floodDamage0;
//					hhi.TotDamage1 	 	 +=  hhi.floodDamage;
//					
					totalPropertyVal 	 += hhi.CurrentJV;
					totalrisk0 			 += hhi.FloodRisk0 ;
					totalrisk1 			 += hhi.FloodRisk;
					
					fdamage0			 += hhi.floodDamage0;
					fdamage1 			 += hhi.floodDamage;
					
				}
				
				ResultInfo[0][loopi][yi ]  	= totalPropertyVal;
				ResultInfo[1][loopi][yi ]  	= totalrisk0;
				ResultInfo[2][loopi][yi ]  	= totalrisk1;
				
				ResultInfo[3][loopi][yi ]   = fdamage0;
				ResultInfo[4][loopi][yi ]   = fdamage1;
				
				ResultInfo[5][loopi][yi ]   = AdaptationtotalPay;
				ResultInfo[6][loopi][yi ]   = DiscountRate;
				
				ResultInfo[7][loopi][yi ]   = HouseelevationPay;
				ResultInfo[8][loopi][yi ]   = dryproofPay;
				ResultInfo[9][loopi][yi ]   = wetproofPay;
			}
//				System.out.println(tail +" Finished !");
			
		}
		
		Iterator<Properties> iter4 = Allhouseholds.iterator();
		while(iter4.hasNext()){
			Properties hhi = iter4.next();
			hhi.TotFloodDamage   = hhi.TotFloodDamage 	/ (Totyrs*Nloops);
			hhi.TotFloodDamage1  = hhi.TotFloodDamage1 	/ (Totyrs*Nloops);
			hhi.TotalAdaptPay	 = hhi.TotalAdaptPay 	/ (Totyrs*Nloops);
			hhi.TotalAdaptCost   = hhi.TotalAdaptCost 	/ (Totyrs*Nloops);
			
//			hhi.TotWindDamage 	 = hhi.TotWindDamage 	/ (Totyrs*Nloops);
//			hhi.TotWindDamage1 	 = hhi.TotWindDamage1 	/ (Totyrs*Nloops);
//			hhi.TotDamage 	 	 = hhi.TotDamage 		/ (Totyrs*Nloops);
//			hhi.TotDamage1 	 	 = hhi.TotDamage1 		/ (Totyrs*Nloops);
		}
		return ResultInfo;
	}
	
	
	public static void CreateHousehold( List<Properties> residentBuildings, 
										HashMap<Integer, ArrayList<Double> > paras, int initi){
		
		Iterator<Properties> iter = residentBuildings.iterator();
		
		while(iter.hasNext()){
			Properties budlidngi = iter.next();
			//System.out.println( budlidngi.wetproofPay );
			budlidngi.CurrentJV = budlidngi.Justvalue;
			
//			budlidngi.TotFloodDamage   = 0.0;
//			budlidngi.TotFloodDamage1  = 0.0;
//			budlidngi.TotalAdaptCost   = 0.0;
			if(  initi == 0 ){
				int Pname = budlidngi.ZipCode;
				ArrayList<Double> parai = null ;
				if ( paras.containsKey( Pname ) ){
					parai = paras.get(Pname);
//					System.out.println(  "Zip Code is " + Pname  );	
				}else{
					parai = paras.get( 0 );
//					System.out.println( "All Zip case " + Pname );
				}
				
				budlidngi.a0 = parai.get(0) ;
				budlidngi.a1 = parai.get(1) ;
				budlidngi.a2 = parai.get(2) ;
				budlidngi.a3 = parai.get(3) ;
				budlidngi.a4 = parai.get(4) ;
//				budlidngi.a5 = parai.get(5) ;
//				System.out.println(budlidngi.a0 + " "+ budlidngi.a1 + " "+ budlidngi.a2);
				budlidngi.CurrentJV = budlidngi.Justvalue ;
				if(budlidngi.CurrentJV <= 0){
					budlidngi.CurrentJV = 0;
				}
			}
		}
		
//System.out.println("Finish processing household creation step" );
}
	
}



