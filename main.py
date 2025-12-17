import os.path

def prompt_filename():
    """
    Prompt the user for text file name until a valid file name is provided.

    Returns:
       path to valid text file
    """
    while not os.path.isfile(filename := input('give path to data file: ')):
        print(f'data file {filename} does not exist')

    return filename

class Person:
    def __init__(self, lastname, firstname, address):
        self.lastname = lastname
        self.firstname = firstname
        self.address = address

    def __str__(self):
        return f'{self.lastname} {self.firstname} {self.address}'

    def is_namesake(self, person):
        """
        Tests whether this person is a namesake of another.

        Args:
            person: second person to be checked against

        Returns:
            True if the two are namesakes, False otherwise.
        """
        return self.firstname == person.firstname

def read_persons(filename):
    """
    Read persons from given text file.

    Args:
        filename: path to text file

    Returns:
        list of objects of type Person
    """
    persons = []
    with open(filename) as f:
        for line in f:
            words = line.split()
            lastname = words [0]
            firstname = words [1]
            # address is everything from 3rd word; these are joined into single string
            address = str.join(' ', words [2:]) 
            persons.append(Person(lastname, firstname, address))

    return persons

def print_namesakes(persons):
    """
    Print namesakes on consecutive lines.

    Args:
        list of objects of type Person
    """
    num_persons = len(persons)
    # traverse through persons
    for (index, person) in enumerate(persons):
        if person != None: # if this person has not been printed yet
            print(person)
            # traverse the rest of the list, looking for namesakes
            for j in range(index + 1, num_persons):
                candidate = persons [j]
                if candidate != None and person.is_namesake(candidate):
                    print(candidate)
                    persons [j] = None # mark this person as printed

filename = prompt_filename()
persons = read_persons(filename)
print_namesakes(persons)
