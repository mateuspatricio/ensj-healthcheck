/*
 * Copyright (C) 2004 EBI, GRL
 * 
 * This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with this library; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.ensembl.healthcheck.testcase.generic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.ensembl.healthcheck.DatabaseRegistryEntry;
import org.ensembl.healthcheck.DatabaseType;
import org.ensembl.healthcheck.ReportManager;
import org.ensembl.healthcheck.Species;
import org.ensembl.healthcheck.Team;
import org.ensembl.healthcheck.testcase.SingleDatabaseTestCase;
import org.ensembl.healthcheck.util.DBUtils;
import org.ensembl.healthcheck.util.SqlTemplate;

/**
 * Check that the meta table exists and has data and the entries correspond to
 * the database name and a few other basic data errors. Other meta_value tests
 * are done in the MetaValue test case. Only one meta table at a time is done
 * here; checks for the consistency of the meta table across species are done in
 * MetaCrossSpecies.
 */
public class Meta extends SingleDatabaseTestCase {
	private boolean isSangerVega = false;

	/**
	 * Creates a new instance of CheckMetaDataTableTestCase
	 */
	public Meta() {

		addToGroup("post_genebuild");
		addToGroup("compara-ancestral");
		addToGroup("pre-compara-handover");
		addToGroup("post-compara-handover");

		setTeamResponsible(Team.GENEBUILD);
		setDescription("Check that the meta table exists and has data and the entries correspond to the database name");
	}

	/**
	 * Check that the meta table exists and has data and the entries correspond
	 * to the database name.
	 * 
	 * @param dbre
	 *            The database to check.
	 * @return True if the test passed.
	 */
	public boolean run(final DatabaseRegistryEntry dbre) {

		boolean result = true;

		Connection con = dbre.getConnection();

		Species species = dbre.getSpecies();

		result &= checkTableExists(con);

		result &= tableHasRows(con);

		result &= checkSchemaVersionDBName(dbre);

		if (species == Species.ANCESTRAL_SEQUENCES) {
			// The rest of the tests are not relevant for the ancestral
			// sequences DB
			return result;
		}

		if (dbre.getType() == DatabaseType.CORE) {
			result &= checkKeysPresent(con);
		}

		// -------------------------------------------

		result &= checkDuplicates(dbre);

		// -------------------------------------------

		result &= checkArrays(dbre);

		return result;

	} // run

	// ---------------------------------------------------------------------

	private boolean checkTableExists(Connection con) {

		boolean result = true;

		if (!DBUtils.checkTableExists(con, "meta")) {
			result = false;
			ReportManager.problem(this, con, "Meta table not present");
		} else {
			ReportManager.correct(this, con, "Meta table present");
		}

		return result;

	}

	// ---------------------------------------------------------------------

	private boolean tableHasRows(Connection con) {

		boolean result = true;

		int rows = DBUtils.countRowsInTable(con, "meta");
		if (rows == 0) {
			result = false;
			ReportManager.problem(this, con, "meta table is empty");
		} else {
			ReportManager.correct(this, con, "meta table has data");
		}

		return result;

	}

	// ---------------------------------------------------------------------

	private boolean checkKeysPresent(Connection con) {
		boolean result = true;

		// check that certain keys exist
		String[] metaKeys = { 
		  "assembly.default", 
		  "assembly.name", 
		  "assembly.date", 
		  "assembly.coverage_depth",
		  
		  "species.classification",
		  "species.common_name",
		  "species.display_name",
		  "species.production_name", 
		  "species.scientific_name",
		  "species.stable_id_prefix",
		  "species.taxonomy_id",
		  "species.url",		  
			
			"repeat.analysis",
			"marker.priority",
		};
		for (String metaKey: metaKeys) {
		  int rows = metaKeyCount(con, metaKey);
			if (rows == 0) {
				result = false;
				ReportManager.problem(this, con, "No entry in meta table for "+ metaKey);
			}
		}

		// check that there are some species.alias entries
		int MIN_ALIASES = 3;

		int rows = metaKeyCount(con, "species.alias");
		if (rows < MIN_ALIASES) {
			result = false;
			String msg = String.format("Only %d species.alias entries, should be at least %d", rows, MIN_ALIASES);
			ReportManager.problem(this, con, msg);
		}
		else {
			ReportManager.correct(this, con, rows+" species.alias entries present");
		}

		return result;
	}

	//---------------------------------------------------------------------
	private int metaKeyCount(Connection con, String metaKey) {
	  String sql = "select count(*) from meta where meta_key =?";
	  SqlTemplate t = getSqlTemplate(con);
	  return t.queryForDefaultObject(sql, Integer.class, metaKey);
	}

	// ---------------------------------------------------------------------
	/**
	 * Check that the schema_version in the meta table is present and matches
	 * the database name.
	 */
	private boolean checkSchemaVersionDBName(DatabaseRegistryEntry dbre) {

		boolean result = true;
		// get version from database name
		String dbNameVersion = dbre.getSchemaVersion();
		logger.finest("Schema version from database name: " + dbNameVersion);

		// get version from meta table
		Connection con = dbre.getConnection();

		if (dbNameVersion == null) {
			ReportManager.warning(this, con,
					"Can't deduce schema version from database name.");
			return false;
		}

		String schemaVersion = DBUtils.getRowColumnValue(con,
				"SELECT meta_value FROM meta WHERE meta_key='schema_version'");
		logger.finest("schema_version from meta table: " + schemaVersion);

		if (schemaVersion == null || schemaVersion.length() == 0) {

			ReportManager.problem(this, con,
					"No schema_version entry in meta table");
			return false;

		} else if (!schemaVersion.matches("[0-9]+")) {

			ReportManager.problem(this, con, "Meta schema_version "
					+ schemaVersion + " is not numeric");
			return false;

		} else if (!dbNameVersion.equals(schemaVersion) && !isSangerVega) {// do
																			// not
																			// report
																			// for
																			// sangervega

			ReportManager.problem(this, con, "Meta schema_version "
					+ schemaVersion
					+ " does not match version inferred from database name ("
					+ dbNameVersion + ")");
			return false;

		} else {

			ReportManager.correct(this, con, "schema_version " + schemaVersion
					+ " matches database name version " + dbNameVersion);

		}
		return result;

	}

	// ---------------------------------------------------------------------
	/**
	 * Check for duplicate entries in the meta table.
	 */
	private boolean checkDuplicates(DatabaseRegistryEntry dbre) {

		boolean result = true;

		Connection con = dbre.getConnection();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT meta_key, meta_value FROM meta GROUP BY meta_key, meta_value, species_id HAVING COUNT(*)>1");

			while (rs.next()) {

				ReportManager.problem(
						this,
						con,
						"Key/value pair " + rs.getString(1) + "/"
								+ rs.getString(2)
								+ " appears more than once in the meta table");
				result = false;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (result) {
			ReportManager.correct(this, con, "No duplicates in the meta table");
		}

		return result;

	}

	// ---------------------------------------------------------------------
	/**
	 * Check for values containing the text ARRAY(.
	 */
	private boolean checkArrays(DatabaseRegistryEntry dbre) {

		boolean result = true;

		Connection con = dbre.getConnection();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT meta_key, meta_value FROM meta WHERE meta_value LIKE 'ARRAY(%'");

			while (rs.next()) {

				ReportManager.problem(this, con, "Meta table entry for key "
						+ rs.getString(1) + " has value " + rs.getString(2)
						+ " which is probably incorrect");
				result = false;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (result) {
			ReportManager.correct(this, con, "No duplicates in the meta table");
		}

		return result;

	}

} // Meta
