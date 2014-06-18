package com.josue.lottery.eap.persistence.liquibase;

import java.sql.SQLException;

import javax.annotation.Resource;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;

import liquibase.integration.cdi.CDILiquibaseConfig;
import liquibase.integration.cdi.annotations.LiquibaseType;
import liquibase.resource.ResourceAccessor;

public class LiquibaseHelper extends AbstractLiquibaseHelper {

	@Resource(lookup = "jdbc/lotteryDS")
	private DataSource datasource;

	@Produces
	@LiquibaseType
	public CDILiquibaseConfig createConfig() {
		return super.createConfig();
	}

	@Produces
	@LiquibaseType
	public DataSource createDataSource() throws SQLException {
		return datasource;
	}

	@Produces
	@LiquibaseType
	public ResourceAccessor create() {
		return super.create();
	}
}
