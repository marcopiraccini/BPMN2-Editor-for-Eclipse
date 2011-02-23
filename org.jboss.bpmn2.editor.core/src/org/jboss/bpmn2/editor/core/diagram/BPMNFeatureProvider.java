package org.jboss.bpmn2.editor.core.diagram;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IDirectEditingFeature;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.IMoveShapeFeature;
import org.eclipse.graphiti.features.IResizeShapeFeature;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.context.IMoveShapeContext;
import org.eclipse.graphiti.features.context.IResizeShapeContext;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.ui.features.DefaultFeatureProvider;
import org.jboss.bpmn2.editor.core.features.FeatureContainer;
import org.jboss.bpmn2.editor.core.features.FeatureResolver;
import org.jboss.bpmn2.editor.core.features.artifact.ArtifactFeatureResolver;
import org.jboss.bpmn2.editor.core.features.event.BoundaryEventFeatureContainer;
import org.jboss.bpmn2.editor.core.features.event.EventFeatureResolver;
import org.jboss.bpmn2.editor.core.features.event.definitions.CancelEventDefinitionContainer;
import org.jboss.bpmn2.editor.core.features.event.definitions.CompensateEventDefinitionContainer;
import org.jboss.bpmn2.editor.core.features.event.definitions.ConditionalEventDefinitionContainer;
import org.jboss.bpmn2.editor.core.features.event.definitions.ErrorEventDefinitionContainer;
import org.jboss.bpmn2.editor.core.features.event.definitions.EscalationEventDefinitionContainer;
import org.jboss.bpmn2.editor.core.features.event.definitions.LinkEventDefinitionContainer;
import org.jboss.bpmn2.editor.core.features.event.definitions.MessageEventDefinitionContainer;
import org.jboss.bpmn2.editor.core.features.event.definitions.SignalEventDefinitionContainer;
import org.jboss.bpmn2.editor.core.features.event.definitions.TimerEventDefinitionContainer;
import org.jboss.bpmn2.editor.core.features.flow.FlowFeatureResolver;
import org.jboss.bpmn2.editor.core.features.gateway.ComplexGatewayFeatureContainer;
import org.jboss.bpmn2.editor.core.features.gateway.EventBasedGatewayFeatureContainer;
import org.jboss.bpmn2.editor.core.features.gateway.ExclusiveGatewayFeatureContainer;
import org.jboss.bpmn2.editor.core.features.gateway.InclusiveGatewayFeatureContainer;
import org.jboss.bpmn2.editor.core.features.gateway.ParallelGatewayFeatureContainer;
import org.jboss.bpmn2.editor.core.features.lane.LaneFeatureResolver;
import org.jboss.bpmn2.editor.core.features.participant.ParticipantFeatureResolver;
import org.jboss.bpmn2.editor.core.features.subprocess.SubProcessFeatureContainer;
import org.jboss.bpmn2.editor.core.features.subprocess.TransactionFeatureContainer;
import org.jboss.bpmn2.editor.core.features.task.BusinessRuleTaskFeatureContainer;
import org.jboss.bpmn2.editor.core.features.task.ManualTaskFeatureContainer;
import org.jboss.bpmn2.editor.core.features.task.ReceiveTaskFeatureContainer;
import org.jboss.bpmn2.editor.core.features.task.ScriptTaskFeatureContainer;
import org.jboss.bpmn2.editor.core.features.task.SendTaskFeatureContainer;
import org.jboss.bpmn2.editor.core.features.task.ServiceTaskFeatureContainer;
import org.jboss.bpmn2.editor.core.features.task.TaskFeatureContainer;
import org.jboss.bpmn2.editor.core.features.task.UserTaskFeatureContainer;

/**
 * Determines what kinds of business objects can be added to a diagram.
 * 
 * @author imeikas
 * 
 */
public class BPMNFeatureProvider extends DefaultFeatureProvider {

	private List<FeatureContainer> containers;

	private List<FeatureResolver> resolvers;

	private ICreateFeature[] createFeatures;

