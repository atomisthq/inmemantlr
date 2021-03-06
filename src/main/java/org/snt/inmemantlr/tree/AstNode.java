/*
* Inmemantlr - In memory compiler for Antlr 4
*
* Copyright 2016, Julian Thomé <julian.thome@uni.lu>
*
* Licensed under the EUPL, Version 1.1 or – as soon they will be approved by
* the European Commission - subsequent versions of the EUPL (the "Licence");
* You may not use this work except in compliance with the Licence. You may
* obtain a copy of the Licence at:
*
* https://joinup.ec.europa.eu/sites/default/files/eupl1.1.-licence-en_0.pdf
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the Licence is distributed on an "AS IS" basis, WITHOUT
* WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the Licence for the specific language governing permissions and
* limitations under the Licence.
*/

package org.snt.inmemantlr.tree;

import org.snt.inmemantlr.utils.EscapeUtils;

import java.util.List;
import java.util.Vector;

public class AstNode {

    private String label;
    private String ntype;
    private AstNode parent;
    private Ast tree;
    private int id;

    private List<AstNode> children;
    private static int cnt = 0;

    /**
     * constructor
     *
     * @param tree tree to whom the node belongs to
     */
    private AstNode(Ast tree) {
        this.tree = tree;
        id = cnt++;
        children = new Vector<>();
    }

    /**
     * constructor
     *
     * @param tree tree to whom the node belongs to
     * @param parent parent node
     * @param nt non terminal id
     * @param label label
     */
    protected AstNode(Ast tree, AstNode parent, String nt, String label) {
        this(tree);
        ntype = nt;
        this.label = label;
        this.parent = parent;
    }

    /**
     * deep copy constructor
     *
     * @param tree tree to whom the node belongs to
     * @param nod node to duplication
     */
    protected AstNode(Ast tree, AstNode nod) {
        this(tree);
        id = nod.id;
        ntype = nod.ntype;
        label = nod.label;
        for (AstNode c : nod.children) {
            AstNode cnod = new AstNode(tree, c);
            cnod.parent = this;
            this.tree.nodes.add(cnod);
            children.add(cnod);
        }
    }

    /**
     * get child with index i
     *
     * @param i the index of the child
     * @return child with index i
     */
    public AstNode getChild(int i) {
        assert 0 <= i && i < children.size();
        return children.get(i);
    }

    /**
     * get last child (note that nodes are ordered in their appearance)
     *
     * @return last child
     */
    public AstNode getLastChild() {
        if (!children.isEmpty()) {
            return children.get(children.size() - 1);
        }
        return null;
    }

    /**
     * get first child (note that nodes are ordered in their appearance)
     *
     * @return first child
     */
    public AstNode getFirstChild() {
        if (!children.isEmpty()) {
            return children.get(0);
        }
        return null;
    }

    /**
     * set parent node
     *
     * @param par parent node
     */
    public void setParent(AstNode par) {
        parent = par;
    }

    /**
     * check if node has parent
     *
     * @return true if node has parent, false otherwise
     */
    public boolean hasParent() {
        return parent != null;
    }

    /**
     * get parent node
     *
     * @return parent node
     */
    public AstNode getParent() {
        return parent;
    }

    /**
     * check if node has children
     *
     * @return true if node has children, false otherwise
     */
    public boolean hasChildren() {
        return !children.isEmpty();
    }

    /**
     * get List of children
     *
     * @return list of children
     */
    public List<AstNode> getChildren() {
        return children;
    }

    /**
     * append child node
     *
     * @param n child node to be added
     */
    public void addChild(AstNode n) {
        children.add(n);
    }

    /**
     * delete child node
     *
     * @param n child node to be deleted
     */
    public void delChild(AstNode n) {
        children.remove(n);
    }

    /**
     * replace child
     *
     * @param oldNode child to be replaced
     * @param newNode replacement
     */
    public void replaceChild(AstNode oldNode, AstNode newNode) {
        if (children.contains(oldNode)) {
            children.set(children.indexOf(oldNode), newNode);
            newNode.parent = this;
        }
    }

    /**
     * gt identifier
     *
     * @return id which identifies node uniquely
     */
    public int getId() {
        return id;
    }

    /**
     * get non-terminal rule of that node
     *
     * @return non-terminal rule
     */
    public String getRule() {
        return ntype;
    }

    /**
     * get label where special chars are escaped
     *
     * @return escaped label
     */
    public String getEscapedLabel() {
        return EscapeUtils.escapeSpecialCharacters(label);
    }

    /**
     * get label
     *
     * @return unescaped label
     */
    public String getLabel() {
        return label;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AstNode))
            return false;

        AstNode n = (AstNode) o;
        return n.getId() == getId() && n.ntype.equals(ntype) &&
                n.label.equals(label) && children.equals(n.children);
    }

    @Override
    public String toString() {
        return id + " " + ntype + " " + label;
    }

    /**
     * check whether this node is a leaf node
     *
     * @return true if node has no children, false otherwise
     */
    public boolean isLeaf() {
        return children.isEmpty();
    }
}
