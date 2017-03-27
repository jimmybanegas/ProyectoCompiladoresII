package tree.statement;

import codegeneration.ExpressionCode;
import tree.expression.ExpressionNode;

import java.util.List;

/**
 * Created by Jimmy Ramos on 22-Mar-17.
 */
public class IdDeclarationNode extends  StatementNode {

    private String nativeType;

    public IdDeclarationNode( String name, String type ) {
        this.name = name;
        this.setNativeType(type);
    }

    //:(
    private String name;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getNativeType() {
        return nativeType;
    }

    public void setNativeType(String nativeType) {
        this.nativeType = nativeType;
    }

    @Override
    public void evaluate() {
    }

    @Override
    public String generateCode() {
        switch (nativeType){
            case "int":
                return "@"+name+"@" + " dd 0\n";
            case "string":
                return "@"+name+"@" +" db 0\n";
            case "char":
                return "@"+name+"@" +" db 0\n";
            case "float":
                return "@"+name+"@" +" db 0\n";
            case "bool":
                return "@"+name+"@" +" db 0\n";
            default:
                return "";
        }
    }
}
