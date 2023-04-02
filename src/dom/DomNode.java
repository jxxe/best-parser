package dom;

import java.util.ArrayList;
import java.util.List;

public class DomNode {

    private String tagName;
    private Attributes attributes = new Attributes();

    private DomNode parent = null;
    private List<DomNode> children = new ArrayList<>();
    private String textContent = "";

    public DomNode(String tagName) { this.tagName = tagName; }

    public void setTextContent(String textContent) { this.textContent = textContent; }
    public String getTextContent() { return textContent; }

    public void setAttributes(Attributes attributes) { this.attributes = attributes; }
    public void setAttribute(String key, String value) { attributes.set(key, value); }
    public String getAttribute(String key) { return attributes.get(key); }

    public String getTagName() { return tagName; }
    // public void setTagName(String tagName) { this.tagName = tagName; }

    public DomNode getParent() { return parent; }
    public void setParent(DomNode parent) { this.parent = parent; }

    public List<DomNode> getChildren() { return children; }
    public void addChild(DomNode child) { children.add(child); }
    public void removeAllChildren() { children.clear(); }
    public boolean hasChildren() { return !children.isEmpty(); }

    @Override
    public String toString() {
        return '<' + getTagName() + "> \"" + getTextContent() + "\" [" + attributes + ']';
    }

}