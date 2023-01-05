import java.util.Scanner;

public class Main {
    public static void main(String[]args){
        Scanner input = new Scanner(System.in);
        int choice;
        do{
            System.out.print("""
                [1]CAESAR
                [2]PLAYFAIR
                [3]POLYBIUS
                [4]EXIT
                Choose from the category:ㅤ""");
            choice = Integer.parseInt(input.nextLine());

            if(choice==1||choice==2||choice==3){
                switch(choice){
                    case 1 -> Caesar.caesar();
                    case 2 -> Playfair.call_playfair();
                    case 3 -> Polybius.polybius();
                }
            }
        } while(choice!=4);
    }
}
