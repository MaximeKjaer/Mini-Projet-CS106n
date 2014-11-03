package assignment;

import java.util.Random;

public class test {
	public static void main(String[] args) {
		Random random = new Random();
		int row = 50, col = 100, d = 10;
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
		/*double[][] M = {
				{ 11, 0, 9, 8, 7 },
				{ 18, 0, 18, 18, 18 },
				{ 29, 28, 27, 0, 25 },
				{ 6, 6, 0, 6, 6 },
				{ 17, 16, 15, 14, 0 }
			};
			double U[][] = { {1,1}, {1,1}, {1,1}, {1,1}, {1,1} };
			double V[][] = { {1,1,1,1,1}, {1,1,1,1,1} };
			System.out.println(Recommendation.matrixToString(M));
			System.out.println("\nTest 2 (isMatrix):");
			double[][] testMatrix = {
				{11.0,0.0,9.0,8.0,7.0},
				{18.0,0.0,18.0,18.0,18.0},
				{29.0,28.0,27.0,0.0,25.0},
				{6.0,6.0,0.0,6.0,6.0},
				{17.0,16.0,15.0,14.0,0.0}
			};
			double T[][] = {{ 1, 0, 2},{ 0, 1}};
			System.out.println("M est une matrice: " + Recommendation.isMatrix(M));
			System.out.println("testMatrix est une matrice: " + Recommendation.isMatrix(testMatrix));
			System.out.println("T est une matrice: " + Recommendation.isMatrix(T));
			System.out.println("\nTest 3 (multiplyMatrix): ");
			double[][] A = {{ 1, 0, 2}, { 0, 1, 1}};
			double[][] B = {{ 1, 2},{ 0, 1},{ 1, 0}};
			System.out.println("A*B = \n" + Recommendation.matrixToString(Recommendation.multiplyMatrix(A, B)));
			System.out.println("\nTest 4 (createMatrix): ");
			System.out.println("Matrice au pif: \n" + Recommendation.matrixToString(Recommendation.createMatrix(2,3,0,5)));
			System.out.println("\nTest 5 (RMSE): ");
			double[][] m1 = {{ 1, 0, 0},{ 0, 1, 1}};
			double[][] p1 = {{ 1, 0, 2},{ 0, 1, 1}};
			System.out.println("Test 1 de RMSE (expect 0.0): " + Recommendation.rmse(m1, p1));
			double[][] m2 = {{ 1, 0, 1},{ 0, 1, 1}};
			double[][] p2 = {{ 1, 0, 2},{ 3, 1, 1}};
			System.out.println("Test 2 de RMSE (expect 0.5): " + Recommendation.rmse(m2, p2));
			double[][] m3 = {{ 1, 0, 1},{ 0, 1, 1}};
			double[][] p3 = {{ 1, 0},{ 3, 1}};
			System.out.println("Test 3 de RMSE (expect -1): " + Recommendation.rmse(m3, p3));
			System.out.println("\nTest 6 (updateUElem): ");
			double[][] m0 = {
				{ 11, 0, 9, 8, 7 },
				{ 18, 0, 18, 18, 18 },
				{ 29, 28, 27, 0, 25 },
				{ 6, 6, 0, 6, 6 },
				{ 17, 16, 15, 14, 0 }
			};
			double[][] m1_2 = {
				{ 0, 8, 9, 8, 7 },
				{ 18, 0, 18, 18, 18 },
				{ 29, 28, 27, 0, 25 },
				{ 6, 6, 0, 6, 6 },
				{ 17, 16, 15, 14, 0 }
			};
			double u1[][] = { {2,2}, {2,2}, {2,2}, {2,2}, {2,2} };
			double v1[][] = { {1,1,1,1,1}, {1,1,1,1,1} };
			System.out.println("Test 1 de updateUElem (expect ~6.750): " + Recommendation.updateUElem(m0, u1, v1, 0, 0));
			System.out.println("Test 2 de updateUElem (expect ~6.000): " + Recommendation.updateUElem(m1_2, u1, v1, 0, 0));
			System.out.println("Test 1 de updateVElem (expect ~7.100): " + Recommendation.updateVElem(m0, u1, v1, 0, 0));
			System.out.println("Test 2 de updateVElem (expect ~7.750): " + Recommendation.updateVElem(m1_2, u1, v1, 0, 0));*/
	}
}
