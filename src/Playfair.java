import java.awt.Point;
import java.util.Scanner;

public class Playfair
{
    //Initializes the length of the digraph array
    private int length = 0;

    //Creates a matrix for Playfair cipher where we will insert the key and other characters in the alphabet
    private String[][] table;

    public static void main(String[] args)
    {
        Playfair pf = new Playfair();
    }

    //main run of the program, Playfair method
    //constructor of the class
    private Playfair()
    {
        Scanner sc = new Scanner(System.in);
        int choice;

        //Prompts user for the keyword to use & creates table
        System.out.print("Enter the key for playfair cipher: ");
        String key = parseString(sc);
        while(key.equals(""))
            key = parseString(sc);
        table = this.cipherTable(key);

        //Shows the playfair table with the keyword and other characters
        this.keyTable(table);


        //A do loop that takes in the users input choice until the user chooses to exit the program
        do {

            System.out.print("""
                [1]ENCIPHER
                [2]DECIPHER
                [3]EXIT
                Choose from the category:ㅤ""");

            //Accepts the user input as string and convert it to integer to prevent unnecessary bugs
            choice = Integer.parseInt(sc.nextLine());

            if(choice==1||choice==2) {

                //Prompts user for the text to be encoded or decoded
                System.out.print("Enter text: ");

                //Creates a variable that houses the text parsed ready for encoding or decoding
                String input = parseString(sc);
                while (input.equals(""))
                    input = parseString(sc);

                //switch case
                //if the user inputs 1 then the text will be enciphered and shown the results
                //if the user inputs 2 then the text will be deciphered and shown the results
                switch (choice) {
                    case 1 -> {
                        String enciphered = cipher(input);
                        System.out.println("Enciphered text: " + enciphered +"\n");
                    }
                    case 2 -> {
                        String deciphered = decode(input);
                        System.out.println("Deciphered text: " + deciphered +"\n");
                    }
                }
            }

        }while(choice!=3);
    }


    //Parses an input string to remove numbers, punctuation,
    //replaces any J's with I's and makes string all caps
    private String parseString(Scanner sc)
    {
        //Creates a String variable to house the text
        String parse = sc.nextLine();

        //Converts all the letters in the text to upper case
        parse = parse.toUpperCase();

        //The string is set to substitute all characters that are not capital [A-Z] by a blank.
        parse = parse.replaceAll("[^A-Z]", "");

        //Replace the letter J by I
        parse = parse.replace("J", "I");

        return parse;
    }

    //Creates the cipher table based on some input string (already parsed)
    private String[][] cipherTable(String key)
    {
        //Creates a matrix of 5*5
        String[][] playfairTable = new String[5][5];
        String keyString = key + "ABCDEFGHIKLMNOPQRSTUVWXYZ";

        //Fill string array with empty string
        for(int i = 0; i < 5; i++)
            for(int j = 0; j < 5; j++)
                playfairTable[i][j] = "";

        //Fill string array with the key and alphabet letters
        for(int k = 0; k < keyString.length(); k++)
        {
            //Create 2 booleans for
            boolean repeat = false;
            boolean used = false;
            for(int i = 0; i < 5; i++)
            {
                for(int j = 0; j < 5; j++)
                {
                    //Checks if the string at index i and j is equal to the character in the keyString at k
                    if(playfairTable[i][j].equals("" + keyString.charAt(k)))
                    {
                        //Sets the boolean repeat to true
                        repeat = true;
                    }
                    //Checks to see if the string at index i and j is blank and if repeat and used is set to false
                    else if(playfairTable[i][j].equals("") && !repeat && !used)
                    {
                        //Sets the string at index i and j to the char at k in string keyString
                        playfairTable[i][j] = "" + keyString.charAt(k);
                        used = true;
                    }
                }
            }
        }

        //Returns the String array playfairTable
        return playfairTable;
    }

    //Cipher: takes input text (all upper-case), encodes it, and returns the output
    private String cipher(String in)
    {
        //Calculates the array length for the digraphs
        length = in.length() / 2 + in.length() % 2;

        //Insert x between double-letter digraphs & redefines "length"
        for(int i = 0; i < (length - 1); i++)
        {
            //Checks if the character at (2 * i) is the same as (2 * i + 1) in the text
            //It is multiplied by 2 because digraphs are 2 characters together
            if(in.charAt(2 * i) == in.charAt(2 * i + 1))
            {
                //Inserts X at the second character of the digraph
                in = new StringBuffer(in).insert(2 * i + 1, 'X').toString();
                //Calculates the length again
                length = in.length() / 2 + in.length() % 2;
            }
        }

        //Creates an array of digraphs with the length
        String[] digraph = new String[length];

        //Loops over the text
        for(int j = 0; j < length ; j++)
        {
            //Checks if the text is of even length or not
            if(j == (length - 1) && in.length() / 2 == (length - 1))
                //If not even length it appends X at the end of the text
                in = in + "X";

            //Adds the digraph at index j
            digraph[j] = in.charAt(2 * j) +""+ in.charAt(2 * j + 1);
        }

        //------Encodes the digraphs and returns the output

        //Creates a stringbuilder
        StringBuilder out = new StringBuilder();
        //Create a string array for storing the encoded text
        String[] encDigraphs;
        //Stores the value returned by the encodeDigraph method
        encDigraphs = encodeDigraph(digraph);

        //Loops till it hits length
        for(int k = 0; k < length; k++)
            //Appends each digraph to the string
            out.append(encDigraphs[k]);

        //returns the encoded string
        return out.toString();
    }

