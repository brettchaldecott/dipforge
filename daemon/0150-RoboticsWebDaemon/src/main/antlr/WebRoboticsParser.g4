parser grammar WebRoboticsParser;

@header {
    package com.burntjam.dipforge.robotics.web.antlr.base;

    import com.burntjam.dipforge.robotics.web.antlr.base.WebRoboticsProgram;
    import com.burntjam.dipforge.robotics.web.antlr.base.WebRoboticsProgramStatement;
    import com.burntjam.dipforge.robotics.web.antlr.base.WebRoboticsProgramStatementFactory;
    import com.burntjam.dipforge.robotics.web.antlr.base.WebRoboticsLiteralUtils;
}

@members {
  
  private WebRoboticsProgram webRoboticsProgram;
  
  public WebRoboticsProgram getWebRoboticsProgram() {
    return webRoboticsProgram;
  }
}


options {
    tokenVocab=WebRoboticsLexer;
    superClass=WebRoboticsParserBase;
}

program returns [WebRoboticsProgram  value]
    :  {
        System.out.println("Init the web robotics program");
        $value = new WebRoboticsProgram();
        webRoboticsProgram = $value;
    } sourceElements? EOF
    ;

sourceElement
    : {
    System.out.println("This source element : ");
    } Export? statement {
    System.out.println("The source element statement : " + $statement.text);
    } 
    ;

statement
    : 
    {
    System.out.println("This is a statement");
    }
    block
    | variableStatement {
        System.out.println("A variable statement is being being called");
        webRoboticsProgram.completeStatementDeclaration();
    }
    | emptyStatement
    | expressionStatement {
        System.out.println("An expression statement is being being called");
        webRoboticsProgram.completeStatementDeclaration();
    }
    | ifStatement
    | iterationStatement
    | continueStatement
    | breakStatement
    | returnStatement
    | withStatement
    | labelledStatement
    | switchStatement
    | throwStatement
    | tryStatement
    | debuggerStatement
    | functionDeclaration
    | classDeclaration
    ;

block
    : '{' statementList? '}'
    ;

statementList
    : statement+ {System.out.println("The statement list is here");}
    ;

variableStatement
    : {
        System.out.println("Add a variable");
    } varModifier variableDeclarationList eos
    ;

variableDeclarationList
    : variableDeclaration (',' variableDeclaration)*
    ;

variableDeclaration
    : {System.out.println("Variable declaration"); 
    }(Identifier {
    System.out.println("Identifier is on : " + $Identifier.text );
    webRoboticsProgram.addVariable($Identifier.text);
    }| arrayLiteral {
    System.out.println("Array litteral");
    webRoboticsProgram.addVariable($arrayLiteral.text);
    } | objectLiteral{
    System.out.println("Object litteral : ");
    webRoboticsProgram.addVariable($objectLiteral.text);
    }) ('=' singleExpression {
        System.out.println("The single expression for the varible declartion");
        webRoboticsProgram.completeStatementDeclaration();
    } )? // ECMAScript 6: Array & Object Matching
    ;

emptyStatement
    : SemiColon
    ;

expressionStatement
    : {notOpenBraceAndNotFunction()}? expressionSequence {System.out.println("This is an expression sequence");} eos
    ;

ifStatement
    : If '(' expressionSequence ')' statement (Else statement)?
    ;


iterationStatement
    : Do statement While '(' expressionSequence ')' eos                                                 # DoStatement
    | While '(' expressionSequence ')' statement                                                        # WhileStatement
    | For '(' expressionSequence? ';' expressionSequence? ';' expressionSequence? ')' statement         # ForStatement
    | For '(' varModifier variableDeclarationList ';' expressionSequence? ';' expressionSequence? ')'
          statement                                                                                     # ForVarStatement
    | For '(' singleExpression (In | Identifier{p("of")}?) expressionSequence ')' statement             # ForInStatement
    | For '(' varModifier variableDeclaration (In | Identifier{p("of")}?) expressionSequence ')' statement      # ForVarInStatement
    ;

varModifier  // let, const - ECMAScript 6
    : Var
    | Let
    | Const
    ;

continueStatement
    : Continue ({notLineTerminator()}? Identifier)? eos
    ;

breakStatement
    : Break ({notLineTerminator()}? Identifier)? eos
    ;

returnStatement
    : Return ({notLineTerminator()}? expressionSequence)? eos
    ;

withStatement
    : With '(' expressionSequence ')' statement
    ;

switchStatement
    : Switch '(' expressionSequence ')' caseBlock
    ;

caseBlock
    : '{' caseClauses? (defaultClause caseClauses?)? '}'
    ;

caseClauses
    : caseClause+
    ;

caseClause
    : Case expressionSequence ':' statementList?
    ;

defaultClause
    : Default ':' statementList?
    ;

