<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
  <img src="/resources/images/banner-graphic.png"/>

  <nav class="navbar navbar-default">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
              data-target="#bs-navbar-collapse" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
    </div>

    <div class="collapse navbar-collapse" id="bs-navbar-collapse">
      <ul class="nav navbar-nav">
        <li><a href="/"><i class="glyphicon glyphicon-home"></i> Home</a></li>


        <%-- Owners --%>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">
            <i class="glyphicon glyphicon-search"></i>
            <span> Owners</span>
            <span class="caret"></span>
          </a>
          <ul class="dropdown-menu">
            <li><a href="/owners">List all owners</a></li>
            <li><a href="/owners/new">Create new owner</a></li>
          </ul>
        </li>


        <li><a href="/vets"><i class="glyphicon glyphicon-th-list"></i> Veterinarians</a></li>


        <%-- Errors --%>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">
            <i class="glyphicon glyphicon-warning-sign"></i>
            <span> Errors</span>
            <span class="caret"></span>
          </a>
          <ul class="dropdown-menu">
            <li><a href="/errors">Default exception</a></li>
            <li><a href="/errors/oops">Oops exception</a></li>
          </ul>
        </li>
      </ul>
    </div>
  </nav>

</div>