    //---------------encryption logic-----------------
    //encodes the digraph input with the cipher's specifications
    private String[] encodeDigraph(String[] di)
    {
        //Creates a string array that will house the encipher
        String[] encipher = new String[length];

        //Loops through until it hits length
        for(int i = 0; i < length; i++)
        {
            //Creates 2 char variables that sets its value to the character at position 0 and 1 respectively
            char a = di[i].charAt(0);
            char b = di[i].charAt(1);

            //Creates 4 int variables that gets the points from the table based on the 2 char variables
            int r1 = (int) getPoint(a).getX();
            int r2 = (int) getPoint(b).getX();
            int c1 = (int) getPoint(a).getY();
            int c2 = (int) getPoint(b).getY();

            //executes if the letters of digraph appear in the same row
            //in such case shift columns to right
            if(r1 == r2)
            {
                //Shifts the columns right by 1
                c1 = (c1 + 1) % 5;
                c2 = (c2 + 1) % 5;
            }

            //executes if the letters of digraph appear in the same column
            //in such case shift rows down
            else if(c1 == c2)
            {
                //Shifts the rows down by 1
                r1 = (r1 + 1) % 5;
                r2 = (r2 + 1) % 5;
            }

            //executes if the letters of digraph appear in the different row and different column
            // in such case swap the first column with the second column
            else
            {
                //Creates a temporary int holder
                int temp = c1;
                c1 = c2;
                c2 = temp;
            }

            //performs the table look-up and puts those values into the encoded array
            encipher[i] = table[r1][c1] + "" + table[r2][c2];
        }

        //Returns the string array encipher
        return encipher;
    }

    //-----------------------decryption logic---------------------
    // decodes the text (opp. of encoding process)
    private String decode(String out)
    {
        //Create a string
        StringBuilder decoded = new StringBuilder();

        //Loops through until it hits length
        for(int i = 0; i < out.length() / 2; i++)
        {
            //Creates 2 char variables that sets its value to the character at position (2*i) and (2*i+1) respectively
            char a = out.charAt(2*i);
            char b = out.charAt(2*i+1);

            //Creates 4 int variables that gets the points from the table based on the 2 char variables
            int r1 = (int) getPoint(a).getX();
            int r2 = (int) getPoint(b).getX();
            int c1 = (int) getPoint(a).getY();
            int c2 = (int) getPoint(b).getY();

            //executes if the letters of digraph appear in the same row
            //in such case shift columns to left
            if(r1 == r2)
            {
                c1 = (c1 + 4) % 5;
                c2 = (c2 + 4) % 5;
            }

            //executes if the letters of digraph appear in the same column
            //in such case shift rows up
            else if(c1 == c2)
            {
                r1 = (r1 + 4) % 5;
                r2 = (r2 + 4) % 5;
            }

            //executes if the letters of digraph appear in the different row and different column
            // in such case swap the first column with the second column
            else
            {

                //Creates a temporary int holder
                int temp = c1;
                c1 = c2;
                c2 = temp;
            }
            decoded.append(table[r1][c1]).append(table[r2][c2]);
        }

        //returns the decoded message
        return decoded.toString();
    }

    // returns a point containing the row and column of the letter
    private Point getPoint(char c)
    {
        //Create a variable point
        Point pt = new Point(0,0);

        //Searches the table for the char
        for(int i = 0; i < 5; i++)
            for(int j = 0; j < 5; j++)
                if(c == table[i][j].charAt(0))
                    pt = new Point(i,j);

        //returns the point
        return pt;
    }

    //Function prints the key-table in matrix form for playfair cipher
    private void keyTable(String[][] printTable)
    {
        System.out.println("Playfair Cipher Key Matrix: ");
        System.out.println();

        //loop iterates for rows
        for(int i = 0; i < 5; i++)
        {

            //loop iterates for column
            for(int j = 0; j < 5; j++)
            {

                //prints the key-table in matrix form
                System.out.print(printTable[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
