<%@ taglib uri="/WEB-INF/jsp/kavachart-taglib.tld" prefix="chart" %>
<html>
<head>
<title>KavaChart Pie Chart Samples</title>
	<link type="text/css" rel="stylesheet" href="../../../documentation/style.css">
</head>
<P>
Pie charts are great for comparing the individual values within a single
set of data.  They can be used to show each data value as a percentage of 
a whole at a single point in time.  
<p>
KavaChart ProServe uses a chartType of "pieApp" for pie charting.
<p>
Use the source code to this page as a guide in creating your own pie charts.
You can also use the KavaChart <a href="http://www.ve.com/editor/" target="_blank">
ChartWizard</a>
to design your own pie charts.
<hr>
<table border=0><tr><td valign=top>
This chart uses a real data to create a chart showing the
U.S. population breakdown according to age groups as of the 2000 census. 
While this chart uses static data, you'd likely pull your data from a database
and use a DataProvider, or populate the "dataset0yValues" param with live
data.
<p>
The pie labels can show up to three pieces of information, the percentage
of the total pie, the actual numeric value, and a text label.  By default
only the percent label is shown, however here we have decided to also display
the text label by setting the parameter "textLabelsOn" = "true".  The 
numeric value of the slice can be seen by placing the mouse pointer over that
slice.  
<p>
This chart also uses a background image to make the overall chart more 
aesthetically pleasing.  Here we are using the image 
javachart/html/images/blur_circle.jpg, which provides a slight shadow which
we can place the actual pie over using the appropriate style properties.  
</td><td valign=top>
<chart:streamed chartType="pieApp" > 
	<chart:param name="antialiasOn" value="true" />
	<chart:param name="width" value="400" />
	<chart:param name="height" value="400" />
	<chart:param name="backgroundImage" value="<%=application.getRealPath("C:/examples/samples/images/blur_circle.jpg")%>" />
	<chart:param name="backgroundTexture" value="-1" />
	<chart:param name="dataset0yValues" value="19175798, 41077577, 39183891, 39891724, 45148527, 37677952, 24274684, 18390986, 12361180, 4239587" />
	<chart:param name="dataset0Labels" value="Under 5 years, 5 to 14 years, 15 to 24 years, 25 to 34 years, 35 to 44 years, 45 to 54 years, 55 to 64 years, 65 to 74 years, 75 to 84 years, 85 years and |over" />
	<chart:param name="xLoc" value="0.49" />
	<chart:param name="yLoc" value="0.51" />
	<chart:param name="textLabelsOn" value="true" />
	<chart:param name="pieWidth" value="0.58" />
	<chart:param name="pieHeight" value="0.58" />
	<chart:param name="titleString" value="U.S. Population by Age Group" />
	<chart:param name="subTitleString" value="from the 2000 U.S. census" />
	<chart:param name="titleFont" value="Arial,14,1" />
	<chart:param name="subTitleFont" value="Arial,12,2" />
	<chart:param name="dwellUseXValue" value="false" />
	<chart:param name="dwellYString" value="XX people" />
</chart:streamed>
</td></tr></table>
<hr>
<table border=0><tr><td valign=top><tr><td valign="top">
<chart:streamed chartType="pieApp">
	<chart:param name="antialiasOn" value="true" />
	<chart:param name="width" value="480" />
	<chart:param name="height" value="300" />
	<chart:param name="colorPalette" value="web_seattle" />
	<chart:param name="dataset0Colors" value="0xE3D782, 0xC7555A, 0x7D1242, 0x51779C, 0x82CBD9, 0x1A2A67, 0xBCCCC1, 0xC7555A" />
	<chart:param name="titleString" value="Ethnic Population Breakdown for California" />
	<chart:param name="subTitleString" value="from the 2000 U.S. census" />
	<chart:param name="titleFont" value="Arial,14,1" />
	<chart:param name="titleX" value="0.1" />
	<chart:param name="subTitleX" value="0.25" />
	<chart:param name="textLabelsOn" value="anything" />
	<chart:param name="dataset0yValues" value="10966556, 15816790, 2181926, 178984, 3648860, 103736, 71681, 903115" />
	<chart:param name="dataset0Labels" value="Hispanic or Latino (of any race), White, Black or African American, American Indian and Alaska Native, Asian, Native Hawaiian and Other Pacific Islander, Some other race, Two or more races" />
	<chart:param name="pieWidth" value="0.4" />
	<chart:param name="xLoc" value="0.3" />
	<chart:param name="yLoc" value="0.45" />
	<chart:param name="startDegrees" value="20" />
	<chart:param name="explodeSlice" value="4" />
	<chart:param name="percentPrecision" value="2" />
	<chart:param name="dwellUseXValue" value="false" />
	<chart:param name="dwellYString" value="Value: XX" />
