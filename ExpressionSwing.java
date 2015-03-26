import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExpressionSwing {
    private JTextField inputExpression;
    private JButton postfixButton;
    private JTextField postfix;
    private JPanel P;
    private JTextArea tree;
    private JButton treeButton;
    private JTextField result;
    private JButton Evaluate;
    private JButton infixButton;
    private JTextField infix;
    private SimpleBinaryNode root;

    private boolean checkPriority(char a, char b) {
        int aa = 0;
        int bb = 0;
        if (a == '(')
            return false;
        if (a == '^')
            aa = 2;
        if (b == '^')
            bb = 2;
        if (a == '*' || a == '/')
            aa = 1;
        if (b == '*' || b == '/')
            bb = 1;
        return (bb <= aa);
    }

    public ExpressionSwing() {
        postfixButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String infix = inputExpression.getText();
                SimpleStack stack = new SimpleStack();
                LinkedList output = new LinkedList();
                char[] chars = infix.toCharArray();
                for (char ch : chars) {
                    if (ch >= '0' && ch <= '9')
                        output.append(ch);
                    else if (ch == '(')
                        stack.push(ch);
                    else if (ch == ')') {
                        while ((Character) stack.peek() != '(')
                            output.append(stack.pop());
                        stack.pop();
                    } else {
                        if (stack.isEmpty()) {
                            stack.push(ch);
                            continue;
                        }
                        while (checkPriority((Character) stack.peek(), ch))
                            output.append(stack.pop());
                        stack.push(ch);
                    }
                }
                while (!stack.isEmpty())
                    output.append(stack.pop());
                StringBuilder builder = new StringBuilder(output.size());
                SimpleIterator it = output.ListIterator();
                builder.append(it.next());
                while (it.hasNext())
                    builder.append(it.next());
                postfix.setText(builder.toString());
            }
        });

        treeButton.addActionListener(new ActionListener() {
            public void LoadNode(SimpleBinaryNode node, SimpleIterator it) {
                Character chr = (Character) it.next();
                node.element = chr;
                if (chr >= '0' && chr <= '9')
                    return;
                node.left = new SimpleBinaryNode();
                node.right = new SimpleBinaryNode();
                LoadNode(node.right, it);
                LoadNode(node.left, it);
            }

            public void actionPerformed(ActionEvent e) {
                String post = postfix.getText();
                LinkedList nodes = new LinkedList();
                for (char chr : post.toCharArray())
                    nodes.append(chr);
                nodes.reverse();
                root = new SimpleBinaryNode();
                SimpleIterator it = nodes.ListIterator();
                LoadNode(root, it);
                BTreePrinter bp = new BTreePrinter();
                String treeS = bp.printNode(root);
                tree.setText(treeS);
            }
        });

        Evaluate.addActionListener(new ActionListener() {
            public int evaluate(SimpleBinaryNode t) {
                if ((Character) t.element <= '9' && (Character) t.element >= '0')
                    return Character.getNumericValue((Character) t.element);
                if ((Character) t.element == '+')
                    return evaluate(t.left) + evaluate(t.right);
                if ((Character) t.element == '-')
                    return evaluate(t.left) - evaluate(t.right);
                if ((Character) t.element == '*')
                    return evaluate(t.left) * evaluate(t.right);
                if ((Character) t.element == '/')
                    return evaluate(t.left) / evaluate(t.right);
                else //if ((Character) t.element == '^')
                    return (int) Math.pow(evaluate(t.left), evaluate(t.right));
            }

            public void actionPerformed(ActionEvent e) {
                result.setText(Integer.toString(evaluate(root)));
            }
        });
        infixButton.addActionListener(new ActionListener() {
            public String toInfix(SimpleBinaryNode t, String s) {
                if (t.left == null && t.right == null)
                    return s + t.element;
                return '(' + toInfix(t.left, s) + t.element + toInfix(t.right, s) + ')';
            }

            public void actionPerformed(ActionEvent e) {
                String s = "";
                infix.setText(toInfix(root, s));
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ExpressionSwing");
        frame.setContentPane(new ExpressionSwing().P);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        P = new JPanel();
        P.setLayout(new GridLayoutManager(5, 2, new Insets(0, 0, 0, 0), 0, 0));
        postfix = new JTextField();
        postfix.setText("");
        P.add(postfix, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension(150, 20), null, 0, false));
        postfixButton = new JButton();
        postfixButton.setText("Postfix");
        P.add(postfixButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, new Dimension(108, 20), null, 0, false));
        inputExpression = new JFormattedTextField();
        P.add(inputExpression, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, new Dimension(150, 22), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Expression");
        label1.setVerticalAlignment(0);
        P.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, new Dimension(108, 22), null, 1, false));
        treeButton = new JButton();
        treeButton.setText("Tree");
        P.add(treeButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tree = new JTextArea();
        tree.setText("");
        P.add(tree, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 250), new Dimension(350, 250), null, 0, false));
        Evaluate = new JButton();
        Evaluate.setText("Evaluate");
        P.add(Evaluate, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        result = new JTextField();
        result.setText("");
        P.add(result, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        infixButton = new JButton();
        infixButton.setText("Infix");
        P.add(infixButton, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        infix = new JTextField();
        P.add(infix, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return P;
    }
}
