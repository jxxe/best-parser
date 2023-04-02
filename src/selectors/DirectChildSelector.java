package selectors;

import dom.DomNode;

import java.util.function.Predicate;

public class DirectChildSelector implements Predicate<DomNode> {

    private Predicate<? super DomNode> parentPredicate;

    @Override
    public boolean test(DomNode domNode) {
        if(parentPredicate == null)
            throw new RuntimeException("Never use predicate directly, only combined using and()");

        if(domNode.getParent() == null) return false;
        return parentPredicate.test(domNode.getParent());
    }

    @Override
    public Predicate<DomNode> and(Predicate<? super DomNode> other) {
        return domNode -> {
            var predicate = new DirectChildSelector();
            predicate.parentPredicate = other;
            return predicate.test(domNode);
        };
    }

}
