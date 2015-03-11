package HW_1;
import java.math.*;

public class CosineSimilarity {

	double cosineSimilarity(int[] v1,int[] v2){
		double dotProduct=dotProduct(v1,v2);
		double magnitudeV1=getMagnitude(v1);
		double magnitudeV2=getMagnitude(v2);
		return(dotProduct/(magnitudeV1*magnitudeV2));
	}
	
	double dotProduct(int[] v1,int[] v2){
		double sum=0;
		
		for(int i=0;i<v1.length;i++){
			sum=sum+(v1[i]*v2[i]);
		}
		return sum;
	}
	
	double getMagnitude(int[] v){
		double sum=0;
		for(int i=0;i<v.length;i++){
			sum=sum+(v[i]*v[i]);
		}
		
		return(Math.sqrt(sum));
	}
}
