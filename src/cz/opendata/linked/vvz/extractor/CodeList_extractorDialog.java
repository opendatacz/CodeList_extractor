package cz.opendata.linked.vvz.extractor;

import com.vaadin.ui.*;
import cz.cuni.mff.xrg.odcs.commons.configuration.*;
import cz.cuni.mff.xrg.odcs.commons.module.dialog.BaseConfigDialog;

/**
 * DPU's configuration dialog. User can use this dialog to configure DPU configuration.
 *
 */
public class CodeList_extractorDialog extends BaseConfigDialog<CodeList_extractorConfig> {

    private GridLayout mainLayout;


    public CodeList_extractorDialog() {
        super(CodeList_extractorConfig.class);
        buildMainLayout();
        setCompositionRoot(mainLayout);
    }


	@Override
	public void setConfiguration(CodeList_extractorConfig conf) throws ConfigException {

	}

	@Override
	public CodeList_extractorConfig getConfiguration() throws ConfigException {


        CodeList_extractorConfig conf = new CodeList_extractorConfig();

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
        



        return mainLayout;
    }

	
}
