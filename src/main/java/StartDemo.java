import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.math3.distribution.BetaDistribution;
import org.apache.commons.math3.distribution.PoissonDistribution;
import com.infomatiq.jsi.Rectangle;
import com.infomatiq.jsi.SpatialIndex;
import com.infomatiq.jsi.rtree.RTree;

import gnu.trove.TIntProcedure;

public class StartDemo {
	
	public static double[] probs  = { 0.1, 0.04, 0.02, 0.01, 0.002};
	
	
	private static DecimalFormat df2 = new DecimalFormat("#.####");
	
	public static double Payment(double intRate, double N, double loan) {
	      double pay = 0.0;
	      pay = intRate * Math.pow(1+intRate, N) * loan /  ( Math.pow(1+intRate, N) - 1 ) ;
	      return pay;
	}
	
	public static ArrayList<Properties> readProperty(String csvFile, double popPercent) {
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
	                if( rand_p < popPercent & Double.parseDouble(dataline[8] ) < 5000000){
	                	Properties propertyi = new Properties(dataline) ;
//	                	propertyi.FID = counti;
	                	propertyi.agentID 	= counti;
			            entities.add(propertyi);
			            counti++;
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
		System.out.println("Finished reading residential parcels!");
		return entities;
	}
	
	public static ArrayList<Census> readCensuses(String csvFile2) {
		ArrayList<Census> householdALL = new Census().readFile(csvFile2);
//		System.out.println("Finished reading Census!");
		return householdALL;
	}
	
	public static HashMap<String, Census> censusDict( ArrayList<Census> CensusList ){
		HashMap<String, Census> hash_map = new HashMap<String, Census>(); 
		for (Census Censusi : CensusList) {
			hash_map.put(Censusi.CensusID, Censusi);
		}
		return hash_map;
	}
	
	public static ArrayList<Properties> residentialCensus(ArrayList<Properties> residentials, String CID){
		ArrayList<Properties> censusResid = new ArrayList<Properties>();
		for (Properties entity : residentials) {
			if (entity.CensusID == CID) {
				censusResid.add(entity);
		    }
		}
		return censusResid;
	}
	
	
	public static double PropertyJustVal( Properties propi, double deltaDEM, double discountR, int yrs) { 
        double changeVal = propi.liveArea* (  propi.a2 + propi.a4 + propi.a5 * deltaDEM );
//        double risk = propi.AdaptFloodRisk + propi.AdaptationCost + propi.insuranceCost - propi.FloodRisk0;
        double updatedVal  = (propi.exposedValue + changeVal)  ;
         // -1*propi.a1*propi.AdaptFloodRisk  - propi.AdaptFloodRisk  - propi.AdaptationCost + propi.insuranceCost
        return updatedVal ;
    } 
	
	public static void SocialNetwork(ArrayList<Properties> singleFamilyHouses, float radius){
		int rewire = 0;
		int Count = singleFamilyHouses.size();
		if(rewire == 0){
			final Rectangle[] rects = new Rectangle[Count];
		    SpatialIndex si = new RTree();
		    si.init(null);
		    HashMap<Integer, Properties> IDMaps = new HashMap<Integer, Properties>();
			Iterator<Properties> iter = singleFamilyHouses.iterator();
			while ( iter.hasNext() ) {
				Properties budlidngi = iter.next();
				float x1 = (float) budlidngi.latitude;
				float y1 = (float) budlidngi.longitude;
				float x2 = (float) budlidngi.latitude;
				float y2 = (float) budlidngi.longitude;
				rects[budlidngi.agentID] = new Rectangle(x1, y1, x2, y2); 
				si.add(rects[ budlidngi.agentID ], budlidngi.agentID);
				IDMaps.put(budlidngi.agentID, budlidngi);}
			Iterator<Properties> iter2 = singleFamilyHouses.iterator();
			List<Integer> friendsIDs = new ArrayList<>();
			Random rn = new Random();
			while ( iter2.hasNext() ) {
				Properties budlidngi = iter2.next();
				float x1 = (float) budlidngi.latitude;
				float y1 = (float) budlidngi.longitude;
				class SaveToListProcedure implements TIntProcedure {
				      ArrayList<Integer> ids = new ArrayList<Integer>();
				      public boolean execute(int id) {
				        ids.add(id);
				        return true; }; 
				      private List<Integer> getIds() {
				        return ids;}
				}
				SaveToListProcedure myProc = new SaveToListProcedure();
			    si.contains(new Rectangle(x1- radius, y1- radius, x1+ radius, y1+ radius), myProc);
			    friendsIDs = myProc.getIds();
			    List<Integer> newfriendsIDs = new ArrayList<>();
			    if( friendsIDs.size() >  budlidngi.Nfriend ){
			    	budlidngi.friends = new ArrayList<Integer>( budlidngi.Nfriend );
			    	newfriendsIDs = getRandomElement(friendsIDs, budlidngi.Nfriend);
			    	for (Integer item : newfriendsIDs) {budlidngi.friends.add(item) ;}
			    }else{
			    	budlidngi.friends = new ArrayList<Integer>(friendsIDs.size());
			    	newfriendsIDs.addAll(friendsIDs) ;
			    	for (Integer item : friendsIDs) {budlidngi.friends.add(item) ;}
			    } 
//			    System.out.println(newfriendsIDs.size() + " "+ budlidngi.Nfriend);
			    int KnowledgeCount = singleFamilyHouses.size(); 
//			    budlidngi.friends = (ArrayList<Integer>) friendsIDs;
			    for( int ii = 0; ii < newfriendsIDs.size(); ii ++){
			    	int Findex = newfriendsIDs.get(ii);
			    	int loopi = 0;
			    	while( loopi == 0) {
			    		if( 0.1 > Math.random()){
			    			Findex = rn.nextInt(KnowledgeCount  ) ;
			    			if( IDMaps.get( Findex ) != null) {
			    				budlidngi.friends.set(ii, Findex); loopi = 1;}}}
			    	}
			    budlidngi.friendCount = newfriendsIDs.size();
			}
		}
	}
	
	public static void Risk_Evaluation( ArrayList<Properties> ResidBidPrice, double p_th, int lamda){
		Iterator<Properties> iter = ResidBidPrice.iterator();
		int pid = 0;
		
		while(iter.hasNext()){
			Properties housei = iter.next();
			if( housei.elevation < 0 & housei.cate5 >= 0 ){
				housei.elevation = 1;
			}
			housei.BFE = housei.cate4 ;
			if( housei.BFE < 0){ housei.BFE = 0;}
//			if( ( housei.floodZone == 1 && Math.random() < 0.7)){
//				housei.requiredIns = 1;
//			}
			if( housei.floodZone == 1  ){
				housei.risk_perception0 = Math.random() * (p_th*2-p_th*0.2);
			}else{
				housei.risk_perception0 = Math.random() * (p_th*0.2);
			}
			housei.risk_perception0 = housei.risk_perception ;
			/** in case of some extreme large values in house property*/
//			if( housei.elevation < 0 & housei.cate5 < 0 ){ housei.elevation = housei.BFE + 5; }
			housei.FloodRisk0 	 = housei.QuantifyRisk(0.0, 0.0, housei.Ptype, 0, 0, 0);
			housei.frisk_initial = housei.FloodRisk0;
			housei.TraditionalElevating( 0 );
//			housei.WetProofing( 0 );
//			housei.DryProofing( 0 );
			
			int Azone = housei.SFHA.indexOf('A');
			int Vzone = housei.SFHA.indexOf('V');
			if(Azone < 0 & Vzone < 0 ){ housei.floodZone = 0; }else { housei.floodZone = 1;}	
			
			if( lamda > 0){
				PoissonDistribution poissoni = new PoissonDistribution(lamda);
				housei.Nfriend 		= poissoni.sample() ;
			}else{
				housei.Nfriend 		= 0;
			}
			
			
			
			
			housei.BayesAlpha 	= 0.0;
			housei.BayesBeta  	= 1.0;
			
//			housei.SVIndex    = housei.SocialVulnerability(housei.Income, housei.Education, housei.Race);
//			housei.RiskPIndex = housei.RiskPerception(housei.floodZone, housei.directExperience, housei.indirectexperience);
			
			pid = pid + 1;
		}	
		float f = (float) 0.005;
		SocialNetwork(ResidBidPrice, f);
//		String bidPth = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Data_processed\\ParcelRiskInfo.csv";
//		CsvFileWriter.writeBidPrice(ResidBidPrice, bidPth);	
	}
	public static int sumTotal( int[] array0){
		int total = 0;
		for(int i = 0; i < array0.length; i++){
			if(array0[i] > 0) {
				total	+=	1;
			}
		}
		return total;
	}
	
	
	public static double[] neighborEffects(HashMap<Integer, Properties> IDMaps, Properties propertyi) {
//		double prob = 0;
		double m1 = 0.0;
		double n1 = propertyi.friends.size();
		Iterator<Integer> iterN = propertyi.friends.iterator();
		int Findex;
		while (iterN.hasNext()) {
			Findex = (int) iterN.next();
//			System.out.println("the value "+ FloodCountj+ " id "+Findex+ " "+ totalsNeighbors);
			Properties propertyj =  IDMaps.get( Findex );
			if( propertyj.floodDamage1 > 0 ) {
				m1 = m1 + 1;
			}
//			n1 = n1 + 1;
		}
		double[] FloodCountj = {m1, n1};
//		prob = (double)insCountj / (double)totalsNeighbors;
		return FloodCountj;
	}
	
	public static List<Integer> getRandomElement(List<Integer> list, int totalItems) { 
			Random rand = new Random(); 

			// create a temporary list for storing 
			// selected element 
			List<Integer> newList = new ArrayList<>(); 
			for (int i = 0; i < totalItems; i++) { 
			
			// take a raundom index between 0 to size  
			// of given List 
			int randomIndex = rand.nextInt(list.size()); 
			
			// add element in temporary list 
			newList.add(list.get(randomIndex)); 
			
			// Remove selected element from orginal list 
			list.remove(randomIndex); 
			} 
			return newList; 
	} 
	
	
	
	
	public static double[] SumColTot( double[][] totalDamages_T ) {
		double[] simInfo  = new double[5];
		for (int i = 0; i < totalDamages_T[0].length; i++) {
			if( i == 0) {
				simInfo[0] = totalDamages_T[totalDamages_T.length -1][i];}
			if( i == 2) {
				simInfo[1] = totalDamages_T[totalDamages_T.length -1][i];}
			if( i == 3) {
				simInfo[2] = totalDamages_T[totalDamages_T.length -1][i];}
			if( i == 6) {
				simInfo[3] = totalDamages_T[totalDamages_T.length -1][i];}
			if( i == 8) {
				double sum = 0;
				for (int j = 0; j < totalDamages_T.length; j++) {
		            sum = sum + totalDamages_T[j][i];}
				simInfo[4] = sum;}
			}
		return simInfo;
	}
	
	public static ArrayList<Integer> StormYear( int[] SScate ){
		ArrayList<Integer> stormList = new ArrayList<Integer>();
		for(int i = 0; i < SScate.length; i++){
			if( SScate[i] > 0) {stormList.add( i );}
		}
		return stormList;
	}
	
	
	public static double[][][] futureChange( ArrayList<Properties> Allhouseholds, ArrayList<Census> CensusList,
			Government gov1, double[] inputs_rp, 
			double[][] Return_periods, String tail ) throws IOException{
		int Nhouse = Allhouseholds.size();
		/** Property count **/
		HashMap<Integer, Properties> IDMaps = new HashMap<Integer, Properties>();
		Iterator<Properties> iter0 = Allhouseholds.iterator();
		while ( iter0.hasNext() ) {
			Properties budlidngi = iter0.next();
			IDMaps.put(budlidngi.agentID, budlidngi);			
		}
		
		/** Census count **/
		HashMap<String, Census> CensusCount = new HashMap<String, Census>();
		Iterator<Census> censui = CensusList.iterator();
		while ( censui.hasNext() ) {
			Census censusi = censui.next();
			censusi.damageCount = 0;
			censusi.lossCount = 0;
			censusi.CensusFloodCount = 0.0;
			censusi.CensusLossCount = 0.0;
			CensusCount.put(censusi.CensusID, censusi);}
		Iterator<Properties> iter01 = Allhouseholds.iterator();
		while(iter01.hasNext()){
			Properties hhi = iter01.next();	
			if( CensusCount.containsKey(hhi.CensusID) ){
				CensusCount.get(hhi.CensusID).N = CensusCount.get(hhi.CensusID).N + 1;	
			}
		}
		System.out.println( "The number of properties is "+ Nhouse );
		double slrH = 0;
		double floodHeight_mu1 = 0;
		double floodH_yrsi;
		double TotWaterH;
		int 	totalElev;
		double  TotalACost;
		double floodrisk1;
		int 	damageN ;
		int 	existN  ;
		int     requiredInsN ;
		int 	CensusCommunityAdaptationCount;
		double[] neighborExper = {0.0, 0.0};
		/*Risk perception parameters*/
		double TotalInsuredCost;
		double TotalCoverage;
		double TotalInsuer;
		double Policy_min_Claims ;
		double fdamage, fdamage0, fdamage1;
		double FInundation = 0 ;
		double rdHurricane_yri;
		double rate ;
		double compensate;
		double TotalSubsidy;
		int totalAdapte = 0;
		int EvaluatedN  = 0;
		int changeAttr = 0;
		int    	MemoryBanki = 0;
		double  TotRiskPerct = 0.0;
		double para_b 			= inputs_rp[0];
		int analysisCase 		= (int) inputs_rp[1];
		double weighti 			= inputs_rp[2];
		int Totyrs 				= (int) inputs_rp[3];
		int MemN 				= (int) inputs_rp[4];
		double seawall  		= inputs_rp[5];
		float NetworkR 			= (float) inputs_rp[6];
		int Nloops 				= (int) inputs_rp[7];
		double p_th 			= inputs_rp[8];	
		double census_percent_damage 			= inputs_rp[9];
		
//		double[][] ResultInfo  	= new double[16][Totyrs ] ;	
		double[][][] ResultInfo = new double[Nloops][19][Totyrs ] ;
		
		for( int loopi = 0; loopi < Nloops; loopi++){ // This is the big Monte-Carlo loop.
			double analysisval 					= 0;
			
			requiredInsN  						= 0;
			CensusCommunityAdaptationCount  	= 0;
			double Nbetaa 						= 0;
			Policy_min_Claims 					= 0;
			damageN								= 0;
			slrH 								= 0;
			floodHeight_mu1 					= 0;
			CreateHousehold(Allhouseholds, CensusList, 1, p_th); 
			for(int yi = 0; yi < Totyrs; yi++){ // This is the total simulation year loop.
				rdHurricane_yri = Return_periods[loopi][yi];
				if(yi >= 1 && para_b > 0){
					rate = ( 0.0017*(yi ) + para_b*Math.pow( (yi), 2) ) - ( 0.0017*(yi -1) + para_b*Math.pow( (yi -1), 2) ) ;
				}else {
					rate = 0 ;	
				}
				Iterator<Census> iterc = CensusList.iterator();
				while(iterc.hasNext()){
					Census censusi = iterc.next();
					censusi.policyN_yi = 0;
//					censusi.claimN_yi  = 0;
				}
				Policy_min_Claims 					= 0;
				existN  		= 0;
				MemoryBanki = yi % MemN;
				slrH = slrH + rate *3.28084;
				//System.out.println(slrH);
	//			DiscountRate 	= 0.02 ;
				TotWaterH 			= 0;
				totalElev 			= 0; 
				TotalInsuredCost 	= 0;
				TotalCoverage 		= 0;
				TotalInsuer 		= 0;
				TotalACost 			= 0;
				damageN				= 0;
				EvaluatedN			= 0;
				TotRiskPerct		= 0;
				totalAdapte 		= 0;
				floodrisk1 			= 0; 
				fdamage 			= 0; 
				fdamage0 			= 0;
				fdamage1			= 0;
				TotalSubsidy 		= 0;
				compensate 			= 0;
				Nhouse = Allhouseholds.size();
				
//				if(yi == 0){
//					SocialNetwork(Allhouseholds, yi, NetworkR);
//				}
				
				int lostProperty = 0;
				Iterator<Properties> iter3 = Allhouseholds.iterator();
				while(iter3.hasNext())  {  // This is the total simulation year loop.
					Properties hhi = iter3.next();
					hhi.slrHeight = slrH;
	//				System.out.println( "risk Perception "+ hhi.BayesAlpha );	
					if( yi == 0 ){
						hhi.seawall = 0.0;
					}
					//* floodHeight_mu0 = hhi.fmu + hhi.slrHeight;	*//
					floodHeight_mu1 = hhi.mu0 + hhi.slrHeight;	
					if(yi == 0){
						if( analysisCase == 4 ){
							hhi.houseElevation_advisory( 0, analysisCase);
							hhi.elePay 	= Payment( 0.004, 30, hhi.elecosts );
							hhi.PreFRIM = 0;
						}else{
							hhi.TraditionalElevating( 0);
							hhi.elePay 	= Payment( 0.008, 30, hhi.elecosts );
							if(hhi.EFFYER < 1973){
								hhi.PreFRIM = 1;
							}else{
								hhi.PreFRIM = 0;
							}
						}
						
						/**
						 * Coefficients:
				              Estimate Std. Error t value Pr(>|t|)    
							(Intercept)    3743.93     626.91   5.972 1.79e-08 ***
							Age            -198.38      72.13  -2.750  0.00673 ** 
							ethnicity.f2   -604.26     363.90  -1.661  0.09902 .  
							ethnicity.f3    506.78     233.19   2.173  0.03142 *  
							ethnicity.f4   -561.12     314.85  -1.782  0.07686 .  
							LiveTime       -524.12     159.32  -3.290  0.00126 ** 
							PropertyValue    86.08      42.59   2.021  0.04512 *  
							NearWater      -305.21     181.43  -1.682  0.09472 .   
						 */
						
						Random r = new Random();
//						hhi.Normal_b0 	= r.nextGaussian() *626.91      +3743.93    ;
//						hhi.Normal_b0 			= 3743.93    ;
//						hhi.Normal_age   	 	=  198.38    ;
//						hhi.Normal_LiveYrs   	=  524.12    ;
//						hhi.Normal_JVclass 		=  86.08     ;
//						hhi.Normal_floodZone 	=  305.21 	 ;
						
						
//						hhi.Normal_b0 			=  7.62398			;
//						hhi.Normal_LiveYrs   	=  -0.46588         ;
//						hhi.Normal_JVclass 		=  0.16134          ;
						
						hhi.Normal_b0 			=  r.nextGaussian() *0.410   +  7.62398    ;
						hhi.Normal_LiveYrs   	=  r.nextGaussian() *0.105 	 -  0.46588    ;
						hhi.Normal_JVclass 		=  r.nextGaussian() *0.029   +  0.16134      ;
						
//						hhi.Normal_b0 			= r.nextGaussian() *0.41    + 7.62398    ;
//						hhi.Normal_LiveYrs   	= r.nextGaussian() *0.105 	- 0.46588      ;
//						hhi.Normal_JVclass 		= r.nextGaussian() *0.029   + 0.16134     ;
						
//						hhi.Normal_age   = r.nextGaussian() *72.13       +198.38      ;
//						hhi.Normal_LiveYrs   = r.nextGaussian() *159.32  + 524.12      ;
//						hhi.Normal_JVclass = r.nextGaussian() *42.59     +86.08     ;
//						hhi.Normal_floodZone = r.nextGaussian() *181.43  +305.21 ;
						
//						if( hhi.Race == 2 ){
//							hhi.Normal_Race =  -0.78903      	;	
//						}else if( hhi.Race == 3){
//							hhi.Normal_Race =  -0.20026    	;
//						}else if( hhi.Race == 4){
//							hhi.Normal_Race =  -0.36368    	;
//						}
						
						if( hhi.Race == 2 ){
							hhi.Normal_Race =  r.nextGaussian() * 0.34 -0.78903  	;	
						}else if( hhi.Race == 3){
							hhi.Normal_Race =  r.nextGaussian() * 0.16 -0.20026		;
						}else if( hhi.Race == 4){
							hhi.Normal_Race =  r.nextGaussian() * 0.26 -0.36368		;
						}
						
//						if( hhi.Race == 2 ){
//							hhi.Normal_Race = r.nextGaussian() * 0.34   - 0.78903  ;	
//						}else if( hhi.Race == 3){
//							hhi.Normal_Race = r.nextGaussian() * 0.16   - 0.20026;
//						}else if( hhi.Race == 4){
//							hhi.Normal_Race = r.nextGaussian() * 0.26   - 0.36368;
//						}
						
//						if( hhi.Race == 2 ){
//							hhi.Normal_Race = r.nextGaussian() * 363.90   + 604.26  ;	
//						}else if( hhi.Race == 3){
//							hhi.Normal_Race = r.nextGaussian() * 233.19   + 506.78;
//						}else if( hhi.Race == 4){
//							hhi.Normal_Race = r.nextGaussian() * 314.85   + 561.12;
//						}
						
//						double[] InsuranceList0 = Insurer.insuranceCostf( hhi.exposedValue, hhi.SFHA , hhi.elevation, 0, 
//								hhi.BFE, hhi.PreFRIM, hhi.Ptype, increment, 
//								hhi.crs ,analysisCase, hhi.insCancel);
//						double[] InsuranceList1 = Insurer.insuranceCostf( hhi.exposedValue, hhi.SFHA , hhi.elevation, hhi.eleHeight, 
//								hhi.BFE, hhi.PreFRIM, hhi.Ptype, increment, 
//								hhi.crs, analysisCase, hhi.insCancel );
//						hhi.InsuranceCoverage1 	= InsuranceList0[0] ;
//						hhi.InsuranceCost1 		= InsuranceList0[3] ;
//						hhi.InsuranceCoverage2 	= InsuranceList0[1] ;
//						hhi.InsuranceCost2 		= InsuranceList0[4] ;
//						hhi.InsuranceCoverage3 	= InsuranceList0[2] ;
//						hhi.InsuranceCost3  	= InsuranceList0[5] ;
//						hhi.InsSubsidy1			= InsuranceList0[6] ;
//						hhi.InsSubsidy2			= InsuranceList0[7] ;
//						hhi.InsSubsidy3			= InsuranceList0[8] ;
//						hhi.RiskInsuranceCoverage1 = InsuranceList1[0] ;
//						hhi.RiskInsuranceCost1     = InsuranceList1[3] ;
//						hhi.RiskInsuranceCoverage2 = InsuranceList1[1] ;
//						hhi.RiskInsuranceCost2     = InsuranceList1[4] ;
//						hhi.RiskInsuranceCoverage3 = InsuranceList1[2] ;
//						hhi.RiskInsuranceCost3     = InsuranceList1[5] ;
//						hhi.RiskInsSubsidy1		   = InsuranceList1[6] ;
//						hhi.RiskInsSubsidy2		   = InsuranceList1[7] ;
//						hhi.RiskInsSubsidy3		   = InsuranceList1[8] ;
						
						hhi.BayesAlpha 		= 0.0	;
						hhi.BayesBeta  		= 1.0	;
						hhi.ExperAlpha[9] 	= 0.0	; // experience
						hhi.ExperBeta[9]  	= 0.0	;
//						hhi.updated_p  		= Properties.BayesProspect(probs, capacity)		;	
//						System.out.println( Arrays.toString(hhi.updated_p) );	
						
						if( hhi.Race == 1 ){	
							hhi.WillingToPay = Math.exp( hhi.Normal_b0 + hhi.Normal_LiveYrs* hhi.LiveYrs + hhi.Normal_JVclass* hhi.JVclass ) ;
						}else if( hhi.Race == 2 ){
							hhi.WillingToPay = Math.exp( hhi.Normal_b0 + hhi.Normal_LiveYrs* hhi.LiveYrs + hhi.Normal_Race + hhi.Normal_JVclass* hhi.JVclass ) ;
						}else if( hhi.Race == 3){
							hhi.WillingToPay = Math.exp( hhi.Normal_b0 + hhi.Normal_LiveYrs* hhi.LiveYrs + hhi.Normal_Race + hhi.Normal_JVclass* hhi.JVclass ) ;
						}else{
							hhi.WillingToPay = Math.exp( hhi.Normal_b0 + hhi.Normal_LiveYrs* hhi.LiveYrs - hhi.Normal_Race + hhi.Normal_JVclass* hhi.JVclass ) ;
						}
						double[] InsuranceList0 = Insurer.insuranceCostf2( hhi.exposedValue, hhi.WillingToPay, 
																			hhi.SFHA , hhi.elevation, 0, 
																			hhi.BFE, hhi.PreFRIM, hhi.Ptype, 
																			hhi.crs ,analysisCase, hhi.insCancel);
						double[] InsuranceList1 = Insurer.insuranceCostf2( hhi.exposedValue, hhi.WillingToPay, 
																			hhi.SFHA , hhi.elevation, hhi.eleHeight, 
																			hhi.BFE, hhi.PreFRIM, hhi.Ptype, 
																			hhi.crs ,analysisCase, hhi.insCancel);
						
						hhi.InsuranceCoverage1 		= InsuranceList0[0] ;
						hhi.InsuranceCost1 			= InsuranceList0[1] ;
						hhi.InsSubsidy1				= InsuranceList0[2] ;
						
						hhi.InsuranceCoverage2 		= InsuranceList0[3] ;
						hhi.InsuranceCost2 			= InsuranceList0[4] ;
						hhi.InsSubsidy2				= InsuranceList0[5] ;
						
						hhi.RiskInsuranceCoverage1 	= InsuranceList1[0] ;
						hhi.RiskInsuranceCost1     	= InsuranceList1[1] ;
						hhi.RiskInsSubsidy1		   	= InsuranceList1[2] ;
						
						hhi.RiskInsuranceCoverage2 	= InsuranceList1[3] ;
						hhi.RiskInsuranceCost2     	= InsuranceList1[4] ;
						hhi.RiskInsSubsidy2		   	= InsuranceList1[5] ;
						changeAttr = 0;
					}
					
					if( (yi +1) % 1 == 0){
						hhi.cate5 = floodHeight_mu1 + ( (Math.pow( 0.005, -1* hhi.xi0) - 1)*hhi.beta0  / hhi.xi0  ) ; 
						hhi.cate4 = floodHeight_mu1 + ( (Math.pow( 0.01 , -1* hhi.xi0) - 1)*hhi.beta0  / hhi.xi0  ) ; 
						hhi.cate3 = floodHeight_mu1 + ( (Math.pow( 0.02 , -1* hhi.xi0) - 1)*hhi.beta0  / hhi.xi0  ) ;  
						hhi.cate2 = floodHeight_mu1 + ( (Math.pow( 0.04 , -1* hhi.xi0) - 1)*hhi.beta0  / hhi.xi0   ) ; 
						hhi.cate1 = floodHeight_mu1 + ( (Math.pow( 0.1  , -1* hhi.xi0) - 1)*hhi.beta0  / hhi.xi0   ) ; 
					}
					
					if( yi % 5 == 0  ){
						hhi.LiveYrs = hhi.LiveYrs + 1;
						if( hhi.LiveYrs > 4 ){
							hhi.LiveYrs = 4;
							if( hhi.Race == 1 ){	
								hhi.WillingToPay = Math.exp( hhi.Normal_b0 + hhi.Normal_LiveYrs* hhi.LiveYrs + hhi.Normal_JVclass* hhi.JVclass ) ;
							}else if( hhi.Race == 2 ){
								hhi.WillingToPay = Math.exp( hhi.Normal_b0 + hhi.Normal_LiveYrs* hhi.LiveYrs + hhi.Normal_Race + hhi.Normal_JVclass* hhi.JVclass ) ;
							}else if( hhi.Race == 3){
								hhi.WillingToPay = Math.exp( hhi.Normal_b0 + hhi.Normal_LiveYrs* hhi.LiveYrs + hhi.Normal_Race + hhi.Normal_JVclass* hhi.JVclass ) ;
							}else{
								hhi.WillingToPay = Math.exp( hhi.Normal_b0 + hhi.Normal_LiveYrs* hhi.LiveYrs - hhi.Normal_Race + hhi.Normal_JVclass* hhi.JVclass ) ;
							}
						}
					}
					
					
					if(rdHurricane_yri <= 1 && rdHurricane_yri >= 0){
						floodH_yrsi =  floodHeight_mu1  +
										( (Math.pow(    -1*Math.log( rdHurricane_yri ) , -1*hhi.xi0) - 1)*hhi.beta0 / hhi.xi0 ) ; 
					}else{
						floodH_yrsi = 0  ;
					}	
					
					// flood height with elevation 
					if( floodH_yrsi < hhi.seawall +hhi.elevation ){  
						FInundation = 0; 
					}else{
						FInundation  = floodH_yrsi ;
					}
					
					
					/** Risk evaluation  **/
					// important  1
					if( CensusCount.containsKey(hhi.CensusID) ){
						if( CensusCount.get(hhi.CensusID).CensusFloodCount > census_percent_damage ){
							hhi.seawall = seawall;						
						}
//						if( CensusCount.get(hhi.CensusID).CensusLossCount > 0.1 && hhi.floodZone == 1 && hhi.requiredIns == 0){
//							hhi.requiredIns = 1 ;
//							requiredInsN = requiredInsN + 1;
//							hhi.InsuranceCoverage1 		= hhi.InsuranceCoverage2;
//							hhi.InsuranceCost1 			= hhi.InsuranceCost2  ;
//							hhi.InsSubsidy1				= hhi.InsSubsidy2	 ;
//							hhi.RiskInsuranceCoverage1 	= hhi.RiskInsuranceCoverage2 ;
//							hhi.RiskInsuranceCost1     	= hhi.RiskInsuranceCost2 ;
//							hhi.RiskInsSubsidy1		   	= hhi.RiskInsSubsidy2 ;
//						}
					}
					
					
//					if( analysisCase == 1 || analysisCase == 3 ){			
//						System.out.println( hhi.damageCount + " " + hhi.floodZone ) ; 
//					if( hhi.floodZone == 1 && hhi.requiredIns == 0 && hhi.floodDamage1 >= 1){
//							hhi.requiredIns = 1 ;
//							requiredInsN = requiredInsN + 1;
//							hhi.InsuranceCoverage1 		= hhi.InsuranceCoverage2;
//							hhi.InsuranceCost1 			= hhi.InsuranceCost2  ;
//							hhi.InsSubsidy1				= hhi.InsSubsidy2	 ;
//							hhi.RiskInsuranceCoverage1 	= hhi.RiskInsuranceCoverage2 ;
//							hhi.RiskInsuranceCost1     	= hhi.RiskInsuranceCost2 ;
//							hhi.RiskInsSubsidy1		   	= hhi.RiskInsSubsidy2 ;
//					}
//					}
					
					if(hhi.seawall > 0 && hhi.seawall <= 1){
						hhi.crs = 0.1;
					}else if( hhi.seawall > 1 && hhi.seawall < 3 ){
						hhi.crs = 0.25;
					}else if( hhi.seawall >= 3 ){
						hhi.crs = 0.45;
					}else{
						hhi.crs = 0.0;
					}
					
					if( changeAttr == 0 && hhi.crs > 0  ){
						double[] InsuranceList0 = Insurer.insuranceCostf2( hhi.exposedValue, hhi.WillingToPay, 
																			hhi.SFHA , hhi.elevation, 0, 
																			hhi.BFE, hhi.PreFRIM, hhi.Ptype, 
																			hhi.crs ,analysisCase, hhi.insCancel);
						double[] InsuranceList1 = Insurer.insuranceCostf2( hhi.exposedValue, hhi.WillingToPay, 
																			hhi.SFHA , hhi.elevation, hhi.eleHeight, 
																			hhi.BFE, hhi.PreFRIM, hhi.Ptype, 
																			hhi.crs ,analysisCase, hhi.insCancel);
						
						hhi.InsuranceCoverage1 		= InsuranceList0[0] ;
						hhi.InsuranceCost1 			= InsuranceList0[1] ;
						hhi.InsSubsidy1				= InsuranceList0[2] ;
						
						hhi.InsuranceCoverage2 		= InsuranceList0[3] ;
						hhi.InsuranceCost2 			= InsuranceList0[4] ;
						hhi.InsSubsidy2				= InsuranceList0[5] ;
						
						hhi.RiskInsuranceCoverage1 	= InsuranceList1[0] ;
						hhi.RiskInsuranceCost1     	= InsuranceList1[1] ;
						hhi.RiskInsSubsidy1		   	= InsuranceList1[2] ;
						
						hhi.RiskInsuranceCoverage2 	= InsuranceList1[3] ;
						hhi.RiskInsuranceCost2     	= InsuranceList1[4] ;
						hhi.RiskInsSubsidy2		   	= InsuranceList1[5] ;
						changeAttr = 1;
					}
					
					
					// important  2

		            hhi.risk_perception = hhi.BayesAlpha / ( hhi.BayesAlpha + hhi.BayesBeta ) ; 

//					System.out.println( hhi.risk_perception + "  " + hhi.prospectAlpha ) ;
					
					//* attribute to relocation *//
					TotWaterH = TotWaterH + floodHeight_mu1 ;
					
					hhi.RiskEvaluation(FInundation, floodHeight_mu1, hhi.seawall);
					
					/** Risk Adaptation start *********************************************************/
					
					TotRiskPerct = TotRiskPerct + hhi.risk_perception ;
					if(	 hhi.risk_perception > p_th || hhi.requiredIns == 1 ){
//						System.out.println( hhi.requiredIns +" "+ hhi.WillingToPay );
						if( analysisCase <= 3 ){
							hhi.Adaptation2(floodHeight_mu1, yi, hhi.seawall, analysisCase);
						}else{
							hhi.Adaptation3(floodHeight_mu1, yi, hhi.seawall, analysisCase);
						}
						EvaluatedN = EvaluatedN + 1;
						hhi.poissonLamda = hhi.poissonLamda0;
						
						if( hhi.floodZone == 1 && hhi.requiredIns == 0 && hhi.floodDamage1 >= 1){
							hhi.requiredIns = 1 ;
							requiredInsN = requiredInsN + 1;
							hhi.InsuranceCoverage1 		= hhi.InsuranceCoverage2;
							hhi.InsuranceCost1 			= hhi.InsuranceCost2  ;
							hhi.InsSubsidy1				= hhi.InsSubsidy2	 ;
							hhi.RiskInsuranceCoverage1 	= hhi.RiskInsuranceCoverage2 ;
							hhi.RiskInsuranceCost1     	= hhi.RiskInsuranceCost2 ;
							hhi.RiskInsSubsidy1		   	= hhi.RiskInsSubsidy2 ;
						}
						
//						if( hhi.insuranceCost > 0 ){
//							hhi.poissonLamda = poissoni.sample() ;
//						}
//						System.out.println( hhi.insuranceCost + " " + hhi.InsuranceCost1 + " " + hhi.RiskInsuranceCost1 ) ; 
					}else{
						if( hhi.insuranceCost > 0 ){
//							System.out.println( hhi.insuranceCost  ) ; Math.random() < 0.01 &&
							if( hhi.poissonLamda >= 0){
								hhi.poissonLamda = hhi.poissonLamda - 1;
							}
							if( hhi.poissonLamda < 0 ){
								hhi.AdaptationCost  	=  hhi.AdaptationCost - hhi.insuranceCost;
								hhi.insuranceCoverage 	= 0.0;
								hhi.insuranceCost 		= 0.0;
								hhi.insuranceLevel  	= 0;
								hhi.subsidy  	= 0;
								
							}
						}
						if(	Math.random() < 0.01 && hhi.FloodAD == 1 && hhi.damageCount < 1 && hhi.floodZone  == 0	){
							hhi.FloodAD = 0;
							hhi.ElevationYrs = 0;
						}
					}
					
					/** Risk Adaptation  end***************************************************************/
					/** Outputs  **/
//					totalPropertyVal 	+= hhi.exposedValue;
					floodrisk1 			+= hhi.FloodRisk1 ;
					TotalInsuredCost 	+= hhi.insuranceCost;
					TotalCoverage 		+= hhi.insuranceCoverage;
					fdamage1 			+= hhi.floodDamage1;
					fdamage0			+= hhi.floodDamage0;
					compensate 			+= hhi.subsidy;
					/** Outputs  **/
					
					if(hhi.insuranceCost > 0 ){
						if(  hhi.floodDamage1 > 0){
							if( hhi.insuranceCoverage > hhi.floodDamage1 ){
								if( hhi.floodDamage1 > 500 ){
									Policy_min_Claims 	= Policy_min_Claims + hhi.insuranceCost - hhi.floodDamage1  + 500 ;
								}else{
									Policy_min_Claims 	= Policy_min_Claims + hhi.insuranceCost ;
								}
							}else{
								Policy_min_Claims		= Policy_min_Claims + hhi.insuranceCost - hhi.insuranceCoverage + 500;
							}
						}else{
							Policy_min_Claims 			= Policy_min_Claims + hhi.insuranceCost ;
						}
					}
					
					if( hhi.floodDamage1 > 0 ){
						if( CensusCount.containsKey(hhi.CensusID) ){
							CensusCount.get(hhi.CensusID).damageCount = CensusCount.get(hhi.CensusID).damageCount + 1;		
//							if( hhi.insuranceCost > 0){
//								CensusCount.get(hhi.CensusID).claimN_yi  = CensusCount.get(hhi.CensusID).claimN_yi + 1;
//							}
							if(  hhi.floodDamage1 > hhi.exposedValue*0.5  &&  hhi.exposedValue > 0 ){
								CensusCount.get(hhi.CensusID).lossCount = CensusCount.get(hhi.CensusID).lossCount + 1;		
							}
						}
						hhi.ExperAlpha[MemoryBanki] = 1.0; // experience
						hhi.ExperBeta[MemoryBanki]  = 0.0; 
					}else{
						hhi.ExperAlpha[MemoryBanki] = 0.0; // experience
						hhi.ExperBeta[MemoryBanki]  = 1.0; // experience
						
					}
					
//					System.out.println( MemoryBanki );
					if( floodH_yrsi > 0 ){
						neighborExper = neighborEffects(IDMaps, hhi);
						hhi.ExperAlpha[MemoryBanki] = hhi.ExperAlpha[MemoryBanki] + weighti*  neighborExper[0]; // experience
						hhi.ExperBeta[MemoryBanki]  = hhi.ExperBeta[MemoryBanki]  + weighti*( neighborExper[1] - neighborExper[0] );
//						System.out.println( neighborExper[0] + " " + neighborExper[1] );
					}
	//				hhi.indirectexperience = Arrays.stream(hhi.IndirectFloodMemory).sum();					
					Nbetaa			= Arrays.stream(hhi.ExperBeta).sum();
					hhi.BayesBeta   = hhi.BayesBeta + Nbetaa;
					hhi.BayesAlpha 	= hhi.BayesAlpha + Arrays.stream(hhi.ExperAlpha).sum();
					
//					hhi.floodCounts = FloodCount;
					
					if( hhi.FloodAD == 1 ){
						totalElev ++;
						hhi.ElevationYrs++;
					}
					if( hhi.subsidy > 0){
						TotalSubsidy = TotalSubsidy + hhi.subsidy;
					}
					if( hhi.FloodAD > 0 ){
						totalAdapte++;
					}
					if( hhi.insuranceCost > 0){
						TotalInsuer++;
						if( CensusCount.containsKey(hhi.CensusID) ){
							CensusCount.get(hhi.CensusID).policyN_yi = CensusCount.get(hhi.CensusID).policyN_yi + 1;
						}		
						totalAdapte++;
					}
					
					if( hhi.floodDamage1 > hhi.exposedValue*0.3  &&  hhi.exposedValue > 0){
						if( hhi.insuranceCoverage < hhi.floodDamage1 ){
							hhi.lessCover = 1;
						}
					}
					
					if( hhi.floodDamage1 > hhi.exposedValue*0.5 &&  hhi.exposedValue > 0){
						hhi.damageCount = hhi.damageCount +1;
						hhi.FloodAD = 0;
					}
					
					if( hhi.floodDamage1 > 0  ){
						damageN	= damageN	+ 1;
					}
					
					/** Scenario  **/
	//				if( hhi.exposedValue  > 0){
	//					hhi.exposedValue = PropertyJustVal( hhi, 0.0, DiscountRate, yi) ;
	//				}else{
	//					hhi.exposedValue = 0.0 ;
	//				}
//					if(hhi.insuranceCoverage > 0.0){
//						if( hhi.insuranceCoverage > hhi.floodDamage1){
//							if(hhi.subsidy > 2*hhi.Justvalue ){
////								hhi.subsidy += hhi.Justvalue - hhi.floodDamage1;
//							}
//							
////							else{
////								hhi.subsidy = hhi.subsidy + hhi.floodDamage1;
////							}
//						}else{
//							if(hhi.subsidy > 2*hhi.Justvalue ){
////								hhi.subsidy += hhi.Justvalue - hhi.insuranceCoverage;
//							}
////								else{
////								hhi.subsidy = hhi.subsidy + hhi.insuranceCoverage;
////							}
//						}
//					}
					if( hhi.exposedValue > 0 && floodHeight_mu1 < (hhi.elevation + hhi.eleHeight + gov1.seawallH  ) ){
						if( hhi.exposedValue <= 0){ hhi.exposedValue = 0.0;}
						hhi.TotFdamage  	+= hhi.Fdamage;
						fdamage 			+= hhi.floodDamage1;
						TotalACost			+= hhi.AdaptationCost;
					}else {
						//System.out.println( "Loss year happened "+ yi );	
						lostProperty 	 	= lostProperty + 1;
						analysisval 	 	=  Math.abs(hhi.CurrentLADVAL);
						hhi.CurrentLADVAL	= 0.0;
						hhi.exposedValue 	= 0.0;
						hhi.TotFdamage 		+= analysisval;
						fdamage 			+= analysisval;
						TotalACost			+= hhi.AdaptationCost;
						hhi.relocate		= 1;
						hhi.PropertyLoss 	= 1;
					}
					
					if(fdamage < 0 || hhi.CurrentLADVAL < 0){
						System.out.println( hhi.Fdamage + " "+ hhi.CurrentLADVAL );
					}
					
					if( hhi.damageCount >= 2 ){
						if(  analysisCase == 3  ){
							analysisval = 0.0;
							hhi.exposedValue = 0.0;
							hhi.FloodAD 			= 0;
							hhi.insuranceCost 		= 0;
							hhi.insuranceCoverage 	= 0;
							hhi.insuranceLevel  	= 0;
							hhi.requiredIns 		= 0;
							if( hhi.subsidy > 2*hhi.Justvalue  ){
								hhi.CurrentLADVAL	 = 0.0;
								hhi.TotFdamage 		+= 0.0;
								hhi.Fdamage    		 = 0.0;
								hhi.relocate		 = 1;
							}
							hhi.FhaseOut = yi+ 1;
							existN = existN + 1;
						}
					}
					
					hhi.TotFloodDamage   += hhi.floodDamage0;
					hhi.TotFloodDamage1  += hhi.floodDamage1;;		
					hhi.TotadaptCost     += hhi.AdaptationCost ;
					if( hhi.FloodAD > 0){
						hhi.TotEleCost		 += hhi.elePay; 
					}
					hhi.TotInsCost   	 += hhi.insuranceCost;
					hhi.TotSubsidy 		 += hhi.subsidy;
				} // end of total households loop
				ResultInfo[loopi][0] [yi ]  = fdamage1 / (Allhouseholds.size() - existN);
				ResultInfo[loopi][1] [yi ]  = TotRiskPerct / (Allhouseholds.size()*1.0 );
				ResultInfo[loopi][2] [yi ]  = floodrisk1;
				ResultInfo[loopi][3] [yi ]  = TotalACost;
				ResultInfo[loopi][4] [yi ]  = EvaluatedN;
				ResultInfo[loopi][5] [yi ]  = fdamage1; 
				ResultInfo[loopi][6] [yi ]  = fdamage0;
				ResultInfo[loopi][7] [yi ]  = damageN;	
				ResultInfo[loopi][8] [yi ]  = totalAdapte;
				ResultInfo[loopi][9] [yi ]  = totalElev;				
				ResultInfo[loopi][10][yi ]  = TotalSubsidy;
				ResultInfo[loopi][11][yi ]  = TotalInsuredCost; 
				ResultInfo[loopi][12][yi ]  = TotalCoverage; 
				ResultInfo[loopi][13][yi ]  = TotalInsuer;
				ResultInfo[loopi][14][yi ]  = Policy_min_Claims;
				ResultInfo[loopi][15][yi ]  = compensate;
				ResultInfo[loopi][16][yi ]  = existN;
				ResultInfo[loopi][17][yi ]  = requiredInsN;
				
//				System.out.println( yi + " "+ TotalInsuer );		
				Iterator<Census> cenidx = CensusList.iterator();
				while ( cenidx.hasNext() ) {
					Census censusti = cenidx.next();
					CensusCount.get(censusti.CensusID).floodCountwP[MemoryBanki] = CensusCount.get(censusti.CensusID).policyN_yi ;
//					CensusCount.get(censusti.CensusID).policyCount[MemoryBanki]  = CensusCount.get(censusti.CensusID).claimN_yi ;
					
					CensusCount.get(censusti.CensusID).CensusFloodCount = CensusCount.get(censusti.CensusID).damageCount* 1.0 / CensusCount.get(censusti.CensusID).N* 1.0;
					CensusCount.get(censusti.CensusID).CensusLossCount = CensusCount.get(censusti.CensusID).lossCount* 1.0 / CensusCount.get(censusti.CensusID).N* 1.0;
					
					CensusCount.get(censusti.CensusID).damageCount = 0;	
					CensusCount.get(censusti.CensusID).lossCount   = 0;	
					CensusCount.get(censusti.CensusID).policyN_yi  = 0;	
//					CensusCount.get(censusti.CensusID).claimN_yi   = 0;
					
					CensusCommunityAdaptationCount = CensusCommunityAdaptationCount + CensusCount.get(censusti.CensusID).communityAdaptation;
				}  // end of while loop
				ResultInfo[loopi][18][yi ]  = CensusCommunityAdaptationCount;
				
			} // end of total simulation years
			Iterator<Properties> iter31 = Allhouseholds.iterator();
			while(iter31.hasNext())  {
				Properties hhi = iter31.next();
				if( hhi.FhaseOut > 0 ){
					hhi.AvgPhaseOut = hhi.AvgPhaseOut + 1.0;
				}
			}
			
		} // end of big monte-carlo loop
		Iterator<Properties> iter4 = Allhouseholds.iterator();
		while(iter4.hasNext())  {
			Properties hhi = iter4.next();
			hhi.TotFloodDamage   = hhi.TotFloodDamage / (Totyrs*Nloops*1.0);
			hhi.TotCumFloodDamage1  = hhi.TotFloodDamage1 / (Nloops*1.0);
			hhi.TotFloodDamage1  = hhi.TotFloodDamage1 / (Totyrs*Nloops*1.0);
			hhi.TotadaptCost     = hhi.TotadaptCost / (Totyrs*Nloops*1.0) ;
			hhi.TotEleCost		 = hhi.TotEleCost / (Totyrs*Nloops*1.0); 
			hhi.TotInsCost   	 = hhi.TotInsCost / (Totyrs*Nloops*1.0);
			hhi.TotSubsidy 		 = hhi.TotSubsidy / (Totyrs*Nloops*1.0);
			hhi.AvgPhaseOut 	 = hhi.AvgPhaseOut / (Nloops*1.0);
		}
		String csvFile7 = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Results\\" + tail;
		CsvFileWriter.writeFinalParcel(Allhouseholds, csvFile7) ;
		
		System.out.println(tail +" Finished !");
		return ResultInfo;
	}
	
	public static void Sort_RiskLevel( ArrayList<Properties> residentBuildings ){
		Map<Integer, Double> unSortedMap = new HashMap<>();
		Iterator<Properties> iter = residentBuildings.iterator();
		while(iter.hasNext()){
			Properties budlidngi = iter.next();
			double Surge[] = { budlidngi.cate1, budlidngi.cate2, budlidngi.cate3, budlidngi.cate4, budlidngi.cate5 };
			budlidngi.FloodRisk0  	=  Properties.FloodRiskEstmator( Surge, budlidngi.Justvalue, 
															budlidngi.Ptype, budlidngi.elevation, 0, 0, 0);
			unSortedMap.put(budlidngi.FID, budlidngi.FloodRisk0);
		}
		LinkedHashMap<Integer, Double> reverseSortedMap = new LinkedHashMap<>();
		unSortedMap.entrySet()
	    .stream()
	    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) 
	    .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
		Set<Integer> keys = reverseSortedMap.keySet();
		int rankK = 0;
		LinkedHashMap<Integer, Integer> FIDMapRank = new LinkedHashMap<>();
		for(Integer k:keys){
			FIDMapRank.put(k, rankK) ;
			rankK++;
		}
	}
	
