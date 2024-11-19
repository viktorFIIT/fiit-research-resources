package stochastictree;

import javafx.scene.control.Alert;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.w3c.dom.Document;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxStylesheet;

/**
 * Wrapper around JGraphT library used to visualize stochastic tree.
 * */
public class StochasticTree {

    private Map<Integer, List<Vertex>> levelsAndNodes = new HashMap<>();

    private List<Vertex> allNodesInTree = new ArrayList<>();

    private DefaultDirectedGraph<Vertex, DefaultEdge> g = new DefaultDirectedGraph<>(DefaultEdge.class);

    private int levelsCounter = 0;

    /**
     * Clears stochastic tree after clicking on the button labeled 'Clear'
     * */
    void clearGraphStorage(List<String> alreadyInsertedPatternNames) {
        alreadyInsertedPatternNames.clear();
        levelsCounter = 0;
        allNodesInTree.clear();
        levelsAndNodes.clear();
        g = new DefaultDirectedGraph<>(DefaultEdge.class); // replace previous stochastic tree with new empty one
    }

    /**
     * Re-constructs stochastic tree by adding nodes for pattern abbreviations inserted into text field.
     * */
    WebView visualizeStochasticTreeUsingTextField(String patterNameTextField, int numberOfPatterns) {
        if (levelsCounter == 0) {

            Vertex sourceNode = new Vertex(" root ");
            sourceNode.setProbability(0);

            Vertex insertedFirstNodeApplied = new Vertex(patterNameTextField);
            insertedFirstNodeApplied.setProbability(1.0/numberOfPatterns);

            Vertex insertedFirstNodeNotApplied = new Vertex("¬" + patterNameTextField);
            insertedFirstNodeNotApplied.setProbability(1.0-(1.0/numberOfPatterns));

            List nodes = new ArrayList<Vertex>();
            nodes.add(insertedFirstNodeApplied);
            nodes.add(insertedFirstNodeNotApplied);
            levelsAndNodes.put(levelsCounter, nodes);

            g.addVertex(sourceNode);
            g.addVertex(insertedFirstNodeApplied);
            g.addVertex(insertedFirstNodeNotApplied);

            g.addEdge(sourceNode, insertedFirstNodeApplied);
            g.addEdge(sourceNode, insertedFirstNodeNotApplied);

            levelsCounter = levelsCounter + 1;
        } else {

            List<Vertex> additionalNodes = new ArrayList<>();
            List<Vertex> nodesOnPreviousLevel = levelsAndNodes.get(levelsCounter - 1);

            for (Vertex node : nodesOnPreviousLevel) {

                Vertex subtreeRoot = node;
                g.addVertex(subtreeRoot);
                if (!allNodesInTree.contains(subtreeRoot)) allNodesInTree.add(subtreeRoot);

                Vertex leftNode = new Vertex(patterNameTextField);
                // store information about whole path
                // this should register all parents of parent of left node
                for (Vertex parent : node.getParents()) {
                    leftNode.addParent(parent);
                }
                leftNode.addParent(node);

                // use information about whole path to calculate denominator
                leftNode.setProbability(1.0/calculateDenominator(leftNode, numberOfPatterns));

                g.addVertex(leftNode);
                if (!allNodesInTree.contains(leftNode)) allNodesInTree.add(leftNode);
                additionalNodes.add(leftNode);
                g.addEdge(subtreeRoot, leftNode);

                Vertex rightNode = new Vertex("¬" + patterNameTextField);
                // store information about whole path
                for (Vertex parent : node.getParents()) {
                    rightNode.addParent(parent);

                }
                rightNode.addParent(node);

                // use information about whole path to calculate denominator
                rightNode.setProbability(1.0-(1.0/calculateDenominator(rightNode, numberOfPatterns)));

                g.addVertex(rightNode);
                if (!allNodesInTree.contains(rightNode)) allNodesInTree.add(rightNode);
                additionalNodes.add(rightNode);
                g.addEdge(subtreeRoot, rightNode);

            }

            levelsAndNodes.put(levelsCounter, additionalNodes);
            levelsCounter = levelsCounter + 1;

        }

        if (levelsAndNodes.keySet().size() > 8) {
            return null;
        }

        JGraphXAdapter<Vertex, DefaultEdge> graphAdapter = new JGraphXAdapter<>(g);
        graphAdapter.getCellToEdgeMap().forEach((edge, cell) -> {
            edge.setValue(null);
        });
        graphAdapter.getModel().beginUpdate();
        try {
            graphAdapter.clearSelection();
            graphAdapter.selectAll();
            Object[] cells = graphAdapter.getSelectionCells();

            for (Object c : cells) {
                mxCell cell = (mxCell) c;
                mxGeometry geometry = cell.getGeometry();

                if (cell.isVertex()) {
                    geometry.setWidth(10);
                    geometry.setHeight(10);
                }
            }
        } finally {
            graphAdapter.getModel().endUpdate();
        }
        mxStylesheet stylesheet = graphAdapter.getStylesheet();
        Hashtable<String, Object> style = new Hashtable<>();
        mxConstants.DEFAULT_FONTSIZE = 5;
        stylesheet.putCellStyle("styled", style);

        mxIGraphLayout layout = new mxCompactTreeLayout(graphAdapter, true);
        layout.execute(graphAdapter.getDefaultParent());
        Document htmlDocumentFromJGraphT = mxCellRenderer.createHtmlDocument(graphAdapter, null, 2, java.awt.Color.WHITE, null);
        WebView html = loadStochasticTreeIntoWebView(htmlDocumentFromJGraphT);

        return html;
    }

