<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>

<html lang="en">
<jsp:include page="../common/header.jsp"/>
<body>
<jsp:include page="../common/nav.jsp"/>

<div class="container">

  <form:form modelAttribute="owner" cssClass="form-horizontal">
    <c:forEach var="prop" items='${["firstName", "lastName", "address", "city", "telephone"]}'>
      <div class="form-group">
        <form:label path="${prop}" cssClass="col-xs-2 control-label">${prop}</form:label>
        <div class="col-xs-5">
          <form:input path="${prop}" cssClass="col-sm-10 form-control" readonly="true"/>
        </div>
      </div>
    </c:forEach>
  </form:form>
</div>
<jsp:include page="../common/footer.jsp"/>
<jsp:include page="../common/scripts.jsp"/>

<script type="text/javascript">
  $(function() {
    $("form label").each(function() {
      $this = $(this);
      $this.text(_.chain($this.text()).kebabCase().capitalize().value().replace('-', ' '));
    });
  });
</script>


</body>
</html>