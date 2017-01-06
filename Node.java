import java.util.ArrayList;
import java.util.HashMap;
/**
 * An implemenation of a Node class.
 * 
 * @author Mordechai Schmutter
 * @version 1.0
 */

public class Node
{
    private String tag;
    private Node parent;
    private ArrayList<Node> children = new ArrayList<Node>();
    private HashMap<String, String> attributes = new HashMap<String, String>();
    public Node(Node parent, String tag)
    {
        this.parent = parent;
        this.tag = tag;
    }

    public Node getParent()
    {
        return this.parent;
    }

    public void addChild(Node child)
    {
        this.children.add(child);
    }

    public ArrayList<Node> getChildren()
    {
        return this.children;
    }

    public String getTag()
    {
        return this.tag;
    }

    public HashMap<String, String> getAttributes()
    {
        return this.attributes;
    }

    public void addAttribute(String key, String value)
    {
        this.attributes.put(key, value);
    }
}