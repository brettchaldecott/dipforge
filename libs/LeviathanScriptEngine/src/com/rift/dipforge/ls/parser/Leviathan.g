grammar Leviathan;

options {
  language = Java;
}



@header {
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
}

@lexer::header {
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
}

@members {
  
  private Workflow workflow;
  private String workflowName;
  private Block currentBlock;
  
  
  
  public Workflow getWorkflow() {
    return workflow;
  }
}

workflow returns [Workflow value]
  : {
    $value = new Workflow();
    currentBlock = $value;
    }
    (define {$value.getDefinedTypes().add($define.value);})*
   (anno=annotation {$value.getAnnotations().add($anno.value);} )*
   'flow' flowName=IDENT { $value.setName($flowName.text);}
   '{'
   (variable (assignment {
      $variable.value.setInitialValue($assignment.value);
    })? {
      $value.getStatements().add($variable.value);})*
    (meth1=method {$value.getStatements().add(meth1);})*
     blo=block {
     $value.getStatements().add(blo);
     }
    (meth2=method {
    $value.getStatements().add(meth2);
    })*
   '}' { 
      workflow = $value;};

define returns [TypeDefinition value]
  : {$value = new TypeDefinition();} 'define' STRING {
      $value.setUri($STRING.text);
    } 'as' IDENT {
      $value.setName($IDENT.text);
    };

annotation returns [Annotation value]
  : '@' name=IDENT '(' (annotationList=list) ')' 
  {
  $value = new Annotation($name.text,annotationList);};

statement returns [Statement value]
  : (
  varCall=callStatement {$value = varCall;} (ass1=assignment {varCall.setAssignment(ass1);})? ';'? 
  | var=variable {$value = var;} (ass2=assignment {var.setInitialValue(ass2);})? ';'? 
  | ifStat=ifStatement {$value = ifStat;}
  | whileStat=whileStatement {$value = whileStat;}
  | forStat=forStatement {$value = forStat;}
  | caseStatement
  | bl=block {$value = bl;}
  | ret=returnStatement {$value = ret;}
  | 'continue' {$value = new ContinueStatement(); }
  | 'break' {$value = new BreakStatement(); } );

block returns [Block value]
  : '{' {
      $value = new Block();
      $value.setParent(currentBlock);
      currentBlock = $value;
      } (statement { $value.getStatements().add($statement.value);} )* '}'
      {
      currentBlock = currentBlock.getParent();
      };

ifStatement returns [IfStatement value]
  : {$value = new IfStatement();}'if' '(' exp1=expression ')' bl1=block {
      $value.addBlock(IfStatement.IfStatementType.IF, exp1, bl1); }
    ('else' 'if' '(' exp2=expression ')' bl2=block {
      $value.addBlock(IfStatement.IfStatementType.ELSE_IF, exp2, bl2); })*
    ('else' bl3=block {
      $value.addBlock(bl3); })?;

whileStatement returns [WhileStatement value]
  : {$value = new WhileStatement();}'while' '(' exp1=expression ')' bl1=block {
    $value.setExpression(exp1);
    $value.setBlock(bl1);};

forStatement returns [ForStatement value]
  : {
  $value = new ForStatement();
  $value.setParent(currentBlock);
  currentBlock = $value;}'for' '(' (var=variable (ass1=assignment {
  var.setInitialValue(ass1);
  $value.setInitialValue(var);
  })? )? ';' exp1=expression {
  $value.setComparison(exp1);
  } ';'  ( exp2=expression {
  $value.setIncrement(exp2);
  })? ')' bl=block {$value.setChild(bl);};
  
caseStatement returns [CaseStatement value]
 : {
    $value = new CaseStatement();
   }'switch' '(' exp1=expression {
    $value.setExpression(exp1);
   } ')' '{'
  ('case' exp1=expression ':' bl1=block {$value.addBlock(exp1,bl1);})*
  ('default' ':' block {$value.addBlock(null,bl1);})? ;

variable returns [Variable value]
  : {$value = new Variable();} t1=type IDENT {
    $value.setType(t1);
    $value.setName($IDENT.text);
  };
  
callStatement returns [CallStatement value]
  : {
    $value = new CallStatement();
    CallStatement.CallStatementEntry currentEntry = null;
  } s1=IDENT {
    currentEntry = $value.addEntry($s1.text);
  } (lsArg1=listArgument {currentEntry.setArgument(lsArg1);} | paramArg1=parameterArgument {currentEntry.setArgument(paramArg1);}) ? ('.' s2=IDENT {
    currentEntry = $value.addEntry($s2.text);
  } (lsArg2=listArgument {currentEntry.setArgument(lsArg2);} | paramArg2=parameterArgument {currentEntry.setArgument(paramArg2);}) ? )*;

listArgument returns [LsListArgument value]
  : {$value = new LsListArgument();} '[' exp=expression {$value.setExpression(exp); }']';

parameterArgument returns [ParameterArgument value]
  : {$value = new ParameterArgument(); } '(' (exp1=expression {$value.addExpression(exp1);} (',' exp2=expression {$value.addExpression(exp2);})*)? ')';
  
