import java.io.*; 
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class CsvFileWriter {
	private static String fileName = "C:\\Users\\yuh46\\Desktop\\MiamiDade\\simResults\\BidPrices.csv";
	private static final String FILE_HEADER = "RFID,HHiid,bidPrice,JV,CensusID,Income,latitude,longitude";
//	private static String fileName2 = "C:\\Users\\yuh46\\Desktop\\MiamiDade\\simResults\\floodRisk2.csv";
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	
	public static void writeCsvFile(ArrayList<BidPrice> BidHouseholds) {
		FileWriter fileWriter = null;
			try{
				fileWriter = new FileWriter(fileName);
				fileWriter.append(FILE_HEADER.toString());
				fileWriter.append(NEW_LINE_SEPARATOR);
				
				for (BidPrice housei : BidHouseholds) {
					fileWriter.append( String.valueOf(housei.RFID) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.bidHHiid) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.maxbid) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.JV) );
					fileWriter.append(COMMA_DELIMITER);
				
					fileWriter.append( String.valueOf(housei.CensusID) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.bidIncome) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.latitude) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.longitude) );
					fileWriter.append(NEW_LINE_SEPARATOR);
				}
				System.out.println("BidPrices.CSV file was created successfully !!!");
			}catch (Exception e) {
				System.out.println("Error in CsvFileWriter !!!");
				e.printStackTrace();
			}finally {
				try {
					fileWriter.flush();
					fileWriter.close();
				} catch (IOException e) {
					System.out.println("Error while flushing/closing fileWriter !!!");
					e.printStackTrace();
					}
				}
			}
	public static void writeFloodRisk(Map <Integer, ArrayList<Double>> HouseRisk, String fileName2) {
		FileWriter fileWriter = null;
		String FILE_HEADER2 =  	"RFID,BeforeRisk,PostRisk,willPay,ICost,"
								+ "InsurCost,PremiumCost,eleHeight,elevationCost,"
								+ "bidPrice,Justvalue,bidIncome,Area,UtilityCost,AdaptCost,CBratio,IncomeP,latitude,longitude";
		try{
			fileWriter = new FileWriter(fileName2);
			fileWriter.append(FILE_HEADER2.toString());
			fileWriter.append(NEW_LINE_SEPARATOR);
			
			for (Integer rid : HouseRisk.keySet())  {
				
				ArrayList<Double> HouseProperty = HouseRisk.get( rid );
				fileWriter.append( String.valueOf(rid) );
				fileWriter.append( COMMA_DELIMITER );
				for (int i = 0; i < HouseProperty.size() - 1 ; i++) {
					fileWriter.append( String.valueOf(HouseProperty.get(i)) );
					fileWriter.append( COMMA_DELIMITER);
				}
				fileWriter.append( String.valueOf(HouseProperty.get(HouseProperty.size() - 1)) );
				fileWriter.append( NEW_LINE_SEPARATOR);
			}
			System.out.println("floodrisk.CSV file was created successfully !!!");
			}catch (Exception e) {
				System.out.println("Error in flood risk CsvFileWriter !!!");
				e.printStackTrace();
				}finally {
					try {
						fileWriter.flush();
						fileWriter.close();
				} catch (IOException e) {
					System.out.println("Error while flushing/closing flood risk fileWriter !!!");
					e.printStackTrace();
				}
			}
	}
	public static void writeBidPrice(ArrayList<Properties> BidParcels, String filepath, boolean append) {
		String FILE_HEADER3 =  	"FRID,HouseType,biderID,bidderAge,"
								+ "bidIncome,payratio,bidderrace,bidderCar,"
								+ "LArea,SArea,EFFYER,bidPrice,Justvalue,"
								+ "LADVAL,NORESUNTS,FLD_ZONE,"
								+ "cate1,cate2,cate3,cate4,cate5,BFE,elevation,"
								+ "floodRisk,FloodPay,AdaptFloodRisk,CBratio,"
								+ "eleheight,"
								+ "eleCost2,eleCost4,eleCost6,eleCost8,wetCost2,wetCost4,"
								+ "wetCost6,dryCost2,dryCost4,dryCost6,"
								+ "adaptYear,adoptMeasure,adaptH,adaptCost,latitude,longitude";
		FileWriter fileWriter = null;
		ReadWriteLock lock = new ReentrantReadWriteLock();
		Lock writeLock2 = lock.writeLock();
				
		try{
			writeLock2.lock();
			fileWriter = new FileWriter(filepath, append);
			if( append == false) {
				fileWriter.append(FILE_HEADER3.toString());
			}else {
				for (Properties housei : BidParcels) {
					fileWriter.append(NEW_LINE_SEPARATOR);
					fileWriter.append( String.valueOf(housei.FID) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.Ptype) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.biderid) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.bidderage) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.bidIncome) );
					fileWriter.append(COMMA_DELIMITER);
					
					fileWriter.append( String.valueOf(housei.payratio) );
					fileWriter.append(COMMA_DELIMITER);
					
					fileWriter.append( String.valueOf(housei.bidrace) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.bidVehicle) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.liveArea) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.Area) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.EFFYER) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.bidPrice) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.Justvalue) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.LV) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.NORESUNTS) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.zone) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.cate1) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.cate2) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.cate3) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.cate4) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.cate5) );
					fileWriter.append(COMMA_DELIMITER);
					
					fileWriter.append( String.valueOf(housei.BFE) );
					fileWriter.append(COMMA_DELIMITER);
					
					fileWriter.append( String.valueOf(housei.elevation) );
					fileWriter.append(COMMA_DELIMITER);
					
					fileWriter.append( String.valueOf(housei.FloodRisk0) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.FloodAdaptPay) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.FloodRisk) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.CBratio) );
					fileWriter.append(COMMA_DELIMITER);
					

					fileWriter.append( String.valueOf(housei.eleHeight) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.elevationCost2) );
					fileWriter.append(COMMA_DELIMITER);
					
					fileWriter.append( String.valueOf(housei.elevationCost4) );
					fileWriter.append(COMMA_DELIMITER);
					
					fileWriter.append( String.valueOf(housei.elevationCost6) );
					fileWriter.append(COMMA_DELIMITER);
					
					fileWriter.append( String.valueOf(housei.elevationCost8) );
					fileWriter.append(COMMA_DELIMITER);
					
					fileWriter.append( String.valueOf(housei.wetproofCost2) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.wetproofCost4) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.wetproofCost6) );
					fileWriter.append(COMMA_DELIMITER);
					
					
					fileWriter.append( String.valueOf(housei.dryproofCost2) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.dryproofCost4) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.dryproofCost6) );
					fileWriter.append(COMMA_DELIMITER);
					
					
					fileWriter.append( String.valueOf(housei.adaptYear) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.FloodMeasure) );
					fileWriter.append(COMMA_DELIMITER);
					
					fileWriter.append( String.valueOf(housei.adaptH) );
					fileWriter.append(COMMA_DELIMITER);
					
					fileWriter.append( String.valueOf(housei.AdaptationCost) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.latitude) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.longitude) );
				}
