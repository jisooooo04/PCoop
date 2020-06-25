# PCoop

### image, CSS, JS 파일들 src/main/webapp/resources 아래에 세부 폴더 생성하여 넣는다.
  > src/main/resources 아님!! 혼동하지 말 것!!<br>
  > css, fonts, images, js 파일은 만들어 놨고 그 안에 하위 폴더 생성하시면 됩니다.
### jsp는 src/main/webapp/WEB-INF/views 아래에 세부 폴더 생성하여 넣는다.
  > 기능 이름으로 생성하시면 좋습니다. 구분하기 쉽게~
### jsp 만들어서 기능 추가하실 때, views 안에 넣어 둔 template.jsp를 수정하시면 됩니다.
  > header와 sidebar-left는 따로 .jsp로 뺐습니다. (views/header에 아래에 있음)<br>
  > template.jsp 보시면 <jsp:include> 부분이 header와 sidebar 가져오는 코드입니다.<br>
  > 각각의 css 파일도 따로 뺴서 코드 간편하게 볼 수 있도록 했습니다. (src/main/webapp/resources/css에 있음)<br>
  > <section> 아래 <div id="container"> 부분이 각자의 기능을 추가하는 부분입니다.
### sidebar-left.jsp의 내비게이션 부분에 리스트 추가 방법
  > sidebar-left.jsp: 내비게이션 메뉴의 해당 .navcontents에 이름 추가(ex. class="navtitle backup")<br>
  > 본인 jsp: 해당 기능 class에 jquery로 태그 append(ex. $(".backup".append("<div></div>");)
  > 제가 먼저 해 봤는데 동작 잘돼요. 만약에 안 되거나 모르겠으면 저한테 질문 주세요.
