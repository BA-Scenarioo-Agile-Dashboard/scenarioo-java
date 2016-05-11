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

import static org.junit.Assert.*;
import static org.scenarioo.api.TestConstants.*;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.scenarioo.api.files.ScenarioDocuFiles;
import org.scenarioo.model.docu.entities.*;
import org.scenarioo.model.docu.entities.base.DocuObjectMap;
import org.scenarioo.model.docu.entities.base.Labels;
import org.scenarioo.model.docu.entities.screenAnnotations.ScreenAnnotation;
import org.scenarioo.model.docu.entities.screenAnnotations.ScreenAnnotationClickAction;
import org.scenarioo.model.docu.entities.screenAnnotations.ScreenAnnotationStyle;

/**
 * Some smoke tests for the Scenarioo generator API.
 */
public class ScenarioDocuWriterAndReaderTest {

    private static final String TEST_DETAILS_VERSION_KEY = "version";

    /**
     * Objects under test
     */
    private ScenarioDocuWriter writer;
    private ScenarioDocuReader reader;

    /**
     * docuFiles is used for checking some of the written files.
     */
    private ScenarioDocuFiles docuFiles;

    @Before
    public void setUp() {
        TEST_ROOT_DIRECTORY.mkdirs();
        writer = new ScenarioDocuWriter(TEST_ROOT_DIRECTORY, TEST_BRANCH_NAME, TEST_BUILD_NAME);
        reader = new ScenarioDocuReader(TEST_ROOT_DIRECTORY);
        docuFiles = new ScenarioDocuFiles(TEST_ROOT_DIRECTORY);
    }

    @AfterClass
    public static void tearDown() {
        deleteDirectory(TEST_ROOT_DIRECTORY);
    }

    private static void deleteDirectory(final File testRootDirectory) {
        try {
            FileUtils.deleteDirectory(testRootDirectory);
        } catch (IOException e) {
            throw new RuntimeException("Could not delete test data directory", e);
        }
    }

    @Test
    public void write_and_read_branch_description() {

        // GIVEN: a typical branch
        Branch branch = new Branch();
        branch.setName(TEST_BRANCH_NAME);
        branch.setDescription("just a simple development branch, might as well be the trunk.");

        // WHEN: the branch was saved.
        writer.saveBranchDescription(branch);
        writer.flush();

        // THEN: the branch can be loaded successfully and correctly
        Branch branchFromFile = reader.loadBranch(TEST_BRANCH_NAME);
        assertEquals("expected branch name", TEST_BRANCH_NAME, branchFromFile.getName());
        assertEquals("expected branch description", branch.getDescription(), branchFromFile.getDescription());

    }

    @Test
    public void write_and_read_build_description() {

        // GIVEN: a typical build
        Build build = new Build();
        build.setName(TEST_BUILD_NAME);
        build.setDate(new Date());
        build.setRevision("10123");
        build.setStatus("success");
        build.addProperty(TEST_DETAILS_VERSION_KEY, "1.0.1");

        // WHEN: the build was saved.
        writer.saveBuildDescription(build);
        writer.flush();

        // THEN: the build can be loaded successfully and correctly
        Build buildFromFile = reader.loadBuild(TEST_BRANCH_NAME, TEST_BUILD_NAME);
        assertEquals("expected build name", TEST_BUILD_NAME, buildFromFile.getName());
        assertEquals("expected date", build.getDate(), buildFromFile.getDate());
        assertEquals("expected revision", build.getRevision(), buildFromFile.getRevision());
        assertEquals("expected state", build.getStatus(), buildFromFile.getStatus());
        assertEquals("expected details properties", "1.0.1", buildFromFile.getProperties().get(TEST_DETAILS_VERSION_KEY).getValue());

    }

