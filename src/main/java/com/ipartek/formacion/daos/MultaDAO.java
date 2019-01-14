package com.ipartek.formacion.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ipartek.formacion.pojos.Agente;
import com.ipartek.formacion.pojos.Multa;

public class MultaDAO {
	private static MultaDAO INSTANCE = null;
	private final static Logger LOG = Logger.getLogger(MultaDAO.class);

	private static final String SQL_INSERT = "INSERT INTO multa (importe, concepto, id_coche, id_agente) VALUES (?, ?, ?, ?);";
	private static final String SQL_UPDATE = "UPDATE multa SET fecha_baja = CURRENT_TIMESTAMP WHERE (`id` = ?);";
	// INSERT INTO multa (importe, concepto, id_coche, id_agente) VALUES ('200',
	// 'velocidad', '4', '4');

	// UPDATE multa SET fecha_baja = '2019-01-12 09:30:15' WHERE (`id` = '5');  // 
	
	private MultaDAO() {
		super();
	}

	public synchronized static MultaDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MultaDAO();
		}
		return INSTANCE;
	}

	public boolean insert(Multa multa, Agente agente) throws SQLException {

		boolean resul = false;
		String sql = SQL_INSERT;
		try (Connection conn = ConnectionManager.getConnection(); PreparedStatement pst = conn.prepareStatement(sql);) {

			pst.setInt(1, multa.getImporte());
			pst.setString(2, multa.getConcepto());
			pst.setLong(3, multa.getCoche().getId());
			pst.setLong(4, agente.getId());
			int affectedRows = pst.executeUpdate();
			if (affectedRows == 1) {
				resul = true;
				LOG.debug("coche insertado");
			}

		}
		return resul;
	}

	public boolean update(Multa m) throws SQLException {
		
		boolean resul = false;
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement pst = conn.prepareStatement(SQL_UPDATE);) {

		
			pst.setLong(1, m.getId());
	
			int affectedRows = pst.executeUpdate();
			if (affectedRows == 1) {
				resul = true;
			}
		}
		return resul;

	}

	
}
