$("#searching").on("click", function(){
	
	var goodsName = $("#search").val();
	
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
					var gname = data.result[i].goods_nm+"";
					var gcode = data.result[i].goods_code+"";
					
					var products = $("<tr class='product'></tr>");
					$("<td><input type='radio' name='goodsCodeList' class='checked'></td>").appendTo(products);
					$("<td></td>").addClass("goods_code").text(data.result[i].goods_code).appendTo(products);
					$("<td></td>").addClass("goods_nm").text(data.result[i].goods_nm).appendTo(products);
					$("<td></td>").addClass("goods_pc").text(data.result[i].goods_pc).appendTo(products);
					
					products.appendTo($("#productList"));
					
				}
				$(".checked").on("click",function(){
					$("#selectGoods").val(gname);
					$("#selectGcode").val(gcode);
					
					var goods_code =($(this).parent().parent().find('.goods_code').text());
					
					$('#goods_code').val(goods_code);
					
					$("#stockSave").on("click",function(){
						$.ajax({
							type:"post",
							url:"insertStock",
							data:{
								user_id:user_id,
								wrhousng_qy:wrhousng_qy,
								wrhousng_de:wrhousng_de,
								puchas_pc:puchas_pc,
								puchas_de:puchas_de,
								invntry_qy:invntry_qy,
								distb_de:distb_de,
								goods_code:goods_code
							}
						});
					});	
				});	
			}else {
				$("<td></td>").text("검색된 물품이 없습니다.").appendTo(products);
			}
		},
		error:function(){
			system.out.println('ERROR');
		}
	});
});

$("#stockCancel").click(function(){
	$("#stockForm").attr("method", "get");
	$("#stockForm").attr("action", "stock_Management");
	$("#stockForm").submit();
});