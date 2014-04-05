package cz.opendata.linked.tarql.extractor;

import cz.cuni.mff.xrg.odcs.commons.data.DataUnitException;
import cz.cuni.mff.xrg.odcs.commons.dpu.DPU;
import cz.cuni.mff.xrg.odcs.commons.dpu.DPUContext;
import cz.cuni.mff.xrg.odcs.commons.dpu.DPUException;
import cz.cuni.mff.xrg.odcs.commons.dpu.annotation.*;
import cz.cuni.mff.xrg.odcs.commons.module.dpu.ConfigurableBase;
import cz.cuni.mff.xrg.odcs.commons.web.AbstractConfigDialog;
import cz.cuni.mff.xrg.odcs.commons.web.ConfigDialogProvider;
import cz.cuni.mff.xrg.odcs.dataunit.file.FileDataUnit;
import cz.cuni.mff.xrg.odcs.dataunit.file.handlers.DirectoryHandler;
import cz.cuni.mff.xrg.odcs.rdf.interfaces.RDFDataUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.deri.tarql.Tarql;

import java.io.*;
import cz.cuni.mff.xrg.odcs.dataunit.file.handlers.Handler;
import cz.cuni.mff.xrg.odcs.dataunit.file.handlers.FileHandler;
import cz.cuni.mff.xrg.odcs.commons.message.MessageType;



@AsExtractor
public class Tarql_extractor extends ConfigurableBase<Tarql_extractorConfig>
        implements DPU, ConfigDialogProvider<Tarql_extractorConfig> {

	private final Logger logger = LoggerFactory.getLogger(Tarql_extractor.class);

	@InputDataUnit(name = "inputFile", optional=true)
	public FileDataUnit inputFile;

	@OutputDataUnit(name="extractedRDF",description="RDF triples returned from parsed CSV or Excel (xls, xlsx) file using Tarql tool.")
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

	    String filePathOrURL = "";

	    // does input file come from DPU config or DPU file input?
	    if(this.inputFile==null) { // input file maybe comes from DPU config
		    filePathOrURL = this.config.filepathOrURI;
	    } else { // input file comes from DPU file input
		    DirectoryHandler root = inputFile.getRootDir();
		    for (Handler handler : root) {
			    if (handler instanceof FileHandler) {
				    File file = handler.asFile();
			        filePathOrURL = file.getAbsolutePath();
			    }
		    }
	    }

	    String queryPath = context.getWorkingDir() + File.separator + "query.sparql";
	    String resultPath = context.getWorkingDir() + File.separator + "result.ttl";

	    try {
		    logger.info("Loading SPARQL query to file.");

	        PrintStream queryPs = new PrintStream(queryPath);

		    queryPs.println(this.config.sparqlQuery);
		    queryPs.flush();
		    queryPs.close();

		    logger.info("SPARQL query loaded.");
	    } catch(Exception e) {
		    throw new DPUException("Could not save SPARQL query to file",e);
	    }

	    try {

		    logger.info("Applying Tarql tool to extract RDF data from CSV or Excel file.");


		    if(!filePathOrURL.isEmpty()) {
		        Tarql.main("--out",resultPath,queryPath,filePathOrURL);
		    } else {
			    Tarql.main("--out",resultPath,queryPath);
		    }

		    logger.info("Extraction by Tarql has been succesful.");

		    rdfOutput.addFromTurtleFile(new File(resultPath));

	    } catch(Exception e) {
		    throw new DPUException("Could not save RDF result to file", e);
	    }

    }

    @Override
    public void cleanUp() {	}

}
