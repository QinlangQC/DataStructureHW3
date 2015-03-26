
public class SimpleStack extends LinkedList {
    public SimpleStack(){
        header = null;
    }

    public Object peek(){
        if (header == null)
            return  null;
        return header.data;
    }

    public Object pop(){
        if (header == null)
            return null;
        Object result = header.data;
        header = header.next;
        return result;
    }

    public Object push(Object n){//add front
        if (header == null) {
            Node newNode = new Node(n, null);
            header = newNode;
            return n;
        }
        Node newNode = new Node(n, header);
        header = newNode;
        return n;
    }
}