    @Test
    public void write_and_read_case_description() {

        // GIVEN: a typical use case
        UseCase usecase = new UseCase();
        usecase.setName(TEST_CASE_NAME);
        usecase.setDescription("this is a typical use case with a decription");
        usecase.setStatus("success");
        usecase.addProperty("webtestName", "UseCaseWebTest");
        usecase.addLabel("label1").addLabel("label2");

        // WHEN: the usecase was saved.
        writer.saveUseCase(usecase);
        writer.flush();

        // THEN: the usecase can be loaded successfully and correctly
        UseCase usecaseFromFile = reader.loadUsecase(TEST_BRANCH_NAME, TEST_BUILD_NAME, TEST_CASE_NAME);
        assertEquals("expected case name", TEST_CASE_NAME, usecaseFromFile.getName());
        assertEquals("expected case desription", usecase.getDescription(), usecaseFromFile.getDescription());
        assertEquals("expected case state", usecase.getStatus(), usecaseFromFile.getStatus());
        assertEquals("expected webtestName", "UseCaseWebTest", usecaseFromFile.getProperties().get("webtestName").getValue());
        assertExpectedLabels(usecaseFromFile.getLabels(), "label1", "label2");
    }

    @Test
    public void write_and_read_scenario_description() {

        // GIVEN: a typical scenario
        Scenario scenario = new Scenario();
        scenario.setName(TEST_SCENARIO_NAME);
        scenario.setDescription("this is a typical scenario with a decription");
        scenario.setStatus("success");
        scenario.addProperty("userRole", "customer");
        scenario.addLabel("label1").addLabel("label2");

        // WHEN: the scenario was saved.
        writer.saveScenario(TEST_CASE_NAME, scenario);
        writer.flush();

        // THEN: the scenario can be loaded successfully and correctly
        Scenario scenarioFromFile = reader.loadScenario(TEST_BRANCH_NAME, TEST_BUILD_NAME, TEST_CASE_NAME,
                TEST_SCENARIO_NAME);
        assertEquals("expected scneario name", TEST_SCENARIO_NAME, scenarioFromFile.getName());
        assertEquals("expected scenario desription", scenario.getDescription(), scenarioFromFile.getDescription());
        assertEquals("expected scenario state", scenario.getStatus(), scenarioFromFile.getStatus());
        assertEquals("expected scenario details properties", "customer", scenarioFromFile.getProperties().get("userRole").getValue());
        assertExpectedLabels(scenarioFromFile.getLabels(), "label1", "label2");

    }

    /**
     * This test is to test that nothing is required to set somehow, that is not mandatory for saving and loading a step
     * correctly.
     */
    @Test
    public void write_and_read_step_minimal() {

//		// GIVEN: a typical step with only minimal required fields beeing set
//		Step step = new Step();
//		StepDescription stepDescription = new StepDescription();
//		stepDescription.setIndex(TEST_STEP_INDEX);
//		stepDescription.setScreenshotFileName("001.png");
//		step.setStepDescription(stepDescription);
//
//		// WHEN: the step was saved.
//		writer.saveStep(TEST_CASE_NAME, TEST_SCENARIO_NAME, step);
//		writer.flush();
//
//		// THEN: the step can be loaded successfully and correctly
//		Step stepFromFile = reader.loadStep(TEST_BRANCH_NAME, TEST_BUILD_NAME, TEST_CASE_NAME, TEST_SCENARIO_NAME,
//				TEST_STEP_INDEX);
//		assertEquals("expected step index", TEST_STEP_INDEX, stepFromFile.getStepDescription().getIndex());
//		assertEquals("expected empty step title", "", stepFromFile.getStepDescription().getTitle());
//		assertEquals("expected no status", "", stepFromFile.getStepDescription().getStatus());
//		assertEquals("expected no step step description details properties", 0, stepFromFile.getStepDescription()
//				.getDetails()
//				.getProperties().size());
//		assertEquals("expected no step metadata details properties", 0, stepFromFile.getMetadata().getDetails()
//				.getProperties().size());
//		assertEquals("expected no labels", 0, stepFromFile.getStepDescription().getLabels().size());
//		assertNull("expected no page", stepFromFile.getPage());

    }

