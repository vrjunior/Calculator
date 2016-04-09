/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

/**
 *
 * @author guilherme
 */
public class Expression implements ExpressionContainer {

    private Queue<ExpressionElement> elms;
    private String exp;

    public Expression(String exp) throws Exception {
        if (exp == null) {
            throw new Exception("Invalid Expression (null)");
        }

        // Remove white spaces
        exp = exp.replaceAll(" ", "");

        if (exp.length() <= 0) {
            throw new Exception("Invalid Expression (empty)");
        }

        this.exp = exp;
    }

    public void parseExpression() throws Exception {
        // Expression should be string where the worst case its
        // mapping 1:1 ExpressionElement
        this.elms = new Queue<>(exp.length());

        StringBuilder tempBuilder = new StringBuilder();

        for (char c : this.exp.toCharArray()) {
            ExpressionElement elm = null;

            try {
                elm = new OperatorElement(c);
            } catch (Exception ex) {
            }

            try {
                elm = new ParenthesisElement(c);
            } catch (Exception ex1) {
            }

            if (elm == null) {
                // Is a number
                tempBuilder.append(c);
            } else {
                if (tempBuilder.length() > 0) {
                    double parsed = 0;

                    try {
                        parsed = Double.parseDouble(tempBuilder.toString());
                    } catch (Exception ex) {
                        throw new Exception("Invalid term " + tempBuilder.toString());
                    }

                    elms.enqueue(new NumericElement(parsed));
                    tempBuilder.delete(0, tempBuilder.length());
                }

                elms.enqueue(elm);
            }
        }
    }

    public Queue<ExpressionElement> getExpressionQueue() {
        try {
            return (Queue<ExpressionElement>) elms.clone();
        } catch (CloneNotSupportedException ex) {
            // Cloning a queue is Supported!
            return null;
        }
    }
}