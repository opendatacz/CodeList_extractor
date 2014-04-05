package cz.opendata.linked.tarql.extractor;


import cz.cuni.mff.xrg.odcs.dataunit.file.FileDataUnit;
import cz.cuni.mff.xrg.odcs.dataunit.file.options.OptionsAdd;
import cz.cuni.mff.xrg.odcs.rdf.interfaces.RDFDataUnit;
import org.junit.Test;
import org.junit.Assert;

import cz.cuni.mff.xrg.odcs.dpu.test.TestEnvironment;

import java.io.File;


public class Tarql_extractorTest {

	@Test
	public void testDownload() {


		String SPARQLQuery = "PREFIX p: <http://pomyka.cz/> \n" +
				"CONSTRUCT {\n" +
				"  ?URI a p:Thing;\n" +
				"      p:color ?barvaWithLang;\n" +
				"      p:name ?jmeno;\n" +
				"}\n" +
				"WHERE {\n" +
				"  BIND (URI(CONCAT('http://pomyka.cz/', ?jmeno,?barva)) AS ?URI)\n" +
				"  BIND (STRLANG(?barva, \"cs\") AS ?barvaWithLang)\n" +
				"}\n" +
				"OFFSET 1";

		String pathToTestingDataFile = "src/main/resources/data.xls";

		Tarql_extractor extractor = new Tarql_extractor();
		Tarql_extractorConfig config = new Tarql_extractorConfig(SPARQLQuery,pathToTestingDataFile);

		try {
			extractor.configureDirectly(config);

			TestEnvironment env = TestEnvironment.create();
			RDFDataUnit output = env.createRdfOutput("extractedRDF",false);

			try {
				// run the execution
				env.run(extractor);

				Assert.assertTrue("No result triples",output.getTripleCount() > 0);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// release resources
				env.release();
			}


		} catch(Exception e) {
			e.printStackTrace();
		}

	}


	@Test
	public void testDPUFileInput() {

		String SPARQLQuery = "PREFIX p: <http://pomyka.cz/> \n" +
				"CONSTRUCT {\n" +
				"  ?URI a p:Thing;\n" +
				"      p:color ?barvaWithLang;\n" +
				"      p:name ?jmeno;\n" +
				"}\n" +
				"WHERE {\n" +
				"  BIND (URI(CONCAT('http://pomyka.cz/', ?jmeno,?barva)) AS ?URI)\n" +
				"  BIND (STRLANG(?barva, \"cs\") AS ?barvaWithLang)\n" +
				"}\n" +
				"OFFSET 1";

		String pathToTestingDataDir = "src/main/resources";
		String pathToTestingDataFile = pathToTestingDataDir + File.separator + "data.xls";

		Tarql_extractor extractor = new Tarql_extractor();
		Tarql_extractorConfig config = new Tarql_extractorConfig(SPARQLQuery,pathToTestingDataFile);

		try {
			extractor.configureDirectly(config);

			TestEnvironment env = TestEnvironment.create();
			RDFDataUnit output = env.createRdfOutput("extractedRDF",false);
			FileDataUnit input = env.createFileInput("inputFile",new File(pathToTestingDataDir));
			input.getRootDir().addExistingFile(new File(pathToTestingDataFile),new OptionsAdd(false));

			try {
				// run the execution
				env.run(extractor);

				Assert.assertTrue("No result triples",output.getTripleCount() > 0);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// release resources
				env.release();
			}

		} catch(Exception e) {
			e.printStackTrace();
		}

	}



}
