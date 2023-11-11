from collections import defaultdict
import networkx as nx
import matplotlib.pyplot as plt


class DiekertGraph:
    def __init__(self, word, idependent_relations):
        self.edges = defaultdict(list)
        self.graph = nx.DiGraph()
        self.word = word
        self.n = len(word)
        self.i_relations = idependent_relations
        self.build_graph()

    def build_graph(self):
        """Builds the graph from the word and independent relations."""
        for idx, letter in enumerate(self.word[:-1]):
            edges = list(filter(lambda x: x[0] == letter, list(self.i_relations)))
            for jdx, second_letter in enumerate(self.word[idx + 1 :], idx + 1):
                if not any(second_letter == e for _, e in edges):
                    self.edges[idx].append(jdx)

        self.remove_edges(self.find_transitive_edges())
        for i in range(self.n):
            for j in self.edges[i]:
                self.graph.add_edge(i, j)

    def remove_edges(self, edges):
        """Removes edges from the graph."""
        for a, b in edges:
            self.edges[a].remove(b)

    def find_transitive_edges(self):
        """Finds transitive edges in the graph."""
        transitive_edges = set()
        for i in range(self.n):
            for j in range(self.n):
                for k in range(self.n):
                    if j in self.edges[i] and k in self.edges[j] and k in self.edges[i]:
                        transitive_edges.add((i, k))
        return transitive_edges

    def draw_graph(self, save_path=None):
        """Draws the graph."""
        pos = nx.spring_layout(self.graph, seed=42, k=0.5, iterations=50)
        labels = {i: letter for i, letter in enumerate(self.word)}
        nx.draw(
            self.graph,
            pos,
            with_labels=True,
            labels=labels,
            font_weight="bold",
            arrowsize=20,
            node_color="orchid",
        )

        if save_path:
            plt.savefig(save_path, format="png")

        plt.show()
