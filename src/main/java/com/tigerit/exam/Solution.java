package com.tigerit.exam;


import static com.tigerit.exam.IO.*;


import static com.tigerit.exam.Table.*;

/**
 * All of your application logic should be placed inside this class.
 * Remember we will load your application from our custom container.
 * You may add private method inside this class but, make sure your
 * application's execution points start from inside run method.
 */
public class Solution implements Runnable {
    @Override
    public void run() {
        
    	Table tableA = null,tableB=null;
    	int test=readLineAsInteger();
    	//Number of iteration 
    	for(int iteration=0;iteration<test;iteration++)
    	{	
    		int tables=readLineAsInteger();
    		  Table[] t=new Table[tables];
    		  //input Number of tables
    		  for(int y=0;y<tables;y++)
    		  {
    			  String n=readLine();
    			  int[] dimention=readMultipleLineAsInteger();
    			  t[y]=new Table(dimention[0],dimention[1]);
    			  t[y].name=n;
    			  String collumnName = readLine();
    			  t[y].col_name= collumnName.split("\\s+");
    			  // Reading data from Input
    			  for(int r=0;r<dimention[0];r++)
    			  {
    				  String datas=readLine();
    				  t[y].data[r]=datas.split("\\s+");
    			  }
    		  }
    		  //Total number of Queries
    		  int querynum=readLineAsInteger();
    		  for(int y=0;y<querynum;y++)
    		  {
    			  String select=readLine();
    			    String from=readLine();
    			    String join=readLine();
    			    String on=readLine();
    			    String[] fromArray=tableselect(from);
    			    //handling "From" section from the queries
    			    
    			      for(int q=0;q<tables;q++)
    			      {
    			        if(t[q].name.equals(fromArray[0]))
    			        {
    			        
    			        if(fromArray.length==2)
        			    {
    			          t[q].shortname=fromArray[1];
    			          tableA=t[q];
    			         
    			          break;
    			        }
    			        else
    			        {
    			        	tableA=t[q];
    			        	break;
    			        }
    			      }
    			      }
    			   
    			    //handling "Join" section from the query
    			    String[] joinArray=tableselect(join);
    			    
    			      for(int q=0;q<tables;q++)
    			      {
    			        if(t[q].name.equals(joinArray[0]))
    			        {
    			        if(joinArray.length==2)
    	    		    {
    			          t[q].shortname=joinArray[1];
    			          tableB=t[q];
    			          break;
    			        }
    			        else {
    			        	tableB=t[q];
      			          break;
    			        }
    			      }
    			    }
    			   
    			    //Handling the "ON" section from the query
    			    int colSelectedA=0,colSelectedB=0;
    			    String[] conditionArray=condition(on);
    			    if(tableA.name.equals(conditionArray[0])||tableA.shortname.equals(conditionArray[0]))
    			    {
    			    	for(int col=0;col<tableA.col_name.length;col++)
    			    	{
    			    		if(tableA.col_name[col].equals(conditionArray[1]))
    			    		{
    			    			colSelectedA=col;
    			    		}
    			    	}
    			    	for(int col=0;col<tableB.col_name.length;col++)
    			    	{
    			    		if(tableB.col_name[col].equals(conditionArray[3]))
    			    		{
    			    			colSelectedB=col;
    			    		}
    			    	}
    			    }
    			    else if(tableA.name.equals(conditionArray[2])||tableA.shortname.equals(conditionArray[2]))
    			    {
    			    	for(int col=0;col<tableA.col_name.length;col++)
    			    	{
    			    		if(tableA.col_name[col].equals(conditionArray[3]))
    			    		{
    			    			colSelectedA=col;
    			    		}
    			    	}
    			    	for(int col=0;col<tableB.col_name.length;col++)
    			    	{
    			    		if(tableB.col_name[col].equals(conditionArray[1]))
    			    		{
    			    			colSelectedB=col;
    			    		}
    			    	}
    			    }
    			    //Selection of which columns to show
    			    String[] selectedcollumns=selectCollumn(select);
    			    //This section is to show all columns in the output
    			    
    			    if(selectedcollumns.length==0)
    			    {	
    			    	for (int i = 0; i < tableA.show.length; i++) {
							tableA.show[i]=1;							
						}
    			    	
    			    	for (int i = 0; i < tableB.show.length; i++) {
							tableB.show[i]=1;
						}
    			    	
    			    }
    			    
    			    //This section is for showing selected columns in output
    			    else
    			    {
    			    	//which columns to show from tableA
    			    	for (int i = 0; i < selectedcollumns.length; i++) {
							String[] s=selectedcollumns[i].split("\\.");
							if(s[0].equals(tableA.name)||s[0].equals(tableA.shortname))
							{
								for (int j = 0; j < tableA.col_name.length; j++) {
									if(s[1].equals(tableA.col_name[j]))
									{
										tableA.show[j]=1;
									}
								}
							}
							//which columns to show from tableB
							else if(s[0].equals(tableB.name)||s[0].equals(tableB.shortname))
							{
								for (int j = 0; j < tableB.col_name.length; j++) {
									if(s[1].equals(tableB.col_name[j]))
									{
										tableB.show[j]=1;
									}
								}
							}
						}
    			    }
    			   
    			    printLine("");
    			  //Printing the columns Names
    			    String firstline="";
    			    
    			    
    			    for (int i = 0; i < tableA.col; i++) {
						if(tableA.show[i]==1)
						{
							firstline=firstline+tableA.col_name[i]+" ";
							
						}
					}
    			    for (int i = 0; i < tableB.col; i++) {
						if(tableB.show[i]==1)
						{
							firstline=firstline+tableB.col_name[i]+" ";
							
						}
					}
    			    printLine(firstline);
    			    //showing the data
    			    for (int i = 0; i < tableA.ro; i++) {
    			    	
    			    	for (int tB = 0; tB <tableB.ro; tB++) {
							
    			    		String dataline="";
						if(tableA.data[i][0].equals(tableB.data[tB][0])&&tableA.data[i][colSelectedA].equals(tableB.data[tB][colSelectedB]))
						{ 
							
							for (int j = 0; j <tableA.col; j++) {
								if(tableA.show[j]==1)
								{
									dataline=dataline+tableA.data[i][j]+" ";
								}
							}	
							for (int j = 0; j <tableB.col; j++) {
								if(tableB.show[j]==1)
								{
									dataline=dataline+tableB.data[tB][j]+" ";
								}
							}
							printLine(dataline);
						}
						
    			    	}	
					}
    			 tableA.shortname="";
    			 tableA.show=new int[tableA.col];
    			 tableB.shortname="";
    			 tableB.show=new int[tableB.col];
    		  }
    	}
    	
    	
    }
    
    private static String[] tableselect(String tablesel)
    {
      String[] tablename=tablesel.substring(5).split("\\s+");
      
      return tablename;
                   
    }
    private static String[] condition(String conditionstr){
    	 String con=conditionstr.substring(3);
    	 String[] variables=con.split("=");
    	 for(int x=0;x<variables.length;x++)
    	 {
    	   variables[x]=variables[x].trim();
    	 }
    	 String[] first=variables[0].split("\\.");
    	 String[] second=variables[1].split("\\.");
    	 String[] var=new String[4];
    	 var[0]=first[0];
    	 var[1]=first[1];
    	 var[2]=second[0];
    	 var[3]=second[1];
    	 return var;
    	 }
    // selection of the columns here
    public static String[] selectCollumn(String selection){
       if(selection.contains("*"))
       {
         String[] star=new String[0];
         return star;
       }
       else
       {
         String[] collumns = selection.substring(7).split(",");
         for(int x=0;x<collumns.length;x++)
         {
           collumns[x]=collumns[x].trim();
         }
         return collumns;
       }
       
      
    }
}