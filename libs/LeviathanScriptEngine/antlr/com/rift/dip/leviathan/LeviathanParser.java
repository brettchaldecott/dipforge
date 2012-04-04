// $ANTLR 3.4 /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g 2012-04-04 10:57:15

  package com.rift.dip.leviathan;
  
  import com.rift.dipforge.ls.parser.obj.LsAnnotation;
  import com.rift.dipforge.ls.parser.obj.Assignment;
  import com.rift.dipforge.ls.parser.obj.TypeDefinition;
  import com.rift.dipforge.ls.parser.obj.Workflow;
  import com.rift.dipforge.ls.parser.obj.Expression;
  import com.rift.dipforge.ls.parser.obj.Relation;
  import com.rift.dipforge.ls.parser.obj.Operation;
  import com.rift.dipforge.ls.parser.obj.CallStatement;
  import com.rift.dipforge.ls.parser.obj.Variable;
  import com.rift.dipforge.ls.parser.obj.Statement;
  import com.rift.dipforge.ls.parser.obj.StatementArgument;
  import com.rift.dipforge.ls.parser.obj.LsListArgument;
  import com.rift.dipforge.ls.parser.obj.ParameterArgument;
  import com.rift.dipforge.ls.parser.obj.Block;
  import com.rift.dipforge.ls.parser.obj.LsList;
  import com.rift.dipforge.ls.parser.obj.MethodDefinition;
  import com.rift.dipforge.ls.parser.obj.Types;
  import com.rift.dipforge.ls.parser.obj.ReturnStatement;
  import com.rift.dipforge.ls.parser.obj.ContinueStatement;
  import com.rift.dipforge.ls.parser.obj.BreakStatement;
  import com.rift.dipforge.ls.parser.obj.IfStatement;
  import com.rift.dipforge.ls.parser.obj.WhileStatement;
  import com.rift.dipforge.ls.parser.obj.ForStatement;
  import com.rift.dipforge.ls.parser.obj.CaseStatement;
  import com.rift.dipforge.ls.parser.obj.IncrementStatement;
  import com.rift.dipforge.ls.parser.obj.NegationStatement;
  import com.rift.dipforge.ls.parser.obj.UnaryStatement;
  import com.rift.dipforge.ls.parser.obj.MultStatement;
  import com.rift.dipforge.ls.parser.obj.AddStatement;
  import com.rift.dipforge.ls.parser.obj.Comparison;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class LeviathanParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "COMMENT", "FLOAT", "IDENT", "INCREMENTER", "INTEGER", "LINE_COMMENT", "STRING", "WHITESPACE", "'!'", "'!='", "'%'", "'&&'", "'('", "')'", "'*'", "'+'", "','", "'-'", "'.'", "'/'", "':'", "';'", "'<'", "'<='", "'='", "'=='", "'>'", "'>='", "'@'", "'String'", "'['", "']'", "'break'", "'byte'", "'char'", "'continue'", "'def'", "'double'", "'else'", "'float'", "'flow'", "'for'", "'if'", "'int'", "'long'", "'return'", "'void'", "'while'", "'{'", "'||'", "'}'"
    };

    public static final int EOF=-1;
    public static final int T__12=12;
    public static final int T__13=13;
    public static final int T__14=14;
    public static final int T__15=15;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__19=19;
    public static final int T__20=20;
    public static final int T__21=21;
    public static final int T__22=22;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int T__25=25;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int T__29=29;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__42=42;
    public static final int T__43=43;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int T__50=50;
    public static final int T__51=51;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int COMMENT=4;
    public static final int FLOAT=5;
    public static final int IDENT=6;
    public static final int INCREMENTER=7;
    public static final int INTEGER=8;
    public static final int LINE_COMMENT=9;
    public static final int STRING=10;
    public static final int WHITESPACE=11;

    // delegates
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators


    public LeviathanParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public LeviathanParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

    public String[] getTokenNames() { return LeviathanParser.tokenNames; }
    public String getGrammarFileName() { return "/home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g"; }


      
      private Workflow workflow;
      private String workflowName;
      private Block currentBlock;
      
      
      
      public Workflow getWorkflow() {
        return workflow;
      }



    // $ANTLR start "workflow"
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:92:1: workflow returns [Workflow value] : (anno= annotation )* 'flow' flowName= IDENT '{' ( variable ( assignment )? )* (meth1= method )* blo= block (meth2= method )* '}' ;
    public final Workflow workflow() throws RecognitionException {
        Workflow value = null;


        Token flowName=null;
        LsAnnotation anno =null;

        MethodDefinition meth1 =null;

        Block blo =null;

        MethodDefinition meth2 =null;

        Variable variable1 =null;

        Assignment assignment2 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:93:3: ( (anno= annotation )* 'flow' flowName= IDENT '{' ( variable ( assignment )? )* (meth1= method )* blo= block (meth2= method )* '}' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:93:5: (anno= annotation )* 'flow' flowName= IDENT '{' ( variable ( assignment )? )* (meth1= method )* blo= block (meth2= method )* '}'
            {

                value = new Workflow();
                currentBlock = value;
                

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:98:4: (anno= annotation )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==32) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:98:5: anno= annotation
            	    {
            	    pushFollow(FOLLOW_annotation_in_workflow66);
            	    anno=annotation();

            	    state._fsp--;


            	    value.getAnnotations().add(anno);

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            match(input,44,FOLLOW_44_in_workflow76); 

            flowName=(Token)match(input,IDENT,FOLLOW_IDENT_in_workflow80); 

             value.setName((flowName!=null?flowName.getText():null));

            match(input,52,FOLLOW_52_in_workflow87); 

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:101:4: ( variable ( assignment )? )*
            loop3:
            do {
                int alt3=2;
                switch ( input.LA(1) ) {
                case 50:
                    {
                    int LA3_1 = input.LA(2);

                    if ( (LA3_1==IDENT) ) {
                        int LA3_11 = input.LA(3);

                        if ( (LA3_11==28||LA3_11==33||(LA3_11 >= 37 && LA3_11 <= 38)||(LA3_11 >= 40 && LA3_11 <= 41)||LA3_11==43||(LA3_11 >= 47 && LA3_11 <= 48)||LA3_11==50||LA3_11==52) ) {
                            alt3=1;
                        }


                    }


                    }
                    break;
                case 47:
                    {
                    int LA3_2 = input.LA(2);

                    if ( (LA3_2==IDENT) ) {
                        int LA3_11 = input.LA(3);

                        if ( (LA3_11==28||LA3_11==33||(LA3_11 >= 37 && LA3_11 <= 38)||(LA3_11 >= 40 && LA3_11 <= 41)||LA3_11==43||(LA3_11 >= 47 && LA3_11 <= 48)||LA3_11==50||LA3_11==52) ) {
                            alt3=1;
                        }


                    }


                    }
                    break;
                case 48:
                    {
                    int LA3_3 = input.LA(2);

                    if ( (LA3_3==IDENT) ) {
                        int LA3_11 = input.LA(3);

                        if ( (LA3_11==28||LA3_11==33||(LA3_11 >= 37 && LA3_11 <= 38)||(LA3_11 >= 40 && LA3_11 <= 41)||LA3_11==43||(LA3_11 >= 47 && LA3_11 <= 48)||LA3_11==50||LA3_11==52) ) {
                            alt3=1;
                        }


                    }


                    }
                    break;
                case 41:
                    {
                    int LA3_4 = input.LA(2);

                    if ( (LA3_4==IDENT) ) {
                        int LA3_11 = input.LA(3);

                        if ( (LA3_11==28||LA3_11==33||(LA3_11 >= 37 && LA3_11 <= 38)||(LA3_11 >= 40 && LA3_11 <= 41)||LA3_11==43||(LA3_11 >= 47 && LA3_11 <= 48)||LA3_11==50||LA3_11==52) ) {
                            alt3=1;
                        }


                    }


                    }
                    break;
                case 43:
                    {
                    int LA3_5 = input.LA(2);

                    if ( (LA3_5==IDENT) ) {
                        int LA3_11 = input.LA(3);

                        if ( (LA3_11==28||LA3_11==33||(LA3_11 >= 37 && LA3_11 <= 38)||(LA3_11 >= 40 && LA3_11 <= 41)||LA3_11==43||(LA3_11 >= 47 && LA3_11 <= 48)||LA3_11==50||LA3_11==52) ) {
                            alt3=1;
                        }


                    }


                    }
                    break;
                case 38:
                    {
                    int LA3_6 = input.LA(2);

                    if ( (LA3_6==IDENT) ) {
                        int LA3_11 = input.LA(3);

                        if ( (LA3_11==28||LA3_11==33||(LA3_11 >= 37 && LA3_11 <= 38)||(LA3_11 >= 40 && LA3_11 <= 41)||LA3_11==43||(LA3_11 >= 47 && LA3_11 <= 48)||LA3_11==50||LA3_11==52) ) {
                            alt3=1;
                        }


                    }


                    }
                    break;
                case 37:
                    {
                    int LA3_7 = input.LA(2);

                    if ( (LA3_7==IDENT) ) {
                        int LA3_11 = input.LA(3);

                        if ( (LA3_11==28||LA3_11==33||(LA3_11 >= 37 && LA3_11 <= 38)||(LA3_11 >= 40 && LA3_11 <= 41)||LA3_11==43||(LA3_11 >= 47 && LA3_11 <= 48)||LA3_11==50||LA3_11==52) ) {
                            alt3=1;
                        }


                    }


                    }
                    break;
                case 33:
                    {
                    int LA3_8 = input.LA(2);

                    if ( (LA3_8==IDENT) ) {
                        int LA3_11 = input.LA(3);

                        if ( (LA3_11==28||LA3_11==33||(LA3_11 >= 37 && LA3_11 <= 38)||(LA3_11 >= 40 && LA3_11 <= 41)||LA3_11==43||(LA3_11 >= 47 && LA3_11 <= 48)||LA3_11==50||LA3_11==52) ) {
                            alt3=1;
                        }


                    }


                    }
                    break;
                case 40:
                    {
                    int LA3_9 = input.LA(2);

                    if ( (LA3_9==IDENT) ) {
                        int LA3_11 = input.LA(3);

                        if ( (LA3_11==28||LA3_11==33||(LA3_11 >= 37 && LA3_11 <= 38)||(LA3_11 >= 40 && LA3_11 <= 41)||LA3_11==43||(LA3_11 >= 47 && LA3_11 <= 48)||LA3_11==50||LA3_11==52) ) {
                            alt3=1;
                        }


                    }


                    }
                    break;

                }

                switch (alt3) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:101:5: variable ( assignment )?
            	    {
            	    pushFollow(FOLLOW_variable_in_workflow93);
            	    variable1=variable();

            	    state._fsp--;


            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:101:14: ( assignment )?
            	    int alt2=2;
            	    int LA2_0 = input.LA(1);

            	    if ( (LA2_0==28) ) {
            	        alt2=1;
            	    }
            	    switch (alt2) {
            	        case 1 :
            	            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:101:15: assignment
            	            {
            	            pushFollow(FOLLOW_assignment_in_workflow96);
            	            assignment2=assignment();

            	            state._fsp--;



            	                  variable1.setInitialValue(assignment2);
            	                

            	            }
            	            break;

            	    }



            	          value.getStatements().add(variable1);

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:105:5: (meth1= method )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==33||(LA4_0 >= 37 && LA4_0 <= 38)||(LA4_0 >= 40 && LA4_0 <= 41)||LA4_0==43||(LA4_0 >= 47 && LA4_0 <= 48)||LA4_0==50) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:105:6: meth1= method
            	    {
            	    pushFollow(FOLLOW_method_in_workflow113);
            	    meth1=method();

            	    state._fsp--;


            	    value.getStatements().add(meth1);

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            pushFollow(FOLLOW_block_in_workflow126);
            blo=block();

            state._fsp--;



                 value.getStatements().add(blo);
                 

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:109:5: (meth2= method )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==33||(LA5_0 >= 37 && LA5_0 <= 38)||(LA5_0 >= 40 && LA5_0 <= 41)||LA5_0==43||(LA5_0 >= 47 && LA5_0 <= 48)||LA5_0==50) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:109:6: meth2= method
            	    {
            	    pushFollow(FOLLOW_method_in_workflow137);
            	    meth2=method();

            	    state._fsp--;



            	        value.getStatements().add(meth2);
            	        

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            match(input,54,FOLLOW_54_in_workflow146); 

             
                  workflow = value;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "workflow"



    // $ANTLR start "annotation"
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:122:1: annotation returns [LsAnnotation value] : '@' name= IDENT '(' ( STRING )* ')' ;
    public final LsAnnotation annotation() throws RecognitionException {
        LsAnnotation value = null;


        Token name=null;
        Token STRING3=null;

        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:123:3: ( '@' name= IDENT '(' ( STRING )* ')' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:123:5: '@' name= IDENT '(' ( STRING )* ')'
            {
            match(input,32,FOLLOW_32_in_annotation165); 

            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_annotation169); 


                  value = new LsAnnotation((name!=null?name.getText():null));
                

            match(input,16,FOLLOW_16_in_annotation173); 

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:125:11: ( STRING )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==STRING) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:125:12: STRING
            	    {
            	    STRING3=(Token)match(input,STRING,FOLLOW_STRING_in_annotation176); 


            	          value.addValue((STRING3!=null?STRING3.getText():null));
            	        

            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);


            match(input,17,FOLLOW_17_in_annotation181); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "annotation"



    // $ANTLR start "statement"
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:130:1: statement returns [Statement value] : (varCall= callStatement (ass1= assignment )? ( ';' )? |var= variable (ass2= assignment )? ( ';' )? |ifStat= ifStatement |whileStat= whileStatement |forStat= forStatement |bl= block |ret= returnStatement | 'continue' | 'break' ) ;
    public final Statement statement() throws RecognitionException {
        Statement value = null;


        CallStatement varCall =null;

        Assignment ass1 =null;

        Variable var =null;

        Assignment ass2 =null;

        IfStatement ifStat =null;

        WhileStatement whileStat =null;

        ForStatement forStat =null;

        Block bl =null;

        ReturnStatement ret =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:131:3: ( (varCall= callStatement (ass1= assignment )? ( ';' )? |var= variable (ass2= assignment )? ( ';' )? |ifStat= ifStatement |whileStat= whileStatement |forStat= forStatement |bl= block |ret= returnStatement | 'continue' | 'break' ) )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:131:5: (varCall= callStatement (ass1= assignment )? ( ';' )? |var= variable (ass2= assignment )? ( ';' )? |ifStat= ifStatement |whileStat= whileStatement |forStat= forStatement |bl= block |ret= returnStatement | 'continue' | 'break' )
            {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:131:5: (varCall= callStatement (ass1= assignment )? ( ';' )? |var= variable (ass2= assignment )? ( ';' )? |ifStat= ifStatement |whileStat= whileStatement |forStat= forStatement |bl= block |ret= returnStatement | 'continue' | 'break' )
            int alt11=9;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                alt11=1;
                }
                break;
            case 33:
            case 37:
            case 38:
            case 40:
            case 41:
            case 43:
            case 47:
            case 48:
            case 50:
                {
                alt11=2;
                }
                break;
            case 46:
                {
                alt11=3;
                }
                break;
            case 51:
                {
                alt11=4;
                }
                break;
            case 45:
                {
                alt11=5;
                }
                break;
            case 52:
                {
                alt11=6;
                }
                break;
            case 49:
                {
                alt11=7;
                }
                break;
            case 39:
                {
                alt11=8;
                }
                break;
            case 36:
                {
                alt11=9;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;

            }

            switch (alt11) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:132:3: varCall= callStatement (ass1= assignment )? ( ';' )?
                    {
                    pushFollow(FOLLOW_callStatement_in_statement205);
                    varCall=callStatement();

                    state._fsp--;


                    value = varCall;

                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:132:45: (ass1= assignment )?
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( (LA7_0==28) ) {
                        alt7=1;
                    }
                    switch (alt7) {
                        case 1 :
                            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:132:46: ass1= assignment
                            {
                            pushFollow(FOLLOW_assignment_in_statement212);
                            ass1=assignment();

                            state._fsp--;


                            varCall.setAssignment(ass1);

                            }
                            break;

                    }


                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:132:95: ( ';' )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0==25) ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:132:95: ';'
                            {
                            match(input,25,FOLLOW_25_in_statement218); 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:133:5: var= variable (ass2= assignment )? ( ';' )?
                    {
                    pushFollow(FOLLOW_variable_in_statement228);
                    var=variable();

                    state._fsp--;


                    value = var;

                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:133:34: (ass2= assignment )?
                    int alt9=2;
                    int LA9_0 = input.LA(1);

                    if ( (LA9_0==28) ) {
                        alt9=1;
                    }
                    switch (alt9) {
                        case 1 :
                            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:133:35: ass2= assignment
                            {
                            pushFollow(FOLLOW_assignment_in_statement235);
                            ass2=assignment();

                            state._fsp--;


                            var.setInitialValue(ass2);

                            }
                            break;

                    }


                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:133:82: ( ';' )?
                    int alt10=2;
                    int LA10_0 = input.LA(1);

                    if ( (LA10_0==25) ) {
                        alt10=1;
                    }
                    switch (alt10) {
                        case 1 :
                            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:133:82: ';'
                            {
                            match(input,25,FOLLOW_25_in_statement241); 

                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:134:5: ifStat= ifStatement
                    {
                    pushFollow(FOLLOW_ifStatement_in_statement251);
                    ifStat=ifStatement();

                    state._fsp--;


                    value = ifStat;

                    }
                    break;
                case 4 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:135:5: whileStat= whileStatement
                    {
                    pushFollow(FOLLOW_whileStatement_in_statement261);
                    whileStat=whileStatement();

                    state._fsp--;


                    value = whileStat;

                    }
                    break;
                case 5 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:136:5: forStat= forStatement
                    {
                    pushFollow(FOLLOW_forStatement_in_statement271);
                    forStat=forStatement();

                    state._fsp--;


                    value = forStat;

                    }
                    break;
                case 6 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:138:5: bl= block
                    {
                    pushFollow(FOLLOW_block_in_statement284);
                    bl=block();

                    state._fsp--;


                    value = bl;

                    }
                    break;
                case 7 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:139:5: ret= returnStatement
                    {
                    pushFollow(FOLLOW_returnStatement_in_statement294);
                    ret=returnStatement();

                    state._fsp--;


                    value = ret;

                    }
                    break;
                case 8 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:140:5: 'continue'
                    {
                    match(input,39,FOLLOW_39_in_statement302); 

                    value = new ContinueStatement(); 

                    }
                    break;
                case 9 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:141:5: 'break'
                    {
                    match(input,36,FOLLOW_36_in_statement310); 

                    value = new BreakStatement(); 

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "statement"



    // $ANTLR start "block"
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:143:1: block returns [Block value] : '{' ( statement )* '}' ;
    public final Block block() throws RecognitionException {
        Block value = null;


        Statement statement4 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:144:3: ( '{' ( statement )* '}' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:144:5: '{' ( statement )* '}'
            {
            match(input,52,FOLLOW_52_in_block328); 


                  value = new Block();
                  value.setParent(currentBlock);
                  currentBlock = value;
                  

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:148:9: ( statement )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==IDENT||LA12_0==33||(LA12_0 >= 36 && LA12_0 <= 41)||LA12_0==43||(LA12_0 >= 45 && LA12_0 <= 52)) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:148:10: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_block333);
            	    statement4=statement();

            	    state._fsp--;


            	     value.getStatements().add(statement4);

            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);


            match(input,54,FOLLOW_54_in_block340); 


                  currentBlock = currentBlock.getParent();
                  

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "block"



    // $ANTLR start "ifStatement"
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:153:1: ifStatement returns [IfStatement value] : 'if' '(' exp1= expression ')' bl1= block ( 'else' 'if' '(' exp2= expression ')' bl2= block )* ( 'else' bl3= block )? ;
    public final IfStatement ifStatement() throws RecognitionException {
        IfStatement value = null;


        Expression exp1 =null;

        Block bl1 =null;

        Expression exp2 =null;

        Block bl2 =null;

        Block bl3 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:154:3: ( 'if' '(' exp1= expression ')' bl1= block ( 'else' 'if' '(' exp2= expression ')' bl2= block )* ( 'else' bl3= block )? )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:154:5: 'if' '(' exp1= expression ')' bl1= block ( 'else' 'if' '(' exp2= expression ')' bl2= block )* ( 'else' bl3= block )?
            {
            value = new IfStatement();

            match(input,46,FOLLOW_46_in_ifStatement363); 

            match(input,16,FOLLOW_16_in_ifStatement365); 

            pushFollow(FOLLOW_expression_in_ifStatement369);
            exp1=expression();

            state._fsp--;


            match(input,17,FOLLOW_17_in_ifStatement371); 

            pushFollow(FOLLOW_block_in_ifStatement375);
            bl1=block();

            state._fsp--;



                  value.addBlock(IfStatement.IfStatementType.IF, exp1, bl1); 

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:156:5: ( 'else' 'if' '(' exp2= expression ')' bl2= block )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==42) ) {
                    int LA13_1 = input.LA(2);

                    if ( (LA13_1==46) ) {
                        alt13=1;
                    }


                }


                switch (alt13) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:156:6: 'else' 'if' '(' exp2= expression ')' bl2= block
            	    {
            	    match(input,42,FOLLOW_42_in_ifStatement384); 

            	    match(input,46,FOLLOW_46_in_ifStatement386); 

            	    match(input,16,FOLLOW_16_in_ifStatement388); 

            	    pushFollow(FOLLOW_expression_in_ifStatement392);
            	    exp2=expression();

            	    state._fsp--;


            	    match(input,17,FOLLOW_17_in_ifStatement394); 

            	    pushFollow(FOLLOW_block_in_ifStatement398);
            	    bl2=block();

            	    state._fsp--;



            	          value.addBlock(IfStatement.IfStatementType.ELSE_IF, exp2, bl2); 

            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);


            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:158:5: ( 'else' bl3= block )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==42) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:158:6: 'else' bl3= block
                    {
                    match(input,42,FOLLOW_42_in_ifStatement409); 

                    pushFollow(FOLLOW_block_in_ifStatement413);
                    bl3=block();

                    state._fsp--;



                          value.addBlock(bl3); 

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "ifStatement"



    // $ANTLR start "whileStatement"
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:161:1: whileStatement returns [WhileStatement value] : 'while' '(' exp1= expression ')' bl1= block ;
    public final WhileStatement whileStatement() throws RecognitionException {
        WhileStatement value = null;


        Expression exp1 =null;

        Block bl1 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:162:3: ( 'while' '(' exp1= expression ')' bl1= block )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:162:5: 'while' '(' exp1= expression ')' bl1= block
            {
            value = new WhileStatement();

            match(input,51,FOLLOW_51_in_whileStatement432); 

            match(input,16,FOLLOW_16_in_whileStatement434); 

            pushFollow(FOLLOW_expression_in_whileStatement438);
            exp1=expression();

            state._fsp--;


            match(input,17,FOLLOW_17_in_whileStatement440); 

            pushFollow(FOLLOW_block_in_whileStatement444);
            bl1=block();

            state._fsp--;



                value.setExpression(exp1);
                value.setBlock(bl1);

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "whileStatement"



    // $ANTLR start "forStatement"
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:166:1: forStatement returns [ForStatement value] : 'for' '(' (var= variable ass1= assignment )? ';' exp1= expression ';' (exp2= expression )? ')' bl= block ;
    public final ForStatement forStatement() throws RecognitionException {
        ForStatement value = null;


        Variable var =null;

        Assignment ass1 =null;

        Expression exp1 =null;

        Expression exp2 =null;

        Block bl =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:167:3: ( 'for' '(' (var= variable ass1= assignment )? ';' exp1= expression ';' (exp2= expression )? ')' bl= block )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:167:5: 'for' '(' (var= variable ass1= assignment )? ';' exp1= expression ';' (exp2= expression )? ')' bl= block
            {

              value = new ForStatement();
              value.setParent(currentBlock);
              currentBlock = value;

            match(input,45,FOLLOW_45_in_forStatement461); 

            match(input,16,FOLLOW_16_in_forStatement463); 

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:170:36: (var= variable ass1= assignment )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==33||(LA15_0 >= 37 && LA15_0 <= 38)||(LA15_0 >= 40 && LA15_0 <= 41)||LA15_0==43||(LA15_0 >= 47 && LA15_0 <= 48)||LA15_0==50) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:170:37: var= variable ass1= assignment
                    {
                    pushFollow(FOLLOW_variable_in_forStatement468);
                    var=variable();

                    state._fsp--;


                    pushFollow(FOLLOW_assignment_in_forStatement472);
                    ass1=assignment();

                    state._fsp--;



                      var.setInitialValue(ass1);
                      value.setInitialValue(var);
                      

                    }
                    break;

            }


            match(input,25,FOLLOW_25_in_forStatement479); 

            pushFollow(FOLLOW_expression_in_forStatement483);
            exp1=expression();

            state._fsp--;



              value.setComparison(exp1);
              

            match(input,25,FOLLOW_25_in_forStatement487); 

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:175:10: (exp2= expression )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( ((LA16_0 >= FLOAT && LA16_0 <= INTEGER)||LA16_0==STRING||LA16_0==12||LA16_0==16||LA16_0==19||LA16_0==21) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:175:12: exp2= expression
                    {
                    pushFollow(FOLLOW_expression_in_forStatement494);
                    exp2=expression();

                    state._fsp--;



                      value.setIncrement(exp2);
                      

                    }
                    break;

            }


            match(input,17,FOLLOW_17_in_forStatement500); 

            pushFollow(FOLLOW_block_in_forStatement504);
            bl=block();

            state._fsp--;


            value.setChild(bl);

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "forStatement"



    // $ANTLR start "variable"
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:193:1: variable returns [Variable value] :t1= type IDENT ;
    public final Variable variable() throws RecognitionException {
        Variable value = null;


        Token IDENT5=null;
        String t1 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:194:3: (t1= type IDENT )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:194:5: t1= type IDENT
            {
            value = new Variable();

            pushFollow(FOLLOW_type_in_variable529);
            t1=type();

            state._fsp--;


            IDENT5=(Token)match(input,IDENT,FOLLOW_IDENT_in_variable531); 


                value.setType(t1);
                value.setName((IDENT5!=null?IDENT5.getText():null));
              

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "variable"



    // $ANTLR start "callStatement"
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:199:1: callStatement returns [CallStatement value] :s1= IDENT ( '.' s2= IDENT )* (lsArg1= listArgument |paramArg1= parameterArgument )? ;
    public final CallStatement callStatement() throws RecognitionException {
        CallStatement value = null;


        Token s1=null;
        Token s2=null;
        LsListArgument lsArg1 =null;

        ParameterArgument paramArg1 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:200:3: (s1= IDENT ( '.' s2= IDENT )* (lsArg1= listArgument |paramArg1= parameterArgument )? )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:200:5: s1= IDENT ( '.' s2= IDENT )* (lsArg1= listArgument |paramArg1= parameterArgument )?
            {

                value = new CallStatement();
                CallStatement.CallStatementEntry currentEntry = null;
              

            s1=(Token)match(input,IDENT,FOLLOW_IDENT_in_callStatement553); 


                currentEntry = value.addEntry((s1!=null?s1.getText():null));
              

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:205:5: ( '.' s2= IDENT )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==22) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:205:6: '.' s2= IDENT
            	    {
            	    match(input,22,FOLLOW_22_in_callStatement558); 

            	    s2=(Token)match(input,IDENT,FOLLOW_IDENT_in_callStatement562); 


            	        currentEntry = value.addEntry((s2!=null?s2.getText():null));
            	      

            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);


            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:207:7: (lsArg1= listArgument |paramArg1= parameterArgument )?
            int alt18=3;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==34) ) {
                alt18=1;
            }
            else if ( (LA18_0==16) ) {
                alt18=2;
            }
            switch (alt18) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:207:8: lsArg1= listArgument
                    {
                    pushFollow(FOLLOW_listArgument_in_callStatement571);
                    lsArg1=listArgument();

                    state._fsp--;


                    currentEntry.setArgument(lsArg1);

                    }
                    break;
                case 2 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:207:66: paramArg1= parameterArgument
                    {
                    pushFollow(FOLLOW_parameterArgument_in_callStatement579);
                    paramArg1=parameterArgument();

                    state._fsp--;


                    currentEntry.setArgument(paramArg1);

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "callStatement"



    // $ANTLR start "listArgument"
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:209:1: listArgument returns [LsListArgument value] : '[' exp= expression ']' ;
    public final LsListArgument listArgument() throws RecognitionException {
        LsListArgument value = null;


        Expression exp =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:210:3: ( '[' exp= expression ']' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:210:5: '[' exp= expression ']'
            {
            value = new LsListArgument();

            match(input,34,FOLLOW_34_in_listArgument600); 

            pushFollow(FOLLOW_expression_in_listArgument604);
            exp=expression();

            state._fsp--;


            value.setExpression(exp); 

            match(input,35,FOLLOW_35_in_listArgument607); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "listArgument"



    // $ANTLR start "parameterArgument"
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:212:1: parameterArgument returns [ParameterArgument value] : '(' (exp1= expression ( ',' exp2= expression )* )? ')' ;
    public final ParameterArgument parameterArgument() throws RecognitionException {
        ParameterArgument value = null;


        Expression exp1 =null;

        Expression exp2 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:213:3: ( '(' (exp1= expression ( ',' exp2= expression )* )? ')' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:213:5: '(' (exp1= expression ( ',' exp2= expression )* )? ')'
            {
            value = new ParameterArgument(); 

            match(input,16,FOLLOW_16_in_parameterArgument623); 

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:213:46: (exp1= expression ( ',' exp2= expression )* )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( ((LA20_0 >= FLOAT && LA20_0 <= INTEGER)||LA20_0==STRING||LA20_0==12||LA20_0==16||LA20_0==19||LA20_0==21) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:213:47: exp1= expression ( ',' exp2= expression )*
                    {
                    pushFollow(FOLLOW_expression_in_parameterArgument628);
                    exp1=expression();

                    state._fsp--;


                    value.addExpression(exp1);

                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:213:93: ( ',' exp2= expression )*
                    loop19:
                    do {
                        int alt19=2;
                        int LA19_0 = input.LA(1);

                        if ( (LA19_0==20) ) {
                            alt19=1;
                        }


                        switch (alt19) {
                    	case 1 :
                    	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:213:94: ',' exp2= expression
                    	    {
                    	    match(input,20,FOLLOW_20_in_parameterArgument633); 

                    	    pushFollow(FOLLOW_expression_in_parameterArgument637);
                    	    exp2=expression();

                    	    state._fsp--;


                    	    value.addExpression(exp2);

                    	    }
                    	    break;

                    	default :
                    	    break loop19;
                        }
                    } while (true);


                    }
                    break;

            }


            match(input,17,FOLLOW_17_in_parameterArgument645); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "parameterArgument"



    // $ANTLR start "method"
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:215:1: method returns [MethodDefinition value] :t1= type name= IDENT '(' (param= IDENT )* ')' bl1= block ;
    public final MethodDefinition method() throws RecognitionException {
        MethodDefinition value = null;


        Token name=null;
        Token param=null;
        String t1 =null;

        Block bl1 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:216:3: (t1= type name= IDENT '(' (param= IDENT )* ')' bl1= block )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:216:5: t1= type name= IDENT '(' (param= IDENT )* ')' bl1= block
            {

            	  value = new MethodDefinition();
            	  value.setParent(currentBlock);
            	  currentBlock = value;
              

            pushFollow(FOLLOW_type_in_method665);
            t1=type();

            state._fsp--;



                value.setType(t1);
              

            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_method671); 


                value.setName((name!=null?name.getText():null));
              

            match(input,16,FOLLOW_16_in_method674); 

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:224:8: (param= IDENT )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==IDENT) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:224:9: param= IDENT
            	    {
            	    param=(Token)match(input,IDENT,FOLLOW_IDENT_in_method679); 


            	        value.addParameter((param!=null?param.getText():null));
            	      

            	    }
            	    break;

            	default :
            	    break loop21;
                }
            } while (true);


            match(input,17,FOLLOW_17_in_method686); 

            pushFollow(FOLLOW_block_in_method690);
            bl1=block();

            state._fsp--;


            value.getStatements().add(bl1);


                currentBlock = value.getParent();
              

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "method"



    // $ANTLR start "assignment"
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:230:1: assignment returns [Assignment value] : ( '=' exp= expression | '=' ls= list ) ;
    public final Assignment assignment() throws RecognitionException {
        Assignment value = null;


        Expression exp =null;

        LsList ls =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:231:3: ( ( '=' exp= expression | '=' ls= list ) )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:231:5: ( '=' exp= expression | '=' ls= list )
            {
            value = new Assignment();

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:231:34: ( '=' exp= expression | '=' ls= list )
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==28) ) {
                int LA22_1 = input.LA(2);

                if ( ((LA22_1 >= FLOAT && LA22_1 <= INTEGER)||LA22_1==STRING||LA22_1==12||LA22_1==16||LA22_1==19||LA22_1==21) ) {
                    alt22=1;
                }
                else if ( (LA22_1==34) ) {
                    alt22=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 22, 1, input);

                    throw nvae;

                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;

            }
            switch (alt22) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:231:35: '=' exp= expression
                    {
                    match(input,28,FOLLOW_28_in_assignment712); 

                    pushFollow(FOLLOW_expression_in_assignment716);
                    exp=expression();

                    state._fsp--;


                    value.setValue(exp);

                    }
                    break;
                case 2 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:231:80: '=' ls= list
                    {
                    match(input,28,FOLLOW_28_in_assignment722); 

                    pushFollow(FOLLOW_list_in_assignment726);
                    ls=list();

                    state._fsp--;


                    value.setValue(ls);

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "assignment"



    // $ANTLR start "list"
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:233:1: list returns [LsList value] : '[' key1= expression ( ':' val1= expression )? ( ',' key2= expression ( ':' val2= expression )? )* ']' ;
    public final LsList list() throws RecognitionException {
        LsList value = null;


        Expression key1 =null;

        Expression val1 =null;

        Expression key2 =null;

        Expression val2 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:234:3: ( '[' key1= expression ( ':' val1= expression )? ( ',' key2= expression ( ':' val2= expression )? )* ']' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:234:5: '[' key1= expression ( ':' val1= expression )? ( ',' key2= expression ( ':' val2= expression )? )* ']'
            {
            value = new LsList();

            match(input,34,FOLLOW_34_in_list747); 

            pushFollow(FOLLOW_expression_in_list751);
            key1=expression();

            state._fsp--;


            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:234:50: ( ':' val1= expression )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==24) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:234:51: ':' val1= expression
                    {
                    match(input,24,FOLLOW_24_in_list754); 

                    pushFollow(FOLLOW_expression_in_list758);
                    val1=expression();

                    state._fsp--;


                    }
                    break;

            }



                  if (val1 == null) {
                    value.setValueList(new java.util.ArrayList());
                    value.getValueList().add(key1);
            			} else {
            			  value.setValueMap(new java.util.HashMap());
            			  value.getValueMap().put(key1,val1);
                  }
                

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:242:7: ( ',' key2= expression ( ':' val2= expression )? )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( (LA25_0==20) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:242:8: ',' key2= expression ( ':' val2= expression )?
            	    {
            	    match(input,20,FOLLOW_20_in_list766); 

            	    pushFollow(FOLLOW_expression_in_list770);
            	    key2=expression();

            	    state._fsp--;


            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:242:28: ( ':' val2= expression )?
            	    int alt24=2;
            	    int LA24_0 = input.LA(1);

            	    if ( (LA24_0==24) ) {
            	        alt24=1;
            	    }
            	    switch (alt24) {
            	        case 1 :
            	            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:242:29: ':' val2= expression
            	            {
            	            match(input,24,FOLLOW_24_in_list773); 

            	            pushFollow(FOLLOW_expression_in_list777);
            	            val2=expression();

            	            state._fsp--;


            	            }
            	            break;

            	    }



            	          if (value.getValueList() != null) {
            	            if (val2 != null) {
            	              throw new FailedPredicateException(input, "list", "\n Expected [value] got [key:value].");
            	            }
            	            value.getValueList().add(key2);
            	          } else {
            	            if (val2 == null) {
            	              throw new FailedPredicateException(input, "list", "\n Expected [key:value] got [value].");
            	            }
            	            value.getValueMap().put(key2,val2);
            	          }
            	        

            	    }
            	    break;

            	default :
            	    break loop25;
                }
            } while (true);


            match(input,35,FOLLOW_35_in_list786); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "list"



    // $ANTLR start "value"
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:256:1: value returns [Object value] : ( INTEGER | FLOAT | STRING );
    public final Object value() throws RecognitionException {
        Object value = null;


        Token INTEGER6=null;
        Token FLOAT7=null;
        Token STRING8=null;

        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:257:3: ( INTEGER | FLOAT | STRING )
            int alt26=3;
            switch ( input.LA(1) ) {
            case INTEGER:
                {
                alt26=1;
                }
                break;
            case FLOAT:
                {
                alt26=2;
                }
                break;
            case STRING:
                {
                alt26=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 26, 0, input);

                throw nvae;

            }

            switch (alt26) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:257:5: INTEGER
                    {
                    INTEGER6=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_value801); 

                     value = new Integer((INTEGER6!=null?INTEGER6.getText():null));

                    }
                    break;
                case 2 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:258:5: FLOAT
                    {
                    FLOAT7=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_value810); 

                     value = new Float((FLOAT7!=null?FLOAT7.getText():null)); 

                    }
                    break;
                case 3 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:259:5: STRING
                    {
                    STRING8=(Token)match(input,STRING,FOLLOW_STRING_in_value818); 

                     value = (STRING8!=null?STRING8.getText():null); 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "value"



    // $ANTLR start "term"
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:261:1: term returns [Object value] : ( callStatement | '(' expression ')' | value );
    public final Object term() throws RecognitionException {
        Object value = null;


        CallStatement callStatement9 =null;

        Expression expression10 =null;

        Object value11 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:262:3: ( callStatement | '(' expression ')' | value )
            int alt27=3;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                alt27=1;
                }
                break;
            case 16:
                {
                alt27=2;
                }
                break;
            case FLOAT:
            case INTEGER:
            case STRING:
                {
                alt27=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 27, 0, input);

                throw nvae;

            }

            switch (alt27) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:262:5: callStatement
                    {
                    pushFollow(FOLLOW_callStatement_in_term834);
                    callStatement9=callStatement();

                    state._fsp--;


                    value = callStatement9;

                    }
                    break;
                case 2 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:263:5: '(' expression ')'
                    {
                    match(input,16,FOLLOW_16_in_term842); 

                    pushFollow(FOLLOW_expression_in_term844);
                    expression10=expression();

                    state._fsp--;


                    value = expression10;

                    match(input,17,FOLLOW_17_in_term848); 

                    }
                    break;
                case 3 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:264:5: value
                    {
                    pushFollow(FOLLOW_value_in_term854);
                    value11=value();

                    state._fsp--;


                    value = value11;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "term"



    // $ANTLR start "increment"
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:267:1: increment returns [IncrementStatement value] : (inc1= INCREMENTER )? term (inc2= INCREMENTER )? ;
    public final IncrementStatement increment() throws RecognitionException {
        IncrementStatement value = null;


        Token inc1=null;
        Token inc2=null;
        Object term12 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:268:3: ( (inc1= INCREMENTER )? term (inc2= INCREMENTER )? )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:268:5: (inc1= INCREMENTER )? term (inc2= INCREMENTER )?
            {

                  value = new IncrementStatement();
                

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:270:6: (inc1= INCREMENTER )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==INCREMENTER) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:270:7: inc1= INCREMENTER
                    {
                    inc1=(Token)match(input,INCREMENTER,FOLLOW_INCREMENTER_in_increment877); 


                          value.setOperation((inc1!=null?inc1.getText():null));
                        

                    }
                    break;

            }


            pushFollow(FOLLOW_term_in_increment883);
            term12=term();

            state._fsp--;


            value.setValue(term12);

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:272:46: (inc2= INCREMENTER )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==INCREMENTER) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:272:47: inc2= INCREMENTER
                    {
                    inc2=(Token)match(input,INCREMENTER,FOLLOW_INCREMENTER_in_increment890); 


                          value.setOperation((inc2!=null?inc2.getText():null));
                        

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "increment"



    // $ANTLR start "negation"
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:276:1: negation returns [NegationStatement value] : ( '!' )? inc= increment ;
    public final NegationStatement negation() throws RecognitionException {
        NegationStatement value = null;


        IncrementStatement inc =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:277:3: ( ( '!' )? inc= increment )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:277:5: ( '!' )? inc= increment
            {

                  value = new NegationStatement();
                

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:279:7: ( '!' )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==12) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:279:8: '!'
                    {
                    match(input,12,FOLLOW_12_in_negation913); 


                          value.setNegation(true);
                        

                    }
                    break;

            }


            pushFollow(FOLLOW_increment_in_negation921);
            inc=increment();

            state._fsp--;



                  value.setIncrement(inc);
                

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "negation"



    // $ANTLR start "unary"
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:285:1: unary returns [UnaryStatement value] : ( '+' | '-' )? neg= negation ;
    public final UnaryStatement unary() throws RecognitionException {
        UnaryStatement value = null;


        NegationStatement neg =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:286:3: ( ( '+' | '-' )? neg= negation )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:286:5: ( '+' | '-' )? neg= negation
            {

                  value = new UnaryStatement();
                

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:288:7: ( '+' | '-' )?
            int alt31=3;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==19) ) {
                alt31=1;
            }
            else if ( (LA31_0==21) ) {
                alt31=2;
            }
            switch (alt31) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:288:8: '+'
                    {
                    match(input,19,FOLLOW_19_in_unary940); 


                          value.setOperation("+");
                        

                    }
                    break;
                case 2 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:290:8: '-'
                    {
                    match(input,21,FOLLOW_21_in_unary945); 


                          value.setOperation("-");
                        

                    }
                    break;

            }


            pushFollow(FOLLOW_negation_in_unary952);
            neg=negation();

            state._fsp--;



                  value.setNegation(neg);
                

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "unary"



    // $ANTLR start "mult"
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:296:1: mult returns [MultStatement value] :u1= unary (op= ( '*' | '/' | '%' ) u2= unary )* ;
    public final MultStatement mult() throws RecognitionException {
        MultStatement value = null;


        Token op=null;
        UnaryStatement u1 =null;

        UnaryStatement u2 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:297:3: (u1= unary (op= ( '*' | '/' | '%' ) u2= unary )* )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:297:5: u1= unary (op= ( '*' | '/' | '%' ) u2= unary )*
            {

                  value = new MultStatement();
                

            pushFollow(FOLLOW_unary_in_mult972);
            u1=unary();

            state._fsp--;



                  value.setInitialValue(u1);
                

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:301:7: (op= ( '*' | '/' | '%' ) u2= unary )*
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( (LA32_0==14||LA32_0==18||LA32_0==23) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:301:9: op= ( '*' | '/' | '%' ) u2= unary
            	    {
            	    op=(Token)input.LT(1);

            	    if ( input.LA(1)==14||input.LA(1)==18||input.LA(1)==23 ) {
            	        input.consume();
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }


            	    pushFollow(FOLLOW_unary_in_mult994);
            	    u2=unary();

            	    state._fsp--;



            	          value.addBlock((op!=null?op.getText():null), u2);
            	        

            	    }
            	    break;

            	default :
            	    break loop32;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "mult"



    // $ANTLR start "add"
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:305:1: add returns [AddStatement value] :m1= mult (op= ( '+' | '-' ) m2= mult )* ;
    public final AddStatement add() throws RecognitionException {
        AddStatement value = null;


        Token op=null;
        MultStatement m1 =null;

        MultStatement m2 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:306:3: (m1= mult (op= ( '+' | '-' ) m2= mult )* )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:306:5: m1= mult (op= ( '+' | '-' ) m2= mult )*
            {

                  value = new AddStatement();
                

            pushFollow(FOLLOW_mult_in_add1017);
            m1=mult();

            state._fsp--;



                  value.setInitialValue(m1);
                

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:310:7: (op= ( '+' | '-' ) m2= mult )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( (LA33_0==19||LA33_0==21) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:310:9: op= ( '+' | '-' ) m2= mult
            	    {
            	    op=(Token)input.LT(1);

            	    if ( input.LA(1)==19||input.LA(1)==21 ) {
            	        input.consume();
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }


            	    pushFollow(FOLLOW_mult_in_add1035);
            	    m2=mult();

            	    state._fsp--;



            	          value.addBlockStatement((op!=null?op.getText():null), m2);

            	    }
            	    break;

            	default :
            	    break loop33;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "add"



    // $ANTLR start "relation"
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:313:1: relation returns [Relation value] :a1= add (op= ( '<' | '<=' | '>=' | '>' ) a2= add )? ;
    public final Relation relation() throws RecognitionException {
        Relation value = null;


        Token op=null;
        AddStatement a1 =null;

        AddStatement a2 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:314:3: (a1= add (op= ( '<' | '<=' | '>=' | '>' ) a2= add )? )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:314:5: a1= add (op= ( '<' | '<=' | '>=' | '>' ) a2= add )?
            {

                  value = new Relation();
                

            pushFollow(FOLLOW_add_in_relation1060);
            a1=add();

            state._fsp--;



                  value.setInitialValue(a1);
                

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:318:7: (op= ( '<' | '<=' | '>=' | '>' ) a2= add )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( ((LA34_0 >= 26 && LA34_0 <= 27)||(LA34_0 >= 30 && LA34_0 <= 31)) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:318:8: op= ( '<' | '<=' | '>=' | '>' ) a2= add
                    {
                    op=(Token)input.LT(1);

                    if ( (input.LA(1) >= 26 && input.LA(1) <= 27)||(input.LA(1) >= 30 && input.LA(1) <= 31) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                    pushFollow(FOLLOW_add_in_relation1086);
                    a2=add();

                    state._fsp--;



                          value.addBlock((op!=null?op.getText():null),a2);

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "relation"



    // $ANTLR start "comparison"
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:321:1: comparison returns [Comparison value] :rel1= relation (op= ( '==' | '!=' ) rel2= relation )* ;
    public final Comparison comparison() throws RecognitionException {
        Comparison value = null;


        Token op=null;
        Relation rel1 =null;

        Relation rel2 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:322:3: (rel1= relation (op= ( '==' | '!=' ) rel2= relation )* )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:322:5: rel1= relation (op= ( '==' | '!=' ) rel2= relation )*
            {

                  value = new Comparison();
                

            pushFollow(FOLLOW_relation_in_comparison1109);
            rel1=relation();

            state._fsp--;



                  value.setInitialValue(rel1);
                

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:326:7: (op= ( '==' | '!=' ) rel2= relation )*
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( (LA35_0==13||LA35_0==29) ) {
                    alt35=1;
                }


                switch (alt35) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:326:8: op= ( '==' | '!=' ) rel2= relation
            	    {
            	    op=(Token)input.LT(1);

            	    if ( input.LA(1)==13||input.LA(1)==29 ) {
            	        input.consume();
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }


            	    pushFollow(FOLLOW_relation_in_comparison1126);
            	    rel2=relation();

            	    state._fsp--;



            	          value.addBlock((op!=null?op.getText():null),rel2);
            	        

            	    }
            	    break;

            	default :
            	    break loop35;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "comparison"



    // $ANTLR start "expression"
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:330:1: expression returns [Expression value] :var1= comparison (op= ( '&&' | '||' ) var2= comparison )* ;
    public final Expression expression() throws RecognitionException {
        Expression value = null;


        Token op=null;
        Comparison var1 =null;

        Comparison var2 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:331:3: (var1= comparison (op= ( '&&' | '||' ) var2= comparison )* )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:331:5: var1= comparison (op= ( '&&' | '||' ) var2= comparison )*
            {
            value = new Expression();

            pushFollow(FOLLOW_comparison_in_expression1148);
            var1=comparison();

            state._fsp--;



                  value.setInitialValue(var1);
                

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:333:7: (op= ( '&&' | '||' ) var2= comparison )*
            loop36:
            do {
                int alt36=2;
                int LA36_0 = input.LA(1);

                if ( (LA36_0==15||LA36_0==53) ) {
                    alt36=1;
                }


                switch (alt36) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:333:8: op= ( '&&' | '||' ) var2= comparison
            	    {
            	    op=(Token)input.LT(1);

            	    if ( input.LA(1)==15||input.LA(1)==53 ) {
            	        input.consume();
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }


            	    pushFollow(FOLLOW_comparison_in_expression1166);
            	    var2=comparison();

            	    state._fsp--;



            	          value.addBlock((op!=null?op.getText():null),var2);

            	    }
            	    break;

            	default :
            	    break loop36;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "expression"



    // $ANTLR start "returnStatement"
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:336:1: returnStatement returns [ReturnStatement value] : 'return' exp= expression ;
    public final ReturnStatement returnStatement() throws RecognitionException {
        ReturnStatement value = null;


        Expression exp =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:337:3: ( 'return' exp= expression )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:337:5: 'return' exp= expression
            {
            match(input,49,FOLLOW_49_in_returnStatement1187); 

            pushFollow(FOLLOW_expression_in_returnStatement1191);
            exp=expression();

            state._fsp--;


            value = new ReturnStatement(exp);

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "returnStatement"



    // $ANTLR start "type"
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:339:1: type returns [String value] : ( 'void' | 'int' | 'long' | 'double' | 'float' | 'char' | 'byte' | 'String' | 'def' );
    public final String type() throws RecognitionException {
        String value = null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:340:3: ( 'void' | 'int' | 'long' | 'double' | 'float' | 'char' | 'byte' | 'String' | 'def' )
            int alt37=9;
            switch ( input.LA(1) ) {
            case 50:
                {
                alt37=1;
                }
                break;
            case 47:
                {
                alt37=2;
                }
                break;
            case 48:
                {
                alt37=3;
                }
                break;
            case 41:
                {
                alt37=4;
                }
                break;
            case 43:
                {
                alt37=5;
                }
                break;
            case 38:
                {
                alt37=6;
                }
                break;
            case 37:
                {
                alt37=7;
                }
                break;
            case 33:
                {
                alt37=8;
                }
                break;
            case 40:
                {
                alt37=9;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 37, 0, input);

                throw nvae;

            }

            switch (alt37) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:340:5: 'void'
                    {
                    match(input,50,FOLLOW_50_in_type1209); 

                    value = Types.VOID;

                    }
                    break;
                case 2 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:341:5: 'int'
                    {
                    match(input,47,FOLLOW_47_in_type1217); 

                    value = Types.INT;

                    }
                    break;
                case 3 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:342:5: 'long'
                    {
                    match(input,48,FOLLOW_48_in_type1225); 

                    value = Types.LONG;

                    }
                    break;
                case 4 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:343:5: 'double'
                    {
                    match(input,41,FOLLOW_41_in_type1233); 

                    value = Types.DOUBLE;

                    }
                    break;
                case 5 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:344:5: 'float'
                    {
                    match(input,43,FOLLOW_43_in_type1241); 

                    value = Types.FLOAT;

                    }
                    break;
                case 6 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:345:5: 'char'
                    {
                    match(input,38,FOLLOW_38_in_type1249); 

                    value = Types.CHAR;

                    }
                    break;
                case 7 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:346:5: 'byte'
                    {
                    match(input,37,FOLLOW_37_in_type1257); 

                    value = Types.BYTE;

                    }
                    break;
                case 8 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:347:5: 'String'
                    {
                    match(input,33,FOLLOW_33_in_type1265); 

                    value = Types.STRING;

                    }
                    break;
                case 9 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:348:5: 'def'
                    {
                    match(input,40,FOLLOW_40_in_type1273); 

                    value = Types.DEF;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "type"

    // Delegated rules


 

    public static final BitSet FOLLOW_annotation_in_workflow66 = new BitSet(new long[]{0x0000100100000000L});
    public static final BitSet FOLLOW_44_in_workflow76 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_workflow80 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_workflow87 = new BitSet(new long[]{0x00158B6200000000L});
    public static final BitSet FOLLOW_variable_in_workflow93 = new BitSet(new long[]{0x00158B6210000000L});
    public static final BitSet FOLLOW_assignment_in_workflow96 = new BitSet(new long[]{0x00158B6200000000L});
    public static final BitSet FOLLOW_method_in_workflow113 = new BitSet(new long[]{0x00158B6200000000L});
    public static final BitSet FOLLOW_block_in_workflow126 = new BitSet(new long[]{0x00458B6200000000L});
    public static final BitSet FOLLOW_method_in_workflow137 = new BitSet(new long[]{0x00458B6200000000L});
    public static final BitSet FOLLOW_54_in_workflow146 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_annotation165 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_annotation169 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_annotation173 = new BitSet(new long[]{0x0000000000020400L});
    public static final BitSet FOLLOW_STRING_in_annotation176 = new BitSet(new long[]{0x0000000000020400L});
    public static final BitSet FOLLOW_17_in_annotation181 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_callStatement_in_statement205 = new BitSet(new long[]{0x0000000012000002L});
    public static final BitSet FOLLOW_assignment_in_statement212 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_25_in_statement218 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_variable_in_statement228 = new BitSet(new long[]{0x0000000012000002L});
    public static final BitSet FOLLOW_assignment_in_statement235 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_25_in_statement241 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ifStatement_in_statement251 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_whileStatement_in_statement261 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_forStatement_in_statement271 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_block_in_statement284 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_returnStatement_in_statement294 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_statement302 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_36_in_statement310 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_52_in_block328 = new BitSet(new long[]{0x005FEBF200000040L});
    public static final BitSet FOLLOW_statement_in_block333 = new BitSet(new long[]{0x005FEBF200000040L});
    public static final BitSet FOLLOW_54_in_block340 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_ifStatement363 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_ifStatement365 = new BitSet(new long[]{0x00000000002915E0L});
    public static final BitSet FOLLOW_expression_in_ifStatement369 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_ifStatement371 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_block_in_ifStatement375 = new BitSet(new long[]{0x0000040000000002L});
    public static final BitSet FOLLOW_42_in_ifStatement384 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_46_in_ifStatement386 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_ifStatement388 = new BitSet(new long[]{0x00000000002915E0L});
    public static final BitSet FOLLOW_expression_in_ifStatement392 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_ifStatement394 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_block_in_ifStatement398 = new BitSet(new long[]{0x0000040000000002L});
    public static final BitSet FOLLOW_42_in_ifStatement409 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_block_in_ifStatement413 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_51_in_whileStatement432 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_whileStatement434 = new BitSet(new long[]{0x00000000002915E0L});
    public static final BitSet FOLLOW_expression_in_whileStatement438 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_whileStatement440 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_block_in_whileStatement444 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_45_in_forStatement461 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_forStatement463 = new BitSet(new long[]{0x00058B6202000000L});
    public static final BitSet FOLLOW_variable_in_forStatement468 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_assignment_in_forStatement472 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_forStatement479 = new BitSet(new long[]{0x00000000002915E0L});
    public static final BitSet FOLLOW_expression_in_forStatement483 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_forStatement487 = new BitSet(new long[]{0x00000000002B15E0L});
    public static final BitSet FOLLOW_expression_in_forStatement494 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_forStatement500 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_block_in_forStatement504 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_variable529 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_variable531 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_callStatement553 = new BitSet(new long[]{0x0000000400410002L});
    public static final BitSet FOLLOW_22_in_callStatement558 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_callStatement562 = new BitSet(new long[]{0x0000000400410002L});
    public static final BitSet FOLLOW_listArgument_in_callStatement571 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_parameterArgument_in_callStatement579 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_listArgument600 = new BitSet(new long[]{0x00000000002915E0L});
    public static final BitSet FOLLOW_expression_in_listArgument604 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_listArgument607 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_parameterArgument623 = new BitSet(new long[]{0x00000000002B15E0L});
    public static final BitSet FOLLOW_expression_in_parameterArgument628 = new BitSet(new long[]{0x0000000000120000L});
    public static final BitSet FOLLOW_20_in_parameterArgument633 = new BitSet(new long[]{0x00000000002915E0L});
    public static final BitSet FOLLOW_expression_in_parameterArgument637 = new BitSet(new long[]{0x0000000000120000L});
    public static final BitSet FOLLOW_17_in_parameterArgument645 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_method665 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_method671 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_method674 = new BitSet(new long[]{0x0000000000020040L});
    public static final BitSet FOLLOW_IDENT_in_method679 = new BitSet(new long[]{0x0000000000020040L});
    public static final BitSet FOLLOW_17_in_method686 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_block_in_method690 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_assignment712 = new BitSet(new long[]{0x00000000002915E0L});
    public static final BitSet FOLLOW_expression_in_assignment716 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_assignment722 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_list_in_assignment726 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_list747 = new BitSet(new long[]{0x00000000002915E0L});
    public static final BitSet FOLLOW_expression_in_list751 = new BitSet(new long[]{0x0000000801100000L});
    public static final BitSet FOLLOW_24_in_list754 = new BitSet(new long[]{0x00000000002915E0L});
    public static final BitSet FOLLOW_expression_in_list758 = new BitSet(new long[]{0x0000000800100000L});
    public static final BitSet FOLLOW_20_in_list766 = new BitSet(new long[]{0x00000000002915E0L});
    public static final BitSet FOLLOW_expression_in_list770 = new BitSet(new long[]{0x0000000801100000L});
    public static final BitSet FOLLOW_24_in_list773 = new BitSet(new long[]{0x00000000002915E0L});
    public static final BitSet FOLLOW_expression_in_list777 = new BitSet(new long[]{0x0000000800100000L});
    public static final BitSet FOLLOW_35_in_list786 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_value801 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_in_value810 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_value818 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_callStatement_in_term834 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_term842 = new BitSet(new long[]{0x00000000002915E0L});
    public static final BitSet FOLLOW_expression_in_term844 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_term848 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_value_in_term854 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INCREMENTER_in_increment877 = new BitSet(new long[]{0x0000000000010560L});
    public static final BitSet FOLLOW_term_in_increment883 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_INCREMENTER_in_increment890 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_negation913 = new BitSet(new long[]{0x00000000000105E0L});
    public static final BitSet FOLLOW_increment_in_negation921 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_unary940 = new BitSet(new long[]{0x00000000000115E0L});
    public static final BitSet FOLLOW_21_in_unary945 = new BitSet(new long[]{0x00000000000115E0L});
    public static final BitSet FOLLOW_negation_in_unary952 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unary_in_mult972 = new BitSet(new long[]{0x0000000000844002L});
    public static final BitSet FOLLOW_set_in_mult980 = new BitSet(new long[]{0x00000000002915E0L});
    public static final BitSet FOLLOW_unary_in_mult994 = new BitSet(new long[]{0x0000000000844002L});
    public static final BitSet FOLLOW_mult_in_add1017 = new BitSet(new long[]{0x0000000000280002L});
    public static final BitSet FOLLOW_set_in_add1025 = new BitSet(new long[]{0x00000000002915E0L});
    public static final BitSet FOLLOW_mult_in_add1035 = new BitSet(new long[]{0x0000000000280002L});
    public static final BitSet FOLLOW_add_in_relation1060 = new BitSet(new long[]{0x00000000CC000002L});
    public static final BitSet FOLLOW_set_in_relation1067 = new BitSet(new long[]{0x00000000002915E0L});
    public static final BitSet FOLLOW_add_in_relation1086 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_relation_in_comparison1109 = new BitSet(new long[]{0x0000000020002002L});
    public static final BitSet FOLLOW_set_in_comparison1116 = new BitSet(new long[]{0x00000000002915E0L});
    public static final BitSet FOLLOW_relation_in_comparison1126 = new BitSet(new long[]{0x0000000020002002L});
    public static final BitSet FOLLOW_comparison_in_expression1148 = new BitSet(new long[]{0x0020000000008002L});
    public static final BitSet FOLLOW_set_in_expression1155 = new BitSet(new long[]{0x00000000002915E0L});
    public static final BitSet FOLLOW_comparison_in_expression1166 = new BitSet(new long[]{0x0020000000008002L});
    public static final BitSet FOLLOW_49_in_returnStatement1187 = new BitSet(new long[]{0x00000000002915E0L});
    public static final BitSet FOLLOW_expression_in_returnStatement1191 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_50_in_type1209 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_47_in_type1217 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_48_in_type1225 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_41_in_type1233 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_43_in_type1241 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_38_in_type1249 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_type1257 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_type1265 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_40_in_type1273 = new BitSet(new long[]{0x0000000000000002L});

}