<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div class="row">
	<div class="col-lg-12">
		<h3 class="page-header">
			<i class="icon_genius"></i> 문의 사항
		</h3>

		<ol class="breadcrumb">
			<li><i class="fa fa-home"></i><a href="index">Home</a></li>
			<li><i class="icon_genius"></i>ServiceCenter</li>
		</ol>
		<div class="box-body">
			<form id="formObj">
				<select name="searchType">
					<option value="t"
						<c:out value="${cri.searchType eq 't'?'selected':'' }"/>>
						제목</option>
					<option value="c"
						<c:out value="${cri.searchType eq 'c'?'selected':'' }"/>>
						내용</option>
					<option value="w"
						<c:out value="${cri.searchType eq 'w'?'selected':'' }"/>>
						작성자</option>
					<option value="tc"
						<c:out value="${cri.searchType eq 'tc'?'selected':'' }"/>>
						제목 + 내용</option>
				</select> <input type="text" name="keyword" id="keywordInput"
					value='${cri.keyword }'>
				<button class="searchBtn">Search</button>
			</form>
		</div>
	</div>
	<div class="col-lg-12">
		<div class="panel panel-default">
			<form class='formObj' method="get">
				<div class="panel-heading">
					<h2>
						<i class="fa fa-map-marker red"></i><strong>문의 사항</strong>
					</h2>
					<div class="panel-actions">
						<a href="insertHelp${pageMaker.makeSearch(pageMaker.cri.page)}"
							class="btn-setting"><i class="fa fa-plus" aria-hidden="true"></i></a>
					</div>
				</div>
				<section class="panel col-lg-12"
						style="height: 100%;">
				<table class="table table-hover">
					<tr>
						<th>글 번호</th>
						<th>제목</th>
						<th>지점</th>
						<th>분류</th>
						<th>등록자</th>
						<th>등록날짜</th>
					</tr>
					<c:if test="${fn:length(list) != 0}">
						<c:forEach items="${list }" var="helpList">
							<tr>
								<td>${helpList.bbsctt_code }</td>
								<td><a
									href='readPage${pageMaker.makeSearch(pageMaker.cri.page)}&bbsctt_code=${helpList.bbsctt_code}&msg=${msg}'>${helpList.bbsctt_sj }</a></td>
								<td>${helpList.bhf_code }</td>
								<td>${helpList.bbsctgry_code }</td>
								<td>${helpList.user_id }</td>
								<td>${helpList.bbsctt_rgsde }</td>
							</tr>
						</c:forEach>
					</c:if>
				</table>
				<c:if test="${fn:length(list) == 0}">
					<div class="text-center"
						style="font-size: 18px; font-weight: 600; margin:25px 0px 500px 0px;">게시글이
						존재하지 않습니다.</div>
				</c:if>
				</section>
			</form>
		</div>
	</div>
	<div class="text-center">
		<ul class="pagination">
			<c:if test="${msg == true}">
				<c:if test="${pageMaker.prev }">
					<li><a
						href="help_List?page=${pageMaker.makeSearch(pageMaker.startPage -1)}">&laquo;</a></li>
				</c:if>

				<c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}"
					var="idx">
					<li
						<c:out value="${pageMaker.cri.page == idx?'class =active':''}"/>>
						<a href="help_List${pageMaker.makeSearch(idx)}">${idx}</a>
					</li>
				</c:forEach>

				<c:if test="${pageMaker.next && pageMaker.endPage > 0}">
					<li><a
						href="help_List${pageMaker.makeSearch(pageMaker.endPage + 1) }">&raquo;</a></li>
				</c:if>
			</c:if>
			<c:if test="${msg == false}">
				<c:if test="${pageMaker.prev }">
					<li><a
						href="searchHelp?page=${pageMaker.makeSearch(pageMaker.startPage -1)}">&laquo;</a></li>
				</c:if>

				<c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}"
					var="idx">
					<li
						<c:out value="${pageMaker.cri.page == idx?'class =active':''}"/>>
						<a href="searchHelp${pageMaker.makeSearch(idx)}">${idx}</a>
					</li>
				</c:forEach>
				<c:if test="${pageMaker.next && pageMaker.endPage > 0}">
					<li><a
						href="searchHelp${pageMaker.makeSearch(pageMaker.endPage + 1) }">&raquo;</a></li>
				</c:if>
			</c:if>
		</ul>
	</div>
</div>

<script>
	$(document).ready(function() {
		$('.searchBtn').on("click", function() {
			var keywordInput = $("#keywordInput").val();
			if(keywordInput == ""){
				window.alert("검색어를 입력하세요.");
				return false;
			}else{
				$('#formObj').attr('method', 'get');
				$('#formObj').attr('action', 'searchHelp');
				$('#formObj').submit();
			}
		});
	});
</script>