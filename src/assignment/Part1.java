/*
 *	Author:      Timote Vaucher (timote.vaucher@epfl.ch)
 *	Date:        15 oct. 2014
 *  Content : Part one of the assignment :
 *  - matrixToString
 *  - isMatrix
 *  - createMatrix
 */

package assignment;

public class Part1 {
	public static String matrixToString(double[][] A) {
		//There's little verification here, as it's supposed to be done in isMatrix
		if (A == null) return null;
		else {
			String output = "{\n";
			for (int i = 0; i < A.length; ++i) {
				output += " {";
				for (int j = 0; j < A[i].length; ++j) {
					output += A[i][j];
					if (j < A[i].length-1) output += ",";
				}
				output += (i < A.length-1) ? "},\n" : "}\n";
			}
			output += "};";
			return output;
		}
	}
	
	public static boolean isMatrix(double[][] A) {
		//Sanity check
		if (A == null || A.length == 0) return false;
		else {
			int amountRows = A.length, sizeRowToCompare = A[0].length;
			if (sizeRowToCompare == 0) return false;
			else {
				for (int i = 1; i<amountRows;++i) {
					//Check the size of row -> if one is empty
					if (A[i].length != sizeRowToCompare) return false;
				}
				//if all tests passed, then return true
				return true;
			}
		}
	}
	
	public static void main(String[] args) {
		double[][] testMatrix = {
				{11.0,0.0,9.0,8.0,7.0},
				{18.0,0.0,18.0,18.0,18.0},
				{29.0,28.0,27.0,0.0,25.0},
				{6.0,6.0,0.0,6.0,6.0},
				{17.0,16.0,15.0,14.0,0.0}
				};
		double[][] A = {{ 3, 5.4, 7.777, 1.0/3}, {0, 1, 2, 3},
				{1.1, 2.222, 3}};
		double[][] B = { { 3.0, 5.4, 7.777, 0.3333333333333333},
				{ 0.0, 1.0, 2.0, 3.0},
				{ 1.1, 2.222, 3.0}};
		double [][] A1 = null;
		double [][] A2 = {{}};
		double [][] A3 = {{1.0,2},{1,2,3.3}};
		double [][] A4 = {{1.0,2,0},{1,2,3.3}};
		System.out.println(isMatrix(A1));
		System.out.println(isMatrix(A2));
		System.out.println(isMatrix(A3));
		System.out.println(isMatrix(A4));
		System.out.println(matrixToString(A1));
		//System.out.println(matrixToString(B));
	}
}