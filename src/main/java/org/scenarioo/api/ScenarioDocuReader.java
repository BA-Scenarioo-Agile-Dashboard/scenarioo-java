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


package org.scenarioo.api;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.scenarioo.api.files.ObjectFromDirectory;
import org.scenarioo.api.files.ScenarioDocuFiles;
import org.scenarioo.api.util.xml.ScenarioDocuXMLFileUtil;
import org.scenarioo.model.docu.derived.BuildLink;
import org.scenarioo.model.docu.entities.Branch;
import org.scenarioo.model.docu.entities.Build;
import org.scenarioo.model.docu.entities.Scenario;
import org.scenarioo.model.docu.entities.Step;
import org.scenarioo.model.docu.entities.UseCase;

/**
 * Gives access to the geenrated scenario docu files in the filesystem.
 */
public class ScenarioDocuReader {
	
	private ScenarioDocuFiles docuFiles;
	
	public ScenarioDocuReader(final File rootDirectory) {
		this.docuFiles = new ScenarioDocuFiles(rootDirectory);
	}
	
	public Branch loadBranch(final String branchName) {
		File file = docuFiles.getBranchFile(branchName);
		return ScenarioDocuXMLFileUtil.unmarshal(Branch.class, file);
	}
	
	public List<Branch> loadBranches() {
		List<File> branchFiles = docuFiles.getBranchFiles();
		return ScenarioDocuXMLFileUtil.unmarshalListOfFiles(Branch.class, branchFiles);
	}
	
	public Build loadBuild(final String branchName, final String buildName) {
		File file = docuFiles.getBuildFile(branchName, buildName);
		return ScenarioDocuXMLFileUtil.unmarshal(Build.class, file);
	}
	
	public List<BuildLink> loadBuilds(final String branchName) {
		List<File> buildFiles = docuFiles.getBuildFiles(branchName);
		List<BuildLink> result = new ArrayList<BuildLink>();
		for (ObjectFromDirectory<Build> build : ScenarioDocuXMLFileUtil.unmarshalListOfFilesWithDirNames(buildFiles,
				Build.class)) {
			BuildLink link = new BuildLink(build.getObject(), build.getDirectoryName());
			result.add(link);
		}
		return result;
	}
	
	public List<UseCase> loadUsecases(final String branchName, final String buildName) {
		List<File> files = docuFiles.getUseCaseFiles(branchName, buildName);
		return ScenarioDocuXMLFileUtil.unmarshalListOfFiles(UseCase.class, files);
	}
	
	public UseCase loadUsecase(final String branchName, final String buildName, final String useCaseName) {
		File file = docuFiles.getUseCaseFile(branchName, buildName, useCaseName);
		return ScenarioDocuXMLFileUtil.unmarshal(UseCase.class, file);
	}
	
	public List<Scenario> loadScenarios(final String branchName, final String buildName, final String useCaseName) {
		List<File> files = docuFiles.getScenarioFiles(branchName, buildName, useCaseName);
		return ScenarioDocuXMLFileUtil.unmarshalListOfFiles(Scenario.class, files);
	}
	
	public Scenario loadScenario(final String branchName, final String buildName, final String useCaseName,
			final String scenarioName) {
		File file = docuFiles.getScenarioFile(branchName, buildName, useCaseName, scenarioName);
		return ScenarioDocuXMLFileUtil.unmarshal(Scenario.class, file);
	}
	
	public List<Step> loadSteps(final String branchName, final String buildName, final String useCaseName,
			final String scenarioName) {
		List<File> files = docuFiles.getStepFiles(branchName, buildName, useCaseName, scenarioName);
		return ScenarioDocuXMLFileUtil.unmarshalListOfFiles(Step.class, files);
	}
	
	public Step loadStep(final String branchName, final String buildName, final String useCaseName,
			final String scenarioName, final int stepIndex) {
		File file = docuFiles.getStepFile(branchName, buildName, useCaseName, scenarioName, stepIndex);
		return ScenarioDocuXMLFileUtil.unmarshal(Step.class, file);
	}
	
	/**
	 * Screenshot files are simply provided by path, the REST service will take care of streaming it.
	 */
	public File getScreenshotFile(final String branchName, final String buildName, final String useCaseName,
			final String scenarioName, final String imageName) {
		return new File(docuFiles.getScreenshotsDirectory(branchName, buildName, useCaseName, scenarioName),
				imageName);
	}
}
