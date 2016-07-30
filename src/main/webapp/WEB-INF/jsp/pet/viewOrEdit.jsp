<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="string" uri="http://www.springframework.org/tags" %>
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
      <form:label path="type" cssClass="col-xs-2 control-label">Type</form:label>
      <div class="col-xs-5">
        <c:if test="${viewOnly}">
          <form:input cssClass="form-control" path="type.name" readonly="true"/>
        </c:if>
        <c:if test="${!viewOnly}">
          <form:select path="type" items="${types}" itemLabel="name" itemValue="id" readonly="${viewOnly}"/>
        </c:if>
      </div>
      <form:errors path="type"/>
    </div>

    <div class="form-group">
      <form:label path="birthDate" cssClass="col-xs-2 control-label">Birth Date</form:label>
      <div class="col-xs-5">
        <form:input placeholder="yyyy-mm-dd" path="birthDate" cssClass="col-sm-10 form-control" readonly="${viewOnly}"/>
      </div>
      <form:errors path="birthDate"/>
    </div>

    <div class="form-group">
      <form:label path="owner" cssClass="col-xs-2 control-label">Owner</form:label>
      <div class="col-xs-5">
        <c:if test="${viewOnly}">
          <form:input cssClass="form-control" path="owner.firstName" readonly="true"/>
        </c:if>
        <c:if test="${!viewOnly}">
          <form:select path="owner" items="${owners}" itemLabel="firstName" itemValue="id" readonly="${viewOnly}"/>
        </c:if>
      </div>
    </div>
    <form:errors path="owner"/>

    <c:if test="${viewOnly}">
      <hr/>
      <label class="col-xs-2 control-label">Visits</label>
      <a class="btn btn-xs btn-primary" href="/pets/${pet.id}/visits/new">
        <i class="glyphicon glyphicon-plus"></i>
      </a>
      <ul>
        <c:forEach var="pet" items="${owner.pets}">
          <li class="badge">${pet.name}</li>
        </c:forEach>
      </ul>
      <ol>
        <c:forEach var="visit" items="#{pet.visits}">
          <li class="badge">${visit.visitDate} ${visit.description}
            <string:url var="deleteVisitUrl" value="/pets/${pet.id}/visits/${visit.id}/delete"/>
            <a class="btn btn-xs btn-danger" href="${deleteVisitUrl}"><i class="glyphicon glyphicon-remove"></i></a>
          </li>
        </c:forEach>
      </ol>


    </c:if>


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
