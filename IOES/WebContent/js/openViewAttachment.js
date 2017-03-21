/**
 * 
 */


function getAttachmentList(accountid , orderno , crmaccountno)
{

	var width  = 700;
	var height = 300;
	var left   = (screen.width  - width)/2;
	var top    = (screen.height - height)/2;
	var params = 'width='+width+', height='+height;
		params += ', top='+top+', left='+left;
		params += ', directories=no';
		params += ', location=no';
		params += ', menubar=no';
		params += ', resizable=no';
		params += ', scrollbars=yes';
		params += ', status=no';
		params += ', toolbar=no';
	var path = gb_path+"/NewOrderAction.do?method=goToDownloadedFilePage&orderNo="+orderno+"&accountNo="+accountid+"&crmAccountNo="+crmaccountno+"&fileTypeId=0&sentMethod=0";	
	window.open(path,"FileDownloadWindow",params); 						
}
