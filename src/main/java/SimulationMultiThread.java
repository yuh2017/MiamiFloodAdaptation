import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;

public class SimulationMultiThread implements Callable< double[][][] > {
	private ArrayList<Properties> residential;
	private String outputFile;
	private int simYrs;
	private int threadk;
	private double DiscountRate;
	private int k0 ;
	private int ifSensitivity ;
	private int slri;
	private int outputN;
	private int deltaT 			= 1;
	private int TotalN 			;
	private double[][] Return_periods 	;
	private double[][][] ResultInfo ;
	
	double[][] gev_paras ;
	
	HashMap<Integer, ArrayList<Double> > paras2; 	
	public SimulationMultiThread(ArrayList<Properties> residentiali, double[][] Return_periods, double[][] gev_paras, double[] paras, String[] paras2) {
		this.residential 	= (ArrayList<Properties>) residentiali;
		this.outputFile 	= paras2[0];
		this.deltaT  		= (int) paras[0] ;
		this.Return_periods = Return_periods;
		this.gev_paras		= gev_paras ;
		this.slri			= (int) paras[1];
		this.outputN		= (int) paras[2];
		this.simYrs 		= (int) paras[3];
		this.TotalN  		= (int) paras[4] ;
		this.DiscountRate 	= paras[5];
		this.threadk 		= (int) paras[6];
		this.ifSensitivity  = (int) paras[7];
		this.k0  			= (int) paras[8];
		this.ResultInfo 	= new double[outputN][TotalN][simYrs ] ; ;
		this.paras2 		= Properties.readAlphaParas( paras2[1] );
	}
	
	public double[][][] call() throws Exception {
//        System.out.println("thread "+ threadk+" is executing");
//        Thread.sleep(3000);        
		/** Change SLR scenario for sensitivity analysis**/
        String[] tailis_adaptsce  	= 	{ "1", "2", "3", "4"};
		String[] tailis_slr  		= 	{ "_SLR02.csv", "_SLR05.csv", "_SLR12.csv", "_SLR20.csv" };
		double[] slrsces   			=   {0.000003, 0.000033, 0.000103, 0.000183};
		/** Change SLR scenario for sensitivity analysis**/
		int[] scenarios   			=   {1, 2, 3, 4};
		int scei ;
		long starttime ;
		long elapsedTime, endtime ; 
		starttime = System.nanoTime() ;
		String tail0i  = 	tailis_slr[ slri ] ;
		double SLRb    =    slrsces[ slri ] ;
//		Government gov1 = new Government();
	    scei = scenarios[ k0 ] ;
	    String taili  	        = 	tailis_adaptsce[ k0 ] + tail0i ;
//	    double[] parayi = { simYrs, DiscountRate, SLRb, deltaT, 0.391, 0.140, 0.105  } ;
//			if( scei == 2  ) {
//				InitClass.AdaptDecision2(residential, parayi, gov1) ;
//			}else if( scei == 3 ) {
//				InitClass.AdaptDecision3(residential, parayi, gov1) ;
//			}else if( scei == 4 ) {
//				InitClass.AdaptDecision4(residential, parayi, gov1) ;
//			}else {
//				InitClass.AdaptDecision( residential, parayi, gov1) ;
//			}
			/* write adaptation file */
//			System.out.println("Thread " + threadk +" Adaptation execution time in minutes "+ elapsedTime/1000000000.0/60.0 );	
//			starttime = System.nanoTime() ;
		double[] input_doubles 	= { SLRb, DiscountRate, 49, simYrs, TotalN, scei, ifSensitivity, deltaT};
		ResultInfo 			= InitClass.futureChange2( residential, Return_periods, gev_paras, paras2, input_doubles, taili); 
			
//			System.out.println( "Test resultInfo " + ResultInfo[k0][2][2][2] ); 
//			String datafile  		= outputFile + "/BidParcels" + taili ;			
////			System.out.println( outputFile ); 
//			/* write adaptation file */
//			CsvFileWriter.writeBidPrice( residential, datafile, true);	
////			System.out.println("Finish adaptation planning" );
//			
//			String ocsvFile6 		= outputFile + "/Uncertainty_CB/Households_" + taili;
//			CsvFileWriter.writeBidPrice2( residential, ocsvFile6, true) 
		endtime 				= System.nanoTime() ;
		elapsedTime 			= endtime - starttime;
		System.out.println("Thread " + threadk +" Execution time in minutes "+ elapsedTime/1000000000.0/60.0 );
		return ResultInfo;
	}
}