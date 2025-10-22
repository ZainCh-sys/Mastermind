import game.*;
import java.util.Scanner;
//add a few changes when doing part 6, make inventory array to store weapons incase of more weapons and make player vs ai into a method in player.java and call it in main. Just to make everything look a lot cleaner when combing with group mates instead of a whole mess
public class AI {
  //color code will be over here, note the color is what I have found online
  //citation for the color code: https://www.mymiller.name/wordpress/java/ansi-colors/ 
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30;1m";
    public static final String RED = "\u001B[31;1m";
    public static final String GREEN = "\u001B[32;1m";
    public static final String YELLOW = "\u001B[33;1m";
    public static final String BLUE = "\u001B[34;1m";
    public static final String MAGENTA = "\u001B[35;1m";
    public static final String CYAN = "\u001B[36;1m";
    public static final String WHITE = "\u001B[37;1m";
    private int startingCash = 1100;
    private int playerAtk = 9;
    Scanner input = new Scanner(System.in);
    private Weapons weapons = new Weapons();

    // method for the menu to show up and look like
    public void displayMenu() {
        System.out.println("\n===================");
        System.out.println(CYAN + "Welcome to Gamble Gambit" + RESET);
        System.out.println("===================");
        System.out.println(GREEN + "1. Play" + RESET);
        System.out.println(YELLOW + "2. How to Play" + RESET);
        System.out.println(RED + "3. Quit" + RESET);
    }
  public int getStartingCash(){
    return startingCash;
  }

    // option 2 how to play method showing all instructions
    public void showInstructions() {
        System.out.println(WHITE + "\n How to Play:" + RESET);
        System.out.println(WHITE + "Goal: Defeat the AI by reducing its HP to 0." + RESET);
        System.out.println(WHITE + "You and the AI start with 25 HP and 25 stamina." + RESET);
        System.out.println(WHITE + "You get $50–$350 randomly each game." + RESET);
        System.out.println(WHITE + "Buy ONE weapon or skip:" + RESET);
        System.out.println(BLUE + "- Full Auto Rifle ($100) [+2 ATK]" + RESET);
        System.out.println(BLUE + "- Burst Rifle ($200) [+1 ATK]" + RESET);
        System.out.println(BLUE + "- Semi-Auto Sniper ($300) [+3 ATK]" + RESET);
        System.out.println(BLUE + "- Or skip buying" + RESET);
        System.out.println(WHITE + "Fight options:" + RESET);
        System.out.println(CYAN + "- attack: Deal damage (costs stamina)" + RESET);
        System.out.println(CYAN + "- heal: +3 HP (max 25)" + RESET);
        System.out.println(CYAN + "- restore: +3 stamina (max 25)" + RESET);
        System.out.println(CYAN + "- quit: Exit current game" + RESET);
        System.out.println(CYAN + "AI attacks randomly for 3–7 damage each turn." + RESET);
    }