	private ICreateConnectionFeature[] createConnectionFeatures;

	public BPMNFeatureProvider(IDiagramTypeProvider dtp) {
		super(dtp);

		// TODO convert resolvers to containers, provides better decoupling
		resolvers = new ArrayList<FeatureResolver>();
		resolvers.add(new EventFeatureResolver());
		resolvers.add(new FlowFeatureResolver());
		resolvers.add(new LaneFeatureResolver());
		resolvers.add(new ParticipantFeatureResolver());
		resolvers.add(new ArtifactFeatureResolver());

		containers = new ArrayList<FeatureContainer>();
		containers.add(new BoundaryEventFeatureContainer());
		containers.add(new ServiceTaskFeatureContainer());
		containers.add(new UserTaskFeatureContainer());
		containers.add(new ManualTaskFeatureContainer());
		containers.add(new ScriptTaskFeatureContainer());
		containers.add(new BusinessRuleTaskFeatureContainer());
		containers.add(new SendTaskFeatureContainer());
		containers.add(new ReceiveTaskFeatureContainer());
		containers.add(new TaskFeatureContainer());
		containers.add(new ExclusiveGatewayFeatureContainer());
		containers.add(new InclusiveGatewayFeatureContainer());
		containers.add(new ParallelGatewayFeatureContainer());
		containers.add(new EventBasedGatewayFeatureContainer());
		containers.add(new ComplexGatewayFeatureContainer());
		containers.add(new TransactionFeatureContainer());
		containers.add(new SubProcessFeatureContainer());
		containers.add(new ConditionalEventDefinitionContainer());
		containers.add(new MessageEventDefinitionContainer());
		containers.add(new TimerEventDefinitionContainer());
		containers.add(new SignalEventDefinitionContainer());
		containers.add(new EscalationEventDefinitionContainer());
		containers.add(new CompensateEventDefinitionContainer());
		containers.add(new LinkEventDefinitionContainer());
		containers.add(new ErrorEventDefinitionContainer());
		containers.add(new CancelEventDefinitionContainer());

		List<ICreateFeature> createFeaturesList = new ArrayList<ICreateFeature>();

		for (FeatureResolver r : resolvers) {
			createFeaturesList.addAll(r.getCreateFeatures(this));
		}

		for (FeatureContainer container : containers) {
			createFeaturesList.add(container.getCreateFeature(this));
		}

		createFeatures = createFeaturesList.toArray(new ICreateFeature[createFeaturesList.size()]);

		List<ICreateConnectionFeature> createConnectionFeatureList = new ArrayList<ICreateConnectionFeature>();
		for (FeatureResolver r : resolvers) {
			createConnectionFeatureList.addAll(r.getCreateConnectionFeatures(this));
		}

		createConnectionFeatures = createConnectionFeatureList
		        .toArray(new ICreateConnectionFeature[createConnectionFeatureList.size()]);

	}

	@Override
	public IAddFeature getAddFeature(IAddContext context) {
		Object o = context.getNewObject();

		if (isNotBaseElement(o)) {
			return super.getAddFeature(context);
		}

		BaseElement element = (BaseElement) o;

		// TODO remove after refactoring all remaining resolvers -> containers
		for (FeatureResolver r : resolvers) {
			IAddFeature f = r.getAddFeature(this, element);
			if (f != null) {
				return f;
			}
		}

		for (FeatureContainer container : containers) {
			if (container.canApplyTo(element)) {
				IAddFeature feature = container.getAddFeature(this);
				if (feature == null)
					continue;
				return feature;
			}
		}

		return super.getAddFeature(context);
	}

	@Override
	public ICreateFeature[] getCreateFeatures() {
		return createFeatures;
	}

