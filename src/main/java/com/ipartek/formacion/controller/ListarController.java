package com.ipartek.formacion.controller; //

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ipartek.formacion.daos.AgenteDAO;
import com.ipartek.formacion.pojos.Multa;

/**
 * Servlet implementation class ListarController
 */
@WebServlet("/listar")
public class ListarController extends HttpServlet {
	// variable necesaria 
	private static final long serialVersionUID = 1L;
	
	// variables 
	private static final int ID_AGENTE_PREDEFINIDO = 4; 									// variable  para  hardcodear agente
	private static final String LISTADO_MULTAS = "privado/listadoActivas.jsp"; 				// variable para rutas 
	private static final String LISTADO_MULTAS_ANULAR = "privado/listadoAnuladas.jsp";		// variable para rutas 
	private final static Logger LOG = Logger.getLogger(LoginController.class);

	// ArrayList y DAO
	private static AgenteDAO agenteDAO = null;
	private ArrayList<Multa> multas = null;

	// metodo init paa crear daos, objetos, colecciones
	@Override
	public void init(ServletConfig config) throws ServletException {  						//objetos, colecciones en metodo init
		super.init(config);
		agenteDAO = AgenteDAO.getInstance();  												// singleton    
		multas = new ArrayList<Multa>();	  												// arrayList de multas
	}

	private void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String operacion = request.getParameter("operacion"); 								// recojo parametro
		
        if (operacion.equals("activas")) { 													// si recibo activas voy a listado activas
			multas = agenteDAO.getMultas(ID_AGENTE_PREDEFINIDO); 							// creo multas (array list) y guardo como elementos todas las multas del agente 4 que hay en la base de datos
			request.setAttribute("multas", multas);  										// creo parametro con atributo objeto multas para enviar al formulario
			request.getRequestDispatcher(LISTADO_MULTAS).forward(request, response);  		// ruta
			LOG.debug("Mostrando listado");													// para mostrar mensajes tipo log
        }else if (operacion.equals("anuladas")) { 											// si recibo anuladas voy a listado anuladas
        	multas = agenteDAO.getMultasAnuladas(ID_AGENTE_PREDEFINIDO);					// arraylist con multas anuladas
    		request.setAttribute("multas", multas);											// parametro que envio a listado anuladas con todas las multas anuladas como atributo
    		request.getRequestDispatcher(LISTADO_MULTAS_ANULAR).forward(request, response);	// ruta
    		LOG.debug("Mostrando listado");													// para mostrar mensajes tipo log
        }else { 																			// si recibo otra cosa voy a 404.jsp
        	request.getRequestDispatcher("/404.jsp").forward(request, response);			// ruta
    		
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

}

