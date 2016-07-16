package mazeacode;
import java.util.*;
import java.io.*;
/*
 * globally 0 corresponds to start of function and 1 corresponds to end of the function
 *      i.e 0 = function calls
 * 	        1 = function definition 
 
 */

public class MazeACode 
{

	public static int start,end;
	static int namelength[]=new int[100];
	static String multidim[][]=new String[100][100];
	static ArrayList<String> read = new ArrayList<String>();
	static int numberOfFunctions;
	static String path=null;
	static int choice=1,loc;
	
	
	public static void main(String args[])
	{
	 path=args[0];	// Specfies the path of the file to be obfuscated
	 int i;
	 int parameters[]=new int[2];
	 int positions[]=new int[2];
	 parameters=mlcount(path);
	 numberOfFunctions=parameters[0];
	 loc=parameters[1];
	 int startpos[]=new int[numberOfFunctions];
	 int endPos[]=new int[numberOfFunctions];
	 startpos=funcStart(path,numberOfFunctions);
	 endPos=funcEnd(path,numberOfFunctions);
	 start = startpos[0];
	 end = endPos[0];	  
	 //System.out.println("No of methods:"+parameters[0]);
	 //System.out.println("Lines of code:"+parameters[1]);	  
	 read=arrayReturn(path);
	 keyvaluepair((numberOfFunctions-1),startpos);
	 
	 for(i=1;i<numberOfFunctions;i++)
	 {
		  stringReplacer(multidim[i][0],multidim[i][1]);	// performs name obfuscation by replacing all function names and function calls with corresponding random string 
		}
     if(numberOfFunctions>=10)	// selects the entry path based on parameters
     {
     	choice =2;
     }
     else if(loc>500)
     {
     	choice=3;
     }
     int random1;
     switch(choice)
     {
     	case 1:
     	
     	random1=randomNoGenerator(2);
     	if(random1==0)
     	{
     		String dummy1[]=dummyCalls();
     		endPos=insert(start, dummy1[0],0,endPos,0);
     		positions=randomPositionReturn(endPos);
     		endPos=insert(positions[0],dummy1[1],1,endPos,positions[1]);
     	}
     	else
     	{
     		String alloc1[]=alloc();
     		endPos=insert(start, alloc1[0],0,endPos,0);
     		positions=randomPositionReturn(endPos);
     		endPos=insert(positions[0],alloc1[1],1,endPos,positions[1]);
     	}
     	String bogus1=bogus();
     	endPos=insert(start,bogus1,0,endPos,0);
     	break;
     	
     	case 2:

     	random1=randomNoGenerator(2);
     	if(random1==0)
     	{
     		String dummy2[]=dummyCalls();
     		endPos=insert(start, dummy2[0],0,endPos,0);
     		positions=randomPositionReturn(endPos);
     		endPos=insert(positions[0],dummy2[1],1,endPos,positions[1]);
     	}                  
     	else
     	{
     		String startup1[]=startupRoutines();
     		endPos=insert(start, startup1[0],0,endPos,0);
     		positions=randomPositionReturn(endPos);
     		endPos=insert(positions[0],startup1[1],1,endPos,positions[1]);
     	}
     	String trampo1[]=trampolines();
     	endPos=insert(start, trampo1[0],0,endPos,0);		  
     	positions=randomPositionReturn(endPos);
     	endPos=insert(positions[0],trampo1[1],1,endPos,positions[1]);	 
     	break;
     	
     	case 3:

     	random1=randomNoGenerator(2);
     	if(random1==0)
     	{
     		String alloc2[]=alloc();
     		endPos=insert(start, alloc2[0],0,endPos,0);
     		positions=randomPositionReturn(endPos);
     		endPos=insert(positions[0],alloc2[1],1,endPos,positions[1]);
     	}
     	else
     	{
     		String startup2[]=startupRoutines();
     		endPos=insert(start, startup2[0],0,endPos,0);
     		positions=randomPositionReturn(endPos);
     		endPos=insert(positions[0],startup2[1],1,endPos,positions[1]);
     	}
     	String bogus2=bogus();
     	endPos=insert(start,bogus2,0,endPos,0);
     	break;
     	
     	default:
     	break;
     	
     	
     } 
     
     writes();
     
 }
 
