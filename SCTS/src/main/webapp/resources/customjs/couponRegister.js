/*var p_div=document.getElementById("p_div");
var c_div=document.getElementById("c_div");

$(document).ready(function(){
	p_div.style.display="none";
	c_div.style.display="none";
});
*/
$("#selection").on("change",function(){
	var selected = $("#selection option:selected").val();
	if(selected=="product"){
		p_div.style.display="block";
		c_div.style.display="none";
	}else if(selected=="category"){
		p_div.style.display="none";
		c_div.style.display="block";
	}
});

$("#couponCancel").on("click",function(){
	$('#couponForm').attr("method", "get");
	$('#couponForm').attr("action", "coupon_Management");
	$('#couponForm').submit();
});

$("#searching").on("click", function(){
	
	var goodsName = $("#search").val();

	
//	alert(goodsName);
	$.ajax({
		type:"get",
		url:"search",
		data:{
			goodsName:goodsName
		},
		dataType:"json",
		
		success:function(data){			
			if(data!=null){
				$("#productList").empty();
				
				var length = data.result.length;
				for(var i=0; i<length; i++) {
//					alert('hello3');
					var gname = data.result[i].goods_nm+"";
					var gcode = data.result[i].goods_code+"";

					var products = $("<tr class='product'></tr>");
					$("<td><input type='radio' name='goodsCodeList' class='checked'></td>").appendTo(products);
					$("<td></td>").addClass("goods_code").text(data.result[i].goods_code).appendTo(products);
					$("<td></td>").addClass("goods_nm").text(data.result[i].goods_nm).appendTo(products);
					$("<td></td>").addClass("goods_pc").text(data.result[i].goods_pc).appendTo(products);
					
					products.appendTo($("#productList"));
					
					$(".checked").on("click",function(){
//						alert('체크됐지롱!');
						$("#selectGoods").val(gname);
						$("#selectGcode").val(gcode);
						
						
						var goods_code =($(this).parent().parent().find('.goods_code').text());
						var code = parseInt($("#codes").val())+1;
						var coupon_co = $("#coupon_co").val();
						
						$('#goods_code').val(goods_code);
						
						$("#couponSave").on("click",function(){
							alert(coupon_code+goods_code+coupon_co);
							$.ajax({
								type:"post",
								url:"insertCoupon",
								data:{
									coupon_code:coupon_code,
									goods_code:goods_code,
									coupon_co:coupon_co,
									coupon_nm:coupon_nm,
									coupon_cntnts:coupon_cntnts,
									coupon_dscnt:coupon_dscnt,
									coupon_begin_de:coupon_begin_de,
									coupon_end_de:coupon_end_de
								}
							});
						});	
					});

				}
				$(".checked").on("click",function(){
					$("#selectGoods").val(gname);
					$("#selectGcode").val(gcode);
					
				
					/*	$.ajax({
						type:"post",
						url:"insertCoupon",
						data:{
							goods_code:goods_code
						}
					});*/
				});
			}else {
				$("<td></td>").text("검색된 물품이 없습니다.").appendTo(products);
//				alert('hii');
			}
		},
		error:function(){
			system.out.println('ERROR');
		}
	});
});