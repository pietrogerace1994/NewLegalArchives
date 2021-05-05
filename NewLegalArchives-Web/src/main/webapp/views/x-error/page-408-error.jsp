<%@page import="java.util.Properties"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.util.ResourceBundle"%>
<%@page import="eng.la.util.DateUtil"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.StringWriter"%>
<%@page import="eng.la.util.SpringUtil"%>
<%@page import="eng.la.util.CurrentSessionUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>

<!DOCTYPE html lang="${language}">
<!--[if IE 9 ]><html class="ie9"><![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">


<jsp:include page="/parts/script-init.jsp">
</jsp:include>

</head>
<body data-ma-header="teal">
	<jsp:include page="/parts/headerNotAut.jsp">
	</jsp:include>
	<!-- SECION MAIN -->
	<section id="main">
		<!-- SECTION CONTENT -->
		<section id="content">
			<div class="container">
				<div class="row">
					<div id="col-1" class="col-lg-12 col-md-12 col-sm-12 col-sx-12">
						<div class="card">
							<div class="card-header ch-dark palette-Green-SNAM bg">
								<i class="fa fa-exclamation-circle" aria-hidden="true"
									style="font-size: 50px; color: red;"></i>

								<h1 style="color: white;">
								408 REQUEST TIMEOUT
								</h1>

							</div>

						</div>
				</div>		
			</div>
			<!--/ fine col-1 -->
		</section>

	</section>

	<footer>
		<jsp:include page="/parts/footer.jsp">
		</jsp:include>
	</footer>


</body>
</html>
