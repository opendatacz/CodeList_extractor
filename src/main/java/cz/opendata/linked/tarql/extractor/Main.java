package cz.opendata.linked.tarql.extractor;

import org.deri.tarql.tarql;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Main {

	public static void main(String[] args) {


		// Create a stream to hold the output
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		// IMPORTANT: Save the old System.out!
		PrintStream old = System.out;
		// Tell Java to use your special stream
		System.setOut(ps);

		// Print some output: goes to your special stream
		tarql.main("/home/cammeron/Java-Workspace/tarql/temp/query2.sparql","http://michal.pomyka.cz/X/pravni_forma.xls");

		// Put things back
		System.out.flush();
		System.setOut(old);
		// Show what happened
		System.out.println(baos.toString());

	}

}
