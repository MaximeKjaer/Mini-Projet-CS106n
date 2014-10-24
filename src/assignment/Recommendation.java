//package assignment;

import java.util.Random;

public class Recommendation {

	/*
	 * Inscrivez votre nom complet (pr�nom et nom de famille)
	 * ainsi que votre num�ro sciper ci-dessous :
	 */
	
	/* Etudiant 1 */
	public static String NAME1 = "Timot� Vaucher";
	public static int SCIPER1 = 246532;
	
	/* Etudiant 2 - laissez tel quel si vous avez cod� le projet tout seul */
	public static String NAME2 = "Maxime Kjaer";
	public static int SCIPER2 = 250694;
	
	private static Random random = new Random();
	
	public static String matrixToString(double[][] A) {
		//We return null if matrix is null to avoid unnecessary exceptions
		if (A == null) return null;
		else {
			String output = "{\n";
			int inputLength = A.length;
			for (int i = 0; i < inputLength; ++i) {
				output += " {";
				for (int j = 0; j < A[i].length; ++j) { 
					//We keep some flexibility because we aren't sure to always print a strict n x m matrix
					output += A[i][j];
					if (j < A[i].length-1) output += ", ";
				}
				output += (i < inputLength-1) ? "},\n" : "}\n";
			}
			output += "}";
			return output;
		}
	}
	
	public static boolean isMatrix( double[][] A ) {
		//Sanity check
		if (A == null || A.length == 0) return false;
		else {
			int amountRows = A.length, sizeRowToCompare = A[0].length;
			if (sizeRowToCompare == 0) return false;
			else {
				for (int i = 1; i < amountRows; ++i) {
					//Check the size of row -> if one is empty
					if (A[i].length != sizeRowToCompare) return false;
				}
				//if all tests passed, then return true
				return true;
			}
		}
	}
	
	public static double[][] multiplyMatrix(double[][] A, double[][] B) {
		if (!isMatrix(A) || !isMatrix(B)) return null;
		else {
			//first sanity check, if A and B aren't matrixes we can't possibly multiply them
			int[] amountRows = {A.length, B.length}, amountCols = {A[0].length, B[0].length};
			if (amountCols[0] != amountRows[1]) return null;
			else {
				//second sanity check, because we can multiply A and B only if the amount of cols in A == amount of rows in B
				double[][] outputMatrix = new double[amountRows[0]][amountCols[1]];
				for (int i = 0; i < amountRows[0]; ++i) { //iteration on the rows
					for (int j = 0; j < amountCols[1]; ++j) { //iteration on the cols
						double valueAtij = 0.;
						for (int d = 0; d < amountRows[1]; ++d) { //calculation of the value p_ij
							valueAtij += A[i][d] * B[d][j];
						}
						//we update the value in the outputMatrix
						outputMatrix[i][j] = valueAtij;
					}
				}
				return outputMatrix;
			}
		}
	}
	
	public static double[][] createMatrix( int n, int m, int k, int l) {
		if (n <= 0 || m <= 0 || k > l) {
			//compared to the instruction we handle cases where n or m < 0, because such matrix doesn't exist
			return null;
		}
		else {
			double[][] randomMatrix = new double[n][m];
			if (k != l) { //else we return the default n x m matrix
				for (int i = 0; i < n; ++i) {
					for (int j = 0; j < m; ++j) {
						double randomMultipler = random.nextInt(Integer.MAX_VALUE)/(Integer.MAX_VALUE-1.);
						//create a number in [0,1] with a great amount of digits after the decimal point
						randomMatrix[i][j] = (l-k)*randomMultipler + k; // => in [k,l]
					}
				}
			}
			return randomMatrix;
		}
	}
	
	public static double rmse(double[][] M, double[][] P) {
		if (!isMatrix(M) || !isMatrix(P)) return -1; //avoid to treat input that aren't matrixes
		else {
			if (M.length != P.length || M[0].length != P[0].length) return -1; //different dimensions
			else {
				int divisor = 0; //Number of nonzero entries
				double sum = 0, epsilon = 1e-6; //sum (mij - pij)^2 / epsilon due to how double works
				for (int i = 0; i < M.length; ++i) {
					for (int j = 0; j < M[0].length; ++j) {
						if (Math.abs(M[i][j]) > epsilon) {
							++divisor; //increment the amount of nonzero entries
							sum += Math.pow((M[i][j] - P[i][j]),2);
						}
					}
				}
				return divisor == 0 ? 0 : Math.sqrt(sum/divisor); //avoid division by 0
			}
		}
	}
	
