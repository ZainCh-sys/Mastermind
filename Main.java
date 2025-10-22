/*
Name's:Zain,Pav,Stiven
Date:June 10,2025
Description:Final sprint ISU Game
*/


import java.util.*;
import game.*;
import info.*;

public class Main {
    // ANSI color codes
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
  
    public static void main(String[] args) {      
        //objects and varriables
      int cashAmount = 500;
      int level=0, options=0, race=0, horse=0, bet=0;
      Race b = new Race();
      AI a = new AI();
      Shop c = new Shop();
      Scanner input = new Scanner(System.in);
      StatusEffect defaultEffect = new StatusEffect();
      Weapons defaultWeapons = new Weapons();
      StatusEffect effect = new StatusEffect();
      StatusEffect boost = new StatusEffect("Boost", 2, 2, 2);
      boolean lowBet;

      String option;
      cashAmount = a.getStartingCash();
      do{
        System.out.println(GREEN+"1. Play"+RESET);
        System.out.println(YELLOW+"2. Shop/Bet"+RESET);
        System.out.println(PURPLE+"3. Help"+RESET);
        System.out.println(RED+"4. Exit"+RESET);
        System.out.println("Pick an option from above: ");
        option = input.next();
        
        if(option.equalsIgnoreCase("1")||option.equalsIgnoreCase("Play")){
          weaponsOwned(defaultWeapons);
          a.playGame(input);
        }
        //option 2
        if(option.equalsIgnoreCase("2")||option.equalsIgnoreCase("Shop")||option.equalsIgnoreCase("Bet")){
            if (cashAmount>=100){
              effect.effectName("Price Decrease");
              effect.setAmount(0.5);
              effect.setDuration(1);
              effect.setTurns(1);
              effect.startEffect();
            }
            do{
              System.out.println("Pick a option?\n1.Bet\n2.Shop");
            try{
            race = input.nextInt();
            }
            catch(InputMismatchException e){
              System.out.println("Error must be a number");
              input.next();
            }
            
            }while(race<1 || race>2);
          if(race == 1){
            System.out.println("You have a total of $" + cashAmount + " to use\n\n");
            System.out.println("Here are the horses\n\n");
            b.randNames();
            b.generateName();
            b.generateSpeed();
            do{
              System.out.println("\n\nWhich one would you like to bet on?(1-6)");
                try{
                  horse = input.nextInt();
                  }
                catch(InputMismatchException e){
                  System.out.println("Please enter a number!");
                  input.next();
                }
              }while(horse<1 || horse>6);
            b.setHorse(--horse);
            do{
              System.out.println("\nhow much would you like to bet? ");
              try{
                bet = input.nextInt();
              }
              catch(InputMismatchException e){
                System.out.println("Error must be a number\n Enter your bet again: ");
                input.next();
              }
            }while(bet<0 || bet>cashAmount);
            
            b.setBet(bet);
            cashAmount-=bet;
            
            System.out.println("\n");
            b.generateTrackLength();
            System.out.println("\n");
            b.winningHorse();
            
            if (b.winningAmount()<100){
              lowBet = true;
              System.out.println("You have won " + b.winningAmount()*boost.getDuration());
              cashAmount += b.winningAmount()*boost.getDuration();
            }
            else{
              lowBet = true;
              System.out.println("You have won " + b.winningAmount());
              cashAmount += b.winningAmount();
            }
            
          }
          //pav
          if(race == 2){
            a.shop(input, effect);
          }
        }
        //option 3
        if(option.equalsIgnoreCase("3")||option.equalsIgnoreCase("Help")){
          System.out.println("Help: Choose an option by typing the number or name.");
          System.out.println("Play - Show your weapons and status effects.");
          System.out.println("Shop/Bet - Shop menu or the Horse betting game.");
          System.out.println("Help - Display this help menu.");
          System.out.println("Exit - Quit the program.");
          System.out.println("You can navigate using the numbers such as 1 or 2\n You can also type the option such as Play or Shop");
        }
        
      }while(!(option.equalsIgnoreCase("Exit")||option.equalsIgnoreCase("4")));
      System.out.println("Exiting program. Goodbye!");
      input.close();
    }

  public static void weaponsOwned(Weapons customWeapons){
    System.out.println("Your weapons are the following: ");
    System.out.println(customWeapons.toString()+"\n");
  }
  public static void Statuseffects(StatusEffect defaultEffect){
    System.out.println("Your status effects are the following: ");
    System.out.println(defaultEffect.toString()+"\n");
  }
}