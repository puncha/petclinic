<%--@elvariable id="exception" type="java.lang.exception"--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html lang="en">
<jsp:include page="../common/header.jsp"/>
<body>
<jsp:include page="../common/nav.jsp"/>

<div class="container">
    <h1>Exceptions: ${exception.getMessage()} </h1>
      <c:set var="stack" value="${exception.getStackTrace()}"/>
      <c:forEach var="stackItem" items='${stack}'>
        <samp>${stackItem}</samp>
      </c:forEach>
    </div>
  </div>
</div>

<jsp:include page="../common/footer.jsp"/>
<jsp:include page="../common/scripts.jsp"/>

</body>
</html>
