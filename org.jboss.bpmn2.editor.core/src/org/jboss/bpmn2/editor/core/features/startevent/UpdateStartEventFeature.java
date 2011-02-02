package org.jboss.bpmn2.editor.core.features.startevent;

import org.eclipse.bpmn2.StartEvent;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.jboss.bpmn2.editor.core.features.AbstractBPMNUpdateFeature;

public class UpdateStartEventFeature extends AbstractBPMNUpdateFeature {

	public UpdateStartEventFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canUpdate(IUpdateContext context) {
		return getBusinessObjectForPictogramElement(context.getPictogramElement()) instanceof StartEvent;
	}
}