package org.ensembl.healthcheck.testgroup;

import org.ensembl.healthcheck.GroupOfTests;

/**
 * These are the tests that register themselves as compara_genomic. The tests are:
 * 
 * <ul>
 *   <li> org.ensembl.healthcheck.testcase.compara.ForeignKeyMethodLinkId </li> 
 *   <li> org.ensembl.healthcheck.testcase.compara.CheckConservationScore </li> 
 *   <li> org.ensembl.healthcheck.testcase.compara.CheckGenomicAlignGenomeDBs </li> 
 *   <li> org.ensembl.healthcheck.testcase.compara.ForeignKeyTaxonId </li> 
 *   <li> org.ensembl.healthcheck.testcase.compara.SingleDBCollations </li> 
 *   <li> org.ensembl.healthcheck.testcase.compara.ForeignKeyMethodLinkSpeciesSetIdGenomicAlignBlock </li> 
 *   <li> org.ensembl.healthcheck.testcase.compara.ForeignKeyGenomicAlignBlockId </li> 
 *   <li> org.ensembl.healthcheck.testcase.compara.ForeignKeyGenomicAlignId </li> 
 *   <li> org.ensembl.healthcheck.testcase.compara.ForeignKeySyntenyRegionId </li> 
 *   <li> org.ensembl.healthcheck.testcase.compara.CheckSynteny </li> 
 *   <li> org.ensembl.healthcheck.testcase.compara.SpeciesNameConsistency </li> 
 *   <li> org.ensembl.healthcheck.testcase.compara.ForeignKeyDnafragId </li> 
 *   <li> org.ensembl.healthcheck.testcase.compara.ForeignKeyMethodLinkSpeciesSetId </li> 
 *   <li> org.ensembl.healthcheck.testcase.compara.ForeignKeyGenomeDbId </li> 
 * </ul>
 *
 * @author Autogenerated
 *
 */
public class ComparaGenomic extends GroupOfTests {
	
	public ComparaGenomic() {

		addTest(
			org.ensembl.healthcheck.testcase.compara.ForeignKeyMethodLinkId.class,
			org.ensembl.healthcheck.testcase.compara.CheckConservationScore.class,
			org.ensembl.healthcheck.testcase.compara.CheckGenomicAlignGenomeDBs.class,
			org.ensembl.healthcheck.testcase.compara.ForeignKeyTaxonId.class,
			org.ensembl.healthcheck.testcase.compara.SingleDBCollations.class,
			org.ensembl.healthcheck.testcase.compara.ForeignKeyMethodLinkSpeciesSetIdGenomicAlignBlock.class,
			org.ensembl.healthcheck.testcase.compara.ForeignKeyGenomicAlignBlockId.class,
			org.ensembl.healthcheck.testcase.compara.ForeignKeyGenomicAlignId.class,
			org.ensembl.healthcheck.testcase.compara.ForeignKeySyntenyRegionId.class,
			org.ensembl.healthcheck.testcase.compara.CheckSynteny.class,
			org.ensembl.healthcheck.testcase.compara.SpeciesNameConsistency.class,
			org.ensembl.healthcheck.testcase.compara.ForeignKeyDnafragId.class,
			org.ensembl.healthcheck.testcase.compara.ForeignKeyMethodLinkSpeciesSetId.class,
			org.ensembl.healthcheck.testcase.compara.ForeignKeyGenomeDbId.class
		);
	}
}