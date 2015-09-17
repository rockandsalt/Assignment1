// COMP 250 - ASSIGNMENT #1

// NAME: Marc Wang
// STUDENT ID: 260534272


import java.io.*;    
import java.util.*;

class StudentList {
    int studentID[];
    int numberOfStudents;
    String courseName;
    

    // A constructor that reads a StudentList from the given fileName and assigns it the given courseName
    public StudentList(String fileName, String course) {
	String line;
	int tempID[]=new int[4000000]; // this will only work if the number of students is less than 4000000.
	numberOfStudents=0;
	courseName=course;
	BufferedReader myFile;
	try {
	    myFile = new BufferedReader(new FileReader( fileName ) );	

	    while ( (line=myFile.readLine())!=null ) {
		tempID[numberOfStudents]=Integer.parseInt(line);
		numberOfStudents++;
	    }
	    studentID=new int[numberOfStudents];
	    for (int i=0;i<numberOfStudents;i++) {
		studentID[i]=tempID[i];
	    }
	} catch (Exception e) {System.out.println("Can't find file "+fileName);}
	
    } //__2015__


    // This method produces a String containing the information about a StudentList.
    public String toString() {
	return("Course name: " + courseName + "\n" + "Number of students: " + numberOfStudents + "Student IDs:" + Arrays.toString(studentID));
    }


    //A copy constructor that copies the content original StudentList 
    // ranging from minStudentIndex to maxStudentIndex inclusively.
    // Used for sort();
    public StudentList(StudentList original, int minStudentIndex, int maxStudentIndex) {
	numberOfStudents=maxStudentIndex-minStudentIndex+1;
	courseName=original.courseName;
	studentID=new int[original.numberOfStudents];
	for (int i=minStudentIndex;i<=maxStudentIndex;i++) {
	    studentID[i-minStudentIndex]=original.studentID[i];
	}
    }
    

    // A constructor that generates a random student list of the given size and assigns it the given courseName
    public StudentList(int size, String course) {
	int IDrange=2*size;
	studentID=new int[size];
	boolean[] usedID=new boolean[IDrange];
	for (int i=0;i<IDrange;i++) usedID[i]=false;
	for (int i=0;i<size;i++) {
	    int t;
	    do {
		t=(int)(Math.random()*IDrange);
	    } while (usedID[t]);
	    usedID[t]=true;
	    studentID[i]=t;
	}
	courseName=course;
	numberOfStudents=size;
    }


