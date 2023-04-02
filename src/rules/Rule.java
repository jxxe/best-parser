package rules;

import dom.Dom;
import dom.DomNode;

import java.util.List;
import java.util.function.Predicate;

public class Rule {

    final private Predicate<DomNode> superPredicate;

    public Rule(List<Predicate<DomNode>> predicates) {
        superPredicate = predicates.stream().reduce(
            p -> true,
            (p1, p2) -> p2.and(p1)
        );
    }

    public List<DomNode> enforce(Dom dom) {
        return dom.flatten().stream().filter(superPredicate).toList();
    }

}