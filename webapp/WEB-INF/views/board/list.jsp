<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% pageContext.setAttribute("newLine", "\r\n"); %>
<!DOCTYPE html>
<html>
<head>
<title>mysite4</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath}/board/search" method="get">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>			
					
					<c:set var="count" value="${pageMap.total-5*(pageMap.page-1) }"/>
					<c:forEach items="${list }" var="vo" varStatus ="status">
					
					<tr>
						<td>${count-status.index }</td>
						<c:choose>
						<c:when test="${vo.depth > 0 }">
							<td style="text-align:left; padding-left:${vo.depth*20}px">
								<img src="${pageContext.request.contextPath}/assets/images/reply.png">
								<a href="${pageContext.request.contextPath}/board/view?no=${vo.no }&user_no=${vo.user_no }&page=${pageMap.page}">${vo.title }</a>
							</td>
						</c:when>
						<c:otherwise>
							<td style="text-align:left">
								<a href="${pageContext.request.contextPath}/board/view?no=${vo.no }&user_no=${vo.user_no }">${vo.title }</a>
							</td>
						</c:otherwise>
						</c:choose>
						
						<td>${vo.user_name }</td>
						<td>${vo.viewCount }</td>
						<td>${vo.reg_date }</td>
						
						<c:if test="${sessionScope.authUser.no == vo.user_no }">
							<td><a href="${pageContext.request.contextPath}/board/delete?no=${vo.no }" class="del">삭제</a></td>
						</c:if>
					</tr>
					
					</c:forEach>	
					
				</table>
				
				<div class="pager">
					<ul>					
						<c:if test="${pageMap.left == 1 }">
							<li><a href="${pageContext.request.contextPath}/board?page=${pageMap.startPage-5 }">◀</a></li>	
						</c:if>
						<c:forEach begin="${pageMap.startPage }" end="${pageMap.lastPage }" var="i">
							<c:choose>
								<c:when test="${i == pageMap.page }">
									<li class="selected"><a href="${pageContext.request.contextPath}/board?page=${i }">${i }</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="${pageContext.request.contextPath}/board?page=${i }">${i }</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						
						<c:if test="${pageMap.right == 1 }">
							<li><a href="${pageContext.request.contextPath}/board?page=${pageMap.lastPage +1 }">▶</a></li>
						</c:if>
						
					</ul>
				</div>
				<div class="bottom">
					<c:if test="${not empty sessionScope.authUser }">
						<a href="${pageContext.request.contextPath}/board/addForm?user_no=${sessionScope.authUser.no }" id="new-book">글쓰기</a>
					</c:if>
				</div>
								
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp"/>
		<c:import url="/WEB-INF/views/include/footer.jsp"/>
	</div>
</body>
</html>