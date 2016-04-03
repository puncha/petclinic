<%--@elvariable id="owners" type="java.util.List"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>

<html lang="en">
<jsp:include page="../common/header.jsp"/>
<body>
<jsp:include page="../common/nav.jsp"/>

<div class="container">
  <a class="btn btn-xs btn-primary pull-right" href="/owners/new">
    <i class="glyphicon glyphicon-plus"></i>
  </a>

  <table class="table table-owners">
    <thead>
    <tr>
      <th>First Name</th>
      <th>Last Name</th>
      <th>Address</th>
      <th>City</th>
      <th>Telephone</th>
      <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="owner" items="${owners}">
      <tr>
        <td>${owner.firstName}</td>
        <td>${owner.lastName}</td>
        <td>${owner.address}</td>
        <td>${owner.city}</td>
        <td>${owner.telephone}</td>
        <td>
          <div class="btn-group btn-group-xs">
            <a class="btn btn-xs btn-success" href="/owners/${owner.id}">View</a>
            <a class="btn btn-xs btn-primary" href="/owners/${owner.id}/edit">Edit</a>
            <a class="btn btn-xs btn-danger" href="/owners/${owner.id}/delete">Delete</a>
          </div>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>

</div>
<jsp:include page="../common/footer.jsp"/>
<jsp:include page="../common/scripts.jsp"/>
</body>
</html>