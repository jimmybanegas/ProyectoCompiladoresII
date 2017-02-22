package Automaton;

import java.util.ArrayList;

/**
 * Created by Jimmy Ramos on 18-Feb-17.
 */
public class Production {
    private String production;
    private String leftSide;
    private String rightSide;

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public String getLeftSide() {
        return leftSide;
    }

    public void setLeftSide(String leftSide) {
        this.leftSide = leftSide;
    }

    public String getRightSide() {
        return rightSide;
    }

    public void setRightSide(String rightSide) {
        this.rightSide = rightSide;
    }

    public Production(){ }

    public Production(String production){
        setProduction(production);
        setLeftSide("");
        setRightSide("");
    }

    public Production(Production production){
        setProduction(production.getProduction());
        setLeftSide(production.getLeftSide());
        setRightSide(production.getRightSide());
    }

    public Production(String leftSide, String rightSide){
        setLeftSide(leftSide);
        setRightSide(rightSide);
        setProduction(leftSide + "->" + rightSide);
    }

    public final boolean ValidateProduction(){
        int indexArrowStart;
        int indexArrowEnds = -1;

        if ((indexArrowStart = getProduction().indexOf("->")) != -1)
            indexArrowEnds = indexArrowStart + 1;

        if (indexArrowStart != -1 && indexArrowStart + 1 == indexArrowEnds)
        {
            setLeftSide(getProduction().substring(0, indexArrowStart));
            setRightSide(getProduction().substring(indexArrowEnds + 1));
            if (getLeftSide().equals("")) //return Aplica_Error(1 por ejemplo);
            {
                //"La producción no está bien definida. Falta el encabezado o lado izquierdo de la producción.
                return false;
            }
            if (getRightSide().equals(""))
            {
                //"La producción no está bien definida. Falta el cuerpo o lado derecho de la producción.
                return false;
            }
            if (getRightSide().charAt(0) == ('|'))
            {
                //La producción no está bien definida. Hay un símbolo '|' justo después del símbolo '→'
                return false;
            }
            if (getRightSide().charAt(getRightSide().length() - 1) == ('|'))
            {
                //La producción no está bien definida. Hay un símbolo '|' justo al final de la producción.
                return false;
            }
        }
        else
        {
            //La producción no está bien definida. El símbolo '→' de la producción no está bien formado.
                 return false;
        }
        return true;
    }

    public final ArrayList<Production> getGeneratedProductions(){
        ArrayList<Production> generatedProductions = new ArrayList<Production>();
        String[] splitOfRightSide;

        splitOfRightSide = getRightSide().split("[|]", -1);

        for (int index = 0; index < splitOfRightSide.length; index++)
        {
            if (splitOfRightSide[index].equals(""))
            {
                splitOfRightSide[index] = "~";
            }
            Production newProduction = new Production(getLeftSide(), splitOfRightSide[index]);
            generatedProductions.add(newProduction);
        }

        return generatedProductions;
    }

}
