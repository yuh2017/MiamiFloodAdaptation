
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
}