	public static void CreateHousehold( ArrayList<Properties> residentBuildings, 
			ArrayList<Census> CensusList, int initi, double p_th){
		
		Iterator<Census> iterc = CensusList.iterator();
		while(iterc.hasNext()){
			Census censusi = iterc.next();
			censusi.damageCount = 0;
			censusi.lossCount = 0;
			censusi.CensusFloodCount = 0.0;
			censusi.CensusLossCount = 0.0;
			Arrays.fill(censusi.floodCountwP, 0);
			Arrays.fill(censusi.policyCount , 0);
			censusi.policyN_yi = 0;
//			censusi.claimN_yi  = 0;
			censusi.communityAdaptation  =  0;
		}
		Iterator<Properties> iter = residentBuildings.iterator();
//		HashMap<Integer, Census> census_dict = censusDict(CensusList);
		while(iter.hasNext()){
			Properties budlidngi = iter.next();
			budlidngi.exposedValue = budlidngi.Justvalue;
//			budlidngi.directExperience   = 0;
//			budlidngi.indirectexperience = 0;
			budlidngi.SVIndex    = 0;
			budlidngi.RiskPIndex = 0;
			budlidngi.insCancel 	= 0;
//			PoissonDistribution poissoni = new PoissonDistribution(1.428);
//			budlidngi.poissonLamda = poissoni.sample() ;
			budlidngi.poissonLamda = budlidngi.poissonLamda0;
			budlidngi.AgeL   	= budlidngi.AgeL0 ;
			budlidngi.LiveYrs   = 0 ;
			budlidngi.FhaseOut  = -1;
			budlidngi.subsidy 			= 0;
			budlidngi.SLRheight 		= 0.0;
			budlidngi.insuranceCost 	= 0.0;
			budlidngi.insuranceCoverage = 0.0;
			budlidngi.totalCost 		= 0.0;
			
			budlidngi.eleHeight = 0;
			budlidngi.elecosts 	= 0.0;
			budlidngi.elePay   	= 0;
			budlidngi.elevated  = 0;
			budlidngi.PreFRIM 	= 0;
			budlidngi.govern_p 	= 0;
			budlidngi.requiredIns = 0;
			
			Arrays.fill(budlidngi.ExperAlpha, 0.0);
			Arrays.fill(budlidngi.ExperBeta , 0.0);
			budlidngi.BayesAlpha = 0.0;
			budlidngi.BayesBeta  = 1.0;
			budlidngi.OptimizedInsuranceCost = 0.0;
			budlidngi.OptimizedCoverage = 0.0; 
			budlidngi.examined 			= 0;
			
			budlidngi.MitigatedRisk  	= 0;
			budlidngi.prospectAlpha		= 0;
			
			budlidngi.risk_perception 	= budlidngi.risk_perception0;
			budlidngi.FloodAD			= 0;			
			budlidngi.AdaptationCost 	= 0;
			budlidngi.relocate			= 0;
			budlidngi.PropertyLoss  	= 0;
			budlidngi.seawall 			= 0.0;
			budlidngi.lessCover 		= 0; 
			budlidngi.floodDamage1 		= 0;
			budlidngi.floodMem 			= 0;
			budlidngi.FloodRisk1 		= 0.0;
			
			budlidngi.damageCount		= 0;
			budlidngi.crs 				= 0.15 ;
			budlidngi.slrHeight 		= 0;
			budlidngi.Fdamage			= 0;
			budlidngi.floodDamage0		= 0;
			budlidngi.TotFdamage 		= 0.0;	
			
			budlidngi.ElevationYrs 		= 0;
			
			budlidngi.FloodRisk0 	 = budlidngi.QuantifyRisk(0.0, 0.0, budlidngi.Ptype, 0, 0, 0);
			budlidngi.frisk_initial = budlidngi.FloodRisk0;
			budlidngi.TraditionalElevating( 0 );
//			budlidngi.WetProofing( 0 );
//			budlidngi.DryProofing( 0 );
			budlidngi.elePay 	= Payment( 0.008, 30, budlidngi.elecosts );	
			
//			budlidngi.SVIndex    = budlidngi.SocialVulnerability(budlidngi.Income, budlidngi.Education, budlidngi.Race);
//			budlidngi.RiskPIndex = budlidngi.RiskPerception(budlidngi.floodZone, budlidngi.directExperience, budlidngi.indirectexperience);
		}
	}
	
	
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String Censuspth = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Data_processed\\Miami_NFIP_census_summary.csv" ;
		String Residpth  = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Data_processed\\MiamiParcels_MCMC.csv";	
		String[] scenario_names = {"case0", "case1", "case2", "case3", "case4"}; // "_SLR05", "_SLR12", "_SLR20"
		int[] scenario_labels = { 0, 1, 2, 3, 4} ; 
//		int[] scenario_labels = { 0 } ;
		int simYrs  = 50;
		int totRepN = 1;
		double capacity = 0.8;
		double[][] SScate = new double[totRepN][simYrs];		
		Random rand = new Random(); 
		
