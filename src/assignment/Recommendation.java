package assignment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Recommendation {
	
	//PRIVATE ATTRIBUTES
	private static Random random = new Random();

	private static double epsilon = 1e-5; // epsilon is threshold for difference in rmse
	//sadly I cannot use epsilon as a constant, because a threshold of 10^-5 make the computation time explode when d > 10

	private static ArrayList<HashMap<Integer, Double>> recommendationStats = new ArrayList<HashMap<Integer, Double>>();
	
	//PUBLIC ATTRIBUTES
	/* Etudiant 1 */
	public static String NAME1 = "Timoté Vaucher";
	public static int SCIPER1 = 246532;
	
	/* Etudiant 2 - laissez tel quel si vous avez codé le projet tout seul */
	public static String NAME2 = "Maxime Kjaer";
	public static int SCIPER2 = 250694;
	
	//PRIVATE METHODS
	private static double calculateStartingValue(double[][] M, int d) {
		int amountNonZero = 0;
		double sum = 0;
		for (int i = 0; i < M.length; ++i) {
			for (int j = 0; j < M[0].length; ++j) {
				if (Math.abs(M[i][j]) > epsilon) {
					++amountNonZero;
					sum += M[i][j];
				}
			}
		}
		return amountNonZero == 0 ? 0 : Math.sqrt(sum/(amountNonZero*d));
	}
	
	private static double[][] createStartingMatrix(int amountRow, int amountCol, double startingValue, double variation) {
		double[][] startingMatrix = new double[amountRow][amountCol];
		for (int i = 0; i < amountRow; ++i) {
			for (int j = 0; j < amountCol; ++j) {
				int randomSign = (random.nextInt(Integer.MAX_VALUE) % 2 == 0) ? -1 : 1;
				startingMatrix[i][j] = startingValue + random.nextDouble() * variation * randomSign;
			}
		}
		return startingMatrix;
	}
	
	private static void situateZeros(double[][] M) {
		//function that find the indexes of zeros in M
		for (int i = 0; i < M.length; ++i) {
			recommendationStats.add(new HashMap<Integer, Double>());
			for (int j = 0; j < M[i].length; ++j) {
				if (Math.abs(M[i][j]) < epsilon) {
					recommendationStats.get(i).put(j, 0.);
				}
			}
		}
	}
	
	public static void printArray(double[] tab) { //Debug purpose
		for (int i = 0; i < tab.length; ++i) System.out.print(i + " : [" + tab[i] + "], ");
		System.out.println();
	}
	
	public static void printArray(int[] tab) { //Debug purpose
		for (int i = 0; i < tab.length; ++i) System.out.print(i + " : [" + tab[i] + "], ");
		System.out.println();
	}

	public static double getScore(int[] recommendation, double[][] P, double[][] M) { //Debug purpose
		double score = 0;
		for (int i = 0; i < P.length; ++i) {
			double[] max = {0, 0};
			int[] index = {-1, -1};
			int amountZeroM = 0;
			for (int j = 0; j < P[i].length; ++j) {
				if (Math.abs(M[i][j]) < epsilon) {
					double value = P[i][j];
					if (value > max[1]) {
						if (value > max[0]) {
							max[1] = max[0];
							index[1] = index[0];
							max[0] = value;
							index[0] = j;
						}
						else {
							max[1] = value;
							index[1] = j;
						}
						++amountZeroM;
					}
				}
				if (amountZeroM == P[i].length) index[0] = -1;
			}
			if (index[0] == recommendation[i]) score += 1; //if best recommend
			else if (index[1] == recommendation[i]) score += 0.5; //if second best recommend
			//else no point :)
		}
		return score;
	}
	
	//PUBLIC METHODS - original ones, to be evaluated	
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
			if (k != l) { //else we return a n x m matrix containing k for all elements
				for (int i = 0; i < n; ++i) {
					for (int j = 0; j < m; ++j) {
						double randomMultipler = random.nextInt(Integer.MAX_VALUE)/(Integer.MAX_VALUE-1.);
						//create a number in [0,1] with a great amount of digits after the decimal point
						randomMatrix[i][j] = (l-k)*randomMultipler + k; // => in [k,l]
					}
				}
			}
			else {
				randomMatrix = createStartingMatrix(n, m, k, 0);
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
	
	public static double updateUElem(double[][] M, double[][] U, double[][] V, int r, int s) {
		double denominator = 0, numerator = 0, secondSum = 0;
		int d = V.length, m = V[0].length;

		for (int j = 0; j < m; ++j) {
			if (Math.abs(M[r][j]) > epsilon) {
				secondSum = 0.;
				for (int k = 0; k < d; ++k) {
					if (k != s) {
						secondSum += U[r][k] * V[k][j];
					}
				}
				double vsj =  V[s][j];
				numerator += vsj * (M[r][j] - secondSum);
				denominator += vsj * vsj; //sum_j of v_sj^2
			}
		}
		return denominator <= epsilon ? 0. : (numerator / denominator);
	}
	
	public static double updateVElem(double[][] M, double[][] U, double[][] V, int r, int s) {
		double denominator = 0, numerator = 0, secondSum = 0;
		int d = V.length, n = U.length;
		
		for (int i = 0; i < n; ++i) {
			if (Math.abs(M[i][s]) > epsilon) {
				secondSum = 0.;
				for (int k = 0; k < d; ++k) {
					if (k != r) {
						secondSum += U[i][k] * V[k][s];
					}
				}
				double uir = U[i][r];
				numerator += uir * (M[i][s] - secondSum);
				denominator += uir * uir; //sum_i of u_ir^2
			}
		}
		return denominator <= epsilon ? 0. : (numerator / denominator);
	}
	
	/* Concerning optimizeU/optimizeV
	 * After some testing, it has been proved that updating only once each element of U/V
	 * has resulted in the only huge optimization of recommend compared to updating U/V up to a threshold
	 */
	public static double[][] optimizeU( double[][] M, double[][] U, double[][] V) {
		int rows = U.length, cols = U[0].length;
		for (int i = 0; i < rows; ++i) {
			for (int j = 0; j < cols; ++j) {
				U[i][j] = updateUElem(M, U, V, i, j);
			}
		}
		return U;
	}

	public static double[][] optimizeV( double[][] M, double[][] U, double[][] V) {
		int rows = V.length, cols = V[0].length;
		for (int i = 0; i < rows; ++i) {
			for (int j = 0; j < cols; ++j) {
				V[i][j] = updateVElem(M, U, V, i, j);
			}
		}
		return V;
	}

	public static int[] recommend(double[][] M, int d) {
		if (!isMatrix(M) || d <= 0) return null;
		else {
			if (d > 10) epsilon = 1e-4;
			final int NUMBER_ITERATION = (int) Math.floor(35*Math.pow(d,-0.75)); //we want to have some flexibility due to input size
			//d=2 => 20, d=4 => 12, d=6 => 9, d=8 => 6, d=10 => 6, d=20 => 3
			situateZeros(M);
			double startingValue = calculateStartingValue(M, d);
			for (int i = 0; i < NUMBER_ITERATION; ++i) {
				double variation = i*startingValue/(NUMBER_ITERATION*2); //variation = 0-50 % of starting value
				double[][] U = createStartingMatrix(M.length, d, startingValue, variation);
				double[][] V = createStartingMatrix(d, M[0].length, startingValue, variation);
				double rmseStart = 0, rmseEnd = rmse(M, multiplyMatrix(U, V));
				int order = 0;
				do {
					rmseStart = rmseEnd;
					switch (order) {
					case 0 : //alternate order of optimize U/V
						optimizeU(M, U, V);
						optimizeV(M, U, V);
						++order;
						break;
					default :
						optimizeV(M, U, V);
						optimizeU(M, U, V);
						order = 0;
						break;
					}
					double[][] intermediateUV = multiplyMatrix(U, V);
					if (intermediateUV.length == 0 || intermediateUV[0].length == 0) break;
					else rmseEnd = rmse(M, intermediateUV);
				} while ((rmseStart - rmseEnd) >= epsilon);
				double[][] optimizedUV = multiplyMatrix(U, V);
				//Update of recommendationStats
				for (int j = 0; j < M.length; ++j) {
					if (!recommendationStats.get(j).isEmpty() && recommendationStats.get(j).size() != M[j].length) {
						double[] max = {0, 0};
						int[] index = {-1, -1};
						for (int k = 0; k < M[j].length; ++k) {
							//choose the highest score for indexes of M where there is a zero
							if(recommendationStats.get(j).containsKey(k)) {
								double value = optimizedUV[j][k];
								if (value > max[1]) {
									if (value > max[0]) {
										max[1] = max[0];
										index[1] = index[0];
										max[0] = value;
										index[0] = k;
									}
									else {
										max[1] = value;
										index[1] = k;
									}
								}
							}
						}
						if (index[0] > -1) { //treat only if it exists at least one 0 in row j
							//update the score
							double newScore = recommendationStats.get(j).get(index[0]) +  2/rmseEnd; //best recommendation in row
							recommendationStats.get(j).put(index[0], newScore);
							if (index[1] != index[0] && index[1] > -1) {
								newScore = recommendationStats.get(j).get(index[1]) +  1/rmseEnd; //second best
								recommendationStats.get(j).put(index[1], newScore);
							}
						}
					}
				}
			}
			
			//Decision of the best prediction for each user
			int[] output = new int[M.length];
			for (int i = 0; i < M.length; ++i) {
				if (recommendationStats.get(i).isEmpty() || recommendationStats.get(i).size() == M[i].length) output[i] = -1;
				else {
					double max = -1;
					int index = -1;
					//get the index where the value is at its maximum
					for (int j = 0; j < M[i].length; ++j) {
						if (recommendationStats.get(i).containsKey(j)) {
							double value = recommendationStats.get(i).get(j);
							if (value > max) {
								max = value;
								index = j;
							}
						}						
					}
					output[i] = index;
				}
			}
			return output;
		}
	}
}
