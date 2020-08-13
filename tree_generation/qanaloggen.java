import java.util.*;
public class qanaloggen {
	static int m;
	static int n;
	public static void main(String[] args) throws Exception {
		Scanner in = new Scanner(System.in);
		System.out.print("n = ");
		m = in.nextInt();
		for (n = 1; n < 10; n++) {//mn is length of path; m colors; n of each colors
			System.out.println("For m = " + n);
    		Node[] nodes = new Node[n*m+1];
    		nodes[0] = new Node(0, 0, 0);
    		int[][] info = new int[m+1][2];// 0 = color of parent ; 1 = number used
    		for (int i = 0; i < m+1; i++) info[i][0] = -1;
    		long[][] ret = generate(0, info, new long[m*n][m], nodes, 0);
    		int f = factorial(m);
			System.out.println("table k : N(m, n, k)");
    		for (int i = 0; i < ret.length; i++) {
				System.out.print((i+1));
				for (int j = 0; j < ret[i].length; j++) System.out.print(" " + ret[i][j]/f);
				System.out.println();
			}
    	}
	}
	static long[][] generate(int cur, int[][] info, long[][] ret, Node[] nodes, int level) {
		if (cur == n*m) {
			int[] stuff = ucount(countLeaves(0, nodes), 0, nodes);
			int k = stuff[0];
			int u = stuff[1];
			ret[k-1][u-1]++;
			return ret;
		}
		for (int c = 0; c <= cur; c++) {
			if (nodes[c].level == level-1 && c >= nodes[cur].parindex) {
				for (int i = 1; i <= m; i++) {
					if (info[i][1] < n && (info[i][0] == nodes[c].color || info[i][0] == -1)) {
						int[][] copy = copy(info);
						copy[i][1]++;
						copy[i][0] = nodes[c].color;
						Node[] ncopy = copy(nodes);
						ncopy[cur+1] = new Node(i, c, level);
						ncopy[c].addChild(cur+1);
						ret = generate(cur+1, copy, ret, ncopy, level);
					}
				}
			}
			else if (nodes[c].level == level) {
				for (int i = 1; i <= m; i++) {
					if (info[i][1] < n && (info[i][0] == nodes[c].color || info[i][0] == -1)) {
						int[][] copy = copy(info);
						copy[i][1]++;
						copy[i][0] = nodes[c].color;
						Node[] ncopy = copy(nodes);
						ncopy[cur+1] = new Node(i, c, level+1);
						ncopy[c].addChild(cur+1);
						ret = generate(cur+1, copy, ret, ncopy, level+1);
					}
				}
			}
		}
		return ret;
	}
	static int countLeaves(int head, Node[] nodes) {
		if (nodes[head].children.size() == 0) return 1;
		int count = 0;
		for (int n : nodes[head].children) count += countLeaves(n, nodes);
		return count;
	}
	static int[] ucount (int count, int head, Node[] nodes) {
	    int[] ret = new int[2];
	    ret[0] = count;
	    ret[1] = nodes[head].children.size()/n;
		return ret;
	}
	static Node[] copy(Node[] arr) {
		Node[] ret = new Node[n*m+1];
		for (int i = 0; i < arr.length; i++) ret[i] = (arr[i] == null)?null:arr[i].copy();
		return ret;
	}
	static int[][] copy(int[][] arr) {
		int[][] ret = new int[m+1][2];
		for (int i = 0; i < arr.length; i++) for (int j = 0; j < arr[i].length; j++) ret[i][j] = arr[i][j];
		return ret;
	}
	static int factorial(int n) {
		if (n < 2) return 1;
		return n * factorial(n-1);
	}
	static class Node {
		int color;
		int parindex;
		int level;
		ArrayList<Integer> children;
		public Node(int color, int parindex, int level) {
			this.color = color;
			children = new ArrayList<Integer>();
			this.parindex = parindex;
			this.level = level;
		}
		void addChild(int n) {
			children.add(n);
		}
		Node copy() {
			Node n = new Node(this.color, this.parindex, this.level);
			n.children = (ArrayList)children.clone();
			return n;
		}
	}
}