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

  <form:form modelAttribute="pet" action="/pets/new" method="post">
    <div class="form-group row">
      <form:label path="name" cssClass="col-xs-2 control-label">Name</form:label>
      <div class="col-xs-5">
        <form:input path="name" cssClass="col-sm-10 form-control"
                    cssErrorClass="form-control form-control-danger" readonly="${viewOnly}"/>
      </div>
      <form:errors cssClass="form-control-feedback" path="name"/>
    </div>

    <div class="form-group row">
      <form:label path="type" cssClass="col-xs-2 control-label">Type</form:label>
      <div class="col-xs-5">
        <c:if test="${viewOnly}">
          <form:input cssClass="form-control" path="type.name" readonly="true"/>
        </c:if>
        <c:if test="${!viewOnly}">
          <form:select cssClass="form-control" path="type" items="${types}" itemLabel="name" itemValue="id"/>
        </c:if>
      </div>
      <form:errors cssClass="form-control-feedback" path="type"/>
    </div>

    <div class="form-group row">
      <form:label path="birthDate" cssClass="col-xs-2 control-label">Birth Date</form:label>
      <div class="col-xs-5">
        <form:input type="date" placeholder="yyyy-mm-dd" path="birthDate"
                    cssClass="col-sm-10 form-control" cssErrorClass="form-control form-control-danger"
                    readonly="${viewOnly}"/>
      </div>
      <div class="form-control-feedback"><form:errors path="birthDate"/></div>
    </div>

    <div class="form-group row">
      <form:label path="owner" cssClass="col-xs-2 control-label">Owner</form:label>
      <div class="col-xs-5">
        <c:if test="${viewOnly}">
          <form:input cssClass="form-control" path="owner.firstName" readonly="true"/>
        </c:if>
        <c:if test="${!viewOnly}">
          <form:select path="owner" items="${owners}" itemLabel="firstName" itemValue="id"
                       cssClass="form-control" cssErrorClass="form-control form-control-danger"/>
        </c:if>
      </div>
    </div>
    <form:errors cssClass="form-control-feedback" path="owner"/>

    <c:if test="${viewOnly}">
      <div class="row">
        <hr/>
        <label class="col-xs-2 control-label h5">Visits</label>
        <ul class="col-xs-3 m-l-1">
          <c:forEach var="visit" items="#{pet.visits}">
            <li class="m-y-1">${visit.visitDate} ${visit.description}
              <string:url var="deleteVisitUrl" value="/pets/${pet.id}/visits/${visit.id}/delete"/>
              <a class="btn btn-sm btn-danger" href="${deleteVisitUrl}"><i class="fa fa-remove fa-2x"></i> delete</a>
            </li>
          </c:forEach>
        </ul>
      </div>
      <div class="col-xs-3 offset-xs-2">
        <a class="btn btn-sm btn-primary" href="/pets/${pet.id}/visits/new">
          <i class="fa fa-plus fa-2x"></i> Add new visit
        </a>
      </div>
    </c:if>

    <c:if test="${!viewOnly}">
      <form:hidden path="id"/>

      <div class="form-group row">
        <a class="list-group-item list-group-item-danger"><i class="fa fa-warning"></i> Errors</a>
        <form:errors cssClass="list-group-item" path="*"/>
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
