/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the "License").  You may not use this file except
 * in compliance with the License.
 * 
 * You can obtain a copy of the license at
 * https://jwsdp.dev.java.net/CDDLv1.0.html
 * See the License for the specific language governing
 * permissions and limitations under the License.
 * 
 * When distributing Covered Code, include this CDDL
 * HEADER in each file and include the License file at
 * https://jwsdp.dev.java.net/CDDLv1.0.html  If applicable,
 * add the following below this CDDL HEADER, with the
 * fields enclosed by brackets "[]" replaced with your
 * own identifying information: Portions Copyright [yyyy]
 * [name of copyright owner]
 */

package com.sun.codemodel;


/**
 * A return statement
 */
class JReturn implements JStatement {

    /**
     * JExpression to return; may be null.
     */
    private JExpression expr;

    /**
     * JReturn constructor
     *
     * @param expr
     *        JExpression which evaluates to return value
     */
    JReturn(JExpression expr) {
       this.expr = expr;
    }

    public void state(JFormatter f) {
        f.p("return ");
        if (expr != null) f.g(expr);
        f.p(';').nl();
    }

}