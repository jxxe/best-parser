package selectors;

import dom.DomNode;

import java.util.function.Predicate;

public class DescendantSelector implements Predicate<DomNode> {

    private Predicate<? super DomNode> ancestorPredicate;

    @Override
    public boolean test(DomNode domNode) {
        if(ancestorPredicate == null)
            throw new RuntimeException("Never use predicate directly, only combined using and()");

        DomNode parent = domNode.getParent();

        while(parent != null) {
            if(ancestorPredicate.test(parent)) return true;
            parent = parent.getParent();
        }

        return false;
    }

    @Override
    public Predicate<DomNode> and(Predicate<? super DomNode> other) {
        return domNode -> {
            var predicate = new DescendantSelector();
            predicate.ancestorPredicate = other;
            return predicate.test(domNode);
        };
    }

}