        /*
        Read Functions 
        */
        public static int[] mlcount(String s)   // Returns number of methods and lines of code
        {
        	int n=0;
        	int ar[] = new int[2];
        	int lno=0;
        	File filer = new File(s);
        	try
        	{
        		BufferedReader br = new BufferedReader(new FileReader(filer));
        		String line;
        		int count=-1;
        		
        		while ((line = br.readLine()) != null) 
        		{
        			lno++;
        			if (line.contains("{"))
        			{
        				count++;
        			}
        			
        			if(line.contains("}"))
        			{
        				count--;
        				if(count==0)
        				{
        					n++;
        				}

        			}

        		}
        		br.close();
        	}
        	
        	catch (Exception e)
        	{
        		e.printStackTrace();
        	} 
        	ar[0]=n;
        	ar[1]=lno;
        	return ar;
        }
        
        public static int[] funcStart(String s, int n)   // Returns array with starting line no. of methods
        {
        	int lno=0,i=0;
        	int arr[]= new int[n];
        	File file = new File(s);
        	System.out.println(file);
        	try
        	{
        		BufferedReader br = new BufferedReader(new FileReader(file));
        		String line;
        		int count=-1,no=0;

        		while ((line = br.readLine()) != null) 
        		{
        			lno++;
        			if (line.contains("{"))
        			{
        				if(count==0)
        				{   
        					arr[i]=(lno);
        					i++;
        				}
        				count++;
        			}
        			if(line.contains("}"))
        			{
        				count--;

        				if(count==0)
        				{
        					no++;
        				}

        			}

        		}
        		
        	}
        	catch (Exception e)
        	{
        		e.printStackTrace();
        	} 
        	for(int j=0;i<n;i++)
        	{
        		System.out.println("func no "+(i+1)+" is "+1);
        	}
        	return arr;
        }
        
        public static int[] funcEnd(String s, int n)   // Returns array with ending line no. of methods
        {
        	int lno=0,i=0;
        	int arr[]= new int[n];
        	File file = new File(s);
        	try
        	{
        		BufferedReader br = new BufferedReader(new FileReader(file));
        		String line;
        		int count=-1;

        		while ((line = br.readLine()) != null) 
        		{
        			lno++;
        			if (line.contains("{"))
        			{
        				count++;
        			}
        			if(line.contains("}"))
        			{
        				count--;

        				if(count==0)
        				{
        					arr[i]=(lno);
        					i++;
        				}

        			}

        		}
        		
        	}
        	catch (Exception e)
        	{
        		e.printStackTrace();
        	} 
        	return arr;
        }


        /*
        ArrayList functions
        */
        public static ArrayList<String> arrayReturn(String path)   // converts input file into arraylist
        {
        	
        	ArrayList<String> read = new ArrayList<String>();
        	File filer = new File(path);
        	try
        	{
        		BufferedReader br = new BufferedReader(new FileReader(filer));
        		String line;
        		
        		while ((line = br.readLine()) != null) 
        		{
        			read.add(line);
        			
        		}
        		br.close();
        	}
        	
        	catch (Exception e)
        	{
        		e.printStackTrace();
        	} 
        	return read;
        }
        
        public static int[] insert(int lineNoToBeInsertedAt, String toBeInserted, int position, int[] endPos, int indexInEndPos)	//inserts content and updates pointers
        {
        	read.add(lineNoToBeInsertedAt,toBeInserted);
        	if(position==0)
        	{
        		start++;
        		for(int i=0;i<endPos.length;i++)
        		{
        			endPos[i]++;
        		}
        	}
        	if(position==1)
        	{
        		for(int i=indexInEndPos;i<endPos.length;i++)
        		{
        			endPos[i]++;
        		}
        	}
        	
        	return endPos;
        }
        
        public static int randomNoGenerator(int range)	// returns random number
        {
        	return (int)(Math.floor(Math.random() *range));
        	
        }
        
        public static void writes()		// writes into the input file
        {
        	String s=path.replaceAll(".java","-obfuscated.java"); 
        	File file1= new File(s);
        	try
        	{
        		BufferedWriter br1 = new BufferedWriter(new FileWriter(file1));
        		for(int i=0;i<read.size();i++)
        		{		    	
        			br1.write(read.get(i));
        			br1.newLine();

        		}
        		br1.close();
        	}
        	catch (Exception e)
        	{
        		e.printStackTrace();
        	} 
        }
        
        
	/*
        Name Obfuscation 
        */
        public static void stringReplacer(String toBeReplaced, String replacer)	//replaces  Strings in the arrayList
        {
        	String temp;
        	for(int i=0;i<read.size();i++)
        	{
        		temp=read.get(i);
        		int l=toBeReplaced.length();
        		String s=toBeReplaced+"(";
                         //System.out.println(s);
        			String z=replacer+"(";
        				if(temp.contains(toBeReplaced))
        				{
        					int si=temp.indexOf(s);
        					
        					temp=temp.replaceAll(toBeReplaced,replacer);
        					read.set(i, temp);
        				}
        			}
        		}
        		
