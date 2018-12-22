package com.tigerit.exam;

public class Table {
	int col;
	int ro;
	String name="";
	String shortname="";
	String[][] data;
	String[] col_name;
	int[] show;
	Table(int row,int collumn) 
	{
	    ro=row;
	   col=collumn;
	   show=new int[col];
	  data=new String[ro][col];
	  col_name=new String[col];
	}
}