import java.util.Scanner;

//Main class used to calculate passengers total wait time
public class totalWaitTime {
	public static int[] calculateDriver(driver[] driver, int[][] map, int start, int end, int timestamp, int nextStart,
			int nextEnd, int nextReq) {
		int minWait = 999;//Initialize the wait time to allow update
		int index = 0;
		int result[] = new int[2];
		int driverNum = driver.length;
		boolean flag = false;
		for (int i = 0; i < driverNum; i++) {
			int timeCost = driver[i].getTravelTime();
			int currentLoc = driver[i].getCurrentLocation();
			if (timeCost == 0) {//The case that requires no travel time for next request
				driver[i].setTravelTime(start, end, map);
				timeCost = driver[i].getTravelTime();
			}
			if (timeCost < nextReq-timestamp) {//Send the driver if they are available
				flag = true;
				int wait = Dijkstra.getTimeCost(currentLoc, nextStart, map);
				if (wait < minWait) {//Compare the wait time and decide which driver should fulfill the request
					minWait = wait;
					index = i;
				}
			}
		}
		if (flag == false) {//The driver is busy currently
			for (int i = 0; i < driverNum; i++) {
				int timeCost = driver[i].getTravelTime();
				int currentLoc = driver[i].getCurrentLocation();
				if (timeCost == 0) {//The case that requires no travel time for next request
					driver[i].setTravelTime(start, end, map);
					timeCost = driver[i].getTravelTime();
				}
				int timeNeedToComplete = timestamp + timeCost - nextReq;//Time to complete current request
				int arrive = Dijkstra.getTimeCost(currentLoc, nextStart, map);//Time to travel to next request location
				int wait = arrive + timeNeedToComplete;
				if (wait < minWait) {//Compare the wait time and decide which driver should fullfill the request
					minWait = wait;
					index = i;
				}
			}
		}
		result[0] = index; //Store the driver info to result
		result[1] = minWait; //Store the wait time to result
		driver[index].setCurrentLocation(nextEnd);
		driver[index].setTravelTime(nextStart, nextEnd, map);
		return result;
	}

	public static void main(String[] args) throws Exception {
		int[][] map = handleInput.buildMap();
		int[][] req = handleInput.buildReq();
		int reqNum = req.length;
		int[][] result = new int[reqNum][2];
		int waitTime = 60;
		System.out.println("Please insert driver numbers:");
		Scanner sc = new Scanner(System.in);
		int driverNum = sc.nextInt();
		driver driver[] = new driver[driverNum];
		for (int i = 0; i < driverNum; i++) {
			driver[i] = new driver();
		}

		//Send the drivers to pick up their first requests
		for (int i = 1; i <= driverNum; i++) {
			result[i][1] = Dijkstra.getTimeCost(1, req[i][1], map);
			driver[i - 1].setCurrentLocation(req[i][2]);
			driver[i - 1].setTravelTime(req[i][1], req[i][2], map);
		}

		//The rest of unfulfilled requests 
		for (int k = driverNum; k < reqNum - 1; k++) {
			result[k + 1] = calculateDriver(driver, map, req[k][1], req[k][2], req[k][0], req[k + 1][1], req[k + 1][2],
					req[k + 1][0]);
		}

		for (int i = 1; i <= reqNum - 1; i++) {
		//	System.out.println(i + ": " + result[i][1] + " driver:" + result[i][0]);
			waitTime = waitTime + result[i][1];
		}
		System.out.println(waitTime);
	}
}
