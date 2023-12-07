
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.distribution.BetaDistribution;
import org.apache.commons.math3.distribution.LogNormalDistribution;
import org.apache.commons.math3.distribution.PoissonDistribution;



public class Properties {
	public final static double xi1 = 0.1270625   , mu1 = 6.5057213    , beta1 = 1.5659975; // unit feet
	public final static double xi2 = 0.1160023   , mu2 = 7.8278154    , beta2 = 2.2505572; // unit feet
	
	public final static double xi3 = 0.1471483   , mu3 = 4.2803585    , beta3 = 1.511572 ; // unit feet
	public final static double xi4 = 0.1367217   , mu4 = 9.2042769    , beta4 = 2.2022389 ; // unit feet
	public final static double xi5 = 0.1168172   , mu5 = 3.8102603    , beta5 = 0.9898370; // unit feet
	
	public final static double xi6 = 0.1317424   , mu6 = 6.1144574    , beta6 = 2.3204303 ; 
	public final static double xi7 = -0.0214855  , mu7 = 5.2346275    , beta7 = 1.7835003; 
	
	public final static double xi8 = 0.0966425   , mu8 = 7.0159768    , beta8 = 2.4348513 ; 
	public final static double xi9 = -0.05731282 , mu9 = 7.91555106   , beta9 = 1.47552469 ; 
	public final static double xi10 = 0.1313929  , mu10 =  11.8365851 , beta10 = 2.1244820; 
	
	public static double[] probs  = { 0.1, 0.04, 0.02, 0.01, 0.002};
	public static double[] probs_damage  = {0.5, 0.2, 0.15, 0.1, 0.07, 0.05, 0.03, 0.02, 0.01, 0.007, 0.005, 0.002, 0.001};
	public static int SLR_threshold = 2; /* 0 */
	public double[] updated_p =  new double[ (probs.length+1) ];
	
	public double[] updated_p0 =  { 0.828, 0.1, 0.04, 0.02, 0.01, 0.002};
	
	public int    FID, agentID, Desc , EFFYER , HOUSEHOLDS, withIns, AgeL, AgeL0, LiveYrs, Ptype;
	public int    JVclass, Race, Education, Income, Nfriend;
	
	public double IMPROVVAL , Justvalue, exposedValue, LADVAL, CurrentLADVAL, 
					bfeFloodH, slrHeight, seawall, crs ;
	public int    NOBULDNG , NORESUNTS , ownership, morgage, CateLabel;
	public double cate1 , cate2 , cate3, cate4, cate5;
	public double cate10 , cate20 , cate30, cate40, cate50;
	public double latitude , longitude;
	public double liveArea , elevation, coastDistance;
	
	public double Normal_b0, Normal_age, Normal_LiveYrs, Normal_JVclass, Normal_floodZone, Normal_Race;
	
	public double BayesAlpha, BayesBeta, risk_perception, risk_perception0, prospectAlpha, WillingToPay;
	public String CensusID;
	public List<Integer> neighbors;
	public double BFE;
	public String SFHA;
	public String insType;
	
	public double xi0, mu0, beta0; // unit feet
	
	public int pastFlood, floodZone, FhaseOut;
	public int SVIndex, RiskPIndex, FloodAD, floodMem;
	public double[] ExperAlpha = new double[51];
	public double[] ExperBeta  = new double[51];
	
	public double gov_Fheight_reduct, gov_flood_damage_R;
	
	public int poissonLamda0, poissonLamda;
	
	public double InsuranceCost1, InsuranceCost2, InsuranceCost3;
	public double InsuranceCoverage1, InsuranceCoverage2, InsuranceCoverage3;
	public double InsSubsidy1, InsSubsidy2, InsSubsidy3;
	
	public double OptimizedInsuranceCost, OptimizedCoverage;
	public int OptimalElevated, examined;
	public double RiskInsuranceCost1, RiskInsuranceCost2, RiskInsuranceCost3;
	public double RiskInsuranceCoverage1, RiskInsuranceCoverage2, RiskInsuranceCoverage3;
	public double RiskInsSubsidy1, RiskInsSubsidy2, RiskInsSubsidy3;
//	public double elevationPay2, elevationPay4, elevationPay6, elevationPay8;
	
//	public double RA0, RAknowledge,  RApos, RAneg, BayesPR, govern_p = 0;
	public int friendCount, lessCover, damageCount, PreFRIM, insCancel;
	public ArrayList<Integer> friends;
	
	public double frisk_initial, FloodRisk1, FloodRisk0, totalCost, 
					realDamage, floodDamage1, floodDamage0, 
					MitigatedRisk, TotFdamage, Fdamage, TotCumFloodDamage1;
	public double AdaptationCost, CBratio, AfterRisk;
	public double SLRheight;
	
	public double a1, a2, a3, a4, a5;
	public double TotFloodDamage, TotFloodDamage1, TotadaptCost, TotEleCost, TotInsCost, TotSubsidy, AvgPhaseOut;
	public double insuranceCost, insuranceCoverage, subsidy, govern_p;
	public double elecosts, eleHeight, elePay ;
	public int elevated, floodCounts;
	public int ElevationYrs, relocate, PropertyLoss, requiredIns, insuranceLevel;
	public Properties(){}
	public static void printArray(String[] rows) {
	      for (int i = 0; i < rows.length; i++) {
	         System.out.println(i + " , " +rows[i]);
	         System.out.print('\n');
	      }
	      System.out.println("\nlength of the array is "+ rows.length);
	}
	public Properties(String[] line)  {
//		printArray(line);
		this.AgeL		= (int)Double.parseDouble(line[0] );
		this.Ptype		= (int)Double.parseDouble(line[1] );
		this.EFFYER    = (int)Double.parseDouble(line[4] );
		this.FID       = (int)Double.parseDouble(line[6] );				
		this.Justvalue = Double.parseDouble(line[8] );
		this.elevation = Double.parseDouble(line[2] );
		this.coastDistance = Double.parseDouble(line[3] );
		
		if( this.elevation < 0 ){
			this.elevation = 0.0;
		}
		
		this.TotFloodDamage   = 0.0;	
		this.TotFloodDamage1  = 0.0;	
		this.TotadaptCost     = 0.0;	
		this.TotEleCost		  = 0.0;	
		this.TotInsCost   	  = 0.0;	
		this.TotSubsidy 	  = 0.0;		
		this.AvgPhaseOut 	  = 0.0;		
		
		this.JVclass = (int) Double.parseDouble(line[9] );
		this.Race = (int) Double.parseDouble(line[24] );
		this.Education = (int) Double.parseDouble(line[5] );
		this.Income = (int) Double.parseDouble(line[21] );
		
		this.LADVAL    = Double.parseDouble(line[10] );
		this.NORESUNTS = (int) Float.parseFloat(line[12] );
		this.SFHA  = line[7];
		this.CateLabel = (int) Double.parseDouble(line[24] );
		
		if(this.CateLabel == 0){
			this.xi0 = xi1; 
			this.mu0 = mu1; 
			this.beta0 = beta1;
		}else if( this.CateLabel == 1){
			this.xi0 = xi2; 
			this.mu0 = mu2; 
			this.beta0 = beta2;
		}else if( this.CateLabel == 2){
			this.xi0 = xi3; 
			this.mu0 = mu3; 
			this.beta0 = beta3;
		}else if( this.CateLabel == 3){
			this.xi0 = xi4; 
			this.mu0 = mu4; 
			this.beta0 = beta4;
		}else if( this.CateLabel == 4){
			this.xi0 = xi5; 
			this.mu0 = mu5; 
			this.beta0 = beta5;
		}else if( this.CateLabel == 5){
			this.xi0 = xi6; 
			this.mu0 = mu6; 
			this.beta0 = beta6;
		}else if( this.CateLabel == 6){
			this.xi0 = xi7; 
			this.mu0 = mu7; 
			this.beta0 = beta7;
		}else if( this.CateLabel == 7){
			this.xi0 = xi8; 
			this.mu0 = mu8; 
			this.beta0 = beta8;
		}else if( this.CateLabel == 8){
			this.xi0 = xi9; 
			this.mu0 = mu9; 
			this.beta0 = beta9;
		}else{
			this.xi0 = xi10; 
			this.mu0 = mu10; 
			this.beta0 = beta10;
		}
		
//		if( this.elevation < this.mu0){
//			this.elevation = this.mu0 + Math.random()*2;
//		}
		this.elevation = this.elevation + Math.random()*4 + 2;
		
		this.cate10 = Double.parseDouble(line[15] );
		this.cate20 = Double.parseDouble(line[16] );
		this.cate30 = Double.parseDouble(line[17] );
		this.cate40 = Double.parseDouble(line[18] );
		this.cate50 = Double.parseDouble(line[19] );
		
		if(  this.cate50 < 0 ){
			this.mu0 = 0.0; 
		}
		
		this.cate1 = this.mu0 + ( (Math.pow( 0.1,  -1* this.xi0) - 1)*this.beta0 / this.xi0 );
		this.cate2 = this.mu0 + ( (Math.pow( 0.04, -1* this.xi0) - 1)*this.beta0 / this.xi0 );
		this.cate3 = this.mu0 + ( (Math.pow( 0.02, -1* this.xi0) - 1)*this.beta0 / this.xi0 );
		this.cate4 = this.mu0 + ( (Math.pow( 0.01, -1* this.xi0) - 1)*this.beta0 / this.xi0 );
		this.cate5 = this.mu0 + ( (Math.pow( 0.005,-1* this.xi0) - 1)*this.beta0 / this.xi0 );
		double min = 0.0;
		
//		if(  (this.cate4 > this.elevation) && (this.cate40 < 0)  ){
//			min = this.cate4 - this.elevation;
//			this.elevation = this.elevation + min ;
//			
//		}
//		
//		if(  (this.cate5 > this.elevation) && (this.cate50 < 0)  ){
//			min = this.cate5 - this.elevation;
//			this.elevation = this.elevation + min ;
//			
//		}
		
		this.BFE =  this.cate4;		
		this.CensusID =  line[20] ;
		
		this.latitude  		= Double.parseDouble(line[22] );
		this.longitude 		= Double.parseDouble(line[23] );	
		this.liveArea  		= Double.parseDouble(line[14] );
//		this.prospectAlpha 	= Double.parseDouble(line[25] );
		this.SLRheight = 0.0;
		this.exposedValue = this.Justvalue;
		this.friendCount = 0;
		
//		this.RApos = Math.random() * 0.9;
//		this.RAneg = Math.random();	
//		this.RAknowledge = 0.0;
//		this.RA0 = 0.0;
		
		this.PreFRIM 	= 0;
		this.insCancel 	= 0;
		
		int Azone = this.SFHA.indexOf('A');
		int Vzone = this.SFHA.indexOf('V');
		if(Azone >= 0 || Vzone >= 0){
			this.floodZone = 1;
		}else{
			this.floodZone = 0;
		}
//		if( this.floodZone == 1 ){
//			PoissonDistribution poissoni = new PoissonDistribution(2.8);
//			this.poissonLamda0 = poissoni.sample() ;
//		}else{
//			PoissonDistribution poissoni = new PoissonDistribution(2.4);
//			this.poissonLamda0 = poissoni.sample() ;
//		}
		this.poissonLamda0 = 0;
		
		
//		this.directExperience   = 0;
//		this.indirectexperience = 0;
		this.SVIndex    = 0;
		this.RiskPIndex = 0;
		this.elePay 	= 0;
		double b_prob, cdfX;
		cdfX = Math.random();
		BetaDistribution beta2 = new BetaDistribution(2, 7);
		Random rn = new Random();
		b_prob = beta2.inverseCumulativeProbability(cdfX);
//		if( this.ownership == 0){
//			this.bidIncome = this.Justvalue / (22 + (b_prob - 0.5)*12) * 5;
//		}else{
//			if( this.Justvalue < 150000){
//				this.bidIncome = this.Justvalue / (2.5 + rn.nextInt(2 + 1) -1.5) ;
//			}else if(this.Justvalue > 150000 && this.Justvalue < 1000000){
//				this.bidIncome = this.Justvalue / (3.5 + rn.nextInt(6 + 1) -3) ;
//			}else{
//				this.bidIncome = this.Justvalue / (5 + rn.nextInt(7 + 1) -2) ;
//			}
//		}
	}
	
