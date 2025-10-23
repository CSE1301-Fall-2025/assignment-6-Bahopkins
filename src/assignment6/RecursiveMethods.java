package assignment6;

public class RecursiveMethods {

	/**
	 * Recursively computes base ^ exponent
	 * 
	 * @param base the base - can be positive or negative
	 * @param exp  the exponent - can be positive or negative
	 * @return base ^ exponent
	 */
	public static double exponent(int base, int exp) {
		if (exp == 0) {
			return 1;
		}
		else if (exp < 0) {
			return 1.0 / (base * exponent(base, -exp - 1));
		}
		else {
			return base * exponent(base, exp - 1);
		}			
	}

	/**
	 * Recursively compute the sum of elements in an array
	 * 
	 * @param array an array of integers
	 * @return the sum of the elements in values
	 */
	public static int arraySum(int[] array) {
		int i = 0;
		if (array.length == 0) {
			return 0;
		}
		else {
			return helperArraySum(array, i);
		}	
	}

	public static int helperArraySum(int[] array, int i) {
		if (i == array.length) {
			return 0;
		}
		else {
			return array[i] + helperArraySum(array, i + 1);
		}
	}

	/**
	 * Recursively computes string representations of dragon curves
	 * 
	 * @param n the desired degree of the dragon curve
	 * @return the nth dragon curve
	 */
	public static String dragon(int n) {
		if (n == 0) {
        	return "F-H";
    	}
		else {
			String prev = dragon(n - 1);

			// 1) Mark original H's so we don't touch H's created by step 2
			String s = prev.replace("H", "A");   // any placeholder not in the alphabet

			// 2) Apply F-substitution (creates new H's we must not re-substitute now)
			s = s.replace("F", "F-H");

			// 3) Convert only the original H's (the placeholders)
			s = s.replace("A", "F+H");

			return s;
		}
	}

	/**
	 * Finds the length of the longest path in the given 2D array from the specified
	 * start position.
	 * 
	 * @param chart 2D array of stones
	 * @param r     start position row
	 * @param c     start position column
	 * @return the length of the longest path that was found
	 */
	
	 // regular: just return the longest length
	public static int maxPathLength(boolean[][] chart, int r, int c) {
    	return maxPathLength(chart, r, c, null, 0);   // no painting
	}

	// call this to FILL numbers 1..L along one longest path into labels[][]
	public static int numberMaxPath(boolean[][] chart, int r, int c, int[][] labels) {
		return maxPathLength(chart, r, c, labels, 1); // paint starting at 1
	}

	// -------- core (few extra lines to optionally paint) --------
	private static int maxPathLength(boolean[][] chart, int r, int c, int[][] labels, int step) {
		// out of bounds OR not safe → length 0
		if (r < 0 || c < 0 || r >= chart.length || c >= chart[0].length || !chart[r][c]) {
			return 0;
		}
		// mark visited by flipping to false
		chart[r][c] = false;

		int up = maxPathLength(chart, r - 1, c, null, 0);
		int down = maxPathLength(chart, r + 1, c, null, 0);
		int left = maxPathLength(chart, r, c - 1, null, 0);
		int right = maxPathLength(chart, r, c + 1, null, 0);

		int best = Math.max(Math.max(up, down), Math.max(left, right));

		// ---- PAINT in just a few lines (only if labels != null) ----
		if (labels != null) {
			labels[r][c] = step;                  // write this tile's number
			int need = best;                      // need remaining length
			if (up == need) {     
				maxPathLength(chart, r - 1, c, labels, step + 1);
			}
			else if (down == need) {
				maxPathLength(chart, r + 1, c, labels, step + 1);
			}			
			else if (left == need) {
				maxPathLength(chart, r, c - 1, labels, step + 1);
			}
			else if (right == need) {
				maxPathLength(chart, r, c + 1, labels, step + 1);
			}
		}
		chart[r][c] = true;                       // unmark before returning
		return 1 + best;                          // this tile + best branch
	}
}