/*
 * Authur: Mingxia Li
 * Date: Nov 20,2018
 */
 
//This class use Dijkstra algorithm to find the shorest path
public class Dijkstra {
	static int[] dijkstra(int network[][], int startLoc) {
		int vertexNum = network.length;
		int distance[] = new int[vertexNum]; // This array will hold the shorest distance 
		int flag[] = new int[vertexNum];//Use flag to mark the visited vertices

		//Initialize all distances as infinite numbers and all the flags to zero 
		for (int i = 1; i <= vertexNum-1; i++) {
			distance[i] = Integer.MAX_VALUE;
			flag[i] = 0;
		}
		// Distance of start vertex from itself is always 0
		distance[startLoc] = 0;

		// Find shortest path for all vertices
		for (int count = 1; count <= vertexNum - 1; count++) {
			int u = minDistance(distance, flag);
			flag[u] = 1;
			for (int i = 1; i <= vertexNum-1; i++)
				if (flag[i] == 0 && network[u][i] != 0 && distance[u] != Integer.MAX_VALUE
						&& distance[u] + network[u][i] < distance[i])
					distance[i] = distance[u] + network[u][i];
		}
		return distance;//Store it back
	}

	static int minDistance(int dist[], int flag[]) {
		int vertexNum = 51;
		int min = Integer.MAX_VALUE;
		int k = -1;
		for (int i = 1; i <= vertexNum-1; i++)// Find shortest path from the set of vertices not yet visited in shortest path tree
			if (flag[i] == 0 && dist[i] <= min) {
				min = dist[i];
				k = i;
			}
		return k;
	}

	//A function to find the shortest path/timecost between two points
	public static int getTimeCost(int start, int destination, int[][] network) {
		int[] dist = dijkstra(network, start);
		int c = dist.length;
		for (int i = 1; i <= c; i++) {
			if (i == destination) {
				return dist[i];
			}
		}
		return 0;
	}
}
