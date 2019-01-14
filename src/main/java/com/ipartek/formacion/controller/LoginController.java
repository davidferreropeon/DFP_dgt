package com.ipartek.formacion.controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ipartek.formacion.daos.AgenteDAO;
import com.ipartek.formacion.pojos.Agente;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int ID_AGENTE_PREDEFINIDO = 4;
	private final static Logger LOG = Logger.getLogger(LoginController.class);

	private static final String PRIVADO_PRINCIPAL = "/privado/principal.jsp";

	private AgenteDAO agenteDAO = null;
	Agente a = null;

	@Override
	public void init(ServletConfig config) throws ServletException {

		super.init(config);
		agenteDAO = AgenteDAO.getInstance();

		a = new Agente();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	private void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();									// conexion
		session.setMaxInactiveInterval(60 * 60 * 24 * 365 * 10);  					// intervalo para reiniciar sesion

		a = agenteDAO.getById(ID_AGENTE_PREDEFINIDO);  								// metodo para buscar por id el agente que se logea
		session.setAttribute("agente_logeado", a);  								// parametro paa mostrar el agente logeado en la cabecera

		// response.sendRedirect( request.getContextPath() + PRIVADO_PRINCIPAL);
		request.getRequestDispatcher(PRIVADO_PRINCIPAL).forward(request, response); // ruta
		LOG.debug("Logeado en pagina principal");

	}

}
