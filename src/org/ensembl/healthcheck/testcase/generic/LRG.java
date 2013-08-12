/*
 * Copyright (C) 2004 EBI, GRL
 * 
 * This library is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation; either version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with
 * this library; if not, write to the Free Software Foundation, Inc., 59 Temple Place,
 * Suite 330, Boston, MA 02111-1307 USA
 */

package org.ensembl.healthcheck.testcase.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.ensembl.healthcheck.DatabaseRegistryEntry;
import org.ensembl.healthcheck.DatabaseType;
import org.ensembl.healthcheck.ReportManager;
import org.ensembl.healthcheck.Team;
import org.ensembl.healthcheck.testcase.AbstractTemplatedTestCase;
import org.ensembl.healthcheck.util.DefaultMapRowMapper;
import org.ensembl.healthcheck.util.MapRowMapper;

/**
 * Check for mistakes relating to LRGs
 */
public class LRG extends AbstractTemplatedTestCase {
	
	/**
	 * Used just for convenience in this test alone
	 */
	private static enum Feature {
		GENE,
		TRANSCRIPT;
		
		public String getSqlName() {
			return name().toLowerCase();
		}
	}
	
	public static final String CS_NAME = "lrg";

	/**
	 * Creates a new instance of LRG healthcheck
	 */
	public LRG() {

		addToGroup("post-compara-handover");
		
		setDescription("Healthcheck for LRGs");
		setTeamResponsible(Team.CORE);

	}

	/**
	 * This only applies to core databases.
	 */
	public void types() {

		removeAppliesToType(DatabaseType.OTHERFEATURES);
		removeAppliesToType(DatabaseType.VEGA);
		removeAppliesToType(DatabaseType.SANGER_VEGA);
		removeAppliesToType(DatabaseType.RNASEQ);
                removeAppliesToType(DatabaseType.CDNA);

	}
	
	/**
	 * Runs the LRG tests
	 */
	@Override
	protected boolean runTest(DatabaseRegistryEntry dbre) {
		if(assertLrgs(dbre)) {
			return
					assertLrgFeatureAnnotations(dbre, Feature.GENE) &&
					assertLrgFeatureAnnotations(dbre, Feature.TRANSCRIPT);
		}
		else {
			logger.finest("No LRG seq_regions found, skipping test");
			return true;
		}
	}
	
	/**
	 * Asserts that we have LRG sequence regions in the database
	 * 
	 * @param dbre Registry entry
	 * @return Boolean if we found any LRG coordinate systems and sequence regions
	 */
	protected boolean assertLrgs(DatabaseRegistryEntry dbre) {
		String sql = "SELECT count(sr.seq_region_id) FROM coord_system cs JOIN seq_region sr using (coord_system_id) WHERE cs.name = ?";
		int count = getTemplate(dbre).queryForDefaultObject(sql, Integer.class, CS_NAME);
		return count != 0;
	}
	
	/**
	 * Check that the given features are mapped to a coordinate system called
	 * lrg and that the biotypes of those linked to an lrg coordinate system
	 * follow the form <code>LRG%</code>
	 * 
	 * @param dbre Registry entry
	 * @param feature Feature to assert
	 * @return Success of the test. If false the error has already been reported
	 */
	protected boolean assertLrgFeatureAnnotations(DatabaseRegistryEntry dbre, Feature feature) {
		MapRowMapper<String,Integer> mapper = new DefaultMapRowMapper<String,Integer>(String.class, Integer.class);
		String featureName = feature.getSqlName();
		
		//Check that all LRG features are linked to the lrg coordinate system
		Map<String, Integer> lrgGeneCoordinateSystems = getTemplate(dbre).queryForMap(
				"SELECT cs.name, count(*) FROM coord_system cs JOIN seq_region sr using (coord_system_id) join "+featureName+" f using (seq_region_id) WHERE f.biotype LIKE ? GROUP BY cs.name", 
				mapper, 
				"LRG%");
		
		if(!lrgGeneCoordinateSystems.containsKey(CS_NAME)) {
			ReportManager.problem(this, dbre.getConnection(), "lrg coordinate system exists but no "+featureName+"(s) are attached");
			return false;
		}
		lrgGeneCoordinateSystems.remove(CS_NAME);
		if(!lrgGeneCoordinateSystems.isEmpty()) {
			String badCoordinateSystems = StringUtils.join(lrgGeneCoordinateSystems.keySet(), ',');
			ReportManager.problem(this, dbre.getConnection(), "LRG biotyped "+featureName+"(s) attached to the wrong coordinate systems. ["+badCoordinateSystems+"]");
			return false;
		}
		
		//Check the inverse that all lrg coord systems contain the right biotype of feature
		Map<String, Integer> lrgCoordinateSystemGenes = getTemplate(dbre).queryForMap(
				"SELECT f.biotype, count(*) FROM coord_system cs JOIN seq_region sr using (coord_system_id) join "+featureName+" f using (seq_region_id) WHERE cs.name = ? GROUP BY f.biotype", 
				mapper, 
				CS_NAME);
		List<String> misMaps = new ArrayList<String>();
		for(String biotype: lrgCoordinateSystemGenes.keySet()) {
			if(biotype.indexOf("LRG") == -1) {
				misMaps.add(biotype);
			}
		}
		if(!misMaps.isEmpty()) {
			String badBiotypes = StringUtils.join(misMaps, ',');
			ReportManager.problem(this, dbre.getConnection(), CS_NAME+" coordinate system has the following wrong biotyped "+featureName+"(s) attached ["+badBiotypes+"]");
			return false;
		}
		
		return true;
	}

} // LRG
