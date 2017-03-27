package tree.statement;

import codegeneration.ExpressionCode;
import tree.expression.ExpressionNode;

/**
 * Created by jpaz on 3/22/17.
 */
public class ScanNode extends StatementNode {

    public ExpressionNode varu;

    public ScanNode(ExpressionNode e) {
        this.varu = e;
    }

    @Override
    public void evaluate() {

    }

    @Override
    public String generateCode() {
        ExpressionCode varCode = varu.GenerateCode();
        String name = varCode.getDestination().replace("[","").replace("]","");
        String toReturn = "push " + name + "\n" +
                          "push @satan\n" +
                          "call [scanf]\n" +
                          "add esp, 8\n";
        return toReturn;
    }
}
