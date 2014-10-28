package assignment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Recommendation {
	
	//PRIVATE ATTRIBUTES
	private static Random random = new Random();

	private static final double EPSILON = 2e-4; // epsilon is threshold for difference in rmse

	private static ArrayList<HashMap<Integer, Double>> recommendationStats = new ArrayList<HashMap<Integer, Double>>();
	
	//For debugging purpose
	private static int row = 10, col = 20, d = 4;

	private static double[][] P = multiplyMatrix(createMatrix(row, d, 1, 5), createMatrix(d, col, 1, 5)), M = new double[row][col];
	
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
				if (Math.abs(M[i][j]) > EPSILON) {
					++amountNonZero;
					sum += M[i][j];
				}
			}
		}
		//System.out.println("sum : " + sum + ", 0 :" + amountNonZero + ", d : " + d);
		return amountNonZero == 0 ? 0 : Math.sqrt(sum/(amountNonZero*d));
	}
	
	private static double[][] createStartingMatrix(int amountRow, int amountCol, double startingValue, double variation) {
		//To be reworked on
		double[][] startingMatrix = new double[amountRow][amountCol];
		for (int i = 0; i < amountRow; ++i) {
			for (int j = 0; j < amountCol; ++j) {
				int randomSign = random.nextInt(2) == 0 ? -1 : 1;
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
				if (Math.abs(M[i][j]) < EPSILON) {
					recommendationStats.get(i).put(j, 0.);
				}
			}
		}
	}
	
	private static void printArray(double[] tab) { //Debug purpose
		for (int i = 0; i < tab.length; ++i) System.out.print(i + " : [" + tab[i] + "], ");
		System.out.println();
	}
	
	private static void printArray(int[] tab) { //Debug purpose
		for (int i = 0; i < tab.length; ++i) System.out.print(i + " : [" + tab[i] + "], ");
		System.out.println();
	}


	private static int[] getRealRecommendation() { //Debug purpose
		int[] ouptut = new int[P.length];
		for (int i = 0; i < P.length; ++i) {
			int index = -1, amountZeroM = 0;
			double max = 0;
			for (int j = 0; j < P[i].length; ++j) {
				if (Math.abs(M[i][j]) < EPSILON) {
					if (P[i][j] > max) {
						max = P[i][j];
						index = j;
					}
					++amountZeroM;
				}
			}
			if (amountZeroM == P[i].length) index = -1;
			ouptut[i] = index;
		}
		return ouptut;
	}
	
	private static double getScore(int[] recommendation) { //Debug purpose
		double score = 0;
		for (int i = 0; i < P.length; ++i) {
			double[] max = {0, 0};
			int[] index = {-1, -1};
			int amountZeroM = 0;
			for (int j = 0; j < P[i].length; ++j) {
				if (Math.abs(M[i][j]) < EPSILON) {
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
			if (index[0] == recommendation[i]) score += 1;
			else if (index[1] == recommendation[i]) score += 0.5;
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
	
	public static double updateUElem(double[][] M, double[][] U, double[][] V, int r, int s) {
		double denominator = 0, numerator = 0, secondSum = 0;
		int d = V.length, m = V[0].length;

		for (int j = 0; j < m; ++j) {
			if (Math.abs(M[r][j]) > EPSILON) {
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
		return denominator <= EPSILON ? 0. : (numerator / denominator);
	}
	
	public static double updateVElem(double[][] M, double[][] U, double[][] V, int r, int s) {
		double denominator = 0, numerator = 0, secondSum = 0;
		int d = V.length, n = U.length;
		
		for (int i = 0; i < n; ++i) {
			if (Math.abs(M[i][s]) > EPSILON) {
				secondSum = 0.;
				for (int k = 0; k < d; ++k) {
					if (k != r) {
						secondSum += U[i][k] * V[k][s];
					}
				}
				double uir = U[i][r];
				numerator += uir * (M[i][s] - secondSum);
				denominator += uir * uir;
			}
		}
		return denominator <= EPSILON ? 0. : (numerator / denominator);
	}
	
	public static double[][] optimizeU( double[][] M, double[][] U, double[][] V) {
		double rmseStart = 0, rmseEnd = rmse(M, multiplyMatrix(U, V));
		int rows = U.length, cols = U[0].length;
		
		do {
			rmseStart = rmseEnd;
			for (int i = 0; i < rows; ++i) {
				for (int j = 0; j < cols; ++j) {
					U[i][j] = updateUElem(M, U, V, i, j);
				}
			}
			rmseEnd = rmse(M, multiplyMatrix(U, V));
		} while ((rmseStart - rmseEnd) >= EPSILON);
		return U;
	}

	public static double[][] optimizeV( double[][] M, double[][] U, double[][] V) {
		double rmseStart = 0, rmseEnd = rmse(M, multiplyMatrix(U, V));
		int rows = V.length, cols = V[0].length;
		
		do {
			rmseStart = rmseEnd;
			for (int i = 0; i < rows; ++i) {
				for (int j = 0; j < cols; ++j) {
					V[i][j] = updateVElem(M, U, V, i, j);
				}
			}
			rmseEnd = rmse(M, multiplyMatrix(U, V));
		} while ((rmseStart - rmseEnd) >= EPSILON);
		return V;
	}

	public static int[] recommend(double[][] M, int d) {
		if (!isMatrix(M) || d <= 0) return null;
		else {
			final int NUMBER_ITERATION = 10;
			situateZeros(M);
			double startingValue = calculateStartingValue(M, d);
			double avgRMSEp = 0, avgRMSEm = 0;
			System.out.println(startingValue);

			int order = 0;

			for (int i = 0; i < NUMBER_ITERATION; ++i) {
				System.out.print(i + " ");
				double[][] U = createStartingMatrix(M.length, d, startingValue, i/NUMBER_ITERATION);
				double[][] V = createStartingMatrix(d, M[0].length, startingValue, i/NUMBER_ITERATION);
				double rmseStart = 0, rmseEnd = rmse(M, multiplyMatrix(U, V));
				do {
					rmseStart = rmseEnd;
					if (order == 0) {
						optimizeU(M, U, V);
						optimizeV(M, U, V);
						order = (order + 1) % 2;
					}
					else {
						optimizeV(M, U, V);
						optimizeU(M, U, V);
						order = (order + 1) % 2;
					}

					double[][] intermediateUV = multiplyMatrix(U, V);
					if (intermediateUV.length == 0 || intermediateUV[0].length == 0) break;
					else rmseEnd = rmse(M, intermediateUV);
				} while ((rmseStart - rmseEnd) >= 2*EPSILON);
				double[][] optimizedUV = multiplyMatrix(U, V);
				avgRMSEp += rmse(P, optimizedUV);
				avgRMSEm += rmseEnd;
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
						if (index[0] > -1) {
							//update the score
							double newScore = recommendationStats.get(j).get(index[0]) +  2/rmseEnd;
							recommendationStats.get(j).put(index[0], newScore);
							if (index[1] != index[0] && index[1] > -1) {
								newScore = recommendationStats.get(j).get(index[1]) +  1/rmseEnd;
								recommendationStats.get(j).put(index[1], newScore);
							}
						}
					}
				}
			}
			
			//Decision of the best prediction for each user
			int[] output = new int[M.length];
			int amountZerosM = 0;
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
							++amountZerosM;
						}						
					}
					output[i] = index;

				}
			}
			
			//DEBUG PURPOSE
			System.out.println("\navg rmse with original P : " + avgRMSEp/NUMBER_ITERATION);
			System.out.println("avg rmse with M : " + avgRMSEm/NUMBER_ITERATION);
			System.out.println("amount of zeros in M : " + amountZerosM + "/" + col*row + " : " + (100.*amountZerosM)/(col*row) + "%");
			
			return output;
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < row; ++i) {
			for (int j = 0; j < col; ++j) {
				M[i][j] = P[i][j];
			}
		}
		for (int i = 0; i < (row * col / 2.); ++i) {
			M[random.nextInt(row)][random.nextInt(col)] = 0;
		}

		double scoreSum = 0;

		for (int i = 0; i < 30; ++i) {
			long startTime = System.currentTimeMillis();
			int[] recommendation = recommend(M, d);
			long stopTime = System.currentTimeMillis();
			System.out.println("Temps écoulé pour recommender : " + (stopTime-startTime)/1000. + " s");
			System.out.println("RECOMMMENDATIONS: ");
			printArray(recommendation);
			/*int[] real = getRealRecommendation();
			int score = 0;*/
			/*ArrayList<Integer> error = new ArrayList<Integer>();
			for (int i = 0; i < recommendation.length; ++i) {
				if (recommendation[i] == real[i]) ++score;
				else {
					System.out.println("error on line " + i + " expected : " + real[i] + ", recommended : " + recommendation[i]);
					printArray(P[i]);
					printArray(M[i]);
					error.add(i);
				}
			}*/
			scoreSum += getScore(recommendation);
			System.out.println(/*score + " out of " + recommendation.length + " : " + (100.*score)/recommendation.length + "%\ntrue */"score : " + getScore(recommendation) + "\n");
		}
		System.out.println("SCORE TOTAL: " + scoreSum);
		System.out.println("Moyenne sur 30 itérations: " + scoreSum / 30);
	}
}
