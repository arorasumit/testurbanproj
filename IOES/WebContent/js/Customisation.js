/******************************************************************************************************************
=========================================================
FileName	:Customisation.js
@Author		:Raghu
Date		:22-Aug-11
Purpose		:Use product catelog for customisation validation for particular Product.			
=========================================================
********************************************************************************************************************/

/*Function	:CheckMPLSWithEnterpriseSignage
*@Author	:Raghu
*Date		:22-Aug-11
*Purpose	:To Check MPLS With Enterprise Signage Only
**/
function CheckMPLSWithEnterpriseSignage(attributeID,attributeVal)
{
      var Total_Player=0;
      var Total_Intractive_Player=0;
      var Total_Non_Intaractive_Player=0;
      var Total_Player_id='';
	  var Total_Intractive_Player_id='';
	  var Total_Non_Intaractive_Player_id='';
	  var total=0;
     
      
      for(i=0;i<attributeID.length;i++)
		{
			if(attributeID[i]==1604 || attributeID[i]==1582)
			{
			  var id=attributeID[i];
			  var value=attributeVal[i];
			}
			if(attributeID[i]==1607 || attributeID[i]==1585 )
			{
			  var attValue=attributeVal[i];
			}
			if( attributeID[i]==1589  )
			{
			  Total_Player_id=attributeID[i];
			  Total_Player=attributeVal[i];
			  }
			if( attributeID[i]==1599 )
			{
			  Total_Intractive_Player_id=attributeID[i];
			  Total_Intractive_Player=attributeVal[i];
			}
			if( attributeID[i]==1601 )
			{
			  Total_Non_Intaractive_Player_id=attributeID[i];
			  Total_Non_Intaractive_Player=attributeVal[i];
			}
			
			
		}
		total=(Number(Total_Intractive_Player) + Number(Total_Non_Intaractive_Player));
		if(total > Total_Player)
		{
		  alert('Total of interactive and non interactive player should not exceed the global limit ');
	       return false
		}
  if((id == 1604 || id==1582) && value != 2)
	{
     if (attValue==1)
     {
      alert('MPLS is not allowed for this Service Flavours!!');
      return false;
     }
   }
}
function checkProductServiceType(attributeID,attributeVal,Billingformat)
{
      for(i=0;i<attributeID.length;i++)
		{
			if(attributeID[i]==2341 || attributeID[i]==2342)
			{
			  var attribute_id=attributeID[i];
			  var attribute_value=attributeVal[i];
			}
			
		}
	   if(Billingformat == 3 && attribute_value==1)
		{
	      alert('Please Select Valid Hardware Formate!!');
	      return false;
	     }
	   if(Billingformat ==2 && attribute_value==2)
		{
	      alert('Please Select Valid Hardware Formate!!');
	      return false;
	   }
}

