import java.util.Scanner;

public class Polybius {

    /*
        polybius square cipher
        refer to this link: https://www.braingle.com/brainteasers/codes/polybius.php#form
        SINCE WE HAVE 26 LETTERS IN THE ALPHABETS
        I AND J HAVE THE SAME VALUE SO THAT IT WILL BE 5X5 SQUARE OR 25 LETTERS
     */

    public static char[][] encryptionSquare = {
            {'A','B','C','D','E'},
            {'F','G','H','I','K'},
            {'L','M','N','O','P'},
            {'Q','R','S','T','U'},
            {'V','W','X','Y','Z'}
    };
    public static void main(String[]args){
        Scanner input = new Scanner(System.in);
        int choice;

        /*
            A LOOP TO SHOW THE USER IT'S CHOICES
         */
        do{
            System.out.print("""
                [1]ENCIPHER
                [2]DECIPHER
                [3]EXIT
                Choose from the category:ㅤ""");
            //accepts the user input as string and convert it to integer to prevent unnecessary bugs
            choice = Integer.parseInt(input.nextLine());

            //if the user's choice is 1 or 2, it will go in this body
            if(choice==1||choice==2){
                //ask the user for the text to cipher or decipher depending on their choice
                System.out.print("Enter text: ");
                String text = input.nextLine();

                switch(choice){
                    //if the user chooses 1 then it will print out the enciphered text
                    case 1 -> System.out.println("Encipher: " + encipher(text));
                    //if the user chooses 2 then it will print out the deciphered text
                    case 2 -> System.out.println("Decipher: " + decipher(text));
                }
            }

        } while(choice!=3);
    }

