function fnCheckAll()
{
   if(document.forms[0].chkService)
   {
	    var i;
	    var chkLength1=document.forms[0].chkService.length;
	    var myForm=document.getElementById('frmBillingTrigger');
	    var hdnlngth=document.frmBillingTrigger.itemServiceCount.value;
	    var counter=1;
	    
		if(document.getElementById('SelectAllChck').checked==true)
		{
		   
			        if(chkLength1==undefined)
	        
	                {
		                 if(document.forms[0].chkService.disabled==false)
			             {
			           
		          
		                    document.forms[0].chkService.checked=true;
		                    counter++;
		                   
		                   
							//var chkvalue=new String(document.forms[0].chkService.name);
							//changeVal(chkvalue.replace(chkvalue,""));
							changeVal(1);
		                 }
                  }
          
      else
      {    
	         //for (i =0; i<chkLength1; i++)
			   for (i =1; i<=hdnlngth; i++)
			   { 
		     		//if(myForm.chkService[i]!=null)
		     		if(document.frmBillingTrigger("chkService"+i)!=null)
		     		{
				     //if(myForm.chkService[i].disabled==false)
				     	if(document.frmBillingTrigger("chkService"+i).disabled==false)
						 {
						        document.frmBillingTrigger("chkService"+i).checked=true ;
						        counter++;
						      changeVal(i);
						}      
					}
		   
		      }
	}
	
	 if(counter==1)
		         {
					alert("No lIne Items Available for Selection");         
		         }
	         
		   
}

	else
	   {
	           if(chkLength1==undefined)
        
          {
          
                    
	                 if(document.forms[0].chkService.disabled==false)
		             {
		                 
	        
	                    document.forms[0].chkService.checked=false;
	                    counter++;
	                     changeVal(1);
	                 }
	                 
	             
	          
          }
          
      else
      {    
         //for (i =0; i<chkLength1; i++)
		   for (i =1; i<=hdnlngth; i++)
		   { 
		     if(document.frmBillingTrigger("chkService"+i)!=null)
		     {
			     if(document.frmBillingTrigger("chkService"+i).disabled==false)
			     
					 {
					        document.frmBillingTrigger("chkService"+i).checked=false ;
					        counter++;
					          changeVal(i);
					 }       
			}
		   
		   }
	}
	
		       if(counter==1)
		 
		         {
					alert("No lIne Items Available for Selection");         
		         }
	}
}	
	


		else
		   {
		      alert("No Line Item Available For Selection");
		   }
}