	public static double updateUElem( double[][] M, double[][] U, double[][] V, int r, int s ) {
		double newUElem = 0.0;
		double denominator = 0.0;
		double numerator = 0.0;
		double secondSum = 0.0;

		int n = M.length;
		int d = V.length;
		int m = V[0].length;

		for (int j = 0; j < m; j++) { //sum_j of v_sj^2
			denominator += V[s][j] * V[s][j];
		}
		System.out.println("DENOMINATOR " + denominator);
		if (denominator == 0.0) return 0.0; //Divide by zero!
		else {
			for (int j = 0; j < m; j++) {
				if (M[r][j] == 0) continue;
				else {
					secondSum = 0.0;
					for (int k = 0; k < d; k++) {
						if (k != s) {
							secondSum += U[r][k] * V[k][j];
						}
					}
					numerator += V[s][j] * (M[r][j] - secondSum);
					System.out.println("j: "+j+"mrj: "+M[r][j]+", numerator:"+numerator);
				}
			}
		newUElem = numerator / denominator;
		return newUElem;
		}
	}
	
	public static double updateVElem( double[][] M, double[][] U, double[][] V, int r, int s ) {
		/* M�thode � coder */	
		return 0;		
	}
	
	public static double[][] optimizeU( double[][] M, double[][] U, double[][] V) {
		/* M�thode � coder */	
		return null;		
	}

	public static double[][] optimizeV( double[][] M, double[][] U, double[][] V) {
		/* M�thode � coder */	
		return null;		
	}
	
	public static int[] recommend( double[][] M, int d) {
		/* M�thode � coder */	
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println("Test 1 (matrixToString):");
		double[][] M = {
			{ 11, 0, 9, 8, 7 },
			{ 18, 0, 18, 18, 18 },
			{ 29, 28, 27, 0, 25 },
			{ 6, 6, 0, 6, 6 },
			{ 17, 16, 15, 14, 0 }
		};
		double U[][] = { {1,1}, {1,1}, {1,1}, {1,1}, {1,1} };
		double V[][] = { {1,1,1,1,1}, {1,1,1,1,1} };
		System.out.println(matrixToString(M));


		System.out.println("\nTest 2 (isMatrix):");
		double[][] testMatrix = {
			{11.0,0.0,9.0,8.0,7.0},
			{18.0,0.0,18.0,18.0,18.0},
			{29.0,28.0,27.0,0.0,25.0},
			{6.0,6.0,0.0,6.0,6.0},
			{17.0,16.0,15.0,14.0,0.0}
		};
		double T[][] = {{ 1, 0, 2},{ 0, 1}};
		System.out.println("M est une matrice: " + isMatrix(M));
		System.out.println("testMatrix est une matrice: " + isMatrix(testMatrix));
		System.out.println("T est une matrice: " + isMatrix(T));


		System.out.println("\nTest 3 (multiplyMatrix): ");
		double[][] A = {{ 1, 0, 2}, { 0, 1, 1}};
		double[][] B = {{ 1, 2},{ 0, 1},{ 1, 0}};
		System.out.println("A*B = \n" + matrixToString(multiplyMatrix(A, B)));


		System.out.println("\nTest 4 (createMatrix): ");
		System.out.println("Matrice au pif: \n" + matrixToString(createMatrix(2,3,0,5)));


		System.out.println("\nTest 5 (RMSE): ");
		double[][] m1 = {{ 1, 0, 0},{ 0, 1, 1}};
		double[][] p1 = {{ 1, 0, 2},{ 0, 1, 1}};
		System.out.println("Test 1 de RMSE (expect 0.0): " + rmse(m1, p1));
		double[][] m2 = {{ 1, 0, 1},{ 0, 1, 1}};
		double[][] p2 = {{ 1, 0, 2},{ 3, 1, 1}};
		System.out.println("Test 2 de RMSE (expect 0.5): " + rmse(m2, p2));
		double[][] m3 = {{ 1, 0, 1},{ 0, 1, 1}};
		double[][] p3 = {{ 1, 0},{ 3, 1}};
		System.out.println("Test 3 de RMSE (expect -1): " + rmse(m3, p3));

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
		System.out.println("Test 1 de updateUElem (expect ~6.750): " + updateUElem(m0, u1, v1, 0, 0));
		System.out.println("Test 2 de updateUElem (expect ~6.000): " + updateUElem(m1_2, u1, v1, 0, 0));


		/*
		System.out.println(matrixToString(createMatrix(20, 20, 0, 5)));
		createMatrix(5, 5, 0, 5);
		double[][] A = createMatrix(2, 2, 0, 5);
		double[][] B = createMatrix(2, 2, 0, 5);
		System.out.println(matrixToString(A));
		System.out.println(matrixToString(B));
		System.out.println(matrixToString(multiplyMatrix(A, B)));
		double[][] M = createMatrix(2, 2, 0, 5);
		double[][] P = createMatrix(2, 2, 0, 5);
		System.out.println(matrixToString(M));
		System.out.println(matrixToString(P));
		System.out.println(rmse(M, P));
		double[][] M2 = {{0,0},{0,0}};
		System.out.println(rmse(M2, P));
		*/
	}
}


