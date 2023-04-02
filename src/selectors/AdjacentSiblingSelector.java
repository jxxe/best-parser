package selectors;

import dom.DomNode;

import java.util.function.Predicate;

public class AdjacentSiblingSelector implements Predicate<DomNode> {

    private Predicate<? super DomNode> siblingPredicate;

    @Override
    public boolean test(DomNode domNode) {
        if(siblingPredicate == null)
            throw new RuntimeException("Never use predicate directly, only combined using and()");

        if(domNode.getParent() == null) return false;

        var siblings = domNode.getParent().getChildren();
        int immediatelyYoungerSiblingIndex = siblings.indexOf(domNode) - 1;
        if(immediatelyYoungerSiblingIndex < 0) return false;

        var immediatelyYoungerSibling = siblings.get(immediatelyYoungerSiblingIndex);
        return siblingPredicate.test(immediatelyYoungerSibling);
    }

    @Override
    public Predicate<DomNode> and(Predicate<? super DomNode> other) {
        return domNode -> {
            var predicate = new AdjacentSiblingSelector();
            predicate.siblingPredicate = other;
            return predicate.test(domNode);
        };
    }

}
