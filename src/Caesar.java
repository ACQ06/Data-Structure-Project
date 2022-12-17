import java.util.Scanner;
public class Caesar {
    public static void main(String[]args){
        Scanner input = new Scanner(System.in);
        int choice;

        //loop to print out the choices
        do{
            System.out.print("""
                [1]ENCIPHER
                [2]DECIPHER
                [3]EXIT
                Choose from the category:ㅤ""");
            //accept the given input as a string and parse(convert) it to integer.
            //to avoid unnecessary scanner bug.
            choice = Integer.parseInt(input.nextLine());


            //condition if the user chooses either 1 or 2
            if(choice==1||choice==2){
                //prompts the user to enter a text for the program to cipher or decipher
                //depends on the user's choice
                System.out.print("Enter text: ");
                String text = input.nextLine();

                /*
                    Shift the alphabet position to the right depending on how much
                    the user wanted to.
                    e.g.
                    Number of letters to shift to the right: 4
                    FROM: A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
                    TO: W X Y Z A B C E D F G H I J K L M N O P Q R S T U V
                 */
                System.out.print("Number of letters to shift to the right: ");
                int shift = Integer.parseInt(input.nextLine());
                /*
                    switch case to give the user the right output depending on their choice.
                    refer to input from the question:
                    [1]ENCIPHER
                    [2]DECIPHER
                    [3]EXIT
                    Choose from the category:ㅤ
                */
                switch(choice){
                    /*
                        if the user chooses 1 then it's cipher
                        we will pass the text the user inputted previously
                        and the number of how much the alphabet will shift.
                     */
                    case 1 -> System.out.println(encipher(text, shift));
                    /*
                        if 2 then it will be to decipher
                        we will pass the text the user inputted previously
                        and the number of how much the alphabet will shift.
                     */
                    case 2 -> System.out.println(decipher(text, shift));
                }
            }

            //if the user chooses 3 then the program will exit
            //otherwise it will just loop back to the category question
        } while(choice!=3);
    }