    /**
     * This test tests also all optional fields of a step, that they can be set and are loaded again properly.
     */
    @Test
    public void write_and_read_step_with_major_optional_fields() {

//		// GIVEN: a typical step with all major optional fields also beeing set
//		Step step = new Step();
//		StepDescription stepDescription = new StepDescription();
//		stepDescription.setIndex(TEST_STEP_INDEX);
//		stepDescription.setTitle("Test Step");
//		stepDescription.setStatus("success");
//		stepDescription.addDetail("key1", "value1").addDetail("key2", "value2");
//		stepDescription.addLabel("label1").addLabel("label2");
//		step.setStepDescription(stepDescription);
//		step.setHtml(new StepHtml("<html>just some page text</html>"));
//		Page page = new Page("customer_overview.jsp");
//		page.addLabel("page-label1").addLabel("page-label2");
//		step.setPage(page);
//		StepMetadata stepMetadata = new StepMetadata();
//		stepMetadata.setVisibleText("just some page text");
//		stepMetadata.addProperty("mockedServicesConfiguration", "dummy_config_xy.properties");
//		step.setMetadata(stepMetadata);
//
//		// WHEN: the step was saved.
//		writer.saveStep(TEST_CASE_NAME, TEST_SCENARIO_NAME, step);
//		writer.flush();
//
//		// THEN: the step can be loaded successfully and correctly
//		Step stepFromFile = reader.loadStep(TEST_BRANCH_NAME, TEST_BUILD_NAME, TEST_CASE_NAME, TEST_SCENARIO_NAME,
//				TEST_STEP_INDEX);
//		assertEquals("expected step name", TEST_STEP_INDEX, stepFromFile.getStepDescription().getIndex());
//		assertEquals("expected step desription", step.getStepDescription().getTitle(), stepFromFile
//				.getStepDescription().getTitle());
//		assertEquals("expected step status", step.getStepDescription().getStatus(), stepFromFile.getStepDescription()
//				.getStatus());
//		assertEquals("expected step description details properties", step.getStepDescription().getDetails()
//				.getProperties(), stepFromFile.getStepDescription().getProperties());
//		assertExpectedLabels(stepFromFile.getStepDescription().getLabels(), "label1", "label2");
//		assertEquals("expected step html", step.getHtml().getHtmlSource(), stepFromFile.getHtml().getHtmlSource());
//		Page pageFromFile = stepFromFile.getPage();
//		assertEquals("expected step page name", step.getPage().getName(), pageFromFile.getName());
//		assertExpectedLabels(pageFromFile.getLabels(), "page-label1", "page-label2");
//		assertEquals("expected step visible text", "just some page text", stepFromFile.getMetadata().getVisibleText());
//		assertEquals("expected step metadata details properties", step.getMetadata().getProperties(),
//				stepFromFile.getMetadata().getProperties());

    }

    /**
     * Test adding screen annotations to a step (which is optional too)
     */
    @Test
    public void write_and_read_step_with_screen_annotations() {

//		// GIVEN: a typical step with only minimal required fields beeing set and two screen annotations
//		Step step = new Step();
//		StepDescription stepDescription = new StepDescription();
//		stepDescription.setIndex(TEST_STEP_INDEX);
//		stepDescription.setScreenshotFileName("001.png");
//		step.setStepDescription(stepDescription);
//		step.addScreenAnnotation(createMinimalScreenAnnotation());
//		step.addScreenAnnotation(createFullScreenAnnotation());
//
//		// WHEN: the step was saved.
//		writer.saveStep(TEST_CASE_NAME, TEST_SCENARIO_NAME, step);
//		writer.flush();
//
//		// THEN: the step can be loaded successfully and correctly
//		Step stepFromFile = reader.loadStep(TEST_BRANCH_NAME, TEST_BUILD_NAME, TEST_CASE_NAME, TEST_SCENARIO_NAME,
//				TEST_STEP_INDEX);
//		assertEquals("expected number of screen annotations", 2, stepFromFile.getScreenAnnotations().size());
//		assertMinimalScreenAnnotation(stepFromFile.getScreenAnnotations().get(0));
//		assertFullScreenAnnotation(stepFromFile.getScreenAnnotations().get(1));

    }

