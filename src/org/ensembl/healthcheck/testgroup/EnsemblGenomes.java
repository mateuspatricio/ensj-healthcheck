/*
 * Copyright [1999-2015] Wellcome Trust Sanger Institute and the EMBL-European Bioinformatics Institute
 * Copyright [2016-2018] EMBL-European Bioinformatics Institute
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ensembl.healthcheck.testgroup;

import org.ensembl.healthcheck.GroupOfTests;

/**
 * These are the tests that register themselves as ensembl_genomes. The tests are:
 * 
 * <ul>
 *   <li> org.ensembl.healthcheck.testcase.eg_core.DuplicateXref </li> 
 *   <li> org.ensembl.healthcheck.testcase.eg_core.ExonBoundary </li> 
 *   <li> org.ensembl.healthcheck.testcase.eg_core.DisplayXrefId </li> 
 *   <li> org.ensembl.healthcheck.testcase.eg_core.MetaForCompara </li> 
 *   <li> org.ensembl.healthcheck.testcase.eg_core.GenesDisplayable </li> 
 *   <li> org.ensembl.healthcheck.testcase.eg_core.DeprecatedEgMeta </li> 
 *   <li> org.ensembl.healthcheck.testcase.eg_core.SeqRegionName </li> 
 *   <li> org.ensembl.healthcheck.testcase.eg_core.GeneGC </li> 
 *   <li> org.ensembl.healthcheck.testcase.eg_core.AliasAndNaming </li> 
 *   <li> org.ensembl.healthcheck.testcase.eg_core.GeneDescription </li> 
 *   <li> org.ensembl.healthcheck.testcase.eg_core.SuggestedEgMeta </li> 
 *   <li> org.ensembl.healthcheck.testcase.eg_core.GeneDescriptionSource </li> 
 *   <li> org.ensembl.healthcheck.testcase.eg_core.GeneDescriptionNewline </li> 
 *   <li> org.ensembl.healthcheck.testcase.eg_core.EgMeta </li> 
 *   <li> org.ensembl.healthcheck.testcase.eg_core.IgiXref </li> 
 *   <li> org.ensembl.healthcheck.testcase.eg_core.ProteinCodingGene </li> 
 *   <li> org.ensembl.healthcheck.testcase.eg_core.DuplicateProteinId </li> 
 *   <li> org.ensembl.healthcheck.testcase.eg_core.TranslationAttribType </li> 
 *   <li> org.ensembl.healthcheck.testcase.eg_core.IdentityXref </li> 
 *   <li> org.ensembl.healthcheck.testcase.eg_core.StableId </li> 
 *   <li> org.ensembl.healthcheck.testcase.eg_core.EgProteinFeatureTranslation </li> 
 *   <li> org.ensembl.healthcheck.testcase.eg_core.SampleSetting </li> 
 *   <li> org.ensembl.healthcheck.testcase.eg_core.InterproFeature </li> 
 *   <li> org.ensembl.healthcheck.testcase.eg_core.PeptideTranslationAttribs </li> 
 *   <li> org.ensembl.healthcheck.testcase.eg_core.DuplicateMetaKeys </li> 
 *   <li> org.ensembl.healthcheck.testcase.eg_core.SpeciesSqlName </li> 
 *   <li> org.ensembl.healthcheck.testcase.eg_core.DuplicateTopLevel </li> 
 *   <li> org.ensembl.healthcheck.testcase.eg_core.SharedDisplayXref </li> 
 * </ul>
 *
 * @author Autogenerated
 *
 */
public class EnsemblGenomes extends GroupOfTests {
	
	public EnsemblGenomes() {

		addTest(
			org.ensembl.healthcheck.testcase.eg_core.DuplicateXref.class,
			org.ensembl.healthcheck.testcase.eg_core.ExonBoundary.class,
			org.ensembl.healthcheck.testcase.eg_core.DisplayXrefId.class,
			org.ensembl.healthcheck.testcase.eg_core.MetaForCompara.class,
			org.ensembl.healthcheck.testcase.eg_core.GenesDisplayable.class,
			org.ensembl.healthcheck.testcase.eg_core.DeprecatedEgMeta.class,
			org.ensembl.healthcheck.testcase.eg_core.SeqRegionName.class,
			org.ensembl.healthcheck.testcase.eg_core.GeneGC.class,
			org.ensembl.healthcheck.testcase.eg_core.AliasAndNaming.class,
			org.ensembl.healthcheck.testcase.eg_core.GeneDescription.class,
			org.ensembl.healthcheck.testcase.eg_core.PermittedEgMeta.class,
			org.ensembl.healthcheck.testcase.eg_core.GeneDescriptionSource.class,
			org.ensembl.healthcheck.testcase.eg_core.GeneDescriptionNewline.class,
			org.ensembl.healthcheck.testcase.eg_core.RequiredEgMeta.class,
			org.ensembl.healthcheck.testcase.eg_core.IgiXref.class,
			org.ensembl.healthcheck.testcase.eg_core.ProteinCodingGene.class,
			org.ensembl.healthcheck.testcase.eg_core.DuplicateProteinId.class,
			org.ensembl.healthcheck.testcase.eg_core.TranslationAttribType.class,
			org.ensembl.healthcheck.testcase.eg_core.IdentityXref.class,
			org.ensembl.healthcheck.testcase.eg_core.StableId.class,
			org.ensembl.healthcheck.testcase.eg_core.EgProteinFeatureTranslation.class,
			org.ensembl.healthcheck.testcase.eg_core.SampleSetting.class,
			org.ensembl.healthcheck.testcase.eg_core.InterproFeature.class,
			org.ensembl.healthcheck.testcase.eg_core.PeptideTranslationAttribs.class,
			org.ensembl.healthcheck.testcase.eg_core.DuplicateMetaKeys.class,
			org.ensembl.healthcheck.testcase.eg_core.SpeciesProductionName.class,
			org.ensembl.healthcheck.testcase.eg_core.DuplicateTopLevel.class,
			org.ensembl.healthcheck.testcase.eg_core.SharedDisplayXref.class
		);
	}
}
