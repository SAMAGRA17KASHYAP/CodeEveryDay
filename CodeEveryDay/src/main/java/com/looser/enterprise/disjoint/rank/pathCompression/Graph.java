package com.looser.enterprise.disjoint.rank.pathCompression;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Graph {

	class Edge implements Comparable<Integer> {
		int src;
		int dest;
		int weight;
		boolean isPresentInMST;

		@Override
		public int compareTo(Integer weight) {
			return Integer.compare(this.weight, weight);
		}

	}

	Edge[] edges;

	int vertexCount;
	int edgeCount;

	class Subset {
		int parent;
		int rank;

		Subset(int parent, int rank) {
			this.parent = parent;
			this.rank = rank;
		}
	}

	public Graph(int vertexCount, int edgeCount) {
		super();
		this.vertexCount = vertexCount;
		this.edgeCount = edgeCount;
		this.edges = Stream.iterate(0, n -> n + 1).limit(edgeCount).map(x -> new Edge()).toArray(Edge[]::new);
	}

	public int find(Subset[] subsets, int i) {
		if (subsets[i].parent != i) {
			subsets[i].parent = find(subsets, subsets[i].parent);
		}
		return subsets[i].parent;
	}

	public void union(Subset[] subsets, int x, int y) {
		int xParent = find(subsets, x);
		int yParent = find(subsets, y);
		if (subsets[xParent].rank < subsets[yParent].rank) {
			subsets[xParent].parent = subsets[yParent].parent;
		} else if (subsets[yParent].rank < subsets[xParent].rank) {
			subsets[yParent].parent = subsets[xParent].parent;
		} else {
			subsets[yParent].parent = subsets[xParent].parent;
			subsets[xParent].rank++;
		}
	}

	public boolean containsCycle() {
		Subset[] subsets = IntStream.range(0, vertexCount).mapToObj(x -> new Subset(x, 0)).toArray(Subset[]::new);

		for (int i = 0; i < edges.length; i++) {
			int xRoot = find(subsets, edges[i].src);
			int yRoot = find(subsets, edges[i].dest);
			if (xRoot == yRoot)
				return true;
			union(subsets, xRoot, yRoot);
		}
		return false;
	}

	public void kruskalMST() {
		Arrays.sort(edges);
		Subset[] subsets = IntStream.range(0, vertexCount).mapToObj(x -> new Subset(x, 0)).toArray(Subset[]::new);
		for (int i = 0; i < edges.length; i++) {
			int xRoot = find(subsets, edges[i].src);
			int yRoot = find(subsets, edges[i].dest);
			edges[i].isPresentInMST = xRoot != yRoot;
		}
	}

}
