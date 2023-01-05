import java.awt.Point;
import java.util.Scanner;

public class Playfair
{
    private int length = 0;
    private String[][] table;
    public static void call_playfair()
    {
        Playfair pf = new Playfair();
    }
    private Playfair()
    {
        Scanner sc = new Scanner(System.in);
        int choice;
        System.out.print("Enter the key for playfair cipher: ");
        String key = parseString(sc);
        while(key.equals(""))
            key = parseString(sc);
        table = this.cipherTable(key);
        this.keyTable(table);
        do {
            System.out.print("""
                [1]ENCIPHER
                [2]DECIPHER
                [3]EXIT
                Choose from the category:ㅤ""");
            choice = Integer.parseInt(sc.nextLine());
            if(choice==1||choice==2) {
                System.out.print("Enter text: ");
                String input = parseString(sc);
                while (input.equals(""))
                    input = parseString(sc);
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

    private String parseString(Scanner sc)
    {
        String parse = sc.nextLine();
        parse = parse.toUpperCase();
        parse = parse.replaceAll("[^A-Z]", "");
        parse = parse.replace("J", "I");
        return parse;
    }


    private String[][] cipherTable(String key)
    {
        String[][] playfairTable = new String[5][5];
        String keyString = key + "ABCDEFGHIKLMNOPQRSTUVWXYZ";
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
    private String cipher(String in)
    {
        length = in.length() / 2 + in.length() % 2;
        for(int i = 0; i < (length - 1); i++)
        {
            if(in.charAt(2 * i) == in.charAt(2 * i + 1))
            {
                in = new StringBuffer(in).insert(2 * i + 1, 'X').toString();
                length = in.length() / 2 + in.length() % 2;
            }
        }
        String[] digraph = new String[length];
        for(int j = 0; j < length ; j++)
        {
            if(j == (length - 1) && in.length() / 2 == (length - 1))
                in = in + "X";
            digraph[j] = in.charAt(2 * j) +""+ in.charAt(2 * j + 1);
        }

        StringBuilder out = new StringBuilder();
        String[] encDigraphs;
        encDigraphs = encodeDigraph(digraph);
        for(int k = 0; k < length; k++)
            out.append(encDigraphs[k]);
        return out.toString();
    }

    //ENCRYPTION
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

            if(r1 == r2)
            {

                c1 = (c1 + 1) % 5;
                c2 = (c2 + 1) % 5;
            }

            else if(c1 == c2)
            {
                //Shifts the rows down by 1
                r1 = (r1 + 1) % 5;
                r2 = (r2 + 1) % 5;
            }

            else
            {
                int temp = c1;
                c1 = c2;
                c2 = temp;
            }

            encipher[i] = table[r1][c1] + "" + table[r2][c2];
        }
        return encipher;
    }

    //-----------------------decryption logic---------------------
    // decodes the text (opp. of encoding process)
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
                int temp = c1;
                c1 = c2;
                c2 = temp;
            }
            decoded.append(table[r1][c1]).append(table[r2][c2]);
        }
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

    //Function prints the key-table in matrix form for playfair cipher
    private void keyTable(String[][] printTable)
    {
        System.out.println("Playfair Cipher Key Matrix: ");
        System.out.println();

        for(int i = 0; i < 5; i++)
        {
            for(int j = 0; j < 5; j++)
            {

                System.out.print(printTable[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }
}