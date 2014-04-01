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

/**
 * Special calculated data for a scenario, this data gets calculated by the Scenarioo webaplication when generated docu
 * is imported automatically, therefore you do NOT have to fill this data when generating documentation (it will be
 * overwritten by the webapplication anyway).
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ScenarioCalculatedData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int numberOfPages;
	private int numberOfSteps;
	
	public int getNumberOfPages() {
		return numberOfPages;
	}
	
	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}
	
	public int getNumberOfSteps() {
		return numberOfSteps;
	}
	
	public void setNumberOfSteps(int numberOfSteps) {
		this.numberOfSteps = numberOfSteps;
	}
	
}
