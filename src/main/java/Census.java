
import java.util.Arrays;

public class Census {


	public int censusID;
	// Each agent is expected to interact (INTERACTIONS / N) * 2 times.  (Each interaction uses 2 agents.)
	// We double the expected length of history for safety.
	public Census( ) {
		
	}
	public void DisplayCensus() {
        System.out.println( this.censusID );
	}

}
