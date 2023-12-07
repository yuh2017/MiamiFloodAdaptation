
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class SensitivityAnalysis {
	
	public static double[][] SamplingParas( double[] Pmax, double[] Pmin, int totalN ) {
		int rowN = totalN;
		int colN = Pmax.length;
		double randPara = 0.0;
		double[][] paras = new double[rowN][colN];
		for(int i = 0; i < rowN; i ++) {
			for( int j = 0; j < colN; j ++) {
				double maxj = Pmax[j];
				double minj = Pmin[j];
				Random rd = new Random();
				randPara = minj + rd.nextDouble()* (maxj - minj);
				paras[i][j] = randPara;
			}
		}
		return paras;
	}
	
	public static double[] getArray( double[][] totalDamages_T, int colj ) {
		int rowN = totalDamages_T.length;
		int colN = totalDamages_T[0].length;
		
		double[] arr = new double[rowN];
		for(int i = 0; i < rowN; i ++) {
			
			for( int j = 0; j < colN; j ++) {
				if(j == colj) {
					arr[i] = totalDamages_T[i][j];
				}
			}
		}
		return arr;
	}
	
	public static double[] sumArrs(double [] arrOne,double [] arrTwo) {
        if(arrOne.length!=arrTwo.length) {// check if length is same
            return null;
        }
        double[] returnArray = new double[arrOne.length];
        for(int i=0;i<arrOne.length;i++) { // adding with same index
            returnArray[i]= arrOne[i]+arrTwo[i];
        }
        return returnArray;
    }
	
	public static void main(String[] args) throws IOException {
		int sim_N  = 500;
		int simYrs = 50;
		long starttime    = System.nanoTime() ;
		
		String Censuspth = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Data_processed\\Miami_NFIP_census_summary.csv" ;
		String Residpth  = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Data_processed\\MiamiParcels_MCMC.csv";	
		
//		String PropParapth  = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Data_processed\\Bay_paras2.csv";
//		String AlphaPth  	= "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Data_processed\\Bay_Alphas_Rates.csv";		
		
		
		
		String[] tails_str = {	"02case0.csv",  "02case1.csv",   "02case2.csv",  "02case3.csv",
								"02case4.csv",  "02case5.csv",   "02case6.csv",  "02case7.csv",
								"02case8.csv",  "02case9.csv",  
								
								"02case10.csv", "02case11.csv", "02case12.csv",  "02case13.csv", 
								"02case14.csv", "02case15.csv", "02case16.csv",  "02case17.csv", 
								"02case18.csv", "02case19.csv", "02case20.csv",  
								
								"02case21.csv", "02case22.csv", "02case23.csv",  "02case24.csv", 
								"02case25.csv", "02case26.csv", "02case27.csv",  "02case28.csv", 
								"02case29.csv", "02case30.csv", 
								
								"02case31.csv", "02case32.csv", "02case33.csv", "02case34.csv", 
								"02case35.csv", "02case36.csv", "02case37.csv", "02case38.csv", 
								"02case39.csv", "02case40.csv"
								
								}; // "_SLR05", "_SLR12", "_SLR20"
		
		int[] scenario_labels 	= { 0, 0, 0, 0, 0, 
									0, 0, 0, 0, 0, 
									
									0, 0, 0, 0, 0, 0, 
									0, 0, 0, 0, 0,
									
									2, 2, 2, 2, 2, 
									2, 2, 2, 2, 2,
									
									0, 0, 0, 0, 0,
									0, 0, 0, 0, 0
									
									
								} ;
//		
		double[] SLRsce   = {  0.000003, 0.000003, 0.000003, 0.000003, 
								0.000003, 0.000003, 0.000003, 0.000003, 
								0.000003, 0.000003, 
								
								0.000003, 0.000003, 
								0.000003, 0.000003, 0.000003, 0.000003, 
								0.000003, 0.000003, 0.000003, 0.000003, 0.000003,
								
								0.000003, 0.000003, 0.000003, 0.000003, 0.000003,
								0.000003, 0.000003, 0.000003, 0.000003, 0.000003,
								
								0.000003, 0.000003, 0.000003, 0.000003, 0.000003, 
								0.000003, 0.000003, 0.000003, 0.000003, 0.000003
							};
		
		double[] weights    = { 0.1, 0.1, 0.1, 0.1, 0.1,
								0.1, 0.1, 0.1, 0.1, 0.1,
								
								0.0, 0.1, 0.2, 0.4, 0.5, 
								0.6, 0.8, 1.0, 2.0, 4.0, 5.0,
								
								0.1, 0.1, 0.1, 0.1, 0.1,
								0.1, 0.1, 0.1, 0.1, 0.1,
								
								0.1, 0.1, 0.1, 0.1, 0.1, 
								0.1, 0.1, 0.1, 0.1, 0.1
								};
		
		
		
//		double[] NetworkRs   = {0.001f, 0.001f, 0.001f, 0.001f, 0.001f, 
//								0.001f, 0.001f, 0.001f, 0.001f, 0.001f, 
//				
//								0.001f, 0.001f, 0.001f, 0.001f, 0.001f, 
//								0.001f, 0.001f, 0.001f, 0.001f, 0.001f, 0.001f, 
//				
//								0.0f, 	0.0005f,  0.001f,  0.003f, 0.005f, 
//								0.008f, 0.01f,   0.02f,  0.03f, 0.05f,
//			    
//								0.001f, 0.001f, 0.001f, 0.001f, 0.001f, 
//								0.001f, 0.001f, 0.001f, 0.001f, 0.001f
//								};
		
		
		int[] NetworkLamda   = {
									4, 4, 4, 4, 4, 
									4, 4, 4, 4, 4, 
					
									4, 4, 4, 4, 4, 
									4, 4, 4, 4, 4, 4, 
									
									4, 4, 4, 4, 4, 
									4, 4, 4, 4, 4,
					
									0, 	1,  2,  4,  6, 
									8,  10, 15, 20, 25
								};
		
		
		
//		double[] NetworkRs   = {   0.005f, 0.005f, 0.005f, 0.005f, 0.005f, 
//									0.005f, 0.005f, 0.005f, 0.005f, 0.005f, 
//			    					
//									0.005f, 0.005f, 0.005f, 0.005f, 0.005f, 
//									0.005f, 0.005f, 0.005f, 0.005f, 0.005f, 0.005f, 
//			    					
//			    					 0.0f, 0.001f,  0.002f,  0.004f, 0.006f, 
//			    					0.01f,  0.02f,   0.03f,   0.06f,  0.10f,
//								    
//			    					0.005f, 0.005f, 0.005f, 0.005f, 0.005f, 0.005f, 0.005f
//								};
		
//		int[] 	memeroySize		= { 50, 50, 50, 50, 50, 50, 50, 50, 50, 50,
//				
//									50, 50, 50, 50, 50, 50,
//									50, 50, 50, 50, 50,
//									
//									50, 50, 50, 50, 50,
//									50, 50, 50, 50, 50,
//									
//									1, 5, 10, 15, 20, 25, 30, 35, 40, 50
//								};
		
		
		
		double[] 	census_percent_damage_list		= { 0.1, 0.1, 0.1, 0.1, 0.1, 
														0.1, 0.1, 0.1, 0.1, 0.1,
				
														0.1, 0.1, 0.1, 0.1, 0.1, 
														0.1, 0.1, 0.1, 0.1, 0.1, 0.1,
														
														0.0, 0.01, 0.02, 0.05, 0.08, 
														0.1, 0.2, 0.3, 0.4, 0.5,
														
														0.1, 0.1, 0.1, 0.1, 0.1, 
														0.1, 0.1, 0.1, 0.1, 0.1,
													};
		
		
		
		double[] p_ths   = { 0.0, 0.01, 0.02, 0.04, 0.06, 0.08, 
							 0.1, 0.2, 0.3, 0.4,
							 
							 0.02, 0.02, 0.02, 0.02, 0.02, 
							 0.02, 0.02, 0.02, 0.02, 0.02, 0.02,
							
							 0.02, 0.02, 0.02, 0.02, 0.02,
							 0.02, 0.02, 0.02, 0.02, 0.02,
							
							 0.02, 0.02, 0.02, 0.02, 0.02, 
							 0.02, 0.02, 0.02, 0.02, 0.02
							};
		
		
//		String[] tails_str    = { 
//									"02case31.csv", "02case32.csv", "02case33.csv", "02case34.csv", 
//									"02case35.csv", "02case36.csv", "02case37.csv",
//									
//									"02case38.csv", "02case39.csv", "02case40.csv",  "02case41.csv", 
//									"02case42.csv", "02case43.csv", "02case44.csv",  "02case45.csv",
//									"02case46.csv", "02case47.csv", "02case48.csv"
//								};
//		double[] weights      = { 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 
//									0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 
//									0.1, 0.1, 0.1, 0.1 
//								};			
//		double[] NetworkRs    = { 0.005f, 0.005f, 0.005f, 0.005f, 0.005f, 0.005f,
//									0.005f, 0.005f, 0.005f, 0.005f, 0.005f, 0.005f,
//									0.005f, 0.005f, 0.005f, 0.005f, 0.005f, 0.005f};
//		int[] 	memeroySize	  = { 1, 5, 10, 15, 20, 25, 30, 35, 40,
//									5, 5, 5, 5, 5, 5, 5, 5, 5 };
//		
//		double[] RejectRates  = { 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 
//									0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1};
//		double[] capacityP    = { 0.8, 0.8, 0.8, 0.8, 0.8, 0.8, 0.8, 0.8, 0.8, 
//									0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0 };
		
		
//		double DiscountRate  = 0.04;
		int N_case = tails_str.length;
		
		
		double[][][] EADF_1 		= new double[N_case][sim_N][simYrs];
		double[][][] realFDamages 	= new double[N_case][sim_N][simYrs];
		double[][][] adapt_cost_tot = new double[N_case][sim_N][simYrs];
		double[][][] AdaptedN 		= new double[N_case][sim_N][simYrs];
		double[][][] insuerN 		= new double[N_case][sim_N][simYrs];
		double[][][] elevateN		= new double[N_case][sim_N][simYrs];
		
		double 	seaWallH;
		double 	SLRb;
		int  	lamdai;
		double 	weighti 			= 0.0;
		double  radius 				= 0.0;
		int 	Msizei     			= 0;
		double  pth 				= 0.0;
		double  percent_damag 		= 0.0;
		ArrayList<Properties> 	Allhouseholds 			= StartDemo.readProperty(Residpth, 0.1);
		
		
		
		ArrayList<Census> 		CensusList 				= StartDemo.readCensuses(Censuspth); 
//		HashMap<String, ArrayList<Double> > paras 		= Properties.readPropertyParas(PropParapth); 
//		HashMap<String, ArrayList<Double> > paras2 		= Properties.readAlphaParas( AlphaPth );
//		HashMap<String, ArrayList<Double> > paras 		= null; 
//		HashMap<String, ArrayList<Double> > paras2 		= null; 
		StartDemo.Risk_Evaluation(Allhouseholds, 0.01, 4);
//		StartDemo.Sort_RiskLevel(Allhouseholds);
		double[][] SScate = new double[sim_N][simYrs];		
		for(int i =0; i < sim_N; i++)  {
			Random rand = new Random(); 
			for(int kii =0; kii < simYrs; kii++){
				SScate[i][kii] = rand.nextDouble();
			}
		}
			
//			System.out.println("Government cost "+ governor1.SeawallCosts + " "+ governor1.utilityCost) ;
		for ( int iter = 0; iter < tails_str.length ; iter ++)	{
			String tail_i0 = "Sensitivity1\\Households_" +tails_str[iter] ; // 300 is 395
						
			int analysisi  	= scenario_labels[iter] ;	
			SLRb 	  		= SLRsce[iter];
			weighti   		= weights[iter];
			Msizei    		=   1  ; /** memeroySize[iter];  **/
			lamdai 			= NetworkLamda[iter];
			percent_damag 	= census_percent_damage_list[iter];
			
			pth 			= p_ths[iter];
//			radius  = (float) NetworkRs[iter];
			if( analysisi == 0 ){
				seaWallH = 0.0;
			}else if( analysisi == 1 ){
				seaWallH = 0.0;
			}else if( analysisi == 2 ){
				seaWallH = 2.0;
			}else if( analysisi == 3 ){
				seaWallH = 2.0;
			}else{
				seaWallH = 2.0;
			}
			if( iter > 30 ){
				StartDemo.Risk_Evaluation(Allhouseholds, pth, lamdai);
			}
			
			StartDemo.CreateHousehold(Allhouseholds, CensusList, 0, pth); 
			
			Government governor1 = new Government();
			governor1.SeaWallCostEstimator(Allhouseholds, seaWallH, 0);
			
			
			double[] input_doubles = { SLRb, analysisi, weighti, simYrs, Msizei, seaWallH, radius, sim_N, pth, percent_damag};
			double[][][] totalDamages_T = StartDemo.futureChange(Allhouseholds, CensusList, governor1, 
																input_doubles, SScate, tail_i0); 
			
			
			
//			System.out.println(  totalDamages_T.length + " "+ totalDamages_T[0].length + " "+ totalDamages_T[0][0].length); 
			for( int xi = 0; xi < totalDamages_T.length; xi++ ){
				EADF_1[iter][xi]			= totalDamages_T[xi][2];
				adapt_cost_tot[iter][xi] 	= totalDamages_T[xi][3];
				realFDamages[iter][xi]   	= totalDamages_T[xi][5];
				AdaptedN[iter][xi]			= totalDamages_T[xi][8];
				elevateN[iter][xi] 			= totalDamages_T[xi][9];
				insuerN[iter][xi]			= totalDamages_T[xi][13];
			}
			System.out.println("Simulation scenario "+ tails_str[iter] );
			StartDemo.CreateHousehold(Allhouseholds, CensusList, 1, pth); 
		}
			
		for ( int iter = 0; iter < tails_str.length ; iter ++)	{
			String tail_i1 = tails_str[iter]; 
			
			
//			String bidPth2 = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Results\\Sensitivity1\\FloodEAD"+ tail_i;
//			CsvFileWriter.writeEvacuation(EADF_0[iter], bidPth2);
			
			String bidPth3 = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Results\\Sensitivity1\\FloodAdaptEAD"+ tail_i1;
			CsvFileWriter.writeEvacuation(EADF_1[iter], bidPth3);	
			
//			String bidPth0 = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Results\\Sensitivity1\\FloodMitigateEAD"+ tail_i;
//			CsvFileWriter.writeEvacuation(EADF_2[iter], bidPth0);
			
			String bidPth6 = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Results\\Sensitivity1\\adaptcost_tot"+ tail_i1;
			CsvFileWriter.writeEvacuation(adapt_cost_tot[iter], bidPth6);
			
//			String bidPth65 = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Results\\Sensitivity1\\FloodDamage0"+ tail_i;
//			CsvFileWriter.writeEvacuation(realFDamages0[iter], bidPth65);
			
			String bidPth7 = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Results\\Sensitivity1\\FloodDamage"+ tail_i1;
			CsvFileWriter.writeEvacuation(realFDamages[iter], bidPth7);
			
//			String bidPth9 = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Results\\Sensitivity1\\Subsidy"+ tail_i;
//			CsvFileWriter.writeEvacuation(subsidies[iter], bidPth9);
			
			String bidPth10 = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Results\\Sensitivity1\\TotAdaptN"+ tail_i1;
			CsvFileWriter.writeEvacuation(AdaptedN[iter], bidPth10);	
			
			String bidPth11 = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Results\\Sensitivity1\\ElevateN"+ tail_i1;
			CsvFileWriter.writeEvacuation(elevateN[iter], bidPth11);
			
//			String bidPth12 = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Results\\Sensitivity1\\WetproofN"+ tail_i;
//			CsvFileWriter.writeEvacuation(wetProofN[iter], bidPth12);
//			
//			String bidPth13 = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Results\\Sensitivity1\\DryproofN"+ tail_i;
//			CsvFileWriter.writeEvacuation(dryProofN[iter], bidPth13);
			
			String bidPth14 = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Results\\Sensitivity1\\InsuerN"+ tail_i1;
			CsvFileWriter.writeEvacuation(insuerN[iter], bidPth14);
			
//			String bidPth15 = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Results\\Sensitivity1\\InsuerCost"+ tail_i;
//			CsvFileWriter.writeEvacuation(insuranceCost[iter], bidPth15);
//
//			String bidPth16 = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Results\\Sensitivity1\\PropVal"+ tail_i;
//			CsvFileWriter.writeEvacuation(PropertyValue[iter], bidPth16);
//			
//			String bidPth17 = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Results\\Sensitivity1\\Compensation"+ tail_i;
//			CsvFileWriter.writeEvacuation(compensation[iter], bidPth17);
		}
		long endtime = System.nanoTime() ;
		long elapsedTime = endtime - starttime;
		System.out.println("Execution time in minutes "+ elapsedTime/1000000000.0/60.0 );
		}
}