    //ENCIPHER METHOD, ACCEPTS STRING, AND INTEGER VALUES
    public static String encipher(String text, int shift){
        /*
            INITIALIZING A STRING BUILDER VARIABLE,
            A VARIABLE OF WHICH WE CAN MAKE OUR STRING
            BY USING THE .APPEND METHOD
            E.G.
            CURRENT STRING: ABC
                            STRING.APPEND('D')
            NEW STRING: ABCD
            BASICALLY ADDS CHARACTER TO MAKE A STRING.
         */
        StringBuilder encrypted= new StringBuilder();

        //PROGRAM WILL LOOP UNTIL IT REACHED THE LAST CHARACTER OF THE GIVEN STRING
        for (int i=0; i<text.length(); i++) {

            /*
                CHECKS IF THE CHARACTER IS IN THE ALPHABET, LOWERCASE OR UPPERCASE
                USING AN ASCII TABLE
                REFER TO THIS LINK TO VIEW THE ASCII TABLE: https://www.rapidtables.com/code/text/ascii-table.html
             */
            if((text.charAt(i)>=65 && text.charAt(i)<=90) ||(text.charAt(i)>=97 && text.charAt(i)<=122)){

                //CHECK IF THE GIVEN CHARACTER IS UPPERCASE
                if(Character.isUpperCase(text.charAt(i))){
                    /*
                        INITIALIZE A RANGE VARIABLE WHICH HOLDS A VALUE OF THE GIVEN CHARACTER
                        ACCORDING THE ASCII TABLE
                        E.G. 'A' HAS A VALUE OF 65
                        THIS IS ONLY USEFUL IF CHARACTER IS A OR Z
                        OTHERWISE IT'S VALUE IS GONNA CHANGE
                     */
                    int range=text.charAt(i);

                    //OTHERWISE, IF THE CHARACTER IS FROM B - Y, IT'S VALUE IS GOING TO BE 65
                    if(text.charAt(i)>65 && text.charAt(i)<90) range = 65;

                    /*
                        A SIMPLE FORMULA TO GET THE CHARACTER GIVEN THE SHIFTED ALPHABETS
                        THIS FOLLOWS THE PEMDAS RULE
                        IT WILL FIRST COMPUTE FOR SHIFT - RANGE
                        THEN GET THE CHARACTER VALUE FROM THE ASCII TABLE
                        AND ADDS THE PREVIOUS VALUE TO IT SINCE THE ALPHABET IS SHIFTED
                        AND GET THE MODULO, OR REMAINDER OF IT
                        E.G. 27%26 = 1 BECAUSE THE REMAINDER OF 27 DIVIDED 26 IS 1
                        THEN LASTLY ADD 65 TO GET THE RIGHT CHARACTER WITH THE SHIFTED ALPHABETS
                     */
                    char c = (char)((text.charAt(i) + shift - range) % 26 + 65);

                    //ADD THE CHARACTER TO THE ENCRYPTED TEXT.
                    encrypted.append(c);
                }

                //OTHERWISE IF IT'S LOWERCASE
                else{
                    /*
                        INITIALIZE A RANGE VARIABLE WHICH HOLDS A VALUE OF THE GIVEN CHARACTER
                        ACCORDING THE ASCII TABLE
                        E.G. 'a' HAS A VALUE OF 97
                        THIS IS ONLY USEFUL IF CHARACTER IS a OR z
                        OTHERWISE IT'S VALUE IS GONNA CHANGE
                     */
                    int range=text.charAt(i);

                    //OTHERWISE, IF THE CHARACTER IS FROM b - y, IT'S VALUE IS GOING TO BE 97
                    if(text.charAt(i)>97 && text.charAt(i)<122) range = 97;

                    /*
                        A SIMPLE FORMULA TO GET THE CHARACTER GIVEN THE SHIFTED ALPHABETS
                        THIS FOLLOWS THE PEMDAS RULE
                        IT WILL FIRST COMPUTE FOR SHIFT - RANGE
                        THEN GET THE CHARACTER VALUE FROM THE ASCII TABLE
                        AND ADDS THE PREVIOUS VALUE TO IT SINCE THE ALPHABET IS SHIFTED
                        AND GET THE MODULO, OR REMAINDER OF IT
                        E.G. 27%26 = 1 BECAUSE THE REMAINDER OF 27 DIVIDED 26 IS 1
                        THEN LASTLY ADD 97 TO GET THE RIGHT CHARACTER WITH THE SHIFTED ALPHABETS
                     */
                    char c = (char)((text.charAt(i) + shift - range) % 26 + 97);

                    //ADD THE CHARACTER TO THE ENCRYPTED TEXT.
                    encrypted.append(c);
                }
                //SKIPS THE CURRENT ITERATION SO THAT IT WON'T HAVE UNNECESSARY VALUES.
                continue;
            }
            //IF THE CHARACTER ISN'T IN ALPHABET, IT WILL JUST ADD WHATEVER THE CHARACTER IS TO THE ENCRYPTION STRING.
            encrypted.append(text.charAt(i));
        }

        //RETURNS THE ENCRYPTED TEXT IN STRING FORMAT.
        return encrypted.toString();
    }

