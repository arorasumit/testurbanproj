function getBrowserHeight() 
{
    var intH = 0;
    var intW = 0;
   	var intY = 0;
   	intW = document.body.scrollWidth;    
    intH = document.body.scrollHeight;
    /*if(typeof window.innerWidth  == 'number' ) 
    {
       intH = window.innerHeight;
       intW = window.innerWidth;
    } 
    else if(document.documentElement && (document.documentElement.clientWidth || document.documentElement.clientHeight)) 
    {
	    //intH = document.documentElement.clientHeight;
        //intW = document.documentElement.clientWidth;
    }
    else if(document.body && (document.body.clientWidth || document.body.clientHeight)) 
    {
        intH = document.body.clientHeight;
        intW = document.body.clientWidth;
    }*/

    return { width: parseInt(intW), height: parseInt(intH) };
}  

function setLayerPosition() 
{
    var shadow = document.getElementById("shadow");
    var bws = getBrowserHeight();
    shadow.style.width = bws.width + "px";
    shadow.style.height = bws.height + "px";
    shadow = null;
}

function showLayer() 
{
	setLayerPosition();
    var shadow = document.getElementById("shadow");
    shadow.style.display = "block"; 
    shadow = null;
}

function showLayerExcel()
{
    setLayerPosition();
    var shadow = document.getElementById("shadow");
    shadow.innerHTML = "<font color='yellow' size='6'> Please wait.. <br/> <br/> <a href='#' onClick='javascript:removeLayer();'>(Click to return)</a></font>";
    shadow.style.display = "block";
    shadow = null;
}

function removeLayer()
{
    setLayerPosition();
    var shadow = document.getElementById("shadow");
    shadow.innerHTML = "<font color='yellow' size='6'> Please wait..</font>";
    shadow.style.display = "none";
    shadow = null;
}

