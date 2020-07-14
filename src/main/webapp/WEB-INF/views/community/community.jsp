<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="../header/cdn.jsp"></jsp:include>

<style>

            *{box-sizing: border-box; margin: 0px;}

            header{
                border: 1px solid black;
                height: 80px;
            }
            section{

            }
            footer{
                border: 1px solid black;
                height: 80px;
            }


            .row{
                width: 100%;
                margin: 0px;
            }


            /* contents_section 설정 */
            .contents_section{
                margin: 0px;
            }

            /* card 스타일 */
            .card_section{
                padding: 0px;
            }
            .card{
                border: 1px solid darkgray;
                /*                border-right: 3px solid dimgray;*/
                /*                border-bottom: 3px solid darkgray;*/
                width: 300px;
                height: 360px;
                margin: auto;
                padding: 0px;
                border-radius: 0;
                margin-top: 20px;
                margin-bottom: 20px;
            }
            /*            .card *{border: 1px solid orange;}*/

            /* 카드 맨위 프로필 박스 */
            .profile_box{
                height: 45px;
                padding: 10px;
            }
            .profile{
                float: left;
                width: 30px;
                height: 30px;
            }
            .profile_img{
                width: 100%;
                height: 100%;
                border-radius: 20px;
            }
            .writer{
                float: left;
                margin-left: 5px;
                line-height: 30px;
                font-weight: 600;
            }

            /* 카드 이미지 박스 */
            .image_box{
                width: 100%;
                max-height: 190px;
                text-align: center;
            }
            .card_img{
                max-width: 100%;
                max-height: 100%;
                /*                padding: 5px;*/
                padding-top: 5px;
                padding-bottom: 5px;
            }

            /* 카드 컨텐츠 박스 */
            /* 360 - 45 - 190 = 125 */
            .contents_box{
                padding: 5px;
                padding-bottom: 5px;
                max-height: 120px;
                overflow: hidden;
            }
            .title{
                height: 45px;
            }
            .category{
                max-height: 45px;
                color: dodgerblue;
                font-size: 14px;
            }
            a{margin-right: 3px;}
            a:hover{cursor: pointer;}
            .card_bottom{
                overflow: hidden;
                position: absolute;
                width: 96%;
                bottom: 5px;
            }
            .view{
                float: left;
                width: 50%;
                color: darkgray;
                font-size: 13px;
            }
            .date{
                float: left;
                width: 50%;
                text-align: right;
                color: darkgray;
                font-size: 13px;
            }


            /* 검색창 영역 */
            .search_section{
                margin-top: 30px;
                margin-bottom: 20px;
            }
            .search_gap{
                padding: 0px;
            }
            .search_box{
                width: 100%;
                height: 130px;
                border: 1px solid lightgray;
                background-color: #f7f7f7;
                padding: 25px;
                padding-left: 50px;
            }
            .search_title{
                margin-bottom: 5px;
                font-size: 18px;
                color: dimgray;
            }
            .search_btn{
                border: 1px solid gray;
            }



            /* 페이지 네비 */
            .pagenavi_section{
                border: 1px solid black;
                height: 50px;
                text-align: center;
                line-height: 50px;
                width: 100%;
            }
            .write_btn{
                height: 30px;
                line-height: 0px;
                right: 0px;
                margin-top: 10px;
                position: absolute;
            }


            .gap{
                padding: 0px;
            }
            .main{
                padding: 0px;
            }
            hr{
                background-color: black;
                height: 2px;
                padding: 0px;
            }

</style>

</head>
<body>
	<header>헤더</header>

	<section>

		<!-- 검색 섹션 -->
		<div class="search_section row">
			<div class="search_gap col-1"></div>
			<div class="col-10 search_box">
				<div class=search_title>키워드 검색</div>
				<select name="search_category">
					<option value="none">카테고리</option>
					<option value="웹사이트">웹사이트</option>
					<option value="Android">Android</option>
					<option value="iOS">iOS</option>
					<option value="JAVA">JAVA</option>
					<option value="HTML">HTML</option>
					<option value="기타">기타</option>
				</select> <input type=text>
				<button type=submit class="search_btn">검색</button>
			</div>
			<div class="search_gap col-1"></div>
		</div>


		<!-- 컨텐츠 섹션 -->
		<div class="main row">
			<div class="gap col-1"></div>

			<div class="main col-10">
				<hr>

				<!-- 컨텐츠 col-10 -->
				<div class="contents_section row">
					<!-- 카드 섹션 -->
					<div class="card_section col-lg-4 col-md-6 col-12">
						<div class="card">
							<div class=profile_box>
								<div class=profile>
									<img src="resources/images/chatting/profile.png" class=profile_img>
								</div>
								<div class=writer>작성자</div>
							</div>

							<div class=image_box>
								<img src="upload/webpage.PNG" class=card_img>
							</div>

							<div class=contents_box>
								<div class=title>효율적인 협업을 위한 PCoop!</div>
								<div class=category>
									<a>#웹사이트</a> <a>#Java</a> <a>#CSS</a> <a>#HTML</a>
								</div>
								<div class=card_bottom>
									<div class=view>조회수 10</div>
									<div class=date>2020/07/12</div>
								</div>

							</div>
						</div>
					</div>

					<!-- 카드 섹션 -->
					<div class="card_section col-lg-4 col-md-6 col-12">
						<div class="card">
							<div class=profile_box>
								<div class=profile>
									<img src="resources/images/chatting/profile.png" class=profile_img>
								</div>
								<div class=writer>작성자</div>
							</div>

							<div class=image_box>
								<img src="upload/webpage.PNG" class=card_img>
							</div>

							<div class=contents_box>
								<div class=title>효율적인 협업을 위한 PCoop!</div>
								<div class=category>
									<a>#웹사이트</a> <a>#Java</a> <a>#CSS</a> <a>#HTML</a>
								</div>
								<div class=card_bottom>
									<div class=view>조회수 10</div>
									<div class=date>2020/07/12</div>
								</div>

							</div>
						</div>
					</div>

					<!-- 카드 섹션 -->
					<div class="card_section col-lg-4 col-md-6 col-12">
						<div class="card">
							<div class=profile_box>
								<div class=profile>
									<img src="resources/images/chatting/profile.png" class=profile_img>
								</div>
								<div class=writer>작성자</div>
							</div>

							<div class=image_box>
								<img src="upload/webpage.PNG" class=card_img>
							</div>

							<div class=contents_box>
								<div class=title>효율적인 협업을 위한 PCoop!</div>
								<div class=category>
									<a>#웹사이트</a> <a>#Java</a> <a>#CSS</a> <a>#HTML</a>
								</div>
								<div class=card_bottom>
									<div class=view>조회수 10</div>
									<div class=date>2020/07/12</div>
								</div>

							</div>
						</div>
					</div>
				</div>
				<!-- 카드 섹션 종료 -->

				<hr>

				<div class="pagenavi_section">
					페이지네비
					<button class=write_btn onclick="location.href='community_write'">글쓰기</button>
				</div>

			</div>
			<!-- 컨텐츠 col-10 종료 -->

			<div class="gap col-1"></div>
		</div>
		<!-- 컨텐츠 섹션 종료 -->

	</section>

	<footer>푸터</footer>
</body>
</html>