$('.btn-default').on("click",function(){
		//alert('hi');
		var row = document.getElementById('row');
		row.style.display("none");
	});
	
	$(".fileUpload").on("dragenter dragover", function(event){
		event.preventDefault();
	});

	$(".fileUpload").on("drop", function(event){
		event.preventDefault();
		var uploadTest = confirm('upload?');
		if(uploadTest==true){
			var files = event.originalEvent.dataTransfer.files;
			var file = files[0];
			console.log(file);
			
			var formData = new FormData();
			formData.append("file", file);
			
			$.ajax({
				url:"upload",
				data: formData, 
				dataType:"text", 
				processData: false, 
				contentType: false, 
				type: "POST", 
				success: function(data) {
					var str = "";

					if(checkImageType(data)){
						str = "<div class='imgDiv' style='float: left; width: 32%; height:40%; margin-top:15px; margin-right:5px;'>"+"<img class='img' src='"+
								"displayFile?fileName="+data+"'style='width:100%; height:100%;'/>"+"</div>";
						
						
						$(document).on("click",".img",function(){
							var image = $(".img").attr('src');
							var imgSrc = "displayFile?fileName="+data;
							
							if(image == imgSrc){
								var deleteTest = confirm('delete?');
								
								if(deleteTest==true){
									alert('deleted');
									alert(image);
								}else{
									alert('canceled');
								}
							}
						});
					}
					
					$(".uploadList").append(str);
				}
			});
			
		}else{
			alert("취소되었습니다.");
		}
		
	});
	
	function checkImageType(fileName){
		var pattern = /jpg|gif|jpeg|png/i;
		return fileName.match(pattern);
	}
	
	function getOriginalName(name){
		if(checkImageType(name) == false) return;
		
		var folderPath = name.substr(0,12); // 파일 이름에서 폴더명 추출.
		var orgName = name.substr(12+"thumbNail_".length);
		
		console.log(orgName);
		return folderPath+orgName;
	}
