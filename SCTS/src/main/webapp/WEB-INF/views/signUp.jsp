<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Creative - Bootstrap 3 Responsive Admin Template">
    <meta name="author" content="GeeksLabs">
    <meta name="keyword" content="Creative, Dashboard, Admin, Template, Theme, Bootstrap, Responsive, Retina, Minimal">
    <link rel="shortcut icon" href="/scts/resources/img/favicon.png">
	<script src="/scts/resources/js/jquery-3.2.1.min.js"></script>
    <title>Login Page 2 | Creative - Bootstrap 3 Responsive Admin Template</title>

    <!-- Bootstrap CSS -->    
    <link href="/scts/resources/css/bootstrap.min.css" rel="stylesheet">
    <!-- bootstrap theme -->
    <link href="/scts/resources/css/bootstrap-theme.css" rel="stylesheet">
    <!--external css-->
    <!-- font icon -->
    <link href="/scts/resources/css/elegant-icons-style.css" rel="stylesheet" />
    <link href="/scts/resources/css/font-awesome.css" rel="stylesheet" />
    <!-- Custom styles -->
    <link href="/scts/resources/css/style.css" rel="stylesheet">
    <link href="/scts/resources/css/style-responsive.css" rel="stylesheet" />

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 -->
    <!--[if lt IE 9]>
    <script src="/scts/resources/js/html5shiv.js"></script>
    <script src="/scts/resources/js/respond.min.js"></script>
    <![endif]-->
</head>

  <body class="login-img3-body">

    <div class="container">

      <form class="login-form" >        
      
        <div class="login-wrap">
            <p class="login-img"><i class="icon_lock_alt"></i></p>
            <div class="input-group">
              <span class="input-group-addon"><i class="icon_profile"></i></span>
              <input type="text" class="form-control" placeholder="Username" name="user_id" id="user_id" autofocus>
            </div>
            <button class="btn btn-info btn-lg btn-block" id="checkUser" type="button" style="margin-bottom: 20px;">check Id</button>
            <div class="input-group">
                <span class="input-group-addon"><i class="icon_key_alt"></i></span>
                <input type="password" class="form-control" placeholder="Password" name="user_pw" id="user_pw">
            </div>
            <div class="input-group">
                <span class="input-group-addon"><i class="icon_key_alt"></i></span>
                <input type="password" class="form-control" placeholder="Password Check" name="user_pw_c" id="user_pw_c">
            </div>
            <div class="input-group">
                <span class="input-group-addon"><i class="icon_profile"></i></span>
                <input type="text" class="form-control" placeholder="age" name="age" id="age">
            </div>
            <div class="input-group">
                <span class="input-group-addon"><i class="icon_profile"></i></span> 
                <input type="radio" id="m" name="gender" value="m"> <label for="m">남자</label>
                <input type="radio" id="w" name="gender"  value="w"> <label for="w">여자</label>
               
            </div>

            <button class="btn btn-info btn-lg btn-block" id="signUp" type="button">Sign up</button>
        </div>
      </form>
    <div class="text-right">
            <div class="credits">
                <!-- 
                    All the links in the footer should remain intact. 
                    You can delete the links only if you purchased the pro version.
                    Licensing information: https://bootstrapmade.com/license/
                    Purchase the pro version form: https://bootstrapmade.com/buy/?theme=NiceAdmin
                -->
                <a href="https://bootstrapmade.com/free-business-bootstrap-themes-website-templates/">Business Bootstrap Themes</a> by <a href="https://bootstrapmade.com/">BootstrapMade</a>
            </div>
        </div>
    </div>
    
    <script>
    $("#checkUser").on("click", function(){
    	var user_id = $("#user_id").val();
    	if(!user_id){
    		$("#user_id").focus()
    	}
    	$.ajax({
    		type:"get",
    		url:"checkUser",
    		data:{
    			user_id : user_id
    		},
    		success: function(data){
    			
    			if(data == "0"){
    				alert("사용 가능한 아이디입니다.");
    			}else{
    				alert("사용 가능하지 않은 아이디 입니다.");
    			}
    		}
    	});
    });
    var isSignUp = false;
    $("#signUp").on("click", function(){
    	
    	if(isSignUp) return false;
    	
    	var user_id = $("#user_id").val();
    	var user_pw = $("#user_pw").val();
    	var user_pw_c = $("#user_pw_c").val();
  
    	var age = $("#age").val();
    	var gender = $("input[name='gender']:checked").val();
    	
    	if(!user_id){
    		alert("아이디를 입력해주세요");
    		 $("#user_id").focus();
    		
    	}
    	if(!user_pw){
    		alert("비밀번호를 입력해주세요");
    		$("#user_pw").focus();
    	}
    	if(!user_pw_c){
    		alert("비밀번호 확인 입력해주세요");
    		$("#user_pw_c").focus();
    	}
    	if(user_pw != user_pw_c){
    		alert("비밀번호가 맞지 않습니다.");
    		$("#user_pw_c").focus();
    		
    	}
    	if(!age){
    		alert("나이를 입력해주세요");
    		$("#age").focus();
    	}
    	if(!gender){
    		alert("성별을 입력해주세요");
    		$("#gender").focus();
    	}
    
    	
    	isSignUp = true;
    	
    	$.ajax({
    		type:"post",
    		url:"signUp",
    		data:{
    			user_id : user_id,
    			user_pw : user_pw,
    			age : age,
    			gender : gender
    		},
    		success: function(data){
    			
    			if(data == "0"){
    				alert("회원 가입 되지 않았어요");
    			}else{
    				alert("회원가입 완료!");
    			}
    			self.location = "login";
    		}
    	});
    });
    </script>
	


  </body>
</html>
