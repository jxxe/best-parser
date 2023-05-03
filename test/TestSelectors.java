import dom.Dom;
import dom.DomNode;
import dom.HtmlParseException;
import dom.HtmlParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import rules.Rule;
import selectors.*;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSelectors {

    private static Dom dom;

    @BeforeAll
    public static void createDom() {
        try {
            dom = HtmlParser.fromString("""
                <html lang="en-US">
                    <body id="unique" class="parent ancestor">
                        <p class="child">Hello, world!</p>
                
                        <div class="parent child">
                            <p class="child descendant parent"></p>
                            <p></p>
                            <p class="distant-sibling"></p>
                        </div>
                
                        <div class="child descendant ancestor">
                            <p class="child descendant"></p>
                        </div>
                    </body>
                </html>
            """);
        } catch(HtmlParseException e) {
            throw new RuntimeException(e);
        }
    }

    // .parent > .child
    @Test public void testDirectChild() {
        var selectors = Arrays.asList(
            new ClassSelector("parent"),
            new ChildSelector(),
            new ClassSelector("child")
        );

        var rule = new Rule(selectors);
        var results = rule.enforce(dom);

        assertEquals(4, results.size());
        assertTrue(results.stream().allMatch(node -> {
            return node.getAttribute("class").contains("child") &&
                    node.getParent().getAttribute("class").contains("parent");
        }));
    }

    // .child ~ .distant-sibling
    @Test public void testSibling() {
        var selectors = Arrays.asList(
            new ClassSelector("child"),
            new SiblingSelector(),
            new ClassSelector("distant-sibling")
        );

        var rule = new Rule(selectors);
        var results = rule.enforce(dom);

        System.out.println(results);
        assertEquals(1, results.size());
        assertTrue(results.stream().allMatch(node -> node.getAttribute("class").equals("distant-sibling")));
    }

    // .child + .child
    @Test public void testNeighbor() {
        var selectors = Arrays.asList(
            new ClassSelector("child"),
            new AdjacentSiblingSelector(),
            new ClassSelector("child")
        );

        var rule = new Rule(selectors);
        var results = rule.enforce(dom);

        System.out.println(results);
        assertEquals(2, results.size());
        assertTrue(results.stream().allMatch(node -> node.getAttribute("class").contains("child")));
    }

    // .ancestor .descendant
    @Test public void testDescendant() {
        var selectors = Arrays.asList(
            new ClassSelector("ancestor"),
            new DescendantSelector(),
            new ClassSelector("descendant")
        );

        var rule = new Rule(selectors);
        var results = rule.enforce(dom);

        assertEquals(3, results.size());
        assertEquals(1, results.stream().filter(node -> node.getAttribute("class").equals("child descendant")).count());
        assertEquals(1, results.stream().filter(node -> node.getAttribute("class").equals("child descendant parent")).count());
        assertEquals(1, results.stream().filter(node -> node.getAttribute("class").equals("child descendant ancestor")).count());
    }

    // *
    @Test public void testWildcard() {
        List<Predicate<DomNode>> selectors = List.of(new WildcardSelector());

        var rule = new Rule(selectors);
        var results = rule.enforce(dom);

        assertEquals(dom.flatten(), results);
    }

    // #unique
    @Test public void testIdSelector() {
        List<Predicate<DomNode>> selectors = List.of(new IdSelector("unique"));

        var rule = new Rule(selectors);
        var results = rule.enforce(dom);

        assertEquals(1, results.size());
        assertEquals("body", results.get(0).getTagName());
        assertEquals("unique", results.get(0).getAttribute("id"));
    }

    // .parent
    @Test public void testClassSelector() {
        List<Predicate<DomNode>> selectors = List.of(new ClassSelector("parent"));

        var rule = new Rule(selectors);
        var results = rule.enforce(dom);

        assertEquals(3, results.size());
        assertTrue(results.stream().allMatch(node -> Arrays.stream(node.getAttribute("class").split(" ")).toList().contains("parent")));
    }

    // div
    @Test public void testTagSelector() {
        List<Predicate<DomNode>> selectors = List.of(new TagSelector("div"));

        var rule = new Rule(selectors);
        var results = rule.enforce(dom);

        assertEquals(2, results.size());
        assertTrue(results.stream().allMatch(node -> node.getTagName().equals("div")));
    }

}
