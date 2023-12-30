import json
import sys

from gauss_elimination_tt import create_alphabet, create_dependency_relation, find_foata_normal_form, DiekertGraph


def save_fnf_to_json(fnf, filename):
    json_fnf_data = [[letter.print_to_json() for letter in fnf_class] for fnf_class in fnf]
    data = {"fnf_classes": json_fnf_data}

    with open(filename, "w") as file:
        json.dump(data, file, indent=2)


if __name__ == '__main__':
    # n = int(sys.argv[1])
    n = 3
    filename = f"fnfs/fnf_{n}.json"
    alphabet, word = create_alphabet(n)
    dependency_table = create_dependency_relation(alphabet)
    diekert_graph = DiekertGraph(alphabet, dependency_table)
    fnf, colors = find_foata_normal_form(alphabet, diekert_graph.full_graph_list)
    save_fnf_to_json(fnf, filename)
