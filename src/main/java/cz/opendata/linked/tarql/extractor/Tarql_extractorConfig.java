package cz.opendata.linked.tarql.extractor;

import cz.cuni.mff.xrg.odcs.commons.configuration.DPUConfigObject;

/**
 *
 * Put your DPU's configuration here.
 *
 */
public class Tarql_extractorConfig implements DPUConfigObject {

	public String sparqlQuery= "";
	public String filepathOrURI = "";

    @Override
    public boolean isValid() {

        return true;
    }

}



















