/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package birds;

/**
 *
 * @author whyfj
 */
public class OrderedDictionary implements OrderedDictionaryADT {

    Node root;
    
    OrderedDictionary(){
        root=new Node(null);
        //root.makeLeaf();
    }
    
    @Override
    public BirdRecord find(DataKey k) throws DictionaryException {
        return findNode(k).value;
    }
    
    private Node findNode(DataKey k) throws DictionaryException {
        this.emptyCheck();
        Node cNode=root;
        boolean found=false;
        while(!found&&cNode.value!=null){//stop when it is found or reach the leaf.
            
            switch (k.compareTo(cNode.value.getDataKey())){
                case 0:
                    //key Found
                    found=true;
                    break;
                case 1:
                    //key is larger than current node, go right child.
                    cNode=cNode.right;
                    break;
                case -1:
                    //key is smaller than current node, go left child.
                    cNode=cNode.left;
                    break;
                default:
                    throw new DictionaryException("Error: @Found  incorrect return value of compareTo()");
            }
            
        }
        if (!found) throw new DictionaryException("Warning: @find  No Such key!");     //throw warning if it is not found
        return cNode;     //if it is found, return the key.
    }

    @Override
    public void insert(BirdRecord r) throws DictionaryException {
        Node cNode=root;
        while (cNode.value!=null)
        {
            //find the leaf with null value
            switch (r.getDataKey().compareTo(cNode.value.getDataKey())){
                case 0:
                    //exist same key
                    throw new DictionaryException("Error: @insert key exist");
                case 1:
                    //key is larger than current node, go right
                    cNode=cNode.right;
                    break;
                case -1:
                    //key is small than current node, go left
                    cNode=cNode.left;
                    break;
                default:
                    throw new DictionaryException("Warning: @insert incorrect return value of compareTo()");
            }
        }
        cNode.value=r;
        cNode.makeLeaf();
    }

    @Override
    public void remove(DataKey k) throws DictionaryException {
        try {
            Node node=findNode(k);
            if(node.left.value==null&&node.right.value==null){
                //no non-null-value node, type 1, make it leaf
                node.left=node.right=null;
                node.value=null;
            }
            else if(node.left.value==null||node.right.value==null){
                //one and only one of children is non-null-value node
                Node temp=node.left.value==null?node.right:node.left;               //get the non-null child
                node.value=temp.value;
                node.left=temp.left;
                node.right=temp.right;
                temp.left.parent=node;
                temp.right.parent=node;
            }
            else{
                //two non-null-value children
                BirdRecord temp = subTree(node.left).largest();
                remove(temp.getDataKey());
                node.value=temp;
            }
        }catch (DictionaryException ex){
            throw new DictionaryException("No such record key exists");
        }
        
    }
    
    

    @Override
    public BirdRecord successor(DataKey k) throws DictionaryException {
        if (k.compareTo(this.largest().getDataKey())==0) throw new DictionaryException("There is no successor for the given record key");
        if (k.compareTo(this.largest().getDataKey())==1) return this.predecessor(k);
        try{
            Node node=findNode(k);            
            if (node.right.value==null){
                Node cNode=node.parent;
                while(k.compareTo(cNode.value.getDataKey())!=-1){
                    //find the parent that larger than itself
                    cNode=cNode.parent;
                }
                return cNode.value;
            }
            else{
                return subTree(node.right).smallest();
            }
        }catch(DictionaryException ex){
            insert(new BirdRecord(k,"","",""));
            BirdRecord temp=successor(k);
            remove(k);
            return temp;
        }
        
    }

    @Override
    public BirdRecord predecessor(DataKey k) throws DictionaryException {
        if (k.compareTo(this.smallest().getDataKey())==0) throw new DictionaryException("There is no predecessor for the given record key");
        if (k.compareTo(this.smallest().getDataKey())==-1) return this.successor(k);
        try{
            Node node=findNode(k);            
            if (node.left.value==null){
                Node cNode=node.parent;
                while(k.compareTo(cNode.value.getDataKey())!=1){
                    //find the parent that larger than itself()
                    cNode=cNode.parent;
                }
                return cNode.value;
            }
            else{
                return subTree(node.left).largest();
            }
        }catch(DictionaryException ex){
            insert(new BirdRecord(k,"","",""));
            BirdRecord temp=predecessor(k);
            remove(k);
            return temp;
        }

    }

    @Override
    public BirdRecord largest() throws DictionaryException {
        this.emptyCheck();
        Node cNode=root;
        
        while(cNode.right.value!=null){
            cNode=cNode.right;
        }
        
        return cNode.value;
    }

    @Override
    public BirdRecord smallest() throws DictionaryException {
        this.emptyCheck();
        Node cNode=root;
        
        while(cNode.left.value!=null){
            cNode=cNode.left;
        }
        
        return cNode.value;
    }

    @Override
    public boolean isEmpty() {
        return root.value==null;
    }
    
    private void emptyCheck() throws DictionaryException{
        if (isEmpty()) throw new DictionaryException("Error, Tree is empty!");
    }

    private OrderedDictionary subTree(Node subRoot){
        OrderedDictionary r=new OrderedDictionary();
        r.root=subRoot;
        return r;
    }
    
    
    
    private class Node{
        BirdRecord value;
        Node left=null;
        Node right=null;
        Node parent=null;
        
        Node(BirdRecord value){
            this.value=value;
        }
        
        void makeLeaf(){
            left=new Node(null);
            right=new Node(null);
            left.parent=this;
            right.parent=this;
        }
        
        void cpNode(Node n){
            this.value=n.value;
            this.left=n.left;
            this.right=n.right;
            this.parent=n.parent;
            
        }
        
        
//        Node(BirdRecord value,Node left,Node right){
//            this.left=left;
//            this.right=right;
//        }
        
    }
    
}
