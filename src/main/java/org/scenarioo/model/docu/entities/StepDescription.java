/* scenarioo-api
 * Copyright (C) 2014, scenarioo.org Development Team
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * As a special exception, the copyright holders of this library give you 
 * permission to link this library with independent modules, according 
 * to the GNU General Public License with "Classpath" exception as provided
 * in the LICENSE file that accompanied this code.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.scenarioo.model.docu.entities;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.scenarioo.model.docu.entities.generic.Details;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class StepDescription implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int index = 0;
	private String title = "";
	private String status = "";
	
	/**
	 * Only the filename of the image, the directory is defined through the scenario, usecase etc.
	 * 
	 * TODO(#72): may be we should remove this from the model and replace by a convention ({stepIndex}.png: 001.png,
	 * 002.png etc.)
	 */
	private String screenshotFileName;
	
	private final Details details = new Details();
	
	/**
	 * TODO(#72) the following data should be removed from here, this does not belong into the API for generating data.
	 * Thsi data is calculated by the webapp anyway.
	 */
	
	// TODO(#72): name should be something like pageIndex ??
	@Deprecated
	private int occurence = 0;
	
	// TODO(#72): name should be something like pageStepIndex ??
	@Deprecated
	private int relativeIndex = 0;
	@Deprecated
	private int variantIndex = 0;
	@Deprecated
	private StepIdentification previousStepVariant;
	@Deprecated
	private StepIdentification nextStepVariant;
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(final int index) {
		this.index = index;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(final String title) {
		this.title = title;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(final String status) {
		this.status = status;
	}
	
	public String getScreenshotFileName() {
		return screenshotFileName;
	}
	
	public void setScreenshotFileName(final String screenshotFileName) {
		this.screenshotFileName = screenshotFileName;
	}
	
	public Details getDetails() {
		return details;
	}
	
	/**
	 * Only for internal use, has no effect when setting it manually, will be removed in next API version.
	 */
	@Deprecated
	public StepIdentification getPreviousStepVariant() {
		return previousStepVariant;
	}
	
	/**
	 * Only for internal use, has no effect when setting it manually, will be removed in next API version.
	 */
	@Deprecated
	public void setPreviousStepVariant(final StepIdentification previousStepVariant) {
		this.previousStepVariant = previousStepVariant;
	}
	
	/**
	 * Only for internal use, has no effect when setting it manually, will be removed in next API version.
	 */
	@Deprecated
	public StepIdentification getNextStepVariant() {
		return nextStepVariant;
	}
	
	/**
	 * Only for internal use, has no effect when setting it manually, will be removed in next API version.
	 */
	@Deprecated
	public void setNextStepVariant(final StepIdentification nextStepVariant) {
		this.nextStepVariant = nextStepVariant;
	}
	
	/**
	 * Only for internal use, has no effect when setting it manually, will be removed in next API version.
	 */
	@Deprecated
	public int getOccurence() {
		return occurence;
	}
	
	/**
	 * Only for internal use, has no effect when setting it manually, will be removed in next API version.
	 */
	@Deprecated
	public void setOccurence(final int occurence) {
		this.occurence = occurence;
	}
	
	/**
	 * Only for internal use, has no effect when setting it manually, will be removed in next API version.
	 */
	@Deprecated
	public int getRelativeIndex() {
		return relativeIndex;
	}
	
	/**
	 * Only for internal use, has no effect when setting it manually, will be removed in next API version.
	 */
	@Deprecated
	public void setRelativeIndex(final int relativeIndex) {
		this.relativeIndex = relativeIndex;
	}
	
	/**
	 * Only for internal use, has no effect when setting it manually, will be removed in next API version.
	 */
	@Deprecated
	public int getVariantIndex() {
		return variantIndex;
	}
	
	/**
	 * Only for internal use, has no effect when setting it manually, will be removed in next API version.
	 */
	@Deprecated
	public void setVariantIndex(final int variantIndex) {
		this.variantIndex = variantIndex;
	}
	
	public void addDetails(final String key, final Object value) {
		details.addDetail(key, value);
	}
	
}
