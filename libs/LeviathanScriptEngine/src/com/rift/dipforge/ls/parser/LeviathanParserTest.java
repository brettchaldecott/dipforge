package com.rift.dipforge.ls.parser;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import com.rift.dip.leviathan.LeviathanLexer;
import com.rift.dip.leviathan.LeviathanParser;
import com.rift.dipforge.ls.parser.obj.Workflow;

public class LeviathanParserTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		LeviathanLexer lex = new LeviathanLexer(new ANTLRFileStream("example-leviathan.ls"));
            CommonTokenStream tokens = new CommonTokenStream(lex);

            LeviathanParser parser = new LeviathanParser(tokens);

            try {
                Workflow flow = parser.workflow();
                System.out.println(flow.toString());
            } catch (RecognitionException e) {
                e.printStackTrace();
            }
	}

}
