package com.ipartek.formacion.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.log4j.Logger;

import com.ipartek.formacion.daos.CocheDAO;
import com.ipartek.formacion.daos.MultaDAO;
import com.ipartek.formacion.pojos.Agente;
import com.ipartek.formacion.pojos.Coche;
import com.ipartek.formacion.pojos.Multa;

@WebServlet("/privado/multa")
public class MultaController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// LOG
	private final static Logger LOG = Logger.getLogger(MultaController.class);

	private ValidatorFactory factory;
	private Validator validator;

	// VISTAS
	private static final String VIEW_INDEX = "principal.jsp";
	private static final String VIEW_FORM = "multa.jsp";
	private static final String VIEW_BUSCAR = "buscar";
	
	//mandamos el parametro de operacion para que lo recoja el servelt y funcione perfecto
	private static final String VIEW_LISTADO = "listar?operacion=0";
	private String vista;

	// OPERACIONES
	public static final String OP_INSERTAR = "1";
	public static final String OP_ANULAR = "2";

	// parametros
	private String operacion;
	private String id_multa;
	private String id_agente;
	private String id_coche;
	private String importe1;
	private Integer importe2;
	private String concepto;
	private String matricula;

	private static MultaDAO multaDAO = null;
	private static CocheDAO cocheDAO = null;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		multaDAO = MultaDAO.getInstance();
		cocheDAO = CocheDAO.getInstance();
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();

	}

	private void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		vista = VIEW_INDEX;

		try {
			// recoger parametros
			getParametros(request);
			// realizar operacion
			switch (operacion) {
			case OP_INSERTAR:
				insertar(request);
				break;
			case OP_ANULAR:
				anular(request);
				break;
			}

		} catch (Exception e) {
			LOG.error(e);
			vista = VIEW_BUSCAR;

			Coche c = new Coche();
			c = cocheDAO.getMatricula(matricula);
			request.setAttribute("matricula", c.getMatricula());
			request.setAttribute("mensaje", "Reinicio");

		} finally {
			request.getRequestDispatcher(vista).forward(request, response);

		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	private void insertar(HttpServletRequest request) {

		// crear pojos mediante parametros del formulario
	
		Coche coche = new Coche();
		int coche_id = Integer.parseInt(id_coche);
		coche.setId((long) coche_id);
		coche.setMatricula(matricula);
		Multa multa = new Multa();
		multa.setImporte(importe2);
		multa.setConcepto(concepto);
		multa.setCoche(coche);

		Agente agente = new Agente();
		int agente_id = Integer.parseInt(id_agente);
		agente.setId((long) agente_id);

		// validar
		Set<ConstraintViolation<Multa>> violations = validator.validate(multa);

		if (violations.size() > 0) { // validacion NO correcta
			vista = VIEW_FORM;
			Coche c = new Coche();
			c = cocheDAO.getMatricula(matricula);
			request.setAttribute("coche", c);
			String mensaje = "<ul>";
			for (ConstraintViolation<Multa> violation : violations) {
				mensaje += String.format("<li> %s : %s </li>", violation.getPropertyPath(), violation.getMessage());
			}
			mensaje += "</ul>";
			request.setAttribute("mensaje", mensaje);
			
		} else { // validacion correcta
			try {		
				multaDAO.insert(multa, agente);
				request.setAttribute("mensaje", "Multa registrada correctamente");
				LOG.debug("AGENTE " + agente_id + " Importe: " + multa.getImporte() + " Concepto: "
						+ multa.getConcepto() + " Coche: " + multa.getCoche().getMatricula());
			} catch (SQLException e) {
				request.setAttribute("mensaje", "Multa no registrada , ERROR");
				LOG.debug("Multa no registrada ");
			}
		}
	}

	private void getParametros(HttpServletRequest request) {
		id_multa=request.getParameter("id");
		operacion = request.getParameter("operacion");
		id_agente = request.getParameter("id_agente");
		id_coche = request.getParameter("id_coche");
		importe1 = request.getParameter("importe");
		concepto = request.getParameter("concepto");
		matricula = request.getParameter("matricula");
		try {
			importe2 = Integer.parseInt(importe1);
		} catch (Exception e) {
			importe2 = null;
			
		}finally {
			request.setAttribute("importe", importe1);
			request.setAttribute("concepto", concepto);
		}
	}

	
	private void anular (HttpServletRequest request) {
		Long identificador = Long.parseLong(id_multa);
		Multa multa = new Multa();
		multa.setId(identificador);
		try {
			multaDAO.update(multa);
			request.setAttribute("mensaje", "multa anulada");
			LOG.debug("Anulando multa" + identificador);
			vista =VIEW_LISTADO;
		}catch(Exception e){
			request.setAttribute("mensaje", "multa no  anulada");
			LOG.debug("Multa no anulada" + identificador);
		}
	}
}