	public static double elevateCost( double eleH, double EleArea, int yrsi){
		double HardMeasureCost = 0;
		if(eleH <= 2 & eleH >0){
			HardMeasureCost = (17*eleH )* EleArea  ;
		}else if(eleH < 8 & eleH >2){
			HardMeasureCost = (17*2 + 0.75*(eleH-2) )* EleArea  ;
		}else{
			HardMeasureCost = (17*2 + 1*(eleH-8) + 0.75*(eleH-2) )* EleArea  ;
		}
		HardMeasureCost = HardMeasureCost*Math.pow(1.02, 18);
		return HardMeasureCost;
	}
	
	public static double estimateCost( double eleH, double EleArea, int yrsi){
		double HardMeasureCost;
		if(eleH == 0){
			HardMeasureCost =0;
		}else if( eleH < 2 & eleH > 0 ){
			HardMeasureCost = (19.5*eleH + 46) * Math.sqrt( EleArea )*4  ;
		}else{
			HardMeasureCost = elevateCost( eleH, EleArea, yrsi)  ;
		}
		return HardMeasureCost;
		
	}

	
	public static HashMap<String, ArrayList<Double> > readPropertyParas(String csvFile)  {
		HashMap<String, ArrayList<Double> > ParaMap = new HashMap<String, ArrayList<Double> >(); 
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		try {
			br = new BufferedReader(new FileReader(csvFile));
			br.readLine();
			while ((line = br.readLine()) != null) {
				ArrayList<Double> PropertyParas = new ArrayList<Double>();
				String[] Dline = line.split(cvsSplitBy);
//				printArray(Dline);\
//				String[] slice = IntStream.range(start, end + 1)
//						.map(i -> arr[i])
//						.toArray();
				PropertyParas.add( Double.parseDouble(Dline[2] ) );
				PropertyParas.add( Double.parseDouble(Dline[3] ) );
				PropertyParas.add( Double.parseDouble(Dline[4] ) );
				PropertyParas.add( Double.parseDouble(Dline[5] ) );
				PropertyParas.add( Double.parseDouble(Dline[6] ) );
				PropertyParas.add( Double.parseDouble(Dline[7] ) );
				
				if ( Dline[0].indexOf("SINGLE FAMILY") != -1){
					ParaMap.put("SINGLE FAMILY", PropertyParas) ;
				}else if( Dline[0].indexOf("MULTI-FAMILY") != -1 ){
					ParaMap.put("MULTI-FAMILY", PropertyParas) ;
				}else if( Dline[0].indexOf("MOBILE HOMES") != -1 ){
					ParaMap.put("MOBILE HOMES", PropertyParas) ;
				}else if( Dline[0].indexOf("Other") != -1 ){
					ParaMap.put("Other", PropertyParas) ;
				}else{
					String sname = Dline[0]+ "_" +Dline[1]; 
					ParaMap.put(sname, PropertyParas) ;
				}
			}
//			System.out.println(" Parameter Map size " + ParaMap.size());
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
		return ParaMap;
	}
	
	public static HashMap<String, ArrayList<Double> > readAlphaParas(String csvFile)  {
		HashMap<String, ArrayList<Double> > ParaMap = new HashMap<String, ArrayList<Double> >(); 
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		try {
			br = new BufferedReader(new FileReader(csvFile));
			br.readLine();
			while ((line = br.readLine()) != null) {
				String ZipCode ; 
				String[] Dline 	= line.split(cvsSplitBy);
				ArrayList<Double> PropertyParas = new ArrayList<Double>();
//				printArray(Dline);
				ZipCode 		=  Dline[0];
				PropertyParas.add( Double.parseDouble(Dline[1] ) );
				PropertyParas.add( Double.parseDouble(Dline[2] ) );
				PropertyParas.add( Double.parseDouble(Dline[3] ) );
				
				ParaMap.put( ZipCode, PropertyParas ) ;
			}
			System.out.println(" Parameter Map size " + ParaMap.size());
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
		return ParaMap;
	}
	
	public static double[] FloodDamageFunc2( double inundation, int ptype, int adapti, double adaptH) {
		inundation = inundation / 3.28;
		adaptH = adaptH / 3.28 ;
		
		double damageFactor1 = 1.0; 
		double damageFactor2 = 1.0; 
		double inundation1 = 0.0; //meter
		double inundation2 = 0.0; //meter	
		
		if(damageFactor1 < 0){ damageFactor1 = 0; } 
		if(damageFactor2 < 0){ damageFactor2 = 0; }
		if(damageFactor1 > 1){ damageFactor1 = 1; } 
		if(damageFactor2 > 1){ damageFactor2 = 1; }
		
		inundation1 = inundation*damageFactor1;
		inundation2 = inundation*damageFactor2;
		double[] damage = {0, 0};
		double tempD = 0, tempC = 0;
		if( ptype <= 4 && ptype != 3){
			if(inundation1 > 0){
				tempD =  0.2391*Math.pow(inundation1, 3) - 3.5524*Math.pow(inundation1, 2) +  19.933*inundation1 + 11.623; //residential
			}else{
				tempD = 0;
			}
			if( inundation2 > 0){
				tempC =  0.1037*Math.pow(inundation, 3) - 2.815*Math.pow(inundation, 2)  +  20.898*inundation + 14.957;
			}else{
				tempC = 0;
			}
		}else {
			if(inundation1 > 0){
				tempD = -0.1347*Math.pow(inundation1, 3) + 1.1448*Math.pow(inundation1, 2) +  9.1078*inundation1 + 4.4057; //residential
			}else{
				tempD = 0;
			}
			if( inundation2 > 0){
				tempC =  0.6278*Math.pow(inundation2, 3) - 9.597*Math.pow(inundation2, 2)  +  47.22*inundation2  + 18.886;
			}else{
				tempC = 0;
			}
		}
		if(  adapti > 1 ){
			if( inundation < adaptH ){  
				if( adapti == 2 ){
					damageFactor1 = 0.2;
					damageFactor2 = 0.2;
				}else{
					damageFactor1 = 0;
					damageFactor2 = 0;
				}	
			}else {
				if( adapti == 2 ){
					damageFactor1 = 0.9;
					damageFactor2 = 0.9;
				}else{
					damageFactor1 = 0.9;
					damageFactor2 = 0.9;
				}	
			}
		}
		if(tempD> 100){
			tempD = 100;
		}
		tempD = tempD*damageFactor1;
		if( tempD > 0){
			damage[0] = tempD / 100;
		}else{
			damage[0] = 0;}
		damage[1] = 0;
		return damage;
	}
	
	
//	public static double FloodDamageFunc( double inundation) {
//		inundation = inundation / 3.28084; //meter
//		double damage = 0;
//		double tempD;
//		tempD = 0.2391*Math.pow(inundation, 3) - 3.5524*Math.pow(inundation, 2)+ 19.933*inundation +11.623; //residential
//		//tempS = 0.1037*Math.pow(inundation, 3) - 2.815*Math.pow(inundation, 2)+ 20.898*inundation +14.957; //residential
//		if( tempD > 0){
//			damage = tempD / 100;
//		}else{
//			damage = 0;
//		}
//		return damage;
//	}
	
	
	public int SocialVulnerability( int HHincome, int HHeducation, int HHrace){
		int SVindex = 0;
		if(HHincome == 1){
			if( HHeducation == 1){
				if( HHrace == 1){
					SVindex = 4;
				}else if( HHrace == 2){
					SVindex = 4;
				}else if( HHrace == 3){
					SVindex = 4;
				}else{
					SVindex = 4;
				}
			}else if( HHeducation == 2){
				if( HHrace == 1){
					SVindex = 3;
				}else if( HHrace == 2){
					SVindex = 4;
				}else if( HHrace == 3){
					SVindex = 4;
				}else{
					SVindex = 4;
				}
			}else{
				if( HHrace == 1){
					SVindex = 2;
				}else if( HHrace == 2){
					SVindex = 3;
				}else if( HHrace == 3){
					SVindex = 3;
				}else{
					SVindex = 3;
				}
			}
		}else if(HHincome == 2){
			if( HHeducation == 1){
				if( HHrace == 1){
					SVindex = 3;
				}else if( HHrace == 2){
					SVindex = 3;
				}else if( HHrace == 3){
					SVindex = 3;
				}else{
					SVindex = 3;
				}
			}else if( HHeducation == 2){
				if( HHrace == 1){
					SVindex = 1;
				}else if( HHrace == 2){
					SVindex = 2;
				}else if( HHrace == 3){
					SVindex = 2;
				}else{
					SVindex = 2;
				}
			}else{
				if( HHrace == 1){
					SVindex = 1;
				}else if( HHrace == 2){
					SVindex = 2;
				}else if( HHrace == 3){
					SVindex = 1;
				}else{
					SVindex = 1;
				}
			}
		}else if(HHincome == 3){
			if( HHeducation == 1){
				if( HHrace == 1){
					SVindex = 2;
				}else if( HHrace == 2){
					SVindex = 3;
				}else if( HHrace == 3){
					SVindex = 3;
				}else{
					SVindex = 3;
				}
			}else if( HHeducation == 2){
				if( HHrace == 1){
					SVindex = 1;
				}else if( HHrace == 2){
					SVindex = 2;
				}else if( HHrace == 3){
					SVindex = 2;
				}else{
					SVindex = 2;
				}
			}else{
				if( HHrace == 1){
					SVindex = 1;
				}else if( HHrace == 2){
					SVindex = 1;
				}else if( HHrace == 3){
					SVindex = 1;
				}else{
					SVindex = 1;
				}
			}
		}else{
			if( HHeducation == 1){
				if( HHrace == 1){
					SVindex = 2;
				}else if( HHrace == 2){
					SVindex = 2;
				}else if( HHrace == 3){
					SVindex = 2;
				}else{
					SVindex = 2;
				}
			}else if( HHeducation == 2){
				if( HHrace == 1){
					SVindex = 1;
				}else if( HHrace == 2){
					SVindex = 1;
				}else if( HHrace == 3){
					SVindex = 1;
				}else{
					SVindex = 1;
				}
			}else{
				if( HHrace == 1){
					SVindex = 1;
				}else if( HHrace == 2){
					SVindex = 1;
				}else if( HHrace == 3){
					SVindex = 1;
				}else{
					SVindex = 1;
				}
			}
		}
		return SVindex;
	}
	
	public int RiskPerception( int FloodZone, int DirectExper, int IndirectExper){
		int percept = 0;
		if(DirectExper == 0){
			if( IndirectExper == 0){
				if(FloodZone == 1){
					percept = 2;
				}else{
					percept = 1;
				}
			}else if( IndirectExper == 1){
				if(FloodZone == 1){
					percept = 2;
				}else{
					percept = 1;
				}
			}else{
				if(FloodZone == 1){
					percept = 1;
				}else{
					percept = 1;
				}
			}
		}else if( DirectExper == 1 ){
			if( IndirectExper == 0){
				if(FloodZone == 1){
					percept = 2;
				}else{
					percept = 1;
				}
			}else if( IndirectExper == 1){
				if(FloodZone == 1){
					percept = 3;
				}else{
					percept = 2;
				}
			}else{
				if(FloodZone == 1){
					percept = 2;
				}else{
					percept = 2;
				}
			}
		}else if( DirectExper == 2 ){
			if( IndirectExper == 0){
				if(FloodZone == 1){
					percept = 3;
				}else{
					percept = 3;
				}
			}else if( IndirectExper == 1){
				if(FloodZone == 1){
					percept = 3;
				}else{
					percept = 3;
				}
			}else{
				if(FloodZone == 1){
					percept = 4;
				}else{
					percept = 3;
				}
			}
		}else{
			if( IndirectExper == 0){
				if(FloodZone == 1){
					percept = 4;
				}else{
					percept = 4;
				}
			}else if( IndirectExper == 1){
				if(FloodZone == 1){
					percept = 4;
				}else{
					percept = 4;
				}
			}else{
				if(FloodZone == 1){
					percept = 4;
				}else{
					percept = 3;
				}
			}
		}
		return percept;
	}
	
	public static double surgeHeightF(double returnP, double fxmin, double beta0, double xi0){
		double surge2;
		surge2 = fxmin + ( (Math.pow( returnP, -xi0) - 1)*beta0 / xi0 ) ;
		if(surge2 < 0){surge2 = 0.0;}
		return surge2;
	}
	public double QuantifyRisk( double insuranceCoverage, double eleHeight, int ptype, int floodAd, double insinsuranceCoverage, double adaptH){
		double[] Surge  = new double[ probs.length ];
		double mu;
		mu = this.mu0 + this.SLRheight;
		for(int i = 0; i < probs.length; i ++){
			Surge[i] = surgeHeightF(probs[i], mu, this.beta0, this.xi0 ) ;
		}
		double[] riski = {0, 0};
		double riskValue = this.exposedValue ; 
		if(riskValue < 0){ riskValue = 0;}
		double new_house_height = eleHeight + this.elevation;		
		double TotalRisk;
		double[] height  = new double[ probs.length ];
		double[] risk_k2 = new double[ probs.length ];
		for( int i = 0; i < Surge.length; i ++ ){
			height[i] = Surge[i] - new_house_height;
			if( height[i] <= 0 ){
				height[i] = 0;
				risk_k2[i] = 1;
			}else{
				riski = FloodDamageFunc2(height[i], ptype, floodAd, adaptH);
				risk_k2[i] = riskValue*riski[0] - insinsuranceCoverage;
				if( risk_k2[i] <= 0 ){ risk_k2[i] = 0; }}
		}
		TotalRisk	=	( 	( risk_k2[0] + risk_k2[1] ) *(probs[0] - probs[1] ) 
							+ ( risk_k2[2] + risk_k2[1] ) *(probs[1] - probs[2] ) 
							+ ( risk_k2[3] + risk_k2[2] ) *(probs[2] - probs[3] )
							+ ( risk_k2[4] + risk_k2[3] ) *(probs[3] - probs[4] ) ) / 2;
		return TotalRisk;
	}
	

	public static double FloodRiskEstmator(double[] surgeHeight, double value, int Ptype, 
			double propertyHeight , double insCoverage, int floodAD, double adaptH){
		if(  propertyHeight < 0){ System.out.println("An error occured "+ propertyHeight);}
		double FloodRiski = 0.0;
		FloodRiski =  FloodRiskF( propertyHeight, surgeHeight, value, Ptype, insCoverage, floodAD, adaptH );
		if( FloodRiski < 0){ FloodRiski = 0;}
		return FloodRiski;
	}

	
	public static double FloodRiskF( double DEM_elevH, double[] surges, double value, int ptype, double insinsuranceCoverage, int floodAd, double adaptH) {
		double TotalRisk = 0;
		double[] height = new double[ probs.length ];
		double[] riski = {0, 0};
		double[] risk_k = new double[ probs.length ];
		for( int i = 0; i < surges.length; i ++){
			if(surges[i] < 0){ surges[i] = 0;}
			height[i] = surges[i] - DEM_elevH;
			if( height[i] <= 0 ){
				height[i] = 0;
				risk_k[i] = 0;
			}else{
				riski = FloodDamageFunc2(height[i], ptype, floodAd, adaptH);
				risk_k[i] = value*riski[0] - insinsuranceCoverage;
				if( risk_k[i] <= 0 ){ risk_k[i] = 0; }
			}
		}
		
		TotalRisk = ( (risk_k[0] + risk_k[1]) *Math.abs(probs[0] - probs[1]) + 
				  	  (risk_k[1] + risk_k[2]) *Math.abs(probs[1] - probs[2]) +  
				  	  (risk_k[2] + risk_k[3]) *Math.abs(probs[2] - probs[3]) +
				 	  (risk_k[3] + risk_k[4]) *Math.abs(probs[3] - probs[4])   ) / 2;
		if( Double.isNaN(TotalRisk)  || Double.isInfinite(TotalRisk)) {
			System.out.println( "An error in flood estimator occured "+ Arrays.toString(risk_k) );
		}
		if( TotalRisk < 0){ TotalRisk = 0;}
		return TotalRisk;
	}
	
	public static double DryProofingCost( int ptype, double LivingArea, double dproofH ){
		double dryproofC; //0.2
		double area_in_cal = 0;
		area_in_cal =  LivingArea ;
		if( ptype == 1 ){
			if( dproofH == 2 ){
				dryproofC = 5.53 * area_in_cal ;
			}else if( dproofH == 4 ){
				dryproofC = 6.96 * area_in_cal ;
			}else {
				dryproofC = 8.38 * area_in_cal ;
			}
		}else if( ptype == 2 && ptype == 3 ){
			if( dproofH == 2 ){
				dryproofC = 6.86 * area_in_cal;
			}else if( dproofH == 4 ){
				dryproofC = 8.73 * area_in_cal;
			}else {
				dryproofC = 10.59 * area_in_cal;
			}
		}else if( ptype == 4 ){
			if( dproofH == 2 ){
				dryproofC = 6.19 * area_in_cal;
			}else if( dproofH == 4 ){
				dryproofC = 7.84 * area_in_cal;
			}else {
				dryproofC = 9.48 * area_in_cal;
			}
		}else {
			if( dproofH == 2 ){
				dryproofC = 6.86 * area_in_cal;
			}else if( dproofH == 4 ){
				dryproofC = 8.73 * area_in_cal;
			}else {
				dryproofC = 10.59 * area_in_cal;
			}
		}
		
		return dryproofC ;
	}
	
	public static double WetProofingCost( int ptype, int Nunit, double LivingArea , double wproofH ){
		double wetproofCost; // 20 percent
		double area_in_cal = LivingArea;
		if( Nunit > 2 ){
			area_in_cal = LivingArea / ( Nunit +1) ;
		}
		if( ptype == 1 ){
			if( wproofH == 2 ){
				wetproofCost = 1.43 * area_in_cal ;
			}else if( wproofH == 4 ){
				wetproofCost = 2.97 * area_in_cal ;
			}else {
				wetproofCost = 5.69 * area_in_cal ;
			}
		}else if( ptype == 2 && ptype == 3 ){
			if( wproofH == 2 ){
				wetproofCost = 2.44 * area_in_cal;
			}else if( wproofH == 4 ){
				wetproofCost = 5.05 * area_in_cal;
			}else {
				wetproofCost = 9.68 * area_in_cal;
			}
		}else if( ptype == 4 ){
			if( wproofH == 2 ){
				wetproofCost = 1.9 * area_in_cal;
			}else if( wproofH == 4 ){
				wetproofCost = 3.93 * area_in_cal;
			}else {
				wetproofCost = 7.54 * area_in_cal;
			}
		}else {
			if( wproofH == 2 ){
				wetproofCost = 2.44 * area_in_cal;
			}else if( wproofH == 4 ){
				wetproofCost = 5.05 * area_in_cal;
			}else {
				wetproofCost = 9.68 * area_in_cal;
			}
		}
		return wetproofCost;
	}
	
	public static double elevateCost2( double eleH, double EleArea, int type_P){
		double HardMeasureCost = 0;
		double Unitrate = 0;
		if( type_P == 1 ){
			if( eleH == 2 ){
				Unitrate = 22.2 ;
			}else if( eleH == 4 ){
				Unitrate = 23.6 ;
			}else if( eleH == 6 ){
				Unitrate = 24.9 ;
			}else{
				Unitrate = 26.1 ;
			}
		}else if( type_P == 2 && type_P == 3 ){
			if( eleH == 2 ){
				Unitrate = 41.4 ;
			}else if( eleH == 4 ){
				Unitrate = 43.9 ;
			}else if( eleH == 6 ){
				Unitrate = 46.0 ;
			}else{
				Unitrate = 48.0 ;
			}
		}else if( type_P == 4 ){
			if( eleH == 2 ){
				Unitrate = 18.0 ;
			}else if( eleH == 4 ){
				Unitrate = 20.0 ;
			}else if( eleH == 6 ){
				Unitrate = 31.0 ;
			}else{
				Unitrate = 33.3 ;
			}
		}else {
			if( eleH == 2 ){
				Unitrate = 41.4 ;
			}else if( eleH == 4 ){
				Unitrate = 43.9 ;
			}else if( eleH == 6 ){
				Unitrate = 46.0 ;
			}else{
				Unitrate = 48.0 ;
			}
		}
		if(eleH == 0){
			HardMeasureCost = 0;
		}else if(eleH <= 2 && eleH >0){
			HardMeasureCost = (Unitrate ) * EleArea  ;
		}else {
			HardMeasureCost = (Unitrate ) * EleArea  ;
		}
		return HardMeasureCost;
	}
	
	
//	public static double elevateCost2( double eleH, double EleArea, int type_P){
//		double HardMeasureCost = 0;
//		double Unitrate = 0;
//		if( type_P == 1 ){
//			Unitrate = 23 ;
//		}else if( type_P == 4 ){
//			Unitrate = 18 ;
//		}else {
//			Unitrate = 41 ;
//		}
//		if(eleH == 0){
//			HardMeasureCost = 0;
//		}else if(eleH <= 2 && eleH >0){
//			HardMeasureCost = (Unitrate )* EleArea  ;
//		}else {
//			HardMeasureCost = (Unitrate  + 1.25*(eleH- 2)) * EleArea  ;
//		}
////		BetaDistribution beta = new BetaDistribution(2, 5);
////		double cdfX = Math.random();
////		double b_prob = beta.inverseCumulativeProbability(cdfX);
//		return HardMeasureCost;
//	}
	
//	public void HouseElevation( int yrs ){
//		double BFE = this.BFE;
//		double erea_cal = 0.0;
//		if( this.NORESUNTS > 2){
//			erea_cal = this.liveArea / ( this.NORESUNTS + 1) ;
//		}else{
//			erea_cal = this.liveArea ;
//		}
//		this.bfeFloodH = BFE - this.elevation + 1;
//
//		this.elevationCost2 = elevateCost2( 2.0, erea_cal, this.Ptype);
//		this.elevationCost4 = elevateCost2( 4.0, erea_cal, this.Ptype);
//		this.elevationCost6 = elevateCost2( 6.0, erea_cal, this.Ptype);
//		this.elevationCost8 = elevateCost2( 8.0, erea_cal, this.Ptype);
//		
//	}
	
	public void houseElevation_advisory( int yrs, int scenario){
		double BFE = this.BFE;
		double erea_cal = 0.0;
		if( this.NORESUNTS > 2){
			erea_cal = this.liveArea / ( this.NORESUNTS + 1) ;
		}else{
			erea_cal = this.liveArea ;
		}
		int eleHeight0 =  (int)( BFE - this.elevation + 1);
		if( ( eleHeight0 > 0 && this.Ptype <= 4) ){
			double costi = 0;
			double MaxCostBenefitH = eleHeight0;
			double minCosts = Double.POSITIVE_INFINITY;
			double propertyHeight ;
			double FloodRiski ;
			if( eleHeight0 < 20){
				for( int inti =  eleHeight0 ; eleHeight0 <= 20; eleHeight0 ++ ){
					costi= elevateCost( eleHeight0, erea_cal, this.Ptype);
					double[] InsuranceList = Insurer.insuranceCostf( this.exposedValue, this.SFHA , 
																	this.elevation, eleHeight0, 
							 										this.BFE, this.PreFRIM, this.Ptype, 
							 										1.1, this.crs, scenario, this.insCancel ); 
					
					
					costi = costi + InsuranceList[5] ;
					double[] height  	= {this.cate1, this.cate2, this.cate3, this.cate4, this.cate5};
					propertyHeight = eleHeight0 + this.elevation ;
					FloodRiski =  FloodRiskF( propertyHeight, height, this.exposedValue, this.Ptype, InsuranceList[2], 1, eleHeight0 );
					if( (FloodRiski + costi) < minCosts ){
						minCosts = (FloodRiski + costi) ;
						MaxCostBenefitH = eleHeight0;
					}	
				}
				this.eleHeight = MaxCostBenefitH;
				this.elecosts = elevateCost( MaxCostBenefitH, erea_cal, this.Ptype);
			}else{
				this.eleHeight = eleHeight0;
				this.elecosts = elevateCost( eleHeight0, erea_cal, this.Ptype);
			}
		}else{
			this.eleHeight = 0;
			this.elecosts = 0.0;
		}
	}
	
	public void TraditionalElevating( int yrs ){
		double BFE = this.BFE;
		double erea_cal = 0.0;
		if( this.NORESUNTS > 2){
			erea_cal = this.liveArea / ( this.NORESUNTS + 1) ;
		}else{
			erea_cal = this.liveArea ;
		}
		this.eleHeight = BFE - this.elevation + 1;
		if(this.eleHeight <= 0 ){this.eleHeight = 0.0;}
//		if(this.eleHeight < 9 && this.eleHeight > 0 ){this.eleHeight = this.eleHeight + 1;}
		if(this.eleHeight > 10){this.eleHeight = 10;}
		
		if( (this.eleHeight > 0 ) ){
			this.elecosts = elevateCost( this.eleHeight, erea_cal, this.Ptype);
			this.elecosts = this.elecosts* Math.pow(1.02, 15);
		}else{
			this.eleHeight = 0;
			this.elecosts = 0.0;
		}
	}
	
//	public void WetProofing( int yrs ){
//		double LivingArea = this.liveArea;
//		int Nunit = this.NORESUNTS;
//		this.bfeFloodH = BFE - this.elevation + 1;
//
//		this.wetproofCost2 = WetProofingCost( this.Ptype, Nunit, LivingArea, 2.0);
//		this.wetproofCost4 = WetProofingCost( this.Ptype, Nunit, LivingArea, 4.0);
//		this.wetproofCost6 = WetProofingCost( this.Ptype, Nunit, LivingArea, 6.0);
//		
//	}
//	
//	public void DryProofing( int yrs ){
//		double LivingArea = this.liveArea;
//		this.bfeFloodH = BFE - this.elevation + 1;
//
//		this.dryproofCost2 = DryProofingCost( this.Ptype, LivingArea, 2.0);
//		this.dryproofCost4 = DryProofingCost( this.Ptype, LivingArea, 4.0);
//		this.dryproofCost6 = DryProofingCost( this.Ptype, LivingArea, 6.0);		
//	}


	
//	public ArrayList<Integer> getNeighborsList( ){
//		ArrayList<Integer> NeighborsID = new ArrayList<Integer>();
//		Iterator<Properties> iter = neighborsList.iterator();
//		while(iter.hasNext()){
//			NeighborsID.add(iter.next().FID);
//		}
//		return NeighborsID ;
//	}
	
	public void displayProperties() {
		System.out.println( "HouseID " 		+ this.FID + 
							"\nbidder "		+ this.Race +
							"\n CEID " 		+ this.CensusID + 
							"\nElevation "  + this.elevation + 
							"\n justvalue " + this.Justvalue + 
							"\n income " 	+ this.Income + 
							"\n zone " 		+ this.SFHA);
	}
	
	public int getIndexOfSmallest( double[] array ){
		if ( array == null || array.length == 0 ) return -1; // null or empty
		int Smallest = 0;
		for ( int i = 1; i < array.length; i++ ){
			if ( array[i] < array[Smallest] ) Smallest = i;
		}
		return Smallest; // position of the first largest found
	}
	
	public int getIndex( double[] array, double TotalDensity){
		if ( array == null || array.length == 0 ) return -1; // null or empty
		double[] Probs = new double[array.length];
		int largest = 0;
		for ( int i = 1; i < array.length; i++ ){
			Probs[i] = array[i] / TotalDensity;
		}
		double rdn = Math.random();
		double x = Probs[0];
		for ( int i = 1; i < array.length; i++ ){
			if( rdn <= x ){
				largest = i;
				break;
			}else{
				x += Probs[i];
			}
		}
		return largest; // position of the first largest found
	}
	public int adaptation_choice( double[] prospectUtilities, int[] AdaptOptions ){
		int		 BestOption = AdaptOptions[0];
		double 	 minUtility = prospectUtilities[0];
		  for(int i = 1; i< prospectUtilities.length; i++){
		    if(prospectUtilities[i] < minUtility){
		    	minUtility = prospectUtilities[i];
		    	BestOption = AdaptOptions[i];
			}
		  }
		return BestOption;
	}
	
	public void RiskEvaluation( double FloodHeight, double mu, double seawallH){
		double[] Surge  	= new double[probs.length];
		for(int i = 0; i < probs.length; i ++) {
			Surge[i] 	= surgeHeightF(probs[i], mu, this.beta0, this.xi0);
			if( Surge[i]	<= seawallH + this.elevation || Surge[i]< 0 ){ Surge[i]  = 0; }
		}
		
		this.Fdamage		= CalculateFloodDamage( FloodHeight, this.FloodAD, this.eleHeight, this.exposedValue, 
													this.insuranceCoverage, 0)	; 	
				
		this.floodDamage0 	= CalculateFloodDamage( FloodHeight, 0, 0, this.exposedValue, 0, 0)	; 
		this.floodDamage1 	= CalculateFloodDamage( FloodHeight, this.FloodAD, this.eleHeight, this.exposedValue, this.insuranceCoverage, this.eleHeight)	; 
		
//		this.Fdamage		= this.floodDamage1 ;
		
		if( this.Fdamage > this.floodDamage0 ){
			this.Fdamage = this.floodDamage0 ;
		}		
		this.FloodRisk0  	=  FloodRiskEstmator( Surge, this.exposedValue, this.Ptype, this.elevation, 0, 0, 0);
		
		
		double TotalDEM 	=  this.elevation;
		if( this.FloodAD == 1){
			TotalDEM = TotalDEM + this.eleHeight ;
		}
		this.FloodRisk1  	=  FloodRiskEstmator( Surge, this.exposedValue, this.Ptype, TotalDEM,
													this.insuranceCoverage, this.FloodAD, this.eleHeight);
		this.MitigatedRisk 	=  FloodRiskEstmator( Surge, this.exposedValue, this.Ptype, TotalDEM,
													0.0, this.FloodAD, this.eleHeight);
	}

	
	public double CalculateFloodDamage( double floodHeight, int measure, double adaptH, double value, double insuranceCoverage, double SWheight){
		double damagei = 0;
//		feet " floodHeight = floodHeight * 0.3048 ";
		
		if( measure == 1 ){	
			floodHeight =  floodHeight - this.eleHeight; 
		}else{
			floodHeight =  floodHeight ;
		}
		
		if( floodHeight < 0) { floodHeight = 0; }
		
		double FHeight ;
		double[] adamagei = {0, 0};
		if( floodHeight > 0){
			FHeight = floodHeight  ;
			if( FHeight <= 0 ){
				damagei = 0;
			}else{
				adamagei = FloodDamageFunc2(FHeight, this.Ptype, measure, adaptH);
				damagei  = value*adamagei[0] - insuranceCoverage ;
				if( damagei <= 0 ){ damagei = 0; }
			}
		}else{
			damagei = 0;
		}
		return damagei;
	}
	
	public void Adaptation2( double mu, int yrs, double seawallH, int scenario){
		//double[] Surges 	= new double[probs.length];
		double[] Surges  	= {this.cate1,  this.cate2, this.cate3, this.cate4, this.cate5};
		for(int i = 0; i < Surges.length; i ++){
			if( Surges[i]  <= seawallH+this.elevation || Surges[i]< 0 ){ Surges[i]  = 0.0; }
		}
		
		if(this.damageCount > 0 || this.requiredIns == 1 || this.lessCover == 1){
			this.InsuranceCoverage1 	= this.InsuranceCoverage2 ;
			this.InsuranceCost1 		= this.InsuranceCost2  ;
			this.InsSubsidy1			= this.InsSubsidy2 ;
			
			this.RiskInsuranceCoverage1 = this.RiskInsuranceCoverage2 ;
			this.RiskInsuranceCost1     = this.RiskInsuranceCost2  ;
			this.RiskInsSubsidy1		= this.RiskInsSubsidy2 ;
			
		}
		
//		if( scenario == 0 || scenario == 2){
//			if( this.Income < 10 && this.insCancel == 0){
//				if( this.InsuranceCost1 > (500*(this.Income +1 )) ){
//					this.insCancel = this.insCancel + 1;
//				}
//				if( this.RiskInsuranceCost1 + this.elePay  > ( 1000*(this.Income +1 ) ) ){
//					this.insCancel = this.insCancel + 1;
//				}
//			}
//		}
//		System.out.println( (10000*(this.Income +1 ))*0.05 + " "+ this.InsuranceCost1 + " "+ this.RiskInsuranceCost1 + " " + this.elePay);
//		System.out.println( "1 " + this.InsuranceCost1 + " 2 " + this.InsuranceCost2 + " 3 " + this.InsuranceCost3 + " "+
//				this.RiskInsuranceCost1 + " "+ this.RiskInsuranceCost2 + " "+ this.RiskInsuranceCost3)  ;
		double bonus1 = 0;
		double bonus2 = 0;
		double subsidies = 0.0;
//		if(scenario != 0 && scenario != 2){
//
//			if( ( this.RiskInsuranceCost1 + this.elePay > ( 500*(this.Income +1 ) ) )  && this.Income < 4  ){
//				bonus1 =  this.RiskInsuranceCost1 + this.elePay - 500*(this.Income +1 );
//				this.RiskInsSubsidy1 = bonus1 ;
//			}
//
//		}
		int label = 0;
		
		if( scenario != 0 && scenario != 2 ){
			if( this.damageCount > 1 && this.eleHeight > 0 ){
				label = 1;
			}
		}
		
//		if(  (this.floodZone  == 1 & this.eleHeight > 4) ){
//			label = 1;
//		}
		
		
		double AdaptDecision1 = 0;
		if(  this.FloodAD == 0 ){
			if( label == 1 ){
				this.elevated 			= 	1;
				this.FloodAD 			=	1;
				this.AdaptationCost  	=   this.elePay ;
				if( this.RiskInsuranceCost1 > 0){
					AdaptDecision1 = 3;
					this.insuranceCost 		=  this.RiskInsuranceCost1;
					this.insuranceCoverage 	=  this.RiskInsuranceCoverage1;
					this.AdaptationCost  	=  this.RiskInsuranceCost1 + this.elePay ;
					this.subsidy 			=  this.RiskInsSubsidy1;
				}else{
					AdaptDecision1 = 2;
					this.insuranceCost 		=  0.0;
					this.insuranceCoverage 	=  0.0;
					this.AdaptationCost  	=  this.elePay ;
					this.subsidy 			=  0.0;
				}
			}else if( this.RiskInsuranceCost1 + this.elePay < this.InsuranceCost1 && this.RiskInsuranceCost1 > 0 && this.eleHeight > 0){
				AdaptDecision1 = 3;
				this.insuranceCost 		=  this.RiskInsuranceCost1;
				this.insuranceCoverage 	=  this.RiskInsuranceCoverage1;
				this.elevated 			= 	1;
				this.FloodAD 			=	1;
				this.AdaptationCost  	= this.RiskInsuranceCost1 + this.elePay ;
				this.subsidy 			= this.RiskInsSubsidy1;
			}else if( this.RiskInsuranceCost1 + this.elePay > this.InsuranceCost1 && this.InsuranceCost1 > 0){
				AdaptDecision1 = 1;
				this.insuranceCost 		=  this.InsuranceCost1;
				this.insuranceCoverage 	=  this.InsuranceCoverage1;
				this.elevated 			= 	0;
				this.FloodAD 			= 	0;
				this.AdaptationCost  	=  this.InsuranceCost1;
				this.subsidy 			=  this.InsSubsidy1	;
			}

		}else{
			if( this.RiskInsuranceCost1 > 0){
				AdaptDecision1 = 3;
				this.insuranceCost 		=  this.RiskInsuranceCost1;
				this.insuranceCoverage 	=  this.RiskInsuranceCoverage1;
				this.AdaptationCost  	=  this.insuranceCost + this.elePay ;
				this.subsidy 			=  this.RiskInsSubsidy1;
			}else{
				AdaptDecision1 = 2;
				this.insuranceCost 		=  0.0;
				this.insuranceCoverage 	=  0.0;
				this.AdaptationCost  	=  this.elePay ;
				this.subsidy 			=  0.0;
			}
		}
		
		if(  this.insuranceCost <= 0 ){
			this.subsidy = 0.0;
		}
		
		if( this.insuranceCoverage == 250000){
			this.insuranceLevel  = 2;
		}else if( this.insuranceCoverage > 0 ){
			this.insuranceLevel  = 1;
		}else{
			this.insuranceLevel  = 0;
		}
		
		if( this.damageCount > 2 && scenario != 0 && scenario != 2){
			this.insuranceCost 		=  0.0;
			this.insuranceCoverage 	=  0.0;
			this.subsidy 			=  0.0;
			
			if( AdaptDecision1 == 3){
				this.AdaptationCost  =  this.elePay ;
			}else if( AdaptDecision1 == 1 ){
				this.AdaptationCost  =  0.0 ;
			}
			
		}	
	}
	
	
	
	public void Adaptation3( double mu, int yrs, double seawallH, int scenario){
		//double[] Surges 	= new double[probs.length];
		double[] Surges  	= {this.cate1,  this.cate2, this.cate3, this.cate4, this.cate5};
		for(int i = 0; i < Surges.length; i ++){
			if( Surges[i]  <= seawallH+this.elevation || Surges[i]< 0 ){ Surges[i]  = 0.0; }
		}
		
		if( this.examined == 0 ){
			
			double cover0 = 60000;
			double utilityi = Double.POSITIVE_INFINITY ;
			
			double risk0 = QuantifyRisk2( Surges, 0, 0, 0, 0, 0);
			double risk1 = 0.0;
			double risk2 = 0.0;
			for(int i = 0; i < 11; i++){
				double[] InsuranceList0 = Insurer.insuranceCostf3( this.exposedValue, cover0, this.SFHA , this.elevation, 0, 
																this.BFE, this.PreFRIM, this.Ptype, 1, this.crs ,scenario, this.insCancel);
				
				double[] InsuranceList1 = Insurer.insuranceCostf3( this.exposedValue, cover0, this.SFHA , this.elevation, this.eleHeight, 
																this.BFE, this.PreFRIM, this.Ptype, 1, this.crs ,scenario, this.insCancel);
				
				risk1 = QuantifyRisk2( Surges, cover0, 	InsuranceList0[0], 0, 0, 0);
				risk2 = QuantifyRisk2( Surges, cover0, 	InsuranceList1[0], this.elePay, 1, InsuranceList1[2]);
				
				double[] prospects_ins = {  risk0, risk1, risk2};
				int[] choices = {0 ,1 , 2};
				int AdaptDecision1 	= adaptation_choice( prospects_ins, choices );
				
				if( AdaptDecision1 > 0){
					if( prospects_ins[AdaptDecision1] < utilityi){
						utilityi = prospects_ins[AdaptDecision1];
						if( AdaptDecision1 == 1){
							this.OptimizedInsuranceCost = InsuranceList0[0];
							this.OptimizedCoverage 		= InsuranceList0[1];
							this.OptimalElevated 		= 0;
						}else{
							this.OptimizedInsuranceCost = InsuranceList1[0];
							this.OptimizedCoverage 		= InsuranceList1[1];
							this.OptimalElevated 		= 1;
						}
					}
					
				}
				cover0 = cover0 + 20000;
				if( cover0 > 250000 ){ cover0 = 250000; }
			}
			this.examined = 1;
		}
		
		
		if(  this.damageCount <= 2 ){
			this.insuranceCost 		= this.OptimizedInsuranceCost;
			this.insuranceCoverage 	= this.OptimizedCoverage;
			this.elevated 			= this.OptimalElevated;
			if( this.elevated == 1 ){
				this.FloodAD 	= 1;
			}else{
				this.FloodAD 	= 0;
			}
			if(this.elevated  == 1){
				this.AdaptationCost  = this.elePay + this.insuranceCost  ;
			}else{
				this.AdaptationCost  = this.insuranceCost  ;
			}
			
			this.subsidy = 0.0;
		}else{
			this.insuranceCost 		= 0.0;
			this.insuranceCoverage 	= 0.0;
			this.elevated 			= this.OptimalElevated;
			if( this.elevated == 1 ){
				this.FloodAD 	= 1;
			}else{
				this.FloodAD 	= 0;
			}
			if(this.elevated  == 1){
				this.AdaptationCost  = this.elePay   ;
			}else{
				this.AdaptationCost  = 0.0  ;
			}
			this.subsidy = 0.0;
		}
		
	}
	
	public void Adaptation( double mu, int yrs, double seawallH, int scenario){
		//double[] Surges 	= new double[probs.length];
		double[] Surges  	= {this.cate1,  this.cate2, this.cate3, this.cate4, this.cate5};
		for(int i = 0; i < Surges.length; i ++){
			if( Surges[i]  <= seawallH+this.elevation || Surges[i]< 0 ){ Surges[i]  = 0.0; }
		}
		
		double[] InsuranceList0 = {this.InsuranceCoverage1, this.InsuranceCoverage2, this.InsuranceCoverage3, 
									this.InsuranceCost1, this.InsuranceCost2, this.InsuranceCost3,
									this.InsSubsidy1, this.InsSubsidy2, this.InsSubsidy3} ;
		double[] InsuranceList1 = {this.RiskInsuranceCoverage1, this.RiskInsuranceCoverage2, this.RiskInsuranceCoverage3,
									this.RiskInsuranceCost1, this.RiskInsuranceCost2, this.RiskInsuranceCost3,
									this.RiskInsSubsidy1, this.RiskInsSubsidy2, this.RiskInsSubsidy3} ;
		
		
		if( scenario == 0 || scenario == 2){
			if( this.Income == 1 ){
				if( this.InsuranceCost1 > 2000  ){
					InsuranceList0[0] = 0;
					InsuranceList0[3] = Double.POSITIVE_INFINITY;
					InsuranceList0[6] = 0;
				}else if( this.InsuranceCost2 > 2000 ){
					InsuranceList0[1] = 0;
					InsuranceList0[4] = Double.POSITIVE_INFINITY;
					InsuranceList0[7] = 0;
				}else if( this.InsuranceCost3 > 2000 ){
					InsuranceList0[2] = 0;
					InsuranceList0[5] = Double.POSITIVE_INFINITY;
					InsuranceList0[8] = 0;
				}
				if( this.RiskInsuranceCost1 + this.elePay  > 2000  ){
					InsuranceList1[0] = 0;
					InsuranceList1[3] = Double.POSITIVE_INFINITY;
					InsuranceList1[6] = 0;
				}else if( this.RiskInsuranceCost2+ this.elePay  > 2000 ){
					InsuranceList1[1] = 0;
					InsuranceList1[4] = Double.POSITIVE_INFINITY;
					InsuranceList1[7] = 0;
				}else if( this.RiskInsuranceCost3+ this.elePay  > 2000 ){
					InsuranceList1[2] = 0;
					InsuranceList1[5] = Double.POSITIVE_INFINITY;
					InsuranceList1[8] = 0;
				}
				
			}else if( this.Income == 2 ){
				if( this.InsuranceCost1 > 3500  ){
					InsuranceList0[0] = 0;
					InsuranceList0[3] = Double.POSITIVE_INFINITY;
					InsuranceList0[6] = 0;
				}else if( this.InsuranceCost2 > 3500 ){
					InsuranceList0[1] = 0;
					InsuranceList0[4] = Double.POSITIVE_INFINITY;
					InsuranceList0[7] = 0;
				}else if( this.InsuranceCost3 > 3500 ){
					InsuranceList0[2] = 0;
					InsuranceList0[5] = Double.POSITIVE_INFINITY;
					InsuranceList0[8] = 0;
				}
				if( this.RiskInsuranceCost1 + this.elePay  > 3500  ){
					InsuranceList1[0] = 0;
					InsuranceList1[3] = Double.POSITIVE_INFINITY;
					InsuranceList1[6] = 0;
				}else if( this.RiskInsuranceCost2+ this.elePay  > 3500 ){
					InsuranceList1[1] = 0;
					InsuranceList1[4] = Double.POSITIVE_INFINITY;
					InsuranceList1[7] = 0;
				}else if( this.RiskInsuranceCost3+ this.elePay  > 3500 ){
					InsuranceList1[2] = 0;
					InsuranceList1[5] = Double.POSITIVE_INFINITY;
					InsuranceList1[8] = 0;
				}
			}
		}
		
//		System.out.println( "1 " + this.InsuranceCost1 + " 2 " + this.InsuranceCost2 + " 3 " + this.InsuranceCost3 + " "+
//				this.RiskInsuranceCost1 + " "+ this.RiskInsuranceCost2 + " "+ this.RiskInsuranceCost3)  ;
		
		double bonus1 = 0, bonus2 = 0, bonus3 = 0;
		double[] subsidies = { 0.0, 0.0, 0.0, 0.0, bonus1, bonus2, bonus3 };
		if(scenario == 1 || scenario == 3){
			if(this.FloodAD <= 1){
				if( (InsuranceList1[3] + this.elePay > ( 2000) )  && this.Income <= 2  ){
					bonus1 =  InsuranceList1[3] + this.elePay - 2000;
					subsidies[4] = bonus1 ;
				}
				if( (InsuranceList1[4] + this.elePay > ( 2000) ) && this.Income <= 2    ){
					bonus2 =  InsuranceList1[4] + this.elePay - 2000;
					subsidies[5] = bonus2 ;
				}
				if( (InsuranceList1[5] + this.elePay > ( 2000) )  && this.Income <= 2   ){
				bonus3 =  InsuranceList1[5] + this.elePay - 2000;
				subsidies[6] = bonus3 ;
				}
			}else{
				subsidies[4] = 0.0;
				subsidies[5] = 0.0;
				subsidies[6] = 0.0;
			}
		}else{
			subsidies[1] = InsuranceList0[6];
			subsidies[2] = InsuranceList0[7];
			subsidies[3] = InsuranceList0[8];
			subsidies[4] = InsuranceList1[6];
			subsidies[5] = InsuranceList1[7];
			subsidies[6] = InsuranceList1[8];
		}
		double[] prospects_ins = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		int[] choices  = { 0, 1, 2, 3, 4, 5, 6 };
		
		int AdaptDecision1 ;
		
		double[] insurancecosts = { 0.0, InsuranceList0[3], InsuranceList0[4], InsuranceList0[5],
									InsuranceList1[3], InsuranceList1[4], InsuranceList1[5]		};
		double[] coverages 		= { 0.0, InsuranceList0[0], InsuranceList0[1], InsuranceList0[2],
									InsuranceList1[0], InsuranceList1[1], InsuranceList1[2] };
		int[] 	measures 		= { 0, 0, 0, 0, 1, 1, 1 };
		double[] measurePay 	= { 0.0, 0.0, 0.0, 0.0, 
									this.elePay, this.elePay, this.elePay };
		int[] Ifelevated  		= { 0, 0, 0, 0, 1, 1, 1 };
		int[] InsuranceLevels 	= { 0, 1, 2, 3, 1, 2, 3 };
		
		if( this.FloodAD == 0 ){
			this.ElevationYrs = 0;
			prospects_ins[0]  	=  	 QuantifySubjectiveRisk(Surges, 0, 0, 0, 0, 0);
			prospects_ins[1]  	=    QuantifySubjectiveRisk(Surges, InsuranceList0[0], InsuranceList0[3], 0, 0, 0);
			prospects_ins[2]  	=    QuantifySubjectiveRisk(Surges, InsuranceList0[1], InsuranceList0[4], 0, 0, 0);
			prospects_ins[3]  	=    QuantifySubjectiveRisk(Surges, InsuranceList0[2], InsuranceList0[5], 0, 0, 0);
			prospects_ins[4]  	=    QuantifySubjectiveRisk(Surges, InsuranceList1[0], InsuranceList1[3], 
															InsuranceList1[3] + this.elePay, 1, bonus1);
			prospects_ins[5]  	=    QuantifySubjectiveRisk(Surges, InsuranceList1[1], InsuranceList1[4], 
															InsuranceList1[4] + this.elePay, 1, bonus2);
			prospects_ins[6]  	=    QuantifySubjectiveRisk(Surges, InsuranceList1[2], InsuranceList1[5], 
															InsuranceList1[5] + this.elePay, 1, bonus3);
		}else if( this.FloodAD == 1 ){
			prospects_ins[0]  	=  	 QuantifySubjectiveRisk(Surges, 0, 0, this.elePay, 1, 0);
			prospects_ins[1]  	=    Double.POSITIVE_INFINITY;
			prospects_ins[2]  	=    Double.POSITIVE_INFINITY;
			prospects_ins[3]  	=    Double.POSITIVE_INFINITY;
			prospects_ins[4]  	=    QuantifySubjectiveRisk(Surges, InsuranceList1[0], InsuranceList1[3], 
															InsuranceList1[3] + this.elePay, 1, bonus1);
			prospects_ins[5]  	=    QuantifySubjectiveRisk(Surges, InsuranceList1[1], InsuranceList1[4], 
															InsuranceList1[4] + this.elePay, 1, bonus2);
			prospects_ins[6]  	=    QuantifySubjectiveRisk(Surges, InsuranceList1[2], InsuranceList1[5], 
															InsuranceList1[5] + this.elePay, 1, bonus3);
			measurePay[0] 		= 	this.elePay;
			measurePay[1] 		= 	this.elePay; 
			measurePay[2] 		= 	this.elePay;
			measurePay[3] 		= 	this.elePay;
		}

//		if( scenario == 1|| scenario == 3){
//			if( this.damageCount >= 1){
//				prospects_ins[0]   =   Double.POSITIVE_INFINITY;
//				prospects_ins[1]   =   Double.POSITIVE_INFINITY;
//				prospects_ins[2]   =   Double.POSITIVE_INFINITY;
//				prospects_ins[3]   =   Double.POSITIVE_INFINITY;
//			}
//		}
		
		
		
//		if( this.requiredIns == 1 ){
//			prospects_ins[0]   =   Double.POSITIVE_INFINITY;
//			
//			prospects_ins[1]   =   Double.POSITIVE_INFINITY;
//			prospects_ins[2]   =   Double.POSITIVE_INFINITY;
//			
//			prospects_ins[4]   =   Double.POSITIVE_INFINITY;
//			prospects_ins[5]   =   Double.POSITIVE_INFINITY;
//			
//		}
		
		
		
		
//		if( this.lessCover == 1){
//			prospects_ins[0]   =   Double.POSITIVE_INFINITY;
//			if(this.insuranceLevel == 1){
//				prospects_ins[1] = Double.POSITIVE_INFINITY;
//				prospects_ins[4] = Double.POSITIVE_INFINITY;
//			}else if( this.insuranceLevel == 2 ){
//				prospects_ins[1] = Double.POSITIVE_INFINITY;
//				prospects_ins[2] = Double.POSITIVE_INFINITY;
//				prospects_ins[4] = Double.POSITIVE_INFINITY;
//				prospects_ins[5] = Double.POSITIVE_INFINITY;
//			}
//			this.lessCover = 2;
//		}
		
		if(this.damageCount > 0){
			prospects_ins[0]  =   Double.POSITIVE_INFINITY;
			prospects_ins[1]  =   Double.POSITIVE_INFINITY;
			prospects_ins[2]  =   Double.POSITIVE_INFINITY;
			prospects_ins[3]  =   Double.POSITIVE_INFINITY;
		}
		
		if(this.damageCount > 1){
			prospects_ins[1]  =   Double.POSITIVE_INFINITY;
			prospects_ins[2]  =   Double.POSITIVE_INFINITY;
			prospects_ins[3]  =   Double.POSITIVE_INFINITY;
			prospects_ins[4]  =   Double.POSITIVE_INFINITY;
			prospects_ins[5]  =   Double.POSITIVE_INFINITY;
			prospects_ins[6]  =   Double.POSITIVE_INFINITY;
		}
		
//		System.out.println( Arrays.toString(prospects_ins) )  ;
		
		AdaptDecision1 	= adaptation_choice( prospects_ins, choices );
		
		if(this.insuranceCost > 0 && AdaptDecision1 == 0){
			this.insCancel = this.insCancel+1;
		}
		this.insuranceCost 		= insurancecosts[ AdaptDecision1 ];
		this.insuranceCoverage 	= coverages[ AdaptDecision1 ];
		this.elevated 			= Ifelevated[ AdaptDecision1 ];
		if( this.FloodAD == 0 ){
			this.FloodAD 	= measures[ AdaptDecision1 ];
		}
		
		this.AdaptationCost  = measurePay[ AdaptDecision1 ] + this.insuranceCost ;
		this.insuranceLevel  = InsuranceLevels[ AdaptDecision1 ] ;
		this.subsidy = subsidies[AdaptDecision1];
	}
	
	
	public double QuantifyRisk2( double[] Surges, double insCover, 
			  double insCost, double adaptCost, int floodAD, double bonus){
		double new_height;
		if( floodAD == 1){
		new_height = this.eleHeight + this.elevation;
		}else{
		new_height = this.elevation;
		}

		double TotAdaptCost 		= insCost + adaptCost - bonus;
		double justvalue = this.exposedValue ;
		double[] riski 		= {0, 0};
		
		double floodH  	;
		double[] risk_k  	= new double[ Surges.length ];
		Arrays.fill(risk_k, 0.0);
		double Nostorm = TotAdaptCost; // 
		double loss ;
		for( int i = 0; i < Surges.length; i ++){
			floodH = Surges[i] - new_height;
			if( floodH <= 0 ){
				risk_k[i] = TotAdaptCost;
			}else{
				riski = FloodDamageFunc2(floodH, this.Ptype, floodAD, this.eleHeight);
				loss = justvalue*riski[0];
				if( loss < insCover ){
					if(loss <= 500 ){
						risk_k[i] = loss;
					}else if(loss > 500 && loss < insCover){
						risk_k[i] = 500;
					}else{
						risk_k[i] = 500 + loss - insCover ;
					}
				}else{
					risk_k[i] = loss;
				}
				risk_k[i] = risk_k[i] +TotAdaptCost ;
			}
			risk_k[i] = risk_k[i] ;
		}
		double floodrisk =  (  (Nostorm )  	*updated_p0[0] + risk_k[0] *updated_p0[1] 	
								+ risk_k[1] *updated_p0[2] + risk_k[2] *updated_p0[3] 
								+ risk_k[3] *updated_p0[4] + risk_k[4] *updated_p0[5]	);
//		System.out.println( floodrisk )  ; 
		
		return floodrisk;
	}
	
	public double QuantifySubjectiveRisk( double[] Surges, double insCover, 
			  double insCost, double adaptCost, int floodAD, double bonus){
		double new_height;
		if( floodAD == 1){
		new_height = this.eleHeight + this.elevation;
		}else{
		new_height = this.elevation;
		}
		double[] costs = { insCost, insCover, adaptCost, bonus };
		
		
		
		double floodrisk =  ProspectTheory( new_height, Surges, this.SVIndex, this.RiskPIndex, 
											costs, floodAD);
		
//		System.out.println( floodrisk )  ; 
		
		return floodrisk;
	}
	
	
	
//	public static double[] Prospect( double[] p_ie_list){
//		double RP_i;
//		double sigma = 0.69; /** 0.69 */
//		double[] pie_new = new double[probs.length] ;
//		RP_i = 1;
//		double p_RT = 0, p_RT2 = 0;
//		for(int j = 0; j < p_ie_list.length; j++){
//			p_RT = Math.pow( p_ie_list[j], sigma);
//			p_RT2 = Math.pow( 1 - p_ie_list[j], sigma);
//			pie_new[j] = p_RT / Math.pow( (p_RT + p_RT2), 1 /sigma );
//		}
//		return pie_new;
//	}
	
	public static double[] BayesProspect( double[] p_ie_list, double capacity ){
		double sigma = 0.69; /** 0.79 */
		double[] pie_new  	= new double[ probs.length +1];		
		double p_RT = 0, p_RT2 = 0;
		//***************************************************************//
		double RP_i = capacity;	
//		if(soci == 1){
//			RP_i = 0.45;
//		}else if(soci == 2){
//			RP_i = 0.5;
//		}else if(soci == 3){
//			RP_i = 0.6;
//		}else{
//			RP_i = 0.7;
//		}
		
		//********************************************************///
		double prob0 = 1 - Arrays.stream( p_ie_list ).sum();
//		p_RT = Math.pow( Math.pow( 10 , 2*RP_i - 1)*prob0, sigma);
//		p_RT2 = Math.pow( 1 - Math.pow( 10 , 2*RP_i - 1)*prob0, sigma);
//		p_RT = Math.pow( prob0, sigma);
//		p_RT2 = Math.pow( 1 - prob0, sigma);
		pie_new[0] = prob0;
		for(int j = 0; j < p_ie_list.length; j++){
			p_RT 			= Math.pow( Math.pow( 10 , 2*RP_i - 1)*p_ie_list[j], sigma);
			p_RT2 			= Math.pow( 1 - Math.pow( 10 , 2*RP_i - 1)*p_ie_list[j], sigma );
			pie_new[j+1] 	= p_RT / Math.pow( (p_RT + p_RT2), 1 /sigma );
//			pie_new[j] = p_RT / Math.pow( (p_RT + p_RT2), 1 /sigma );
		}
//		System.out.println( Arrays.toString(pie_new) );
		return pie_new;
	}
	
	
	public double ProspectTheory( double new_height, double[] floodheight_2, int soci, int perception, double[] AdaptCosts, int floodAD) {
		
		double alpha_utility = this.prospectAlpha;
		
		double insurCosts  			= AdaptCosts[0];
		double insurCover  			= AdaptCosts[1];
		double Hard_adaptiveCost	= AdaptCosts[2];
		double bonus    			= AdaptCosts[3];
		
		double TotAdaptCost 		= insurCosts + Hard_adaptiveCost - bonus;
		
		
		double justvalue = this.exposedValue ;
		double TotalPerceptRisk = 1;
		double[] riski 		= {0, 0};
		
		double floodH  	;
		double[] risk_k  	= new double[ floodheight_2.length ];
		Arrays.fill(risk_k, 0.0);
		double Nostorm = Math.pow(TotAdaptCost, alpha_utility); // 
		double loss ;
		for( int i = 0; i < floodheight_2.length; i ++){
			floodH = floodheight_2[i] - new_height;
			if( floodH <= 0 ){
				risk_k[i] = TotAdaptCost;
			}else{
				riski = FloodDamageFunc2(floodH, this.Ptype, floodAD, this.eleHeight);
				loss = justvalue*riski[0];
				if( loss < insurCover ){
					if(loss <= 500 ){
						risk_k[i] = loss;
					}else if(loss > 500 && loss < insurCover){
						risk_k[i] = 500;
					}else{
						risk_k[i] = 500 + loss - insurCover ;
					}
				}else{
					risk_k[i] = loss;
				}
				risk_k[i] = risk_k[i] +TotAdaptCost ;
			}
			risk_k[i] = Math.pow(risk_k[i] , alpha_utility);
		}
		
//		System.out.println(  Arrays.toString(risk_k)  );
		
		
		TotalPerceptRisk =  (  (Nostorm )  	*updated_p[0] + risk_k[0] *probs[1] 	
								+ risk_k[1] *probs[2] + risk_k[2] *probs[3] 
								+ risk_k[3] *probs[4] + risk_k[4] *probs[5]	);
//		System.out.println("Result " + TotalPerceptRisk);
//		TotalRisk =  ( 	(Nostorm + risk_k[0] )  *Math.abs(updated_p[0] - updated_p[1]) 
//		+ (risk_k[0] + risk_k[1]) *Math.abs(updated_p[1] - updated_p[2]) 
//		+ (risk_k[1] + risk_k[2]) *Math.abs(updated_p[2] - updated_p[3]) 
//		+ (risk_k[2] + risk_k[3]) *Math.abs(updated_p[3] - updated_p[4]) 
//		+ (risk_k[3] + risk_k[4]) *Math.abs(updated_p[4] - updated_p[5]) 
//		+ (risk_k[4] + risk_k[5]) *Math.abs(updated_p[5] - updated_p[6])
//		+ (risk_k[5] + risk_k[6]) *Math.abs(updated_p[6] - updated_p[7])
//		+ (risk_k[6] + risk_k[7]) *Math.abs(updated_p[7] - updated_p[8])
//		+ (risk_k[7] + risk_k[8]) *Math.abs(updated_p[8] - updated_p[9])) / 2;
		return TotalPerceptRisk;
	}
	
	
	
	
	
//	public double StormFloodDamage( double eleHeight, double insurance, double insuranceCoverage, double surgeH){	
////		double Surge = surgeHeightF(probs[category - 1], mu) ;
//		double riskValue = this.exposedValue;
//		if(riskValue < 0){ riskValue = 0;}
//		double new_house_height = eleHeight + this.elevation;
//		double damage;
//
//		double height = surgeH ;
//		
//		double inundation, int ptype, int adapti, double adaptH
//		
//		damage = riskValue*FloodDamageFunc2(height)  - insuranceCoverage;
//		if(damage <0){damage = 0;}
//		return damage;
//	}
	
//	public double FloodRisk_yri( double eleHeight, double insuranceCoverage){
//		double mu = this.mu0 + this.SLRheight;
//		double[] Surge  = {0.00, 0.00, 0.00, 0.00, 0.00};
//		for(int i = 0; i < probs.length; i ++){ Surge[i] = surgeHeightF(probs[i], mu, this.beta0, this.xi0) ;	}
//		double[] risk_k2 = new double[ probs.length ];
//		double new_house_height = eleHeight + this.elevation;
//		double[] height  = new double[ probs.length ];
//		double damage;
//		double riskValue = this.exposedValue;
//		if(riskValue < 0){ riskValue = 0;}
//		for( int i = 0; i < Surge.length; i ++ ){height[i] = Surge[i] - new_house_height;
//			if( height[i] <= 0 ){
//				height[i] = 0;
//				risk_k2[i] = 1;
//			}else{	
//				damage = riskValue*FloodDamageFunc(height[i])  - insuranceCoverage;
//				if(damage <0){damage = 0;
//			}
//			risk_k2[i] = damage;
///**						riskValue*FloodDamageFunc(height[i]) - insuranceCoverage;  ** with insuranceCoverage */	}
//		}
////		double TotalRisk1	=	( (risk_k2[0] ) *(probs[0] ) 
////								+ (risk_k2[1] ) *(probs[1] ) 
////								+ (risk_k2[2] ) *(probs[2] ) 
////								+ (risk_k2[3] ) *(probs[3] )
////								+ (risk_k2[4] ) *(probs[4] ) ) ;
//		
//		
//		double TotalRisk1	=	( 	( risk_k2[0] + risk_k2[1] ) *(probs[0] - probs[1] ) 
//									+ ( risk_k2[2] + risk_k2[1] ) *(probs[1] - probs[2] ) 
//									+ ( risk_k2[3] + risk_k2[2] ) *(probs[2] - probs[3] )
//									+ ( risk_k2[4] + risk_k2[3] ) *(probs[3] - probs[4] ) ) / 2;
//		
//		return TotalRisk1;
//	}
	

}