        		public static String randomStringGenerator(int targetStringLength)	//generates random strings
        		{
        			StringBuilder buffer = new StringBuilder(targetStringLength);
        			for (int i = 0; i < targetStringLength; i++) {
        				int randomLimitedInt = 97 + (int)(new Random().nextFloat() * (122 - 97));
        				buffer.append((char) randomLimitedInt);
        			}
        			String generatedString = buffer.toString();
        			
        			
        			
        			return generatedString;
        			
        		}
        		
        		public static String nameFinder(ArrayList<String> content, int position)// returns name of all the functions except main
        		{
        			String s= content.get(position);
        			String name=null;
        			int pos=s.indexOf("(");
        				int pos2=pos;
        				while(pos2!=0)
        				{
        					if(s.charAt(pos2)== ' ')
        					{
        						name=s.substring((pos2+1), pos);
        						break;
        					}
        					else
        						pos2--;
        				}
        				return name;
        			}
        			
        			public static void keyvaluepair(int length, int[] functionStart)// generates key value pair for function names
        			{
        				String temp;
        				for(int i=0;i<=length;i++)
        				{
			              //System.out.println("in kvp, value to pass "+(functionStart[i]-2));
        					String tempo=read.get(functionStart[i]-1);
        					if(tempo.contains("("))
        					{
        						temp=nameFinder(read,(functionStart[i]-1));
        					}
        					else
        						temp=nameFinder(read,(functionStart[i]-2));
        					
        					if(!temp.equalsIgnoreCase("main"))
        					{
        						multidim[i][0]= temp;
        						namelength[i]=multidim[i][0].length();
        						multidim[i][1]=randomStringGenerator(namelength[i]);
        					}
        				}
        				for(int i=1;i<=length;i++)
        				{
        					System.out.println("String is "+multidim[i][0]);
        					System.out.println("Random String is "+multidim[i][1]);
        					System.out.println();
        				}
        				
        				
        			}
        			
        			public static int[] randomPositionReturn(int[] endpos)	// returns random position for insertion of function calls
        			{
        				int arr[]=new int[2];
        				int rand=randomNoGenerator(endpos.length);
        				arr[0]=endpos[rand];
        				arr[1]=rand;
        				return arr;
        				
        			}
        			
         /*
         String insertions 
         */
         public static String[] dummyCalls()		// returns strings for dummy calls
         {
         	int random2=randomNoGenerator(3);
         	String[] dummy0={"      fgdhgf(1,2);","public static void fgdhgf(int a,int b){\n      int c=a+b;\n      asdk(c);\n	}\n\n	public static void asdk(int c)\n	{\n	int z=c;\n	uegjdb();\n	}\n	public static void uegjdb()\n{\n	\n	}"};
         	String[] dummy1={"      float xy=2.45f;\n	float zz=123.4f;\n	   abjgvjfk(xy,zz);\n","public static void abjgvjfk(float ps,float df){\n      int ef=(int)(ps+df);\n      xykdjjf(ef);\n	}\n\n	public static void xykdjjf(int ef)\n	{\n	int z=ef;\n	qgfgfhjf();\n	}\n\n	public static void qgfgfhjf(){\n	\n}"};
         	String[] dummy2={"	vcdhsj();\n","public static void vcdhsj()\n	{\n djfg();\n	gjbfdjb();\n	}\n\n	public static void djfg()\n	{\n	vcdhsj();\n fgeuifb();\n	}\n\n	public static void fgeuifb()\n	{\n	djfg();\n	}\n	public static void gjbfdjb()\n	{\n\n	}\n"};
         	if(random2==0)
         		return dummy0;
         	if(random2==1)
         		return dummy1;
         	else
         		return dummy2;		
         }
         
