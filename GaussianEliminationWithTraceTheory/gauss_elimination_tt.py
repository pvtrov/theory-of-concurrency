import sys
from queue import Queue
from utils import A, B, C, DiekertGraph


def create_alphabet(n):
    word = []
    alphabet = []

    for i in range(1, n+1):
        for k in range(i+1, n+1):
            new_A = A(i, k)
            alphabet.append(new_A)
            word.append(new_A)
            for r in range(i, n+2):
                new_B = B(p=i, r=r, s=k)
                new_C = C(p=i, r=r, s=k)
                alphabet.append(new_B)
                alphabet.append(new_C)
                word.append(new_B)
                word.append(new_C)

    return alphabet, word


def create_dependency_relation(alphabet):
    dependency_table = set()
    for first_node in alphabet:
        for second_node in alphabet:
            if first_node.name == "A":
                if second_node.name == "C":
                    if (first_node.i-1 == second_node.i and first_node.i == second_node.j
                            and first_node.i == second_node.k):
                        dependency_table.add((first_node, second_node))
                    if (first_node.i-1 == second_node.i and first_node.i == second_node.j
                            and first_node.k == second_node.k):
                        dependency_table.add((first_node, second_node))

            if first_node.name == "B":
                if second_node.name == "A":
                    if first_node.i == second_node.i and first_node.k == second_node.k:
                        dependency_table.add((first_node, second_node))
                if second_node.name == "C":
                    if (first_node.i-1 == second_node.i and first_node.j == second_node.j
                            and first_node.k-1 == second_node.k):
                        dependency_table.add((first_node, second_node))

            if first_node.name == "C":
                if second_node.name == "B":
                    if (first_node.j == second_node.j and first_node.k == second_node.k
                            and first_node.i == second_node.i):
                        dependency_table.add((first_node, second_node))

                if second_node.name == "C":
                    if (first_node.i-1 == second_node.i and first_node.j == second_node.j
                            and first_node.k == second_node.k):
                        dependency_table.add((first_node, second_node))

    return dependency_table


def find_foata_normal_form(alphabet, graph):
    start_points = set(range(len(graph)))
    for v_edges in graph:
        start_points -= v_edges

    classes = [-1 for _ in range(len(graph))]
    q = Queue()
    for point in start_points:
        classes[point] = 0
        q.put(point)
    while not q.empty():
        tmp = q.get()
        for i in graph[tmp]:
            classes[i] = classes[tmp] + 1
            q.put(i)

    fnf_classes = [[] for _ in range(max(classes) + 1)]
    for i, i_class in enumerate(classes):
        fnf_classes[i_class].append(alphabet[i])

    return fnf_classes, classes


def print_results_to_file(alphabet, word, dependency_table, fnf):
    with open("theory_result.txt", "a") as file:
        file.write("Alphabet = {" + ", ".join(sorted(letter.idx_name for letter in alphabet)) + "}\n")
        file.write("Gauss elimination algorithm (t) = {" + ", ".join(letter.idx_name for letter in word) + "}\n")
        file.write("D = {" + ", ".join(f"({a.idx_name}, {b.idx_name})" for a, b in dependency_table) + "}\n")
        file.write("FNF = (" + ")(".join(", ".join(letter.idx_name for letter in sublista) for sublista in fnf) + ")\n")


def main(n):
    """
    Execute the main program logic.

    :param n: Size of matrix to multiplication.
    :return:

    Function performs the following steps:
    1. Creates alphabet based on given size n.
    2. Creates Dependency Relation: Builds a dependency table based on the specific relationships between letters.
    3. Constructs a graph based on the dependency table.
    5. Finds the Foata Normal Form of the given word using BFS algorithm.
    6. Writes alphabet, dependency table and Foata Normal Form to a result file.
    7. Renders Diekert graph.

    Note: The results are written to a file, and the Diekert graph is displayed as part of the program execution.
    """
    alphabet, word = create_alphabet(n)
    dependency_table = create_dependency_relation(alphabet)
    diekert_graph = DiekertGraph(alphabet, dependency_table)
    fnf, colors = find_foata_normal_form(alphabet, diekert_graph.full_graph_list)
    print_results_to_file(alphabet, word, dependency_table, fnf)
    diekert_graph.draw_graph(fnf_classes=colors, save_path=f"graphs/diekert_graph_{n}.png")


if __name__ == "__main__":
    with open("theory_result.txt", "w") as file:
        file.write(f".")

    n = int(sys.argv[1]) if sys.argv[1] else 3
    with open("theory_result.txt", "a") as file:
        file.write(f"\n~~~~~ For matrix with size {n} ~~~~~\n")
    main(n)
