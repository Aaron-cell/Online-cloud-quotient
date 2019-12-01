<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0"> 
	<meta name="format-detection" content="telephone=no"> 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="/css/a.css">
	<link rel="stylesheet" type="text/css" href="/css/addtocart-album.css">
	<title>商品已成功加入购物车</title>
<link rel="stylesheet" type="text/css" href="/css/a_002.css">
<link rel="stylesheet" type="text/css" href="/css/share-rec.css">
	<script>var jdpts = new Object(); jdpts._st = new Date().getTime();</script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" type="text/css" href="/css/taotao.css" media="all" />
	<link rel="stylesheet" type="text/css" href="/css/pshow.css" media="all" />
	
</head>
<body version="140120">
<script type="text/javascript">try{(function(flag){ if(!flag){return;} if(window.location.hash == '#m'){var exp = new Date();exp.setTime(exp.getTime() + 30 * 24 * 60 * 60 * 1000);document.cookie = "pcm=1;expires=" + exp.toGMTString() + ";path=/;domain=jd.com";return;}else{var cook=document.cookie.match(new RegExp("(^| )pcm=([^;]*)(;|$)"));var flag=false;if(cook&&cook.length>2&&unescape(cook[2])=="1"){flag=true;}} var userAgent = navigator.userAgent; if(userAgent){ userAgent = userAgent.toUpperCase();if(userAgent.indexOf("PAD")>-1){return;} var mobilePhoneList = ["IOS","IPHONE","ANDROID","WINDOWS PHONE"];for(var i=0,len=mobilePhoneList.length;i<len;i++){ if(userAgent.indexOf(mobilePhoneList[i])>-1){var url="http://m.jd.com/product/"+pageConfig.product.skuid+".html";if(flag){pageConfig.product.showtouchurl=true;}else{window.location.href = url;}break;}}}})((function(){var json={"6881":3,"1195":3,"10011":3,"6980":3,"12360":3};if(json[pageConfig.product.cat[0]+""]==1||json[pageConfig.product.cat[1]+""]==2||json[pageConfig.product.cat[2]+""]==3){return false;}else{return true;}})());}catch(e){}</script>
<!-- header start -->
<jsp:include page="commons/header.jsp" />
<!-- header end -->
<div class="main">

	<div class="success-wrap">
		<div class="w" id="result">
			<div class="m succeed-box">
				<div class="mc success-cont">
					<div class="success-lcol">
						<div class="success-top">
							<b class="succ-icon"></b>
							<h3 class="ftx-02">商品已成功加入购物车！</h3>
						</div>
						<div class="p-item">
							<div class="p-img">
								<a href="http://www.taotao.com/item/${cartItem.id}.html" target="_blank">
									<img src="${cartItem.image }" width="60" height="60" data-img="1"  clstag="pageclick|keycount|201601152|11">
								</a>
							</div>
							<div class="p-info">
								<div class="p-name">
									<a href="http://www.taotao.com/item/${cartItem.id}.html" target="_blank" clstag="pageclick|keycount|201601152|2" title="${cartItem.title}">${cartItem.title}</a>
								</div>
								<div class="p-extra">
									<span class="txt" >价格：￥<fmt:formatNumber groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" value="${cartItem.price / 100 }"/></span>
									<span class="txt">/  数量：${num}</span>
								</div>
							</div>
							<div class="clr">
							</div>
						</div>
					</div>
					<div class="success-btns success-btns-new">
						<div class="success-ad">
							<a href="#none"></a>
						</div>
						<div class="clr"></div>
						<div>
							<a class="btn-tobback" href="/item/${cartItem.id}.html" target="_blank" clstag="pageclick|keycount|201601152|3">查看商品详情</a>
							<a class="btn-addtocart" href="/cart/cart.html" id="GotoShoppingCart" clstag="pageclick|keycount|201601152|4"><b></b>去购物车结算</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- footer start -->
<jsp:include page="commons/footer.jsp" />
<!-- footer end -->
<script type="text/javascript" src="/js/jquery-1.6.4.js"></script>
</body>
</html>