import java.util.Scanner;
public class Caesar {
    public static void caesar(){
        Scanner input = new Scanner(System.in);
        int choice;

        do{
            System.out.print("""
                [1]ENCIPHER
                [2]DECIPHER
                [3]EXIT
                Choose from the category:ㅤ""");
            choice = Integer.parseInt(input.nextLine());


            if(choice==1||choice==2){
                System.out.print("Enter text: ");
                String text = input.nextLine();
                System.out.print("Number of letters to shift to the right: ");

                int shift = Integer.parseInt(input.nextLine());
                switch(choice){

                    case 1 -> System.out.println(encipher(text, shift));

                    case 2 -> System.out.println(decipher(text, shift));
                }
            }
        } while(choice!=3);
    }

    public static String encipher(String text, int shift){
        StringBuilder encrypted= new StringBuilder();
        for (int i=0; i<text.length(); i++) {
            if((text.charAt(i)>=65 && text.charAt(i)<=90) ||(text.charAt(i)>=97 && text.charAt(i)<=122)){
                if(Character.isUpperCase(text.charAt(i))){
                    int range = 65;
                    char c = (char)((((text.charAt(i) + shift) - range) % 26) + 65);
                    encrypted.append(c);
                }

                else{
                    int range = 97;
                    char c = (char)((((text.charAt(i) + shift) - range) % 26) + 97);
                    encrypted.append(c);
                }
                continue;
            }
            encrypted.append(text.charAt(i));
        }
        return encrypted.toString();
    }

    public static String decipher(String text, int shift){
        StringBuilder encrypted= new StringBuilder();
        for (int i=0; i<text.length(); i++) {
            if((text.charAt(i)>=65 && text.charAt(i)<=90) ||(text.charAt(i)>=97 && text.charAt(i)<=122)){
                if(Character.isUpperCase(text.charAt(i))){
                    int range = 65;
                    char c = (char)(((((text.charAt(i) + 26) - shift) - range) % 26) + 65);
                    encrypted.append(c);
                }

                else if(Character.isLowerCase(text.charAt(i))){
                    int range = 97;
                    char c = (char)(((((text.charAt(i) + 26) - shift) - range) % 26) + 97);
                    encrypted.append(c);
                }
                continue;
            }
            encrypted.append(text.charAt(i));
        }

        return encrypted.toString();
    }
}