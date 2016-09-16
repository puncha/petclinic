<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-dark bg-inverse navbar-full m-b-1">
  <button class="navbar-toggler hidden-md-up pull-xs-right" data-toggle="collapse" data-target="#collapsingNavbar">
    <i class="fa fa-bars"></i>
  </button>

  <div id="collapsingNavbar" class="collapse navbar-toggleable-sm">
    <div class="navbar-brand hidden-sm-down m-r-1">
      <a href="/"><img src="/resources/images/spring-pivotal-logo.png"/></a>
    </div>
    <ul class="nav navbar-nav">
      <li class="nav-item h3 m-r-1"><a class="nav-link" href="/owners"><i class="fa fa-user"></i> Owners</a></li>
      <li class="nav-item h3 m-r-1"><a class="nav-link" href="/pets"><i class="fa fa-paw"></i> Pets</a></li>

      <li class="nav-item h3 dropdown">
        <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown">
          <i class="fa fa-exclamation-triangle"></i> Exceptions
        </a>
        <div class="dropdown-menu">
          <a class="dropdown-item h4" href="/errors">Default exception</a>
          <a class="dropdown-item h4" href="/errors/oops">Oops exception</a>
        </div>
      </li>

      <li class="nav-item h3 dropdown pull-xs-right">
        <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown">
          <i class="fa fa-html5 fa-spin"></i> Versions
        </a>
        <div class="dropdown-menu">
          <a class="dropdown-item h4" href="/">Jsp</a>
          <a class="dropdown-item h4" href="/ng1/index.html">AngularJs1</a>
          <a class="dropdown-item h4" href="/ng2/index.html">Angular2</a>
        </div>
      </li>

    </ul>
  </div>
</nav>
