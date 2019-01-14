<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/cabecera.jsp"  %>
 <main role="main" class="container-fluid mt-5">
 <c:if test = "${mensaje!=null}">
<div class="alert alert-info alert-dismissible fade show" role="alert">
  <strong> ${mensaje }</strong>
  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
    <span aria-hidden="true">&times;</span>
  </button>
</div>
</c:if>	

<h1>Multas Anuladas</h1>

<div class="accordion" id="accordionExample">		
	<table class="table">
		  <thead class="thead-light">
		    <tr>
		      <th scope="col">FECHA </th>
		      <th scope="col">MATRICULA</th>
		       <th></th>
		    </tr>
		  </thead>
		  <tbody>    
		 	<c:forEach items="${multas}" var="m">
			    <tr>
				 	<td scope="col" data-target="#collapse${m.id}">			     		
			     		<button class="btn btn-link collapsed" type="button" data-toggle="collapse" data-target="#collapse${m.id}" aria-expanded="false" aria-controls="collapseTwo">
				         	<fmt:formatDate pattern = "dd/MM/yy"  value = "${m.fecha}" />
				          	<fmt:formatDate pattern = "HH:mm"  value = "${m.hora}" />
				     	</button>
			     	</td>
			     	<td scope="col">
			     		<button class="btn btn-link collapsed" type="button" data-toggle="collapse" data-target="#collapse${m.id}" aria-expanded="false" aria-controls="collapseTwo">
				          	${m.coche.matricula}
				     	</button>
				   </td>
		     	</tr>  
		     	<tr id="collapse${m.id}" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionExample">
			     	<td colspan="3">		     	
				     	<div class="card mb-4 shadow-sm ">
					      <div class="card-header">
					        <h4 class="my-0 font-weight-normal text-center">${m.coche.matricula.toUpperCase()} </h4>
					      </div>
					      <div class="card-body">
					        <ul class="list-unstyled mt-3 mb-4">					         	
					         	<li>Fecha: ${m.fecha}</li>
					         	<li>Hora: ${m.hora}</li>
					    		<li>Importe: ${m.importe} €</li>
							    <li>Concepto: ${m.concepto}</li>
						     	<li>Matricula: ${m.coche.matricula}</li>
						     	<li>Modelo: ${m.coche.modelo}</li>
						     	<li>Kilometraje: ${m.coche.km}</li>
					        </ul>
					      </div>
					    </div>				    
			     	</td>			     	
		     	</tr>  
		   </c:forEach>
		 </tbody>
	</table>		
</div>				
</main>				
<%@ include file="../includes/footer.jsp"  %>
