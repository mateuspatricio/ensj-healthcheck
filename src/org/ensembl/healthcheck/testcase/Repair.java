/*
 * Copyright [1999-2014] Wellcome Trust Sanger Institute and the EMBL-European Bioinformatics Institute
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


package org.ensembl.healthcheck.testcase;

import org.ensembl.healthcheck.DatabaseRegistryEntry;

/**
 * Interface to be implemented by TestCases that can also repair problems they
 * find.
 *  
 */
public interface Repair {

    /**
     * Repair any problems that have been found.
     * 
     * @param dbre
     *          The database to use.
     */
    void repair(DatabaseRegistryEntry dbre);

    /**
     * Show what repairs would be carried out, but <em>do not</em> actually
     * do it.
     * 
     * @param dbre
     *          The database to use.
     */
    void show(DatabaseRegistryEntry dbre);

}
