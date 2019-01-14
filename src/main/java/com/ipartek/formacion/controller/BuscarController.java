package com.ipartek.formacion.controller;

import java.io.IOException; //

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ipartek.formacion.daos.CocheDAO;
import com.ipartek.formacion.pojos.Coche;

@WebServlet("/privado/buscar")
public class BuscarController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// LOG
	private final static Logger LOG = Logger.getLogger(BuscarController.class);

	// VISTAS
	private static final String BUSCAR_MATRICULA_JSP = "buscarMatricula.jsp";
	private static final String RELLENAR_MULTA_JSP = "multa.jsp";
	
	// objeto coche 
	private CocheDAO cocheDAO = null;
	
	// metodo init para daos y colecciones
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		cocheDAO = CocheDAO.getInstance();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)   // LLEGA POR DOGET
			throws ServletException, IOException {
		request.setAttribute("mensaje", "Busca una matricula ");   

		request.getRequestDispatcher(BUSCAR_MATRICULA_JSP).forward(request, response); // NADA MAS ENTRAR AL SERVLET ME DIRIGE A BUSCAR MATRICULA A LA QUE PONER UNA MULTA
		LOG.debug("Entrando buscador matricula");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String matriculaBuscar = request.getParameter("buscar");  							// recojo parametro buscar
		Coche c = new Coche();																// creo objeto para obtener matricula  mediatne dao
		c = cocheDAO.getMatricula(matriculaBuscar); 										// dao para obtener matricula de la base de datos

		if (c != null) {  																	// SI se introduce matricula
			request.setAttribute("coche", c);												// Parametro coche para mandar atributo coche al jsp y podr mostrar info. Ejemplo ${coche.matricula}
			request.setAttribute("mensaje", "Todos los campos son obligatorios"); 			// mensaje
			request.getRequestDispatcher(RELLENAR_MULTA_JSP).forward(request, response); 	// RUTA
		} else {																			// SI NO  se introduce matricula
			request.setAttribute("matricula", matriculaBuscar);  							// parametro para mostrar matricula en buscarMatricula.jsp
			request.setAttribute("mensaje", "La matricula no existe"); 
			
			request.getRequestDispatcher(BUSCAR_MATRICULA_JSP).forward(request, response);  // ruta
		}

	}

}