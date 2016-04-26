package com.estsoft.db;

import java.sql.*;

public interface DBConnection {
	public Connection getConnection() throws SQLException;
}
