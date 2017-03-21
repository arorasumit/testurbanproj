package com.ibm.ioes.npd.utilities;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
/*
 * @author: Sri Ram
 */
public class FindCycles {

	public Integer mapping[][]=null;
	public Integer parent[]=null;
	public Integer color[]=null;
	
	
	public ArrayList<Integer> backedgeFrom = new ArrayList<Integer>();
	public ArrayList<Integer> backedgeTo = new ArrayList<Integer>();
	
	//int time=0;
	
	public static final int white=1;
	public static final int grey=2;
	public static final int black=3;

	
	public static void main(String[] args) {
		FindCycles find=new FindCycles();
		
		
		//get task data
		HashMap<Long,Integer> map_TaskIds_newIds=new HashMap<Long, Integer>();
		
		Long[] mapping_NewIds_TaskIds=null;
		
		//basic algo
		//populate both mapping map_TaskIds_newIds and mapping_NewIds_TaskIds
		//populate find.mapping
		//call doDFS on find
		//fetch parent and (edgeFrom,edgeTo)  : use these to find cycles
		
		
//		call doDFS on find
		find.doDFS();
		
		
//		fetch parent and (edgeFrom,edgeTo)  : use these to find cycles
		Integer parent[]=find.parent;
		ArrayList<Integer> edgeFrom = find.backedgeFrom;
		ArrayList<Integer> edgeTo = find.backedgeTo;
		
		//find cycles
		if(edgeFrom.size()>0)
		{
			System.err.println("Cycle Detected");
		}
		
		
		
	}


	public void doDFS() {
		
		int noOfVertices=mapping.length;
		color=new Integer[noOfVertices];
		parent=new Integer[noOfVertices];
		
		for(int vertex=0;vertex<noOfVertices;vertex++)
		{
			color[vertex]=white;
			parent[vertex]=null;
		}
		//time=0;
		for(int vertex=0;vertex<noOfVertices;vertex++)
		{
			if(color[vertex]==white)
			{
				DFSVisit(vertex);
			}
		}
		
	}


	private void DFSVisit(int vertex) {
		color[vertex]=grey;
		Integer[] adjacentList=mapping[vertex];
		for (Integer adjacent : adjacentList) {
			if(color[adjacent.intValue()]==white)
			{
				parent[adjacent.intValue()]=vertex;
				DFSVisit(adjacent);
			}
			else if(color[adjacent.intValue()]==grey)
			{
				backedgeFrom.add(vertex);
				backedgeTo.add(adjacent);
			}
		}
		color[vertex]=black;
	}
}

