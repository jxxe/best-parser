package selectors;

import dom.DomNode;

import java.util.Arrays;
import java.util.function.Predicate;

public class ClassSelector implements Predicate<DomNode> {

    private final String className;

    public ClassSelector(String className) {
        this.className = className;
    }

    @Override
    public boolean test(DomNode domNode) {
        String classAttribute = domNode.getAttribute("class");
        if(classAttribute == null) return false;
        String[] classes = classAttribute.split(" ");
        return Arrays.stream(classes).anyMatch(c -> c.equalsIgnoreCase(className));
    }

}
