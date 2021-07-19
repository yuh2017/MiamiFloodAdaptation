
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Government {
	
	public static double[] probs  = {0.1, 0.04, 0.02, 0.01, 0.005};

	public double PumpCosts, SeawallCosts, goveAdaptH ;
	
	public int idx , FID , Desc , CensusID , DistrictID , EFFYER , HOUSEHOLDS, withIns;
	public double IMPROVVAL , Justvalue , LADVAL , MEDHHINC , MEDOOHVAL , DistWater;
	public int NOBULDNG , NORESUNTS , OWNER , TOTALPOP , WHITE , BLACK , HISPANIC;
	public double PCT_POV , HBELOW_POV , cate1 , cate2 , cate3, cate4, cate5;
	public double latitude , longitude;
	
	/**
	 * increased premium +2%
	 * monthly utility $ 16
	 */
	
	public Government() {
		this.goveAdaptH = 0.0;
		//System.out.println("create empty house property");
	}
	
	public static double TotalSeaWallCosts(double length) {
		double damage;
		damage = 3280840 * length / 1000; 
		return damage;
	}
	
	public static double[] TotalPumpCosts(int N) {
		double[] damage = new double[2];
		damage[0] = 1.937*1000000 * N; 
		damage[1] = 1.937*1000000 * 0.1 * N ;
		return damage;
	}
	
	public static double RiskReduction( double distance  ){
		double ReductionP;
		ReductionP = Math.exp( -8 * distance / 0.03 - 0.98 );
		return ReductionP;
	}
	
	
	public static double RiskReductionSeaWall( double distance  ){
		double ReductionP;
		ReductionP = Math.exp( -14 * distance / 0.03 - 0.48 );
		return ReductionP;
	}
	
	
	
	public void AdaptDecision4( ArrayList<Properties> ResidBidPrice ) throws IOException{
		
		
		double[] PubSeawallHeights 		= { 0.0, 2.0, 4.0, 6.0, 8.0, 10.0 };
		double[] PubSeawallCBA 			= { 0.0, 0.0, 0.0, 0.0 , 0.0 , 0.0};
		double[] SeawallUnitCost 		= { 0.0, 81.0, 151.18, 251.98, 332.37, 417.86 };
//		double seawallLength = 55;
		for( int k = 0; k < PubSeawallHeights.length; k ++ ) {
			double heighti = PubSeawallHeights[k] ;
			double riskreduction , totalRiskReduction;
			totalRiskReduction = 0.0;
			double seallCost = 55 * SeawallUnitCost[k] / 0.0001894 ;
			PubSeawallCBA[k] = PubSeawallCBA[k] + seallCost;
			Iterator<Properties> iter0 = ResidBidPrice.iterator();
			while(iter0.hasNext()){
				Properties housei = iter0.next();
				double mu = housei.mu0 		+ 1.0 * housei.muse0 ;
				double xi = housei.xi0 		+ 1.0 * housei.xise0 ;
				double beta = housei.beta0 	+ 1.0 * housei.betase0 ;
				
				double[] stormsurges = {0.0, 0.0, 0.0, 0.0, 0.0} ;
				double[] stormsurges2 = {0.0, 0.0, 0.0, 0.0, 0.0} ;
				for(int i = 0; i < probs.length; i ++){
					stormsurges[i] 	= InitClass.surgeHeightF(probs[i], mu, xi, beta) ;
					
					if( stormsurges[i] <  heighti ) {
						stormsurges2[i] = 0.0 ;
					}else {
						stormsurges2[i] = stormsurges[i] - heighti ;
					}
				} 
				riskreduction =  Properties.FloodRiskF( housei.elevation, stormsurges, housei.CurrentJV, 
										housei.Ptype,  0, 0.0, 0.0  ) - Properties.FloodRiskF( housei.elevation, stormsurges2, 
												housei.CurrentJV, housei.Ptype,  0, 0.0, 0.0  ) ;
				
				for( int yri = 0; yri < 100; yri++  ) {
					totalRiskReduction = totalRiskReduction + riskreduction * 1 / (  1 + Math.pow( 0.05, yri ) ) ;
					PubSeawallCBA[k] = PubSeawallCBA[k] + seallCost * 0.05 * 1 / (  1 + Math.pow( 0.05, yri ) ) ;
				}
				
				PubSeawallCBA[k] = PubSeawallCBA[k] - totalRiskReduction ;
				
			}
		}
		System.out.println( Arrays.toString( PubSeawallCBA )  );
		
		int seallid = InitClass.findSmallest0(PubSeawallCBA) ;
		double goveAdaptH = PubSeawallHeights[seallid] ;
		this.goveAdaptH = goveAdaptH ;
	}
	
	
	
public void AdaptDecision3( ArrayList<Properties> ResidBidPrice ) throws IOException{
		
		double[] PubSeawallHeights 		= { 0.0, 2.0, 4.0, 6.0, 8.0, 10.0 };
		double[] PubSeawallCBA 			= { 0.0, 0.0, 0.0, 0.0 , 0.0 , 0.0};
		double[] SeawallUnitCost 		= { 0.0, 81.0, 151.18, 251.98, 332.37, 417.86 };
//		double seawallLength = 55;
		for( int k = 0; k < PubSeawallHeights.length; k ++ ) {
			double heighti = PubSeawallHeights[k] ;
			double riskreduction , totalRiskReduction;
			totalRiskReduction = 0.0;
			double seallCost = 55 * SeawallUnitCost[k] / 0.0001894 ;
			PubSeawallCBA[k] = PubSeawallCBA[k] + seallCost;
			Iterator<Properties> iter0 = ResidBidPrice.iterator();
			while(iter0.hasNext()){
				Properties housei = iter0.next();
				double mu = housei.mu0 		- 1.0 * housei.muse0 ;
				double xi = housei.xi0 		- 1.0 * housei.xise0 ;
				double beta = housei.beta0 	- 1.0 * housei.betase0 ;
				double[] stormsurges = {0.0, 0.0, 0.0, 0.0, 0.0} ;
				
				double[] stormsurges2 = {0.0, 0.0, 0.0, 0.0, 0.0} ;
				
				for(int i = 0; i < probs.length; i ++){
					stormsurges[i] 	= InitClass.surgeHeightF(probs[i], mu, xi, beta) ;
					
					if( stormsurges[i] <  heighti ) {
						stormsurges2[i] = 0.0 ;
					}else {
						stormsurges2[i] = stormsurges[i] - heighti ;
					}
				} 
				riskreduction =  Properties.FloodRiskF( housei.elevation, stormsurges, housei.CurrentJV, 
										housei.Ptype,  0, 0.0, 0.0 ) - Properties.FloodRiskF( housei.elevation, stormsurges2, 
												housei.CurrentJV, housei.Ptype,  0, 0.0 , 0.0 ) ;
				
				for( int yri = 0; yri < 100; yri++  ) {
					totalRiskReduction = totalRiskReduction + riskreduction * 1 / (  1 + Math.pow( 0.05, yri ) ) ;
					PubSeawallCBA[k] = PubSeawallCBA[k] + seallCost * 0.05 * 1 / (  1 + Math.pow( 0.05, yri ) ) ;
				}
				
				PubSeawallCBA[k] = PubSeawallCBA[k] - totalRiskReduction ;
//				System.out.println( Arrays.toString(housei.Surge)  );			
			}
			
			
		}
		
		System.out.println( Arrays.toString( PubSeawallCBA )  );	
		int seallid = InitClass.findSmallest0(PubSeawallCBA) ;
		double govHeight = PubSeawallHeights[seallid] ;
		this.goveAdaptH = govHeight ;
		
	}
	
	
	/**
	 * increased premium +5%
	 * monthly utility $8
	 */

	/**
	 * increased premium +8%
	 * monthly utility $4
	 */
	/**
	 * increased premium +15%
	 */
	
	
//	public static ArrayList<Properties> residBidPrice( ArrayList<Properties> censusProperty, ArrayList<Agents> censusHouseholds){
//		ArrayList<Properties> ResidBidPrice = new ArrayList<Properties>();
//		Iterator<Properties> iter2 = censusProperty.iterator();
//		ArrayList<Integer> bidedHouseid= new ArrayList<Integer>();
//		while(iter2.hasNext()){
//			Properties Housei = iter2.next();
//			double JV = Housei.Justvalue;
//			double bidIncome = 0;
//			double maxbid = JV;
//			int bidHHiid = 0;
//			double diffBid = JV*0.5;
//			double bidPrice = 0;	
//			if(Housei.Desc == 1){
//				Iterator<Agents> iter3 = censusHouseholds.iterator();
//				//Random rand = new Random();
//				//int numberOfElements = censusHouseholds.size();
//				
//				while(iter3.hasNext()){
//					//int randIndex = rand.nextInt(numberOfElements);
//					Agents householdi = iter3.next();
//					int hhid = householdi.HHID;
//					if( householdi.income * 5 > JV &&  householdi.income*2  < JV && householdi.income < 500000){
//						if (householdi.income < 60000 ) {
//							bidPrice =  -1.011e6  - 2.773e2* Housei.NORESUNTS + 
//										3.57e2* Math.log( Housei.LADVAL + 0.01) -
//										1.601e4* Math.log(Housei.accesscommercial + 0.01) +
//										6.131e3* Math.log(Housei.liveArea + 0.01) -
//										8.247e3* Math.log(Housei.EFFYER + 0.01) +
//										1.97e4* Math.log(Housei.accesscenter + 0.01) + 
//										5.318e3* Math.log(Housei.accessindustrial + 0.01) +
//										1.129e5* Math.log(householdi.income + 0.01 ) -
//										1.005e-1 * Housei.FRisk	 ;
//				        } else if (householdi.income > 60000 && householdi.income < 120000) {
//				        	bidPrice =  -3.272e6  + 1.663e2* Housei.NORESUNTS +
//				        				1.31e1* Math.log( Housei.LADVAL + 0.01) -
//				        				3.472e2*Math.log(Housei.accesscommercial + 0.01) - 
//				        				4.371e2* Math.log(Housei.liveArea + 0.01) - 
//				        				1.893e4* Math.log(Housei.EFFYER + 0.01) +
//				        				3.336e2* Math.log(Housei.accesscenter + 0.01) -
//				        				1.124e2* Math.log(Housei.accessindustrial + 0.01) + 
//				        				3.317e5* Math.log(householdi.income + 0.01) +
//				        				3.641e-2 * Housei.FRisk ;
//				        } else if (householdi.income > 120000 && householdi.income < 230000) {
//				        	bidPrice =  -7.218e6  -3.345e1* Housei.NORESUNTS - 
//				        				1.481e2* Math.log( Housei.LADVAL + 0.01)  -
//				        				2.493e3*Math.log(Housei.accesscommercial + 0.01) +
//				        				7.832e2* Math.log(Housei.liveArea + 0.01) -
//				        				7.333e2* Math.log(Housei.EFFYER + 0.01) +
//				        				2.625e3* Math.log(Housei.accesscenter+ 0.01) +
//				        				1.298e3* Math.log(Housei.accessindustrial+ 0.01) + 
//				        				6.568e5* Math.log(householdi.income + 0.01) +
//				        				4.143e-2 * Housei.FRisk ;
//				        } else if(householdi.income > 230000 && householdi.income < 500000 ){
//				        	bidPrice =  -4.060e7  - 4.562e4* Housei.NORESUNTS -
//			        					2.639e4* Math.log( Housei.LADVAL+ 0.01)  -
//			        					3.13e5*Math.log(Housei.accesscommercial+ 0.01) -
//			        					7.756e5* Math.log(Housei.liveArea+ 0.01) +
//			        					7.249e5* Math.log(Housei.EFFYER+ 0.01) +
//			        					6.794e5* Math.log(Housei.accesscenter+ 0.01) -
//			        					1.935e5* Math.log(Housei.accessindustrial+ 0.01) +
//			        					3.349e6* Math.log(householdi.income + 0.01) + 
//			        					4.18 * Housei.FRisk ;
//				        }
//						
//						if(bidPrice > maxbid && bidPrice - JV < diffBid && !bidedHouseid.contains(hhid) ){
//							maxbid = bidPrice;
//							bidHHiid = hhid;
//							bidIncome = householdi.income;
//						}
//						
//				        }else if( householdi.income*2  < JV && householdi.income > 500000) {
//				        	bidPrice =  -98285947  +43376* Housei.NORESUNTS -120945* Math.log( Housei.LADVAL+ 0.01)  -
//		        					752245*Math.log(Housei.accesscommercial+ 0.01) -
//		        					1266094* Math.log(Housei.liveArea+ 0.01) + 
//		        					1688591* Math.log(Housei.EFFYER+ 0.01) +
//		        					1481966* Math.log(Housei.accesscenter+ 0.01) -
//		        					513925 * Math.log(Housei.accessindustrial+ 0.01) + 
//		        					7448924* Math.log(householdi.income + 0.01);
//				        	
//				        	if(bidPrice > maxbid && bidPrice - JV < diffBid && !bidedHouseid.contains(hhid) ){
//								maxbid = bidPrice;
//								bidHHiid = hhid;
//								bidIncome = householdi.income;
//								
//							}
//				        }
//					} 
//				if(maxbid > JV){
//					bidedHouseid.add(bidHHiid);
//					//Properties ResidBids = new Properties();
//					Housei.bidHHiid = bidHHiid;
//					Housei.bidPrice = maxbid;
//					Housei.bidIncome = bidIncome;
//					Housei.pastFlood       =  0;
//					
//					ResidBidPrice.add(Housei);
//					}
//				}
//			}
//		return ResidBidPrice;
//	}
	//ArrayList<Double> FractDamage = new ArrayList<Double>();
//	if(residi.cate1 > 0){
//			double[] damage  =  FloodDamage(residi.liveArea, residi.cate1, residi.Justvalue);
//			fractDamag1 = expectUtility( damage[0]);
//			PossDamag1  = expectUtility( damage[1]);
////			Damag1      = expectUtility( damage[0] + damage[1] );
//	}else{
//			fractDamag1 = 0;
//			PossDamag1  = 0;
//			//Damag1      = 0;
//		}
//	if( residi.cate2 > 0){
//			double[] damage  =  FloodDamage(residi.liveArea, residi.cate2, residi.Justvalue);
//			fractDamag2 = expectUtility( damage[0]);
//			PossDamag2  = expectUtility( damage[1]);
//			//Damag2      = expectUtility( damage[0] + damage[1] );
//		}else{
//			fractDamag2 = 0;
//			PossDamag2  = 0;
////			Damag2      = 0;
//		}
//	if(residi.cate3 > 0){
//			double[] damage  =  FloodDamage(residi.liveArea, residi.cate3, residi.Justvalue);
//			fractDamag3 = expectUtility( damage[0]);
//			PossDamag3  = expectUtility( damage[1]);
//			//Damag3      = expectUtility( damage[0] + damage[1] );
//		}else{
//			fractDamag3 = 0;
//			PossDamag3  = 0;
//			//Damag3      = 0;
//		}
//	if( residi.cate4 > 0){
//			double[] damage  =  FloodDamage(residi.liveArea, residi.cate4, residi.Justvalue);
//			fractDamag4 = expectUtility( damage[0]);
//			PossDamag4  = expectUtility( damage[1]);
//			//Damag4      = expectUtility( damage[0] + damage[1] );
//		}else{
//			fractDamag4 =  0;
//			PossDamag4  =  0;
//			//Damag4      =  0;
//		}
//	if( residi.cate5 > 0){
//			double[] damage  =  FloodDamage(residi.Area, residi.cate5, residi.Justvalue);
//			fractDamag5 = expectUtility( damage[0]);
//			PossDamag5  = expectUtility( damage[1]);
//			//Damag5      = expectUtility( damage[0] + damage[1] );
//	}else{
//			fractDamag5 =  0;
//			PossDamag5  =  0;
////			Damag5      =  0;
//		}
//	double[] paras = new double[]{0.2, 0.1, 0.05, 0.02, 0.01};
//	residi.FRisk = paras[0]*fractDamag1 + paras[1] * fractDamag2 + paras[2] * fractDamag3 + paras[3] * fractDamag4 + paras[4] * fractDamag5;
//	residi.PropertyRisk  = paras[0]*PossDamag1 + paras[1] * PossDamag2 + paras[2] * PossDamag3 + paras[3] * PossDamag4 + paras[4] * PossDamag5;
//	residi.totaRisk = residi.FRisk + residi.PropertyRisk;
	//residi.totaRisk2 = expectedRisk( residi.elevation, residi.liveArea, residi.Justvalue );
	//residi.totaRisk2 = expectedRiskSLR( residi.elevation, residi.liveArea, residi.Justvalue );
}
