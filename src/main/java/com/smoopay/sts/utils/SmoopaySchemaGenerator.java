package com.smoopay.sts.utils;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class SmoopaySchemaGenerator {

	private final Configuration cfg;

	public SmoopaySchemaGenerator(String... packageNames) throws ClassNotFoundException {
		cfg = new Configuration();
		cfg.setProperty("hibernate.hbm2ddl.auto", "create");
		for (String p : packageNames) {
			for (Class<Object> clazz : getClasses(p)) {
				cfg.addAnnotatedClass(clazz);
			}
		}
	}

	/**
	 * Method that actually creates the file.
	 * 
	 * @param dbDialect
	 *            to use
	 */
	private void generate(Dialect dialect) {
		cfg.setProperty("hibernate.dialect", dialect.getDialectClass());
		SchemaExport export = new SchemaExport(cfg);
		export.setDelimiter(";");
		export.setOutputFile("./ddl/ddl_" + dialect.name().toLowerCase() + ".sql");
		export.execute(true, false, false, false);
	}

	public static void main(String[] args) {
		SmoopaySchemaGenerator gen = null;
		try {
			gen = new SmoopaySchemaGenerator("com.smoopay.sts.entity.bank", "com.smoopay.sts.entity.client", "com.smoopay.sts.entity.common",
					"com.smoopay.sts.entity.merchant", "com.smoopay.sts.entity.payments", "com.smoopay.sts.entity.common.account",
					"com.smoopay.sts.entity.common.account.data", "com.smoopay.sts.entity.common.account.status", "com.smoopay.sts.entity.common.address",
					"com.smoopay.sts.entity.common.client.role", "com.smoopay.sts.entity.common.client.status");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		if (gen != null) {
			gen.generate(Dialect.POSTGRES);
			gen.generate(Dialect.ORACLE);
		}
	}

	/**
	 * Utility method used to fetch Class list based on a package name.
	 * 
	 * @param packageName
	 *            (should be the package containing your annotated beans.
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("rawtypes")
	private List<Class> getClasses(String packageName) throws ClassNotFoundException {
		List<Class> classes = new ArrayList<Class>();
		File directory = null;
		try {
			ClassLoader cld = Thread.currentThread().getContextClassLoader();
			if (cld == null) {
				throw new ClassNotFoundException("Can't get class loader.");
			}
			String path = packageName.replace('.', '/');
			URL resource = cld.getResource(path);
			if (resource == null) {
				throw new ClassNotFoundException("No resource for " + path);
			}
			directory = new File(resource.getFile());
		} catch (NullPointerException x) {
			throw new ClassNotFoundException(packageName + " (" + directory + ") does not appear to be a valid package");
		}
		if (directory.exists()) {
			String[] files = directory.list();
			for (int i = 0; i < files.length; i++) {
				if (files[i].endsWith(".class")) {
					// removes the .class extension
					classes.add(Class.forName(packageName + '.' + files[i].substring(0, files[i].length() - 6)));
				}
			}
		} else {
			throw new ClassNotFoundException(packageName + " is not a valid package");
		}

		return classes;
	}

	/**
	 * Holds the classnames of hibernate dialects for easy reference.
	 */
	private static enum Dialect {
		POSTGRES("org.hibernate.dialect.PostgreSQL82Dialect"), ORACLE("org.hibernate.dialect.Oracle10gDialect"), MYSQL("org.hibernate.dialect.MySQLDialect"), HSQL(
				"org.hibernate.dialect.HSQLDialect");

		private String dialectClass;

		private Dialect(String dialectClass) {
			this.dialectClass = dialectClass;
		}

		public String getDialectClass() {
			return dialectClass;
		}
	}
}