labelledStatement
    : Identifier ':' statement
    ;

throwStatement
    : Throw {notLineTerminator()}? expressionSequence eos
    ;

tryStatement
    : Try block (catchProduction finallyProduction? | finallyProduction)
    ;

catchProduction
    : Catch '(' Identifier ')' block
    ;

finallyProduction
    : Finally block
    ;

debuggerStatement
    : Debugger eos
    ;

functionDeclaration
    : Function Identifier '(' formalParameterList? ')' '{' functionBody '}'
    ;

classDeclaration
    : Class Identifier classTail
    ;

classTail
    : (Extends singleExpression)? '{' classElement* '}'
    ;

classElement
    : Static? methodDefinition
    ;

methodDefinition
    : propertyName '(' formalParameterList? ')' '{' functionBody '}'
    | getter '(' ')' '{' functionBody '}'
    | setter '(' formalParameterList? ')' '{' functionBody '}'
    | generatorMethod
    ;

generatorMethod
    : '*'? Identifier '(' formalParameterList? ')' '{' functionBody '}'
    ;

formalParameterList
    : formalParameterArg (',' formalParameterArg)* (',' lastFormalParameterArg)?
    | lastFormalParameterArg
    | arrayLiteral                            // ECMAScript 6: Parameter Context Matching
    | objectLiteral                           // ECMAScript 6: Parameter Context Matching
    ;

formalParameterArg
    : Identifier ('=' singleExpression)?      // ECMAScript 6: Initialization
    ;

lastFormalParameterArg                        // ECMAScript 6: Rest Parameter
    : Ellipsis Identifier
    ;

functionBody
    : sourceElements?
    ;

sourceElements
    : sourceElement+
    ;

arrayLiteral
    : '[' ','* elementList? ','* ']'
    ;

elementList
    : singleExpression (','+ singleExpression)* (','+ lastElement)?
    | lastElement
    ;

lastElement                      // ECMAScript 6: Spread Operator
    : Ellipsis Identifier
    ;

objectLiteral
    : '{' (propertyAssignment (',' propertyAssignment)*)? ','? '}'
    ;

propertyAssignment
    : {System.out.println("The property name"); } propertyName (':' |'=') singleExpression       # PropertyExpressionAssignment
    | '[' singleExpression ']' ':' singleExpression {System.out.println("The single expression : "); } # ComputedPropertyExpressionAssignment
    | {System.out.println("The getter"); } getter '(' ')' '{' functionBody '}'            # PropertyGetter
    | {System.out.println("The setter"); }setter '(' Identifier ')' '{' functionBody '}' # PropertySetter
    | {System.out.println("The generatorMethod"); }generatorMethod                                # MethodProperty
    | {System.out.println("The Identifier"); }Identifier                                     # PropertyShorthand
    ;

propertyName
    : identifierName {System.out.println("The property name is being called :"); }
    | StringLiteral
    | numericLiteral
    ;

arguments
    : '('(
          singleExpression (',' singleExpression)* (',' lastArgument)? |
          lastArgument
       )?')'
    ;

lastArgument                                  // ECMAScript 6: Spread Operator
    : Ellipsis Identifier
    ;

expressionSequence
    : singleExpression (',' singleExpression)*
    ;

