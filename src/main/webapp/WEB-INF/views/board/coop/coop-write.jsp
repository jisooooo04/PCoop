<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML>
<!--
	Projection by TEMPLATED
	templated.co @templatedco
	Released for free under the Creative Commons Attribution 3.0 license (templated.co/license)
-->
<html>


<head>
<title>PCOOP!</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />

<jsp:include page="../../header/cdn.jsp"></jsp:include>

</head>

<script>
	$(document).ready(function() {
		$('#summernote').summernote({
			placeholder : 'content',
			minHeight : 370,
			maxHeight : null,
			focus : true,
			lang : 'ko-KR'
		});
	});
</script>
</head>
<body>
	<h2 style="text-align: center;">글 작성</h2>
	<br>
	<br>
	<br>

	<div style="width: 60%; margin: auto;">
		<form method="post" action="/coop-write">
			<input type="text" name="writer" style="width: 20%;"
				placeholder="작성자" /><br> <input type="text" name="title"
				style="width: 40%;" placeholder="제목" /> <br>
			<br>
			<textarea id="summernote" name="content"></textarea>
			<input id="subBtn" type="button" value="글 작성" style="float: right;"
				onclick="goWrite(this.form)" />
		</form>
	</div>
<script>
function goWrite(frm) {
	var title = frm.title.value;
	var writer = frm.writer.value;
	var content = frm.content.value;
	
	if (title.trim() == ''){
		alert("제목을 입력해 주세요");
		return false;
	}
	if (writer.trim() == ''){
		alert("작성자를 입력해 주세요");
		return false;
	}
	if (content.trim() == ''){
		alert("내용을 입력해 주세요");
		return false;
	}
	frm.submit();
}
</script>	
</body>
</html>