//				System.out.println("writeBidPrice.CSV file1 was created successfully !!!");
			}
			
		}catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		}finally {
			writeLock2.unlock();
		}
		
		
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}
		}

	
	public static void writeBidPrice2(ArrayList<Properties> BidParcels, String filepath, boolean append) {
		String FILE_HEADER3 =  	"FID,HouseType,BidIncome,payratio,"
								+ "CurrentValue,JustValue,FZone,elevation,BFE,"
								+ "FloodMeasure,"
								+ "FloodaDamage,AdaptFloodDamage,CBratio,"
								+ "AdaptCost,FloodPay,AdaptYear,AdaptH,eleHeight,"
								+ "latitude,longitude";
		FileWriter fileWriter = null;
		ReadWriteLock rwLock = new ReentrantReadWriteLock();
		Lock writeLock = rwLock.writeLock();
		try{
			writeLock.lock();
			fileWriter = new FileWriter(filepath, append);
			
			if( append == false) {
				fileWriter.append(FILE_HEADER3.toString());
			}else {
				
				for (Properties housei : BidParcels) {
					fileWriter.append(NEW_LINE_SEPARATOR);
					fileWriter.append( String.valueOf(housei.FID) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.Ptype) );
					fileWriter.append(COMMA_DELIMITER);
					
					fileWriter.append( String.valueOf(housei.bidIncome) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.payratio) );
					fileWriter.append(COMMA_DELIMITER);
					
					fileWriter.append( String.valueOf(housei.CurrentJV) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.Justvalue) );
					fileWriter.append(COMMA_DELIMITER);
					
					fileWriter.append( String.valueOf(housei.zone) );
					fileWriter.append(COMMA_DELIMITER);
					
					fileWriter.append( String.valueOf(housei.elevation) );
					fileWriter.append(COMMA_DELIMITER);
					
					fileWriter.append( String.valueOf(housei.BFE) );
					fileWriter.append(COMMA_DELIMITER);
					
					fileWriter.append( String.valueOf(housei.FloodMeasure) );
					fileWriter.append(COMMA_DELIMITER);
					
					fileWriter.append( String.valueOf(housei.TotFloodDamage) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.TotFloodDamage1) );
					fileWriter.append(COMMA_DELIMITER);
					
					fileWriter.append( String.valueOf(housei.CBratio) );
					fileWriter.append(COMMA_DELIMITER);
					
					fileWriter.append( String.valueOf(housei.TotalAdaptCost) );
					fileWriter.append(COMMA_DELIMITER);
					
					fileWriter.append( String.valueOf(housei.TotalAdaptPay) );
					fileWriter.append(COMMA_DELIMITER);
					
					fileWriter.append( String.valueOf(housei.adaptYear) );
					fileWriter.append(COMMA_DELIMITER);
					
					fileWriter.append( String.valueOf(housei.adaptH) );
					fileWriter.append(COMMA_DELIMITER);
					
					fileWriter.append( String.valueOf(housei.eleHeight) );
					fileWriter.append(COMMA_DELIMITER);
					
					
					fileWriter.append( String.valueOf(housei.latitude) );
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append( String.valueOf(housei.longitude) );
//					fileWriter.append(NEW_LINE_SEPARATOR);
				}
