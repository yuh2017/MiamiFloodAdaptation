import norsys.netica.*;
import norsys.neticaEx.aliases.Node;
public class BayesainNet {
	
	
	public BayesainNet(){}
	
	public static Net BuildNet(){
		try{
			Node.setConstructorClass ("norsys.neticaEx.aliases.Node");
			Environ env  = new Environ(null);
			Net net = new Net();
			net.setName("HumanFloodInsurance");
			Node riskPerception 	 = new Node( "RiskPerception", "low,median,relativeHigh,High", net);
			Node RiskApprisal 		 = new Node( "RiskApprisal", "lowRisk,medianRisk,HighRisk", net);
			Node Adaptation 		 = new Node( "Adaptation_Decision", "UnderProtect,Proper,OverProtect", net);
			RiskApprisal.setTitle ("risk evaluation");
			Adaptation.setTitle ("Adaptation");
			riskPerception.setTitle ("perception of risk");			
			Adaptation.addLink (riskPerception); // link from riskPerception to Adaptation
			Adaptation.addLink (RiskApprisal);
			float[] asiaProbs = { 0.4F,0.3F,0.2F,0.1F };
			riskPerception.setCPTable(asiaProbs);
			RiskApprisal.setCPTable (0.2, 0.3, 0.5 );
			
			
			Adaptation.setCPTable("low,lowRisk", 0.85, 0.05, 0.1);
			Adaptation.setCPTable("low,medianRisk", 0.75, 0.2, 0.05);
			Adaptation.setCPTable("low,HighRisk", 0.65, 0.25, 0.1);
			
			Adaptation.setCPTable("median,lowRisk", 0.5, 0.35, 0.15);
			Adaptation.setCPTable("median,medianRisk", 0.5, 0.3, 0.2);
			Adaptation.setCPTable("median,HighRisk", 0.5, 0.35, 0.15);
			
			Adaptation.setCPTable("relativeHigh,lowRisk", 0.4, 0.45, 0.15);
			Adaptation.setCPTable("relativeHigh,medianRisk", 0.4, 0.35, 0.25);
			Adaptation.setCPTable("relativeHigh,HighRisk", 0.3, 0.35, 0.35);
			
			Adaptation.setCPTable("High,lowRisk", 0.5, 0.35, 0.15);
			Adaptation.setCPTable("High,medianRisk", 0.4, 0.25, 0.35);
			Adaptation.setCPTable("High,HighRisk", 0.2, 0.35, 0.45);
			
			net.compile();
			
			double belief = Adaptation.getBelief ("UnderProtect");          
			System.out.println ("\nThe probability of risk perception is " + belief);
			
			RiskApprisal.finding().enterState ("medianRisk");
			riskPerception.finding().enterState ("relativeHigh");
			
			belief = Adaptation.getBelief ("UnderProtect");          
			System.out.println ("\nGiven an median risk apprisal and relative high risk perception,\n" +
					    "the probability of low riskPerception is " + belief + "\n");
			
			net.retractFindings();
			
			belief = Adaptation.getBelief ("Proper");          
			System.out.println ("\nThe probability of risk perception is " + belief);
			
			RiskApprisal.finding().enterState ("HighRisk");
			riskPerception.finding().enterState ("relativeHigh");
			
			belief = Adaptation.getBelief ("Proper");          
			System.out.println ("\nGiven an median risk perception,\n" +
					    "the probability of low risk appriasal is " + belief + "\n");
			
			net.retractFindings();
			Streamer stream = new Streamer ("C:/Users/yuh46/Desktop/netica/NeticaJ_Win/NeticaJ_504/DynamicHumanBehavior/adaptation.dne");
			net.write (stream);
			net.finalize();
			env.finalize();
			return net;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
	    }
		
	}
	
	
}
