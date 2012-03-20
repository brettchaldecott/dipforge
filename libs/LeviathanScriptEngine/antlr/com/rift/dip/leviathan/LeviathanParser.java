// $ANTLR 3.4 /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g 2012-03-19 15:51:14

  package com.rift.dip.leviathan;
  
  import com.rift.dipforge.ls.parser.obj.Annotation;
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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "COMMENT", "FLOAT", "IDENT", "INCREMENTER", "INTEGER", "LINE_COMMENT", "STRING", "WHITESPACE", "'!'", "'!='", "'%'", "'&&'", "'('", "')'", "'*'", "'+'", "','", "'-'", "'.'", "'/'", "':'", "';'", "'<'", "'<='", "'='", "'=='", "'>'", "'>='", "'@'", "'String'", "'['", "']'", "'as'", "'break'", "'byte'", "'case'", "'char'", "'continue'", "'def'", "'default'", "'define'", "'double'", "'else'", "'float'", "'flow'", "'for'", "'if'", "'int'", "'long'", "'return'", "'switch'", "'void'", "'while'", "'{'", "'||'", "'}'"
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
    public static final int T__55=55;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int T__59=59;
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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:92:1: workflow returns [Workflow value] : ( define )* (anno= annotation )* 'flow' flowName= IDENT '{' ( variable ( assignment )? )* (meth1= method )* blo= block (meth2= method )* '}' ;
    public final Workflow workflow() throws RecognitionException {
        Workflow value = null;


        Token flowName=null;
        Annotation anno =null;

        MethodDefinition meth1 =null;

        Block blo =null;

        MethodDefinition meth2 =null;

        TypeDefinition define1 =null;

        Variable variable2 =null;

        Assignment assignment3 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:93:3: ( ( define )* (anno= annotation )* 'flow' flowName= IDENT '{' ( variable ( assignment )? )* (meth1= method )* blo= block (meth2= method )* '}' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:93:5: ( define )* (anno= annotation )* 'flow' flowName= IDENT '{' ( variable ( assignment )? )* (meth1= method )* blo= block (meth2= method )* '}'
            {

                value = new Workflow();
                currentBlock = value;
                

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:97:5: ( define )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==44) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:97:6: define
            	    {
            	    pushFollow(FOLLOW_define_in_workflow60);
            	    define1=define();

            	    state._fsp--;


            	    value.getDefinedTypes().add(define1);

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:98:4: (anno= annotation )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==32) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:98:5: anno= annotation
            	    {
            	    pushFollow(FOLLOW_annotation_in_workflow72);
            	    anno=annotation();

            	    state._fsp--;


            	    value.getAnnotations().add(anno);

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            match(input,48,FOLLOW_48_in_workflow82); 

            flowName=(Token)match(input,IDENT,FOLLOW_IDENT_in_workflow86); 

             value.setName((flowName!=null?flowName.getText():null));

            match(input,57,FOLLOW_57_in_workflow93); 

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:101:4: ( variable ( assignment )? )*
            loop4:
            do {
                int alt4=2;
                switch ( input.LA(1) ) {
                case 55:
                    {
                    int LA4_1 = input.LA(2);

                    if ( (LA4_1==IDENT) ) {
                        int LA4_11 = input.LA(3);

                        if ( (LA4_11==28||LA4_11==33||LA4_11==38||LA4_11==40||LA4_11==42||LA4_11==45||LA4_11==47||(LA4_11 >= 51 && LA4_11 <= 52)||LA4_11==55||LA4_11==57) ) {
                            alt4=1;
                        }


                    }


                    }
                    break;
                case 51:
                    {
                    int LA4_2 = input.LA(2);

                    if ( (LA4_2==IDENT) ) {
                        int LA4_11 = input.LA(3);

                        if ( (LA4_11==28||LA4_11==33||LA4_11==38||LA4_11==40||LA4_11==42||LA4_11==45||LA4_11==47||(LA4_11 >= 51 && LA4_11 <= 52)||LA4_11==55||LA4_11==57) ) {
                            alt4=1;
                        }


                    }


                    }
                    break;
                case 52:
                    {
                    int LA4_3 = input.LA(2);

                    if ( (LA4_3==IDENT) ) {
                        int LA4_11 = input.LA(3);

                        if ( (LA4_11==28||LA4_11==33||LA4_11==38||LA4_11==40||LA4_11==42||LA4_11==45||LA4_11==47||(LA4_11 >= 51 && LA4_11 <= 52)||LA4_11==55||LA4_11==57) ) {
                            alt4=1;
                        }


                    }


                    }
                    break;
                case 45:
                    {
                    int LA4_4 = input.LA(2);

                    if ( (LA4_4==IDENT) ) {
                        int LA4_11 = input.LA(3);

                        if ( (LA4_11==28||LA4_11==33||LA4_11==38||LA4_11==40||LA4_11==42||LA4_11==45||LA4_11==47||(LA4_11 >= 51 && LA4_11 <= 52)||LA4_11==55||LA4_11==57) ) {
                            alt4=1;
                        }


                    }


                    }
                    break;
                case 47:
                    {
                    int LA4_5 = input.LA(2);

                    if ( (LA4_5==IDENT) ) {
                        int LA4_11 = input.LA(3);

                        if ( (LA4_11==28||LA4_11==33||LA4_11==38||LA4_11==40||LA4_11==42||LA4_11==45||LA4_11==47||(LA4_11 >= 51 && LA4_11 <= 52)||LA4_11==55||LA4_11==57) ) {
                            alt4=1;
                        }


                    }


                    }
                    break;
                case 40:
                    {
                    int LA4_6 = input.LA(2);

                    if ( (LA4_6==IDENT) ) {
                        int LA4_11 = input.LA(3);

                        if ( (LA4_11==28||LA4_11==33||LA4_11==38||LA4_11==40||LA4_11==42||LA4_11==45||LA4_11==47||(LA4_11 >= 51 && LA4_11 <= 52)||LA4_11==55||LA4_11==57) ) {
                            alt4=1;
                        }


                    }


                    }
                    break;
                case 38:
                    {
                    int LA4_7 = input.LA(2);

                    if ( (LA4_7==IDENT) ) {
                        int LA4_11 = input.LA(3);

                        if ( (LA4_11==28||LA4_11==33||LA4_11==38||LA4_11==40||LA4_11==42||LA4_11==45||LA4_11==47||(LA4_11 >= 51 && LA4_11 <= 52)||LA4_11==55||LA4_11==57) ) {
                            alt4=1;
                        }


                    }


                    }
                    break;
                case 33:
                    {
                    int LA4_8 = input.LA(2);

                    if ( (LA4_8==IDENT) ) {
                        int LA4_11 = input.LA(3);

                        if ( (LA4_11==28||LA4_11==33||LA4_11==38||LA4_11==40||LA4_11==42||LA4_11==45||LA4_11==47||(LA4_11 >= 51 && LA4_11 <= 52)||LA4_11==55||LA4_11==57) ) {
                            alt4=1;
                        }


                    }


                    }
                    break;
                case 42:
                    {
                    int LA4_9 = input.LA(2);

                    if ( (LA4_9==IDENT) ) {
                        int LA4_11 = input.LA(3);

                        if ( (LA4_11==28||LA4_11==33||LA4_11==38||LA4_11==40||LA4_11==42||LA4_11==45||LA4_11==47||(LA4_11 >= 51 && LA4_11 <= 52)||LA4_11==55||LA4_11==57) ) {
                            alt4=1;
                        }


                    }


                    }
                    break;

                }

                switch (alt4) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:101:5: variable ( assignment )?
            	    {
            	    pushFollow(FOLLOW_variable_in_workflow99);
            	    variable2=variable();

            	    state._fsp--;


            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:101:14: ( assignment )?
            	    int alt3=2;
            	    int LA3_0 = input.LA(1);

            	    if ( (LA3_0==28) ) {
            	        alt3=1;
            	    }
            	    switch (alt3) {
            	        case 1 :
            	            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:101:15: assignment
            	            {
            	            pushFollow(FOLLOW_assignment_in_workflow102);
            	            assignment3=assignment();

            	            state._fsp--;



            	                  variable2.setInitialValue(assignment3);
            	                

            	            }
            	            break;

            	    }



            	          value.getStatements().add(variable2);

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:105:5: (meth1= method )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==33||LA5_0==38||LA5_0==40||LA5_0==42||LA5_0==45||LA5_0==47||(LA5_0 >= 51 && LA5_0 <= 52)||LA5_0==55) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:105:6: meth1= method
            	    {
            	    pushFollow(FOLLOW_method_in_workflow119);
            	    meth1=method();

            	    state._fsp--;


            	    value.getStatements().add(meth1);

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            pushFollow(FOLLOW_block_in_workflow132);
            blo=block();

            state._fsp--;



                 value.getStatements().add(blo);
                 

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:109:5: (meth2= method )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==33||LA6_0==38||LA6_0==40||LA6_0==42||LA6_0==45||LA6_0==47||(LA6_0 >= 51 && LA6_0 <= 52)||LA6_0==55) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:109:6: meth2= method
            	    {
            	    pushFollow(FOLLOW_method_in_workflow143);
            	    meth2=method();

            	    state._fsp--;



            	        value.getStatements().add(meth2);
            	        

            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);


            match(input,59,FOLLOW_59_in_workflow152); 

             
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



    // $ANTLR start "define"
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:115:1: define returns [TypeDefinition value] : 'define' STRING 'as' IDENT ;
    public final TypeDefinition define() throws RecognitionException {
        TypeDefinition value = null;


        Token STRING4=null;
        Token IDENT5=null;

        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:116:3: ( 'define' STRING 'as' IDENT )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:116:5: 'define' STRING 'as' IDENT
            {
            value = new TypeDefinition();

            match(input,44,FOLLOW_44_in_define170); 

            STRING4=(Token)match(input,STRING,FOLLOW_STRING_in_define172); 


                  value.setUri((STRING4!=null?STRING4.getText():null));
                

            match(input,36,FOLLOW_36_in_define176); 

            IDENT5=(Token)match(input,IDENT,FOLLOW_IDENT_in_define178); 


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
    // $ANTLR end "define"



    // $ANTLR start "annotation"
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:122:1: annotation returns [Annotation value] : '@' name= IDENT '(' (annotationList= list ) ')' ;
    public final Annotation annotation() throws RecognitionException {
        Annotation value = null;


        Token name=null;
        LsList annotationList =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:123:3: ( '@' name= IDENT '(' (annotationList= list ) ')' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:123:5: '@' name= IDENT '(' (annotationList= list ) ')'
            {
            match(input,32,FOLLOW_32_in_annotation194); 

            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_annotation198); 

            match(input,16,FOLLOW_16_in_annotation200); 

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:123:24: (annotationList= list )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:123:25: annotationList= list
            {
            pushFollow(FOLLOW_list_in_annotation205);
            annotationList=list();

            state._fsp--;


            }


            match(input,17,FOLLOW_17_in_annotation208); 


              value = new Annotation((name!=null?name.getText():null),annotationList);

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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:127:1: statement returns [Statement value] : (varCall= callStatement (ass1= assignment )? ( ';' )? |var= variable (ass2= assignment )? ( ';' )? |ifStat= ifStatement |whileStat= whileStatement |forStat= forStatement | caseStatement |bl= block |ret= returnStatement | 'continue' | 'break' ) ;
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
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:128:3: ( (varCall= callStatement (ass1= assignment )? ( ';' )? |var= variable (ass2= assignment )? ( ';' )? |ifStat= ifStatement |whileStat= whileStatement |forStat= forStatement | caseStatement |bl= block |ret= returnStatement | 'continue' | 'break' ) )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:128:5: (varCall= callStatement (ass1= assignment )? ( ';' )? |var= variable (ass2= assignment )? ( ';' )? |ifStat= ifStatement |whileStat= whileStatement |forStat= forStatement | caseStatement |bl= block |ret= returnStatement | 'continue' | 'break' )
            {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:128:5: (varCall= callStatement (ass1= assignment )? ( ';' )? |var= variable (ass2= assignment )? ( ';' )? |ifStat= ifStatement |whileStat= whileStatement |forStat= forStatement | caseStatement |bl= block |ret= returnStatement | 'continue' | 'break' )
            int alt11=10;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                alt11=1;
                }
                break;
            case 33:
            case 38:
            case 40:
            case 42:
            case 45:
            case 47:
            case 51:
            case 52:
            case 55:
                {
                alt11=2;
                }
                break;
            case 50:
                {
                alt11=3;
                }
                break;
            case 56:
                {
                alt11=4;
                }
                break;
            case 49:
                {
                alt11=5;
                }
                break;
            case 54:
                {
                alt11=6;
                }
                break;
            case 57:
                {
                alt11=7;
                }
                break;
            case 53:
                {
                alt11=8;
                }
                break;
            case 41:
                {
                alt11=9;
                }
                break;
            case 37:
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
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:129:3: varCall= callStatement (ass1= assignment )? ( ';' )?
                    {
                    pushFollow(FOLLOW_callStatement_in_statement233);
                    varCall=callStatement();

                    state._fsp--;


                    value = varCall;

                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:129:45: (ass1= assignment )?
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( (LA7_0==28) ) {
                        alt7=1;
                    }
                    switch (alt7) {
                        case 1 :
                            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:129:46: ass1= assignment
                            {
                            pushFollow(FOLLOW_assignment_in_statement240);
                            ass1=assignment();

                            state._fsp--;


                            varCall.setAssignment(ass1);

                            }
                            break;

                    }


                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:129:95: ( ';' )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0==25) ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:129:95: ';'
                            {
                            match(input,25,FOLLOW_25_in_statement246); 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:130:5: var= variable (ass2= assignment )? ( ';' )?
                    {
                    pushFollow(FOLLOW_variable_in_statement256);
                    var=variable();

                    state._fsp--;


                    value = var;

                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:130:34: (ass2= assignment )?
                    int alt9=2;
                    int LA9_0 = input.LA(1);

                    if ( (LA9_0==28) ) {
                        alt9=1;
                    }
                    switch (alt9) {
                        case 1 :
                            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:130:35: ass2= assignment
                            {
                            pushFollow(FOLLOW_assignment_in_statement263);
                            ass2=assignment();

                            state._fsp--;


                            var.setInitialValue(ass2);

                            }
                            break;

                    }


                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:130:82: ( ';' )?
                    int alt10=2;
                    int LA10_0 = input.LA(1);

                    if ( (LA10_0==25) ) {
                        alt10=1;
                    }
                    switch (alt10) {
                        case 1 :
                            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:130:82: ';'
                            {
                            match(input,25,FOLLOW_25_in_statement269); 

                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:131:5: ifStat= ifStatement
                    {
                    pushFollow(FOLLOW_ifStatement_in_statement279);
                    ifStat=ifStatement();

                    state._fsp--;


                    value = ifStat;

                    }
                    break;
                case 4 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:132:5: whileStat= whileStatement
                    {
                    pushFollow(FOLLOW_whileStatement_in_statement289);
                    whileStat=whileStatement();

                    state._fsp--;


                    value = whileStat;

                    }
                    break;
                case 5 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:133:5: forStat= forStatement
                    {
                    pushFollow(FOLLOW_forStatement_in_statement299);
                    forStat=forStatement();

                    state._fsp--;


                    value = forStat;

                    }
                    break;
                case 6 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:134:5: caseStatement
                    {
                    pushFollow(FOLLOW_caseStatement_in_statement307);
                    caseStatement();

                    state._fsp--;


                    }
                    break;
                case 7 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:135:5: bl= block
                    {
                    pushFollow(FOLLOW_block_in_statement315);
                    bl=block();

                    state._fsp--;


                    value = bl;

                    }
                    break;
                case 8 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:136:5: ret= returnStatement
                    {
                    pushFollow(FOLLOW_returnStatement_in_statement325);
                    ret=returnStatement();

                    state._fsp--;


                    value = ret;

                    }
                    break;
                case 9 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:137:5: 'continue'
                    {
                    match(input,41,FOLLOW_41_in_statement333); 

                    value = new ContinueStatement(); 

                    }
                    break;
                case 10 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:138:5: 'break'
                    {
                    match(input,37,FOLLOW_37_in_statement341); 

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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:140:1: block returns [Block value] : '{' ( statement )* '}' ;
    public final Block block() throws RecognitionException {
        Block value = null;


        Statement statement6 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:141:3: ( '{' ( statement )* '}' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:141:5: '{' ( statement )* '}'
            {
            match(input,57,FOLLOW_57_in_block359); 


                  value = new Block();
                  value.setParent(currentBlock);
                  currentBlock = value;
                  

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:145:9: ( statement )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==IDENT||LA12_0==33||(LA12_0 >= 37 && LA12_0 <= 38)||(LA12_0 >= 40 && LA12_0 <= 42)||LA12_0==45||LA12_0==47||(LA12_0 >= 49 && LA12_0 <= 57)) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:145:10: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_block364);
            	    statement6=statement();

            	    state._fsp--;


            	     value.getStatements().add(statement6);

            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);


            match(input,59,FOLLOW_59_in_block371); 


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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:150:1: ifStatement returns [IfStatement value] : 'if' '(' exp1= expression ')' bl1= block ( 'else' 'if' '(' exp2= expression ')' bl2= block )* ( 'else' bl3= block )? ;
    public final IfStatement ifStatement() throws RecognitionException {
        IfStatement value = null;


        Expression exp1 =null;

        Block bl1 =null;

        Expression exp2 =null;

        Block bl2 =null;

        Block bl3 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:151:3: ( 'if' '(' exp1= expression ')' bl1= block ( 'else' 'if' '(' exp2= expression ')' bl2= block )* ( 'else' bl3= block )? )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:151:5: 'if' '(' exp1= expression ')' bl1= block ( 'else' 'if' '(' exp2= expression ')' bl2= block )* ( 'else' bl3= block )?
            {
            value = new IfStatement();

            match(input,50,FOLLOW_50_in_ifStatement394); 

            match(input,16,FOLLOW_16_in_ifStatement396); 

            pushFollow(FOLLOW_expression_in_ifStatement400);
            exp1=expression();

            state._fsp--;


            match(input,17,FOLLOW_17_in_ifStatement402); 

            pushFollow(FOLLOW_block_in_ifStatement406);
            bl1=block();

            state._fsp--;



                  value.addBlock(IfStatement.IfStatementType.IF, exp1, bl1); 

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:153:5: ( 'else' 'if' '(' exp2= expression ')' bl2= block )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==46) ) {
                    int LA13_1 = input.LA(2);

                    if ( (LA13_1==50) ) {
                        alt13=1;
                    }


                }


                switch (alt13) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:153:6: 'else' 'if' '(' exp2= expression ')' bl2= block
            	    {
            	    match(input,46,FOLLOW_46_in_ifStatement415); 

            	    match(input,50,FOLLOW_50_in_ifStatement417); 

            	    match(input,16,FOLLOW_16_in_ifStatement419); 

            	    pushFollow(FOLLOW_expression_in_ifStatement423);
            	    exp2=expression();

            	    state._fsp--;


            	    match(input,17,FOLLOW_17_in_ifStatement425); 

            	    pushFollow(FOLLOW_block_in_ifStatement429);
            	    bl2=block();

            	    state._fsp--;



            	          value.addBlock(IfStatement.IfStatementType.ELSE_IF, exp2, bl2); 

            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);


            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:155:5: ( 'else' bl3= block )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==46) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:155:6: 'else' bl3= block
                    {
                    match(input,46,FOLLOW_46_in_ifStatement440); 

                    pushFollow(FOLLOW_block_in_ifStatement444);
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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:158:1: whileStatement returns [WhileStatement value] : 'while' '(' exp1= expression ')' bl1= block ;
    public final WhileStatement whileStatement() throws RecognitionException {
        WhileStatement value = null;


        Expression exp1 =null;

        Block bl1 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:159:3: ( 'while' '(' exp1= expression ')' bl1= block )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:159:5: 'while' '(' exp1= expression ')' bl1= block
            {
            value = new WhileStatement();

            match(input,56,FOLLOW_56_in_whileStatement463); 

            match(input,16,FOLLOW_16_in_whileStatement465); 

            pushFollow(FOLLOW_expression_in_whileStatement469);
            exp1=expression();

            state._fsp--;


            match(input,17,FOLLOW_17_in_whileStatement471); 

            pushFollow(FOLLOW_block_in_whileStatement475);
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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:163:1: forStatement returns [ForStatement value] : 'for' '(' (var= variable (ass1= assignment )? )? ';' exp1= expression ';' (exp2= expression )? ')' bl= block ;
    public final ForStatement forStatement() throws RecognitionException {
        ForStatement value = null;


        Variable var =null;

        Assignment ass1 =null;

        Expression exp1 =null;

        Expression exp2 =null;

        Block bl =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:164:3: ( 'for' '(' (var= variable (ass1= assignment )? )? ';' exp1= expression ';' (exp2= expression )? ')' bl= block )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:164:5: 'for' '(' (var= variable (ass1= assignment )? )? ';' exp1= expression ';' (exp2= expression )? ')' bl= block
            {

              value = new ForStatement();
              value.setParent(currentBlock);
              currentBlock = value;

            match(input,49,FOLLOW_49_in_forStatement492); 

            match(input,16,FOLLOW_16_in_forStatement494); 

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:167:36: (var= variable (ass1= assignment )? )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==33||LA16_0==38||LA16_0==40||LA16_0==42||LA16_0==45||LA16_0==47||(LA16_0 >= 51 && LA16_0 <= 52)||LA16_0==55) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:167:37: var= variable (ass1= assignment )?
                    {
                    pushFollow(FOLLOW_variable_in_forStatement499);
                    var=variable();

                    state._fsp--;


                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:167:50: (ass1= assignment )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( (LA15_0==28) ) {
                        alt15=1;
                    }
                    switch (alt15) {
                        case 1 :
                            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:167:51: ass1= assignment
                            {
                            pushFollow(FOLLOW_assignment_in_forStatement504);
                            ass1=assignment();

                            state._fsp--;



                              var.setInitialValue(ass1);
                              value.setInitialValue(var);
                              

                            }
                            break;

                    }


                    }
                    break;

            }


            match(input,25,FOLLOW_25_in_forStatement513); 

            pushFollow(FOLLOW_expression_in_forStatement517);
            exp1=expression();

            state._fsp--;



              value.setComparison(exp1);
              

            match(input,25,FOLLOW_25_in_forStatement521); 

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:172:10: (exp2= expression )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( ((LA17_0 >= FLOAT && LA17_0 <= INTEGER)||LA17_0==STRING||LA17_0==12||LA17_0==16||LA17_0==19||LA17_0==21) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:172:12: exp2= expression
                    {
                    pushFollow(FOLLOW_expression_in_forStatement528);
                    exp2=expression();

                    state._fsp--;



                      value.setIncrement(exp2);
                      

                    }
                    break;

            }


            match(input,17,FOLLOW_17_in_forStatement534); 

            pushFollow(FOLLOW_block_in_forStatement538);
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



    // $ANTLR start "caseStatement"
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:176:1: caseStatement returns [CaseStatement value] : 'switch' '(' exp1= expression ')' '{' ( 'case' exp1= expression ':' bl1= block )* ( 'default' ':' block )? ;
    public final CaseStatement caseStatement() throws RecognitionException {
        CaseStatement value = null;


        Expression exp1 =null;

        Block bl1 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:177:2: ( 'switch' '(' exp1= expression ')' '{' ( 'case' exp1= expression ':' bl1= block )* ( 'default' ':' block )? )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:177:4: 'switch' '(' exp1= expression ')' '{' ( 'case' exp1= expression ':' bl1= block )* ( 'default' ':' block )?
            {

                value = new CaseStatement();
               

            match(input,54,FOLLOW_54_in_caseStatement556); 

            match(input,16,FOLLOW_16_in_caseStatement558); 

            pushFollow(FOLLOW_expression_in_caseStatement562);
            exp1=expression();

            state._fsp--;



                value.setExpression(exp1);
               

            match(input,17,FOLLOW_17_in_caseStatement566); 

            match(input,57,FOLLOW_57_in_caseStatement568); 

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:182:3: ( 'case' exp1= expression ':' bl1= block )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==39) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:182:4: 'case' exp1= expression ':' bl1= block
            	    {
            	    match(input,39,FOLLOW_39_in_caseStatement573); 

            	    pushFollow(FOLLOW_expression_in_caseStatement577);
            	    exp1=expression();

            	    state._fsp--;


            	    match(input,24,FOLLOW_24_in_caseStatement579); 

            	    pushFollow(FOLLOW_block_in_caseStatement583);
            	    bl1=block();

            	    state._fsp--;


            	    value.addBlock(exp1,bl1);

            	    }
            	    break;

            	default :
            	    break loop18;
                }
            } while (true);


            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:183:3: ( 'default' ':' block )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==43) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:183:4: 'default' ':' block
                    {
                    match(input,43,FOLLOW_43_in_caseStatement592); 

                    match(input,24,FOLLOW_24_in_caseStatement594); 

                    pushFollow(FOLLOW_block_in_caseStatement596);
                    block();

                    state._fsp--;


                    value.addBlock(null,bl1);

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
    // $ANTLR end "caseStatement"



    // $ANTLR start "variable"
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:185:1: variable returns [Variable value] :t1= type IDENT ;
    public final Variable variable() throws RecognitionException {
        Variable value = null;


        Token IDENT7=null;
        String t1 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:186:3: (t1= type IDENT )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:186:5: t1= type IDENT
            {
            value = new Variable();

            pushFollow(FOLLOW_type_in_variable619);
            t1=type();

            state._fsp--;


            IDENT7=(Token)match(input,IDENT,FOLLOW_IDENT_in_variable621); 


                value.setType(t1);
                value.setName((IDENT7!=null?IDENT7.getText():null));
              

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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:191:1: callStatement returns [CallStatement value] :s1= IDENT (lsArg1= listArgument |paramArg1= parameterArgument )? ( '.' s2= IDENT (lsArg2= listArgument |paramArg2= parameterArgument )? )* ;
    public final CallStatement callStatement() throws RecognitionException {
        CallStatement value = null;


        Token s1=null;
        Token s2=null;
        LsListArgument lsArg1 =null;

        ParameterArgument paramArg1 =null;

        LsListArgument lsArg2 =null;

        ParameterArgument paramArg2 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:192:3: (s1= IDENT (lsArg1= listArgument |paramArg1= parameterArgument )? ( '.' s2= IDENT (lsArg2= listArgument |paramArg2= parameterArgument )? )* )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:192:5: s1= IDENT (lsArg1= listArgument |paramArg1= parameterArgument )? ( '.' s2= IDENT (lsArg2= listArgument |paramArg2= parameterArgument )? )*
            {

                value = new CallStatement();
                CallStatement.CallStatementEntry currentEntry = null;
              

            s1=(Token)match(input,IDENT,FOLLOW_IDENT_in_callStatement643); 


                currentEntry = value.addEntry((s1!=null?s1.getText():null));
              

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:197:5: (lsArg1= listArgument |paramArg1= parameterArgument )?
            int alt20=3;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==34) ) {
                alt20=1;
            }
            else if ( (LA20_0==16) ) {
                alt20=2;
            }
            switch (alt20) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:197:6: lsArg1= listArgument
                    {
                    pushFollow(FOLLOW_listArgument_in_callStatement650);
                    lsArg1=listArgument();

                    state._fsp--;


                    currentEntry.setArgument(lsArg1);

                    }
                    break;
                case 2 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:197:64: paramArg1= parameterArgument
                    {
                    pushFollow(FOLLOW_parameterArgument_in_callStatement658);
                    paramArg1=parameterArgument();

                    state._fsp--;


                    currentEntry.setArgument(paramArg1);

                    }
                    break;

            }


            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:197:134: ( '.' s2= IDENT (lsArg2= listArgument |paramArg2= parameterArgument )? )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==22) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:197:135: '.' s2= IDENT (lsArg2= listArgument |paramArg2= parameterArgument )?
            	    {
            	    match(input,22,FOLLOW_22_in_callStatement666); 

            	    s2=(Token)match(input,IDENT,FOLLOW_IDENT_in_callStatement670); 


            	        currentEntry = value.addEntry((s2!=null?s2.getText():null));
            	      

            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:199:5: (lsArg2= listArgument |paramArg2= parameterArgument )?
            	    int alt21=3;
            	    int LA21_0 = input.LA(1);

            	    if ( (LA21_0==34) ) {
            	        alt21=1;
            	    }
            	    else if ( (LA21_0==16) ) {
            	        alt21=2;
            	    }
            	    switch (alt21) {
            	        case 1 :
            	            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:199:6: lsArg2= listArgument
            	            {
            	            pushFollow(FOLLOW_listArgument_in_callStatement677);
            	            lsArg2=listArgument();

            	            state._fsp--;


            	            currentEntry.setArgument(lsArg2);

            	            }
            	            break;
            	        case 2 :
            	            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:199:64: paramArg2= parameterArgument
            	            {
            	            pushFollow(FOLLOW_parameterArgument_in_callStatement685);
            	            paramArg2=parameterArgument();

            	            state._fsp--;


            	            currentEntry.setArgument(paramArg2);

            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    break loop22;
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
    // $ANTLR end "callStatement"



    // $ANTLR start "listArgument"
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:201:1: listArgument returns [LsListArgument value] : '[' exp= expression ']' ;
    public final LsListArgument listArgument() throws RecognitionException {
        LsListArgument value = null;


        Expression exp =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:202:3: ( '[' exp= expression ']' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:202:5: '[' exp= expression ']'
            {
            value = new LsListArgument();

            match(input,34,FOLLOW_34_in_listArgument709); 

            pushFollow(FOLLOW_expression_in_listArgument713);
            exp=expression();

            state._fsp--;


            value.setExpression(exp); 

            match(input,35,FOLLOW_35_in_listArgument716); 

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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:204:1: parameterArgument returns [ParameterArgument value] : '(' (exp1= expression ( ',' exp2= expression )* )? ')' ;
    public final ParameterArgument parameterArgument() throws RecognitionException {
        ParameterArgument value = null;


        Expression exp1 =null;

        Expression exp2 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:205:3: ( '(' (exp1= expression ( ',' exp2= expression )* )? ')' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:205:5: '(' (exp1= expression ( ',' exp2= expression )* )? ')'
            {
            value = new ParameterArgument(); 

            match(input,16,FOLLOW_16_in_parameterArgument732); 

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:205:46: (exp1= expression ( ',' exp2= expression )* )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( ((LA24_0 >= FLOAT && LA24_0 <= INTEGER)||LA24_0==STRING||LA24_0==12||LA24_0==16||LA24_0==19||LA24_0==21) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:205:47: exp1= expression ( ',' exp2= expression )*
                    {
                    pushFollow(FOLLOW_expression_in_parameterArgument737);
                    exp1=expression();

                    state._fsp--;


                    value.addExpression(exp1);

                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:205:93: ( ',' exp2= expression )*
                    loop23:
                    do {
                        int alt23=2;
                        int LA23_0 = input.LA(1);

                        if ( (LA23_0==20) ) {
                            alt23=1;
                        }


                        switch (alt23) {
                    	case 1 :
                    	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:205:94: ',' exp2= expression
                    	    {
                    	    match(input,20,FOLLOW_20_in_parameterArgument742); 

                    	    pushFollow(FOLLOW_expression_in_parameterArgument746);
                    	    exp2=expression();

                    	    state._fsp--;


                    	    value.addExpression(exp2);

                    	    }
                    	    break;

                    	default :
                    	    break loop23;
                        }
                    } while (true);


                    }
                    break;

            }


            match(input,17,FOLLOW_17_in_parameterArgument754); 

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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:207:1: method returns [MethodDefinition value] :t1= type name= IDENT '(' (param= IDENT )* ')' bl1= block ;
    public final MethodDefinition method() throws RecognitionException {
        MethodDefinition value = null;


        Token name=null;
        Token param=null;
        String t1 =null;

        Block bl1 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:208:3: (t1= type name= IDENT '(' (param= IDENT )* ')' bl1= block )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:208:5: t1= type name= IDENT '(' (param= IDENT )* ')' bl1= block
            {

            	  value = new MethodDefinition();
            	  value.setParent(currentBlock);
            	  currentBlock = value;
              

            pushFollow(FOLLOW_type_in_method774);
            t1=type();

            state._fsp--;



                value.setType(t1);
              

            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_method780); 


                value.setName((name!=null?name.getText():null));
              

            match(input,16,FOLLOW_16_in_method783); 

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:216:8: (param= IDENT )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( (LA25_0==IDENT) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:216:9: param= IDENT
            	    {
            	    param=(Token)match(input,IDENT,FOLLOW_IDENT_in_method788); 


            	        value.addParameter((param!=null?param.getText():null));
            	      

            	    }
            	    break;

            	default :
            	    break loop25;
                }
            } while (true);


            match(input,17,FOLLOW_17_in_method795); 

            pushFollow(FOLLOW_block_in_method799);
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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:222:1: assignment returns [Assignment value] : ( '=' exp= expression | '=' ls= list ) ;
    public final Assignment assignment() throws RecognitionException {
        Assignment value = null;


        Expression exp =null;

        LsList ls =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:223:3: ( ( '=' exp= expression | '=' ls= list ) )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:223:5: ( '=' exp= expression | '=' ls= list )
            {
            value = new Assignment();

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:223:34: ( '=' exp= expression | '=' ls= list )
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==28) ) {
                int LA26_1 = input.LA(2);

                if ( ((LA26_1 >= FLOAT && LA26_1 <= INTEGER)||LA26_1==STRING||LA26_1==12||LA26_1==16||LA26_1==19||LA26_1==21) ) {
                    alt26=1;
                }
                else if ( (LA26_1==34) ) {
                    alt26=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 26, 1, input);

                    throw nvae;

                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 26, 0, input);

                throw nvae;

            }
            switch (alt26) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:223:35: '=' exp= expression
                    {
                    match(input,28,FOLLOW_28_in_assignment821); 

                    pushFollow(FOLLOW_expression_in_assignment825);
                    exp=expression();

                    state._fsp--;


                    value.setValue(exp);

                    }
                    break;
                case 2 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:223:80: '=' ls= list
                    {
                    match(input,28,FOLLOW_28_in_assignment831); 

                    pushFollow(FOLLOW_list_in_assignment835);
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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:225:1: list returns [LsList value] : '[' key1= expression ( ':' val1= expression )? ( ',' key2= expression ( ':' val2= expression )? )* ']' ;
    public final LsList list() throws RecognitionException {
        LsList value = null;


        Expression key1 =null;

        Expression val1 =null;

        Expression key2 =null;

        Expression val2 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:226:3: ( '[' key1= expression ( ':' val1= expression )? ( ',' key2= expression ( ':' val2= expression )? )* ']' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:226:5: '[' key1= expression ( ':' val1= expression )? ( ',' key2= expression ( ':' val2= expression )? )* ']'
            {
            value = new LsList();

            match(input,34,FOLLOW_34_in_list856); 

            pushFollow(FOLLOW_expression_in_list860);
            key1=expression();

            state._fsp--;


            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:226:50: ( ':' val1= expression )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==24) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:226:51: ':' val1= expression
                    {
                    match(input,24,FOLLOW_24_in_list863); 

                    pushFollow(FOLLOW_expression_in_list867);
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
                

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:234:7: ( ',' key2= expression ( ':' val2= expression )? )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( (LA29_0==20) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:234:8: ',' key2= expression ( ':' val2= expression )?
            	    {
            	    match(input,20,FOLLOW_20_in_list875); 

            	    pushFollow(FOLLOW_expression_in_list879);
            	    key2=expression();

            	    state._fsp--;


            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:234:28: ( ':' val2= expression )?
            	    int alt28=2;
            	    int LA28_0 = input.LA(1);

            	    if ( (LA28_0==24) ) {
            	        alt28=1;
            	    }
            	    switch (alt28) {
            	        case 1 :
            	            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:234:29: ':' val2= expression
            	            {
            	            match(input,24,FOLLOW_24_in_list882); 

            	            pushFollow(FOLLOW_expression_in_list886);
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
            	    break loop29;
                }
            } while (true);


            match(input,35,FOLLOW_35_in_list895); 

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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:248:1: value returns [Object value] : ( INTEGER | FLOAT | STRING );
    public final Object value() throws RecognitionException {
        Object value = null;


        Token INTEGER8=null;
        Token FLOAT9=null;
        Token STRING10=null;

        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:249:3: ( INTEGER | FLOAT | STRING )
            int alt30=3;
            switch ( input.LA(1) ) {
            case INTEGER:
                {
                alt30=1;
                }
                break;
            case FLOAT:
                {
                alt30=2;
                }
                break;
            case STRING:
                {
                alt30=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 30, 0, input);

                throw nvae;

            }

            switch (alt30) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:249:5: INTEGER
                    {
                    INTEGER8=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_value910); 

                     value = new Integer((INTEGER8!=null?INTEGER8.getText():null));

                    }
                    break;
                case 2 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:250:5: FLOAT
                    {
                    FLOAT9=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_value919); 

                     value = new Float((FLOAT9!=null?FLOAT9.getText():null)); 

                    }
                    break;
                case 3 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:251:5: STRING
                    {
                    STRING10=(Token)match(input,STRING,FOLLOW_STRING_in_value927); 

                     value = (STRING10!=null?STRING10.getText():null); 

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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:253:1: term returns [Object value] : ( callStatement | '(' expression ')' | value );
    public final Object term() throws RecognitionException {
        Object value = null;


        CallStatement callStatement11 =null;

        Expression expression12 =null;

        Object value13 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:254:3: ( callStatement | '(' expression ')' | value )
            int alt31=3;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                alt31=1;
                }
                break;
            case 16:
                {
                alt31=2;
                }
                break;
            case FLOAT:
            case INTEGER:
            case STRING:
                {
                alt31=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 31, 0, input);

                throw nvae;

            }

            switch (alt31) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:254:5: callStatement
                    {
                    pushFollow(FOLLOW_callStatement_in_term943);
                    callStatement11=callStatement();

                    state._fsp--;


                    value = callStatement11;

                    }
                    break;
                case 2 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:255:5: '(' expression ')'
                    {
                    match(input,16,FOLLOW_16_in_term951); 

                    pushFollow(FOLLOW_expression_in_term953);
                    expression12=expression();

                    state._fsp--;


                    value = expression12;

                    match(input,17,FOLLOW_17_in_term957); 

                    }
                    break;
                case 3 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:256:5: value
                    {
                    pushFollow(FOLLOW_value_in_term963);
                    value13=value();

                    state._fsp--;


                    value = value13;

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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:259:1: increment returns [IncrementStatement value] : (inc1= INCREMENTER )? term (inc2= INCREMENTER )? ;
    public final IncrementStatement increment() throws RecognitionException {
        IncrementStatement value = null;


        Token inc1=null;
        Token inc2=null;
        Object term14 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:260:3: ( (inc1= INCREMENTER )? term (inc2= INCREMENTER )? )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:260:5: (inc1= INCREMENTER )? term (inc2= INCREMENTER )?
            {

                  value = new IncrementStatement();
                

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:262:6: (inc1= INCREMENTER )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==INCREMENTER) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:262:7: inc1= INCREMENTER
                    {
                    inc1=(Token)match(input,INCREMENTER,FOLLOW_INCREMENTER_in_increment986); 


                          value.setOperation((inc1!=null?inc1.getText():null));
                        

                    }
                    break;

            }


            pushFollow(FOLLOW_term_in_increment992);
            term14=term();

            state._fsp--;


            value.setValue(term14);

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:264:46: (inc2= INCREMENTER )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==INCREMENTER) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:264:47: inc2= INCREMENTER
                    {
                    inc2=(Token)match(input,INCREMENTER,FOLLOW_INCREMENTER_in_increment999); 


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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:268:1: negation returns [NegationStatement value] : ( '!' )? inc= increment ;
    public final NegationStatement negation() throws RecognitionException {
        NegationStatement value = null;


        IncrementStatement inc =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:269:3: ( ( '!' )? inc= increment )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:269:5: ( '!' )? inc= increment
            {

                  value = new NegationStatement();
                

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:271:7: ( '!' )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==12) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:271:8: '!'
                    {
                    match(input,12,FOLLOW_12_in_negation1022); 


                          value.setNegation(true);
                        

                    }
                    break;

            }


            pushFollow(FOLLOW_increment_in_negation1030);
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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:277:1: unary returns [UnaryStatement value] : ( '+' | '-' )? neg= negation ;
    public final UnaryStatement unary() throws RecognitionException {
        UnaryStatement value = null;


        NegationStatement neg =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:278:3: ( ( '+' | '-' )? neg= negation )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:278:5: ( '+' | '-' )? neg= negation
            {

                  value = new UnaryStatement();
                

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:280:7: ( '+' | '-' )?
            int alt35=3;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==19) ) {
                alt35=1;
            }
            else if ( (LA35_0==21) ) {
                alt35=2;
            }
            switch (alt35) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:280:8: '+'
                    {
                    match(input,19,FOLLOW_19_in_unary1049); 


                          value.setOperation("+");
                        

                    }
                    break;
                case 2 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:282:8: '-'
                    {
                    match(input,21,FOLLOW_21_in_unary1054); 


                          value.setOperation("-");
                        

                    }
                    break;

            }


            pushFollow(FOLLOW_negation_in_unary1061);
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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:288:1: mult returns [MultStatement value] :u1= unary (op= ( '*' | '/' | '%' ) u2= unary )* ;
    public final MultStatement mult() throws RecognitionException {
        MultStatement value = null;


        Token op=null;
        UnaryStatement u1 =null;

        UnaryStatement u2 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:289:3: (u1= unary (op= ( '*' | '/' | '%' ) u2= unary )* )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:289:5: u1= unary (op= ( '*' | '/' | '%' ) u2= unary )*
            {

                  value = new MultStatement();
                

            pushFollow(FOLLOW_unary_in_mult1081);
            u1=unary();

            state._fsp--;



                  value.setInitialValue(u1);
                

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:293:7: (op= ( '*' | '/' | '%' ) u2= unary )*
            loop36:
            do {
                int alt36=2;
                int LA36_0 = input.LA(1);

                if ( (LA36_0==14||LA36_0==18||LA36_0==23) ) {
                    alt36=1;
                }


                switch (alt36) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:293:9: op= ( '*' | '/' | '%' ) u2= unary
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


            	    pushFollow(FOLLOW_unary_in_mult1103);
            	    u2=unary();

            	    state._fsp--;



            	          value.addBlock((op!=null?op.getText():null), u2);
            	        

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
    // $ANTLR end "mult"



    // $ANTLR start "add"
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:297:1: add returns [AddStatement value] :m1= mult (op= ( '+' | '-' ) m2= mult )* ;
    public final AddStatement add() throws RecognitionException {
        AddStatement value = null;


        Token op=null;
        MultStatement m1 =null;

        MultStatement m2 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:298:3: (m1= mult (op= ( '+' | '-' ) m2= mult )* )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:298:5: m1= mult (op= ( '+' | '-' ) m2= mult )*
            {

                  value = new AddStatement();
                

            pushFollow(FOLLOW_mult_in_add1126);
            m1=mult();

            state._fsp--;



                  value.setInitialValue(m1);
                

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:302:7: (op= ( '+' | '-' ) m2= mult )*
            loop37:
            do {
                int alt37=2;
                int LA37_0 = input.LA(1);

                if ( (LA37_0==19||LA37_0==21) ) {
                    alt37=1;
                }


                switch (alt37) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:302:9: op= ( '+' | '-' ) m2= mult
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


            	    pushFollow(FOLLOW_mult_in_add1144);
            	    m2=mult();

            	    state._fsp--;



            	          value.addBlockStatement((op!=null?op.getText():null), m2);

            	    }
            	    break;

            	default :
            	    break loop37;
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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:305:1: relation returns [Relation value] :a1= add (op= ( '<' | '<=' | '>=' | '>' ) a2= add )? ;
    public final Relation relation() throws RecognitionException {
        Relation value = null;


        Token op=null;
        AddStatement a1 =null;

        AddStatement a2 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:306:3: (a1= add (op= ( '<' | '<=' | '>=' | '>' ) a2= add )? )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:306:5: a1= add (op= ( '<' | '<=' | '>=' | '>' ) a2= add )?
            {

                  value = new Relation();
                

            pushFollow(FOLLOW_add_in_relation1169);
            a1=add();

            state._fsp--;



                  value.setInitialValue(a1);
                

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:310:7: (op= ( '<' | '<=' | '>=' | '>' ) a2= add )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( ((LA38_0 >= 26 && LA38_0 <= 27)||(LA38_0 >= 30 && LA38_0 <= 31)) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:310:8: op= ( '<' | '<=' | '>=' | '>' ) a2= add
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


                    pushFollow(FOLLOW_add_in_relation1195);
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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:313:1: comparison returns [Comparison value] :rel1= relation (op= ( '==' | '!=' ) rel2= relation )* ;
    public final Comparison comparison() throws RecognitionException {
        Comparison value = null;


        Token op=null;
        Relation rel1 =null;

        Relation rel2 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:314:3: (rel1= relation (op= ( '==' | '!=' ) rel2= relation )* )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:314:5: rel1= relation (op= ( '==' | '!=' ) rel2= relation )*
            {

                  value = new Comparison();
                

            pushFollow(FOLLOW_relation_in_comparison1218);
            rel1=relation();

            state._fsp--;



                  value.setInitialValue(rel1);
                

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:318:7: (op= ( '==' | '!=' ) rel2= relation )*
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( (LA39_0==13||LA39_0==29) ) {
                    alt39=1;
                }


                switch (alt39) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:318:8: op= ( '==' | '!=' ) rel2= relation
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


            	    pushFollow(FOLLOW_relation_in_comparison1235);
            	    rel2=relation();

            	    state._fsp--;



            	          value.addBlock((op!=null?op.getText():null),rel2);
            	        

            	    }
            	    break;

            	default :
            	    break loop39;
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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:322:1: expression returns [Expression value] :var1= comparison (op= ( '&&' | '||' ) var2= comparison )* ;
    public final Expression expression() throws RecognitionException {
        Expression value = null;


        Token op=null;
        Comparison var1 =null;

        Comparison var2 =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:323:3: (var1= comparison (op= ( '&&' | '||' ) var2= comparison )* )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:323:5: var1= comparison (op= ( '&&' | '||' ) var2= comparison )*
            {
            value = new Expression();

            pushFollow(FOLLOW_comparison_in_expression1257);
            var1=comparison();

            state._fsp--;



                  value.setInitialValue(var1);
                

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:325:7: (op= ( '&&' | '||' ) var2= comparison )*
            loop40:
            do {
                int alt40=2;
                int LA40_0 = input.LA(1);

                if ( (LA40_0==15||LA40_0==58) ) {
                    alt40=1;
                }


                switch (alt40) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:325:8: op= ( '&&' | '||' ) var2= comparison
            	    {
            	    op=(Token)input.LT(1);

            	    if ( input.LA(1)==15||input.LA(1)==58 ) {
            	        input.consume();
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }


            	    pushFollow(FOLLOW_comparison_in_expression1275);
            	    var2=comparison();

            	    state._fsp--;



            	          value.addBlock((op!=null?op.getText():null),var2);

            	    }
            	    break;

            	default :
            	    break loop40;
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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:328:1: returnStatement returns [ReturnStatement value] : 'return' exp= expression ;
    public final ReturnStatement returnStatement() throws RecognitionException {
        ReturnStatement value = null;


        Expression exp =null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:329:3: ( 'return' exp= expression )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:329:5: 'return' exp= expression
            {
            match(input,53,FOLLOW_53_in_returnStatement1296); 

            pushFollow(FOLLOW_expression_in_returnStatement1300);
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
    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:331:1: type returns [String value] : ( 'void' | 'int' | 'long' | 'double' | 'float' | 'char' | 'byte' | 'String' | 'def' );
    public final String type() throws RecognitionException {
        String value = null;


        try {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:332:3: ( 'void' | 'int' | 'long' | 'double' | 'float' | 'char' | 'byte' | 'String' | 'def' )
            int alt41=9;
            switch ( input.LA(1) ) {
            case 55:
                {
                alt41=1;
                }
                break;
            case 51:
                {
                alt41=2;
                }
                break;
            case 52:
                {
                alt41=3;
                }
                break;
            case 45:
                {
                alt41=4;
                }
                break;
            case 47:
                {
                alt41=5;
                }
                break;
            case 40:
                {
                alt41=6;
                }
                break;
            case 38:
                {
                alt41=7;
                }
                break;
            case 33:
                {
                alt41=8;
                }
                break;
            case 42:
                {
                alt41=9;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 41, 0, input);

                throw nvae;

            }

            switch (alt41) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:332:5: 'void'
                    {
                    match(input,55,FOLLOW_55_in_type1318); 

                    value = Types.VOID;

                    }
                    break;
                case 2 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:333:5: 'int'
                    {
                    match(input,51,FOLLOW_51_in_type1326); 

                    value = Types.INT;

                    }
                    break;
                case 3 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:334:5: 'long'
                    {
                    match(input,52,FOLLOW_52_in_type1334); 

                    value = Types.LONG;

                    }
                    break;
                case 4 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:335:5: 'double'
                    {
                    match(input,45,FOLLOW_45_in_type1342); 

                    value = Types.DOUBLE;

                    }
                    break;
                case 5 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:336:5: 'float'
                    {
                    match(input,47,FOLLOW_47_in_type1350); 

                    value = Types.FLOAT;

                    }
                    break;
                case 6 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:337:5: 'char'
                    {
                    match(input,40,FOLLOW_40_in_type1358); 

                    value = Types.CHAR;

                    }
                    break;
                case 7 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:338:5: 'byte'
                    {
                    match(input,38,FOLLOW_38_in_type1366); 

                    value = Types.BYTE;

                    }
                    break;
                case 8 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:339:5: 'String'
                    {
                    match(input,33,FOLLOW_33_in_type1374); 

                    value = Types.STRING;

                    }
                    break;
                case 9 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:340:5: 'def'
                    {
                    match(input,42,FOLLOW_42_in_type1382); 

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


 

    public static final BitSet FOLLOW_define_in_workflow60 = new BitSet(new long[]{0x0001100100000000L});
    public static final BitSet FOLLOW_annotation_in_workflow72 = new BitSet(new long[]{0x0001000100000000L});
    public static final BitSet FOLLOW_48_in_workflow82 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_workflow86 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_workflow93 = new BitSet(new long[]{0x0298A54200000000L});
    public static final BitSet FOLLOW_variable_in_workflow99 = new BitSet(new long[]{0x0298A54210000000L});
    public static final BitSet FOLLOW_assignment_in_workflow102 = new BitSet(new long[]{0x0298A54200000000L});
    public static final BitSet FOLLOW_method_in_workflow119 = new BitSet(new long[]{0x0298A54200000000L});
    public static final BitSet FOLLOW_block_in_workflow132 = new BitSet(new long[]{0x0898A54200000000L});
    public static final BitSet FOLLOW_method_in_workflow143 = new BitSet(new long[]{0x0898A54200000000L});
    public static final BitSet FOLLOW_59_in_workflow152 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_44_in_define170 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_STRING_in_define172 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_36_in_define176 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_define178 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_annotation194 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_annotation198 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_annotation200 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_list_in_annotation205 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_annotation208 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_callStatement_in_statement233 = new BitSet(new long[]{0x0000000012000002L});
    public static final BitSet FOLLOW_assignment_in_statement240 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_25_in_statement246 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_variable_in_statement256 = new BitSet(new long[]{0x0000000012000002L});
    public static final BitSet FOLLOW_assignment_in_statement263 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_25_in_statement269 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ifStatement_in_statement279 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_whileStatement_in_statement289 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_forStatement_in_statement299 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_caseStatement_in_statement307 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_block_in_statement315 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_returnStatement_in_statement325 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_41_in_statement333 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_statement341 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_block359 = new BitSet(new long[]{0x0BFEA76200000040L});
    public static final BitSet FOLLOW_statement_in_block364 = new BitSet(new long[]{0x0BFEA76200000040L});
    public static final BitSet FOLLOW_59_in_block371 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_50_in_ifStatement394 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_ifStatement396 = new BitSet(new long[]{0x00000000002915E0L});
    public static final BitSet FOLLOW_expression_in_ifStatement400 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_ifStatement402 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_block_in_ifStatement406 = new BitSet(new long[]{0x0000400000000002L});
    public static final BitSet FOLLOW_46_in_ifStatement415 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_ifStatement417 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_ifStatement419 = new BitSet(new long[]{0x00000000002915E0L});
    public static final BitSet FOLLOW_expression_in_ifStatement423 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_ifStatement425 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_block_in_ifStatement429 = new BitSet(new long[]{0x0000400000000002L});
    public static final BitSet FOLLOW_46_in_ifStatement440 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_block_in_ifStatement444 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_56_in_whileStatement463 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_whileStatement465 = new BitSet(new long[]{0x00000000002915E0L});
    public static final BitSet FOLLOW_expression_in_whileStatement469 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_whileStatement471 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_block_in_whileStatement475 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_49_in_forStatement492 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_forStatement494 = new BitSet(new long[]{0x0098A54202000000L});
    public static final BitSet FOLLOW_variable_in_forStatement499 = new BitSet(new long[]{0x0000000012000000L});
    public static final BitSet FOLLOW_assignment_in_forStatement504 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_forStatement513 = new BitSet(new long[]{0x00000000002915E0L});
    public static final BitSet FOLLOW_expression_in_forStatement517 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_forStatement521 = new BitSet(new long[]{0x00000000002B15E0L});
    public static final BitSet FOLLOW_expression_in_forStatement528 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_forStatement534 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_block_in_forStatement538 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_54_in_caseStatement556 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_caseStatement558 = new BitSet(new long[]{0x00000000002915E0L});
    public static final BitSet FOLLOW_expression_in_caseStatement562 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_caseStatement566 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_caseStatement568 = new BitSet(new long[]{0x0000088000000002L});
    public static final BitSet FOLLOW_39_in_caseStatement573 = new BitSet(new long[]{0x00000000002915E0L});
    public static final BitSet FOLLOW_expression_in_caseStatement577 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_24_in_caseStatement579 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_block_in_caseStatement583 = new BitSet(new long[]{0x0000088000000002L});
    public static final BitSet FOLLOW_43_in_caseStatement592 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_24_in_caseStatement594 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_block_in_caseStatement596 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_variable619 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_variable621 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_callStatement643 = new BitSet(new long[]{0x0000000400410002L});
    public static final BitSet FOLLOW_listArgument_in_callStatement650 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_parameterArgument_in_callStatement658 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_22_in_callStatement666 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_callStatement670 = new BitSet(new long[]{0x0000000400410002L});
    public static final BitSet FOLLOW_listArgument_in_callStatement677 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_parameterArgument_in_callStatement685 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_34_in_listArgument709 = new BitSet(new long[]{0x00000000002915E0L});
    public static final BitSet FOLLOW_expression_in_listArgument713 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_listArgument716 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_parameterArgument732 = new BitSet(new long[]{0x00000000002B15E0L});
    public static final BitSet FOLLOW_expression_in_parameterArgument737 = new BitSet(new long[]{0x0000000000120000L});
    public static final BitSet FOLLOW_20_in_parameterArgument742 = new BitSet(new long[]{0x00000000002915E0L});
    public static final BitSet FOLLOW_expression_in_parameterArgument746 = new BitSet(new long[]{0x0000000000120000L});
    public static final BitSet FOLLOW_17_in_parameterArgument754 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_method774 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_method780 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_method783 = new BitSet(new long[]{0x0000000000020040L});
    public static final BitSet FOLLOW_IDENT_in_method788 = new BitSet(new long[]{0x0000000000020040L});
    public static final BitSet FOLLOW_17_in_method795 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_block_in_method799 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_assignment821 = new BitSet(new long[]{0x00000000002915E0L});
    public static final BitSet FOLLOW_expression_in_assignment825 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_assignment831 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_list_in_assignment835 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_list856 = new BitSet(new long[]{0x00000000002915E0L});
    public static final BitSet FOLLOW_expression_in_list860 = new BitSet(new long[]{0x0000000801100000L});
    public static final BitSet FOLLOW_24_in_list863 = new BitSet(new long[]{0x00000000002915E0L});
    public static final BitSet FOLLOW_expression_in_list867 = new BitSet(new long[]{0x0000000800100000L});
    public static final BitSet FOLLOW_20_in_list875 = new BitSet(new long[]{0x00000000002915E0L});
    public static final BitSet FOLLOW_expression_in_list879 = new BitSet(new long[]{0x0000000801100000L});
    public static final BitSet FOLLOW_24_in_list882 = new BitSet(new long[]{0x00000000002915E0L});
    public static final BitSet FOLLOW_expression_in_list886 = new BitSet(new long[]{0x0000000800100000L});
    public static final BitSet FOLLOW_35_in_list895 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_value910 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_in_value919 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_value927 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_callStatement_in_term943 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_term951 = new BitSet(new long[]{0x00000000002915E0L});
    public static final BitSet FOLLOW_expression_in_term953 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_term957 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_value_in_term963 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INCREMENTER_in_increment986 = new BitSet(new long[]{0x0000000000010560L});
    public static final BitSet FOLLOW_term_in_increment992 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_INCREMENTER_in_increment999 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_negation1022 = new BitSet(new long[]{0x00000000000105E0L});
    public static final BitSet FOLLOW_increment_in_negation1030 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_unary1049 = new BitSet(new long[]{0x00000000000115E0L});
    public static final BitSet FOLLOW_21_in_unary1054 = new BitSet(new long[]{0x00000000000115E0L});
    public static final BitSet FOLLOW_negation_in_unary1061 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unary_in_mult1081 = new BitSet(new long[]{0x0000000000844002L});
    public static final BitSet FOLLOW_set_in_mult1089 = new BitSet(new long[]{0x00000000002915E0L});
    public static final BitSet FOLLOW_unary_in_mult1103 = new BitSet(new long[]{0x0000000000844002L});
    public static final BitSet FOLLOW_mult_in_add1126 = new BitSet(new long[]{0x0000000000280002L});
    public static final BitSet FOLLOW_set_in_add1134 = new BitSet(new long[]{0x00000000002915E0L});
    public static final BitSet FOLLOW_mult_in_add1144 = new BitSet(new long[]{0x0000000000280002L});
    public static final BitSet FOLLOW_add_in_relation1169 = new BitSet(new long[]{0x00000000CC000002L});
    public static final BitSet FOLLOW_set_in_relation1176 = new BitSet(new long[]{0x00000000002915E0L});
    public static final BitSet FOLLOW_add_in_relation1195 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_relation_in_comparison1218 = new BitSet(new long[]{0x0000000020002002L});
    public static final BitSet FOLLOW_set_in_comparison1225 = new BitSet(new long[]{0x00000000002915E0L});
    public static final BitSet FOLLOW_relation_in_comparison1235 = new BitSet(new long[]{0x0000000020002002L});
    public static final BitSet FOLLOW_comparison_in_expression1257 = new BitSet(new long[]{0x0400000000008002L});
    public static final BitSet FOLLOW_set_in_expression1264 = new BitSet(new long[]{0x00000000002915E0L});
    public static final BitSet FOLLOW_comparison_in_expression1275 = new BitSet(new long[]{0x0400000000008002L});
    public static final BitSet FOLLOW_53_in_returnStatement1296 = new BitSet(new long[]{0x00000000002915E0L});
    public static final BitSet FOLLOW_expression_in_returnStatement1300 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_type1318 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_51_in_type1326 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_52_in_type1334 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_45_in_type1342 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_47_in_type1350 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_40_in_type1358 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_38_in_type1366 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_type1374 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_42_in_type1382 = new BitSet(new long[]{0x0000000000000002L});

}