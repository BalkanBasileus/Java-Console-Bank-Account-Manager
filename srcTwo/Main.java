/*******************************************************************
* The following JAVA program simulates an ATM menu. The user enters
* a bank account number and decides whether to withdrawal or
* deposit. Account will update and ask for another transaction or
* quit.
*
* Files: 1)Main.java 2)BankAccount.java 3)BankData.txt
*
*******************************************************************/
package com.HashMap;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        //Program Variables
        int acctEnter;      //Account number input
        short pin;          //Account pin input
        boolean resume = true;
        char wd;            //Withdraw or deposit choice
        char contYN;        //Another Transaction choice
        double wdAmount;    //Withdraw or deposit amount
        Scanner input = new Scanner(System.in);

        // Create a hash map
        HashMap<Integer,BankAccount> hm = new HashMap<Integer, BankAccount>();
        Set set = hm.entrySet();


        //Run Program ***********
        try{
            readFile(hm);
        }
        catch (IOException e){
            System.out.println("Could not find file. ");
            //e.printStackTrace();
        }


        do {

            try {//Find account
                System.out.print("Enter account: ");
                acctEnter = input.nextInt();

                try{//Match Pin
                    System.out.print("Enter Pin: ");
                    pin = input.nextShort();

                    //If Pin Correct
                    if( pin == hm.get(acctEnter).getPin() ){

                        hm.get(acctEnter).displayAcct(); //Display account

                        System.out.print("Withdrawal or Deposit[W/D]: ");
                        wd = input.next().charAt(0);

                        //Error Check
                        while (Character.toLowerCase(wd) != 'w' && Character.toLowerCase(wd) != 'd'){
                            System.out.print("Withdrawal or Deposit[W/D]: ");
                            wd = input.next().charAt(0);
                        }

                        //Withdraw
                        if(wd == 'w'){
                            System.out.print("Enter amount: ");
                            wdAmount = input.nextDouble();

                            //Update account balance and display
                            hm.get(acctEnter).withdraw(wdAmount);
                            hm.get(acctEnter).displayAcct();
                        }

                        //Deposit
                        if(wd == 'd'){
                            System.out.print("Enter amount: ");
                            wdAmount = input.nextDouble();

                            //Update account balance and display
                            hm.get(acctEnter).deposit(wdAmount);
                            hm.get(acctEnter).displayAcct();
                        }

                        //Continue
                        System.out.print("Another Transaction[y/n]: ");
                        contYN = input.next().charAt(0);

                        //Error Check
                        while (Character.toLowerCase(contYN) != 'y' && Character.toLowerCase(contYN) != 'n'){
                            System.out.print("Another Transaction[y/n]: ");
                            contYN = input.next().charAt(0);
                        }

                        //Terminate
                        if(contYN == 'n'){
                            System.out.println("Goodbye.");
                            resume = false;  //Terminate
                        }

                        //Otherwise repeat do-while
                        else{
                            continue;
                        }

                    }
                    //If incorrect pin, prompt and restart.
                 //   else {

                   // }
                }
                catch (NullPointerException | InputMismatchException e){
                    System.out.println("Incorrect Pin. ");
                    input.next();// Move to next other wise exception

                    //Skip past remainder of program and restart do-while.
                    continue;
                }


            } catch (NullPointerException | InputMismatchException e) {
                System.out.println("Could not find account. ");
                input.next();// Move to next other wise exception
            }


        }while(resume);

        //End Program ***********
    }


    //METHODS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Read File
    public static void readFile(HashMap<Integer,BankAccount> hashMap) throws FileNotFoundException, IOException {
        /*
         * Reads file with one space in-between each element. Declares FileReader and BufferedReader.
         * Creates temporary BankAccount class object. While reading file it will tokenize string and
         * parse it into temporary variable. Creates new temp bank account by calling constructor and
         * then adds it to the hashmap of bank accounts.
         */
        FileReader file = new FileReader("BankData.txt");
        BufferedReader br = new BufferedReader(file);
        String line;
        StringTokenizer st;


        BankAccount tmpAcct;


        while ((line = br.readLine()) != null) { //So long as there is line to read.

            //tokenize the line
            st = new StringTokenizer(line);

            //Account elements
            String first = st.nextToken(" ");
            String last = st.nextToken(" ");
            int acct = Integer.parseInt(st.nextToken(" "));
            short pswrd = Short.parseShort(st.nextToken(" "));
            double bal = Double.parseDouble(st.nextToken(" "));

            //Create new bank account.
            tmpAcct = new BankAccount(first, last, acct, pswrd, bal); //Call constructor
            hashMap.put( tmpAcct.getAccount(), tmpAcct );

            //System.out.println(line);
            /**
             * .txt File must have only one space " " in-between
             * tokens. Otherwise throws NoSuchElementException.
             * */
        }
        //Close File.
        br.close();
    }
}


/*
SAMPLE OUTPUT

Enter account: 526115
Enter Pin: hello
Incorrect Pin.
Enter account: 526115
Enter Pin: 2441
////////////////////////
Name:          Mirna Oke
Account:          526115
Balance:          253.41
////////////////////////
Withdrawal or Deposit[W/D]: w
Enter amount: 100
////////////////////////
Name:          Mirna Oke
Account:          526115
Balance:          153.41
////////////////////////
Another Transaction[y/n]: y
Enter account: 526115
Enter Pin: 2441
////////////////////////
Name:          Mirna Oke
Account:          526115
Balance:          153.41
////////////////////////
Withdrawal or Deposit[W/D]: d
Enter amount: 100
////////////////////////
Name:          Mirna Oke
Account:          526115
Balance:          253.41
////////////////////////
Another Transaction[y/n]: n
Goodbye.
*/