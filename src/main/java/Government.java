import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Government {
	public double utilityCost 	= 0.0;
	public double seawallH 		= 0.0;
	public double PumpCosts 	= 0.0; 
	public double SeawallCosts 	= 0.0;
	/**
	 * increased premium +2%
	 * monthly utility $ 16
	 */
	public double currentHeight 	= 0;
	public int protectChoice;
	public int lengthOfEvaluation 	= 5;
	public double[] lookupDamage  	= new double[10];
	public double[] lookupReturnP 	= new double[10];
	public Government(){}
	
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
	
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Entry.comparingByValue( Comparator.reverseOrder() ));
        Map<K, V> result = new LinkedHashMap<>();
        for (Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
	public static double FloodDamageFunc( double inundation) {
		inundation = inundation / 3.28084; //meter
		double damage = 0;
		double tempD;
		tempD = 0.2391*Math.pow(inundation, 3) - 3.5524*Math.pow(inundation, 2)+ 19.933*inundation +11.623; //residential
		//tempS = 0.1037*Math.pow(inundation, 3) - 2.815*Math.pow(inundation, 2)+ 20.898*inundation +14.957; //residential
		if( tempD > 0){
			damage = tempD / 100;
		}else{
			damage = 0;
		}
		return damage;
	}
	public static double[] residentialCensus(ArrayList<Properties> residentials, String CID, double R_H){
		double[] R_H_value = {0,0};
		int count = 0;
		for (Properties entity : residentials) {
			if (entity.CensusID == CID) {
					R_H_value[0] += entity.FloodRisk0;
					count ++;
				
		    }
		}
		double damage_R_i = 0;
		for (Properties entity : residentials) {
			if (entity.CensusID == CID) {
				if( entity.cate4 > R_H) {
					damage_R_i = entity.Justvalue*( FloodDamageFunc( entity.cate4  ) - FloodDamageFunc( entity.cate4 - R_H ) );
					R_H_value[1] += damage_R_i;
					entity.gov_flood_damage_R = damage_R_i;
					entity.gov_Fheight_reduct = R_H;
				}else {
					entity.gov_flood_damage_R = 0;
					entity.gov_Fheight_reduct = 0;
				}
			 }else {
					entity.gov_flood_damage_R = 0;
					entity.gov_Fheight_reduct = 0;
				}
		}
		
		return R_H_value;
	}
	
	public static double TotalResidentialArea(ArrayList<Properties> residentials){
		Iterator<Properties> iter = residentials.iterator();
		double TotalArea = 0;
		while(iter.hasNext()){
			Properties property = iter.next();
			TotalArea += property.liveArea;
		}
		return TotalArea;
	}
	
	
	public int[] RiskAssessment( ArrayList<Properties> residentials, ArrayList<Census> CensusList, int preserve_N, double reduct_H){
		int[] Preserve_censusid = new int[preserve_N];
		double totalArea = TotalResidentialArea(residentials);
		
		if( preserve_N > 0){
			Iterator<Census> iter = CensusList.iterator();
			HashMap<String, Double> hmap_tpvalues 	= new HashMap<String, Double>();
			HashMap<String, Double> hmap_total 		= new HashMap<String, Double>();
			int N_households = residentials.size();
			
			
			while(iter.hasNext()){
				Census censusi = iter.next();
				double[] censusi_values = residentialCensus(residentials, censusi.CensusID, reduct_H);
				hmap_tpvalues.put(censusi.CensusID, censusi_values[1]);
				hmap_total.put(censusi.CensusID, censusi_values[0]);
				}
			HashMap<String, Double> hmap_tarea_sorted = (HashMap<String, Double>) sortByValue(hmap_total);
			int count = 0;
			for(Map.Entry m:hmap_tarea_sorted.entrySet()){  
//				System.out.println("Protect "+m.getKey()+" "+m.getValue());  
				Preserve_censusid[count] = (int) m.getKey();
				this.utilityCost += (double) hmap_tpvalues.get( m.getKey() );
				count ++;
				if (count >= preserve_N){ break; }
			}
			this.utilityCost = this.utilityCost / totalArea *(1 - 0) /1.29 ; //1.29
			System.out.println("The utility cost is "+ this.utilityCost);  
		}else{
			this.utilityCost = 0;
			System.out.println("The utility cost is "+ this.utilityCost);  
		}
		return Preserve_censusid;
	}
	public double TotalSeaWallCosts(double length, double height) {
		double totalCosts = 0;
		
//		if( height == 2 ){
//			totalCosts = 85*Math.pow(1+0.02, 16)*length;
//		}else if( height == 4 ){
//			totalCosts = 124*Math.pow(1+0.02, 16)*length;
//		}
		if( height == 2 ){
			totalCosts = 648*2*length;
		}else if( height == 4 ){
			totalCosts = 648*4*length;
		}
		
		this.SeawallCosts = totalCosts * 3.28084; 
		return totalCosts;
	}
	public void SeaWallCostEstimator( ArrayList<Properties> residentials, double height, int protectChoice){
		double totalLength = 88996;
		this.protectChoice = protectChoice;
		if( this.protectChoice == 0 ){
			this.seawallH = 0;
			this.utilityCost = 0;
		}else if( this.protectChoice == 1 ){
			this.seawallH = height;
			TotalSeaWallCosts(totalLength, this.seawallH) ;
			double totalArea = TotalResidentialArea(residentials);
			this.utilityCost = this.SeawallCosts / totalArea;
		}else if( this.protectChoice == 2 ){
			double totalDamage = Arrays.stream( this.lookupDamage ).sum() ;
			if( this.currentHeight == 0){
				this.currentHeight = height;
			}
			double[] totalD = {0.0, 0.0};
			for( int k = 0; k < this.lookupReturnP.length; k ++){
				Iterator<Properties> iter3 = residentials.iterator();
				double floodH_yrsi;
				while(iter3.hasNext())  {
					Properties budlidngi = iter3.next();
					floodH_yrsi = -1* Math.log( lookupReturnP[k]) / budlidngi.beta0 + budlidngi.mu0 + budlidngi.slrHeight;
					TotalSeaWallCosts(this.currentHeight, this.currentHeight) ;
					totalD[0] += budlidngi.CalculateFloodDamage( floodH_yrsi, budlidngi.FloodAD, this.seawallH, budlidngi.exposedValue, budlidngi.insuranceCoverage, this.currentHeight )	; 
				}
			}
		}
		
	}
}
