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
public class LeviathanLexer extends Lexer {
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
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {};
    }

    public LeviathanLexer() {} 
    public LeviathanLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public LeviathanLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }
    public String getGrammarFileName() { return "/home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g"; }

    // $ANTLR start "T__12"
    public final void mT__12() throws RecognitionException {
        try {
            int _type = T__12;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:42:7: ( '!' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:42:9: '!'
            {
            match('!'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__12"

    // $ANTLR start "T__13"
    public final void mT__13() throws RecognitionException {
        try {
            int _type = T__13;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:43:7: ( '!=' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:43:9: '!='
            {
            match("!="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__13"

    // $ANTLR start "T__14"
    public final void mT__14() throws RecognitionException {
        try {
            int _type = T__14;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:44:7: ( '%' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:44:9: '%'
            {
            match('%'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__14"

    // $ANTLR start "T__15"
    public final void mT__15() throws RecognitionException {
        try {
            int _type = T__15;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:45:7: ( '&&' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:45:9: '&&'
            {
            match("&&"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__15"

    // $ANTLR start "T__16"
    public final void mT__16() throws RecognitionException {
        try {
            int _type = T__16;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:46:7: ( '(' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:46:9: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__16"

    // $ANTLR start "T__17"
    public final void mT__17() throws RecognitionException {
        try {
            int _type = T__17;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:47:7: ( ')' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:47:9: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__17"

    // $ANTLR start "T__18"
    public final void mT__18() throws RecognitionException {
        try {
            int _type = T__18;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:48:7: ( '*' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:48:9: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__18"

    // $ANTLR start "T__19"
    public final void mT__19() throws RecognitionException {
        try {
            int _type = T__19;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:49:7: ( '+' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:49:9: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__19"

    // $ANTLR start "T__20"
    public final void mT__20() throws RecognitionException {
        try {
            int _type = T__20;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:50:7: ( ',' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:50:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__20"

    // $ANTLR start "T__21"
    public final void mT__21() throws RecognitionException {
        try {
            int _type = T__21;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:51:7: ( '-' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:51:9: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__21"

    // $ANTLR start "T__22"
    public final void mT__22() throws RecognitionException {
        try {
            int _type = T__22;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:52:7: ( '.' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:52:9: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__22"

    // $ANTLR start "T__23"
    public final void mT__23() throws RecognitionException {
        try {
            int _type = T__23;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:53:7: ( '/' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:53:9: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__23"

    // $ANTLR start "T__24"
    public final void mT__24() throws RecognitionException {
        try {
            int _type = T__24;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:54:7: ( ':' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:54:9: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__24"

    // $ANTLR start "T__25"
    public final void mT__25() throws RecognitionException {
        try {
            int _type = T__25;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:55:7: ( ';' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:55:9: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__25"

    // $ANTLR start "T__26"
    public final void mT__26() throws RecognitionException {
        try {
            int _type = T__26;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:56:7: ( '<' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:56:9: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__26"

    // $ANTLR start "T__27"
    public final void mT__27() throws RecognitionException {
        try {
            int _type = T__27;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:57:7: ( '<=' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:57:9: '<='
            {
            match("<="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__27"

    // $ANTLR start "T__28"
    public final void mT__28() throws RecognitionException {
        try {
            int _type = T__28;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:58:7: ( '=' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:58:9: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__28"

    // $ANTLR start "T__29"
    public final void mT__29() throws RecognitionException {
        try {
            int _type = T__29;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:59:7: ( '==' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:59:9: '=='
            {
            match("=="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__29"

    // $ANTLR start "T__30"
    public final void mT__30() throws RecognitionException {
        try {
            int _type = T__30;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:60:7: ( '>' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:60:9: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__30"

    // $ANTLR start "T__31"
    public final void mT__31() throws RecognitionException {
        try {
            int _type = T__31;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:61:7: ( '>=' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:61:9: '>='
            {
            match(">="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__31"

    // $ANTLR start "T__32"
    public final void mT__32() throws RecognitionException {
        try {
            int _type = T__32;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:62:7: ( '@' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:62:9: '@'
            {
            match('@'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__32"

    // $ANTLR start "T__33"
    public final void mT__33() throws RecognitionException {
        try {
            int _type = T__33;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:63:7: ( 'String' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:63:9: 'String'
            {
            match("String"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__33"

    // $ANTLR start "T__34"
    public final void mT__34() throws RecognitionException {
        try {
            int _type = T__34;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:64:7: ( '[' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:64:9: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__34"

    // $ANTLR start "T__35"
    public final void mT__35() throws RecognitionException {
        try {
            int _type = T__35;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:65:7: ( ']' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:65:9: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__35"

    // $ANTLR start "T__36"
    public final void mT__36() throws RecognitionException {
        try {
            int _type = T__36;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:66:7: ( 'break' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:66:9: 'break'
            {
            match("break"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__36"

    // $ANTLR start "T__37"
    public final void mT__37() throws RecognitionException {
        try {
            int _type = T__37;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:67:7: ( 'byte' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:67:9: 'byte'
            {
            match("byte"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__37"

    // $ANTLR start "T__38"
    public final void mT__38() throws RecognitionException {
        try {
            int _type = T__38;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:68:7: ( 'char' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:68:9: 'char'
            {
            match("char"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__38"

    // $ANTLR start "T__39"
    public final void mT__39() throws RecognitionException {
        try {
            int _type = T__39;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:69:7: ( 'continue' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:69:9: 'continue'
            {
            match("continue"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__39"

    // $ANTLR start "T__40"
    public final void mT__40() throws RecognitionException {
        try {
            int _type = T__40;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:70:7: ( 'def' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:70:9: 'def'
            {
            match("def"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__40"

    // $ANTLR start "T__41"
    public final void mT__41() throws RecognitionException {
        try {
            int _type = T__41;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:71:7: ( 'double' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:71:9: 'double'
            {
            match("double"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__41"

    // $ANTLR start "T__42"
    public final void mT__42() throws RecognitionException {
        try {
            int _type = T__42;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:72:7: ( 'else' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:72:9: 'else'
            {
            match("else"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__42"

    // $ANTLR start "T__43"
    public final void mT__43() throws RecognitionException {
        try {
            int _type = T__43;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:73:7: ( 'float' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:73:9: 'float'
            {
            match("float"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__43"

    // $ANTLR start "T__44"
    public final void mT__44() throws RecognitionException {
        try {
            int _type = T__44;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:74:7: ( 'flow' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:74:9: 'flow'
            {
            match("flow"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__44"

    // $ANTLR start "T__45"
    public final void mT__45() throws RecognitionException {
        try {
            int _type = T__45;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:75:7: ( 'for' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:75:9: 'for'
            {
            match("for"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__45"

    // $ANTLR start "T__46"
    public final void mT__46() throws RecognitionException {
        try {
            int _type = T__46;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:76:7: ( 'if' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:76:9: 'if'
            {
            match("if"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__46"

    // $ANTLR start "T__47"
    public final void mT__47() throws RecognitionException {
        try {
            int _type = T__47;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:77:7: ( 'int' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:77:9: 'int'
            {
            match("int"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__47"

    // $ANTLR start "T__48"
    public final void mT__48() throws RecognitionException {
        try {
            int _type = T__48;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:78:7: ( 'long' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:78:9: 'long'
            {
            match("long"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__48"

    // $ANTLR start "T__49"
    public final void mT__49() throws RecognitionException {
        try {
            int _type = T__49;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:79:7: ( 'return' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:79:9: 'return'
            {
            match("return"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__49"

    // $ANTLR start "T__50"
    public final void mT__50() throws RecognitionException {
        try {
            int _type = T__50;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:80:7: ( 'void' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:80:9: 'void'
            {
            match("void"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__50"

    // $ANTLR start "T__51"
    public final void mT__51() throws RecognitionException {
        try {
            int _type = T__51;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:81:7: ( 'while' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:81:9: 'while'
            {
            match("while"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__51"

    // $ANTLR start "T__52"
    public final void mT__52() throws RecognitionException {
        try {
            int _type = T__52;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:82:7: ( '{' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:82:9: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__52"

    // $ANTLR start "T__53"
    public final void mT__53() throws RecognitionException {
        try {
            int _type = T__53;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:83:7: ( '||' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:83:9: '||'
            {
            match("||"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__53"

    // $ANTLR start "T__54"
    public final void mT__54() throws RecognitionException {
        try {
            int _type = T__54;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:84:7: ( '}' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:84:9: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__54"

    // $ANTLR start "IDENT"
    public final void mIDENT() throws RecognitionException {
        try {
            int _type = IDENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:350:6: ( ( 'a' .. 'z' | 'A' .. 'Z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:350:8: ( 'a' .. 'z' | 'A' .. 'Z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            {
            if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:350:29: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0 >= '0' && LA1_0 <= '9')||(LA1_0 >= 'A' && LA1_0 <= 'Z')||LA1_0=='_'||(LA1_0 >= 'a' && LA1_0 <= 'z')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "IDENT"

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:352:8: ( '\"' ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '\\\\' | '\\'' | '\\\"' | '/' | ':' | '@' | '#' | '$' | '%' | '.' )* '\"' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:352:10: '\"' ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '\\\\' | '\\'' | '\\\"' | '/' | ':' | '@' | '#' | '$' | '%' | '.' )* '\"'
            {
            match('\"'); 

            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:352:14: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '\\\\' | '\\'' | '\\\"' | '/' | ':' | '@' | '#' | '$' | '%' | '.' )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0=='\"') ) {
                    int LA2_1 = input.LA(2);

                    if ( ((LA2_1 >= '\"' && LA2_1 <= '%')||LA2_1=='\''||(LA2_1 >= '.' && LA2_1 <= ':')||(LA2_1 >= '@' && LA2_1 <= 'Z')||LA2_1=='\\'||(LA2_1 >= 'a' && LA2_1 <= 'z')) ) {
                        alt2=1;
                    }


                }
                else if ( ((LA2_0 >= '#' && LA2_0 <= '%')||LA2_0=='\''||(LA2_0 >= '.' && LA2_0 <= ':')||(LA2_0 >= '@' && LA2_0 <= 'Z')||LA2_0=='\\'||(LA2_0 >= 'a' && LA2_0 <= 'z')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:
            	    {
            	    if ( (input.LA(1) >= '\"' && input.LA(1) <= '%')||input.LA(1)=='\''||(input.LA(1) >= '.' && input.LA(1) <= ':')||(input.LA(1) >= '@' && input.LA(1) <= 'Z')||input.LA(1)=='\\'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            match('\"'); 

            setText(getText().substring(1,getText().length() -2));

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "STRING"

    // $ANTLR start "WHITESPACE"
    public final void mWHITESPACE() throws RecognitionException {
        try {
            int _type = WHITESPACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:354:12: ( ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+ )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:354:14: ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+
            {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:354:14: ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0 >= '\t' && LA3_0 <= '\n')||(LA3_0 >= '\f' && LA3_0 <= '\r')||LA3_0==' ') ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:
            	    {
            	    if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||(input.LA(1) >= '\f' && input.LA(1) <= '\r')||input.LA(1)==' ' ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt3 >= 1 ) break loop3;
                        EarlyExitException eee =
                            new EarlyExitException(3, input);
                        throw eee;
                }
                cnt3++;
            } while (true);


             _channel = HIDDEN; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WHITESPACE"

    // $ANTLR start "INTEGER"
    public final void mINTEGER() throws RecognitionException {
        try {
            int _type = INTEGER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:356:8: ( ( '0' .. '9' )* )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:356:10: ( '0' .. '9' )*
            {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:356:10: ( '0' .. '9' )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0 >= '0' && LA4_0 <= '9')) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "INTEGER"

    // $ANTLR start "FLOAT"
    public final void mFLOAT() throws RecognitionException {
        try {
            int _type = FLOAT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:358:6: ( ( '0' .. '9' )* '.' '0' .. '9' '0' .. '9' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:358:8: ( '0' .. '9' )* '.' '0' .. '9' '0' .. '9'
            {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:358:8: ( '0' .. '9' )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0 >= '0' && LA5_0 <= '9')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            match('.'); 

            matchRange('0','9'); 

            matchRange('0','9'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "FLOAT"

    // $ANTLR start "INCREMENTER"
    public final void mINCREMENTER() throws RecognitionException {
        try {
            int _type = INCREMENTER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:360:12: ( ( '++' | '--' ) )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:360:14: ( '++' | '--' )
            {
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:360:14: ( '++' | '--' )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0=='+') ) {
                alt6=1;
            }
            else if ( (LA6_0=='-') ) {
                alt6=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;

            }
            switch (alt6) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:360:15: '++'
                    {
                    match("++"); 



                    }
                    break;
                case 2 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:360:21: '--'
                    {
                    match("--"); 



                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "INCREMENTER"

    // $ANTLR start "COMMENT"
    public final void mCOMMENT() throws RecognitionException {
        try {
            int _type = COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:362:9: ( '/*' ( . )* '*/' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:362:11: '/*' ( . )* '*/'
            {
            match("/*"); 



            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:362:16: ( . )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0=='*') ) {
                    int LA7_1 = input.LA(2);

                    if ( (LA7_1=='/') ) {
                        alt7=2;
                    }
                    else if ( ((LA7_1 >= '\u0000' && LA7_1 <= '.')||(LA7_1 >= '0' && LA7_1 <= '\uFFFF')) ) {
                        alt7=1;
                    }


                }
                else if ( ((LA7_0 >= '\u0000' && LA7_0 <= ')')||(LA7_0 >= '+' && LA7_0 <= '\uFFFF')) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:362:16: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);


            match("*/"); 



            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMMENT"

    // $ANTLR start "LINE_COMMENT"
    public final void mLINE_COMMENT() throws RecognitionException {
        try {
            int _type = LINE_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:364:14: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' )
            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:364:16: '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
            {
            match("//"); 



            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:364:21: (~ ( '\\n' | '\\r' ) )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( ((LA8_0 >= '\u0000' && LA8_0 <= '\t')||(LA8_0 >= '\u000B' && LA8_0 <= '\f')||(LA8_0 >= '\u000E' && LA8_0 <= '\uFFFF')) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:
            	    {
            	    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\f')||(input.LA(1) >= '\u000E' && input.LA(1) <= '\uFFFF') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);


            // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:364:35: ( '\\r' )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0=='\r') ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:364:35: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }


            match('\n'); 

            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LINE_COMMENT"

    public void mTokens() throws RecognitionException {
        // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:8: ( T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | IDENT | STRING | WHITESPACE | INTEGER | FLOAT | INCREMENTER | COMMENT | LINE_COMMENT )
        int alt10=51;
        alt10 = dfa10.predict(input);
        switch (alt10) {
            case 1 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:10: T__12
                {
                mT__12(); 


                }
                break;
            case 2 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:16: T__13
                {
                mT__13(); 


                }
                break;
            case 3 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:22: T__14
                {
                mT__14(); 


                }
                break;
            case 4 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:28: T__15
                {
                mT__15(); 


                }
                break;
            case 5 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:34: T__16
                {
                mT__16(); 


                }
                break;
            case 6 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:40: T__17
                {
                mT__17(); 


                }
                break;
            case 7 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:46: T__18
                {
                mT__18(); 


                }
                break;
            case 8 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:52: T__19
                {
                mT__19(); 


                }
                break;
            case 9 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:58: T__20
                {
                mT__20(); 


                }
                break;
            case 10 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:64: T__21
                {
                mT__21(); 


                }
                break;
            case 11 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:70: T__22
                {
                mT__22(); 


                }
                break;
            case 12 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:76: T__23
                {
                mT__23(); 


                }
                break;
            case 13 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:82: T__24
                {
                mT__24(); 


                }
                break;
            case 14 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:88: T__25
                {
                mT__25(); 


                }
                break;
            case 15 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:94: T__26
                {
                mT__26(); 


                }
                break;
            case 16 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:100: T__27
                {
                mT__27(); 


                }
                break;
            case 17 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:106: T__28
                {
                mT__28(); 


                }
                break;
            case 18 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:112: T__29
                {
                mT__29(); 


                }
                break;
            case 19 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:118: T__30
                {
                mT__30(); 


                }
                break;
            case 20 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:124: T__31
                {
                mT__31(); 


                }
                break;
            case 21 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:130: T__32
                {
                mT__32(); 


                }
                break;
            case 22 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:136: T__33
                {
                mT__33(); 


                }
                break;
            case 23 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:142: T__34
                {
                mT__34(); 


                }
                break;
            case 24 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:148: T__35
                {
                mT__35(); 


                }
                break;
            case 25 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:154: T__36
                {
                mT__36(); 


                }
                break;
            case 26 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:160: T__37
                {
                mT__37(); 


                }
                break;
            case 27 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:166: T__38
                {
                mT__38(); 


                }
                break;
            case 28 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:172: T__39
                {
                mT__39(); 


                }
                break;
            case 29 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:178: T__40
                {
                mT__40(); 


                }
                break;
            case 30 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:184: T__41
                {
                mT__41(); 


                }
                break;
            case 31 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:190: T__42
                {
                mT__42(); 


                }
                break;
            case 32 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:196: T__43
                {
                mT__43(); 


                }
                break;
            case 33 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:202: T__44
                {
                mT__44(); 


                }
                break;
            case 34 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:208: T__45
                {
                mT__45(); 


                }
                break;
            case 35 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:214: T__46
                {
                mT__46(); 


                }
                break;
            case 36 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:220: T__47
                {
                mT__47(); 


                }
                break;
            case 37 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:226: T__48
                {
                mT__48(); 


                }
                break;
            case 38 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:232: T__49
                {
                mT__49(); 


                }
                break;
            case 39 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:238: T__50
                {
                mT__50(); 


                }
                break;
            case 40 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:244: T__51
                {
                mT__51(); 


                }
                break;
            case 41 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:250: T__52
                {
                mT__52(); 


                }
                break;
            case 42 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:256: T__53
                {
                mT__53(); 


                }
                break;
            case 43 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:262: T__54
                {
                mT__54(); 


                }
                break;
            case 44 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:268: IDENT
                {
                mIDENT(); 


                }
                break;
            case 45 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:274: STRING
                {
                mSTRING(); 


                }
                break;
            case 46 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:281: WHITESPACE
                {
                mWHITESPACE(); 


                }
                break;
            case 47 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:292: INTEGER
                {
                mINTEGER(); 


                }
                break;
            case 48 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:300: FLOAT
                {
                mFLOAT(); 


                }
                break;
            case 49 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:306: INCREMENTER
                {
                mINCREMENTER(); 


                }
                break;
            case 50 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:318: COMMENT
                {
                mCOMMENT(); 


                }
                break;
            case 51 :
                // /home/brett/Development/dipforge/dipforge/libs/LeviathanScriptEngine/src/com/rift/dipforge/ls/parser/Leviathan.g:1:326: LINE_COMMENT
                {
                mLINE_COMMENT(); 


                }
                break;

        }

    }


    protected DFA10 dfa10 = new DFA10(this);
    static final String DFA10_eotS =
        "\1\46\1\50\5\uffff\1\52\1\uffff\1\53\1\55\1\60\2\uffff\1\62\1\64"+
        "\1\66\1\uffff\1\42\2\uffff\12\42\6\uffff\1\46\21\uffff\12\42\1\121"+
        "\12\42\1\134\3\42\1\141\1\uffff\1\142\6\42\1\151\1\152\1\42\1\uffff"+
        "\1\42\1\155\1\42\1\157\2\uffff\1\160\1\42\1\162\2\42\1\165\2\uffff"+
        "\2\42\1\uffff\1\170\2\uffff\1\42\1\uffff\1\172\1\173\1\uffff\1\42"+
        "\1\175\1\uffff\1\176\2\uffff\1\42\2\uffff\1\u0080\1\uffff";
    static final String DFA10_eofS =
        "\u0081\uffff";
    static final String DFA10_minS =
        "\1\11\1\75\5\uffff\1\53\1\uffff\1\55\1\60\1\52\2\uffff\3\75\1\uffff"+
        "\1\164\2\uffff\1\162\1\150\1\145\2\154\1\146\1\157\1\145\1\157\1"+
        "\150\6\uffff\1\56\21\uffff\1\162\1\145\1\164\1\141\1\156\1\146\1"+
        "\165\1\163\1\157\1\162\1\60\1\164\1\156\1\164\3\151\1\141\1\145"+
        "\1\162\1\164\1\60\1\142\1\145\1\141\1\60\1\uffff\1\60\1\147\1\165"+
        "\1\144\1\154\1\156\1\153\2\60\1\151\1\uffff\1\154\1\60\1\164\1\60"+
        "\2\uffff\1\60\1\162\1\60\1\145\1\147\1\60\2\uffff\1\156\1\145\1"+
        "\uffff\1\60\2\uffff\1\156\1\uffff\2\60\1\uffff\1\165\1\60\1\uffff"+
        "\1\60\2\uffff\1\145\2\uffff\1\60\1\uffff";
    static final String DFA10_maxS =
        "\1\175\1\75\5\uffff\1\53\1\uffff\1\55\1\71\1\57\2\uffff\3\75\1\uffff"+
        "\1\164\2\uffff\1\171\2\157\1\154\1\157\1\156\1\157\1\145\1\157\1"+
        "\150\6\uffff\1\71\21\uffff\1\162\1\145\1\164\1\141\1\156\1\146\1"+
        "\165\1\163\1\157\1\162\1\172\1\164\1\156\1\164\3\151\1\141\1\145"+
        "\1\162\1\164\1\172\1\142\1\145\1\167\1\172\1\uffff\1\172\1\147\1"+
        "\165\1\144\1\154\1\156\1\153\2\172\1\151\1\uffff\1\154\1\172\1\164"+
        "\1\172\2\uffff\1\172\1\162\1\172\1\145\1\147\1\172\2\uffff\1\156"+
        "\1\145\1\uffff\1\172\2\uffff\1\156\1\uffff\2\172\1\uffff\1\165\1"+
        "\172\1\uffff\1\172\2\uffff\1\145\2\uffff\1\172\1\uffff";
    static final String DFA10_acceptS =
        "\2\uffff\1\3\1\4\1\5\1\6\1\7\1\uffff\1\11\3\uffff\1\15\1\16\3\uffff"+
        "\1\25\1\uffff\1\27\1\30\12\uffff\1\51\1\52\1\53\1\54\1\55\1\56\1"+
        "\uffff\1\57\1\2\1\1\1\61\1\10\1\12\1\60\1\13\1\62\1\63\1\14\1\20"+
        "\1\17\1\22\1\21\1\24\1\23\32\uffff\1\43\12\uffff\1\35\4\uffff\1"+
        "\42\1\44\6\uffff\1\32\1\33\2\uffff\1\37\1\uffff\1\41\1\45\1\uffff"+
        "\1\47\2\uffff\1\31\2\uffff\1\40\1\uffff\1\50\1\26\1\uffff\1\36\1"+
        "\46\1\uffff\1\34";
    static final String DFA10_specialS =
        "\u0081\uffff}>";
    static final String[] DFA10_transitionS = {
            "\2\44\1\uffff\2\44\22\uffff\1\44\1\1\1\43\2\uffff\1\2\1\3\1"+
            "\uffff\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\12\45\1\14\1\15\1"+
            "\16\1\17\1\20\1\uffff\1\21\22\42\1\22\7\42\1\23\1\uffff\1\24"+
            "\3\uffff\1\42\1\25\1\26\1\27\1\30\1\31\2\42\1\32\2\42\1\33\5"+
            "\42\1\34\3\42\1\35\1\36\3\42\1\37\1\40\1\41",
            "\1\47",
            "",
            "",
            "",
            "",
            "",
            "\1\51",
            "",
            "\1\51",
            "\12\54",
            "\1\56\4\uffff\1\57",
            "",
            "",
            "\1\61",
            "\1\63",
            "\1\65",
            "",
            "\1\67",
            "",
            "",
            "\1\70\6\uffff\1\71",
            "\1\72\6\uffff\1\73",
            "\1\74\11\uffff\1\75",
            "\1\76",
            "\1\77\2\uffff\1\100",
            "\1\101\7\uffff\1\102",
            "\1\103",
            "\1\104",
            "\1\105",
            "\1\106",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\54\1\uffff\12\45",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\107",
            "\1\110",
            "\1\111",
            "\1\112",
            "\1\113",
            "\1\114",
            "\1\115",
            "\1\116",
            "\1\117",
            "\1\120",
            "\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "\1\122",
            "\1\123",
            "\1\124",
            "\1\125",
            "\1\126",
            "\1\127",
            "\1\130",
            "\1\131",
            "\1\132",
            "\1\133",
            "\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "\1\135",
            "\1\136",
            "\1\137\25\uffff\1\140",
            "\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "",
            "\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "\1\143",
            "\1\144",
            "\1\145",
            "\1\146",
            "\1\147",
            "\1\150",
            "\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "\1\153",
            "",
            "\1\154",
            "\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "\1\156",
            "\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "",
            "",
            "\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "\1\161",
            "\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "\1\163",
            "\1\164",
            "\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "",
            "",
            "\1\166",
            "\1\167",
            "",
            "\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "",
            "",
            "\1\171",
            "",
            "\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "",
            "\1\174",
            "\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "",
            "\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "",
            "",
            "\1\177",
            "",
            "",
            "\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            ""
    };

    static final short[] DFA10_eot = DFA.unpackEncodedString(DFA10_eotS);
    static final short[] DFA10_eof = DFA.unpackEncodedString(DFA10_eofS);
    static final char[] DFA10_min = DFA.unpackEncodedStringToUnsignedChars(DFA10_minS);
    static final char[] DFA10_max = DFA.unpackEncodedStringToUnsignedChars(DFA10_maxS);
    static final short[] DFA10_accept = DFA.unpackEncodedString(DFA10_acceptS);
    static final short[] DFA10_special = DFA.unpackEncodedString(DFA10_specialS);
    static final short[][] DFA10_transition;

    static {
        int numStates = DFA10_transitionS.length;
        DFA10_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA10_transition[i] = DFA.unpackEncodedString(DFA10_transitionS[i]);
        }
    }

    class DFA10 extends DFA {

        public DFA10(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 10;
            this.eot = DFA10_eot;
            this.eof = DFA10_eof;
            this.min = DFA10_min;
            this.max = DFA10_max;
            this.accept = DFA10_accept;
            this.special = DFA10_special;
            this.transition = DFA10_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | IDENT | STRING | WHITESPACE | INTEGER | FLOAT | INCREMENTER | COMMENT | LINE_COMMENT );";
        }
    }
 

}