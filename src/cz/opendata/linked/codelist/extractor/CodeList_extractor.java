package cz.opendata.linked.codelist.extractor;

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



@AsExtractor
public class CodeList_extractor extends ConfigurableBase<CodeList_extractorConfig>
        implements DPU, ConfigDialogProvider<CodeList_extractorConfig> {

	private final Logger logger = LoggerFactory.getLogger(CodeList_extractor.class);

	private DPUContext context;

	@OutputDataUnit(description="...")
    public RDFDataUnit rdfOutput;

    public CodeList_extractor() {
        super(CodeList_extractorConfig.class);
    }

    @Override
    public AbstractConfigDialog<CodeList_extractorConfig> getConfigurationDialog() {
        return new CodeList_extractorDialog();
    }

    @Override
    public void execute(DPUContext context) throws DPUException, DataUnitException {

	    this.context = context;


    }

    @Override
    public void cleanUp() {	}

}
