<%--@elvariable id="exception" type="java.lang.exception"--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html lang="en">
<jsp:include page="../common/header.jsp"/>
<body>
<jsp:include page="../common/nav.jsp"/>

<div class="container">
  <div class="panel panel-danger panel-exception">
    <div class="panel-heading">Exceptions: ${exception.getMessage()} </div>
    <div class="panel-body">
      <c:set var="stack" value="${exception.getStackTrace()}"/>
      <c:forEach var="stackItem" items='${stack}'>
        ${stackItem}
      </c:forEach>
    </div>
  </div>
</div>

<jsp:include page="../common/footer.jsp"/>
<jsp:include page="../common/scripts.jsp"/>

</body>
</html>
