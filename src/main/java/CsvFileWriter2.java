import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class CsvFileWriter {
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	
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
	public static void writeBidPrice(ArrayList<Properties> BidParcels, String filepath) {
		String FILE_HEADER =  	  "FID,Ptype,CensusID,EFFYER,"
								+ "SFHA,Wdistance,Justvalue,LADVAL,NORESUNTS,Area,fCount,"
								+ "cate10,cate20,cate30,cate40,cate50,"
								+ "cate1,cate2,cate3,cate4,cate5,DEM,"
								+ "bidIncome,race,education,SVIndex,Friends,"
								+ "BFE,gov_RH,insCost,"
								+ "subsidy,Coverage,eleHeight,eleCost,Frisk0,AFrisk1,"
								+ "Tcost,FloodAD,CateLabel,RiskPerception,Alpha,Beta,"
								+ "prospectAlpha,floodCount,"
								+ "subsidy,latitude,longitude";
		FileWriter fileWriter = null;
		try{
			fileWriter = new FileWriter(filepath);
			fileWriter.append(FILE_HEADER.toString());
			fileWriter.append(NEW_LINE_SEPARATOR);
			
			for (Properties housei : BidParcels) {
				fileWriter.append( String.valueOf(housei.FID) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.Ptype) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.CensusID) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.EFFYER) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.SFHA) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.coastDistance) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.Justvalue) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.LADVAL) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.NORESUNTS) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.liveArea) );
				fileWriter.append(COMMA_DELIMITER);
				
				fileWriter.append( String.valueOf(housei.friendCount) );
				fileWriter.append(COMMA_DELIMITER);
				
				
				fileWriter.append( String.valueOf(housei.cate10) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.cate20) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.cate30) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.cate40) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.cate50) );
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
				fileWriter.append( String.valueOf(housei.elevation) );
				fileWriter.append(COMMA_DELIMITER);
				
				fileWriter.append( String.valueOf(housei.Income) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.Race) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.Education) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.SVIndex) );
				fileWriter.append(COMMA_DELIMITER);
				
				fileWriter.append( String.valueOf(housei.friendCount) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.BFE) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.gov_Fheight_reduct) );
				fileWriter.append(COMMA_DELIMITER);
				
				fileWriter.append( String.valueOf(housei.insuranceCost) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.subsidy) );
				fileWriter.append(COMMA_DELIMITER);
				
				fileWriter.append( String.valueOf(housei.insuranceCoverage) );
				fileWriter.append(COMMA_DELIMITER);
				
				fileWriter.append( String.valueOf(housei.eleHeight) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.elecosts) );
				fileWriter.append(COMMA_DELIMITER);
				
				fileWriter.append( String.valueOf(housei.FloodRisk0) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.FloodRisk1) );
				fileWriter.append(COMMA_DELIMITER);
				
				fileWriter.append( String.valueOf(housei.totalCost) );
				fileWriter.append(COMMA_DELIMITER);
				
				fileWriter.append( String.valueOf(housei.FloodAD) );
				fileWriter.append(COMMA_DELIMITER);
				
				fileWriter.append( String.valueOf(housei.CateLabel) );
				fileWriter.append(COMMA_DELIMITER);
				
				fileWriter.append( String.valueOf(housei.risk_perception) );
				fileWriter.append(COMMA_DELIMITER);
				
				fileWriter.append( String.valueOf(housei.BayesAlpha) );
				fileWriter.append(COMMA_DELIMITER);
				
				fileWriter.append( String.valueOf(housei.BayesBeta) );
				fileWriter.append(COMMA_DELIMITER);
				
				fileWriter.append( String.valueOf(housei.prospectAlpha) );
				fileWriter.append(COMMA_DELIMITER);
				
				fileWriter.append( String.valueOf(housei.floodCounts) );
				fileWriter.append(COMMA_DELIMITER);
				
				fileWriter.append( String.valueOf(housei.subsidy) );
				fileWriter.append(COMMA_DELIMITER); 
				
				
				fileWriter.append( String.valueOf(housei.latitude) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.longitude) );
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
			System.out.println("writeBidPrice.CSV file was created successfully !!!");
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
	
	
	
	public static void writeFinalParcel(ArrayList<Properties> BidParcels, String filepath) {
		String FILE_HEADER =  	  "FID,Ptype,CensusID,EFFYER,"
								+ "SFHA,Wdistance,Justvalue,LADVAL,NORESUNTS,Area,Friends,"
								+ "cate1,cate2,cate3,cate4,cate5,DEM,"
								+ "bidIncome,race,education,willPay,Rpercept,lastYears,"
								+ "BFE,eleHeight,eleCost,Frisk0,AFrisk1,"
								+ "AvgDamag0,AvgDamag1,CumDamage1,"
								+ "AvgACost,AvgEleCost,AvgInsCost,AvgLostR," // insCost1,cover1,RiskInsCost1,riskCover1,
								+ "AvgSubsidy,RequiredIns,Flabel,latitude,longitude";
		FileWriter fileWriter = null;
		try{
			fileWriter = new FileWriter(filepath);
			fileWriter.append(FILE_HEADER.toString());
			fileWriter.append(NEW_LINE_SEPARATOR);
			
			for (Properties housei : BidParcels) {
				fileWriter.append( String.valueOf(housei.FID) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.Ptype) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.CensusID) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.EFFYER) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.floodZone) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.coastDistance) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.Justvalue) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.LADVAL) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.NORESUNTS) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.liveArea) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.friendCount) );
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
				fileWriter.append( String.valueOf(housei.elevation) );
				fileWriter.append(COMMA_DELIMITER);
				
				fileWriter.append( String.valueOf(housei.Income) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.Race) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.Education) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.WillingToPay) );
				fileWriter.append(COMMA_DELIMITER);
				
				fileWriter.append( String.valueOf(housei.risk_perception) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.poissonLamda) );
				fileWriter.append(COMMA_DELIMITER);
				
				fileWriter.append( String.valueOf(housei.BFE) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.eleHeight) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.elecosts) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.FloodRisk0) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.FloodRisk1) );
				fileWriter.append(COMMA_DELIMITER);

				
				fileWriter.append( String.valueOf(housei.TotFloodDamage) );
				fileWriter.append(COMMA_DELIMITER); 
				fileWriter.append( String.valueOf(housei.TotFloodDamage1) );
				fileWriter.append(COMMA_DELIMITER); 
				fileWriter.append( String.valueOf(housei.TotCumFloodDamage1) );
				fileWriter.append(COMMA_DELIMITER); 
				fileWriter.append( String.valueOf(housei.TotadaptCost) );
				fileWriter.append(COMMA_DELIMITER); 			
				fileWriter.append( String.valueOf(housei.TotEleCost) );
				fileWriter.append(COMMA_DELIMITER); 
				fileWriter.append( String.valueOf(housei.TotInsCost) );
				fileWriter.append(COMMA_DELIMITER);
				
				fileWriter.append( String.valueOf(housei.AvgPhaseOut) );
				fileWriter.append(COMMA_DELIMITER);
				
