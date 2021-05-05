<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>


<!--  MODAL ATTO LETTURA --> 
        <div class="modal fade" id="modal-assegna-fascicolo" tabindex="-1" role="dialog"  aria-hidden="true">
            <div class="modal-dialog" style="width:800px">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title"><spring:message text="??atto.label.attoAssegnaFascicolo??" code="atto.label.attoAssegnaFascicolo" /></h4>
                    </div>
                    <div class="modal-body">
                     <input type="hidden" id="codiceAttoId" value="">   
                        <table id="table-assegna-fascicolo"
									class="table table-striped table-responsive" data-height="400" data-width="800">
									<thead>
										<tr>
											<th data-column-id="id" data-visible="false">ID</th>
											<th data-column-id="none"><spring:message text="??atto.label.attoNome??" code="atto.label.attoNome" /></th>
											<th data-column-id="commands" data-formatter="commands" data-sortable="false">Commands</th>
										</tr>
									</thead>
									<tbody>
									<c:if test="${ listaFascicolo != null }">
										<c:forEach items="${listaFascicolo}" var="oggetto">	
										<tr>
										    <td><c:out value="${oggetto.id}"></c:out></td>
										    <td><c:out value="${oggetto.nome}"></c:out></td>
											<td> </td>
										</tr>
										</c:forEach> 
								</c:if>
									</tbody>
						</table>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">
                        <spring:message text="??atto.button.attoChiudi??" code="atto.button.attoChiudi" />
                        </button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->

<script>

   /*  var $table = $('#table-assegna-fascicolo');
    $(function () {
        $('#modal-assegna-fascicolo').on('shown.bs.modal', function () {
            $table.bootstrapTable('resetView');
        });
    });
    */
</script>

<!-- FINE MODAL ATTO LETTURA --> 	

						
									