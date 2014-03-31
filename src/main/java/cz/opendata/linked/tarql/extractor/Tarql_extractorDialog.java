package cz.opendata.linked.tarql.extractor;

import com.vaadin.ui.*;
import cz.cuni.mff.xrg.odcs.commons.configuration.*;
import cz.cuni.mff.xrg.odcs.commons.module.dialog.BaseConfigDialog;

/**
 * DPU's configuration dialog. User can use this dialog to configure DPU configuration.
 *
 */
public class Tarql_extractorDialog extends BaseConfigDialog<Tarql_extractorConfig> {

    private GridLayout mainLayout;
	private TextArea input_sparqlQuery;
	private TextField input_filenameOrURI;


    public Tarql_extractorDialog() {
        super(Tarql_extractorConfig.class);
        buildMainLayout();
        setCompositionRoot(mainLayout);
    }


	@Override
	public void setConfiguration(Tarql_extractorConfig conf) throws ConfigException {
		input_sparqlQuery.setValue(conf.sparqlQuery);
		input_filenameOrURI.setValue(conf.filepathOrURI);
	}

	@Override
	public Tarql_extractorConfig getConfiguration() throws ConfigException {


        Tarql_extractorConfig conf = new Tarql_extractorConfig();
		conf.sparqlQuery = input_sparqlQuery.getValue();
		conf.filepathOrURI = input_filenameOrURI.getValue().trim();

        return conf;

	}

    /**
     * Builds main layout
     *
     * @return mainLayout GridLayout with all components of configuration
     *         dialog.
     */
    private GridLayout buildMainLayout() {

        // common part: create layout
        mainLayout = new GridLayout(1,4);
        mainLayout.setImmediate(false);
        mainLayout.setWidth("100%");
        mainLayout.setHeight("100%");
        mainLayout.setMargin(false);
        //mainLayout.setSpacing(true);

        // top-level component properties
        setWidth("100%");
        setHeight("100%");

	    // text field for date from
		input_sparqlQuery = new TextArea();
		input_sparqlQuery.setNullRepresentation("");
		input_sparqlQuery.setCaption("SPARQL query");
		input_sparqlQuery.setImmediate(true);
		input_sparqlQuery.setWidth("100%");
		input_sparqlQuery.setHeight("150px");
		input_sparqlQuery.setDescription("");

	    mainLayout.addComponent(   input_sparqlQuery);

	    // text field for date to
	    input_filenameOrURI = new TextField();
	    input_filenameOrURI.setNullRepresentation("");
	    input_filenameOrURI.setCaption("Filepath or URI:");
	    input_filenameOrURI.setImmediate(true);
	    input_filenameOrURI.setWidth("100%");
	    input_filenameOrURI.setHeight("-1px");
	    input_filenameOrURI.setDescription("Filepath or URI where can be found CSV or Excel (xls,xlsx) file.");

	    mainLayout.addComponent(input_filenameOrURI);

        return mainLayout;
    }

	
}
