<%--@elvariable id="owners" type="java.util.List"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="string" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>

<html lang="en">
<jsp:include page="../common/header.jsp"/>
<body>
<jsp:include page="../common/nav.jsp"/>

<div class="container-fluid">
  <a class="btn btn-xs btn-primary" href="/owners/new"><i class="fa fa-plus fa-2x"></i> Add new owner</a>
  <a class="btn btn-xs btn-info" href="/owners.pdf"><i class="fa fa-file-pdf-o"></i> View as PDF</a>
  <a class="btn btn-xs btn-info" href="/owners.xml"><i class="fa fa-file-code-o"></i> View as XML</a>
  <a class="btn btn-xs btn-info" href="/owners.json"><i class="fa fa-file-o"></i> View as JSON</a>
  <a class="btn btn-xs btn-info" href="/owners.atom"><i class="fa fa-rss"></i> View as Atom Feeds</a>
  <a class="btn btn-xs btn-info" href="/owners.xls"><i class="fa fa-file-excel-o"></i> Download as Excel</a>

  <form class="form-inline" action="/owners" method="get">
    <div class="form-group m-y-1">
      <label class="label m-r-1" for="search-first-name">First Name: </label>
      <input id="search-first-name" name="search-first-name"
             type="text" class="form-control"
             placeholder="first name to search"
             value="${param["search-first-name"]}">
    </div>
    <button type="submit" class="btn btn-primary"><i class="fa fa-search"></i> Search</button>

  </form>

  <table class="table table-owners">
    <thead class="thead-default">
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
            <a class="btn btn-success" href="/owners/${owner.id}">View</a>
            <a class="btn btn-primary" href="/owners/${owner.id}/edit">Edit</a>
            <a class="btn btn-danger" href="/owners/${owner.id}/delete">Delete</a>
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