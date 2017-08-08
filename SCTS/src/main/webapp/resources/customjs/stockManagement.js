/* 검색 버튼 */
$('#mySearch').click(function(){
		var amount = $('#start').val() + $('#end').val();
		var keyword = $('.searchForm').val();
		var searchType = $('#searchType').val();
		var check;

			if(searchType == 'n' && keyword == "" && amount == ""){  // 전부 누락, 전체 리스트 띄움
				window.alert("검색 조건을 설정하세요.");
				return false;
			}
			
			if(keyword != ""){ // 상품명만 검색 (o)
				check = 0;
				if(amount != "" && searchType != 'n'){ // 상품명, 카테고리, 재고량 검색 (o)
					check = 1;
				}else if(amount == "" && searchType != 'n'){ // 상품명, 카테고리만 검색 (o)
					check = 2;
				}else if(amount != "" && searchType == 'n'){ // 상품명, 재고량만 검색  (o)
					check = 3;
				}
			}else if(amount != ""){ // 재고량만 검색 (o)
				check = 4;
				if(keyword == "" && searchType != 'n'){ // 카테고리, 재고량 검색 (o)
					check = 5;
				}
			}else if(searchType != 'n'){   // 카테고리만 검색 (o)
				check = 6;
			}
			
		
			
			$('.navbar-form').append("<input type='hidden' name='check' value='"+ check + "'/> ");
			$('.navbar-form').attr("action", "searchStock");
			$('.navbar-form').attr("method", "get");
			$('.navbar-form').submit();
		});

	
	/* 엉터리 코드 */
		$('.delBtn').click(function(){
			var page = $('#page').val();
			var perPageNum = $('#perPageNum').val();
			var amount = $('#start').val() + $('#end').val();
			var keyword = $('.searchForm').val();
			var searchType = $('#searchType').val();
			var startAmount = $('#start').val();
			var endAmount = $('#end').val();
			var check;
			
			if(searchType == 'n' && keyword == "" && amount == ""){  // 전부 누락, 전체 리스트 띄움
				check = 7;
			}
			
			if(keyword != ""){ // 상품명만 검색 (o)
				check = 0;
				if(amount != "" && searchType != 'n'){ // 상품명, 카테고리, 재고량 검색 (o)
					check = 1;
				}else if(amount == "" && searchType != 'n'){ // 상품명, 카테고리만 검색 (o)
					check = 2;
				}else if(amount != "" && searchType == 'n'){ // 상품명, 재고량만 검색  (o)
					check = 3;
				}
			}else if(amount != ""){ // 재고량만 검색 (o)
				check = 4;
				if(keyword == "" && searchType != 'n'){ // 카테고리, 재고량 검색 (o)
					check = 5;
				}
			}else if(searchType != 'n'){   // 카테고리만 검색 (o)
				check = 6;
			}
			
		
			var SiNum = $(this).parent().siblings().eq(2).html();
			var SiNum2 = $(this).parent().siblings().eq(4).html();
			
			$('.navbar-form').removeAttr("method", "get");
			$('.navbar-form').removeAttr("action", "stock_Management");
			$('.navbar-form').append("<input type='hidden' name='user_id' value='"+ SiNum + "'/>");
			$('.navbar-form').append("<input type='number' style='display:none;' name='goods_code' value='"+ SiNum2 + "'/> ");
			$('.navbar-form').append("<input type='hidden' name='page' value='"+ page +"'/> ");
			$('.navbar-form').append("<input type='hidden' name='perPageNum' value='"+ perPageNum +"'/> ");
			$('.navbar-form').append("<input type='hidden' name='check' value='"+ check + "'/> ");
			$('.navbar-form').attr('action','deleteStock');
			$('.navbar-form').atrr('method', 'get');
			$('.navbar-form').submit();
		});

		
		/* +버튼   */
		$("#StockBtn").click(function(){
			$('.navbar-form').attr('method', 'get');
			$('.navbar-form').attr('action', 'insertStock');
			$('.navbar-form').submit();
		});

		
		/************************** 검색버튼 **************************************/
	