    // playGame method here, decided to put Scanner in parameter after some trial and error as 
    // play the game method should be here
    public void shop(Scanner input, StatusEffect effect) {
        // weapon price
        double autoPrice = 100;
        double burstPrice = 200;
        double sniperPrice = 300;
        int cashAmount;
        cashAmount = startingCash;
        // random money to start with from 50 - 350 dollars
        System.out.println(WHITE + "\n Amount of money: $" + cashAmount  + RESET);
         if (effect != null && effect.getDuration() > 0 && effect.effectName().equalsIgnoreCase("Price Decrease")){
          autoPrice = autoPrice*effect.getAmount();
          burstPrice = autoPrice*effect.getAmount();
          sniperPrice = autoPrice*effect.getAmount();
          effect.setTurns(effect.getTurns()-1);
        }
        // shop weapon purchase options
        System.out.println(BLUE + "\n Choose one weapon:"  + RESET);
        System.out.println(BLUE + "1. Full Auto Rifle ($100) [+2 ATK]"  + RESET);
        System.out.println(BLUE + "2. Burst Rifle ($200) [+1 ATK]"  + RESET);
        System.out.println(BLUE + "3. Semi-Auto Sniper ($300) [+3 ATK]"  + RESET);
        System.out.println(BLUE + "4. Skip weapon"  + RESET);
        
      boolean validChoice = false;

        // loop till user enters a value btwn 1 - 4 for proper weapon selection
        while (!validChoice) {
            System.out.print(WHITE + "Enter choice (1–4): "  + RESET);
            String choice = input.next();

            // check user weapon choice below and see if they have enough money to buy it
            if (choice.equals("1") && cashAmount >= autoPrice) {
                playerAtk += 2;
                System.out.println(WHITE + "You bought Full Auto Rifle!"  + RESET);
                validChoice = true;
            } else if (choice.equals("2") && cashAmount >= burstPrice) {
                playerAtk += 1;
                System.out.println(WHITE + "You bought Burst Rifle!"  + RESET);
                validChoice = true;
            } else if (choice.equals("3") && cashAmount >= sniperPrice) {
                playerAtk += 3;
                System.out.println(WHITE + "You bought Semi-Auto Sniper!"  + RESET);
                validChoice = true;
            } else if (choice.equals("4")) {
                System.out.println(WHITE + "You skipped buying a weapon."  + RESET);
                validChoice = true;
            } else {
                System.out.println(RED + "Invalid or insufficient funds."  + RESET);
            }
        } 
    }
  public void playGame(Scanner input){
         // init player and setup AI
        Player player = new Player();
        player.setHp(25);
        player.setStamina(25);
        int aiHp = 25;
        String action;

        input.nextLine(); // extra line to make input cleaner

        // player vs AI game loop
    while (!player.isGameOver() && aiHp > 0) {
            System.out.println("\n " + player);
            System.out.println(" AI HP: " + aiHp);
            System.out.print(WHITE + "Choose action (attack/heal/restore/quit): "  + RESET);
            action = input.nextLine();
            action = action.toLowerCase();

            if (action.equals("attack")) {
                if (player.getStamina() <= 0) {
                    System.out.println(RED + " Not enough stamina."  + RESET);
                } else {
                    int damage[] = {((int)(Math.random() * 5) + weapons.getSwordDamage() + (playerAtk - 5)), ((int)(Math.random() * 5) + weapons.getMagicDamage() + (playerAtk - 5)), ((int)(Math.random() * 5) + weapons.getBlasterDamage() + (playerAtk - 5))};
                    int cost = (int)(Math.random() * 3) + 3;
                    int damages = damage[(int)(Math.random() * 3) +0];
                    aiHp -= damages;
                    player.setStamina(player.getStamina() - cost);
                    System.out.println(RED + " You dealt " + damages + " damage! " + RESET + BLUE + "(-" + cost + " stamina)"  + RESET);

                    // AI Turn
                    if (aiHp > 0) {
                        int aiDmg = (int)(Math.random() * 5) + 3;
                        player.setHp(player.getHp() - aiDmg);
                        System.out.println(RED + " AI attacks for " + aiDmg + " damage!"  + RESET);
                    }
                }
            } else if (action.equals("heal")) {
                if (player.getHp() >= 25) {
                    System.out.println(GREEN + "HP is full."  + RESET);
                } else {
                    player.setHp(player.getHp() + 3);
                    System.out.println(GREEN + " Healed 3 HP."  + RESET);

                    // AI Turn
                    if (aiHp > 0) {
                        int aiDmg = (int)(Math.random() * 5) + 3;
                        player.setHp(player.getHp() - aiDmg);
                        System.out.println(RED + " AI attacks for " + aiDmg + " damage!"  + RESET);
                    }
                }
            } else if (action.equals("restore")) {
                if (player.getStamina() >= 25) {
                    System.out.println(BLUE + "Stamina is full."  + RESET);
                } else {
                    player.setStamina(player.getStamina() + 3);
                    System.out.println(BLUE + " Restored 3 stamina."  + RESET);

                    // AI Turn
                    if (aiHp > 0) {
                        int aiDmg = (int)(Math.random() * 5) + 3;
                        player.setHp(player.getHp() - aiDmg);
                        System.out.println(RED + " AI attacks for " + aiDmg + " damage!"  + RESET);
                    }
                }
            } else if (action.equals("quit")) {
                System.out.println(MAGENTA + "Quitting game..."  + RESET);
                return; //is return allowed to be used to quit the if statement? I know break is not alloweed but i remember return being allowed would need to double check
            } else {
                System.out.println(RED + "Invalid action."  + RESET);
            }
        }

        if (aiHp <= 0) {
            System.out.println(MAGENTA + " You defeated the AI!"  + RESET);
        } else {
            System.out.println(MAGENTA + " You lost the battle."  + RESET);
        }
  }
}
