
public class Agents {
	
	public double HHsize;
	public int HouseholdID, age;
	public String censusID;
	public int HOUSEHOLDS;
	public int MED_AGE;
	public int race;
	public int OWNER;
	public int income;
	public int nvehicle;
	
	
	
	public String[] data0 = new String[10];
	// Each agent is expected to interact (INTERACTIONS / N) * 2 times.  (Each interaction uses 2 agents.)
	// We double the expected length of history for safety.
	public Agents( String[] line ) {
		//this.opinion = new double[(RAModel.INTERACTIONS / RAModel.N) * 4];
		this.age      = (int) Float.parseFloat(line[0]);
		data0[0] = line[1];
		this.HHsize      = Double.parseDouble(line[1]);
		data0[0] = line[1];
		this.HouseholdID = (int) Float.parseFloat(line[2]);
		data0[1] = line[2];
		this.censusID  = line[3];
		data0[2] = line[3];
		this.race = (int) Float.parseFloat(line[4]);
		data0[3] = line[4];
		this.income = (int) Float.parseFloat(line[5]);
		data0[4] = line[5];
		this.nvehicle = (int) Float.parseFloat(line[6]);
		
//		this.opinion = new double[(RAModel.INTERACTIONS / RAModel.N) * 4];
		
		
	} // Agent
	
	
	public void displayAgents() {
		System.out.println("Household age"  + this.age+
							"\n id "      + this.HouseholdID + 
							"\n CensusID "  + this.censusID +
							"\n HOUSEHOLDS "+ this.HOUSEHOLDS + 
							"\n income "    + this.income +
							"\n Head race " + this.race);
		} // getUncertainty
	
	
	
}

