<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Document</title>
<link rel="stylesheet" href="/resources/css/main/header.css?after" />
<link rel="stylesheet" href="/resources/css/main/sidebar-left.css?after" />

<script>
$(function(){
	
	//왼쪽 네비 메뉴 클릭
    $(".navcontents").hide();
    $(document).on("click", ".navtitle", function(){
        var selectIndex = ($(this).index()-1)/2;

        $(".navcontents").each(function(index, item){
            if(index == selectIndex){
                $(item).slideToggle(300);
            }
        })
    })

    //오른쪽 네비 열기
    $(".header_sidebar").on("click", function(){
        $("#rightnav").animate({right:0}, 300);
    })
    
    //오른쪽 네비 닫기
    $(".close_r_nav").on("click", function(){
        $("#rightnav").animate({right:-250}, 300);
    })
    
})
</script>

</head>

<body>

	<!-- 헤더 -->
	<header>
		<!-- 로고 -->
		<div class="logo" OnClick="location.href ='/goMain'">
			<b>P</b>COOP!
		</div>

		<!-- 메뉴 -->
		<div class="row">
			<div class="d-md-block d-none header_menu_list">
				<div class="header_menu">커뮤니티</div>
				<div class="header_menu">로그아웃</div>
				<div class="header_menu" OnClick="location.href ='/member/gomypage'">마이 페이지</div>
			</div>
			<div class="d-md-none d-block header_menu_list">
				<span class="fa fa-bars header_sidebar"></span>
				<!-- <img src="resources/images/header/menu.png" class=header_sidebar> -->
			</div>
		</div>
	</header>

	<!-- 오른쪽 사이드바 -->
	<nav id=rightnav>
		<span class="fa fa-times close_r_nav"></span>
		<!--  <img src="resources/images/header/close.png" class=close_r_nav>-->
		<div class=r_navmenu>마이 페이지</div>
		<div class=r_navmenu>로그아웃</div>
		<div class=r_navmenu>커뮤니티</div>
	</nav>

</body>
</html>