    /**
     * Constructs the stochastic tree visualization if less than 8 patterns were provided in the kick-off pattern sequence
     * in the text area after click on button 'Insert Kick-off pattern sequence'. If more than 8 patterns are provided
     * in this kick-off pattern sequence, then stochastic tree visualization is not constructed and only internal
     * representation of this tree is created.
     * */
    WebView visualizeStochasticTreeUsingListOfPatternAbbreviations(List<String> patternAbbreviations, int numberOfPatterns) throws IOException {
        if (!evaluateIfVisualizationShouldBeConstructed(patternAbbreviations, numberOfPatterns)) return null;

        // less than 8 patterns, we can visualize the tree
        DefaultDirectedGraph<Vertex, DefaultEdge> g = new DefaultDirectedGraph<>(DefaultEdge.class);
        int levelsCounter = 0;
        allNodesInTree.clear();
        levelsAndNodes.clear();

        for (int i=0; i < patternAbbreviations.size(); i++) {
            if (levelsCounter == 0) {

                Vertex sourceNode = new Vertex(" root ");
                sourceNode.setProbability(0);

                Vertex firstRightChild = new Vertex(patternAbbreviations.get(i));
                firstRightChild.setProbability(1.0/numberOfPatterns);

                Vertex firstLeftChild = new Vertex("¬" + patternAbbreviations.get(i));
                firstLeftChild.setProbability(1.0-(1.0/numberOfPatterns));

                List nodes = new ArrayList<Vertex>();
                nodes.add(firstRightChild);
                nodes.add(firstLeftChild);

                levelsAndNodes.put(levelsCounter, nodes);

                g.addVertex(sourceNode);
                g.addVertex(firstRightChild);
                g.addVertex(firstLeftChild);

                g.addEdge(sourceNode, firstRightChild);
                g.addEdge(sourceNode, firstLeftChild);

                levelsCounter = levelsCounter + 1;
            } else {

                List<Vertex> additionalNodes = new ArrayList<>();

                List<Vertex> nodesOnPreviousLevel = levelsAndNodes.get(levelsCounter - 1);

                for (Vertex node : nodesOnPreviousLevel) {

                    Vertex subtreeRoot = node;
                    g.addVertex(subtreeRoot);

                   if (!allNodesInTree.contains(subtreeRoot)) allNodesInTree.add(subtreeRoot);

                    Vertex rightNode = new Vertex(patternAbbreviations.get(i));

                    for (Vertex parent : node.getParents()) {
                        rightNode.addParent(parent);
                    }
                    rightNode.addParent(node);

                    rightNode.setProbability(1.0/calculateDenominator(rightNode, numberOfPatterns));
                    g.addVertex(rightNode);

                    if (!allNodesInTree.contains(rightNode)) allNodesInTree.add(rightNode);

                    additionalNodes.add(rightNode);
                    g.addEdge(subtreeRoot, rightNode);


                    Vertex leftNode = new Vertex("¬" + patternAbbreviations.get(i));

                    for (Vertex parent : node.getParents()) {
                        leftNode.addParent(parent);

                    }
                    leftNode.addParent(node);

                    // use information about whole path to calculate denominator
                    leftNode.setProbability(1.0-(1.0/calculateDenominator(leftNode, numberOfPatterns)));
                    g.addVertex(leftNode);

                    if (!allNodesInTree.contains(leftNode)) allNodesInTree.add(leftNode);
                    additionalNodes.add(leftNode);
                    g.addEdge(subtreeRoot, leftNode);

                }

                levelsAndNodes.put(levelsCounter, additionalNodes);
                levelsCounter = levelsCounter + 1;

            }
        }

        // remove labels generated by JGraphT
        JGraphXAdapter<Vertex, DefaultEdge> graphAdapter = new JGraphXAdapter<>(g);
        graphAdapter.getCellToEdgeMap().forEach((edge, cell) -> {
            edge.setValue(null);
        });
        // change vertex sizes such that tree is constructed in reasonable time
        graphAdapter.getModel().beginUpdate();
        try {
            graphAdapter.clearSelection();
            graphAdapter.selectAll();
            Object[] cells = graphAdapter.getSelectionCells();

            for (Object c : cells) {
                mxCell cell = (mxCell) c;
                mxGeometry geometry = cell.getGeometry();

                if (cell.isVertex()) {
                    geometry.setWidth(10);
                    geometry.setHeight(10);
                }
            }
        } finally {
            graphAdapter.getModel().endUpdate();
        }

        // update font size such that text does not overflow boundary of the node
        mxStylesheet stylesheet = graphAdapter.getStylesheet();
        Hashtable<String, Object> style = new Hashtable<>();
        mxConstants.DEFAULT_FONTSIZE = 5;
        stylesheet.putCellStyle("styled", style);

        mxIGraphLayout layout = new mxCompactTreeLayout(graphAdapter, true);
        layout.execute(graphAdapter.getDefaultParent());
        Document htmlDocumentFromJGraphT = mxCellRenderer.createHtmlDocument(graphAdapter, null, 2, java.awt.Color.WHITE, null);
        // show stochastic tree as html page with its own scroll bar, so web view does not have to be wrapped in scroll pane
        WebView html = loadStochasticTreeIntoWebView(htmlDocumentFromJGraphT);
        return html;
    }

