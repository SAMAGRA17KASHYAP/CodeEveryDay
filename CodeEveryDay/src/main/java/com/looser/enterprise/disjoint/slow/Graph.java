package com.looser.enterprise.disjoint.slow;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Graph 
{
	
	class Edge
	{
		int src;
		int dest;
	}
	
	Edge[] edges;
	
	int vertexCount;
	int edgeCount;
	
	
	public Graph(int vertexCount, int edgeCount) {
		super();
		this.vertexCount = vertexCount;
		this.edgeCount = edgeCount;
		this.edges = Stream.iterate(0, n->n+1)
							.limit(edgeCount)
							.map(x-> new Edge())
							.toArray(Edge[]::new);
	}
	
	public int find(int[] parent,int i)
	{
		while(parent[i] != i)
		{
			i = parent[i];
		}
		return parent[i];
	}
	
	public void union(int[] parent,int x,int y)
	{
		int xParent = find(parent,x);
		int yParent = find(parent,y);
		parent[xParent] = yParent;
	}
	
	public boolean containsCycle()
	{
		int[] parent = IntStream.range(0, vertexCount).toArray();
		
		for (int i = 0; i < edges.length; i++) {
			int xRoot = find(parent,edges[i].src);
			int yRoot = find(parent,edges[i].dest);
			if(xRoot == yRoot)
				return true;
			union(parent,xRoot,yRoot);
		}
		return false;
	}
	
	
}
