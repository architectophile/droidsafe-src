package droidsafe.analyses.infoflow;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import soot.ArrayType;
import soot.G;
import soot.RefType;
import soot.SootField;
import soot.Type;
import soot.jimple.toolkits.pta.IAllocNode;
import droidsafe.analyses.pta.PTABridge;

public class AllocNodeUtils {
    private static AllocNodeUtils v;

    public static void run() {
        v = new AllocNodeUtils();
    }

    public static AllocNodeUtils v() {
        return v;
    }

    HashMap<IAllocNode, Set<IAllocNode>> allocNodeToReachableAllocNodes = new HashMap<IAllocNode, Set<IAllocNode>>();

    Set<IAllocNode> reachableAllocNodes(IAllocNode allocNode) {
        HashSet<IAllocNode> visitedAllocNodes = new HashSet<IAllocNode>();
        return reachableAllocNodes(allocNode, visitedAllocNodes);
    }

    Set<IAllocNode> reachableAllocNodes(IAllocNode allocNode, Set<IAllocNode> visitedAllocNodes) {
        visitedAllocNodes.add(allocNode);

        if (this.allocNodeToReachableAllocNodes.containsKey(allocNode)) {
            return this.allocNodeToReachableAllocNodes.get(allocNode);
        }

        Set<IAllocNode> reachableAllocNodes = new HashSet<IAllocNode>();
        reachableAllocNodes.add(allocNode);
        Set<IAllocNode> directlyReachableAllocNodes = new HashSet<IAllocNode>();
        Type type = allocNode.getType();
        if (type instanceof RefType) {
            if (allocNode instanceof soot.jimple.spark.pag.AllocNode) {
                Set<soot.jimple.spark.pag.AllocDotField> allocDotFields = ((soot.jimple.spark.pag.AllocNode)allocNode).getFields();
                for (soot.jimple.spark.pag.AllocDotField allocDotField : allocDotFields) {
                    // FIXME FIXME       FIXME       FIXME                   FIXME
                    // FIXME             FIXME             FIXME       FIXME
                    // FIXME FIXME       FIXME                   FIXME
                    // FIXME             FIXME             FIXME       FIXME
                    // FIXME             FIXME       FIXME                   FIXME
                    try {
                        SootField field = (SootField)allocDotField.getField();
                        Set<IAllocNode> allocNodes = (Set<IAllocNode>) PTABridge.v().getPTSet(allocNode, field);
                        directlyReachableAllocNodes.addAll(allocNodes);
                    } catch (RuntimeException exception) {
                        exception.printStackTrace(G.v().out);
                    }
                }
            } else if (allocNode instanceof soot.jimple.paddle.AllocNode) {
                Iterator allocDotFields = ((soot.jimple.paddle.AllocNode)allocNode).fields();
                while (allocDotFields.hasNext()) {
                    soot.jimple.paddle.AllocDotField allocDotField = (soot.jimple.paddle.AllocDotField)allocDotFields.next();
                    // FIXME FIXME       FIXME       FIXME                   FIXME
                    // FIXME             FIXME             FIXME       FIXME
                    // FIXME FIXME       FIXME                   FIXME
                    // FIXME             FIXME             FIXME       FIXME
                    // FIXME             FIXME       FIXME                   FIXME
                    try {
                        SootField field = (SootField)allocDotField.field();
                        Set<IAllocNode> allocNodes = (Set<IAllocNode>) PTABridge.v().getPTSet(allocNode, field);
                        directlyReachableAllocNodes.addAll(allocNodes);
                    } catch (RuntimeException exception) {
                        exception.printStackTrace(G.v().out);
                    }
                }
            }
        } else if (type instanceof ArrayType) {
            directlyReachableAllocNodes.addAll(PTABridge.v().getPTSetOfArrayElement(allocNode));
        }
        for (IAllocNode directlyReachableAllocNode : directlyReachableAllocNodes) {
            if (!(visitedAllocNodes.contains(directlyReachableAllocNode))) {
                reachableAllocNodes.addAll(reachableAllocNodes(directlyReachableAllocNode, visitedAllocNodes));
            }
        }
        this.allocNodeToReachableAllocNodes.put(allocNode, reachableAllocNodes);

        return reachableAllocNodes;
    }
}
