package selectors;

import dom.DomNode;

import java.util.function.Predicate;

public class IdSelector implements Predicate<DomNode> {

    private final String id;

    public IdSelector(String id) {
        this.id = id;
    }

    @Override
    public boolean test(DomNode domNode) {
        String idAttribute = domNode.getAttribute("id");
        return idAttribute != null && idAttribute.equalsIgnoreCase(id);
    }

}
