<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="pets" scope="request" type="java.util.List"/>
<!DOCTYPE html>

<html lang="en">
<jsp:include page="../common/header.jsp"/>
<body>
<jsp:include page="../common/nav.jsp"/>
<jsp:include page="../common/footer.jsp"/>
<jsp:include page="../common/scripts.jsp"/>


<div class="container">
  <a class="btn btn-xs btn-primary pull-right" href="/pets/new">
    <i class="glyphicon glyphicon-plus"></i>
  </a>

  <table class="table table-owners">
    <thead>
    <tr>
      <th>Name</th>
      <th>Type</th>
      <th>Birth Date</th>
      <th>Owner</th>
      <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="pet" items="${pets}">
      <tr>
        <td>${pet.name}</td>
        <td>${pet.type.name}</td>
        <td>${pet.birthDate}</td>
        <td>${pet.owner.lastName}</td>
        <td>
          <div class="btn-group btn-group-xs">
            <a class="btn btn-xs btn-success" href="/pets/${pet.id}">View</a>
            <a class="btn btn-xs btn-primary" href="/pets/${pet.id}/edit">Edit</a>
            <a class="btn btn-xs btn-danger" href="/pets/${pet.id}/delete">Delete</a>
          </div>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>

</div>
</body>
</html>