		for(int ri = 0; ri< totRepN; ri++){
			for(int kii =0; kii < simYrs; kii++){
				SScate[ri][kii] = rand.nextDouble();
			}
		}
		
		long elapsedTime;
		long starttime ;
		long endtime ;
		
		double threshiold_p = 0.01;
		
		
		ArrayList<Properties> singleFamilyHouses 	= readProperty(Residpth, 0.1);
		ArrayList<Census> CensusList 				= readCensuses(Censuspth); 
		Risk_Evaluation(singleFamilyHouses, threshiold_p, 4);
//		ArrayList<Properties> AdaptProperty = GenerateHouseholds(singleFamilyHouses, CensusList);
		for( int scei = 0; scei < scenario_labels.length ; scei++){
			double seaWallH;
			int analysisi  = scenario_labels[scei] ;
			String scename = scenario_names[scei] ; 
			if( analysisi == 0 ){
				seaWallH = 0.0;
			}else if( analysisi == 1 ){
				seaWallH = 0.0;
			}else if( analysisi == 2 ){
				seaWallH = 2.0;
			}else {
				seaWallH = 2.0;
			}
			Government governor1 = new Government();
			governor1.SeaWallCostEstimator(singleFamilyHouses, seaWallH, 1);
//			String[] tails_str = {"_SLR02", "_SLR05", "_SLR12", "_SLR20","_SLR22"}; // "_SLR05", "_SLR12", "_SLR20"
			String[] tails_str = {"_SLR00"};
//			double[] slrsces   =  {0.000003, 0.000033, 0.000103, 0.000183, 0.000183};
			double[] slrsces   =  {0.000003, 0.000003, 0.000003, 0.000003, 0.000003};
			for( int k0 = 0; k0 < tails_str.length ; k0++){
				
				String tail = "_w" + String.valueOf(seaWallH) + String.valueOf(0);
				double SLRb   = slrsces[k0];
				String taili  = tails_str[k0] + tail + ".csv";
				String taili2 = scename+ "2"+taili ;
				starttime = System.nanoTime() ;
				
				double[] input_doubles = { SLRb, analysisi, 0.2, simYrs, 50, seaWallH, 0.01f, totRepN, threshiold_p, 0.1};
				double[][][] ResultInfo = futureChange(singleFamilyHouses, CensusList, governor1, input_doubles, SScate, taili2); 
				
				System.out.println( singleFamilyHouses.size());
				String csvFile6 = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Results\\Default\\SimuResult2_" + scename + taili;
				CsvFileWriter.writeEvacuation(ResultInfo[0], csvFile6) ;
				endtime = System.nanoTime() ;
				elapsedTime = endtime - starttime;
//				CreateHousehold(singleFamilyHouses, CensusList, 1, threshiold_p);
				System.out.println("Execution time in minutes "+ elapsedTime/1000000000.0/60.0 );
			}	
		}
	}
}
