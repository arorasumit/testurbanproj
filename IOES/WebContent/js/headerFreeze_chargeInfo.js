var freezeRow=1; //change to row to freeze at
var freezeCol=2; //change to column to freeze at
var myRow=freezeRow;
var myCol=freezeCol;
var speed=100; //timeout speed
var myTable;
var noRows;
var myCells,ID,myCells1;

function setUp(){
	if(!myTable){myTable=document.getElementById("tableCharges");}
 	myCells = myTable.rows[0].cells.length;
 	//if(myTable.rows[2].cells.length != null)
 	//myCells2 = myTable.rows[2].cells.length;
	noRows=myTable.rows.length;
	//alert('myCells - '+myCells);
	//alert('myCells1 - '+myCells2);
	//alert('noRows - '+noRows);

	for( var x = 0; x < myTable.rows[0].cells.length; x++ ) {
		colWdth=myTable.rows[0].cells[x].offsetWidth;
		myTable.rows[0].cells[x].setAttribute("width",colWdth-4);

	}
}


function right(up){
	if(up)
	{		
			window.clearTimeout(ID);return;
	}
	setUp();

	if(myCol<(myCells)){
		for( var x = 0; x < noRows; x++ ) 
		{
			//alert('myCol_right -'+myCol)
			//alert(' x '+x)
			if(x!=1)
			{
				myTable.rows[x].cells[myCol].style.display="";
			}
		}
		if(myCol >freezeCol){myCol--;}
		ID = window.setTimeout('right()',speed);
	}
}

function right1(up){
	if(up){window.clearTimeout(ID);return;}
	setUp();

	if(myCol<(myCells)){
		for( var x = 0; x < noRows; x++ ) 
		{
			//alert('myCol_right -'+myCol)
			//alert(' x '+x)
			if(x!=1)
			{
				myTable.rows[x].cells[myCol].style.display="";
			}
		}
		if(myCol >freezeCol){myCol--;}
		ID = window.setTimeout('right1()',speed);
	}
}

function left(up){
	if(up){window.clearTimeout(ID);return;}
	setUp();

	if(myCol<(myCells-1)){
		for( var x = 0; x < noRows; x++ ) {
			//alert('myCol_left -'+myCol)
			//alert(' x '+x)
			if(x!=1)
			{
				myTable.rows[x].cells[myCol].style.display="none";
			}	
		}
		myCol++
		ID = window.setTimeout('left()',speed);

	}
}

function down(up){
	if(up){window.clearTimeout(ID);return;}
	setUp();

	if(myRow<(noRows-1)){
			myTable.rows[myRow].style.display="none";
			myRow++	;

			ID = window.setTimeout('down()',speed);
	}
}

function upp(up){
	if(up){window.clearTimeout(ID);return;}
	setUp();
	if(myRow<noRows){
		//alert('noRows_upp -'+noRows)
		myTable.rows[myRow].style.display="";
		if(myRow >freezeRow){myRow--;}
		ID = window.setTimeout('upp()',speed);
	}
}

function handle(delta) {
        if (delta <0)
        {
        		down();
                down(1);
        		
        }        
        else
        {
        		upp();
                upp(1);
        }        
}
 
function wheel(event){
        var delta = 0;
        if (!event) event = window.event;
        if (event.wheelDelta) {
                delta = event.wheelDelta/120; 
                if (window.opera) delta = -delta;
        } else if (event.detail) {
                delta = -event.detail/3;
        }
        if (delta)
                handle(delta);
}
 
/* Initialization code. */
if (window.addEventListener)
        window.addEventListener('DOMMouseScroll', wheel, false);
window.onmousewheel = document.onmousewheel = wheel;