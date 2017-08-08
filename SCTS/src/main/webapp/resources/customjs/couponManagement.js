	
	/* modal창 스크립트 */
	
	var couponModal = document.getElementById('couponModal');
	var couponBtn = document.getElementById('couponBtn');
	var modifyModal = document.getElementById('modifyModal');
	var cancel = document.getElementsByClassName("btn btn-default");
	
	$("#couponBtn").on("click", function(){
		couponModal.style.display = "block";
	});
	
	$(".cancel").on("click",function(){
		couponModal.style.display = "none";
	});
	
	window.onclick = function(event){
		if (event.target == couponModal) {
			couponModal.style.display = "none";
		}else if(event.target == modifyModal){
			modifyModal.style.display = "none";
		}
	}
	
	$(".delBtn").on("click", function(){
		var CouNum = $(this).parent().siblings().eq(0).html();
		$('.formObj').append("<input type='hidden' name='coupon_code' value='"+ CouNum +"'/>");
		$(".formObj").attr("action", "deleteCoupon");
		$(".formObj").attr("method", "post");
		$(".formObj").submit();
	});
	
	
	$(".modifyBtn").on("click",function(){
		var CouNum = $(this).parent().siblings().eq(0).html();

		$(".formObj").append("<input type='hidden' name='coupon_code' value='"+ CouNum +"'/>");
		$(".formObj").attr("action", "coupon_modify");
		$(".formObj").attr("method", "post");

	});

	$(".cancel").on("click", function(){
		$("#couponForm").attr("action", "coupon_Management");
		$("#couponForm").attr("method", "get");
		$("#couponForm").submit();
	});
	
