package com.easydynamics.oscal.data.constraint;

import gov.nist.secauto.metaschema.model.common.constraint.IAllowedValuesConstraint;
import gov.nist.secauto.metaschema.model.common.constraint.ICardinalityConstraint;
import gov.nist.secauto.metaschema.model.common.constraint.IConstraintValidationHandler;
import gov.nist.secauto.metaschema.model.common.constraint.IExpectConstraint;
import gov.nist.secauto.metaschema.model.common.constraint.IIndexConstraint;
import gov.nist.secauto.metaschema.model.common.constraint.IIndexHasKeyConstraint;
import gov.nist.secauto.metaschema.model.common.constraint.IKeyConstraint;
import gov.nist.secauto.metaschema.model.common.constraint.IMatchesConstraint;
import gov.nist.secauto.metaschema.model.common.constraint.IUniqueConstraint;
import gov.nist.secauto.metaschema.model.common.metapath.DynamicContext;
import gov.nist.secauto.metaschema.model.common.metapath.ISequence;
import gov.nist.secauto.metaschema.model.common.metapath.MetapathException;
import gov.nist.secauto.metaschema.model.common.metapath.item.INodeItem;
import java.util.List;

/**
 * A constraint validation handler that does nothing with errors. When any of the `handle`
 * methods for this validation handler are invoked, no action
 * is taken. This allows for very quiet runs of the service against noisy documents to
 * focus on actual errors and messages from the surrounding tooling.
 */
public class NoopConstraintValidationHandler implements IConstraintValidationHandler {
  @Override
  public void handleAllowedValuesViolation(List<IAllowedValuesConstraint> arg0, INodeItem arg1) {
    return;
  }

  @Override
  public void handleCardinalityMaximumViolation(ICardinalityConstraint arg0, INodeItem arg1,
      ISequence<? extends INodeItem> arg2) {
    return;
  }

  @Override
  public void handleCardinalityMinimumViolation(ICardinalityConstraint arg0, INodeItem arg1,
      ISequence<? extends INodeItem> arg2) {
    return;
  }

  @Override
  public void handleExpectViolation(IExpectConstraint arg0, INodeItem arg1, INodeItem arg2,
      DynamicContext arg3) {
    return;
  }

  @Override
  public void handleIndexDuplicateKeyViolation(IIndexConstraint arg0, INodeItem arg1,
      INodeItem arg2, INodeItem arg3) {
    return;
  }

  @Override
  public void handleIndexDuplicateViolation(IIndexConstraint arg0, INodeItem arg1) {
    return;
  }

  @Override
  public void handleIndexMiss(IIndexHasKeyConstraint arg0, INodeItem arg1, INodeItem arg2) {
    return;
  }

  @Override
  public void handleKeyMatchError(IKeyConstraint arg0, INodeItem arg1, INodeItem arg2,
      MetapathException arg3) {
    return;
  }

  @Override
  public void handleMatchDatatypeViolation(IMatchesConstraint arg0, INodeItem arg1,
      INodeItem arg2, String arg3, IllegalArgumentException arg4) {
    return;
  }

  @Override
  public void handleMatchPatternViolation(IMatchesConstraint arg0, INodeItem arg1, INodeItem arg2,
      String arg3) {
    return;
  }

  @Override
  public void handleUniqueKeyViolation(IUniqueConstraint arg0, INodeItem arg1, INodeItem arg2,
      INodeItem arg3) {
    return;
  }
}
