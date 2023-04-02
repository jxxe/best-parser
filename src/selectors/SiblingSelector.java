package selectors;

import dom.DomNode;

import java.util.Optional;
import java.util.function.Predicate;

public class SiblingSelector implements Predicate<DomNode> {

    private Predicate<? super DomNode> siblingPredicate;

    @Override
    public boolean test(DomNode domNode) {
        if(siblingPredicate == null)
            throw new RuntimeException("Never use predicate directly, only combined using and()");

        if(domNode.getParent() == null) return false;

        var siblings = domNode.getParent().getChildren();
        int index = siblings.indexOf(domNode);

        Optional<DomNode> first = siblings.stream().filter(siblingPredicate).findFirst();
        return first.isPresent() && siblings.indexOf(first.get()) < index;
    }

    @Override
    public Predicate<DomNode> and(Predicate<? super DomNode> other) {
        return domNode -> {
            var predicate = new SiblingSelector();
            predicate.siblingPredicate = other;
            return predicate.test(domNode);
        };
    }

}
