import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;
import components.map.Map.Pair;
import components.program.Program;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.statement.Statement;

/**
 * JUnit test fixture for {@code Program}'s constructor and kernel methods.
 *
 * @author Wayne Heym
 * @author Layan Abdallah & Oak Hodous
 *
 */
public abstract class ProgramTest {

    /**
     * The name of a file containing a BL program.
     */
    private static final String FILE_NAME_1 = "data/program-sample.bl";
    /**
     * The name of a file containing a BL program.
     */
    private static final String FILE_NAME_2 = "data/program-test1.bl";
    /**
     * The name of a file containing a BL program.
     */
    private static final String FILE_NAME_3 = "data/program-test2.bl";

    /**
     * Invokes the {@code Program} constructor for the implementation under test
     * and returns the result.
     *
     * @return the new program
     * @ensures constructor = ("Unnamed", {}, compose((BLOCK, ?, ?), <>))
     */
    protected abstract Program constructorTest();

    /**
     * Invokes the {@code Program} constructor for the reference implementation
     * and returns the result.
     *
     * @return the new program
     * @ensures constructor = ("Unnamed", {}, compose((BLOCK, ?, ?), <>))
     */
    protected abstract Program constructorRef();

    /**
     *
     * Creates and returns a {@code Program}, of the type of the implementation
     * under test, from the file with the given name.
     *
     * @param filename
     *            the name of the file to be parsed to create the program
     * @return the constructed program
     * @ensures createFromFile = [the program as parsed from the file]
     */
    private Program createFromFileTest(String filename) {
        Program p = this.constructorTest();
        SimpleReader file = new SimpleReader1L(filename);
        p.parse(file);
        file.close();
        return p;
    }

    /**
     *
     * Creates and returns a {@code Program}, of the reference implementation
     * type, from the file with the given name.
     *
     * @param filename
     *            the name of the file to be parsed to create the program
     * @return the constructed program
     * @ensures createFromFile = [the program as parsed from the file]
     */
    private Program createFromFileRef(String filename) {
        Program p = this.constructorRef();
        SimpleReader file = new SimpleReader1L(filename);
        p.parse(file);
        file.close();
        return p;
    }

    /**
     * Test constructor.
     */
    @Test
    public final void testConstructor() {
        /*
         * Setup
         */
        Program pRef = this.constructorRef();

        /*
         * The call
         */
        Program pTest = this.constructorTest();

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
    }

    /**
     * Test name.
     */
    @Test
    public final void testName() {
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_1);
        Program pRef = this.createFromFileRef(FILE_NAME_1);

