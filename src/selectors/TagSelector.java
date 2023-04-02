package selectors;

import dom.DomNode;

import java.util.function.Predicate;

public class TagSelector implements Predicate<DomNode> {

    private final String tagName;

    public TagSelector(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public boolean test(DomNode domNode) {
        return domNode.getTagName().equalsIgnoreCase(tagName);
    }

}