    /**
     * Tests writing and reading of a scenario docu file containing most important generic objetc model data structures that need to be supported.
     */
    @Test
    public void write_and_read_generic_docu_object_data() {

        // GIVEN: any object containing collections in details
        Scenario scenario = new Scenario();
        scenario.setName(TEST_SCENARIO_NAME);
        scenario.setDescription("a scenario for testing collections in details");

        // Add a simple property as key value
        scenario.addProperty("myProperty", "a property value");

        // Add a list of strings as a property
        DocuObject list = scenario.addProperty("list");
        list.addItem("item1");
        list.addItem("item2");
        list.addItem("item3");

        // Add a typed object with further properties (even can have children as items for tree structures)
        DocuObject object = scenario.addProperty("myObject", "a object").setType("MyObjectType");
        object.addProperty("myObjectProperty", "a objetc property value");
        object.addItem("A child object").setType("MyObjectType").setId("objects-can-have-manual-ids");

        // Properties as key-value pairs (for more structured objects)
        DocuObject section = scenario.addSection("My Special Section");
        section.setValue("A section can also just have some description text as a value, as all other docu objects can have");
        section.addProperty("key1", "value1");
        section.addProperty("key2", "value2");
        section.addProperty("anyGenericObjectReference", DocuObject.object("serviceCall", "MainDB.getUsers"));
        section.addProperty("anyGenericObject", DocuObject.object("configuration", "my_dummy_mocks_configuration.properties"));

        // WHEN: the object was saved.
        writer.saveScenario(TEST_CASE_NAME, scenario);
        writer.flush();

        // THEN: the properties and contained docu object data structures get loaded again as expected
        Scenario scenarioFromFile = reader.loadScenario(TEST_BRANCH_NAME, TEST_BUILD_NAME, TEST_CASE_NAME,
                TEST_SCENARIO_NAME);
        assertEquals("Expected simple property value", "a property value", scenarioFromFile.getProperties().get("myProperty").getValue());
        assertEquals("Expected number of items in items list", 3, scenarioFromFile.getProperties().get("list").getItems().size());
        assertEquals("Expected typed object of type `MyObjectType`", "MyObjectType", scenarioFromFile.getProperties().get("myObject").getType());
        assertEquals("Expected child object in tree structure", "A child object", scenarioFromFile.getProperties().get("myObject").getItems().get(0).getValue());
        assertEquals("expected value for 'key1' in properties of section", "value1", scenarioFromFile.getSections().get("My Special Section").getProperties().get("key1").getValue());

    }

    /**
     * Test that the files are written asynchronously and that the flush method waits correctly for the last file to be
     * written.
     */
    @Test
    public void async_write_of_multiple_files_and_flush() {

        // GIVEN: a lot of large steps to write, that have not yet been written
        Step[] steps = new Step[10];
        for (int index = 0; index < 10; index++) {
            steps[index] = createBigDataStepForLoadTestAsyncWriting(index + 1);
        }
        File expectedFileForStep10 = docuFiles.getStepFile(TEST_BRANCH_NAME, TEST_BUILD_NAME, TEST_CASE_NAME,
                TEST_SCENARIO_NAME, 10);
        assertFalse(expectedFileForStep10.exists());

        // WHEN: saving those steps,
        for (int index = 0; index < 10; index++) {
            writer.saveStep(TEST_CASE_NAME, TEST_SCENARIO_NAME, steps[index]);
        }

        // THEN: the files are not created directly but asynchronously. flush will wait until all save's are finished.
        assertFalse(expectedFileForStep10.exists());
        writer.flush();
        assertTrue(expectedFileForStep10.exists());

    }

    /**
     * Create a screen annotation with only the required fields set.
     */
    private ScreenAnnotation createMinimalScreenAnnotation() {
        return new ScreenAnnotation(100, 150, 90, 10);
    }

    private void assertMinimalScreenAnnotation(final ScreenAnnotation screenAnnotation) {
        assertEquals("Expected x coordinate", 100, screenAnnotation.getRegion().getX());
        assertEquals("Expected y coordinate", 150, screenAnnotation.getRegion().getY());
        assertEquals("Expected width", 90, screenAnnotation.getRegion().getWidth());
        assertEquals("Expected height", 10, screenAnnotation.getRegion().getHeight());
        assertEquals("Expected no text", "", screenAnnotation.getScreenText());
        assertEquals("Expected no description", "", screenAnnotation.getDescription());
        assertEquals("Expected default style", screenAnnotation.getStyle(), ScreenAnnotationStyle.DEFAULT);
        assertNull("Expected no click action", screenAnnotation.getClickAction());
        assertNull("Expected no click action url", screenAnnotation.getClickActionUrl());
        assertNull("Expected no click action text", screenAnnotation.getClickActionText());
        assertEquals("Expected no details", 0, screenAnnotation.getProperties().size());
    }

