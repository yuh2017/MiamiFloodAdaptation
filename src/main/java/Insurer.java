import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Insurer {
	public int Floodzone_N, NonFloodzone_N;
//	public double 
	public static ArrayList<Double> estimateRisk(Map <Integer, Properties> HouseRisk) {
		ArrayList<Double> InsurInfo = new ArrayList<Double>();
		double totalRisk = 0;
		double nBuyers = 0;
		double Revenue = 0;
		for (Entry<Integer, Properties> entry : HouseRisk.entrySet()) {
			int houseID = entry.getKey();
			Properties floodProperty = entry.getValue();
			double Pr = floodProperty.insuranceCost;
			
			totalRisk = totalRisk + floodProperty.FloodRisk1;
			if(Pr > 0.41){
				nBuyers = nBuyers + 1;
				Revenue = Revenue + floodProperty.insuranceCost;
			}
		}
		InsurInfo.add(totalRisk);
		InsurInfo.add(nBuyers);
		InsurInfo.add(Revenue);
		return InsurInfo;
	}
	public static double[] private_parameter_zone( double aboveBFE, String zone){
		double[] paras = {0, 0};
		double a0_p, b0_c=0;
		if(aboveBFE > 4){aboveBFE = 4;}
		if(aboveBFE < -1){aboveBFE = -1;}
		
		
		if(zone.equals("A")){
			a0_p = Math.exp( aboveBFE * -0.5832 + 1.93 );
//			a0_p = Math.exp( aboveBFE * -0.45 + 2.0 );
		}else if(zone.equals("V")){
			a0_p = Math.exp( -0.3115*aboveBFE + 1.5459 );
//			a0_p = Math.exp( -0.3464*aboveBFE + 1.202 );
		}else{
//			a0_p = Math.exp( -0.544*aboveBFE + 0.57);
			a0_p = Math.exp( -0.6979*aboveBFE + 0.6915);
//			if( aboveBFE >= 1 ){a0_p = 1.31;}
//			else{a0_p = 2.2;}
		}
		paras[0] = a0_p;
		paras[1] = b0_c;
		return paras;
	}
	
	public static double[] privateInsurance( double Value,  String zone, double elevation, double eleHeight, double BFE){
		int Azone = zone.indexOf('A');
		int Vzone = zone.indexOf('V');
		double diff = Math.random()*1.10;
		String zoneAV ;
		if(Azone >= 0 ){
			zoneAV = "A";
		}else if( Vzone >= 0 ){
			zoneAV = "V";
		}else{
			zoneAV = "NA";
		}
		
		double new_elevation = elevation + eleHeight;
		double aboveBFE = new_elevation - (BFE - diff);
		
		double a0_p;
		double[] paras = private_parameter_zone( aboveBFE, zoneAV );
		a0_p = paras[0];
//		System.out.println("zone "+ zone+ " "+zoneAV);
		double caver = a0_p;
		double[] PrivateCost = { 0, 0, 0, 0, 0, 0, 0, 0 };
		
		double Coverage1 = 0, Coverage2 = 0, Coverage3 = 0, Coverage4 = 0;
		double Insurance1 = 0, Insurance2 = 0, Insurance3 = 0, Insurance4 = 0;
		
		if( Value < 60000){
			Coverage1 = 60000;
			Coverage2 = 60000;
			Coverage3 = 250000;
			Coverage4 = 250000;
		}else if( Value >= 60000 & Value < 250000 ){
			Coverage1 = 60000;
			Coverage2 = Value;
			Coverage3 = 250000;
			Coverage4 = 250000;
		}else if( Value >= 250000 & Value < 500000 ){
			Coverage1 = 60000;
			Coverage2 = 155000;
			Coverage3 = 250000;
			Coverage4 = 500000;
		}else{
			Coverage1 = 60000;
			Coverage2 = 155000;
			Coverage3 = 250000;
			Coverage4 = 500000;
		}
		Insurance1 = Coverage1/100*caver; 
		Insurance2 = Coverage2/100*caver; 
		Insurance3 = Coverage3/100*caver; 
		Insurance4 = Coverage4/100*caver; 
		PrivateCost[0] = Coverage1;
		PrivateCost[1] = Coverage2;
		PrivateCost[2] = Coverage3; 
		PrivateCost[3] = Coverage4; 
		PrivateCost[4] = Insurance1;
		PrivateCost[5] = Insurance2;
		PrivateCost[6] = Insurance3;
		PrivateCost[7] = Insurance4;
		return PrivateCost;
	}
	
	
//	public static double[] parameter_zone( double aboveBFE, String zone){
//		double[] paras = {0, 0};
//		double a0_p, b0_c=0;
//		if(zone.equals("A")){
//			
//			if(aboveBFE > 3){
//				a0_p = 0.24;
//			}else if(aboveBFE > 2 & aboveBFE <= 3){
//				a0_p = 0.3;
//			}else if(aboveBFE > 1 & aboveBFE <= 2){
//				a0_p = 0.42;
//			}else if( aboveBFE > 0 & aboveBFE <= 1 ){
//				a0_p = 0.71;
//			}else if( aboveBFE > 0 & aboveBFE <= -1){
//				a0_p = 1.78;
//			}else{
//				a0_p = 4.4;
//			}	
////			a0_p = Math.exp( aboveBFE * -0.5832 + 0.5776 );
//		}else if(zone.equals("V")){
//			
//			if(aboveBFE > 3){
//				a0_p = 0.7;
//			}else if(aboveBFE > 2 & aboveBFE <= 3){
//				a0_p = 0.75;
//			}else if(aboveBFE > 1 & aboveBFE <= 2){
//				a0_p = 1.01;
//			}else if( aboveBFE > 0 & aboveBFE <= 1 ){
//				a0_p = 1.27;
//			}else if( aboveBFE > 0 & aboveBFE <= -1){
//				a0_p = 1.75;
//			}else if( aboveBFE > -1 & aboveBFE <= -2){
//				a0_p = 2.39;
//			}else{
//				a0_p = 3.41;
//			}			
////			a0_p = Math.exp( -0.279*aboveBFE + 0.6092 );
//		}else{
//			
//			if(aboveBFE > 3){
//				a0_p = 0.1;
//			}else if(aboveBFE > 2 & aboveBFE <= 3){
//				a0_p = 0.14;
//			}else if(aboveBFE > 1 & aboveBFE <= 2){
//				a0_p = 0.24;
//			}else if( aboveBFE > 0 & aboveBFE <= 1 ){
//				a0_p = 0.35;
//			}else if( aboveBFE > 0 & aboveBFE <= -1){
//				a0_p = 0.71;
//			}else{
//				a0_p = 1.4;
//			}	
////			a0_p = Math.exp( -0.507*aboveBFE -0.3348 );
////			if( aboveBFE >= 1 ){a0_p = 1.41;b0_c = 0.38;}
////			else{a0_p = 1.7;b0_c = 1.15;}
//		}
//		paras[0] = a0_p * 1;
//		paras[1] = b0_c;	
//		return paras;
//	}
	public static double[] parameter_zone( double aboveBFE, String zone, int Ptype){		
		double[] paras = {0, 0};
		double a0_p, b0_c=0;
		
		if( Ptype <= 4 ){
			if( zone.equals("A")  ){
				if(aboveBFE >= 4){
					a0_p = 0.3;
				}else if(aboveBFE >= 3 & aboveBFE < 4){
					a0_p = 0.34;
				}else if(aboveBFE >= 2 & aboveBFE < 3){
					a0_p = 0.49;
				}else if(aboveBFE >= 1 & aboveBFE < 2){
					a0_p = 0.92;
				}else if( aboveBFE >= 0 & aboveBFE < 1 ){
					a0_p = 2.13; 
				}else if( aboveBFE >= -1 & aboveBFE < 0){
					a0_p = 4.92;
				}else if( aboveBFE >= -2 & aboveBFE < -1){
					a0_p = 5.18;
				}else if( aboveBFE >= -3 & aboveBFE < -2){
					a0_p = 9.52;
				}else if( aboveBFE >= -4 & aboveBFE < -3){
					a0_p = 11.52;
				}else if( aboveBFE >= -5 & aboveBFE < -4){
					a0_p = 13.05;
				}else if( aboveBFE >= -6 & aboveBFE < -5){
					a0_p = 13.44;
				}else if( aboveBFE >= -7 & aboveBFE < -6){
					a0_p = 13.84;
				}else if( aboveBFE >= -8 & aboveBFE < -7){
					a0_p = 13.44;
				}else if( aboveBFE >= -9 & aboveBFE < -8){
					a0_p = 14.12;
				}else if( aboveBFE >= -10 & aboveBFE < -9){
					a0_p = 14.14;
				}else if( aboveBFE >= -11 & aboveBFE < -10){
					a0_p = 14.15;
				}else if( aboveBFE >= -12 & aboveBFE < -11){
					a0_p = 14.16;
				}else if( aboveBFE >= -13 & aboveBFE < -12){
					a0_p = 14.17;
				}else if( aboveBFE >= -14 & aboveBFE < -13){
					a0_p = 14.18;
				}else {
					a0_p = 14.23;
				}
				
//				a0_p = Math.exp( aboveBFE * -0.5832 + 0.5776 );
			}else if(zone.equals("V")){
				if(aboveBFE >= 4){
					a0_p = 0.88;
				}else if(aboveBFE >= 3 & aboveBFE < 4){
					a0_p = 0.96;
				}else if(aboveBFE >= 2 & aboveBFE < 3){
					a0_p = 1.39;
				}else if(aboveBFE >= 1 & aboveBFE < 2){
					a0_p = 1.88;
				}else if( aboveBFE >= 0 & aboveBFE < 1 ){
					a0_p = 2.44;
				}else if( aboveBFE >= -1 & aboveBFE < 0){
					a0_p = 5.18;
				}else if( aboveBFE >= -2 & aboveBFE < -1){
					a0_p = 7.64;
				}else if( aboveBFE >= -3 & aboveBFE < -2){
					a0_p = 11.52;
				}else if( aboveBFE >= -4 & aboveBFE < -3){
					a0_p = 13.05;
				}else if( aboveBFE >= -5 & aboveBFE < -4){
					a0_p = 14.05;
				}else {
					a0_p = 18.44;
				}
				
			}else{
				if(aboveBFE > 4){
					a0_p = 0.3;
				}else if(aboveBFE > 3 & aboveBFE <= 4){
					a0_p = 0.31;
				}else if(aboveBFE > 2 & aboveBFE <= 3){
					a0_p = 0.49;
				}else if( aboveBFE > 1 & aboveBFE <= 2 ){
					a0_p = 0.92;
				}else if( aboveBFE > 0 & aboveBFE <= 1){
					a0_p = 1.1;
				}else{
					a0_p = 3.1;
				} 
			}
		}else{
			if(aboveBFE >= 4){
				a0_p = 0.27;
			}else if(aboveBFE >= 3 & aboveBFE < 4){
				a0_p = 0.31;
			}else if(aboveBFE >= 2 & aboveBFE < 3){
				a0_p = 0.45;
			}else if(aboveBFE >= 1 & aboveBFE < 2){
				a0_p = 0.62;
			}else if( aboveBFE >= 0 & aboveBFE < 1 ){
				a0_p = 1.15; 
			}else if( aboveBFE >= -1 & aboveBFE < 0){
				a0_p = 2.13;
			}else if( aboveBFE >= -2 & aboveBFE < -1){
				a0_p = 3.18;
			}else if( aboveBFE >= -3 & aboveBFE < -2){
				a0_p = 4.01;
			}else{
				a0_p = 5.18;
			}
			
			
			
//			else if( aboveBFE >= -4 & aboveBFE < -3){
//				a0_p = 10.44;
//			}else if( aboveBFE >= -5 & aboveBFE < -4){
//				a0_p = 11.98;
//			}else if( aboveBFE >= -6 & aboveBFE < -5){
//				a0_p = 12.52;
//			}else if( aboveBFE >= -7 & aboveBFE < -6){
//				a0_p = 13.00;
//			}else if( aboveBFE >= -8 & aboveBFE < -7){
//				a0_p = 13.35;
//			}else if( aboveBFE >= -9 & aboveBFE < -8){
//				a0_p = 13.41;
//			}else if( aboveBFE >= -10 & aboveBFE < -9){
//				a0_p = 13.46;
//			}else if( aboveBFE >= -11 & aboveBFE < -10){
//				a0_p = 13.51;
//			}else if( aboveBFE >= -12 & aboveBFE < -11){
//				a0_p = 13.56;
//			}else if( aboveBFE >= -13 & aboveBFE < -12){
//				a0_p = 13.70;
//			}else if( aboveBFE >= -14 & aboveBFE < -13){
//				a0_p = 13.79;
//			}else if( aboveBFE >= -15 & aboveBFE < -14){
//				a0_p = 14.10;
//			}else {
//				a0_p = 14.10;
//			}
		}
		paras[0] = a0_p * 1;
		paras[1] = b0_c;	
		return paras;
	}
	
	
	public static double[] parameter_gfathering( double aboveBFE, String zone, int preFIRM){
		double[] paras = {0, 0};
		double a0_p, b0_c=0;
		if(zone.equals("A") && preFIRM == 0 ){
			
			if(aboveBFE > 3){
				a0_p = 0.34;
			}else if(aboveBFE > 2 & aboveBFE <= 3){
				a0_p = 0.49;
			}else if(aboveBFE > 1 & aboveBFE <= 2){
				a0_p = 0.92;
			}else if( aboveBFE > 0 & aboveBFE <= 1){
				a0_p = 1.28;
			}else{
				a0_p = 3.4;
			}
		}else if( zone.equals("A") && preFIRM == 1 ){
			
			if(aboveBFE >= 4){
				a0_p = 0.3;
			}else if(aboveBFE >= 3 & aboveBFE < 4){
				a0_p = 0.34;
			}else if(aboveBFE >= 2 & aboveBFE < 3){
				a0_p = 0.49;
			}else if(aboveBFE >= 1 & aboveBFE < 2){
				a0_p = 0.92;
			}else { 
				a0_p = 1.1;
			}
		}else if(zone.equals("V")){
			
			if(aboveBFE > 4){
				a0_p = 0.88;
			}else if(aboveBFE > 3 & aboveBFE <= 4){
				a0_p = 0.96;
			}else if(aboveBFE > 2 & aboveBFE <= 3){
				a0_p = 1.39;
			}else if( aboveBFE > 1 & aboveBFE <= 2 ){
				a0_p = 1.88;
			}else if( aboveBFE > 0 & aboveBFE <= 1){
				a0_p = 2.44;
			}else{
				a0_p = 3.18;
			} 
			
//			a0_p = Math.exp( -0.279*aboveBFE + 0.6092 );
		}else{
				
			if(aboveBFE > 3){
				a0_p = 0.27;
			}else if(aboveBFE > 2 & aboveBFE <= 3){
				a0_p = 0.45;
			}else if(aboveBFE > 1 & aboveBFE <= 2){
				a0_p = 0.82;
			}else if( aboveBFE > 0 & aboveBFE <= 1 ){
				a0_p = 1.15;
			}else if( aboveBFE > -1 & aboveBFE <= 0){
				a0_p = 3.4;
			}else{
				a0_p = 3.43;
			}
			
		}
		paras[0] = a0_p * 1;
		paras[1] = b0_c;	
		return paras;
	}
	
	
	public static double[] insuranceCostf( double Value, String SFHA, double elevation, 
			double eleHeight, double BFE, int PreFRIM, int type, 
			double increment, double crs, int scenario, int cancelIns){
		double discounts = 1.0;
		double communityDis = 1;
		if(crs > 0 && crs <= 0.5){
			communityDis = 1 - crs;
		}
		int Azone = SFHA.indexOf('A');
		int Vzone = SFHA.indexOf('V');
		String zoneAV ;
		if(Azone >= 0 ){
			zoneAV = "A";
		}else if( Vzone >= 0 ){
			zoneAV = "V";
		}else{
			zoneAV = "NA";
		}
		double[] InsCosts = {0,0, 0,0, 0,0, 0,0, 0,0, 0,0, 0.0, 0.0, 0.0};
		double new_elevation = elevation + eleHeight;
		double aboveBFE;
		aboveBFE = new_elevation - BFE;
		double[] paras = {0.0, 0.0};
		
		double[] paras_gf = {0.0, 0.0};
		
		double Coverage1 = 0, Coverage2 = 0, Coverage3 = 0;
		double Insurance1 = 0, Insurance2 = 0, Insurance3 = 0;
		
		double UnFirmInsurance1 = 0, UnFirmInsurance2 = 0, UnFirmInsurance3 = 0;
		
		double[] unFirmRate = parameter_zone(  aboveBFE, zoneAV, type);
		if( type <= 4){
			if( Value < 60000){
				Coverage1 = 60000;
				Coverage2 = 155000;
				Coverage3 = 250000;
				
			}else if( Value >= 60000 & Value < 250000){
				Coverage1 = 60000;
				Coverage2 = Value;
				Coverage3 = 250000;
				
			}else{
				Coverage1 = 60000;
				Coverage2 = 155000;
				Coverage3 = 250000;
			}
			
			if( scenario == 0 || scenario == 2 ){
				paras_gf = parameter_gfathering(  aboveBFE, zoneAV	, PreFRIM	);
//				unFirmRate = parameter_gfathering(  aboveBFE, zoneAV);
			}else{
				paras_gf = parameter_zone(  aboveBFE, zoneAV, type	);	
//				unFirmRate = parameter_zone(  aboveBFE, zoneAV, type, 0);
			}
		}else{
			if( Value < 175000){
				Coverage1 = 175000;
				Coverage2 = 300000;
				Coverage3 = 500000;
				
			}else if( Value >= 175000 & Value < 300000){
				Coverage1 = 175000;
				Coverage2 = Value;
				Coverage3 = 500000;
				
			}else{
				Coverage1 = 175000;
				Coverage2 = 300000;
				Coverage3 = 500000;
			}
			
			if( scenario == 0 || scenario == 2 ){
				paras_gf = parameter_gfathering(  aboveBFE, zoneAV, PreFRIM	);
//				unFirmRate = parameter_gfathering(  aboveBFE, zoneAV);
			}else{
				paras_gf = parameter_zone(  aboveBFE, zoneAV, type	);	
//				unFirmRate = parameter_zone(  aboveBFE, zoneAV, type, 0);
			}
		}
		if( type <= 4){
			if( Value < 250000){
				Coverage3 = Value;
			}else{
				Coverage3 = 250000;
			}
		}else{
			if( Value < 500000){
				Coverage3 = Value;
			}else{
				Coverage3 = 500000;
			}
		}
		double a0_p = paras_gf[0];
		double b0_c = paras_gf[1];
		if(cancelIns > 0){
			if( unFirmRate[0] >= a0_p*increment ){
				discounts = discounts*increment;
			}else{
				discounts = unFirmRate[0] / a0_p ;
			}
		}
		
		Insurance1 = a0_p * Coverage1/100 * discounts * communityDis; // + b0_c*basicCoverage/100*0.25;
		Insurance2 = a0_p * Coverage2/100 * discounts * communityDis; // + b0_c*basicCoverage/100*0.25;
		Insurance3 = a0_p * Coverage3/100 * discounts * communityDis; // + b0_c*basicCoverage/100*0.25;
		
		UnFirmInsurance1 = unFirmRate[0] * Coverage1/100  ; // + b0_c*basicCoverage/100*0.25;
		UnFirmInsurance2 = unFirmRate[0] * Coverage2/100  ; // + b0_c*basicCoverage/100*0.25;
		UnFirmInsurance3 = unFirmRate[0] * Coverage3/100  ; // + b0_c*basicCoverage/100*0.25;
		
		
		InsCosts[0] = Coverage1;
		InsCosts[1] = Coverage2;
		InsCosts[2] = Coverage3;
		InsCosts[3] = Insurance1;
		InsCosts[4] = Insurance2;
		InsCosts[5] = Insurance3;
		if( unFirmRate[0] >= a0_p*increment ){
			InsCosts[6] = UnFirmInsurance1 - a0_p * Coverage1/100 * discounts;
			InsCosts[7] = UnFirmInsurance2 - a0_p * Coverage2/100 * discounts;	
			InsCosts[8] = UnFirmInsurance3 - a0_p * Coverage3/100 * discounts;	
		}else{
			InsCosts[6] = 0.0;
			InsCosts[7] = 0.0;	
			InsCosts[8] = 0.0;	
		}
		return InsCosts;
	}
	
	
	public static double[] Determine_Insurance_rates( double aboveBFE, String zone, int Ptype){		
		double[] paras = {0, 0};
		double a0_p, b0_c=0;
		
		if( Ptype <= 4 ){
			if( zone.equals("A")  ){
				if(aboveBFE >= 4){
					a0_p = 0.3;
				}else if(aboveBFE >= 3 & aboveBFE < 4){
					a0_p = 0.34;
				}else if(aboveBFE >= 2 & aboveBFE < 3){
					a0_p = 0.49;
				}else if(aboveBFE >= 1 & aboveBFE < 2){
					a0_p = 0.92;
				}else if( aboveBFE >= 0 & aboveBFE < 1 ){
					a0_p = 2.13; 
				}else if( aboveBFE >= -1 & aboveBFE < 0){
					a0_p = 4.92;
				}else if( aboveBFE >= -2 & aboveBFE < -1){
					a0_p = 5.18;
				}else if( aboveBFE >= -3 & aboveBFE < -2){
					a0_p = 9.52;
				}else if( aboveBFE >= -4 & aboveBFE < -3){
					a0_p = 11.52;
				}else if( aboveBFE >= -5 & aboveBFE < -4){
					a0_p = 13.05;
				}else if( aboveBFE >= -6 & aboveBFE < -5){
					a0_p = 13.44;
				}else if( aboveBFE >= -7 & aboveBFE < -6){
					a0_p = 13.84;
				}else if( aboveBFE >= -8 & aboveBFE < -7){
					a0_p = 13.44;
				}else if( aboveBFE >= -9 & aboveBFE < -8){
					a0_p = 14.12;
				}else if( aboveBFE >= -10 & aboveBFE < -9){
					a0_p = 14.14;
				}else if( aboveBFE >= -11 & aboveBFE < -10){
					a0_p = 14.15;
				}else if( aboveBFE >= -12 & aboveBFE < -11){
					a0_p = 14.16;
				}else if( aboveBFE >= -13 & aboveBFE < -12){
					a0_p = 14.17;
				}else if( aboveBFE >= -14 & aboveBFE < -13){
					a0_p = 14.18;
				}else {
					a0_p = 14.23;
				}
				
//				a0_p = Math.exp( aboveBFE * -0.5832 + 0.5776 );
			}else if(zone.equals("V")){
				if(aboveBFE >= 4){
					a0_p = 0.88;
				}else if(aboveBFE >= 3 & aboveBFE < 4){
					a0_p = 0.96;
				}else if(aboveBFE >= 2 & aboveBFE < 3){
					a0_p = 1.39;
				}else if(aboveBFE >= 1 & aboveBFE < 2){
					a0_p = 1.88;
				}else if( aboveBFE >= 0 & aboveBFE < 1 ){
					a0_p = 2.44;
				}else if( aboveBFE >= -1 & aboveBFE < 0){
					a0_p = 5.18;
				}else if( aboveBFE >= -2 & aboveBFE < -1){
					a0_p = 7.64;
				}else if( aboveBFE >= -3 & aboveBFE < -2){
					a0_p = 11.52;
				}else if( aboveBFE >= -4 & aboveBFE < -3){
					a0_p = 13.05;
				}else{
					a0_p = 13.44;
				}
				
				
				
				
//				else if( aboveBFE >= -5 & aboveBFE < -4){
//					a0_p = 13.05;
//				}else if( aboveBFE >= -6 & aboveBFE < -5){
//					a0_p = 13.44;
//				}
				
			}else{
				if(aboveBFE > 4){
					a0_p = 0.3;
				}else if(aboveBFE > 3 & aboveBFE <= 4){
					a0_p = 0.31;
				}else if(aboveBFE > 2 & aboveBFE <= 3){
					a0_p = 0.49;
				}else if( aboveBFE > 1 & aboveBFE <= 2 ){
					a0_p = 0.92;
				}else if( aboveBFE > 0 & aboveBFE <= 1){
					a0_p = 1.1;
				}else{
					a0_p = 3.1;
				} 
			}
		}else{
			if(aboveBFE >= 4){
				a0_p = 0.27;
			}else if(aboveBFE >= 3 & aboveBFE < 4){
				a0_p = 0.31;
			}else if(aboveBFE >= 2 & aboveBFE < 3){
				a0_p = 0.45;
			}else if(aboveBFE >= 1 & aboveBFE < 2){
				a0_p = 0.62;
			}else if( aboveBFE >= 0 & aboveBFE < 1 ){
				a0_p = 1.15; 
			}else if( aboveBFE >= -1 & aboveBFE < 0){
				a0_p = 2.13;
			}else if( aboveBFE >= -2 & aboveBFE < -1){
				a0_p = 3.18;
			}else if( aboveBFE >= -3 & aboveBFE < -2){
				a0_p = 4.01;
			}else{
				a0_p = 5.18;
			}
			
			
			
			
//			else if( aboveBFE >= -4 & aboveBFE < -3){
//				a0_p = 10.44;
//			}else if( aboveBFE >= -5 & aboveBFE < -4){
//				a0_p = 11.98;
//			}else if( aboveBFE >= -6 & aboveBFE < -5){
//				a0_p = 12.52;
//			}else if( aboveBFE >= -7 & aboveBFE < -6){
//				a0_p = 13.00;
//			}else if( aboveBFE >= -8 & aboveBFE < -7){
//				a0_p = 13.35;
//			}else if( aboveBFE >= -9 & aboveBFE < -8){
//				a0_p = 13.41;
//			}else if( aboveBFE >= -10 & aboveBFE < -9){
//				a0_p = 13.46;
//			}else if( aboveBFE >= -11 & aboveBFE < -10){
//				a0_p = 13.51;
//			}else if( aboveBFE >= -12 & aboveBFE < -11){
//				a0_p = 13.56;
//			}else if( aboveBFE >= -13 & aboveBFE < -12){
//				a0_p = 13.70;
//			}else if( aboveBFE >= -14 & aboveBFE < -13){
//				a0_p = 13.79;
//			}else if( aboveBFE >= -15 & aboveBFE < -14){
//				a0_p = 14.10;
//			}else {
//				a0_p = 14.10;
//			}
		}
		paras[0] = a0_p * 1;
		paras[1] = b0_c;	
		return paras;
	}
	
	
	public static double[] insuranceCostf2( double Value, double will2Pay, String SFHA, double elevation, 
											double eleHeight, double BFE, int PreFRIM, int type, 
											double crs, int scenario, int cancelIns){
		double discounts = 1.0;
		double communityDis = 1;
		if(crs > 0 && crs <= 0.5){
			communityDis = 1 - crs;
		}
		int Azone = SFHA.indexOf('A');
		int Vzone = SFHA.indexOf('V');
		String zoneAV ;
		if(Azone >= 0 ){
			zoneAV = "A";
		}else if( Vzone >= 0 ){
			zoneAV = "V";
		}else{
			zoneAV = "NA";
		}
		double[] InsCosts = {0,0, 0,0, 0,0, 0.0, 0.0, 0.0};
		double new_elevation = elevation + eleHeight;
		double aboveBFE;
		aboveBFE = new_elevation - BFE;
		double[] paras = {0.0, 0.0};
		double[] paras_gf = {0.0, 0.0};
		
		double Coverage;
		double Insurance1 = 0;		
		double[] unFirmRate = parameter_zone(  aboveBFE, zoneAV, type);

		if( scenario == 0 || scenario == 2 ){
			paras_gf = parameter_gfathering(  aboveBFE, zoneAV	, PreFRIM	);
		}else{
			paras_gf = parameter_zone(  aboveBFE, zoneAV, type	);	
		}

		double a0_p = paras_gf[0];
		double b0_c = paras_gf[1];
//		if(cancelIns > 0){
//			if( unFirmRate[0] < a0_p ){
//				discounts = unFirmRate[0] / a0_p ;
//			}else{
//				discounts = a0_p / unFirmRate[0]  ;
//			}
//		}
		
		Coverage = will2Pay*100 / a0_p  / communityDis;
		if( Coverage < 60000){
			Coverage = 0 ;
			will2Pay = Coverage / 100*  a0_p ;
		}else if( Coverage > 250000){
			Coverage = 250000 ;
		}
		
		Insurance1 = will2Pay; // + b0_c*basicCoverage/100*0.25;
		
		InsCosts[0] = Coverage;
		InsCosts[1] = Insurance1;
		if( unFirmRate[0] >= a0_p ){
			InsCosts[2] = (unFirmRate[0] - a0_p) * Coverage/100;
		}else{
			InsCosts[2] = 0.0;
		}
		
		InsCosts[3] = 250000;
		InsCosts[4] = a0_p * 250000/100  * communityDis;
		if( unFirmRate[0] >= a0_p ){
			InsCosts[5] = (unFirmRate[0] - a0_p) * 250000/100;
		}else{
			InsCosts[5] = 0.0;
		}
		
		return InsCosts;
	}
	
	
	public static double[] insuranceCostf3( double Value, double cover, String SFHA, double elevation, 
			double eleHeight, double BFE, int PreFRIM, int type, 
			double increment, double crs, int scenario, int cancelIns){
		double discounts = 1.0;
		double communityDis = 1;
		if(crs > 0 && crs <= 0.5){
			communityDis = 1 - crs;
		}
		int Azone = SFHA.indexOf('A');
		int Vzone = SFHA.indexOf('V');
		String zoneAV ;
		if(Azone >= 0 ){
			zoneAV = "A";
		}else if( Vzone >= 0 ){
			zoneAV = "V";
		}else{
			zoneAV = "NA";
		}
		double[] InsCosts = { 0.0, 0.0, 0.0 };
		double new_elevation = elevation + eleHeight;
		double aboveBFE;
		aboveBFE = new_elevation - BFE;
		double[] paras = {0.0, 0.0};
		double[] paras_gf = {0.0, 0.0};
		
		double Coverage1 = cover ;
		double Insurance1 = 0 ;
		
		double UnFirmInsurance1 = 0;
		
		double[] unFirmRate = parameter_zone(  aboveBFE, zoneAV, type);
		if( type <= 4){
			if( scenario == 0 || scenario == 2 ){
				paras_gf = parameter_gfathering(  aboveBFE, zoneAV	, PreFRIM	);
//				unFirmRate = parameter_gfathering(  aboveBFE, zoneAV);
			}else{
				paras_gf = parameter_zone(  aboveBFE, zoneAV, type	);	
//				unFirmRate = parameter_zone(  aboveBFE, zoneAV, type, 0);
			}
		}else{
			if( scenario == 0 || scenario == 2 ){
				paras_gf = parameter_gfathering(  aboveBFE, zoneAV, PreFRIM	);
//				unFirmRate = parameter_gfathering(  aboveBFE, zoneAV);
			}else{
				paras_gf = parameter_zone(  aboveBFE, zoneAV, type	);	
//				unFirmRate = parameter_zone(  aboveBFE, zoneAV, type, 0);
			}
		}
		
		double a0_p = paras_gf[0];
		double b0_c = paras_gf[1];
//		if(cancelIns > 0){
//			if( unFirmRate[0] >= a0_p*increment ){
//				discounts = discounts*increment;
//			}else{
//				discounts = unFirmRate[0] / a0_p ;
//			}
//		}
		
		Insurance1 = a0_p * Coverage1/100 * discounts * communityDis; // + b0_c*basicCoverage/100*0.25;
		
		
		UnFirmInsurance1 = unFirmRate[0] * Coverage1/100  ; // + b0_c*basicCoverage/100*0.25;
		
		
		
		InsCosts[0] = Insurance1;
		InsCosts[1] = Coverage1;
		if( unFirmRate[0] >= a0_p*increment ){
			InsCosts[2] = UnFirmInsurance1 - a0_p * Coverage1/100 * discounts;
		}else{
			InsCosts[2] = 0.0;
		}
		return InsCosts;
	}
	
}
