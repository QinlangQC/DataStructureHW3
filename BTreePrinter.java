/**
 * Created by Mac on 2015/3/23.
 */
import java.util.*;

public class BTreePrinter {
    private StringBuilder buf;

    public BTreePrinter(){
        buf = new StringBuilder();
    }

    public String printNode(SimpleBinaryNode t){
        int maxHeight = height(t);
        List<SimpleBinaryNode> list = new ArrayList<SimpleBinaryNode>();
        list.add(t);
        printInTree(list, 0, maxHeight);
        return buf.toString();
    }

    public int height(SimpleBinaryNode t){
        if (t == null)
            return 0;
        return Math.max(height(t.left), height(t.right)) + 1;
    }

    public void printWhiteSpace(int n){
        for (; n > 0; n--) {
            System.out.print(' ');
            buf.append(' ');
        }
    }

    public static boolean isAllNull(List<SimpleBinaryNode> list){
        for (SimpleBinaryNode node : list){
            if (node != null)
                return false;
        }
        return true;
    }

    public void printInTree(List<SimpleBinaryNode> nodeList, int level, int maxHeight){
        if (nodeList.isEmpty() || isAllNull(nodeList))
            return;
        int floor = maxHeight - level;
        int firstSpace = (int) Math.pow(2, floor - 1) - 1;
        int betweenSpace = (int) Math.pow(2, floor) - 1;
        printWhiteSpace(firstSpace);
        List<SimpleBinaryNode> newNodeList = new ArrayList<SimpleBinaryNode>();
        for (SimpleBinaryNode node: nodeList){
            if (node == null) {
                printWhiteSpace(1);
                newNodeList.add(null);
                newNodeList.add(null);
            }
            else {
                System.out.print(node.element);
                buf.append(node.element);
                newNodeList.add(node.left);
                newNodeList.add(node.right);
            }
            printWhiteSpace(betweenSpace);
        }
        System.out.println("");
        buf.append('\n');
        for (int i = 1; i < (int) Math.pow(2, Math.max(floor - 2, 0)) + 1; i++){
            printWhiteSpace(firstSpace - i);
            for (int j = 0; j < nodeList.size(); j++){
                if (nodeList.get(j) == null){
                    //printWhiteSpace(i * 2  + 1);
                    printWhiteSpace(betweenSpace + 1);
                    continue;
                }
                if (nodeList.get(j).left == null)
                    printWhiteSpace(1);
                else {
                    System.out.print("/");
                    buf.append("/");
                }
                printWhiteSpace(i * 2 - 1);
                if (nodeList.get(j).right == null)
                    printWhiteSpace(1);
                else {
                    System.out.print("\\");
                    buf.append("\\");
                }
                printWhiteSpace(betweenSpace - i * 2);
            }
            System.out.println("");
            buf.append('\n');
        }
        printInTree(newNodeList, ++level, maxHeight);
    }

    public static void main(String[] args){
        SimpleBinaryNode root = new SimpleBinaryNode(2);
        SimpleBinaryNode n11 = new SimpleBinaryNode(7);
        SimpleBinaryNode n12 = new SimpleBinaryNode(5);
        SimpleBinaryNode n21 = new SimpleBinaryNode(2);
        SimpleBinaryNode n22 = new SimpleBinaryNode(6);
        SimpleBinaryNode n23 = new SimpleBinaryNode(9);
        SimpleBinaryNode n31 = new SimpleBinaryNode(5);
        SimpleBinaryNode n32 = new SimpleBinaryNode(8);
        SimpleBinaryNode n33 = new SimpleBinaryNode(4);

        root.left = n11;
        root.right = n12;
        n11.left = n21;
        n11.right = n22;
        n12.right = n23;
        n22.left = n31;
        n22.right = n32;
        n23.left = n33;

        BTreePrinter b = new BTreePrinter();
        String buf = b.printNode(root);
        System.out.print(buf);
    }
}