singleExpression
    : Function Identifier? '(' formalParameterList? ')' '{' functionBody '}' # FunctionExpression
    | Class Identifier? classTail                                            # ClassExpression
    | singleExpression '[' expressionSequence ']'                            # MemberIndexExpression
    | singleExpression {System.out.println("1 The single expression :");} '.' identifierName       {
        System.out.println("The identifier on the expression : " + $identifierName.text);
        webRoboticsProgram.addFunctionCallToExpression($identifierName.text);
        }                             # MemberDotExpression
    | singleExpression arguments                                             # ArgumentsExpression
    | New singleExpression arguments?                                        # NewExpression
    | singleExpression {notLineTerminator()}? '++'                           # PostIncrementExpression
    | singleExpression {notLineTerminator()}? '--'                           # PostDecreaseExpression
    | Delete singleExpression                                                # DeleteExpression
    | Void singleExpression                                                  # VoidExpression
    | Typeof singleExpression                                                # TypeofExpression
    | '++' singleExpression                                                  # PreIncrementExpression
    | '--' singleExpression                                                  # PreDecreaseExpression
    | '+' singleExpression                                                   # UnaryPlusExpression
    | '-' singleExpression                                                   # UnaryMinusExpression
    | '~' singleExpression                                                   # BitNotExpression
    | '!' singleExpression                                                   # NotExpression
    | singleExpression ('*' | '/' | '%') singleExpression                    # MultiplicativeExpression
    | singleExpression ('+' | '-') singleExpression                          # AdditiveExpression
    | singleExpression ('<<' | '>>' | '>>>') singleExpression                # BitShiftExpression
    | singleExpression ('<' | '>' | '<=' | '>=') singleExpression            # RelationalExpression
    | singleExpression Instanceof singleExpression                           # InstanceofExpression
    | singleExpression In singleExpression                                   # InExpression
    | singleExpression ('==' | '!=' | '===' | '!==') singleExpression        # EqualityExpression
    | singleExpression '&' singleExpression                                  # BitAndExpression
    | singleExpression '^' singleExpression                                  # BitXOrExpression
    | singleExpression '|' singleExpression                                  # BitOrExpression
    | singleExpression '&&' singleExpression                                 # LogicalAndExpression
    | singleExpression '||' singleExpression                                 # LogicalOrExpression
    | singleExpression '?' singleExpression ':' singleExpression             # TernaryExpression
    | singleExpression '=' singleExpression                                  # AssignmentExpression
    | singleExpression assignmentOperator singleExpression                   # AssignmentOperatorExpression
    | singleExpression TemplateStringLiteral                                 # TemplateStringExpression  // ECMAScript 6
    | This                                                                   # ThisExpression
    | Identifier {
        System.out.println("The identifier is being called : " + $Identifier.text);
        webRoboticsProgram.createExpression($Identifier.text);
        }                                                             # IdentifierExpression
    | Super                                                                  # SuperExpression
    | literal                                                                # LiteralExpression
    | arrayLiteral                                                           # ArrayLiteralExpression
    | objectLiteral                                                          # ObjectLiteralExpression
    | '(' expressionSequence ')'                                             # ParenthesizedExpression
    | arrowFunctionParameters '=>' arrowFunctionBody                         # ArrowFunctionExpression   // ECMAScript 6
    ;

arrowFunctionParameters
    : Identifier
    | '(' formalParameterList? ')'
    ;

arrowFunctionBody
    : singleExpression
    | '{' functionBody '}'
    ;

assignmentOperator
    : '*='
    | '/='
    | '%='
    | '+='
    | '-='
    | '<<='
    | '>>='
    | '>>>='
    | '&='
    | '^='
    | '|='
    ;

literal 
    : NullLiteral {
        System.out.println("Null Literal : ");
        webRoboticsProgram.setFunctionArgument(null);
        }
    | BooleanLiteral {
        System.out.println("Boolean Literal : " + $BooleanLiteral.text);
        webRoboticsProgram.setFunctionArgument(WebRoboticsProgramStatementFactory.createBoolLiteralStatement($BooleanLiteral.text));
        }
    | StringLiteral {
        System.out.println("String Literal value [" + $StringLiteral.text + "]");
        String value = WebRoboticsLiteralUtils.trim($StringLiteral.text);
        if (!value.equals("use strict")) {
            System.out.println("Add literal value of : [" + value + "]");
            webRoboticsProgram.setFunctionArgument(WebRoboticsProgramStatementFactory.createStringLiteralStatement(value));
        }
        }
    | TemplateStringLiteral {
        System.out.println("Template String Literal : " + $TemplateStringLiteral.text);
        String value = WebRoboticsLiteralUtils.trim($TemplateStringLiteral.text);
        webRoboticsProgram.setFunctionArgument(WebRoboticsProgramStatementFactory.createStringLiteralStatement(value));
        }
    | RegularExpressionLiteral
    | numericLiteral {
        System.out.println("Nemeric Literal : " + $numericLiteral.text);
        webRoboticsProgram.setFunctionArgument(WebRoboticsProgramStatementFactory.createNumericLiteralStatement($numericLiteral.text));
        }
    ;

numericLiteral
    : DecimalLiteral
    | HexIntegerLiteral
    | OctalIntegerLiteral
    | OctalIntegerLiteral2
    | BinaryIntegerLiteral
    ;

identifierName
    : Identifier{System.out.println("The identifier is : " + $Identifier.text);}
    | reservedWord
    ;

reservedWord
    : keyword
    | NullLiteral
    | BooleanLiteral
    ;

keyword
    : Break
    | Do
    | Instanceof
    | Typeof
    | Case
    | Else
    | New
    | Var
    | Catch
    | Finally
    | Return
    | Void
    | Continue
    | For
    | Switch
    | While
    | Debugger
    | Function
    | This
    | With
    | Default
    | If
    | Throw
    | Delete
    | In
    | Try

    | Class
    | Enum
    | Extends
    | Super
    | Const
    | Export
    | Import
    | Implements
    | Let
    | Private
    | Public
    | Interface
    | Package
    | Protected
    | Static
    | Yield
    ;

getter
    : Identifier{p("get")}? propertyName
    ;

setter
    : Identifier{p("set")}? propertyName
    ;

eos
    : SemiColon
    | EOF
    | {lineTerminatorAhead()}?
    | {closeBrace()}?
    ;