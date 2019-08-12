package com.jslsolucoes.nginx.admin.database;

public enum DatabaseDriver {
	ORACLE("oracle"), H2("h2"), MARIADB("mariadb"), MYSQL("mysql"), POSTGRESQL("postgresql"), SQLSERVER("sqlserver"), OCEANBASE(
			"oceanbase");

	private String driverName;

	DatabaseDriver(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverName() {
		return driverName;
	}

	public static DatabaseDriver forName(String driverName) {
		for (DatabaseDriver databaseDriver : values()) {
			if (databaseDriver.getDriverName().equals(driverName)) {
				return databaseDriver;
			}
		}
		throw new RuntimeException("Could not select database driver");
	}
}
