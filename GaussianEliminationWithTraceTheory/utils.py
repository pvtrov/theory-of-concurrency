from collections import defaultdict
from queue import Queue
import graphviz


class LetterException(Exception):
    pass


class TraceTheoryException(Exception):
    pass


class DiekertGraph:
    def __init__(self, alphabet, dependencies):
        self.edges = defaultdict(list)
        self.graph = graphviz.Digraph()
        self.alphabet = alphabet
        self.n = len(alphabet)
        self.d_relations = dependencies
        self.full_graph_list = self.build_graph()
        self.reduced_graph = self.remove_transitive_edges()

    def build_graph(self):
        """Builds full graph from alphabet and dependency relations."""
        dependency_dict = defaultdict(list)
        for elem in self.d_relations:
            dependency_dict[elem[1]].append(elem[0])

        graph = []
        for letter in self.alphabet:
            graph.append(set(map(self.alphabet.index, dependency_dict[letter])))

        return graph

    def does_path_exist(self, start, end):
        n = len(self.full_graph_list)
        visited = [False for _ in range(n)]
        q = Queue()
        visited[start] = True
        q.put(start)
        while not q.empty():
            tmp = q.get()
            for i in self.full_graph_list[tmp]:
                if i == end:
                    return True
                if not visited[i]:
                    visited[i] = True
                    q.put(i)
        return False

    def remove_transitive_edges(self):
        """Removes transitive edges from the graph."""
        for i in range(len(self.full_graph_list)):
            curr = self.full_graph_list[i].copy()
            for v in curr:
                self.full_graph_list[i].remove(v)
                if not self.does_path_exist(i, v):
                    self.full_graph_list[i].add(v)

        return self.full_graph_list

    def draw_graph(self, fnf_classes, save_path=None):
        """Draws the graph."""
        colors = ['deeppink', 'purple', 'pink', 'springgreen', 'yellow', 'lightcoral', 'violet', 'gold', 'deepskyblue']

        for i, elem in enumerate(self.alphabet):
            color = colors[fnf_classes[i] % len(colors)]
            self.graph.node(str(i), elem.idx_name,  style='filled', fillcolor=color)

        for i, elem in enumerate(self.alphabet):
            for j in self.reduced_graph[i]:
                self.graph.edge(str(i), str(j))

        self.graph.render(save_path, format="pdf", view=True)


class Letter:
    def print_letter(self) -> str:
        if self.name == "A":
            return f"{self.name}_{self.i}{self.k}"
        elif self.name == "B" or self.name == "C":
            return f"{self.name}_{self.i}{self.j}{self.k}"
        else:
            raise LetterException("Invalid letter")

    def print_to_json(self):
        if self.name == "A":
            return {"name": self.name, "i": self.i, "j": None, "k": self.k}
        elif self.name == "B" or self.name == "C":
            return {"name": self.name, "i": self.i, "j": self.j, "k": self.k}
        else:
            raise LetterException("Invalid letter")


class A(Letter):
    def __init__(self, i, k):
        self.name = "A"
        self.i = i
        self.k = k
        self.neighbours = []
        self.idx_name = self.print_letter()


class B(Letter):
    def __init__(self, p, r, s):
        self.name = "B"
        self.i = p
        self.j = r
        self.k = s
        self.idx_name = self.print_letter()


class C(Letter):
    def __init__(self, p, r, s):
        self.name = "C"
        self.i = p
        self.j = r
        self.k = s
        self.idx_name = self.print_letter()


