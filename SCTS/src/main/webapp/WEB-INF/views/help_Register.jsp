<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<style>
.wrap{
	font-family:"Malgun Gothic";
	font-size: 20px;
	width:1200px;
	height:100%;
	margin-left:7%";
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
			<li><i class="icon_genius"></i>문의 사항</li>
		</ol>
		<form role="formObj" action="insertHelp" method="post">
			<input type="hidden" name="page" value="${cri.page }"> <!-- form으로 현재 페이지 등 데이터를 넘겨줌 -->
			<input type="hidden" name="perPageNum" value="${cri.perPageNum }">
			<input type="hidden" name="searchType" value="${cri.searchType }">
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
						<textarea name="bbsctt_sj" class="title-area textarea" onkeyup="resize(this)"
							placeholder="제목"></textarea>
					</div>
					<div id="editor-textArea">
						<textarea name="bbsctt_cn" class="content-area textarea" onkeyup="resize(this)"
							placeholder="내용을 입력하세요."></textarea>
					</div>
					<div id="editor-submit">
						<button type="submit" class="btn btn-primary">등록</button>
						<button type="submit" class="btn btn-danger">취소</button>
					</div>
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
		if(confirm("등록하시겠습니까?")){
			window.alert("등록되었습니다.");
			formObj.submit();
		}else{
			return false;
		}
	});
	
	$(".btn-danger").on("click", function(){
		formObj.attr("method", "get");
		if(confirm("등록을 취소하시겠습니까?")){
			if(msg == true){ /* 일반 리스트 컨트롤러에서 넘어 왔을 경우 */
				formObj.attr("action", "help_List");
			}else if(msg == false){ /* 검색 컨트롤러에서 넘어 왔을 경우 */
				formObj.attr("action", "searchHelp");
			}
				formObj.submit();
		}else{
			return false;
		}
		
	});
});
function resize(obj) {
    obj.style.height = "1px";
    obj.style.height = (20+obj.scrollHeight)+"px";
}
</script>