    /**
     * Create a screen annotation with all the fields set.
     */
    private ScreenAnnotation createFullScreenAnnotation() {
        ScreenAnnotation screenAnnotation = new ScreenAnnotation(200, 150, 90, 20);
        screenAnnotation.setScreenText("just a text");
        screenAnnotation.setTitle("title");
        screenAnnotation.setDescription("just a description");
        screenAnnotation.setStyle(ScreenAnnotationStyle.CLICK);
        screenAnnotation.setClickAction(ScreenAnnotationClickAction.TO_URL);
        screenAnnotation.setClickActionUrl("http://just-an-url.com");
        screenAnnotation.setClickActionText("just a click action text");
        screenAnnotation.addProperty("just a detail", "just a value");
        return screenAnnotation;
    }

    private void assertFullScreenAnnotation(final ScreenAnnotation screenAnnotation) {
        assertEquals("Expected x coordinate", 200, screenAnnotation.getRegion().getX());
        assertEquals("Expected y coordinate", 150, screenAnnotation.getRegion().getY());
        assertEquals("Expected width", 90, screenAnnotation.getRegion().getWidth());
        assertEquals("Expected height", 20, screenAnnotation.getRegion().getHeight());
        assertEquals("Expected screen text", "just a text", screenAnnotation.getScreenText());
        assertEquals("Expected title", "title", screenAnnotation.getTitle());
        assertEquals("Expected description", "just a description", screenAnnotation.getDescription());
        assertEquals("Expected style", ScreenAnnotationStyle.CLICK, screenAnnotation.getStyle());
        assertEquals("Expected clickAction", ScreenAnnotationClickAction.TO_URL, screenAnnotation.getClickAction());
        assertEquals("Expected clickActionUrl", "http://just-an-url.com", screenAnnotation.getClickActionUrl());
        assertEquals("Expected clickActionText", "just a click action text", screenAnnotation.getClickActionText());
        assertEquals("Expected details", 1, screenAnnotation.getProperties().size());
    }

    private Step createBigDataStepForLoadTestAsyncWriting(final int index) {

        Step step = new Step();
        step.setIndex(index);
        step.setTitle("this is a step with a lot of data in it such that writing should take realy long for testing async writing");

        // Page
        step.setPage(new Page("test.jsp"));

        // Many additional properties data
        addManyDocuObjects(step.getProperties());

        return step;
    }

    /**
     * Simply generate a lot of docu objects inside a DocuObjectMap to get a big step for load testing of writing.
     */
    private void addManyDocuObjects(DocuObjectMap docuObjectMap) {
        for (int i = 0; i < 100000; i++) {
            docuObjectMap.add("detail" + i,
                    "just a detail to produce a lot of data that needs marshalling and writing.");
        }
    }

    /**
     * Simply generate a big dummy html for load testing of writing.
     */
    private String createBigHtml() {

        StringBuilder builder = new StringBuilder();
        builder.append("<html><head></head><body>");
        builder.append("<p>This is just a dummy html code with lot of content to generate a lot of big data to write for load testing.<p>");
        for (int i = 0; i < 1000; i++) {
            builder.append("<div class=\"dummyParagraph"
                    + i
                    + "\">This is just a dummy html code with lot of content to generate a lot of big data to write for load testing.</div>");
        }
        builder.append("</body></html>");

        return builder.toString();

    }

    private void assertExpectedLabels(final Labels labels, final String... expectedLabels) {
        assertEquals("Expected number of labels", expectedLabels.length, labels.size());
        for (String expectedLabel : expectedLabels) {
            assertTrue("expected label '" + expectedLabel + "' to be contained in labels.",
                    labels.contains(expectedLabel));
        }
    }
}
