import java.util.Scanner;

public class Polybius {
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

                switch(choice){
                    case 1 -> System.out.println("Encipher: " + encipher(text));
                    case 2 -> System.out.println("Decipher: " + decipher(text));
                }
            }
        } while(choice!=3);
    }
    public static String encipher(String text){
        text = text.toUpperCase();
        StringBuilder encrypt= new StringBuilder();
        for(int i=0; i<text.length(); i++){
            if(Character.toString(text.charAt(i)).matches("[a-zA-Z]+")){
                if(text.charAt(i) == 'J' || text.charAt(i) == 'j'){
                    encrypt.append(2);
                    encrypt.append(4);
                }
                else {
                    for(int x=0; x<5; x++){
                        for(int y=0; y<5; y++){
                            if(encryptionSquare[x][y] == text.charAt(i)){
                                encrypt.append(x+1);
                                encrypt.append(y+1);
                            }
                        }
                    }
                }
                continue;
            }
            if(text.charAt(i)==' '){
                encrypt.append(text.charAt(i));
            }
        }
        return encrypt.toString();
    }
    public static String decipher(String text){
        StringBuilder word = new StringBuilder();
        StringBuilder decrypt = new StringBuilder();
        int i=0;
        while(i<text.length()){
            if((!Character.toString(text.charAt(i)).matches("-?(0|[1-9]\\d*)"))||i==text.length()-1){
                String s="";
                if(i==text.length()-1){
                    word.append(text.charAt(i));
                }

                if(!Character.toString(text.charAt(i)).matches("-?(0|[1-9]\\d*)")){
                    if(text.charAt(i)==' ') s = String.valueOf(text.charAt(i));
                }
                decrypt.append(decipherWord(word.toString())).append(s);
                word = new StringBuilder();
                i++;
                continue;
            }
            word.append(text.charAt(i));
            i++;
        }
        return decrypt.toString();
    }
    public static String decipherWord(String text){
        StringBuilder decrypt = new StringBuilder();
        for(int i=0; i<text.length(); i+=2){
            if(i >= text.length() - 3 && i!=0 && text.length()%2!=0){
                break;
            }
            int row = text.charAt(i) - '0' - 1;
            int col = text.charAt(i+1) - '0'- 1;
            decrypt.append(encryptionSquare[row][col]);
        }
        return decrypt.toString();
    }
}