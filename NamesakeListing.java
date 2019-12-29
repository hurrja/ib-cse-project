import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

class NamesakeListing
{
  public static void main (String[] args)
  {
    final int MAX_NUM_PERSONS = 6;

    // get name of data file
    String filename;
    if (args.length >= 1)
      filename = args [0];
    else
      filename = queryFilename ();

    // read persons into an array
    Person[] persons = new Person [MAX_NUM_PERSONS];
    int numPersons = readPersons (filename, persons);

    // list namesakes
    printNamesakes (persons, numPersons);
  }

  // query the user for the name of data file; return value is the
  // name of the file
  static String queryFilename ()
  {
    String filename;
    try (Scanner scanner = new Scanner (System.in))
    {
      System.out.print ("give data file name: ");
      filename = scanner.next ();
    }
    catch (Exception e)
    {
      System.out.println ("unable to read data file name, exception: " + e);
      filename = "";
      System.exit (-1);
    }

    return filename;
  }

  // read persons from data file with given file name into the
  // supplied array; return value is the number of persons in the data
  // file
  static int readPersons (String filename, Person[] persons)
  {
    int numPersons = 0;

    // open file and create a scanner for it
    try (FileReader dataFileReader = new FileReader (new File (filename));
         Scanner scanner = new Scanner (dataFileReader))
    {
      try
      {
        // read while there are lines in the file
        while (scanner.hasNextLine ())
        {
          if (numPersons > persons.length)
          {
            System.out.println ("exceeded maximum number of persons " + persons.length);
            System.exit (-1);
          }

          // each line has last name, first name, address
          String lastname = scanner.next ();
          String firstname = scanner.next ();
          String address = scanner.nextLine (); // address is the rest of the line

          persons [numPersons] = new Person (lastname, firstname, address);
          numPersons = numPersons + 1;
        }
      }
      catch (Exception e)
      {
        System.out.println ("unable to read person data, exception " + e);
        System.exit (-1);
      }
    }
    catch (Exception e)
    {
      System.out.println ("unable to open file " + filename + " for reading, exception : " + e);
      System.exit (-1);
    }

    return numPersons;
  }

  // print the persons as groups of namesakes
  static void printNamesakes (Person[] persons, int numPersons)
  {
    // go through the persons
    for (int personInd = 0; personInd < numPersons; personInd = personInd + 1)
    {
      Person person = persons [personInd];

      // if the person has not been printed yet, print the person and
      // the possible namesakes
      if (person != null)
      {
        System.out.println (person);

        // go through namesake candidates, starting from the next
        // person
        for (int candInd = personInd + 1; candInd < numPersons; candInd = candInd + 1)
        {
          Person candidate = persons [candInd];
          if (candidate != null && person.isNamesake (candidate))
          {
            System.out.println (candidate);
            persons [candInd] = null;
          }
        }
      }
    } 
  }
}


// class containing information of person and converting info to
// string
class Person
{
  public Person (String lastname, String firstname, String address)
  {
    this.lastname = lastname;
    this.firstname = firstname;
    this.address = address;
  }

  public boolean isNamesake (Person person)
  {
    return firstname.equals (person.firstname);
  }

  public String toString ()
  {
    // note that address always contains a space at its beginning, so
    // none needs to be added between firstname and address
    return lastname + " " + firstname + address;
  }

  public String firstname, lastname, address;
}
