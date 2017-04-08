import org.junit.Before;
import org.junit.Test;
import st.EntryMap;
import st.TemplateEngine;

import static org.junit.Assert.assertEquals;

public class TemplateEngineTest {

    private EntryMap map;

    private TemplateEngine engine;

    @Before
    public void setUp() throws Exception {
        map = new EntryMap();
        engine = new TemplateEngine();
    }

    @Test
    public void Test_TDD_1() {
        map.store("abcd", "tiki", true);
        map.store("${abcdrest}", "taka", true);
        String result = engine.evaluate("${${abcdrest}}", map,"optimization");
        assertEquals("taka", result);
    }

    @Test
    public void Test_TDD_2() {
        map.store("${${abcd}${abcd}}", "tiki", true);
        map.store("abcd", "taka", true);
        String result = engine.evaluate("${${abcd}${abcd}}", map,"optimization");
        assertEquals("${takataka}", result);
    }


    @Test
    public void Test_TDD_3() {
        map.store("${abcd}xyz${abcd}lmn", "tiki", true);
        map.store("xyzlmn", "taka", true);
        map.store("opqrst", "talin", true);
        String result = engine.evaluate("${${abcd}xyz${abcd}lmn}${${abcd}opq${abcd}rst}", map,"optimization");
        assertEquals("takatalin", result);
    }

    @Test
    public void Test_TDD_4() {
        map.store("${abcd}xyz${abcd}lmn", "tiki", true);
        map.store("${abcd}opq${abcd}rst", "taka", true);
        String result = engine.evaluate("${${abcd}xyz${abcd}lmn}${${abcd}opq${abcd}rst}", map,"optimization");
        assertEquals("tikitaka", result);
    }

    @Test
    public void Test_TDD_5() {
        map.store("def", "tiki", true);
        map.store("${abc}def", "taka", true);
        String result = engine.evaluate("${${abc}def}", map,"optimization");
        assertEquals("taka", result);
    }

    @Test
    public void Test_TDD_6() {
        map.store("defklm", "tiki", true);
        map.store("${abc}def${gij}klm", "taka", true);
        String result = engine.evaluate("${${abc}def${gij}klm}", map,"optimization");
        assertEquals("taka", result);
    }

    @Test
    public void Test_TDD_7() {
        map.store("abc", "tiki", true);
        map.store("def", "taka", true);
        map.store("${abcd}", "ram1", true);
        map.store("${defg}", "ram2", true);
        map.store("${xy}z", "ram3", true);
        String result = engine.evaluate("${${abcd}}${${defg}}${${xy}z}", map,"optimization");
        assertEquals("ram1ram2ram3", result);
    }

    @Test
    public void Test_TDD_8() {
        map.store("abc${pat1}", "ram1", true);
        map.store("abc", "ram2", true);

        map.store("def${pat2}", "ram3", true);
        map.store("def", "ram4", true);

        map.store("xyz${pat3}zzz", "ram5", true);
        map.store("xyzzzz", "ram6", true);

        map.store("${pat4}a", "ram7", true);
        map.store("a", "ram8", true);

        String result = engine.evaluate("${abc${pat1}}${def${pat2}}${xyz${pat3}zzz}${${pat4}a}", map,"optimization");
        assertEquals("ram1ram3ram5ram7", result);
    }

    @Test
    public void Test_TDD_9() {
        map.store("abc${pat1}", "ram1", true);
        map.store("abc", "ram2", true);

        map.store("def${pat2}", "ram3", true);
        map.store("def", "ram4", true);

        map.store("xyz${pat3}zzz", "ram5", true);
        map.store("xyzzzz", "ram6", true);

        map.store("${pat4}a", "ram7", true);
        map.store("a", "ram8", true);

        map.store("extra", "ram9", true);

        String result = engine.evaluate("${abc${pat1}}${def${pat2}}${xyz${pat3}zzz}${${pat4}a}${${something}extra}", map,"optimization");
        assertEquals("ram2ram4ram6ram8ram9", result);
    }

    @Test
    public void Test_TDD_10() {
        map.store("abc${pat1}", "ram1", true);
        map.store("abc", "ram2", true);

        map.store("def${pat2}", "ram3", true);
        map.store("def", "ram4", true);

        map.store("xyz${pat3}zzz", "ram5", true);
        map.store("xyzzzz", "ram6", true);

        map.store("${pat4}a", "ram7", true);
        map.store("a", "ram8", true);

        map.store("${pat5}extra", "ram9", true);

        String result = engine.evaluate("${abc${pat1}}${def${pat2}}${xyz${pat3}zzz}${${pat4}a}${${pat5}extra}", map,"optimization");
        assertEquals("ram1ram3ram5ram7ram9", result);
    }
}