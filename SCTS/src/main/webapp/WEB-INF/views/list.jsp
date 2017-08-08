<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js" type="text/javascript"></script>
   
<style>
   
   #content {
       margin: 20px;
       padding: 10px;
       background: #393;
   }
   
   #rank-list a {
       color: #FFF;
       text-decoration: none;
   }
   
   #rank-list a:hover {
       text-decoration: underline;
   }
   
   #rank-list {
       overflow: hidden;
       width: 160px;
       height: 20px;
       margin: 0;
   }
   
   #rank-list dt {
       display: none;
   }
   
   #rank-list dd {
       position: relative;
       margin: 0;
   }
   
   #rank-list ol {
       position: absolute;
       top: 0;
       left: 0;
       margin: 0;
       padding: 0;
       list-style-type: none;
   }
   
   #rank-list li {
       height: 20px;
       line-height: 20px;
   }
</style>
        <div id="content">
            <dl id="rank-list">
                <dt>실시간 급상승 검색어</dt>
                <dd>
                    <ol>
                        <li><a href="#">1 순위</a></li>
                        <li><a href="#">2 순위</a></li>
                        <li><a href="#">3 순위</a></li>
                        <li><a href="#">4 순위</a></li>
                        <li><a href="#">5 순위</a></li>
                        <li><a href="#">6 순위</a></li>
                        <li><a href="#">7 순위</a></li>
                        <li><a href="#">8 순위</a></li>
                        <li><a href="#">9 순위</a></li>
                        <li><a href="#">10 순위</a></li>
                    </ol>
                </dd>
            </dl>
        </div>

<script>
   $(function() {
       var count = $('#rank-list li').length;
       var height = $('#rank-list li').height();
   
       alert(count);
       alert(height);
       function step(index) {
           $('#rank-list ol').delay(2000).animate({
               top: -height * index,
           }, 500, function() {
               step((index + 1) % count);
           });
       }
   
       step(1);
   });
</script>