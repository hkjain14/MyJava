package LLD;

public class Trie_SearchWord {
    class TrieNode {
        TrieNode[] children;
        boolean isWord;

        TrieNode() {
            children = new TrieNode[26];
            isWord = false;
        }
    }
    TrieNode root;

    Trie_SearchWord() {
        this.root = new TrieNode();
    }

    void add(String word) {
        TrieNode node = root;
        for(char c : word.toCharArray()) {
            if(node.children[c-'a'] == null) {
                node.children[c-'a'] = new TrieNode();
            }
            node = node.children[c-'a'];
        }
        node.isWord = true;
    }

    boolean search(String word) {
        return searchUtil(word, root, 0);
    }

    boolean searchUtil(String word, TrieNode node, int index) {
        if(word.length() == index)
            return node.isWord;
        char c = word.charAt(index);
        if(c != '.') {
            return node.children[c-'a'] != null && searchUtil(word,node.children[c-'a'],index+1);
        } else {
            for(TrieNode n: node.children) {
                if(n!=null && searchUtil(word,n,index+1))
                    return true;
            }
            return false;
        }
    }

    public static void main(String[] args) {
        Trie_SearchWord dic = new Trie_SearchWord();
        dic.add("abc");
        dic.add("abcd");

        System.out.println(dic.search("...."));
    }
}
