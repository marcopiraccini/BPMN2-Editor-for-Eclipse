package org.jboss.bpmn2.editor.core.features.event;

import org.eclipse.bpmn2.Event;
import org.eclipse.bpmn2.SignalEventDefinition;
import org.eclipse.bpmn2.StartEvent;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.algorithms.Ellipse;
import org.eclipse.graphiti.mm.algorithms.Polygon;
import org.jboss.bpmn2.editor.core.features.ShapeUtil;
import org.jboss.bpmn2.editor.core.features.StyleUtil;

public class AddSignalStartEventFeature extends AbstractAddEventFeature {

	public AddSignalStartEventFeature(IFeatureProvider fp) {
	    super(fp);
    }
	
	@Override
	protected void decorateEllipse(Ellipse e) {
		Polygon triangle = ShapeUtil.createTriangle(e);
		triangle.setFilled(false);
		triangle.setForeground(manageColor(StyleUtil.CLASS_FOREGROUND));
	}
	
	@Override
    protected Class<? extends Event> getEventClass() {
	    return StartEvent.class;
    }

	public static boolean isDefinitionsMatch(StartEvent e) {
		if (e.getEventDefinitions() == null || e.getEventDefinitions().isEmpty() || e.getEventDefinitions().size() > 1)
			return false;
		return e.getEventDefinitions().get(0) instanceof SignalEventDefinition;
    }
}