<%@ include file="../includes/cabecera.jsp" %> 	

<main >

<section class="container-fluid  mt-5">
<c:if test = "${mensaje!=null}">
<div class="alert alert-info alert-dismissible fade show" role="alert">
  <strong> ${mensaje }</strong>
  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
    <span aria-hidden="true">&times;</span>
  </button>
</div>
</c:if>
<a class="btn btn-lg btn-info btn-block mb-3" href="listar?operacion=activas" role="button">LISTAR ACTIVAS</a>
<a class="btn btn-lg btn-info btn-block mb-3" href="listar?operacion=anuladas" role="button">LISTAR MULTAS ANULADAS</a>
<a class="btn btn-lg btn-info btn-block" href="privado/buscar" role="button">NUEVA MULTA</a>
</section>
</main>

<%@ include file="../includes/footer.jsp"  %>