</chart:streamed>
</td><td valign=top>
<p>
Like the first chart, this pie chart also uses U.S. census data, however this 
time it is the ethnic breakdown of California.  Here we've used an exploded 
slice to highlight a particular value in our data.  In this case we've chosen
to explode slice number four, by setting the parameter "explodeSlice"="4".  
The slice numbering actually starts at 0--you can see the slice number of
any slice on the pie by placing your mouse pointer over it.  
<p>
We could also have chosen to explode multiple slices by using the 
"explodeSlices" parameter, which takes a list of explosion factors for each 
slice.  The explosion factor is how much you want the slice to stick out 
of the pie.  A value of "0" would be no explosion at all, while a value of 
"0.05" would give you an explosion like the one in this chart.  
<p>
As you can see, there are some very small slices on this chart.  By
comparing the labels in the first chart to these labels, you can see that
the labels have automatically moved around, and some pointers have been
extended to make sure the labels don't overlap.  We've changed the
starting angle of the first slice using the "startDegrees" parameter to make
sure the labels fit nicely in the image area, as well as changed the default
colors of the pie (using "dataset0Colors") to make colors of the small
slices more distinct.
<p>
The default precision of the percentage labels is 0, meaning that the labels
will all be integers like in the first chart.  Since there are some very 
small slices on this chart, the default percentages would be 0.  The
"percentPrecision" parameter can be used to force a greater decimal 
precision in your percent labels, as was done here.
</td></tr></table>
<hr>
<table border=0><tr><td valign=top><tr><td valign=top>
Here is a pie chart showing the budget allocations for the state of Maine.  
This chart uses a legend instead of having the text labels around the pie 
itself.  You can also see the text labels by moving the mouse pointer over
a slice.  
<p>
The legend uses a transparent background so that the legend can 
overlap the pie without completely covering it.  Any chart component 
except the background can
be rendered transparent by setting its color to "transparent".
<p>
You'll notice that one of the text labels has a comma in it.  Since the 
default delimiter in our properties is a comma, this means we have to change
it with the "delimiter" parameter in order to use a comma in our labels.  In
this case we changed the delimiter to "|".
</td><td valign=top>
<chart:streamed chartType="pieApp" >
	<chart:param name="antialiasOn" value="true" />
	<chart:param name="width" value="480" />
	<chart:param name="height" value="300" />
	<chart:param name="delimiter" value="|" />
	<chart:param name="explodeSlice" value="2" />
	<chart:param name="titleString" value="Budget Allocation for the State of Maine" />
	<chart:param name="subTitleString" value="FY '04-'05, all values in millions of dollars" />
	<chart:param name="titleY" value="0.96" />
	<chart:param name="valueLabelsOn" value="anything" />
	<chart:param name="percentLabelsOn" value="anything" />
	<chart:param name="dataset0yValues" value="544.392| 95.672| 2378.792| 1579.955| 145.108| 7.660|  448.019| 17.767" />
	<chart:param name="dataset0Labels" value="Governmental Support & Operations| Economic Development & Work Force Training| Education| Health & Human Services| Natural Resources Development and Protection| Transportation Safety and Development| Justice & Protection| Arts, Heritage and Cultural Enrichment" />
	<chart:param name="currencyLabels" value="true" />
	<chart:param name="percentPrecision" value="2" />
	<chart:param name="3D" value="true" />
	<chart:param name="xLoc" value="0.60" />
	<chart:param name="pieWidth" value="0.5" />
	<chart:param name="legendOn" value="true" />
	<chart:param name="legendVertical" value="true" />
	<chart:param name="legendllX" value="0.0" />
	<chart:param name="legendllY" value="0.2" />
	<chart:param name="legendLabelFont" value="Arial|10|1" />
	<chart:param name="legendColor" value="transparent" />
	<chart:param name="iconWidth" value=".02" />
	<chart:param name="iconHeight" value=".02" />
	<chart:param name="iconGap" value=".015" />
	<chart:param name="dwellUseXValue" value="false" />
	<chart:param name="dwellUseYValue" value="false" />
	<chart:param name="dwellUseLabelString" value="true" />
	<chart:param name="colorPalette" value="web_pastel" />
</chart:streamed>
</td>
</tr>
</table>
<hr>
<table>
<tr><td width="500">
<%
	for(int i=0;i<4;i++){
		int labelPos = i;
		String textLabels = "textLabelsOff";
		if(i>2){
			labelPos = 2;
			textLabels = "textLabelsOn";
		}
%>
<chart:streamed chartType="pieApp" >
	<chart:param name="colorPalette" value="web_prague" />
	<chart:param name="antialiasOn" value="true" />
	<chart:param name="dataset0yValues" value="5,6,7" />
	<chart:param name="dataset0Labels" value="Calif, New York, Florida" />
	<chart:param name="<%=textLabels%>" value="true" />
	<chart:param name="labelPosition" value="<%=Integer.toString(labelPos)%>" />
	<chart:param name="dwellUseXValue" value="false" />
	<chart:param name="dwellYString" value="XX" />
</chart:streamed>
<%
	}
%>
</td>
<td valign="top">
<p>
Pie labels can appear inside each slice, at the edge of the slice, or connected
to a pointer.  Labels on the outside of the pie automatically adjust to
accomodate dynamic data best.  Although the examples to the left work well
with any label position, each of the pie charts above represents a typical
dynamic data case, with both large and small slices.
<p>
Most pie charts should use antialiasing for legible text and smooth edges.
<p>
If you examine the JSP source code, you'll also see that the chart tag
is operating inside a loop.  You can use KavaChart's tag library with 
scriptlet logic, JSTL, JSF or other coding techniques.
</td></tr>
</table>

