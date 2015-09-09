<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../../basic.jsp"></jsp:include>

<title>MWS Admin - Dashboard</title>

</head>

<body>

	<!-- Themer -->  
	<div id="mws-themer">
    	<div id="mws-themer-hide"></div>
        <div id="mws-themer-content">
        	<div class="mws-themer-section">
	        	<label for="mws-theme-presets">Presets</label> <select id="mws-theme-presets"></select>
            </div>
            <div class="mws-themer-separator"></div>
            <div class="mws-themer-section">
                <ul>
                    <li><span>Base Color</span> <div id="mws-base-cp" class="mws-cp-trigger"></div></li>
                    <li><span>Text Color</span> <div id="mws-text-cp" class="mws-cp-trigger"></div></li>
                    <li><span>Text Glow Color</span> <div id="mws-textglow-cp" class="mws-cp-trigger"></div></li>
                </ul>
            </div>
            <div class="mws-themer-separator"></div>
            <div class="mws-themer-section">
            	<ul>
                    <li><span>Text Glow Opacity</span> <div id="mws-textglow-op"></div></li>
                </ul>
            </div>
            <div class="mws-themer-separator"></div>
            <div class="mws-themer-section">
	            <button class="mws-button red small" id="mws-themer-getcss">Get CSS</button>
            </div>
        </div>
        <div id="mws-themer-css-dialog">
        	<div class="mws-form">
            	<div class="mws-form-row" style="padding:0;">
		        	<div class="mws-form-item">
                    	<textarea cols="auto" rows="auto" readonly="readonly"></textarea>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Themer End -->
    

	<!-- Header Wrapper -->
	<div id="mws-header" class="clearfix">
    
    	<!-- Logo Wrapper -->
    	<div id="mws-logo-container">
        	<div id="mws-logo-wrap">
            	<img src="images/mws-logo.png" alt="mws admin" />
			</div>
        </div>
        
        <!-- User Area Wrapper -->
        <div id="mws-user-tools" class="clearfix">
        
        	<!-- User Notifications -->
        	<div id="mws-user-notif" class="mws-dropdown-menu">
            	<a href="#" class="mws-i-24 i-alert-2 mws-dropdown-trigger">Notifications</a>
                <span class="mws-dropdown-notif">35</span>
                <div class="mws-dropdown-box">
                	<div class="mws-dropdown-content">
                        <ul class="mws-notifications">
                        	
                            <!-- Notification Content -->
                        	<li class="read">
                            	<a href="#">
                                    <span class="message">
                                        Lorem ipsum dolor sit amet consectetur adipiscing elit, et al commore
                                    </span>
                                    <span class="time">
                                        January 21, 2012
                                    </span>
                                </a>
                            </li>
                        	<li class="read">
                            	<a href="#">
                                    <span class="message">
                                        Lorem ipsum dolor sit amet
                                    </span>
                                    <span class="time">
                                        January 21, 2012
                                    </span>
                                </a>
                            </li>
                        	<li class="unread">
                            	<a href="#">
                                    <span class="message">
                                        Lorem ipsum dolor sit amet
                                    </span>
                                    <span class="time">
                                        January 21, 2012
                                    </span>
                                </a>
                            </li>
                        	<li class="unread">
                            	<a href="#">
                                    <span class="message">
                                        Lorem ipsum dolor sit amet
                                    </span>
                                    <span class="time">
                                        January 21, 2012
                                    </span>
                                </a>
                            </li>
                            <!-- End Notification Content -->
                            
                        </ul>
                        <div class="mws-dropdown-viewall">
	                        <a href="#">View All Notifications</a>
                        </div>
                    </div>
                </div>
            </div>
            
            <!-- User Messages -->
            <div id="mws-user-message" class="mws-dropdown-menu">
            	<a href="#" class="mws-i-24 i-message mws-dropdown-trigger">Messages</a>
                <span class="mws-dropdown-notif">35</span>
                <div class="mws-dropdown-box">
                	<div class="mws-dropdown-content">
                        <ul class="mws-messages">
                        
                        	<!-- Message Content -->
                        	<li class="read">
                            	<a href="#">
                                    <span class="sender">John Doe</span>
                                    <span class="message">
                                        Lorem ipsum dolor sit amet consectetur adipiscing elit, et al commore
                                    </span>
                                    <span class="time">
                                        January 21, 2012
                                    </span>
                                </a>
                            </li>
                        	<li class="read">
                            	<a href="#">
                                    <span class="sender">John Doe</span>
                                    <span class="message">
                                        Lorem ipsum dolor sit amet
                                    </span>
                                    <span class="time">
                                        January 21, 2012
                                    </span>
                                </a>
                            </li>
                        	<li class="unread">
                            	<a href="#">
                                    <span class="sender">John Doe</span>
                                    <span class="message">
                                        Lorem ipsum dolor sit amet
                                    </span>
                                    <span class="time">
                                        January 21, 2012
                                    </span>
                                </a>
                            </li>
                        	<li class="unread">
                            	<a href="#">
                                    <span class="sender">John Doe</span>
                                    <span class="message">
                                        Lorem ipsum dolor sit amet
                                    </span>
                                    <span class="time">
                                        January 21, 2012
                                    </span>
                                </a>
                            </li>
                            <!-- End Messages -->
                            
                        </ul>
                        <div class="mws-dropdown-viewall">
	                        <a href="#">View All Messages</a>
                        </div>
                    </div>
                </div>
            </div>
            
            <!-- User Functions -->
            <div id="mws-user-info" class="mws-inset">
            	<div id="mws-user-photo">
                	<!-- <img src="example/profile.jpg" alt="User Photo" /> -->
                </div>
                <div id="mws-user-functions">
                    <div id="mws-username">
                      	   你好:${username },现在时间是<%= new Date() %>
                    </div>
                    <ul>
                    	<li><a href="#">Profile</a></li>
                        <li><a href="#">Change Password</a></li>
                        <li><a href="index-2.html">Logout</a></li>
                    </ul>
                </div>
                <div class="htmleaf-container">
					<div class="container">
						<div id="myclock"></div>
						<div id="alarm1" class="alarm"><a href="javascript:void(0)" id="turnOffAlarm">ALARM OFF</a></div>
					</div>
				</div>
            </div>
            <!-- End User Functions -->
            
        </div>
    </div>
    
    <!-- Main Wrapper -->
    <div id="mws-wrapper">
    	<!-- Necessary markup, do not remove -->
		<div id="mws-sidebar-stitch"></div>
		<div id="mws-sidebar-bg"></div>
        
        <!-- Sidebar Wrapper -->
        <div id="mws-sidebar">
        	
            <!-- Search Box -->
        	<div id="mws-searchbox" class="mws-inset">
            	<form action="http://www.malijuwebshop.com/themes/mws-admin/dashboard.html">
                	<input type="text" class="mws-search-input" />
                    <input type="submit" class="mws-search-submit" />
                </form>
            </div>
            
            <!-- Main Navigation -->
            <div id="mws-navigation">
            	<ul>
                	<li class="active"><a href="dashboard.html" class="mws-i-24 i-home">Dashboard</a></li>
                	<li><a href="charts.html" class="mws-i-24 i-chart">Charts</a></li>
                	<li><a href="calendar.html" class="mws-i-24 i-day-calendar">Calendar</a></li>
                	<li><a href="files.html" class="mws-i-24 i-file-cabinet">File Manager</a></li>
                	<li><a href="table.html" class="mws-i-24 i-table-1">Table</a></li>
                	<li>
                    	<a href="#" class="mws-i-24 i-list">Forms</a>
                        <ul>
                        	<li><a href="form_layouts.html">Layouts</a></li>
                        	<li><a href="form_elements.html">Elements</a></li>
                        </ul>
                    </li>
                	<li><a href="widgets.html" class="mws-i-24 i-cog">Widgets</a></li>
                	<li><a href="typography.html" class="mws-i-24 i-text-styling">Typography</a></li>
                	<li><a href="grids.html" class="mws-i-24 i-blocks-images">Grids &amp; Panels</a></li>
                	<li><a href="gallery.html" class="mws-i-24 i-polaroids">Gallery</a></li>
                	<li><a href="error.html" class="mws-i-24 i-alert-2">Error Page</a></li>
                	<li>
                    	<a href="icons.html" class="mws-i-24 i-pacman">
                        	Icons <span class="mws-nav-tooltip">2000+</span>
                        </a>
                    </li>
                    <li>
                    	<a href="weather.html" class="mws-i-24 i-weather">
                        	Weather
                        </a>
                    </li>
                </ul>
            </div>
            <!-- End Navigation -->
            
        </div>
        
        
        <!-- Container Wrapper -->
        <div id="mws-container" class="clearfix">
        
        	<!-- Main Container -->
            <div class="container">
            
            	<div class="mws-report-container clearfix">
                	<a class="mws-report" href="#">
                    	<span class="mws-report-icon mws-ic ic-building"></span>
                        <span class="mws-report-content">
                        	<span class="mws-report-title">Floors Climbed</span>
                            <span class="mws-report-value">324</span>
                        </span>
                    </a>

                	<a class="mws-report" href="#">
                    	<span class="mws-report-icon mws-ic ic-sport"></span>
                        <span class="mws-report-content">
                        	<span class="mws-report-title">Calories Burned</span>
                            <span class="mws-report-value down">74%</span>
                        </span>
                    </a>

                	<a class="mws-report" href="#">
                    	<span class="mws-report-icon mws-ic ic-walk"></span>
                        <span class="mws-report-content">
                        	<span class="mws-report-title">Kilometers Walked</span>
                            <span class="mws-report-value">14</span>
                        </span>
                    </a>
                    
                	<a class="mws-report" href="#">
                    	<span class="mws-report-icon mws-ic ic-bug"></span>
                        <span class="mws-report-content">
                        	<span class="mws-report-title">Bugs Fixed</span>
                            <span class="mws-report-value up">22%</span>
                        </span>
                    </a>
                    
                	<a class="mws-report" href="#">
                    	<span class="mws-report-icon mws-ic ic-car"></span>
                        <span class="mws-report-content">
                        	<span class="mws-report-title">Cars Repaired</span>
                            <span class="mws-report-value">77</span>
                        </span>
                    </a>
                </div>
                
            	<div class="mws-panel grid_5">
                	<div class="mws-panel-header">
                    	<span class="mws-i-24 i-graph">Charts</span>
                    </div>
                    <div class="mws-panel-body">
                    	<div class="mws-panel-content">
	                    	<div id="mws-dashboard-chart" style="width:100%; height:215px;"></div>
                        </div>
                    </div>
                </div>
                
            	<div class="mws-panel grid_3">
                	<div class="mws-panel-header">
                    	<span class="mws-i-24 i-books-2">Website Summary</span>
                    </div>
                    <div class="mws-panel-body">
                        <ul class="mws-summary">
                            <li>
                                <span>1238</span> total visits
                            </li>
                            <li>
                                <span>512</span> votes
                            </li>
                            <li>
                                <span>11</span> new members
                            </li>
                            <li>
                                <span>716</span> products
                            </li>
                            <li>
                                <span>831</span> comments
                            </li>
                            <li>
                                <span>312</span> items purchased
                            </li>
                        </ul>
                    </div>
                </div>
                
            	<div class="mws-panel grid_8">
                	<div class="mws-panel-header">
                    	<span class="mws-i-24 i-sign-post">Register New Member</span>
                    </div>
                    <div class="mws-panel-body">
                        <div class="mws-wizard clearfix">
                            <ul>
                                <li>
                                    <a class="mws-ic-16 ic-accept" href="#">Member Profile</a>
                                </li>
                                <li class="current">
                                    <a href="#" class="mws-ic-16 ic-delivery">Membership Stype</a>
                                </li>
                                <li>
                                    <a class="mws-ic-16 ic-user" href="#">Confirmation</a>
                                </li>
                            </ul>
                        </div>
                    	<form class="mws-form" action="http://www.malijuwebshop.com/themes/mws-admin/dashboard.html">
                    		<div class="mws-form-inline">
                                <div class="mws-form-row">
                                    <label>Fullname</label>
                                    <div class="mws-form-item large">
                                    	<input type="text" name="fullname" class="mws-textinput" />
                                    </div>
                                </div>
                                <div class="mws-form-row">
                                    <label>Email</label>
                                    <div class="mws-form-item large">
                                    	<input type="text" name="email" class="mws-textinput" />
                                    </div>
                                </div>
                                <div class="mws-form-row">
                                    <label>Address</label>
                                    <div class="mws-form-item large">
                                    	<textarea name="address" rows="100%" cols="100%"></textarea>
                                    </div>
                                </div>
                                <div class="mws-form-row">
                                    <label>Gender</label>
                                    <div class="mws-form-item">
	                                    <ul class="mws-form-list">
	                                    	<li><input type="radio" id="male" name="gender" /> <label for="male">Male</label></li>
	                                    	<li><input type="radio" id="female" name="gender" /> <label for="female">Female</label></li>
	                                    </ul>
                                    </div>
                                </div>
                    		</div>
                    		<div class="mws-button-row">
                    			<input type="submit" value="Prev" class="mws-button gray left" />
                    			<input type="submit" value="Next" class="mws-button green" />
                    		</div>
                    	</form>
                    </div>
                </div>
                
                <div class="mws-panel grid_8 mws-collapsible">
                	<div class="mws-panel-header">
                    	<span class="mws-i-24 i-table-1">Simple Table</span>
                    </div>
                    <div class="mws-panel-body">
                        <table class="mws-table">
                            <thead>
                                <tr>
                                    <th>Rendering engine</th>
                                    <th>Browser</th>
                                    <th>Platform(s)</th>
                                    <th>Engine version</th>
                                    <th>CSS grade</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr class="gradeX">
                                    <td>Trident</td>
                                    <td>Internet
                                         Explorer 4.0</td>
                                    <td>Win 95+</td>
                                    <td class="center">4</td>
                                    <td class="center">X</td>
                                </tr>
                                <tr class="gradeC">
                                    <td>Trident</td>
                                    <td>Internet
                                         Explorer 5.0</td>
                                    <td>Win 95+</td>
                                    <td class="center">5</td>
                                    <td class="center">C</td>
                                </tr>
                                <tr class="gradeA">
                                    <td>Trident</td>
                                    <td>Internet
                                         Explorer 5.5</td>
                                    <td>Win 95+</td>
                                    <td class="center">5.5</td>
                                    <td class="center">A</td>
                                </tr>
                                <tr class="gradeA">
                                    <td>Trident</td>
                                    <td>Internet
                                         Explorer 6</td>
                                    <td>Win 98+</td>
                                    <td class="center">6</td>
                                    <td class="center">A</td>
                                </tr>
                                <tr class="gradeA">
                                    <td>Trident</td>
                                    <td>Internet Explorer 7</td>
                                    <td>Win XP SP2+</td>
                                    <td class="center">7</td>
                                    <td class="center">A</td>
                                </tr>
                                <tr class="gradeA">
                                    <td>Trident</td>
                                    <td>AOL browser (AOL desktop)</td>
                                    <td>Win XP</td>
                                    <td class="center">6</td>
                                    <td class="center">A</td>
                                </tr>
                                <tr class="gradeA">
                                    <td>Gecko</td>
                                    <td>Firefox 1.0</td>
                                    <td>Win 98+ / OSX.2+</td>
                                    <td class="center">1.7</td>
                                    <td class="center">A</td>
                                </tr>
                                <tr class="gradeA">
                                    <td>Gecko</td>
                                    <td>Firefox 1.5</td>
                                    <td>Win 98+ / OSX.2+</td>
                                    <td class="center">1.8</td>
                                    <td class="center">A</td>
                                </tr>
                                <tr class="gradeA">
                                    <td>Gecko</td>
                                    <td>Camino 1.5</td>
                                    <td>OSX.3+</td>
                                    <td class="center">1.8</td>
                                    <td class="center">A</td>
                                </tr>
                                <tr class="gradeA">
                                    <td>Gecko</td>
                                    <td>Netscape 7.2</td>
                                    <td>Win 95+ / Mac OS 8.6-9.2</td>
                                    <td class="center">1.7</td>
                                    <td class="center">A</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <!-- End Main Container -->
            
            <!-- Footer -->
            <div id="mws-footer">
            	Copyright &copy; 2014.Company name All rights reserved.<a target="_blank" href="http://www.17sucai.com/">&#x7F51;&#x9875;&#x6A21;&#x677F;</a>
            </div>
            <!-- End Footer -->
            
        </div>
        <!-- End Container Wrapper -->
        
    </div>
    <!-- End Main Wrapper -->
	<script language="javascript">
		var intVal, myclock;

		$(window).resize(function(){
			window.location.reload()
		});

		$(document).ready(function(){

			var audioElement = new Audio("");

			//clock plugin constructor
			$('#myclock').thooClock({
				size:$(document).height()/5.0,
				onAlarm:function(){
					//all that happens onAlarm
					$('#alarm1').show();
					alarmBackground(0);
					//audio element just for alarm sound
					document.body.appendChild(audioElement);
					var canPlayType = audioElement.canPlayType("audio/ogg");
					if(canPlayType.match(/maybe|probably/i)) {
						audioElement.src = 'alarm.ogg';
					} else {
						audioElement.src = 'alarm.mp3';
					}
					// erst abspielen wenn genug vom mp3 geladen wurde
					audioElement.addEventListener('canplay', function() {
						audioElement.loop = true;
						audioElement.play();
					}, false);
				},
				showNumerals:true,
				brandText:'THOOYORK',
				brandText2:'Germany',
				onEverySecond:function(){
					//callback that should be fired every second
				},
				//alarmTime:'15:10',
				offAlarm:function(){
					$('#alarm1').hide();
					audioElement.pause();
					clearTimeout(intVal);
					$('body').css('background-color','#FCFCFC');
				}
			});

		});

	</script>
</body>
</html>
