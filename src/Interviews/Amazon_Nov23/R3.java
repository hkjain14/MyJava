package Interviews.Amazon_Nov23;

public class R3 {
    /*
    > >  Given 2 arrays X and Y of size N
> > >
> > >   output = 0;
> > >
> > >   for (i=0->N-1)
> > >     for(j=0->N-1)
> > >       for(k=0->N-1)
> > >         if(X[i]==X[j] or X[j]==X[k] or X[k]==X[i])
> > >           continue;
> > >         else
> > >           output = max(output, Y[i]+Y[j]+Y[k])
> > >  return output




X : 1,2,3,2,4

1,2,3
1,2,4
1,3,2
1,2,4
1,3,4


map :
1 : 1
2: 2,4
3: 3
4 : 5


Sorted X : 1,2,2,3,4
           i,j, ,k


Y : 3,4,5,6,7

Problem 4: Given a binary tree, check whether it is symmetric.

>      1
>    /   \
>   2     2
>  / \   / \
> 3   4 4   3
> This is symmetric.



class Node {
    int val;
    Node left;
    Node right;
}


boolean isMirror(Node node1, Node node2) {

    if(node1.left == null && node2.right == null)
        return true;


    if(node1.left != null && node2.right != null && node1.val == node2.val)
        return isMirror(node1.left, node2.right) && isMirror(node1.right, node2.left);


    return false;
}


boolean isSymmetricTree(Node root) {
    if(root != null) {
        return isMirror(root, root);
    }
}


boolean isNewSymmetricTreeUtil(Node root, boolean isEven) {
    if(isEven) {

    }
}


boolean isNewSymmetricTree(Node root) {
    if(root != null) {
        return isNewSymmetricTreeUtil(root,false);
    }

}



Part 2:
New-symmetric:
      ....
>   2     2 -->  Odd levels should be symmetric.
>  / \   / \
> 3   4 3   4 --> Even levels should have the same elements.

Find whether the tree is new-symmetric.
     */
}
