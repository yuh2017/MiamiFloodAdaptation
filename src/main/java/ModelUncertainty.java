import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class ModelUncertainty {
	
	public static double[] getArray( double[][] totalDamages_T, int rowi ) {
		int rowN = totalDamages_T.length;
		int colN = totalDamages_T[0].length;
		
		double[] arr = new double[rowN];
		for(int i = 0; i < rowN; i ++) {
			
			for( int j = 0; j < colN; j ++) {
				if(i == rowi) {
					arr[i] = totalDamages_T[i][j];
				}
			}
		}
		return arr;
	}
//	
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
	
//	
	public static void main(String[] args) throws IOException {
		int sim_N  = 1000;
		int simYrs = 51;
		int analysisCase = 0;
		long starttime    = System.nanoTime() ;
		long elapsedTime;
		long endtime ;		
		
		String Censuspth = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Data_processed\\Miami_NFIP_census_summary.csv" ;
		String Residpth  = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Data_processed\\MiamiParcels_MCMC.csv";	
		
//		String[] tails_str = {"1_SLR02.csv", "1_SLR05.csv", "1_SLR12.csv", "1_SLR20.csv",
//								"2_SLR02.csv", "2_SLR05.csv", "2_SLR12.csv", "2_SLR20.csv",
//								"3_SLR02.csv", "3_SLR05.csv", "3_SLR12.csv", "3_SLR20.csv",
//								"4_SLR02.csv", "4_SLR05.csv", "4_SLR12.csv", "4_SLR20.csv"};
//		double[] SLRsce    = {0.000003, 0.000033, 0.000103, 0.000183,
//								0.000003, 0.000033, 0.000103, 0.000183,
//								0.000003, 0.000033, 0.000103, 0.000183,
//								0.000003, 0.000033, 0.000103, 0.000183};
//		
//		int[] scenarios		= { 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4 };
		
		String[] tails_str  = {"1_SLR02.csv", "2_SLR02.csv", "3_SLR02.csv", "4_SLR02.csv"}; // , "5_SLR02.csv"
		double[] SLRsce     = {0.000003, 0.000003, 0.000003, 0.000003, 0.000003};
		int[] scenarios		= {0, 1, 2, 3, 4};
		double[] SWheights  = {0.0, 0.0, 2.0, 2.0, 2.0};
		
		double p_th = 0.3;
		
//		String[] tails_str = {"_SLR02.csv"};
//		double[] SLRsce    = {0.000003};
//		double[] SWheights   = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
//								2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0};
		int N_case = tails_str.length;
		double[][][] EADF_0 				= new double[N_case][sim_N][simYrs];
		double[][][] EADF_1 				= new double[N_case][sim_N][simYrs];
		double[][][] EADF_2 				= new double[N_case][sim_N][simYrs];
		double[][][] realFDamages0			= new double[N_case][sim_N][simYrs];
		double[][][] realFDamages1 			= new double[N_case][sim_N][simYrs];
		
		double[][][] EvaluatedNs 			= new double[N_case][sim_N][simYrs];
		
		double[][][] adapt_cost_tot 		= new double[N_case][sim_N][simYrs];
		
		double[][][] elevateN 				= new double[N_case][sim_N][simYrs];
		double[][][] subsidies				= new double[N_case][sim_N][simYrs];
		
		double[][][] insuerN 				= new double[N_case][sim_N][simYrs];
		double[][][] insuranceCost			= new double[N_case][sim_N][simYrs];
		double[][][] insurerProfit			= new double[N_case][sim_N][simYrs];
		
		double[][][] TotalDamageN 				= new double[N_case][sim_N][simYrs];
		double[][][] TotalAdaptN 				= new double[N_case][sim_N][simYrs];
		
		double[][][] Coverages 				= new double[N_case][sim_N][simYrs];
		double[][][] DamagedN 				= new double[N_case][sim_N][simYrs];
		double[][][] ExistingBuildingN		= new double[N_case][sim_N][simYrs];
		double[][][] requiredInsN		= new double[N_case][sim_N][simYrs];
		
		double SLRb;
		double seaWall;
		ArrayList<Properties> 	Allhouseholds 		= StartDemo.readProperty(Residpth, 0.1);
		ArrayList<Census> 		CensusList 			= StartDemo.readCensuses(Censuspth); 
		
		StartDemo.Risk_Evaluation(Allhouseholds, p_th, 8);
//		HashMap<String, ArrayList<Double> > paras 		= null; 
//		HashMap<String, ArrayList<Double> > paras2 		= null; 
		
		StartDemo.CreateHousehold(Allhouseholds, CensusList, 0, p_th); 
		StartDemo.Sort_RiskLevel(Allhouseholds);
		
		double[][] SScate = new double[sim_N][simYrs];		
		for(int i =0; i < sim_N; i++)  {
			Random rand = new Random(); 
			for(int kii =0; kii < simYrs; kii++){
				SScate[i][kii] = rand.nextDouble();
			}
		}
			
//			System.out.println("Government cost "+ governor1.SeawallCosts + " "+ governor1.utilityCost) ;
		for ( int iter = 0; iter < tails_str.length ; iter ++)	{
			String tail_i0 = "Scenarios\\Households_" +tails_str[iter] ; // 300 is 395
//			String tail_i = tails_str[iter]; // 300 is 395
			SLRb = SLRsce[iter];
			analysisCase = scenarios[iter];
			seaWall = SWheights[iter];
			Government governor1 = new Government();
			governor1.SeaWallCostEstimator(Allhouseholds, seaWall, 1);
			double[] input_doubles = { SLRb, analysisCase, 0.2, simYrs, 1, seaWall, 0.001f, sim_N, 0.02, 0.1};		
			double[][][] totalDamages_T = StartDemo.futureChange(Allhouseholds, CensusList, governor1, 
																	input_doubles, SScate, tail_i0); 
			System.out.println("Simulation case "+ iter);
			for( int xi = 0; xi < totalDamages_T.length; xi++ ){
				EADF_0[iter][xi] 		= totalDamages_T[xi][1];
				EADF_1[iter][xi] 		= totalDamages_T[xi][2];
				EADF_2[iter][xi] 		= totalDamages_T[xi][0];
				adapt_cost_tot[iter][xi]= totalDamages_T[xi][3];
//					System.out.println( Arrays.toString( totalDamages_T[3] ) );
				
				EvaluatedNs[iter][xi]   = totalDamages_T[xi][4];
				
				realFDamages1[iter][xi] = totalDamages_T[xi][5];
				realFDamages0[iter][xi] = totalDamages_T[xi][6];
				
				
				TotalDamageN[iter][xi] = totalDamages_T[xi][7];
				
				TotalAdaptN[iter][xi] = totalDamages_T[xi][8];
				
				
				elevateN[iter][xi] 		= totalDamages_T[xi][9];
				
				subsidies[iter][xi]		= totalDamages_T[xi][10];
				insuranceCost[iter][xi]	= totalDamages_T[xi][11];
				insuerN[iter][xi]		= totalDamages_T[xi][13];
				insurerProfit[iter][xi]	= totalDamages_T[xi][14];
				
				Coverages[iter][xi]		= totalDamages_T[xi][12];
				DamagedN[iter][xi]		= totalDamages_T[xi][7];
				ExistingBuildingN[iter][xi]	= totalDamages_T[xi][16];
				requiredInsN[iter][xi]	= totalDamages_T[xi][17];
				
			}
			StartDemo.CreateHousehold(Allhouseholds, CensusList, 1, p_th); 
		}
		for ( int iter = 0; iter < tails_str.length ; iter ++)	{
			String tail_i = tails_str[iter]; 
			String bidPth1 = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Results\\Scenarios\\RiskPerception"+ tail_i;
			CsvFileWriter.writeEvacuation(EADF_0[iter], bidPth1);	
			
			String bidPth3 = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Results\\Scenarios\\FloodAdaptEAD"+ tail_i;
			CsvFileWriter.writeEvacuation(EADF_1[iter], bidPth3);	
			
			String bidPth0 = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Results\\Scenarios\\AgentDamage"+ tail_i;
			CsvFileWriter.writeEvacuation(EADF_2[iter], bidPth0);
			
			
			
			String bidPth81 = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Results\\Scenarios\\TotalAdaptN"+ tail_i;
			CsvFileWriter.writeEvacuation(TotalAdaptN[iter], bidPth81);
			
			String bidPth71 = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Results\\Scenarios\\TotalDamageN"+ tail_i;
			CsvFileWriter.writeEvacuation(TotalDamageN[iter], bidPth71);
			
			
			String bidPth6 = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Results\\Scenarios\\adaptcost_tot"+ tail_i;
			CsvFileWriter.writeEvacuation(adapt_cost_tot[iter], bidPth6);
			
			
			String bidPth4 = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Results\\Scenarios\\EvaluatedN"+ tail_i;
			CsvFileWriter.writeEvacuation(EvaluatedNs[iter], bidPth4);
		
			
			String bidPth7 = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Results\\Scenarios\\FloodDamage1"+ tail_i;
			CsvFileWriter.writeEvacuation(realFDamages1[iter], bidPth7);
			
			String bidPth8 = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Results\\Scenarios\\FloodDamage0"+ tail_i;
			CsvFileWriter.writeEvacuation(realFDamages0[iter], bidPth8);
		
//			String bidPth9 = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Results\\Scenarios\\PropertyVal"+ tail_i;
//			CsvFileWriter.writeEvacuation(PropertyValue[iter], bidPth9);	
		
		
			String bidPth11 = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Results\\Scenarios\\ElevateN"+ tail_i;
			CsvFileWriter.writeEvacuation(elevateN[iter], bidPth11);
			
			String bidPth14 = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Results\\Scenarios\\InsuerN"+ tail_i;
			CsvFileWriter.writeEvacuation(insuerN[iter], bidPth14);
			
			String bidPth15 = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Results\\Scenarios\\insCost"+ tail_i;
			CsvFileWriter.writeEvacuation(insuranceCost[iter], bidPth15);
			
			String bidPth151 = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Results\\Scenarios\\InsurerProfit"+ tail_i;
			CsvFileWriter.writeEvacuation(insurerProfit[iter], bidPth151);
			
			String bidPth16 = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Results\\Scenarios\\subsidies"+ tail_i;
			CsvFileWriter.writeEvacuation(subsidies[iter], bidPth16);
			
			
			String bidPth17 = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Results\\Scenarios\\TotCoverage"+ tail_i;
			CsvFileWriter.writeEvacuation(Coverages[iter], bidPth17);
			
			String bidPth18 = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Results\\Scenarios\\ExistN"+ tail_i;
			CsvFileWriter.writeEvacuation(ExistingBuildingN[iter], bidPth18);
			
			String bidPth19 = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Results\\Scenarios\\DamagedN"+ tail_i;
			CsvFileWriter.writeEvacuation(DamagedN[iter], bidPth19);
			
			String bidPth20 = "C:\\Users\\zanwa\\OneDrive\\Desktop\\Risk_Analysis_2\\Results\\Scenarios\\requiredInsN"+ tail_i;
			CsvFileWriter.writeEvacuation(requiredInsN[iter], bidPth20);
		}
		endtime = System.nanoTime() ;
		elapsedTime = endtime - starttime;
		System.out.println("Execution time in minutes "+ elapsedTime/1000000000.0/60.0 );
		}
	}
