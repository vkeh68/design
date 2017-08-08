/**
 * 
 */


$(document).ready(function() {
	/*
	$("input[type='submit']").on("click", function(event) {
		event.preventDefault();
	});*/
	
/*
	$(".fileDrop").on("dragenter dragover", function(event) {
		event.preventDefault();
	});
	
	$(".fileDrop").on("drop", function(event) {
		event.preventDefault();
		var files = event.originalEvent.dataTransfer.files;
		var file = files[0];
		
		var formData = new FormData();
		formData.append("file", file);
		
		$.ajax({
			url:"uploadAjax",
			data: formData,
			dataType: "text",
			processData: false,
			contentType: false,
			type:"post",
			success: function(data) {
				//alert(data);
				//console.log(data);
				var str = "";
				if(checkImageType(data)) {
					str = "<div>" +
						"<a href='displayFile?fileName=" +
						getOriginalName(data) +
						"'>" +
						"<img src='displayFile?fileName=" + data + "'/>" +
						"</a>" +
						"<input type='button' value='X' data-src='" +
						data +
						"' />" +
						//data + 
						"</div>";
				}
				else {
					str =	"<div>" +
							"<a href='displayFile?fileName=" +
							data +
							"'>" +
							data +
							"</a>" +
							"<input type='button' value='X' />" +
							"</div>";
				}
				$(".uploadList").append(str);
			},
			error: function(data) {
				alert("에러 명 :" + data);
			}
		});
		
		 console.log(file);
		
		for(var i=0; i<files.length; i++) {
			console.log(files[i]);
		} 
	});
	
	$(".uploadList").on("click", "input[type='button']", function() {
		console.log("버튼클릭됨");
		var small = $(this);
		console.log(small.attr('data-src'));
		
		$.ajax({
			url: "deleteFile",
			type: "delete",
			headers: {
				"Content-Type" : "application/json",
				"X-HTTP-Method-Override" : "DELETE"
			},
			
			dataType: "json",
			data: JSON.stringify({fileName : small.attr('data-src')}),
			success: function(result) {
				if(result == "deleted") {
					alert("deleted!");
					small.parent().remove();
				}
			}
			
			
		}); 
	});
	*/
	
	
});

function checkImageType(fileName) {
	var pattern = /jpg|gif|jpeg|png/i;
	
	return fileName.match(pattern);
}

function getOriginalName(name) {
	if(checkImageType(name) == false) return;
	var folderPath = name.substr(0,12);
	var orgName = name.substr(12+"thumbNail_".length);
	//console.log(folderPath);
	//console.log(orgName);
	return folderPath+orgName;
}