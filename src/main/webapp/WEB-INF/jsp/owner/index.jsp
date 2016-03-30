<%--@elvariable id="owners" type="java.util.List"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>

<html lang="en">
<jsp:include page="../common/header.jsp"/>
<body>
<jsp:include page="../common/nav.jsp"/>

<div class="container">
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
            <button type="button" class="btn btn-xs btn-primary action-show-owner">Show</button>
            <button type="button" class="btn btn-xs btn-primary action-edit-owner">Edit</button>
            <button type="button" data-id="${owner.id}" class="btn btn-xs btn-primary action-delete-owner">Delete</button>
          </div>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
  <form class="invisible" action="/owners/delete" method="post">
    <input name="id">
  </form>
</div>
<jsp:include page="../common/footer.jsp"/>
<jsp:include page="../common/scripts.jsp"/>
<script type="text/javascript">
  $(function(){
    $("button.action-delete-owner").click(function() {
      var id = $(this).attr("data-id");
      var form = $("form");
      var idField = form.find("input[name='id']");
      idField.val(id);
      form.submit();
    });
  });
</script>
</body>
</html>