	@Override
	public IUpdateFeature getUpdateFeature(IUpdateContext context) {
		Object o = getBusinessObjectForPictogramElement(context.getPictogramElement());

		if (isNotBaseElement(o)) {
			return super.getUpdateFeature(context);
		}

		BaseElement element = (BaseElement) o;

		// TODO remove after refactoring all remaining resolvers -> containers
		for (FeatureResolver r : resolvers) {
			IUpdateFeature f = r.getUpdateFeature(this, element);
			if (f != null) {
				return f;
			}
		}

		for (FeatureContainer container : containers) {
			if (container.canApplyTo(element)) {
				IUpdateFeature feature = container.getUpdateFeature(this);
				if (feature == null)
					continue;
				return feature;
			}
		}

		return super.getUpdateFeature(context);
	}

	@Override
	public ICreateConnectionFeature[] getCreateConnectionFeatures() {
		return createConnectionFeatures;
	}

	@Override
	public IDirectEditingFeature getDirectEditingFeature(IDirectEditingContext context) {
		Object o = getBusinessObjectForPictogramElement(context.getPictogramElement());

		if (isNotBaseElement(o)) {
			return super.getDirectEditingFeature(context);
		}

		BaseElement element = (BaseElement) o;

		// TODO remove after refactoring all remaining resolvers -> containers
		for (FeatureResolver r : resolvers) {
			IDirectEditingFeature f = r.getDirectEditingFeature(this, (BaseElement) o);
			if (f != null) {
				return f;
			}
		}

		for (FeatureContainer container : containers) {
			if (container.canApplyTo(element)) {
				IDirectEditingFeature feature = container.getDirectEditingFeature(this);
				if (feature == null)
					continue;
				return feature;
			}
		}

		return super.getDirectEditingFeature(context);
	}

	@Override
	public ILayoutFeature getLayoutFeature(ILayoutContext context) {
		Object o = getBusinessObjectForPictogramElement(context.getPictogramElement());

		if (isNotBaseElement(o)) {
			return super.getLayoutFeature(context);
		}

		BaseElement element = (BaseElement) o;

		// TODO remove after refactoring all remaining resolvers -> containers
		for (FeatureResolver r : resolvers) {
			ILayoutFeature f = r.getLayoutFeature(this, (BaseElement) o);
			if (f != null) {
				return f;
			}
		}

		for (FeatureContainer container : containers) {
			if (container.canApplyTo(element)) {
				ILayoutFeature feature = container.getLayoutFeature(this);
				if (feature == null)
					continue;
				return feature;
			}
		}

		return super.getLayoutFeature(context);
	}

	@Override
	public IMoveShapeFeature getMoveShapeFeature(IMoveShapeContext context) {
		Object o = getBusinessObjectForPictogramElement(context.getPictogramElement());

		if (isNotBaseElement(o)) {
			return super.getMoveShapeFeature(context);
		}

		BaseElement element = (BaseElement) o;

		// TODO remove after refactoring all remaining resolvers -> containers
		for (FeatureResolver r : resolvers) {
			IMoveShapeFeature f = r.getMoveFeature(this, (BaseElement) o);
			if (f != null) {
				return f;
			}
		}

		for (FeatureContainer container : containers) {
			if (container.canApplyTo(element)) {
				IMoveShapeFeature feature = container.getMoveFeature(this);
				if (feature == null)
					continue;
				return feature;
			}
		}

		return super.getMoveShapeFeature(context);
	}

	@Override
	public IResizeShapeFeature getResizeShapeFeature(IResizeShapeContext context) {
		Object o = getBusinessObjectForPictogramElement(context.getPictogramElement());

		if (isNotBaseElement(o)) {
			return super.getResizeShapeFeature(context);
		}

		BaseElement element = (BaseElement) o;

		// TODO remove after refactoring all remaining resolvers -> containers
		for (FeatureResolver r : resolvers) {
			IResizeShapeFeature f = r.getResizeFeature(this, (BaseElement) o);
			if (f != null) {
				return f;
			}
		}

		for (FeatureContainer container : containers) {
			if (container.canApplyTo(element)) {
				IResizeShapeFeature feature = container.getResizeFeature(this);
				if (feature == null)
					continue;
				return feature;
			}
		}

		return super.getResizeShapeFeature(context);
	}

	private boolean isNotBaseElement(Object o) {
		return !(o instanceof BaseElement);
	}
}