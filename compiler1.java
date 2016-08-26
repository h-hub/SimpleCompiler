import java.util.*;
import java.io.*;

class compiler1{

  public static void main(String args[]){
    
    InputStreamReader isr=new InputStreamReader(System.in);
    BufferedReader br=new BufferedReader(isr);
    while(true){
      //FSM number =new FSM();
        System.out.println("=================FSM=================");
        System.out.println("Enter A String You Want:");
        try{
          String number=br.readLine();
          NFA(number);
  
        }catch(Exception e){
          System.out.println("Error");
        }
      
    }
    
  }
/*------------------  check for the floating point number **begin** ---------------------------------------------------*/  
  public static void NFA(String number){
  
    //connection between nodes
    String[][] node= new String[8][8];
    node[0][1]="1234567890";
    node[0][4]=".";
    node[1][2]=".";
    node[1][1]="1234567890";
    node[1][5]="Ee";//
    node[2][3]="1234567890";
    node[2][5]="eE";
    node[3][3]="1234567890";
    node[3][5]="eE";
    node[5][7]="-+";
    node[5][6]="1234567890";
    node[7][6]="1234567890";
    node[6][6]="1234567890";
    node[4][3]="1234567890";
    
    //array of nodes
    qNode[] qNode_array = new qNode[8];
    for(int i=0;i< qNode_array.length ;i++){
      qNode_array[i]=new qNode(i);
      
    }
    
    //initialise the nodes
    for(int i=0;i< qNode_array.length ;i++){
      qNode_array[i].color="white";
      qNode_array[i].distance=Double.POSITIVE_INFINITY;
      qNode_array[i].parent=null;
    }
      
      //final nodes
      qNode_array[2].isfinal=true;
      qNode_array[3].isfinal=true;
      qNode_array[6].isfinal=true;
      
      //initial node
      qNode_array[0].color="gray";
      qNode_array[0].distance=0;
      qNode_array[0].parent=null;
      
      //create a queue of nodes
      queue q1=new queue();
      
      //inser the initial node
      q1.enqueue(qNode_array[0]);
      
      //character counter
      int increment=0;
      
      //check for the floating point number using BFS
      while(!q1.isempty()){
        
        //get an element from the queue
        qNode u = q1.dequeue();
        
        //adjacent elements
        Vector<Integer> a1=adjecents(node,u.element1);
        
        //create an iterator to loop through the adjacent elements
        Iterator itr = a1.iterator();
        
        //for each v Î Adj[u] (for each adjacent node)
        while(itr.hasNext()){
        
        //index of the nodes
        int i=(Integer)(itr.next());
        
        //take one character at a time
        char c=number.charAt(increment);
        
          //if there is a path between nodes
          if(node[u.element1][i].contains(Character.toString(c))){
          
          //reached the end of the string and in a final state
          if(increment==(number.length()-1) && qNode_array[i].isfinal==true){
          System.out.println("string is excepted");
          System.exit(0);
          }
          
          //reached the end of the string and not in a final state
          if(increment==(number.length()-1) && qNode_array[i].isfinal==false){
          System.out.println("string is not excepted");
          System.exit(0);
          }
          
          //if there is a path to itself, then put it back, and then break.
          if(u.element1==i){
            q1.enqueue(qNode_array[i]);
            break;
          }
          
          //change the attributes of the node and put it into the queue
          if(qNode_array[i].color=="white"){
            qNode_array[i].color="gray";
            qNode_array[i].distance = qNode_array[u.element1].distance+1;
            qNode_array[i].parent = u;
            q1.enqueue(qNode_array[i]);
          }
          }
          
        }
        //if no path was found, then display message
        if(q1.isempty()){
          System.out.println("string is not excepted");
          System.exit(0);
        }
        
        qNode_array[u.element1].color="black";
        
        //increment by one to move on to the next character
        increment++;
        
      }
  }
/*------------------  check for the floating point number **end** ---------------------------------------------------*/  

  
  //get the adjacent nodes
  public static Vector adjecents(String[][] node,int index){
      Vector<Integer> v1=new Vector();
      for(int i=0; i<node[index].length;i++){
        if(node[index][i]!=null){
          v1.add(i);
        }
      }
      return v1;
  }
}


// data structer for qNode
class qNode {

        public int   element1;
        public qNode next;
    public double distance;
    public qNode parent;
    public String color;
    public boolean isfinal;
     
     
  
    public qNode( int theElement1 ) {
        this( theElement1,null );
    }
    
    public qNode( int theElement1,qNode n ) {
        element1 = theElement1;
        next    = n;
    } 
}

//data structure for the queue
class queue{

    public qNode front;
    public qNode rear;
     
    public queue(){
            front=rear=null;
    }
     
    public boolean isempty(){
            return front==null;
    }
     
    public void enqueue(qNode x){
            if(isempty())
            front=rear=x;
            else
            rear=rear.next=x;
    }
  
  public qNode dequeue(){
      qNode ret=front;
            if(isempty()){
            System.out.println("is empty");
      return null;
      }
            else{
            front=front.next;
      return ret;
      }
    }
  
}