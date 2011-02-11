package org.jboss.bpmn2.editor.core.features.event;

import org.eclipse.bpmn2.EndEvent;
import org.eclipse.bpmn2.Event;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.algorithms.Ellipse;

public class AddEndEventFeature extends AbstractAddEventFeature {

	public AddEndEventFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	protected void enhanceEllipse(Ellipse e) {
		e.setLineWidth(3);
	}

	@Override
    protected Class<? extends Event> getEventClass() {
	    return EndEvent.class;
    }
}