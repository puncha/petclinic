<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<jsp:useBean id="mode" scope="request" type="tk.puncha.controllers.ControllerBase.FormMode"/>
<jsp:useBean id="owner" scope="request" type="tk.puncha.models.Owner"/>
<!DOCTYPE html>

<html lang="en">
<jsp:include page="../common/header.jsp"/>
<body>
<jsp:include page="../common/nav.jsp"/>

<div class="container">

  <c:if test="${mode == 'Readonly'}" var="viewOnly"/>

  <spring:hasBindErrors name="owner">
    <c:set value="${errors.hasFieldErrors('firstName')}" var="firstNameHasErrors"/>
    <c:set value="${errors.hasFieldErrors('lastName')}" var="lastNameHasErrors"/>
    <c:set value="${errors.hasFieldErrors('address')}" var="addressHasErrors"/>
    <c:set value="${errors.hasFieldErrors('city')}" var="cityHasErrors"/>
    <c:set value="${errors.hasFieldErrors('telephone')}" var="telephoneHasErrors"/>
  </spring:hasBindErrors>

  <form:form modelAttribute="owner" action="/owners/new" method="post">
    <div class="form-group row ${firstNameHasErrors ? 'has-danger' : ''}">
      <form:label path="firstName" cssClass="col-xs-2 control-label">First name</form:label>
      <div class="col-xs-5">
        <form:input path="firstName" cssClass="form-control" cssErrorClass="form-control form-control-danger"/>
      </div>
      <div class="form-control-feedback"><form:errors cssClass="col-xs-5" path="firstName"/></div>
    </div>

    <div class="form-group row ${lastNameHasErrors ? 'has-danger' : ''}">
      <form:label path="lastName" cssClass="col-xs-2 control-label">Last name</form:label>
      <div class="col-xs-5">
        <form:input path="lastName" cssClass="form-control" cssErrorClass="form-control form-control-danger"/>
      </div>
      <div class="form-control-feedback"><form:errors cssClass="col-xs-5" path="lastName"/></div>
    </div>

    <div class="form-group row ${addressHasErrors ? 'has-danger' : ''}">
      <form:label path="address" cssClass="col-xs-2 control-label">Address</form:label>
      <div class="col-xs-5">
        <form:input path="address" cssClass="form-control" cssErrorClass="form-control form-control-danger"/>
      </div>
      <div class="form-control-feedback"><form:errors cssClass="col-xs-5" path="address"/></div>
    </div>

    <div class="form-group row ${cityHasErrors ? 'has-danger' : ''}">
      <form:label path="city" cssClass="col-xs-2 control-label">City</form:label>
      <div class="col-xs-5">
        <form:input path="city" cssClass="form-control" cssErrorClass="form-control form-control-danger"/>
      </div>
      <div class="form-control-feedback"><form:errors cssClass="col-xs-5" path="city"/></div>
    </div>


    <div class="form-group row ${telephoneHasErrors ? 'has-danger' : ''}">
      <form:label path="telephone" cssClass="col-xs-2 control-label">Telephone</form:label>
      <div class="col-xs-5">
        <form:input path="telephone" cssClass="form-control" cssErrorClass="form-control form-control-danger"/>
      </div>
      <div class="form-control-feedback"><form:errors cssClass="col-xs-5" path="telephone"/></div>
    </div>
    <c:if test="${viewOnly}">
      <div class="form-group row">
        <hr/>
        <label class="col-xs-2 label h5">Pets</label>
        <ul class="col-xs-5 m-l-2">
          <c:forEach var="pet" items="${owner.pets}">
            <li><a href="/pets/${pet.id}">${pet.name}</a></li>
          </c:forEach>
        </ul>
      </div>
    </c:if>

    <c:choose>
      <c:when test="${viewOnly}">
        <div class="form-group row">
          <div class="offset-xs-3 col-xs-3">
            <a class="btn btn-primary" href="/owners/${owner.id}/edit"><i class="fa fa-edit"></i> Edit</a>
          </div>
        </div>
      </c:when>
      <c:otherwise>
        <form:hidden path="id"/>
        <div>
          <label class="label label-danger">Errors:</label>
          <hr/>
          <ul><form:errors path="*"/></ul>
        </div>

        <div class="form-group row">
          <div class="col-xs-2 offset-xs-1">
            <button type="submit" class="btn btn-primary">Submit</button>
          </div>
          <div class="col-xs-2">
            <button type="reset" class="btn btn-warning">Reset</button>
          </div>
          <div class="col-xs-3">
            <a class="btn btn-secondary" href="/owners/${owner.id}">
              <i class="fa fa-backward"></i> Back
            </a>
          </div>
        </div>
      </c:otherwise>
    </c:choose>

  </form:form>


</div>
<jsp:include page="../common/footer.jsp"/>
<jsp:include page="../common/scripts.jsp"/>
</body>
</html>