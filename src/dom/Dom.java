package dom;

import java.util.ArrayList;
import java.util.List;

public class Dom {

    private DomNode root;
    private List<DomNode> flattened = null;

    public Dom() {
    }

    public Dom(DomNode root) {
        this.root = root;
    }

    /**
     * Flattens the DOM tree into an array
     * @return A list of every element in the DOM
     */
    public List<DomNode> flatten() {
        if(flattened == null) flattened = flatten(getRoot());
        return flattened;
    }

    /**
     * Used for recursion, see {@link #flatten()}
     */
    private List<DomNode> flatten(DomNode node) {
        var nodes = new ArrayList<DomNode>();

        if(node != null) {
            nodes.add(node);

            for(DomNode child : node.getChildren()) {
                nodes.addAll(flatten(child));
            }
        }

        return nodes;
    }

    /**
     * Generate a DOM tree from an HTML string
     * @param html The HTML to parse, which must have one root element
     * @return A DOM object or null if parsing fails
     */
    public static Dom fromString(String html) {
        try {
            return HtmlParser.fromString(html);
        } catch(HtmlParseException e) {
            return null;
        }
    }

    public DomNode getRoot() {
        return root;
    }

    /**
     * Turns the DOM tree into a human-readable string where each line
     * begins with a tag name, text between quotes is the textContent of
     * nodes, and numbers between brackets is the count of attributes
     */
    @Override public String toString() {
        return generateString(getRoot(), 0).trim();
    }

    /**
     * Used for recursion, see {@link #toString()}
     */
    private String generateString(DomNode node, int depth) {
        StringBuilder string = new StringBuilder()
            .append(" ".repeat(2).repeat(depth))
            .append(node.toString());

        string.append("\n");

        depth++;

        for(DomNode child : node.getChildren()) {
            string.append(generateString(child, depth));
        }

        return string.toString();
    }

}