    /**
     * Decides if JGraphT should visualize the stochastic tree. Stochastic tree is going to be visualized
     * if it is going to be constructed for kick-off pattern sequence with less than 8 patterns.
     * */
    private boolean evaluateIfVisualizationShouldBeConstructed(List<String> patternAbbreviations, int numberOfPatterns) {
        if (patternAbbreviations.size() > 8) {
            buildStochasticTreeStructureButDoNotVisualizeIt(patternAbbreviations, numberOfPatterns);
            return false;
        } else {
            return true;
        }
    }

    /**
     * Creates internal representation of the stochastic tree but without visualization, such that application can
     * still extract expected pattern sequence candidate from it.
     * */
    private void buildStochasticTreeStructureButDoNotVisualizeIt(List<String> patternAbbreviations, int numberOfPatterns) {
        int levelsCounter = 0;
        allNodesInTree.clear();
        levelsAndNodes.clear();

        for (int i=0; i < patternAbbreviations.size(); i++) {
            if (levelsCounter == 0) {

                Vertex sourceNode = new Vertex(" root ");
                sourceNode.setProbability(0);

                Vertex insertedFirstNodeApplied = new Vertex(patternAbbreviations.get(i));
                insertedFirstNodeApplied.setProbability(1.0/numberOfPatterns);

                Vertex insertedFirstNodeNotApplied = new Vertex("¬" + patternAbbreviations.get(i));
                insertedFirstNodeNotApplied.setProbability(1.0-(1.0/numberOfPatterns));

                List nodes = new ArrayList<Vertex>();
                nodes.add(insertedFirstNodeApplied);
                nodes.add(insertedFirstNodeNotApplied);

                levelsAndNodes.put(levelsCounter, nodes);

                levelsCounter = levelsCounter + 1;
            } else {

                List<Vertex> additionalNodes = new ArrayList<>();
                List<Vertex> nodesOnPreviousLevel = levelsAndNodes.get(levelsCounter - 1);

                for (Vertex node : nodesOnPreviousLevel) {

                    Vertex subtreeRoot = node;
                    if (!allNodesInTree.contains(subtreeRoot)) allNodesInTree.add(subtreeRoot);

                    Vertex leftNode = new Vertex(patternAbbreviations.get(i));
                    // store information about whole path
                    // this should register all parents of parent of left node
                    for (Vertex parent : node.getParents()) {
                        leftNode.addParent(parent);
                    }
                    leftNode.addParent(node);

                    // use information about whole path to calculate denominator
                    leftNode.setProbability(1.0/calculateDenominator(leftNode, numberOfPatterns));

                    if (!allNodesInTree.contains(leftNode)) allNodesInTree.add(leftNode);
                    additionalNodes.add(leftNode);

                    Vertex rightNode = new Vertex("¬" + patternAbbreviations.get(i));
                    // store information about whole path
                    for (Vertex parent : node.getParents()) {
                        rightNode.addParent(parent);

                    }
                    rightNode.addParent(node);

                    // use information about whole path to calculate denominator
                    rightNode.setProbability(1.0-(1.0/calculateDenominator(rightNode, numberOfPatterns)));

                    if (!allNodesInTree.contains(rightNode)) allNodesInTree.add(rightNode);
                    additionalNodes.add(rightNode);

                }

                levelsAndNodes.put(levelsCounter, additionalNodes);
                levelsCounter = levelsCounter + 1;

            }

        }
    }