//				System.out.println("writeBidPrice.CSV file was created successfully !!!");
			}
		}catch (IOException e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		}finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
				}
			writeLock.unlock();
		}
//		
		}
	
	public static void writeEvacuation(double[][] totalDamages, String filepath, boolean append) throws IOException {
		StringBuilder builder = new StringBuilder();
		
		if( append == false) {
			for(int j0 = 0; j0 < totalDamages[0].length; j0++){
				builder.append(j0+"");
				if(j0 < totalDamages[0].length - 1)//if this is not the last row element
					builder.append(",");}
			builder.append("\n");
		}else {
			for(int i = 0; i < totalDamages.length; i++){
//				builder.append(i +",");
				for(int j = 0; j < totalDamages[i].length ; j++){
					builder.append(totalDamages[i][j]+"");//append to the output string
					if(j < totalDamages[0].length - 1)//if this is not the last row element
						builder.append(",");//then add comma (if you don't like commas you can use spaces)
					}
			   builder.append("\n");//append new line at the end of the row
			}
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter(filepath, append));
		writer.write(builder.toString());//save the string representation of the board
		writer.close();
		
	}
	
	public static void writeAdaptInfo(double[][] totalDamages, String filepath, boolean append) throws IOException {
		StringBuilder builder = new StringBuilder();
		for(int j0 = 0; j0 < totalDamages[0].length; j0++){
			builder.append(j0+"");
			if(j0 < totalDamages[0].length - 1)//if this is not the last row element
				builder.append(",");}
		builder.append("\n");
		for(int i = 0; i < totalDamages.length; i++){
//			builder.append(i +",");
			for(int j = 0; j < totalDamages[i].length ; j++){
				builder.append(totalDamages[i][j]+"");//append to the output string
				if(j < totalDamages[0].length - 1)//if this is not the last row element
					builder.append(",");//then add comma (if you don't like commas you can use spaces)
				}
		   builder.append("\n");//append new line at the end of the row
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter(filepath, append));
		writer.write(builder.toString());//save the string representation of the board
		writer.close();
	}
	
	
	public static void writeTotResult(Double[] totArray, String filepath) throws IOException {
		StringBuilder builder = new StringBuilder();
		
		for(int i = 0; i < totArray.length; i++) {//for each row{
			builder.append(i +",");
		}
		
		builder.append("\n");
		for(int i = 0; i < totArray.length; i++) {//for each row{
//			builder.append(i +",");
			builder.append(totArray[i]+"");//append to the output string
			if(i < totArray.length - 1)//if this is not the last row element
				builder.append(",");//then add comma (if you don't like commas you can use spaces)
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
		writer.write(builder.toString());//save the string representation of the board
		writer.close();
		System.out.println("finish fileWriter !!!");
	}

public static void writeEvacuation(Double[][] opinionDynamics, String filepath) throws IOException {
	
	StringBuilder builder = new StringBuilder();
	builder.append(",");
	for(int j0 = 0; j0 < opinionDynamics[0].length; j0++){
		builder.append(j0+"");
		if(j0 < opinionDynamics[0].length - 1)//if this is not the last row element
			builder.append(",");
	}
	builder.append("\n");
	for(int i = 0; i < opinionDynamics.length; i++)//for each row
	{
		builder.append(i +",");
		
		for(int j = 0; j < opinionDynamics[i].length; j++)//for each column
			{
			builder.append(opinionDynamics[i][j]+"");//append to the output string
			if(j < opinionDynamics[0].length - 1)//if this is not the last row element
				builder.append(",");//then add comma (if you don't like commas you can use spaces)
			}
	   builder.append("\n");//append new line at the end of the row
	}
	BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
	writer.write(builder.toString());//save the string representation of the board
	writer.close();
	
}



}