    // Sorts a student list using the MergeSort algorithm
    public void sort() {
	if (numberOfStudents<=1) return;
	StudentList left=new StudentList(this, 0, numberOfStudents/2-1); // the left half of the list
	StudentList right=new StudentList(this, numberOfStudents/2, numberOfStudents-1); // the right half of the list
	left.sort(); // recursively sort the left and right halves
	right.sort();

	// now merge the two sorted halves
	int tmpIndex=0;
	int indexLeft=0;
	int indexRight=0;
	while (tmpIndex<numberOfStudents) {
	    if (indexRight>=right.numberOfStudents || 
		(indexLeft<left.numberOfStudents && left.studentID[indexLeft]<=right.studentID[indexRight])) {
		studentID[tmpIndex]=left.studentID[indexLeft];
		indexLeft++;
	    }
	    else {
		studentID[tmpIndex]=right.studentID[indexRight];
		indexRight++;
	    }
	    tmpIndex++;
	    
	}
    }



    
    // Returns the number of students present in both lists L1 and L2
    public static int intersectionSizeNestedLoops(StudentList L1, StudentList L2) {
	/* Write your code for question 1 here */
     
    	List<Integer> mergedlist = new ArrayList<Integer>();
    	
    	for(int i = 0; i< L1.numberOfStudents; i++)
    	{
    		
    		for(int j = 0 ; j < L2.numberOfStudents; j++)
    		{
    			if(L1.studentID[i]==L2.studentID[j])
    			{
    				mergedlist.add(L1.studentID[i]);
    			}
    			
    		}
    		
    	}  		
	return mergedlist.size();
    }
    
    
    // This algorithm takes as input a sorted array of integers called mySortedArray, the number of elements it contains, and the student ID number to look for
    // It returns true if the array contains an element equal to ID, and false otherwise.
    public static boolean myBinarySearch(int mySortedArray[], int numberOfStudents, int ID) {
	/* For question 2, Write your implementation of the binary search algorithm here */
      
       int i;
       int upper = numberOfStudents-1;
       int lower = 0;
       while(upper>=lower) 
	   {
    	   i = (upper + lower)/2;
	       if(mySortedArray[i]> ID )//look upper part
	       {
	    	   upper = i-1;
	       }
	       else if(mySortedArray[i]< ID)
	       {
	    	   lower = i+1;
	       }
	       if(mySortedArray[i]==ID)
	       {
	    	   return true; 
	       }
	   };
       
	return false;
    }
    
    
    public static int intersectionSizeBinarySearch(StudentList L1, StudentList L2) {
	/* Write your code for question 2 here */
    	int counter = 0;
    	L2.sort();
    	for(int i =0 ; i< L1.numberOfStudents; i++)
    	{
    		if (StudentList.myBinarySearch(L2.studentID, L2.numberOfStudents, L1.studentID[i]))
    		{
    			counter = counter +1;
    		}
    		
    	}
    	
	return counter;
    }
    
    
    public static int intersectionSizeSortAndParallelPointers(StudentList L1, StudentList L2) {
	/* Write your code for question 3 here */
    	L1.sort();
    	L2.sort();
    	int inter = 0;
    	int ptL1=0; 
    	int ptL2=0; 
    	
    	while(ptL1<L1.numberOfStudents && ptL2 < L2.numberOfStudents)
    	{
    		if(L1.studentID[ptL1] == L2.studentID[ptL2])
    		{
				inter = inter+1;
				ptL1 = ptL1+1;
				ptL2 = ptL2+1;
    			
    		}
    		else if(L1.studentID[ptL1] > L2.studentID[ptL2])
    		{
    			ptL2 = ptL2+1;    			
    		}
    		else
    		{
    			ptL1 = ptL1+1;
    		}
    	}
    	
	return inter;
    }
    
    
    public static int intersectionSizeMergeAndSort(StudentList L1, StudentList L2) {
	/* Write your code for question 4 here */
    	List<Integer> mergedlist = new ArrayList<Integer>();
    	for(int i = 0; i< L1.numberOfStudents; i++)
    	{
    		mergedlist.add(L1.studentID[i]);	
    	}
    	for(int i = 0 ; i <L2.numberOfStudents; i++)
    	{
    		mergedlist.add(L2.studentID[i]);
    	}
    	
    	Collections.sort(mergedlist);
    	
    	int ptr = 0 ;
    	int counter=0;
    	while(ptr<mergedlist.size()-1)
    	{
    		if(mergedlist.get(ptr)==mergedlist.get(ptr+1))
    		{
    			counter= counter+1;
    			ptr = ptr+2;
    			
    		}
    		else
    		{
    			ptr = ptr+1; 
    		}
    		
    	}
    
	return counter;
    }
    
    
    
    /* The main method */
    /* Write code here to test your methods, and evaluate the running time of each.*/
    /* This method will not be marked */
    public static void main(String args[]) throws Exception {
	
	StudentList firstList;
	StudentList secondList;
	
	// This is how to read lists from files. Useful for debugging.
	
	//	firstList=new StudentList("COMP250.txt", "COMP250 - Introduction to Computer Science");
	//	secondList=new StudentList("MATH240.txt", "MATH240 - Discrete Mathematics");
	
	// get the time before starting the intersections
	long startTime = System.nanoTime();
	
	// repeat the process a certain number of times, to make more accurate average measurements.
	int numberRepetitions=3;
	for (int rep=0;rep<numberRepetitions;rep++) {
	    
	    // This is how to generate lists of random IDs. 
	    // For firstList, we generate 16 IDs
	    // For secondList, we generate 16 IDs
	    
	    firstList=new StudentList(32000, "COMP250 - Introduction to Computer Science"); 
	    secondList=new StudentList(1024000, "MATH240 - Discrete Mathematics"); 

	    // print the two lists, for future debugging purposes
	    //System.out.println(firstList);
	    //System.out.println(secondList);
	    
	    //run MergeSort
	    //int intersection = StudentList.intersectionSizeMergeAndSort(firstList, secondList);
	    
	    //run Parrallel search
	    //int intersection = StudentList.intersectionSizeSortAndParallelPointers(firstList, secondList);
	    
	    //run binary search
	    //int intersection = StudentList.intersectionSizeBinarySearch(firstList, secondList);
	    
	    // run nested loop
	    int intersection = StudentList.intersectionSizeNestedLoops(firstList, secondList);
	    
	    //System.out.println("The intersection size is: "+intersection);
	}
	
	// get the time after the intersection
	long endTime = System.nanoTime();
	
	
	System.out.println("Running time: "+ (float)(endTime-startTime)/numberRepetitions + " nanoseconds");
    }	
}


