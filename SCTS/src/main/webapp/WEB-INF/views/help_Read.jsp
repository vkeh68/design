<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
.row{
	width: 1000px;
}
.wrap {
	font-family: "Malgun Gothic";
	font-size: 20px;
	width: 1200px;
	height: 100%;
}
.modal-content{
	margin-top: 50%;
}
#editor-title {
	width: 70%;
}
.btn-xs{
    margin-bottom:5px;
	float:right;
}
#editor-content {
	width: 70%;
	background-color:#D8D8D8;
}

.textarea {
	width: 100%;
	max-width: 900px;
	resize: none;
	/* padding: 1.1em; prevents text jump on Enter keypress */
	line-height: 1.6; /* 줄 간격 */
	overflow: visible;
	background-color:#D8D8D8;
}

.title-area {
	resize: none;
	font-size: 12pt;
	border: none;
	overflow y: none;
}

.content-area {
	min-height: 500px;
	font-size: 12pt;
	border: none;
	margin-top: 10px;
}

#editor-textArea {
	margin-top: 10px;
	border-top: 1px solid;
	width: 100%;
	height: 100%;
}
</style>
<!-- 헤더 부분 -->
<div class="row">
	<div class="col-lg-12" style="margin-left:2%;">
		<h3 class="page-header">
			<i class="icon_genius"></i> 고객 센터
		</h3>
		<ol class="breadcrumb">
			<li><i class="fa fa-home"></i><a href="index">Home</a></li>
			<li><i class="icon_genius"></i>Help</li>
		</ol>
		<form role="form">
			<!-- 컨트롤러에 값을 들고갑시다. -->
			<input type="hidden" name="bbsctt_code" value="${bbsctt_code }"> 
			<input type="hidden" name="page" value="${cri.page }"> 
			<input type="hidden" name="perPageNum" value="${cri.perPageNum}">
			<input type="hidden" name="searchType" value="${cri.searchType }">
			<input type="hidden" name="keyword" value="${cri.keyword }">
		</form>
		<!-- 텍스트 에리어 부분 -->
		<div class="wrap" id="wrap">
			<div id="editor-content">
				<div id="editor-title">
					<textarea class="title-area textarea" name="bbsctt_sj"
						readonly="readonly">${helpVO.bbsctt_sj }</textarea>
				</div>
				<div id="editor-textArea">
					<textarea class="content-area textarea" name="bbsctt_cn"
						readonly="readonly">${helpVO.bbsctt_cn }</textarea>
				</div>
			</div>
			<div class="editor-footer">
				<button type="submit" id="upList" class="btn btn-primary">수정</button>
				<button type="submit" id="delList" class="btn btn-danger">삭제</button>
				<button type="submit" id="moveList" class="btn btn-primary">리스트로</button>
			</div>
		</div>
	</div>
</div>
	<script>
	/////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////// 게시글 수정,삭제,돌아가기  		  ///////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////
	
	$(document).ready(function() {
		var msg = ${msg};
		var formObj = $("form[role='form']");
			
		$('#upList').on('click', function() {
			formObj.attr('action', "updateHelp");
			formObj.attr('method', 'get');
			formObj.submit();
		});

		$('#delList').on("click", function() {
			if (confirm("삭제하시겠습니까?")) {
				if (msg == true) {
					formObj.attr('action', 'deleteHelp');
					formObj.attr('method', 'post');
				} else {
					formObj.attr('action', 'deleteHelp');
					formObj.attr('method', 'get');
				}
				formObj.submit();
				window.alert("삭제되었습니다.");
			} else {
				return false;
			}
		});

		$('#moveList').on('click', function() {
			formObj.attr("method", "get");

			if (msg == true) { /* 일반 리스트 컨트롤러에서 넘어 왔을 경우 */
				formObj.attr("action", "help_List");
			} else { /* 검색 컨트롤러에서 넘어 왔을 경우 */
				formObj.attr("action", "searchHelp");
			}
			formObj.submit();
		});

	}); 
	
	
	
</script>