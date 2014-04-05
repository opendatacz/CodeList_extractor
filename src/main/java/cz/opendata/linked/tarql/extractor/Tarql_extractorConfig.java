package cz.opendata.linked.tarql.extractor;

import cz.cuni.mff.xrg.odcs.commons.module.config.DPUConfigObjectBase;

/**
 *
 * Put your DPU's configuration here.
 *
 */
public class Tarql_extractorConfig extends DPUConfigObjectBase {

	public String sparqlQuery;
	public String filepathOrURI;

	public Tarql_extractorConfig() {
		this.sparqlQuery= "";
		this.filepathOrURI = "";
	}

	public Tarql_extractorConfig(String sparqlQuery, String filepathOrURI) {
		this.sparqlQuery = sparqlQuery;
		this.filepathOrURI = filepathOrURI;
	}

    @Override
    public boolean isValid() {

        return true;
    }

}



















