// $ANTLR 3.4 /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g 2012-06-05 09:01:40

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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:130:1: statement returns [Statement value] : (varCall= callStatement (ass1= assignment )? ( ';' )? |var= variable (ass2= assignment )? ( ';' )? | incrementStatement ';' |ifStat= ifStatement |whileStat= whileStatement |forStat= forStatement |bl= block |ret= returnStatement | 'continue' | 'break' ) ;
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
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:131:3: ( (varCall= callStatement (ass1= assignment )? ( ';' )? |var= variable (ass2= assignment )? ( ';' )? | incrementStatement ';' |ifStat= ifStatement |whileStat= whileStatement |forStat= forStatement |bl= block |ret= returnStatement | 'continue' | 'break' ) )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:131:5: (varCall= callStatement (ass1= assignment )? ( ';' )? |var= variable (ass2= assignment )? ( ';' )? | incrementStatement ';' |ifStat= ifStatement |whileStat= whileStatement |forStat= forStatement |bl= block |ret= returnStatement | 'continue' | 'break' )
            {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:131:5: (varCall= callStatement (ass1= assignment )? ( ';' )? |var= variable (ass2= assignment )? ( ';' )? | incrementStatement ';' |ifStat= ifStatement |whileStat= whileStatement |forStat= forStatement |bl= block |ret= returnStatement | 'continue' | 'break' )
            int alt11=10;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                int LA11_1 = input.LA(2);

                if ( (LA11_1==IDENT||LA11_1==16||LA11_1==22||LA11_1==25||LA11_1==28||(LA11_1 >= 33 && LA11_1 <= 34)||(LA11_1 >= 36 && LA11_1 <= 41)||LA11_1==43||(LA11_1 >= 45 && LA11_1 <= 52)||LA11_1==54) ) {
                    alt11=1;
                }
                else if ( (LA11_1==INCREMENTER) ) {
                    int LA11_12 = input.LA(3);

                    if ( (LA11_12==IDENT) ) {
                        alt11=1;
                    }
                    else if ( (LA11_12==25) ) {
                        alt11=3;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 11, 12, input);

                        throw nvae;

                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 11, 1, input);

                    throw nvae;

                }
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
            case INCREMENTER:
                {
                alt11=3;
                }
                break;
            case 46:
                {
                alt11=4;
                }
                break;
            case 51:
                {
                alt11=5;
                }
                break;
            case 45:
                {
                alt11=6;
                }
                break;
            case 52:
                {
                alt11=7;
                }
                break;
            case 49:
                {
                alt11=8;
                }
                break;
            case 39:
                {
                alt11=9;
                }
                break;
            case 36:
                {
                alt11=10;
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
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:134:5: incrementStatement ';'
                    {
                    pushFollow(FOLLOW_incrementStatement_in_statement248);
                    incrementStatement();

                    state._fsp--;


                    match(input,25,FOLLOW_25_in_statement250); 

                    }
                    break;
                case 4 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:135:5: ifStat= ifStatement
                    {
                    pushFollow(FOLLOW_ifStatement_in_statement258);
                    ifStat=ifStatement();

                    state._fsp--;


                    value = ifStat;

                    }
                    break;
                case 5 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:136:5: whileStat= whileStatement
                    {
                    pushFollow(FOLLOW_whileStatement_in_statement268);
                    whileStat=whileStatement();

                    state._fsp--;


                    value = whileStat;

                    }
                    break;
                case 6 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:137:5: forStat= forStatement
                    {
                    pushFollow(FOLLOW_forStatement_in_statement278);
                    forStat=forStatement();

                    state._fsp--;


                    value = forStat;

                    }
                    break;
                case 7 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:139:5: bl= block
                    {
                    pushFollow(FOLLOW_block_in_statement291);
                    bl=block();

                    state._fsp--;


                    value = bl;

                    }
                    break;
                case 8 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:140:5: ret= returnStatement
                    {
                    pushFollow(FOLLOW_returnStatement_in_statement301);
                    ret=returnStatement();

                    state._fsp--;


                    value = ret;

                    }
                    break;
                case 9 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:141:5: 'continue'
                    {
                    match(input,39,FOLLOW_39_in_statement309); 

                    value = new ContinueStatement(); 

                    }
                    break;
                case 10 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:142:5: 'break'
                    {
                    match(input,36,FOLLOW_36_in_statement317); 

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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:144:1: block returns [Block value] : '{' ( statement )* '}' ;
    public final Block block() throws RecognitionException {
        Block value = null;


        Statement statement4 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:145:3: ( '{' ( statement )* '}' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:145:5: '{' ( statement )* '}'
            {
            match(input,52,FOLLOW_52_in_block335); 


                  value = new Block();
                  value.setParent(currentBlock);
                  currentBlock = value;
                  

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:149:9: ( statement )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( ((LA12_0 >= IDENT && LA12_0 <= INCREMENTER)||LA12_0==33||(LA12_0 >= 36 && LA12_0 <= 41)||LA12_0==43||(LA12_0 >= 45 && LA12_0 <= 52)) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:149:10: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_block340);
            	    statement4=statement();

            	    state._fsp--;


            	     value.getStatements().add(statement4);

            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);


            match(input,54,FOLLOW_54_in_block347); 


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



    // $ANTLR start "incrementStatement"
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:154:1: incrementStatement returns [IncrementStatement value] : (inc1= INCREMENTER ident1= IDENT |ident2= IDENT inc2= INCREMENTER ) ;
    public final IncrementStatement incrementStatement() throws RecognitionException {
        IncrementStatement value = null;


        Token inc1=null;
        Token ident1=null;
        Token ident2=null;
        Token inc2=null;

        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:155:3: ( (inc1= INCREMENTER ident1= IDENT |ident2= IDENT inc2= INCREMENTER ) )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:155:5: (inc1= INCREMENTER ident1= IDENT |ident2= IDENT inc2= INCREMENTER )
            {

                  value = new IncrementStatement();
                

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:157:6: (inc1= INCREMENTER ident1= IDENT |ident2= IDENT inc2= INCREMENTER )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==INCREMENTER) ) {
                alt13=1;
            }
            else if ( (LA13_0==IDENT) ) {
                alt13=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;

            }
            switch (alt13) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:157:7: inc1= INCREMENTER ident1= IDENT
                    {
                    inc1=(Token)match(input,INCREMENTER,FOLLOW_INCREMENTER_in_incrementStatement373); 


                          value.setOperation((inc1!=null?inc1.getText():null));
                        

                    ident1=(Token)match(input,IDENT,FOLLOW_IDENT_in_incrementStatement379); 

                    value.setVariable((ident1!=null?ident1.getText():null));

                    }
                    break;
                case 2 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:159:58: ident2= IDENT inc2= INCREMENTER
                    {
                    ident2=(Token)match(input,IDENT,FOLLOW_IDENT_in_incrementStatement387); 


                          value.setVariable((ident2!=null?ident2.getText():null));

                    inc2=(Token)match(input,INCREMENTER,FOLLOW_INCREMENTER_in_incrementStatement393); 


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
    // $ANTLR end "incrementStatement"



    // $ANTLR start "ifStatement"
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:165:1: ifStatement returns [IfStatement value] : 'if' '(' exp1= expression ')' bl1= block ( 'else' 'if' '(' exp2= expression ')' bl2= block )* ( 'else' bl3= block )? ;
    public final IfStatement ifStatement() throws RecognitionException {
        IfStatement value = null;


        Expression exp1 =null;

        Block bl1 =null;

        Expression exp2 =null;

        Block bl2 =null;

        Block bl3 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:166:3: ( 'if' '(' exp1= expression ')' bl1= block ( 'else' 'if' '(' exp2= expression ')' bl2= block )* ( 'else' bl3= block )? )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:166:5: 'if' '(' exp1= expression ')' bl1= block ( 'else' 'if' '(' exp2= expression ')' bl2= block )* ( 'else' bl3= block )?
            {
            value = new IfStatement();

            match(input,46,FOLLOW_46_in_ifStatement412); 

            match(input,16,FOLLOW_16_in_ifStatement414); 

            pushFollow(FOLLOW_expression_in_ifStatement418);
            exp1=expression();

            state._fsp--;


            match(input,17,FOLLOW_17_in_ifStatement420); 

            pushFollow(FOLLOW_block_in_ifStatement424);
            bl1=block();

            state._fsp--;



                  value.addBlock(IfStatement.IfStatementType.IF, exp1, bl1); 

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:168:5: ( 'else' 'if' '(' exp2= expression ')' bl2= block )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==42) ) {
                    int LA14_1 = input.LA(2);

                    if ( (LA14_1==46) ) {
                        alt14=1;
                    }


                }


                switch (alt14) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:168:6: 'else' 'if' '(' exp2= expression ')' bl2= block
            	    {
            	    match(input,42,FOLLOW_42_in_ifStatement433); 

            	    match(input,46,FOLLOW_46_in_ifStatement435); 

            	    match(input,16,FOLLOW_16_in_ifStatement437); 

            	    pushFollow(FOLLOW_expression_in_ifStatement441);
            	    exp2=expression();

            	    state._fsp--;


            	    match(input,17,FOLLOW_17_in_ifStatement443); 

            	    pushFollow(FOLLOW_block_in_ifStatement447);
            	    bl2=block();

            	    state._fsp--;



            	          value.addBlock(IfStatement.IfStatementType.ELSE_IF, exp2, bl2); 

            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);


            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:170:5: ( 'else' bl3= block )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==42) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:170:6: 'else' bl3= block
                    {
                    match(input,42,FOLLOW_42_in_ifStatement458); 

                    pushFollow(FOLLOW_block_in_ifStatement462);
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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:173:1: whileStatement returns [WhileStatement value] : 'while' '(' exp1= expression ')' bl1= block ;
    public final WhileStatement whileStatement() throws RecognitionException {
        WhileStatement value = null;


        Expression exp1 =null;

        Block bl1 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:174:3: ( 'while' '(' exp1= expression ')' bl1= block )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:174:5: 'while' '(' exp1= expression ')' bl1= block
            {
            value = new WhileStatement();

            match(input,51,FOLLOW_51_in_whileStatement481); 

            match(input,16,FOLLOW_16_in_whileStatement483); 

            pushFollow(FOLLOW_expression_in_whileStatement487);
            exp1=expression();

            state._fsp--;


            match(input,17,FOLLOW_17_in_whileStatement489); 

            pushFollow(FOLLOW_block_in_whileStatement493);
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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:178:1: forStatement returns [ForStatement value] : 'for' '(' (var= variable ass1= assignment )? ';' exp1= expression ';' (inc= incrementStatement |varCall= callStatement ass1= assignment )? ')' bl= block ;
    public final ForStatement forStatement() throws RecognitionException {
        ForStatement value = null;


        Variable var =null;

        Assignment ass1 =null;

        Expression exp1 =null;

        IncrementStatement inc =null;

        CallStatement varCall =null;

        Block bl =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:179:3: ( 'for' '(' (var= variable ass1= assignment )? ';' exp1= expression ';' (inc= incrementStatement |varCall= callStatement ass1= assignment )? ')' bl= block )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:179:5: 'for' '(' (var= variable ass1= assignment )? ';' exp1= expression ';' (inc= incrementStatement |varCall= callStatement ass1= assignment )? ')' bl= block
            {

              value = new ForStatement();
              value.setParent(currentBlock);
              currentBlock = value;

            match(input,45,FOLLOW_45_in_forStatement510); 

            match(input,16,FOLLOW_16_in_forStatement512); 

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:182:36: (var= variable ass1= assignment )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==33||(LA16_0 >= 37 && LA16_0 <= 38)||(LA16_0 >= 40 && LA16_0 <= 41)||LA16_0==43||(LA16_0 >= 47 && LA16_0 <= 48)||LA16_0==50) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:182:37: var= variable ass1= assignment
                    {
                    pushFollow(FOLLOW_variable_in_forStatement517);
                    var=variable();

                    state._fsp--;


                    pushFollow(FOLLOW_assignment_in_forStatement521);
                    ass1=assignment();

                    state._fsp--;



                      var.setInitialValue(ass1);
                      value.setInitialValue(var);
                      

                    }
                    break;

            }


            match(input,25,FOLLOW_25_in_forStatement528); 

            pushFollow(FOLLOW_expression_in_forStatement532);
            exp1=expression();

            state._fsp--;



              value.setComparison(exp1);
              

            match(input,25,FOLLOW_25_in_forStatement536); 

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:187:10: (inc= incrementStatement |varCall= callStatement ass1= assignment )?
            int alt17=3;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==INCREMENTER) ) {
                alt17=1;
            }
            else if ( (LA17_0==IDENT) ) {
                int LA17_2 = input.LA(2);

                if ( (LA17_2==INCREMENTER) ) {
                    alt17=1;
                }
                else if ( (LA17_2==16||LA17_2==22||LA17_2==28||LA17_2==34) ) {
                    alt17=2;
                }
            }
            switch (alt17) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:187:11: inc= incrementStatement
                    {
                    pushFollow(FOLLOW_incrementStatement_in_forStatement542);
                    inc=incrementStatement();

                    state._fsp--;



                        value.setIncrement(inc);
                      

                    }
                    break;
                case 2 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:189:7: varCall= callStatement ass1= assignment
                    {
                    pushFollow(FOLLOW_callStatement_in_forStatement550);
                    varCall=callStatement();

                    state._fsp--;


                    pushFollow(FOLLOW_assignment_in_forStatement554);
                    ass1=assignment();

                    state._fsp--;



                        varCall.setAssignment(ass1);
                        value.setCall(varCall);
                      

                    }
                    break;

            }


            match(input,17,FOLLOW_17_in_forStatement561); 

            pushFollow(FOLLOW_block_in_forStatement565);
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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:208:1: variable returns [Variable value] :t1= type IDENT ;
    public final Variable variable() throws RecognitionException {
        Variable value = null;


        Token IDENT5=null;
        String t1 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:209:3: (t1= type IDENT )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:209:5: t1= type IDENT
            {
            value = new Variable();

            pushFollow(FOLLOW_type_in_variable590);
            t1=type();

            state._fsp--;


            IDENT5=(Token)match(input,IDENT,FOLLOW_IDENT_in_variable592); 


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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:214:1: callStatement returns [CallStatement value] :s1= IDENT ( '.' s2= IDENT )* (lsArg1= listArgument |paramArg1= parameterArgument )? ;
    public final CallStatement callStatement() throws RecognitionException {
        CallStatement value = null;


        Token s1=null;
        Token s2=null;
        LsListArgument lsArg1 =null;

        ParameterArgument paramArg1 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:215:3: (s1= IDENT ( '.' s2= IDENT )* (lsArg1= listArgument |paramArg1= parameterArgument )? )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:215:5: s1= IDENT ( '.' s2= IDENT )* (lsArg1= listArgument |paramArg1= parameterArgument )?
            {

                value = new CallStatement();
                CallStatement.CallStatementEntry currentEntry = null;
              

            s1=(Token)match(input,IDENT,FOLLOW_IDENT_in_callStatement614); 


                currentEntry = value.addEntry((s1!=null?s1.getText():null));
              

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:220:5: ( '.' s2= IDENT )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==22) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:220:6: '.' s2= IDENT
            	    {
            	    match(input,22,FOLLOW_22_in_callStatement619); 

            	    s2=(Token)match(input,IDENT,FOLLOW_IDENT_in_callStatement623); 


            	        currentEntry = value.addEntry((s2!=null?s2.getText():null));
            	      

            	    }
            	    break;

            	default :
            	    break loop18;
                }
            } while (true);


            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:222:7: (lsArg1= listArgument |paramArg1= parameterArgument )?
            int alt19=3;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==34) ) {
                alt19=1;
            }
            else if ( (LA19_0==16) ) {
                alt19=2;
            }
            switch (alt19) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:222:8: lsArg1= listArgument
                    {
                    pushFollow(FOLLOW_listArgument_in_callStatement632);
                    lsArg1=listArgument();

                    state._fsp--;


                    currentEntry.setArgument(lsArg1);

                    }
                    break;
                case 2 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:222:66: paramArg1= parameterArgument
                    {
                    pushFollow(FOLLOW_parameterArgument_in_callStatement640);
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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:224:1: listArgument returns [LsListArgument value] : '[' exp= expression ']' ;
    public final LsListArgument listArgument() throws RecognitionException {
        LsListArgument value = null;


        Expression exp =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:225:3: ( '[' exp= expression ']' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:225:5: '[' exp= expression ']'
            {
            value = new LsListArgument();

            match(input,34,FOLLOW_34_in_listArgument661); 

            pushFollow(FOLLOW_expression_in_listArgument665);
            exp=expression();

            state._fsp--;


            value.setExpression(exp); 

            match(input,35,FOLLOW_35_in_listArgument668); 

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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:227:1: parameterArgument returns [ParameterArgument value] : '(' (exp1= expression ( ',' exp2= expression )* )? ')' ;
    public final ParameterArgument parameterArgument() throws RecognitionException {
        ParameterArgument value = null;


        Expression exp1 =null;

        Expression exp2 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:228:3: ( '(' (exp1= expression ( ',' exp2= expression )* )? ')' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:228:5: '(' (exp1= expression ( ',' exp2= expression )* )? ')'
            {
            value = new ParameterArgument(); 

            match(input,16,FOLLOW_16_in_parameterArgument684); 

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:228:46: (exp1= expression ( ',' exp2= expression )* )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( ((LA21_0 >= FLOAT && LA21_0 <= IDENT)||LA21_0==INTEGER||LA21_0==STRING||LA21_0==12||LA21_0==16||LA21_0==19||LA21_0==21) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:228:47: exp1= expression ( ',' exp2= expression )*
                    {
                    pushFollow(FOLLOW_expression_in_parameterArgument689);
                    exp1=expression();

                    state._fsp--;


                    value.addExpression(exp1);

                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:228:93: ( ',' exp2= expression )*
                    loop20:
                    do {
                        int alt20=2;
                        int LA20_0 = input.LA(1);

                        if ( (LA20_0==20) ) {
                            alt20=1;
                        }


                        switch (alt20) {
                    	case 1 :
                    	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:228:94: ',' exp2= expression
                    	    {
                    	    match(input,20,FOLLOW_20_in_parameterArgument694); 

                    	    pushFollow(FOLLOW_expression_in_parameterArgument698);
                    	    exp2=expression();

                    	    state._fsp--;


                    	    value.addExpression(exp2);

                    	    }
                    	    break;

                    	default :
                    	    break loop20;
                        }
                    } while (true);


                    }
                    break;

            }


            match(input,17,FOLLOW_17_in_parameterArgument706); 

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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:230:1: method returns [MethodDefinition value] :t1= type name= IDENT '(' (param= IDENT )* ')' bl1= block ;
    public final MethodDefinition method() throws RecognitionException {
        MethodDefinition value = null;


        Token name=null;
        Token param=null;
        String t1 =null;

        Block bl1 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:231:3: (t1= type name= IDENT '(' (param= IDENT )* ')' bl1= block )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:231:5: t1= type name= IDENT '(' (param= IDENT )* ')' bl1= block
            {

            	  value = new MethodDefinition();
            	  value.setParent(currentBlock);
            	  currentBlock = value;
              

            pushFollow(FOLLOW_type_in_method726);
            t1=type();

            state._fsp--;



                value.setType(t1);
              

            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_method732); 


                value.setName((name!=null?name.getText():null));
              

            match(input,16,FOLLOW_16_in_method735); 

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:239:8: (param= IDENT )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==IDENT) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:239:9: param= IDENT
            	    {
            	    param=(Token)match(input,IDENT,FOLLOW_IDENT_in_method740); 


            	        value.addParameter((param!=null?param.getText():null));
            	      

            	    }
            	    break;

            	default :
            	    break loop22;
                }
            } while (true);


            match(input,17,FOLLOW_17_in_method747); 

            pushFollow(FOLLOW_block_in_method751);
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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:245:1: assignment returns [Assignment value] : ( '=' exp= expression | '=' ls= list ) ;
    public final Assignment assignment() throws RecognitionException {
        Assignment value = null;


        Expression exp =null;

        LsList ls =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:246:3: ( ( '=' exp= expression | '=' ls= list ) )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:246:5: ( '=' exp= expression | '=' ls= list )
            {
            value = new Assignment();

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:246:34: ( '=' exp= expression | '=' ls= list )
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==28) ) {
                int LA23_1 = input.LA(2);

                if ( ((LA23_1 >= FLOAT && LA23_1 <= IDENT)||LA23_1==INTEGER||LA23_1==STRING||LA23_1==12||LA23_1==16||LA23_1==19||LA23_1==21) ) {
                    alt23=1;
                }
                else if ( (LA23_1==34) ) {
                    alt23=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 23, 1, input);

                    throw nvae;

                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;

            }
            switch (alt23) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:246:35: '=' exp= expression
                    {
                    match(input,28,FOLLOW_28_in_assignment773); 

                    pushFollow(FOLLOW_expression_in_assignment777);
                    exp=expression();

                    state._fsp--;


                    value.setValue(exp);

                    }
                    break;
                case 2 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:246:80: '=' ls= list
                    {
                    match(input,28,FOLLOW_28_in_assignment783); 

                    pushFollow(FOLLOW_list_in_assignment787);
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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:248:1: list returns [LsList value] : '[' key1= expression ( ':' val1= expression )? ( ',' key2= expression ( ':' val2= expression )? )* ']' ;
    public final LsList list() throws RecognitionException {
        LsList value = null;


        Expression key1 =null;

        Expression val1 =null;

        Expression key2 =null;

        Expression val2 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:249:3: ( '[' key1= expression ( ':' val1= expression )? ( ',' key2= expression ( ':' val2= expression )? )* ']' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:249:5: '[' key1= expression ( ':' val1= expression )? ( ',' key2= expression ( ':' val2= expression )? )* ']'
            {
            value = new LsList();

            match(input,34,FOLLOW_34_in_list808); 

            pushFollow(FOLLOW_expression_in_list812);
            key1=expression();

            state._fsp--;


            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:249:50: ( ':' val1= expression )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==24) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:249:51: ':' val1= expression
                    {
                    match(input,24,FOLLOW_24_in_list815); 

                    pushFollow(FOLLOW_expression_in_list819);
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
                

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:257:7: ( ',' key2= expression ( ':' val2= expression )? )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( (LA26_0==20) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:257:8: ',' key2= expression ( ':' val2= expression )?
            	    {
            	    match(input,20,FOLLOW_20_in_list827); 

            	    pushFollow(FOLLOW_expression_in_list831);
            	    key2=expression();

            	    state._fsp--;


            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:257:28: ( ':' val2= expression )?
            	    int alt25=2;
            	    int LA25_0 = input.LA(1);

            	    if ( (LA25_0==24) ) {
            	        alt25=1;
            	    }
            	    switch (alt25) {
            	        case 1 :
            	            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:257:29: ':' val2= expression
            	            {
            	            match(input,24,FOLLOW_24_in_list834); 

            	            pushFollow(FOLLOW_expression_in_list838);
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
            	    break loop26;
                }
            } while (true);


            match(input,35,FOLLOW_35_in_list847); 

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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:271:1: value returns [Object value] : ( INTEGER | FLOAT | STRING );
    public final Object value() throws RecognitionException {
        Object value = null;


        Token INTEGER6=null;
        Token FLOAT7=null;
        Token STRING8=null;

        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:272:3: ( INTEGER | FLOAT | STRING )
            int alt27=3;
            switch ( input.LA(1) ) {
            case INTEGER:
                {
                alt27=1;
                }
                break;
            case FLOAT:
                {
                alt27=2;
                }
                break;
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
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:272:5: INTEGER
                    {
                    INTEGER6=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_value862); 

                     value = new Integer((INTEGER6!=null?INTEGER6.getText():null));

                    }
                    break;
                case 2 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:273:5: FLOAT
                    {
                    FLOAT7=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_value871); 

                     value = new Float((FLOAT7!=null?FLOAT7.getText():null)); 

                    }
                    break;
                case 3 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:274:5: STRING
                    {
                    STRING8=(Token)match(input,STRING,FOLLOW_STRING_in_value879); 

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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:276:1: term returns [Object value] : ( callStatement | '(' expression ')' | value );
    public final Object term() throws RecognitionException {
        Object value = null;


        CallStatement callStatement9 =null;

        Expression expression10 =null;

        Object value11 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:277:3: ( callStatement | '(' expression ')' | value )
            int alt28=3;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                alt28=1;
                }
                break;
            case 16:
                {
                alt28=2;
                }
                break;
            case FLOAT:
            case INTEGER:
            case STRING:
                {
                alt28=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 28, 0, input);

                throw nvae;

            }

            switch (alt28) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:277:5: callStatement
                    {
                    pushFollow(FOLLOW_callStatement_in_term895);
                    callStatement9=callStatement();

                    state._fsp--;


                    value = callStatement9;

                    }
                    break;
                case 2 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:278:5: '(' expression ')'
                    {
                    match(input,16,FOLLOW_16_in_term903); 

                    pushFollow(FOLLOW_expression_in_term905);
                    expression10=expression();

                    state._fsp--;


                    value = expression10;

                    match(input,17,FOLLOW_17_in_term909); 

                    }
                    break;
                case 3 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:279:5: value
                    {
                    pushFollow(FOLLOW_value_in_term915);
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



    // $ANTLR start "negation"
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:282:1: negation returns [NegationStatement value] : ( '!' )? term1= term ;
    public final NegationStatement negation() throws RecognitionException {
        NegationStatement value = null;


        Object term1 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:283:3: ( ( '!' )? term1= term )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:283:5: ( '!' )? term1= term
            {

                  value = new NegationStatement();
                

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:285:7: ( '!' )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==12) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:285:8: '!'
                    {
                    match(input,12,FOLLOW_12_in_negation937); 


                          value.setNegation(true);
                        

                    }
                    break;

            }


            pushFollow(FOLLOW_term_in_negation945);
            term1=term();

            state._fsp--;



                  value.setTerm(term1);
                

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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:291:1: unary returns [UnaryStatement value] : ( '+' | '-' )? neg= negation ;
    public final UnaryStatement unary() throws RecognitionException {
        UnaryStatement value = null;


        NegationStatement neg =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:292:3: ( ( '+' | '-' )? neg= negation )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:292:5: ( '+' | '-' )? neg= negation
            {

                  value = new UnaryStatement();
                

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:294:7: ( '+' | '-' )?
            int alt30=3;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==19) ) {
                alt30=1;
            }
            else if ( (LA30_0==21) ) {
                alt30=2;
            }
            switch (alt30) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:294:8: '+'
                    {
                    match(input,19,FOLLOW_19_in_unary964); 


                          value.setOperation("+");
                        

                    }
                    break;
                case 2 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:296:8: '-'
                    {
                    match(input,21,FOLLOW_21_in_unary969); 


                          value.setOperation("-");
                        

                    }
                    break;

            }


            pushFollow(FOLLOW_negation_in_unary976);
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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:302:1: mult returns [MultStatement value] :u1= unary (op= ( '*' | '/' | '%' ) u2= unary )* ;
    public final MultStatement mult() throws RecognitionException {
        MultStatement value = null;


        Token op=null;
        UnaryStatement u1 =null;

        UnaryStatement u2 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:303:3: (u1= unary (op= ( '*' | '/' | '%' ) u2= unary )* )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:303:5: u1= unary (op= ( '*' | '/' | '%' ) u2= unary )*
            {

                  value = new MultStatement();
                

            pushFollow(FOLLOW_unary_in_mult996);
            u1=unary();

            state._fsp--;



                  value.setInitialValue(u1);
                

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:307:7: (op= ( '*' | '/' | '%' ) u2= unary )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==14||LA31_0==18||LA31_0==23) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:307:9: op= ( '*' | '/' | '%' ) u2= unary
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


            	    pushFollow(FOLLOW_unary_in_mult1018);
            	    u2=unary();

            	    state._fsp--;



            	          value.addBlock((op!=null?op.getText():null), u2);
            	        

            	    }
            	    break;

            	default :
            	    break loop31;
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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:311:1: add returns [AddStatement value] :m1= mult (op= ( '+' | '-' ) m2= mult )* ;
    public final AddStatement add() throws RecognitionException {
        AddStatement value = null;


        Token op=null;
        MultStatement m1 =null;

        MultStatement m2 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:312:3: (m1= mult (op= ( '+' | '-' ) m2= mult )* )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:312:5: m1= mult (op= ( '+' | '-' ) m2= mult )*
            {

                  value = new AddStatement();
                

            pushFollow(FOLLOW_mult_in_add1041);
            m1=mult();

            state._fsp--;



                  value.setInitialValue(m1);
                

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:316:7: (op= ( '+' | '-' ) m2= mult )*
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( (LA32_0==19||LA32_0==21) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:316:9: op= ( '+' | '-' ) m2= mult
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


            	    pushFollow(FOLLOW_mult_in_add1059);
            	    m2=mult();

            	    state._fsp--;



            	          value.addBlockStatement((op!=null?op.getText():null), m2);

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
    // $ANTLR end "add"



    // $ANTLR start "relation"
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:319:1: relation returns [Relation value] :a1= add (op= ( '<' | '<=' | '>=' | '>' ) a2= add )? ;
    public final Relation relation() throws RecognitionException {
        Relation value = null;


        Token op=null;
        AddStatement a1 =null;

        AddStatement a2 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:320:3: (a1= add (op= ( '<' | '<=' | '>=' | '>' ) a2= add )? )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:320:5: a1= add (op= ( '<' | '<=' | '>=' | '>' ) a2= add )?
            {

                  value = new Relation();
                

            pushFollow(FOLLOW_add_in_relation1084);
            a1=add();

            state._fsp--;



                  value.setInitialValue(a1);
                

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:324:7: (op= ( '<' | '<=' | '>=' | '>' ) a2= add )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( ((LA33_0 >= 26 && LA33_0 <= 27)||(LA33_0 >= 30 && LA33_0 <= 31)) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:324:8: op= ( '<' | '<=' | '>=' | '>' ) a2= add
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


                    pushFollow(FOLLOW_add_in_relation1110);
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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:327:1: comparison returns [Comparison value] :rel1= relation (op= ( '==' | '!=' ) rel2= relation )* ;
    public final Comparison comparison() throws RecognitionException {
        Comparison value = null;


        Token op=null;
        Relation rel1 =null;

        Relation rel2 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:328:3: (rel1= relation (op= ( '==' | '!=' ) rel2= relation )* )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:328:5: rel1= relation (op= ( '==' | '!=' ) rel2= relation )*
            {

                  value = new Comparison();
                

            pushFollow(FOLLOW_relation_in_comparison1133);
            rel1=relation();

            state._fsp--;



                  value.setInitialValue(rel1);
                

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:332:7: (op= ( '==' | '!=' ) rel2= relation )*
            loop34:
            do {
                int alt34=2;
                int LA34_0 = input.LA(1);

                if ( (LA34_0==13||LA34_0==29) ) {
                    alt34=1;
                }


                switch (alt34) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:332:8: op= ( '==' | '!=' ) rel2= relation
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


            	    pushFollow(FOLLOW_relation_in_comparison1150);
            	    rel2=relation();

            	    state._fsp--;



            	          value.addBlock((op!=null?op.getText():null),rel2);
            	        

            	    }
            	    break;

            	default :
            	    break loop34;
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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:336:1: expression returns [Expression value] :var1= comparison (op= ( '&&' | '||' ) var2= comparison )* ;
    public final Expression expression() throws RecognitionException {
        Expression value = null;


        Token op=null;
        Comparison var1 =null;

        Comparison var2 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:337:3: (var1= comparison (op= ( '&&' | '||' ) var2= comparison )* )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:337:5: var1= comparison (op= ( '&&' | '||' ) var2= comparison )*
            {
            value = new Expression();

            pushFollow(FOLLOW_comparison_in_expression1172);
            var1=comparison();

            state._fsp--;



                  value.setInitialValue(var1);
                

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:339:7: (op= ( '&&' | '||' ) var2= comparison )*
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( (LA35_0==15||LA35_0==53) ) {
                    alt35=1;
                }


                switch (alt35) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:339:8: op= ( '&&' | '||' ) var2= comparison
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


            	    pushFollow(FOLLOW_comparison_in_expression1190);
            	    var2=comparison();

            	    state._fsp--;



            	          value.addBlock((op!=null?op.getText():null),var2);

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
    // $ANTLR end "expression"



    // $ANTLR start "returnStatement"
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:342:1: returnStatement returns [ReturnStatement value] : 'return' exp= expression ;
    public final ReturnStatement returnStatement() throws RecognitionException {
        ReturnStatement value = null;


        Expression exp =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:343:3: ( 'return' exp= expression )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:343:5: 'return' exp= expression
            {
            match(input,49,FOLLOW_49_in_returnStatement1211); 

            pushFollow(FOLLOW_expression_in_returnStatement1215);
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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:345:1: type returns [String value] : ( 'void' | 'int' | 'long' | 'double' | 'float' | 'char' | 'byte' | 'String' | 'def' );
    public final String type() throws RecognitionException {
        String value = null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:346:3: ( 'void' | 'int' | 'long' | 'double' | 'float' | 'char' | 'byte' | 'String' | 'def' )
            int alt36=9;
            switch ( input.LA(1) ) {
            case 50:
                {
                alt36=1;
                }
                break;
            case 47:
                {
                alt36=2;
                }
                break;
            case 48:
                {
                alt36=3;
                }
                break;
            case 41:
                {
                alt36=4;
                }
                break;
            case 43:
                {
                alt36=5;
                }
                break;
            case 38:
                {
                alt36=6;
                }
                break;
            case 37:
                {
                alt36=7;
                }
                break;
            case 33:
                {
                alt36=8;
                }
                break;
            case 40:
                {
                alt36=9;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 36, 0, input);

                throw nvae;

            }

            switch (alt36) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:346:5: 'void'
                    {
                    match(input,50,FOLLOW_50_in_type1233); 

                    value = Types.VOID;

                    }
                    break;
                case 2 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:347:5: 'int'
                    {
                    match(input,47,FOLLOW_47_in_type1241); 

                    value = Types.INT;

                    }
                    break;
                case 3 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:348:5: 'long'
                    {
                    match(input,48,FOLLOW_48_in_type1249); 

                    value = Types.LONG;

                    }
                    break;
                case 4 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:349:5: 'double'
                    {
                    match(input,41,FOLLOW_41_in_type1257); 

                    value = Types.DOUBLE;

                    }
                    break;
                case 5 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:350:5: 'float'
                    {
                    match(input,43,FOLLOW_43_in_type1265); 

                    value = Types.FLOAT;

                    }
                    break;
                case 6 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:351:5: 'char'
                    {
                    match(input,38,FOLLOW_38_in_type1273); 

                    value = Types.CHAR;

                    }
                    break;
                case 7 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:352:5: 'byte'
                    {
                    match(input,37,FOLLOW_37_in_type1281); 

                    value = Types.BYTE;

                    }
                    break;
                case 8 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:353:5: 'String'
                    {
                    match(input,33,FOLLOW_33_in_type1289); 

                    value = Types.STRING;

                    }
                    break;
                case 9 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:354:5: 'def'
                    {
                    match(input,40,FOLLOW_40_in_type1297); 

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
    public static final BitSet FOLLOW_incrementStatement_in_statement248 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_statement250 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ifStatement_in_statement258 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_whileStatement_in_statement268 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_forStatement_in_statement278 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_block_in_statement291 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_returnStatement_in_statement301 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_statement309 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_36_in_statement317 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_52_in_block335 = new BitSet(new long[]{0x005FEBF2000000C0L});
    public static final BitSet FOLLOW_statement_in_block340 = new BitSet(new long[]{0x005FEBF2000000C0L});
    public static final BitSet FOLLOW_54_in_block347 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INCREMENTER_in_incrementStatement373 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_incrementStatement379 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_incrementStatement387 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_INCREMENTER_in_incrementStatement393 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_ifStatement412 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_ifStatement414 = new BitSet(new long[]{0x0000000000291560L});
    public static final BitSet FOLLOW_expression_in_ifStatement418 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_ifStatement420 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_block_in_ifStatement424 = new BitSet(new long[]{0x0000040000000002L});
    public static final BitSet FOLLOW_42_in_ifStatement433 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_46_in_ifStatement435 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_ifStatement437 = new BitSet(new long[]{0x0000000000291560L});
    public static final BitSet FOLLOW_expression_in_ifStatement441 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_ifStatement443 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_block_in_ifStatement447 = new BitSet(new long[]{0x0000040000000002L});
    public static final BitSet FOLLOW_42_in_ifStatement458 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_block_in_ifStatement462 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_51_in_whileStatement481 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_whileStatement483 = new BitSet(new long[]{0x0000000000291560L});
    public static final BitSet FOLLOW_expression_in_whileStatement487 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_whileStatement489 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_block_in_whileStatement493 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_45_in_forStatement510 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_forStatement512 = new BitSet(new long[]{0x00058B6202000000L});
    public static final BitSet FOLLOW_variable_in_forStatement517 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_assignment_in_forStatement521 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_forStatement528 = new BitSet(new long[]{0x0000000000291560L});
    public static final BitSet FOLLOW_expression_in_forStatement532 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_forStatement536 = new BitSet(new long[]{0x00000000000200C0L});
    public static final BitSet FOLLOW_incrementStatement_in_forStatement542 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_callStatement_in_forStatement550 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_assignment_in_forStatement554 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_forStatement561 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_block_in_forStatement565 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_variable590 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_variable592 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_callStatement614 = new BitSet(new long[]{0x0000000400410002L});
    public static final BitSet FOLLOW_22_in_callStatement619 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_callStatement623 = new BitSet(new long[]{0x0000000400410002L});
    public static final BitSet FOLLOW_listArgument_in_callStatement632 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_parameterArgument_in_callStatement640 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_listArgument661 = new BitSet(new long[]{0x0000000000291560L});
    public static final BitSet FOLLOW_expression_in_listArgument665 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_listArgument668 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_parameterArgument684 = new BitSet(new long[]{0x00000000002B1560L});
    public static final BitSet FOLLOW_expression_in_parameterArgument689 = new BitSet(new long[]{0x0000000000120000L});
    public static final BitSet FOLLOW_20_in_parameterArgument694 = new BitSet(new long[]{0x0000000000291560L});
    public static final BitSet FOLLOW_expression_in_parameterArgument698 = new BitSet(new long[]{0x0000000000120000L});
    public static final BitSet FOLLOW_17_in_parameterArgument706 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_method726 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_method732 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_method735 = new BitSet(new long[]{0x0000000000020040L});
    public static final BitSet FOLLOW_IDENT_in_method740 = new BitSet(new long[]{0x0000000000020040L});
    public static final BitSet FOLLOW_17_in_method747 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_block_in_method751 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_assignment773 = new BitSet(new long[]{0x0000000000291560L});
    public static final BitSet FOLLOW_expression_in_assignment777 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_assignment783 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_list_in_assignment787 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_list808 = new BitSet(new long[]{0x0000000000291560L});
    public static final BitSet FOLLOW_expression_in_list812 = new BitSet(new long[]{0x0000000801100000L});
    public static final BitSet FOLLOW_24_in_list815 = new BitSet(new long[]{0x0000000000291560L});
    public static final BitSet FOLLOW_expression_in_list819 = new BitSet(new long[]{0x0000000800100000L});
    public static final BitSet FOLLOW_20_in_list827 = new BitSet(new long[]{0x0000000000291560L});
    public static final BitSet FOLLOW_expression_in_list831 = new BitSet(new long[]{0x0000000801100000L});
    public static final BitSet FOLLOW_24_in_list834 = new BitSet(new long[]{0x0000000000291560L});
    public static final BitSet FOLLOW_expression_in_list838 = new BitSet(new long[]{0x0000000800100000L});
    public static final BitSet FOLLOW_35_in_list847 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_value862 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_in_value871 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_value879 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_callStatement_in_term895 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_term903 = new BitSet(new long[]{0x0000000000291560L});
    public static final BitSet FOLLOW_expression_in_term905 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_term909 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_value_in_term915 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_negation937 = new BitSet(new long[]{0x0000000000010560L});
    public static final BitSet FOLLOW_term_in_negation945 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_unary964 = new BitSet(new long[]{0x0000000000011560L});
    public static final BitSet FOLLOW_21_in_unary969 = new BitSet(new long[]{0x0000000000011560L});
    public static final BitSet FOLLOW_negation_in_unary976 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unary_in_mult996 = new BitSet(new long[]{0x0000000000844002L});
    public static final BitSet FOLLOW_set_in_mult1004 = new BitSet(new long[]{0x0000000000291560L});
    public static final BitSet FOLLOW_unary_in_mult1018 = new BitSet(new long[]{0x0000000000844002L});
    public static final BitSet FOLLOW_mult_in_add1041 = new BitSet(new long[]{0x0000000000280002L});
    public static final BitSet FOLLOW_set_in_add1049 = new BitSet(new long[]{0x0000000000291560L});
    public static final BitSet FOLLOW_mult_in_add1059 = new BitSet(new long[]{0x0000000000280002L});
    public static final BitSet FOLLOW_add_in_relation1084 = new BitSet(new long[]{0x00000000CC000002L});
    public static final BitSet FOLLOW_set_in_relation1091 = new BitSet(new long[]{0x0000000000291560L});
    public static final BitSet FOLLOW_add_in_relation1110 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_relation_in_comparison1133 = new BitSet(new long[]{0x0000000020002002L});
    public static final BitSet FOLLOW_set_in_comparison1140 = new BitSet(new long[]{0x0000000000291560L});
    public static final BitSet FOLLOW_relation_in_comparison1150 = new BitSet(new long[]{0x0000000020002002L});
    public static final BitSet FOLLOW_comparison_in_expression1172 = new BitSet(new long[]{0x0020000000008002L});
    public static final BitSet FOLLOW_set_in_expression1179 = new BitSet(new long[]{0x0000000000291560L});
    public static final BitSet FOLLOW_comparison_in_expression1190 = new BitSet(new long[]{0x0020000000008002L});
    public static final BitSet FOLLOW_49_in_returnStatement1211 = new BitSet(new long[]{0x0000000000291560L});
    public static final BitSet FOLLOW_expression_in_returnStatement1215 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_50_in_type1233 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_47_in_type1241 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_48_in_type1249 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_41_in_type1257 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_43_in_type1265 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_38_in_type1273 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_type1281 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_type1289 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_40_in_type1297 = new BitSet(new long[]{0x0000000000000002L});

}