method returns [MethodDefinition value]
  : {
	  $value = new MethodDefinition();
	  $value.setParent(currentBlock);
	  currentBlock = $value;
  } t1=type {
    $value.setType(t1);
  } name=IDENT {
    $value.setName($name.text);
  }'(' (param=IDENT {
    $value.addParameter($param.text);
  } )* ')' bl1=block {$value.getStatements().add(bl1);}  {
    currentBlock = $value.getParent();
  };

assignment returns [Assignment value]
  : {$value = new Assignment();} ('=' exp=expression {$value.setValue(exp);} | '=' ls=list {$value.setValue(ls);});
  
list returns [LsList value]
  : {$value = new LsList();} '[' key1=expression (':' val1=expression )? {
      if (val1 == null) {
        $value.setValueList(new java.util.ArrayList());
        $value.getValueList().add(key1);
			} else {
			  $value.setValueMap(new java.util.HashMap());
			  $value.getValueMap().put(key1,val1);
      }
    } (',' key2=expression (':' val2=expression)? {
      if ($value.getValueList() != null) {
        if (val2 != null) {
          throw new FailedPredicateException(input, "list", "\n Expected [value] got [key:value].");
        }
        $value.getValueList().add(key2);
      } else {
        if (val2 == null) {
          throw new FailedPredicateException(input, "list", "\n Expected [key:value] got [value].");
        }
        $value.getValueMap().put(key2,val2);
      }
    } )* ']' ;

value returns [Object value]
  : INTEGER { $value = new Integer($INTEGER.text);} 
  | FLOAT { $value = new Float($FLOAT.text); }
  | STRING { $value = $STRING.text; };

term returns [Object value]
  : callStatement {$value = $callStatement.value;}
  | '(' expression {$value = $expression.value;} ')'
  | value {$value = $value.value;}
  ;

increment returns [IncrementStatement value]
  : {
      $value = new IncrementStatement();
    }(inc1=INCREMENTER {
      $value.setOperation($inc1.text);
    })? term {$value.setValue($term.value);} (inc2=INCREMENTER {
      $value.setOperation($inc2.text);
    })?;
  
negation returns [NegationStatement value]
  : {
      $value = new NegationStatement();
    } ('!' {
      $value.setNegation(true);
    })? inc=increment {
      $value.setIncrement($inc.value);
    };

unary returns [UnaryStatement value]
  : {
      $value = new UnaryStatement();
    } ('+' {
      $value.setOperation("+");
    }| '-'{
      $value.setOperation("-");
    })? neg=negation {
      $value.setNegation($neg.value);
    };

mult returns [MultStatement value]
  : {
      $value = new MultStatement();
    } u1=unary {
      $value.setInitialValue($u1.value);
    } ( op=('*' | '/' | '%') u2=unary {
      $value.addBlock($op.text, u2);
    })* ;

add returns [AddStatement value]
  : {
      $value = new AddStatement();
    } m1=mult {
      $value.setInitialValue(m1);
    } ( op=('+' | '-') m2=mult {
      $value.addBlockStatement($op.text, m2);})* ;
  
relation returns [Relation value]
  : {
      $value = new Relation();
    } a1=add {
      $value.setInitialValue(a1);
    } (op=('<'  | '<=' | '>=' | '>') a2=add {
      $value.addBlock($op.text,a2);})? ;

comparison returns [Comparison value]
  : {
      $value = new Comparison();
    } rel1=relation {
      $value.setInitialValue(rel1);
    } (op=('==' | '!=') rel2=relation {
      $value.addBlock($op.text,rel2);
    })*;

expression returns [Expression value]
  : {$value = new Expression();} var1=comparison {
      $value.setInitialValue(var1);
    } (op=('&&' | '||' ) var2=comparison {
      $value.addBlock($op.text,var2);})* ;
  
returnStatement returns [ReturnStatement value]
  : 'return' exp=expression {$value = new ReturnStatement(exp);};
  
type returns [String value]
  : 'void' {$value = Types.VOID;}
  | 'int' {$value = Types.INT;}
  | 'long' {$value = Types.LONG;}
  | 'double' {$value = Types.DOUBLE;}
  | 'float' {$value = Types.FLOAT;}
  | 'char' {$value = Types.CHAR;}
  | 'byte' {$value = Types.BYTE;}
  | 'String' {$value = Types.STRING;}
  | 'def' {$value = Types.DEF;};

IDENT: ('a'..'z' | 'A'..'Z')('a'..'z' | 'A'..'Z' | '0'..'9' | '_')*;

STRING: '"' ('a'..'z' | 'A'..'Z' | '0'..'9' | '\\' | '\'' | '\"' | '/' | ':' | '@' | '#' | '$' | '%' | '.' )* '"';

WHITESPACE : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+    { $channel = HIDDEN; } ;

INTEGER: '0'..'9'* ;

FLOAT: '0'..'9'*'.''0'..'9''0'..'9';

INCREMENTER: ('++'| '--');

COMMENT : '/*' .* '*/' {$channel=HIDDEN;} ;

LINE_COMMENT : '//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;} ;

