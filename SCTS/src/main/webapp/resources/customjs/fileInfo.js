var imgLoad = function(product_id) {

	console.log(product_id);
	
	$.ajax({
		url: "getProductImg",
		type: "get",
		data: {
			product_id:product_id
		},
		dataType: "json",
		success: function(data) {

			if(data != null) {
				$('#product_img').empty();

				var pImg =  "<img src='displayFile?fileName="+data+"'style='width:100%; height:100%;'/>";	
				pImg.appendTo($('#product_img'));
				

			}
		},
		error: function(data) {
			console.log("error!");
		}
	});
};