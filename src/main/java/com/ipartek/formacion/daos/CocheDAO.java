package com.ipartek.formacion.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.ipartek.formacion.pojos.Coche;

public class CocheDAO {

	private static CocheDAO INSTANCE = null;
	private final static Logger LOG = Logger.getLogger(AgenteDAO.class);

	private static final String SQL_GET_MATRICULA = "SELECT id, matricula, modelo, km FROM coche WHERE matricula =?;";
	// SELECT id, matricula, modelo, km FROM coche WHERE matricula ='3548MKZ';

	private CocheDAO() {
		super();
	}

	public synchronized static CocheDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CocheDAO();
		}
		return INSTANCE;
	}

	public Coche getMatricula(String matricula) {
		String sql = SQL_GET_MATRICULA;
		Coche coche = null;
		try (Connection conn = ConnectionManager.getConnection(); PreparedStatement pst = conn.prepareStatement(sql);) {
			pst.setString(1, matricula);
			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					coche = new Coche();
					coche.setId(rs.getLong("id"));
					coche.setMatricula(rs.getString("matricula"));
					coche.setModelo(rs.getString("modelo"));
					coche.setKm(rs.getInt("km"));
				}
			}

		} catch (Exception e) {
			LOG.fatal("getMultas:---> " + e);
		}
		LOG.debug("Listado multas OK");
		return coche;
	}

}