         public static String[] startupRoutines()		// returns strings for startup routines
         {
         	int random3=randomNoGenerator(3);
         	String[] sr0={"	  asdfghj();","public static void asdfghj() 	{\n		\n		int[] jjhg = new int[5];\n		Random r = new Random();\n		int guij = 0;\n		while (guij < 5) {\n			int egd = r.nextInt(52);\n			boolean erga = false;\n			for (int ab=0; ab<guij; ab++) {\n				if (egd == jjhg[ab])\n					erga = true;\n			}\n\n			if (!erga) {\n				jjhg[guij] = egd;\n				guij++;\n			}\n		}\n\n		for (int ab=0; ab<jjhg.length; ab++)\n			yjhfk(jjhg[ab]);\n\n		int[] wefs = new int[13];\n		for (int xx=0; xx<wefs.length; xx++)\n		wefs[xx] = 0;\n\n		for (int ab=0; ab<jjhg.length; ab++)\n			wefs[jjhg[ab]%13]++;\n\n		for (int ab=0; ab<wefs.length; ab++) {\n			if (wefs[ab] > 1)\n			ryfbgk(ab);\n		}\n\n		wefs = new int[4];\n		for (int xx=0; xx<wefs.length; xx++)\n			wefs[xx] = 0;\n\n		for (int xx=0; xx<jjhg.length; xx++)\n			wefs[jjhg[xx]/13]++;\n\n		for (int ab=0; ab<wefs.length; ab++) {\n			if (wefs[ab] > 1)\n				abhcvkf(13*ab);\n		}\n		\n	}\n	\n\n	public static char abhcvkf(int dgfdk) {\n\n		if (dgfdk < 13)\n			return 'C';\n\n		else if (dgfdk < 26)\n			return 'D';\n\n		else if (dgfdk < 39)\n\n			return 'H';\n\n	else\n			return 'S';\n	}\n\n	public static char ryfbgk(int dgfdk) {\n\n		if (dgfdk%13 == 0)\n			return 'K';\n\n		else if (dgfdk%13 == 1)\n			return 'A'; \n\n		else if (dgfdk%13 == 10)\n			return 'T';\n\n		else if (dgfdk%13 == 11)\n			return 'J';\n\n		else if (dgfdk%13 == 12)\n			return 'Q';\n		\n		else\n			return (char)('0'+(dgfdk%13));\n	}\n	 \n	public static String yjhfk(int dgfdk) {\n		char fj = ryfbgk(dgfdk);\n		char sj = abhcvkf(dgfdk);\n		return \"\" + fj + sj;\n	}"};
         	String[] sr1={"	  tehdg();","    public static void tehdg() \n        {\n            int aDSsXDx=0;\n            \n            int trhg=0;\n\n            int ergd=0;\n            \n            int Counter=0;\n            int CarDoor=0;\n\n            int [] Door = new int[3];\n            \n            int Guess=0;\n            int OpenedDoor=0;\n\n            boolean aDSsXDx_DOOR=false;\n\n            for (int p=0; p<2; p++){\n                if (aDSsXDx==1){\n                    \n                    aDSsXDx_DOOR=true;\n                    trhg=0;\n                    ergd=0;\n                    \n               }\n                aDSsXDx++;\n            \n\n                for (int i=0; i<10000; i++)\n                {\n                    \n                    Door[0]=0;\n                    Door[1]=0;\n                    Door[2]=0;\n\n                    \n                    CarDoor = (int)((Math.random()*9)%3);\n                    Door[CarDoor]=1;\n                    Guess = (int)((Math.random()*9)%3);\n\n \n                                      OpenedDoor = furyg(Door,Guess);\n                    Door[OpenedDoor]=-1;\n\n      if (aDSsXDx_DOOR){\n                        for (int j=0; j<3; j++){\n                            if (Door[j] != -1 && Door[j] != Guess){\n                                Guess=j;\n                            }\n                        }\n                    }\n                \n                     if (Door[Guess]==1){\n                        trhg++;\n                   }\n                    else{\n                        ergd++;\n                    }\n                }\n              \n             }\n        }\n\n    public static int furyg(int[] Door,int Guess)\n        {\n            int GoatDoor=0;\n            int TryDoor=0;\n            boolean Found=false;\n            \n            while(!Found)\n                {\n                    TryDoor = (int)((Math.random()*9)%3);\n                    if (Door[TryDoor]==0){\n                        GoatDoor=TryDoor;          \n              Found=true;\n                    }\n                }\n            return GoatDoor;\n        }"};
         	String[] sr2={"    rhguvh(\"Arup\");\n    int bc=yfuehf(2,3)+yfuehf(5,2);\n    double adf=rdt(1000,4,bc);\n \n    Random r = new Random();\n    for (int i=0; i<10; i++)\n      fgdhgf(r);\n    System.out.println();\n","  public static void rhguvh(String name) {\n   String acd=name;\n  }\n    \n  public static int yfuehf(int base, int exp) {\n    \n    int ans = 1;\n\n    for (int i=0; i<exp; i++)\n      ans = ans*base;\n     \n    return ans;\n  }\n  \n public static double rdt(double principle, double intrate, int numyears) {\n    \n    for (int i=0; i<numyears; i++)\n      principle = principle*(1+intrate/100);\n      \n    return principle;\n  }\n  \n public static String fgdhgf(Random r) {\n\n    int num = r.nextInt(52);\n    \n    String card;\n    \n    if (num < 13)\n      card = \"C\";\n    \n    else if (num < 26)\n      card = \"D\";\n\n    else if (num < 39)\n      card = \"H\";\n\n    else\n      card = \"S\";\n\n    if (num%13 == 0)\n      card = \"K\" + card;\n \n    else if (num%13 == 1)\n      card = \"A\" + card;\n\n    else if (num%13 == 10)\n      card = \"T\" + card;\n\n    else if (num%13 == 11)\n      card = \"J\" + card;\n\n    else if (num%13 == 12)\n      card = \"Q\" + card;\n\n    else\n      card = (num%13) + card;\n\n    return card;\n  }"};
         	if(random3==0)
         		return sr0;
         	if(random3==1)
         		return sr1;
         	else
         		return sr2;		
         }
         
