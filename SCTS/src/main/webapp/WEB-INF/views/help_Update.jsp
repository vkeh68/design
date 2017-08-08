<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
#wrap{
	font-family:"Malgun Gothic";
	font-size: 20px;
	width:1200px;
	height:100%;
}

#editor-title{
	width: 70%;
}

#editor-content{
 	width: 70%;
 	height: 70%;
}

.textarea{
	width:100%;
	max-width:900px;
	resize: none;
	/* padding: 1.1em; prevents text jump on Enter keypress */
    line-height: 1.6; /* 줄 간격 */
	overflow:visible
}

.title-area{
	resize:none;
	font-size: 12pt;
	border:none;
	background-color: #EEEEEE;
	overflow y:none;
}

.content-area{
	min-height:500px;
	font-size: 12pt;
	border:none;
	margin-top: 10px;
	background-color: #EEEEEE;
}

#editor-textArea{
	margin-top: 10px;
	border-top: 1px solid;
	width:100%;
	height:100%;
}
</style>
<div class="row">
	<div class="col-lg-12">
		<h3 class="page-header">
			<i class="icon_genius"></i> 고객 센터
		</h3>
		<ol class="breadcrumb">
			<li><i class="fa fa-home"></i><a href="index">Home</a></li>
			<li><i class="icon_genius"></i>Help</li>
		</ol>
		<form role="form" method="post" action="updateHelp">
			<input type="hidden" name="bbsctt_code" value="${bbsctt_code }">
			<input type="hidden" name="page" value="${cri.page}">
			<input type="hidden" name="perPageNum" value="${cri.perPageNum}">
			<input type="hidden" name="searchType" value="${cri.searchType }"> <!-- 여기서 히든으로 넣어주냐 안 넣어주냐에 따라 주소창에 넘겨주는 값이 달라짐. -->
			<input type="hidden" name="keyword" value="${cri.keyword }">
			<div class="wrap">
				<div id="editor-content">
					카테고리 선택 : <span id="editor-category">
						<select name="bbsctgry_code">
							<option value="1">공지사항</option>
							<option value="2">고객문의사항</option>
							<option value="3">이벤트</option>
						</select>
					</span>
					<div id="editor-title">
						<textarea class="title-area textarea" name="bbsctt_sj">${helpVO.bbsctt_sj }</textarea>
					</div>
					<div id="editor-textArea">
						<textarea class="content-area textarea" autofocus name="bbsctt_cn">${helpVO.bbsctt_cn }</textarea>
					</div>
				</div>
			<div class="editor-footer">
				<button type="submit" class="btn btn-primary">저장</button>
				<button type="submit" class="btn btn-warning">리스트로</button>
			</div>
			</div>
		</form>
	</div>
</div>
<script>
$(document).ready(function(){
	var formObj = $("form[role='form']");
	var msg = ${msg};
	
	$(".btn-primary").on("click", function(){
		if(confirm("수정하시겠습니까?")){
			window.alert("수정되었습니다.");
			formObj.submit();
		}else{
			return false;
		}
	});
	
	$(".btn-warning").on("click", function(){
		if(msg != false){
			/* window.alert("true다"); */
			formObj.attr("method", "get");
			formObj.attr("action", "help_List");
		}else if(msg == false){
			/* window.alert("false다"); */
			formObj.attr("method", "get");
			formObj.attr("action", "searchHelp");
		}
	});
});
</script>