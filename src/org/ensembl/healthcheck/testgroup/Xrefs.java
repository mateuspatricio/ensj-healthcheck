package org.ensembl.healthcheck.testgroup;

import org.ensembl.healthcheck.GroupOfTests;

/**
 * These are the tests that register themselves as xrefs. The tests are:
 * 
 * <ul>
 *   <li> org.ensembl.healthcheck.testcase.xref.ForeignKeys </li> 
 * </ul>
 *
 * @author Autogenerated
 *
 */
public class Xrefs extends GroupOfTests {
	
	public Xrefs() {

		addTest(
			org.ensembl.healthcheck.testcase.xref.ForeignKeys.class
		);
	}
}