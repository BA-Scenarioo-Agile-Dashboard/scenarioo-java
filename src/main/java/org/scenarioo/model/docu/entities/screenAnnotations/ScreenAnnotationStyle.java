/*
 * scenarioo-api
 * Copyright (C) 2016, scenarioo.org Development Team
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
 *
 */

package org.scenarioo.model.docu.entities.screenAnnotations;

/**
 * Predefined styles that can be used with screen annotations.
 * 
 * A style defines in what style the annotated screen region is highlighted.
 */
public enum ScreenAnnotationStyle {

	/**
	 * Style to visualize a click event, when the UI test clicked on an element to select something
	 */
	CLICK,

	/**
	 * Style to visualize a keyboard event, when the UI test typed something inside an element
	 */
	KEYBOARD,

	/**
	 * Use this to mark regions that are asserted / checked by the UI test.
	 * (e.g. when content of a text box is inspected for containing the expected content)
	 */
	EXPECTED,

	/**
	 * Style to visualize that the UI test entered a different URL inside the browser navigation bar
	 */
	NAVIGATE_TO_URL,

	/**
	 * Generic error style:
	 * For any annotation that marks something unexpected (e.g. a validation that failed or an event that failed or an
	 * exception that occured)
	 */
	ERROR,

	/**
	 * Generic warning style, for arbitrary warning annotations
	 */
	WARN,

	/**
	 * Generic info style: for any additional information message on the screen (e.g. useful for captions, subtitles and
	 * other informations)
	 */
	INFO,

	/**
	 * Generic highlight style to highlight special regions in the UI that are important in this step
	 */
	HIGHLIGHT,

	/**
	 * Generic default style that has no special meanings. This style is useful to simply attach additional informations
	 * to specific UI elements (e.g. like dropdown items in a dropdown selection box). This style is used if no
	 * explicit stlye is specified for an annotation.
	 */
	DEFAULT;

}
