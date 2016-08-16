<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="string" uri="http://www.springframework.org/tags" %>
<jsp:useBean id="pet" scope="request" type="tk.puncha.models.Pet"/>
<jsp:useBean id="visit" scope="request" type="tk.puncha.models.Visit"/>

<!DOCTYPE html>

<html lang="en">
<jsp:include page="../common/header.jsp"/>
<body>
<jsp:include page="../common/nav.jsp"/>

<div class="container">

  <p>Add visit to ${pet.owner.firstName}'s ${pet.type.name}: ${pet.name}</p>

  <form:form modelAttribute="visit" action="/pets/${pet.id}/visits/new" method="post">
    <div class="form-group row">
      <form:label path="description" cssClass="col-xs-2 control-label">Description</form:label>
      <div class="col-xs-5">
        <form:input path="description" cssClass="col-sm-10 form-control"/>
      </div>
      <form:errors path="description"/>
    </div>

    <div class="form-group row">
      <form:label path="visitDate" cssClass="col-xs-2 control-label">Visit Date</form:label>
      <div class="col-xs-5">
        <form:input placeholder="yyyy-mm-dd" path="visitDate" type="date" cssClass="col-sm-10 form-control" readonly="${viewOnly}"/>
      </div>
      <form:errors path="visitDate"/>
    </div>


    <div>
      <label class="label label-danger">Errors:</label>
      <br/>
      <ul><form:errors path="*"/></ul>
    </div>

    <div class="form-group row">
      <div class="col-xs-offset-2 col-xs-3">
        <button type="submit" class="btn btn-primary">Submit</button>
      </div>
      <div class="col-xs-3">
        <button type="reset" class="btn btn-primary">Reset</button>
      </div>
    </div>
  </form:form>

</div>
<jsp:include page="../common/footer.jsp"/>
<jsp:include page="../common/scripts.jsp"/>
</body>
</html>
