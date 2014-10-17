/*
 *	Author:      Timote Vaucher (timote.vaucher@epfl.ch)
 *	Date:        17 oct. 2014
 */

package assignment;

public class Part2 {
	public static double[][] multiplyMatrix(double[][] A, double[][] B) {
		if (Part1.isMatrix(A) && Part1.isMatrix(B)) {
			//first sanity check, if A and B aren't matrixes we can't possibly multiply them
			int[] amountRows = {A.length, B.length}, amountCols = {A[0].length, B[0].length};
			if (amountCols[0] == amountRows[1]) {
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
			else return null;
			
		}
		else return null;
	}
	
	public static void main(String[] args) {
		double[][] A = {{ 1, 0, 2},
				{ 0, 1, 1}};
		double[][] B = {{ 1, 2},
				{ 0, 1},
				{ 1, 0}};
		System.out.println(Part1.matrixToString(multiplyMatrix(Part1.createMatrix(2, 2, 0, 5), Part1.createMatrix(2, 2, 0, 5))));
		System.out.println(Part1.matrixToString(multiplyMatrix(A,B)));
	}
}
