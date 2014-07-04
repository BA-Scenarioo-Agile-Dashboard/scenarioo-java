package org.scenarioo.model.docu.entities;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Labels {
	@XmlElement(name = "label")
	private Set<String> labels = new LinkedHashSet<String>();
	
	public Labels add(String label) {
		if(isValidLabel(label)) {
			labels.add(label);
		} else {
			throw new RuntimeException("Invalid label name: '" + label + "'");
		}
		return this;
	}
	
	public void set(Set<String> labelsToSet) {
		Set<String> labelsCopy = new LinkedHashSet<String>();
		for (String label : labelsToSet) {
			if(isValidLabel(label)) {
				labelsCopy.add(label);
			} else {
				throw new RuntimeException("Invalid label name: '" + label + "'");
			}
		}
		
		this.labels = labelsCopy;
	}
	
	/**
	 * Validates a label for validity. A label must only contain letters, numbers and/or '_', '-'
	 */
	public static boolean isValidLabel(String label) {
		return label.matches("^[ a-zA-Z0-9_-]+$");
	}

	/**
	 * @return all labels as set. Never null.
	 */
	public Set<String> toSet() {
		if(labels == null) {
			labels = new HashSet<String>();
		}
		return labels;
	}
	
	/**
	 * Convenience method for JSON mapping. Use {@link #toSet()} instead.
	 */
	@Deprecated
	public Set<String> getLabels() {
		// TODO We should introduce DTO objects in the server
		return toSet();
	}
	
	/**
	 * Convenience method for JSON mapping. Use {@link #set(Set)}
	 */
	@Deprecated
	public void setLabels(Set<String> labels) {
		// TODO We should introduce DTO objects in the server
		set(labels);
	}
}