    //DECIPHER METHOD, ACCEPTS STRING, AND INTEGER VALUES
    public static String decipher(String text, int shift){
        /*
            INITIALIZING A STRING BUILDER VARIABLE,
            A VARIABLE OF WHICH WE CAN MAKE OUR STRING
            BY USING THE .APPEND METHOD
            E.G.
            CURRENT STRING: ABC
                            STRING.APPEND('D')
            NEW STRING: ABCD
            BASICALLY ADDS CHARACTER TO MAKE A STRING.
         */
        StringBuilder encrypted= new StringBuilder();

        //PROGRAM WILL LOOP UNTIL IT REACHED THE LAST CHARACTER OF THE GIVEN STRING
        for (int i=0; i<text.length(); i++) {

            /*
                CHECKS IF THE CHARACTER IS IN THE ALPHABET, LOWERCASE OR UPPERCASE
                USING AN ASCII TABLE
                REFER TO THIS LINK TO VIEW THE ASCII TABLE: https://www.rapidtables.com/code/text/ascii-table.html
             */
            if((text.charAt(i)>=65 && text.charAt(i)<=90) ||(text.charAt(i)>=97 && text.charAt(i)<=122)){

                //CHECK IF THE GIVEN CHARACTER IS UPPERCASE
                if(Character.isUpperCase(text.charAt(i))){
                    /*
                        INITIALIZE A RANGE VARIABLE WHICH HOLDS A VALUE OF THE GIVEN CHARACTER
                        ACCORDING THE ASCII TABLE
                        E.G. 'A' HAS A VALUE OF 65
                        THIS IS ONLY USEFUL IF CHARACTER IS A OR Z
                        OTHERWISE IT'S VALUE IS GONNA CHANGE
                     */

                    int range=text.charAt(i);

                    //OTHERWISE, IF THE CHARACTER IS FROM B - Y, IT'S VALUE IS GOING TO BE 65
                    if(text.charAt(i)>65 && text.charAt(i)<90) range = 65;

                    /*
                        A SIMPLE FORMULA TO GET THE CHARACTER GIVEN THE SHIFTED ALPHABETS
                        THIS FOLLOWS THE PEMDAS RULE
                        IT WILL FIRST COMPUTE FOR SHIFT - RANGE
                        THEN GET THE CHARACTER VALUE FROM THE ASCII TABLE
                        AND SUBTRACT THE PREVIOUS VALUE TO IT SINCE THE ALPHABET IS SHIFTED
                        AND GET THE MODULO, OR REMAINDER OF IT
                        E.G. 27%26 = 1 BECAUSE THE REMAINDER OF 27 DIVIDED 26 IS 1
                        THEN LASTLY ADD 65 TO GET THE RIGHT CHARACTER WITH THE SHIFTED ALPHABETS
                     */
                    char c = (char)(((text.charAt(i) - shift - range)+26) % 26 + 65);

                    //ADD THE CHARACTER TO THE ENCRYPTED TEXT.
                    encrypted.append(c);
                }

                //OTHERWISE IF IT'S LOWERCASE
                else if(Character.isLowerCase(text.charAt(i))){
                    /*
                        INITIALIZE A RANGE VARIABLE WHICH HOLDS A VALUE OF THE GIVEN CHARACTER
                        ACCORDING THE ASCII TABLE
                        E.G. 'a' HAS A VALUE OF 97
                        THIS IS ONLY USEFUL IF CHARACTER IS a OR z
                        OTHERWISE IT'S VALUE IS GONNA CHANGE
                     */
                    int range=text.charAt(i);

                    //OTHERWISE, IF THE CHARACTER IS FROM b - y, IT'S VALUE IS GOING TO BE 97
                    if(text.charAt(i)>97 && text.charAt(i)<122) range = 97;


                    /*
                        A SIMPLE FORMULA TO GET THE CHARACTER GIVEN THE SHIFTED ALPHABETS
                        THIS FOLLOWS THE PEMDAS RULE
                        IT WILL FIRST COMPUTE FOR SHIFT - RANGE
                        THEN GET THE CHARACTER VALUE FROM THE ASCII TABLE
                        AND SUBTRACT THE PREVIOUS VALUE TO IT SINCE THE ALPHABET IS SHIFTED
                        AND GET THE MODULO, OR REMAINDER OF IT
                        E.G. 27%26 = 1 BECAUSE THE REMAINDER OF 27 DIVIDED 26 IS 1
                        THEN LASTLY ADD 97 TO GET THE RIGHT CHARACTER WITH THE SHIFTED ALPHABETS
                     */
                    char c = (char)(((text.charAt(i) - shift - range)+26) % 26 + 97);

                    //ADD THE CHARACTER TO THE ENCRYPTED TEXT.
                    encrypted.append(c);
                }
                //SKIPS THE CURRENT ITERATION SO THAT IT WON'T HAVE UNNECESSARY VALUES.
                continue;
            }
            //IF THE CHARACTER ISN'T IN ALPHABET, IT WILL JUST ADD WHATEVER THE CHARACTER IS TO THE ENCRYPTION STRING.
            encrypted.append(text.charAt(i));
        }

        //RETURNS THE DECRYPTED TEXT IN STRING FORMAT.
        return encrypted.toString();
    }
}