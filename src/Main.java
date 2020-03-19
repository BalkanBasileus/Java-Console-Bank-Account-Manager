package com.company;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Collections;
import java.io.File;
import java.util.Comparator;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        ArrayList<BankBranch> accounts = new ArrayList<>(); //The bank accounts

        //Local Variables
        int acctNum = 0;        //For searching account
        short pin = 0;          //Entering pin
        boolean terminate = false;  //Terminates program
        char with_dept;         //Withdrawal / Deposit
        char conti = ' ';       //Decides to Terminate
        int index = 0;          //Finding account index in arrayList.
        double amt = 0;          //Withdrawal / Deposit amount

        //File Variables
        //File[] file = new File("/Users/Dimitrov/IdeaProjects/BankBranch").listFiles();
        //showFiles(file);


        try{
            readFile(accounts);         //Read file w/ accounts
            Collections.sort(accounts); //Sort
        }
        catch (IOException e){
            System.out.println("Could not find file. ");
            e.printStackTrace();
        }



        //Comparator used for Binary Search when looking for account/pin info.
        Comparator<BankBranch> c = new Comparator<BankBranch>()
        {
            public int compare(BankBranch acct1, BankBranch acct2)
            {
                return acct1.compareTo(acct2);
            }
        };


       //MAIN DRIVER ----
        do {

            //BANK MENU ----

            System.out.print("Enter account number: ");
            acctNum = input.nextInt();

            //Search For Account
            //If not found, returns -1 or larger negative(-).
            index = Collections.binarySearch(accounts,new BankBranch(null,null,acctNum,pin,0),c);


            //Error Check Account Number
            //If number does not match, prompt.
            if(index <= -1){
                System.out.println("Account not found!");
                System.out.println("Do you wish to continue[Y/N]: ");
                conti = input.next().charAt(0);

                //Error Check. Only accept [Y/N].
                while(Character.toUpperCase(conti) != 'N' && Character.toUpperCase(conti) != 'Y'){
                    System.out.println("Account not found!");
                    System.out.println("Do you wish to continue[Y/N]: ");
                    conti = input.next().charAt(0);
                }

                //Exit Application
                if(Character.toUpperCase(conti) == 'N'){
                    terminate = true;
                }

                //Go back to beginning by skipping past remainder of program.
                //Skips everything and goes back to "Enter Account Number: "
                if(Character.toUpperCase(conti) == 'Y'){
                    continue;
                }
            }

            //If no errors, proceed with pin.
            else {

                System.out.print("Enter pin number: ");
                pin = input.nextShort();


                //Error Check pin.
                if (accounts.get(index).getPin() != pin) {
                    System.out.println("Incorrect pin!");
                    System.out.println("Do you wish to continue[Y/N]: ");
                    conti = input.next().charAt(0);

                    //Error Check. Only accept [Y/N].
                    while (Character.toUpperCase(conti) != 'N' && Character.toUpperCase(conti) != 'Y') {
                        System.out.println("Incorrect pin!");
                        System.out.println("Do you wish to continue[Y/N]: ");
                        conti = input.next().charAt(0);
                    }

                    //Exit Application
                    if (Character.toUpperCase(conti) == 'N') {
                        terminate = true;
                    }

                    //Go back to beginning by skipping past remainder of program.
                    //Skips everything and goes back to "Enter Account Number: "
                    if (Character.toUpperCase(conti) == 'Y') {
                        continue;
                    }
                }

                //If pin is correct, proceed w/Display Account

                //Display Account
                accounts.get(index).displayAcct();

                System.out.println("Withdrawal or Deposit[W/D]: ");
                with_dept = input.next().charAt(0);

                //Error Check. Only accept [W/D].
                while (Character.toUpperCase(with_dept) != 'W' && Character.toUpperCase(with_dept) != 'D') {
                    System.out.println("Withdrawal or Deposit[W/D]: ");
                    with_dept = input.next().charAt(0);
                }

                //If Withdrawal
                if(Character.toUpperCase(with_dept) == 'W'){

                    System.out.println("Enter amount: ");
                    amt = input.nextShort();
                    accounts.get(index).withdraw(amt);
                }

                //If Deposit
                else if(Character.toUpperCase(with_dept) == 'D'){

                    System.out.println("Enter amount: ");
                    amt = input.nextShort();
                    accounts.get(index).deposit(amt);
                }

                //Display Account after transaction
                accounts.get(index).displayAcct();

                //Proceed or Terminate
                System.out.println("Do you wish to continue[Y/N]: ");
                conti = input.next().charAt(0);

                //Error Check. Only accept [Y/N].
                while (Character.toUpperCase(conti) != 'N' && Character.toUpperCase(conti) != 'Y') {
                    System.out.println("Do you wish to continue[Y/N]: ");
                    conti = input.next().charAt(0);
                }

                //Exit Application
                if (Character.toUpperCase(conti) == 'N') {
                    terminate = true;

                    try {
                        writeFile(accounts);
                    }
                    catch (IOException e){
                        System.out.println("Could not write file. ");
                        System.out.println("Account not updated. ");
                        e.printStackTrace();
                    }
                }

                //Go back to beginning by skipping past remainder of program.
                //Skips everything and goes back to "Enter Account Number: "
                if (Character.toUpperCase(conti) == 'Y') {
                    continue;
                }
            }

        }while (!terminate);



    }

    //METHODS
    /////////////////////////////////////////////////////////

    //Read File
    public static void readFile(ArrayList<BankBranch> accounts) throws FileNotFoundException , IOException {
        /*
        * Reads file with one space in-between each element. Declares FileReader and BufferedReader.
        * Creates temporary BankBranch class object. While reading file it will tokenize string and
        * parse it into temporary variable. Creates new temp bank account by calling constructor and
        * then adds it to the arrayList of bank accounts.
        */
        FileReader file = new FileReader("BankData.txt");
        BufferedReader br = new BufferedReader(file);
        String line;
        StringTokenizer st;


        BankBranch tmpAcct;


        while((line = br.readLine()) != null){ //So long as there is line to read.

            //tokenize the line
            st = new StringTokenizer(line);

            //Account elements
            String first = st.nextToken(" ");
            String last = st.nextToken(" ");
            int acct = Integer.parseInt(st.nextToken(" "));
            short pswrd = Short.parseShort(st.nextToken(" "));
            double bal = Double.parseDouble(st.nextToken(" "));

            //Create new bank account.
            tmpAcct = new BankBranch(first, last, acct, pswrd, bal); //Call constructor
            accounts.add(tmpAcct);

            //System.out.println(line);
            /**
             * .txt File must have only one space " " in-between
             * tokens. Otherwise throws NoSuchElementException.
             * */
        }
        //Close File.
        br.close();
    }

    //Write File
    public static void writeFile(ArrayList<BankBranch> accounts) throws IOException {
        /*
        * Method description..
        */
        FileWriter in = new FileWriter("BankData.txt");
        BufferedWriter bw = new BufferedWriter(in);
        String acctContent = "";

        /**
         * Need to find way to only write the new balance of a particular account.
         * There is no need to traverse every single account and write all of them
         * to file.
         */

        for (int i = 0; i < accounts.size(); i++) {

        acctContent +=  accounts.get(i).getFirstName() + " " + accounts.get(i).getLastName() + " " +
                        Integer.toString(accounts.get(i).getAccount()) + " "+ Short.toString(accounts.get(i).getPin()) + " " +
                        Double.toString(accounts.get(i).getBalance()) + "\n";

        }

        bw.write(acctContent);
        bw.close();
    }

    //Compare bank accounts
    public static boolean compareAccounts(BankBranch acct1, BankBranch acct2) {
        //Return whether the accounts match.
        return acct1.getAccount() == acct2.getAccount();

    }


    public static void showFiles(File[] files){
        /*
        * Use File.isDirectory() to test if the given file (path) is a directory.
        * If this is true, then you just call the same method again with its
        * File.listFiles() outcome recursively.
        */

        for(File file : files){
            if(file.isDirectory()){
                System.out.println("Directory: " + file.getName());
                showFiles(file.listFiles()); // Calls same method again recursively.
            }
            else{
                System.out.println("File: " + file.getName());
            }
        }
    }

}
