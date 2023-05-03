package rules;

import dom.DomNode;
import selectors.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RuleParser {

    public static Rule fromString(String rule) {
        var selectors = new ArrayList<Predicate<DomNode>>();

        var dividers = new Character[]{' ', '>', '.', '#', '~', '+'};
        String regex = Arrays.stream(dividers).map(d -> {
            return String.format("((?<=\\%1$c)|(?=\\%1$c))", d);
        }).collect(Collectors.joining("|"));

        String[] tokens = rule.split(regex);
        var iterator = Arrays.asList(tokens).iterator();

        while(iterator.hasNext()) {
            String token = iterator.next().trim();

            selectors.add(
                switch(token) {
                    case "" -> new DescendantSelector();
                    case ">" -> new ChildSelector();
                    case "." -> new ClassSelector(iterator.next());
                    case "#" -> new IdSelector(iterator.next());
                    case "~" -> new SiblingSelector();
                    case "+" -> new AdjacentSiblingSelector();
                    case "*" -> new WildcardSelector();
                    default -> new TagSelector(token);
                }
            );
        }

        return new Rule(selectors);
    }

}
