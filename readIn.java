/*
 * Authur: Mingxia Li
 * Date: Nov 20,2018
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
//This class read in files and build arrays for the following classes
public class handleInput {
	private static final String filepath = "x.csv";
	private static final String filepath2 = "x.csv";
//Build a 2d array to store street network
	static int[][] buildMap() throws Exception {
		int[][] network = new int[51][51];
		File csv = new File(filepath);//read in file
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(csv));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line = null;
		for (int index = 1; index <= 50; index++) {//start from index 1, end at index 50
			line = br.readLine();
			if (line != null) {
				String items[] = line.split(",");
				for (int i = 1; i <= 50; i++) {
					network[index][i] = Integer.parseInt(items[i - 1]);
					if (network[index][i] == 0) {//if the transit time is zero, set the time to 999 to incidate that the way is blocked
						network[index][i] = 999;
					}
					// System.out.println(network[index][i]);
				}
			}
		}
		return network;
	}
	//build a array to store request
	static int[][] buildReq() throws Exception {
		int[][] request = new int[301][3];
		File csv = new File(filepath2);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(csv));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line = null;
		for (int index = 1; index <= 300; index++) {
			line = br.readLine();
			if (line != null) {
				String items[] = line.split(",");
				for (int i = 0; i < 3; i++) {
					request[index][i] = Integer.parseInt(items[i]);
					//System.out.println(request[index][i]);
				}
			}
		}
		return request;
	}
}
