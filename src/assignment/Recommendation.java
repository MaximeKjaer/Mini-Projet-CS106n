package assignment;

import java.util.Random;

public class Recommendation {

	/*
	 * Inscrivez votre nom complet (prénom et nom de famille)
	 * ainsi que votre numéro sciper ci-dessous :
	 */
	
	/* Etudiant 1 */
	public static String NAME1 = "Timoté Vaucher";
	public static int SCIPER1 = 246532;
	
	/* Etudiant 2 - laissez tel quel si vous avez codé le projet tout seul */
	public static String NAME2 = "Maxime Kjaer";
	public static int SCIPER2 = 250694;
	
	private static Random random = new Random();

	private static final double EPSILON = 1e-6; // epsilon due to how double works

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
				double sum = 0.; //sum (mij - pij)^2
				for (int i = 0; i < M.length; ++i) {
					for (int j = 0; j < M[0].length; ++j) {
						if (Math.abs(M[i][j]) > EPSILON) {
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
		double denominator = 0.;
		double numerator = 0.;
		double secondSum = 0.;

		int d = V.length;
		int m = V[0].length;

		for (int j = 0; j < m; j++) { //sum_j of v_sj^2
			if (Math.abs(M[r][j]) > EPSILON) {
				denominator += V[s][j] * V[s][j];
			}
		}
		if (denominator <= EPSILON) return 0.; //Divide by zero!
		else {
			for (int j = 0; j < m; ++j) {
				if (Math.abs(M[r][j]) > EPSILON) {
					secondSum = 0.;
					for (int k = 0; k < d; ++k) {
						if (k != s) {
							secondSum += U[r][k] * V[k][j];
						}
					}
					numerator += V[s][j] * (M[r][j] - secondSum);
				}
			}
		return numerator / denominator;
		}
	}
	
	public static double updateVElem( double[][] M, double[][] U, double[][] V, int r, int s ) {
		double denominator = 0.;
		double numerator = 0.;
		double secondSum = 0.;

		int d = V.length;
		int n = U.length;

		for (int i = 0; i < n; i++) { //sum_j of v_sj^2
			if (Math.abs(M[i][s]) > EPSILON) {
				denominator += U[i][r] * U[i][r];
			}
		}
		if (denominator <= EPSILON) return 0.; //Divide by zero!
		else {
			for (int i = 0; i < n; ++i) {
				if (Math.abs(M[i][s]) > EPSILON) {
					secondSum = 0.;
					for (int k = 0; k < d; ++k) {
						if (k != r) {
							secondSum += U[i][k] * V[k][s];
						}
					}
					numerator += U[i][r] * (M[i][s] - secondSum);
				}
			}
		return numerator / denominator;
		}
	}
	
	public static double[][] optimizeU( double[][] M, double[][] U, double[][] V) {
		double rmseStart = 0;
		double rmseEnd = 0;
		int rows = U.length;
		int cols = U[0].length;
		do {
			rmseStart = rmse(M, multiplyMatrix(U, V));
			//System.out.println("RMSE Start: " + rmseStart);
			for (int i = 0; i < rows; ++i) {
				for (int j = 0; j < cols; ++j) {
					U[i][j] = updateUElem(M, U, V, i, j);
				}
			}
			rmseEnd = rmse(M, multiplyMatrix(U, V));
			//System.out.println("RMSE End: " + rmseEnd);
		} while ((rmseStart - rmseEnd) >= 10e-6);
		return U;
	}

	public static double[][] optimizeV( double[][] M, double[][] U, double[][] V) {
		double rmseStart = 0;
		double rmseEnd = 0;
		int rows = V.length;
		int cols = V[0].length;
		do {
			rmseStart = rmse(M, multiplyMatrix(U, V));
			//System.out.println("RMSE Start: " + rmseStart);
			for (int i = 0; i < rows; ++i) {
				for (int j = 0; j < cols; ++j) {
					V[i][j] = updateVElem(M, U, V, i, j);
				}
			}
			rmseEnd = rmse(M, multiplyMatrix(U, V));
			//System.out.println("RMSE End: " + rmseEnd);
		} while ((rmseStart - rmseEnd) >= 10e-6);
		return V;
	}
	
	public static int[] recommend( double[][] M, int d) {
		/* Méthode à  coder */	
		return null;
	}
	
	public static void main(String[] args) {
		double[][] M = {
			{ 11, 0, 9, 8, 7 },
			{ 18, 0, 18, 18, 18 },
			{ 29, 28, 27, 0, 25 },
			{ 6, 6, 0, 6, 6 },
			{ 17, 16, 15, 14, 0 }
		};
		double U[][] = { {1,1}, {1,1}, {1,1}, {1,1}, {1,1} };
		double V[][] = { {1,1,1,1,1}, {1,1,1,1,1} };
	}
}
