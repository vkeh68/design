<!Doctype html>
<!--[if lt IE 7]><html lang="en" class="no-js ie6"><![endif]-->
<!--[if IE 7]><html lang="en" class="no-js ie7"><![endif]-->
<!--[if IE 8]><html lang="en" class="no-js ie8"><![endif]-->
<!--[if gt IE 8]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->

<head>
    <meta charset="UTF-8">
    <title>SevenApp - Home</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <link rel="shortcut icon" href="favicon.ico">

    <link rel="stylesheet" href="sevenResources/css/bootstrap.min.css">
    <link rel="stylesheet" href="sevenResources/css/animate.css">
    <link rel="stylesheet" href="sevenResources/css/font-awesome.min.css">
    <link rel="stylesheet" href="sevenResources/css/owl.carousel.css">
    <link rel="stylesheet" href="sevenResources/css/owl.theme.css">
    <link rel="stylesheet" href="sevenResources/css/styles.css">
    <style>
    	
		/* The Modal (background) */
		.modal {
			display: none; /* Hidden by default */
			position: fixed; /* Stay in place */
			z-index: 1; /* Sit on top */
			padding-top: 220px; /* Location of the box */
			left: 0;
			top: 0;
			width: 100%; /* Full width */
			height: 100%; /* Full height */
			overflow: auto; /* Enable scroll if needed */
			background-color: rgb(0, 0, 0); /* Fallback color */
			background-color: rgba(0, 0, 0, 0.6); /* Black w/ opacity */
		}
		
		/* Modal Content */
		.modal-row {
			position: relative;
			background-color: #fefefe;
			margin: auto;
			padding-top: 16px;
			width: 53%;
			height: 67%;
			box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0
				rgba(0, 0, 0, 0.19);
			-webkit-animation-name: animatetop;
			-webkit-animation-duration: 0.4s;
			animation-name: animatetop;
			animation-duration: 0.4s
		}
		
		/* Add Animation */
		@
		-webkit-keyframes animatetop {
			from {top: -300px;
			opacity: 0
		}
		
		to {
			top: 0;
			opacity: 1
		}
		
		}
		@
		keyframes animatetop {
			from {top: -300px;
			opacity: 0
		}
		
		to {
			top: 0;
			opacity: 1
		}
		
		}
		.panel-heading {
			border: 1px solid #D5D5D5;
		}
		
		/*
		 * Specific styles of signin component
		 */
		/*
		 * General styles
		 */
		
		.card-container.card {
		    max-width: 350px;
		    padding: 40px 40px;
		}
		
		/*
		 * Card component
		 */
		.card {
		    background-color: #F7F7F7;
		    /* just in case there no content*/
		    padding: 20px 25px 30px;
		    margin: 0 auto 25px;
		    margin-top: 50px;
		    /* shadows and rounded borders */
		    -moz-border-radius: 2px;
		    -webkit-border-radius: 2px;
		    border-radius: 2px;
		    -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
		    -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
		    box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
		}
		
		.profile-img-card {
		    width: 96px;
		    height: 96px;
		    margin: 0 auto 10px;
		    display: block;
		    -moz-border-radius: 50%;
		    -webkit-border-radius: 50%;
		    border-radius: 50%;
		}
		
		/*
		 * Form styles
		 */
		.profile-name-card {
		    font-size: 16px;
		    font-weight: bold;
		    text-align: center;
		    margin: 10px 0 0;
		    min-height: 1em;
		}
		
		.reauth-email {
		    display: block;
		    color: #404040;
		    line-height: 2;
		    margin-bottom: 10px;
		    font-size: 14px;
		    text-align: center;
		    overflow: hidden;
		    text-overflow: ellipsis;
		    white-space: nowrap;
		    -moz-box-sizing: border-box;
		    -webkit-box-sizing: border-box;
		    box-sizing: border-box;
		}
		
		.form-signin #inputEmail,
		.form-signin #inputPassword {
		    direction: ltr;
		    height: 44px;
		    font-size: 16px;
		}
		
		.form-signin input[type=email],
		.form-signin input[type=password],
		.form-signin input[type=text],
		.form-signin button {
		    width: 100%;
		    display: block;
		    margin-bottom: 10px;
		    z-index: 1;
		    position: relative;
		    -moz-box-sizing: border-box;
		    -webkit-box-sizing: border-box;
		    box-sizing: border-box;
		}
		
		.form-signin .form-control:focus {
		    border-color: rgb(104, 145, 162);
		    outline: 0;
		    -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgb(104, 145, 162);
		    box-shadow: inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgb(104, 145, 162);
		}
		
		.btn.btn-signin {
		    /*background-color: #4d90fe; */
		    background-color: rgb(104, 145, 162);
		    /* background-color: linear-gradient(rgb(104, 145, 162), rgb(12, 97, 33));*/
		    padding: 0px;
		    font-weight: 700;
		    font-size: 14px;
		    height: 36px;
		    -moz-border-radius: 3px;
		    -webkit-border-radius: 3px;
		    border-radius: 3px;
		    border: none;
		    -o-transition: all 0.218s;
		    -moz-transition: all 0.218s;
		    -webkit-transition: all 0.218s;
		    transition: all 0.218s;
		}
		
		.btn.btn-signin:hover,
		.btn.btn-signin:active,
		.btn.btn-signin:focus {
		    background-color: rgb(12, 97, 33);
		}
		
		.forgot-password {
		    color: rgb(104, 145, 162);
		}
		
		.forgot-password:hover,
		.forgot-password:active,
		.forgot-password:focus{
		    color: rgb(12, 97, 33);
		}
    	
    </style>
    <script src="sevenResources/js/modernizr.custom.32033.js"></script>

    <!--[if IE]><script type="text/javascript" src="sevenResources/js/excanvas.compiled.js"></script><![endif]-->

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>
    <!-- Wrap all page content here -->
    <div id="wrap" style="height:150%;">
        <header class="masthead">
            <div class="slider-container" id="slider">
                <div class="container">
                    <div class="row mh-container">
                        <h1 style="font-color:#5CD1E5;">
                        	<span style="font-color:#5CD1E5;">Customer</span>Tracking System
                        </h1>
                        <h3>Before Starting, Download Our Application!</h3>
                        <div class="col-md-4 col-md-push-4">
                            <div class="btn-group btn-group-justified btn-lg small">
                                <div class="btn-group">
                                    <a href="#" class="btn btn-default scrollpoint sp-effect6">
                                    </a>
                                </div>
                                <div class="btn-group">
                                    <a href="#" class="btn btn-default scrollpoint sp-effect6">
                                        <span class="play"></span>
                                    </a>
                                </div>
                                <div class="btn-group">
                                    <a href="#" class="btn btn-default scrollpoint sp-effect6">
                                        <span class="android"></span>
                                    </a>
                                </div>
                                <div class="btn-group">
                                    <a href="#" class="btn btn-default scrollpoint sp-effect6">
                                    </a>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-10 col-md-push-1 hidden-xs mh-slider"  style="margin-bottom:2%;">
                            <div class="row">
                                <div class="col-md-3">
                                    <a id="signBtn" class="btn btn-default side">Sign Now!</a>
                                </div>
                                <div class="col-md-6">
                                    <div id="carousel-slider" class="carousel slide" data-ride="carousel">

                                        <!-- Wrapper for slides -->
                                        <div class="carousel-inner">
                                            <div class="item active">
                                                <img src="sevenResources/img/slide1.png" alt="..." class="img-responsive">
                                            </div>                                            
                                            <div class="item">
                                                <img src="sevenResources/img/slide1.png" alt="..." class="img-responsive">
                                            </div>
                                        </div>

                                        <!-- Controls -->
                                        <a class="left carousel-control" href="#carousel-slider" role="button" data-slide="prev">
                                            <span class="slider-left"></span>
                                        </a>
                                        <a class="right carousel-control" href="#carousel-slider" role="button" data-slide="next">
                                            <span class="slider-right"></span>
                                        </a>
                                    </div>

                                </div>
                                <div class="col-md-3">
                                    <a id="loginBtn" class="btn btn-empty side">Login Now!</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
            <div id="loginModal" class="modal" style="z-index: 3;">
				<div class="card card-container">
			            <img id="profile-img" class="profile-img-card" src="//ssl.gstatic.com/accounts/ui/avatar_2x.png" />
			            <p id="profile-name" class="profile-name-card"></p>
			            <form class="form-signin">
			                <span id="reauth-email" class="reauth-email"></span>
			                <input type="text" id="inputId" class="form-control" placeholder="Id" required autofocus>
			                <input type="password" id="inputPassword" class="form-control" placeholder="Password" required>
			                <button class="btn btn-lg btn-primary btn-block btn-signin" id="login" type="submit">Login</button>
			            </form>
			     </div>
            </div>
            
            <div id="signModal" class="modal" style="z-index: 3;">
				<div class="card card-container">
			            <img id="profile-img" class="profile-img-card" src="//ssl.gstatic.com/accounts/ui/avatar_2x.png" />
			            <p id="profile-name" class="profile-name-card"></p>
			            <form class="form-signin">
			                <span id="reauth-email" class="reauth-email"></span>
			                <input type="text" id="inputId" class="form-control" placeholder="Id" required autofocus>
			                <input type="password" id="inputPassword" class="form-control" placeholder="Password" required>
			                <input type="text" id="inputName" class="form-control" placeholder="Name" required>
			                <input type="date" id="inputBirth" class="form-control" required>
			                <input type="text" id="inputAddress" class="form-control" placeholder="Address" required>
			                <input type="text" id="inputEtc" class="form-control" placeholder="Etc" required>
			                <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit"></button>
			            </form>
			     </div>
            </div>
        </header>
    </div>

    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="sevenResources/js/bootstrap.min.js"></script>
    <script src="sevenResources/js/owl.carousel.min.js"></script>
    <script src="sevenResources/js/waypoints.min.js"></script>

    <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyASm3CwaK9qtcZEWYa-iQwHaGi3gcosAJc&sensor=false"></script>

    <!-- jQuery REVOLUTION Slider  -->
    <script type="text/javascript" src="sevenResources/rs-plugin/js/jquery.themepunch.plugins.min.js"></script>
    <script type="text/javascript" src="sevenResources/rs-plugin/js/jquery.themepunch.revolution.min.js"></script>

    <script src="sevenResources/js/script.js"></script>
    
    <script>
    
    loginModal.style.display="none";
    signModal.style.display="none";
    
    $("#loginBtn").on("click",function(){
    	loginModal.style.display="block";
    });
    
    $("#signBtn").on("click",function(){
    	signModal.style.display="block";
    });
    
    window.onclick = function(event) {
		if (event.target == loginModal) {
			loginModal.style.display = "none";
		}else if(event.target == signModal){
			signModal.style.display="none";
		}
	}
	
	var isLogin = false;
	
	$("#login").on("click", function(){
		
		if(isLogin) return false;
		
		var user_id = $("#inputId").val();
		var user_pw = $("#inputPassword").val();
		
		if(!user_id){
			alert("아이디를 입력하세요");
			$("#inputId").focus();
			
		}
		if(!user_pw){
			alert("비밀번호를 입력하세요");
			$("#inputPassword").focus();
		}
		
		isLogin = true;
		
		$.ajax({
			type:"post",
			url:"login",
			data: {
				user_id : user_id,
				user_password : user_pw
			},
			dataType: "json",
			success: function(data){
				if(data.status == "success"){
					
					//self.location = "mainPage";
					self.location = data.location;
				}else{
					alert("아이디나 비밀번호가 틀렸습니다.");
					$("#inputId").focus();
				}
				isLogin = false;
			},
			error: function(data) {
				isLogin = false;
			}
		});
	});
    </script>

</body>

</html>