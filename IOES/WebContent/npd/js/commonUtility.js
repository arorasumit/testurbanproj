// Author Varun
// This methid contains common utility for 
// 1. Trim
// 2. 
// 3.  
// 4. 
// 5.  

function TrimUsingRecursion(str) 
	{   
		 if(str.charAt(0) == " ")
		   { 
				str = TrimUsingRecursion(str.substring(1));
		   }
		
		   if (str.charAt(str.length-1) == " ")
		   {
				str = TrimUsingRecursion(str.substring(0,str.length-1));
		   }
		
		   return str;
	}
