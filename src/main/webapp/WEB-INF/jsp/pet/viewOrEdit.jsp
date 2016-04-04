<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<jsp:useBean id="mode" scope="request" type="tk.puncha.controllers.ControllerBase.FormMode"/>
<jsp:useBean id="pet" scope="request" type="tk.puncha.models.Pet"/>
<jsp:useBean id="types" scope="request" type="java.util.List<tk.puncha.models.PetType>"/>
<jsp:useBean id="owners" scope="request" type="java.util.List<tk.puncha.models.Owner>"/>
<!DOCTYPE html>

<html lang="en">
<jsp:include page="../common/header.jsp"/>
<body>
<jsp:include page="../common/nav.jsp"/>

<div class="container">

  <c:if test="${mode == 'Readonly'}" var="viewOnly"/>

  <form:form modelAttribute="pet" cssClass="form-horizontal" action="/pets/new" method="post">
    <div class="form-group">
      <form:label path="name" cssClass="col-xs-2 control-label">Name</form:label>
      <div class="col-xs-5">
        <form:input path="name" cssClass="col-sm-10 form-control" readonly="${viewOnly}"/>
      </div>
      <form:errors path="name"/>
    </div>


    <div class="form-group">
      <form:label path="typeId" cssClass="col-xs-2 control-label">Type</form:label>
      <div class="col-xs-5">
        <form:select path="typeId" items="${types}" itemLabel="name" itemValue="id" readonly="${viewOnly}"/>
      </div>
      <form:errors path="typeId"/>
    </div>

    <div class="form-group">
      <form:label path="birthDate" cssClass="col-xs-2 control-label">Birth Date</form:label>
      <div class="col-xs-5">
        <form:input placeholder="yyyy-mm-dd" path="birthDate" cssClass="col-sm-10 form-control" readonly="${viewOnly}"/>
      </div>
      <form:errors path="birthDate"/>
    </div>

    <div class="form-group">
      <form:label path="ownerId" cssClass="col-xs-2 control-label">Owner</form:label>
      <div class="col-xs-5">
        <form:select path="ownerId" items="${owners}" itemLabel="lastName" itemValue="id" readonly="${viewOnly}"/>
      </div>
      <form:errors path="ownerId"/>
    </div>

    <c:if test="${!viewOnly}">
      <form:hidden path="id"/>

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
    </c:if>
  </form:form>
</div>
<jsp:include page="../common/footer.jsp"/>
<jsp:include page="../common/scripts.jsp"/>
</body>
</html>