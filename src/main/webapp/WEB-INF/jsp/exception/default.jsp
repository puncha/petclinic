<%--@elvariable id="exception" type="java.lang.exception"--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html lang="en">
<jsp:include page="../common/header.jsp"/>
<body>
<jsp:include page="../common/nav.jsp"/>

<div class="container">
  <img src="/resources/images/pets.png"/>
  <h2>Something happened...</h2>
  <samp>${exception.message}</samp>
</div>

<jsp:include page="../common/footer.jsp"/>
<jsp:include page="../common/scripts.jsp"/>

</body>
</html>
