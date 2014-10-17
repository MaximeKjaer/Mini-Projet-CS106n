package assignment;

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
	public static int SCIPER2 = 123789;
	
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
					if (j < A[i].length-1) output += ",";
				}
				output += (i < inputLength-1) ? "},\n" : "}\n";
			}
			output += "};";
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
		/* M�thode � coder */
		return null;	
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
						//create a pseudo random number in [k,l]
						randomMatrix[i][j] = random.nextInt(l-k) + random.nextDouble() + k;
					}
				}
			}
			return randomMatrix;
		}
	}
	
	public static double rmse(double[][] M, double[][] P) {
		/* M�thode � coder */	
		return 0;
	}
	
	public static double updateUElem( double[][] M, double[][] U, double[][] V, int r, int s ) {
		/* M�thode � coder */	
		return 0;
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
		System.out.println(matrixToString(createMatrix(5, 5, -10, 10)));
	}
}


