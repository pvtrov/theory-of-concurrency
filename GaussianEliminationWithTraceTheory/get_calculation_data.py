import json
import sys

from gauss_elimination_tt import create_alphabet, create_dependency_relation, find_foata_normal_form, DiekertGraph


def save_fnf_to_json(fnf, filename):
    json_fnf_data = [[letter.print_to_json() for letter in fnf_class] for fnf_class in fnf]
    data = {"fnf_classes": json_fnf_data}

    with open(filename, "w") as file:
        json.dump(data, file, indent=2)


def save_matrix_data_to_json(example, filename):
    with open(example, 'r') as file:
        lines = file.readlines()

    matrix = [list(map(float, line.split())) for line in lines]

    with open(filename, 'r') as json_file:
        data = json.load(json_file)

    data["matrix"] = matrix

    with open(filename, "w") as file:
        json.dump(data, file, indent=2)


n = int(sys.argv[1]) if sys.argv[1] else 3
filename = f"calculation_data/calculation_data_{n}.json"
example = f"examples/example_{n}"
alphabet, word = create_alphabet(n)
dependency_table = create_dependency_relation(alphabet)
diekert_graph = DiekertGraph(alphabet, dependency_table)
fnf, colors = find_foata_normal_form(alphabet, diekert_graph.full_graph_list)
save_fnf_to_json(fnf, filename)
save_matrix_data_to_json(example, filename)