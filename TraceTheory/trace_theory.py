from diekert_graph import DiekertGraph


class TraceTheoryException(Exception):
    pass


def match_symbols(alphabet, transactions):
    """
    Match variables in transaction to action letters.
    For example, in the transaction (a) x := x + y, the variable x is matched to the action letter a.
    """
    matched_symbols = {}
    for transaction in transactions:
        transaction = transaction.replace(" ", "")
        if transaction[1] in alphabet:
            value = transaction[1]
            key = transaction[3]

            if key not in matched_symbols:
                matched_symbols[key] = []

            matched_symbols[key].append(value)
        else:
            raise TraceTheoryException("Invalid input")

    return matched_symbols


def create_dependency_relation(matches_symbols, alphabet, transactions):
    """
    Creates dependency relation from matched symbols.
    For example, in the transactions (a) x := x + y, the dependency relation is (a, b),
    because action b is matched with y letter based on: (b) y := y + 2z.
    """
    dependency_table = set()
    for transaction in transactions:
        transaction = transaction.replace(" ", "")
        if transaction[1] in alphabet:
            first_node = transaction[1]
            start = transaction.index("=")
            if start == -1:
                raise TraceTheoryException("Invalid input")
            for i in range(start + 1, len(transaction)):
                if transaction[i].isalpha():
                    nodes = matches_symbols[transaction[i]]
                    for second_node in nodes:
                        dependency_table.add((first_node, second_node))
                        dependency_table.add((second_node, first_node))
        else:
            raise TraceTheoryException("Invalid input")

    return dependency_table


def create_independency_relation(dependency_table):
    """Creates independency relation from dependency relation."""
    complement_dict = {letter: set() for rel in dependency_table for letter in rel}

    for relation in dependency_table:
        for letter in complement_dict:
            if letter != relation[1] and (letter, relation[1]) not in dependency_table:
                complement_dict[letter].add(relation[1])

    return [(key, value) for key, values in complement_dict.items() for value in values]


def find_foata_normal_form(alphabet, graph, word):
    """Finds the Foata Normal Form of a given word using the provided graph and mineral mining algorithm."""
    stacks = {letter: [] for letter in alphabet}

    for letter in word[::-1]:
        stacks[letter].append(letter)
        neighbours = graph[letter]
        for neib in neighbours:
            if neib != letter:
                stacks[neib].append("*")

    foata = []
    while any(stacks.values()):
        collected = set()

        for letter, stack in stacks.items():
            if stack and stack[-1].isalpha():
                collected.add(stack.pop())

        if collected:
            foata.append(list(collected))
        else:
            for stack in stacks.values():
                if stack:
                    stack.pop()

    return foata


def create_graph(dependency_table):
    """Creates graph from dependency table."""
    graph = {}
    for first_node, second_node in dependency_table:
        if first_node not in graph:
            graph[first_node] = []
        graph[first_node].append(second_node)

    return graph


def write_result_file(dependency_table, independency_table, foata):
    """Writes the results to a text file named 'result.txt'."""
    with open("result.txt", "a") as file:
        file.write(
            "D = {" + ", ".join(f"({a}, {b})" for a, b in dependency_table) + "}\n"
        )
        file.write(
            "I = {" + ", ".join(f"({a}, {b})" for a, b in independency_table) + "}\n"
        )
        file.write(
            "FNF = ("
            + ")(".join("".join(sorted(sublista)) for sublista in foata)
            + ")\n"
        )


def main(alphabet, word, transactions, exercise=1):
    """
    Executes the main program logic, involving the analysis of a given word using Diekert graph theory.

    :param alphabet: A set of letters representing actions in the transactions.
    :param word: The word to be tested against the Diekert graph.
    :param transactions: A list of transactions on variables.
    :return: None

    This function performs the following steps:
    1. Match Symbols: Identifies and extracts symbols from the transactions that correspond to the provided alphabet.
    2. Create Dependency Relation: Builds a dependency table based on the matched symbols and transactions.
    3. Create Independency Relation: Derives an independency table from the dependency table.
    4. Create Graph: Constructs a graph based on the dependency table.
    5. Find Foata Normal Form: Determines the Foata Normal Form of the given word using the constructed graph.
    6. Create Diekert Graph: Initializes a DiekertGraph instance with the word and independency table.
    7. Write Result File: Outputs the dependency table, independency table, and Foata Normal Form to a result file.
    8. Draw Diekert Graph: Visualizes the Diekert graph.

    Note: The results are written to a file, and the Diekert graph is displayed as part of the program execution.
    """
    matched_symbols = match_symbols(alphabet, transactions)
    dependency_table = create_dependency_relation(
        matched_symbols, alphabet, transactions
    )
    independency_table = create_independency_relation(dependency_table)
    graph = create_graph(dependency_table)
    fnf = find_foata_normal_form(alphabet, graph, word)
    diekert_graph = DiekertGraph(word, independency_table)
    write_result_file(dependency_table, independency_table, fnf)
    diekert_graph.draw_graph(save_path=f"diekert_graph{exercise}.png")


if __name__ == "__main__":
    A = {"a", "b", "c", "d"}
    w = "baadcb"
    transactions = [
        "(a) x := x + y",
        "(b) y := y + 2z",
        "(c) x := 3x + z",
        "(d) z := y − z",
    ]

    A2 = {"a", "b", "c", "d", "e", "f"}
    w2 = "acdcfbbe"
    transactions2 = [
        "(a) x := x + 1",
        "(b) y := y + 2z",
        "(c) x := 3x + z",
        "(d) w := w + v",
        "(e) z := y - z",
        "(f) v := x + v",
    ]

    A3 = {"a", "b", "c", "d", "e", "f"}
    w3 = "acdcfbbe"
    transactions3 = [
        "(a) x := y + z",
        "(b) y := y + w + x",
        "(c) x := x + y + v",
        "(d) w := v + z",
        "(e) v := x + v + w",
        "(f) z := y + z + v",
    ]

    with open("result.txt", "w") as file:
        file.write("~~~~~ Exercise 1 ~~~~~\n")
    main(alphabet=A, transactions=transactions, word=w, exercise=1)

    with open("result.txt", "a") as file:
        file.write("\n~~~~~ Exercise 2 ~~~~~\n")
    main(alphabet=A2, transactions=transactions2, word=w2, exercise=2)

    with open("result.txt", "a") as file:
        file.write("\n~~~~~ Exercise 3 ~~~~~\n")
    main(alphabet=A3, transactions=transactions3, word=w3, exercise=3)