    /**
     * Loads stochastic tree as HTML page into browser that has its own scrollbar,
     * thus web view does not have to be wrapped by scroll pane.
     * */
    private WebView loadStochasticTreeIntoWebView(Document htmlDocumentFromJGraphT) {
        WebView browser = new WebView();
        try {
            DOMSource source = new DOMSource(htmlDocumentFromJGraphT);
            File stochasticTreeInHTMLFile = new File("src/test/resources/stochastic-tree.html");
            FileWriter writer = new FileWriter(stochasticTreeInHTMLFile);
            StreamResult result = new StreamResult(writer);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.transform(source, result);
            WebEngine webEngine = browser.getEngine();
            String pathToStochasticTreeFile = stochasticTreeInHTMLFile.toURI().toString();
            webEngine.load(pathToStochasticTreeFile);
        } catch (Exception ex) {
            ex.printStackTrace();
            Alert missingPatternNameError = new Alert(Alert.AlertType.ERROR);
            missingPatternNameError.setContentText("Stochastic tree could not be loaded into the presentation view");
            missingPatternNameError.show();
        }
        return browser;
    }

    public List<Vertex> getAllNodesInTree() {
        return allNodesInTree;
    }

    Map<Integer, List<Vertex>> getLevelsAndNodes() {
        return levelsAndNodes;
    }

    int getNumberOfLevels() {
        return levelsAndNodes.keySet().size();
    }

    /**
     * Calculates denominator for calculating probability of applying node in the
     * stochastic tree. Denominator for nodes in the first level of this tree is
     * equal to number of patterns user works with. Parents of each node on the
     * subsequent levels are checked and if parent node was applied, default value
     * of denominator is decreased by one.
     * */
    private double calculateDenominator(Vertex forNode, int numberOfPatterns) {
        double nodeDenumerator = Double.valueOf(numberOfPatterns);
        for (Vertex parent : forNode.getParents()) {
            if (!parent.getVertexName().contains("¬")) {
                nodeDenumerator = nodeDenumerator - 1.0;
            }
        }
        return nodeDenumerator;
    }
}
