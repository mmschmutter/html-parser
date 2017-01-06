import java.util.concurrent.LinkedBlockingQueue;
import java.util.UUID;
import java.util.ArrayList;
/**
 * Parses a given HTML string into a DOM tree which can then be printed out in either breadth first or depth first order.
 * 
 * @author Mordechai Schmutter 
 * @version 1.0
 */
public class Tree
{
    private ArrayList<String> htmlList;
    private Node root;
    private Node parent;
    public void breadthFirst(Node n)
    {
        LinkedBlockingQueue<Node> queue = new LinkedBlockingQueue<Node>();
        queue.add(n);
        while(!queue.isEmpty())
        {
            Node current = queue.remove();
            System.out.println(current.getTag() + " " + current.getAttributes());
            for(Node child : current.getChildren())
            {
                queue.add(child);
            }
        }
    }

    public void depthFirst(Node n)
    {
        System.out.println(n.getTag() + " " + n.getAttributes());
        for(Node child : n.getChildren())
        {
            depthFirst(child);
        }
    }

    public Node parse(String html)
    {
        if(html == null || html.isEmpty())
        {
            throw new IllegalArgumentException("Invalid HTML");
        }
        String[] htmlArray = html.split("(\\s*<)|(>\\s*)");
        htmlList = new ArrayList<String>();
        for(int i = 0; i < htmlArray.length; i++) {
            if(!htmlArray[i].equals("")){
                htmlList.add(htmlArray[i].trim());
            }
        }
        parent = null;
        for(String element : htmlList) {
            if(element.matches("(html|head|title|body|p|b|u|i|ol|ul|li|h1|h2|blockquote|div|code|pre|span|iframe)"))
            {
                Node node = new Node(parent, element);
                String uuid = UUID.randomUUID().toString();
                node.addAttribute("id", uuid);
                if(parent != null) {
                    parent.addChild(node);
                }
                else{
                    root = node;
                }
                parent = node;
            }
            else if(element.matches("(html|head|title|body|p|b|u|i|ol|ul|li|h1|h2|blockquote|div|code|pre|span|iframe)(\\s.+)?"))
            {
                ArrayList<String> attributeList = new ArrayList<String>();
                String[] attributeArray = element.split("\\s", 2);
                String tag = attributeArray[0];
                attributeArray = attributeArray[1].split("(\"|\')\\s");
                for(int i = 0; i < attributeArray.length; i++) {
                    if(!attributeArray[i].equals("")){
                        if(i == attributeArray.length - 1){
                            attributeArray[i] = attributeArray[i].substring(0, attributeArray[i].length() - 1);
                        }
                        attributeList.add(attributeArray[i].trim());
                    }
                }
                Node node = new Node(parent, tag);
                String uuid = UUID.randomUUID().toString();
                node.addAttribute("id", uuid);
                for(String attribute : attributeList){
                    String[] mapArray = attribute.split("=(\"|\')");
                    ArrayList<String> mapList = new ArrayList<String>();
                    for(int i = 0; i < mapArray.length; i++) {
                        if(!mapArray[i].equals("")){
                            mapList.add(mapArray[i].trim());
                        }
                    }
                    node.addAttribute(mapList.get(0), mapList.get(1));
                }
                if(parent != null) {
                    parent.addChild(node);
                }
                else{
                    root = node;
                }
                parent = node;
            }
            else if(element.matches("/.*"))
            {
                parent = parent.getParent();
            }
            else
            {
                Node node = new Node(parent, "text");
                String uuid = UUID.randomUUID().toString();
                node.addAttribute("id", uuid);
                node.addAttribute("content", element);
                parent.addChild(node);
            }
        }
        return root;
    }

    public Node getRoot(){
        return root;
    }
}