package cz.opendata.linked.tarql.extractor;

import cz.cuni.mff.xrg.odcs.commons.data.DataUnitException;
import cz.cuni.mff.xrg.odcs.commons.dpu.DPU;
import cz.cuni.mff.xrg.odcs.commons.dpu.DPUContext;
import cz.cuni.mff.xrg.odcs.commons.dpu.DPUException;
import cz.cuni.mff.xrg.odcs.commons.dpu.annotation.*;
import cz.cuni.mff.xrg.odcs.commons.module.dpu.ConfigurableBase;
import cz.cuni.mff.xrg.odcs.commons.web.AbstractConfigDialog;
import cz.cuni.mff.xrg.odcs.commons.web.ConfigDialogProvider;
import cz.cuni.mff.xrg.odcs.rdf.interfaces.RDFDataUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.deri.tarql.tarql;

import java.io.File;
import java.io.PrintStream;


@AsExtractor
public class Tarql_extractor extends ConfigurableBase<Tarql_extractorConfig>
        implements DPU, ConfigDialogProvider<Tarql_extractorConfig> {

	private final Logger logger = LoggerFactory.getLogger(Tarql_extractor.class);

	@OutputDataUnit(description="RDF triples returned from parsed CSV or Excel (xls, xlsx) file using Tarql tool.")
    public RDFDataUnit rdfOutput;

    public Tarql_extractor() {
        super(Tarql_extractorConfig.class);
    }

    @Override
    public AbstractConfigDialog<Tarql_extractorConfig> getConfigurationDialog() {
        return new Tarql_extractorDialog();
    }

    @Override
    public void execute(DPUContext context) throws DPUException, DataUnitException {

	    String queryPath = context.getWorkingDir() + File.separator + "query.sparql";
	    String resultPath = context.getWorkingDir() + File.separator + "result.ttl";

	    try {
		    logger.info("Loading SPARQL query to file.");
	        PrintStream queryPs = new PrintStream(queryPath);
		    queryPs.println(this.config.sparqlQuery);
		    queryPs.close();
		    logger.info("SPARQL query loaded.");
	    } catch(Exception e) {
		    throw new DPUException("Could not save SPARQL query to file",e);
	    }

	    try {
		    // Create a stream to hold the tarql output
		    PrintStream resultPs = new PrintStream(resultPath);
		    // Save the old System.out
		    PrintStream old = System.out;
		    // Tell Java to use your special stream
		    System.setOut(resultPs);

		    logger.info("Applying Tarql tool to extract RDF data from CSV or Excel file.");
		    // Print some output: goes to your special stream
		    if(!this.config.filepathOrURI.isEmpty()) {
		        tarql.main(queryPath,this.config.filepathOrURI);
		    } else {
			    tarql.main(queryPath);
		    }
		    logger.info("Extraction by Tarql has been succesful.");
		    resultPs.close();

		    // Put things back
		    System.out.flush();
		    System.setOut(old);

		    rdfOutput.addFromTurtleFile(new File(resultPath));

	    } catch(Exception e) {
		    throw new DPUException("Could not save RDF result to file", e);
	    }

    }

    @Override
    public void cleanUp() {	}

}