        /*
         * The call
         */
        String result = pTest.name();

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
        assertEquals("Test", result);
    }

    /**
     * Test setName.
     */
    @Test
    public final void testSetName() {
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_1);
        Program pRef = this.createFromFileRef(FILE_NAME_1);
        String newName = "Replacement";
        pRef.setName(newName);

        /*
         * The call
         */
        pTest.setName(newName);

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
    }

    /**
     * Test newContext.
     */
    @Test
    public final void testNewContext() {
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_1);
        Program pRef = this.createFromFileRef(FILE_NAME_1);
        Map<String, Statement> cRef = pRef.newContext();

        /*
         * The call
         */
        Map<String, Statement> cTest = pTest.newContext();

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
        assertEquals(cRef, cTest);
    }

    /**
     * Test swapContext.
     */
    @Test
    public final void testSwapContext1() {
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_1);
        Program pRef = this.createFromFileRef(FILE_NAME_1);
        Map<String, Statement> contextRef = pRef.newContext();
        Map<String, Statement> contextTest = pTest.newContext();
        String oneName = "one";
        pRef.swapContext(contextRef);
        Pair<String, Statement> oneRef = contextRef.remove(oneName);
        /* contextRef now has just "two" */
        pRef.swapContext(contextRef);
        /* pRef's context now has just "two" */
        contextRef.add(oneRef.key(), oneRef.value());
        /* contextRef now has just "one" */

        /* Make the reference call, replacing, in pRef, "one" with "two": */
        pRef.swapContext(contextRef);

        pTest.swapContext(contextTest);
        Pair<String, Statement> oneTest = contextTest.remove(oneName);
        /* contextTest now has just "two" */
        pTest.swapContext(contextTest);
        /* pTest's context now has just "two" */
        contextTest.add(oneTest.key(), oneTest.value());
        /* contextTest now has just "one" */

        /*
         * The call
         */
        pTest.swapContext(contextTest);

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
        assertEquals(contextRef, contextTest);
    }

    /**
     * test swap context with second file
     */
    @Test
    public final void testSwapContext2() {
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_2);
        Program pRef = this.createFromFileRef(FILE_NAME_2);
        Map<String, Statement> contextRef = pRef.newContext();
        Map<String, Statement> contextTest = pTest.newContext();
        String oneName = "only";
        pRef.swapContext(contextRef);
        Pair<String, Statement> oneRef = contextRef.remove(oneName);
        /* contextRef now has just "two" */
        pRef.swapContext(contextRef);
        /* pRef's context now has just "two" */
        contextRef.add(oneRef.key(), oneRef.value());
        /* contextRef now has just "one" */

        /* Make the reference call, replacing, in pRef, "one" with "two": */
        pRef.swapContext(contextRef);

        pTest.swapContext(contextTest);
        Pair<String, Statement> oneTest = contextTest.remove(oneName);
        /* contextTest now has just "two" */
        pTest.swapContext(contextTest);
        /* pTest's context now has just "two" */
        contextTest.add(oneTest.key(), oneTest.value());
        /* contextTest now has just "one" */

        /*
         * The call
         */
        pTest.swapContext(contextTest);

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
        assertEquals(contextRef, contextTest);
    }

    /**
     * test of swap context when there is no instruction
     */
    @Test
    public final void testSwapContext3() {
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_2);
        Program pRef = this.createFromFileRef(FILE_NAME_2);
        Map<String, Statement> contextRef = pRef.newContext();
        Map<String, Statement> contextTest = pTest.newContext();
        String oneName = "only";
        pRef.swapContext(contextRef);
        Pair<String, Statement> oneRef = contextRef.remove(oneName);
        /* contextRef now has just "two" */
        pRef.swapContext(contextRef);
        /* pRef's context now has just "two" */
        contextRef.add(oneRef.key(), oneRef.value());
        /* contextRef now has just "one" */

        /* Make the reference call, replacing, in pRef, "one" with "two": */
        pRef.swapContext(contextRef);

        pTest.swapContext(contextTest);
        Pair<String, Statement> oneTest = contextTest.remove(oneName);
        /* contextTest now has just "two" */
        pTest.swapContext(contextTest);
        /* pTest's context now has just "two" */
        contextTest.add(oneTest.key(), oneTest.value());
        /* contextTest now has just "one" */

        /*
         * The call
         */
        pTest.swapContext(contextTest);

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
        assertEquals(contextRef, contextTest);
    }

    /**
     * Test newBody.
     */
    @Test
    public final void testNewBody() {
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_1);
        Program pRef = this.createFromFileRef(FILE_NAME_1);
        Statement bRef = pRef.newBody();

        /*
         * The call
         */
        Statement bTest = pTest.newBody();

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
        assertEquals(bRef, bTest);
    }

    /**
     * Test swapBody.
     */
    @Test
    public final void testSwapBody() {
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_1);
        Program pRef = this.createFromFileRef(FILE_NAME_1);
        Statement bodyRef = pRef.newBody();
        Statement bodyTest = pTest.newBody();
        pRef.swapBody(bodyRef);
        Statement firstRef = bodyRef.removeFromBlock(0);
        /* bodyRef now lacks the first statement */
        pRef.swapBody(bodyRef);
        /* pRef's body now lacks the first statement */
        bodyRef.addToBlock(0, firstRef);
        /* bodyRef now has just the first statement */

        /* Make the reference call, replacing, in pRef, remaining with first: */
        pRef.swapBody(bodyRef);

        pTest.swapBody(bodyTest);
        Statement firstTest = bodyTest.removeFromBlock(0);
        /* bodyTest now lacks the first statement */
        pTest.swapBody(bodyTest);
        /* pTest's body now lacks the first statement */
        bodyTest.addToBlock(0, firstTest);
        /* bodyTest now has just the first statement */

        /*
         * The call
         */
        pTest.swapBody(bodyTest);

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
        assertEquals(bodyRef, bodyTest);
    }

    // TODO - provide additional test cases to thoroughly test ProgramKernel

    //test cases for name method

    /**
     * Test name.
     */
    @Test
    public final void testName2() {
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_2);
        Program pRef = this.createFromFileRef(FILE_NAME_2);

        /*
         * The call
         */
        String result = pTest.name();

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
        assertEquals("Test1", result);
    }

    /**
     * Test name.
     */
    @Test
    public final void testName3() {
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_3);
        Program pRef = this.createFromFileRef(FILE_NAME_3);

        /*
         * The call
         */
        String result = pTest.name();

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
        assertEquals("JUnitTest2", result);
    }

    //test cases for swapBody method

    /**
     * Test swapBody.
     */
    @Test
    public final void testSwapBody2() {
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_2);
        Program pRef = this.createFromFileRef(FILE_NAME_2);
        Statement bodyRef = pRef.newBody();
        Statement bodyTest = pTest.newBody();
        pRef.swapBody(bodyRef);
        Statement firstRef = bodyRef.removeFromBlock(0);
        /* bodyRef now lacks the first statement */
        pRef.swapBody(bodyRef);
        /* pRef's body now lacks the first statement */
        bodyRef.addToBlock(0, firstRef);
        /* bodyRef now has just the first statement */

        /* Make the reference call, replacing, in pRef, remaining with first: */
        pRef.swapBody(bodyRef);

        pTest.swapBody(bodyTest);
        Statement firstTest = bodyTest.removeFromBlock(0);
        /* bodyTest now lacks the first statement */
        pTest.swapBody(bodyTest);
        /* pTest's body now lacks the first statement */
        bodyTest.addToBlock(0, firstTest);
        /* bodyTest now has just the first statement */

        /*
         * The call
         */
        pTest.swapBody(bodyTest);

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
        assertEquals(bodyRef, bodyTest);
    }

    /**
     * Test swapBody.
     */
    @Test
    public final void testSwapBody3() {
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_3);
        Program pRef = this.createFromFileRef(FILE_NAME_3);
        Statement bodyRef = pRef.newBody();
        Statement bodyTest = pTest.newBody();
        pRef.swapBody(bodyRef);
        Statement firstRef = bodyRef.removeFromBlock(0);
        /* bodyRef now lacks the first statement */
        pRef.swapBody(bodyRef);
        /* pRef's body now lacks the first statement */
        bodyRef.addToBlock(0, firstRef);
        /* bodyRef now has just the first statement */

        /* Make the reference call, replacing, in pRef, remaining with first: */
        pRef.swapBody(bodyRef);

        pTest.swapBody(bodyTest);
        Statement firstTest = bodyTest.removeFromBlock(0);
        /* bodyTest now lacks the first statement */
        pTest.swapBody(bodyTest);
        /* pTest's body now lacks the first statement */
        bodyTest.addToBlock(0, firstTest);
        /* bodyTest now has just the first statement */

        /*
         * The call
         */
        pTest.swapBody(bodyTest);

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
        assertEquals(bodyRef, bodyTest);
    }

    //SET NAME TESTS
    /**
     * set name test 1
     */
    @Test
    public final void testSetName1() {
        Program pTest = this.createFromFileTest(FILE_NAME_1);
        Program pRef = this.createFromFileRef(FILE_NAME_1);
        String newName = "Replacement";

        pRef.setName(newName);
        pTest.setName(newName);

        assertEquals(pRef, pTest);
        assertEquals(pRef.name(), pTest.name());
    }

    /**
     * set name test 2
     */
    @Test
    public final void testSetName2() {
        Program pTest = this.createFromFileTest(FILE_NAME_2);
        Program pRef = this.createFromFileRef(FILE_NAME_2);
        String newName = "Replacement";

        pRef.setName(newName);
        pTest.setName(newName);

        assertEquals(pRef, pTest);
        assertEquals(pRef.name(), pTest.name());
    }

    /**
     * set name test 3
     */
    @Test
    public final void testSetName3() {
        Program pTest = this.createFromFileTest(FILE_NAME_3);
        Program pRef = this.createFromFileRef(FILE_NAME_3);
        String newName = "Replacement";

        pRef.setName(newName);
        pTest.setName(newName);

        assertEquals(pRef, pTest);
        assertEquals(pRef.name(), pTest.name());
    }

    //NEW CONTEXT NAMES
    /**
     * Test new context 1
     */
    @Test
    public final void testNewContext1() {
        Program pTest = this.createFromFileTest(FILE_NAME_1);
        Program pRef = this.createFromFileRef(FILE_NAME_1);
        Map<String, Statement> cRef = pRef.newContext();

        Map<String, Statement> cTest = pTest.newContext();

        assertEquals(pRef, pTest);
        assertEquals(cRef, cTest);
    }

    /**
     * Test new context 2
     */
    @Test
    public final void testNewContext2() {
        Program pTest = this.createFromFileTest(FILE_NAME_2);
        Program pRef = this.createFromFileRef(FILE_NAME_2);
        Map<String, Statement> cRef = pRef.newContext();

        Map<String, Statement> cTest = pTest.newContext();

        assertEquals(pRef, pTest);
        assertEquals(cRef, cTest);
    }

    /**
     * Test new context 3
     */
    @Test
    public final void testNewContext3() {
        Program pTest = this.createFromFileTest(FILE_NAME_3);
        Program pRef = this.createFromFileRef(FILE_NAME_3);
        Map<String, Statement> cRef = pRef.newContext();

        Map<String, Statement> cTest = pTest.newContext();

        assertEquals(pRef, pTest);
        assertEquals(cRef, cTest);
    }

    //NEW BODY TEXTS

    /**
     * New Body test 1
     */
    @Test
    public final void testNewBody1() {
        Program pTest = this.createFromFileTest(FILE_NAME_1);
        Program pRef = this.createFromFileRef(FILE_NAME_1);
        Statement bRef = pRef.newBody();

        Statement bTest = pTest.newBody();

        assertEquals(pRef, pTest);
        assertEquals(bRef, bTest);
    }

    /**
     * New Body test 2
     */
    @Test
    public final void testNewBody2() {
        Program pTest = this.createFromFileTest(FILE_NAME_2);
        Program pRef = this.createFromFileRef(FILE_NAME_2);
        Statement bRef = pRef.newBody();

        Statement bTest = pTest.newBody();

        assertEquals(pRef, pTest);
        assertEquals(bRef, bTest);
    }

    /**
     * New Body test 3
     */
    @Test
    public final void testNewBody3() {
        Program pTest = this.createFromFileTest(FILE_NAME_3);
        Program pRef = this.createFromFileRef(FILE_NAME_3);
        Statement bRef = pRef.newBody();

        Statement bTest = pTest.newBody();

        assertEquals(pRef, pTest);
        assertEquals(bRef, bTest);
    }

}
