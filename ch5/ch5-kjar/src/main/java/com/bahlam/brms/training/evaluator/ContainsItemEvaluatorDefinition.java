package com.bahlam.brms.training.evaluator;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import org.drools.core.base.ValueType;
import org.drools.core.base.evaluators.EvaluatorDefinition;
import org.drools.core.base.evaluators.Operator;
import org.drools.core.spi.Evaluator;


public class ContainsItemEvaluatorDefinition implements EvaluatorDefinition {

    protected static final String containsItemOp = "containsItem";

    public static Operator CONTAINS_ITEM;
    public static Operator NOT_CONTAINS_ITEM;

    private static String[] SUPPORTED_IDS;

    private ContainsItemEvaluator evaluator;
    private ContainsItemEvaluator negatedEvaluator;

    static {
        init();
    }

    static void init() {
        if (SUPPORTED_IDS == null) {
            CONTAINS_ITEM = Operator.addOperatorToRegistry(containsItemOp, false);
            NOT_CONTAINS_ITEM = Operator.addOperatorToRegistry(containsItemOp, true);
            SUPPORTED_IDS = new String[]{containsItemOp};
        }
    }

    @Override
    public String[] getEvaluatorIds() {
        return new String[]{containsItemOp};
    }

    @Override
    public boolean isNegatable() {
        return true;
    }

    @SuppressWarnings("rawtypes")
	@Override
    public Evaluator getEvaluator(ValueType type, Operator operator) {
        return this.getEvaluator(type, operator.getOperatorString(), 
                operator.isNegated(), null);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Evaluator getEvaluator(ValueType type, Operator operator, 
            String parameterText) {
        return this.getEvaluator(type, operator.getOperatorString(), 
                operator.isNegated(), parameterText);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Evaluator getEvaluator(ValueType type, String operatorId, 
            boolean isNegated, String parameterText) {
        return getEvaluator(type, operatorId, isNegated, parameterText, 
                Target.BOTH, Target.BOTH);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Evaluator getEvaluator(ValueType type, String operatorId, 
            boolean isNegated, String parameterText, Target leftTarget, 
            Target rightTarget) {
        return isNegated ? 
                negatedEvaluator == null ? 
                    new ContainsItemEvaluator(type, isNegated) : negatedEvaluator 
                : evaluator == null ? 
                    new ContainsItemEvaluator(type, isNegated) : evaluator;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean supportsType(ValueType type) {
        return true;
    }

    @Override
    public Target getTarget() {
        return Target.BOTH;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(evaluator);
        out.writeObject(negatedEvaluator);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        evaluator = (ContainsItemEvaluator) in.readObject();
        negatedEvaluator = (ContainsItemEvaluator) in.readObject();
    }

}