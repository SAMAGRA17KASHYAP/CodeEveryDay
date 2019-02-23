package com.looser.enterprise.disjoint.rank.pathCompression;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

import junit.framework.TestCase;

public class GraphTest extends TestCase {

	@Test
	public void testWithCycle() {
		Graph graph=		giveGraphWithCycle("GraphTestData.dat");
		Assert.assertTrue(graph.containsCycle());
	}
	
	@Test
	public void testWithoutCycle() {
		Graph graph=		giveGraphWithCycle("GraphTestDataWithoutCycle.dat");
		Assert.assertFalse(graph.containsCycle());
	}

	private Graph giveGraphWithCycle(String filename) {
		Graph graph =null;
		try (Stream<String> stream = Files.lines(Paths.get(filename))) {
			// First line is vertex count and edege count
			List<String> lines = stream.map(x -> x).collect(Collectors.toList());
			int vertexCount = Integer.valueOf((lines.get(0).split(" ")[0]));
			int edgeCount = Integer.valueOf((lines.get(0).split(" ")[1]));
			graph = new Graph(vertexCount, edgeCount);

			for (int i = 1; i < lines.size(); i++) {
				graph.edges[i - 1].src = Integer.valueOf((lines.get(i).split(" ")[0]));
				graph.edges[i - 1].dest = Integer.valueOf((lines.get(i).split(" ")[1]));

			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return graph;
	}
}