    //ENCIPHER METHODS WHICH RETURNS A STRING
    public static String encipher(String text){

        //CONVERTS THE GIVEN TEXT TO UPPERCASE
        text = text.toUpperCase();

        //INITIALIZE A VARIABLE OF WHICH WE CAN BUILD OUR STRING
        StringBuilder encrypt= new StringBuilder();

        //LOOP TO MAKE THE ENCRYPTION, IT WILL LOOP UNTIL IT REACHED THE LAST CHARACTER OF THE GIVEN TEXT
        for(int i=0; i<text.length(); i++){

            //CONDITIONALS TO CHECK WHETHER THE GIVEN INPUT IS A TO Z
            if(Character.toString(text.charAt(i)).matches("[a-zA-Z]+")){

                //CONDITIONAL IF THE CHARACTER IS LETTER J
                if(text.charAt(i) == 'J' || text.charAt(i) == 'j'){

                    /*
                        SINCE I AND J HAVE THE SAME VALUE
                        WE WILL MANUALLY ADD THE 2,4 VALUE
                        (2,4 IS THE X AND Y LOCATION OF THE LETTER I IN THE POLYBIUS SQUARE)
                        REFER TO THE POLYBIUS SQUARE ABOVE
                        2 IS THE ROW
                        4 IS THE COLUMN

                        NOTE: ROW WILL ALWAYS BE ADDED BEFORE COLUMN
                    */
                    encrypt.append(2);
                    encrypt.append(4);
                }

                //OTHERWISE IT WILL FIND THE CHARACTER IN THE SQUARE
                else {

                    //A LOOP TO CHECK EACH ELEMENT IN THE POLYBIUS SQUARE
                    for(int x=0; x<5; x++){
                        for(int y=0; y<5; y++){
                            /*
                                IF THE ELEMENT IN THE POLYBIUS SQUARE MATCHES THE CHARACTER THAT
                                WE ARE SEARCHING FOR WE WILL ADD IT'S X AND Y VALUE
                                TO THE ENCRYPTED TEXT

                                NOTE THAT WE ALWAYS HAVE TO ADD 1 TO X AND Y
                                SINCE THE ARRAY ELEMENTS STARTS AT 0 INDEX.
                             */
                            if(encryptionSquare[x][y] == text.charAt(i)){
                                encrypt.append(x+1);
                                encrypt.append(y+1);
                            }
                        }
                    }
                }
                //SKIPS THE CURRENT ITERATION SO THAT IT WONT ADD UNNECESSARY VALUES TO THE ENCRYPTION.
                continue;
            }

            /*
                IF THE CHARACTER IS A SPACE THEN WE WILL ADD THAT TO THE ENCRYPTION
                OTHERWISE IT WILL GET IGNORED

                E.G.
                TEXT = ABC BC
                ENCRYPTED TEXT = 111213 1213

                TEXT = ABC,BC
                ENCRYPTED TEXT = 1112131213
             */
            if(text.charAt(i)==' '){
                encrypt.append(text.charAt(i));
            }
        }

        //RETURNS THE BUILT ENCRYPTION AS A STRING.
        return encrypt.toString();
    }
    public static String decipher(String text){

        /*
            TWO STRING BUILDER VARIABLE ONE IS FOR WORD
            AND THE OTHER ONE IS FOR THE WHOLE STRING
            OR THE WHOLE DECRYPTED TEXT
         */
        StringBuilder word = new StringBuilder();
        StringBuilder decrypt = new StringBuilder();

        //THIS IS JUST A COUNTER FOR THE LOOP
        int i=0;
        while(i<text.length()){

            //CHECK IF THE CHARACTER IS NUMERIC (0-9) USING A REGEX FUNCTION
            //OR IF THE VALUE IS THE COUNTER IS 1 LESS THAN THE TEXT'S LENGTH
            if((!Character.toString(text.charAt(i)).matches("-?(0|[1-9]\\d*)"))||i==text.length()-1){

                //INITIALIZE OUR STRING VARIABLE
                String s="";

                //CHECK IF THE COUNTER IS 1 LESS THAN THE TEXT'S LENGTH
                //THEN IT WILL ADD THE LAST CHARACTER TO THE WORD
                if(i==text.length()-1){
                    word.append(text.charAt(i));
                }

                /*
                    CHECK IF THE CHARACTER IS NOT NUMERIC AND IF IT'S SPACE
                    IT WILL ADD THE SPACE CHARACTER TO THE VARIABLE S
                */
                if(!Character.toString(text.charAt(i)).matches("-?(0|[1-9]\\d*)")){
                    if(text.charAt(i)==' ') s = String.valueOf(text.charAt(i));
                }

                //ADD THE WORD THAT IS GOTTEN FROM THE decipherWord method
                //TO THE DECRYPT VARIABLE (A VARIABLE THAT WHICH HOLDS OUR WHOLE DECRYPTED TEXT)
                decrypt.append(decipherWord(word.toString())).append(s);

                //RESETS THE WORD VARIABLE, SO THAT IT WILL NOW
                //PROCESS OTHER WORD.
                word = new StringBuilder();

                //INCREASE THE COUNTER COUNT
                i++;
                //SKIPS ITERATION
                continue;
            }

            //ADDS THE CHARACTER TO OUR WORD
            word.append(text.charAt(i));
            //INCREASE THE COUNT
            i++;
        }

        //RETURNS THE DECRYPTED TEXT
        return decrypt.toString();
    }

    //ANOTHER METHOD THAT WILL DECRYPT THE NUMBERS GIVEN
    public static String decipherWord(String text){
        //DECRYPT VARIABLE THAT HOLDS THE WORD (A SINGLE WORD)
        StringBuilder decrypt = new StringBuilder();

        //A LOOP TO FIND THE CHARACTER GIVEN THE X AND Y VALUES FROM THE NUMBER
        for(int i=0; i<text.length(); i+=2){

            /*
                IF THE TEXT'S LENGTH IS ODD THEN IT WON'T ADD THE LAST NUMBER
                E.G. 243
                IT WILL ONLY GET THE 2 AND 4 WHICH IS THE LETTER 'I'
                THE NUMBER 3 WILL GET IGNORED SINCE IT DOESN'T HAVE A NUMBER AFTER IT
                (THE Y AXIS NUMBER)
                THEN IT WILL END THE LOOP.
            */
            if(i >= text.length() - 3 && i!=0 && text.length()%2!=0){
                break;
            }

            /*
                get the wrong and column from the character's ascii value
                then subtracts 1 from it, since array elements starts with 0
             */
            int row = text.charAt(i) - '0' - 1;
            int col = text.charAt(i+1) - '0'- 1;

            //adds the letter to the decrypt word
            decrypt.append(encryptionSquare[row][col]);
        }
        //return the decrypted word as a string
        return decrypt.toString();
    }
}