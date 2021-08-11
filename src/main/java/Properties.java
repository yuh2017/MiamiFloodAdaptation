
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Properties {
	public static double[] probs  = {0.1, 0.04, 0.02, 0.01, 0.005};	
	 
	public double xi0, xise0, mu0, muse0, beta0, betase0; // unit feet
//	public double xi, mu, beta;
	
	
	public int    Ptype, FloodMeasure, WindMeasure ;
	public int    FID, ParcelID, EFFYER , bidderage, CateLabel;
	public int    ZipCode, floodZone;
	public double Justvalue0, Justvalue , LV ;
	public int    NOBULDNG , NORESUNTS , biderid, bidrace, bidVehicle;
	public double bidPrice , bidIncome;
	public int    adaptYear;
	public double windRisk, windRisk0; 
	public double TotFloodDamage1, TotFloodDamage, TotFloodDamageYrs, TotalAdaptCost, TotalAdaptPay;
	public double cate1 , cate2 , cate3, cate4, cate5;
	
	public double cate01 , cate02 , cate03, cate04, cate05;
	
	public double latitude, longitude, Area;
	public double liveArea, dem, elevation, slrHeight;
	
//	public double PropertyRisk, totaRisk;
	
	
	public double FloodAdaptPay, eleHeight, bfeH, adaptH;
	
	public double wetproofCost2, dryproofCost2, elevationCost2;
	public double wetproofCost4, dryproofCost4, elevationCost4;
	public double wetproofCost6, dryproofCost6, elevationCost6, elevationCost8;
	
	public double wetproofPay, dryproofPay, elevationPay, relocatePay;
	public double FloodRisk, FloodRisk0;
	public double floodDamage, floodDamage0;
	public double ShutterPay;
	public double a0, a1, a2, a3, a4, a5;
	public double PremiumCost;
	public String zone, Desc, EZONE, floodplain;
	public double AdaptationCost, CBratio, IP;
	public double fbeta1, fmu1, fxi1 ;
	public double unInsuredCost;
	public double CurrentJV, initialHeight, BFE, BFE0, GovSeallH;
	
	public double evacuationP, payratio;
	public double[] Surge  = {0.00, 0.00, 0.00, 0.00, 0.0};
	public int 		eleYear, sellingYer;
	
	ArrayList<Properties> neighborsList = new ArrayList<Properties>();
	public static void printArray(String[] rows) {
	      for (int i = 0; i < rows.length; i++) {
	         System.out.println(i + " , " +rows[i]);
	         System.out.print('\n');
	      }
	      System.out.println("\nlength of the array is "+ rows.length);
	      }
	
	public Properties() {
		//System.out.println("create empty house property");
		
		
		this.xi0 			= 0.0688 ; 
		this.xise0 			= 0.07 ;
		this.mu0 			= 0.9   ; 
		this.muse0 			= 0.146    ;
		this.beta0 			= 0.600 ; 
		this.betase0 		= 0.125 ;

	}
	
	public Properties(String[] line) {
		this.ParcelID   	= (int)Double.parseDouble(line[3] );
		this.ZipCode    	= (int) Float.parseFloat(line[14]);
		this.Desc       	= line[12];
		this.floodplain     = line[5];
		this.LV     		= Double.parseDouble(line[9] );
		this.Justvalue0  	= Double.parseDouble(line[7] );
		this.bidIncome     		= Double.parseDouble(line[6] );
		this.Justvalue	 	= this.Justvalue0;
		this.elevation 		=  (int) Float.parseFloat(line[1]);
		
		this.zone        	= line[4] ;
		this.Ptype       	= (int) Float.parseFloat(line[0]);
		this.CateLabel		= (int) Float.parseFloat(line[22]);
		
		
		
		
		
		this.xi0 			= 0.140 ; 
		this.xise0 			= 0.181 ;
		this.mu0 			= 0.9672   ; 
		this.muse0 			= 0.176    ;
		this.beta0 			= 0.600 ; 
		this.betase0 		= 0.125 ;
		
		
		
		
		this.cate01 = Double.parseDouble( line[15]);
		this.cate02 = Double.parseDouble( line[16]);
		this.cate03 = Double.parseDouble( line[17]);
		this.cate04 = Double.parseDouble( line[18]);
		this.cate05 = Double.parseDouble( line[19]);
		
		this.cate1 = (mu0 + ( (Math.pow( 0.1  ,  -1*xi0) - 1)*beta0 / xi0 ) ) / 0.3048 ;
		this.cate2 = (mu0 + ( (Math.pow( 0.04 ,  -1*xi0) - 1)*beta0 / xi0 ) ) / 0.3048 ;
		this.cate3 = (mu0 + ( (Math.pow( 0.02 ,  -1*xi0) - 1)*beta0 / xi0 ) ) / 0.3048 ;
		this.cate4 = (mu0 + ( (Math.pow( 0.01 ,  -1*xi0) - 1)*beta0 / xi0 ) ) / 0.3048 ; 
		this.cate5 = (mu0 + ( (Math.pow( 0.005,  -1*xi0) - 1)*beta0 / xi0 ) ) / 0.3048 ;
		
		
		
		if( this.cate1 > this.elevation && cate01 < 0){
			this.elevation   = this.elevation + this.cate1 - this.elevation ;
		}
		
		if( this.cate2 > this.elevation && cate02 < 0){
			this.elevation   = this.elevation + this.cate2 - this.elevation ;
		}
		
		if( this.cate3 > this.elevation && cate03 < 0){
			this.elevation   = this.elevation + this.cate3 - this.elevation ;
		}
		
		
		if( this.cate4 > this.elevation && cate04 < 0){
			this.elevation   = this.elevation + this.cate4 - this.elevation ;
		}

		if( this.cate5 > this.elevation && cate05 < 0){
			this.elevation   = this.elevation + this.cate5 - this.elevation ;
		}
		
		this.initialHeight 	= mu0 ;
//		this.elevation0  	= this.elevation;
		this.BFE0  			= this.cate4 ;
		
//		Double.parseDouble( line[17] );	;		
		this.Area   	= Double.parseDouble( line[13] );
		
		this.NOBULDNG   = (int) Float.parseFloat( line[10] );
		this.NORESUNTS  = (int) Float.parseFloat( line[11] );
		
		this.EFFYER     = (int)Double.parseDouble( line[2] );
		
		this.latitude  = Double.parseDouble( line[20] );
		this.longitude = Double.parseDouble( line[21] );	
		double FAR0 = 0.3;
		if(  this.NORESUNTS == 1 && (this.Ptype == 1 || this.Ptype == 4)){
			this.liveArea = this.Area ;
		}else if(  this.Ptype <= 4 && this.NORESUNTS > 1 ){
			this.liveArea = ( Math.random() *0.1+ FAR0 )* this.Area / Math.sqrt(this.NORESUNTS);
		}else if( this.Ptype > 4 && this.NORESUNTS > 1 ){
			this.liveArea = (  Math.random() *0.05+ FAR0 )*this.Area / Math.sqrt(this.NORESUNTS);
		}else if( this.Ptype != 1 && this.Ptype != 4 ){
			this.liveArea = ( FAR0 )*this.Area ;
			this.Justvalue = this.Justvalue0 ;
			if( (this.Justvalue - this.LV) / 100 < this.liveArea){
				this.liveArea = this.Justvalue0 / 100;
			}
		}else{
			this.liveArea = this.Area ;
		}
		
		if( this.Ptype == 1 ) {
			this.payratio = 1.760e-02 - 4.822e-03 + 2.685e-06*this.Area + -5.521e-08 * this.bidIncome ;
		}else {
			this.payratio = 1.760e-02 + 2.685e-06*this.Area + -5.521e-08 * this.bidIncome ;
		}
		
		this.BFE 	= this.BFE0;
		this.fbeta1	= beta0;
		this.fmu1 	= mu0;
		this.fxi1 	= xi0;
		
        int Azone = this.zone.indexOf('A');
		int Vzone = this.zone.indexOf('V');
		
		if( Azone >= 0 || Vzone >= 0) {
			this.floodZone = 1;
		} else {
			this.floodZone = 0;
		}
		
		
		this.TotFloodDamage   = 0.0;
		this.TotFloodDamage1  = 0.0;
		this.TotalAdaptCost   = 0.0;
		this.TotalAdaptPay    = 0.0;
	} 
	
	
	
	
	public static HashMap<Integer, ArrayList<Double> > readAlphaParas(String csvFile)  {
		HashMap<Integer, ArrayList<Double> > ParaMap = new HashMap<Integer, ArrayList<Double> >(); 
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		try {
			br = new BufferedReader(new FileReader(csvFile));
			br.readLine();
			while ((line = br.readLine()) != null) {
				int ZipCode ; 
				String[] Dline 	= line.split(cvsSplitBy);
				ArrayList<Double> PropertyParas = new ArrayList<Double>();
//				printArray(Dline);
				ZipCode 		=  (int) Float.parseFloat(Dline[0] );
				PropertyParas.add( Double.parseDouble(Dline[1] ) );
				PropertyParas.add( Double.parseDouble(Dline[2] ) );
				PropertyParas.add( Double.parseDouble(Dline[3] ) );
				PropertyParas.add( Double.parseDouble(Dline[4] ) );
				PropertyParas.add( Double.parseDouble(Dline[5] ) );
				
				ParaMap.put( ZipCode, PropertyParas ) ;
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
//				printArray(Dline);

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
	
	
	
	public static double[] FloodDamageFunc( double inundation, int ptype, int adapti, double adaptH) {
		inundation = inundation * 0.3048 ;
		
		adaptH = adaptH * 0.3048 ;
		double damageFactor1 = 1.0; 
		double damageFactor2 = 1.0; 
		double inundation1 = 0.0; //meter
		double inundation2 = 0.0; //meter	
		double tempD = 0, tempC = 0;
		double[] damage = {0, 0};
		
		
		inundation1 = inundation;
		
		if( inundation1 > 0 ) {
			if( inundation1 < 5  ) {
				if( ptype <= 4 && ptype != 3){
					if(inundation1 > 0){
						tempD =  0.2391*Math.pow(inundation1, 3) - 3.5524*Math.pow(inundation1, 2) +  19.933*inundation1 + 11.623; //residential
					}else{
						tempD = 0;
					}
//					if( inundation2 > 0){
//						tempC =  0.1037*Math.pow(inundation, 3) - 2.815*Math.pow(inundation, 2)  +  20.898*inundation + 14.957;
//					}else{
//						tempC = 0;
//					}
				}else {
					if(inundation1 > 0){
						tempD = -0.1347*Math.pow(inundation1, 3) + 1.1448*Math.pow(inundation1, 2) +  9.1078*inundation1 + 4.4057; //residential
					}else{
						tempD = 0;
					}
//					if( inundation2 > 0){
//						tempC =  0.6278*Math.pow(inundation2, 3) - 9.597*Math.pow(inundation2, 2)  +  47.22*inundation2  + 18.886;
//					}else{
//						tempC = 0;
//					}
				}
			}else {
				tempD = 100;
				tempC = 100;
			}
			
			
			if(  adapti > 1 ){
				if( inundation1 < adaptH ){  
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
		}else {
			damage[0] =  0.0 ;
			damage[1] =  0.0 ;
		}
		
		return damage;
	}
	
	public double CalculateFloodDamage( double floodHeight, int measure, double adaptH, double value, double coverage, double SWheight){
		double damagei = 0;
		if(floodHeight < 0){ floodHeight = 0;}
		if( measure == 1 ){	
			floodHeight =  floodHeight - this.elevation - this.eleHeight; 
		}else{
			floodHeight =  floodHeight - this.elevation ;
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
				damagei  = value*adamagei[0] - coverage ;
				if( damagei <= 0 ){ damagei = 0; }
			}
		}else{
			damagei = 0;
		}
		return damagei;
	}
	
	
	public static double FloodRiskF( double DEM_elevH, double[] surges, double value, int ptype, int floodAd, double adaptH, double govAdaptH) {
		double TotalRisk = 0;
		double[] height = new double[ probs.length ];
		double[] riski = {0, 0};
		double[] risk_k = new double[ probs.length ];
		for( int i = 0; i < surges.length; i ++){
			if(surges[i] < 0){ surges[i] = 0;}
			if( surges[i] <= govAdaptH  ) {
				surges[i] = 0.0;
			}else {
				surges[i] = surges[i] - govAdaptH;
			}
			
			height[i] = surges[i] - DEM_elevH;
			
			if( height[i] <= 0 ){
				height[i] = 0;
				risk_k[i] = 0;
			}else{
				riski = FloodDamageFunc2(height[i], ptype, floodAd, adaptH);
				risk_k[i] = value*riski[0] ;
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
	
	
	public static double[] FloodDamageFunc2( double inundation, int ptype, int adapti, double adaptH) {
		inundation = inundation * 0.3048;
		adaptH = adaptH * 0.3048 ;
		
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
		double[] damage = {0.0, 0.0};
				
//		System.out.println( inundation1 ) ;
		
		if( inundation1 > 0 ) {
			double tempD = 0, tempC = 0;
			if( inundation1 < 5  ) {
				if( ptype <= 4 && ptype != 3){
					if(inundation1 > 0){
						tempD =  0.2391*Math.pow(inundation1, 3) - 3.5524*Math.pow(inundation1, 2) +  19.933*inundation1 + 11.623; //residential
					}else{
						tempD = 0;
					}
//					if( inundation2 > 0){
//						tempC =  0.1037*Math.pow(inundation2, 3) - 2.815*Math.pow(inundation2, 2)  +  20.898*inundation2 + 14.957;
//					}else{
//						tempC = 0;
//					}
				}else {
					if(inundation1 > 0){
						tempD = -0.1347*Math.pow(inundation1, 3) + 1.1448*Math.pow(inundation1, 2) +  9.1078*inundation1 + 4.4057; //residential
					}else{
						tempD = 0;
					}
//					if( inundation2 > 0){
//						tempC =  0.6278*Math.pow(inundation2, 3) - 9.597*Math.pow(inundation2, 2)  +  47.22*inundation2  + 18.886;
//					}else{
//						tempC = 0;
//					}
				}
			}else {
				tempD =  100 ;
				tempC =  100 ;
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
		}else {
			damage[0] =  0.0 ;
			damage[1] =  0.0 ;
		}
		
		
		
		return damage;
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
	
	public static double elevateCost( double eleH, double EleArea, int type_P){
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
	
	
	public static double elevateCost2( double eleH, double EleArea, int type_P){
		double HardMeasureCost = 0;
		double Unitrate = 0;
		if( type_P == 1 ){
			Unitrate = 23 ;
		}else if( type_P == 4 ){
			Unitrate = 18 ;
		}else {
			Unitrate = 41 ;
		}
		if(eleH == 0){
			HardMeasureCost = 0;
		}else if(eleH <= 2 && eleH >0){
			HardMeasureCost = (Unitrate )* EleArea  ;
		}else {
			HardMeasureCost = (Unitrate  + 1.25*(eleH- 2)) * EleArea  ;
		}
//		BetaDistribution beta = new BetaDistribution(2, 5);
//		double cdfX = Math.random();
//		double b_prob = beta.inverseCumulativeProbability(cdfX);
		return HardMeasureCost;
	}
	
	
	public void houseElevation( int yrs ){
		double BFE = this.BFE;
		double erea_cal = 0.0;
		if( this.NORESUNTS > 2){
			erea_cal = this.liveArea / ( this.NORESUNTS + 1) ;
		}else{
			erea_cal = this.liveArea ;
		}
		this.bfeH = BFE - this.elevation + 1;

		this.elevationCost2 = elevateCost2( 2.0, erea_cal, this.Ptype);
		this.elevationCost4 = elevateCost2( 4.0, erea_cal, this.Ptype);
		this.elevationCost6 = elevateCost2( 6.0, erea_cal, this.Ptype);
		this.elevationCost8 = elevateCost2( 8.0, erea_cal, this.Ptype);
		
	}
	
	
	public void houseStrength( ){
		double cost =0;
		double structurePrice = 0;
		if(this.bidPrice > this.LV) {
			structurePrice = this.bidPrice - this.LV;
		}else {
			structurePrice = this.bidPrice;
		}
		if(this.liveArea < 2850 ){
			cost = this.liveArea * 8;
		}else {
			cost = structurePrice / 1200 * 5 ;
		}
		
		this.ShutterPay = cost/ 22.8 ;
	}
	
	
//	public double buyInsurance( double currenJV){
//		double insuranceCost;
//		if(this.Ptype < 5){
//			if(this.elePay > 0 | this.eleHeight > 1 | this.cate3 <= 0){
//				insuranceCost   = Insurer.PremiumCostF( currenJV, this.FLD_ZONE, this.elevation +this.eleHeight, this.cate3 );
//			}else{
//				insuranceCost = Insurer.insuranceCostF( currenJV, this.FLD_ZONE , this.elevation, this.cate3);
//			}
//		}else{
//			insuranceCost = Insurer.BusinessInsurance( currenJV, this.FLD_ZONE , this.elevation);
//		}
//		return insuranceCost;
//	}
	
	
	public ArrayList<Integer> getNeighborsList( ){
		ArrayList<Integer> NeighborsID = new ArrayList<Integer>();
		Iterator<Properties> iter = neighborsList.iterator();
		while(iter.hasNext()){
			NeighborsID.add(iter.next().FID);
		}
		return NeighborsID ;
	}
	
	public void displayProperties() {
		System.out.println( "HouseID " + this.FID + 
							"\nbidder "+ this.bidderage +
							"\nElevation " +  this.elevation + 
							"\n justvalue " + this.Justvalue + 
							"\n income " +this.bidIncome + 
							"\n zone " + this.zone);
	}



}
