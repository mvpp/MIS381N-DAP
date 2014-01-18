package xihan;

class LogAnalysis {
	// TODO(DAP): add helper methods, fields here

	static int numberOfEntries(String logFileString) { // count the number of
														// entries
		String[] strings = logFileString.split("\n");
		return strings.length;
	}

	private String[] getLogRecords(String logFileString) {
		String[] strings = logFileString.split("\n");
		return strings;
	}

	private String getAddress(String aLog) {
		return aLog.split(",")[1];
	}

	private String getName(String aLog) {
		return aLog.split(",")[2];
	}

	private String[] getAllAddress(String[] logs) {
		int count = 0;
		String[] address = new String[5000];
		for (String aLog : logs) {
			String aAddress = getAddress(aLog);
			boolean isAddressAlreadyExist = false;
			for (String str : address) {
				if (aAddress.equals(str)) {
					isAddressAlreadyExist = true;
					break;
				}
			}
			if (!isAddressAlreadyExist) {
				address[count] = aAddress;
				count++;
			}
		}
		String[] returned = new String[count];
		for (int i = 0; i < count; i++) {
			returned[i] = address[i];
			System.out.println(returned[i]);
		}
		return returned;
	}

	private String[] getAllNames(String[] logs) {
		int count = 0;
		String[] names = new String[1000];
		for (String aLog : logs) {
			String aName = getName(aLog);
			boolean isNameAlreadyExist = false;
			for (String str : names) {
				if (aName.equals(str)) {
					isNameAlreadyExist = true;
					break;
				}
			}
			if (!isNameAlreadyExist) {
				names[count] = aName;
				count++;
			}
		}
		String[] returned = new String[count];
		for (int i = 0; i < count; i++) {
			returned[i] = names[i];
			System.out.println(returned[i]);
		}
		return returned;
	}

	private boolean[][] getLogMatrix(String[] logs, String[] addresses,
			String[] names) {
		int numOfAdd = addresses.length;
		int numOfName = names.length;
		boolean[][] matrix = new boolean[numOfName][numOfAdd];

		for (int i = 0; i < numOfName; i++) {
			String currentName = names[i];
			for (int j = 0; j < numOfAdd; j++) {
				String currentAddress = addresses[j];

				for (String aLog : logs) {
					String name = getName(aLog);
					String add = getAddress(aLog);
					if (name.equals(currentName)) {
						if (add.equals(currentAddress)) {
							matrix[i][j] = true;
							break;
						}
					}
				}
			}
		}

		for (int k = 0; k < matrix.length; k++) {
			for (int l = 0; l < matrix[k].length; l++) {
				System.out.print(matrix[k][l] + " ");
			}
			System.out.print("\n");
		}
		return matrix;
	}

	private int[][] getCountForEachPair(boolean[][] matrix) {
		int numberOfAdd = matrix[0].length;
		int[][] counts = new int[numberOfAdd][numberOfAdd];

		for (boolean[] aBoolArrayForAPerson : matrix) {

			for (int i = 0; i < numberOfAdd; i++) {
				for (int j = 0; j < numberOfAdd; j++) {
					if (i != j) {
						if (aBoolArrayForAPerson[i] == true
								&& aBoolArrayForAPerson[j] == true) {
							counts[i][j]++;
						}
					}
				}
			}
		}

		for (int k = 0; k < counts.length; k++) {
			for (int l = 0; l < counts[k].length; l++) {
				System.out.print(counts[k][l] + " ");
			}
			System.out.print("\n");
		}

		return counts;
	}

	/**
	 * Take a string represention of a log file and return the pair of files
	 * that have the highest affinity.
	 * 
	 * You can assume there's no commas in file names, and that the
	 * logFileString is valid.
	 * 
	 * @param logFileString
	 *            is a string holding lines of log entries
	 * @see LogAnalysisTest
	 * @return pair of String names ordered by dictionary order, separated by a
	 *         comma, e.g.,
	 *         articles/finance/gs-2013.html,reports/oil/ms-9/9/2013.pdf.
	 */
	public String highestAffinityPair(String logFileString) {
		// TODO(DAP):write this method
		String r = null;

		String[] allRecords = getLogRecords(logFileString);
		String[] addresses = getAllAddress(allRecords);
		String[] names = getAllNames(allRecords);

		boolean[][] bools = getLogMatrix(allRecords, addresses, names);
		int[][] counts = getCountForEachPair(bools);

		int max = 0;
		int indexOfAdd1 = 0, indexOfAdd2 = 0;
		for (int k = 0; k < counts.length; k++) {
			for (int l = 0; l < counts[k].length; l++) {
				if (counts[k][l] > max) {
					max = counts[k][l];
					indexOfAdd1 = k;
					indexOfAdd2 = l;
				}
			}
		}

		String add1 = addresses[indexOfAdd1];
		String add2 = addresses[indexOfAdd2];

		if (add1.compareTo(add2) < 0) {
			r = add1 + "," + add2;
		} else {
			r = add2 + "," + add1;
		}

		System.out.println("max = " + max + " pair: " + r);
		return r;
	}
}
