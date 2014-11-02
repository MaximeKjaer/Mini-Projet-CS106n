package assignment;

import java.util.Random;

public class test {
	public static void main(String[] args) {
		Random random = new Random();
		int row = 10, col = 20, d = 2;
		double[][] P = Recommendation.multiplyMatrix(Recommendation.createMatrix(row, d, 1, 5), Recommendation.createMatrix(d, col, 1, 5)), M = new double[row][col];
		for (int i = 0; i < row; ++i) {
			for (int j = 0; j < col; ++j) {
				M[i][j] = P[i][j];
			}
		}
		for (int i = 0; i < (row * col / 2.); ++i) {
			M[random.nextInt(row)][random.nextInt(col)] = 0;
		}
		
		double scoreSum = 0;
		int iteration = 1;
		for (int i = 0; i < iteration; ++i) {
			long startTime = System.currentTimeMillis();
			int[] recommendation = Recommendation.recommend(M, d);
			long stopTime = System.currentTimeMillis();
			System.out.println("Temps écoulé pour recommender : " + (stopTime-startTime)/1000. + " s");
			System.out.println("RECOMMMENDATIONS: ");
			Recommendation.printArray(recommendation);
			double scoreThis = Recommendation.getScore(recommendation, P, M);
			scoreSum += scoreThis;
			System.out.println("score : " + scoreThis + "\n");
		}
		System.out.println("SCORE TOTAL: " + scoreSum);
		System.out.println("Moyenne sur " + iteration + " itérations: " + scoreSum / iteration);
		//System.out.println(Recommendation.matrixToString(M));
		//System.out.println(Recommendation.matrixToString(P));
	}
}
