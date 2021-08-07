import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Mainclass {

public static void main(String[] args) throws Exception {		
	
	int nth = (int) Float.parseFloat( args[0] ); // n thread
	int sensiLabel = (int) Float.parseFloat( args[1] ); // if sensitivity analysis   
	double percentData = Double.parseDouble( args[2] ); // percentage data chosen   
	int AzoneLabel = (int) Double.parseDouble( args[3] ); // if chosen A zone buildings  
	int ReplicateN = (int) Double.parseDouble( args[4] ); // total simulation times
	    
	String input  = args[5] ; //input directory path
	String output = args[6];  //output directory path

	System.out.println("Input arguments " + args[6]  );
		
	
//	String input  = "MiamiFloodAdaptation/src/main/resources" ;
//	String output = "MiamiFloodAdaptation/src/main/resources/results_MonteCarlo";
		
		
	String file1 = input;
	String file2 = output;
		
        File datafile1 = new File(file1, "MiamiParcels2.csv" ) ;
        String Residpth = datafile1.getPath()  ;  		
        System.out.println( Residpth ); 
        
        File datafile2 = new File(file1, "Miami_paras.csv" ) ;
        String AlphaPth = datafile2.getPath();          
        System.out.println( AlphaPth ); 
        
        File datafile3 = new File(file1, "Miami_NFIP_census_summary.csv" ) ;
        String simHHpth = datafile3.getPath();          
        System.out.println( simHHpth ); 
        
        int simYrs 			= 100;
	int deltaT 			= 10;
	double DiscountRate = 0.04;	
	int TotalN 			= ReplicateN;
	ArrayList<Properties> residential 	= InitClass.readProperty(Residpth, percentData, AzoneLabel);
		
	int nthread = nth;
	int ifSensitivity	= sensiLabel;
	long elapsedTime;
	long starttime ;
	long endtime ; 
        double[][] Return_periods 	= new double[TotalN][simYrs];
		for( int i = 0; i < TotalN; i++ ){
			Random rand = new Random(); 
			for(int kii =0; kii < simYrs; kii++){
				Return_periods[i][kii] = rand.nextDouble();
			}
		}
		
	double[][] gev_paras 	= new double[TotalN][simYrs*3];
	for( int i1 = 0; i1 < TotalN; i1++ ){
		for( int i2 = 0; i2 < simYrs; i2++ ){
			Random rand = new Random(); 
			gev_paras[i1][i2*3]  	= rand.nextGaussian() * 0.125   + 0.600 ;
			gev_paras[i1][i2*3+1]   = rand.nextGaussian() * 0.176   + 0.9672 ;
			gev_paras[i1][i2*3+2]   = rand.nextGaussian() * 0.181   + 0.140 ;
		}
	}
		
		
	int totalPropertyN = residential.size();
	int chunksize = (int) Math.ceil( totalPropertyN / nthread );
	System.out.println("Number of thread "+ nthread +" and chunksize "+  chunksize);	
		
		
	/**Sea-level rise Scenarios and parameters
	 * 	0.2: 0.000003   
	 * 	0.5: 0.000033
	 *  	1.2: 0.000103   
	 *  	2  : 0.000183
	**/
	int endSize = chunksize;
	starttime = System.nanoTime() ;
	HashMap<Integer, ArrayList<Double> > paras2 = Properties.readAlphaParas( AlphaPth );
	ArrayList<Properties> resultArrayList = new ArrayList<Properties>();
		
	int outputN = 10;
	double[][][] ResultInfo = new double[outputN][TotalN][simYrs] ;
//	String[] tails_str 	= { "_SLR02", "_SLR05", "_SLR12", "_SLR20"}; // "_SLR05", "_SLR12", "_SLR20"
	/** Change SLR scenario for sensitivity analysis**/
	String[] tailis_adaptsce  	= 	{ "1", "2", "3", "4"};
	String[] tailis_slr  		= 	{ "_SLR02.csv", "_SLR05.csv", "_SLR12.csv", "_SLR20.csv" };
//	double[] slrsces   		=  {0.000003, 0.000033, 0.000103, 0.000183};
			
	/** Change SLR scenario for sensitivity analysis**/		
	for(  int atgb = 0; atgb < tailis_slr.length; atgb++ ) {
		String tail0i  	= 	tailis_slr[atgb];	
		for( int k0 = 0; k0 < tailis_adaptsce.length ; k0++ ){
			String taili  	 = 	tailis_adaptsce[ k0 ] + "_"+ String.valueOf(ifSensitivity) + tail0i ;
			String hdatafile = file2 + "/BidParcels" + taili ;			
			CsvFileWriter.writeBidPrice(residential, hdatafile, false);	
			String hbidPth0  = file2 + "/Uncertainty_CB/PropertyValue"+ taili;
			CsvFileWriter.writeEvacuation(ResultInfo[0], hbidPth0, false);	
			String hbidPth1  = file2 + "/Uncertainty_CB/EAD0"+ taili;
			CsvFileWriter.writeEvacuation(ResultInfo[1], hbidPth1, false);
			
			String hbidPth2  = file2 + "/Uncertainty_CB/EAD1"+ taili;
			CsvFileWriter.writeEvacuation(ResultInfo[2], hbidPth2, false);
			String hbidPth3  = file2 + "/Uncertainty_CB/FloodDamage0"+ taili;
			CsvFileWriter.writeEvacuation(ResultInfo[3], hbidPth3, false);
			String hbidPth4  = file2 + "/Uncertainty_CB/FloodDamage1"+ taili;
			CsvFileWriter.writeEvacuation(ResultInfo[4], hbidPth4, false);	
			String hbidPth5  = file2 + "/Uncertainty_CB/AdaptationPay"+ taili;
			CsvFileWriter.writeEvacuation(ResultInfo[5], hbidPth5, false);	
				
			String hcsvFile6 = file2 + "/Uncertainty_CB/Households_" + taili;
			CsvFileWriter.writeBidPrice2(residential, hcsvFile6, false) ;	
			String hbidPth62 = file2 + "/Uncertainty_CB/DiscountRate"+ taili;
			CsvFileWriter.writeEvacuation(ResultInfo[6], hbidPth62, false);	
			String hbidPth7 = file2 + "/Uncertainty_CB/ElevationPay"+ taili;
			CsvFileWriter.writeEvacuation(ResultInfo[7], hbidPth7, false);	
			String hbidPth8 = file2 + "/Uncertainty_CB/DryproofPay"+ taili;
			CsvFileWriter.writeEvacuation(ResultInfo[8], hbidPth8, false);	
			String hbidPth9 = file2 + "/Uncertainty_CB/WetproofPay"+ taili;
			CsvFileWriter.writeEvacuation(ResultInfo[9], hbidPth9, false);	
			
			int countSize = 0;
	//			ExecutorService executor = Executors.newCachedThreadPool();
			ExecutorService executor = Executors.newFixedThreadPool(nthread);
	//			List<Future<double[][][][]>> futures = new ArrayList<>();
			
			
			CopyOnWriteArrayList< Future<double[][][]> > futures2  = new CopyOnWriteArrayList<>();
			
			for(int threadi = 0; threadi< nthread; threadi++) {
				if( countSize < totalPropertyN) {
					endSize = countSize+chunksize;
				}else {
					endSize = totalPropertyN;
				}
				ArrayList<Properties> residential_subset = new ArrayList<Properties>( residential.subList(countSize, countSize+chunksize) );
				InitClass.CreateHousehold(residential_subset, paras2, 0);
				System.out.println("Adaptation Scenario"+ (k0+1)  +" is starting the thread " + threadi );	
				
				double[] para1 = {deltaT, atgb, outputN, simYrs, TotalN, DiscountRate, threadi, ifSensitivity, k0};
				String[] para2 = {file2, AlphaPth};
					
				SimulationMultiThread taski = new SimulationMultiThread( residential_subset, Return_periods, gev_paras, para1, para2);
				Future<double[][][]> result = executor.submit(taski);     
				futures2.add(result);
		        
	//	        while (!result.isDone()) {
	//	            System.out.println(
	//	              String.format(  "future %s is %s ",  threadi, result.isDone() ? "done" : "not done" )
	//	            );
	//	            Thread.sleep(300);
	//	        }
				countSize = countSize + chunksize;
			}
			executor.shutdown();
			try {
				executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			} catch (InterruptedException e) {
				System.out.println("InterruptedException Error!");
			}
			System.out.println("all task finished!");
			
			double[][][] ResultInfo2 = new double[outputN][TotalN][simYrs] ;
			
			Iterator< Future<double[][][]> > iterator0 = futures2.iterator();
			Lock lock = new ReentrantLock();
			while( iterator0.hasNext() ) {
				Future<double[][][]> f = iterator0.next();
				try {
					while( true ) {
						if (f.isDone() && !f.isCancelled()) {
	//					System.out.println("Future get" + f.get() == null );
						double[][][] resultsi =  f.get();
						for( int row1 = 0; row1 < outputN; row1++ ) {
							for( int row2 = 0; row2 < TotalN; row2++ ) {
								for( int row3 = 0; row3 < simYrs; row3++ ) {	
										ResultInfo2[row1][row2][row3] = 
													 ResultInfo2[row1][row2][row3] + resultsi[row1][row2][row3] ;
								}
							}
						}
						break;
						}
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
		    	}
	
			String obidPth0 = file2 + "/Uncertainty_CB/PropertyValue"+ taili;
			CsvFileWriter.writeEvacuation(ResultInfo2[0], obidPth0, true);	
			String obidPth1  = file2 + "/Uncertainty_CB/EAD0"+ taili;
			CsvFileWriter.writeEvacuation(ResultInfo2[1], obidPth1, true);
			String obidPth2  = file2 + "/Uncertainty_CB/EAD1"+ taili;
			CsvFileWriter.writeEvacuation(ResultInfo2[2], obidPth2, true);
			
			String obidPth3 = file2 + "/Uncertainty_CB/FloodDamage0"+ taili;
			CsvFileWriter.writeEvacuation(ResultInfo2[3], obidPth3, true);
			String obidPth4 = file2 + "/Uncertainty_CB/FloodDamage1"+ taili;
			CsvFileWriter.writeEvacuation(ResultInfo2[4], obidPth4, true);	
			String obidPth5 = file2 + "/Uncertainty_CB/AdaptationPay"+ taili;
			CsvFileWriter.writeEvacuation(ResultInfo2[5], obidPth5, true);	
				
			String obidPth6 = file2 + "/Uncertainty_CB/DiscountRate"+ taili;
			CsvFileWriter.writeEvacuation(ResultInfo2[6], obidPth6, true);	
			String obidPth7 = file2 + "/Uncertainty_CB/ElevationPay"+ taili;
			CsvFileWriter.writeEvacuation(ResultInfo2[7], obidPth7, true);	
			String obidPth8 = file2 + "/Uncertainty_CB/DryproofPay"+ taili;
			CsvFileWriter.writeEvacuation(ResultInfo2[8], obidPth8, true);	
			String obidPth9 = file2 + "/Uncertainty_CB/WetproofPay"+ taili;
			CsvFileWriter.writeEvacuation(ResultInfo2[9], obidPth9, true);	
				
			//System.out.println("Finish futureChange2" );
			/* write adaptation file */
			String datafile  		= file2 + "/BidParcels" + taili ;			
		//	System.out.println( outputFile ); 
			
			/* write building file */
			CsvFileWriter.writeBidPrice( residential, datafile, true);	
		//	System.out.println("Finish adaptation planning" );
			String ocsvFile6 		= file2 + "/Uncertainty_CB/Households_" + taili;
			CsvFileWriter.writeBidPrice2( residential, ocsvFile6, true)  ;
			endtime = System.nanoTime() ;
			elapsedTime = endtime - starttime;
			System.out.println("Total Execution time in minutes "+ elapsedTime/1000000000.0/60.0 );
			System.out.println("Finish futureChange2" );
			
			}
		}
	}
}
