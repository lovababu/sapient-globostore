package com.sapient.globostore.constant;

import javax.script.Bindings;

/**
 * Expressions used by <code>{@link javax.script.ScriptEngine#eval(String, Bindings)}</code> to evaluate
 *
 * Created by dpadal on 12/13/2016.
 */
public final class Expressions {

    public static final String EXPRESSION_QUANTITIVE = "(Math.floor(Q/DQ) * DP) + ((Q%DQ) * UP)";
    public static final String EXPRESSION_PERCENTAGE = "UP * DP/100";
    public static final String EXPRESSION_TOTAL = "(Q * UP)";

}
