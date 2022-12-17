import java.awt.Point;
import java.util.Scanner;

public class Playfair
{
    //length of digraph array
    private int length = 0;

    //creates a matrix for Playfair cipher
    private String[][] table;

    //main() method to test Playfair method
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

        //prompts user for the keyword to use for encoding & creates tables
        System.out.print("Enter the key for playfair cipher: ");
        String key = parseString(sc);
        while(key.equals(""))
            key = parseString(sc);
        table = this.cipherTable(key);

        //shows the playfair table
        this.keyTable(table);

        do {

            System.out.print("""
                [1]ENCIPHER
                [2]DECIPHER
                [3]EXIT
                Choose from the category:ㅤ""");
            //accepts the user input as string and convert it to integer to prevent unnecessary bugs
            choice = Integer.parseInt(sc.nextLine());

            if(choice==1||choice==2) {

                //prompts user for message to be encoded or decoded
                System.out.print("Enter text: ");

                //System.out.println("using the previously given keyword");
                String input = parseString(sc);
                while (input.equals(""))
                    input = parseString(sc);

                //switch case
                //if the user inputs 1 then the text will be enciphered
                //if the user inputs 2 then the text will be deciphered
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


    //parses an input string to remove numbers, punctuation,
    //replaces any J's with I's and makes string all caps
    private String parseString(Scanner sc)
    {
        String parse = sc.nextLine();

        //converts all the letters in upper case
        parse = parse.toUpperCase();

        //the string to be substituted by space for each match (A to Z)
        parse = parse.replaceAll("[^A-Z]", "");

        //replace the letter J by I
        parse = parse.replace("J", "I");

        return parse;
    }

    //creates the cipher table based on some input string (already parsed)
    private String[][] cipherTable(String key)
    {
        //creates a matrix of 5*5
        String[][] playfairTable = new String[5][5];
        String keyString = key + "ABCDEFGHIKLMNOPQRSTUVWXYZ";

        //fill string array with empty string
        for(int i = 0; i < 5; i++)
            for(int j = 0; j < 5; j++)
                playfairTable[i][j] = "";
        for(int k = 0; k < keyString.length(); k++)
        {
            boolean repeat = false;
            boolean used = false;
            for(int i = 0; i < 5; i++)
            {
                for(int j = 0; j < 5; j++)
                {
                    if(playfairTable[i][j].equals("" + keyString.charAt(k)))
                    {
                        repeat = true;
                    }
                    else if(playfairTable[i][j].equals("") && !repeat && !used)
                    {
                        playfairTable[i][j] = "" + keyString.charAt(k);
                        used = true;
                    }
                }
            }
        }
        return playfairTable;
    }

    //cipher: takes input (all upper-case), encodes it, and returns the output
    private String cipher(String in)
    {
        length = in.length() / 2 + in.length() % 2;
        //insert x between double-letter digraphs & redefines "length"

        for(int i = 0; i < (length - 1); i++)
        {
            if(in.charAt(2 * i) == in.charAt(2 * i + 1))
            {
                in = new StringBuffer(in).insert(2 * i + 1, 'X').toString();
                length = in.length() / 2 + in.length() % 2;
            }
        }

        //------------makes plaintext of even length--------------
        //creates an array of digraphs
        String[] digraph = new String[length];

        //loop iterates over the plaintext
        for(int j = 0; j < length ; j++)
        {

            //checks the plaintext is of even length or not
            if(j == (length - 1) && in.length() / 2 == (length - 1))
                //if not addends X at the end of the plaintext
                in = in + "X";

            digraph[j] = in.charAt(2 * j) +""+ in.charAt(2 * j + 1);
        }

        //encodes the digraphs and returns the output
        StringBuilder out = new StringBuilder();
        String[] encDigraphs;
        encDigraphs = encodeDigraph(digraph);
        for(int k = 0; k < length; k++)
            out.append(encDigraphs[k]);
        return out.toString();
    }

    //---------------encryption logic-----------------

    //encodes the digraph input with the cipher's specifications
    private String[] encodeDigraph(String[] di)
    {
        String[] encipher = new String[length];
        for(int i = 0; i < length; i++)
        {
            char a = di[i].charAt(0);
            char b = di[i].charAt(1);
            int r1 = (int) getPoint(a).getX();
            int r2 = (int) getPoint(b).getX();
            int c1 = (int) getPoint(a).getY();
            int c2 = (int) getPoint(b).getY();

            //executes if the letters of digraph appear in the same row
            //in such case shift columns to right
            if(r1 == r2)
            {
                c1 = (c1 + 1) % 5;
                c2 = (c2 + 1) % 5;
            }

            //executes if the letters of digraph appear in the same column
            //in such case shift rows down
            else if(c1 == c2)
            {
                r1 = (r1 + 1) % 5;
                r2 = (r2 + 1) % 5;
            }

            //executes if the letters of digraph appear in the different row and different column
            // in such case swap the first column with the second column
            else
            {
                int temp = c1;
                c1 = c2;
                c2 = temp;
            }

            //performs the table look-up and puts those values into the encoded array
            encipher[i] = table[r1][c1] + "" + table[r2][c2];
        }
        return encipher;
    }

    //-----------------------decryption logic---------------------
    // decodes the output given from the cipher and decode methods (opp. of encoding process)
    private String decode(String out)
    {
        StringBuilder decoded = new StringBuilder();
        for(int i = 0; i < out.length() / 2; i++)
        {
            char a = out.charAt(2*i);
            char b = out.charAt(2*i+1);
            int r1 = (int) getPoint(a).getX();
            int r2 = (int) getPoint(b).getX();
            int c1 = (int) getPoint(a).getY();
            int c2 = (int) getPoint(b).getY();
            if(r1 == r2)
            {
                c1 = (c1 + 4) % 5;
                c2 = (c2 + 4) % 5;
            }
            else if(c1 == c2)
            {
                r1 = (r1 + 4) % 5;
                r2 = (r2 + 4) % 5;
            }
            else
            {

                //swapping logic
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
        Point pt = new Point(0,0);
        for(int i = 0; i < 5; i++)
            for(int j = 0; j < 5; j++)
                if(c == table[i][j].charAt(0))
                    pt = new Point(i,j);
        return pt;
    }

    //function prints the key-table in matrix form for playfair cipher
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
