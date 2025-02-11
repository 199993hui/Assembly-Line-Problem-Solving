package assembly;

import java.util.Random;

public class assembly_line 
{
	static int min(int a, int b) //function used to compare the time required to leave the current station
	{ 
		return a < b ? a : b; 	
	}
	
	static int randomInput(int a) //generate random number
	{
		Random rand = new Random(); 
      	int input = rand.nextInt(100);
      	
      	if (a == 1) // generate random number of station which must be equal to or greater than 2
      	{
      		if(input > 1)
    	    	return input;
    	    else
    	    	return randomInput(1);
      	}
      	else	// generate random length of time for switching time and station time which cannot be 0
      	{
      		if(input > 0)
    	    	return input;
    	    else
    	    	return randomInput(0);
      	}
	    
	}
	
	static int station_num = randomInput(1);
	static int counter = 0, count_n = 0;
	
	static int assembly(int a[][], int t[][], int e[], int x[])
	{
		int L1[]= new int [station_num]; // used to store minimum accumulated time for each station in assembly line 1
		int L2[] =new int[station_num] ; // used to store minimum accumulated time for each station in assembly line 2
		int i;

//------count the PO in the base case-------------------------------------------------------------------------------
		counter += 12;	// count PO of 4 indexing into an array, 1 assignment and 1 arithmetic in each assembly line.
						// so total  = 6 x 2 = 12 for both assembly line
		
		// adding base case times which is the sum of the first station time and the entering time
		L1[0] = e[0] + a[0][0]; 
		L2[0] = e[1] + a[1][0];
	
//------count the PO before enter the loop----------------------------------------------------------------------------
		counter += 2;	// count PO of assignment (i = 1) and comparison (i<n)
		
		// Filling the dynamic programming tables L1[] and L2[] using recursive relations
		for (i = 1; i < station_num; i++)
		{
			// compute the minimum time needed to leave the current station in each line
			L1[i] = min(L1[i - 1] + a[0][i], L2[i - 1] + t[1][i] + a[0][i]);
			L2[i] = min(L2[i - 1] + a[1][i], L1[i - 1] + t[0][i] + a[1][i]);
		}
		

//------- count the PO in the loop, which will run (n-1) times ----------------------------------------------
			count_n += 3;	// count PO of 1 comparison (i<n), 1 assignment and 1 addition (i = (i+1)) 
			
			count_n += 32;	// 2 assembly lines, and each has 9 indexing into an array, 5 arithmetics and 1 assignment 
							// and 1 call method.(total each has 16 PO, then for 2 lines, total = 16 x 2 = 32)
			
			count_n += 4;	// count the PO in the function called "min" which are 1 return method and 1 comparison
							// (total 2 PO) then 2 assembly line will have 4 PO
//------------------------------------------------------------------------------------------------------------

//------- count the PO in the code of line 79 ----------------------------------------------------------------
		counter += 10;	// count the PO of 1 returning method, 1 calling method, 4 arithmetics 
						// and 4 indexing into an array
		
		counter += 2;	// count the PO in the function called "min" which are 1 return method and 1 comparison
		
		// finding final times and returning the minimum value 
		return min(L1[station_num-1] + x[0], L2[station_num-1] + x[1]);

	}
	
	
	// Driver code
	public static void main (String[] args) 
	{
		int a[][]= new int [2][station_num]; // a[][] array to store each station time
		int t[][] =new int[2][station_num] ; // t[][] array to store each switching time
		
		//generate and store the station time and switching time
		for (int i = 0; i < 2; i++)
		{
			t[i][0] = 0;
			a[i][0] = randomInput(0);
		}
		
		//generate and store the station time and switching time
		for (int i = 0; i < 2; i++)
			for (int j = 1; j < station_num; j++)
			{
				a[i][j] = randomInput(0); 
				t[i][j] = randomInput(0); 
			}
		
		//generate and store the entering time and exiting time where 		
		// e[] is for entering time and x[] is for exiting time
		int m = randomInput(0);
		int n = randomInput(0);
		int o = randomInput(0);
		int p = randomInput(0);
		
	    int e[] = {m, n}, x[] = {o, p}; 
		
	    // display the result including the number of station
	    System.out.println("Number of stations: " + station_num); 
	    // display the minimum time taken to pass through n stations
	    System.out.println("The minimum time taken is: " + assembly(a, t, e, x));
	 // display the number of primitive operation
	    System.out.println("Number of primitive operation =  " + count_n + "n" + (- count_n + counter));
	 // display the total number of primitive operation with n stations
	    System.out.println("Total number of primitive operation = " + (count_n*station_num + (- count_n + counter))); 
	}
}
