package com.ipartek.formacion.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ipartek.formacion.pojos.Agente;
import com.ipartek.formacion.pojos.Coche;
import com.ipartek.formacion.pojos.Multa;

public class AgenteDAO {

	private static final String SQL_GET_BY_ID = "SELECT id, nombre, placa, id_departamento FROM agente WHERE id = ?;";
	private static final String SQL_ALL_MULTAS = "SELECT m.id AS 'id_multa', c.id AS 'id_coche',fecha, importe, concepto, matricula, modelo,km FROM agente AS a, multa AS m, coche AS c WHERE a.id=m.id_agente AND m.id_coche=c.id AND a.id=? AND m.fecha_baja IS NULL ORDER BY m.fecha DESC ";
	private static final String SQL_ALL_MULTAS_ANULADAS = "SELECT m.id AS 'id_multa', c.id AS 'id_coche',fecha, importe, concepto, matricula, modelo,km FROM agente AS a, multa AS m, coche AS c WHERE a.id=m.id_agente AND m.id_coche=c.id AND a.id=? AND m.fecha_baja IS NOT NULL ORDER BY m.fecha DESC ";
	private static AgenteDAO INSTANCE = null;
	private final static Logger LOG = Logger.getLogger(AgenteDAO.class);

	private AgenteDAO() {
		super();
	}

	public synchronized static AgenteDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new AgenteDAO();
		}
		return INSTANCE;
	}

	private Agente rowMapper(ResultSet rs) throws SQLException {
		Agente a = new Agente();
		a.setId(rs.getLong("id"));
		a.setNombre(rs.getString("nombre"));
		a.setPlaca(rs.getInt("placa"));
		return a;
	}

	public Agente getById(long id) {

		Agente u= null; 										// objeto para guardar datos 
		String sql = SQL_GET_BY_ID;								// consulta sql
		try (Connection conn = ConnectionManager.getConnection(); 
		PreparedStatement pst = conn.prepareStatement(sql);) {	// CREO OBJETO CONNECTION
			pst.setLong(1, id); 								// RECOJO PARAMETROS Del formulario
			try (ResultSet rs = pst.executeQuery()) { 			// EJECUTAR SQL
				while (rs.next()) {								// RECORRER BASE DE DATOS MEDIANtE METODO RS.NEXT.
					u = rowMapper(rs);							// guardar los datos recogidos en la base de datos
				}
			}
		} catch (Exception e) {
			LOG.fatal("getById:---> " + e);
		}
		LOG.debug("Agente_id encontrado");
		return u;												// devuelve objeto ususario con los datos de la base de datos

	}
	
	private Multa rowMapperMultas(ResultSet rs) throws SQLException {  // metodo para recoger parametros y reutilizar el codigo
		Multa m = new Multa();									// objeto para guardar los parametros recogidos		
		m.setId(rs.getLong("id_multa"));
		m.setFecha(rs.getDate("fecha"));
		m.setHora(rs.getTime("fecha"));
		m.setConcepto(rs.getString("concepto"));
		return m;
	}

	public ArrayList<Multa> getMultas(long id) {   				// metodo para obtener multas activas
		ArrayList<Multa> multas = new ArrayList<Multa>();		// creo arraylista para almacenar multas
		String sql = SQL_ALL_MULTAS;							// consulta sql para obtener multas de la bbdd
		Multa multa = null;										// objeto multa para info multa
		Coche coche = null;										// coche para info coche multado
		try (Connection conn = ConnectionManager.getConnection(); PreparedStatement pst = conn.prepareStatement(sql);) { // conecta con la bbdd
			pst.setLong(1, id); 								// RECOJO PARAMETROS Del formulario tantos como ? haya en la consulta
			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					
					multa = new Multa();
					coche = new Coche();
					
					multa.setId(rs.getLong("id_multa"));
					multa.setFecha(rs.getDate("fecha"));
					multa.setHora(rs.getTime("fecha"));
					multa.setImporte(rs.getInt("importe"));
					multa.setConcepto(rs.getString("concepto"));
					
					coche.setId(rs.getLong("id_coche"));
					coche.setMatricula(rs.getString("matricula"));
					coche.setModelo(rs.getString("modelo"));
					coche.setKm(rs.getInt("km"));

					multa.setCoche(coche);
					multas.add(multa);

				}
			}

		} catch (Exception e) {
			LOG.fatal("getMultas:---> " + e);
		}
		LOG.debug("Listado multas OK");
		return multas;
	}
	
	public ArrayList<Multa> getMultasAnuladas(long id) { // metodo para obtener multas anuladas
		ArrayList<Multa> multas = new ArrayList<Multa>();
		String sql = SQL_ALL_MULTAS_ANULADAS;
		Multa multa = null;
		Coche coche = null;
		try (Connection conn = ConnectionManager.getConnection(); PreparedStatement pst = conn.prepareStatement(sql);) {
			pst.setLong(1, id);
			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					multa = new Multa();
					coche = new Coche();
					multa.setId(rs.getLong("id_multa"));
					coche.setId(rs.getLong("id_coche"));
					multa.setFecha(rs.getDate("fecha"));
					multa.setHora(rs.getTime("fecha"));
					multa.setImporte(rs.getInt("importe"));
					multa.setConcepto(rs.getString("concepto"));
					coche.setMatricula(rs.getString("matricula"));
					coche.setModelo(rs.getString("modelo"));
					coche.setKm(rs.getInt("km"));

					multa.setCoche(coche);
					multas.add(multa);

				}
			}

		} catch (Exception e) {
			LOG.fatal("getMultas:---> " + e);
		}
		LOG.debug("Listado multas OK");
		return multas;
	}
}// agente dao fin
