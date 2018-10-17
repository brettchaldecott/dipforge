/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.burntjam.dipforge.robotics.web.antlr.base;


import org.antlr.v4.runtime.*;

import java.util.Stack;

/**
 *
 * @author ubuntu
 */
public abstract class WebRoboticsLexerBase extends Lexer
{
    /**
     * Stores values of nested modes. By default mode is strict or
     * defined externally (useStrictDefault)
     */
    private Stack<Boolean> scopeStrictModes = new Stack<Boolean>();

    private Token lastToken = null;
    /**
     * Default value of strict mode
     * Can be defined externally by setUseStrictDefault
     */
    private boolean useStrictDefault = false;
    /**
     * Current value of strict mode
     * Can be defined during parsing, see StringFunctions.js and StringGlobal.js samples
     */
    private boolean useStrictCurrent = false;

    public WebRoboticsLexerBase(CharStream input) {
        super(input);
    }

    public boolean getStrictDefault() {
        return useStrictDefault;
    }

    public void setUseStrictDefault(boolean value) {
        useStrictDefault = value;
        useStrictCurrent = value;
    }

    public boolean IsSrictMode() {
        return useStrictCurrent;
    }

    /**
     * Return the next token from the character stream and records this last
     * token in case it resides on the default channel. This recorded token
     * is used to determine when the lexer could possibly match a regex
     * literal. Also changes scopeStrictModes stack if tokenize special
     * string 'use strict';
     *
     * @return the next token from the character stream.
     */
    @Override
    public Token nextToken() {
        Token next = super.nextToken();

        if (next.getChannel() == Token.DEFAULT_CHANNEL) {
            // Keep track of the last token on the default channel.
            this.lastToken = next;
        }

        return next;
    }

    protected void ProcessOpenBrace()
    {
        useStrictCurrent = scopeStrictModes.size() > 0 && scopeStrictModes.peek() ? true : useStrictDefault;
        scopeStrictModes.push(useStrictCurrent);
    }

    protected void ProcessCloseBrace()
    {
        useStrictCurrent = scopeStrictModes.size() > 0 ? scopeStrictModes.pop() : useStrictDefault;
    }

    protected void ProcessStringLiteral()
    {
        if (lastToken == null || lastToken.getType() == WebRoboticsLexer.OpenBrace)
        {
            String text = getText();
            if (text.equals("\"use strict\"") || text.equals("'use strict'"))
            {
                if (scopeStrictModes.size() > 0)
                    scopeStrictModes.pop();
                useStrictCurrent = true;
                scopeStrictModes.push(useStrictCurrent);
            }
        }
    }

    /**
     * Returns {@code true} if the lexer can match a regex literal.
     */
    protected boolean IsRegexPossible() {
                                       
        if (this.lastToken == null) {
            // No token has been produced yet: at the start of the input,
            // no division is possible, so a regex literal _is_ possible.
            return true;
        }
        
        switch (this.lastToken.getType()) {
            case WebRoboticsLexer.Identifier:
            case WebRoboticsLexer.NullLiteral:
            case WebRoboticsLexer.BooleanLiteral:
            case WebRoboticsLexer.This:
            case WebRoboticsLexer.CloseBracket:
            case WebRoboticsLexer.CloseParen:
            case WebRoboticsLexer.OctalIntegerLiteral:
            case WebRoboticsLexer.DecimalLiteral:
            case WebRoboticsLexer.HexIntegerLiteral:
            case WebRoboticsLexer.StringLiteral:
            case WebRoboticsLexer.PlusPlus:
            case WebRoboticsLexer.MinusMinus:
                // After any of the tokens above, no regex literal can follow.
                return false;
            default:
                // In all other cases, a regex literal _is_ possible.
                return true;
        }
    }
}
