var persistmenu="yes" //"yes" or "no". Make sure each DIV content contains an incrementing ID starting at 1 (id="sub1", id="sub2", etc)
var persisttype="sitewide" //enter "sitewide" for menu to persist across site, "local" for this page only

if (document.getElementById){ //DynamicDrive.com change
document.write('<style type="text/css">\n')
document.write('.tbq{display: none;}\n')
document.write('</style>\n')
}
function SwitchMenu(obj){
	if(document.getElementById){
	var el = document.getElementById(obj);
	var ar = document.getElementById("masterdiv").getElementsByTagName("div"); //DynamicDrive.com change
		if(el.style.display != "block"){ //DynamicDrive.com change
			for (var i=0; i<ar.length; i++){
				if (ar[i].className=="tbq") //DynamicDrive.com change
				ar[i].style.display = "none";
			}
			el.style.display = "block";}
			else{
			el.style.display = "none";
		}
	}
}


