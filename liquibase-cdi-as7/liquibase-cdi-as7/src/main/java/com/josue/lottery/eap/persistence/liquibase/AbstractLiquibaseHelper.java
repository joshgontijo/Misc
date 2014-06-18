package com.josue.lottery.eap.persistence.liquibase;

import java.sql.SQLException;

import javax.sql.DataSource;

import liquibase.integration.cdi.CDILiquibaseConfig;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;

public abstract class AbstractLiquibaseHelper {

	public abstract DataSource createDataSource() throws SQLException;

	public CDILiquibaseConfig createConfig() {
		CDILiquibaseConfig config = new CDILiquibaseConfig();
		config.setChangeLog("liquibase/changelog.xml");
		return config;
	}

	public ResourceAccessor create() {
		return new ClassLoaderResourceAccessor(getClass().getClassLoader());
	}

}