         public static String[] alloc()		// returns strings for allocations and deallocations
         {
         	int random4=randomNoGenerator(3);
         	String[] ad0={"	  azqopt();","        public static void azqopt() \n{\n  \n  double[] arr = {1.9, 2.9, 3.4, 3.5,12,134,1.9,1234.12,24335.32,12432134.425,12341.124,3142345.134,123124.235};\n\n  \n         double i = 0;\n         int j;\n      for (j = 0; j < arr.length; j++) {\n         i += arr[j];\n      }\n      \n      double max = arr[0];\n      for (j = 1; j < arr.length; j++) {\n         if (arr[j] > max) max = arr[j];\n      }\n   \n     \n   for (j = 0; j < arr.length; j++) {\n  \n  arr[j] = 0;\n\n    }\n}\n"};
         	String[] ad1={"	  eizmaskia(1259);","        public static long eizmaskia(int n) {\n\n               if(n<2)\n               {\n                  return 1;\n               }\n               else\n                  return n*eizmaskia(n-1);\n       }"};
         	String[] ad2={"	  usadilk();","        public static void usadilk()\n{\n        String str=\"In this age where the use of computers and networks related to them has become commonplace, there has developed problems concerning cyber security. Cyber security is an important factor to be considered if one is to be able to protect oneself from malicious people and software from the internet. Most of the threats to computer networks come from the internet and these are often intentional, having been developed by people with malicious intent. Cyber security is, therefore, an attempt by individuals to protect their personal information and other digital assets from attacks from the internet\";\n        char[] charArray= str.toCharArray();\n        char[] charArray2= charArray;\n        for(int i=0;i<charArray.length;i++)\n        {\n        	charArray2[i]='o';\n        }\n        String str2=new String(charArray2);\n\n\n}"};
         	if(random4==0)
         		return ad0;
         	if(random4==1)
         		return ad1;
         	else
         		return ad2;		
         }
         