//				fileWriter.append( String.valueOf(housei.InsuranceCost1) );
//				fileWriter.append(COMMA_DELIMITER);
//				fileWriter.append( String.valueOf(housei.InsuranceCoverage1) );
//				fileWriter.append(COMMA_DELIMITER);
//				fileWriter.append( String.valueOf(housei.RiskInsuranceCost1) );
//				fileWriter.append(COMMA_DELIMITER);
//				fileWriter.append( String.valueOf(housei.RiskInsuranceCoverage1) );
//				fileWriter.append(COMMA_DELIMITER);
				
				fileWriter.append( String.valueOf(housei.TotSubsidy) );
				fileWriter.append(COMMA_DELIMITER); 
				
				fileWriter.append( String.valueOf(housei.requiredIns) );
				fileWriter.append(COMMA_DELIMITER); 
				
				fileWriter.append( String.valueOf(housei.CateLabel) );
				fileWriter.append(COMMA_DELIMITER); 
				
				
				fileWriter.append( String.valueOf(housei.latitude) );
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append( String.valueOf(housei.longitude) );
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
			System.out.println("writeBidPrice.CSV file was created successfully !!!");
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
	
	
	public static void writeEvacuation(double[][] totalDamages, String filepath) throws IOException {
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
		BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
		writer.write(builder.toString());//save the string representation of the board
		writer.close();
	}
	
	public static void writeTotResult(double[][] totArray, String filepath) throws IOException {
		StringBuilder builder = new StringBuilder();
		for(int j = 0; j < totArray.length; j++){
			for(int i = 0; i < totArray[j].length; i++) {
				builder.append(totArray[j][i]+"");//append to the output string
				if(i < totArray[j].length - 1){	//if this is not the last row element
					builder.append(",");}	//then add comma (if you don't like commas you can use spaces)
			}
			builder.append("\n");
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
		writer.write(builder.toString());//save the string representation of the board
		writer.close();
		System.out.println("finish fileWriter !!!");
		}
	}
