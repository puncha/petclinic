<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="pets" scope="request" type="java.util.List<tk.puncha.models.Pet>"/>
<!DOCTYPE html>

<html lang="en">
<jsp:include page="../common/header.jsp"/>
<body>
<jsp:include page="../common/nav.jsp"/>

<div class="container-fluid">
  <a class="btn btn-xs btn-primary" href="/pets/new"><i class="fa fa-plus fa-2x"></i> Add new Pet</a>
  <a class="btn btn-xs btn-info" href="/pets.xml"><i class="fa fa-file-code-o"></i> View as XML</a>
  <a class="btn btn-xs btn-info" href="/pets.json"><i class="fa fa-file-o"></i> View as JSON</a>

  <div class="card-columns m-t-1">
    <c:forEach var="pet" items="${pets}">
      <div class="card">
        <div class="card-block">
          <h4 class="card-title">${pet.name}</h4>
          <p class="card-text"><i class="fa fa-user"></i> Owned by:
            <a href="/owners/${pet.owner.id}">${pet.owner.firstName} ${pet.owner.lastName}</a>
          </p>
          <p class="card-text"><i class="fa fa-calendar"></i> Birthday: ${pet.birthDate}</p>
          <p class="card-text"><i class="fa fa-paw"></i> Type: ${pet.type.name}</p>
          <c:forEach var="visit" items="${pet.visits}">
            <p class="card-text">
              <i class="fa fa-calendar-o"></i> "${visit.visitDate} visit: ${visit.description}
            </p>
          </c:forEach>
        </div>
        <div class="card-block">
          <a class="btn btn-xs btn-success" href="/pets/${pet.id}">View</a>
          <a class="btn btn-xs btn-primary" href="/pets/${pet.id}/edit">Edit</a>
          <a class="btn btn-xs btn-danger" href="/pets/${pet.id}/delete">Delete</a>
        </div>
      </div>
    </c:forEach>
  </div>

</div>
<jsp:include page="../common/footer.jsp"/>
<jsp:include page="../common/scripts.jsp"/>
</body>
</html>