         public static String[] trampolines()		// returns strings for trampolines
         {
         	String[] t0={"    alzsd();","	public static void alzsd( )\n	{	\n    int W=20;\n    int n=10;\n	int M=5;\n	int [] a={1,23,43,52,45,66,56,45,34,78};\n	int [] arr={56,32,12,5,98,67,90,74,2,18};\n	int [] wt={87,45,33,45,23,1,68,4,98,46};\n	int [] val={65,32,91,87,58,65,3,54,12,88};\n	int low=0;\n	int high=20;\n	int x;\n	Scanner scan = new Scanner(System.in);\n		for(int i=0;i<4;i++){\n            x=yazmkalsd(1,40);\n        \n        \n        if(x<=10)\n             gaplzx();\n           else if(x>10&&x<=20)\n            bzla();\n           else if(x>20&&x<=30)\n           {\n           \n        	wnzp(arr, 0, n);\n           }\n           	else\n           	\n            noapzx(wt, val, W, n);\n\n   }\n}\n\n\n	public static int yazmkalsd(int min, int max) \n{\n        Random foo = new Random();\n        int randomNumber = foo.nextInt((max + 1) - min) + min;\n       	int number_of_nodes=4;\n        int source=2;\n        return randomNumber;\n\n}\n\n\n\n	public static void gaplzx() \n    { \n        int ROW =100;\n      int[][] pascal  = new int[ROW +1][];\n    pascal[1] = new int[1 + 2];\n    pascal[1][1] = 1;\n    for (int i = 2; i <= ROW; i++) {\n        pascal[i] = new int[i + 2];\n        for (int j = 1; j < pascal[i].length - 1; j++) {\n            pascal[i][j] = pascal[i-1][j-1] + pascal[i-1][j];\n            }\n    }\n}\n    public  static void bzla()\n    {\n \n         int febCount = 100;\n         int[] feb = new int[febCount];\n         feb[0] = 0;\n         feb[1] = 1;\n         for(int i=2; i < febCount; i++){\n             feb[i] = feb[i-1] + feb[i-2];\n         }\n \n           }\n  \n        public static void wnzp(int[] a, int low, int high)\n    {\n        int N = high - low;\n        if (N <= 1) \n            return; \n        int mid = low + N/2; \n        wnzp(a, low, mid); \n        wnzp(a, mid, high); \n               int[] temp = new int[N];\n        int i = low, j = mid;\n        for (int k = 0; k < N; k++)\n        {\n            if (i == mid)  \n                temp[k] = a[j++];\n            else if (j == high) \n                temp[k] = a[i++];\n            else if (a[j]<a[i]) \n                temp[k] = a[j++];\n            else \n                temp[k] = a[i++];\n        }    \n        for (int k = 0; k < N; k++)\n            a[low + k] = temp[k];\n     }  \n    public  static void noapzx(int[] wt, int[] val, int W, int M)\n    {\n        int NEGATIVE_INFINITY = Integer.MIN_VALUE;\n        int[][] m = new int[M + 1][W + 1];\n        int[][] sol = new int[M + 1][W + 1];\n for (int i = 1; i < M; i++)\n        {\n            for (int j = 0; j <= W; j++)\n            {\n                int m1 = m[i - 1][j];\n                int m2 = NEGATIVE_INFINITY;\n                if (j >= wt[i])\n                    m2 = m[i - 1][j - wt[i]] + val[i];\n                \n                m[i][j] = Math.max(m1, m2);\n                sol[i][j] = m2 > m1 ? 1 : 0;\n            }\n        } \n         \n        int[] selected = new int[M + 1];\n        for (int n = M, w = W; n > 0; n--)\n        {\n            if (sol[n][w] != 0)\n            {\n                selected[n] = 1;\n                w = w - wt[n];\n            }\n            else\n                selected[n] = 0;\n        }\n               \n    }"};
         	
         	return t0;		
         }
         
         public static String bogus()		// returns string for bogus code
         {
         	int random6=randomNoGenerator(2);	
         	String bogus0="            double w2,w3,w1;\n            double tF=56;\n            double m1=0;\n            double m0=tF/2;\n            double e=tF;\n            boolean fin=false;\n            int p0,poll=10;\n            while (fin==false)\n            {\n\n                m1=(m0+(e/m0))/2;\n                if (m1>m0){\n                    if ((m1-m0)<0.00005){\n                        fin=true;\n                    }\n                }\n                else if (m0>m1){\n                    if ((m0-m1)<0.00005){\n                        fin=true;\n                    }\n                }\n                m0=m1; \n            }\n        w2=m1;\n        w1=4;\n\n        for(p0=2;p0<poll;p0++) \n         {    \n            w3=w1+w2;  \n                 w1=w2; \n              w2=w3;   \n       }    \n";
         	String bogus1="            double s;\n       double tTotal=0;\n       double tAverage=0;\n       int tNumberOfItems=0;\n       int c=0;\n       int fact=1;\n       int tLoops = 2;\n\n       for (int counter=0; counter <tLoops; counter++){\n\n           double tNumber = 0;\n           \n          while (tNumber != -1) {\n                tNumber = 2;\n                if (tNumber == -1){\n                    tAverage = tTotal/tNumberOfItems;\n                    tAverage = 0;\n                    tNumberOfItems=0;\n                    tTotal=0;\n  break;\n                }\n               tNumber=-1;  \n                        tTotal=tTotal + tNumber;\n               tNumberOfItems++;\n               \n                }\n       }\n      tLoops=17;\n      for ( c = 1 ; c <= tLoops ; c++ )\n            fact = fact*c;\n s=fact+5-6*4/1+5+7;\n";
         	if(random6==0)
         		return bogus0;
         	else
         		return bogus1;	
         }
         
         
         
     }
