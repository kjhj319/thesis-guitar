<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
	<title>Tab Page</title>
	
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<link rel="stylesheet" href="css/navbar.css">
	<link rel="stylesheet" href="css/style.css">
	<script src="js/jquery.min.js"></script>
	<script src="js/more.js"></script>
	<script src="js/Chart.js"></script>
	<script src="js/bootstrap.min.js"></script>
	
	<style>
		.chart-legend ul{
			margin-left: auto;
			text-align: left;
			list-style-type: none;
		}
		.chart-legend li span{
		    display: inline-block;
		    width: 12px;
		    height: 12px;
		    margin-right: 5px;
		}
	</style>
	<script>
	function changeview(){
		if ( $('#cont1').is(':visible') ){
			 $("#" + "cont1").hide();
			 $("#" + "cont2").show();
			 $("#" + "change").text('View Graph1');
		}
		
		else{
			 $("#" + "cont2").hide();
			 $("#" + "cont1").show();
			 $("#" + "change").text('View Graph2');
		}
	}
	</script>
</head>
<body>
	<div class="container-fluid2"> 
		<nav class="navbar navbar-inverse " >
		<!-- Second navbar for sign in -->
			<div class="container">
		<!-- Brand and toggle get grouped for better mobile display -->
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed" 
						data-toggle="collapse" data-target="#navbar-collapse-2">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					
					<a class="navbar-brand" href="index.jsp">
						<img src="img/vmm4.png">
					</a>
				
				</div>
			
				<!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse" id="navbar-collapse-2">
				  <ul class="nav navbar-nav navbar-right">
					<li><a href="index.jsp">Home</a></li>
					<li><a href="upload.jsp">Upload Tab</a></li>
					<li><a href="AllTabsServlet">All Tabs</a></li>
					<c:choose>
							  <c:when test="${information[0] == null }">
							  	<li><a href = "login.jsp">Log in</a></li>
								<li><a href = "register.jsp">Register</a></li>
							  </c:when>
							  <c:otherwise>
							    <li><a href = "#">
									<c:out value="${information[0]}"/>
									</a>
								</li>
							    <li><a id = "proceedDashboard" href = "dashboard.jsp">
									Edit Profile
								</a>
								</li>
							    <li><a id = "proceedLogOut" href = "LogoutServlet">
									Logout
									</a>
								</li>
							  </c:otherwise>
					</c:choose>
				
					<li>
					  <a class="btn btn-default btn-outline btn-circle collapsed"  
						data-toggle="collapse" href="#nav-search" 
						aria-expanded="false" aria-controls="nav-search">
						Search
					</a>
					</li>
					
				  </ul>
			   
				  <div class="collapse nav navbar-nav nav-collapse slide-down" 
					id="nav-search">
					<form class="navbar-form navbar-right" role="search" 
						action = "SearchServlet" method = "POST">
					  <div class="form-group">
					  	<select class="form-control" name="searchType">
					  		<option value="None">None</option>
					  		<option value="Artist">Artist</option>
					  		<option value="Title">Song Title</option>
					  		<option value="Difficulty">Difficulty(1-8)</option>
					  		<option value="Uploader">Uploader</option>
					  	</select>
						<input type="text" name="search" class="form-control"
							placeholder="Search" />
					  </div>
					  <button type="submit" class="btn btn-danger">
						<span class="glyphicon glyphicon-search" 
							aria-hidden="true">
						</span>
					  </button>
					</form>
				  </div>
				</div><!-- /.navbar-collapse -->
			</div><!-- /.container -->
		</nav><!-- /.navbar -->
		
		<c:choose>
			<c:when test="${not empty tab}">
			<div class="bodytab">
			<h1> ${tab.title} by ${tab.artist} </h1>
			</div>
			<div class="upstep1"> <h1> Difficulty Information</h1> </div>
			<div class="uploadcont">
			
			<div class="center">
			<center>
			<button id="change" class="toggle btn btn-primary" onclick="changeview()">
			View Graph2
			</button>
			<h2> Overall Difficulty: 
				<c:forEach var="i" begin="1" end= "${tab.difficulty}">
					★
				</c:forEach>
			</h2> 
			</center>
			<br>
			
			<div style="width:100%" id="cont1">
			<canvas id="radarChart" width="600" height="400"></canvas>
				<script>
			var one = "${tab.ratioOfChord}";
			var two = "${tab.localDist}";
			var three = "${tab.chordDist_2}";
			var four = "${tab.chordDist_4}";
			var five = "${tab.fingerDist_0}";
			var six = "${tab.fingerDist_1}";
			var seven = "${tab.fretDist_0}";
			var eight 
			var data = {
				    labels: ["ratio Of Chord", "local hand Distribution", "1-Note", "2-note", "3-note", "4-note", "5-note", "6-note", "finger Distribution-0", "finger Distribution-1", "finger Distribution-2", "fret Distribution-0", "fret Distribution-1", "fret Distribution-2", "fret Distribution-3"],
				    datasets: [
				        {
				        	label: "My Second dataset",
				            fillColor: "rgba(151,187,205,0.2)",
				            strokeColor: "rgba(151,187,205,1)",
				            pointColor: "rgba(151,187,205,1)",
				            pointStrokeColor: "#fff",
				            pointHighlightFill: "#fff",
				            pointHighlightStroke: "rgba(151,187,205,1)",
				            data: ["${tab.ratioOfChord}", "${tab.localDist}", "${tab.chordDist_1}", "${tab.chordDist_2}", 
				                   "${tab.chordDist_3}", "${tab.chordDist_4}", "${tab.chordDist_5}", "${tab.chordDist_6}",
				                   "${tab.fingerDist_0}", "${tab.fingerDist_1}", "${tab.fingerDist_2}", "${tab.fretDist_0}",
				                   "${tab.fretDist_1}", "${tab.fretDist_2}", "${tab.fretDist_3}"]
				        },
				    ]
				};
			var ctx2 = document.getElementById("radarChart").getContext("2d");
			var myNewChart = new Chart(ctx2).Radar(data);

			new Chart(ctx2).Radar(data);
			</script><br>
			</div>
			
			<div style="width:100%;" id="cont2">
			<table style="width:100%">
			
				<tr>
					<td style="width:40%" colspan="2"><h4>NOTE AND CHORD DIFFICULTY FEATURES</h4></td>
					<td style="width:60%" colspan="2"><h4>MONOPHONIC NOTE DIFFICULTY FEATURES</h4></td>
				</tr>
				<tr>
					<td width="30%"><div id="chordChart-legend" class="chart-legend"></div></td>
					<td width="0%"><canvas id="barChart" width="400" height="300"></canvas></td>
					<td width="10%"><div id="chordChart-legend2" class="chart-legend"></div></td>
					<td width="0%"><canvas id="barChart2" width="400" height="300"></canvas></td>
				</tr>
				
				<tr>
					<td><br><br><br><br></td>
				</tr>
				
				<tr>
					<td colspan="2"><center><h4>Chord</h4></center></td>
					<td colspan="2"><center><h4>TECHNIQUES</h4></center></td>
				</tr>
				
				<tr>
					<td width="10%"><div id="chordChart-legend3" class="chart-legend"></div></td>
					<td width="0%"><canvas id="barChart3" width="400" height="300"></canvas></td>
					<td width="20%"><div id="chordChart-legend4" class="chart-legend"></div></td>
					<td width="0%"><canvas id="barChart4" width="400" height="300"></canvas></td>
				</tr>
			
			</table>
			</div>
			
			
			<script>
			var data = [
							{
								value: "${tab.fretDist_0}",
								label: "Not Adjacent Note",
								color: "rgb(222,100,100)"
							},
							{
								value: "${tab.fretDist_1}",
								label: "Adjacent Note",
								color: "rgb(255,170,75)"
							},
							{
								value: "${tab.fretDist_2}",
								label: "Not Adjacent Chord",
								color: "rgb(255,255,140)"
							},
							{
								value: "${tab.fretDist_3}",
								label: "Barre Chord",
								color: "rgb(173,222,171)"
							}
						];
			
			var data2 = [
						{
							value: "${tab.fingerDist_0}",
							label: "Play Within Finger",
							color: "rgb(222,100,100)"
						},
						{
							value: "${tab.fingerDist_1}",
							label: "Stretch With Pinky",
							color: "rgb(255,170,75)"
						},
						{
							value: "${tab.fingerDist_2}",
							label: "Hand Movement",
							color: "rgb(255,255,140)"
						}
					];
			
			var data3 = [
				{
					value: "${tab.chordDist_1}",
					label: "1-Note",
					color: "rgb(222,100,100)"
				},
				{
					value: "${tab.chordDist_2}",
					label: "2-Note",
					color: "rgb(255,170,75)"
				},
				{
					value: "${tab.chordDist_3}",
					label: "3-Note",
					color: "rgb(255,255,140)"
				},
				{
					value: "${tab.chordDist_4}",
					label: "4-Note",
					color: "rgb(173,222,171)"
				},
				{
					value: "${tab.chordDist_5}",
					label: "5-Note",
					color: "rgb(100,100,255)"
				},
				{
					value: "${tab.chordDist_6}",
					label: "6-Note",
					color: "rgb(150,180,140)"
				}
			             ];
			
			var data4 = [
					{
						value: "${tab.freqOfSlur}",
						label: "Slur",
						color: "rgb(222,100,100)"
					},
					{
						value: "${tab.freqOfTech}",
						label: "Other Technique",
						color: "rgb(255,170,75)"
					}
				];
			
			var ctx2 = document.getElementById("barChart").getContext("2d");
			var ctx3 = document.getElementById("barChart2").getContext("2d");
			var ctx4 = document.getElementById("barChart3").getContext("2d");
			var ctx5 = document.getElementById("barChart4").getContext("2d");
			
			var fretPie = new Chart(ctx2).Pie(data);
			var fingerPie = new Chart(ctx3).Pie(data2);
			var chordPie = new Chart(ctx4).Pie(data3);
			var slurPie = new Chart(ctx5).Pie(data4);
			
			document.getElementById('chordChart-legend').innerHTML = fretPie.generateLegend();
			document.getElementById('chordChart-legend2').innerHTML = fingerPie.generateLegend();
			document.getElementById('chordChart-legend3').innerHTML = chordPie.generateLegend();
			document.getElementById('chordChart-legend4').innerHTML = slurPie.generateLegend();
			
			$("#" + "cont2").hide();
			</script>
			
			</div>
			</div>
			<div id="uploadexpand"><div class="center">Show/Hide</div></div>
			<div class="tabstep"></div>
			<div class="song-tab-content">
				<span><b>Uploaded Date</b>: ${tab.date}</span>
				<span><b>Uploaded By</b>: ${tab.username}</span>
				<br>
				<span><b>Ratio of number of chords to number of notes:</b> ${tab.ratioOfChord}</span>
				<span><b>Frequency of slurs:</b> ${tab.freqOfSlur}</span>
				<span><b>Frequency of guitar techniques:</b> ${tab.freqOfTech}</span>
				<br>
				<span><b>Statistics of local hand displacement distribution</b>
					: ${tab.localDist}
				</span>
				<br>
				<span><b>
					Statistics of distribution of the number of notes in chords
					</b>
				</span>
				<span>         1 Note: ${tab.chordDist_1}</span>
				<span>         2 Note: ${tab.chordDist_2}</span>
				<span>         3 Note: ${tab.chordDist_3}</span>
				<span>         4 Note: ${tab.chordDist_4}</span>
				<span>         5 Note: ${tab.chordDist_5}</span>
				<span>         6 Note: ${tab.chordDist_6}</span>
				<br>
				<span><b>Statistics of finger stretch metric distribution of chords</b></span>  
				<span>0: ${tab.fingerDist_0}</span>
				<span>1: ${tab.fingerDist_1}</span>
				<span>2: ${tab.fingerDist_2}</span>
				<br>
				<span><b>Statistics of distribution of same string fret switching</b></span>
				<span>0: ${tab.fretDist_0}</span>
				<span>1: ${tab.fretDist_1}</span>
				<span>2: ${tab.fretDist_2}</span>
				<span>3: ${tab.fretDist_3}</span>
				<br>
				<span><b>Tab Title: ${tab.title}</b></span>
				<span>${tab.tab}</span>
			</c:when>
		</c:choose>
		</div>
	

		<!--
		<div class="form-group">
			<label for="artist">OR Upload Tab</label>
		<input type="file" name="file" accept="text/plain">
		</div>
		--!>
	
		<!--
		<div class="form-group">
			<label for="Tab">Tab</label>
			<textarea id="Tab" form="upload" value='' placeholder="Paste your tab here" type="textarea" rows="4" cols="50" class="form-control" /></textarea>
		</div>
		--!>

		<!--
		<div class="maincont">
		
			<div class="push4">
			</div>
			
			<div class="content"> 
			<h1> Upload </h1>
				<form id ="upload" action="">
					<div class="form-group">
						<label for="title">Song Title</label>
						<input value='' id="title" placeholder="Song Title" type="text" class="form-control" />
					</div>
					<div class="form-group">
						<label for="artist">Artist</label>
						<input id="artist" value='' placeholder="Artist" type="text" class="form-control" />
					</div>
					<div class="form-group">
						<label for="Tab">Tab</label>
						<textarea id="Tab" form="upload" value='' placeholder="Paste your tab here" type="textarea" rows="4" cols="50" class="form-control" /></textarea>
					</div>
					<div class="form-group">
					<input type="file" name="file" accept="text/plain">
					</div>
					
					<div class="form-group text-center">
						<input type="submit" class="btn btn-success btn-login-submit" value="Upload" />
					</div>
				</form>
			</div> 
		</div> -->
 	</div>
</body>
</html>