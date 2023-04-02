package selectors;

import dom.DomNode;

import java.util.function.Predicate;

public class WildcardSelector implements Predicate<DomNode> {

    @Override
    public boolean test(DomNode domNode) {
        return true;
    }

}
