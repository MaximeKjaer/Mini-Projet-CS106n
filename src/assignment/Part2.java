/*
 *	Author:      Timote Vaucher (timote.vaucher@epfl.ch)
 *	Date:        17 oct. 2014
 */

package assignment;

public class Part2 {
	public static double[][] multiplyMatrix(double[][] A, double[][] B) {
		if (!Part1.isMatrix(A) || !Part1.isMatrix(B)) return null;
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
	
	public static double rmse(double[][] M, double[][] P) {
		if (!Part1.isMatrix(M) || !Part1.isMatrix(P)) return -1; //avoid to treat input that aren't matrixes
		else {
			if (M.length != P.length || M[0].length != P[0].length) return -1; //different dimensions
			else {
				int divisor = 0; //Number of not null entries
				double sum = 0, epsilon = 1e-6; //sum (mij - pij)^2 / epsilon due to how double works
				for (int i = 0; i < M.length; ++i) {
					for (int j = 0; j < M[0].length; ++j) {
						if (M[i][j] < -epsilon || M[i][j] > epsilon) {
							++divisor;
							sum += Math.pow((M[i][j] - P[i][j]),2);
						}
					}
				}
				return divisor == 0 ? -1 : Math.sqrt(sum/divisor); //avoid division by 0
			}
		}
	}
	
	public static void main(String[] args) {
		/*double[][] A = {{ 1, 0, 2},
				{ 0, 1, 1}};
		double[][] B = {{ 1, 2},
				{ 0, 1},
				{ 1, 0}};
		System.out.println(Part1.matrixToString(multiplyMatrix(Part1.createMatrix(2, 2, 0, 5), Part1.createMatrix(2, 2, 0, 5))));
		System.out.println(Part1.matrixToString(multiplyMatrix(A,B)));*/
		/*double[][] M = {{ 1, 0, 0},{ 0, 1, 1}};
		double[][] P = {{ 1, 0, 2},{ 0, 1, 1}};*/
		/*double[][] M = {{ 1, 0, 1},{ 0, 1, 1}};
		double[][] P = {{ 1, 0, 2},{ 3, 1, 1}};*/
		/*double[][] M = {{ 1, 0, 1},{ 0, 1, 1}};
		double[][] P = {{ 1, 0},{ 3, 1}};*/
		double[][] M = Part1.createMatrix(2, 2, 0, 5);
		double[][] P = Part1.createMatrix(2, 2, 0, 5);
		System.out.println(Part1.matrixToString(M));
		System.out.println(Part1.matrixToString(P));
		System.out.println(rmse(M, P));
	}
}
