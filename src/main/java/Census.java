import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

public class Census {
	public String CensusID;
	public int DistrictID;
	public int communityAdaptation;
	public int compliancePUM;
	public int ASIAN, BLACK, WHITE, OTHER;
	public int OWNER, RENTER;
	public int damageCount;
	public int lossCount;
	public double CensusFloodCount = 0.0;
	public double CensusLossCount = 0.0;
	public int HOUSEHOLDS, N;
	int policyN_yi = 0;
	int claimN_yi  = 0;
	public int[] floodCountwP = new int[50];
	public int[] policyCount  = new int[50];
	public String[] data;
	// Each agent is expected to interact (INTERACTIONS / N) * 2 times.  (Each interaction uses 2 agents.)
	// We double the expected length of history for safety.
	public Census( ) { }
	public static void printArray(String[] rows) {
	      for (int i = 0; i < rows.length; i++) {
	         System.out.println(i + " , " +rows[i]);
	         System.out.print('\n');
	      }
	      System.out.println("\nlength of the array is "+ rows.length);
	}
	public ArrayList<Census> readFile(String csvFile)  {
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		ArrayList<Census> censusAll = new ArrayList<Census>();
		try {
			br = new BufferedReader(new FileReader(csvFile));
			br.readLine();
			while ((line = br.readLine()) != null) {
				String[] Dline = line.split(cvsSplitBy);
//				printArray(Dline);
				Census censusi = new Census();
				censusi.data = Dline;
				censusi.CensusID   	= Dline[2];				
				censusi.ASIAN       = (int) Float.parseFloat(Dline[0]);
				censusi.BLACK 	    = (int) Float.parseFloat(Dline[1]);
				censusi.WHITE       = (int) Float.parseFloat(Dline[12] );
				censusi.OTHER       = (int) Float.parseFloat(Dline[7]);
				censusi.OWNER       = (int) Float.parseFloat(Dline[8]);
				censusi.RENTER      = (int) Float.parseFloat(Dline[11]);
				censusi.HOUSEHOLDS  = (int) Float.parseFloat(Dline[6]);
				censusi.N 		    = 0;
				censusi.damageCount = 0;
				censusi.lossCount = 0;
				censusi.CensusFloodCount = 0.0;
				censusi.CensusLossCount = 0.0;
				censusAll.add(censusi);
	           }
			System.out.println("Total Census " + censusAll.size());
			return censusAll;
			}catch (FileNotFoundException e){
				e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (br != null) {
	                try {
	                    br.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
		return censusAll;
	}
	public void DisplayCensus() {
		System.out.println("Census id "+this.CensusID + "ImprovePUM");
        System.out.println( Arrays.toString(this.data) );
	}

}
