package dom;

import java.util.ArrayDeque;
import java.util.regex.Pattern;

public class HtmlParser {

    private static final Pattern tagPattern = Pattern.compile("<(?<tag>[^ ]+?)(?: (?<attr>.+?))?>(?<text>[^<]*)(?<rest>.*)", Pattern.DOTALL);
    private static final Pattern attributesPattern = Pattern.compile("(?<key>[^ ]+)=\"(?<value>[^\"]*)\"", Pattern.DOTALL);

    public static Dom fromString(String html) throws HtmlParseException {
        var stack = new ArrayDeque<DomNode>();
        var matcher = tagPattern.matcher(html);
        int line = 0;

        DomNode root = null;

        while(matcher.find()) {
            String rest = matcher.group("rest");
            String tagName = matcher.group("tag");

            // Skip comments, DOCTYPE, etc.
            if(tagName.charAt(0) == '!') continue;

            if(tagName.charAt(0) == '/') {
                tagName = tagName.substring(1);
                DomNode node = stack.pop();
                if(!node.getTagName().equals(tagName)) error("Expecetd " + tagName + " but found " + node.getTagName());
                if(stack.isEmpty()) {
                    if(rest != null && rest.trim().length() > 0) {
                        error("No closing tag for " + tagName);
                    }
                } else {
                    stack.peek().addChild(node);
                }
            } else {
                var node = new DomNode(tagName);
                node.setParent(stack.peek());

                // Set text content, if present
                String textContent = matcher.group("text");
                if(textContent != null) node.setTextContent(textContent.trim());

                // Parse attributes, if present
                String attributes = matcher.group("attr");
                if(attributes != null) node.setAttributes(parseAttributes(attributes));

                stack.push(node);
                if(root == null) root = node;
            }

            if(rest != null) matcher = tagPattern.matcher(rest);
        }

        return root == null ? null : new Dom(root);
    }

    private static void error(String message) throws HtmlParseException {
        throw new HtmlParseException(message);
    }

    /**
     * Parse a string of attributes into an {@link Attributes}
     * object. Quoteless (key=value), valueless (required), and
     * singly-quoted attributes are not supported.
     *
     * @param attributesString e.g. `maxlength="10" required=""`
     */
    private static Attributes parseAttributes(String attributesString) {
        final var matcher = attributesPattern.matcher(attributesString);
        var attributes = new Attributes();

        while(matcher.find()) {
            String key = matcher.group("key");
            String value = matcher.group("value");
            attributes.set(key, value);
        }

        return attributes;
    }

}
